package com.seleniumFW.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.seleniumFW.actiondriver.ActionDriver;
import com.seleniumFW.base.BaseClass;

public class LoginPage {

	private ActionDriver actionDriver;
	
	public LoginPage(WebDriver driver)
	{
		//this.actionDriver=new ActionDriver(driver);
		this.actionDriver=BaseClass.getActionDriver();
	}
	
	
	//define webelement using By class
	private By username=By.name("username");
	private By password= By.cssSelector("input[type=password]");
	private By loginButton= By.cssSelector("button[type=submit]");
	private By errorMessage=By.xpath("//div[contains(@class,'content--error')]");
	
	public void Login(String userName, String passWord) {
		
	actionDriver.enterText(username,userName);
	actionDriver.enterText(password,passWord);
	actionDriver.click(loginButton);
	}
	
	public boolean isErrorMessageDisplayed()
	{
		return actionDriver.isDisplayed(errorMessage);
	}
	
	public String getErrorMessageText()
	{
		return actionDriver.getText(errorMessage);
	}
	
	public boolean verifyErrorMessage(String expectedError)
	{
		return actionDriver.compareText(errorMessage, expectedError);
	}
}
