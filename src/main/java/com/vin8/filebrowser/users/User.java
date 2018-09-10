package com.vin8.filebrowser.users;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Document
public class User {

    @Id
    private final String id;
    private final String username;
    @NotEmpty
    private String password;
    @Email(message = "*Please provide valid email")
    private final String email;

    public User(String id, String username, String password, @Email(message = "*Please provide valid email") String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
