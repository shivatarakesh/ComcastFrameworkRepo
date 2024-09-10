package com.comcast.crm.listnerutility;

import java.io.File;	
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.comcast.crm.basetest.BaseClass;
import com.comcast.crm.generic.webdriverutility.UtilityClassObject;
/**
 * @author Shiva
 * 
 */
public class ListnerImpClass implements ITestListener, ISuiteListener {
	public ExtentReports report ;
	public static ExtentTest test;

	@Override
	public void onStart(ISuite suite) {
		System.out.println("Report Configuration");
		String time=new Date().toString().replace(" ", "_").replace(":", "_");

		ExtentSparkReporter spark = new ExtentSparkReporter("./AdvanceReport/report"+time+".html");
		spark.config().setDocumentTitle("CRM test suite Results");
		spark.config().setReportName("CRM Report");
		spark.config().setTheme(Theme.DARK);
		

		// Add env Information and create test
		 report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("OS", "Windows-10");
		report.setSystemInfo("BROWSER", "CHROME-100");
		
	}

	@Override
	public void onFinish(ISuite suite) {
		System.out.println("Report Backup");
		report.flush();
	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("===" + result.getMethod().getMethodName() + "===START===");
		test = report.createTest(result.getMethod().getMethodName());
		UtilityClassObject.setTest(test);
		test.log(Status.INFO,  result.getMethod().getMethodName() + "===>START<===");

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("===" + result.getMethod().getMethodName() + "===END===");
		test.log(Status.PASS,  result.getMethod().getMethodName() + "===>Completed<===");


	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String time=new Date().toString().replace(" ", "_").replace(":", "_");
		
		 TakesScreenshot ts=(TakesScreenshot) BaseClass.sdriver;
		 String filepath=ts.getScreenshotAs(OutputType.BASE64);
		 
		 test.addScreenCaptureFromBase64String(filepath, testName+"_"+time);
			test.log(Status.FAIL,  result.getMethod().getMethodName() + "===>Failed<===");

		 
//		 TakesScreenshot ts=(TakesScreenshot) BaseClass.sdriver;
//			File temp=ts.getScreenshotAs(OutputType.FILE);
//			try {
//				FileHandler.copy(temp, new File("./Screenshots/"+testName+"+"+time+".png"));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

//		EventFiringWebDriver edriver = new EventFiringWebDriver(BaseClass.sdriver);
//
//		File srcFile = edriver.getScreenshotAs(OutputType.FILE);
//
//		try {
//			FileUtils.copyFile(srcFile, new File("./Screenshots/"+testName+".png"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

}
