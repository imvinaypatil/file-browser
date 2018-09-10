package com.vin8.filebrowser.users;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User,String> {
   Mono<User> findByUsername(String username);
}
