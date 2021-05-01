package com.unq.edu.tpi.tip.backend.models;

import com.unq.edu.tpi.tip.backend.models.dtos.ProductDTO;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ProductTest {
    @Test
    public void whenAProductIsCreatedWithConstructorItShouldEqualItsCorrespondingProperties() {
        Product tempProduct = new Product("Agua tonica", "arrolla la sed", 100.0, "");

        assertEquals(tempProduct.getName(), "Agua tonica");
        assertEquals(tempProduct.getDescription(), "arrolla la sed");
        assertEquals(tempProduct.getPrice(), new Double(100.0));
    }

    @Test
    public void whenAProductIsAskedForItsEqualItShouldHaveTheSameNameDescriptionAndPriceIfItsTheSameObject() {
        Product tempProduct1 = new Product("Agua tonica", "arrolla la sed", 100.0, "");
        Product tempProduct2 = new Product("Agua tonica", "arrolla la sed", 100.0, "");

        assertEquals(tempProduct1, tempProduct2);
    }

    @Test
    public void shouldAProductBeDifferentToAnotherIfIsFromOtherClass() {
        Product tempProduct1 = new Product("Agua tonica", "arrolla la sed", 100.0, "");
        ProductDTO tempProductDTO = new ProductDTO();
        tempProductDTO.setDescription("arrolla la sed");
        tempProductDTO.setName("Agua tonica");
        tempProductDTO.setPrice(100.0);

        assertNotEquals(tempProduct1, tempProductDTO);
    }

    @Test
    public void shouldAProductReturnEqualsToTrueIfIsTheSameObject() {
        Product tempProduct1 = new Product("Agua tonica", "arrolla la sed", 100.0, "");
        assertEquals(tempProduct1, tempProduct1);
    }

    @Test
    public void shouldAProductBeNotEqualComparedToNull() {
        Product tempProduct1 = new Product("Agua tonica", "arrolla la sed", 100.0, "");
        assertNotEquals(tempProduct1, null);
    }

    @Test
    public void shouldHashcodeOfEmptyProductBeZero() {
        Product tempProduct = new Product();

        assertEquals(tempProduct.hashCode(), 0);
    }

    @Test
    public void shouldHashcodeOfAProductBeAsExpected() {
        Product tempProduct1 = new Product("Agua tonica", "arrolla la sed", 100.0, "");
        assertEquals(tempProduct1.hashCode(), -586002);
    }
}
