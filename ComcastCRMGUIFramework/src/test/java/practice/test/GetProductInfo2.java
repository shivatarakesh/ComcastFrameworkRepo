package practice.test;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.comcast.crm.generic.fileutility.ExcelUtility;

public class GetProductInfo2 {
	@Test
	public void getProductInfoTtest(String brandname,String productname) {
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get("https://www.amazon.in");
		//Search Product
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("iphone",Keys.ENTER);
		WebElement price=driver.findElement(By.xpath("//div[@class='a-section a-spacing-small a-spacing-top-small']/descendant::span[text()='"+productname+"']/../../../../descendant::span[@class='a-price-whole']"));
		String x=price.getText();
		System.out.println(x);
		//Capture product Info
	}
	@DataProvider
	public Object[][] getData() throws Throwable {
		ExcelUtility eu=new ExcelUtility();
		int rowcount=eu.getRowCount("Sheet3");
		System.out.println(rowcount);
		Object[][] arr=new Object[rowcount][2];
		for(int i=0;i<rowcount;i++) {
		arr[i][0]=eu.getDataFromExcel("Sheet3",i+1, 0);
		arr[i][1]=eu.getDataFromExcel("Sheet3",i+1,1);			
	}
		return arr;
	}

}
