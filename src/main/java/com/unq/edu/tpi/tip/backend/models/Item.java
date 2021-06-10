package com.unq.edu.tpi.tip.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "item")
@NoArgsConstructor
public class Item implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Getter
    @Setter
    private Integer amount;

    @Getter
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_id")
    @Fetch(FetchMode.JOIN)
    private Product product;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "customerOrder_id")
    @Getter
    @Setter
    private CustomerOrder customerOrder;

    public Item(Integer amount, Product product) {
        this.amount = amount;
        this.product = product;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Item item = (Item) o;

        return product != null ? product.equals(item.product) : item.product == null;
    }

    @Override public int hashCode()
    {
        return product != null ? product.hashCode() : 0;
    }
}
