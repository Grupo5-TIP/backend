package com.unq.edu.tpi.tip.backend.models;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class CustomerOrderTest {
    @Test
    public void whenAnOrderIsCreatedWithConstructorItShouldEqualItsCorrespondingProperties() {
        CustomerOrder order = new CustomerOrder(1L);

        assertEquals(order.getTableId().longValue(), 1L);
        assertEquals(order.getOrderedItems().size(), 0);
    }

    @Test
    public void shouldANewCreatedOrderNotHaveAnyOrderedItems() {
        CustomerOrder order = new CustomerOrder(1L);

        assertEquals(order.hasOrderedItems(), false);
    }

    @Test
    public void shouldAOrderWithOneItemHaveItems() {
        CustomerOrder order = new CustomerOrder(1L);
        order.getOrderedItems().add(mock(Item.class));

        assertEquals(order.hasOrderedItems(), true);
    }
}
