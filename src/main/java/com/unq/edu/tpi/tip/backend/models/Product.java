package com.unq.edu.tpi.tip.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
		name = "Product.items",
		attributeNodes = @NamedAttributeNode("items")
)

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
public class Product implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String description;
	private Double price;
	private String image;

	@Nullable
	@JsonIgnore
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	private List<Item> items;

	public Product(String name, String description, Double price) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.items = new ArrayList();
	}

	@Override public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Product product = (Product) o;

		if (name != null ? !name.equals(product.name) : product.name != null)
			return false;
		if (description != null ? !description.equals(product.description) : product.description != null)
			return false;
		return price != null ? price.equals(product.price) : product.price == null;
	}

	@Override public int hashCode()
	{
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (price != null ? price.hashCode() : 0);
		return result;
	}
}
