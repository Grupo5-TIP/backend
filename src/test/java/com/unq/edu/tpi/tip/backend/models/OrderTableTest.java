package com.unq.edu.tpi.tip.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class OrderTableTest {

    OrderTable orderTable;
    State mockState;

    @BeforeEach
    public void setUp(){
        mockState = mock(State.class);
        orderTable = new OrderTable(mockState, 1, 300, 6);
    }

    @Test
    public void whenAnOrderTableIsCreatedWithConstructorItShouldEqualItsCorrespondingProperties() {

        assertEquals(orderTable.getCustomerOrder(), new ArrayList<>());
        assertEquals(orderTable.getId(), null);
        assertEquals(orderTable.getSize(), 6);
        assertEquals(orderTable.getX(), 1);
        assertEquals(orderTable.getY(), 300);
        assertEquals(orderTable.getState(), mockState);
    }
}
