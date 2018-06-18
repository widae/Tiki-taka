package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.User;

public interface UserRepository extends JpaRepository<User, Integer>  {
	List<User> findByEmailAndProvider(String email, String provider);
}