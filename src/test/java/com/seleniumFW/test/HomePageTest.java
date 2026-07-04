package com.seleniumFW.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.seleniumFW.base.BaseClass;
import com.seleniumFW.pages.HomePage;
import com.seleniumFW.pages.LoginPage;
import com.seleniumFW.utilities.RetryAnalyzer;

public class HomePageTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPages()
	{
		loginPage=new LoginPage(getDriver());
		homePage=new HomePage(getDriver());
	}
	
	@Test(retryAnalyzer=RetryAnalyzer.class)
	public void VerifyOrangeHRMLogo()
	{
	  loginPage.Login("Admin", "admin123");
	  Assert.assertTrue(homePage.verifyOrangeHRMLogo(),"Orange HRM logo not visible");
	}
	
}
