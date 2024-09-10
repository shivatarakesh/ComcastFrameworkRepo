package com.comcast.crm.contacttest;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.objectrepositary.utility.ContactsInfoPage;
import com.comcast.crm.objectrepositary.utility.ContactsPage;
import com.comcast.crm.objectrepositary.utility.CreatingNewContactPage;
import com.comcast.crm.objectrepositary.utility.HomePage;
import com.comcast.crm.objectrepositary.utility.LoginPage;
import com.comcast.crm.objectrepositary.utility.OrganizationsPage;

public class CreateContactTestUsingPom {
	public static void main(String[] args) throws Throwable {
		/* Create Object */
		FileUtility fLib= new FileUtility();
		ExcelUtility eLib=new ExcelUtility();
		JavaUtility jLib=new JavaUtility();
		
		// read common data from properties file
		String BROWSER = fLib.getDataFromPropertiesFile("browser");
		String URL = fLib.getDataFromPropertiesFile("url");
		String USERNAME = fLib.getDataFromPropertiesFile("username");
		String PASSWORD = fLib.getDataFromPropertiesFile("password");

		// read testScript data from Excel file
		String lastname=eLib.getDataFromExcel("contact", 1, 3) + jLib.getRandomNumber();

		WebDriver driver = null;

		if (BROWSER.equals("chrome")) {
			driver = new ChromeDriver();
		} else if (BROWSER.equals("edge")) {
			driver = new EdgeDriver();
		} else {
			driver = new ChromeDriver();
		}
		// Step 1: login to app
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get(URL);
		LoginPage lp = new LoginPage(driver);
		lp.loginToApp(USERNAME, PASSWORD);
		
		//step 2: navigate to org module
		HomePage hp = new HomePage(driver);
		hp.getContactLink().click();		
		//step 3: click on create org button
		ContactsPage cp = new ContactsPage(driver);
		cp.getCreateNewContactBtn().click();		
		// step 4: enter all details and create new org
		
		CreatingNewContactPage cncp=new CreatingNewContactPage(driver);
		cncp.getLastnameEdt().sendKeys(lastname);
		cncp.getSaveBtn().click();

		// step 5: verify header msg expected result
		ContactsInfoPage cip=new ContactsInfoPage(driver);
		
		String headerInfo = cip.getHeaderInfo().getText();//driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (headerInfo.contains(lastname)) {
			System.out.println(lastname + " is created==PASS");
		} else {
			System.out.println(lastname + "is not created==FAIL");
		}

		// step 6: verify header lastname info expected result
		String actLastName = cip.getActLastName().getText();//driver.findElement(By.id("dtlview_Last Name")).getText();
		if (actLastName.equals(lastname)) {
			System.out.println(lastname + " is verified==PASS");
		} else {
			System.out.println(lastname + "is not verified==FAIL");
		}
		// step 7: logout
		hp.logout();
		System.out.println("logout done");
		driver.quit();
	}

}
