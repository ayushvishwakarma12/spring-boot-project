package com.example.codingpractice.repository;


import com.example.codingpractice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<User, UUID> {
   // User findByMob_num(String mobNum);
    User findByUserId(UUID userId);
    User findByMobNo(String mobNo);
}
