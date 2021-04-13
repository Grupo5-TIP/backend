package com.unq.edu.tpi.tip.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
public class Product implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String description;
	private Double price;

	@Nullable
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<Item> items;

	public Product(String name, String description, Double price) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.items = new ArrayList();
	}
}
