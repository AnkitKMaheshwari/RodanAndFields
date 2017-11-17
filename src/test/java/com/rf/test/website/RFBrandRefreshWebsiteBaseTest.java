
package com.rf.test.website;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.rf.core.driver.website.RFWebsiteDriver;
import com.rf.core.utils.DBUtil;
import com.rf.core.utils.HtmlLogger;
import com.rf.core.utils.SoftAssert;
import com.rf.core.website.constants.TestConstantsRFL;
import com.rf.core.website.constants.dbQueries.DBQueries_RFL;
import com.rf.pages.website.storeFrontBrandRefresh.StoreFrontBrandRefreshConsultantPage;
import com.rf.pages.website.storeFrontBrandRefresh.StoreFrontBrandRefreshHomePage;
import com.rf.pages.website.storeFrontBrandRefresh.StoreFrontBrandRefreshPCUserPage;
import com.rf.pages.website.storeFrontBrandRefresh.StoreFrontBrandRefreshPulsePage;
import com.rf.pages.website.storeFrontBrandRefresh.StoreFrontBrandRefreshRCUserPage;
import com.rf.test.base.RFBaseTest;

/**
 * @author ShubhamMathur RFLegacyStoreFrontWebsiteBaseTest is the super class for all
 *         Legacy desktop Test classes initializes the driver is used for execution.
 *
 */
public class RFBrandRefreshWebsiteBaseTest extends RFBaseTest {
	StringBuilder verificationErrors = new StringBuilder();
	protected String password = null;
	protected String countryId = null;
	protected String prefix=null;
	protected String CVV  = TestConstantsRFL.SECURITY_CODE;

	protected StoreFrontBrandRefreshHomePage storeFrontBrandRefreshHomePage;
	protected StoreFrontBrandRefreshConsultantPage storeFrontBrandRefreshConsultantPage;
	protected StoreFrontBrandRefreshPCUserPage storeFrontBrandRefreshPCUserPage;
	protected StoreFrontBrandRefreshRCUserPage storeFrontBrandRefreshRCUserPage;
	protected StoreFrontBrandRefreshPulsePage storeFrontBrandRefreshPulsePage;
	protected String RFL_DB = null;
	protected String RFO_DB = null;
	protected String billingName = null;
	protected String cardNumber = null;
	protected String nameOnCard = null;
	protected String expMonth = null;
	protected String expYear = null;
	protected String addressLine1 =  null;
	protected String postalCode = null;
	protected String phnNumber = null;
	protected String regimen = null;
	protected String kitName = null;
	protected String enrollmentType = null;
	protected String phnNumber1 = null;
	protected String phnNumber2 = null;
	protected String phnNumber3 = null;
	private boolean isActiveBIZPWSFound=false;
	private boolean isActiveCOMPWSFound=false;
	private String bizPWS=null;
	private String comPWS=null;

	protected RFWebsiteDriver driver = new RFWebsiteDriver(propertyFile);
	private static final Logger logger = LogManager
			.getLogger(RFBrandRefreshWebsiteBaseTest.class.getName());

	public RFBrandRefreshWebsiteBaseTest() {
		storeFrontBrandRefreshHomePage = new StoreFrontBrandRefreshHomePage(driver);
		storeFrontBrandRefreshConsultantPage = new StoreFrontBrandRefreshConsultantPage(driver);
		storeFrontBrandRefreshPCUserPage = new StoreFrontBrandRefreshPCUserPage(driver);
		storeFrontBrandRefreshRCUserPage = new StoreFrontBrandRefreshRCUserPage(driver);
		storeFrontBrandRefreshPulsePage = new StoreFrontBrandRefreshPulsePage(driver);
		billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		cardNumber = TestConstantsRFL.CARD_NUMBER;
		nameOnCard = TestConstantsRFL.FIRST_NAME;
		expMonth = TestConstantsRFL.EXP_MONTH;
		expYear = TestConstantsRFL.EXP_YEAR;
		addressLine1 =  TestConstantsRFL.ADDRESS_LINE1;
		postalCode = TestConstantsRFL.POSTAL_CODE;
		phnNumber = TestConstantsRFL.NEW_ADDRESS_PHONE_NUMBER_US;
		regimen = TestConstantsRFL.REGIMEN_NAME_REDEFINE;
		kitName = TestConstantsRFL.KIT_NAME_BIG_BUSINESS;
		enrollmentType = TestConstantsRFL.STANDARD_ENROLLMENT;
		phnNumber1 = TestConstantsRFL.PHONE_NUMBER_1;
		phnNumber2 = TestConstantsRFL.PHONE_NUMBER_2;
		phnNumber3 = TestConstantsRFL.PHONE_NUMBER_3;
	}

	/**
	 * @throws Exception
	 *             setup function loads the driver and environment and baseUrl
	 *             for the tests
	 */
	@BeforeSuite(alwaysRun=true)
	public void setUp() throws Exception {
		logger.info("In the Before suite..");
		driver.loadApplication();		
		logger.info("Application loaded");				
		driver.setDBConnectionString();
		logger.info("out of Before suite..");
		RFL_DB = driver.getDBNameRFL();
		RFO_DB = driver.getDBNameRFO();
	}

	@BeforeMethod(alwaysRun=true)
	public void beforeMethod(){
		logger.info("In Before method..");
		s_assert = new SoftAssert();
		String country = driver.getCountry();
		driver.get(driver.getURL());
			logout();
		if(country.equalsIgnoreCase("ca"))
			countryId = "40";
		else if(country.equalsIgnoreCase("us"))
			countryId = "236";	

		setStoreFrontPassword(driver.getStoreFrontPassword());
		logger.info("Out of Before method..");
	}

	@AfterMethod
	public void tearDownAfterMethod(){

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
		driver.quickWaitForElementPresent(By.xpath("//a[text()='Log-Out' or text()='Log Out']"));
		//driver.pauseExecutionFor(3000);
		if(driver.isElementPresent(By.xpath("//a[text()='Log-Out' or text()='Log Out']"))){
			driver.click(By.xpath("//a[text()='Log-Out' or text()='Log Out']"));
			//driver.findElement(By.xpath("//a[text()='Log-Out' or text()='Log Out']")).click();
			logger.info("Logout done");  
			driver.pauseExecutionFor(1000);
			driver.waitForPageLoad(); 
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

	public void openBIZSite(){			
		RFL_DB = driver.getDBNameRFL();
		RFO_DB = driver.getDBNameRFO();
		if(isActiveBIZPWSFound==false){
			for(int i=1;i<=15;i++){			
				List<Map<String, Object>> randomConsultantPrefix = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_PREFIX,RFL_DB);
				bizPWS = (String) getValueFromQueryResult(randomConsultantPrefix, "URL");
				bizPWS=bizPWS.replaceAll(".com", ".biz");
				driver.get(bizPWS);
				if(driver.getCurrentUrl().contains("Error")||driver.getCurrentUrl().contains("error")||driver.getCurrentUrl().contains("SiteNotActive")){
					continue;
				}
				else{
					isActiveBIZPWSFound=true;
					bizPWS = (String) getValueFromQueryResult(randomConsultantPrefix, "URL");
					bizPWS=bizPWS.replaceAll(".com", ".biz");
					break;
				}
			}	
		}
		else
			driver.get(bizPWS);
		driver.waitForPageLoad();
		logout();
	
	}

	public void openCOMSite(){			
		RFL_DB = driver.getDBNameRFL();
		RFO_DB = driver.getDBNameRFO();
		if(isActiveCOMPWSFound==false){
			for(int i=1;i<=15;i++){			
				List<Map<String, Object>> randomConsultantPrefix = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_PREFIX,RFL_DB);
				comPWS = (String) getValueFromQueryResult(randomConsultantPrefix, "URL");
				comPWS=comPWS.replaceAll(".biz", ".com");
				driver.get(comPWS);
				if(driver.getCurrentUrl().contains("Error")||driver.getCurrentUrl().contains("error")||driver.getCurrentUrl().contains("SiteNotActive")){
					continue;
				}
				else{
					isActiveCOMPWSFound=true;
					comPWS = (String) getValueFromQueryResult(randomConsultantPrefix, "URL");
					comPWS=comPWS.replaceAll(".biz", ".com");
					break;
				}
			}	
		}
		else
			driver.get(comPWS);		
		driver.waitForPageLoad();
		logout();
	}

	public String getRandomUserFromDB(String userType){
		RFL_DB = driver.getDBNameRFL();
		RFO_DB = driver.getDBNameRFO();
		List<Map<String, Object>> randomUserList = null;
		if(userType.equals(TestConstantsRFL.USER_TYPE_CONSULTANT)){
			randomUserList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_EMAILID, RFL_DB);
			return (String) getValueFromQueryResult(randomUserList, "EmailAddress");
		}
		else if(userType.equals(TestConstantsRFL.USER_TYPE_PC)){
			randomUserList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFL, RFL_DB);
			return (String) getValueFromQueryResult(randomUserList, "UserName");
		}
		else if(userType.equals(TestConstantsRFL.USER_TYPE_RC)){
			randomUserList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS, RFL_DB);
			return (String) getValueFromQueryResult(randomUserList, "UserName");
		}
		return null;

	}

	public String getRandomSponsorFromDB(){
		List<Map<String, Object>> randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_EMAILID,RFL_DB);
		return  String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountNumber"));
	}

}
