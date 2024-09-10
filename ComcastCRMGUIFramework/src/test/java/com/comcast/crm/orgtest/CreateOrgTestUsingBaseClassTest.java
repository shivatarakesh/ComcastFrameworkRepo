package com.comcast.crm.orgtest;

import java.time.Duration;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.comcast.crm.basetest.BaseClass;
import com.comcast.crm.generic.webdriverutility.UtilityClassObject;
import com.comcast.crm.listnerutility.ListnerImpClass;
import com.comcast.crm.objectrepositary.utility.CreatingNewOrganizationPage;
import com.comcast.crm.objectrepositary.utility.HomePage;
import com.comcast.crm.objectrepositary.utility.LoginPage;
import com.comcast.crm.objectrepositary.utility.OrganizationInfoPage;
import com.comcast.crm.objectrepositary.utility.OrganizationsPage;
@Listeners(com.comcast.crm.listnerutility.ListnerImpClass.class)
public class CreateOrgTestUsingBaseClassTest extends BaseClass {

	@Test(groups = "smokeTest")
	public void createOrgTest() throws Throwable {
		//ListnerImpClass.test.log(Status.INFO, "read data from excel");
		UtilityClassObject.getTest().log(Status.INFO, "read data from excel");
		String orgname = eLib.getDataFromExcel("org", 1, 2) + jLib.getRandomNumber();
		// Step 1: login to app
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		// step 2: navigate to org module
		UtilityClassObject.getTest().log(Status.INFO, "Navigate to org page");

		HomePage hp = new HomePage(driver);
		hp.getOrgLink().click();

		// step 3: click on create org button
		UtilityClassObject.getTest().log(Status.INFO, "Navigate to create org page");

		OrganizationsPage op = new OrganizationsPage(driver);
		op.getCreateNewOrgBtn().click();
		// step 4: enter all details and create new org
		UtilityClassObject.getTest().log(Status.INFO, "Create a new org");

		CreatingNewOrganizationPage cnop = new CreatingNewOrganizationPage(driver);
		cnop.createOrg(orgname);
		UtilityClassObject.getTest().log(Status.INFO, orgname+"===>Create a new org");

		// step 5: verify header msg expected result
		OrganizationInfoPage oip = new OrganizationInfoPage(driver);
		String actualOrgName = oip.getHeaderMsg().getText();
		if (actualOrgName.contains(orgname)) {
			System.out.println(orgname + " is verified==PASS");
		} else {
			System.out.println(orgname + "is not verified==FAIL");
		}

	}

	@Test(groups = "regressionTest")
	public void createOrgTestWithIndustry() throws Throwable {
		String orgname = eLib.getDataFromExcel("org", 4, 2) + jLib.getRandomNumber();
		String IndustryName = eLib.getDataFromExcel("org", 4, 3);
		String TypeName = eLib.getDataFromExcel("org", 4, 4);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		// step 2: navigate to org module
		HomePage hp = new HomePage(driver);
		hp.getOrgLink().click();
		// step 3: click on create org button
		OrganizationsPage op = new OrganizationsPage(driver);
		op.getCreateNewOrgBtn().click();
		// step 4: enter all details and create new org
		CreatingNewOrganizationPage cnop = new CreatingNewOrganizationPage(driver);
		cnop.createOrg(orgname, IndustryName);

		// verify the industry and type info
		OrganizationInfoPage oip = new OrganizationInfoPage(driver);
		String actualOrgName = oip.getHeaderMsg().getText();
		if (actualOrgName.contains(orgname)) {
			System.out.println(orgname + " is verified==PASS");
		} else {
			System.out.println(orgname + "is not verified==FAIL");
		}
	}

	@Test(groups = "regressionTest")
	public void createOrgTestWithPhoneNumber() throws Throwable {
		String orgname = eLib.getDataFromExcel("org", 7, 2) + jLib.getRandomNumber();
		String phoneNumber = eLib.getDataFromExcel("org", 7, 3);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		// step 2: navigate to org module
		HomePage hp = new HomePage(driver);
		hp.getOrgLink().click();
		// step 3: click on create org button
		OrganizationsPage op = new OrganizationsPage(driver);
		op.getCreateNewOrgBtn().click();
		// step 4: enter all details and create new org
		CreatingNewOrganizationPage cnop = new CreatingNewOrganizationPage(driver);
		cnop.createOrgwithPhno(orgname, phoneNumber);

		// step 5: verify phno as expected result
		String actPhoneNo = driver.findElement(By.id("dtlview_Phone")).getText();
		if (actPhoneNo.equals(phoneNumber)) {
			System.out.println(phoneNumber + " is verified=PASS");
		} else {
			System.out.println(phoneNumber + "is not verified==FAIL");
		}
	}

}
