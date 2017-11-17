package com.rf.test.website.nscore4;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.rf.core.utils.CommonUtils;
import com.rf.core.utils.DBUtil;
import com.rf.core.utils.ExcelReader;
import com.rf.core.website.constants.TestConstants;
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

public class NSCore4AccountTest extends RFNSCoreWebsiteBaseTest{
	private static final Logger logger = LogManager
			.getLogger(NSCore4AccountTest.class.getName());

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
	List<Map<String, Object>> randomPCAccountList =  null;
	public NSCore4AccountTest() {
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
		randomPCAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_DETAILS_RFL,RFL_DB);
	}

	//QTest ID TC-239 NSC4_AdministratorLogin_LogingLogout
	@Test(priority=1)
	public void testAdministrationLoginLogout_239(){
		s_assert.assertTrue(nscore4HomePage.isLogoutLinkPresent(),"Home Page has not appeared after login");
		nscore4LoginPage = nscore4HomePage.clickLogoutLink();		
		s_assert.assertTrue(nscore4LoginPage.isLoginButtonPresent(),"Login page has not appeared after logout");
		login("admin", "skin123!");
		s_assert.assertAll();
	}

	//QTest ID TC-240 NSC4_AdministratorLogin_InvalidLoging
	@Test(priority=2)
	public void testAdministratorLoginInvalidLogin_240(){
		nscore4LoginPage = nscore4HomePage.clickLogoutLink();	
		login("abcd", "test1234!");
		s_assert.assertTrue(nscore4LoginPage.isLoginCredentailsErrorMsgPresent(), "Login credentials error msg is not displayed");
		login("admin", "skin123!");
		s_assert.assertAll();
	}

	//QTest ID TC-266 NSC4_AccountsTab_AccountLookup
	@Test(priority=3)
	public void testAccountsTabAccountLookup_266(){
		String accountNumber = null;
		String firstName = null;
		String lastName = null;
		logger.info("DB is "+RFL_DB);
		randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACCOUNT_DETAILS,RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber");	
		firstName = (String) getValueFromQueryResult(randomAccountList, "FirstName");	
		lastName = (String) getValueFromQueryResult(randomAccountList, "LastName");	
		logger.info("Account number from DB is "+accountNumber);
		logger.info("First name from DB is "+firstName);
		logger.info("Last name from DB is "+lastName);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch();
		s_assert.assertTrue(nscore4HomePage.isFirstAndLastNamePresentinSearchResults(firstName, lastName), "First and last name is not present in search result,when searched with account number");
		nscore4HomePage.enterFirstAndLastNameInCorrespondingFields(firstName, lastName);
		nscore4HomePage.clickGoBtnOfSearch();
		s_assert.assertTrue(nscore4HomePage.isFirstAndLastNamePresentinSearchResults(firstName, lastName), "First and last name is not present in search result,when searched with first & last name");
		s_assert.assertAll();
	}

	//QTest ID TC-273 NSC4_AccountsTab_OverviewAutoshipsEdit
	@Test(priority=4)
	public void testAccountsTabOverviewAutoshipsEdit_273(){
		String accountNumber = null;  
		String SKU = null;
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newBillingProfileName = "RFAutoNSCore4"+randomNum;
		String lastName = "lN";
		String nameOnCard = "rfTestUser";
		String cardNumber =  "4747474747474747";
		logger.info("DB is "+RFL_DB); 
		do{
			accountNumber = (String) getValueFromQueryResult(randomConsultantAccountList, "AccountNumber"); 
			logger.info("Account number from DB is "+accountNumber);
			nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
			nscore4HomePage.clickGoBtnOfSearch(accountNumber);  
			if(nscore4HomePage.isEditPresentForConsultantReplenishmentPresent() && nscore4HomePage.isEditPresentForPulseMonthlySubscriptionPresent()==true){
				break;
			}else{
				randomConsultantAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL,RFL_DB);
				nscore4HomePage.clickTab("Accounts");
			}
		}while(true);
		nscore4HomePage.clickConsultantReplenishmentEdit();
		SKU= nscore4HomePage.addAndGetProductSKU("10");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
		//nscore4HomePage.addPaymentMethod(newBillingProfileName, lastName, nameOnCard, cardNumber);
		//nscore4HomePage.clickSavePaymentMethodBtn();
		nscore4HomePage.clickSaveAutoshipTemplate();
		s_assert.assertTrue(nscore4HomePage.isAddedProductPresentInOrderDetailPage(SKU), "SKU = "+SKU+" is not present in the Order detail page");
		nscore4HomePage.clickCustomerlabelOnOrderDetailPage();
		nscore4HomePage.clickPulseMonthlySubscriptionEdit();
		String updatedQuantity = nscore4HomePage.updatePulseProductQuantityAndReturnValue();
		logger.info("updated pulse product quantity = "+updatedQuantity);
		//nscore4HomePage.addPaymentMethod(newBillingProfileName, lastName, nameOnCard, cardNumber);
		//nscore4HomePage.clickSavePaymentMethodBtn();
		nscore4HomePage.clickSaveAutoshipTemplate();
		s_assert.assertTrue(nscore4HomePage.getQuantityOfPulseProductFromOrderDetailPage().contains(updatedQuantity), "updated pulse product qunatity is not present in the Order detail page");
		s_assert.assertAll();
	}

	//QTest ID TC-302 NSC4_MobileTab_ HeadlineNews
	@Test(priority=5)
	public void testMobileTabHeadLineNews_302(){
		nscore4MobilePage=nscore4HomePage.clickMobileTab();
		//click headlines news link
		nscore4MobilePage.clickHeadLineNewsLink();
		//verify 'Browse HeadLine News' Page comes up?
		s_assert.assertTrue(nscore4MobilePage.validateBrowseHeadLineNewsPage(), "Browse 'HeadLineNews' Page is not displayed");
		s_assert.assertAll();
	}

	//QTest ID TC-303 NSC4_MobileTab_ R+FInTheNews
	@Test(priority=6)
	public void testMobileTabRFInNews_303(){
		nscore4MobilePage=nscore4HomePage.clickMobileTab();
		//click R+F In the news link
		nscore4MobilePage.clickRFInNewsLink();
		//verify 'Browse RF In News' Page comes up?
		s_assert.assertTrue(nscore4MobilePage.validateBrowseRFInNewsPage(), "Browse 'RF In News' Page is not displayed");
		s_assert.assertAll();
	}

	//NSC4_AccountsTab_OrdersEditCancelOrder
	@Test(priority=7)
	public void testAccountsTab_OrdersEditCancelOrder(){
		String accountNumber = null;
		//List<Map<String, Object>> randomAccountList =  null;
		logger.info("DB is "+RFL_DB);
		while(true){
			//randomConsultantAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL,RFL_DB);
			accountNumber = (String) getValueFromQueryResult(randomConsultantAccountList, "AccountNumber"); 
			logger.info("Account number from DB is "+accountNumber);
			nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
			nscore4HomePage.clickGoBtnOfSearch(accountNumber);
			nscore4HomePage.enterOrderStatus("Pending");
			if(nscore4HomePage.isNoOrderFoundMessagePresent()){
				nscore4HomePage.clickTab("Accounts");
				continue;
			}
			else
				break;
		}
		nscore4OrdersTabPage = nscore4HomePage.clickPendingOrderID();
		nscore4OrdersTabPage.clickCancelOrderBtn();
		s_assert.assertTrue(nscore4OrdersTabPage.validateOrderStatus(),"order is not cancelled");
		s_assert.assertAll();
	}

	//QTest ID TC-287 NSC4_OrdersTab_OrderIdSearch
	@Test(priority=8)
	public void testOrdersTab_OrderIdSearch_287(){
		String accountNumber = null;
		logger.info("DB is "+RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomConsultantAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);
		String orderID = nscore4HomePage.getOrderIDFromOverviewPage();
		nscore4OrdersTabPage = nscore4HomePage.clickOrdersTab();
		nscore4OrdersTabPage.enterOrderIDInInputField(orderID);
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderDetailPagePresent(),"This is not order details page");
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Products"),"Product information not present as expected");
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Shipments"),"Shipments information not present as expected");
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Payment"),"Payment information not present as expected");
		s_assert.assertAll();
	}

	//QTest ID TC-288 NSC4_OrdersTab_OrderAdvancedSearch
	@Test(priority=9, enabled=false)
	public void testOrdersTab_OrderAdvancedSearch(){
		String accountNumber = null;
		List<Map<String, Object>> completeNameList =  null;
		logger.info("DB is "+RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		completeNameList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_SPONSER_DETAILS_FROM_SPONSER_ACCOUNT_NUMBER, accountNumber),RFL_DB);
		String firstNameDB = String.valueOf(getValueFromQueryResult(completeNameList, "FirstName"));
		String lastNameDB = String.valueOf(getValueFromQueryResult(completeNameList, "LastName"));
		String completeNameDB = firstNameDB+" "+lastNameDB;
		logger.info("Complete name from DB is: "+completeNameDB);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);
		nscore4OrdersTabPage = nscore4HomePage.clickOrdersTab();
		nscore4OrdersTabPage.selectDropDownAdvancedSearch("Customer Account");
		nscore4OrdersTabPage.enterValueInAdvancedSearchInputField(accountNumber);
		String completeNameFromUI = nscore4OrdersTabPage.getCompleteNameFromFirstRow();
		s_assert.assertTrue(completeNameDB.equalsIgnoreCase(completeNameFromUI), "Expected complete name is: "+"completeNameDB+ actual on UI is: "+completeNameFromUI);
		s_assert.assertAll();
	}

	//QTest ID TC-250 NSC4_SitesTab_nsCorporate_CorporatePWSContentReviewApprove
	@Test(priority=10)
	public void testCorporatePWSContentReviewApprove_250() {
		int randomNumb = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String firstName= "RFTest";
		String lastName= "QA";
		String linkName = "Product Focus";
		String storyTitle = "This is story title: "+randomNumb;
		String story = "This is new Automation story: "+randomNumber;

		logger.info("DB is "+RFL_DB);
		nscore4HomePage.enterFirstAndLastNameInCorrespondingFields(firstName, lastName);
		nscore4HomePage.clickGoBtnOfSearch(); 
		int rows = nscore4HomePage.getCountOfSearchResultRows();
		int randomNum = CommonUtils.getRandomNum(1,rows);
		nscore4HomePage.clickAndReturnAccountnumber(randomNum);
		nscore4HomePage.clickProxyLink(linkName);
		nscore4HomePage.switchToSecondWindow();
		s_assert.assertTrue(driver.getCurrentUrl().contains("myrfo"+driver.getEnvironment()+".com"), "User is not on dot com pws after clicking product focus proxy link.");
		nscore4HomePage.clickHeaderLinkAfterLogin("edit my pws");
		nscore4HomePage.clickEditMyStoryLink();
		nscore4HomePage.clickIWantToWriteMyOwnStory();
		nscore4HomePage.typeSomeStoryAndclickSaveStory(storyTitle,story);
		s_assert.assertTrue(nscore4HomePage.verifyWaitingForApprovalLinkForNewStory(storyTitle),"Waiting for approval link is not present for newly created story");
		nscore4HomePage.switchToPreviousTab();
		nscore4HomePage.clickTab("Sites");
		nscore4SitesTabPage.clickPWSContentReviewLinkUnderNSCorporate();
		s_assert.assertTrue(nscore4SitesTabPage.verifyNewStoryWaitingForApprovedLink(story),"Waiting for approved link is not present for newly created story");
		nscore4SitesTabPage.clickApproveLinkForNewStory(story);
		s_assert.assertTrue(nscore4SitesTabPage.verifyApproveRequestDisappearFromUIOnceStoryApproved(story),"Approve request still appears in UI once Approved");
		nscore4SitesTabPage.clickTab("Accounts");
		nscore4HomePage.enterFirstAndLastNameInCorrespondingFields(firstName, lastName);
		nscore4HomePage.clickGoBtnOfSearch(); 
		nscore4HomePage.clickAndReturnAccountnumber(randomNum);
		nscore4HomePage.clickProxyLink(linkName);
		nscore4HomePage.switchToSecondWindow();
		nscore4HomePage.clickHeaderLinkAfterLogin("edit my pws");
		s_assert.assertTrue(nscore4HomePage.verifyNewlyCreatedStoryIsUpdated(story),"New Story displayed on legacy is not the newly created");
		nscore4HomePage.switchToPreviousTab();
		s_assert.assertAll();
	}

	//QTest ID TC-251 NSC4_SitesTab_nsCorporate_CorporatePWSContentReviewDenied
	@Test(priority=11)
	public void testCorporatePWSContentReviewDenied_251(){
		int randomNumb = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumbers = CommonUtils.getRandomNum(10000, 1000000);
		String name= "RFTest QA";
		String linkName = "Product Focus";
		String storyTitle = "This is story title: "+randomNumb;
		String story = "This is new Automation story: "+randomNumber;
		String denyReason = "This is automation deny: "+randomNumbers;

		logger.info("DB is "+RFL_DB);
		nscore4HomePage.enterAccountNumberInAccountSearchField(name);
		nscore4HomePage.clickGoBtnOfSearch(); 
		int rows = nscore4HomePage.getCountOfSearchResultRows();
		int randomNum = CommonUtils.getRandomNum(1,rows);
		nscore4HomePage.clickAndReturnAccountnumber(randomNum);
		nscore4HomePage.clickProxyLink(linkName);
		nscore4HomePage.switchToSecondWindow();
		s_assert.assertTrue(driver.getCurrentUrl().contains("myrfo"+driver.getEnvironment()+".com"), "User is not on dot com pws after clicking product focus proxy link.");
		nscore4HomePage.clickHeaderLinkAfterLogin("edit my pws");
		nscore4HomePage.clickEditMyStoryLink();
		nscore4HomePage.clickIWantToWriteMyOwnStory();
		nscore4HomePage.typeSomeStoryAndclickSaveStory(storyTitle,story);
		s_assert.assertTrue(nscore4HomePage.verifyWaitingForApprovalLinkForNewStory(storyTitle),"Waiting for approval link is not present for newly created story");
		nscore4HomePage.switchToPreviousTab();
		nscore4HomePage.clickTab("Sites");
		nscore4SitesTabPage.clickPWSContentReviewLinkUnderNSCorporate();
		s_assert.assertTrue(nscore4SitesTabPage.verifyNewStoryWaitingForApprovedLink(story),"Waiting for approved link is not present for newly created story");
		nscore4SitesTabPage.clickDenyLinkForNewStory(story);
		nscore4SitesTabPage.enterDenyReasonAndClickSubmit(denyReason);
		s_assert.assertTrue(nscore4SitesTabPage.verifyApproveRequestDisappearFromUIOnceStoryApproved(story),"Approve request still appears in UI once Approved");
		nscore4SitesTabPage.clickTab("Accounts");
		nscore4HomePage.enterAccountNumberInAccountSearchField(name);
		nscore4HomePage.clickGoBtnOfSearch(); 
		nscore4HomePage.clickAndReturnAccountnumber(randomNum);
		nscore4HomePage.clickProxyLink(linkName);
		nscore4HomePage.switchToSecondWindow();
		nscore4HomePage.clickHeaderLinkAfterLogin("edit my pws");
		nscore4HomePage.clickEditMyStoryLink();
		s_assert.assertTrue(nscore4HomePage.getStoryDeniedText(storyTitle).contains("not approved"),"Story denied txt expected =(not approved)"+"while on UI"+nscore4HomePage.getStoryDeniedText(storyTitle));
		nscore4HomePage.switchToPreviousTab();
		s_assert.assertAll();
	}

	//QTest ID TC-513 NSC4_OrdersTab_NewOrder
	@Test(priority=12)
	public void testOrdersTab_NewOrder_513(){
		String accountNumber = null;
		List<Map<String, Object>> completeNameList =  null;
		String SKU = null;
		logger.info("DB is "+RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);
		nscore4OrdersTabPage = nscore4HomePage.clickOrdersTab();
		nscore4OrdersTabPage.clickStartANewOrderLink();
		completeNameList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_SPONSER_DETAILS_FROM_SPONSER_ACCOUNT_NUMBER, accountNumber),RFL_DB);
		String firstNameDB = String.valueOf(getValueFromQueryResult(completeNameList, "FirstName"));
		String lastNameDB = String.valueOf(getValueFromQueryResult(completeNameList, "LastName"));
		String completeNameDB = firstNameDB+" "+lastNameDB;
		logger.info("Complete name from DB is: "+completeNameDB);
		nscore4OrdersTabPage.enterAccountNameAndClickStartOrder(completeNameDB, accountNumber);
		SKU = nscore4HomePage.addAndGetProductSKU("1");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
		nscore4OrdersTabPage.clickPaymentApplyLink();
		nscore4OrdersTabPage.clickSubmitOrderBtn();
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Products"),"Product information not present as expected");
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Shipments"),"Shipments information not present as expected");
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Payment"),"Payment information not present as expected");
		s_assert.assertTrue(nscore4OrdersTabPage.validateOrderStatusAfterSubmitOrder(),"Order is not submitted after submit order");
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderDetailPagePresent(),"This is not order details page");
		s_assert.assertAll();
	}

	//QTest ID TC-290 NSC4_OrdersTab_BrowseOrders
	@Test(priority=13)
	public void testOrdersTabBrowseOrders_290(){
		String accountNumber = null;
		logger.info("DB is "+RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);
		nscore4OrdersTabPage = nscore4HomePage.clickOrdersTab();
		//click Browse Orders link
		nscore4OrdersTabPage.clickBrowseOrdersLlink();
		//Select Status as pending in filter
		nscore4OrdersTabPage.selectStatusDD("Pending");
		//Select Type as PC in filter
		nscore4OrdersTabPage.selectTypeDD("PC");
		//click 'GO' Button
		nscore4OrdersTabPage.clickGoSearchBtn();
		//nscore4OrdersTabPage.clickGoSearchBtn();
		//Verify all the results from the table satisfy the previous filter(s)-1.Order Status
		s_assert.assertTrue(nscore4OrdersTabPage.validateOrderStatusRow(),"Atleast 1 of the status is not 'Pending'");
		s_assert.assertTrue(nscore4OrdersTabPage.validateOrderTypeRow(),"Atleast 1 of the type is not 'PC'");
		s_assert.assertAll();
	}

	//QTest ID TC-272 NSC4_AccountsTab_OverviewPostNewNote
	@Test(priority=14)
	public void testNSC4AccountsTabOverviewPostNewNote_272(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String accountNumber = null;
		String categoryOfNotePopup = "1a";
		String categoryOfChildNote = "1k";
		String typeOfNotePopup="A";
		String typeOfChildNote="E";
		String noteTxt = "AutomationNote"+randomNum;
		String noteTxtOfChildNote = "AutomationChildNote"+randomNumber;

		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber); 
		nscore4HomePage.clickPostNewNodeLinkInOverviewTab();
		nscore4HomePage.selectAndEnterAddANoteDetailsInPopup(categoryOfNotePopup,typeOfNotePopup,noteTxt);
		nscore4HomePage.clickSaveBtnOnAddANotePopup();
		s_assert.assertTrue(nscore4HomePage.isNewlyCreatedNotePresent(noteTxt), "Newly created note"+noteTxt+" is not present ON UI");
		nscore4HomePage.clickPostFollowUpLinkForParentNote(noteTxt);
		nscore4HomePage.selectAndEnterAddANoteDetailsInPopup(categoryOfChildNote,typeOfChildNote,noteTxtOfChildNote);
		nscore4HomePage.clickSaveBtnOnAddANotePopup();
		s_assert.assertTrue(nscore4HomePage.isNewlyCreatedChildNotePresent(noteTxt,noteTxtOfChildNote), "Newly created Child note"+noteTxtOfChildNote+" is not present ON UI under parent note"+noteTxt);
		s_assert.assertTrue(nscore4HomePage.isPostFollowUpLinkPresentForChildNote(noteTxt,noteTxtOfChildNote), "Newly created Child note"+noteTxtOfChildNote+" post follow up link is not present ON UI under parent note"+noteTxt);
		nscore4HomePage.clickCollapseLinkNearToParentNote(noteTxt);
		s_assert.assertFalse(nscore4HomePage.isChildNoteDetailsAppearsOnUI(noteTxt), "Newly created Child note"+noteTxtOfChildNote+" details are present ON UI under parent note"+noteTxt);
		nscore4HomePage.clickExpandLinkNearToParentNote(noteTxt);
		s_assert.assertTrue(nscore4HomePage.isChildNoteDetailsAppearsOnUI(noteTxt), "Newly created Child note"+noteTxtOfChildNote+" details are not present ON UI under parent note"+noteTxt);
		s_assert.assertAll();
	}

	//QTest ID TC-512 NSC4_AccountsTab_OverviewPlaceNew Order
	@Test(priority=15)
	public void testAccountsTab_OverviewPlaceNewOrder_512(){
		String accountNumber = null;
		List<Map<String, Object>> randomSKUList =  null;
		String SKU = null;
		nscore4OrdersTabPage =new NSCore4OrdersTabPage(driver);
		logger.info("DB is "+RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);
		//click 'Place-New-Order' link
		nscore4HomePage.clickPlaceNewOrderLink();
		SKU= nscore4HomePage.addAndGetProductSKU("10");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
		nscore4OrdersTabPage.clickPaymentApplyLink();
		nscore4OrdersTabPage.clickSubmitOrderBtn();
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Products"),"Product information not present as expected");
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Shipments"),"Shipments information not present as expected");
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Payment"),"Payment information not present as expected");
		s_assert.assertTrue(nscore4OrdersTabPage.validateOrderStatusAfterSubmitOrder(),"Order is not submitted after submit order");
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderDetailPagePresent(),"This is not order details page");
		s_assert.assertAll();
	}

	//QTest ID TC-270 NSC4_AccountsTab_OverviewStatusChange
	@Test(priority=16)
	public void testAccountsTab_OverviewStatusChange_270(){
		String accountNumber = null;
		logger.info("DB is "+RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);
		//get the status before change
		String beforeStatus=nscore4HomePage.getStatus();
		//click the status link and change the status-
		nscore4HomePage.clickStatusLink();
		//change the status and save 
		nscore4HomePage.changeStatusDD(1);
		nscore4HomePage.clickSaveStatusBtn();
		nscore4HomePage.refreshPage();
		//get the status after change
		String afterStatus=nscore4HomePage.getStatus();
		//verify the status got changed successfully?
		s_assert.assertNotEquals(beforeStatus, afterStatus);
		//Revert the Changes-
		nscore4HomePage.clickStatusLink();
		nscore4HomePage.changeStatusDD(0);
		nscore4HomePage.clickSaveStatusBtn();
		nscore4HomePage.refreshPage();
		String finalStatus=nscore4HomePage.getStatus();
		s_assert.assertEquals(beforeStatus, finalStatus);
		s_assert.assertAll();
	}

	//QTest ID TC-274 NSC4_AccountsTab_OverviewAutoshipsViewOrders
	@Test
	public void testNSC4AccountTabOverviewAutoshipsViewOrders_274(){
		String accountNumber = null;
		String accounts = "Accounts";
		logger.info("DB is "+RFL_DB);
		String year = nscore4HomePage.getCurrentDateAndMonthAndYearAndMonthShortNameFromPstDate(nscore4HomePage.getPSTDate())[2];
		String currentYear = "%"+year+"%";
		randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_CONSULTANT_ACCOUNT_NUMBER_HAVING_AUTOSHIP_ORDER_WITH_CURRENT_YEAR_RFL, currentYear),RFL_DB);
		accountNumber =(String) getValueFromQueryResult(randomAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);
		nscore4HomePage.clickViewOrderLinkUnderConsultantReplenishment();
		nscore4HomePage.clickCalenderStartDateForFilter();
		nscore4HomePage.selectMonthOnCalenderForNewEvent("Jan");
		nscore4HomePage.selectYearOnCalenderForNewEvent("2017");
		nscore4HomePage.clickSpecficDateOfCalendar("1");
		int totalSearchResults = nscore4HomePage.getCountOfSearchResults();
		String[] allCompleteDate = nscore4HomePage.getAllCompleteDate(totalSearchResults);
		s_assert.assertTrue(nscore4HomePage.isAllCompleteDateContainCurrentYear(allCompleteDate), "All complete date on UI are not in the range of filter for consultant autoship");;
		nscore4HomePage.navigateToBackPage();
		nscore4HomePage.clickTab(accounts);
		randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_CONSULTANT_ACCOUNT_NUMBER_HAVING_PULSE_AUTOSHIP_ORDER_WITH_CURRENT_YEAR_RFL, currentYear, currentYear),RFL_DB);
		accountNumber =(String) getValueFromQueryResult(randomAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);
		nscore4HomePage.clickViewOrderLinkUnderPulseMonthlySubscription();
		nscore4HomePage.clickCalenderStartDateForFilter();
		nscore4HomePage.selectMonthOnCalenderForNewEvent("Jan");
		nscore4HomePage.selectYearOnCalenderForNewEvent("2017");
		nscore4HomePage.clickSpecficDateOfCalendar("1");
		totalSearchResults = nscore4HomePage.getCountOfSearchResults();
		int randomSearchResult = CommonUtils.getRandomNum(1, totalSearchResults);
		allCompleteDate = nscore4HomePage.getAllCompleteDate(totalSearchResults);
		s_assert.assertTrue(nscore4HomePage.isAllCompleteDateContainCurrentYear(allCompleteDate), "All complete date on UI are not in the range of filter for consultant pulse autoship");;
		nscore4HomePage.clickAndReturnRandomOrderNumber(randomSearchResult);
		s_assert.assertTrue(nscore4HomePage.getOrderNumberFromOrderDetails().contains(accountNumber), "Expected OrderNumber on order details page is: "+accountNumber+" Actual on UI is: "+nscore4HomePage.getOrderNumberFromOrderDetails());
		s_assert.assertAll();
	}

	//QTest ID TC-242 NSC4_SitesTab_nsCorporate_CorporateAddEditDeleteNews
	@Test(priority=18)
	public void testNSC4SitesTabNSCorporateAddEditDeleteNews_242(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int randomNum2 = CommonUtils.getRandomNum(10000, 1000000);
		String sites = "Sites";
		String title = "For Automation"+randomNum;
		String newTitle = "For Automation"+randomNum2;
		nscore4HomePage.clickTab(sites);
		nscore4SitesTabPage.clickCorporateLink();
		nscore4SitesTabPage.clickAddNewsLink();
		nscore4SitesTabPage.enterTitleForAddNews(title);
		nscore4SitesTabPage.checkIsActiveChkBoxForNewsTitle();
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("News saved successfully"), "Expected saved message is: News saved successfully but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		int noOfOptionsInSizePageDD = nscore4SitesTabPage.getSizeOfOptinsFromPageSizeDD();
		nscore4SitesTabPage.clickAndSelectOptionInPageSizeDD(noOfOptionsInSizePageDD);
		s_assert.assertTrue(nscore4SitesTabPage.isTitleNamePresentInAnnouncementsList(title), "Title name is not present in announcement list");
		nscore4SitesTabPage.clickTitleNamePresentInAnnouncementsList(title);
		nscore4SitesTabPage.enterTitleForAddNews(newTitle);
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("News saved successfully"), "Expected saved message is: News saved successfully but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		noOfOptionsInSizePageDD = nscore4SitesTabPage.getSizeOfOptinsFromPageSizeDD();
		nscore4SitesTabPage.clickAndSelectOptionInPageSizeDD(noOfOptionsInSizePageDD);
		s_assert.assertTrue(nscore4SitesTabPage.isTitleNamePresentInAnnouncementsList(newTitle), "Title name is not present in announcement list");
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(newTitle);
		nscore4SitesTabPage.clickDeactivateSelectedLink();
		s_assert.assertTrue(nscore4SitesTabPage.getTitleStatus(newTitle).contains("Inactive"), "Expected title status is: Inactive but actual on UI: "+nscore4SitesTabPage.getTitleStatus(newTitle));
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(newTitle);
		nscore4SitesTabPage.clickActivateSelectedLink();
		s_assert.assertTrue(nscore4SitesTabPage.getTitleStatus(newTitle).contains("Active"), "Expected title status is: Active but actual on UI is: "+nscore4SitesTabPage.getTitleStatus(newTitle));
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(newTitle);
		nscore4SitesTabPage.clickDeleteSelectedLink();
		s_assert.assertFalse(nscore4SitesTabPage.isTitleNamePresentInAnnouncementsList(title), "Title name is present in announcement list after delete");
		s_assert.assertAll();
	}

	//QTest ID TC-244 NSC4_SitesTab_nsCorporate_CorporateSiteDetailsEditSite
	@Test(priority=19)
	public void testNSC4SitesTabNSCorporateSiteDetailEditSite_244(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String sites = "Sites";
		String siteDetails = "Site Details";
		String newSiteName = "Auto"+randomNum;
		nscore4HomePage.clickTab(sites);
		nscore4SitesTabPage.clickSubLinkOfCorporate(siteDetails);
		nscore4SitesTabPage.enterSiteNameForSiteDetails(newSiteName);
		nscore4SitesTabPage.checkActiveChkBoxForSiteDetails();
		nscore4SitesTabPage.clickSaveBtnOnSiteDetails();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxtForSite().contains("Site saved successfully"), "Expected saved message is: Site saved successfully but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxtForSite());
		nscore4SitesTabPage.clickTab(sites);
		nscore4SitesTabPage.clickSubLinkOfCorporate(siteDetails);
		nscore4SitesTabPage.enterSiteNameForSiteDetails("Corporate");
		nscore4SitesTabPage.uncheckActiveChkBoxForSiteDetails();
		nscore4SitesTabPage.clickSaveBtnOnSiteDetails();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxtForSite().contains("Site saved successfully"), "Expected saved message is: Site saved successfully but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxtForSite());
		s_assert.assertAll();
	}

	//QTest ID TC-275 NSC4_AccountsTab_FullAccountRecordUpdate
	@Test(priority=20)
	public void testNSC4AccountTabFullAccountRecordUpdate_275(){
		String accountNumber = null;
		logger.info("DB is "+RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);
		nscore4HomePage.clickFullAccountRecordLink();
		//get all values before update
		String userName = nscore4HomePage.getUserName();
		String firstName = nscore4HomePage.getFirstName();
		String homePhone = nscore4HomePage.getLastFourDgitOfHomePhoneNumber();
		String emailID = nscore4HomePage.getEmailAddress();
		String taxExemptValue = nscore4HomePage.getTaxExemptValue();
		String nameOnSSNCard = nscore4HomePage.getNameOnSSNCard();
		String dob = nscore4HomePage.getDOBValue();
		String gender = nscore4HomePage.getSelectedGender();
		String attentionName = nscore4HomePage.getAttentionName();
		String zipCode = nscore4HomePage.getZIPCode();
		String phoneNumber = nscore4HomePage.getLastFourDgitOfPhoneNumber();
		String dobDay = nscore4HomePage.getDayFromDate(dob);
		//update the values
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int randomNum1 = CommonUtils.getRandomNum(10000, 1000000);
		int randomNum2 = CommonUtils.getRandomNum(10000, 1000000);
		int randomNum3 = CommonUtils.getRandomNum(10000, 1000000);
		int randomNum4 = CommonUtils.getRandomNum(10000, 1000000);
		String userNameForUpdate = "Automation"+randomNum+"@gmail.com";
		String nameForUpdate = "Autoname"+randomNum1;
		String homePhoneForUpdate =  String.valueOf(CommonUtils.getRandomNum(1000, 9999));
		String emailIDForUpdate = "auto"+randomNum2+"@gmail.com";
		String taxExemptValueForUpdate =nscore4HomePage.getTaxExemptValueForUpdate(taxExemptValue);
		String nameOnSSNCardForUpdate = "Auto"+randomNum3;
		String dobDayForUpdate = nscore4HomePage.getUpdatedDayFromDate(dobDay);
		String genderForUpdate = nscore4HomePage.getGenderValueForUpdate(gender);
		String attentionNameForUpdate = "Auto"+randomNum4;
		String ZIPCodeForUpdate = "78130-3397";
		String phoneNumberForUpdate =  String.valueOf(CommonUtils.getRandomNum(1000, 9999));
		//For Account Access Section
		nscore4HomePage.enterUserName(userNameForUpdate);
		nscore4HomePage.clickSaveBtnForAccountRecord();
		nscore4HomePage.clickUseAsEnteredbtn();
		s_assert.assertTrue(nscore4HomePage.getUpdationMessage().contains("Account saved successfully"), "Expected message is: Account saved successfully but actual on UI is: "+nscore4HomePage.getUpdationMessage());
		s_assert.assertTrue(nscore4HomePage.getUserName().contains(userNameForUpdate), "Expected username is: "+userNameForUpdate+" But actual on UI is "+nscore4HomePage.getUserName());
		// For Personal Info Section
		nscore4HomePage.enterFirstName(nameForUpdate);
		nscore4HomePage.enterLastFourDigitOfHomePhoneNumber(homePhoneForUpdate);
		nscore4HomePage.enterEmailAddress(emailIDForUpdate);
		nscore4HomePage.selectTaxExemptValue(taxExemptValueForUpdate);
		nscore4HomePage.enterNameOnSSNCard(nameOnSSNCardForUpdate);
		nscore4HomePage.clickDOBDate();
		nscore4HomePage.clickSpecficDateOfCalendar(dobDayForUpdate);
		//driver.pauseExecutionFor(3000);
		nscore4HomePage.selectGender(genderForUpdate);
		nscore4HomePage.clickSaveBtnForAccountRecord();
		nscore4HomePage.clickUseAsEnteredbtn();
		s_assert.assertTrue(nscore4HomePage.getUpdationMessage().contains("Account saved successfully"), "Expected message is: Account saved successfully but actual on UI is: "+nscore4HomePage.getUpdationMessage());
		s_assert.assertTrue(nscore4HomePage.getFirstName().contains(nameForUpdate), "Expected first name is: "+nameForUpdate+" But actual on UI is "+nscore4HomePage.getFirstName());
		s_assert.assertTrue(nscore4HomePage.getLastFourDgitOfHomePhoneNumber().contains(homePhoneForUpdate), "Expected home phone number is: "+homePhoneForUpdate+" But actual on UI is "+nscore4HomePage.getLastFourDgitOfHomePhoneNumber());
		s_assert.assertTrue(nscore4HomePage.getEmailAddress().contains(emailIDForUpdate), "Expected email ID is: "+emailIDForUpdate+" But actual on UI is "+nscore4HomePage.getEmailAddress());
		s_assert.assertTrue(nscore4HomePage.getTaxExemptValue().contains(taxExemptValueForUpdate), "Expected tax exempt value is: "+taxExemptValueForUpdate+" But actual on UI is "+nscore4HomePage.getTaxExemptValue());
		s_assert.assertTrue(nscore4HomePage.getNameOnSSNCard().contains(nameOnSSNCardForUpdate), "Expected name on SSN Card is: "+nameOnSSNCardForUpdate+" But actual on UI is "+nscore4HomePage.getNameOnSSNCard());
		s_assert.assertTrue(nscore4HomePage.getDOBValue().contains(dobDayForUpdate), "Expected day for DOB is: "+dobDayForUpdate+" But actual on UI is "+nscore4HomePage.getDayFromDate(nscore4HomePage.getDOBValue()));
		s_assert.assertTrue(nscore4HomePage.getSelectedGender().contains(genderForUpdate), "Expected gender is: "+genderForUpdate+" But actual on UI is "+nscore4HomePage.getSelectedGender());
		// Assert for Address of Record
		nscore4HomePage.enterAttentionName(attentionNameForUpdate);
		nscore4HomePage.enterZIPCode(ZIPCodeForUpdate);
		nscore4HomePage.enterLastFourDigitOfPhoneNumber(phoneNumberForUpdate);
		nscore4HomePage.clickSaveBtnForAccountRecord();
		nscore4HomePage.clickUseAsEnteredbtn();
		s_assert.assertTrue(nscore4HomePage.getUpdationMessage().contains("Account saved successfully"), "Expected message is: Account saved successfully but actual on UI is: "+nscore4HomePage.getUpdationMessage());
		s_assert.assertTrue(nscore4HomePage.getAttentionName().contains(attentionNameForUpdate), "Expected attention name is: "+attentionNameForUpdate+" But actual on UI is "+nscore4HomePage.getAttentionName());
		s_assert.assertTrue(nscore4HomePage.getZIPCode().contains(ZIPCodeForUpdate), "Expected ZIP code is: "+ZIPCodeForUpdate+" But actual on UI is "+nscore4HomePage.getZIPCode());
		s_assert.assertTrue(nscore4HomePage.getLastFourDgitOfPhoneNumber().contains(phoneNumberForUpdate), "Expected phone number is: "+phoneNumberForUpdate+" But actual on UI is "+nscore4HomePage.getLastFourDgitOfPhoneNumber());
		nscore4HomePage.enterUserName(userName);
		nscore4HomePage.enterFirstName(firstName);
		nscore4HomePage.enterLastFourDigitOfHomePhoneNumber(homePhone);
		nscore4HomePage.enterEmailAddress(emailID);
		nscore4HomePage.selectTaxExemptValue(taxExemptValue);
		nscore4HomePage.enterNameOnSSNCard(nameOnSSNCard);
		nscore4HomePage.clickDOBDate();
		nscore4HomePage.clickSpecficDateOfCalendar(dobDay);
		nscore4HomePage.selectGender(gender);
		nscore4HomePage.enterAttentionName(attentionName);
		nscore4HomePage.enterZIPCode(zipCode);
		nscore4HomePage.enterLastFourDigitOfPhoneNumber(phoneNumber);
		nscore4HomePage.clickSaveBtnForAccountRecord();
		nscore4HomePage.clickUseAsEnteredbtn();
		s_assert.assertTrue(nscore4HomePage.getUpdationMessage().contains("Account saved successfully"), "Expected message is: Account saved successfully but actual on UI is: "+nscore4HomePage.getUpdationMessage());
		s_assert.assertAll();
	}

	//QTest ID TC-278 NSC4_AccountsTab_ ShippingProfilesAddEditDefaultDelete
	@Test(priority=21)
	public void testAccountsTab_ShippingProfilesAddEditDefaultDelete_278(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newShippingProfileName = "newSP"+randomNum;
		String attentionCO ="SP";
		String addressLine1 ="123 J street";
		String zipCode= "28214-5037";
		String accountNumber = null;
		nscore4OrdersTabPage =new NSCore4OrdersTabPage(driver);
		logger.info("DB is "+RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);
		//NAavigate to Billing & Shipping Profile section
		nscore4HomePage.clickBillingAndShippingProfileLink();
		int totalNoOfAddressBeforeAdd = nscore4HomePage.getTotalNoOfShippingProfiles();
		//click 'Add' for the Shipping profile section
		nscore4HomePage.clickShippingProfileAddLink();
		//Enter all Information regarding new Shipping Profile-
		nscore4HomePage.addANewShippingProfile(newShippingProfileName, attentionCO, addressLine1, zipCode);
		//click 'SAVE ADDRESS BTN'
		nscore4HomePage.clickSaveAddressBtn();
		nscore4HomePage.refreshPage();
		//verify newly created shipping profile created?
		s_assert.assertTrue(nscore4HomePage.isNewlyCreatedShippingProfilePresent(newShippingProfileName),"Newly created Shipping Profile is not Present");
		//click on 'Set As Default Address' on the newly created profile
		nscore4HomePage.clickSetAsDefaultAddressForNewlyCreatedProfile(newShippingProfileName);
		//Verify profile is now default?
		s_assert.assertTrue(nscore4HomePage.validateNewlyCreatedShippingProfileIsDefault(newShippingProfileName),"Newly created Shipping Profile is Not Marked-DEFAULT");
		//Delete the newly created profile-
		nscore4HomePage.deleteAddressNewlyCreatedProfile(newShippingProfileName);
		int totalNoOfAddressAfterDelete = nscore4HomePage.getTotalNoOfShippingProfiles();
		s_assert.assertTrue(totalNoOfAddressAfterDelete ==  totalNoOfAddressBeforeAdd, "Expected count of shipping profile after delete is: "+totalNoOfAddressBeforeAdd+" Actual on UI is: "+totalNoOfAddressAfterDelete);
		s_assert.assertAll();    
	}

	//QTest ID TC-277 NSC4_AccountsTab_ BillingProfilesAddEditDefaultDelete
	@Test(priority=22)
	public void testAccountsTab_BillingProfilesAddEditDefaultDelete_277(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newBillingProfileName = "RFAutoNSCore4"+randomNum;
		String lastName = "lN";
		String nameOnCard = "rfTestUser";
		String cardNumber =  "4747474747474747";
		String addressLine1 = TestConstantsRFL.ADDRESS_LINE1;
		String zipCode = TestConstantsRFL.POSTAL_CODE;
		String accountNumber = null;
		String CVV = TestConstantsRFL.SECURITY_CODE;
		logger.info("DB is "+RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);
		//NAavigate to Billing & Shipping Profile section
		nscore4HomePage.clickBillingAndShippingProfileLink();
		int totalBillingProfilesBeforeAddingNew = nscore4HomePage.getTotalBillingProfiles();
		//click 'Add' for the billing profile section
		nscore4HomePage.clickBillingProfileAddLink();
		//Enter all the Information regarding New Billing Profile
		nscore4HomePage.addANewBillingProfile(newBillingProfileName, lastName, nameOnCard, cardNumber,addressLine1,zipCode,CVV);
		//click 'SAVE PAYMENT METHOD'
		nscore4HomePage.clickSavePaymentMethodBtn();
		//Verify that the new profile got created?
		s_assert.assertTrue(nscore4HomePage.getTotalBillingProfiles()==totalBillingProfilesBeforeAddingNew+1,"Newly created Billing Profile is not Present");
		//click on 'Set As Default Payment Method' on the newly created profile
		nscore4HomePage.clickSetAsDefaultPaymentMethodForNewlyCreatedProfile();
		//Verify profile is now default?
		s_assert.assertTrue(nscore4HomePage.validateNewlyCreatedBillingProfileIsDefault(),"Newly created Billing Profile is Not Marked-DEFAULT");
		//Delete the newly created profile-
		nscore4HomePage.deletePaymentMethodNewlyCreatedProfile();
		s_assert.assertTrue(nscore4HomePage.isBillingProfileDeleted(),"Billing profile is not deleted successfully");
		s_assert.assertAll();
	}

	//QTest ID TC-298 NSC4_AdminTab_ Users_AddEditRoles
	@Test(priority=23)
	public void testAdminTabUsersAddEditRoles_298(){
		int randomNum = CommonUtils.getRandomNum(10, 100);
		String roleName ="SampleRole";
		nscore4AdminPage=nscore4HomePage.clickAdminTab();
		//click Role->Add new role
		nscore4AdminPage.clickRolesLink();
		nscore4AdminPage.clickAddNewRoleLink();
		//Enter New Role Name and Save
		nscore4AdminPage.enterRoleName(roleName+randomNum);
		nscore4AdminPage.clickSaveBtn();
		//Verify that the new role got created and displayed as a link in Roles list?
		s_assert.assertTrue(nscore4AdminPage.validateNewRoleListedInRolesList(roleName+randomNum),"NEW Role Name is not listed in the roles list");
		s_assert.assertAll();
	}

	//QTest ID TC-258 NSC4_SitesTab_nsDistributor_BasePWSSitePagesAddEditNewSite
	@Test(priority=24)
	public void testBasePWSPagesAddEditNewSite_258(){
		int randomNumb = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumbers = CommonUtils.getRandomNum(10000, 1000000);
		String sublinkName = "Site Pages";
		String pageName = "AutomationPage"+randomNumb;
		String pageTitle = "AutoTitle"+randomNumber;
		String pageDescription = "AutoDesc"+randomNumbers;
		String pageURL = "/Pages/About/"+pageName;
		String pageKeyword = "Unique"+randomNumb;
		String templateView = "Two Piece View";

		logger.info("DB is "+RFL_DB);
		nscore4HomePage.clickTab("Sites");
		nscore4SitesTabPage.clickSubLinkOfDistributorOnSitePage(sublinkName);
		nscore4SitesTabPage.clickAddNewPageLink();
		nscore4SitesTabPage.enterNewPageDetails(pageName,pageTitle,pageDescription,pageURL,pageKeyword,templateView);
		nscore4SitesTabPage.clickSaveButtonForNewCreatedPage();
		s_assert.assertTrue(nscore4SitesTabPage.getPageSavedSuccessfullyTxtForSite().contains("Page saved successfully!"), "Expected saved message is: Site saved successfully but actual on UI is: "+nscore4SitesTabPage.getPageSavedSuccessfullyTxtForSite());
		int noOfOptionsInSizePageDD = nscore4SitesTabPage.getSizeOfOptinsFromPageSizeDD();
		nscore4SitesTabPage.clickAndSelectOptionInPageSizeDD(noOfOptionsInSizePageDD);
		s_assert.assertTrue(nscore4SitesTabPage.verifyPageNameOnSitePageList(pageName),"Newly created page name is not present on site page list");
		nscore4SitesTabPage.clickPageNameOnSitePageList(pageName);
		nscore4SitesTabPage.checkActiveCheckboxOnSitePage();
		nscore4SitesTabPage.clickSaveButtonForNewCreatedPage();
		s_assert.assertTrue(nscore4SitesTabPage.getPageSavedSuccessfullyTxtForSite().contains("Page saved successfully!"), "Expected Active checkbox unchecked message is: Site saved successfully but actual on UI is: "+nscore4SitesTabPage.getPageSavedSuccessfullyTxtForSite());
		s_assert.assertAll();
	}

	//QTest ID TC-291 NSC4_ProductsTab_ NewUpdateCatalog
	@Test(priority=25)
	public void testProductsTab_NewUpdateCatalog_291(){
		int randomNumber =  CommonUtils.getRandomNum(10000, 1000000);
		int randomNum =  CommonUtils.getRandomNum(1000, 100000);
		String catalogName = "Test"+randomNumber;
		String updatedCatalogInfo = "Test"+randomNum;
		nscore4HomePage.clickTab("Products");
		nscore4ProductsTabPage.clickCreateANewCatalogLink();
		nscore4ProductsTabPage.enterCatalogInfo(catalogName);
		nscore4ProductsTabPage.clickSaveCatalogBtn();
		nscore4HomePage.addAndGetProductSKUForCatalog();//(String) getValueFromQueryResult(randomSKUList, "SKU");;
		nscore4ProductsTabPage.clickSaveCatalogBtn();
		s_assert.assertTrue(nscore4ProductsTabPage.isSuccessMessagePresent(),"Success message is not displayed");
		nscore4ProductsTabPage.clickCatalogManagementLink();
		s_assert.assertTrue(nscore4ProductsTabPage.isNewCatalogPresentInList(catalogName),"new catalog is not present in the catalog list");
		nscore4ProductsTabPage.clicknewlyCreatedCatalogName(catalogName);
		nscore4ProductsTabPage.enterCatalogInfo(updatedCatalogInfo);
		nscore4ProductsTabPage.clickSaveCatalogBtn();
		s_assert.assertTrue(nscore4ProductsTabPage.isSuccessMessagePresent(),"Success message is not displayed");
		nscore4ProductsTabPage.clickCatalogManagementLink();
		s_assert.assertTrue(nscore4ProductsTabPage.isNewCatalogPresentInList(updatedCatalogInfo),"catalog info is not updated in the catalog list");
		s_assert.assertAll();
	}

	//QTest ID TC-245 NSC4_SitesTab_nsCorporate_CorporateReplicateSites
	@Test(priority=26)
	public void testNSC4SitesTabNSCorporateReplicateSites_245(){
		String sites = "Sites";
		String replicatedSites = "Replicated Sites";
		nscore4HomePage.clickTab(sites);
		nscore4SitesTabPage.clickSubLinkOfCorporate(replicatedSites);
		s_assert.assertTrue(nscore4SitesTabPage.isReplicatedSitesHeaderPresent(), "Replicated Sites is not present");
		s_assert.assertAll();
	}


	//NSC4 Full Return 
	@Test(priority=27)
	public void testNSC4FullReturn(){
		String accountNumber = null;
		String accounts = "Accounts";
		List<Map<String, Object>> randomSKUList =  null;
		String SKU = null;
		String placeNewOrder = "Place New Order";
		logger.info("DB is "+RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber");	
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);		
		nscore4HomePage.clickSublinkOfOverview(placeNewOrder);
		SKU= nscore4HomePage.addAndGetProductSKU("10");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
		nscore4HomePage.clickApplyPaymentButton();
		nscore4HomePage.clickSubmitOrderBtn();
		String orderID = nscore4HomePage.getOrderID().split("\\#")[1];
		nscore4HomePage.clickTab(accounts);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);		
		nscore4HomePage.clickOrderId(orderID);
		nscore4OrdersTabPage.clickReturnOrderLink();
		nscore4OrdersTabPage.enableReturnedChkBox();
		nscore4OrdersTabPage.clickUpdateLink();
		nscore4OrdersTabPage.clickSubmitReturnBtn();
		s_assert.assertTrue(nscore4OrdersTabPage.getOrderType().contains("Return"), "Order type does not contain return");
		s_assert.assertAll();
	}

	//NSC4 Partial  Return 
	@Test(priority=28)
	public void testNSC4PartialReturn(){
		String accountNumber = null;
		String accounts = "Accounts";
		String quantity = "5";
		String partialQuantity = "2";
		List<Map<String, Object>> randomSKUList =  null;
		String SKU = null;
		String placeNewOrder = "Place New Order";
		logger.info("DB is "+RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber");	
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);		
		nscore4HomePage.clickSublinkOfOverview(placeNewOrder);
		randomSKUList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_SKU,RFL_DB);
		SKU= nscore4HomePage.addAndGetProductSKU("10");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
		nscore4HomePage.clickApplyPaymentButton();
		nscore4HomePage.clickSubmitOrderBtn();
		String orderID = nscore4HomePage.getOrderID().split("\\#")[1];
		nscore4HomePage.clickTab(accounts);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);		
		nscore4HomePage.clickOrderId(orderID);
		nscore4OrdersTabPage.clickReturnOrderLink();
		nscore4OrdersTabPage.enableReturnedChkBox();
		nscore4OrdersTabPage.enterReturnQuantity(partialQuantity);
		nscore4OrdersTabPage.clickUpdateLink();
		nscore4OrdersTabPage.clickSubmitReturnBtn();
		s_assert.assertTrue(nscore4OrdersTabPage.getOrderType().contains("Return"), "Order type does not contain return");
		s_assert.assertTrue(nscore4OrdersTabPage.getReturnQuantityOfProduct().contains(partialQuantity), "Expected partial quantity is: "+partialQuantity+"Actual on UI is: "+nscore4OrdersTabPage.getReturnQuantityOfProduct());
		s_assert.assertAll();
	}

	//Return with Restocking fee
	@Test(priority=29)
	public void testNSC4ReturnWithRestockingFee(){
		String accountNumber = null;
		String accounts = "Accounts";
		String SKU = null;
		String restockingFee = "10.00";
		String placeNewOrder = "Place New Order";
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newBillingProfileName = "RFAutoNSCore4"+randomNum;
		String lastName = "lN";
		String nameOnCard = "rfTestUser";
		String cardNumber =  "4747474747474747";
		String CVV = TestConstantsRFL.SECURITY_CODE;
		String attentionName = "RFAutoNSCore4"+randomNum;
		logger.info("DB is "+RFL_DB);
		accountNumber =(String) getValueFromQueryResult(randomAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);  
		nscore4HomePage.clickSublinkOfOverview(placeNewOrder);
		SKU= nscore4HomePage.addAndGetProductSKU("10");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
		nscore4HomePage.addPaymentMethod(newBillingProfileName, lastName, nameOnCard, cardNumber,CVV,attentionName);
		nscore4HomePage.clickSavePaymentMethodBtn();
		nscore4HomePage.clickApplyPaymentButton();
		nscore4HomePage.clickSubmitOrderBtn();
		String orderID = nscore4HomePage.getOrderID().split("\\#")[1];
		nscore4HomePage.clickTab(accounts);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);  
		nscore4HomePage.clickOrderId(orderID);
		nscore4OrdersTabPage.clickReturnOrderLink();
		nscore4OrdersTabPage.enableReturnedChkBox();
		nscore4OrdersTabPage.enterRestockingFeeInPercent(restockingFee);
		nscore4OrdersTabPage.clickUpdateLink();
		nscore4OrdersTabPage.clickSubmitReturnBtn();
		s_assert.assertTrue(nscore4OrdersTabPage.getOrderType().contains("Return"), "Order type does not contain return");
		s_assert.assertTrue(nscore4OrdersTabPage.isRestockingFeeTxtPresent(), "Restocking fee is not present after return order with restocking fee");
		s_assert.assertAll();
	}

	// QTest ID TC-297 NSC4_AdminTab_ Users_AddEditUsers
	@Test(priority=30)
	public void testAdminTab_Users_AddEditUsers_297(){
		int randomNum = CommonUtils.getRandomNum(100, 10000);
		String firstName = "firstName"+randomNum;
		String lastName = "Ln"+randomNum;
		String userName = "userName"+randomNum;
		System.out.println(userName+"   username");
		String password = "abc"+randomNum;
		String confirmPassword = password;

		nscore4HomePage.clickTab("Admin");
		nscore4AdminPage.clickAddNewUser();
		nscore4AdminPage.enterInfoAtAddUserPage(firstName,lastName,userName,password,confirmPassword);
		nscore4AdminPage.selectAllSitesAndSave();
		s_assert.assertTrue(nscore4AdminPage.isUserSavedSuccessfully(),"user is saved successfully msg not present");
		s_assert.assertTrue(nscore4AdminPage.isNewUserPresentInList(userName),"new user is not present in the list");
		nscore4AdminPage.clickOnNewUser();
		nscore4AdminPage.selectStatus("InActive");
		s_assert.assertTrue(nscore4AdminPage.isUserSavedSuccessfully(),"user is saved successfully msg not present");
		s_assert.assertAll();

	}

	// QTest ID TC-299 NSC4_AdminTab_ LystTypes
	@Test(priority=31)
	public void testAdminTab_LystTypes_299(){
		int randomNum = CommonUtils.getRandomNum(100, 1000);
		String newAddedListValue = "test"+randomNum;
		nscore4HomePage.clickTab("Admin");
		nscore4AdminPage.clickListTypesLink("Account Note Category");
		nscore4AdminPage.clickAddNewListValue();
		int numberofListPresent = nscore4AdminPage.getTotalNumberOfList();
		nscore4AdminPage.enterValueAndSave(newAddedListValue,numberofListPresent);
		nscore4AdminPage.clickListTypesLink("Account Note Category");
		s_assert.assertTrue(nscore4AdminPage.isNewListAdded(newAddedListValue,numberofListPresent),"new list is not being added");
		nscore4AdminPage.deleteSavedListTypeAndSave(numberofListPresent);
		nscore4AdminPage.handleAlertPop();
		s_assert.assertTrue(nscore4AdminPage.verifyListDeleted(numberofListPresent),"list is not being deleted");
		s_assert.assertAll();
	}

	//QTest ID TC-269 NSC4_AccountsTab_OverviewChangeAndStatusHistory
	@Test(priority=32)
	public void testAccountsTab_OverviewChangeAndStatusHistory_269(){
		String accountNumber = null;
		List<Map<String, Object>> randomAccountList =  null;
		RFL_DB = driver.getDBNameRFL();
		logger.info("DB is "+RFL_DB);
		//randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL,RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomConsultantAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);
		//Overview tab-> click 'Policies Change History' link
		nscore4HomePage.clickPoliciesChangeHistoryLink();
		//verify 'Policies Change History' Page is displayed with respective columns?
		s_assert.assertTrue(nscore4HomePage.validatePoliciesChangeHistoryPageDisplayedWithRespectiveColumns(),"'Policis Change History' Page Is Not displayed with the respective columns");
		//click status history
		nscore4HomePage.clickStatusHistoryLink();
		//verify 'Status History' Page is displayed with respective columns?
		s_assert.assertTrue(nscore4HomePage.validateStatusHistoryPageDisplayedWithRespectiveColumns(),"'Status History' Page Is Not displayed with the respective columns");
		s_assert.assertAll();
	}

	//QTest ID TC-241 NSC4_SitesTab_nsCorporate_CorporateNewEditDeleteEvent
	@Test(priority=33)
	public void testNSC4SitesTabNSCorporateNewEditDeleteEvent_241(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int randomNum2 = CommonUtils.getRandomNum(10000, 1000000);
		String sites = "Sites";
		String subject = "For Automation"+randomNum;
		nscore4HomePage.clickTab(sites);
		nscore4SitesTabPage.clickSubLinksSitesTab("nsCorporate");
		nscore4SitesTabPage.clickSubLinkOfNSCorporate("Auto923175");
		nscore4SitesTabPage.clickAddEventLink();
		nscore4SitesTabPage.enterSubjectForEvent(subject);
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Event saved successfully"), "Expected saved message is: Event saved successfully but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		s_assert.assertTrue(nscore4SitesTabPage.isEventPresentAtCalendar(subject), "Event is not present at calendar ");
		nscore4SitesTabPage.clickEventNamePresentAtCalendar(subject);
		subject = "For Automation"+randomNum2;
		nscore4SitesTabPage.enterSubjectForEvent(subject);
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Event saved successfully"), "Expected saved message is: Event saved successfully but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		s_assert.assertTrue(nscore4SitesTabPage.isEventPresentAtCalendar(subject), "Event is not present at calendar ");
		nscore4SitesTabPage.clickEventNamePresentAtCalendar(subject);
		nscore4SitesTabPage.clickDeleteBtnForEvent();
		nscore4SitesTabPage.clickOKBtnOfJavaScriptPopUp();
		s_assert.assertFalse(nscore4SitesTabPage.isEventPresentAtCalendar(subject), "Event is present at calendar ");
		s_assert.assertAll();
	}

	//QTest ID TC-259 NSC4_SitesTab_nsDistributor_BasePWSSiteMap
	@Test(priority=34, enabled=false)
	public void testNSC4SitesTabBasePWSSiteMap_259(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String sites = "Sites";
		String siteMap = "Site Map";
		String linkText = "AutoRF"+randomNum;
		String pageOption = "MeetDrFields";
		String clearNavigationCache = "Clear Navigation Cache";
		String reloadProductCache  = "Reload Product Cache";

		nscore4HomePage.clickTab(sites);
		nscore4SitesTabPage.clickSubLinkOfDistributorOnSitePage(siteMap);
		nscore4SitesTabPage.clickAddLinkForSiteMap();
		nscore4SitesTabPage.enterLinkTextForSiteMap(linkText);
		nscore4SitesTabPage.selectPagesForSiteMap(pageOption);
		nscore4SitesTabPage.clickSaveBtnOnSiteMap();
		s_assert.assertTrue(nscore4SitesTabPage.isLinkTextNamePresentInTreeMap(linkText), "Link text name is not prsent in site map tree");
		nscore4SitesTabPage.expandTheTreeOfSiteMapOfBasePWS();
		nscore4SitesTabPage.moveToSiteMapLinkUnderDrFieldsForAddLinkOfBasePWS(linkText);
		nscore4SitesTabPage.clickActivateLinkOnSiteMap();
		nscore4HomePage.clickTab(sites);
		nscore4SitesTabPage.clickSubLinkOfNSCorporate(clearNavigationCache);
		s_assert.assertTrue(nscore4SitesTabPage.getProductClearAndReloadConfirmationMessage().contains("The navigation cache has been cleared"), "Expected confirmation message is: The navigation cache has been cleared but actual on UI is: "+nscore4SitesTabPage.getProductClearAndReloadConfirmationMessage());
		nscore4SitesTabPage.clickSubLinkOfNSCorporate(reloadProductCache);
		s_assert.assertTrue(nscore4SitesTabPage.getProductClearAndReloadConfirmationMessage().contains("The product cache has been reloaded"), "Expected confirmation message is: The product cache has been reloaded but actual on UI is: "+nscore4SitesTabPage.getProductClearAndReloadConfirmationMessage());
		nscore4SitesTabPage.clickSubLinkOfDistributorOnSitePage(siteMap);
		nscore4SitesTabPage.expandTheTreeOfSiteMapOfBasePWS();
		nscore4SitesTabPage.clickLinkTextNamePresentInTreeMap(linkText);
		nscore4SitesTabPage.clickDeactivateLinkOnSiteMap();
		s_assert.assertTrue(nscore4SitesTabPage.isActivateLinkPresentOnSiteMap(), "Activate link is not present after clicked on Deactivate link");
		s_assert.assertAll();
	}


	@Test(enabled=false)
	public void testOrdersTab_NewOrder_RC() {		
		/*for(int i=0; i<200; i++){*/
		/*System.out.println("Count is: " + i);*/
		String accountNumber = null;
		List<Map<String, Object>> completeNameList =  null;
		String SKU = null;
		logger.info("DB is "+RFL_DB);
		randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS,RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);
		nscore4OrdersTabPage = nscore4HomePage.clickOrdersTab();
		nscore4OrdersTabPage.clickStartANewOrderLink();
		completeNameList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_SPONSER_DETAILS_FROM_SPONSER_ACCOUNT_NUMBER, accountNumber),RFL_DB);
		String firstNameDB = String.valueOf(getValueFromQueryResult(completeNameList, "FirstName"));
		String lastNameDB = String.valueOf(getValueFromQueryResult(completeNameList, "LastName"));
		String completeNameDB = firstNameDB+" "+lastNameDB;
		logger.info("Complete name from DB is: "+completeNameDB);
		nscore4OrdersTabPage.enterAccountNameAndClickStartOrder(completeNameDB, accountNumber);
		//SKU = nscore4HomePage.addAndGetProductSKU("1");
		nscore4HomePage.addAndGetSpecficProductSKU("1");
		//s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
		driver.pauseExecutionFor(3000);
		nscore4OrdersTabPage.clickPaymentApplyLink();
		nscore4OrdersTabPage.clickSubmitOrderBtn();
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Products"),"Product information not present as expected");
		/*		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Shipments"),"Shipments information not present as expected");
    		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Payment"),"Payment information not present as expected");
    		s_assert.assertTrue(nscore4OrdersTabPage.validateOrderStatusAfterSubmitOrder(),"Order is not submitted after submit order");
    		s_assert.assertTrue(nscore4OrdersTabPage.isOrderDetailPagePresent(),"This is not order details page");*/
		s_assert.assertAll();
		try {
			ExcelReader.ExcelWriter(firstNameDB, lastNameDB, accountNumber, "C:\\Users\\plu\\heirloom\\rf-automation\\JavaBooks.xlsx");
		} catch (IOException e) {

			/*}*/

		}

	}
	//---------------------NSCore4 newly added tests--------------------------------------

	//QTest ID TC-410 NSC4_SitesTab_nsDistributor_BasePWSSiteDetailsEditSite
	@Test
	public void testBasePWSSiteDetailsEditSite_256(){
		String updatedSiteName = null;
		String updatedDescName = null;
		String updatedStatus = null;
		String updatedlanguage = null;
		Map<String,String> initialDetails = null;
		nscore4HomePage.clickTab("Sites");
		nscore4SitesTabPage.clickLinkUnderBasePWS("Site Details");
		initialDetails = nscore4SitesTabPage.getInitialSiteDetails();
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		updatedSiteName = initialDetails.get("SiteName")+randomNumber;
		updatedDescName = initialDetails.get("Description")+randomNumber;
		nscore4SitesTabPage.enterSiteNameForSiteDetails(updatedSiteName);
		nscore4SitesTabPage.enterDescription(updatedDescName);
		updatedStatus = nscore4SitesTabPage.changeStatusOfSiteDetails();
		updatedlanguage = nscore4SitesTabPage.selectNewLanguageFromDD(initialDetails.get("Language"));
		nscore4SitesTabPage.clickSaveBtnOnSiteDetails();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxtForSite().contains("Site saved successfully!"),"Saved Success Msg is not Present");
		s_assert.assertTrue(nscore4SitesTabPage.verifySiteNameFromLeftNav(updatedSiteName),"Site name is not present as Expected on Left Nav");
		s_assert.assertTrue(nscore4SitesTabPage.verifyDescFromLeftNav(updatedDescName),"Site Desc is not present as Expected on Left Nav");
		s_assert.assertTrue(nscore4SitesTabPage.verifyStatusFromLeftNav(updatedStatus),"Status is not present as Expected on Left Nav");
		nscore4SitesTabPage.clickLinkFromLeftNav("Site Details");
		s_assert.assertTrue(nscore4SitesTabPage.verifyLanguageFromSiteDetails(updatedlanguage),"Site name is not present as Selected in Language dropdown");

		// Revert The Changes Made
		nscore4SitesTabPage.enterSiteNameForSiteDetails(initialDetails.get("SiteName"));
		nscore4SitesTabPage.enterDescription(initialDetails.get("Description"));
		nscore4SitesTabPage.changeStatusOfSiteDetails();
		nscore4SitesTabPage.selectLanguageFromDD(initialDetails.get("Language"));
		nscore4SitesTabPage.clickSaveBtnOnSiteDetails();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxtForSite().contains("Site saved successfully!"),"Saved Success Msg is not Present");
		s_assert.assertAll();
	}



	//QTest ID TC-257 NSC4_SitesTab_nsDistributor_BasePWSReplicateSites
	@Test
	public void testBasePWSReplicateSites_257(){
		String currentURL = null;
		String urlToAssert = "ReplicatedSites";
		String currentSiteName = null;
		String updatedSiteName = null;
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		nscore4HomePage.clickTab("Sites");
		nscore4SitesTabPage.clickLinkUnderBasePWS("Replicated Sites");
		currentURL = nscore4SitesTabPage.getCurrentURL();
		s_assert.assertTrue(currentURL.contains(urlToAssert),"Current URL "+currentURL+" does not contain expected URL : "+urlToAssert);
		s_assert.assertTrue(nscore4SitesTabPage.verifyReplicateSitesHeaderIsPresent(),"Replicate Site Header is not present as expected");
		nscore4SitesTabPage.selectFromStatusFilter("Active");
		s_assert.assertTrue(nscore4SitesTabPage.verifySitesStatusAfterFilter("Active"),"Sites status is not found as Active after applying Active filter");
		nscore4SitesTabPage.selectFromStatusFilter("Cancelled");
		s_assert.assertTrue(nscore4SitesTabPage.verifySitesStatusAfterFilter("Cancelled"),"Sites status is not found as Cancelled after applying Cancelled filter");
		nscore4SitesTabPage.clickFirstSiteFromSitesGrid("Active");
		currentSiteName = nscore4SitesTabPage.getSiteName();
		updatedSiteName = currentSiteName+randomNumber;
		nscore4SitesTabPage.enterSiteNameForSiteDetails(updatedSiteName);
		nscore4SitesTabPage.clickSaveBtnOnSiteDetails();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxtForSite().contains("Site saved successfully!"),"Saved Success Msg is not Present");
		s_assert.assertTrue(nscore4SitesTabPage.verifySiteNameFromLeftNav(updatedSiteName),"Site name is not present as Expected on Left Nav");

		// Revert The Changes Made
		nscore4SitesTabPage.clickLinkFromLeftNav("Site Details");
		nscore4SitesTabPage.enterSiteNameForSiteDetails(currentSiteName);
		nscore4SitesTabPage.clickSaveBtnOnSiteDetails();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxtForSite().contains("Site saved successfully!"),"Saved Success Msg is not Present");
		s_assert.assertAll();
	}

	// QTest ID TC-253 NSC4_SitesTab_nsDistributor_BasePWSNewEditDeleteEvent
	@Test
	public void testBasePWSNewEditDeleteEvent_253(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int randomNum2 = CommonUtils.getRandomNum(10000, 1000000);
		String sites = "Sites";
		String subject = "For Automation"+randomNum;
		nscore4HomePage.clickTab(sites);
		nscore4SitesTabPage.clickBasePWSLink();
		nscore4SitesTabPage.clickAddEventLink();
		nscore4SitesTabPage.enterSubjectForEvent(subject);
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Event saved successfully"), "Expected saved message is: Event saved successfully but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		s_assert.assertTrue(nscore4SitesTabPage.isEventPresentAtCalendar(subject), "Event is not present at calendar ");
		nscore4SitesTabPage.clickEventNamePresentAtCalendar(subject);
		subject = "For Automation"+randomNum2;
		nscore4SitesTabPage.enterSubjectForEvent(subject);
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Event saved successfully"), "Expected saved message is: Event saved successfully but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		s_assert.assertTrue(nscore4SitesTabPage.isEventPresentAtCalendar(subject), "Event is not present at calendar ");
		nscore4SitesTabPage.clickEventNamePresentAtCalendar(subject);
		nscore4SitesTabPage.clickDeleteBtnForEvent();
		nscore4SitesTabPage.clickOKBtnOfJavaScriptPopUp();
		s_assert.assertFalse(nscore4SitesTabPage.isEventPresentAtCalendar(subject), "Event is present at calendar ");
		s_assert.assertAll();

	}
	// QTest ID TC-295 NSC4_ProductsTab_ ProductsManagement_FulfillmentDetails
	@Test
	public void testProductsManagementFulfillmentDetails_295(){
		String products = "Products";
		String sku = null;
		String randomNum = Integer.toString(CommonUtils.getRandomNum(100000, 10000000));
		String itemNum = null;
		nscore4HomePage.clickTab(products);
		nscore4ProductsTabPage.hoverProductManagementAndClickOption("Fulfillment Details");
		sku = nscore4ProductsTabPage.getProductSkuWithoutFulfillmentDetails();
		System.out.println("SKU : "+sku);
		nscore4ProductsTabPage.clickAddFulfillmentDetailsLink(sku);
		nscore4ProductsTabPage.enterFulfillmentDetails(randomNum, randomNum, randomNum);
		nscore4ProductsTabPage.clickSaveFulfillmentBtn();
		s_assert.assertTrue(nscore4ProductsTabPage.verifySavedFulfillmentDetailsSuccessfulMsg(),"Saved Fulfillment Successful Msg is not present");
		itemNum = nscore4ProductsTabPage.getItemNumberForSku(sku);
		s_assert.assertTrue(itemNum.equals(randomNum),"Item Number is not present as expected for SKU : "+sku+". Expected : "+randomNum+". Actual : "+itemNum);
		nscore4ProductsTabPage.clickUpdateLinkForSKU(sku);
		randomNum = Integer.toString(CommonUtils.getRandomNum(100000, 10000000));
		nscore4ProductsTabPage.enterFulfillmentDetails(randomNum, randomNum, randomNum);
		nscore4ProductsTabPage.clickSaveFulfillmentBtn();
		s_assert.assertTrue(nscore4ProductsTabPage.verifySavedFulfillmentDetailsSuccessfulMsg(),"Saved Fulfillment Details Successful Msg is not present");
		itemNum = nscore4ProductsTabPage.getItemNumberForSku(sku);
		s_assert.assertTrue(itemNum.equals(randomNum),"Item Number after updation is not present as expected for SKU : "+sku+". Expected : "+randomNum+". Actual : "+itemNum);
		nscore4ProductsTabPage.clickDeleteLinkForSKU(sku);
		s_assert.assertTrue(nscore4ProductsTabPage.verifyDeleteFulfillmentDetailsSuccessfulMsg(),"Delete Fulfillment Details Successful Msg is not present");
		s_assert.assertTrue(nscore4ProductsTabPage.isFulfillmentDetailsNotPresentForSKU(sku),"Fulfillment Details are still present for SKU : "+sku);
		s_assert.assertAll();
	}


	// QTest ID TC-296 NSC4_ProductsTab_ ProductsManagement_ProductTypes
	@Test
	public void testProductsManagementProductTypes_296(){
		String products = "Products";
		String productType = "ProdType"+CommonUtils.getRandomWord(5);
		String updatedProductType = "ProdType"+CommonUtils.getRandomWord(5);
		nscore4HomePage.clickTab(products);
		nscore4ProductsTabPage.hoverProductManagementAndClickOption("Product Types");
		nscore4ProductsTabPage.clickAddANewProductTypeLink();
		nscore4ProductsTabPage.enterProductTypeAndSave(productType);
		s_assert.assertTrue(nscore4ProductsTabPage.isNewlyAddedProductTypePresentInList(productType),"Newly Added ProductType :"+productType+" is not present in the List");
		nscore4ProductsTabPage.clickOnProductTypeFromList(productType);
		nscore4ProductsTabPage.enterProductTypeAndSave(updatedProductType);
		s_assert.assertTrue(nscore4ProductsTabPage.isNewlyAddedProductTypePresentInList(updatedProductType),"Updated ProductType :"+updatedProductType+" is not present in the List");
		nscore4ProductsTabPage.clickOnProductTypeFromList(updatedProductType);
		nscore4ProductsTabPage.clickDeleteProductTypeBtn();
		s_assert.assertFalse(nscore4ProductsTabPage.isNewlyAddedProductTypePresentInList(updatedProductType),"ProductType :"+updatedProductType+" is still present in the List");
		s_assert.assertAll();
	}


	@Test
	public void testProductsManagementAddNewProduct(){
		String products = "Products";
		String productSku = "SKU"+CommonUtils.getRandomNum(100000,1000000);
		String productName = "TestProduct_"+CommonUtils.getRandomNum(100000,1000000);
		String productWeight = "100";
		String expectedUrlForOverviewPage = "Products/Overview";
		String currentUrl = null;
		String linkFromLeftNav = null;
		String prodPrice = "100";
		String shortDesc = "Test Short Description";
		String skuForRelationship = null;
		nscore4HomePage.clickTab(products);
		nscore4ProductsTabPage.hoverProductManagementAndClickOption("Browse Products");
		nscore4ProductsTabPage.chooseProductTypeAndStatus("Kits","All");
		skuForRelationship = nscore4ProductsTabPage.getFirstProductSKU();
		nscore4ProductsTabPage.hoverProductManagementAndClickOption("Add a New Product");
		nscore4ProductsTabPage.enterNewProductDetails(productSku, productName, productWeight);
		nscore4ProductsTabPage.clickSaveCatalogBtn();
		s_assert.assertTrue(nscore4ProductsTabPage.verifyProductOverviewLinkIsPresent(),"Product Overview Link is not present");
		currentUrl = nscore4ProductsTabPage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(expectedUrlForOverviewPage),"Current URL "+currentUrl+" does not contain expected URL : "+expectedUrlForOverviewPage);
		//Details
		linkFromLeftNav = "Details";
		nscore4ProductsTabPage.clickLinkFromLeftNav(linkFromLeftNav);
		productSku = "SKU"+CommonUtils.getRandomNum(100000,1000000);
		nscore4ProductsTabPage.updateSKUOfProduct(productSku);
		nscore4ProductsTabPage.clickSaveCatalogBtn();
		s_assert.assertTrue(nscore4ProductsTabPage.getActionSuccessMsg().contains("Details saved!"),"Details Saved Success msg is not present as expected");
		// Pricing
		linkFromLeftNav = "Pricing";
		nscore4ProductsTabPage.clickLinkFromLeftNav(linkFromLeftNav);
		nscore4ProductsTabPage.enterPriceDetailsForProduct(prodPrice, prodPrice, prodPrice);
		nscore4ProductsTabPage.clickSavePricesBtn();
		s_assert.assertTrue(nscore4ProductsTabPage.getActionSuccessMsg().contains("Prices saved!"),"Pricing Saved Success msg is not present as expected");
		// CMS
		linkFromLeftNav = "CMS";
		nscore4ProductsTabPage.clickLinkFromLeftNav(linkFromLeftNav);
		nscore4ProductsTabPage.enterShortDescOnCMSTab(shortDesc);
		nscore4ProductsTabPage.clickSaveDescriptionsBtn();
		s_assert.assertTrue(nscore4ProductsTabPage.getActionSuccessMsg().contains("Content saved!"),"Description Saved Success msg is not present as expected");
		// Categories
		linkFromLeftNav = "Categories";
		nscore4ProductsTabPage.clickLinkFromLeftNav(linkFromLeftNav);
		nscore4ProductsTabPage.selectRandomCatgory();
		nscore4ProductsTabPage.clickSaveCatalogBtn();
		s_assert.assertTrue(nscore4ProductsTabPage.getActionSuccessMsg().contains("Categories saved!"),"Categories Saved Success msg is not present as expected");
		// Relationships
		linkFromLeftNav = "Relationships";
		nscore4ProductsTabPage.clickLinkFromLeftNav(linkFromLeftNav);
		nscore4ProductsTabPage.enterProductInRelationshipTB(skuForRelationship);
		nscore4ProductsTabPage.selectRelationTypeFromDD("Refill");
		nscore4ProductsTabPage.clickAddRelationBtn();
		s_assert.assertTrue(nscore4ProductsTabPage.isSKUPresentInRelationShipBox(skuForRelationship),skuForRelationship+" is not present in Relationship container");
		//Availability
		linkFromLeftNav = "Availability";
		nscore4ProductsTabPage.clickLinkFromLeftNav(linkFromLeftNav);
		nscore4ProductsTabPage.editProductAvailablityAndSave();
		s_assert.assertTrue(nscore4ProductsTabPage.getActionSuccessMsg().contains("Availability saved!"),"Availability saved Success msg is not present as expected");
		// Product Properties
		linkFromLeftNav = "Product Properties";
		nscore4ProductsTabPage.clickLinkFromLeftNav(linkFromLeftNav);
		nscore4ProductsTabPage.selectAllCartsRadioBtn();
		nscore4ProductsTabPage.clickSaveCatalogBtn();
		s_assert.assertTrue(nscore4ProductsTabPage.getActionSuccessMsg().contains("Properties saved!"),"Properties saved Success msg is not present as expected");
		s_assert.assertAll();
	}

	//QTest ID TC-246 NSC4_SitesTab_nsCorporate_CorporateSitePagesAddEditNewSite
	@Test
	public void testCorporateSitePageAddEditNewSite_246(){
		int randomNumb = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumbers = CommonUtils.getRandomNum(10000, 1000000);
		String sublinkName = "Site Pages";
		String pageName = "AutomationPage"+randomNumb;
		String pageTitle = "AutoTitle"+randomNumber;
		String pageDescription = "AutoDesc"+randomNumbers;
		String pageURL = "/Pages/About/"+pageName;
		String pageKeyword = "Unique"+randomNumb;
		String templateView = "Two Piece View";
		logger.info("DB is "+RFL_DB);
		nscore4HomePage.clickTab("Sites");
		nscore4SitesTabPage.clickSubLinkOfCorporate(sublinkName);
		nscore4SitesTabPage.clickAddNewPageLink();
		nscore4SitesTabPage.enterNewPageDetails(pageName,pageTitle,pageDescription,pageURL,pageKeyword,templateView);
		nscore4SitesTabPage.clickSaveButtonForNewCreatedPage();
		s_assert.assertTrue(nscore4SitesTabPage.getPageSavedSuccessfullyTxtForSite().contains("Page saved successfully!"), "Expected saved message is: Site saved successfully but actual on UI is: "+nscore4SitesTabPage.getPageSavedSuccessfullyTxtForSite());
		int noOfOptionsInSizePageDD = nscore4SitesTabPage.getSizeOfOptinsFromPageSizeDD();
		nscore4SitesTabPage.clickAndSelectOptionInPageSizeDD(noOfOptionsInSizePageDD);
		s_assert.assertTrue(nscore4SitesTabPage.verifyPageNameOnSitePageList(pageName),"Newly created page name is not present on site page list");
		nscore4SitesTabPage.clickPageNameOnSitePageList(pageName);
		//Edit the page details
		randomNumb = CommonUtils.getRandomNum(10000, 1000000);
		randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		randomNumbers = CommonUtils.getRandomNum(10000, 1000000);
		pageName = "AutomationPage"+randomNumb;
		pageTitle = "AutoTitle"+randomNumber;
		pageDescription = "AutoDesc"+randomNumbers;
		pageURL = "/Pages/About/"+pageName;
		pageKeyword = "Unique"+randomNumb;
		nscore4SitesTabPage.enterNewPageDetails(pageName,pageTitle,pageDescription,pageURL,pageKeyword,templateView);
		nscore4SitesTabPage.clickSaveButtonForNewCreatedPage();
		s_assert.assertTrue(nscore4SitesTabPage.getPageSavedSuccessfullyTxtForSite().contains("Page saved successfully!"), "Expected saved message after edit the page details is: Site saved successfully but actual on UI is: "+nscore4SitesTabPage.getPageSavedSuccessfullyTxtForSite());
		noOfOptionsInSizePageDD = nscore4SitesTabPage.getSizeOfOptinsFromPageSizeDD();
		nscore4SitesTabPage.clickAndSelectOptionInPageSizeDD(noOfOptionsInSizePageDD);
		s_assert.assertTrue(nscore4SitesTabPage.verifyPageNameOnSitePageList(pageName),"Edited page name is not present on site page list");
		s_assert.assertAll();
	}

	//QTest ID TC-243 NSC4_SitesTab_nsCorporate_CorporateUploadEditResource
	@Test
	public void testCorporateUploadEditResource_243(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumbers = CommonUtils.getRandomNum(10000, 1000000);
		String name = "AutomationResource"+randomNumber;
		String filePath = "/AutoPath"+randomNumbers;
		String categoryDDValue = "Field Events - US";
		String resourceStatus = null;
		nscore4HomePage.clickTab("Sites");
		nscore4SitesTabPage.clickCorporateLink();
		nscore4SitesTabPage.clickUploadResourceLink();
		nscore4SitesTabPage.enterUploadResourceDetails(name, filePath, categoryDDValue);
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Resource saved successfully!"), "Expected saved message after added the new resource details is: Resource saved successfully! but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		nscore4SitesTabPage.enterSearchTerms(name);
		nscore4SitesTabPage.clickGoBtn();
		s_assert.assertTrue(nscore4SitesTabPage.verifyPageNameOnSitePageList(name),"Newly created resource is not present on resource list");
		nscore4SitesTabPage.clickPageNameOnSitePageList(name);
		// Edit the resource details
		randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		randomNumbers = CommonUtils.getRandomNum(10000, 1000000);
		name = "AutomationResource"+randomNumber;
		filePath = "/AutoPath"+randomNumbers;
		nscore4SitesTabPage.enterUploadResourceDetails(name, filePath, categoryDDValue);
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Resource saved successfully!"), "Expected saved message after editedthe new resource details is: Resource saved successfully! but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		nscore4SitesTabPage.enterSearchTerms(name);
		nscore4SitesTabPage.clickGoBtn();
		s_assert.assertTrue(nscore4SitesTabPage.verifyPageNameOnSitePageList(name),"Edited resource is not present on resource list");
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(name);
		nscore4SitesTabPage.clickDeactivateSelectedLinkForUploadResoursce();
		resourceStatus = nscore4SitesTabPage.getResoursceStatus(name);
		s_assert.assertTrue(resourceStatus.contains("Inactive"), "Expected resource's status is Inactive but actual on UI is "+resourceStatus);
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(name);
		nscore4SitesTabPage.clickActivateSelectedLinkForUploadResoursce();
		resourceStatus = nscore4SitesTabPage.getResoursceStatus(name);
		s_assert.assertTrue(resourceStatus.contains("Active"), "Expected resource's status is Active but actual on UI is "+resourceStatus);
		s_assert.assertAll();
	}

	//QTest ID TC-249 NSC4_SitesTab_nsCorporate_CorporateResourceLibrary
	@Test
	public void testNsCorporateResourceLibrary_249(){
		String resourceLibraryLink = "Resource Library";
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber1 = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber2 = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber3 = CommonUtils.getRandomNum(10000, 1000000);
		String name = "AutomationResource"+randomNumber;
		String filePath = "/AutoPath"+randomNumber1;
		String categoryDDValue = "Field Events - US";
		String categoryDDValueForMove = "Business System";
		String description = "AutoDesc"+randomNumber2;
		String categoryName = "AutoCategory"+randomNumber3;
		String categoryNameFromUI = null;
		String resourceStatus = null;
		String message = null;
		nscore4HomePage.clickTab("Sites");
		nscore4SitesTabPage.clickSubLinkOfCorporate(resourceLibraryLink);
		nscore4SitesTabPage.clickAddResourceLink();
		nscore4SitesTabPage.enterUploadResourceDetails(name, filePath, categoryDDValue);
		nscore4SitesTabPage.enterDescription(description);
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Resource saved successfully!"), "Expected saved message after added the new resource details is: Resource saved successfully! but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		nscore4SitesTabPage.clickAddCategoryLink();
		nscore4SitesTabPage.enterCategoryName(categoryName);
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Category saved successfully!"), "Expected saved message after added the new category details is: Category saved successfully! but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		//Back to resource library page
		nscore4HomePage.clickTab("Sites");
		nscore4SitesTabPage.clickSubLinkOfCorporate(resourceLibraryLink);
		nscore4SitesTabPage.enterSearchTerms(name);
		nscore4SitesTabPage.clickGoBtn();
		s_assert.assertTrue(nscore4SitesTabPage.verifyPageNameOnSitePageList(name),"Edited resource is not present on resource list");
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(name);
		nscore4SitesTabPage.selectCategoryForMoveToResource(categoryName);
		nscore4SitesTabPage.clickMoveSelectedToCategory();
		categoryNameFromUI = nscore4SitesTabPage.getResourceCategory(name);
		s_assert.assertTrue(categoryNameFromUI.contains(categoryName.trim()), "Expected category name is "+categoryName+" but actual on UI is "+categoryNameFromUI);
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(name);
		nscore4SitesTabPage.clickDeactivateSelectedLinkForUploadResoursce();
		resourceStatus = nscore4SitesTabPage.getResoursceStatus(name);
		s_assert.assertTrue(resourceStatus.contains("Inactive"), "Expected resource's status is Inactive but actual on UI is "+resourceStatus);
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(name);
		nscore4SitesTabPage.clickActivateSelectedLinkForUploadResoursce();
		resourceStatus = nscore4SitesTabPage.getResoursceStatus(name);
		s_assert.assertTrue(resourceStatus.contains("Active"), "Expected resource's status is Active but actual on UI is "+resourceStatus);
		nscore4SitesTabPage.clickManageResourceCategoriesLink();
		nscore4SitesTabPage.enterSearchTerms(categoryName);
		nscore4SitesTabPage.clickGoBtnForManageResource();
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(categoryName);
		nscore4SitesTabPage.clickDeleteSelectedLinkForManageResource();
		message = nscore4SitesTabPage.getMessageOfDelete();
		s_assert.assertTrue(message.contains("is in use and cannot be deleted")&& message.contains(categoryName), "Expected message should contains categor name is "+categoryName+ "and is in use and cannot be deleted but actual on UI is "+message);
		//change the category
		nscore4SitesTabPage.clickLinkInSidePanel(resourceLibraryLink);
		nscore4SitesTabPage.enterSearchTerms(name);
		nscore4SitesTabPage.clickGoBtn();
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(name);
		nscore4SitesTabPage.selectCategoryForMoveToResource(categoryDDValueForMove);
		nscore4SitesTabPage.clickMoveSelectedToCategory();
		categoryNameFromUI = nscore4SitesTabPage.getResourceCategory(name);
		s_assert.assertTrue(categoryNameFromUI.contains(categoryDDValueForMove.trim()), "Expected category name after delete is "+categoryName+" but actual on UI is "+categoryDDValueForMove);
		//delete the category
		nscore4SitesTabPage.clickManageResourceCategoriesLink();
		nscore4SitesTabPage.enterSearchTerms(categoryName);
		nscore4SitesTabPage.clickGoBtnForManageResource();
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(categoryName);
		nscore4SitesTabPage.clickDeleteSelectedLinkForManageResource();
		nscore4SitesTabPage.enterSearchTerms(categoryName);
		nscore4SitesTabPage.clickGoBtnForManageResource();
		s_assert.assertFalse(nscore4SitesTabPage.isCategoryNamePresent(categoryName), "Category name is present after delete");
		s_assert.assertAll();
	}

	//QTest ID TC-254 NSC4_SitesTab_nsDistributor_BasePWSAddEditDeleteNews
	@Test
	public void testBasePWSAddEditDeleteNews_254(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber1 = CommonUtils.getRandomNum(10000, 1000000);
		String name = "AutomationNews"+randomNumber;
		String caption = "Automation"+randomNumber1;
		String resourceStatus = null;
		nscore4HomePage.clickTab("Sites");
		nscore4SitesTabPage.clickBasePWSLink();
		nscore4SitesTabPage.clickAddNewsLink();
		nscore4SitesTabPage.enterNewsDetails(name, caption);
		nscore4SitesTabPage.checkIsActiveChkBoxForNewsTitle();
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("News saved successfully!"), "Expected saved message after added news details is: News saved successfully! but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		int noOfOptionsInSizePageDD = nscore4SitesTabPage.getSizeOfOptinsFromPageSizeDD();
		nscore4SitesTabPage.clickAndSelectOptionInPageSizeDD(noOfOptionsInSizePageDD);
		s_assert.assertTrue(nscore4SitesTabPage.verifyPageNameOnSitePageList(name),"Newly edited news name is not present on site page list");
		nscore4SitesTabPage.clickPageNameOnSitePageList(name);
		//Edit the news
		randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		randomNumber1 = CommonUtils.getRandomNum(10000, 1000000);
		name = "AutomationNews"+randomNumber;
		caption = "Automation"+randomNumber1;
		nscore4SitesTabPage.enterNewsDetails(name, caption);
		nscore4SitesTabPage.checkIsActiveChkBoxForNewsTitle();
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("News saved successfully!"), "Expected saved message after added news details is: News saved successfully! but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		noOfOptionsInSizePageDD = nscore4SitesTabPage.getSizeOfOptinsFromPageSizeDD();
		nscore4SitesTabPage.clickAndSelectOptionInPageSizeDD(noOfOptionsInSizePageDD);
		s_assert.assertTrue(nscore4SitesTabPage.verifyPageNameOnSitePageList(name),"Newly edited news name is not present on site page list");
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(name);
		nscore4SitesTabPage.clickDeactivateSelectedLink();
		resourceStatus = nscore4SitesTabPage.getTitleStatus(name);
		s_assert.assertTrue(resourceStatus.contains("Inactive"), "Expected news status is Inactive but actual on UI is "+resourceStatus);
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(name);
		nscore4SitesTabPage.clickActivateSelectedLink();
		resourceStatus = nscore4SitesTabPage.getTitleStatus(name);
		s_assert.assertTrue(resourceStatus.contains("Active"), "Expected resource's status is Active but actual on UI is "+resourceStatus);
		//delete the news
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(name);
		nscore4SitesTabPage.clickDeleteSelectedLink();
		s_assert.assertFalse(nscore4SitesTabPage.isCategoryNamePresent(name), "news name is present after delete");
		s_assert.assertAll();
	}

	//QTest ID TC-255 NSC4_SitesTab_nsDistributor_BasePWSUploadEditResource
	@Test
	public void testBasePWSUploadEditResource_255(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumbers = CommonUtils.getRandomNum(10000, 1000000);
		String name = "AutomationResource"+randomNumber;
		String filePath = "/AutoPath"+randomNumbers;
		String categoryDDValue = "Field Events - US";
		String resourceStatus = null;
		nscore4HomePage.clickTab("Sites");
		nscore4SitesTabPage.clickBasePWSLink();
		nscore4SitesTabPage.clickUploadResourceLink();
		nscore4SitesTabPage.enterUploadResourceDetails(name, filePath, categoryDDValue);
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Resource saved successfully!"), "Expected saved message after added the new resource details is: Resource saved successfully! but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		nscore4SitesTabPage.enterSearchTerms(name);
		nscore4SitesTabPage.clickGoBtn();
		s_assert.assertTrue(nscore4SitesTabPage.verifyPageNameOnSitePageList(name),"Newly created resource is not present on resource list");
		nscore4SitesTabPage.clickPageNameOnSitePageList(name);
		// Edit the resource details
		randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		randomNumbers = CommonUtils.getRandomNum(10000, 1000000);
		name = "AutomationResource"+randomNumber;
		filePath = "/AutoPath"+randomNumbers;
		nscore4SitesTabPage.enterUploadResourceDetails(name, filePath, categoryDDValue);
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Resource saved successfully!"), "Expected saved message after editedthe new resource details is: Resource saved successfully! but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		nscore4SitesTabPage.enterSearchTerms(name);
		nscore4SitesTabPage.clickGoBtn();
		s_assert.assertTrue(nscore4SitesTabPage.verifyPageNameOnSitePageList(name),"Edited resource is not present on resource list");
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(name);
		nscore4SitesTabPage.clickDeactivateSelectedLinkForUploadResoursce();
		resourceStatus = nscore4SitesTabPage.getResoursceStatus(name);
		s_assert.assertTrue(resourceStatus.contains("Inactive"), "Expected resource's status is Inactive but actual on UI is "+resourceStatus);
		s_assert.assertAll();
	}

	//QTest ID TC-261 NSC4_SitesTab_nsDistributor_BasePWSResourceLibrary
	@Test
	public void testBasePWSResourceLibrary(){
		String resourceLibraryLink = "Resource Library";
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber1 = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber2 = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber3 = CommonUtils.getRandomNum(10000, 1000000);
		String name = "AutomationResource"+randomNumber;
		String filePath = "/AutoPath"+randomNumber1;
		String categoryDDValue = "Field Events - US";
		String categoryDDValueForMove = "Business System";
		String description = "AutoDesc"+randomNumber2;
		String categoryName = "AutoCategory"+randomNumber3;
		String categoryNameFromUI = null;
		String resourceStatus = null;
		String message = null;
		nscore4HomePage.clickTab("Sites");
		nscore4SitesTabPage.clickSubLinkOfDistributorOnSitePage(resourceLibraryLink);
		nscore4SitesTabPage.clickAddResourceLink();
		nscore4SitesTabPage.enterUploadResourceDetails(name, filePath, categoryDDValue);
		nscore4SitesTabPage.enterDescription(description);
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Resource saved successfully!"), "Expected saved message after added the new resource details is: Resource saved successfully! but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		nscore4SitesTabPage.clickAddCategoryLink();
		nscore4SitesTabPage.enterCategoryName(categoryName);
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Category saved successfully!"), "Expected saved message after added the new category details is: Category saved successfully! but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		//Back to resource library page
		nscore4HomePage.clickTab("Sites");
		nscore4SitesTabPage.clickSubLinkOfDistributorOnSitePage(resourceLibraryLink);
		nscore4SitesTabPage.enterSearchTerms(name);
		nscore4SitesTabPage.clickGoBtn();
		s_assert.assertTrue(nscore4SitesTabPage.verifyPageNameOnSitePageList(name),"Edited resource is not present on resource list");
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(name);
		nscore4SitesTabPage.selectCategoryForMoveToResource(categoryName);
		nscore4SitesTabPage.clickMoveSelectedToCategory();
		categoryNameFromUI = nscore4SitesTabPage.getResourceCategory(name);
		s_assert.assertTrue(categoryNameFromUI.contains(categoryName.trim()), "Expected category name is "+categoryName+" but actual on UI is "+categoryNameFromUI);
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(name);
		nscore4SitesTabPage.clickDeactivateSelectedLinkForUploadResoursce();
		resourceStatus = nscore4SitesTabPage.getResoursceStatus(name);
		s_assert.assertTrue(resourceStatus.contains("Inactive"), "Expected resource's status is Inactive but actual on UI is "+resourceStatus);
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(name);
		nscore4SitesTabPage.clickActivateSelectedLinkForUploadResoursce();
		resourceStatus = nscore4SitesTabPage.getResoursceStatus(name);
		s_assert.assertTrue(resourceStatus.contains("Active"), "Expected resource's status is Active but actual on UI is "+resourceStatus);
		nscore4SitesTabPage.clickManageResourceCategoriesLink();
		nscore4SitesTabPage.enterSearchTerms(categoryName);
		nscore4SitesTabPage.clickGoBtnForManageResource();
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(categoryName);
		nscore4SitesTabPage.clickDeleteSelectedLinkForManageResource();
		message = nscore4SitesTabPage.getMessageOfDelete();
		s_assert.assertTrue(message.contains("is in use and cannot be deleted")&& message.contains(categoryName), "Expected message should contains categor name is "+categoryName+ "and is in use and cannot be deleted but actual on UI is "+message);
		//change the category
		nscore4SitesTabPage.clickLinkInSidePanel(resourceLibraryLink);
		nscore4SitesTabPage.enterSearchTerms(name);
		nscore4SitesTabPage.clickGoBtn();
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(name);
		nscore4SitesTabPage.selectCategoryForMoveToResource(categoryDDValueForMove);
		nscore4SitesTabPage.clickMoveSelectedToCategory();
		categoryNameFromUI = nscore4SitesTabPage.getResourceCategory(name);
		s_assert.assertTrue(categoryNameFromUI.contains(categoryDDValueForMove.trim()), "Expected category name after delete is "+categoryName+" but actual on UI is "+categoryDDValueForMove);
		//delete the category
		nscore4SitesTabPage.clickManageResourceCategoriesLink();
		nscore4SitesTabPage.enterSearchTerms(categoryName);
		nscore4SitesTabPage.clickGoBtnForManageResource();
		nscore4SitesTabPage.checkTitleNameChkBoxInAnnouncementsList(categoryName);
		nscore4SitesTabPage.clickDeleteSelectedLinkForManageResource();
		nscore4SitesTabPage.enterSearchTerms(categoryName);
		nscore4SitesTabPage.clickGoBtnForManageResource();
		s_assert.assertFalse(nscore4SitesTabPage.isCategoryNamePresent(categoryName), "Category name is present after delete");
		s_assert.assertAll();
	}

	//QTest ID TC-276 NSC4_AccountsTab_SiteSubscriptionsUpdate
	@Test
	public void testSiteSubscriptionsUpdate_276(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber1 = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber2 = CommonUtils.getRandomNum(10000, 1000000);
		String accountNumber = null;
		String subscriptionStatus = null;
		String bizPrefixFromUI = null;
		String comPrefixFromUI = null;
		String pulseEmailFromUI = null;
		String bizPrefixAfterUpdate = null;
		String comPrefixAfterUpdate = null;
		String pulseEmailAfterUpdate = null;
		String bizPrefix = null;
		String comPrefix = null;
		String pulseEmail = null;
		String successMessage = null;
		accountNumber = "805910";//(String) getValueFromQueryResult(randomConsultantAccountList, "AccountNumber");	
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);
		nscore4HomePage.clickLinkInSidePanel("Site Subscriptions");
		subscriptionStatus = nscore4HomePage.getSubscriptionStatus();
		s_assert.assertTrue(subscriptionStatus.contains("paid"), "Expected subscription status is paid but actual on UI is"+subscriptionStatus);
		comPrefixFromUI = nscore4HomePage.getPrefixFromUrl("com");
		bizPrefixFromUI = nscore4HomePage.getPrefixFromUrl("biz");
		pulseEmailFromUI = nscore4HomePage.getPulseEmailFromUI();
		bizPrefix = bizPrefixFromUI+randomNumber;
		comPrefix = comPrefixFromUI+randomNumber1;
		pulseEmail = pulseEmailFromUI+randomNumber2;
		nscore4HomePage.enterPrefixAccordingToUrlType("com",comPrefix);
		nscore4HomePage.enterPrefixAccordingToUrlType("biz",bizPrefix);
		nscore4HomePage.enterPulseEmail(pulseEmail);
		nscore4HomePage.clickSaveChangesBtn();
		successMessage = nscore4HomePage.getUpdationMessage();
		s_assert.assertTrue(successMessage.contains("See below to make sure your sites and email saved correctly"), "Expected success message should contains See below to make sure your sites and email saved correctly but actual on UI is "+successMessage);
		nscore4HomePage.refreshPage();
		comPrefixAfterUpdate = nscore4HomePage.getPrefixFromUrl("com");
		bizPrefixAfterUpdate = nscore4HomePage.getPrefixFromUrl("biz");
		pulseEmailAfterUpdate = nscore4HomePage.getPulseEmailFromUI();
		s_assert.assertTrue(comPrefixAfterUpdate.contains(comPrefix), "Expected com prefix is "+comPrefix+" but actual on UI is "+comPrefixAfterUpdate);
		s_assert.assertTrue(bizPrefixAfterUpdate.contains(bizPrefix), "Expected biz prefix is "+bizPrefix+" but actual on UI is "+bizPrefixAfterUpdate);
		s_assert.assertTrue(pulseEmailAfterUpdate.contains(pulseEmail), "Expected pulse email is "+pulseEmail+" but actual on UI is "+pulseEmailAfterUpdate);
		// revert the updations
		nscore4HomePage.enterPrefixAccordingToUrlType("com",comPrefix);
		nscore4HomePage.enterPrefixAccordingToUrlType("biz",bizPrefix);
		nscore4HomePage.enterPulseEmail(pulseEmail);
		nscore4HomePage.clickSaveChangesBtn();
		successMessage = nscore4HomePage.getUpdationMessage();
		s_assert.assertTrue(successMessage.contains("See below to make sure your sites and email saved correctly"), "Expected success message after revert the changes should contains See below to make sure your sites and email saved correctly but actual on UI is "+successMessage);
		s_assert.assertAll();
	}

	//QTest ID TC-280 NSC4_AccountsTab_DisbursementProfilesNew
	@Test
	public void testDisbursementProfilesNew_280(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String accountNumber = null;
		String namePayable = "rfTestUser"+randomNumber;
		String addressLine1 = TestConstantsRFL.ADDRESS_LINE_1_US;
		String city = TestConstantsRFL.CITY_US;
		String state = TestConstantsRFL.POSTAL_CODE_US;
		String postalCode = TestConstantsRFL.POSTAL_CODE_US;
		String namePayableToAfterUpdate = null;
		String address1AfterUpdate = null;
		String cityAfterUpdate = null;
		String stateAfterUpdate = null;
		String postalCodeAfterUpdate = null;
		String successMessage = null;
		accountNumber = "805910";//(String) getValueFromQueryResult(randomConsultantAccountList, "AccountNumber");	
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);
		nscore4HomePage.clickLinkInSidePanel("Disbursement Profiles");
		nscore4HomePage.checkUseAddressOfRecordChkBox();
		nscore4HomePage.enterShippingDetailsForDisbursementProfile(namePayable, addressLine1, city, state, postalCode);
		nscore4HomePage.clickSaveChangesBtnForDisbursementProfile();
		successMessage = nscore4HomePage.getUpdationMessage();
		s_assert.assertTrue(successMessage.contains("Your disbursement profile(s) were saved successfully!"), "Expected success message should contains Your disbursement profile(s) were saved successfully! but actual on UI is "+successMessage);
		nscore4HomePage.refreshPage();
		namePayableToAfterUpdate = nscore4HomePage.getNameOfPayAbleTo();
		address1AfterUpdate = nscore4HomePage.getAddress1OfDisbursementProfile();
		cityAfterUpdate = nscore4HomePage.getCityOfDisbursementProfile();
		stateAfterUpdate = nscore4HomePage.getStateOfDisbursementProfile();
		postalCodeAfterUpdate = nscore4HomePage.getPostalCodeOfDisbursementProfile();
		//assert the changes
		s_assert.assertTrue(namePayableToAfterUpdate.contains(namePayable), "Expected name to pay is "+namePayable+" Actual on UI is "+namePayableToAfterUpdate);
		s_assert.assertTrue(address1AfterUpdate.contains(addressLine1), "Expected addressLine1 is "+addressLine1+" Actual on UI is "+address1AfterUpdate);
		s_assert.assertTrue(cityAfterUpdate.contains(city), "Expected city is "+city+" Actual on UI is "+cityAfterUpdate);
		s_assert.assertTrue(stateAfterUpdate.contains(state), "Expected state is "+state+" Actual on UI is "+stateAfterUpdate);
		s_assert.assertTrue(postalCodeAfterUpdate.contains(postalCode), "Expected postalCode is "+postalCode+" Actual on UI is "+postalCodeAfterUpdate);
		nscore4HomePage.checkUseAddressOfRecordChkBox();
		nscore4HomePage.clickSaveChangesBtnForDisbursementProfile();
		successMessage = nscore4HomePage.getUpdationMessage();
		s_assert.assertTrue(successMessage.contains("Your disbursement profile(s) were saved successfully!"), "Expected success message after revert the details should contains Your disbursement profile(s) were saved successfully! but actual on UI is "+successMessage);
		s_assert.assertAll();
	}

	//QTest ID TC-292 NSC4_ProductsTab_ CategoryTrees
	@Test
	public void testProductsTabCategoryTrees_292(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber1 = CommonUtils.getRandomNum(10000, 1000000);
		String treeName = TestConstantsRFL.FIRST_NAME+randomNumber;
		String subTreeName = TestConstants.FIRST_NAME+randomNumber1;
		nscore4HomePage.clickTab("Products");
		nscore4ProductsTabPage.clickLinkInSidePanel("Category Trees");
		nscore4ProductsTabPage.clickCreateANewCategoryTree();
		nscore4ProductsTabPage.enterTreeNameAndClickCreateTreeBtn(treeName);
		s_assert.assertTrue(nscore4ProductsTabPage.verifyConfirmationMessage(), "Expected confirmation message is not displayed");
		nscore4ProductsTabPage.enterLanguageNameAndClickSave(subTreeName);
		s_assert.assertTrue(nscore4ProductsTabPage.isSubTreeCreatedUnderTree(), "Expected subtree is not created");
		nscore4ProductsTabPage.clickLinkInSidePanel("Category Trees");
		s_assert.assertTrue(nscore4ProductsTabPage.isNewlyCreatedTreePresent(treeName), "Newly created tree is not present in category trees");
		nscore4ProductsTabPage.checkTitleNameChkBoxInAnnouncementsList(treeName);
		nscore4ProductsTabPage.clickActivateOrDeActivateSelectedLink("Delete Selected");
		nscore4ProductsTabPage.clickOKBtnOfJavaScriptPopUp();
		s_assert.assertFalse(nscore4ProductsTabPage.isNewlyCreatedTreePresent(treeName), "Newly created tree is present after delete in category trees");
		s_assert.assertAll();
	}

	//QTest ID TC-293 NSC4_ProductsTab_ProductsManagement_BrowseProducts
	@Test
	public void testProductsManagementBrowseProducts_293(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String productName = null;
		String txtName = TestConstantsRFL.FIRST_NAME+randomNumber;
		String firstProductNameInList = null;
		nscore4HomePage.clickTab("Products");
		nscore4ProductsTabPage.hoverProductManagementAndClickOption("Browse Products");
		s_assert.assertTrue(nscore4ProductsTabPage.verifyCurrentPage("BrowseProducts"),"User is not redirect to browse products page");
		nscore4ProductsTabPage.chooseProductTypeAndStatus("Kits", "Inactive");
		productName = nscore4ProductsTabPage.getFirstProductNameFromBrowseProducts();
		nscore4ProductsTabPage.selectProductAndClickActivateSelectedLink(productName);
		nscore4ProductsTabPage.clickActivateOrDeActivateSelectedLink("Activate Selected");
		firstProductNameInList = nscore4ProductsTabPage.getFirstProductNameFromBrowseProducts();
		s_assert.assertTrue(nscore4ProductsTabPage.verifyProductBecomeActivated(productName, firstProductNameInList), "Still product is present in inactive list after clicked on deactivate");
		nscore4ProductsTabPage.chooseProductTypeAndStatus("Kits", "Active");
		s_assert.assertTrue(nscore4ProductsTabPage.verifyPageNameOnSitePageList(productName),"Product name "+productName+" is not present in active list");
		nscore4ProductsTabPage.checkTitleNameChkBoxInAnnouncementsList(productName);
		nscore4ProductsTabPage.clickActivateOrDeActivateSelectedLink("Deactivate Selected");
		nscore4ProductsTabPage.chooseProductTypeAndStatus("Kits", "Inactive");
		s_assert.assertTrue(nscore4ProductsTabPage.verifyPageNameOnSitePageList(productName),"Product name "+productName+" is not present in Inactive list");
		nscore4ProductsTabPage.selectARandomProduct();
		nscore4ProductsTabPage.clickSublinkAccordingToLabel("Available In");
		nscore4ProductsTabPage.editProductAvailablityAndSave();
		s_assert.assertTrue(nscore4ProductsTabPage.verifySavedMessagePresent(), "Saved message is not present after edit the product availablity");
		nscore4ProductsTabPage.clickLinkInSidePanel("Product Overview");
		nscore4ProductsTabPage.clickSublinkAccordingToLabel("Pricing");
		nscore4ProductsTabPage.editPricingAndSave();
		s_assert.assertTrue(nscore4ProductsTabPage.verifySavedMessagePresent(), "Saved message is not present after edit the product pricing");
		nscore4ProductsTabPage.clickLinkInSidePanel("Product Overview");
		nscore4ProductsTabPage.clickSublinkAccordingToLabel("Product Details");
		nscore4ProductsTabPage.editProductDetailsAndSave();
		s_assert.assertTrue(nscore4ProductsTabPage.verifySavedMessagePresent(), "Saved message is not present after edit the product details");
		nscore4ProductsTabPage.clickLinkInSidePanel("Product Overview");
		nscore4ProductsTabPage.clickSublinkAccordingToLabel("Product CMS");
		nscore4ProductsTabPage.editCMSDescriptionAndSave();
		s_assert.assertTrue(nscore4ProductsTabPage.verifySavedMessagePresent(), "Saved message is not present after edit the product CMS");
		//edit the Categories
		nscore4ProductsTabPage.clickLinkInSidePanel("Categories");
		nscore4ProductsTabPage.changeCategoriesAndSave("ConsultantsOnly");
		s_assert.assertTrue(nscore4ProductsTabPage.verifySavedMessagePresent(), "Saved message is not present after chnage the categories");
		//edit the relationship
		nscore4ProductsTabPage.clickLinkInSidePanel("Relationships");
		nscore4ProductsTabPage.selectAProductAndRemoveFromRelationShip(productName);
		s_assert.assertTrue(nscore4ProductsTabPage.isRelationPresentInBox(productName), "SKU is present in relationship box after remove");
		//edit the Availability
		nscore4ProductsTabPage.clickLinkInSidePanel("Availability");
		nscore4ProductsTabPage.editProductAvailablityAndSave();
		s_assert.assertTrue(nscore4ProductsTabPage.verifySavedMessagePresent(), "Saved message is not present after edit the product availablity through left panel");
		//edit the Product Properties
		nscore4ProductsTabPage.clickLinkInSidePanel("Product Properties");
		nscore4ProductsTabPage.enterProductPrioritiesANdClickOnSave(txtName);
		s_assert.assertTrue(nscore4ProductsTabPage.verifySavedMessagePresent(), "Saved message is not present after save the product properties");
		s_assert.assertAll();
	}

	// QTest ID TC-248 NSC4_SitesTab_nsCorporate_CorporateCalendarEvents
	@Test
	public void testCorporateCalendarEvents_248(){
		String sites = "Sites";
		String subject = null;
		String monthToSelect = "Oct";
		String yearToSelect = "2020";
		String actualEventSubject = null;
		nscore4HomePage.clickTab(sites);
		nscore4SitesTabPage.clickSubLinkOfCorporate("Calendar Events");

		// Prior Month
		nscore4SitesTabPage.clickAddNewEventLink();
		subject = "For Automation"+CommonUtils.getRandomNum(10000, 1000000);
		nscore4SitesTabPage.enterSubjectForEvent(subject);
		nscore4SitesTabPage.clickCalenderEventStartDate();
		nscore4SitesTabPage.selectEventMonthFromDD(monthToSelect);
		nscore4SitesTabPage.selectFirstDateFromCalendar();
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Event saved successfully"), "Expected saved message is: Event saved successfully but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		nscore4SitesTabPage.selectMonthForCalendarEvent(monthToSelect);
		s_assert.assertTrue(nscore4SitesTabPage.isEventPresentAtCalendar(subject), "Event is not present at calendar ");
		// Prior Year
		nscore4SitesTabPage.clickAddNewEventLink();
		subject = "For Automation"+CommonUtils.getRandomNum(10000, 1000000);
		nscore4SitesTabPage.enterSubjectForEvent(subject);
		nscore4SitesTabPage.clickCalenderEventStartDate();
		nscore4SitesTabPage.selectEventYearFromDD(yearToSelect);
		nscore4SitesTabPage.selectFirstDateFromCalendar();
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Event saved successfully"), "Expected saved message is: Event saved successfully but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		nscore4SitesTabPage.selectYearForCalendarEvent(yearToSelect);
		s_assert.assertTrue(nscore4SitesTabPage.isEventPresentAtCalendar(subject), "Event is not present at calendar ");
		// Today Event
		nscore4SitesTabPage.clickTodayBtn();
		nscore4SitesTabPage.clickAddNewEventLink();
		subject = "For Automation"+CommonUtils.getRandomNum(10000, 1000000);
		nscore4SitesTabPage.enterSubjectForEvent(subject);
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Event saved successfully"), "Expected saved message is: Event saved successfully but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		s_assert.assertTrue(nscore4SitesTabPage.isEventPresentAtCalendar(subject), "Event is not present at calendar ");
		nscore4SitesTabPage.clickEventNamePresentAtCalendar(subject);
		actualEventSubject = nscore4SitesTabPage.getEventSubjectFromEventDetails();
		s_assert.assertTrue(actualEventSubject.contains(subject),"Event Detail is not found as expected");
		nscore4SitesTabPage.clickCancelBtnOnEventDetails();
		// Previous Button
		nscore4SitesTabPage.clickAddNewEventLink();
		subject = "For Automation"+CommonUtils.getRandomNum(10000, 1000000);
		nscore4SitesTabPage.enterSubjectForEvent(subject);
		nscore4SitesTabPage.clickCalenderEventStartDate();
		nscore4SitesTabPage.selectPreviousMonthFromCurrentDate();
		nscore4SitesTabPage.selectFirstDateFromCalendar();
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Event saved successfully"), "Expected saved message is: Event saved successfully but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		nscore4SitesTabPage.clickPreviousBtn();  
		s_assert.assertTrue(nscore4SitesTabPage.isEventPresentAtCalendar(subject), "Event is not present at calendar ");
		// Next Button
		nscore4SitesTabPage.clickAddNewEventLink();
		subject = "For Automation"+CommonUtils.getRandomNum(10000, 1000000);
		nscore4SitesTabPage.enterSubjectForEvent(subject);
		nscore4SitesTabPage.clickCalenderEventStartDate();
		nscore4SitesTabPage.selectNextMonthFromCurrentDate();
		nscore4SitesTabPage.selectFirstDateFromCalendar();
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Event saved successfully"), "Expected saved message is: Event saved successfully but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		nscore4SitesTabPage.clickNextBtn();  
		s_assert.assertTrue(nscore4SitesTabPage.isEventPresentAtCalendar(subject), "Event is not present at calendar ");
		s_assert.assertAll();
	}
	//QTest ID TC-260 NSC4_SitesTab_nsDistributor_BasePWSCalendarEvents
	@Test
	public void testBasePWSCalendarEvents_260(){
		String sites = "Sites";
		String subject = null;
		String monthToSelect = "Oct";
		String yearToSelect = "2020";
		String actualEventSubject = null;
		nscore4HomePage.clickTab(sites);
		nscore4SitesTabPage.clickLinkUnderBasePWS("Calendar Events");

		// Prior Month
		nscore4SitesTabPage.clickAddNewEventLink();
		subject = "For Automation"+CommonUtils.getRandomNum(10000, 1000000);
		nscore4SitesTabPage.enterSubjectForEvent(subject);
		nscore4SitesTabPage.clickCalenderEventStartDate();
		nscore4SitesTabPage.selectEventMonthFromDD(monthToSelect);
		nscore4SitesTabPage.selectFirstDateFromCalendar();
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Event saved successfully"), "Expected saved message is: Event saved successfully but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		nscore4SitesTabPage.selectMonthForCalendarEvent(monthToSelect);
		s_assert.assertTrue(nscore4SitesTabPage.isEventPresentAtCalendar(subject), "Event is not present at calendar ");
		// Prior Year
		nscore4SitesTabPage.clickAddNewEventLink();
		subject = "For Automation"+CommonUtils.getRandomNum(10000, 1000000);
		nscore4SitesTabPage.enterSubjectForEvent(subject);
		nscore4SitesTabPage.clickCalenderEventStartDate();
		nscore4SitesTabPage.selectEventYearFromDD(yearToSelect);
		nscore4SitesTabPage.selectFirstDateFromCalendar();
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Event saved successfully"), "Expected saved message is: Event saved successfully but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		nscore4SitesTabPage.selectYearForCalendarEvent(yearToSelect);
		s_assert.assertTrue(nscore4SitesTabPage.isEventPresentAtCalendar(subject), "Event is not present at calendar ");
		// Today Event
		nscore4SitesTabPage.clickTodayBtn();
		nscore4SitesTabPage.clickAddNewEventLink();
		subject = "For Automation"+CommonUtils.getRandomNum(10000, 1000000);
		nscore4SitesTabPage.enterSubjectForEvent(subject);
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Event saved successfully"), "Expected saved message is: Event saved successfully but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		s_assert.assertTrue(nscore4SitesTabPage.isEventPresentAtCalendar(subject), "Event is not present at calendar ");
		nscore4SitesTabPage.clickEventNamePresentAtCalendar(subject);
		actualEventSubject = nscore4SitesTabPage.getEventSubjectFromEventDetails();
		s_assert.assertTrue(actualEventSubject.contains(subject),"Event Detail is not found as expected");
		nscore4SitesTabPage.clickCancelBtnOnEventDetails();
		// Previous Button
		nscore4SitesTabPage.clickAddNewEventLink();
		subject = "For Automation"+CommonUtils.getRandomNum(10000, 1000000);
		nscore4SitesTabPage.enterSubjectForEvent(subject);
		nscore4SitesTabPage.clickCalenderEventStartDate();
		nscore4SitesTabPage.selectPreviousMonthFromCurrentDate();
		nscore4SitesTabPage.selectFirstDateFromCalendar();
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Event saved successfully"), "Expected saved message is: Event saved successfully but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		nscore4SitesTabPage.clickPreviousBtn();  
		s_assert.assertTrue(nscore4SitesTabPage.isEventPresentAtCalendar(subject), "Event is not present at calendar ");
		// Next Button
		nscore4SitesTabPage.clickAddNewEventLink();
		subject = "For Automation"+CommonUtils.getRandomNum(10000, 1000000);
		nscore4SitesTabPage.enterSubjectForEvent(subject);
		nscore4SitesTabPage.clickCalenderEventStartDate();
		nscore4SitesTabPage.selectNextMonthFromCurrentDate();
		nscore4SitesTabPage.selectFirstDateFromCalendar();
		nscore4SitesTabPage.clickSaveBtn();
		s_assert.assertTrue(nscore4SitesTabPage.getSavedSuccessfullyTxt().contains("Event saved successfully"), "Expected saved message is: Event saved successfully but actual on UI is: "+nscore4SitesTabPage.getSavedSuccessfullyTxt());
		nscore4SitesTabPage.clickNextBtn();  
		s_assert.assertTrue(nscore4SitesTabPage.isEventPresentAtCalendar(subject), "Event is not present at calendar ");
		s_assert.assertAll();
	}

	//QTest ID TC-1942 Payment error message - NSC4
	@Test()
	public void testPaymentErrorMessageNSC4_1942(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newBillingProfileName = "RFAutoNSCore4"+randomNum;
		String lastName = "lN";
		String nameOnCard = "rfAutoUser";
		String attentionCO =newBillingProfileName+" "+lastName;
		String CVV = TestConstantsRFL.SECURITY_CODE;
		String accountNumber = null;
		String SKU = null;
		String emailID=null;

		nscore4OrdersTabPage =new NSCore4OrdersTabPage(driver);
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber"); 
		emailID = (String) getValueFromQueryResult(randomAccountList, "UserName");	

		nscore4HomePage.searchUserOnTheBasisOfEmailAddress(emailID);
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		nscore4HomePage.clickPlaceNewOrderLink();
		SKU= nscore4HomePage.addAndGetProductSKU("10");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
		//nscore4HomePage.addANewBillingProfile(newBillingProfileName, lastName, nameOnCard, TestConstantsRFL.INVALID_CARD_NUMBER, addressLine1, zipCode);
		nscore4HomePage.addPaymentMethod(newBillingProfileName, lastName, nameOnCard, TestConstantsRFL.INVALID_CARD_NUMBER, CVV, attentionCO);
		nscore4HomePage.clickUseAsEnteredbtn();
		nscore4HomePage.clickSavePaymentMethodBtn();
		nscore4HomePage.changeDefaultBillingProfile(attentionCO);
		nscore4OrdersTabPage.clickPaymentApplyLink();
		nscore4OrdersTabPage.clickSubmitOrderBtn();
		s_assert.assertTrue(nscore4HomePage.isErrorMessagePresentOnUI(),"The order could not be submitted: This transaction has been declined due to an invalid account number. is not present on UI");
		s_assert.assertAll();
	}


	// QTest ID TC-1949 Update billing profile CRP template NSC4
	@Test()
	public void testUpdateBillingProfileCRPTemplateNSC4_1949() {
		String emailID = null;
		String accountNumber = null;
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newBillingProfileName = "RFAutoNSCore4"+randomNum;
		String lastNameProfileName = "lN";
		String nameOnCard = "rfAutoUser";
		String cardNumber =  TestConstantsRFL.CARD_NUMBER;
		String addressLine1 = TestConstantsRFL.ADDRESS_LINE1;
		String zipCode = TestConstantsRFL.POSTAL_CODE;
		String CVV = TestConstantsRFL.SECURITY_CODE;
		String billingProfileName=newBillingProfileName+" "+lastNameProfileName;
		String enteredCardNameOnUI=null;
		String billingProfileNameFromUI=null;

		List<Map<String, Object>> randomConsultantList =  null;
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL, RFL_DB);
		emailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");
		accountNumber = (String) getValueFromQueryResult(randomConsultantList, "AccountNumber");

		nscore4HomePage.searchUserOnTheBasisOfEmailAddress(emailID);
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		nscore4HomePage.clickEditCRPTemplate();
		nscore4HomePage.clickEditBillingProfile();
		nscore4HomePage.addANewBillingProfile(newBillingProfileName, lastNameProfileName, nameOnCard, cardNumber,addressLine1,zipCode,CVV);
		nscore4HomePage.clickSavePaymentMethodBtn();
		billingProfileNameFromUI=nscore4HomePage.getBillingProfileName();
		s_assert.assertTrue(billingProfileNameFromUI.contains(billingProfileName),"Expected billing profile name is:"+billingProfileName+" But on UI is: "+billingProfileNameFromUI);
		enteredCardNameOnUI=nscore4HomePage.getBillingProfileEnteredCardName();
		s_assert.assertTrue(enteredCardNameOnUI.contains(nameOnCard),"Expected billing profile name on card is:"+enteredCardNameOnUI+" But on UI is: "+nameOnCard);
		s_assert.assertAll();
	}

	//QTest ID TC-1956 NSC4_Status_active
	@Test
	public void testSearchActiveUser_1956q(){
		List randomUserList = null;
		String firstName = null;
		String lastName = null;
		String accountNumber = null;

		randomUserList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACCOUNT_DETAILS,RFL_DB);
		firstName = (String) getValueFromQueryResult(randomUserList, "FirstName");
		lastName = (String) getValueFromQueryResult(randomUserList, "LastName");
		accountNumber = (String) getValueFromQueryResult(randomUserList, "AccountNumber");

		nscore4HomePage.enterFirstAndLastNameInCorrespondingFields(firstName, lastName);
		nscore4HomePage.selectAccountStatusForSearch("Active");
		nscore4HomePage.clickGoBtnOfSearch();
		s_assert.assertTrue(nscore4HomePage.isFirstAndLastNamePresentinSearchResults(firstName, lastName), "First and last name is not present in search result,when searched with account number");
		s_assert.assertTrue(nscore4HomePage.isCIDPresentinSearchResults(accountNumber), "CID/Account Number is not present in search result,when searched with account number");
		s_assert.assertAll();
	}

	//QTest ID TC-1957 Nsc4_status_inactive
	@Test
	public void testSearchInactiveUser_1957q(){
		List randomUserList = null;
		String firstName = null;
		String lastName = null;
		String accountNumber = null;

		randomUserList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_INACTIVE_ACCOUNT_DETAILS,RFL_DB);
		firstName = (String) getValueFromQueryResult(randomUserList, "FirstName");
		lastName = (String) getValueFromQueryResult(randomUserList, "LastName");
		accountNumber = (String) getValueFromQueryResult(randomUserList, "AccountNumber");

		nscore4HomePage.enterFirstAndLastNameInCorrespondingFields(firstName, lastName);
		nscore4HomePage.selectAccountStatusForSearch("Inactive");
		nscore4HomePage.clickGoBtnOfSearch();
		s_assert.assertTrue(nscore4HomePage.isFirstAndLastNamePresentinSearchResults(firstName, lastName), "First and last name is not present in search result,when searched with account number");
		s_assert.assertTrue(nscore4HomePage.isCIDPresentinSearchResults(accountNumber), "CID/Account Number is not present in search result,when searched with account number");
		s_assert.assertAll();
	}

	//QTest ID TC-1959 NSC4_Browse _Type account_CE
	@Test
	public void testSearchConsultantUser_1959q(){
		List randomUserList = null;
		String firstName = null;
		String lastName = null;
		String accountNumber = null;

		randomUserList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_EMAILID,RFL_DB);
		firstName = (String) getValueFromQueryResult(randomUserList, "FirstName");
		lastName = (String) getValueFromQueryResult(randomUserList, "LastName");
		accountNumber = (String) getValueFromQueryResult(randomUserList, "AccountNumber");

		nscore4HomePage.enterFirstAndLastNameInCorrespondingFields(firstName, lastName);
		nscore4HomePage.selectAccountTypeForSearch("Consultant");
		nscore4HomePage.clickGoBtnOfSearch();
		s_assert.assertTrue(nscore4HomePage.isFirstAndLastNamePresentinSearchResults(firstName, lastName), "First and last name is not present in search result,when searched with account number");
		s_assert.assertTrue(nscore4HomePage.isCIDPresentinSearchResults(accountNumber), "CID/Account Number is not present in search result,when searched with account number");
		s_assert.assertAll();
	}

	//QTest ID TC-1960 NSC4_Browse _Type account_PC
	@Test
	public void testSearchPCUser_1960q(){
		List randomUserList = null;
		String firstName = null;
		String lastName = null;
		String accountNumber = null;

		randomUserList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_PC_EMAILID,RFL_DB);
		firstName = (String) getValueFromQueryResult(randomUserList, "FirstName");
		lastName = (String) getValueFromQueryResult(randomUserList, "LastName");
		accountNumber = (String) getValueFromQueryResult(randomUserList, "AccountNumber");

		nscore4HomePage.enterFirstAndLastNameInCorrespondingFields(firstName, lastName);
		nscore4HomePage.selectAccountTypeForSearch("Preferred Customer");
		nscore4HomePage.clickGoBtnOfSearch();
		s_assert.assertTrue(nscore4HomePage.isFirstAndLastNamePresentinSearchResults(firstName, lastName), "First and last name is not present in search result,when searched with account number");
		s_assert.assertTrue(nscore4HomePage.isCIDPresentinSearchResults(accountNumber), "CID/Account Number is not present in search result,when searched with account number");
		s_assert.assertAll();
	}

	//QTest ID TC-1961 NSC4_Browse _Type account_RC
	@Test
	public void testSearchRCUser_1961q(){
		List randomUserList = null;
		String firstName = null;
		String lastName = null;
		String accountNumber = null;

		randomUserList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_RC_EMAILID,RFL_DB);
		firstName = (String) getValueFromQueryResult(randomUserList, "FirstName");
		lastName = (String) getValueFromQueryResult(randomUserList, "LastName");
		accountNumber = (String) getValueFromQueryResult(randomUserList, "AccountNumber");

		nscore4HomePage.enterFirstAndLastNameInCorrespondingFields(firstName, lastName);
		nscore4HomePage.selectAccountTypeForSearch("Retail Customer");
		nscore4HomePage.clickGoBtnOfSearch();
		s_assert.assertTrue(nscore4HomePage.isFirstAndLastNamePresentinSearchResults(firstName, lastName), "First and last name is not present in search result,when searched with account number");
		s_assert.assertTrue(nscore4HomePage.isCIDPresentinSearchResults(accountNumber), "CID/Account Number is not present in search result,when searched with account number");
		s_assert.assertAll();
	}

	//QTest ID TC-2463 NScore4> Create Adhoc order for the Consultant
	@Test()
	public void testNScore4CreateAdhocOrderForTheConsultant_2463q(){
		String accountNumber = null;
		String emailID = null;
		String SKU = null;
		nscore4OrdersTabPage =new NSCore4OrdersTabPage(driver);
		logger.info("DB is "+RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomConsultantAccountList, "AccountNumber"); 
		emailID = (String) getValueFromQueryResult(randomConsultantAccountList, "UserName");
		logger.info("Account number from DB is "+accountNumber);

		nscore4HomePage.searchUserOnTheBasisOfEmailAddress(emailID);
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		nscore4HomePage.clickPlaceNewOrderLink();
		//click 'Place-New-Order' link
		nscore4HomePage.clickPlaceNewOrderLink();
		SKU= nscore4HomePage.addAndGetProductSKU("10");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
		nscore4OrdersTabPage.clickPaymentApplyLink();
		nscore4OrdersTabPage.clickSubmitOrderBtn();
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Products"),"Product information not present as expected");
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Shipments"),"Shipments information not present as expected");
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Payment"),"Payment information not present as expected");
		s_assert.assertTrue(nscore4OrdersTabPage.validateOrderStatusAfterSubmitOrder(),"Order is not submitted after submit order");
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderDetailPagePresent(),"This is not order details page");
		s_assert.assertAll();
	}



	// QTest ID TC-2464 NScore4> Create Adhoc order for the RC
	@Test()
	public void testNScore4CreateAdhocOrderForTheConsultant_2464q() {
		String accountNumber = null;
		String emailID = null;
		String SKU = null;
		logger.info("DB is " + RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber");
		emailID = (String) getValueFromQueryResult(randomAccountList, "UserName");
		logger.info("Account number from DB is " + accountNumber);

		nscore4HomePage.searchUserOnTheBasisOfEmailAddress(emailID);
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		nscore4HomePage.clickPlaceNewOrderLink();
		// click 'Place-New-Order' link
		nscore4HomePage.clickPlaceNewOrderLink();
		SKU = nscore4HomePage.addAndGetProductSKU("10");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU),"SKU = " + SKU + " is not added to the Autoship Order");
		nscore4OrdersTabPage.clickPaymentApplyLink();
		nscore4OrdersTabPage.clickSubmitOrderBtn();
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Products"),"Product information not present as expected");
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Shipments"),"Shipments information not present as expected");
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Payment"),"Payment information not present as expected");
		s_assert.assertTrue(nscore4OrdersTabPage.validateOrderStatusAfterSubmitOrder(),"Order is not submitted after submit order");
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderDetailPagePresent(), "This is not order details page");
		s_assert.assertAll();
	}

	// QTest ID TC-2465 NScore4> Create Adhoc order for the PC
	@Test()
	public void testNScore4CreateAdhocOrderForThePC_2465q() {
		String accountNumber = null;
		String emailID = null;
		String SKU = null;
		logger.info("DB is " + RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomPCAccountList, "AccountNumber");
		emailID = (String) getValueFromQueryResult(randomPCAccountList, "EmailAddress");
		logger.info("Account number from DB is " + accountNumber);

		nscore4HomePage.searchUserOnTheBasisOfEmailAddress(emailID);
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		nscore4HomePage.clickPlaceNewOrderLink();
		// click 'Place-New-Order' link
		nscore4HomePage.clickPlaceNewOrderLink();
		SKU = nscore4HomePage.addAndGetProductSKU("10");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU),"SKU = " + SKU + " is not added to the Autoship Order");
		nscore4OrdersTabPage.clickPaymentApplyLink();
		nscore4OrdersTabPage.clickSubmitOrderBtn();
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Products"),"Product information not present as expected");
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Shipments"),"Shipments information not present as expected");
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderInformationPresent("Payment"),"Payment information not present as expected");
		s_assert.assertTrue(nscore4OrdersTabPage.validateOrderStatusAfterSubmitOrder(),"Order is not submitted after submit order");
		s_assert.assertTrue(nscore4OrdersTabPage.isOrderDetailPagePresent(), "This is not order details page");
		s_assert.assertAll();
	}

	//QTest ID TC-294 NSC4_ProductsTab_ ProductsManagement_AddNewProduct
	@Test(priority=3)
	public void testNSC4ProductsTabProductsManagementAddNewProduct_294(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber1 = CommonUtils.getRandomNum(10, 100);
		String sku=TestConstantsRFL.SKU_ID+randomNumber1;
		String name= TestConstantsRFL.PRODCUT_NAME+randomNumber1;
		String value= ""+1;
		List<Map<String, Object>> randomSKUList =  null;
		String SKU=null;
		String retail=""+randomNumber1;
		String pc=""+randomNumber1;
		String consultant=""+randomNumber1;
		randomSKUList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_SKU,RFL_DB);
		SKU=(String) getValueFromQueryResult(randomSKUList, "SKU"); 
		nscore4HomePage.clickProductsTab();
		nscore4HomePage.mouseHoverToProductsManagementTab("Add a New Product");

		nscore4HomePage.AddNewProductDetailsAndClickSave(sku,name);
		//s_assert.assertTrue(nscore4HomePage.verifySuccessMessageForAddNewProduct(), "Details Saved success message is not displayed ");
		s_assert.assertTrue(nscore4HomePage.verifyProductOverviewPageisDisplayed().contains(name.toLowerCase().trim()), "Product Overview Page is not displayed ");
		nscore4HomePage.clickLinkInSidePanel("Details");
		nscore4HomePage.UpdateSortOredrValueAndClickSave(value);
		s_assert.assertTrue(nscore4HomePage.verifySuccessMessageForDetails(), "Details Saved success message is not displayed ");
		nscore4HomePage.clickPricingTab();
		nscore4HomePage.UpdatePricingDetails(retail,pc,consultant);
		nscore4HomePage.clickCMSTab();
		nscore4HomePage.EnterShortDescriptionCMSTabAndClickSave();
		s_assert.assertTrue(nscore4HomePage.verifySuccessMessageForProductDescription(), "Product Decription success message is not displayed ");
		nscore4HomePage.clickCategoriesTab();
		nscore4HomePage.SelectCategoryOnCategoriesTabAndClickSave();
		s_assert.assertTrue(nscore4HomePage.verifySuccessMessageForCategorySelected(), "Category selection success message is not displayed ");
		nscore4HomePage.clickRelationshipsTab();
		nscore4HomePage.clickSearchAProduct(SKU);
		nscore4HomePage.selectRelationshipTypeAndClickArrowButton();
		s_assert.assertTrue(nscore4HomePage.verifyProductreflectedInCurrentRelationshipSection().contains(SKU), "Product is not reflected in Current relationship section ");

		nscore4HomePage.clickAvailabilityTab();
		nscore4HomePage.SelectCatalogAndClickSave();
		s_assert.assertTrue(nscore4HomePage.verifySuccessMessageForCatalogSaved(), "Category saved method is not displayed ");
		nscore4HomePage.clickProductPropertiesTab();
		nscore4HomePage.selectallCartsAndClickSave();
		s_assert.assertTrue(nscore4HomePage.verifySuccessMessageForProductPropertiesSaved(), "Product properties saved method is not displayed");

		s_assert.assertAll();

	}



	//QTest ID TC-514 NSC4_AccountsTab_Full OrderHistoryPlaceNewOrder
	@Test
	public void testNSC4AccountsTabFullOrderHistoryPlaceNewOrder_514(){
		String accountNumber = null;
		String retail;
		String pc;
		String consultant;
		String orderNum=null;
		List<Map<String, Object>> randomSKUList =  null;
		String SKU=null;
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber"); 
		randomSKUList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_SKU,RFL_DB);
		SKU=(String) getValueFromQueryResult(randomSKUList, "SKU"); 
		logger.info("Account number from DB is "+accountNumber);
		randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL, accountNumber),RFL_DB);
		nscore4HomePage.clickAccountsTab();
		nscore4HomePage.clickSearchConsultant(accountNumber);
		nscore4HomePage.clickSearchIcon();	
		nscore4HomePage.clickConsultantId(accountNumber);
		nscore4HomePage.clickPlaceNewOrderLink();
		nscore4HomePage.clickSearchSKU(SKU);
		nscore4HomePage.clickAddToOrder();
		nscore4HomePage.clickApplyPaymentButton();
		nscore4HomePage.clickSubmitOrderBtn();
		s_assert.assertTrue(nscore4HomePage.verifyProductNamePresentOnOrderDetailsPage(SKU), "Details Saved success message is not displayed ");
		orderNum  = nscore4HomePage.getOrderName();
		nscore4HomePage.clickConsultantNameOnOrderDetailsPage();
		nscore4HomePage.clickSearchOrderId(orderNum);
		s_assert.assertTrue(nscore4HomePage.verifyOrderDisplayedInSearchResult(orderNum), "Order is Not displayed in the searched results");
		s_assert.assertAll();
	}



	//QTest ID TC-2470 Update billing profile CRP template NSC4
	@Test
	public void testCRPTemplateEditPaymentProfile_2470() {
		String emailID = null;
		String accountNumber = null;
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newPaymentProfileName = "RFAutoNSCore4"+randomNum;
		String lastNameProfileName = "lN";
		String nameOnCard = "rfAutoUser";
		String cardNumber =  TestConstantsRFL.CARD_NUMBER;
		String addressLine1 = TestConstantsRFL.ADDRESS_LINE1;
		String zipCode = TestConstantsRFL.POSTAL_CODE;
		String CVV = TestConstantsRFL.SECURITY_CODE;
		String PaymentProfileName=newPaymentProfileName+" "+lastNameProfileName;
		String enteredCardNameOnUI=null;
		String paymentProfileNameFromUI=null;

		List<Map<String, Object>> randomConsultantList =  null;
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL, RFL_DB);
		emailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");
		accountNumber = (String) getValueFromQueryResult(randomConsultantList, "AccountNumber");

		nscore4HomePage.clickSearchConsultant(accountNumber);
		nscore4HomePage.clickSearchIcon();	
		//nscore4HomePage.clickConsultantId(accountNumber);
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		nscore4HomePage.clickEditCRPTemplate();
		nscore4HomePage.clickEditPaymentProfile();
		nscore4HomePage.editPaymentProfile(newPaymentProfileName, lastNameProfileName, nameOnCard, cardNumber,CVV);
		nscore4HomePage.clickSavePaymentMethodBtn();
		nscore4HomePage.acceptQASPopup();
		paymentProfileNameFromUI=nscore4HomePage.getBillingProfileName();
		s_assert.assertTrue(paymentProfileNameFromUI.contains(PaymentProfileName),"Expected payment profile name is:"+PaymentProfileName+" But on UI is: "+paymentProfileNameFromUI);
		enteredCardNameOnUI=nscore4HomePage.getBillingProfileEnteredCardName();
		s_assert.assertTrue(enteredCardNameOnUI.contains(nameOnCard),"Expected payment profile name on card is:"+enteredCardNameOnUI+" But on UI is: "+nameOnCard);
		s_assert.assertAll();

	}

	// QTest ID TC-2471 Pulse template > Edit payment Profile
	@Test
	public void testPulseTemplateEditPaymentProfile_2471() {
		String emailID = null;
		String accountNumber = null;
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newPaymentProfileName = "RFAutoNSCore4"+randomNum;
		String lastNameProfileName = "lN";
		String nameOnCard = "rfAutoUser";
		String cardNumber =  TestConstantsRFL.CARD_NUMBER;
		String CVV = TestConstantsRFL.SECURITY_CODE;
		String PaymentProfileName=newPaymentProfileName+" "+lastNameProfileName;
		String enteredCardNameOnUI=null;
		String paymentProfileNameFromUI=null;

		List<Map<String, Object>> randomConsultantList =  null;
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL, RFL_DB);
		emailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");
		accountNumber = (String) getValueFromQueryResult(randomConsultantList, "AccountNumber");

		nscore4HomePage.clickSearchConsultant(accountNumber);
		nscore4HomePage.clickSearchIcon();	
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		nscore4HomePage.clickEditPulseTemplate();
		nscore4HomePage.clickEditPaymentProfile();
		nscore4HomePage.editPaymentProfile(newPaymentProfileName, lastNameProfileName, nameOnCard, cardNumber,CVV);
		nscore4HomePage.clickSavePaymentMethodBtn();
		nscore4HomePage.acceptQASPopup();
		paymentProfileNameFromUI=nscore4HomePage.getBillingProfileName();
		s_assert.assertTrue(paymentProfileNameFromUI.contains(PaymentProfileName),"Expected payment profile name is:"+PaymentProfileName+" But on UI is: "+paymentProfileNameFromUI);
		enteredCardNameOnUI=nscore4HomePage.getBillingProfileEnteredCardName();
		s_assert.assertTrue(enteredCardNameOnUI.contains(nameOnCard),"Expected payment profile name on card is:"+enteredCardNameOnUI+" But on UI is: "+nameOnCard);
		s_assert.assertAll();

	}

	// QTest ID TC-2473 CRP template > Add Shipping Profile
	@Test
	public void testCRPTemplateAddShippingProfile_2473() {
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newShippingProfileName = "newSP"+randomNum;
		String attentionCO ="SP";
		String addressLine1 =TestConstantsRFL.ADDRESS_LINE_1_US;
		String zipCode= TestConstantsRFL.POSTAL_CODE_US;
		String emailID = null;
		String accountNumber = null;
		List<Map<String, Object>> randomConsultantList =  null;
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL, RFL_DB);
		emailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");
		accountNumber = (String) getValueFromQueryResult(randomConsultantList, "AccountNumber");
		nscore4HomePage.clickSearchConsultant(accountNumber);
		nscore4HomePage.clickSearchIcon();	
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		nscore4HomePage.clickEditCRPTemplate();
		nscore4HomePage.clickAddNewShippingProfileLink();
		nscore4HomePage.addANewShippingProfile(newShippingProfileName, attentionCO, addressLine1, zipCode);
		//click 'SAVE ADDRESS BTN'
		nscore4HomePage.clickSaveAddressBtn();
		nscore4HomePage.acceptQASPopup();
		//			nscore4HomePage.refreshPage();
		//verify newly created shipping profile created?
		s_assert.assertTrue(nscore4HomePage.isNewlyCreatedShippingProfilePresent(newShippingProfileName),"Newly created Shipping Profile is not Present");
		s_assert.assertAll();
	}

	// QTest ID TC-2472 CRP template > Add payment Profile
	@Test
	public void testCRPTemplateAddPaymentProfile_2472() {
		String emailID = null;
		String accountNumber = null;
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newPaymentProfileName = "RFAutoNSCore4"+randomNum;
		String lastNameProfileName = "lN";
		String nameOnCard = "rfAutoUser";
		String lastName = "lN";
		String attentionName = newPaymentProfileName+" "+lastNameProfileName;
		String cardNumber =  TestConstantsRFL.CARD_NUMBER;
		String addressLine1 = TestConstantsRFL.ADDRESS_LINE1;
		String zipCode = TestConstantsRFL.POSTAL_CODE;
		String CVV = TestConstantsRFL.SECURITY_CODE;
		String PaymentProfileName=newPaymentProfileName+" "+lastNameProfileName;
		String paymentProfileNameFromUI=null;
		List<Map<String, Object>> randomConsultantList =  null;
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL, RFL_DB);
		emailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");
		accountNumber = (String) getValueFromQueryResult(randomConsultantList, "AccountNumber");

		nscore4HomePage.clickSearchConsultant(accountNumber);
		nscore4HomePage.clickSearchIcon();	
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		nscore4HomePage.clickEditCRPTemplate();
		nscore4HomePage.addPaymentMethod(newPaymentProfileName, lastName, nameOnCard, cardNumber,CVV,attentionName);
		nscore4HomePage.clickSavePaymentMethodBtn();
		nscore4HomePage.changeDefaultBillingProfile(PaymentProfileName);
		paymentProfileNameFromUI=nscore4HomePage.getBillingProfileName();
		s_assert.assertTrue(paymentProfileNameFromUI.contains(PaymentProfileName),"Expected payment profile name is:"+PaymentProfileName+" But on UI is: "+paymentProfileNameFromUI);
		nscore4HomePage.clickSaveTemplate();
		s_assert.assertAll();

	}	

	//QTest ID TC-1963 NSC4_Browse by market
	@Test()
	public void testBrowseBymarket_1963(){
		String firstName = null;
		String lastName = null;
		String accountNumber = null;
		List<Map<String, Object>> randomConsultantAddressList =  null;
		String stateFromDB=null;
		String emailID=null;

		logger.info("DB is "+RFL_DB);
		randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_EMAILID,RFL_DB);
		emailID = (String) getValueFromQueryResult(randomAccountList, "EmailAddress");
		firstName = (String) getValueFromQueryResult(randomAccountList, "FirstName");	
		lastName = (String) getValueFromQueryResult(randomAccountList, "LastName");	
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber");	

		randomConsultantAddressList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_ACCOUNT_ADDRESS_DETAILS_FOR_ACCOUNT_INFO_QUERY_RFL,emailID), RFL_DB);
		stateFromDB = (String) getValueFromQueryResult(randomConsultantAddressList, "State");

		nscore4HomePage.selectByLocation(stateFromDB);
		nscore4HomePage.enterFirstName(firstName);
		nscore4HomePage.enterLastName(lastName);
		nscore4HomePage.clickGoBtnOfSearch();
		s_assert.assertTrue(nscore4HomePage.isSearchResultContainsFirstAndLastName(accountNumber,firstName, lastName),"First and last name not present in search results");
		s_assert.assertAll();
	}


	// QTest ID TC-1951 Update billing profile Pulse template NSC4
	@Test
	public void testUpdateBillingProfilePulseTemplateNSC4_1951q() {
		String emailID = null;
		String accountNumber = null;
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newPaymentProfileName = "RFAutoNSCore4"+randomNum;
		String lastNameProfileName = "lN";
		String nameOnCard = "rfAutoUser";
		String cardNumber =  TestConstantsRFL.CARD_NUMBER;
		String CVV = TestConstantsRFL.SECURITY_CODE;
		String PaymentProfileName=newPaymentProfileName+" "+lastNameProfileName;
		String enteredCardNameOnUI=null;
		String paymentProfileNameFromUI=null;

		List<Map<String, Object>> randomConsultantList =  null;
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL, RFL_DB);
		emailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");
		accountNumber = (String) getValueFromQueryResult(randomConsultantList, "AccountNumber");

		nscore4HomePage.clickSearchConsultant(accountNumber);
		nscore4HomePage.clickSearchIcon();	
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		nscore4HomePage.clickEditPulseTemplate();
		nscore4HomePage.changeDefaultBillingProfile(1);
		nscore4HomePage.clickEditPaymentProfile();
		nscore4HomePage.editPaymentProfile(newPaymentProfileName, lastNameProfileName, nameOnCard, cardNumber,CVV);
		nscore4HomePage.clickSavePaymentMethodBtn();
		nscore4HomePage.acceptQASPopup();
		paymentProfileNameFromUI=nscore4HomePage.getBillingProfileName();
		s_assert.assertTrue(paymentProfileNameFromUI.contains(PaymentProfileName),"Expected payment profile name is:"+PaymentProfileName+" But on UI is: "+paymentProfileNameFromUI);
		enteredCardNameOnUI=nscore4HomePage.getBillingProfileEnteredCardName();
		s_assert.assertTrue(enteredCardNameOnUI.contains(nameOnCard),"Expected payment profile name on card is:"+enteredCardNameOnUI+" But on UI is: "+nameOnCard);
		s_assert.assertAll();

	}

	// QTest ID TC-1952 Save new billing profile Pulse template NSC4
	@Test
	public void testSaveNewBillingProfilePulseTemplateNSC4_1952q() {
		String emailID = null;
		String accountNumber = null;
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newPaymentProfileName = "RFAutoNSCore4"+randomNum;
		String lastNameProfileName = "lN";
		String nameOnCard = "rfAutoUser";
		String cardNumber =  TestConstantsRFL.CARD_NUMBER;
		String CVV = TestConstantsRFL.SECURITY_CODE;
		String PaymentProfileName=newPaymentProfileName+" "+lastNameProfileName;
		String attentionName = "RFAutoNSCore4"+randomNum;
		String enteredCardNameOnUI=null;
		String paymentProfileNameFromUI=null;

		List<Map<String, Object>> randomConsultantList =  null;
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL, RFL_DB);
		emailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");
		accountNumber = (String) getValueFromQueryResult(randomConsultantList, "AccountNumber");

		nscore4HomePage.clickSearchConsultant(accountNumber);
		nscore4HomePage.clickSearchIcon();	
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		nscore4HomePage.clickEditPulseTemplate();

		nscore4HomePage.addPaymentMethod(newPaymentProfileName, lastNameProfileName, nameOnCard, cardNumber,CVV,attentionName);
		nscore4HomePage.clickSavePaymentMethodBtn();
		nscore4HomePage.changeDefaultBillingProfile(PaymentProfileName);
		nscore4HomePage.clickSaveTemplate();
		nscore4HomePage.clickCustomerNameOnOrderDetailsPage(accountNumber);
		nscore4HomePage.clickEditPulseTemplate();

		paymentProfileNameFromUI=nscore4HomePage.getBillingProfileName();
		enteredCardNameOnUI=nscore4HomePage.getBillingProfileEnteredCardName();
		s_assert.assertTrue(paymentProfileNameFromUI.contains(PaymentProfileName),"Expected profile name is "+PaymentProfileName+" But Actual on UI is:"+paymentProfileNameFromUI);
		s_assert.assertTrue(enteredCardNameOnUI.contains(nameOnCard),"Expected profile card name is "+nameOnCard+" But Actual on UI is:"+enteredCardNameOnUI);
		s_assert.assertAll();
	}


	//QTest ID TC-1962 Nsc4_browse by Location
	@Test()
	public void testNsc4BrowseByLocation_1962(){
		String firstName = null;
		String lastName = null;
		String accountNumber = null;
		List<Map<String, Object>> randomConsultantAddressList =  null;
		String stateFromDB=null;
		String emailID=null;

		logger.info("DB is "+RFL_DB);
		randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_EMAILID,RFL_DB);
		emailID = (String) getValueFromQueryResult(randomAccountList, "EmailAddress");
		firstName = (String) getValueFromQueryResult(randomAccountList, "FirstName"); 
		lastName = (String) getValueFromQueryResult(randomAccountList, "LastName"); 
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber"); 

		randomConsultantAddressList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_ACCOUNT_ADDRESS_DETAILS_FOR_ACCOUNT_INFO_QUERY_RFL,emailID), RFL_DB);
		stateFromDB = (String) getValueFromQueryResult(randomConsultantAddressList, "State");

		nscore4HomePage.selectByLocation(stateFromDB);
		nscore4HomePage.enterFirstName(firstName);
		nscore4HomePage.enterLastName(lastName);
		nscore4HomePage.clickGoBtnOfSearch();
		s_assert.assertTrue(nscore4HomePage.isSearchResultContainsFirstAndLastName(accountNumber,firstName, lastName),"First and last name not present in search results");
		s_assert.assertAll();
	}

	// QTest ID TC-1953 Update billing profile PC Autoship template NSC4
	@Test()
	public void testUpdateBillingProfilePCAutoshipTemplateNSC4_1953() {
		String emailID = null;
		String accountNumber = null;
		String paymentProfileNameFromUI = null;
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String newPaymentProfileName = "RFAutoNSCore4"+randomNum;
		String newBillingProfileName = "RFAutoNSCore4"+randomNumber;
		String lastNameProfileName = "lN";
		String PaymentProfileName=newPaymentProfileName+" "+lastNameProfileName;
		String nameOnCard = "rfAutoUser";
		String attentionName = newBillingProfileName+lastNameProfileName;
		String cardNumber =  TestConstantsRFL.CARD_NUMBER;
		String CVV = TestConstantsRFL.SECURITY_CODE;
		String enteredCardNameOnUI=null;
		List<Map<String, Object>> randomPCAccountList =  null;
		randomPCAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_DETAILS_RFL,RFL_DB);
		emailID = (String) getValueFromQueryResult(randomPCAccountList, "EmailAddress");
		accountNumber = (String) getValueFromQueryResult(randomPCAccountList, "AccountNumber");
		nscore4HomePage.searchUserOnTheBasisOfEmailAddress(emailID);
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		nscore4HomePage.clickEditPCAutoshipTemplate();
		nscore4HomePage.addPaymentMethod(newBillingProfileName, lastNameProfileName, nameOnCard, cardNumber,CVV,attentionName);
		nscore4HomePage.clickSavePaymentMethodBtn();
		nscore4HomePage.changeDefaultBillingProfile(1);
		nscore4HomePage.clickEditPaymentProfile();
		nscore4HomePage.editPaymentProfile(newPaymentProfileName, lastNameProfileName, nameOnCard, cardNumber,CVV);
		nscore4HomePage.clickSavePaymentMethodBtn();
		nscore4HomePage.acceptQASPopup();
		paymentProfileNameFromUI=nscore4HomePage.getBillingProfileName();
		s_assert.assertTrue(paymentProfileNameFromUI.contains(PaymentProfileName),"Expected payment profile name is:"+PaymentProfileName+" But on UI is: "+paymentProfileNameFromUI);
		enteredCardNameOnUI=nscore4HomePage.getBillingProfileEnteredCardName();
		s_assert.assertTrue(enteredCardNameOnUI.contains(nameOnCard),"Expected payment profile name on card is:"+enteredCardNameOnUI+" But on UI is: "+nameOnCard);
		s_assert.assertAll();
	}

	// QTest ID TC-2475 CRP template > Add Few Items
	@Test
	public void testCRPTemplateAddFewItems_2475() {
		String accountNumber=null;
		List<Map<String, Object>> randomSKUList=null;
		String SKU=null;
		List<Map<String, Object>> randomConsultantList =  null;
		logger.info("Account number from DB is "+accountNumber);
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL, RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomConsultantList, "AccountNumber"); 
		randomSKUList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_SKU,RFL_DB);
		SKU=(String) getValueFromQueryResult(randomSKUList, "SKU"); 
		nscore4HomePage.clickSearchConsultant(accountNumber);
		nscore4HomePage.clickSearchIcon();	
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		nscore4HomePage.clickEditCRPTemplate();
		nscore4HomePage.clickSearchSKU(SKU);
		nscore4HomePage.clickAddToOrder();
		nscore4HomePage.clickSaveAutoshipTemplate();
		s_assert.assertTrue(nscore4HomePage.isNewlyAddedSKUDisplayedOnCRPTemplate(SKU),"newly added sku is not present");
		s_assert.assertAll();

	}


	//QTest ID TC-271 NSC4_AccountsTab_OverviewProxyLinks
	@Test
	public void testNSC4AccountsTabOverviewProxyLinks_271(){

		String accountNumber=null;
		randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL, accountNumber),RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.clickAccountsTab();
		nscore4HomePage.clickSearchConsultant(accountNumber);
		nscore4HomePage.clickSearchIcon();	
		nscore4HomePage.clickConsultantId(accountNumber);
		nscore4HomePage.clickProxyLink("Product Focus");
		nscore4HomePage.switchToSecondWindow();
		s_assert.assertTrue(driver.getCurrentUrl().contains("myrfo"+driver.getEnvironment()+".com"), "User is not on dot com pws after clicking product focus proxy link.");
		s_assert.assertTrue(nscore4HomePage.verifyUserIsLoggedIn(),"Log out link is not present");
		nscore4HomePage.switchToPreviousTab();
		nscore4HomePage.clickProxyLink("Business Focus");
		nscore4HomePage.switchToSecondWindow();
		s_assert.assertTrue(driver.getCurrentUrl().contains("myrfo"+driver.getEnvironment()+".biz"), "User is not on dot biz pws after clicking business focus proxy link.");
		s_assert.assertTrue(nscore4HomePage.verifyUserIsLoggedIn(),"Log out link is not present");
		nscore4HomePage.switchToPreviousTab();
		nscore4HomePage.clickProxyLink("Pulse");
		nscore4HomePage.switchToSecondWindow();
		nscore4HomePage.dismissPulsePopup();
		s_assert.assertTrue(driver.getCurrentUrl().contains("pulserfo."+driver.getEnvironment()+".rodanandfields"+".com"), "User is not on pulse after clicking pulse proxy link.");
		s_assert.assertTrue(nscore4HomePage.verifyUserIsLoggedIn(),"Log out link is not present");
		nscore4HomePage.switchToPreviousTab();
		nscore4HomePage.clickProxyLink("Corporate");
		nscore4HomePage.switchToSecondWindow();
		nscore4HomePage.clickRenewLater();
		s_assert.assertTrue(driver.getCurrentUrl().contains("corprfo."+driver.getEnvironment()+".rodanandfields"+".com"), "User is not on corp site after clicking corporate proxy link.");
		s_assert.assertTrue(nscore4HomePage.verifyUserIsLoggedIn(),"Log out link is not present");
		nscore4HomePage.switchToPreviousTab();
		s_assert.assertAll();
	}

	//QTest ID TC-972 MAIN-5200, SOO and Multiple Line item , Zero Shipping only
	@Test()
	public void testMAIN5200SOOAndMultipleLineItemZeroShippingOnly_972(){
		String accountNumber = null;
		String SKU = null;
		String shippingCharges="0.0";
		String taxTotalFromOrderCreationBeforeUpdate = null;
		String taxTotalFromOrderCreationAfterUpdate = null;
		String paymentsAppliedFromOrderDetailsPage=null;
		String orderTotalFromOrderCreationBeforeRecalculateTAx = null;
		String orderTotalFromOrderCreationAfterRecalculateTAX=null;
		String subtotalFromOrderCreationBeforeRecalculateTAx = null;
		String subtotalFromOrderCreationAfterRecalculateTAX=null;
		String shippingTotalFromOrderCreationAfterRecalculateTax=null;

		accountNumber = (String) getValueFromQueryResult(randomConsultantAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);
		//click 'Place-New-Order' link
		nscore4HomePage.clickPlaceNewOrderLink();
		SKU= nscore4HomePage.addAndGetProductSKU("10");
		nscore4HomePage.addAndGetSpecficProductSKU("5");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder("AARG001"), "SKU = "+"AARG001"+" is not added to the Autoship Order");
		nscore4OrdersTabPage.clickPaymentApplyLink();
		taxTotalFromOrderCreationBeforeUpdate=nscore4OrdersTabPage.getTaxFromOrderCreationPage();
		orderTotalFromOrderCreationBeforeRecalculateTAx=nscore4OrdersTabPage.getOrderTotalFromOrderCreationPage();
		subtotalFromOrderCreationBeforeRecalculateTAx=nscore4OrdersTabPage.getSubtotalFromOrderCreationPage();

		nscore4OrdersTabPage.clickPerformOverridesButton();
		nscore4OrdersTabPage.enterShippingInOverrideOrderWindow(shippingCharges);
		nscore4OrdersTabPage.clickRecalculateTax();
		nscore4OrdersTabPage.selectOverrideReasons();
		nscore4OrdersTabPage.clickSaveButtonOnPerformOverrideWindow();
		nscore4OrdersTabPage.clickPaymentApplyLink();

		shippingTotalFromOrderCreationAfterRecalculateTax=nscore4OrdersTabPage.getShippingTotalFromOrderCreationPage();
		taxTotalFromOrderCreationAfterUpdate=nscore4OrdersTabPage.getTaxFromOrderCreationPage();
		orderTotalFromOrderCreationAfterRecalculateTAX=nscore4OrdersTabPage.getOrderTotalFromOrderCreationPage();
		subtotalFromOrderCreationAfterRecalculateTAX=nscore4OrdersTabPage.getSubtotalFromOrderCreationPage();

		s_assert.assertFalse(orderTotalFromOrderCreationBeforeRecalculateTAx.contains(orderTotalFromOrderCreationAfterRecalculateTAX),"Order Total is not changed after Re-calculating");
		s_assert.assertTrue(subtotalFromOrderCreationAfterRecalculateTAX.contains(subtotalFromOrderCreationBeforeRecalculateTAx),"Sub Total is changed after Re-calculating");
		s_assert.assertFalse(taxTotalFromOrderCreationAfterUpdate.contains(taxTotalFromOrderCreationBeforeUpdate),"Tax is not changed after Re-calculating");
		s_assert.assertTrue(shippingTotalFromOrderCreationAfterRecalculateTax.contains(shippingCharges),"Shipping is not changed after Re-calculating");
		nscore4OrdersTabPage.clickSubmitOrderBtn();
		paymentsAppliedFromOrderDetailsPage=nscore4OrdersTabPage.getPaymentsAppliedFromOrderDetailsPage();
		s_assert.assertFalse(paymentsAppliedFromOrderDetailsPage.contains(orderTotalFromOrderCreationBeforeRecalculateTAx),"Expected orderTotal on before submit order on UI is: "+orderTotalFromOrderCreationBeforeRecalculateTAx+" But After order placed Actual on UI "+paymentsAppliedFromOrderDetailsPage);
		s_assert.assertAll();
	}

	// QTest ID TC-2474 CRP template > Edit Shipping profile
	@Test
	public void testCRPTemplateEditShippingProfile_2474() {
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newShippingProfileName = "newSP"+randomNum;
		String attentionCO ="SP";
		String accountNumber = null;
		String shippingProfileNameFromUI=null;
		List<Map<String, Object>> randomConsultantList =  null;
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL, RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomConsultantList, "AccountNumber");
		nscore4HomePage.clickSearchConsultant(accountNumber);
		nscore4HomePage.clickSearchIcon();	
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		nscore4HomePage.clickEditCRPTemplate();
		nscore4HomePage.clickEditNewShippingProfileLink();
		nscore4HomePage.editShippingProfile(newShippingProfileName, attentionCO);
		nscore4HomePage.clickSaveAddressBtn();
		nscore4HomePage.acceptQASPopup();
		shippingProfileNameFromUI=nscore4HomePage.getShippingProfileName();
		s_assert.assertTrue(shippingProfileNameFromUI.contains(newShippingProfileName),"Expected payment profile name is:"+newShippingProfileName+" But on UI is: "+shippingProfileNameFromUI);
		nscore4HomePage.clickSaveTemplate();
		s_assert.assertAll();
	}

	//QTest ID TC-968 MAIN-5200, SOO and zero shipping only
	@Test()
	public void testMAIN5200SOOAndZeroShippingOnly_968(){
		String accountNumber = null;
		String SKU = null;
		String shippingCharges="0.0";
		String shippingTotalFromOrderCreation=null;
		String paymentsAppliedFromOrderDetailsPage=null;
		String orderTotalFromOrderCreation=null;
		String taxBeforeShippingUpdate=null;
		String taxAfterShippingUpdate=null;

		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);
		//click 'Place-New-Order' link
		nscore4HomePage.clickPlaceNewOrderLink();
		SKU= nscore4HomePage.addAndGetProductSKU("10");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
		nscore4OrdersTabPage.clickPerformOverridesButton();
		taxBeforeShippingUpdate=nscore4OrdersTabPage.getTaxFromOverrideOrderWindow();
		nscore4OrdersTabPage.enterShippingInOverrideOrderWindow(shippingCharges);
		nscore4OrdersTabPage.clickRecalculateTax();
		taxAfterShippingUpdate=nscore4OrdersTabPage.getTaxFromOverrideOrderWindow();
		s_assert.assertTrue(taxAfterShippingUpdate.contains(taxBeforeShippingUpdate),"Tax is not updated after updating shipping charges to zero");
		nscore4OrdersTabPage.selectOverrideReasons();
		nscore4OrdersTabPage.clickSaveButtonOnPerformOverrideWindow();
		nscore4OrdersTabPage.clickPaymentApplyLink();
		shippingTotalFromOrderCreation=nscore4OrdersTabPage.getShippingTotalFromOrderCreationPage();
		orderTotalFromOrderCreation=nscore4OrdersTabPage.getOrderTotalFromOrderCreationPage();
		s_assert.assertTrue(shippingTotalFromOrderCreation.contains(shippingCharges),"Expected Shipping on UI is: "+shippingCharges+" But Actual on UI "+shippingTotalFromOrderCreation);
		//nscore4OrdersTabPage.clickPaymentApplyLink();
		nscore4OrdersTabPage.clickSubmitOrderBtn();
		paymentsAppliedFromOrderDetailsPage=nscore4OrdersTabPage.getPaymentsAppliedFromOrderDetailsPage();
		s_assert.assertTrue(paymentsAppliedFromOrderDetailsPage.contains(orderTotalFromOrderCreation),"Expected orderTotal on UI is: "+paymentsAppliedFromOrderDetailsPage+" But Actual on UI "+orderTotalFromOrderCreation);
		s_assert.assertAll();
	}


	//QTest ID TC-969 MAIN-5200, SOO and Zero Tax  only
	@Test()
	public void testMAIN5200SOOAndZeroTaxOnly_969(){
		String accountNumber = null;
		String SKU = null;
		String tax="0.0";
		String taxTotalFromOrderCreationBeforeUpdate = null;
		String taxTotalFromOrderCreationAfterUpdate = null;
		String paymentsAppliedFromOrderDetailsPage=null;
		String orderTotalFromOrderCreationBeforeRecalculateTAx = null;
		String orderTotalFromOrderCreationAfterRecalculateTAX=null;
		String subtotalFromOrderCreationBeforeRecalculateTAx = null;
		String subtotalFromOrderCreationAfterRecalculateTAX=null;

		accountNumber = (String) getValueFromQueryResult(randomConsultantAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);
		//click 'Place-New-Order' link
		nscore4HomePage.clickPlaceNewOrderLink();
		SKU= nscore4HomePage.addAndGetProductSKU("10");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
		taxTotalFromOrderCreationBeforeUpdate=nscore4OrdersTabPage.getTaxFromOrderCreationPage();
		orderTotalFromOrderCreationBeforeRecalculateTAx=nscore4OrdersTabPage.getOrderTotalFromOrderCreationPage();
		subtotalFromOrderCreationBeforeRecalculateTAx=nscore4OrdersTabPage.getSubtotalFromOrderCreationPage();

		nscore4OrdersTabPage.clickPerformOverridesButton();
		nscore4OrdersTabPage.enterTaxInOverrideOrderWindow(tax);

		nscore4OrdersTabPage.clickRecalculateTax();
		nscore4OrdersTabPage.selectOverrideReasons();
		nscore4OrdersTabPage.clickSaveButtonOnPerformOverrideWindow();
		nscore4OrdersTabPage.clickPaymentApplyLink();
		taxTotalFromOrderCreationAfterUpdate=nscore4OrdersTabPage.getTaxFromOrderCreationPage();
		orderTotalFromOrderCreationAfterRecalculateTAX=nscore4OrdersTabPage.getOrderTotalFromOrderCreationPage();
		subtotalFromOrderCreationAfterRecalculateTAX=nscore4OrdersTabPage.getSubtotalFromOrderCreationPage();

		s_assert.assertTrue(orderTotalFromOrderCreationBeforeRecalculateTAx.contains(orderTotalFromOrderCreationAfterRecalculateTAX),"Order Total is changed after Re-calculating");
		s_assert.assertTrue(subtotalFromOrderCreationAfterRecalculateTAX.contains(subtotalFromOrderCreationBeforeRecalculateTAx),"Sub Total is changed after Re-calculating");
		s_assert.assertTrue(taxTotalFromOrderCreationAfterUpdate.contains(taxTotalFromOrderCreationBeforeUpdate),"Tax is changed after Re-calculating");
		nscore4OrdersTabPage.clickSubmitOrderBtn();
		paymentsAppliedFromOrderDetailsPage=nscore4OrdersTabPage.getPaymentsAppliedFromOrderDetailsPage();
		s_assert.assertTrue(paymentsAppliedFromOrderDetailsPage.contains(orderTotalFromOrderCreationBeforeRecalculateTAx),"Expected orderTotal on UI is: "+orderTotalFromOrderCreationBeforeRecalculateTAx+" But Actual on UI "+paymentsAppliedFromOrderDetailsPage);
		s_assert.assertAll();
	}


	//QTest ID TC-971 MAIN-5200, SOO and Multiple Line item , Zero Tax only
	@Test()
	public void testMAIN5200SOOAndMultipleLineItemZeroTaxOnly_971(){
		String accountNumber = null;
		String SKU = null;
		String tax="0.0";
		String taxTotalFromOrderCreationBeforeUpdate = null;
		String taxTotalFromOrderCreationAfterUpdate = null;
		String paymentsAppliedFromOrderDetailsPage=null;
		String orderTotalFromOrderCreationBeforeRecalculateTAx = null;
		String orderTotalFromOrderCreationAfterRecalculateTAX=null;
		String subtotalFromOrderCreationBeforeRecalculateTAx = null;
		String subtotalFromOrderCreationAfterRecalculateTAX=null;

		accountNumber = (String) getValueFromQueryResult(randomConsultantAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);
		//click 'Place-New-Order' link
		nscore4HomePage.clickPlaceNewOrderLink();
		SKU= nscore4HomePage.addAndGetProductSKU("10");
		nscore4HomePage.addAndGetSpecficProductSKU("5");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder("AARG001"), "SKU = "+"AARG001"+" is not added to the Autoship Order");
		nscore4OrdersTabPage.clickPaymentApplyLink();
		taxTotalFromOrderCreationBeforeUpdate=nscore4OrdersTabPage.getTaxFromOrderCreationPage();
		orderTotalFromOrderCreationBeforeRecalculateTAx=nscore4OrdersTabPage.getOrderTotalFromOrderCreationPage();
		subtotalFromOrderCreationBeforeRecalculateTAx=nscore4OrdersTabPage.getSubtotalFromOrderCreationPage();

		nscore4OrdersTabPage.clickPerformOverridesButton();
		nscore4OrdersTabPage.enterTaxInOverrideOrderWindow(tax);

		nscore4OrdersTabPage.clickRecalculateTax();
		nscore4OrdersTabPage.selectOverrideReasons();
		nscore4OrdersTabPage.clickSaveButtonOnPerformOverrideWindow();
		nscore4OrdersTabPage.clickPaymentApplyLink();

		taxTotalFromOrderCreationAfterUpdate=nscore4OrdersTabPage.getTaxFromOrderCreationPage();
		orderTotalFromOrderCreationAfterRecalculateTAX=nscore4OrdersTabPage.getOrderTotalFromOrderCreationPage();
		subtotalFromOrderCreationAfterRecalculateTAX=nscore4OrdersTabPage.getSubtotalFromOrderCreationPage();

		s_assert.assertTrue(orderTotalFromOrderCreationBeforeRecalculateTAx.contains(orderTotalFromOrderCreationAfterRecalculateTAX),"Order Total is changed after Re-calculating");
		s_assert.assertTrue(subtotalFromOrderCreationAfterRecalculateTAX.contains(subtotalFromOrderCreationBeforeRecalculateTAx),"Sub Total is changed after Re-calculating");
		s_assert.assertTrue(taxTotalFromOrderCreationAfterUpdate.contains(taxTotalFromOrderCreationBeforeUpdate),"Tax is changed after Re-calculating");
		nscore4OrdersTabPage.clickSubmitOrderBtn();
		paymentsAppliedFromOrderDetailsPage=nscore4OrdersTabPage.getPaymentsAppliedFromOrderDetailsPage();
		s_assert.assertTrue(paymentsAppliedFromOrderDetailsPage.contains(orderTotalFromOrderCreationBeforeRecalculateTAx),"Expected orderTotal on UI is: "+orderTotalFromOrderCreationBeforeRecalculateTAx+" But Actual on UI "+paymentsAppliedFromOrderDetailsPage);
		s_assert.assertAll();
	}


	// QTest ID TC-1954 Save new billing profile PC Autoship template NSC4
	@Test
	public void testSaveNewBillingProfilePCAutoshipTemplateNSC4_1954q() {
		String emailID = null;
		String accountNumber = null;
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newPaymentProfileName = "RFAutoNSCore4"+randomNum;
		String lastNameProfileName = "lN";
		String nameOnCard = "rfAutoUser";
		String cardNumber =  TestConstantsRFL.CARD_NUMBER;
		String CVV = TestConstantsRFL.SECURITY_CODE;
		String PaymentProfileName=newPaymentProfileName+" "+lastNameProfileName;
		String attentionName = "RFAutoNSCore4"+randomNum;
		String enteredCardNameOnUI=null;
		String paymentProfileNameFromUI=null;

		emailID = (String) getValueFromQueryResult(randomPCAccountList, "EmailAddress");
		accountNumber = (String) getValueFromQueryResult(randomPCAccountList, "AccountNumber");
		nscore4HomePage.searchUserOnTheBasisOfEmailAddress(emailID);
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		nscore4HomePage.clickEditPCAutoshipTemplate();

		nscore4HomePage.addPaymentMethod(newPaymentProfileName, lastNameProfileName, nameOnCard, cardNumber,CVV,attentionName);
		nscore4HomePage.clickSavePaymentMethodBtn();
		nscore4HomePage.changeDefaultBillingProfile(newPaymentProfileName+" "+lastNameProfileName);
		paymentProfileNameFromUI=nscore4HomePage.getBillingProfileName();
		enteredCardNameOnUI=nscore4HomePage.getBillingProfileEnteredCardName();
		nscore4HomePage.clickSaveTemplate();

		nscore4HomePage.navigateToBackPage();
		nscore4HomePage.reLoadPage();
		nscore4HomePage.changeDefaultBillingProfile(newPaymentProfileName+" "+lastNameProfileName);
		paymentProfileNameFromUI=nscore4HomePage.getBillingProfileName();
		enteredCardNameOnUI=nscore4HomePage.getBillingProfileEnteredCardName();
		s_assert.assertTrue(paymentProfileNameFromUI.contains(PaymentProfileName),"Expected profile name is "+PaymentProfileName+" But Actual on UI is:"+paymentProfileNameFromUI);
		s_assert.assertTrue(enteredCardNameOnUI.contains(nameOnCard),"Expected profile card name is "+nameOnCard+" But Actual on UI is:"+enteredCardNameOnUI);
		s_assert.assertAll();
	}



	//QTest ID TC-970 MAIN-5200, SOO and Multiple Line item , Zero Price only
	@Test()
	public void testMAIN5200SOOAndMultipleLineItemZeroPriceOnly_970(){
		String accountNumber = null;
		String SKU = null;
		String price="0.0";
		String taxTotalFromOrderCreationBeforeUpdate = null;
		String taxTotalFromOrderCreationAfterUpdate = null;
		String paymentsAppliedFromOrderDetailsPage=null;
		String orderTotalFromOrderCreationBeforeRecalculateTAx = null;
		String orderTotalFromOrderCreationAfterRecalculateTAX=null;
		String subtotalFromOrderCreationBeforeRecalculateTAx = null;
		String subtotalFromOrderCreationAfterRecalculateTAX=null;

		accountNumber = (String) getValueFromQueryResult(randomConsultantAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);

		//click 'Place-New-Order' link
		nscore4HomePage.clickPlaceNewOrderLink();
		SKU= nscore4HomePage.addAndGetProductSKU("10");
		nscore4HomePage.addAndGetSpecficProductSKU("5");

		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder("AARG001"), "SKU = "+"AARG001"+" is not added to the Autoship Order");

		nscore4OrdersTabPage.clickPaymentApplyLink();
		taxTotalFromOrderCreationBeforeUpdate=nscore4OrdersTabPage.getTaxFromOrderCreationPage();
		orderTotalFromOrderCreationBeforeRecalculateTAx=nscore4OrdersTabPage.getOrderTotalFromOrderCreationPage();
		subtotalFromOrderCreationBeforeRecalculateTAx=nscore4OrdersTabPage.getSubtotalFromOrderCreationPage();

		nscore4OrdersTabPage.clickPerformOverridesButton();
		nscore4OrdersTabPage.enterPriceInOverrideOrderWindow(price,"AARG001");
		nscore4OrdersTabPage.clickRecalculateTax();
		nscore4OrdersTabPage.selectOverrideReasons();
		nscore4OrdersTabPage.clickSaveButtonOnPerformOverrideWindow();
		nscore4OrdersTabPage.clickPaymentApplyLink();

		taxTotalFromOrderCreationAfterUpdate=nscore4OrdersTabPage.getTaxFromOrderCreationPage();
		orderTotalFromOrderCreationAfterRecalculateTAX=nscore4OrdersTabPage.getOrderTotalFromOrderCreationPage();
		subtotalFromOrderCreationAfterRecalculateTAX=nscore4OrdersTabPage.getSubtotalFromOrderCreationPage();

		s_assert.assertFalse(orderTotalFromOrderCreationBeforeRecalculateTAx.contains(orderTotalFromOrderCreationAfterRecalculateTAX),"Order Total is not changed after Re-calculating");
		s_assert.assertFalse(subtotalFromOrderCreationAfterRecalculateTAX.contains(subtotalFromOrderCreationBeforeRecalculateTAx),"Sub Total is not changed after Re-calculating");
		s_assert.assertFalse(taxTotalFromOrderCreationAfterUpdate.contains(taxTotalFromOrderCreationBeforeUpdate),"Tax is not changed after Re-calculating");

		nscore4OrdersTabPage.clickSubmitOrderBtn();
		paymentsAppliedFromOrderDetailsPage=nscore4OrdersTabPage.getPaymentsAppliedFromOrderDetailsPage();
		s_assert.assertFalse(paymentsAppliedFromOrderDetailsPage.contains(orderTotalFromOrderCreationBeforeRecalculateTAx),"Expected orderTotal on before submit order on UI is: "+orderTotalFromOrderCreationBeforeRecalculateTAx+" But After order placed Actual on UI "+paymentsAppliedFromOrderDetailsPage);
		s_assert.assertAll();
	}		       				


	//QTest ID TC-973 MAIN-5200, Override Comission
	@Test()
	public void testMAIN5200OverrideComission_973(){
		String accountNumber = null;
		String SKU = null;
		String comissionableValue="0.0";
		String taxTotalFromOrderCreationBeforeUpdate = null;
		String taxTotalFromOrderCreationAfterUpdate = null;
		String paymentsAppliedFromOrderDetailsPage=null;
		String orderTotalFromOrderCreationBeforeRecalculateTAx = null;
		String orderTotalFromOrderCreationAfterRecalculateTAX=null;
		String subtotalFromOrderCreationBeforeRecalculateTAx = null;
		String subtotalFromOrderCreationAfterRecalculateTAX=null;

		accountNumber = (String) getValueFromQueryResult(randomConsultantAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);

		//click 'Place-New-Order' link
		nscore4HomePage.clickPlaceNewOrderLink();
		SKU= nscore4HomePage.addAndGetProductSKU("10");
		nscore4HomePage.addAndGetSpecficProductSKU("5");

		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder("AARG001"), "SKU = "+"AARG001"+" is not added to the Autoship Order");

		nscore4OrdersTabPage.clickPaymentApplyLink();
		taxTotalFromOrderCreationBeforeUpdate=nscore4OrdersTabPage.getTaxFromOrderCreationPage();
		orderTotalFromOrderCreationBeforeRecalculateTAx=nscore4OrdersTabPage.getOrderTotalFromOrderCreationPage();
		subtotalFromOrderCreationBeforeRecalculateTAx=nscore4OrdersTabPage.getSubtotalFromOrderCreationPage();

		nscore4OrdersTabPage.clickPerformOverridesButton();
		nscore4OrdersTabPage.enterCVInOverrideOrderWindow(comissionableValue);
		nscore4OrdersTabPage.clickRecalculateTax();
		nscore4OrdersTabPage.selectOverrideReasons();
		nscore4OrdersTabPage.clickSaveButtonOnPerformOverrideWindow();
		nscore4OrdersTabPage.clickPaymentApplyLink();

		taxTotalFromOrderCreationAfterUpdate=nscore4OrdersTabPage.getTaxFromOrderCreationPage();
		orderTotalFromOrderCreationAfterRecalculateTAX=nscore4OrdersTabPage.getOrderTotalFromOrderCreationPage();
		subtotalFromOrderCreationAfterRecalculateTAX=nscore4OrdersTabPage.getSubtotalFromOrderCreationPage();

		s_assert.assertTrue(orderTotalFromOrderCreationBeforeRecalculateTAx.contains(orderTotalFromOrderCreationAfterRecalculateTAX),"Order Total is not changed after Re-calculating");
		s_assert.assertTrue(subtotalFromOrderCreationAfterRecalculateTAX.contains(subtotalFromOrderCreationBeforeRecalculateTAx),"Sub Total is not changed after Re-calculating");
		s_assert.assertTrue(taxTotalFromOrderCreationAfterUpdate.contains(taxTotalFromOrderCreationBeforeUpdate),"Tax is not changed after Re-calculating");

		nscore4OrdersTabPage.clickSubmitOrderBtn();
		paymentsAppliedFromOrderDetailsPage=nscore4OrdersTabPage.getPaymentsAppliedFromOrderDetailsPage();
		s_assert.assertTrue(paymentsAppliedFromOrderDetailsPage.contains(orderTotalFromOrderCreationBeforeRecalculateTAx),"Expected orderTotal on before submit order on UI is: "+orderTotalFromOrderCreationBeforeRecalculateTAx+" But After order placed Actual on UI "+paymentsAppliedFromOrderDetailsPage);
		s_assert.assertAll();
	}		

	//QTest ID TC-267 NSC4_AccountsTab_AccountLookupAdvanceSeach
	@Test()
	public void testAccountLookupAdvanceSearch_267(){
		String firstName = null;
		String lastName = null;
		String accountNumber = null;
		String emailID = null;
		String sponserId = null;
		String sponserAccountNumber = null;
		String sponserFirstName = null;
		String sponserLastName = null;
		List<Map<String, Object>> randomConsultantAddressList =  null;
		List<Map<String, Object>> randomSponserAccountNumberList =  null;
		String stateFromDB=null;

		s_assert.assertTrue(nscore4HomePage.isLogoutLinkPresent(),"Home Page has not appeared after login");
		//Get random consultant from db with sponser details.
		randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_EMAILID,RFL_DB);
		emailID = (String) getValueFromQueryResult(randomAccountList, "EmailAddress");
		accountNumber = String.valueOf(getValueFromQueryResult(randomAccountList, "AccountNumber"));
		firstName = (String) getValueFromQueryResult(randomAccountList, "FirstName");
		lastName = (String) getValueFromQueryResult(randomAccountList, "LastName");
		sponserId = String.valueOf(getValueFromQueryResult(randomAccountList, "SponsorID"));
		//Get state details of consultant user.
		randomConsultantAddressList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_ACCOUNT_ADDRESS_DETAILS_FOR_ACCOUNT_INFO_QUERY_RFL,emailID), RFL_DB);
		stateFromDB = (String) getValueFromQueryResult(randomConsultantAddressList, "State");
		//Fetch sponser details from sponser ID.
		randomSponserAccountNumberList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_SPONSER_ACCOUNT_NUMBER_FROM_SPONSER_ID, sponserId),RFL_DB);
		sponserAccountNumber = (String) getValueFromQueryResult(randomSponserAccountNumberList, "AccountNumber");
		sponserFirstName = (String) getValueFromQueryResult(randomSponserAccountNumberList, "FirstName");
		sponserLastName = (String) getValueFromQueryResult(randomSponserAccountNumberList, "LastName");

		//Search User based on email.
		nscore4HomePage.searchUserOnTheBasisOfEmailAddress(emailID);
		s_assert.assertTrue(nscore4HomePage.isFirstAndLastNamePresentinSearchResults(accountNumber,firstName, lastName),"First and last name not present in search results");
		//Search User based on First and last name.
		nscore4HomePage.clickBrowseAccounts();
		nscore4HomePage.enterFirstName(firstName);
		nscore4HomePage.enterLastName(lastName);
		nscore4HomePage.clickGoBtnOfSearch();
		s_assert.assertTrue(nscore4HomePage.isFirstAndLastNamePresentinSearchResults(accountNumber,firstName, lastName),"First and last name not present in search results");
		//Search User based on Account type.
		nscore4HomePage.clickBrowseAccounts();
		nscore4HomePage.enterFirstName(firstName);
		nscore4HomePage.enterLastName(lastName);
		nscore4HomePage.selectAccountTypeForSearch("Consultant");
		nscore4HomePage.clickGoBtnOfSearch();
		s_assert.assertTrue(nscore4HomePage.isFirstAndLastNamePresentinSearchResults(accountNumber,firstName, lastName),"First and last name not present in search results");
		//Search User based on sponsor account number.
		nscore4HomePage.clickBrowseAccounts();
		nscore4HomePage.enterAccountNumberInAccountSearchField(sponserAccountNumber);
		nscore4HomePage.clickGoBtnOfSearch();
		s_assert.assertTrue(nscore4HomePage.isFirstAndLastNamePresentinSearchResults(sponserAccountNumber,sponserFirstName, sponserLastName),"Sponser First and last name not present in search results");
		//Search user based on sponsor First and last name.
		nscore4HomePage.clickBrowseAccounts();
		nscore4HomePage.enterFirstName(sponserFirstName);
		nscore4HomePage.enterLastName(sponserLastName);
		nscore4HomePage.clickGoBtnOfSearch();
		s_assert.assertTrue(nscore4HomePage.isFirstAndLastNamePresentinSearchResults(sponserAccountNumber,sponserFirstName, sponserLastName),"Sponser First and last name not present in search results");
		//Search User based on location.
		nscore4HomePage.clickBrowseAccounts();
		nscore4HomePage.selectByLocation(stateFromDB);
		nscore4HomePage.enterFirstName(firstName);
		nscore4HomePage.enterLastName(lastName);
		nscore4HomePage.clickGoBtnOfSearch();
		s_assert.assertTrue(nscore4HomePage.isSearchResultContainsFirstAndLastName(accountNumber,firstName, lastName),"First and last name not present in search results");
		s_assert.assertAll();
	}

	//QTest ID TC-976 MAIN-5200, Override the price higher than the original price
	//QTest ID TC-981 MAIN-5200, Override the price higher than the original price
	@Test()
	public void testOverridePriceHigherThanOriginal_976_981(){
		String accountNumber = null;
		String SKU = null;
		String orignalPrice= null;
		String updatedPrice = null;
		String updateByValue = "5";
		String operation = "Add";
		String errorMessage = null;
		String expectedError = "Please check all of the values to make sure they are not negative or greater than the original amount.";

		accountNumber = (String) getValueFromQueryResult(randomConsultantAccountList, "AccountNumber"); 
		logger.info("Account number from DB is "+accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch();
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		//click 'Place-New-Order' link
		nscore4HomePage.clickPlaceNewOrderLink();
		SKU= nscore4HomePage.addAndGetProductSKU("10");

		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
		orignalPrice = nscore4HomePage.getAddedProductPriceBasedOnSKU(SKU);
		updatedPrice = nscore4HomePage.updatePriceForProduct(orignalPrice,updateByValue,operation);

		nscore4OrdersTabPage.clickPaymentApplyLink();
		nscore4OrdersTabPage.clickPerformOverridesButton();
		nscore4OrdersTabPage.enterPriceInOverrideOrderWindow(updatedPrice,SKU);
		nscore4OrdersTabPage.clickRecalculateTax();
		nscore4OrdersTabPage.selectOverrideReasons();
		nscore4OrdersTabPage.clickSaveButtonOnPerformOverrideWindow();
		errorMessage = nscore4OrdersTabPage.getErrorMessageForgreaterProductPrice();
		s_assert.assertTrue(errorMessage.contains(expectedError),"Expected error message after price increase"+expectedError+" but Actual"+errorMessage);
		s_assert.assertAll();
	}

	// QTest ID TC-974 MAIN-5200, Override shipping
	// QTest ID TC-983 MAIN-5200, Override shipping
	@Test()
	public void testMAIN5200OverrideShipping_974_983() {
		String accountNumber = null;
		String SKU = null;
		String originalShippingPrice = null;
		String updatedShippingPrice = null;
		String updatedShippingPriceAfterRecalculateTAX = null;
		String operation = "Subtract";
		String shipping = "5.0";
		String taxTotalFromOrderCreationBeforeUpdate = null;
		String taxTotalFromOrderCreationAfterUpdate = null;
		String paymentsAppliedFromOrderDetailsPage = null;
		String orderTotalFromOrderCreationBeforeRecalculateTAx = null;
		String orderTotalFromOrderCreationAfterRecalculateTAX = null;
		String subtotalFromOrderCreationBeforeRecalculateTAx = null;
		String subtotalFromOrderCreationAfterRecalculateTAX = null;

		accountNumber = (String) getValueFromQueryResult(randomConsultantAccountList, "AccountNumber");
		logger.info("Account number from DB is " + accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);

		// click 'Place-New-Order' link
		nscore4HomePage.clickPlaceNewOrderLink();
		SKU = nscore4HomePage.addAndGetProductSKU("10");
		nscore4HomePage.addAndGetSpecficProductSKU("5");

		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU),"SKU = " + SKU + " is not added to the Autoship Order");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder("AARG001"),"SKU = " + "AARG001" + " is not added to the Autoship Order");

		nscore4OrdersTabPage.clickPaymentApplyLink();
		taxTotalFromOrderCreationBeforeUpdate = nscore4OrdersTabPage.getTaxFromOrderCreationPage();
		orderTotalFromOrderCreationBeforeRecalculateTAx = nscore4OrdersTabPage.getOrderTotalFromOrderCreationPage();
		subtotalFromOrderCreationBeforeRecalculateTAx = nscore4OrdersTabPage.getSubtotalFromOrderCreationPage();
		originalShippingPrice = nscore4OrdersTabPage.getShippingTotalFromOrderCreationPage();
		updatedShippingPrice = nscore4HomePage.updatePriceForProduct(originalShippingPrice, shipping, operation);

		nscore4OrdersTabPage.clickPerformOverridesButton();
		nscore4OrdersTabPage.enterShippingInOverrideOrderWindow(updatedShippingPrice);
		nscore4OrdersTabPage.clickRecalculateTax();
		nscore4OrdersTabPage.selectOverrideReasons();
		nscore4OrdersTabPage.clickSaveButtonOnPerformOverrideWindow();
		nscore4OrdersTabPage.clickPaymentApplyLink();

		taxTotalFromOrderCreationAfterUpdate = nscore4OrdersTabPage.getTaxFromOrderCreationPage();
		orderTotalFromOrderCreationAfterRecalculateTAX = nscore4OrdersTabPage.getOrderTotalFromOrderCreationPage();
		subtotalFromOrderCreationAfterRecalculateTAX = nscore4OrdersTabPage.getSubtotalFromOrderCreationPage();
		updatedShippingPriceAfterRecalculateTAX = nscore4OrdersTabPage.getShippingTotalFromOrderCreationPage();

		s_assert.assertFalse(orderTotalFromOrderCreationBeforeRecalculateTAx.contains(orderTotalFromOrderCreationAfterRecalculateTAX),"Order Total is not changed after Re-calculating Shipping");
		s_assert.assertTrue(subtotalFromOrderCreationAfterRecalculateTAX.contains(subtotalFromOrderCreationBeforeRecalculateTAx),"Sub Total is changed after Re-calculating Shipping");
		s_assert.assertFalse(taxTotalFromOrderCreationAfterUpdate.contains(taxTotalFromOrderCreationBeforeUpdate),"Tax is not changed after Re-calculating Shipping");
		s_assert.assertTrue(updatedShippingPriceAfterRecalculateTAX.contains(updatedShippingPrice),"Shipping price is not updated");

		nscore4OrdersTabPage.clickSubmitOrderBtn();
		paymentsAppliedFromOrderDetailsPage = nscore4OrdersTabPage.getPaymentsAppliedFromOrderDetailsPage();
		s_assert.assertFalse(paymentsAppliedFromOrderDetailsPage.contains(orderTotalFromOrderCreationBeforeRecalculateTAx),"Expected orderTotal on before submit order on UI is: "+ orderTotalFromOrderCreationBeforeRecalculateTAx + " But After order placed Actual on UI "+ paymentsAppliedFromOrderDetailsPage);
		s_assert.assertAll();
	}  		 

	// QTest ID TC-975 MAIN-5200, Override_not change on any value
	// QTest ID TC-982 MAIN-5200, Override_not change on any value
	@Test()
	public void testMAIN5200OverrideOverrideNotChangeOnAnyValue_975_982() {
		String accountNumber = null;
		String SKU = null;
		String originalShippingPrice = null;
		String updatedShippingPriceAfterRecalculateTAX = null;
		String subtotalFromOrderCreationBeforeRecalculateTAx = null;
		String subtotalFromOrderCreationAfterRecalculateTAX = null;

		accountNumber = (String) getValueFromQueryResult(randomConsultantAccountList, "AccountNumber");
		logger.info("Account number from DB is " + accountNumber);
		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch(accountNumber);

		// click 'Place-New-Order' link
		nscore4HomePage.clickPlaceNewOrderLink();
		SKU = nscore4HomePage.addAndGetProductSKU("10");
		nscore4HomePage.addAndGetSpecficProductSKU("5");

		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU),"SKU = " + SKU + " is not added to the Autoship Order");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder("AARG001"),"SKU = " + "AARG001" + " is not added to the Autoship Order");

		nscore4OrdersTabPage.clickPaymentApplyLink();
		subtotalFromOrderCreationBeforeRecalculateTAx = nscore4OrdersTabPage.getSubtotalFromOrderCreationPage();
		originalShippingPrice = nscore4OrdersTabPage.getShippingTotalFromOrderCreationPage();

		nscore4OrdersTabPage.clickPerformOverridesButton();
		nscore4OrdersTabPage.selectOverrideReasons();
		nscore4OrdersTabPage.clickSaveButtonOnPerformOverrideWindow();

		subtotalFromOrderCreationAfterRecalculateTAX = nscore4OrdersTabPage.getSubtotalFromOrderCreationPage();
		updatedShippingPriceAfterRecalculateTAX = nscore4OrdersTabPage.getShippingTotalFromOrderCreationPage();

		s_assert.assertTrue(subtotalFromOrderCreationAfterRecalculateTAX.contains(subtotalFromOrderCreationBeforeRecalculateTAx),"Sub Total is changed after perform override");
		s_assert.assertTrue(updatedShippingPriceAfterRecalculateTAX.contains(originalShippingPrice),"Shipping price is changed after perform override");

		s_assert.assertAll();
	}	


	//QTest ID TC-940 NSC4 - MAIN-5200, Recalculate subtotal after change item price
	@Test
	public void testMAIN5200RecalculateSubtotalAfterChangeItemPrice_940(){
		String accountNumber = null;
		List<Map<String, Object>> randomSKUList=null;
		String SKU=null;
		String price=null;
		String QV=null;
		String CV=null;
		String taxBeforeUpdate=null;
		String taxAfterUpdateToZero=null;
		String taxAfterUpdate=null;
		String priceValue=null;

		randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL, accountNumber),RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber");
		logger.info("Account number from DB is "+accountNumber);
		randomSKUList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_SKU,RFL_DB);
		SKU=(String) getValueFromQueryResult(randomSKUList, "SKU");

		nscore4HomePage.clickSearchConsultant(accountNumber);
		nscore4HomePage.clickSearchIcon();	
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		nscore4HomePage.clickPlaceNewOrderLink();
		nscore4HomePage.clickSearchSKU(SKU);
		nscore4HomePage.clickAddToOrder();
		nscore4OrdersTabPage.clickPerformOverridesButton();
		priceValue=nscore4OrdersTabPage.getSubtotalFromOrderCreationPage().split("\\$")[1];
		System.out.println("price value is:"+ priceValue);
		taxBeforeUpdate=nscore4OrdersTabPage.getTaxFromOverrideOrderWindow();
		nscore4OrdersTabPage.changePriceCVAndQVValuesToZero(price,CV,QV);
		nscore4OrdersTabPage.clickRecalculateTax();
		taxAfterUpdateToZero=nscore4OrdersTabPage.getTaxFromOverrideOrderWindow();
		s_assert.assertTrue(taxAfterUpdateToZero.contains(taxBeforeUpdate),"Tax is not updated after updating shipping charges to zero");
		nscore4OrdersTabPage.changePriceCVAndQVValues(priceValue);
		nscore4OrdersTabPage.clickRecalculateTax();
		taxAfterUpdate=nscore4OrdersTabPage.getTaxFromOverrideOrderWindow();
		s_assert.assertTrue(taxAfterUpdate.contains(taxAfterUpdateToZero),"Tax is not updated after updating shipping charges to zero");
		s_assert.assertAll();
	}

	//QTest ID TC-941 MAIN-5200, Perform Override with a value less than the original
	@Test
	public void testMAIN5200PerformOverrideWithAValueLessThanTheOriginal_941(){
		String accountNumber = null;
		List<Map<String, Object>> randomSKUList=null;
		String SKU=null;
		String price=null;
		String QV=null;
		String CV=null;
		String taxBeforeUpdate=null;
		String shippingBeforeUpdate=null;
		String taxBeforeRecalculation=null;
		String taxAfterRecalculation=null;
		String taxAfterUpdate=null;
		String priceValue=null;
		String shippingAfterRecalculation=null;
		String shippingBeforeRecalculation=null;
		String shippingAfterUpdate=null;
		String orignalPrice= null;
		String updatedPrice = null;
		String updateByValue = "10";
		String operation = "Subtraction";
		randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL, accountNumber),RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber");
		logger.info("Account number from DB is "+accountNumber);
		randomSKUList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_SKU,RFL_DB);
		SKU=(String) getValueFromQueryResult(randomSKUList, "SKU");

		nscore4HomePage.clickSearchConsultant(accountNumber);
		nscore4HomePage.clickSearchIcon();	
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		nscore4HomePage.clickPlaceNewOrderLink();
		SKU= nscore4HomePage.addAndGetProductSKU("10");

		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
		orignalPrice = nscore4HomePage.getAddedProductPriceBasedOnSKU(SKU);
		updatedPrice = nscore4HomePage.updatePriceForProduct(orignalPrice,updateByValue,operation);
		taxBeforeUpdate =nscore4OrdersTabPage.getTaxFromOrderCreationPage();

		nscore4OrdersTabPage.clickPerformOverridesButton();
		priceValue=nscore4OrdersTabPage.getSubtotalFromOrderCreationPage().split("\\$")[1];
		taxBeforeRecalculation=nscore4OrdersTabPage.getTaxFromOverrideOrderWindow();
		shippingBeforeRecalculation=nscore4OrdersTabPage.getShippingValueFromOverrideOrderWindow();
		nscore4OrdersTabPage.clickRecalculateTax();
		taxAfterRecalculation=nscore4OrdersTabPage.getTaxFromOverrideOrderWindow();
		shippingAfterRecalculation=nscore4OrdersTabPage.getShippingValueFromOverrideOrderWindow();
		s_assert.assertTrue(taxAfterRecalculation.contains(taxBeforeRecalculation),"Tax is changed after recalcuating without changing value");
		s_assert.assertTrue(shippingAfterRecalculation.contains(shippingBeforeRecalculation),"Shipping is changed after recalcuating without changing value");
		nscore4OrdersTabPage.enterPriceInOverrideOrderWindow(updatedPrice,SKU);
		nscore4OrdersTabPage.clickRecalculateTax();
		nscore4OrdersTabPage.selectOverrideReasons();
		nscore4OrdersTabPage.clickSaveButtonOnPerformOverrideWindow();
		taxAfterUpdate=nscore4OrdersTabPage.getTaxFromOrderCreationPage();
		shippingAfterUpdate=nscore4OrdersTabPage.getTaxFromOrderCreationPage();
		s_assert.assertFalse(taxAfterUpdate.contains(taxBeforeUpdate),"Tax is not updated after updating shipping charges to zero");
		s_assert.assertAll();

	}

	//QTest ID TC-942 MAIN-5200, Edit QV on override 
	@Test
	public void testMAIN5200EditQVOnOverride_942(){
		String accountNumber = null;
		List<Map<String, Object>> randomSKUList=null;
		String SKU=null;
		String orignalQV=null;
		String updatedQV = null;
		String updateByValue = "5";
		String operation = "Subtraction";

		randomAccountList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL, accountNumber),RFL_DB);
		accountNumber = (String) getValueFromQueryResult(randomAccountList, "AccountNumber");
		logger.info("Account number from DB is "+accountNumber);
		randomSKUList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_SKU,RFL_DB);
		SKU=(String) getValueFromQueryResult(randomSKUList, "SKU");

		nscore4HomePage.clickSearchConsultant(accountNumber);
		nscore4HomePage.clickSearchIcon();	
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		nscore4HomePage.clickPlaceNewOrderLink();
		SKU= nscore4HomePage.addAndGetProductSKU("10");
		s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
		orignalQV = nscore4HomePage.getAddedProductQVBasedOnSKU(SKU);
		updatedQV = nscore4HomePage.updateQVForProduct(orignalQV,updateByValue,operation);
		nscore4OrdersTabPage.clickPerformOverridesButton();
		nscore4OrdersTabPage.clickRecalculateTax();
		nscore4OrdersTabPage.enterQVValueInOverrideOrderWindow(updatedQV,SKU);
		nscore4OrdersTabPage.clickRecalculateTax();
		s_assert.assertFalse(orignalQV.contains(updatedQV),"QV is not updated after changing the value");
		s_assert.assertAll();

	}

	// QTest ID TC-1950 Save new billing profile CRP template NSC4
	@Test
	public void testSaveNewBillingProfileCRPTemplateNSC4_1950q() {
		String emailID = null;
		String accountNumber = null;
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newPaymentProfileName = "RFAutoNSCore4"+randomNum;
		String lastNameProfileName = "lN";
		String nameOnCard = "rfAutoUser";
		String cardNumber =  TestConstantsRFL.CARD_NUMBER;
		String CVV = TestConstantsRFL.SECURITY_CODE;
		String PaymentProfileName=newPaymentProfileName+" "+lastNameProfileName;
		String attentionName = "RFAutoNSCore4"+randomNum;
		String enteredCardNameOnUI=null;
		String paymentProfileNameFromUI=null;

		List<Map<String, Object>> randomConsultantList =  null;
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFL, RFL_DB);
		emailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");
		accountNumber = (String) getValueFromQueryResult(randomConsultantList, "AccountNumber");

		nscore4HomePage.clickSearchConsultant(accountNumber);
		nscore4HomePage.clickSearchIcon();	
		nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
		nscore4HomePage.clickEditCRPTemplate();

		nscore4HomePage.addPaymentMethod(newPaymentProfileName, lastNameProfileName, nameOnCard, cardNumber,CVV,attentionName);
		nscore4HomePage.clickSavePaymentMethodBtn();
		nscore4HomePage.changeDefaultBillingProfile(PaymentProfileName);
		nscore4HomePage.clickSaveTemplate();
		nscore4HomePage.clickCustomerNameOnOrderDetailsPage(accountNumber);
		nscore4HomePage.clickEditCRPTemplate();

		paymentProfileNameFromUI=nscore4HomePage.getBillingProfileName();
		enteredCardNameOnUI=nscore4HomePage.getBillingProfileEnteredCardName();
		s_assert.assertTrue(paymentProfileNameFromUI.contains(PaymentProfileName),"Expected profile name is "+PaymentProfileName+" But Actual on UI is:"+paymentProfileNameFromUI);
		s_assert.assertTrue(enteredCardNameOnUI.contains(nameOnCard),"Expected profile card name is "+nameOnCard+" But Actual on UI is:"+enteredCardNameOnUI);
		s_assert.assertAll();
	}	


	//QTest ID TC-1955 Nsc4_Browse account_migrated account(after rollback)
	@Test
	public void testNsc4BrowseAccountMigratedAccount_After_Rollback_1955q(){
		String firstName = null;
		String lastName = null;
		String accountNumber = null;

		//randomUserList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACCOUNT_DETAILS,RFL_DB);
		firstName = (String) getValueFromQueryResult(randomConsultantAccountList, "FirstName");
		lastName = (String) getValueFromQueryResult(randomConsultantAccountList, "LastName");
		accountNumber = (String) getValueFromQueryResult(randomConsultantAccountList, "AccountNumber");

		nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
		nscore4HomePage.clickGoBtnOfSearch();
		s_assert.assertTrue(nscore4HomePage.isFirstAndLastNamePresentinSearchResults(firstName, lastName), "First and last name is not present in search result,when searched with account number");
		s_assert.assertTrue(nscore4HomePage.isCIDPresentinSearchResults(accountNumber), "CID/Account Number is not present in search result,when searched with account number");
		s_assert.assertAll();
	}	
	
	//QTest ID TC-977 MAIN-5200, Change shipping method after override
		//QTest ID TC-980 MAIN-5200, Change shipping method after override
		@Test(enabled=false)//MAIN-7631
		public void testChangeShippingMethodAfterOverride_977_980(){
			String accountNumber = null;
			String SKU = null;
			String orignalPrice= null;
			String updatedPrice = null;
			String updateByValue = "5";
			String operation = "Subtraction";
			String taxTotalFromOrderCreationBeforeUpdate = null;
			String taxTotalFromOrderCreationAfterUpdate = null;
			String orderTotalFromOrderCreationBeforeRecalculateTAx = null;
			String orderTotalFromOrderCreationAfterRecalculateTAX=null;
			String subtotalFromOrderCreationBeforeRecalculateTAx = null;
			String subtotalFromOrderCreationAfterRecalculateTAX=null;
			String shippingFromOrderCreationBeforeUpdatingShippingMethod=null;
			String shippingFromOrderCreationAfterUpgradingShippingMethod=null;
			String shippingFromOrderCreationAfterDowngradingShippingMethod=null;
			String paymentsAppliedFromOrderDetailsPage=null;
			String shippingMethodUpgradeValue="Higher";
			String shippingMethodDowngradeValue="Lower";


			accountNumber = (String) getValueFromQueryResult(randomConsultantAccountList, "AccountNumber"); 
			logger.info("Account number from DB is "+accountNumber);
			nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
			nscore4HomePage.clickGoBtnOfSearch();
			nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
			//click 'Place-New-Order' link
			nscore4HomePage.clickPlaceNewOrderLink();
			SKU= nscore4HomePage.addAndGetProductSKU("10");

			s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
			orignalPrice = nscore4HomePage.getAddedProductPriceBasedOnSKU(SKU);
			updatedPrice = nscore4HomePage.updatePriceForProduct(orignalPrice,updateByValue,operation);

			nscore4OrdersTabPage.clickPaymentApplyLink();
			//Get original tax,orderTotal and subTotal values
			taxTotalFromOrderCreationBeforeUpdate=nscore4OrdersTabPage.getTaxFromOrderCreationPage();
			orderTotalFromOrderCreationBeforeRecalculateTAx=nscore4OrdersTabPage.getOrderTotalFromOrderCreationPage();
			subtotalFromOrderCreationBeforeRecalculateTAx=nscore4OrdersTabPage.getSubtotalFromOrderCreationPage();

			nscore4OrdersTabPage.clickPerformOverridesButton();
			nscore4OrdersTabPage.enterPriceInOverrideOrderWindow(updatedPrice,SKU);
			nscore4OrdersTabPage.clickRecalculateTax();
			nscore4OrdersTabPage.selectOverrideReasons();
			nscore4OrdersTabPage.clickSaveButtonOnPerformOverrideWindow();
			nscore4OrdersTabPage.clickPaymentApplyLink();

			//Get updated tax,orderTotal and subtTotal values
			taxTotalFromOrderCreationAfterUpdate=nscore4OrdersTabPage.getTaxFromOrderCreationPage();
			orderTotalFromOrderCreationAfterRecalculateTAX=nscore4OrdersTabPage.getOrderTotalFromOrderCreationPage();
			subtotalFromOrderCreationAfterRecalculateTAX=nscore4OrdersTabPage.getSubtotalFromOrderCreationPage();
			shippingFromOrderCreationBeforeUpdatingShippingMethod = nscore4OrdersTabPage.getShippingFromOrderCreationPage();

			//Assert the updated values tax,orderTotal and subTotal values
			s_assert.assertFalse(orderTotalFromOrderCreationBeforeRecalculateTAx.contains(orderTotalFromOrderCreationAfterRecalculateTAX),"Order Total is not changed after Re-calculating");
			s_assert.assertFalse(subtotalFromOrderCreationAfterRecalculateTAX.contains(subtotalFromOrderCreationBeforeRecalculateTAx),"Sub Total is not changed after Re-calculating");
			s_assert.assertFalse(taxTotalFromOrderCreationAfterUpdate.contains(taxTotalFromOrderCreationBeforeUpdate),"Tax is not changed after Re-calculating");

			//Update shipping method one method higher.
			nscore4OrdersTabPage.updateShippingMethod(shippingMethodUpgradeValue);
			nscore4OrdersTabPage.clickPaymentApplyLink();
			shippingFromOrderCreationAfterUpgradingShippingMethod= nscore4OrdersTabPage.getShippingFromOrderCreationPage();

			//Assert for shipping price after one method higher.
			s_assert.assertFalse(shippingFromOrderCreationAfterUpgradingShippingMethod.contains(shippingFromOrderCreationBeforeUpdatingShippingMethod),"Shipping total after upgrading shipping method Expected "+shippingFromOrderCreationAfterUpgradingShippingMethod+" And Actual on UI "+shippingFromOrderCreationBeforeUpdatingShippingMethod);

			//Update shipping method one method lower.
			nscore4OrdersTabPage.updateShippingMethod(shippingMethodDowngradeValue);
			nscore4OrdersTabPage.clickPaymentApplyLink();
			shippingFromOrderCreationAfterDowngradingShippingMethod= nscore4OrdersTabPage.getShippingFromOrderCreationPage();

			//Assert for shipping price after one method lower.
			s_assert.assertFalse(shippingFromOrderCreationAfterDowngradingShippingMethod.contains(shippingFromOrderCreationAfterUpgradingShippingMethod),"Shipping total after degrading shipping method Expected "+shippingFromOrderCreationAfterDowngradingShippingMethod+" And Actual on UI "+shippingFromOrderCreationAfterUpgradingShippingMethod);

			//Submit the override.
			nscore4OrdersTabPage.clickSubmitOrderBtn();
			paymentsAppliedFromOrderDetailsPage=nscore4OrdersTabPage.getPaymentsAppliedFromOrderDetailsPage();
			s_assert.assertFalse(paymentsAppliedFromOrderDetailsPage.contains(orderTotalFromOrderCreationBeforeRecalculateTAx),"Expected orderTotal on before submit order on UI is: "+orderTotalFromOrderCreationBeforeRecalculateTAx+" But After order placed Actual on UI "+paymentsAppliedFromOrderDetailsPage);
			s_assert.assertAll();

		}
		//QTest ID TC-978 MAIN-5200, Change shipping address after override
		//QTest ID TC-979 MAIN-5200, Change shipping address after override
		@Test
		public void testChangeShippingAddressAfterOverride_978_979(){
			String accountNumber = null;
			String SKU = null;
			String orignalPrice= null;
			String updatedPrice = null;
			String updateByValue = "5";
			String operation = "Subtraction";
			String postalCode = TestConstants.POSTAL_CODE_US;
			String taxTotalFromOrderCreationBeforeUpdate = null;
			String taxTotalFromOrderCreationAfterUpdate = null;
			String orderTotalFromOrderCreationBeforeRecalculateTAx = null;
			String orderTotalFromOrderCreationAfterRecalculateTAX=null;
			String subtotalFromOrderCreationBeforeRecalculateTAx = null;
			String subtotalFromOrderCreationAfterRecalculateTAX=null;
			String paymentsAppliedFromOrderDetailsPage=null;
			String shippingProfileAddress = null;



			accountNumber = (String) getValueFromQueryResult(randomConsultantAccountList, "AccountNumber"); 
			logger.info("Account number from DB is "+accountNumber);
			nscore4HomePage.enterAccountNumberInAccountSearchField(accountNumber);
			nscore4HomePage.clickGoBtnOfSearch();
			nscore4HomePage.clickAccountRecordOnBasisOfAccountNumber(accountNumber);
			//click 'Place-New-Order' link
			nscore4HomePage.clickPlaceNewOrderLink();
			SKU= nscore4HomePage.addAndGetProductSKU("10");

			s_assert.assertTrue(nscore4HomePage.isProductAddedToOrder(SKU), "SKU = "+SKU+" is not added to the Autoship Order");
			orignalPrice = nscore4HomePage.getAddedProductPriceBasedOnSKU(SKU);
			updatedPrice = nscore4HomePage.updatePriceForProduct(orignalPrice,updateByValue,operation);

			nscore4OrdersTabPage.clickPaymentApplyLink();
			//Get original tax,orderTotal and subTotal values
			taxTotalFromOrderCreationBeforeUpdate=nscore4OrdersTabPage.getTaxFromOrderCreationPage();
			orderTotalFromOrderCreationBeforeRecalculateTAx=nscore4OrdersTabPage.getOrderTotalFromOrderCreationPage();
			subtotalFromOrderCreationBeforeRecalculateTAx=nscore4OrdersTabPage.getSubtotalFromOrderCreationPage();

			nscore4OrdersTabPage.clickPerformOverridesButton();
			nscore4OrdersTabPage.enterPriceInOverrideOrderWindow(updatedPrice,SKU);
			nscore4OrdersTabPage.clickRecalculateTax();
			nscore4OrdersTabPage.selectOverrideReasons();
			nscore4OrdersTabPage.clickSaveButtonOnPerformOverrideWindow();
			nscore4OrdersTabPage.clickPaymentApplyLink();
			//Get updated tax,orderTotal and subTotal values
			taxTotalFromOrderCreationAfterUpdate=nscore4OrdersTabPage.getTaxFromOrderCreationPage();
			orderTotalFromOrderCreationAfterRecalculateTAX=nscore4OrdersTabPage.getOrderTotalFromOrderCreationPage();
			subtotalFromOrderCreationAfterRecalculateTAX=nscore4OrdersTabPage.getSubtotalFromOrderCreationPage();

			//Assert the updated values tax,orderTotal and subTotal values
			s_assert.assertFalse(orderTotalFromOrderCreationBeforeRecalculateTAx.contains(orderTotalFromOrderCreationAfterRecalculateTAX),"Order Total is not changed after Re-calculating");
			s_assert.assertFalse(subtotalFromOrderCreationAfterRecalculateTAX.contains(subtotalFromOrderCreationBeforeRecalculateTAx),"Sub Total is not changed after Re-calculating");
			s_assert.assertFalse(taxTotalFromOrderCreationAfterUpdate.contains(taxTotalFromOrderCreationBeforeUpdate),"Tax is not changed after Re-calculating");

			//Edit the shipping address for different zip code.
			nscore4OrdersTabPage.clickEditNewShippingProfileLink();
			nscore4OrdersTabPage.UpdateShippingProfile(postalCode);
			nscore4OrdersTabPage.clickSaveAddressBtn();
			shippingProfileAddress = nscore4OrdersTabPage.getShippingProfileAddress();

			//Assert shipping profile address zip code.
			s_assert.assertTrue(shippingProfileAddress.contains(postalCode),"Shipping profile address is not updated for zip code");

			//Submit the override.
			nscore4OrdersTabPage.clickSubmitOrderBtn();
			paymentsAppliedFromOrderDetailsPage=nscore4OrdersTabPage.getPaymentsAppliedFromOrderDetailsPage();
			s_assert.assertFalse(paymentsAppliedFromOrderDetailsPage.contains(orderTotalFromOrderCreationBeforeRecalculateTAx),"Expected orderTotal on before submit order on UI is: "+orderTotalFromOrderCreationBeforeRecalculateTAx+" But After order placed Actual on UI "+paymentsAppliedFromOrderDetailsPage);
			s_assert.assertAll();

		}

}