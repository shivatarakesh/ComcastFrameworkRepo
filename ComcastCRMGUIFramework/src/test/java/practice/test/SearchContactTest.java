package practice.test;
/**
 * Test class for contact module
 * @author Lenovo
 */
import org.testng.annotations.Test;

import com.comcast.crm.basetest.BaseClass;
import com.comcast.crm.objectrepositary.utility.LoginPage;

public class SearchContactTest extends BaseClass{
	/**
	 * Scenario : login()==>navigate to contact==>createcontact()==verify
	 */
	@Test
	public void searchContactTest() {
		/*step 1: login to app*/
		LoginPage lp=new LoginPage(driver);
		lp.loginToApp("url", "password");
		
	}
	
}
