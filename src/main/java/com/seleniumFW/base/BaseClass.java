package com.seleniumFW.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.seleniumFW.actiondriver.ActionDriver;
import com.seleniumFW.utilities.ExtentManager;
import com.seleniumFW.utilities.LoggerManager;

public class BaseClass {

	protected static Properties prop;
	// protected static WebDriver driver;
	// private static ActionDriver actionDriver;
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();
	public static final Logger logger = LoggerManager.getLogger(BaseClass.class);

	@BeforeSuite
	public void loadConfig() throws IOException {
		// load config file
		prop = new Properties();
		FileInputStream fs = new FileInputStream(
		        System.getProperty("user.dir") + "/src/main/resources/config.properties");
		prop.load(fs);
		logger.info("Config.properties file loaded");
		//ExtentManager.getReporter();----implemented in TestListener
	}

	@BeforeMethod
	public synchronized void setup()throws IOException {
		System.out.print("Setting up the webdriver");
		launchBrowser();
		configureBrowser();
		staticwait(2);
		/*
		 * // Initialize the actionDriver only once if (actionDriver == null) {
		 * actionDriver = new ActionDriver(driver);
		 * logger.info("ActionDriver instance is created. "+Thread.currentThread().getId
		 * ()); }
		 */
		// Initialize ActionDriver for the current Thread
		actionDriver.set(new ActionDriver(getDriver()));
		logger.info("ActionDriver initlialized for thread: " + Thread.currentThread().getId());
		}

	

	private synchronized void launchBrowser() {
		// Initialize webdriver based on browser defined in config file
		String browser = prop.getProperty("browser");
		if (browser.equalsIgnoreCase("chrome")) {
			//driver = new ChromeDriver();
			driver.set(new ChromeDriver());
			ExtentManager.registerDriver(getDriver());
		} else if (browser.equalsIgnoreCase("edge")) {
			//driver = new EdgeDriver();
			driver.set(new EdgeDriver());
			ExtentManager.registerDriver(getDriver());
		} else {
			throw new IllegalArgumentException("browser not supported" + browser);
		}
	}

	private void configureBrowser() {
		int implicitWait = Integer.parseInt(prop.getProperty("implicitwait"));
		driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

		// maximize the browser
		driver.get().manage().window().maximize();

		// Navigate to url

		try {
			driver.get().get(prop.getProperty("url"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("unable to navigate to url" + e.getMessage());
			// e.printStackTrace();
		}
	}

	// driver getter method
	// public WebDriver getDriver() {
	// return driver;
	// }

	// driver setter method
	/*public void setDriver(WebDriver driver) {
		this.driver = (ThreadLocal<WebDriver>) driver;
	}*/

	public static WebDriver getDriver() {
		 return driver.get();
	}

	public static ActionDriver getActionDriver()
	{
		
			if(actionDriver.get()==null) 
			{
				System.out.println("Action driver is not initialized");
				throw new IllegalStateException("Action Driver is not initialized");
				
				}
			
	
		return actionDriver.get();
	}

	@AfterMethod
	public synchronized void teardown() {
		if (driver.get() != null) {
			getDriver().quit();
		}
		//driver = null;
		//actionDriver = null;
		driver.remove();
		actionDriver.remove();
		//ExtentManager.endTest(); -----implemented in TestListener
	}

	public void staticwait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}

	public static Properties getProperty() {
		return prop;
	}

}
