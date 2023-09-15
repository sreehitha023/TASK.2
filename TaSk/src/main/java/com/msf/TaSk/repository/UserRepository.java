package com.msf.TaSk.repository;

import com.msf.TaSk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByusernameContainingIgnoreCase(String keyword);
}
