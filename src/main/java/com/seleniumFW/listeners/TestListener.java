package com.seleniumFW.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.seleniumFW.base.BaseClass;
import com.seleniumFW.utilities.ExtentManager;

public class TestListener implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		ExtentManager.StartTest(testName);
		ExtentManager.logStep("Test Started "+testName);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		ExtentManager.logStepWithScreenShot(BaseClass.getDriver(), "Test Passed Successfully", testName);
	}

	/*@Override
	public void onTestFailure(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		String failureMessage=result.getThrowable().getMessage();
		ExtentManager.logStep(failureMessage);
		ExtentManager.logFailure(BaseClass.getDriver(), "Test Failed", testName);
	}*/
	
	@Override
	public void onTestFailure(ITestResult result) {

	    String testName = result.getMethod().getMethodName();

	    Throwable t = result.getThrowable();

	    if (t != null) {
	        ExtentManager.logStep(t.getMessage());
	    }

	    if (BaseClass.getDriver() != null) {
	        ExtentManager.logFailure(BaseClass.getDriver(), "Test Failed", testName);
	    }
	}

	/*@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}*/

	//This will trigger when a suite starts
	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		//ITestListener.super.onStart(context);
		ExtentManager.getReporter();
	}

	//Trigger when the suite ends
	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		//ITestListener.super.onFinish(context);
		ExtentManager.endTest();
	}
	

}
