package com.unq.edu.tpi.tip.backend.models;

import com.unq.edu.tpi.tip.backend.models.dtos.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductTest {

    Product aProduct;

    @BeforeEach
    public void setUp(){
        aProduct = new Product("Agua tonica", "arrolla la sed", 100.0, "");
    }
    @Test
    public void whenAProductIsCreatedWithConstructorItShouldEqualItsCorrespondingProperties() {


        assertEquals(aProduct.getName(), "Agua tonica");
        assertEquals(aProduct.getDescription(), "arrolla la sed");
        assertEquals(aProduct.getPrice(), new Double(100.0));
        assertEquals(aProduct.getId(), null);
        assertEquals(aProduct.getImage(), "");
        assertEquals(aProduct.getItems(), new ArrayList());
    }

    @Test
    public void whenAProductIsAskedForItsEqualItShouldHaveTheSameNameDescriptionAndPriceIfItsTheSameObject() {
        Product tempProduct2 = new Product("Agua tonica", "arrolla la sed", 100.0, "");

        assertEquals(aProduct, tempProduct2);
    }

    @Test
    public void shouldAProductBeDifferentToAnotherIfIsFromOtherClass() {
        ProductDTO tempProductDTO = new ProductDTO();
        tempProductDTO.setDescription("arrolla la sed");
        tempProductDTO.setName("Agua tonica");
        tempProductDTO.setPrice(100.0);

        assertNotEquals(aProduct, tempProductDTO);
    }

    @Test
    public void shouldAProductReturnEqualsToTrueIfIsTheSameObject() {
        assertEquals(aProduct, aProduct);
    }

    @Test
    public void shouldAProductBeNotEqualComparedToNull() {
        assertNotEquals(aProduct, null);
    }

    @Test
    public void shouldHashcodeOfEmptyProductBeZero() {
        Product tempProduct = new Product();

        assertEquals(tempProduct.hashCode(), 0);
    }

    @Test
    public void shouldHashcodeOfAProductBeAsExpected() {
        assertEquals(aProduct.hashCode(), -586002);
    }

    @Test
    public void givenAProductWithNullNameAndAnotherProductWithNameDistinctToNullReturnsFalse(){
        Product mockProduct = mock(Product.class);
        when(mockProduct.getName()).thenReturn("");
        assertNotEquals(aProduct, mockProduct);

    }
            // product.name != null
}
