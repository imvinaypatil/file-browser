package com.vin8.filebrowser.files.controllers;

import com.vin8.filebrowser.files.services.DocumentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class FilesController {

    private final DocumentsService documentsService;
    private final static String BASE_PATH = "/files";
    private final static String FILE_NAME = "{filename:.+}";

    FilesController(DocumentsService documentsService) {
        this.documentsService = documentsService;
    }

    /**
     * @return raw File
     * TODO Secure api with auhtentication
     */
    @GetMapping(value = BASE_PATH+"/"+"{user}"+"/"+FILE_NAME+"/raw")
    @ResponseBody
    public Mono<ResponseEntity<?>> getRawFile(@PathVariable String filename, @PathVariable String user) {
        return documentsService.fetchOneFile(documentsService.findByNameAndOwner(filename,user))
                .map(resource -> {
                    try {
                        return ResponseEntity.ok().contentLength(resource.contentLength())
                                .body(new InputStreamReader(resource.getInputStream()));
                    } catch (IOException e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                    }
                });
    }

    @PostMapping(BASE_PATH+"/"+"{user}"+"{parent}"+"/upload")
    public Mono<String> createFile(@RequestPart(name = "file")Flux<FilePart> files,@PathVariable String parent,@PathVariable String user) {
        return documentsService.createDocument(files,parent,user).then(Mono.just("redirect:/"));
    }

    @DeleteMapping(BASE_PATH+"/"+"{user}"+"/"+"{parent}"+"/"+FILE_NAME)
    public Mono<String> deleteFile(@PathVariable String filename,@PathVariable String parent,@PathVariable String user) {
        return documentsService.deleteDocument(filename,parent).then(Mono.just("redirect:/"));
    }

    @GetMapping("/")
    public Mono<String> index(){
        return Mono.just("index");
    }
}
