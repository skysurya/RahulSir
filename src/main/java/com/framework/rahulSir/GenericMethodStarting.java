package com.framework.rahulSir;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.common.io.Files;

import io.github.bonigarcia.wdm.WebDriverManager;

public class GenericMethodStarting {

	
	
	
	public static void main(String[] args) throws IOException {
		
		ExtentSparkReporter ext = new ExtentSparkReporter("generic.html") ;
		ExtentReports repo = new ExtentReports();
		repo.attachReporter(ext);
		ExtentTest test= repo.createTest("1layer");
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		
		driver.get("http://localhost:8888");
		verificationOfUserName(driver, test, repo);
	}
	public static void verificationOfUserName(WebDriver driver, ExtentTest test, ExtentReports repo) throws IOException {
		test.log(Status.INFO, "Browser is launched successfully");
		try {
		WebElement we= driver.findElement(By.xpath("//input[@name='user_name']"));
		if(we.isDisplayed()==true) {
			test.log(Status.INFO, "element is displayed");
			if(we.isEnabled()==true) {
				test.log(Status.INFO, "element is enabled to get used");
			}else {
				test.log(Status.INFO,"element is not enabled ");
			}
		}else {
			test.log(Status.INFO, "element is not displayed");
		}
		we.sendKeys("admin");
		test.log(Status.INFO, "admin is entered in username box");
		}catch(Exception e) {
			TakesScreenshot tss = (TakesScreenshot)driver;
			File to=tss.getScreenshotAs(OutputType.FILE);
			File from= new File("gen.png");
			Files.copy(to, from);
		}
		repo.flush();
	  }
	
	
}
