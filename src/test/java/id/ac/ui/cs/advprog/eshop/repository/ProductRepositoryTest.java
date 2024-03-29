package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testCreateEmptyField(){
    }
    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9e46b-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testUpdateProduct() {
        // Given
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // When
        product.setProductName("Sampo Cap Baru");
        product.setProductQuantity(150);
        productRepository.update(product);

        // Then
        Product updatedProduct = productRepository.findProductById(product.getProductId());
        assertNotNull(updatedProduct);
        assertEquals("Sampo Cap Baru", updatedProduct.getProductName());
        assertEquals(150, updatedProduct.getProductQuantity());
    }

    @Test
    void testUpdateNonExistentProduct() {
        // Given
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        nonExistentProduct.setProductName("Non-existent Product");
        nonExistentProduct.setProductQuantity(50);

        // When
        assertThrows(IllegalArgumentException.class, () ->
            productRepository.update(nonExistentProduct));
    }

    @Test
    void testPartialUpdateProduct() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // When
        product.setProductName("Sampo Cap Baru");
        productRepository.update(product);

        // Then
        Product updatedProduct = productRepository.findProductById(product.getProductId());
        assertNotNull(updatedProduct);
        assertEquals("Sampo Cap Baru", updatedProduct.getProductName());
        assertEquals(100, updatedProduct.getProductQuantity());

    }


    @Test
    void testDeleteProduct() {
        // Given
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // When
        productRepository.delete(productId);

        // Then
        Product deletedProduct = productRepository.findProductById(productId);
        assertNull(deletedProduct);
    }

    @Test
    void testDeleteProductNotFound() {
        assertThrows(IllegalArgumentException.class, () ->
                productRepository.delete("6f1238f8-d13a-4e5b-936f-e55156158104"));
    }

}
