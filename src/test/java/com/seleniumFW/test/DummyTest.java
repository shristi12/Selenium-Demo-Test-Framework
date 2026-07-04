package com.seleniumFW.test;

import org.testng.annotations.Test;

import com.seleniumFW.base.BaseClass;

public class DummyTest extends BaseClass{

	@Test
	public void dummyTest()
	{
	String title=getDriver().getTitle();
	assert title.equals("OrangeHRM") :"Test Failed- Title not matching";
	
	}
}
