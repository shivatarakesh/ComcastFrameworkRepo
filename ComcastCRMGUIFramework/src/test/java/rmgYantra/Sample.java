package rmgYantra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.Test;

import com.mysql.cj.jdbc.Driver;

public class Sample {
		@Test
		public void withinFrameinfinite() throws Throwable {
		WebDriver driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.get("http://106.51.90.215:8084/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));		
		driver.findElement(By.id("username")).sendKeys("rmgyantra");
		driver.findElement(By.id("inputPassword")).sendKeys("rmgy@9999");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		
		Connection connection=DriverManager.getConnection("jdbc:mysql://106.51.90.215:3333/projects", "root@%", "root");
		
		Statement statement =connection.createStatement();
		
		int result=statement.executeUpdate("insert into project(ProjectId,ProjectName,No Of Emp	,Project Manager,Status) values('0987654,'abcd_1234', 55, 'NinzaHRM','completed')");
		
		System.out.println(result);
		connection.close();
		
			}

}
