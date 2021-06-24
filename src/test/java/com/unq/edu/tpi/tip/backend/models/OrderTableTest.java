package com.unq.edu.tpi.tip.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

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

    @Test
    public void whenAnOrderTableIsCreatedWithEmptyConstructorItShouldEqualItsCorrespondingProperties() {
        orderTable = new OrderTable();

        assertEquals(orderTable.getCustomerOrder(), null);
        assertEquals(orderTable.getId(), null);
        assertEquals(orderTable.getSize(), null);
        assertEquals(orderTable.getX(), null);
        assertEquals(orderTable.getY(), null);
        assertEquals(orderTable.getState(), null);
    }

    @Test
    public void verifyAnOrderTableChangeToRequestBillState() {
        orderTable = new OrderTable();
        orderTable.changeToRequestBillState();

        assertEquals(orderTable.getState().getState(), "bill");
    }

    @Test
    public void verifyAnOrderTableChangeToAvailableState() {
        orderTable = new OrderTable();
        orderTable.setAvailableState();

        assertEquals(orderTable.getState().getState(), "empty");
    }

    @Test
    public void verifyAnOrderTableChangeToMercadoPagoState() {
        orderTable = new OrderTable();
        orderTable.setMercadoPagoState();

        assertEquals(orderTable.getState().getState(), "mercadoPago");
    }
}
