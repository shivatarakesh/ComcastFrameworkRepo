package com.comcast.crm.orgtest;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositary.utility.CreatingNewOrganizationPage;
import com.comcast.crm.objectrepositary.utility.HomePage;
import com.comcast.crm.objectrepositary.utility.LoginPage;
import com.comcast.crm.objectrepositary.utility.OrganizationInfoPage;
import com.comcast.crm.objectrepositary.utility.OrganizationsPage;

public class DeleteOrgTest {
	public static void main(String[] args) throws Throwable {
		/* Create Objects */
		FileUtility fLib = new FileUtility();
		ExcelUtility eLib = new ExcelUtility();
		JavaUtility jLib = new JavaUtility();
		WebDriverUtility wLib=new WebDriverUtility();
		// read common data from properties file

		String BROWSER = fLib.getDataFromPropertiesFile("browser");
		String URL = fLib.getDataFromPropertiesFile("url");
		String USERNAME = fLib.getDataFromPropertiesFile("username");
		String PASSWORD = fLib.getDataFromPropertiesFile("password");

		// read testScript data from Excel file
		String orgname = eLib.getDataFromExcel("org", 10, 2) + jLib.getRandomNumber();

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
		// LoginPage lp=PageFactory.initElements(driver,LoginPage.class);
		LoginPage lp = new LoginPage(driver);
		lp.loginToApp(USERNAME, PASSWORD);

		// step 2: navigate to org module
		HomePage hp = new HomePage(driver);
		hp.getOrgLink().click();

		// step 3: click on create org button
		OrganizationsPage op = new OrganizationsPage(driver);
		op.getCreateNewOrgBtn().click();
		// step 4: enter all details and create new org
		CreatingNewOrganizationPage cnop = new CreatingNewOrganizationPage(driver);
		cnop.createOrg(orgname);

		// step 5: verify header msg expected result
		OrganizationInfoPage oip = new OrganizationInfoPage(driver);
		String actualOrgName = oip.getHeaderMsg().getText();
		if (actualOrgName.contains(orgname)) {
			System.out.println(orgname + " is verified==PASS");
		} else {
			System.out.println(orgname + "is not verified==FAIL");
		}
		//Go back to Organizations Page
		hp.getOrgLink().click();
		//Search for Organizations
		op.getSearchEdt().sendKeys(orgname);
		wLib.selectByVisibleText(op.getSearchDD(), "Organization Name");
		op.getSearchBtn().click();
		
		//In dynamic webtable select and delete org
		driver.findElement(By.xpath("//a[text()='"+orgname+"']/../../td[8]/a[text()='del']")).click();
		wLib.switchToAlertAndAccept(driver);
		// step 6: logout
		hp.logout();
		driver.quit();
	}

}
