package com.example.produktapi;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProduktapiApplicationTests {

	/*
	@Test
	public void checkTitle(){
		// Hämta in den web-driver som jag vill använda
		WebDriver driver = new ChromeDriver();

		// Navigera till den webbsida som ska testas
		driver.get("https://svtplay.se");

		// Testa om förväntad titel matchar med webbplatsens titel
		assertEquals("SVT Play", driver.getTitle(), "Title those not match!");

		driver.quit();
	}

	@Test
	public void checkStiTitle(){

		WebDriver driver = new ChromeDriver();

		driver.get("https://www.sti.se");

		assertEquals("STI - YH-program och vidareutbildningar inom teknik & IT", driver.getTitle(), "wrong titel");

		driver.quit();
	}


	 */
}
