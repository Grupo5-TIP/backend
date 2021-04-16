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


	/*@ManyToMany
	Set<Item> items;*/

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
			name = "ordered_item",
			joinColumns = @JoinColumn(name = "order_id"),
			inverseJoinColumns = @JoinColumn(name = "item_id"))
	Set<Item> orderedItems;

	public Order(Long tableId) {
		this.tableId = tableId;
		//this.items = new HashSet<>();
		this.orderedItems = new HashSet<>();
	}

	public boolean hasOrderedItems()
	{
		return ! this.orderedItems.isEmpty();
	}
}
