package com.vin8.filebrowser.files.repositories;

import com.vin8.filebrowser.files.models.Directory;
import com.vin8.filebrowser.files.models.Document;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DirectoryRepository extends ReactiveCrudRepository<Directory,String> {
    Mono<Document> findByName(String name);
    Flux<Document> findByOwner(String name);
    Flux<Document> findByCreationTime(Long time);
    Flux<Document> findByParentDirectory(String directory);
    Mono<Document> findByNameAndParentDirectory(String name,String directory);
}
