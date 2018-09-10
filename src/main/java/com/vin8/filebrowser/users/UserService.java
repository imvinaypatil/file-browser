//package com.vin8.filebrowser.users.services;
//
//import com.vin8.filebrowser.users.User;
//import com.vin8.filebrowser.users.UserRepository;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import java.util.UUID;
//
//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.userRepository = userRepository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }
//
//    public Mono<User> findUserByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
//
//    public Mono<User> saveUser(Mono<User> user) {
//        return user.map(u -> {
//           u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
//           return u;
//        });
//    }
//
//}
