package com.example.demo.specifictions;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.entity.User;

public class UserSpecification {

	public static Specification<User> hasName(String name){
		return (root, query, cb) ->
		name == null || name.isBlank()
		? null
				: cb.like(
						cb.lower(root.get("name")),
						"%" + name.toLowerCase() + "%"
						);
	}

	public static Specification<User> hasEmail(String email){
		return (root, query, cb) ->
		email == null || email.isBlank()
		? null
				: cb.equal(root.get("email"), email);
	}
}
