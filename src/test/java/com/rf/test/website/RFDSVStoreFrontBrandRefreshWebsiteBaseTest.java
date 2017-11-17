
package com.rf.test.website;

import java.net.MalformedURLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;

import com.rf.core.driver.website.RFWebsiteDriver;
import com.rf.core.utils.HtmlLogger;
import com.rf.core.utils.SoftAssert;
import com.rf.core.website.constants.TestConstantsRFL;
import com.rf.test.base.RFBaseTest;

/**
 * @author ShubhamMathur RFDSVStoreFrontWebsiteBaseTest is the super class for all
 *         DSV StoreFront desktop Test classes initializes the driver is used for execution.
 *
 */
public class RFDSVStoreFrontBrandRefreshWebsiteBaseTest extends RFBaseTest {
	StringBuilder verificationErrors = new StringBuilder();
	protected String password = null;
	protected String countryId = null;
	protected String comPWS = null;

	private static final By USERNAME_TXTFIELD = By.id("username");
	private static final By PASSWORD_TXTFIELD = By.id("password");
	private static final By LOGIN_BTN = By.id("loginButton");
	private static final By CONTINUE_BTN_OF_POLICIES_POPUP = By.id("uxContinue");
	private static final By LOGOUT_LINK_LOC = By.xpath("//a[text()='Log-Out']");

	protected RFWebsiteDriver driver = new RFWebsiteDriver(propertyFile);
	private static final Logger logger = LogManager
			.getLogger(RFDSVStoreFrontBrandRefreshWebsiteBaseTest.class.getName());

	/**
	 * @throws MalformedURLException 
	 * @throws Exception
	 *             setup function loads the driver and environment and baseUrl
	 *             for the tests
	 */

	@BeforeGroups(groups = { "consultant"})
	public void beforeGroupConsultant() throws MalformedURLException{
		driver.loadApplication();		
		logger.info("Application loaded");	
		driver.get(driver.getURL());
		countryId = "236";	
		driver.quickWaitForElementPresent(USERNAME_TXTFIELD);
		driver.click(USERNAME_TXTFIELD);
		System.out.println("Click on login link");
		driver.quickWaitForElementPresent(USERNAME_TXTFIELD);
		driver.type(USERNAME_TXTFIELD, TestConstantsRFL.DSV_CONSULTANT_EMAILID);
		logger.info("Username is "+TestConstantsRFL.DSV_CONSULTANT_EMAILID);		
		driver.type(PASSWORD_TXTFIELD, TestConstantsRFL.DSV_CONSULTANT_PASSWORD);
		driver.click(LOGIN_BTN);
		driver.waitForLoadingImageToDisappear();
		driver.waitForPageLoad();
		clickContinueOnNewPoliciesAndProceduresPopup();
		driver.waitForPageLoad();
	}

	public void clickContinueOnNewPoliciesAndProceduresPopup(){
		if(driver.isElementPresent(CONTINUE_BTN_OF_POLICIES_POPUP))
			driver.click(CONTINUE_BTN_OF_POLICIES_POPUP);

	}


	/*public void loginAsConsultant(){
		logger.info("Application loaded");
		driver.waitForElementPresent(USERNAME_TXTFIELD);
		driver.type(USERNAME_TXTFIELD, TestConstants.DSV_CONSULTANT_EMAILID_US);
		logger.info("Entered Username is: "+TestConstants.DSV_CONSULTANT_EMAILID_US);
		driver.type(PASSWORD_TXTFIELD, TestConstants.DSV_CONSULTANT_PASSWORD_US);
		logger.info("Entered Password is: "+TestConstants.DSV_CONSULTANT_PASSWORD_US);
		Actions actions = new Actions(RFWebsiteDriver.driver);
		actions.moveToElement(driver.findElement(LOGIN_BTN)).click().build().perform();
		logger.info("login  enter button clicked");
		driver.waitForPageLoad();
	}*/

	@BeforeGroups(groups = { "pc" })
	public void beforeGroupPC() throws MalformedURLException{
		driver.loadApplication();		
		logger.info("Application loaded");	
		driver.get(driver.getURL());
		countryId = "236";	
		driver.quickWaitForElementPresent(USERNAME_TXTFIELD);
		driver.click(USERNAME_TXTFIELD);
		System.out.println("Click on login link");
		driver.quickWaitForElementPresent(USERNAME_TXTFIELD);
		driver.type(USERNAME_TXTFIELD, TestConstantsRFL.DSV_PC_EMAILID);
		logger.info("Username is "+TestConstantsRFL.DSV_PC_EMAILID);		
		driver.type(PASSWORD_TXTFIELD, TestConstantsRFL.DSV_PC_PASSWORD);
		driver.click(LOGIN_BTN);
		driver.waitForLoadingImageToDisappear();
		driver.waitForPageLoad();
		clickContinueOnNewPoliciesAndProceduresPopup();
		driver.waitForPageLoad();
	}

	/*
	@BeforeGroups(groups = {"rc" })
	public void beforeGroupRC() throws MalformedURLException{
		driver.loadApplication();		
		logger.info("Application loaded");	
		String country = driver.getCountry();
		driver.get(driver.getURL()+"/"+country);
		if(country.equalsIgnoreCase("ca"))
			countryId = "40";
		else if(country.equalsIgnoreCase("us"))
			countryId = "236";	
		driver.quickWaitForElementPresent(LOGIN_LINK);
		driver.click(LOGIN_LINK);
		driver.quickWaitForElementPresent(USERNAME_TXTFIELD);
		driver.type(USERNAME_TXTFIELD, TestConstants.DSV_RC_EMAILID);
		logger.info("Username is "+TestConstants.DSV_RC_EMAILID);		
		driver.type(PASSWORD_TXTFIELD, TestConstants.DSV_RC_PASSWORD);
		driver.click(LOGIN_BTN);
		driver.waitForLoadingImageToDisappear();
		driver.waitForPageLoad();
	}

	@BeforeGroups(groups = {"nonLogin" })
	public void beforeGroupnonLogin() throws MalformedURLException{
		driver.loadApplication();		
		logger.info("Application loaded");	
		String country = driver.getCountry();
		driver.get(driver.getURL()+"/"+country);
		if(country.equalsIgnoreCase("ca"))
			countryId = "40";
		else if(country.equalsIgnoreCase("us"))
			countryId = "236";	
	}*/

	@BeforeGroups(groups = {"rc" })
	public void beforeGroupRC() throws MalformedURLException{
		driver.loadApplication();		
		logger.info("Application loaded");	
		driver.get(driver.getURL());
		countryId = "236";	
		driver.quickWaitForElementPresent(USERNAME_TXTFIELD);
		driver.click(USERNAME_TXTFIELD);
		System.out.println("Click on login link");
		driver.quickWaitForElementPresent(USERNAME_TXTFIELD);
		driver.type(USERNAME_TXTFIELD, TestConstantsRFL.DSV_RC_EMAILID);
		logger.info("Username is "+TestConstantsRFL.DSV_RC_EMAILID);		
		driver.type(PASSWORD_TXTFIELD, TestConstantsRFL.DSV_RC_PASSWORD);
		driver.click(LOGIN_BTN);
		driver.waitForLoadingImageToDisappear();
		driver.waitForPageLoad();
		clickContinueOnNewPoliciesAndProceduresPopup();
		driver.waitForPageLoad();
	}

	@AfterGroups(groups = { "consultant"})
	public void afterGroupCons(){
		logout();
		driver.quit();
	}

	@AfterGroups(groups = {"pc"})
	public void afterGroupPC(){
		logout();
		driver.quit();
	}

	@AfterGroups(groups = {"rc" })
	public void afterGroupRC(){
		logout();
		driver.quit();
	}


	@AfterGroups(groups = {"nonLogin"})
	public void afterGroupNonLogin(){
		logout();
		driver.quit();
	}

	@BeforeMethod(alwaysRun=true)
	public void beforeMethod() throws MalformedURLException{
		if(driver.getCurrentUrl().contains("myrfpulse.com")){
			driver.get(driver.getURL());	
		}
		driver.waitForPageLoad();
		s_assert = new SoftAssert();
	}

	@AfterMethod
	public void tearDownAfterMethod(){
		driver.get(driver.getURL());
	}

	/**
	 * @throws Exception
	 */
	@AfterSuite(alwaysRun = true)
	public void tearDown() throws Exception {
		new HtmlLogger().createHtmlLogFile();		
		driver.quit();
	}

	public void setStoreFrontPassword(String pass){
		password=pass;
	}


	public void logout(){
		try{
			driver.waitForElementPresent(LOGOUT_LINK_LOC);
			driver.click(LOGOUT_LINK_LOC);
			logger.info("Logout");		
			driver.pauseExecutionFor(3000);
		}catch(Exception e){

		}
	}

	// This assertion for the UI Texts
	public void assertTrue(String message, boolean condition) {
		if (!condition) {
			logger.info("[FUNCTIONAL FAILURE - ASSERTION ERROR ----------- "
					+ message + "]");			
			Assert.fail(message);				
		}

	}


	//This assertion for the Database validations
	public boolean assertTrueDB(String message, boolean condition,String dbName) {
		if (!condition) {
			logger.info("[DATABASE ASSERTION FAILURE -  "+dbName+" ----------- " +message + "]");
			if(!dbName.equals(driver.getDBNameRFL())){
				Assert.fail(message);
			}
			else{
				return false;
			}
		}
		return true;
	}

	public void assertTrue(boolean condition, String message) {

		if (!condition) {
			logger.info("[FUNCTIONAL FAILURE - ASSERTION ERROR ----------- "
					+ message + "]");
			Assert.fail(message);
		}
	}

	public void assertEquals(Object obj1, Object obj2, String message) {
		if (!obj1.equals(obj2)) {
			logger.info("[FUNCTIONAL FAILURE - ASSERTION ERROR ----------- "
					+ message + "]");
			Assert.fail(message);
		}
	}

	public boolean assertEqualsDB(Object obj1, Object obj2, String message,String dbName) {
		if (!obj1.equals(obj2)) {
			logger.info("[DATABASE ASSERTION FAILURE -  "+dbName+" ----------- " +message + "]");
			if(!dbName.equals(driver.getDBNameRFL())){
				Assert.fail(message);
			}	
			else{
				return false;
			}
		}
		return true;		
	}

	public boolean assertEqualsDB(String message, int num1,int num2,String dbName) {
		if (!(num1==num2)) {
			logger.info("[RFL DATABASE ASSERTION FAILURE -  "+message + "]");
			if(!dbName.equals(driver.getDBNameRFL())){
				Assert.fail(message);
			}
			else{
				return false;
			}
		}
		return true;
	}

	public void assertEquals(String message, int num1,int num2) {
		if (!(num1==num2)) {
			logger.info("[FUNCTIONAL FAILURE - ASSERTION ERROR ----------- "
					+ message + "]");
			Assert.fail(message);
		}
	}

	public void assertFalse(boolean condition, String message) {

		if (condition) {
			logger.info("[FUNCTIONAL FAILURE - ASSERTION ERROR ----------- "
					+ message + "]");
			Assert.fail(message);
		}
	}

	public void assertFalse(String message, boolean condition) {

		if (condition) {
			logger.info("[FUNCTIONAL FAILURE - ASSERTION ERROR ----------- "
					+ message + "]");
			Assert.fail(message);
		}
	}

	//	public void assertTrue(boolean condition) {
	//
	//		try {
	//			assertTrue(condition);
	//
	//		} catch (Exception e) {
	//			logger.trace(e.getMessage());
	//		}
	//	}

	public void assertEquals(String message, float num1,float num2) {

		if (!(num1==num2)) {
			logger.info("[FUNCTIONAL FAILURE - ASSERTION ERROR ----------- "
					+ message + "]");
			Assert.fail(message);
		}
	}


	public Object getValueFromQueryResult(List<Map<String, Object>> userDataList,String column){
		Object value = null;
		for (Map<String, Object> map : userDataList) {

			//logger.info("query result:" + map.get(column));

			//	logger.info("query result:" + map.get(column));

			value = map.get(column);			
		}
		logger.info("Data returned by query: "+ value);
		return value;
	}

	public List<String> getValuesFromQueryResult(List<Map<String, Object>> userDataList,String column){
		List<String> allReturnedValuesFromQuery = new ArrayList<String>();
		Object value = null;
		for (Map<String, Object> map : userDataList) {
			logger.info("query result:" + map.get(column));
			value = map.get(column);
			allReturnedValuesFromQuery.add(String.valueOf(value));
		}
		return allReturnedValuesFromQuery;
	}


	@BeforeGroups(groups = {"nonLogin" })
	public void beforeGroupnonLogin() throws MalformedURLException{
		driver.loadApplication();		
		logger.info("Application loaded");	
		driver.get(driver.getURL());
	}

}
