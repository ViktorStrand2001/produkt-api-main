package com.example.produktapi.repository;

import com.example.produktapi.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;

// testar i databasen
@DataJpaTest
class ProductRepositoryTest {

    // en liten constructor av ProductRepository
    @Autowired
    private ProductRepository underTest;

    @Test
    void testingOurRepository(){
        List<Product> products = underTest.findAll();
        Assertions.assertFalse(products.isEmpty());
    }

    // category
    @Test
    void findByCategory_givenValidCategory_whenFindByCategory_thenCheckCategoryNotEmptyAndReturnAnSpecificProductInCategory() {
        // given
        String category = "Elektronik";
        Product product = new Product("En dator", 250000.0, category, "bra at ha", "urlTillBild");
        underTest.save(product);

        // when
        List<Product> listProduct = underTest.findByCategory("Elektronik");

        // then
        assertFalse(listProduct.isEmpty());
        assertEquals(category, listProduct.get(0).getCategory());
    }

    @Test
    void findByNonExistingCategory_givenDeleteAll_whenFindByCategory_thenCheckCategoryIsEmpty() {
        // given
        underTest.deleteAll();

        // when
        List<Product> listProduct = underTest.findByCategory("Elektronik");

        // then
        assertTrue(listProduct.isEmpty());
    }

    // Title
    @Test
    void whenSearchingForAnExistingTitle_thenReturnThatProduct() {
        // given
        String title = "En dator";
        Product product = new Product(title, 250000.0, "Elektronik", "bra at ha", "urlTillBild");
        underTest.save(product);

        // when
        Optional<Product> optionalProduct = underTest.findByTitle("En dator");

        // then
        Assertions.assertAll(
                ()-> assertTrue(optionalProduct.isPresent()),
                ()-> assertFalse(optionalProduct.isEmpty()),
                ()-> assertEquals(product.getTitle(), optionalProduct.get().getTitle()));
    }

    @Test
    void whenSearchingForAnNonExistingTitle_thenReturnEmptyOptional() {
        // given
        String title = "En annan dator";

        // when
        Optional<Product> optionalProduct = underTest.findByTitle(title);

        // then
        Assertions.assertAll(
                () -> assertFalse(optionalProduct.isPresent()),
                () -> assertTrue(optionalProduct.isEmpty()),
                () -> assertThrows(NoSuchElementException.class, () ->optionalProduct.get(), "skicka eget felmeddelande om det blir fel"));
    }

    // find all category
    @Test
    void whenSearchingFindAllCategory_thenReturnAllFourCategories(){
        // when
        List<String> listProduct = underTest.findAllCategories();

        // then
        assertFalse(listProduct.isEmpty());
        assertEquals(listProduct.size(), 4); //
    }

    @Test
    void givenAnListOfCategoriesWithSameCategoriesAsTheRepository_whenFindAllCategories_thenCheckAllFourListCategoriesAndTheyAreSameAsTheRepositoryCategories(){
        List <String> categorys = new ArrayList<>(Arrays.asList("electronics", "jewelery", "men's clothing", "women's clothing")); // Fail electronic
        categorys.stream().distinct().collect(Collectors.toList());

        List<String> listProduct = underTest.findAllCategories();

        assertFalse(listProduct.size() > 4); // Fail annat än 4
        assertEquals(categorys, listProduct); // Jämför Listorna så det ej finns dubbel
    }
}