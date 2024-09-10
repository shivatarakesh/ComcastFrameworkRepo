package com.comcast.crm.objectrepositary.utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreatingNewContactPage {
	WebDriver driver;
	public CreatingNewContactPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(name="lastname")
	private WebElement lastnameEdt;
	
	@FindBy(xpath="//input[@title='Save [Alt+S]']")
	private WebElement saveBtn;
	
	@FindBy(name="support_start_date")
	private WebElement supportStartdate;
	
	@FindBy(name="support_end_date")
	private WebElement supportEnddate;
	
	@FindBy(xpath="//img[@alt='Select']")
	private WebElement OrgImgSelectButton;
	
	public WebElement getOrgImgSelectButton() {
		return OrgImgSelectButton;
	}

	public WebElement getSupportStartdate() {
		return supportStartdate;
	}

	public WebElement getSupportEnddate() {
		return supportEnddate;
	}

	public WebElement getLastnameEdt() {
		return lastnameEdt;
	}

	public WebElement getSaveBtn() {
		return saveBtn;
	}
	
	public void createContact(String lastname) {
		getLastnameEdt().sendKeys(lastname);
		getSaveBtn().click();		
	}
	public void createContactWithSupportDate(String lastname, String StartDate, String EndDate) {
		getLastnameEdt().sendKeys(lastname);
		getSupportStartdate().clear();
		getSupportStartdate().sendKeys(StartDate);
		getSupportEnddate().clear();
		getSupportEnddate().sendKeys(EndDate);
		getSaveBtn().click();
		
	}
	
	

}
