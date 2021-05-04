package com.unq.edu.tpi.tip.backend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "order_table")
@Getter
@Setter
@NoArgsConstructor
public class OrderTable
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	private State state;
	private Integer x;
	private Integer y;
	private Integer size;

	@OneToMany
	private List<CustomerOrder> customerOrder;

	public OrderTable(State state, Integer x, Integer y, Integer size) {
		this.state = state;
		this.x = x;
		this.y = y;
		this.size = size;
	}
}
