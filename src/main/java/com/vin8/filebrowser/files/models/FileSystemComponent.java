package com.vin8.filebrowser.files.models;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class FileSystemComponent {

    Mono<String> getName() {
        throw new UnsupportedOperationException("Can't operate on Abstract FileComponent");
    }

    Mono<String> getOwner() {
        throw new UnsupportedOperationException("Can't operate on Abstract FileComponent");
    }

    Flux<FileSystemComponent> getContent() {
        throw new UnsupportedOperationException("Can't operate on Abstract FileComponent");
    }

//    Mono<Void> create

}
