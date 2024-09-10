package com.comcast.crm.objectrepositary.utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrganizationsSelectPage {
	WebDriver driver;
	public OrganizationsSelectPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(className ="txtBox")
	private WebElement searchBox;
	
	@FindBy(name="search")
	private WebElement searchButton;
	
	public WebElement getSearchBox() {
		return searchBox;
	}

	public WebElement getSearchButton() {
		return searchButton;
	}

	
	
	public void selectOrg(String orgName) {
		getSearchBox().sendKeys(orgName);
		getSearchButton().click();
	
	}

	
	

}
