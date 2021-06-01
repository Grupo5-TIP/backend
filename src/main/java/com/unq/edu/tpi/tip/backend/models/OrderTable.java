package com.unq.edu.tpi.tip.backend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_table")
@NoArgsConstructor
public class OrderTable
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	private Long id;

	@Getter
	@ManyToOne(cascade = {CascadeType.MERGE})
	private State state;

	@Getter
	private Integer x;
	@Getter
	private Integer y;
	@Getter
	private Integer size;

	@OneToMany
	@Getter
	private List<CustomerOrder> customerOrder;

	public OrderTable(State state, Integer x, Integer y, Integer size) {
		this.state = state;
		this.x = x;
		this.y = y;
		this.size = size;
		customerOrder = new ArrayList<>();
	}

	public void changeToRequestBillState()
	{
		this.state = new RequestBillState();
	}
}
