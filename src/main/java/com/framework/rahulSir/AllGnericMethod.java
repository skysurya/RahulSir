package com.framework.rahulSir;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AllGnericMethod {
	public static WebDriver driver;
	public static ExtentTest logger;
	public static ExtentReports extr;

	public static void main(String[] args) {
		generatingReport();
		initLogger();
		launchBrowser("chrome", "http://localhost:8888");
		checkingOfElement(loginPageCredentialsForUsername(), logger);
	}

	public static void generatingReport() {
		ExtentSparkReporter reporter = new ExtentSparkReporter("automation.html");
		extr = new ExtentReports();
		extr.attachReporter(reporter);

	}

	public static void initLogger() {
		logger = extr.createTest("testScript001");
	}

	public static void launchBrowser(String browserName, String URl) {

		if (browserName.equalsIgnoreCase("chrome")) {

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.get(URl);
		} else if (browserName.equalsIgnoreCase("firefox")) {

			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		driver.get(URl);

	}

	public static WebElement loginPageCredentialsForUsername() {
		// WebElement we = driver.findElement(By.xpath("//input[@name='user_name']"));
		return getElement("xpath", "//input[@name='user_name']");
	}

	public static boolean checkingOfElement(WebElement we, ExtentTest test1) {
		boolean flag = true;
		if (we.isDisplayed()) {
			test1.log(Status.INFO, "element is not displayed");
			if (we.isEnabled() == flag) {
				System.out.println("username textbox is enabled for sendkeys");

				we.sendKeys("admin");
				;
			} else {
				System.out.println("username textbox is not enabled for sendkeys");
			}
		} else {
			System.out.println("username textbox is not visible");

		}
		return flag;
	}

	public static WebElement getElement(String locatorType, String datavalue) {
		// checkingOfElement(we, logger);

		WebElement we = null;

		try {
			if (locatorType.equalsIgnoreCase("xpath")) {
				we = driver.findElement(By.xpath(datavalue));
			} else if (locatorType.equalsIgnoreCase("cssSelector")) {
				we = driver.findElement(By.cssSelector(datavalue));
			} else if (locatorType.equalsIgnoreCase("partialLinkText")) {
				we = driver.findElement(By.partialLinkText(datavalue));
			} else if (locatorType.equalsIgnoreCase("tagName")) {
				we = driver.findElement(By.tagName(datavalue));
			} else if (locatorType.equalsIgnoreCase("Linktext")) {
				we = driver.findElement(By.linkText(datavalue));
			} else if (locatorType.equalsIgnoreCase("classNAme")) {
				we = driver.findElement(By.className(datavalue));
			} else if (locatorType.equalsIgnoreCase("id")) {
				we = driver.findElement(By.id(datavalue));
			} else if (locatorType.equalsIgnoreCase("name")) {
				we = driver.findElement(By.name(datavalue));
			}
			logger.log(Status.INFO, datavalue + " is found sucessfully ");

		} catch (Exception e) {
			logger.log(Status.INFO, MarkupHelper
					.createLabel(datavalue + " is not found getting exception " + e.getMessage(), ExtentColor.RED));
		}
		return we;
	}

	public void inputValue(String locatorName, String locatorValue, String value) {

		WebElement we = getElement(locatorName, locatorValue);
		try {
			we.sendKeys(value);
			logger.log(Status.INFO, "Value(" + value + ") is Enterd successFully on" + locatorValue);

		} catch (ElementNotInteractableException e) {

			new Actions(driver).sendKeys(value).perform();
			logger.log(Status.INFO, "Value(" + value + ") is Enterd successFully on" + locatorValue);

			// TODO: handle exception
		} catch (Exception e) {

			((JavascriptExecutor) driver).executeScript("arguments[0].value=\"" + value + "\"", we);
			logger.log(Status.INFO, "Value(" + value + ") is Enterd successFully on" + locatorValue);

		} catch (Throwable e) {
			logger.log(Status.INFO,
					MarkupHelper.createLabel("getting Exception on" + locatorValue + e.getMessage(), ExtentColor.RED));
			attachScreenShot();
		}

	}
	
	public void checkElement(WebElement we) {
		boolean status= false;
		if(we.isDisplayed()==true) {
			if(we.isEnabled()==true) {
				status = true;
			}else {
				logger.log(Status.INFO, MarkupHelper.createLabel("element is not enabled", ExtentColor.RED ));
			}
			
		}else {
			logger.log(Status.INFO, MarkupHelper.createLabel("element is not visible", ExtentColor.RED ));
		}
	}

	public static Point locationOfElement() {

		Point a = getElement("","").getLocation();
		return a;

	}

	public static void attachScreenShot() {
		File abc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		logger.addScreenCaptureFromPath(abc.getAbsolutePath());

	}

}
