package com.evs.vtiger.Vtiger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * Hello world!
 *
 */
public class ExtentReportss 
{
    public static void main( String[] args )
    {
       
 
    	ExtentSparkReporter spark= new ExtentSparkReporter("g.html");
    	ExtentReports report= new ExtentReports();
    	report.attachReporter(spark);
    	ExtentTest test= report.createTest("a");
    	report.setSystemInfo("username", System.getProperty("os.name"));
    	test.log(Status.PASS, "i love evs");
     	report.flush();
    
    	
    }
}
