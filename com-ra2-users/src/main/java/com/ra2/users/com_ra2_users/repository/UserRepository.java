package com.ra2.users.com_ra2_users.repository;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public int save(String name, String description, String email, String password, LocalDateTime ultimAcces, LocalDateTime dataCreated, LocalDateTime dataUpdated) {
        String sql = "INSERT INTO users (name, description, email, password, ultimAcces, dataCreated, dataUpdated) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, name, description, email, password, ultimAcces, dataCreated, dataUpdated);
    }


}
