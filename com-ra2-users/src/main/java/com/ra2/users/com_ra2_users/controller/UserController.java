package com.ra2.users.com_ra2_users.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.ra2.users.com_ra2_users.model.User;
import com.ra2.users.com_ra2_users.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




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

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
    
    @GetMapping("/users/{user_id}")
    public ResponseEntity<User> getUserById(@PathVariable Long user_id) {
        User user = userRepository.findOne(user_id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(user);

    }

    @PutMapping("/users/{user_id}")
    public ResponseEntity<String> updateUser(@PathVariable Long user_id, @RequestBody User user) {
        
        userRepository.modifyUser(user, user_id);
        
        return ResponseEntity.ok().body("User updated successfully");
    }

    @PatchMapping("/users/{user_id}/name")
    public ResponseEntity<User> updateName(@PathVariable Long user_id, @RequestParam String name) {
        User user = userRepository.findOne(user_id);

        if (user == null) return ResponseEntity.notFound().build();

        user.setName(name);

        userRepository.modifyUser(user, user_id);

        return ResponseEntity.ok().body(userRepository.findOne(user_id));
    }

    @DeleteMapping("/users/{user_id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long user_id) {
        User user = userRepository.findOne(user_id);

        if (user != null) {
            userRepository.deleteUser(user_id);
            return ResponseEntity.ok().body("User with id " + user_id + " deleted successfully");
        }

        return ResponseEntity.notFound().build();
    }
}