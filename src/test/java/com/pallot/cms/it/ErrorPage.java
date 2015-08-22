package com.pallot.cms.it;

import org.openqa.selenium.WebElement;

public class ErrorPage {
	private WebElement url;
	private WebElement dataPath;
	private WebElement parseError;
	private WebElement dataContent;
	
	public WebElement getUrl() {
		return url;
	}
	public void setUrl(WebElement url) {
		this.url = url;
	}
	public WebElement getDataPath() {
		return dataPath;
	}
	public void setDataPath(WebElement dataPath) {
		this.dataPath = dataPath;
	}
	public WebElement getParseError() {
		return parseError;
	}
	public void setParseError(WebElement parseError) {
		this.parseError = parseError;
	}
	public WebElement getDataContent() {
		return dataContent;
	}
	public void setDataContent(WebElement dataContent) {
		this.dataContent = dataContent;
	}
	
	
	
}
