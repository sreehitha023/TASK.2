package com.msf.TaSk.controller;

import com.msf.TaSk.dto.ApiResponse;
import com.msf.TaSk.dto.AuthRequest;
import com.msf.TaSk.dto.AuthResponse;
import com.msf.TaSk.entity.User;
import com.msf.TaSk.service.UserService;
import com.msf.TaSk.utility.JWTAuthUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTAuthUtility jwtAuthUtility;

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/new")
    public String message(){
        return "Hello...!";
    }

    @PostMapping
    public String saveUser(@RequestBody User user)
    {
         userService.saveUser(user);
         logger.info("DONE");
         return "ADDED SUCCESSFULLY";
    }
    @GetMapping("/fetchAll")
    public List<User> fetchAll()
    {
        logger.info("Fetched");
        return userService.fetchAll();
    }
    @GetMapping("/fetchAll/{field}")
    public List<User> findUsersWithSorting(@PathVariable String field)
    {
        List<User> Sorting =  userService.findUsersWithSorting(field);
        return new ApiResponse<>(Sorting.size(),Sorting).getResponse();
    }

    @GetMapping ("/pagination/{offset}/{pageSize}")
    public Page<User> findUsersWithPagination(@PathVariable int offset , @PathVariable int pageSize)
    {
        Page<User> Pagination = userService.findUsersWithPagination(offset,pageSize);
        return new ApiResponse<>(Pagination.getSize(),Pagination).getResponse();
    }

    @GetMapping("/paginationAndSort/{offset}/{pageSize}/{field}")
    public Page<User> findUsersWithPaginationAndSorting(@PathVariable int offset,@PathVariable int pageSize,@PathVariable String field)
    {
        return userService.findUsersWithPaginationAndSorting(offset, pageSize, field);
    }

    @PutMapping("/update/id")
    public User update(@RequestBody User updatedUser,@PathVariable Long id)
    {
        return userService.update(id,updatedUser);
    }

    @DeleteMapping("/delete/id")
    public void deleteUser(@PathVariable Long id)
    {
         userService.deleteUser(id);
    }

    @GetMapping
    public Optional<User> findById(@PathVariable Long id)
    {
        return userService.findById(id);
    }

    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String keyword)
    {
        return userService.searchUsers(keyword);
    }

////    @PostMapping("/add")
////    public String add(@RequestBody User user)
//    {
//        return userService.add(user);
//    }
@PostMapping("/authenticate")
public AuthResponse authenticate(@RequestBody AuthRequest authRequest) throws Exception {
    try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                ));
    }catch (BadCredentialsException e) {
        throw new Exception("INVALID CREDENTIALS", e);
    }
    final UserDetails userDetails= userService.loadUserByUsername(authRequest.getUsername());
    final String token=jwtAuthUtility.generateToken(userDetails);
    return new AuthResponse(token);
}
}
