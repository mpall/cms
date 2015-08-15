package com.pallot.cms.it;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pallot.cms.CMSApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CMSApplication.class)
@WebIntegrationTest(value = "server.port=8080")
@SeleniumTest(driver = ChromeDriver.class)
public class CMSTest {
	@Autowired
	private WebDriver driver;

	HomePage homePage;
	
	String baseUrl = "http://localhost:8080/product/info";

	@Before
	public void setUp() throws Exception {
		homePage = PageFactory.initElements(driver, HomePage.class);
	}

	@Test
	public void assertShortDescription() throws InterruptedException {
		String url = getUrl("pen", "writing-implement-brief"); 
		driver.get(url);
		assertEquals("Great Pen", homePage.getShortDescription().getText());
	}
	
	@Test
	public void assertDescription() throws InterruptedException {
		String url = getUrl("pen", "writing-implement"); 
		driver.get(url);
		assertEquals("Great Pen", homePage.getShortDescription().getText());
		assertEquals("This is a detailed description", homePage.getDescription().getText());
	}

	private String getUrl(String product, String view) {
		return baseUrl + "/" + product + "?view=" + view;
	}
}
