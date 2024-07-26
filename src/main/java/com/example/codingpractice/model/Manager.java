package com.example.codingpractice.model;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "managers")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID manager_id;

    @Column(name = "full_name")
    private String full_name;

    @Column(name = "is_active")
    private String is_active;

    public Manager() {}

    public Manager(UUID manager_id, String full_name, String is_active) {
        this.manager_id = manager_id;
        this.full_name = full_name;
        this.is_active = is_active;
    }

    public UUID getManager_id() {
        return manager_id;
    }

    public void setManager_id(UUID manager_id) {
        this.manager_id = manager_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }
}
