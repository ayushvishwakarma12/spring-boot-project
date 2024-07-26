package com.example.codingpractice.repository;


import com.example.codingpractice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Integer> {
    User findByMob_num(String mobNum);
    User findByUser_id(String userId);

}
