package com.vin8.filebrowser.files;

import com.vin8.filebrowser.files.repositories.DirectoryRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class DocumentService {
    private final ResourceLoader resourceLoader;
    private final DocumentRepository documentRepository;
    private final DirectoryRepository directoryRepository;
    private final String UPLOAD_ROOT = System.getProperty("user.dir").concat("\\repository");

    public DocumentService(ResourceLoader resourceLoader, DocumentRepository documentRepository, DirectoryRepository directoryRepository) {
        this.resourceLoader = resourceLoader;
        this.documentRepository = documentRepository;
        this.directoryRepository = directoryRepository;
    }

    public Mono<Resource> fetchOneFile(Mono<Document> documentMono) {
        return documentMono.map(d -> resourceLoader.getResource("file:"+UPLOAD_ROOT+"/"+d.getOwner()+"/"+d.getName()));
    }

    /*
    * TODO append filename with Document id for stroring unique File in the FileSystem
    */
    public Mono<Void> createDocument(Flux<FilePart> files, String directory,String owner) {
        return files.flatMap(filePart -> {
            Mono<Document> saveDatabaseDocument = documentRepository.save(new Document(UUID.randomUUID().toString(),filePart.filename(),directory,owner,System.currentTimeMillis(), "file"));
            Mono<Void> copyFile = Mono.just(Paths.get(UPLOAD_ROOT,owner,filePart.filename()).toFile())
                    .log("copying a file to the target directory")
                    .map(dest -> {
                        try {
                            dest.createNewFile();
                            return dest;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }}).flatMap(filePart::transferTo);
            return Mono.when(saveDatabaseDocument,copyFile);
        }).then();
    }

    /*
    * TODO append filename with Document id */
    public Mono<Void> deleteDocument(String filename,String directory) {
        final Mono<Document> documentMono = documentRepository.findByNameAndParentDirectory(filename,directory);
        Mono<Void> deleteDBEntry = documentMono.flatMap(documentRepository::delete);
        Mono<Void> deleteFile = Mono.fromRunnable(() -> {
            documentMono.flatMap(document -> {
                try {
                    Files.deleteIfExists(Paths.get(UPLOAD_ROOT,document.getOwner(),filename));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Mono.empty();
            });
        });
        return Mono.when(deleteDBEntry,deleteFile).then();
    }

    public Mono<Document> findByName(String name) {
        return documentRepository.findByName(name);
    }
    public Flux<Document> findByOwner(String name) {
        return documentRepository.findByOwner(name);
    }
    public Flux<Document> findByCreationTime(Long time) {
        return documentRepository.findByCreationTime(time);
    }
    public Flux<Document> findByParentDirectory(String directory) {
        return documentRepository.findByParentDirectory(directory);
    }
    public Mono<Document> findByNameAndParentDirectory(String name,String directory) {
        return documentRepository.findByNameAndParentDirectory(name,directory);
    }
    public Mono<Document> findByNameAndOwner(String filename,String owner) {
        return documentRepository.findByNameAndOwner(filename,owner);
    }
}
