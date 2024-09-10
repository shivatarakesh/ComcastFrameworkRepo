package rmgYantra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SampleDataBase {
	@Test
    public void insertAndVerify() throws Exception {
        String projectID = "TY_PROJ_98044";
        String createdBy = "Asit_k";
        String createdOn = "12/10/22";
        String projectName = "Sparks";
        String status = "on going";
        int teamSize = 420;

        WebDriver driver = null;
        Connection conn = null;

        driver = new ChromeDriver();

        conn = DriverManager.getConnection("jdbc:mysql://106.51.90.215:3333/projects", "root@%", "root");
        Statement stat = conn.createStatement();
         /*
        ResultSet resultSet = stat.executeQuery("DESCRIBE project;");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
        }  */

        String sqlQuery = "INSERT INTO project (project_id, created_by, created_on, project_name, status, team_size) "
                + "VALUES ('" + projectID + "', '" + createdBy + "', '" + createdOn + "', '" + projectName + "', '"
                + status + "', " + teamSize + ");";

        int result = stat.executeUpdate(sqlQuery);

        if (result > 0) {
            System.out.println("Data inserted successfully into the database.");
        } else {
            System.out.println("Failed to insert data into the database.");
            return;
        }

        driver.get("http://106.51.90.215:8084/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.findElement(By.name("username")).sendKeys("rmgyantra");
        driver.findElement(By.name("password")).sendKeys("rmgy@9999");
        driver.findElement(By.xpath("//button[.='Sign in']")).click();

        driver.findElement(By.xpath("//a[.='Projects']")).click();

        boolean projectFound = false;
        int pageNumber = 1;

        while (true) {
            try {
                List<WebElement> pjID = driver.findElements(By.xpath("//table/tbody/tr/a/td[1]"));
                for (WebElement projID : pjID) {
                    String actProjectName = projID.getText();
                    //System.out.println(actProjectName);
                    if (actProjectName.equals(projectID)) {
                        projectFound = true;
                        System.out.println("Project Found: " + actProjectName + " - TS PASS");
                        System.out.println("Project found on page: " + pageNumber);
                        break;
                    }
                }

                if (projectFound) {
                    break;
                }

                WebElement nextPageButtonParent = driver
                        .findElement(By.xpath("//a[@aria-label='Go to next page']/parent::li"));
                if (nextPageButtonParent.getAttribute("class").contains("disabled")) {
                    break;
                }

                WebElement nextPageButton = driver.findElement(By.xpath("//a[@aria-label='Go to next page']"));
                nextPageButton.click();
                pageNumber++;

            } catch (StaleElementReferenceException e) {
                System.out.println("Handled");
            }
        }

        if (!projectFound) {
            System.out.println("Project not found - TS FAIL");
            Assert.fail("Project not found in the UI");
        }

        //driver.quit();
        conn.close();
    }

}
