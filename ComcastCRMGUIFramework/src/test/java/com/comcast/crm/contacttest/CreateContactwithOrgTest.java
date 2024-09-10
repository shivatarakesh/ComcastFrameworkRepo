package com.comcast.crm.contacttest;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

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
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;

public class CreateContactwithOrgTest {
	public static void main(String[] args) throws Throwable {
		/* Create Objects*/
		FileUtility fLib=new FileUtility();
		ExcelUtility eLib=new ExcelUtility();
		JavaUtility jLib=new JavaUtility();
		WebDriverUtility wLib=new WebDriverUtility();
		//read common data from properties file
		
		String BROWSER =fLib.getDataFromPropertiesFile("browser");
		String URL =fLib.getDataFromPropertiesFile("url");
		String USERNAME =fLib.getDataFromPropertiesFile("username");
		String PASSWORD =fLib.getDataFromPropertiesFile("password");
		
		//read testScript data from Excel file
		String lastname=eLib.getDataFromExcel("contact", 7, 3) +jLib.getRandomNumber();
		String orgname=eLib.getDataFromExcel("contact", 7, 4) +jLib.getRandomNumber();
		
	
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
		wLib.waitForPageToLoad(driver);
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get(URL);

		driver.findElement(By.name("user_name")).sendKeys(USERNAME);
		driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
		driver.findElement(By.id("submitButton")).click();

		// step 2: create new org
		driver.findElement(By.xpath("(//a[text()='Organizations'])[1]")).click();

		// step 3: click on create org button
		driver.findElement(By.xpath("//img[@alt='Create Organization...']")).click();

		// step 4: enter all details and create new org
		driver.findElement(By.name("accountname")).sendKeys(orgname);
		driver.findElement(By.name("button")).click();

		// step 5: verify header msg expected result
		String headerInfo = driver.findElement(By.className("dvHeaderText")).getText();
		if (headerInfo.contains(orgname)) {
			System.out.println(orgname + " is created==PASS");
		} else {
			System.out.println(orgname + "is not created==FAIL");
		}

		// step 2: navigate to contacts module
		driver.findElement(By.xpath("//a[text()='Contacts']")).click();

		// step 3: click on create contact button
		driver.findElement(By.xpath("//img[@alt='Create Contact...']")).click();

		// step 4: enter all details and create new contact
		driver.findElement(By.name("lastname")).sendKeys(lastname);
		driver.findElement(By.xpath("//img[@alt='Select']")).click();
		// switch to child window
		
		String parent=driver.getWindowHandle();
		wLib.switchNewBrowserTab(driver, "Accounts&action");
//		Set<String> set = driver.getWindowHandles();
//		Iterator<String> it = set.iterator();
//
//		while (it.hasNext()) {
//			String windowID = it.next();
//			driver.switchTo().window(windowID);
//
//			String actUrl = driver.getCurrentUrl();
//			if (actUrl.contains("Accounts&action")) {
//				break;
//			}
//		}
		driver.findElement(By.className("txtBox")).sendKeys(orgname);
		driver.findElement(By.name("search")).click();
		driver.findElement(By.xpath("//a[text()='" + orgname + "']")).click();
		// switch to parent window
//		Set<String> set1 = driver.getWindowHandles();
//		Iterator<String> it1 = set.iterator();
//
//		while (it.hasNext()) {
//			String windowID = it1.next();
//			driver.switchTo().window(windowID);
//
//			String actUrl = driver.getCurrentUrl();
//			System.out.println(actUrl);
//			if (actUrl.contains("Contacts&action")) {
//				break;
//			}
//		}
		driver.switchTo().window(parent);
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		// step 5: verify header msg expected result
		headerInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if (headerInfo.contains(lastname)) {
			System.out.println(lastname + " is created==PASS");
		} else {
			System.out.println(lastname + "is not created==FAIL");
		}

		// step 6: verify header orgname info expected result
		String actOrgname = driver.findElement(By.id("mouseArea_Organization Name")).getText();
		System.out.println(actOrgname);
		if (actOrgname.trim().equals(orgname)) {
			System.out.println(orgname + " is verified==PASS");
		} else {
			System.out.println(orgname + "is not verified==FAIL");
		}
		// step 7: logout
		WebElement logout = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		Actions act = new Actions(driver);
		act.moveToElement(logout).perform();
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		System.out.println("logout done");
		driver.quit();
	}

}
