package com.example.codingpractice.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID user_id;

    @Column(name = "full_name")
    private String full_name;

    @Column(name = "mob_num")
    private String mob_num;

    @Column(name = "pan_num")
    private String pan_num;

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "manager_id")
    private Manager manager_id;
    private boolean is_active;

    public User() {

    }

    public User(UUID user_id, String full_name, String mob_num, String pan_num, int manager_id, boolean is_active) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.mob_num = mob_num;
        this.pan_num = pan_num;
        this.is_active = is_active;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getMob_num() {
        return mob_num;
    }

    public void setMob_num(String mob_num) {
        this.mob_num = mob_num;
    }

    public String getPan_num() {
        return pan_num;
    }

    public void setPan_num(String pan_num) {
        this.pan_num = pan_num;
    }

    public Manager getManager_id() {
        return manager_id;
    }

    public void setManager_id(Manager manager_id) {
        this.manager_id = manager_id;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }
}
