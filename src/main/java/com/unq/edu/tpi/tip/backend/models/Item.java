package com.unq.edu.tpi.tip.backend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "item")
@Getter
@Setter
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer amount;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "item")
    private Set<OrderedItem> orderedItems;

    public Item(Integer amount, Product product) {
        this.amount = amount;
        this.product = product;
        this.orderedItems = new HashSet<>();
    }
}
