package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	public UserService service;
    
    @GetMapping("/users") 
    public List<User> getAll() { 
    	return service.findUsers();
    }
    
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) throws UserNotFoundException {
        // Example of throwing the exception
        return service.findUser(id);
    }
    
    @PostMapping("/user")
    public User user(@Validated @RequestBody UserDto userDto) { 
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
    
    
    
}
