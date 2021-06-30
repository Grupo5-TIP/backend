package com.unq.edu.tpi.tip.backend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	private String username;
	@Getter
	private String password;
	@Getter
	private Boolean isAdmin;

	public User(String username, String password, Boolean isAdmin){
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
	}

}
