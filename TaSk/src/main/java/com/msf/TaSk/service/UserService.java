package com.msf.TaSk.service;

import com.msf.TaSk.entity.User;
import com.msf.TaSk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    public User saveUser(User user)
    {
        return userRepository.save(user);
    }

    public List<User> fetchAll()
    {
        return userRepository.findAll();
    }

    public List<User> findUsersWithSorting(String field)
    {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    public Page<User> findUsersWithPagination(int offset, int pageSize)
    {
        Page<User> users = userRepository.findAll(PageRequest.of(offset, pageSize));
        return users;
    }

    public Page<User> findUsersWithPaginationAndSorting(int offset, int pageSize, String field)
    {
        Page<User> users = userRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return users;

    }

    @CachePut(cacheNames = "users", key = "#id")
    public User update(Long id, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(updatedUser.getUsername());
            user.setAddress(updatedUser.getAddress());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        }
        return null;
    }

    @CacheEvict(cacheNames = "users", key = "#id")
    public void deleteUser(Long id)
    {
        userRepository.deleteById(id);
    }
    @Cacheable(cacheNames = "users",key = "#id")
    public Optional<User> findById(Long id)
    {
        return userRepository.findById(id);
    }

//    public String add(User user){
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
//        return "Added successfully";
//    }

    public List<User> searchUsers(String keyword)
    {
        return userRepository.findByusernameContainingIgnoreCase(keyword);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new org.springframework.security.core.userdetails.User("sree","password",new ArrayList<>());

    }
}
