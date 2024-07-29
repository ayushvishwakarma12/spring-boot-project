package com.example.codingpractice.repository;


import com.example.codingpractice.model.Manager;
import com.example.codingpractice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ManagerJpaRepository extends JpaRepository<Manager, UUID> {
    Manager findByManagerId(UUID managerId);
}
