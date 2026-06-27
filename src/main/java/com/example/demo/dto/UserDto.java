package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

public class UserDto {

	private Long id;
	
	@NotBlank(message = "name cannot be blank")
	private String name; 
	
	@Email(message = "email is not valid format")
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserDto(@NotBlank(message = "name cannot be blank") String name,
			@Email(message = "email is not valid format") String email) {
		super();
		this.name = name;
		this.email = email;
	}

	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
		
}
