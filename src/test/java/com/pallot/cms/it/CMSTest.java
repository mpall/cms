package com.pallot.cms.it;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pallot.cms.CMSApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CMSApplication.class)
@WebIntegrationTest(value = "server.port=8080")
@SeleniumTest(driver = ChromeDriver.class)
@ActiveProfiles(value="integration")
public class CMSTest {
	@Autowired
	private WebDriver driver;

	HomePage homePage;
	ErrorPage errorPage;
		
	String baseUrl = "http://localhost:8080/product/info";

	@Before
	public void setUp() throws Exception {
		homePage = PageFactory.initElements(driver, HomePage.class);
		errorPage = PageFactory.initElements(driver, ErrorPage.class);
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

	@Test
    public void getInvalidJson() throws Exception {
    	String url = getUrl("invalid", "writing-implement"); 
		driver.get(url);
		assertThat(errorPage.getUrl().getText(), containsString("product/info/invalid"));
		assertThat(errorPage.getDataPath().getText(), containsString("external/resources/data/invalid.json"));
		assertTrue("No parse error text", errorPage.getParseError().getText().length() > 0);
		assertTrue("No content", errorPage.getDataContent().getText().length() > 0);
    }
	
	@Test
	public void jsonWithMissingField(){
		String url = getUrl("pen-missing-field", "writing-implement"); 
		driver.get(url);
		assertThat(driver.getPageSource(), containsString("data.detail.shortDescription"));
	}
	
	@Test
    public void getInvalidTemplate() throws Exception {
    	String url = getUrl("pen", "badView"); 
		driver.get(url);
		assertThat(driver.getPageSource(), containsString("Exception parsing document: template=\"template/badView\", line 5 - column 3"));
    }

	private String getUrl(String product, String view) {
		return baseUrl + "/" + product + "?view=" + view;
	}
}
