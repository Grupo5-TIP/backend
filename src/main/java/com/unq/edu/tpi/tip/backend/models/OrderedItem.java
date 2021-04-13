package com.unq.edu.tpi.tip.backend.models;

import javax.persistence.*;

@Entity
public class OrderedItem {
    @EmbeddedId
    OrderedItemKey id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    Item item;
}
