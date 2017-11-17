
package com.rf.test.website;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.rf.core.driver.website.RFWebsiteDriver;
import com.rf.core.utils.HtmlLogger;
import com.rf.core.utils.SoftAssert;
import com.rf.core.website.constants.TestConstants;
import com.rf.test.base.RFBaseTest;

/**
 * @author ShubhamMathur RFDSVCscockpitWebsiteBaseTest is the super class for all
 *         DSV Cscockpit desktop Test classes initializes the driver is used for execution.
 *
 */
public class RFDSVCscockpitWebsiteBaseTest extends RFBaseTest {
	StringBuilder verificationErrors = new StringBuilder();
	protected String password = null;
	protected String countryId = null;
	protected String consultantEmailId = null;
	protected String pcEmailId = null;
	protected String rcEmailId = null;	
	protected String consultantCID = null;
	protected String pcCID = null;
	protected String rcCID = null;	
	protected String consultantCIDForOrderSearch = null;
	protected String pcCIDForOrderSearch = null;
	protected String rcCIDForOrderSearch = null;		
	protected String pwsSuffix = null;
	protected String cardNumber = null;
	protected String expMonth = null;
	protected String expMonthNumber = null;
	protected String expYear= null;
	protected String CVV= null;
	protected String addressLine1= null;
	protected String city= null;
	protected String state = null;	
	protected String postalCode= null;
	protected String phoneNumber= null;
	protected String sponsorWithPWS = null;
	protected String sponsorWithPWS_US = null;
	protected String country = null;	
	protected String countryFullName = null;	
	protected String loginUsername=null;
	protected String loginPassword=null;
	protected String consultantOrderStatus=null;
	protected String consultantOrderNumber=null;
	protected String pcOrderStatus=null;
	protected String pcOrderNumber=null;
	protected String rcOrderStatus=null;
	protected String rcOrderNumber=null;

	private static final By MENU_BTN_LOCATOR = By.xpath("//span[text()='Menu']");
	private static final By LOGOUT_BTN_LOCATOR = By.xpath("//a[contains(text(),'Logout')]");
	private static final By LOGIN_BTN = By.xpath("//td[text()='Login']");
	private static final By USERNAME_TXT_FIELD = By.name("j_username");
	private static final By PASSWORD_TXT_FIELD = By.name("j_password");
	protected RFWebsiteDriver driver = new RFWebsiteDriver(propertyFile);
	private static final Logger logger = LogManager
			.getLogger(RFDSVCscockpitWebsiteBaseTest.class.getName());

	/**
	 * @throws Exception
	 *             setup function loads the driver and environment and baseUrl
	 *             for the tests
	 */
	@BeforeSuite(alwaysRun=true)
	public void setUp() throws Exception {
		driver.loadApplication();  
		driver.get(driver.getURL());
		logger.info("Application loaded"); 
		System.out.println("Application loaded");
		//cscockpitLogin(TestConstants.DSV_CSCOCKPIT_USERNAME, TestConstants.DSV_CSCOCKPIT_PASSWORD); 
		country = driver.getCountry();
		sponsorWithPWS_US = TestConstants.DSV_SPONSOR_WITH_PWS_US;
		if(country.equalsIgnoreCase("ca")){
			loginUsername=TestConstants.DSV_CSCOCKPIT_USERNAME_CA;
			loginPassword=TestConstants.DSV_CSCOCKPIT_PASSWORD_CA;
			countryId = "40";
			consultantEmailId = TestConstants.DSV_CONSULTANT_EMAILID_CA;
			pcEmailId = TestConstants.DSV_PC_EMAILID_CA;
			rcEmailId = TestConstants.DSV_RC_EMAILID_CA;
			password= TestConstants.DSV_CONSULTANT_PASSWORD_CA;
			pwsSuffix = TestConstants.DSV_PWS_SUFFIX_CA;
			cardNumber = TestConstants.DSV_CARD_NUMBER_CA;
			expMonth = TestConstants.DSV_EXPIRY_MONTH_CA;
			expMonthNumber = TestConstants.DSV_EXPIRY_MONTH_NUMBER_CA;
			expYear = TestConstants.DSV_EXPIRY_YEAR_CA;
			CVV = TestConstants.DSV_SECURITY_CODE_CA;
			addressLine1 = TestConstants.DSV_ADDRESS_LINE_1_CA;
			city = TestConstants.DSV_CITY_CA;
			postalCode = TestConstants.DSV_POSTAL_CODE_CA;
			phoneNumber = TestConstants.DSV_PHONE_NUMBER_CA;
			sponsorWithPWS = TestConstants.DSV_SPONSOR_WITH_PWS_CA;
			consultantCID = TestConstants.DSV_CONSULTANT_CID_CA;
			pcCID = TestConstants.DSV_PC_CID_CA;
			rcCID = TestConstants.DSV_RC_CID_CA;
			consultantCIDForOrderSearch = TestConstants.DSV_CONSULTANT_CID_FOR_ORDER_SEARCH_CA;
			pcCIDForOrderSearch = TestConstants.DSV_PC_CID_FOR_ORDER_SEARCH_CA;
			rcCIDForOrderSearch = TestConstants.DSV_RC_CID_FOR_ORDER_SEARCH_CA;
			state = TestConstants.DSV_STATE_CA;
			countryFullName =  TestConstants.DSV_COUNTRY_FULL_NAME_CA;
			consultantOrderStatus=TestConstants.DSV_CONSULTANT_ORDER_STATUS_CA;
			consultantOrderNumber=TestConstants.DSV_CONSULTANT_ORDER_NUMBER_CA;
			pcOrderStatus=TestConstants.DSV_PC_ORDER_STATUS_CA;
			pcOrderNumber=TestConstants.DSV_PC_ORDER_NUMBER_CA;
			rcOrderStatus=TestConstants.DSV_RC_ORDER_STATUS_CA;
			rcOrderNumber=TestConstants.DSV_RC_ORDER_NUMBER_CA;
			cscockpitLogin(loginUsername, loginPassword);


		}
		else if(country.equalsIgnoreCase("au")){
			loginUsername=TestConstants.DSV_CSCOCKPIT_USERNAME_AU;
			loginPassword=TestConstants.DSV_CSCOCKPIT_PASSWORD_AU;
			countryId = "14"; 
			consultantEmailId = TestConstants.DSV_CONSULTANT_EMAILID_AU;
			pcEmailId = TestConstants.DSV_PC_EMAILID_AU;
			rcEmailId = TestConstants.DSV_RC_EMAILID_AU;
			password= TestConstants.DSV_CONSULTANT_PASSWORD_AU;
			pwsSuffix = TestConstants.DSV_PWS_SUFFIX_AU;
			cardNumber = TestConstants.DSV_CARD_NUMBER_AU;
			expMonth = TestConstants.DSV_EXPIRY_MONTH_AU;
			expMonthNumber = TestConstants.DSV_EXPIRY_MONTH_NUMBER_AU;
			expYear = TestConstants.DSV_EXPIRY_YEAR_AU;
			CVV = TestConstants.DSV_SECURITY_CODE_AU;
			addressLine1 = TestConstants.DSV_ADDRESS_LINE_1_AU;
			city = TestConstants.DSV_CITY_AU;
			postalCode = TestConstants.DSV_POSTAL_CODE_AU;
			phoneNumber = TestConstants.DSV_PHONE_NUMBER_AU;
			sponsorWithPWS = TestConstants.DSV_SPONSOR_WITH_PWS_AU;
			consultantCID = TestConstants.DSV_CONSULTANT_CID_AU;
			pcCID = TestConstants.DSV_PC_CID_AU;
			rcCID = TestConstants.DSV_RC_CID_AU;
			consultantCIDForOrderSearch = TestConstants.DSV_CONSULTANT_CID_FOR_ORDER_SEARCH_AU;
			pcCIDForOrderSearch = TestConstants.DSV_PC_CID_FOR_ORDER_SEARCH_AU;
			rcCIDForOrderSearch = TestConstants.DSV_RC_CID_FOR_ORDER_SEARCH_AU;
			state = TestConstants.DSV_STATE_AU;
			countryFullName =  TestConstants.DSV_COUNTRY_FULL_NAME_AU;
			consultantOrderStatus=TestConstants.DSV_CONSULTANT_ORDER_STATUS_AU;
			consultantOrderNumber=TestConstants.DSV_CONSULTANT_ORDER_NUMBER_AU;
			pcOrderStatus=TestConstants.DSV_PC_ORDER_STATUS_AU;
			pcOrderNumber=TestConstants.DSV_PC_ORDER_NUMBER_AU;
			rcOrderStatus=TestConstants.DSV_RC_ORDER_STATUS_AU;
			rcOrderNumber=TestConstants.DSV_RC_ORDER_NUMBER_AU;
			cscockpitLogin(loginUsername, loginPassword);
		}
	}

	@BeforeMethod(alwaysRun=true)
	public void beforeMethod(){
		driver.get(driver.getURL());
		s_assert = new SoftAssert();	
	}

	/**
	 * @throws Exception
	 */
	@AfterSuite(alwaysRun = true)
	public void tearDown() throws Exception {
		cscockpitLogout();
		new HtmlLogger().createHtmlLogFile();		
		driver.quit();
	}

	public void cscockpitLogout(){		
		driver.waitForElementPresent(MENU_BTN_LOCATOR);
		driver.click(MENU_BTN_LOCATOR);
		driver.waitForCSCockpitLoadingImageToDisappear();
		driver.waitForElementPresent(LOGOUT_BTN_LOCATOR);
		driver.click(LOGOUT_BTN_LOCATOR);
		driver.waitForCSCockpitLoadingImageToDisappear();
	}

	public void cscockpitLogin(String userName,String password){
		driver.pauseExecutionFor(2000);
		driver.waitForElementPresent(USERNAME_TXT_FIELD);
		driver.type(USERNAME_TXT_FIELD, userName);
		logger.info("username is "+userName);
		driver.pauseExecutionFor(2000);
		driver.waitForElementPresent(PASSWORD_TXT_FIELD);
		driver.type(PASSWORD_TXT_FIELD, password);
		logger.info("password is "+password);
		driver.click(LOGIN_BTN);
		driver.waitForCSCockpitLoadingImageToDisappear();
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

}
