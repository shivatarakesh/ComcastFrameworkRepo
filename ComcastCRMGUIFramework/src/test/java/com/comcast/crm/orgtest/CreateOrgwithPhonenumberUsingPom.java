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

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.objectrepositary.utility.CreatingNewOrganizationPage;
import com.comcast.crm.objectrepositary.utility.HomePage;
import com.comcast.crm.objectrepositary.utility.LoginPage;
import com.comcast.crm.objectrepositary.utility.OrganizationsPage;

public class CreateOrgwithPhonenumberUsingPom {
	public static void main(String[] args) throws Throwable {
		/* Create Objects*/
		FileUtility fLib=new FileUtility();
		ExcelUtility eLib=new ExcelUtility();
		JavaUtility jLib=new JavaUtility();
		//read common data from properties file
		
		String BROWSER =fLib.getDataFromPropertiesFile("browser");
		String URL =fLib.getDataFromPropertiesFile("url");
		String USERNAME =fLib.getDataFromPropertiesFile("username");
		String PASSWORD =fLib.getDataFromPropertiesFile("password");
		
		//read testScript data from Excel file
		String orgname=eLib.getDataFromExcel("org", 7, 2) +jLib.getRandomNumber();
		String phoneNumber=eLib.getDataFromExcel("org", 7, 3);
		
		WebDriver driver=null;
		
		if(BROWSER.equals("chrome")) {
			 driver=new ChromeDriver();
		}
		else if(BROWSER.equals("edge")) {
			 driver=new EdgeDriver();
		}
		else{
			 driver=new ChromeDriver();
		}
		//Step 1: login to app
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get(URL);
	
		LoginPage lp = new LoginPage(driver);
		lp.loginToApp(USERNAME, PASSWORD);
		
		//step 2: navigate to org module
		HomePage hp = new HomePage(driver);
		hp.getOrgLink().click();		
		//step 3: click on create org button
		OrganizationsPage op = new OrganizationsPage(driver);
		op.getCreateNewOrgBtn().click();		
		//step 4: enter all details and create  new org
		CreatingNewOrganizationPage cnop = new CreatingNewOrganizationPage(driver);
		cnop.createOrgwithPhno(orgname, phoneNumber);
		
		//step 5: verify phno as expected result
		String actPhoneNo=driver.findElement(By.id("dtlview_Phone")).getText();
		if(actPhoneNo.equals(phoneNumber)) {
			System.out.println(phoneNumber +" is verified=PASS");
		}
		else {
			System.out.println(phoneNumber+ "is not verified==FAIL");	
		}
		//step 7: logout
		hp.logout();
		System.out.println("logout done");
		driver.quit();
	}

}
