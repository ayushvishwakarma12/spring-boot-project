package com.example.codingpractice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "managers")
@JsonPropertyOrder({
        "manager_id",
        "full_name",
        "is_active"
})
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty("manager_id")
    private UUID managerId;

    @Column(name = "full_name")
    @JsonProperty("full_name")
    private String fullName;

    @Column(name = "is_active")
    @JsonProperty("is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "manager")
    private List<User> users;

    public Manager() {}

    public Manager(UUID managerId, String fullName, boolean isActive) {
        this.managerId = managerId;
        this.fullName = fullName;
        this.isActive = isActive;
    }

    public UUID getManagerId() {
        return managerId;
    }

    public void setManagerId(UUID managerId) {
        this.managerId = managerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
