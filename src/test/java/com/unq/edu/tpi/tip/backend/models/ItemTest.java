package com.unq.edu.tpi.tip.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemTest {

    Product mockProduct;
    Item item;

    @BeforeEach
    public void setUp(){
        mockProduct = mock(Product.class);
        item = new Item(1, mockProduct);
    }
    @Test
    public void whenAnItemIsCreatedWithConstructorItShouldEqualItsCorrespondingProperties() {
        CustomerOrder customerOrderMock = mock(CustomerOrder.class);
        item.setCustomerOrder(customerOrderMock);
        assertEquals(item.getId(), null);
        assertEquals(item.getAmount(), 1);
        assertEquals(item.getProduct(), mockProduct);
        assertEquals(item.getCustomerOrder(), customerOrderMock);

        item.setAmount(2);
        assertEquals(item.getAmount(), 2);
    }
    @Test
    public void whenAnItemIsAskedForItsEqualItShouldHaveTheSameAmountAndProductIfItsTheSameItem() {
        Item item2 = new Item(1, mockProduct);

        assertEquals(item, item2);
    }

    @Test
    public void shouldAnItemReturnEqualsToTrueIfIsTheSameObject() {
        assertEquals(item, item);
    }

    @Test
    public void shouldAnItemReturnEqualsToFalseIfItsComparedToNull() {
        assertNotEquals(item, null);
    }

    @Test
    public void comparingOneItemWithOneProductMustReturnFalse(){
        assertNotEquals(item, mockProduct);
    }

    @Test
    public void comparingAnItemWithAmountEqualsToNullReturnsFalse(){
        Product anotherProductMock = mock(Product.class);
        Item anotherItem = new Item(null, anotherProductMock);
        assertNotEquals(anotherItem, item);
    }

    @Test
    public void comparingAnItemWithAmountOneAndAnotherItemWithAmountNullReturnsFalse(){
        Product anotherProductMock = mock(Product.class);
        Item anotherItem = new Item(null, anotherProductMock);
        assertNotEquals(item, anotherItem);
    }

    @Test
    public void comparingAnItemWithAmountOneAndAnotherItemWithAmountNullReturnsFalseT(){
        Product anotherProductMock = mock(Product.class);
        Item anotherItem = new Item(null, anotherProductMock);
        item = new Item(null, mockProduct);
        assertNotEquals(item, anotherItem);
    }

    @Test
    public void comparingAnItemWithProductNullWithAnotherItemWithAProductReturnsFalse(){
        Item anotherItem = new Item(null, mockProduct);
        item = new Item(2, null);
        assertNotEquals(item, anotherItem);
    }

    @Test
    public void comparingAnItemWithProductNullWithAnotherItemWithANullProductReturnsTrue(){
        Item anotherItem = new Item(2, null);
        item = new Item(2, null);
        assertEquals(item, anotherItem);
    }

    @Test
    public void comparingUsingHashCodeWithAmountAndProductNullHasHashCodeZero(){
        item = new Item(null, null);
        assertEquals(item.hashCode(), 0);
    }

    @Test
    public void comparingUsingHashCodeWithoutAmountAndProducHasHashCodeZero(){
        item = new Item();
        assertEquals(item.hashCode(), 0);
    }



}
