package com.comcast.crm.contacttest;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.comcast.crm.basetest.BaseClass;
import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.objectrepositary.utility.ContactsInfoPage;
import com.comcast.crm.objectrepositary.utility.ContactsPage;
import com.comcast.crm.objectrepositary.utility.CreatingNewContactPage;
import com.comcast.crm.objectrepositary.utility.CreatingNewOrganizationPage;
import com.comcast.crm.objectrepositary.utility.HomePage;
import com.comcast.crm.objectrepositary.utility.LoginPage;
import com.comcast.crm.objectrepositary.utility.OrganizationsPage;
import com.comcast.crm.objectrepositary.utility.OrganizationsSelectPage;
/**
 * @author Lenovo
 * 
 */
public class CreateContactTestUsingBaseClass extends BaseClass {

	@Test(groups = "smokeTest")
	public void CreateContactTestUsingBaseClas() throws Throwable {
		/* read testScript data from Excel file*/
		String lastname = eLib.getDataFromExcel("contact", 1, 3) + jLib.getRandomNumber();

		/* step 2: navigate to org module */
		HomePage hp = new HomePage(driver);
		hp.getContactLink().click();

		/* step 3: click on create org button */
		ContactsPage cp = new ContactsPage(driver);
		cp.getCreateNewContactBtn().click();

		/* step 4: enter all details and create new org */
		CreatingNewContactPage cncp = new CreatingNewContactPage(driver);
		cncp.createContact(lastname);

		/* step 6: verify header lastname info expected result */
		ContactsInfoPage cip = new ContactsInfoPage(driver);
		String headerInfo =cip.getHeaderInfo().getText(); //driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		boolean status= headerInfo.contains(lastname);
		Assert.assertTrue(status);
		
		String actLastName = cip.getActLastName().getText();// driver.findElement(By.id("dtlview_Last Name")).getText();
		SoftAssert soft=new SoftAssert();
		soft.assertEquals(actLastName, lastname);
		soft.assertAll();
	}

	@Test(groups = "regressionTest")
	public void CreateContactwithSupportDateTest() throws Throwable {
		/* read testScript data from Excel file */
		String lastname = eLib.getDataFromExcel("contact", 4, 3) + jLib.getRandomNumber();

		/* step 2: navigate to org module */
		HomePage hp = new HomePage(driver);
		hp.getContactLink().click();

		/* step 3: click on create org button */
		ContactsPage cp = new ContactsPage(driver);
		cp.getCreateNewContactBtn().click();

		/* step 4: enter all details and create new org */
		String StartDate = jLib.getSystemDateYYYYDDMM();
		String afterDateRequires = jLib.getRequiredDateYYYYDDMM(30);
		CreatingNewContactPage cncp = new CreatingNewContactPage(driver);
		cncp.createContactWithSupportDate(lastname, StartDate, afterDateRequires);

		/* step 5: verify header msg expected result */
		String actualStartDate = driver.findElement(By.id("dtlview_Support Start Date")).getText();
//		if (actualStartDate.contains(StartDate)) {
//			System.out.println(StartDate + " is verified==PASS");
//		} else {
//			System.out.println(StartDate + "is not verified==FAIL");
//		}
		boolean status1= actualStartDate.contains(StartDate);
		Assert.assertTrue(status1);
		

		/* step 6: verify header orgname info expected result */
		String actEndDate = driver.findElement(By.id("dtlview_Support End Date")).getText();
//		if (actEndDate.equals(afterDateRequires)) {
//			System.out.println(afterDateRequires + " is verified==PASS");
//		} else {
//			System.out.println(afterDateRequires + "is not verified==FAIL");
//		}
		Assert.assertEquals(actEndDate, afterDateRequires);
	}

	@Test(groups = "regressionTest")
	public void CreateContactwithOrgTest() throws Throwable {
		String lastname = eLib.getDataFromExcel("contact", 7, 3) + jLib.getRandomNumber();
		String orgname = eLib.getDataFromExcel("contact", 7, 4) + jLib.getRandomNumber();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		/* step 2: navigate to org module
		HomePage hp = new HomePage(driver);
		hp.getOrgLink().click();

		/* step 3: click on create org button */
		OrganizationsPage op = new OrganizationsPage(driver);
		op.getCreateNewOrgBtn().click();

		/* step 4: enter all details and create new org */
		CreatingNewOrganizationPage cnop = new CreatingNewOrganizationPage(driver);
		cnop.createOrg(orgname);

		/* step 5: verify header msg expected result */
		String headerInfoo = driver.findElement(By.className("dvHeaderText")).getText();
		
		if (headerInfoo.contains(orgname)) {
			System.out.println(orgname + " is created==PASS");
		} else {
			System.out.println(orgname + "is not created==FAIL");
		}

		// step 2: navigate to org module 
		HomePage hp = new HomePage(driver);
		hp.getContactLink().click();

		/* step 3: click on create org button */
		ContactsPage cp = new ContactsPage(driver);
		cp.getCreateNewContactBtn().click();

		/* step 4: enter all details and create new contact */
		CreatingNewContactPage cncp=new CreatingNewContactPage(driver);
		cncp.getLastnameEdt().sendKeys(lastname);
		cncp.getOrgImgSelectButton().click();

		/* switch to child window */

		String parent = driver.getWindowHandle();
		wLib.switchNewBrowserTab(driver, "Accounts&action");
		OrganizationsSelectPage osp=new OrganizationsSelectPage(driver);

		osp.selectOrg(orgname);
		driver.findElement(By.xpath("//a[text()='" + orgname + "']")).click();

		driver.switchTo().window(parent);
		cncp.getSaveBtn().click();

		/* step 5: verify header msg expected result */
		String headerInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
//		if (headerInfo.contains(lastname)) {
//			System.out.println(lastname + " is created==PASS");
//		} else {
//			System.out.println(lastname + "is not created==FAIL");
//		}
		boolean status2= headerInfo.contains(lastname);
		Assert.assertTrue(status2);

		/* step 6: verify header orgname info expected result */
		String actOrgname = driver.findElement(By.id("mouseArea_Organization Name")).getText();
		System.out.println(actOrgname);
//		if (actOrgname.trim().equals(orgname)) {
//			System.out.println(orgname + " is verified==PASS");
//		} else {
//			System.out.println(orgname + "is not verified==FAIL");
//		}
		boolean status3= actOrgname.contains(orgname);
		Assert.assertTrue(status3);
	
		

	}

}
