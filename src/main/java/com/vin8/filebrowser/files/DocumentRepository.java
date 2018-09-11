package com.vin8.filebrowser.files;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DocumentRepository extends ReactiveCrudRepository<Document,String> {
    Mono<Document> findByName(String name);
    Flux<Document> findByOwner(String name);
    Flux<Document> findByCreationTime(Long time);
    Flux<Document> findByParentDirectory(String directory);
    Mono<Document> findByNameAndParentDirectory(String name,String directory);
    Mono<Document> findByNameAndOwner(String name,String owner);
    Mono<Document> findByNameParentDirectoryAndType(String name,String directory,String type);
}
