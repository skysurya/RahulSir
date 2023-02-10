package com.evs.roshanBhsdk;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebUtil {

	public WebDriver driver;
	public ExtentTest logger;
	public ExtentReports reports;

	public void invokeApplication(String browser_Name, String URI) {

		try {
			if (browser_Name.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();		
			} else if (browser_Name.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			} else if (browser_Name.equalsIgnoreCase("edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
			}
			logger.log(Status.INFO, browser_Name + " Browser Invoked Succesfully ");
		} catch (Exception e) {
			logger.log(Status.FAIL,
					browser_Name + " Browser is not able to Invoked  Getting Exception " + e.getMessage());
		}
		try {
			driver.get(URI);
		} catch (Exception e) {
			driver.navigate().to(URI);
		} catch (Throwable e) {
			logger.log(Status.FAIL, URI + " Url is not navigated  Getting Exception " + e.getMessage());

		}
		logger.log(Status.INFO, URI + " naviagted Succesfully ");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	}

	public WebElement getElement(String locatorType, String datavalue) {
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
			} else if (locatorType.equalsIgnoreCase("linkText")) {
				we = driver.findElement(By.linkText(datavalue));
			} else if (locatorType.equalsIgnoreCase("classNAme")) {
				we = driver.findElement(By.className(datavalue));
			} else if (locatorType.equalsIgnoreCase("id")) {
				we = driver.findElement(By.id(datavalue));
			} else if (locatorType.equalsIgnoreCase("name")) {
				we = driver.findElement(By.name(datavalue));
			}
			logger.log(Status.INFO, datavalue + " is found sucessfully ");
			reports.setSystemInfo("Browser", ((RemoteWebDriver) driver).getCapabilities().getBrowserName());
			reports.setSystemInfo("Browser Version", ((RemoteWebDriver) driver).getCapabilities().getBrowserVersion());
			reports.setSystemInfo("PlateForm name",((RemoteWebDriver) driver).getCapabilities().getPlatformName().name());
					

		} catch (Exception e) {
			logger.log(Status.INFO, MarkupHelper
					.createLabel(datavalue + " is not found getting exception " + e.getMessage(), ExtentColor.RED));
		}
		return we;
	}

	public void genrateReport() {
		ExtentSparkReporter spark = new ExtentSparkReporter("Report//automation.html");
		reports = new ExtentReports();
		reports.attachReporter(spark);
		reports.setSystemInfo("OS", System.getProperty("os.name"));
		reports.setSystemInfo("USER", System.getProperty("user.name"));
		reports.setSystemInfo("JDK Verison", System.getProperty("java.version"));
		spark.config().setDocumentTitle("Vtiger - Automation report");
		spark.config().setReportName("Automation Report");
		spark.config().setTheme(Theme.STANDARD );

	}
	public void initLogger(String TestCase_Name) {
		logger = reports.createTest(TestCase_Name);
	}

	public void flushReport() {
		reports.flush();
	}

	public void clickOnElement(WebElement we) {
		try {
			we.click();
			logger.log(Status.INFO, " clicked performed  on " + we);

		} catch (ElementNotInteractableException e) {
			new Actions(driver).click(we).perform();
			logger.log(Status.INFO, " clicked performed  on " + we);

		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", we);
			logger.log(Status.INFO, " clicked performed  on " + we);

		} catch (Throwable e) {
			logger.log(Status.WARNING, "Unable to perform click on " + we + " Getting Exception " + e.getMessage());
		}

	}

	public void sendkeysOnElement(WebElement we, String datavalue) {
		try {
			we.sendKeys(datavalue);

			logger.log(Status.INFO, datavalue + " is successfully entered by webElement");
		} catch (ElementNotInteractableException e) {
			new Actions(driver).sendKeys(datavalue).perform();
			logger.log(Status.INFO, datavalue + " is successfully entered by actions");
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].value=\"" + datavalue + "\"", we);
			logger.log(Status.INFO, "value(" + datavalue + ") is successfully entered by javascriptExecuter");
		} catch (Throwable e) {
			logger.log(Status.FAIL,
					datavalue + " is not abled to performed for sendkeys getting Exception " + e.getMessage());
		}
	}

	public void verifyTitle(String expectedTitle) {
		String actual = driver.getTitle();
		if (actual.equalsIgnoreCase(expectedTitle)) {
			logger.log(Status.PASS,
					MarkupHelper.createLabel("Actual Title(" + actual + " is matched with Expected Title("
							+ expectedTitle + ") Test Case is Passed ", ExtentColor.GREEN));
		} else {
			logger.log(Status.FAIL, MarkupHelper.createLabel("Actual Title(" + actual
					+ " is matched with Expected Title(" + expectedTitle + ") Test Case is Passed ", ExtentColor.RED));

		}
	}
	
	public void elementToBeVisible(WebElement webObj, int timeInSeconds) {
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds));
		wait.until(ExpectedConditions.visibilityOf(webObj));
	}
	
	public void textPresentInElement(WebElement webObj, int timeInSeconds, String expectedTitle) {
		String actual= webObj.getText();
		if(actual.equalsIgnoreCase(expectedTitle)) {
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds));
		wait.until(ExpectedConditions.textToBePresentInElement(webObj, expectedTitle));
		logger.log(Status.INFO, MarkupHelper.createLabel(expectedTitle+" is matched with "+actual+ " actual title", ExtentColor.BLUE));
		}
	}
	 
	public void textNotPresentInElement(WebElement webObj, int timeInSeconds, String expectedTitle) {
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds));
		wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(webObj, expectedTitle)));
	}
	
	
	public void scrollUpAndDownByCordinates(int x, int y) {
		((JavascriptExecutor)driver).executeScript("window.scrollBy("+x+","+y+")", "");
	}
	
	public void scrollUpAndDownByElement(WebElement welement) {
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", welement);
	}
	
	public void getAlldropdownOptions(String locatortype, String locatorvalue) {
		ArrayList<String> l= new ArrayList<String> () ;
		Select sel= new Select(getElement(locatortype, locatorvalue));
		List<WebElement>li= sel.getOptions();
		for(int i=0; i<=li.size()-1;i++) {
		WebElement we= li.get(i);
		String s= we.getText();
		//System.out.println(s);
		l.add(s);
      }
		logger.log(Status.INFO, MarkupHelper.createLabel(l+" options of colour theme dropdown", ExtentColor.RED));
	}
		
	public void getInnerText(String locatortype , String locatorvalue, String expectedText) {
		
		String actualText=getElement(locatortype, locatorvalue).getText();
		if(actualText.equalsIgnoreCase(expectedText)) {
			logger.log(Status.INFO, MarkupHelper.createLabel(actualText+" actualText is matched with expectedText "+expectedText, ExtentColor.ORANGE));
			
		}else {
			logger.log(Status.INFO, MarkupHelper.createLabel(actualText+" actualText is not matched with expectedText "+expectedText, ExtentColor.ORANGE));
		}
	}
	
	public void getfirstSelectedOptionInDropdown(String locatortype, String locatorvalue,String locatortype1, String locatorvalue1) {
		
		Select sel= new Select(getElement(locatortype, locatorvalue));
		String text= sel.getFirstSelectedOption().getText();
		logger.log(Status.INFO, MarkupHelper.createLabel(text+" is selected option in dropdown", ExtentColor.BLUE ));
         getInnerText(locatortype1, locatorvalue1, text);	
	}
	
	public Dimension getDimensionOfelement(String locatortype, String locatorvalue) {
		Dimension dimobj= null;
	try {
		 dimobj= getElement(locatortype, locatorvalue).getSize();
		logger.log(Status.INFO, MarkupHelper.createLabel(dimobj+" is the Dimension of element", ExtentColor.GREEN));			
	}catch (Exception e) {
      
		logger.log(Status.FAIL, MarkupHelper.createLabel("not able to fetch dimension of object", ExtentColor.RED ));
	}
	return dimobj;
	}
	
	public Point getLocationOfelement(String locatortype, String locatorvalue) {
		Point locobj= null;
	try {
		 locobj= getElement(locatortype, locatorvalue).getLocation();
		logger.log(Status.INFO, MarkupHelper.createLabel(locobj+" is the location of element", ExtentColor.GREEN));			
	}catch (Exception e) {
      
		logger.log(Status.FAIL, MarkupHelper.createLabel("not able to fetch location of element", ExtentColor.RED));
	}
	return locobj;
	}
	
	public void validationOfLocationElement(Point locobj ,int a1, int a2 ) {
		int a=locobj.x;
		int b=locobj.y;
		Point expected=new Point(a1, a2);
		System.out.println(a);
		System.out.println(b);
		
		if(locobj.equals(expected)) {
			System.out.println("location matched");
			logger.log(Status.INFO, MarkupHelper.createLabel("matched", ExtentColor.GREEN));
		}else {
			System.out.println("location miss-matched");

		}
		
//		if(a==a1 && b==b1) {
//			logger.log(Status.INFO, MarkupHelper.createLabel("matched", ExtentColor.GREEN));
//		}
	}
	
	public Dimension getCoordinateOfElement(String locatortype, String locatorvalue ) {
		Dimension dimObj= null;
		try {
		 dimObj= getElement(locatortype, locatorvalue).getSize();
		logger.log(Status.INFO, MarkupHelper.createLabel("successfuly got the coordinates ", ExtentColor.GREEN));	
		}catch (Exception e) {
         logger.log(Status.FAIL, "not able to get coordinates");
		}
		return dimObj;
	}
	
	public void validationOfCoordinatesElement(Dimension dimObj, int a1, int b1) {
		int a=dimObj.getHeight();
		int b=dimObj.getWidth();
		System.out.println(a);
		System.out.println(b);
		
		if(a==a1 && b==b1) {
			logger.log(Status.INFO, MarkupHelper.createLabel(a+" and "+b+" coordinate is matched with expected coordinate "+a1+ " and "+b1, ExtentColor.GREEN));
		}else {
			logger.log(Status.FAIL, MarkupHelper.createLabel("coordinates is not matched", ExtentColor.RED));
		}
	}
	
	
	
	
	
	
	
	
	
	
}
