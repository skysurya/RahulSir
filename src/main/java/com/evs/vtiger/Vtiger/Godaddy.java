package com.evs.vtiger.Vtiger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Godaddy {
	WebDriver driver;
	public void launching() {
		WebDriverManager.chromedriver().setup();
		 driver= new ChromeDriver();
        driver.get("https://www.godaddy.com//");
        driver.manage().window().maximize();
	}
	
	public void getTitle() {
		launching();
		//close();
		String title=driver.getTitle();
		System.out.println(title);
	}
	
	public void close() {
		driver.close();
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Godaddy go= new Godaddy();
		go.launching();
		Thread.sleep(2000);
		go.close();
		Thread.sleep(2000);
		go.getTitle();

	}

}
