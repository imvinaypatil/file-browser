package com.vin8.filebrowser.files;

import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class DocumentController {

    private final DocumentService documentService;
    private final static String BASE_PATH = "/files";
    private final static String FILE_NAME = "{filename:.+}";

    DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * @return raw File
     * TODO Secure api with auhtentication
     */
    @GetMapping(value = BASE_PATH+"/"+"{user}"+"/"+FILE_NAME+"/raw")
    @ResponseBody
    public Mono<ResponseEntity<?>> getRawFile(@PathVariable String filename, @PathVariable String user) {
        return documentService.fetchOneFile(documentService.findByNameAndOwner(filename,user))
                .map(resource -> {
                    try {
                        return ResponseEntity.ok().contentLength(resource.contentLength())
                                .body(new InputStreamReader(resource.getInputStream()));
                    } catch (IOException e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                    }
                });
    }

    @PostMapping(value = BASE_PATH+"/"+"{user}"+"/list")
    @ResponseBody
    public Flux<Document> listFiles(@RequestParam String path) {
        return documentService.findByParentDirectory(path);
    }

    @PostMapping(BASE_PATH+"/"+"{user}"+"/upload")
    public Mono<String> createFile(@RequestPart(name = "file")Flux<FilePart> files,@RequestParam String parent,@PathVariable String user) {
        return documentService.createDocument(files,parent,user).then(Mono.just("redirect:/"));
    }

    @DeleteMapping(BASE_PATH+"/"+"{user}"+"/delete"+"/"+"{fileid}")
    public Mono<String> deleteFile(@PathVariable String fileid,@RequestParam String filename,@RequestParam String parent,@PathVariable String user) {
        return documentService.deleteDocument(fileid,filename,parent).then(Mono.just("redirect:/"));
    }

    @PostMapping(BASE_PATH+"/"+"{user}"+"/create"+"/directory")
    public Mono<Document> createDirectory(@RequestParam String name,@RequestParam String path,@PathVariable String user) {
        return documentService.createDirectory(name,path,user);
    }

}
