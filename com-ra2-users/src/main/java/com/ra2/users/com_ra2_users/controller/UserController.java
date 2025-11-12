package com.ra2.users.com_ra2_users.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.ra2.users.com_ra2_users.model.User;
import com.ra2.users.com_ra2_users.service.UserService;

import java.sql.Timestamp;
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
    UserService userService;

    // ---------------------- POST /api/users ----------------------
    @PostMapping("/users")
    public ResponseEntity<String> postUser(@RequestBody User user) {    

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Crida al repositori per inserir un nou usuari a la base de dades
        userService.save(
            user.getName(),
            user.getDescription(),
            user.getEmail(),
            user.getPassword(),
            timestamp,
            timestamp,
            timestamp
        );

        // Retorna un missatge d’èxit amb el codi HTTP 201 (Created)
        String msg = "User inserted correctly";

        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

    // ---------------------- GET /api/users ----------------------
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        // Obté la llista completa d’usuaris des de la base de dades
        List<User> users = userService.findAll();

        // Si la llista és buida, retorna 404 (Not Found)
        if (users.isEmpty()) return ResponseEntity.notFound().build();

        // Si hi ha usuaris, retorna 200 (OK) amb la llista
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
    
    // ---------------------- GET /api/users/{user_id} ----------------------
    @GetMapping("/users/{user_id}")
    public ResponseEntity<User> getUserById(@PathVariable Long user_id) {
        // Busca un usuari pel seu ID
        User user = userService.findOne(user_id);

        // Si no existeix, retorna 404 (Not Found)
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Si existeix, retorna 200 (OK) amb l’usuari trobat
        return ResponseEntity.ok().body(user);

    }

    // ---------------------- PUT /api/users/{user_id} ----------------------
    @PutMapping("/users/{user_id}")
    public ResponseEntity<String> updateUser(@PathVariable Long user_id, @RequestBody User user) {
        // Si l'usuari no existeix retorna 404 NOT FOUND
        if (userService.findOne(user_id) == null) return ResponseEntity.notFound().build();
        
        // Actualitza tots els camps de l’usuari amb les noves dades
        
        userService.modifyUser(user, user_id);
        
        // Retorna 200 (OK) amb un missatge de confirmació
        return ResponseEntity.ok().body("User updated successfully");
    }

    // ---------------------- PATCH /api/users/{user_id}/name ----------------------
    @PatchMapping("/users/{user_id}/name")
    public ResponseEntity<User> updateName(@PathVariable Long user_id, @RequestParam String name) {
        // Busca l’usuari que s’ha d’actualitzar
        User user = userService.findOne(user_id);

        // Si no existeix, retorna 404
        if (user == null) return ResponseEntity.notFound().build();

        // Modifica només el nom
        user.setName(name);

        // Actualitza el registre a la base de dades
        userService.modifyUser(user, user_id);

        // Retorna l’usuari actualitzat
        return ResponseEntity.ok().body(userService.findOne(user_id));
    }

    // ---------------------- DELETE /api/users/{user_id} ----------------------
    @DeleteMapping("/users/{user_id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long user_id) {
        // Busca l’usuari que s’ha d’eliminar
        User user = userService.findOne(user_id);

        // Si existeix, l’elimina i retorna un missatge de confirmació
        if (user != null) {
            userService.deleteUser(user_id);
            return ResponseEntity.ok().body("User with id " + user_id + " deleted successfully");
        }

        // Si no existeix, retorna 404 (Not Found)
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/users/{user_id}/image")
    public ResponseEntity<String> postImage(@PathVariable Long user_id, @RequestParam MultipartFile imageFile) {
        return userService.uploadImage(user_id, imageFile);
    }

    @PostMapping("/users/upload-csv")
    public ResponseEntity<String> uploadCsv(@RequestParam MultipartFile csvFile) {
        String result = userService.uploadCsv(csvFile);
        
        if (result == null) {
            return ResponseEntity.internalServerError().body("No s'ha afegit cap usuari");
        }

        return ResponseEntity.ok().body(result);
    }
    
    
}