package com.unq.edu.tpi.tip.backend.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderedItemKey implements Serializable {
    @Column(name = "order_id")
    Long orderId;

    @Column(name = "item_id")
    Long itemId;
}
