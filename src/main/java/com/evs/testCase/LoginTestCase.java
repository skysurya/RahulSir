package com.evs.testCase;

import java.time.Duration;

import org.openqa.selenium.WebElement;

import com.evs.roshanBhsdk.WebUtil;

public class LoginTestCase {

	public static void main(String[] args) throws InterruptedException {
      WebUtil utility= new WebUtil();
      utility.genrateReport();
		utility.initLogger("TC001-To Test and Verify User is able to login with valid credintial");
		utility.invokeApplication("chrome", "http://localhost:8888");	
		utility.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		utility.sendkeysOnElement(utility.getElement("name", "user_name"), "admin");
		utility.validationOfLocationElement(utility.getLocationOfelement("name", "user_name"), 640, 230);		
		utility.validationOfCoordinatesElement(utility.getDimensionOfelement("name", "user_name"),22,149);
		utility.getAlldropdownOptions("xpath", "//select[@name='login_theme']");
		utility.getfirstSelectedOptionInDropdown("xpath", "//select[@name='login_theme']","xpath", "//option[text()='softed']");
		utility.sendkeysOnElement(utility.getElement("name", "user_password"), "admin");
		utility.getDimensionOfelement("name", "Login");
		utility.getLocationOfelement("name", "Login");
		utility.clickOnElement(utility.getElement("name", "Login"));		
		utility.verifyTitle("admin - My Home Page - Home - vtiger CRM 5 - Commercial Open Source CRM");
	//	WebUtil.textPresentInElement(WebUtil.getElement("xpath", "//input[@value='Find']"), 15,"Find");
	//	WebUtil.scrollUpAndDownByCordinates(0,0);
		utility.flushReport();

	}

}
