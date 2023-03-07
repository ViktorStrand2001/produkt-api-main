package com.example.produktapi.service;

import com.example.produktapi.exception.BadRequestException;
import com.example.produktapi.exception.EntityNotFoundException;
import com.example.produktapi.model.Product;
import com.example.produktapi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.*;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // bibliotek med funktionalitet för mock.
class ProductServiceTest {

    @Mock
    private ProductRepository repository; // En fake ProductRepository inte den riktiga.

    @InjectMocks
    private ProductService underTest; // Här ska fake ProductRepository användas.

    @Captor
    ArgumentCaptor<Product> productCaptor; // Fångar argument
    @Captor
    ArgumentCaptor<String> stringCaptor; // Fångar argument

    /*
    Product testProduct;
    @BeforeEach
    void Setup(){
        Product testProduct = new Product("testTitel", 1.0, "testKategori", "testBeskrivning", "");
        testProduct.setId(1);
    }
     */

    // All Products
    @Test
    void whenGetAllProducts_thenExactlyOneInteractionWithRepositoryMethodFindAll() {
        // when
        underTest.getAllProducts();

        // then
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository); // BDDMockito.
    }

    // All CATEGORY
    @Test
    void whenGetAllCategories_thenExactlyOneInteractionWithRepositoryMethodByCategory() {
        // when
        underTest.getAllCategories();
        // then
        verify(repository, times(1)).findAllCategories();
        verifyNoMoreInteractions(repository);
    }

    // Get product by category
    @Test
    void givenAnExistingCategory_whenGetProductByCategory_thenReceivesANonEmptyCategory() {

        // given
        Product product = new Product("",1.0,"my category","","");

        // then
        underTest.getProductsByCategory("my category");

        // when
        verify(repository, times(1)).findByCategory(stringCaptor.capture());
        verifyNoMoreInteractions(repository);
        assertEquals("my category", stringCaptor.getValue());
        assertFalse(product.getCategory().isEmpty());
    }


    // ID
    @Test
    void givenExistingProductId_whenGetProductById_thenFindProductIdIsPresentAndCorrectProductFound() {
        // given
        Product product = new Product("aaa",1.0 ,"aaa", "aaa", "aaa"); // ingen riktig produkt
        product.setId(1);

        given(repository.findById(product.getId())).willReturn(Optional.of(product));  // rätt id returnerar en product

        Product testProduct = underTest.getProductById(product.getId());

        // when
        // then
        assertEquals(product.getId(), testProduct.getId());
        assertEquals(product, testProduct);
    }

    @Test
    void givenNonExistingProductId_whenGetProductById_thenThrowsEntityNotFoundException(){

        given(repository.findById(1)).willReturn(Optional.empty());

        // when
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> underTest.getProductById(1));

        // then
        assertEquals(String.format("Produkt med id %d hittades inte", 1), exception.getMessage());
    }

    // ADD
    @Test
    // name:
    void whenAddingAProduct_thenSaveMethodShouldBeCalled() {
        //given
        Product product = new Product("Dator", 4000.0, "", "", "");

        //when
        underTest.addProduct(product);
        verify(repository).save(productCaptor.capture()); // eftersom att mock inte skickar tillbaka något så måste man använda Captor.

        //then
        assertEquals(product, productCaptor.getValue());
    }

    @Test
    void whenAddingProductWithDuplicateType_thenThrowError(){
        // given
        Product product = new Product("test", 30.0, "", "", "");
        given(repository.findByTitle(product.getTitle())).willReturn(Optional.of(product));

        // when
        // then
        BadRequestException exception = assertThrows(BadRequestException.class, () -> underTest.addProduct(product));

        verify(repository, times(1)).findByTitle(product.getTitle());
        verify(repository, never()).save(product);
        assertEquals("En produkt med titeln: " + "test" + " finns redan", exception.getMessage());
    }

    // UPDATE
    @Test
    void updateProduct_givenValidId_whenTryingToUpdateProduct_thenUpdateProductById() {

        Product product = new Product("gamal title", 1.0, "aaa", "aaa", "aaa");

        Product nyaProduct = new Product("nya title", 2.0, "ddd", "ddd", "ddd");

        when(repository.findById(product.getId())).thenReturn(Optional.of(product));

        underTest.updateProduct(nyaProduct, product.getId());

        verify(repository, times(1)).save(productCaptor.capture());
        assertEquals(nyaProduct, productCaptor.getValue());
    }

    @Test
    void updateProduct_givenNonValidId_whenTryingToUpdateProduct_thenThrowEntityNotFoundException(){

        given(repository.findById(1)).willReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> underTest.updateProduct(any(), 1));

        assertEquals(String.format("Produkt med id %d hittades inte", 1), exception.getMessage());
    }

    // DELETE
    @Test
    void givenValidId_whenDeleteProduct_thenDeleteProductById(){

        // given
        Product product = new Product("title", 1.0, "aaa", "aaa", "aaa");
        product.setId(1);

        // when
        when(repository.findById(product.getId())).thenReturn(Optional.of(product));
        underTest.deleteProduct(product.getId());

        // then
        verify(repository, times(1)).deleteById(1);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deleteNoMatch_givenNonValidId_whenDeleteProduct_thenThrowEntityNotFoundException(){

        given(repository.findById(1)).willReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> underTest.deleteProduct(1));

        verify(repository, never()).deleteById(any());
        assertEquals(String.format("Produkt med id %d hittades inte", 1), exception.getMessage());
    }
}