package com.rf.test.website.nscore4;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.rf.core.utils.CommonUtils;
import com.rf.core.utils.DBUtil;
import com.rf.core.website.constants.TestConstantsRFL;
import com.rf.core.website.constants.dbQueries.DBQueries_RFL;
import com.rf.pages.website.nscore.NSCore4AdminPage;
import com.rf.pages.website.nscore.NSCore4HomePage;
import com.rf.pages.website.nscore.NSCore4LoginPage;
import com.rf.pages.website.nscore.NSCore4MobilePage;
import com.rf.pages.website.nscore.NSCore4OrdersTabPage;
import com.rf.pages.website.nscore.NSCore4ProductsTabPage;
import com.rf.pages.website.nscore.NSCore4SitesTabPage;
import com.rf.test.website.RFNSCoreWebsiteBaseTest;

public class NSCore4OrdersTest extends RFNSCoreWebsiteBaseTest{

	private static final Logger logger = LogManager
			.getLogger(NSCore4OrdersTest.class.getName());

	private NSCore4HomePage nscore4HomePage;
	private NSCore4LoginPage nscore4LoginPage;
	private NSCore4MobilePage nscore4MobilePage;
	private NSCore4OrdersTabPage nscore4OrdersTabPage;
	private NSCore4SitesTabPage nscore4SitesTabPage;
	private NSCore4AdminPage nscore4AdminPage;
	private NSCore4ProductsTabPage nscore4ProductsTabPage;
	String RFL_DB = null;
	List<Map<String, Object>> randomAccountList =  null;
	List<Map<String, Object>> randomConsultantAccountList =  null;
	public NSCore4OrdersTest() {
		nscore4HomePage = new NSCore4HomePage(driver);
		nscore4SitesTabPage = new NSCore4SitesTabPage(driver);
		nscore4ProductsTabPage = new NSCore4ProductsTabPage(driver);
		nscore4OrdersTabPage = new NSCore4OrdersTabPage(driver);
		nscore4AdminPage = new NSCore4AdminPage(driver);
	}

	@BeforeClass
	public void executeCommonQuery(){
		RFL_DB = driver.getDBNameRFL();
		randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS,RFL_DB);
		randomConsultantAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL,RFL_DB);
	}


	//QTest ID TC-1964 Orders>FirstName and LastName
	//QTest ID TC-1965 Orders>CID
	@Test()
	public void testOrdersTab_OrderIdSearch_1964_1965q(){
		String accountNumber = null;
		String firstNameDB = null;
		String lastNameDB =null;
		String completeNameDB =null;
		List<Map<String, Object>> randomConsultantList =  null;
		logger.info("DB is "+RFL_DB); 
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL, RFL_DB);
		firstNameDB = String.valueOf(getValueFromQueryResult(randomConsultantList, "FirstName"));
		lastNameDB = String.valueOf(getValueFromQueryResult(randomConsultantList, "LastName"));
		accountNumber=String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountNumber"));
		completeNameDB = firstNameDB+" "+lastNameDB;
		logger.info("Complete name from DB is: "+completeNameDB);
		nscore4OrdersTabPage = nscore4HomePage.clickOrdersTab();

		//click Browse Orders link
		nscore4OrdersTabPage.clickBrowseOrdersLlink();
		nscore4OrdersTabPage.enterFirstAndLastNameInBrowseOrders(firstNameDB, lastNameDB);
		nscore4OrdersTabPage.clickSearchIcon();
		s_assert.assertTrue(nscore4OrdersTabPage.isSearchResultPresent(completeNameDB),"Expected Search result not present on UI");

		//click Browse Orders link
		nscore4OrdersTabPage.clickBrowseOrdersLlink();
		nscore4OrdersTabPage.enterCIDInBrowseOrders(accountNumber);
		nscore4OrdersTabPage.clickSearchIcon();
		s_assert.assertTrue(nscore4OrdersTabPage.isSearchResultPresent(accountNumber),"Expected Search result does not contain CID on UI");
		s_assert.assertAll();
	}

	//QTest ID TC-1968 Orders>From Date
	@Test()
	public void testOrdersFromDate_1968(){
		String todayDate=null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		todayDate=dateFormat.format(date);
		nscore4OrdersTabPage = nscore4HomePage.clickOrdersTab();
		//click Browse Orders link
		nscore4OrdersTabPage.clickBrowseOrdersLlink();
		s_assert.assertTrue(nscore4OrdersTabPage.isFromDateInBrowseOrdersEditable(todayDate),"From Date on UI is not Editable");
		s_assert.assertAll();
	}

	//QTest ID TC-2476 Cancel Pulse Template
	@Test()
	public void testCancelPulseTemplate_2476(){
		String firstName = null;
		String lastName = null;
		String accountNumber = null;
		String pulseStatus = "Cancelled";
		s_assert.assertTrue(nscore4HomePage.isLogoutLinkPresent(),"Home Page has not appeared after login");
		randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL,RFL_DB);
		//randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_,RFL_DB);
		firstName = (String) getValueFromQueryResult(randomAccountList, "FirstName");	
		lastName = (String) getValueFromQueryResult(randomAccountList, "LastName");	
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber");
		nscore4HomePage.enterFirstName(firstName);
		nscore4HomePage.enterLastName(lastName);
		nscore4HomePage.clickGoBtnOfSearch();
		s_assert.assertTrue(nscore4HomePage.isFirstAndLastNamePresentinSearchResults(accountNumber,firstName, lastName),"First and last name not present in search results");
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		s_assert.assertTrue(nscore4HomePage.isEditPresentForPulseMonthlySubscriptionPresent(),"Edit link for pulse monthly subscription is not present.");
		nscore4HomePage.clickPulseMonthlySubscriptionEdit();
		nscore4HomePage.selectTemplateStatus(pulseStatus);
		nscore4HomePage.clickSaveTemplate();
		s_assert.assertTrue(nscore4HomePage.getCRPOrPulseTemplateStatus().contains(pulseStatus),"Pulse of user has been cancelled successfully.");
		s_assert.assertAll();
	}

	//QTest ID TC-2477 Cancel CRP Template
	@Test()
	public void testCancelCRPTemplate_2477(){
		String firstName = null;
		String lastName = null;
		String accountNumber = null;
		String crpStatus = "Cancelled";
		s_assert.assertTrue(nscore4HomePage.isLogoutLinkPresent(),"Home Page has not appeared after login");
		randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL,RFL_DB);
		//randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_,RFL_DB);
		firstName = (String) getValueFromQueryResult(randomAccountList, "FirstName");	
		lastName = (String) getValueFromQueryResult(randomAccountList, "LastName");	
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber");
		nscore4HomePage.enterFirstName(firstName);
		nscore4HomePage.enterLastName(lastName);
		nscore4HomePage.clickGoBtnOfSearch();
		s_assert.assertTrue(nscore4HomePage.isFirstAndLastNamePresentinSearchResults(accountNumber,firstName, lastName),"First and last name not present in search results");
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		s_assert.assertTrue(nscore4HomePage.isEditPresentForCRPTemplatePresent(),"Edit link for CRP is not present.");
		nscore4HomePage.clickEditCRPTemplate();
		nscore4HomePage.selectTemplateStatus(crpStatus);
		nscore4HomePage.clickSaveTemplate();
		s_assert.assertTrue(nscore4HomePage.getCRPOrPulseTemplateStatus().contains(crpStatus),"CRP of user has been cancelled successfully.");
		s_assert.assertAll();
	}

	//QTest ID TC-2468 Create CRP template
	@Test()
	public void testCreateCRPTemplate_2468(){
		String firstName = null;
		String lastName = null;
		String accountNumber = null;
		String crpStatus = "Submitted Template";
		String SKU = "SBSL001";
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newBillingProfileName = "RFAutoNSCore4"+randomNum;
		String lastNames = "lN";
		String nameOnCard = "rfTestUser";
		String cardNumber =  "4747474747474747";
		String CVV = TestConstantsRFL.SECURITY_CODE;
		String attentionName = "RFAutoNSCore4"+randomNum;

		s_assert.assertTrue(nscore4HomePage.isLogoutLinkPresent(),"Home Page has not appeared after login");
		randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_INACTIVE_CRP_AND_PULSE_TEMPLATE_RFL,RFL_DB);
		firstName = (String) getValueFromQueryResult(randomAccountList, "FirstName");	
		lastName = (String) getValueFromQueryResult(randomAccountList, "LastName");	
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber");
		nscore4HomePage.enterFirstName(firstName);
		nscore4HomePage.enterLastName(lastName);
		nscore4HomePage.clickGoBtnOfSearch();
		s_assert.assertTrue(nscore4HomePage.isFirstAndLastNamePresentinSearchResults(accountNumber,firstName, lastName),"First and last name not present in search results");
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		s_assert.assertTrue(nscore4HomePage.isCreateForCRPTemplatePresent(),"Create link for CRP is not present.");
		nscore4HomePage.clickCreateCRPTemplate();
		nscore4HomePage.selectTemplateStatus(crpStatus);
		nscore4HomePage.enterSKUValues(SKU);
		nscore4HomePage.clickFirstSKUSearchResultOfAutoSuggestion();
		nscore4HomePage.clickAddToOrder();
		nscore4HomePage.addPaymentMethod(firstName, lastNames, nameOnCard, cardNumber, CVV, attentionName);
		nscore4HomePage.clickSaveTemplate();
		nscore4HomePage.clickConsultantNameOnOrderDetailsPage();
		s_assert.assertTrue(nscore4HomePage.isEditPresentForCRPTemplatePresent(),"Create link for CRP is present after enroll in CRP template.");
		s_assert.assertAll();
	}

	//QTest ID TC-2469 Create Pulse template
	@Test()
	public void testCreatePulseTemplate_2469(){
		String firstName = null;
		String lastName = null;
		String accountNumber = null;
		String crpStatus = "Submitted Template";
		String SKU = "PULSE01";
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newBillingProfileName = "RFAutoNSCore4"+randomNum;
		String lastNames = "lN";
		String nameOnCard = "rfTestUser";
		String cardNumber =  "4747474747474747";
		String CVV = TestConstantsRFL.SECURITY_CODE;
		String attentionName = "RFAutoNSCore4"+randomNum;

		s_assert.assertTrue(nscore4HomePage.isLogoutLinkPresent(),"Home Page has not appeared after login");
		randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_INACTIVE_CRP_AND_PULSE_TEMPLATE_RFL,RFL_DB);
		firstName = (String) getValueFromQueryResult(randomAccountList, "FirstName");	
		lastName = (String) getValueFromQueryResult(randomAccountList, "LastName");	
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber");
		nscore4HomePage.enterFirstName(firstName);
		nscore4HomePage.enterLastName(lastName);
		nscore4HomePage.clickGoBtnOfSearch();
		s_assert.assertTrue(nscore4HomePage.isFirstAndLastNamePresentinSearchResults(accountNumber,firstName, lastName),"First and last name not present in search results");
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		s_assert.assertTrue(nscore4HomePage.isCreateForPulseTemplatePresent(),"Create link for CRP is not present.");
		nscore4HomePage.clickCreatePulseTemplate();
		nscore4HomePage.selectTemplateStatus(crpStatus);
		nscore4HomePage.enterSKUValues(SKU);
		nscore4HomePage.clickFirstSKUSearchResultOfAutoSuggestion();
		nscore4HomePage.clickAddToOrder();
		nscore4HomePage.addPaymentMethod(firstName, lastNames, nameOnCard, cardNumber, CVV, attentionName);
		nscore4HomePage.clickSaveTemplate();
		nscore4HomePage.clickConsultantNameOnOrderDetailsPage();
		s_assert.assertTrue(nscore4HomePage.isEditPresentForPulseMonthlySubscriptionPresent(),"Create link for CRP is present after enroll in CRP template.");
		s_assert.assertAll();
	}

}