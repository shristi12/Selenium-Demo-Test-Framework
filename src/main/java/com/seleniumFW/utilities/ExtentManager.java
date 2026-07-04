package com.seleniumFW.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> threadTest =new ThreadLocal<>();
	
	private static Map<Long, WebDriver> driverMap=new HashMap<>();
	
	//Initialize extent report
    public synchronized static ExtentReports getReporter()
    {
    	if(extent==null)
    	{
    		String reportPath=System.getProperty("user.dir")+"/src/test/resources/ExtentReport/ExtentReport.html";
    		ExtentSparkReporter spark=new ExtentSparkReporter(reportPath);
    		spark.config().setReportName("Automation Test Report");
    		spark.config().setDocumentTitle("OrangeHRM Report");
    		extent=new ExtentReports();
    		extent.setSystemInfo("Operating system", System.getProperty("os.name"));
    	}
		return extent;
    	
    }
    
    public synchronized static ExtentTest StartTest(String testName)
    {
    	ExtentTest extentTest=getReporter().createTest(testName);
    	threadTest.set(extentTest);
    	return extentTest;
    }
    
    public static void endTest()
    {
    	getReporter().flush();
    }
    
    public synchronized static ExtentTest getTest()
    {
    	return threadTest.get();
    }
    
    //Method to get the name of the current test
    public static String getTestName()
    {
    	 ExtentTest currentTest= getTest();
    	 if(currentTest!=null)
    	 {
    		return currentTest.getModel().getName();
    	 }
    	 else
    	 {
    		 return "No test active for this thread";
    	 }
    }
    
    //log a step
    public static void logStep(String logMessage)
    {
    	getTest().info(logMessage);
    }
    
    public static void logStepWithScreenShot(WebDriver driver, String logMessage,String screenShotMessage)
    {
    	getTest().pass(logMessage);
    	//screenshot method
    	attachScreenshot(driver,screenShotMessage);
    }
    
    public static void logFailure(WebDriver driver, String logMessage,String screenShotMessage)
    {
    	getTest().fail(logMessage);
    	//screenshot method
    	attachScreenshot(driver,screenShotMessage);
    }
    
    //take SS
    public synchronized static String takeScreenShot(WebDriver driver, String screenShotName)
    {
    	TakesScreenshot ts=(TakesScreenshot)driver;
    	File src=ts.getScreenshotAs(OutputType.FILE);
    	//Format date and time for file name
    	String timeStamp=new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    	//saving SS to a file
    	String destPath=System.getProperty("user.dir")+"/src/test/resources/screenshots"+screenShotName+"_"+timeStamp+".png";
        File finalPath=new File(destPath);
        try {
			FileUtils.copyFile(src,finalPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //convert Screenshot to base64 for embedding in extent report
        String base64Format=convertToBase64(src);
        return base64Format;
       
    }
    
    //convert SS to base64 format
    public static String convertToBase64(File screenShotFile)
    {
    	String base64Format="";
    	//read the file content into byte array
    	byte[] fileContent;
		try {
			fileContent = FileUtils.readFileToByteArray(screenShotFile);
			base64Format= Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//convert the byte array to Base64 string
    	return base64Format;
    }
    
    //Attach SS to report using base64
    public synchronized static void attachScreenshot(WebDriver driver, String message)
    {
    	try {
			String screenShotBase64=takeScreenShot(driver,getTestName());
			getTest().info(message,com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(screenShotBase64).build());
		} catch (Exception e) {
			getTest().fail("Failed to attach Screenshot");
		}
    }
    
    
    public static void registerDriver(WebDriver driver)
    {
    	driverMap.put(Thread.currentThread().getId(), driver);
    }

}
