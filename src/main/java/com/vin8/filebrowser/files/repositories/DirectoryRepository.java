package com.vin8.filebrowser.files.repositories;

import com.vin8.filebrowser.files.models.Directory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DirectoryRepository extends ReactiveCrudRepository<Directory,String> {
    Mono<Directory> findByName(String name);
    Flux<Directory> findByOwner(String name);
    Flux<Directory> findByCreationTime(Long time);
    Flux<Directory> findByParentDirectory(String directory);
    Mono<Directory> findByNameAndParentDirectory(String name,String directory);
    Mono<Directory> findByNameAndOwner(String name,String owner);
}
