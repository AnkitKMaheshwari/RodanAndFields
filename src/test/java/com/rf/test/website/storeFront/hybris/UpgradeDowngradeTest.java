package com.rf.test.website.storeFront.hybris;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rf.core.driver.website.RFWebsiteDriver;
import com.rf.core.utils.CommonUtils;
import com.rf.core.utils.DBUtil;
import com.rf.core.website.constants.TestConstants;
import com.rf.core.website.constants.TestConstantsRFL;
import com.rf.core.website.constants.dbQueries.DBQueries_RFO;
import com.rf.pages.website.storeFront.StoreFrontConsultantPage;
import com.rf.pages.website.storeFront.StoreFrontHomePage;
import com.rf.pages.website.storeFront.StoreFrontPCUserPage;
import com.rf.pages.website.storeFront.StoreFrontUpdateCartPage;
import com.rf.test.website.RFWebsiteBaseTest;

public class UpgradeDowngradeTest extends RFWebsiteBaseTest{
	private static final Logger logger = LogManager
			.getLogger(UpgradeDowngradeTest.class.getName());

	/***
	 * Test Summary:- Terminate a consultant and use his email id to enroll a PC via enroll under last upline popup
	 *  Validation: 
	 *  * PC congrats message
	 *  * Welcome dopdown
	 *  * No edit CRP in drop down after downgrading to PC
	 * @throws InterruptedException
	 */
	// Hybris Project-4724:Consultant to PC downgrade for Canadian User
	@Test(priority=1)
	public void testConsultantToPCDownGradeforCanadianUser_4724() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		List<Map<String, Object>> randomConsultantList =  null;
		String consultantEmailID = null;
		String accountID = null;
		String lastName = "lN";
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_WITH_SAME_COUNTRY_SPONSOR_RFO,countryId, countryId),RFO_DB);
			accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_EMAIL_ID_FROM_ACCOUNT_ID,accountID),RFO_DB);
			consultantEmailID = String.valueOf(getValueFromQueryResult(randomConsultantList, "EmailAddress"));
			logger.info("Account Id of the user is "+accountID);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				driver.get(driver.getURL()+"/"+country);
			}
			else
				break;
		}

		//s_assert.assertTrue(storeFrontConsultantPage.verifyConsultantPage(),"Consultant Page doesn't contain Welcome User Message");
		logger.info("login is successful");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountTerminationPage = storeFrontAccountInfoPage.clickTerminateMyAccount();
		storeFrontAccountTerminationPage.fillTheEntriesAndClickOnSubmitDuringTermination();
		s_assert.assertTrue(storeFrontAccountTerminationPage.verifyAccountTerminationIsConfirmedPopup(), "Account still exist");
		storeFrontAccountTerminationPage.clickOnConfirmTerminationPopup();
		storeFrontAccountTerminationPage.clickOnCloseWindowAfterTermination();
		storeFrontHomePage.clickOnCountryAtWelcomePage();
		//storeFrontHomePage.openPWSSite(country, env);
		driver.get(driver.getURL()+"/"+driver.getCountry());
		// Enroll the PC Again
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		storeFrontHomePage.enterEmailAddress(consultantEmailID);
		Assert.assertTrue(storeFrontHomePage.isEnrollUnderLastUplinePopupDisplayed(), "Enroll Under Last upline popup not displayed for termanted consultant used as PC email");
		storeFrontHomePage.clickOnEnrollUnderLastUpline();
		// Enroll Deactivated Consultant as PC
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		storeFrontHomePage.enterEmailAddress(consultantEmailID);
		storeFrontHomePage.clickOnEnrollUnderLastUplineProcessToPopupDisappear(consultantEmailID);
		storeFrontHomePage.enterNewPCDetails(firstName, lastName, consultantEmailID,password);
		//Enter the Main account info and DO NOT check the "Become a Preferred Customer" and click next
		storeFrontHomePage.enterMainAccountInfo();
		logger.info("Main account details entered");
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		//Enter Billing Profile
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
		storeFrontHomePage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.isPCEnrolledCongratsMessagePresent(), "'Welcome to Rodan + Fields PC Perks' message has not appeared");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		s_assert.assertFalse(storeFrontAccountInfoPage.isEditCRPLinkPresent(), "PC is NOT converted to consultant");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- Terminate a consultant and use his email id to enroll a RC via enroll under last upline popup
	 *  Validation: 
	 *  * Login should fail for terminated consultant
	 *  * Login should pass for  Upgraded RC
	 *   *RC congrats message
	 *  * Welcome dopdown
	 *  * No edit CRP in drop down after downgrading to RC
	 * @throws InterruptedException
	 */
	// Hybris Project-4822:Consultant to RC downgrade Canada
	@Test(priority=2)
	public void testConsultantToRCDowngrade_4822() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		List<Map<String, Object>> randomConsultantList =  null;
		String consultantEmailID = null;
		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment(),driver.getCountry(),countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		}
		//Terminate consultant Account
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyAccountInfoPageIsDisplayed(),"Account Info page has not been displayed");
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountTerminationPage=storeFrontAccountInfoPage.clickTerminateMyAccount();
		s_assert.assertTrue(storeFrontAccountTerminationPage.verifyAccountTerminationPageIsDisplayed(),"Account Termination Page has not been displayed");
		storeFrontAccountTerminationPage.selectTerminationReason();
		storeFrontAccountTerminationPage.enterTerminationComments();
		storeFrontAccountTerminationPage.selectCheckBoxForVoluntarilyTerminate();
		storeFrontAccountTerminationPage.clickSubmitToTerminateAccount();
		s_assert.assertTrue(storeFrontAccountTerminationPage.verifyPopupHeader(),"Account termination Page Pop Up Header is Present");
		storeFrontAccountTerminationPage.clickOnConfirmTerminationPopup();
		storeFrontAccountTerminationPage.clickOnCloseWindowAfterTermination();
		storeFrontHomePage.clickOnCountryAtWelcomePage();
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		s_assert.assertTrue(storeFrontHomePage.isCurrentURLShowsError(),"Inactive User doesn't get Login failed");
		//navigate to  Corp Site And start enrolling RC user.
		driver.get(driver.getURL()+"/"+driver.getCountry());
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String lastName = "lN";
		// Click on our product link that is located at the top of the page and then click in on quick shop
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		//Select a product and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		//Enter the email address and DO NOT check the "Become a Preferred Customer" checkbox 
		storeFrontHomePage.enterEmailAddress(consultantEmailID);
		Assert.assertTrue(storeFrontHomePage.isEnrollUnderLastUplinePopupDisplayed(), "Enroll Under Last upline popup not displayed for termanted consultant used as RC email");
		//Handle popup for enroll under last upline
		storeFrontHomePage.clickOnEnrollUnderLastUpline();
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		//Select a product and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		//Enter the User information and DO NOT check the "Become a Preferred Customer" checkbox and click the create account button
		storeFrontHomePage.enterNewRCDetails(TestConstants.FIRST_NAME+randomNum, TestConstants.LAST_NAME,consultantEmailID, password);
		//Enter the Main account info and DO NOT check the "Become a Preferred Customer" and click next
		storeFrontHomePage.enterMainAccountInfo();
		logger.info("Main account details entered");
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		//Enter Billing Profile
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.isOrderPlacedSuccessfully(), "Order Not placed successfully");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		s_assert.assertFalse(storeFrontAccountInfoPage.isEditCRPLinkPresent(), "PC is NOT converted to consultant");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- Start enrolling RC and enter email of existing PC,RC and cons to verify if the popup appears or not
	 *  Validation: 
	 *  * Popup for existing PC,its cancel and reset password functionality
	 *  * Popup for existing RC,its cancel and reset password functionality
	 *  * Popup for existing cons,its cancel and reset password functionality
	 * @throws InterruptedException
	 */
	//Hybris Project-2186:PC2RC: RC enrollment add already exist PC email ID: Verify Popup
	//Hybris Project-2201 :: Version : 1 :: Verify Existing Consultant popup during RC registration
	//Hybris Project-2200 :: Version : 1 :: Verify Existing Retail Customer popup during RC registration
	//Hybris Project-2199 :: Version : 1 :: Verify Existing Preferred Customer popup during RC registration.
	@Test(priority=3)
	public void testExistingPCPopUpDuringRCRegistration_2199_2200_2201_2186() throws InterruptedException {
		String pcmailId=null;
		String rcmailId=null;
		String consultantmailId=null;
		List<Map<String, Object>> randomPCUserEmailIdList =  null;
		List<Map<String, Object>> randomRCUserEmailIdList =  null;
		List<Map<String, Object>> randomConsultantEmailIdList =  null;
		randomConsultantEmailIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
		consultantmailId = String.valueOf(getValueFromQueryResult(randomConsultantEmailIdList, "Username"));
		randomPCUserEmailIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
		pcmailId = String.valueOf(getValueFromQueryResult(randomPCUserEmailIdList, "Username"));
		randomRCUserEmailIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),RFO_DB);
		rcmailId = String.valueOf(getValueFromQueryResult(randomRCUserEmailIdList, "Username"));
		// Click on our product link that is located at the top of the page and then click in on quick shop
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		//Select a product and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on place order
		storeFrontHomePage.clickOnCheckoutButton();
		//Validate Existing PC Popup during RC Registration by entering existing PC mailid
		s_assert.assertTrue(storeFrontHomePage.validateExistingUserPopUp(pcmailId), "Existing PC PopUp is not displayed");
		s_assert.assertFalse(storeFrontHomePage.validateCancelEnrollmentFunctionalityPC(),"Cancel Registration button not working existing PC");	
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		//Select a product and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on place order
		storeFrontHomePage.clickOnCheckoutButton();
		//Validate Existing PC Popup during RC Registration by entering existing PC mailid
		s_assert.assertTrue(storeFrontHomePage.validateExistingUserPopUp(pcmailId), "Existing PC PopUp is not displayed");
		s_assert.assertFalse(storeFrontHomePage.validateSendMailToResetMyPasswordFunctionalityPC(),"Send Mail to reset password' button not working for existing PC");	
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		//Select a product and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on place order
		storeFrontHomePage.clickOnCheckoutButton();
		//Validate Existing RC Popup during RC Registration by entering existing RC mailid
		s_assert.assertTrue(storeFrontHomePage.validateExistingUserPopUp(rcmailId), "Existing RC PopUp is not displayed");
		s_assert.assertTrue(storeFrontHomePage.validateCancelEnrollmentFunctionality(),"Cancel Registration button not working existing RC");	
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		//Select a product and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on place order
		storeFrontHomePage.clickOnCheckoutButton();
		//Validate Existing RC Popup during RC Registration by entering existing RC mailid
		s_assert.assertTrue(storeFrontHomePage.validateExistingUserPopUp(rcmailId), "Existing RC PopUp is not displayed");
		s_assert.assertTrue(storeFrontHomePage.validateSendMailToResetMyPasswordFunctionalityRC(),"'Send mail to reset my password' button not working for existing RC");	
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		//Select a product and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		storeFrontHomePage.clickOnCheckoutButton();
		//Validate Existing Consultant Popup during RC Registration by entering existing Consultant mailid
		s_assert.assertTrue(storeFrontHomePage.validateExistingUserPopUp(consultantmailId), "Existing Consultant PopUp is not displayed");
		s_assert.assertTrue(storeFrontHomePage.validateCancelEnrollmentFunctionalityConsultant(),"Cancel Registration button not working existing Cons");	
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		//Select a product and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		storeFrontHomePage.clickOnCheckoutButton();
		//Validate Existing Consultant Popup during RC Registration by entering existing Consultant mailid
		s_assert.assertTrue(storeFrontHomePage.validateExistingUserPopUp(consultantmailId), "Existing Consultant PopUp is not displayed");
		s_assert.assertTrue(storeFrontHomePage.validateSendMailToResetMyPasswordFunctionalityConsultant(),"Send Mail To Reset My Password button not working existing Cons");	
		s_assert.assertAll(); 
	}

	/***
	 * Test Summary:- starting from BIZ, enroll PC and add email id of existing active RC
	 *  Validation: 
	 *  * Popup for existing RC,its cancel and reset password functionality
	 * @throws InterruptedException
	 */
	//Hybris Project-4674:During PC enrollment add email ID of RC user who is already exist. Verify the popup and follow the i
	@Test(priority=4)
	public void testDuringPCEnrollmentAddEmailIdOfExistingRCUser_4674() throws InterruptedException{
		Boolean isBizPWSOpened=false;
		String rcmailId=null;
		List<Map<String, Object>> randomRCUserEmailIdList =  null;
		randomRCUserEmailIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),RFO_DB);
		rcmailId = String.valueOf(getValueFromQueryResult(randomRCUserEmailIdList, "Username"));
		for(int i=1;i<=3;i++){
			storeFrontHomePage.openPWSSite(driver.getCountry(), driver.getEnvironment());
			if(driver.getCurrentUrl().contains(".biz")==true){
				isBizPWSOpened=true;
				break;
			}
		}
		Assert.assertTrue(isBizPWSOpened, "BIZ PWS was not opened");
		// Click on our product link that is located at the top of the page and then click in on quick shop
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		// Products are displayed?
		s_assert.assertTrue(storeFrontHomePage.areProductsDisplayed(), "quickshop products not displayed");
		logger.info("Quick shop products are displayed");
		//Select a product and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Cart page is displayed?
		s_assert.assertTrue(storeFrontHomePage.isCartPageDisplayed(), "Cart page is not displayed");
		logger.info("Cart page is displayed");
		//Click on place order
		storeFrontHomePage.clickOnCheckoutButton();
		//Validate Existing PC Popup during RC Registration by entering existing PC mailid
		s_assert.assertTrue(storeFrontHomePage.validateExistingUserPopUp(rcmailId), "Existing RC PopUp is not displayed");
		s_assert.assertTrue(storeFrontHomePage.validateSendMailToResetMyPasswordFunctionalityRC(),"Send mail to reset my password' button not working");	
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- starting from COM, enroll PC and add email id of existing active RC
	 *  Validation: 
	 *  * Popup for existing RC,its cancel and reset password functionality
	 * @throws InterruptedException
	 */
	//Hybris Project-4675:RC2PC : During PC enrollment add email ID of RC user who is already exist
	@Test(priority=5)
	public void testDuringPCEnrollmentFromComSiteAddEmailIdOfExistingRCUser_4675() throws InterruptedException{
		String rcmailId=null;
		Boolean isComPWSOpened =false;
		List<Map<String, Object>> randomRCUserEmailIdList =  null;
		randomRCUserEmailIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),RFO_DB);
		rcmailId = String.valueOf(getValueFromQueryResult(randomRCUserEmailIdList, "Username"));
		String bizPWS = storeFrontHomePage.openPWSSite(driver.getCountry(), driver.getEnvironment());
		String comPWS = storeFrontHomePage.convertBizSiteToComSite(bizPWS);
		storeFrontHomePage.openConsultantPWS(comPWS);
		for(int i=1;i<=3;i++){
			if(driver.getCurrentUrl().contains(".com")==true && driver.getCurrentUrl().contains(".my") ==true){
				isComPWSOpened=true;
				break;
			}
		}
		Assert.assertTrue(isComPWSOpened, "COM PWS was not opened");
		// Click on our product link that is located at the top of the page and then click in on quick shop
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		// Products are displayed?
		s_assert.assertTrue(storeFrontHomePage.areProductsDisplayed(), "quickshop products not displayed");
		logger.info("Quick shop products are displayed");
		//Select a product and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Cart page is displayed?
		s_assert.assertTrue(storeFrontHomePage.isCartPageDisplayed(), "Cart page is not displayed");
		logger.info("Cart page is displayed");
		//Click on place order
		storeFrontHomePage.clickOnCheckoutButton();
		//Validate Existing PC Popup during RC Registration by entering existing PC mailid
		s_assert.assertTrue(storeFrontHomePage.validateExistingUserPopUp(rcmailId), "Existing RC PopUp is not displayed");
		s_assert.assertTrue(storeFrontHomePage.validateSendMailToResetMyPasswordFunctionalityRC(),"Send mail to reset my password' button not working");	
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- Terminate a PC and enroll a consultant with same email id
	 *  Validation: 
	 *  * Consultant congrats message
	 *  * Edit CRP on welcome dropdown
	 * @throws InterruptedException
	 */
	//Hybris Project-4317:Soft-Terminated PC Customer enrolls to be a Consultant with his old email
	@Test(priority=6)
	public void testSoftTerminatedPCEnrollsToBeConsultant_4317() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		String firstName=TestConstants.FIRST_NAME+randomNum;
		List<Map<String, Object>> randomPCUserList =  null;
		String pcUserEmailID = null;
		/*String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME_US+randomNum;
			    String lastName = "lN";*/
		String accountId = null;
		while(true){
			randomPCUserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_AUTOSHIPS_RFO,countryId),RFO_DB);
			pcUserEmailID = (String) getValueFromQueryResult(randomPCUserList, "UserName");  
			accountId = String.valueOf(getValueFromQueryResult(randomPCUserList, "AccountID"));
			logger.info("Account Id of the user is "+accountId);
			storeFrontPCUserPage = storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+pcUserEmailID);
				driver.get(driver.getURL()+"/"+country);
			}
			else
				break;
		} 
		//s_assert.assertTrue(storeFrontPCUserPage.verifyPCUserPage(),"PC User Page doesn't contain Welcome User Message");
		logger.info("login is successful");
		storeFrontPCUserPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontPCUserPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyAccountInfoPageIsDisplayed(),"Account Info page has not been displayed");
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountInfoPage.clickOnPcPerksStatus();
		storeFrontPCUserPage.clickDelayOrCancelPCPerks();
		storeFrontAccountTerminationPage = storeFrontAccountInfoPage.clickOnCancelPCPerks();
		storeFrontAccountTerminationPage.fillTheEntriesAndClickOnSubmitDuringTerminationForPC();
		storeFrontAccountTerminationPage.clickOnConfirmAccountTermination();
		storeFrontAccountTerminationPage.clickOnCloseWindowAfterTermination();
		driver.get(driver.getURL()+"/"+country);
		kitName = TestConstants.KIT_NAME_BIG_BUSINESS;
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(sponsor);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);		
		storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
		storeFrontHomePage.enterEmailAddress(pcUserEmailID);
		Assert.assertTrue(storeFrontHomePage.isEnrollUnderLastUplinePopupDisplayed(), "Enroll Under Last upline popup not displayed for termanted PC used as Cons email");
		storeFrontHomePage.clickOnEnrollUnderLastUpline();
		storeFrontHomePage.enterUserInformationForEnrollmentWithEmail(kitName, regimenName, enrollmentType, firstName, TestConstants.LAST_NAME+randomNum, pcUserEmailID, password, addressLine1, city,state, postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		storeFrontHomePage.clickOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontHomePage.isEditCRPLinkPresent(), "Edit CRP Link is NOT Present on Welcome Drop Down");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- Enroll a PC on Quebec, terminate it and enroll a consultant with same email id
	 *  Validation: 
	 *  * Consultant congrats message
	 *  * Edit CRP on welcome dropdown
	 * @throws InterruptedException
	 */
	//Hybris Project-2191:Enroll PC who lives in Quebec and then try to upgrade his membership from PC to Consultant. 
	@Test(priority=7)
	public void testQuebecPCEnrollmentAndUpgradeMembershipFromPCToConsultant_2191() throws InterruptedException{
		if(driver.getCountry().equalsIgnoreCase("ca")){
			int randomNum = CommonUtils.getRandomNum(10000, 1000000);		
			String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
			String lastName = "lN";
			String firstName=TestConstants.FIRST_NAME+randomNum;
			String emailid = firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
			String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
			String addressLine1_Quebec = TestConstants.ADDRESS_LINE_1_QUEBEC;
			String city_Quebec = TestConstants.CITY_QUEBEC;
			String postalCode_Quebec = TestConstants.POSTAL_CODE_QUEBEC;
			String state_Quebec = TestConstants.PROVINCE_QUEBEC;
			storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
			// Products are displayed?
			s_assert.assertTrue(storeFrontHomePage.areProductsDisplayed(), "quickshop products not displayed");
			logger.info("Quick shop products are displayed");
			//Select a product and proceed to buy it
			storeFrontHomePage.selectProductAndProceedToBuy();
			//Click on Check out
			storeFrontHomePage.clickOnCheckoutButton();
			//Enter the User information and check the "Become a Preferred Customer" checkbox and click the create account button
			storeFrontHomePage.enterNewPCDetails(TestConstants.FIRST_NAME+randomNum, TestConstants.LAST_NAME+randomNum, emailid,password );
			//Enter the Main account info and check the "Become a Preferred Customer" and click next
			storeFrontHomePage.enterMainAccountInfo(addressLine1_Quebec, city_Quebec, state_Quebec, postalCode_Quebec, phoneNumber);
			logger.info("Main account details entered");
			storeFrontHomePage.clickOnContinueWithoutSponsorLink();
			storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
			storeFrontHomePage.clickOnShippingAddressNextStepBtn();
			//Enter Billing Profile
			storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
			storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
			storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
			storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
			storeFrontHomePage.selectNewBillingCardAddress();
			storeFrontHomePage.clickOnSaveBillingProfile();
			storeFrontHomePage.clickOnBillingNextStepBtn();
			storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
			storeFrontHomePage.clickPlaceOrderBtn();
			s_assert.assertTrue(storeFrontHomePage.isPCEnrolledCongratsMessagePresent(), "'Welcome to Rodan + Fields PC Perks' message has not appeared");
			storeFrontHomePage.clickOnRodanAndFieldsLogo();
			// Upgrade the PC as Consultant	
			storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
			storeFrontHomePage.searchCID(sponsor);
			storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
			storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);		
			storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
			storeFrontHomePage.enterEmailAddress(emailid);
			s_assert.assertTrue(storeFrontHomePage.verifyUpradingToConsulTantPopup(), "Upgrading to a consultant popup is not present");
			storeFrontHomePage.enterPasswordForUpgradePcToConsultant();
			storeFrontHomePage.clickOnLoginToTerminateToMyPCAccount();
			storeFrontHomePage.enterFirstName(newBillingProfileName);
			storeFrontHomePage.enterLastName(lastName);
			storeFrontHomePage.enterEmailAddress(emailid);
			storeFrontHomePage.enterPassword(password);
			storeFrontHomePage.enterConfirmPassword(password);
			storeFrontHomePage.enterAddressLine1(addressLine1);
			storeFrontHomePage.enterCity(city);
			s_assert.assertTrue(storeFrontHomePage.verifyQuebecProvinceIsDisabled(),"Quebec province is not disabled for consultant user.");
			storeFrontHomePage.selectProvince(state);
			storeFrontHomePage.enterPostalCode(postalCode);
			storeFrontHomePage.enterPhoneNumber(phoneNumber);		
			storeFrontHomePage.clickNextButton();		
			storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
			storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
			storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
			storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
			storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
			storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
			storeFrontHomePage.clickNextButton();
			storeFrontHomePage.selectAllTermsAndConditionsChkBox();
			storeFrontHomePage.clickOnEnrollMeBtn();
			storeFrontHomePage.clickOnConfirmAutomaticPayment();
			s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
			storeFrontHomePage.clickOnRodanAndFieldsLogo();
			storeFrontHomePage.clickOnWelcomeDropDown();
			s_assert.assertTrue(storeFrontHomePage.isEditCRPLinkPresent(), "Edit CRP Link is Present on Welcome Drop Down");
			s_assert.assertAll();
		}
	}

	/***
	 * Test Summary:- Enter active RC email Id during consultant enrollment, terminate it and complete the enrollment
	 *  Validation: 
	 *   RC termination message
	 *  * Consultant congrats message
	 *  * Edit CRP on welcome dropdown
	 * @throws InterruptedException
	 */
	//Hybris Project-1498:Active RC email address, during consultant enrollment under Same & Different sponsor(BIZ and CORP)
	@Test(priority=8)
	public void testActiveRCEmailAddressDuringConsultantEnrollment_1498() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		List<Map<String, Object>> randomRCList =  null;
		String rcUserEmailID =null;
		String accountId = null;
		String firstName = TestConstants.FIRST_NAME+randomNum;
		String lastName = "lN";
		randomRCList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),RFO_DB);
		rcUserEmailID = (String) getValueFromQueryResult(randomRCList, "UserName");		
		accountId = String.valueOf(getValueFromQueryResult(randomRCList, "AccountID"));
		logger.info("Account Id of the user is "+accountId);
		storeFrontRCUserPage = storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(sponsor);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);  
		storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
		storeFrontHomePage.enterEmailAddress(rcUserEmailID);
		//	s_assert.assertTrue(storeFrontHomePage.isActiveRetailPopupDisplayed(), "Active retail Popup is not present");
		storeFrontHomePage.enterPasswordForUpgradeRCToConsultant();
		storeFrontHomePage.clickOnLoginToTerminateToMyRCAccount();
		s_assert.assertTrue(storeFrontHomePage.verifyAccountTerminationMessage(), "Rc user is not terminated successfully");
		storeFrontHomePage.enterUserInformationForEnrollmentWithEmailWithoutKit(firstName, lastName, rcUserEmailID, password, addressLine1, city, state, postalCode,phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		storeFrontHomePage.clickOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontHomePage.isEditCRPLinkPresent(), "Edit CRP Link is NOT Present on Welcome Drop Down");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- Enter Inactive RC email Id during consultant enrollmentt and complete it
	 *  Validation: 
	 *  Upgrade to consultant popup
	 *  RC termination message
	 *  * Consultant congrats message
	 *  * Edit CRP on welcome dropdown
	 * @throws InterruptedException
	 */
	//Hybris Project-3963:Inactive RC email address, during consultant enrollment under same/different sponsor(BIZ & CORP)
	@Test(priority=9)
	public void testInactiveRCEmailAddressDuringConsultantEnrollment_3963() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		List<Map<String, Object>> randomRCList =  null;
		String rcUserEmailID =null;
		String accountId = null;
		String firstName = TestConstants.FIRST_NAME+randomNum;
		String lastName = "lN";
		randomRCList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_INACTIVE_RC_HAVING_ORDERS_RFO,countryId),RFO_DB);
		rcUserEmailID = (String) getValueFromQueryResult(randomRCList, "UserName");		
		accountId = String.valueOf(getValueFromQueryResult(randomRCList, "AccountID"));
		logger.info("Account Id of the user is "+accountId);
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(sponsor);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);  
		storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
		storeFrontHomePage.enterEmailAddress(rcUserEmailID);
		s_assert.assertFalse(storeFrontHomePage.isActiveRetailPopupDisplayed(), "Active retail Popup is present");
		storeFrontHomePage.enterFirstName(firstName);
		storeFrontHomePage.enterLastName(lastName);
		storeFrontHomePage.enterPassword(password);
		storeFrontHomePage.enterConfirmPassword(password);
		storeFrontHomePage.enterAddressLine1(addressLine1);
		storeFrontHomePage.enterCity(city);
		storeFrontHomePage.selectProvince(state);
		storeFrontHomePage.enterPostalCode(postalCode);
		storeFrontHomePage.enterPhoneNumber(phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		storeFrontHomePage.clickOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontHomePage.isEditCRPLinkPresent(), "Edit CRP Link is NOT Present on Welcome Drop Down");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- Start enrolling RC and check the checkbox for PC at main info page and complete the PC enrollment
	 *  Validation: 
	 *  * PC enrollment message
	 * @throws InterruptedException
	 */
	//Hybris Project-3881:CORP:Join PCPerk in the Order Summary section - CA Spsonor with Pulse
	//Hybris Project-1304 :: Version : 1 :: Switch from RC to PC (Not existing user) 
	@Test(priority=10)
	public void testSwitchFromRCToPC_1304_3881() throws InterruptedException {
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		List<Map<String, Object>> sponserList =  null;
		String sponserHavingPulse = null;
		sponserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment(),driver.getCountry(),countryId),RFO_DB);
		sponserHavingPulse = String.valueOf(getValueFromQueryResult(sponserList, "AccountID"));

		// sponser search by Account Number
		List<Map<String, Object>> sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,sponserHavingPulse),RFO_DB);
		String idForConsultant = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));

		// Click on our product link that is located at the top of the page and then click in on quick shop
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();  
		//Select a product and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		//Enter the User information and DONOT  check the "Become a Preferred Customer" checkbox and click the create account button
		storeFrontHomePage.enterNewRCDetails(firstName,lastName,emailAddress, password);
		storeFrontHomePage.clickYesIWantToJoinPCPerksCB();		
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		storeFrontHomePage.searchCID(idForConsultant);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		//Enter Billing Profile
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.checkIAcknowledgePCAccountCheckBox();
		storeFrontHomePage.checkPCPerksTermsAndConditionsCheckBox();
		storeFrontHomePage.clickPlaceOrderBtn();
		//Validate welcome PC Perks Message
		s_assert.assertTrue(storeFrontHomePage.isWelcomePCPerksMessageDisplayed(), "welcome PC perks message should be displayed");
		s_assert.assertAll(); 
	}

	/***
	 * Test Summary:- Start enrolling RC with low price product and check the checkbox for PC at main info page, increase the quantity and complete the PC enrollment
	 *  Validation: 
	 *  * PC perks checkbox is unchecked by default at Order summary
	 *  * Popup for threshold after checking PC checkbox
	 * @throws InterruptedException
	 */
	//Hybris Project-2091:Switch From Retail Customer to Prefered Customer
	@Test(priority=11)
	public void testSwitchFronRCToPC_2091() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);  
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String lastName = "lN";
		List<Map<String, Object>> randomRCList =  null;
		String rcEmailID = null;
		String qty = "10";
		String increasedQty = "12";
		while(true){
			randomRCList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_RC_RFO,countryId),RFO_DB);
			rcEmailID = (String) getValueFromQueryResult(randomRCList, "Username");
			storeFrontRCUserPage = storeFrontHomePage.loginAsRCUser(rcEmailID,password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+rcEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		}
		// Click on our product link that is located at the top of the page and then click in on quick shop
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		//Select a product with the price less than $80 and proceed to buy it
		storeFrontHomePage.applyPriceFilterLowToHigh();
		storeFrontHomePage.selectProductAndProceedToBuyWithoutFilter();
		//Click on place order
		storeFrontHomePage.clickOnCheckoutButton();
		s_assert.assertFalse(storeFrontHomePage.verifyPCPerksCheckBoxIsSelected(),"pc perks checbox is selected for rc user");
		storeFrontHomePage.clickYesIWantToJoinPCPerksCB();
		s_assert.assertTrue(storeFrontHomePage.isPopUpForPCThresholdPresent(),"Threshold poup for PC validation NOT present");
		storeFrontHomePage.addQuantityOfProduct(qty);
		storeFrontHomePage.addQuantityOfProductTillThresholdPopupDisappear(increasedQty);
		storeFrontHomePage.clickOnCheckoutButton();
		storeFrontHomePage.enterMainAccountInfo(addressLine1, city, state, postalCode, phoneNumber);
		logger.info("Main account details entered");
		storeFrontHomePage.clickOnContinueWithoutSponsorLink();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		storeFrontHomePage.clickAddNewBillingProfileLink();
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
		storeFrontHomePage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		//verify PC User Enrolled successfully
		storeFrontHomePage.clickOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontHomePage.verifyEditPcPerksIsPresentInWelcomDropdownForUpgrade(), "User NOT registered successfully as PC User");
		//		//Verify PC user in RFO.
		//		List<Map<String, Object>> enrolledPCList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACTIVE_ACCOUNT_NUMBER_FROM_EMAIL_ADDRESS,rcEmailID),RFO_DB);
		//		String accountNumberOfUpgradedRC = String.valueOf(getValueFromQueryResult(enrolledPCList, "AccountNumber"));
		//		List<Map<String, Object>> accountTypeList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_TYPE_ID_FROM_ACCOUNT_NUMBER,accountNumberOfUpgradedRC),RFO_DB);
		//		String accountTypeIDOfUpgradedRC = String.valueOf(getValueFromQueryResult(accountTypeList, "AccountTypeID"));
		//		s_assert.assertTrue(accountTypeIDOfUpgradedRC.equals("2"), "RC user account is not upgraded to PC account in RFO database.");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- Login with RC and while checkout check the PC checkbox and complete the PC enrollment
	 *  Validation: 
	 *  * PC perks message after RC enrolled as PC
	 *  
	 * @throws InterruptedException
	 */
	//Hybris Project-2299:Become PC during Checkout - summary Page
	//Hybris Project-2198 :: Version : 1 :: Switch from RC to PC (Under Same Consultant) 
	@Test(priority=12)
	public void testSwitchFromRCToPCUnderSameConsultant_2198_2299() throws InterruptedException {
		String rcUsername=null;
		String rcmailId=null;
		String sponsorId=null;
		String accountNumber=null;
		List<Map<String, Object>> randomRCUserList =  null;
		List<Map<String, Object>> accountNumberList =null;
		Boolean isCorpSponsor=false;
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String firstName=TestConstants.FIRST_NAME+randomNum;
		//		String lastName = "lN";
		//		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		for(int i=1;i<=3;i++){
			randomRCUserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),RFO_DB);
			rcUsername = String.valueOf(getValueFromQueryResult(randomRCUserList, "Username"));
			sponsorId=String.valueOf(getValueFromQueryResult(randomRCUserList, "SponsorId"));
			accountNumber=String.valueOf(getValueFromQueryResult(randomRCUserList, "AccountNumber"));
			rcmailId=String.valueOf(getValueFromQueryResult(DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_EMAIL_FROM_ACC_NUMBER,accountNumber),RFO_DB), "EmailAddress"));
			if(sponsorId.equals("2")){
				isCorpSponsor=true;
			}
			else{
				accountNumberList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,sponsorId),RFO_DB);
				accountNumber = String.valueOf(getValueFromQueryResult(accountNumberList, "AccountNumber"));
			}
			storeFrontRCUserPage = storeFrontHomePage.loginAsRCUser(rcmailId, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+rcmailId);
				if(driver.getCountry().equalsIgnoreCase("ca")||driver.getCountry().equalsIgnoreCase("au")){
					driver.get(driver.getURL()+"/"+driver.getCountry());	
				}
				else
					driver.get(driver.getURL());
			}
			else
				break;
		}
		logger.info("login is successful");		
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();  
		//Select a product and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		storeFrontHomePage.clickYesIWantToJoinPCPerksCB();
		s_assert.assertTrue(storeFrontHomePage.verifyPCPerksCheckBoxIsSelected(), "Yes I want to join pc perks checkboz is not selected");
		if(isCorpSponsor==false){
			storeFrontHomePage.searchCID(accountNumber);
			storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();			
		}
		else{
			storeFrontHomePage.clickOnContinueWithoutSponsorLink();
		}
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.checkIAcknowledgePCAccountCheckBox();
		storeFrontHomePage.checkPCPerksTermsAndConditionsCheckBox();
		storeFrontHomePage.clickPlaceOrderBtn();
		//Validate welcome PC Perks Message
		s_assert.assertTrue(storeFrontHomePage.isWelcomePCPerksMessageDisplayed(), "welcome PC perks message should be displayed");
		s_assert.assertAll(); 
	}

	/***
	 * Test Summary:- Start enrolling PC by checking checkbox at signUp page and uncheck it on order summary page, so that user should be RC not PC
	 *  Validation: 
	 *  * Enrolled user should be RC
	 *  * Enrolled user should NOT be PC, no PC Perks messgae after enrollment 
	 *  * User should NOT have EditPCPerks on welcome dropdown
	 * @throws InterruptedException
	 */
	//Hybris Project-3898:Downgrade to RC on the Payment / Order summary page
	@Test(priority=13)
	public void testDowngradePCToRCAtPaymentOrderSummaryPage_3898() throws InterruptedException{
		int randomNum =  CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstants.FIRST_NAME+randomNum;
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String lastName = TestConstants.LAST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		String PWS = storeFrontHomePage.getBizPWS(country, env);
		PWS = storeFrontHomePage.convertBizSiteToComSite(PWS);
		storeFrontHomePage.openPWS(PWS);
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		storeFrontHomePage.clickOnCheckoutButton();
		storeFrontHomePage.enterNewPCDetails(firstName, lastName,emailAddress, password);
		//Enter main account info
		storeFrontHomePage.enterMainAccountInfo();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		//Enter billing info
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		//Uncheck PC Perks Checkbox on Payment/order summary page
		s_assert.assertTrue(storeFrontHomePage.validatePCPerksCheckBoxIsDisplayed(),"PC Perks checkbox is not present");
		s_assert.assertTrue(storeFrontHomePage.verifyPCPerksCheckBoxIsSelected(),"pc perks checbox is not selected");
		storeFrontHomePage.checkPCPerksCheckBox();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		s_assert.assertFalse(storeFrontHomePage.verifyPCPerksCheckBoxIsSelected(),"pc perks checbox is selected");
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.isOrderPlacedSuccessfully(),"Order is not placed successfully");
		s_assert.assertFalse(storeFrontHomePage.isPCEnrolledCongratsMessagePresent(), "'Welcome to Rodan + Fields PC Perks' message has not appeared");
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		storeFrontHomePage.clickOnWelcomeDropDown();
		s_assert.assertFalse(storeFrontHomePage.verifyEditPcPerksIsPresentInWelcomDropdownForUpgrade(),"Edit Pc Perks Link is present for RC User");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- Terminate RC and use the email Id to enroll the PC
	 *  Validation: 
	 *  * Account termination Page Pop Up Header
	 *  * Login fail for terminated RC
	 *  * PC congrats message
	 * @throws InterruptedException
	 */
	//Hybris Phase 2-2228 :: Version : 1 :: Perform RC Account termination through my account
	// Hybris Project-4694:COM: Terminate RC, Enroll RC and Upgrade 2 PC
	@Test(priority=14)
	public void testTerminateRCEnrollRCUpgradeToPC_4694_2228() throws InterruptedException{
		List<Map<String, Object>> randomRCList =  null;
		String rcUserEmailID = null;
		String accountId = null;
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String lastName = "lN";
		//String RCEmailAddress = firstName+randomNum+TestConstants.EMAIL_ADDRESS_SUFFIX;
		while(true){
			randomRCList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),RFO_DB);
			rcUserEmailID = (String) getValueFromQueryResult(randomRCList, "UserName");  
			accountId = String.valueOf(getValueFromQueryResult(randomRCList, "AccountID"));
			logger.info("Account Id of the user is "+accountId);
			storeFrontRCUserPage = storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
			boolean isError = driver.getCurrentUrl().contains("error");
			if(isError){
				logger.info("login error for the user "+rcUserEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
				continue;
			}
			else{
				break;
			} 
		}
		storeFrontHomePage.clickOnWelcomeDropDown();
		//terminate RC account
		storeFrontAccountInfoPage = storeFrontHomePage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountTerminationPage = storeFrontAccountInfoPage.clickTerminateMyAccount();
		storeFrontAccountTerminationPage.selectTerminationReason();
		storeFrontAccountTerminationPage.enterTerminationComments();
		storeFrontAccountTerminationPage.selectCheckBoxForVoluntarilyTerminate();
		storeFrontAccountTerminationPage.clickSubmitToTerminateAccount();
		s_assert.assertTrue(storeFrontAccountTerminationPage.verifyPopupHeader(),"Account termination Page Pop Up Header is not Present");
		storeFrontAccountTerminationPage.clickOnConfirmTerminationPopup();
		storeFrontHomePage.loginAsRCUser(rcUserEmailID,password);
		s_assert.assertTrue(storeFrontHomePage.isCurrentURLShowsError(),"Inactive User doesn't get Login failed");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		//Enroll RC with same email ID.
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		//Select a product and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		//Enter the User information and DO NOT check the "Become a Preferred Customer" checkbox and click the create account button
		storeFrontHomePage.enterNewPCDetails(firstName, TestConstants.LAST_NAME+randomNum, rcUserEmailID,password);
		//Enter the Main account info and DO NOT check the "Become a Preferred Customer" and click next
		storeFrontHomePage.enterMainAccountInfo();
		logger.info("Main account details entered");
		storeFrontHomePage.clickOnContinueWithoutSponsorLink();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		//Enter Billing Profile
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
		storeFrontHomePage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.isPCEnrolledCongratsMessagePresent(), "'Welcome to Rodan + Fields PC Perks' message has not appeared");
		s_assert.assertAll();
	}

	//Hybris Project-3958:CORP: Active PC email id during consultant enrollment under same sponsor.
	@Test(priority=15)
	public void testActivePCEmailIdDuringConsultantEnrollmentUnderSameSponsor_3958() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);  
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String lastName = "lN";
		// Click on our product link that is located at the top of the page and then click in on quick shop
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		//Select a product with the price less than $80 and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		//Enter the User information and DO  check the "Become a Preferred Customer" checkbox and click the create account button
		String pcEmailId= storeFrontHomePage.createNewPC(TestConstants.FIRST_NAME+randomNum, TestConstants.LAST_NAME+randomNum, password);
		//Enter the Main account info and DO NOT check the "Become a Preferred Customer" and click next
		storeFrontHomePage.enterMainAccountInfo();
		logger.info("Main account details entered");
		// Get  sponser with PWS for PC User from database
		List<Map<String, Object>> sponserList  = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",driver.getCountry(),countryId),RFO_DB);
		String pcSponserAccountID = String.valueOf(getValueFromQueryResult(sponserList, "AccountID"));
		// sponser search by Account Number
		List<Map<String, Object>> sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,pcSponserAccountID),RFO_DB);
		String sponsorIdOfPC = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
		storeFrontHomePage.searchCID(sponsorIdOfPC); 
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPC();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		//Enter Billing Profile
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
		storeFrontHomePage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.isPCEnrolledCongratsMessagePresent(), "'Welcome to Rodan + Fields PC Perks' message has not appeared");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		logout();
		navigateToStoreFrontBaseURL();
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(sponsorIdOfPC);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.selectEnrollmentKitPage(TestConstants.KIT_NAME_BIG_BUSINESS, TestConstants.REGIMEN_NAME_REVERSE);  
		storeFrontHomePage.chooseEnrollmentOption(TestConstants.EXPRESS_ENROLLMENT);
		storeFrontHomePage.enterEmailAddress(pcEmailId);
		s_assert.assertTrue(storeFrontHomePage.verifyUpradingToConsulTantPopup(), "Upgrading to a consultant popup is not present");
		storeFrontHomePage.enterPasswordForUpgradePcToConsultant();
		storeFrontHomePage.clickOnLoginToTerminateToMyPCAccount();
		s_assert.assertTrue(storeFrontHomePage.verifyAccountTerminationMessage(), "Rc user is not terminated successfully");
		storeFrontHomePage.enterFirstName(firstName);
		storeFrontHomePage.enterLastName(lastName);
		storeFrontHomePage.enterEmailAddress(pcEmailId);
		storeFrontHomePage.enterPassword(password);
		storeFrontHomePage.enterConfirmPassword(password);
		storeFrontHomePage.enterAddressLine1(addressLine1);
		storeFrontHomePage.enterCity(city);
		storeFrontHomePage.selectProvince(state);
		storeFrontHomePage.enterPostalCode(postalCode);
		storeFrontHomePage.enterPhoneNumber(phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		s_assert.assertTrue(storeFrontHomePage.isTheTermsAndConditionsCheckBoxDisplayed(), "Terms and Conditions checkbox is not visible");
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		//Verify the enrolled consultant in RFO database.
		List<Map<String, Object>> enrolledConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACTIVE_ACCOUNT_NUMBER_FROM_EMAIL_ADDRESS,pcEmailId),RFO_DB);
		String accountNumberOfUpgradedPC = String.valueOf(getValueFromQueryResult(enrolledConsultantList, "AccountNumber"));
		List<Map<String, Object>> accountTypeList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_TYPE_ID_FROM_ACCOUNT_NUMBER,accountNumberOfUpgradedPC),RFO_DB);
		String accountTypeIDOfUpgradedPC = String.valueOf(getValueFromQueryResult(accountTypeList, "AccountTypeID"));
		s_assert.assertTrue(accountTypeIDOfUpgradedPC.equals("1"), "PC user account is not upgraded to consultant account in RFO database.");
		logout();
		navigateToStoreFrontBaseURL();
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(pcEmailId, password);
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontAccountInfoPage.isEditCRPLinkPresent(), "PC is NOT converted to consultant");
		s_assert.assertAll();
	}

	// Hybris Project-3959:BIZ:Active PC email id during consultant enrollment under different sponsor
	@Test(priority=16)
	public void testActivePCEmailIdDuringConsultantEnrollmentUnderDifferentSponsor_3959() throws InterruptedException{
		String reqCountry = null;
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);  
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String lastName = "lN";
		// Click on our product link that is located at the top of the page and then click in on quick shop
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		//Enter the User information and DO  check the "Become a Preferred Customer" checkbox and click the create account button
		String pcEmailId= storeFrontHomePage.createNewPC(TestConstants.FIRST_NAME+randomNum, TestConstants.LAST_NAME+randomNum, password);
		logger.info("Enrolled PC user email address is "+pcEmailId);
		//Enter the Main account info and DO NOT check the "Become a Preferred Customer" and click next
		storeFrontHomePage.enterMainAccountInfo();
		logger.info("Main account details entered");
		// Get  sponser with PWS for PC User from database
		List<Map<String, Object>> sponserList  = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",driver.getCountry(),countryId),RFO_DB);
		String sponserHavingPulseForPC = String.valueOf(getValueFromQueryResult(sponserList, "AccountID"));
		String bizPWSOfSponserOfPC=String.valueOf(getValueFromQueryResult(sponserList, "URL")); 
		bizPWSOfSponserOfPC=storeFrontHomePage.replaceAllHTTPWithHTTPS(bizPWSOfSponserOfPC);
		logger.info("biz pws of sponser of pc from database "+bizPWSOfSponserOfPC);
		// sponser search by Account Number
		List<Map<String, Object>> sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,sponserHavingPulseForPC),RFO_DB);
		String sponsorIdOfPC = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
		storeFrontHomePage.searchCID(sponsorIdOfPC); 
		//storeFrontHomePage.searchCID();
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPC();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		//Enter Billing Profile
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
		storeFrontHomePage.clickPlaceOrderBtn();
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		logout();
		driver.get(driver.getURL()+"/"+driver.getCountry());
		// Get sponser for consultant enrollment with PWS from database
		sponserList  = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",driver.getCountry(),countryId),RFO_DB);
		String sponserHavingPulse = String.valueOf(getValueFromQueryResult(sponserList, "AccountID"));
		String bizPWSOfSponser=String.valueOf(getValueFromQueryResult(sponserList, "URL")); 
		// sponser search by Account Number
		sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,sponserHavingPulse),RFO_DB);
		String sponsorId = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
		storeFrontHomePage.openConsultantPWS(bizPWSOfSponser);
		while(true){
			if(driver.getCurrentUrl().contains("sitenotfound")){
				sponserList  = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",driver.getCountry(),countryId),RFO_DB);
				sponserHavingPulse = String.valueOf(getValueFromQueryResult(sponserList, "AccountID"));
				bizPWSOfSponser=String.valueOf(getValueFromQueryResult(sponserList, "URL")); 
				// sponser search by Account Number
				sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,sponserHavingPulse),RFO_DB);
				sponsorId = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
				storeFrontHomePage.openConsultantPWS(bizPWSOfSponser);
			}else{
				if(sponsorIdOfPC.equalsIgnoreCase(sponsorId)==false){
					break;
				}else{
					sponserList  = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",driver.getCountry(),countryId),RFO_DB);
					sponserHavingPulse = String.valueOf(getValueFromQueryResult(sponserList, "AccountID"));
					bizPWSOfSponser=String.valueOf(getValueFromQueryResult(sponserList, "URL")); 
					// sponser search by Account Number
					sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,sponserHavingPulse),RFO_DB);
					sponsorId = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
					storeFrontHomePage.openConsultantPWS(bizPWSOfSponser);
				}
			}
		}
		logger.info("biz pws of sponser of Consultant from database "+bizPWSOfSponser);
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.selectEnrollmentKitPage(TestConstants.KIT_NAME_BIG_BUSINESS, TestConstants.REGIMEN_NAME_REVERSE);  
		storeFrontHomePage.chooseEnrollmentOption(TestConstants.EXPRESS_ENROLLMENT);
		storeFrontHomePage.enterEmailAddress(pcEmailId);
		Assert.assertTrue(storeFrontHomePage.verifyUpradingToConsulTantPopup(), "Active PC Termination popup NOT displayed while upgrading to cons");
		storeFrontHomePage.enterPasswordForUpgradePcToConsultant();
		storeFrontHomePage.clickOnLoginToTerminateToMyPCAccount();
		s_assert.assertTrue(storeFrontHomePage.verifyAccountTerminationMessage(), "Pc user is not terminated successfully");
		storeFrontHomePage.enterEmailAddress(pcEmailId);
		storeFrontHomePage.clickOnEnrollUnderLastUpline();
		logger.info("After click enroll under last upline we are on "+driver.getCurrentUrl());
		s_assert.assertTrue(driver.getCurrentUrl().contains(bizPWSOfSponserOfPC),"We have not navigated to biz pws of pc Sponser expected"+bizPWSOfSponserOfPC+" While actual on UI is "+driver.getCurrentUrl());

		storeFrontHomePage.selectEnrollmentKitPage(TestConstants.KIT_NAME_BIG_BUSINESS, TestConstants.REGIMEN_NAME_REVERSE);  
		storeFrontHomePage.chooseEnrollmentOption(TestConstants.EXPRESS_ENROLLMENT);
		storeFrontHomePage.enterUserInformationForEnrollmentWithEmailWithoutKit(firstName, lastName, pcEmailId, password, addressLine1, city, state, postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		s_assert.assertTrue(storeFrontHomePage.verifyPopUpForTermsAndConditions(), "PopUp for terms and conditions is not visible");
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		//Verify the enrolled consultant in RFO database.
		List<Map<String, Object>> enrolledConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACTIVE_ACCOUNT_NUMBER_FROM_EMAIL_ADDRESS,pcEmailId),RFO_DB);
		String accountNumberOfUpgradedPC = String.valueOf(getValueFromQueryResult(enrolledConsultantList, "AccountNumber"));
		List<Map<String, Object>> accountTypeList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_TYPE_ID_FROM_ACCOUNT_NUMBER,accountNumberOfUpgradedPC),RFO_DB);
		String accountTypeIDOfUpgradedPC = String.valueOf(getValueFromQueryResult(accountTypeList, "AccountTypeID"));
		s_assert.assertTrue(accountTypeIDOfUpgradedPC.equals("1"), "PC user account is not upgraded to consultant account in RFO database.");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- Create PC via cart checkout signup and then uncheck the PC checkbox, complete the flow
	 *  Validation: 
	 *  *RC user should be enrolled
	 *  * PC congrats message should NOT be present
	 * @throws InterruptedException
	 */
	//Hybris Project-3899:Downgrade to RC on the Summary/Shipment page
	@Test(priority=17)
	public void testDowngradeToRCOnTheSummary_3899() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);  
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuyWithoutFilter();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		storeFrontHomePage.enterNewPCDetails(firstName,lastName,emailAddress, password);
		storeFrontHomePage.clickYesIWantToJoinPCPerksCB();
		storeFrontHomePage.enterMainAccountInfo();
		logger.info("Main account details entered");
		storeFrontHomePage.clickOnContinueWithoutSponsorLink();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		//Enter Billing Profile
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		s_assert.assertFalse(storeFrontHomePage.verifyPCPerksTermsAndConditionsPopup(),"pc perks terms and condition present");
		s_assert.assertTrue(storeFrontHomePage.validateTermsAndConditionsForRC(), "Terms and Conditions & 'this order cannot be cancelled.' is not present on UI");
		storeFrontHomePage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.isOrderPlacedSuccessfully(),"RC order is  not placed successfully");
		s_assert.assertFalse(storeFrontHomePage.isPCEnrolledCongratsMessagePresent(), "'Welcome to Rodan + Fields PC Perks' message has not appeared");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:-Login with RC and upgrade it to PC, then use the PC email id to upgrade to Consultant(use diff sponsor)
	 *  Validation: 
	 *  *PC perks checkbox is checked after clicking it
	 *  *PC termination message during upgradation to consultant
	 *  * PC congrats message
	 * @throws InterruptedException
	 */
	// Hybris Project-4718: RC 2 PC , PC to consultant under Same & Different sponsor
	@Test(enabled=false)//Needs update
	public void testRCToPcAndPCToConsultantUnderSameAndDifferentSponsor_4718() throws InterruptedException{
		String rcmailId=null;
		String accountNumber=null;
		List<Map<String, Object>> randomRCUserList =  null;
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String lastName = "lN";
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		for(int i=1;i<=3;i++){
			randomRCUserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),RFO_DB);
			accountNumber=String.valueOf(getValueFromQueryResult(randomRCUserList, "AccountNumber"));
			rcmailId=String.valueOf(getValueFromQueryResult(DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_EMAIL_FROM_ACC_NUMBER,accountNumber),RFO_DB), "EmailAddress"));
			storeFrontRCUserPage = storeFrontHomePage.loginAsRCUser(rcmailId, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+rcmailId);
				if(driver.getCountry().equalsIgnoreCase("ca")||driver.getCountry().equalsIgnoreCase("au")){
					driver.get(driver.getURL()+"/"+driver.getCountry());	
				}
				else
					driver.get(driver.getURL());
			}
			else
				break;
		}
		logger.info("login is successful");		
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();  
		//Select a product and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		storeFrontHomePage.clickYesIWantToJoinPCPerksCB();
		s_assert.assertTrue(storeFrontHomePage.verifyPCPerksCheckBoxIsSelected(), "Yes I want to join pc perks checkboz is not selected");
		storeFrontHomePage.searchCID(accountNumber);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();			
		storeFrontHomePage.clickOnContinueWithoutSponsorLink();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.checkIAcknowledgePCAccountCheckBox();
		storeFrontHomePage.checkPCPerksTermsAndConditionsCheckBox();
		storeFrontHomePage.clickPlaceOrderBtn();
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(sponsor);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.selectEnrollmentKitPage(TestConstants.KIT_NAME_BIG_BUSINESS, TestConstants.REGIMEN_NAME_REVERSE);  
		storeFrontHomePage.chooseEnrollmentOption(TestConstants.EXPRESS_ENROLLMENT);
		storeFrontHomePage.enterEmailAddress(rcmailId);
		storeFrontHomePage.enterPasswordForUpgradePcToConsultant();
		storeFrontHomePage.clickOnLoginToTerminateToMyPCAccount();
		s_assert.assertTrue(storeFrontHomePage.verifyAccountTerminationMessage(), "Pc user is not terminated successfully");
		storeFrontHomePage.enterUserInformationForEnrollmentWithEmailWithoutKit(firstName, lastName, rcmailId, password, addressLine1, city, state, postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		s_assert.assertTrue(storeFrontHomePage.verifyPopUpForTermsAndConditions(), "PopUp for terms and conditions is not visible");
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		s_assert.assertAll(); 
	}

	// Hybris Project-4775 :: Version : 1 :: Consultant to PC downgrade and PC to Consultant for same User for US
	@Test(enabled=false)
	public void testConsultantToPCDowngradeAndPCToConsultantForSameForUS_4775() throws InterruptedException{
		String socialInsuranceNumberForReEnrollment = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		//List<Map<String, Object>> randomConsultantList =  null;
		String consultantEmailID = null;	
		//String accountID = null;
		String lastName = "lN";
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME_US+randomNum;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		List<Map<String, Object>> randomConsultantList =  null;
		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment(),driver.getCountry(),countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error") || (driver.getCurrentUrl().contains("corp"));
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		}
		//Deactivate the consultant
		logger.info("login is successful");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountTerminationPage = storeFrontAccountInfoPage.clickTerminateMyAccount();
		storeFrontAccountTerminationPage.fillTheEntriesAndClickOnSubmitDuringTermination();
		s_assert.assertTrue(storeFrontAccountTerminationPage.verifyAccountTerminationIsConfirmedPopup(), "Account still exist");
		storeFrontAccountTerminationPage.clickOnConfirmTerminationPopup();
		storeFrontAccountTerminationPage.clickOnCloseWindowAfterTermination();
		storeFrontHomePage.clickOnCountryAtWelcomePage();
		driver.get(driver.getURL()+"/"+driver.getCountry());
		// Enroll the PC Again
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontHomePage.selectProductAndProceedToBuyWithoutFilter();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		storeFrontHomePage.enterEmailAddress(consultantEmailID);
		//s_assert.assertTrue(storeFrontHomePage.verifyInvalidSponsorPopupIsPresent(), "Invalid Sponsor popup is not present");
		storeFrontHomePage.clickOnEnrollUnderLastUpline();

		// Enroll Deactivated Consultant as PC
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontHomePage.selectProductAndProceedToBuy();

		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		//storeFrontHomePage.enterEmailAddress(consultantEmailID);
		storeFrontHomePage.enterNewPCDetails(firstName, lastName, consultantEmailID,password);
		//		s_assert.assertTrue(storeFrontHomePage.verifyInvalidSponsorPopupIsPresent(), "Invalid Sponsor popup is not present");
		//		storeFrontHomePage.clickOnEnrollUnderLastUpline();

		//Click on Check out
		//storeFrontHomePage.clickOnCheckoutButton();
		//Enter the Main account info and DO NOT check the "Become a Preferred Customer" and click next
		storeFrontHomePage.enterMainAccountInfo();
		logger.info("Main account details entered");
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		//Enter Billing Profile
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
		storeFrontHomePage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.isWelcomePCPerksMessageDisplayed(), "welcome PC perks message should be displayed");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		driver.get(driver.getURL()+"/"+driver.getCountry());
		try{
			storeFrontHomePage.loginAsPCUser(consultantEmailID, password);
		}catch(Exception e){

		}
		//		// Upgrade the PC as Consultant
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		//		List<Map<String, Object>> randomConsultantSponsorList =  null;
		//		randomConsultantSponsorList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment(),driver.getCountry(),countryId),RFO_DB);
		//		String accountID = String.valueOf(getValueFromQueryResult(randomConsultantSponsorList, "AccountID"));  
		//		List<Map<String, Object>>sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,accountID),RFO_DB);
		//		String sponsor = (String) getValueFromQueryResult(sponsorIdList, "AccountNumber");
		storeFrontHomePage.searchCID(sponsor);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);		
		storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
		storeFrontHomePage.enterEmailAddress(consultantEmailID);
		s_assert.assertTrue(storeFrontHomePage.verifyUpradingToConsulTantPopup(), "Upgrading to a consultant popup is not present");
		storeFrontHomePage.enterPasswordForUpgradePcToConsultant();
		storeFrontHomePage.clickOnLoginToTerminateToMyPCAccount();
		storeFrontHomePage.enterEmailAddress(consultantEmailID);
		storeFrontHomePage.clickOnEnrollUnderLastUpline();
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);		
		storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
		storeFrontHomePage.enterEmailAddress(consultantEmailID);
		storeFrontHomePage.enterPassword(password);
		storeFrontHomePage.enterConfirmPassword(password);
		storeFrontHomePage.enterFirstName(firstName);
		storeFrontHomePage.enterLastName(lastName);
		storeFrontHomePage.enterAddressLine1(addressLine1);
		storeFrontHomePage.enterCity(city);
		storeFrontHomePage.selectProvince(state);
		storeFrontHomePage.enterPostalCode(postalCode);
		storeFrontHomePage.enterPhoneNumber(phoneNumber);		
		storeFrontHomePage.clickNextButton();		
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumberForReEnrollment);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		//		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		//		storeFrontConsultantPage.clickOnWelcomeDropDown();
		//		storeFrontOrdersPage = storeFrontConsultantPage.clickOrdersLinkPresentOnWelcomeDropDown();
		//		// Verify Order is present
		//		s_assert.assertTrue(storeFrontOrdersPage.verifyAdhocOrderIsPresent(), "Adhoc Order is not present");
		s_assert.assertAll();
	}

	//Hybris Project-4777:Consultant to RC downgrade(Cross Country)
	@Test(enabled=false)//WIP
	public void testConsultantToRCDowngrade_4777() throws InterruptedException{
		String crossCountryID = null;
		String crossCountry= null;
		if(country.equalsIgnoreCase("au")){
			crossCountryID="40";
			crossCountry="ca";
		}
		else if(country.equalsIgnoreCase("ca")){
			crossCountryID="14";
			crossCountry="au";
		}
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		//String  consultantEmailAddress=TestConstants.FIRST_NAME+randomNum+TestConstants.EMAIL_ADDRESS_SUFFIX;
		//String emailAddress = firstName+randomNum+TestConstants.EMAIL_ADDRESS_SUFFIX;
		List<Map<String, Object>> randomConsultantList =  null;
		driver.get(driver.getURL()+"/"+country);
		String consultantEmailID = null;
		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment(),country,countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error") || (driver.getCurrentUrl().contains("corp"));
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				driver.get(driver.getURL()+"/"+country);
			}
			else
				break;
		}
		//Terminate consultant Account
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyAccountInfoPageIsDisplayed(),"Account Info page has not been displayed");
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountTerminationPage=storeFrontAccountInfoPage.clickTerminateMyAccount();
		s_assert.assertTrue(storeFrontAccountTerminationPage.verifyAccountTerminationPageIsDisplayed(),"Account Termination Page has not been displayed");
		storeFrontAccountTerminationPage.selectTerminationReason();
		storeFrontAccountTerminationPage.enterTerminationComments();
		storeFrontAccountTerminationPage.selectCheckBoxForVoluntarilyTerminate();
		storeFrontAccountTerminationPage.clickSubmitToTerminateAccount();
		s_assert.assertTrue(storeFrontAccountTerminationPage.verifyPopupHeader(),"Account termination Page Pop Up Header is Present");
		storeFrontAccountTerminationPage.clickOnConfirmTerminationPopup();
		storeFrontAccountTerminationPage.clickOnCloseWindowAfterTermination();
		//navigate to  Corp Site And start enrolling RC user on current country
		driver.get(driver.getURL()+"/"+crossCountry);
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		// Click on our product link that is located at the top of the page and then click in on quick shop
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		//Select a product and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		//Select a product and proceed to buy it
		//		String countryName = null;
		//		if(driver.getCountry().equalsIgnoreCase("ca")){
		//			countryName = "us";
		//		}else{
		//			countryName = "ca";
		//		}
		storeFrontHomePage.enterNewRCDetails(rcFirstName, rcLastName+randomNum, consultantEmailID,password);
		//Enter the Main account info and DO NOT check the "Become a Preferred Customer" and click next
		storeFrontHomePage.enterMainAccountInfo();
		logger.info("Main account details entered");
		//storeFrontHomePage.searchCID(sponsor);
		//	   storeFrontHomePage.mouseHoverSponsorDataAndClickContinuePCAndRC(1);
		storeFrontHomePage.clickOnContinueWithoutSponsorLink();		
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		//Enter Billing Profile
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+rcLastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate();
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.isOrderPlacedSuccessfully(), "Order Not placed successfully");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		//		s_assert.assertTrue(driver.getCurrentUrl().contains("corprfo"), "RC is not registered to corporate site");
		//		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		s_assert.assertAll();
	}

	//Hybris Project-4312:Soft-Terminated Consultant becomes a PC
	@Test(enabled=false)//Already covered in 4775
	public void testSoftTerminatedConsultantBecomesAPC_4312() throws InterruptedException {
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		List<Map<String, Object>> randomConsultantList =  null;
		String consultantEmailID = null;
		String accountID = null;
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME_US+randomNum;
		String firstName = TestConstants.FIRST_NAME+randomNum;
		String lastName = "lN";
		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
			accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			logger.info("Account Id of the user is "+accountID);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		}
		logger.info("login is successful");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountTerminationPage = storeFrontAccountInfoPage.clickTerminateMyAccount();
		storeFrontAccountTerminationPage.fillTheEntriesAndClickOnSubmitDuringTermination();
		/*s_assert.assertTrue(storeFrontAccountTerminationPage.verifyAccountTerminationIsConfirmedPopup(), "Account still exist");
				  storeFrontAccountTerminationPage.clickOnCloseWindowAfterTermination();*/
		s_assert.assertTrue(storeFrontAccountTerminationPage.validateConfirmAccountTerminationPopUp(), "confirm account termination pop up is not displayed");
		storeFrontAccountTerminationPage.clickConfirmTerminationBtn();
		s_assert.assertFalse(storeFrontAccountTerminationPage.validateConfirmAccountTerminationPopUp(), "confirm account termination pop up is still displayed");
		storeFrontAccountTerminationPage.clickOnCloseWindowAfterTermination();
		storeFrontHomePage.clickOnCountryAtWelcomePage();
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		s_assert.assertTrue(storeFrontHomePage.isCurrentURLShowsError(),"Terminated User doesn't get Login failed");  
		//click RF logo
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();

		//Select a product with the price less than $80 and proceed to buy it
		//storeFrontHomePage.applyPriceFilterLowToHigh();
		storeFrontHomePage.selectProductAndProceedToBuy();

		//1 product is in the Shopping Cart?
		s_assert.assertTrue(storeFrontHomePage.verifyNumberOfProductsInCart("1"), "number of products in the cart is NOT 1");
		logger.info("1 product is successfully added to the cart");
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();

		storeFrontHomePage.enterEmailAddress(consultantEmailID);
		//s_assert.assertTrue(storeFrontHomePage.verifyInvalidSponsorPopupIsPresent(), "Invalid Sponsor popup is not present");
		//click Enroll under last upline..
		storeFrontHomePage.clickOnEnrollUnderLastUpline();

		// Enroll Deactivated Consultant as PC
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();

		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();

		storeFrontHomePage.enterNewPCDetails(firstName, lastName, consultantEmailID,password);

		storeFrontHomePage.enterMainAccountInfo();
		logger.info("Main account details entered");
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		//Enter Billing Profile
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		//storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
		storeFrontHomePage.checkIAcknowledgePCAccountCheckBox();
		storeFrontHomePage.checkPCPerksTermsAndConditionsCheckBox();
		storeFrontHomePage.clickPlaceOrderBtn();
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		storeFrontHomePage.clickOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontHomePage.verifyEditPcPerksIsPresentInWelcomDropdownForUpgrade(),"Edit Pc Perks Link is present for RC User");
		s_assert.assertAll();
	}

	//Hybris Project-4725:Consultant to PC downgrade for US User
	@Test(enabled=false)
	public void testDowngradeConsultantToPC_4725() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		List<Map<String, Object>> randomConsultantList =  null;
		String consultantEmailID = null;
		String accountID = null;
		enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
		regimenName =  TestConstants.REGIMEN_NAME_REDEFINE;
		if(country.equalsIgnoreCase("us")){
			String lastName = "lN";
			String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME_US+randomNum;
			String firstName=TestConstants.FIRST_NAME+randomNum;

			while(true){
				randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
				consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");		
				accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
				logger.info("Account Id of the user is "+accountID);
				storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
				boolean isLoginError = driver.getCurrentUrl().contains("error");
				if(isLoginError){
					logger.info("Login error for the user "+consultantEmailID);
					driver.get(driver.getURL()+"/"+country);
				}
				else
					break;
			}

			//s_assert.assertTrue(storeFrontConsultantPage.verifyConsultantPage(),"Consultant Page doesn't contain Welcome User Message");
			logger.info("login is successful");
			storeFrontConsultantPage.clickOnWelcomeDropDown();
			storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
			storeFrontAccountInfoPage.clickOnYourAccountDropdown();
			storeFrontAccountTerminationPage = storeFrontAccountInfoPage.clickTerminateMyAccount();
			storeFrontAccountTerminationPage.fillTheEntriesAndClickOnSubmitDuringTermination();
			s_assert.assertTrue(storeFrontAccountTerminationPage.verifyAccountTerminationIsConfirmedPopup(), "Account still exist");
			storeFrontAccountTerminationPage.clickOnConfirmTerminationPopup();
			storeFrontAccountTerminationPage.clickOnCloseWindowAfterTermination();
			storeFrontHomePage.clickOnCountryAtWelcomePage();
			storeFrontHomePage.openPWSSite(country, env);
			// Enroll the PC Again
			storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
			// Products are displayed?
			s_assert.assertTrue(storeFrontHomePage.areProductsDisplayed(), "quickshop products not displayed");
			logger.info("Quick shop products are displayed");
			storeFrontHomePage.selectProductAndProceedToBuy();
			//Cart page is displayed?
			s_assert.assertTrue(storeFrontHomePage.isCartPageDisplayed(), "Cart page is not displayed");
			logger.info("Cart page is displayed");
			//Click on Check out
			storeFrontHomePage.clickOnCheckoutButton();
			//Log in or create an account page is displayed?
			s_assert.assertTrue(storeFrontHomePage.isLoginOrCreateAccountPageDisplayed(), "Login or Create Account page is NOT displayed");
			logger.info("Login or Create Account page is displayed");
			storeFrontHomePage.enterEmailAddress(consultantEmailID);
			s_assert.assertTrue(storeFrontHomePage.verifyInvalidSponsorPopupIsPresent(), "Invalid Sponsor popup is not present");
			storeFrontHomePage.clickOnEnrollUnderLastUpline();
			// Enroll Deactivated Consultant as PC
			storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
			// Products are displayed?
			s_assert.assertTrue(storeFrontHomePage.areProductsDisplayed(), "quickshop products not displayed");
			logger.info("Quick shop products are displayed");
			storeFrontHomePage.selectProductAndProceedToBuy();
			//Click on Check out
			storeFrontHomePage.clickOnCheckoutButton();
			//Log in or create an account page is displayed?
			s_assert.assertTrue(storeFrontHomePage.isLoginOrCreateAccountPageDisplayed(), "Login or Create Account page is NOT displayed");
			logger.info("Login or Create Account page is displayed");
			//storeFrontHomePage.enterEmailAddress(consultantEmailID);
			storeFrontHomePage.enterNewPCDetails(firstName, lastName, consultantEmailID,password);
			//Click on Check out
			//storeFrontHomePage.clickOnCheckoutButton();
			//Enter the Main account info and DO NOT check the "Become a Preferred Customer" and click next
			storeFrontHomePage.enterMainAccountInfo();
			logger.info("Main account details entered");
			//storeFrontHomePage.clickOnContinueWithoutSponsorLink();
			storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
			storeFrontHomePage.clickOnShippingAddressNextStepBtn();
			//Enter Billing Profile
			storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
			storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
			storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
			storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
			storeFrontHomePage.selectNewBillingCardAddress();
			storeFrontHomePage.clickOnSaveBillingProfile();
			storeFrontHomePage.clickOnBillingNextStepBtn();
			storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
			storeFrontHomePage.clickPlaceOrderBtn();
			storeFrontHomePage.clickOnRodanAndFieldsLogo();
			s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
			s_assert.assertTrue(storeFrontHomePage.verifyCurrentUrlContainComSite(), "Pc user not redirected to com site");
			s_assert.assertAll();
		}else{
			logger.info("US specific Test,not for CA");
		}

	}

	//Hybris Project-3900:Downgrade to RC on the Review / Order summary page
	@Test(enabled=false)//duplicate as TC-3898
	public void testDowngradePCToRCAtReviewOrderSummaryPage_3900() throws InterruptedException{
		int randomNum =  CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstants.FIRST_NAME+randomNum;
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String lastName = TestConstants.LAST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		String PWS = storeFrontHomePage.getBizPWS(country, env);
		PWS = storeFrontHomePage.convertBizSiteToComSite(PWS);
		storeFrontHomePage.openPWS(PWS);
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		storeFrontHomePage.clickOnCheckoutButton();
		storeFrontHomePage.enterNewPCDetails(firstName, lastName, emailAddress,password);
		storeFrontHomePage.enterMainAccountInfo();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		//Enter billing info
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		//Uncheck PC Perks Checkbox on Review/order summary page
		s_assert.assertTrue(storeFrontHomePage.validatePCPerksCheckBoxIsDisplayed(),"PC Perks checkbox is not present");
		s_assert.assertTrue(storeFrontHomePage.verifyPCPerksCheckBoxIsSelected(),"pc perks checbox is not selected");
		storeFrontHomePage.checkPCPerksCheckBox();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		s_assert.assertFalse(storeFrontHomePage.verifyPCPerksCheckBoxIsSelected(),"pc perks checbox is selected");
		storeFrontHomePage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.isOrderPlacedSuccessfully(),"Order is not placed successfully");
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		storeFrontHomePage.clickOnWelcomeDropDown();
		s_assert.assertFalse(storeFrontHomePage.verifyEditPcPerksIsPresentInWelcomDropdownForUpgrade(),"Edit Pc Perks Link is present for RC User");
		s_assert.assertAll();
	}

	// Hybris Project-3896:Downgrade to RC on the Main Account page
	@Test(enabled=false)//covered in TC -3898
	public void testDowngradePCToRCAtMainAccountInfoPage_3896() throws InterruptedException{
		int randomNum =  CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstants.FIRST_NAME+randomNum;
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String lastName = TestConstants.LAST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;

		String PWS = storeFrontHomePage.getBizPWS(country, env);
		PWS = storeFrontHomePage.convertBizSiteToComSite(PWS);
		storeFrontHomePage.openPWS(PWS);
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		storeFrontHomePage.clickOnCheckoutButton();
		storeFrontHomePage.enterNewPCDetails(firstName, lastName, emailAddress,password);
		//Uncheck PC Perks Checkbox on Main Account Info/order summary page
		s_assert.assertTrue(storeFrontHomePage.validatePCPerksCheckBoxIsDisplayed(),"PC Perks checkbox is not present");
		s_assert.assertTrue(storeFrontHomePage.verifyPCPerksCheckBoxIsSelected(),"pc perks checbox is not selected");
		storeFrontHomePage.checkPCPerksCheckBox();
		s_assert.assertFalse(storeFrontHomePage.verifyPCPerksCheckBoxIsSelected(),"pc perks checbox is selected");
		//Enter main account info
		storeFrontHomePage.enterMainAccountInfo();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		//Enter billing info
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.isOrderPlacedSuccessfully(),"Order is not placed successfully");
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		storeFrontHomePage.clickOnWelcomeDropDown();
		s_assert.assertFalse(storeFrontHomePage.verifyEditPcPerksIsPresentInWelcomDropdownForUpgrade(),"Edit Pc Perks Link is present for RC User");
		s_assert.assertAll();
	}

	//Hybris Project-2186:PC2RC: RC enrollment add already exist PC email ID: Verify Popup
	@Test(enabled=false)//Already covered 2199
	public void testRcEnrollmentAddAlreadyExistPcEmailId_VerifyPopUp_2186() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String firstName=TestConstants.FIRST_NAME+randomNum;
		List<Map<String, Object>> randomPCUserList =  null;
		String pcUserEmailID = null;
		randomPCUserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
		pcUserEmailID = (String) getValueFromQueryResult(randomPCUserList, "UserName");  
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		// Products are displayed?
		s_assert.assertTrue(storeFrontHomePage.areProductsDisplayed(), "quickshop products not displayed");
		logger.info("Quick shop products are displayed");
		//Select a product and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Cart page is displayed?
		s_assert.assertTrue(storeFrontHomePage.isCartPageDisplayed(), "Cart page is not displayed");
		logger.info("Cart page is displayed");
		storeFrontHomePage.clickOnCheckoutButton();
		s_assert.assertTrue(storeFrontHomePage.isLoginOrCreateAccountPageDisplayed(), "Login or Create Account page is NOT displayed");
		logger.info("Login or Create Account page is displayed");
		storeFrontHomePage.enterNewRCDetails(firstName, pcUserEmailID);
		s_assert.assertTrue(storeFrontHomePage.verifyPresenceOfPopUpForExistingPC(),"Pop Up is not present");
		s_assert.assertAll();
	}

	//Hybris Project-2311:Upgrade RC user to PC User during Checkout and check Prices
	@Test(enabled=false)//covered in TC-4694
	public void testUpgradeRCUserToPCDuringCheckOutChkPrices_2311() throws InterruptedException	{
		List<Map<String, Object>> randomRCList =  null;
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);  
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String lastName = "lN";
		String rcUserEmailID =null;
		String accountId = null;
		while(true){
			randomRCList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),RFO_DB);
			rcUserEmailID = (String) getValueFromQueryResult(randomRCList, "UserName");  
			accountId = String.valueOf(getValueFromQueryResult(randomRCList, "AccountID"));
			logger.info("Account Id of the user is "+accountId);

			storeFrontRCUserPage = storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
			boolean isError = driver.getCurrentUrl().contains("error");
			if(isError){
				logger.info("login error for the user "+rcUserEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		} 
		logger.info("login is successful");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();  
		//validate user is able to see various product prices attached to the product. on quick shop page
		s_assert.assertTrue(storeFrontHomePage.validateRetailProductProcesAttachedToProduct(), "retail product prices attached to product is not displayed");
		//Add product to cart & checkout..
		storeFrontHomePage.selectProductAndProceedToBuy();
		//fetch the pricing of the product selected before chkout..
		double productPricingBefore=storeFrontHomePage.getProductPricingOnCartPage();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		storeFrontHomePage.checkYesIWantToJoinPCPerksCBOnSummaryPage();
		//Again fetch the pricing of the product on summary page after checking the PC Perks CB..
		double productProicingAfter=storeFrontHomePage.getProductPricingOnSummaryPage();
		if(productProicingAfter<productPricingBefore){
			s_assert.assertTrue(true, "price change(decrement) doesn't occurs");
		}else{
			s_assert.assertTrue(false);
		}
		storeFrontHomePage.clickOnContinueWithoutSponsorLink();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		//Enter Billing Profile
		storeFrontHomePage.clickAddNewBillingProfileLink();
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.checkIAcknowledgePCAccountCheckBox();
		storeFrontHomePage.checkPCPerksTermsAndConditionsCheckBox();
		storeFrontHomePage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.isWelcomePCPerksMessageDisplayed(), "welcome PC perks message should be displayed");
		s_assert.assertAll();
	}

	//Hybris Project-2200 :: Version : 1 :: Verify Existing Retail Customer popup during RC registration
	@Test(enabled=false)//Already covered 2199
	public void testExistingRetailCustomerPopupDuringRCRegistration_2200() throws InterruptedException {

	}

	//Hybris Project-2201 :: Version : 1 :: Verify Existing Consultant popup during RC registration
	@Test(enabled=false)//Already covered 2199
	public void testExistingConsultantPopupDuringRCRegistration_2201() throws InterruptedException {

	}
}
