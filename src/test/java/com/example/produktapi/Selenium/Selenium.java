package com.example.produktapi.Selenium;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.*;


@SpringBootTest
public class Selenium {

    /*

    // ALLA G kRAV TESTER
    @Test
    public void checkTitleOfWebbSite(){

        WebDriver driver = new ChromeDriver();

        driver.get("https://java22.netlify.app/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        assertEquals("Webbutik", driver.getTitle(), "fel titel namn!");

        driver.quit();
    }

    @Test
    public void numberOfProductsShouldBeTwenty(){

        WebDriver driver = new ChromeDriver();

        driver.get("https://java22.netlify.app/");

        WebElement productsShowing = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("productItem")));

        List<WebElement> products = driver.findElements(By.className("productItem"));

        assertTrue(productsShowing.isDisplayed(), "Produkterna vissas inte!");
        assertEquals(20, products.size(), "Antalet produkter stämmer inte!");

        driver.quit();
    }

    @Test
    public void checkProductOneHaveRightPrices(){

        WebDriver driver = new ChromeDriver();

        driver.get("https://java22.netlify.app/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        String productOne = driver.findElement(By.xpath("//*[@id=\"productsContainer\"]/div/div[1]/div/div/p")).getText();

        String findPriceForProductOne = "109.95";

        boolean validatePriceForProductOne = productOne.contains(findPriceForProductOne);

        assertTrue(validatePriceForProductOne, "Priset på produkt 1 stämmer inte!");

        driver.quit();
    }

    @Test
    public void checkProductTwoHaveRightPrices(){

        WebDriver driver = new ChromeDriver();

        driver.get("https://java22.netlify.app/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        String productTwo = driver.findElement(By.xpath("//*[@id=\"productsContainer\"]/div/div[2]/div/div/p")).getText();

        String findPriceForProductTwo = "22.3";

        boolean validatePriceForProductTwo = productTwo.contains(findPriceForProductTwo);

        assertTrue(validatePriceForProductTwo, "Priset på produkt 2 stämmer inte!");
        driver.quit();
    }

    @Test
    public void checkProductThreeHaveRightPrices(){

        WebDriver driver = new ChromeDriver();

        driver.get("https://java22.netlify.app/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        String productThree = driver.findElement(By.xpath("//*[@id=\"productsContainer\"]/div/div[3]/div/div/p")).getText();

        String findPriceForProductThree = "55.99";

        boolean validatePriceForProductThree = productThree.contains(findPriceForProductThree);

        assertTrue(validatePriceForProductThree, "Priset på produkt 3 stämmer inte!");

        driver.quit();
    }

// EXTRA TESTER
    @Test
    public void imageShouldBeVisible(){
        WebDriver driver = new ChromeDriver();

        driver.get("https://java22.netlify.app/");

        WebElement productImage = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//img[@class='card-img-top' and contains(@src, 'https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg')]")));

        assertTrue(productImage.isDisplayed(), "Bilden verkar inte läsas in...");

        driver.quit();
    }

   @Test
    public void regex(){

       WebDriver driver = new ChromeDriver();

       driver.get("https://java22.netlify.app/");

               // ta ut priset från texten
               WebElement priceElement = new WebDriverWait(driver, Duration.ofSeconds(20))
                       .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Fin väska me plats för dator' )]")));

       // Ta ut pris från texten
       String priceText = priceElement.getText();
       String price = priceText.replaceAll("[^\\d.]", "");

       // testar att priset stämmer överens - fail genom att ge tex 95.4
       assertEquals("109.95", price, "fel pris");

       driver.quit();
   }

    @Test
    public void checkH1Text(){

        WebDriver driver = new ChromeDriver();

        driver.get("https://java22.netlify.app/");

        // ett sätt
        String h1Text = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div/h1")).getText();

        // annat sätt
        WebElement h1TextWeb = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div/h1"));
        String h1textsen = h1TextWeb.getText();

        assertEquals("Testdriven utveckling - projekt", h1Text, "Rubriken värkar inte stämma");

        driver.quit();
    }


     */
}
