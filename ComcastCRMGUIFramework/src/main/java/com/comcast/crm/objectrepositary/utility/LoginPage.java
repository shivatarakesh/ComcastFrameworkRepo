package com.comcast.crm.objectrepositary.utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.comcast.crm.generic.webdriverutility.WebDriverUtility;

/**
 * @author Lenovo 
 * Contains login page elements & business libraries like login
 */
public class LoginPage extends WebDriverUtility {
	// Rule1: create a seperate java class
	// Rule2: Object Creation
	WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(name = "user_name")
	private WebElement userNameEdt;

	@FindBy(name = "user_password")
	private WebElement passwordEdt;

	@FindBy(id = "submitButton")
	private WebElement loginBtn;
	// Rule 3: object initialization

	// Rule4: Object Encapsulation

	public WebElement getUserNameEdt() {
		return userNameEdt;
	}

	public WebElement getPasswordEdt() {
		return passwordEdt;
	}

	public WebElement getLoginBtn() {
		return loginBtn;
	}
/**
 * login to application based on username, password arguments
 * @param username
 * @param password
 */
	// Rule 5: provide action
	public void loginToApp(String username, String password) {
		driver.manage().window().maximize();
		userNameEdt.sendKeys(username);
		passwordEdt.sendKeys(password);
		loginBtn.click();
	}

}
