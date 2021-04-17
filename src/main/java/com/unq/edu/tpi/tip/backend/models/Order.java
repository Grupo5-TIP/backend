package com.unq.edu.tpi.tip.backend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer_order")
@Getter
@Setter
@NoArgsConstructor
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private OrderTable orderTable;
	private Long tableId;

	@OneToMany(cascade = {CascadeType.ALL}, mappedBy = "order")
	Set<Item> orderedItems;

	public Order(Long tableId) {
		this.tableId = tableId;
		this.orderedItems = new HashSet<>();
	}

	public boolean hasOrderedItems()
	{
		return ! this.orderedItems.isEmpty();
	}
}
