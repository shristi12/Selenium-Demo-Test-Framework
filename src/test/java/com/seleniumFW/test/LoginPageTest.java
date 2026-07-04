package com.seleniumFW.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.seleniumFW.base.BaseClass;
import com.seleniumFW.pages.HomePage;
import com.seleniumFW.pages.LoginPage;
import com.seleniumFW.utilities.ExtentManager;

public class LoginPageTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPages()
	{
		loginPage=new LoginPage(getDriver());
		homePage=new HomePage(getDriver());
	}
	
	@Test(dataProvider="validLoginData", dataProviderClass=DataProvider.class)
	public void verifyValidLogin(String userName, String password)
	{
		//ExtentManager.StartTest("Valid Login Test");  ---->implemented in Test Listener
		//ExtentManager.logStep("Navigating to login page");
		loginPage.Login(userName,password);
		ExtentManager.logStep("Verifying admin tab is visible or not");
		Assert.assertTrue(homePage.isAdminTabVisible(),"Admin tab should be visible after successfull login");
		homePage.logout();
		ExtentManager.logStep("Logged out successfully");
		staticwait(2);
	}
	
	@Test(dataProvider="invalidLoginData", dataProviderClass=DataProvider.class)
	public void verifyInvalidLogin(String userName, String password)
	{
		//ExtentManager.StartTest("Invalid Login Test");
		loginPage.Login(userName, password);
		//String errorMessage=loginPage.getErrorMessageText();
		String errorMessage="Invalid credentials";
		Assert.assertTrue(loginPage.verifyErrorMessage(errorMessage),"Test Failed- invalid error message");
		
	}
}
