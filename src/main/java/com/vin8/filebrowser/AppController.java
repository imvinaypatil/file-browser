package com.vin8.filebrowser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class AppController {


    @GetMapping("/")
    public Mono<String> index(){
        return Mono.just("index");
    }
}
