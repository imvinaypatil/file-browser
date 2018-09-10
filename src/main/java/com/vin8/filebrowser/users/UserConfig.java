//package com.vin8.filebrowser.users.config;
//
//import com.vin8.filebrowser.users.User;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import reactor.core.publisher.Mono;
//
//import java.util.UUID;
//
//@Configuration
//public class config {
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        return bCryptPasswordEncoder;
//    }
//
//    @Bean
//    public CommandLineRunner setup(BCryptPasswordEncoder bCryptPasswordEncoder) {
//        return (args -> {
//            User u = new User(UUID.randomUUID().toString(),"vinay","password","vin@gmail.com");
//            u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
//        });
//
//    }
//
//}
