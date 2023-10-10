package com.msf.TaSk.service;

import com.msf.TaSk.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    List<User> fetchAll();
    List<User> findUsersWithSorting(String field);
    Page<User> findUsersWithPagination(int offset, int pageSize);
    Page<User> findUsersWithPaginationAndSorting(int offset, int pageSize, String field);
    User update(Long id, User updatedUser);
    void deleteUser(Long id);
    Optional<User> findById(Long id);
    List<User> searchUsers(String keyword);
    UserDetails loadUserByUsername(String username);

}
