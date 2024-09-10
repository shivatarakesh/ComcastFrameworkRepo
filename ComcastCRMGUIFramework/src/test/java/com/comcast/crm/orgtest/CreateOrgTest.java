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

public class CreateOrgTest {
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
		String orgname=eLib.getDataFromExcel("org", 1, 2) +jLib.getRandomNumber();

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
	
		driver.findElement(By.name("user_name")).sendKeys(USERNAME);
		driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
		driver.findElement(By.id("submitButton")).click();
		
		//step 2: navigate to org module
		driver.findElement(By.xpath("(//a[text()='Organizations'])[1]")).click();
		
		//step 3: click on create org button
		driver.findElement(By.xpath("//img[@alt='Create Organization...']")).click();
		
		//step 4: enter all details and create  new org
		driver.findElement(By.name("accountname")).sendKeys(orgname);
		driver.findElement(By.name("button")).click();
		
		//step 5: verify header msg expected result
		String headerInfo=driver.findElement(By.className("dvHeaderText")).getText();
		if(headerInfo.contains(orgname)) {
			System.out.println(orgname +" is created==PASS");
		}
		else {
			System.out.println(orgname+ "is not created==FAIL");
		}
		
		//step 6: verify header orgname info expected result
		String actOrgName=driver.findElement(By.id("dtlview_Organization Name")).getText();
		if(actOrgName.equals(orgname)) {
			System.out.println(orgname +" is created==PASS");
		}
		else {
			System.out.println(orgname+ "is not created==FAIL");	
		}
		//step 7: logout
		WebElement logout=driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		Actions act=new Actions(driver);
		act.moveToElement(logout).perform();
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		System.out.println("logout done");
		driver.quit();
	}

}
