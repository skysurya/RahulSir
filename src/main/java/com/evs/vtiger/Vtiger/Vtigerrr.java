package com.evs.vtiger.Vtiger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Vtigerrr {

	public static void main(String[] args) throws IOException {
        WebDriverManager.chromedriver().setup();
		WebDriver driver= new ChromeDriver();		
		FileInputStream fileobj=	new FileInputStream("C:\\Users\\Suraj Kumar Yadav\\.eclipse\\Vtiger\\config.properties");
		Properties prop=new Properties();
		prop.load(fileobj);
		String a=prop.getProperty("username");
		System.out.println(a);
		String b=prop.getProperty("password");
		System.out.println(b);
		driver.get("http://localhost:8888");
		
		driver.findElement(By.name("user_name")).sendKeys(a);
		driver.findElement(By.name("user_password")).sendKeys(b);
		TakesScreenshot tss = (TakesScreenshot) driver;
	    File fll=	tss.getScreenshotAs(OutputType.FILE);
	    File cll= new File("sae.png");
	    Files.copy(fll, cll) ;
		
		//WebDriverWait weobj= new WebDriverWait(driver, Duration.ofSeconds(7));
		
	}
}
