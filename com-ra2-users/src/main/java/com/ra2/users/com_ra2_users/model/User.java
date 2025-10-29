package com.ra2.users.com_ra2_users.model;

import java.sql.Time;

public class User {

    private Long id;
    private String name;
    private String description;
    private String email;
    private String password;
    private Time ultimAcces;
    private Time dataCreated;
    private Time dataUpdated;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Time getUltimAcces() {
        return ultimAcces;
    }
    public void setUltimAcces(Time ultimAcces) {
        this.ultimAcces = ultimAcces;
    }
    public Time getDataCreated() {
        return dataCreated;
    }
    public void setDataCreated(Time dataCreated) {
        this.dataCreated = dataCreated;
    }
    public Time getDataUpdated() {
        return dataUpdated;
    }
    public void setDataUpdated(Time dataUpdated) {
        this.dataUpdated = dataUpdated;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", description=" + description + ", email=" + email + ", password="
                + password + ", ultimAcces=" + ultimAcces + ", dataCreated=" + dataCreated + ", dataUpdated="
                + dataUpdated + "]";
    }

    
}
