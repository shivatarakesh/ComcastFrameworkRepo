package com.comcast.crm.objectrepositary.utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CreatingNewOrganizationPage {
	WebDriver driver;
	public CreatingNewOrganizationPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(name="accountname")
	private WebElement orgNameEdt;
	
	@FindBy(xpath="//input[@title='Save [Alt+S]']")
	private WebElement saveBtn;
	
	@FindBy(name="industry")
	private WebElement industryDD;
	
	@FindBy(id="phone")
	private WebElement phonenumber;
	
	public WebElement getPhonenumber() {
		return phonenumber;
	}
	public WebElement getOrgNameEdt() {
		return orgNameEdt;
	}
	public WebElement getSaveBtn() {
		return saveBtn;
	}
	public WebElement getIndustryDD() {
		return industryDD;
	}
	public void createOrg(String orgName) {
		getOrgNameEdt().sendKeys(orgName);
		getSaveBtn().click();
	}
	public void createOrg(String orgName,String industry) {
		orgNameEdt.sendKeys(orgName);
		Select sel=new Select(industryDD);
		sel.selectByVisibleText(industry);
		getSaveBtn().click();
		//saveBtn.click();
	}
	public void createOrgwithPhno(String orgName,String phoneno) {
		getOrgNameEdt().sendKeys(orgName);
		getPhonenumber().sendKeys(phoneno);
		getSaveBtn().click();
	}
	

	
	

}
