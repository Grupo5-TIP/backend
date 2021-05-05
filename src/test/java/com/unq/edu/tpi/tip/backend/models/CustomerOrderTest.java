package com.unq.edu.tpi.tip.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class CustomerOrderTest {

    CustomerOrder order;
    @BeforeEach
    public void setUp(){
        order = new CustomerOrder(1L);
    }
    @Test
    public void whenAnOrderIsCreatedWithConstructorItShouldEqualItsCorrespondingProperties() {
        assertEquals(order.getTableId().longValue(), 1L);
        assertEquals(order.hasOrderedItems(), false);
        assertEquals(order.getId(), null);
        assertEquals(order.getIsChecked(), false);
        assertEquals(order.getOrderedItems(), new ArrayList<>());
        assertEquals(order.getOrderTable(), null);
    }

    @Test
    public void shouldAOrderWithOneItemHaveItems() {
        CustomerOrder order = new CustomerOrder(1L);
        order.setOrderedItems(Arrays.asList(mock(Item.class)));

        assertEquals(order.hasOrderedItems(), true);
    }
}
