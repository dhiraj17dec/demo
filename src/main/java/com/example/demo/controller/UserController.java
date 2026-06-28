package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserSummary;
import com.example.demo.entity.User;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {
	
	@Autowired
	public UserService service;
    
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) throws UserNotFoundException {
        // Example of throwing the exception
        return service.findUser(id);
    }
    
    @PostMapping("/user")
    public User user(@Valid @RequestBody UserDto userDto) { 
    	return service.createUser(userDto); 
    }
    
    @PutMapping("/user")
    public void updateUser(@RequestBody UserDto userDto) { 
    	service.updateUser(userDto); 
    }
    
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id) throws UserNotFoundException { 
    	service.deleteUser(id); 
    }
    
    @GetMapping("/users")
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return service.getUsers(page, size,sortBy,direction);
    }
    
    @GetMapping("/users/email/{email}")
    public User getByEmail(@PathVariable String email) throws UserNotFoundException{

        return service.getUserByEmail(email);
    }
    
    @GetMapping("/users/search")
    public User getByNameAndEmail(@RequestParam("name") String name,
            @RequestParam("email") String email) throws UserNotFoundException{
        return service.getUserByNameEmail(name,email);
    }
    
    @GetMapping("/users/all")
    public List<User> getUsersOrderByName() throws UserNotFoundException{
        return service.getUsersOrderByName();
    }
    
    @GetMapping("/users/allSummary")
    public List<UserSummary> getAllUsersSummary() throws UserNotFoundException{
        return service.getUserSummary();
    }
    
}
