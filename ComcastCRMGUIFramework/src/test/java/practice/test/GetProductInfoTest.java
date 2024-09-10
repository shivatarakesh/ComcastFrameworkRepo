package practice.test;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.comcast.crm.generic.fileutility.ExcelUtility;

public class GetProductInfoTest {
	@Test(dataProvider = "getData")
	public void getProductInfoTest(String brand, String product) {
		WebDriver driver=new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.amazon.in/");
		
		//search product
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(brand,Keys.ENTER);
		
		//capture product info
		//String x="//span[text()='"+product+"']/../../../../div[3]/div/div/a/span/span[2]/span[2]";
		//String xpath="//span[contains(text()='"+product+"')]/ancestor::div[@class='a-section a-spacing-small puis-padding-left-small puis-padding-right-small']/descendant::span[@class='a-price-whole']";
		String xp="//div[@class='a-section a-spacing-small a-spacing-top-small']/descendant::span[text()='"+product+"']/../../../../descendant::span[@class='a-price-whole']";
		String price=driver.findElement(By.xpath(xp)).getText();
		System.out.println(price);
		
		driver.quit();
	}
//	@DataProvider
//		public Object[][] getData(){
//			Object[][] objArr=new Object[3][2];
//			objArr[0][0]="iphone";
//			objArr[0][1]="Apple iPhone 15 (128 GB) - Black";
//			
//			objArr[1][0]="iphone";
//			objArr[1][1]="Apple iPhone 14 (128 GB) - Midnight";
//			
//			objArr[2][0]="iphone";
//			objArr[2][1]="Apple iPhone 14 (128 GB) - Blue";
//			
//			return objArr;
//		}
	
	@DataProvider
	public Object[][] getData() throws Throwable{
		ExcelUtility eLib=new ExcelUtility();
		int rowCount=eLib.getRowCount("product");
		
	
		Object[][] objArr=new Object[rowCount][2];
		for(int i =0;i<rowCount;i++) {
		objArr[i][0]=eLib.getDataFromExcel("product", i+1, 0);
		objArr[i][1]=eLib.getDataFromExcel("product", i+1, 1);
		}
		return objArr;
	}

}
