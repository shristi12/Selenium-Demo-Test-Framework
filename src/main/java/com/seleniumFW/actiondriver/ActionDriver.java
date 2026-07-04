package com.seleniumFW.actiondriver;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.seleniumFW.base.BaseClass;
import com.seleniumFW.utilities.ExtentManager;

public class ActionDriver {

	private WebDriver driver;
	private WebDriverWait wait;

	public ActionDriver(WebDriver driver) {
		this.driver = driver;
		int explicitWait=Integer.parseInt(BaseClass.getProperty().getProperty("explicitwait"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
	}

	private void waitForElementToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
			ExtentManager.logStep("Click an element");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Element is not clickable" + e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver(), "Element is not clickable","");
			
		}
	}

	private void waitForElementToBeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Element is not Visible" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void click(By by) {
		try {
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to click element" + e.getMessage());
		}
	}

	public void enterText(By by, String text) {
		try {
			waitForElementToBeVisible(by);
			driver.findElement(by).clear();
			driver.findElement(by).sendKeys(text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to enter the value" + e.getMessage());
		}
	}

	public String getText(By by) {
		try {
			waitForElementToBeVisible(by);
			return driver.findElement(by).getText();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to get the text value" + e.getMessage());
			return "";
		}
	}

	public boolean compareText(By by, String expectedText) {
		try {
			waitForElementToBeVisible(by);
			String actualText = driver.findElement(by).getText();
			if (expectedText.equalsIgnoreCase(actualText)) {
				System.out.println("Text are matching " + actualText + " equals " + expectedText);
				ExtentManager.logStepWithScreenShot(BaseClass.getDriver(), "compare text", "text verified successfully"+actualText+" equals "+expectedText);
				return true;
			} else {
				System.out.println("Text are not matching " + actualText + " not equals " + expectedText);
				ExtentManager.logFailure(BaseClass.getDriver(), "compare text", "text not matching"+actualText+" not equals "+expectedText);
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to compare text " + e.getMessage());
			return false;
		}

	}

	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);
			boolean isDisplayed = driver.findElement(by).isDisplayed();

			System.out.println("Element is displayed");
			return isDisplayed;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
	}

	// wait for page to load
	public void waitForPageLoad(int timeUnitSec) {
		try {
			wait.withTimeout(Duration.ofSeconds(timeUnitSec)).until(driver -> ((JavascriptExecutor) driver)
					.executeScript("return document.readyState").equals("complete"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("page not loaded" + e.getMessage());
		}
	}

	public void scrollToElement(By by) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(by);
			js.executeScript("arguments[0],scrollInToView(true);", element);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
