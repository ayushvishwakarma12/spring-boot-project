package com.example.codingpractice.repository;


import com.example.codingpractice.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerJpaRepository extends JpaRepository<Manager, Integer> {
}
