package com.ra2.users.com_ra2_users.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.ra2.users.com_ra2_users.model.User;
import com.ra2.users.com_ra2_users.repository.UserRepository;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/users")
    public ResponseEntity<String> postUser(@RequestBody User user) {    
        
        userRepository.save(
            user.getName(),
            user.getDescription(),
            user.getEmail(),
            user.getPassword(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now()
        );

        
        String msg = "User received: ";

        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }
    
}
