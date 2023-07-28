package com.das.validation;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.das.core.BaseTest;
import com.das.pom.AccountPage;

public class LoginTestJson extends BaseTest {

	@Test(dataProvider = "getData")
	public void validateCartpage(HashMap<String, String> input) throws Exception {

		loadloginPage();
		AccountPage ap = new AccountPage(getDriver());
		ap.sendKeys(ap.getUsername(), input.get("Email"), "Entering email id");
		ap.sendKeys(ap.getPassword(), loadConfig().getPassword(), "Entering password");
		ap.click(ap.getLoginButton(), "Clicking on login button");

		ap.click(ap.getOrderLink(), "Clicking on order link");
		System.out.println("CURRENT THREAD End: " + Thread.currentThread().getId() + ", " + "DRIVER = " + getDriver());

	}

	@DataProvider(parallel = true)
	public Object[][] getData() throws IOException {
		List<HashMap<String, String>> data = getJsonData(
				System.getProperty("user.dir") + "//src//main//resources//TestData.json");

		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}

}
