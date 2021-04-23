package com.unq.edu.tpi.tip.backend.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

public class ItemTest {
    @Test
    public void whenAnItemIsCreatedWithConstructorItShouldEqualItsCorrespondingProperties() {
        Product mockProduct = mock(Product.class);
        Item item = new Item(1, mockProduct);

        assertEquals(item.getAmount(), 1);
        assertEquals(item.getProduct(), mockProduct);
    }
    @Test
    public void whenAnItemIsAskedForItsEqualItShouldHaveTheSameAmountAndProductIfItsTheSameItem() {
        Product mockProduct = mock(Product.class);
        Item item1 = new Item(1, mockProduct);
        Item item2 = new Item(1, mockProduct);

        assertEquals(item1, item2);
    }

    @Test
    public void shouldAnItemReturnEqualsToTrueIfIsTheSameObject() {
        Product mockProduct = mock(Product.class);
        Item item = new Item(1, mockProduct);

        assertEquals(item, item);
    }

    @Test
    public void shouldAnItemReturnEqualsToFalseIfItsComparedToNull() {
        Product mockProduct = mock(Product.class);
        Item item = new Item(1, mockProduct);

        assertNotEquals(item, null);
    }

    @Test
    public void shouldHashcodeOfEmptyItemBeZero() {
        Item item = new Item();

        assertEquals(item.hashCode(), 0);
    }
}
