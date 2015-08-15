package com.pallot.cms.it;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePage {
	private WebElement text;
	@FindBy(how = How.ID, using = "template-name")
	private WebElement templateName;
	private WebElement shortDescription;
	private WebElement description;

	public WebElement getText() {
		return text;
	}

	public void setText(WebElement text) {
		this.text = text;
	}

	public WebElement getTemplateName() {
		return templateName;
	}

	public void setTemplateName(WebElement templateName) {
		this.templateName = templateName;
	}
	
	public WebElement getShortDescription() {
		return shortDescription;
	}
	
	public void setShortDescription(WebElement shortDescription) {
		this.shortDescription = shortDescription;
	}

	public WebElement getDescription() {
		return description;
	}
	
	public void setDescription(WebElement description) {
		this.description = description;
	}

}
