package com.framework.rahulSir;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class problemByFavorite {
	 static ExtentTest tet;
	 static ExtentReports rp;
	public static void extentReportMethod(String fileLocation, String testCaseName) {
		ExtentSparkReporter exp= new ExtentSparkReporter(fileLocation);
		 rp= new ExtentReports();
		rp.attachReporter(exp);
		 tet= rp.createTest(testCaseName);
		}
	public static void flustIt() {
		rp.flush();
	}
	public static WebDriver launchBrowser() {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8888");
		return driver;
	}
	
	
public static void main(String[] args) {
	
	launchBrowser();
	extentReportMethod("yeah.html", "tryingInGeneric");

	tet.log(Status.INFO, "browser is launched successfully");
	flustIt();
}
}
