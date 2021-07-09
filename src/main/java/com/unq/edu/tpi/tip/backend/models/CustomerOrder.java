package com.unq.edu.tpi.tip.backend.models;

import lombok.Getter;
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
public class CustomerOrder implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	@Setter
	private Long id;

	@ManyToOne
	@Getter
	private OrderTable orderTable;
	@Getter
	@Setter
	private Long tableId;
	@Getter
	private Boolean isChecked;

	@OneToMany(cascade = {CascadeType.MERGE}, mappedBy = "customerOrder")
	@Getter
	@Setter
	List<Item> orderedItems;

	public CustomerOrder() {
		this.isChecked = false;
	}

	public CustomerOrder(Long tableId) {
		this.tableId = tableId;
		this.orderedItems = new ArrayList<>();
		this.isChecked = false;
	}

	public boolean hasOrderedItems()
	{
		return ! this.orderedItems.isEmpty();
	}

	public void check()
	{
		this.isChecked = true;
	}
}
