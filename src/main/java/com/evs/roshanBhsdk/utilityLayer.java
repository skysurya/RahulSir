package com.evs.roshanBhsdk;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import io.github.bonigarcia.wdm.WebDriverManager;

public class utilityLayer {

	public static WebDriver driver;
	public static ExtentTest logger;

	public void browserLaunching(String browsername, String url) {
		try {
			if (browsername.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			} else if (browsername.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			} else if (browsername.equalsIgnoreCase("edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
			}
			logger.log(Status.INFO, browsername + " browser is launched successfully");
		} catch (Exception e) {
			logger.log(Status.FAIL, MarkupHelper
					.createLabel(browsername + " browser is not able to launch successfully", ExtentColor.RED));

		}

		try {
			driver.get(url);
		} catch (Exception e) {
			driver.navigate().to(url);
		} catch (Throwable e) {
			logger.log(Status.FAIL, url + " is not able to hit on browser");
		}
		logger.log(Status.INFO, url+" is successfully navigated to the address");
	}
	
	public static WebElement findingElement(String locatortype, String locatorvalue) {
		WebElement we= null;
		if(locatortype.equalsIgnoreCase("xpath")) {
			we.findElement(By.xpath(locatorvalue));
		}else if(locatortype.equalsIgnoreCase("cssSelector")) {
			we.findElement(By.cssSelector(locatorvalue));
		}
		return we;
	}


	public static void windowHandling(String expectedTitle) {
		Set<String>windowHndles= driver.getWindowHandles();
		for(String wind:windowHndles) {
			String actual= driver.switchTo().window(wind).getTitle();
			if(actual.equalsIgnoreCase(expectedTitle)) {			
				logger.log(Status.INFO, MarkupHelper.createLabel(expectedTitle+" is ", null));
				break;
			}			
		}
	}
}
