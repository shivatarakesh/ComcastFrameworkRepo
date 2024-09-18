package com.comcast.crm.basetest;

import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.comcast.crm.generic.databaseutility.DataBaseUtility;
import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.UtilityClassObject;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositary.utility.HomePage;
import com.comcast.crm.objectrepositary.utility.LoginPage;

public class BaseClass {
	
	public FileUtility fLib = new FileUtility();
	public ExcelUtility eLib = new ExcelUtility();
	public JavaUtility jLib = new JavaUtility();
	public DataBaseUtility dLib=new DataBaseUtility();
	public WebDriverUtility wLib=new WebDriverUtility();
	public WebDriver driver=null;
	public static WebDriver sdriver=null;
//	public ExtentSparkReporter spark;
//	public ExtentReports report;

	@BeforeSuite(groups= {"smokeTest","regressionTest"})
	public void configBS() throws SQLException {
		System.out.println("===connect to Db,Report Config===");
		dLib.getDbconnection();
		
//		// Spark Report Config
//		ExtentSparkReporter spark = new ExtentSparkReporter("./AdvanceReport/report.html");
//		spark.config().setDocumentTitle("CRM test suite Results");
//		spark.config().setReportName("CRM Report");
//		spark.config().setTheme(Theme.DARK);
//
//		// Add env Information and create test
//		 report = new ExtentReports();
//		report.attachReporter(spark);
//		report.setSystemInfo("OS", "Windows-10");
//		report.setSystemInfo("BROWSER", "CHROME-100");
	}
	
	
	//@Parameters("BROWSER")
	@BeforeClass(groups= {"smokeTest","regressionTest"})
	public void configBC(/*String browser*/) throws Throwable {
		System.out.println("===Launch The Browser===");
	//	String BROWSER =/*browser;*/  fLib.getDataFromPropertiesFile("browser");	
		String BROWSER =System.getProperty("browser", fLib.getDataFromPropertiesFile("browser"));	
		if(BROWSER.equals("chrome")) {
			 driver=new ChromeDriver();
		}
		else if(BROWSER.equals("edge")) {
			 driver=new EdgeDriver();
		}
		else if(BROWSER.equals("firefox")) {
			 driver=new FirefoxDriver();
		}
		else{
			 driver=new ChromeDriver();
		}
		sdriver =driver;
		UtilityClassObject.setDriver(driver);
	}
	
	@BeforeMethod(groups= {"smokeTest","regressionTest"})
	public void configBM() throws Throwable {
		System.out.println("===Login===");
		wLib.waitForPageToLoad(driver);
		String URL =fLib.getDataFromPropertiesFile("url");
		driver.get(URL);
		String USERNAME =fLib.getDataFromPropertiesFile("username");
		String PASSWORD =fLib.getDataFromPropertiesFile("password");
		LoginPage lp=new LoginPage(driver);
		lp.loginToApp( USERNAME,PASSWORD);
	}
	@AfterMethod(groups= {"smokeTest","regressionTest"})
	public void configAM() {
		System.out.println("===Logout===");
		HomePage hp=new HomePage(driver);
		hp.logout();
		
	}
	@AfterClass(groups= {"smokeTest","regressionTest"})
	public void configAC() {
		System.out.println("===Close The Browser===");
		driver.quit();
	}
	@AfterSuite(groups= {"smokeTest","regressionTest"})
	public void configAS() throws SQLException {
		System.out.println("===connect to Db,Report Backup===");
		dLib.closeDbconnection();
		
//		report.flush();
	}
	

	

}
