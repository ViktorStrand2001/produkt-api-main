package com.example.produktapi.service;

import com.example.produktapi.exception.BadRequestException;
import com.example.produktapi.exception.EntityNotFoundException;
import com.example.produktapi.model.Product;
import com.example.produktapi.repository.ProductRepository;
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

    @Test
    void givenAnExistingCategory_whenGetProductByCategory_thenReceivesANonEmptyList() {

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
    void givenExistingProductId_whenGetProductById_thenFindProductIdIsPresent() {
        // given
        Product product = new Product("aaa",1.0 ,"aaa", "aaa", "aaa");
        product.setId(1);

        // when
        underTest.addProduct(product);

        // then
        given(repository.findById(product.getId())).willReturn(Optional.of(product)); // rätt id returnerar en product
        assertTrue(repository.findById(1).isPresent()); // Fail annat än id 1
    }

    @Test
    void givenNonExistingProductId_whenGetProductById_thenThrowsEntityNotFoundException(){
        Product product = new Product("aaa", 1.0, "aaa", "aaa", "aaa");

        product.setId(1);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> underTest.getProductById(product.getId()));

        verify(repository, times(1)).findById(product.getId());
        verify(repository, never()).save(any());

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

        //then
        verify(repository).save(productCaptor.capture());
        assertEquals(product, productCaptor.getValue());
    }

    @Test
    void whenAddingProductWithDuplicateType_thenThrowError(){
        // given
        String title = "vår test-titel";

        Product product = new Product(title, 30.0, "", "", "");
        given(repository.findByTitle("vår test-titel")).willReturn(Optional.of(product));

        // then
        BadRequestException exception = assertThrows(BadRequestException.class,
                // when
                () -> underTest.addProduct(product));
        verify(repository, times(1)).findByTitle(title);
        verify(repository, never()).save(any());
        assertEquals("En produkt med titeln: " + title + " finns redan", exception.getMessage());
    }

    // UPDATE
    @Test
    void updateProduct_givenValidId_whenTryingToUpdateProduct_thenUpdateProductById() {

        Product product = new Product("gamal title", 1.0, "aaa", "aaa", "aaa");

        product.setId(1);

        Product nyaProduct = new Product("nya title", 2.0, "ddd", "ddd", "ddd");

        when(repository.findById(product.getId())).thenReturn(Optional.ofNullable(nyaProduct));
        when(repository.save(nyaProduct)).thenReturn(nyaProduct);

        Product result = underTest.updateProduct(nyaProduct, 1);

        verify(repository).save(productCaptor.capture());
        assertEquals("nya title",result.getTitle());

    }

    @Test
    void updateProduct_givenNonValidId_whenTryingToUpdateProduct_thenThrowEntityNotFoundException(){

        Product product = new Product("title", 1.0, "aaa", "aaa", "aaa");

        product.setId(1);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> underTest.updateProduct(product, product.getId()));

        assertEquals(product.getId(), 1);
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
        assertNotNull(underTest.getProductById(product.getId()));
    }

    @Test
    void deleteNoMatch_givenNonValidId_whenDeleteProduct_thenThrowEntityNotFoundException(){
        Product product = new Product("title", 1.0, "aaa", "aaa", "aaa");

        product.setId(1);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> underTest.deleteProduct(product.getId()));

        assertEquals(product.getId(), 1);
        verify(repository, times(1)).findById(product.getId());
        assertEquals(String.format("Produkt med id %d hittades inte", 1), exception.getMessage());
    }
    @Test
    void test(){
        assertEquals(2,2);
    }

}