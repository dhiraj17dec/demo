package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.UserSummary;
import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>  {

	@Query("SELECT u FROM User u WHERE u.email=:email")
	Optional<User> findByEmail(String email);

	@Query("""
			SELECT u
			FROM User u
			WHERE u.name=:name
			AND u.email=:email
			""")
	Optional<User> findByNameAndEmail( String name,String email);

	@Query("""
			SELECT u
			FROM User u
			ORDER BY u.name
			""")
	Optional<List<User>> getUsersOrderByName();

	@Query("""
			SELECT new com.example.demo.dto.UserSummary(u.name,u.email)
			FROM User u
			""")
	Optional<List<UserSummary>> getUserSummary();

	@Query(value="""
			SELECT *
			FROM users
			WHERE email=:email
			""",
			nativeQuery=true)
	User getUserByEmail(String email);

	List<User> findByName(String name);

	List<User> findByNameContaining(String name);

	List<User> findByEmailEndingWith(String domain);

	boolean existsByEmail(String email);

	long countByName(String name);

}