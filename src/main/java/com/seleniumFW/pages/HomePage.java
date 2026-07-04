package com.seleniumFW.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.seleniumFW.actiondriver.ActionDriver;
import com.seleniumFW.base.BaseClass;

public class HomePage {
	
	private ActionDriver actionDriver;
	 private By adminTab=By.xpath("//span[text()='Admin']");
	 private By userIdButton=By.className("oxd-userdropdown-name");
	 private By logoutButton=By.xpath("//a[text()='Logout']");
	 private By orangeHRMlogo=By.xpath("//div[@class='oxd-brand-banner']/img");
	 
	 public HomePage(WebDriver driver)
	 {
		 //this.actionDriver=new ActionDriver(driver);
		 this.actionDriver=BaseClass.getActionDriver();
	 }
	 
	 public boolean isAdminTabVisible()
	 {
		 return actionDriver.isDisplayed(adminTab);
	 }
	 
	 public boolean verifyOrangeHRMLogo()
	 {
		 return actionDriver.isDisplayed(orangeHRMlogo);
	 }
	 
	 public void logout()
	 {
		 actionDriver.click(userIdButton);
		 actionDriver.click(logoutButton);
	 }

}
