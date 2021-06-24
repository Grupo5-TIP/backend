package com.unq.edu.tpi.tip.backend.repositories;

import com.unq.edu.tpi.tip.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
	Optional<User> findByUsernameAndPassword(String username, String password);
}
