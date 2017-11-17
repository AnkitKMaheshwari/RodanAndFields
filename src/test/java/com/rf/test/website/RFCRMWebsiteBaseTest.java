package com.rf.test.website;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.rf.core.driver.website.RFWebsiteDriver;
import com.rf.core.utils.DBUtil;
import com.rf.core.utils.HtmlLogger;
import com.rf.core.utils.SoftAssert;
import com.rf.core.website.constants.TestConstants;
import com.rf.core.website.constants.dbQueries.DBQueries_RFO;
import com.rf.pages.website.crm.CRMAccountDetailsPage;
import com.rf.pages.website.crm.CRMContactDetailsPage;
import com.rf.pages.website.crm.CRMHomePage;
import com.rf.pages.website.crm.CRMLoginPage;
import com.rf.pages.website.storeFront.StoreFrontHomePage;
import com.rf.test.base.RFBaseTest;

/**
 * @author ShubhamMathur RFWebsiteBaseTest is the super class for all
 *         desktop Test classes initializes the driver is used for execution.
 *
 */
public class RFCRMWebsiteBaseTest extends RFBaseTest {
	StringBuilder verificationErrors = new StringBuilder();
	protected String password = null;
	protected String countryId = null;
	protected String CRMUserName=null;
	protected String CRMPassword=null;
	protected String addressLine = null;
	protected String city = null;
	protected String postal = null;
	protected String province = null;
	protected String phoneNumber = null;
	protected String countryName = null;
	protected String RFO_DB = null;
	protected String consultantEmailID=null;
	protected String accountID=null;
	protected String pcUserName=null;
	protected String pcAccountID=null;
	protected String rcUserName=null;
	protected String rcAccountID=null;
	protected CRMLoginPage crmLoginpage;
	protected CRMHomePage crmHomePage;
	protected CRMAccountDetailsPage crmAccountDetailsPage; 
	protected CRMContactDetailsPage crmContactDetailsPage;
	protected StoreFrontHomePage storeFrontHomePage;
	protected String CRMSalesSupportCoordinatorUserName=null;
	protected String CRMSalesSupportCoordinatorPassword=null;
	protected String CRMAUSalesSupportCoordinatorUserName=null;
	protected String CRMAUSalesSupportCoordinatorPassword=null;
	protected String CRMComplianceUserName=null;
	protected String CRMCompliancePassword=null;
	protected String CRMSalesSupportManagerUserName=null;
	protected String CRMSalesSupportManagerPassword=null;
	protected String CRMRFConnectionUserName=null;
	protected String CRMRFConnectionPassword=null;
	protected String CRMSalesSupportLeadUserName=null;
	protected String CRMSalesSupportLeadPassword=null;
	private static List<Map<String, Object>> randomConsultantList =  null;
	private static List<Map<String, Object>> randomConsultantUsernameList=  null;
	private static List<Map<String, Object>> randomPCUserList=  null;
	private static List<Map<String, Object>> randomPCUsernameList=  null;
	private static List<Map<String, Object>> randomRCUserList=  null;
	private static List<Map<String, Object>> randomRCUsernameList=  null;

	protected RFWebsiteDriver driver = new RFWebsiteDriver(propertyFile);
	private static final Logger logger = LogManager
			.getLogger(RFCRMWebsiteBaseTest.class.getName());

	private final By LOGIN_BOX_LOCATION = By.id("login_form");
	private final By USERNAME_TXTFLD_LOC = By.id("username");
	private final By PASSWORD_TXTFLD_LOC = By.id("password");
	private final By LOGIN_BTN_LOC = By.id("Login");

	/**
	 * @throws Exception
	 *             setup function loads the driver and environment and baseUrl
	 *             for the tests
	 */
	@BeforeSuite(alwaysRun=true)
	public void setUp() throws Exception {
		driver.loadApplication();                               
		logger.info("Application loaded");                                                            
		driver.setDBConnectionString();                
	}

	@BeforeClass
	public void beforeClass(){
		crmHomePage = new CRMHomePage(driver);
		if(driver.getCountry().equalsIgnoreCase("ca")){
			addressLine = TestConstants.ADDRESS_LINE_1_CA;
			city = TestConstants.CITY_CA;
			postal = TestConstants.POSTAL_CODE_CA;
			province = TestConstants.PROVINCE_ALBERTA;
			phoneNumber = TestConstants.PHONE_NUMBER_CA;
			countryName = TestConstants.COUNTRY_DD_VALUE_CA;
		}else{
			addressLine = TestConstants.ADDRESS_LINE_1_AU;
			city = TestConstants.CITY_AU;
			postal = TestConstants.POSTAL_CODE_AU;
			province = TestConstants.STATE_AU;
			phoneNumber = TestConstants.PHONE_NUMBER_AU;
			countryName = TestConstants.COUNTRY_VALUE_AU;
		}
		driver.get(driver.getURL());
		if(driver.getEnvironment().equalsIgnoreCase("stg3")){
			CRMUserName=TestConstants.CRM_LOGIN_USERNAME_STG3;
			CRMPassword = TestConstants.CRM_LOGIN_PASSWORD_STG3;  
		}
		if(driver.getEnvironment().equalsIgnoreCase("stg2")){
			CRMUserName=TestConstants.CRM_LOGIN_USERNAME_STG2;
			CRMPassword = TestConstants.CRM_LOGIN_PASSWORD_STG2;
			CRMSalesSupportCoordinatorUserName = TestConstants.CRM_SALES_SUPPORT_COORDINATOR_USERNAME_STG2;
			CRMSalesSupportCoordinatorPassword = TestConstants.CRM_SALES_SUPPORT_COORDINATOR_PASSWORD_STG2;
			CRMAUSalesSupportCoordinatorUserName = TestConstants.CRM_AU_SALES_SUPPORT_COORDINATOR_USERNAME_STG2;
			CRMAUSalesSupportCoordinatorPassword = TestConstants.CRM_AU_SALES_SUPPORT_COORDINATOR_PASSWORD_STG2;
			CRMComplianceUserName = TestConstants.CRM_COMPLIANCE_USERNAME_STG2;
		    CRMCompliancePassword = TestConstants.CRM_COMPLIANCE_PASSWORD_STG2;
		    CRMSalesSupportManagerUserName = TestConstants.CRM_SALES_SUPPORT_MANAGER_USERNAME_STG2;
		    CRMSalesSupportManagerPassword = TestConstants.CRM_SALES_SUPPORT_MANAGER_PASSWORD_STG2;
		    CRMRFConnectionUserName = TestConstants.CRM_RF_CONNECTION_USERNAME_STG2;
		    CRMRFConnectionPassword = TestConstants.CRM_RF_CONNECTION_PASSWORD_STG2;
		    CRMSalesSupportLeadUserName = TestConstants.CRM_SALES_SUPPORT_LEAD_USERNAME_STG2;
		    CRMSalesSupportLeadPassword = TestConstants.CRM_SALES_SUPPORT_LEAD_PASSWORD_STG2;
		}
		//loginUser(CRMUserName, CRMPassword);
		String country = driver.getCountry();               
		if(country.equalsIgnoreCase("ca"))
			countryId = "40";
		else if(country.equalsIgnoreCase("us"))
			countryId = "236";            
		setStoreFrontPassword(driver.getStoreFrontPassword());
	}
	

	@BeforeMethod
	public void initiateSoftAssertObject(){
		s_assert = new SoftAssert();
	}

	@AfterClass(alwaysRun = true)
	public void logoutAfterClass() throws Exception {
		crmLogout();
	}

	public void loginUser(String username, String password){
		driver.waitForElementPresent(LOGIN_BOX_LOCATION);
		driver.clear(USERNAME_TXTFLD_LOC);
		driver.type(USERNAME_TXTFLD_LOC, username);
		driver.clear(PASSWORD_TXTFLD_LOC);
		driver.type(PASSWORD_TXTFLD_LOC, password);		
		logger.info("login username is: "+username);
		logger.info("login password is: "+password);
		driver.click(LOGIN_BTN_LOC);
		logger.info("login button clicked");		
	}

	//	@AfterMethod
	//	public void tearDownAfterMethod(){
	//		driver.manage().deleteAllCookies();
	//		if(driver.getURL().contains("salesforce")==true){
	//			try{
	//				crmLogout();
	//			}catch(Exception e){
	//
	//			}
	//		}
	//	}

	/**
	 * @throws Exception
	 */
	@AfterSuite(alwaysRun = true)
	public void tearDown() throws Exception {
		//crmLogout();
		new HtmlLogger().createHtmlLogFile();                 
		driver.quit();

	}

	public void setStoreFrontPassword(String pass){
		password=pass;
	}

	public void crmLogout(){
		driver.switchTo().defaultContent();
		driver.quickWaitForElementPresent(By.id("userNavLabel"));
		driver.click(By.id("userNavLabel"));
		driver.waitForElementPresent(By.id("app_logout"));
		driver.click(By.id("app_logout"));
		logger.info("Logout");
		driver.pauseExecutionFor(3000);
	}

	public void crmLogoutFromHome(){
		driver.switchTo().defaultContent();
		driver.quickWaitForElementPresent(By.id("userNavLabel"));
		driver.click(By.id("userNavLabel"));
		driver.waitForElementPresent(By.xpath("//a[@title='Logout']"));
		driver.click(By.xpath("//a[@title='Logout']"));
		logger.info("Logout");
	}

	public void logout(){
		StoreFrontHomePage storeFrontHomePage = new StoreFrontHomePage(driver);
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		driver.click(By.id("account-info-button"));
		logger.info("Your account info has been clicked");
		driver.waitForElementPresent(By.linkText("Log out"));
		driver.click(By.linkText("Log out"));
		logger.info("Logout");                    
		driver.pauseExecutionFor(3000);
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

	//            public void assertTrue(boolean condition) {
	//
	//                            try {
	//                                            assertTrue(condition);
	//
	//                            } catch (Exception e) {
	//                                            logger.trace(e.getMessage());
	//                            }
	//            }

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

			//            logger.info("query result:" + map.get(column));

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

}
