package practice.test;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RetryPractice {
	@Test(retryAnalyzer = com.comcast.crm.listnerutility.RetryListnerImp.class)
	public void activateSim() {
		System.out.println("execute create invoice test");
		Assert.assertEquals("", "Login");
		System.out.println("step-1");
		System.out.println("step-2");

	}

}
