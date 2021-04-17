package com.unq.edu.tpi.tip.backend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
		name = "CustomerOrder.orderedItems",
		attributeNodes = @NamedAttributeNode("orderedItems")
)

@Entity
@Table(name = "customerorder")
@Getter
@Setter
@NoArgsConstructor
public class CustomerOrder implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private OrderTable orderTable;
	private Long tableId;

	@OneToMany(cascade = {CascadeType.ALL}, mappedBy = "customerOrder")
	List<Item> orderedItems;

	public CustomerOrder(Long tableId) {
		this.tableId = tableId;
		this.orderedItems = new ArrayList<>();
	}

	public boolean hasOrderedItems()
	{
		return ! this.orderedItems.isEmpty();
	}
}
