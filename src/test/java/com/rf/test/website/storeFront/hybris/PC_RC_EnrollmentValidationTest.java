	package com.rf.test.website.storeFront.hybris;

import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.rf.core.utils.CommonUtils;
import com.rf.core.utils.DBUtil;
import com.rf.core.website.constants.TestConstants;
import com.rf.core.website.constants.dbQueries.DBQueries_RFO;
import com.rf.test.website.RFWebsiteBaseTest;

public class PC_RC_EnrollmentValidationTest extends RFWebsiteBaseTest{
	private static final Logger logger = LogManager
			.getLogger(PC_RC_EnrollmentValidationTest.class.getName());

	/***
	 * Test Summary:- Start from corp site, During RC enrollment verify Sponsor Search section
	 *  Validation: 
	 *  * Request a consultant should NOT be  present
	 *  * On Invalid sponsor id search,  'results not found' msg
	 *  * On Invalid sponsor name search, 'results not found' msg
	 *  * On valid sponsor id search, No 'results not found' msg
	 *  * On clicking continue with sponsor, RF corporate is selected
	 *  * In short:- functionalities tested are search again, not your sponsor, continue without sponsor
	 * @throws InterruptedException
	 */
	//Hybris Project-5138:Verify Change Sponsor functionality for RC
	// Hybris Project-1305:From Corporate user should able to change sponsor RC
	@Test (priority=1) 
	public void testCorporateUserShouldAbleToChangeSponsorRC_1305_5138() throws InterruptedException	 {
		//------------------------------------------LOCAL VARIABLES-------------------------------------------------------------------------
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);  
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		//------------------------------------------TEST---------------------------------------------------------------------------------------------
		 storeFrontHomePage.addAProductToCartAndCheckout();
		storeFrontHomePage.enterNewRCDetails(firstName,lastName,emailAddress, password);
		s_assert.assertFalse(storeFrontHomePage.isRequestSponsorLinkPresent(), "request a sponsor link SHOULD NOT be  present for RC(Corp)");
		storeFrontHomePage.searchCID(TestConstants.INVALID_SPONSOR_ID);
		s_assert.assertTrue(storeFrontHomePage.isNoResultFoundMsgDisplayedForRCPCSponsorSearch(), "No result found message SHOULD be displayed for invalid sponsor id");
		storeFrontHomePage.clickSearchAgain();
		storeFrontHomePage.searchCID(TestConstants.INVALID_SPONSOR_NAME);
		s_assert.assertTrue(storeFrontHomePage.isNoResultFoundMsgDisplayedForRCPCSponsorSearch(), "No result found message SHOULD be displayed for invalid sponsor name");
		storeFrontHomePage.clickSearchAgain();
		storeFrontHomePage.searchCID(sponsor);
		s_assert.assertFalse(storeFrontHomePage.isNoResultFoundMsgDisplayedForRCPCSponsorSearch(), "No result found message SHOULD NOT be displayed");
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();
		storeFrontHomePage.clickOnNotYourSponsorLink();
		storeFrontHomePage.clickOnContinueWithoutSponsorLink();
		s_assert.assertTrue(storeFrontHomePage.isSponsorNameContainRFCorporate(), "sponsor Name does not contain RF Corporate");
		s_assert.assertAll(); 
	}

	//Hybris Project-2248 :: Version : 1 :: Verify Request a sponsor functionality
	//Hybris Project-4670:Preffered cusotmer flow >> Request a Sponsor
	@Test (priority=2)  
	public void testPCFlowRequestASponsor_4670_2248() throws InterruptedException{
		//------------------------------------------LOCAL VARIABLES-------------------------------------------------------------------------
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);  
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		//------------------------------------------TEST---------------------------------------------------------------------------------------------
		storeFrontHomePage.addAProductToCartAndCheckout();
		storeFrontHomePage.enterNewPCDetails(firstName,lastName,emailAddress, password);
		storeFrontHomePage.searchCID(TestConstants.INVALID_SPONSOR_ID);
		s_assert.assertTrue(storeFrontHomePage.isNoResultFoundMsgDisplayedForRCPCSponsorSearch(), "No result found message SHOULD be displayed for invalid sponsor id");
		storeFrontHomePage.clickSearchAgain();
		storeFrontHomePage.searchCID(TestConstants.INVALID_SPONSOR_NAME);
		s_assert.assertTrue(storeFrontHomePage.isNoResultFoundMsgDisplayedForRCPCSponsorSearch(), "No result found message SHOULD be displayed for invalid sponsor name");
		storeFrontHomePage.clickSearchAgain();
		storeFrontHomePage.searchCID(sponsor);
		s_assert.assertFalse(storeFrontHomePage.isNoResultFoundMsgDisplayedForRCPCSponsorSearch(), "No result found message SHOULD NOT be displayed");
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();
		storeFrontHomePage.clickOnNotYourSponsorLink();
		storeFrontHomePage.clickOnContinueWithoutSponsorLink();
		s_assert.assertTrue(storeFrontHomePage.isSponsorNameContainRFCorporate(), "sponsor Name does not contain RF Corporate");
		storeFrontHomePage.clickOnNotYourSponsorLink();
		storeFrontHomePage.clickOnRequestASponsorBtn();
		s_assert.assertTrue(storeFrontHomePage.isYourConsultantPresentWhilePCRCEnrollment(),"Sponsor SHOULD be present after clicking request a consultant");	
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- Start from biz PWS site, During PC/RC enrollment verify Sponsor Search section
	 *  Validation: 
	 *  * On Invalid sponsor search, No 'Continue Without sponsor' link
	 *  * On Invalid sponsor search, No 'Request a sponsor' link
	 *  * On valid sponsor search, No 'results not found' msg
	 *  * On valid sponsor search, results present
	 * @throws InterruptedException
	 */
	// Hybris Project-3858:Preferred Customer Flow- checking Not Your sponsor link and default sponsor
	// Hybris Project-1306 :: Version : 1 :: Biz: PC Enroll- Not my sponsor link
	// Hybris Project-3888:BIZ:Verify Search Again functionality on Sponsor Search section
	// Hybris Project-5139:Search Sponsor with PWS for PC
	@Test (priority=3) 
	public void testSearchSponsorWithPWSForPC_5139_3888_1306_3858() throws InterruptedException{
		//-----------------------------------------LOCAL VARIABLES---------------------------------------------------------------------------------------------
		int randomNum = CommonUtils.getRandomNum(10000, 1000000); 
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		String PWS = storeFrontHomePage.getBizPWS(country, env);
		//------------------------------------------TEST-------------------------------------------------------------------------------------------------------------
		storeFrontHomePage.openPWS(PWS);
		storeFrontHomePage.addAProductToCartAndCheckout();
		storeFrontHomePage.enterNewPCDetails(firstName,lastName,emailAddress, password);
		s_assert.assertTrue(storeFrontHomePage.isYourConsultantPresentWhilePCRCEnrollment(),"Sponsor SHOULD BE already present");
		s_assert.assertFalse(storeFrontHomePage.isRequestSponsorLinkPresent(),"request a consultant SHOULD NOT be present for pws sites");
		s_assert.assertFalse(storeFrontHomePage.isContinueWithoutSponserLinkDisplayed(),"continue without sponsor SHOULD NOT be present for pws sites");
		s_assert.assertTrue(storeFrontHomePage.isNotYourSponsorLinkPresent(),"Not your sponsor link SHOULD BE present for pws site");
		storeFrontHomePage.clickOnNotYourSponsorLink();
		storeFrontHomePage.searchCID(sponsor);
		storeFrontHomePage.clickOnNotYourSponsorLink();
		storeFrontHomePage.searchCID(TestConstants.INVALID_SPONSOR_ID);
		s_assert.assertFalse(storeFrontHomePage.verifyContinueWithoutSponsorLinkIsPresent(), "continue without sponsor link SHOULD NOT be  present");
		s_assert.assertFalse(storeFrontHomePage.isRequestSponsorLinkPresent(), "request a sponsor link SHOULD NOT be  present");
		storeFrontHomePage.clickOnNotYourSponsorLink();
		storeFrontHomePage.searchCID(sponsor);
		s_assert.assertFalse(storeFrontHomePage.isNoResultFoundMsgDisplayedForRCPCSponsorSearch(), "No result found message SHOULD NOT be displayed");
		s_assert.assertTrue(storeFrontHomePage.validateUserAbleToSerachSponsorAndContinueFlow(), "User is not able to search sponsor and thus continue with Flow!!");
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		s_assert.assertTrue(storeFrontHomePage.isYourConsultantPresentWhilePCRCEnrollment(),"Sponsor SHOULD BE  present after searching");
		s_assert.assertTrue(storeFrontHomePage.isNotYourSponsorLinkPresent(),"Not your sponsor link SHOULD BE present after search");
		s_assert.assertAll(); 
	}

	/***
	 * Test Summary:- Start from com pws site, during RC enrollment enter sponsor with pws and complete enrollment
	 *  Validation: 
	 *  * User should be on same pws from which it started
	 * @throws InterruptedException
	 */
	// Hybris Project-3854:Register as RC with Different CA Sponsor WITH Pulse(COM)
	@Test (priority=4)  
	public void testRegisterAsRCWithDifferentSponserWithPulseCOM_3854() throws InterruptedException {
		//-----------------------------------------LOCAL VARIABLES---------------------------------------------------------------------------------------------
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		List<Map<String, Object>> sponserList =  null;
		String sponserHavingPulse = null;
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		String pws = null;
		String currentPWSUrl=null;
		List<Map<String, Object>> sponsorIdList=null;
		String sponsorID=null;
		//-----------------------------------------DB QUERIES-------------------------------------------------------------------------------------------------------------
		sponserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment(),driver.getCountry(),countryId),RFO_DB);
		sponserHavingPulse = String.valueOf(getValueFromQueryResult(sponserList, "AccountID"));
		sponsorIdList= DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,sponserHavingPulse),RFO_DB);
		sponsorID = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
		//-----------------------------------------TEST------------------------------------------------------------------------------------------------------------
		pws = storeFrontHomePage.convertBizSiteToComSite(storeFrontHomePage.openPWSSite(country, env));
		storeFrontHomePage.openURL(pws);
		storeFrontHomePage.addAProductToCartAndCheckout();
		storeFrontHomePage.enterNewRCDetails(firstName,lastName,emailAddress, password);
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		storeFrontHomePage.clickOnNotYourSponsorLink();
		storeFrontHomePage.searchCID(sponsorID);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		storeFrontHomePage.enterBillingDetailsAndSave(cardNumber, firstName+" "+lastName, cardExpMonth, cardExpYear, CVV);
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickPlaceOrderBtn();
		currentPWSUrl=driver.getCurrentUrl();
		s_assert.assertFalse(storeFrontHomePage.verifyPWSAfterSuccessfulEnrollment(pws,currentPWSUrl),"PWS of the RC after enrollment is same as the one it started enrollment");
		s_assert.assertAll(); 
	}

	/***
	 * Test Summary:- Start from biz pws site, during RC enrollment enter sponsor with pws and complete enrollment
	 *  Validation: 
	 *  * User should be on same pws from which it started
	 * @throws InterruptedException
	 */
	// Hybris Project-3774:Register as RC with Different CA Sponsor WITH Pulse(BIZ)
	@Test (priority=5)
	public void testRegisterAsRCWithDifferentSponserWithPulseBIZ_3774() throws InterruptedException {
		//-----------------------------------------LOCAL VARIABLES---------------------------------------------------------------------------------------------
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		List<Map<String, Object>> sponserList =  null;
		List<Map<String, Object>> sponsorIdList = null;
		String sponserHavingPulse = null;
		String sponsorID=null;
		String currentPWSUrl=null;
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		String pws =null;
		//-----------------------------------------DB QUERIES-------------------------------------------------------------------------------------------------------------
		sponserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment(),driver.getCountry(),countryId),RFO_DB);
		sponserHavingPulse = String.valueOf(getValueFromQueryResult(sponserList, "AccountID"));
		sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,sponserHavingPulse),RFO_DB);
		sponsorID = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
		//-----------------------------------------TEST------------------------------------------------------------------------------------------------------------
		pws = storeFrontHomePage.convertComSiteToBizSite(storeFrontHomePage.openPWSSite(country, env));
		storeFrontHomePage.openURL(pws);
		storeFrontHomePage.addAProductToCartAndCheckout();
		storeFrontHomePage.enterNewRCDetails(firstName,lastName,emailAddress, password);
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		storeFrontHomePage.clickOnNotYourSponsorLink();
		storeFrontHomePage.searchCID(sponsorID);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		storeFrontHomePage.enterBillingDetailsAndSave(cardNumber, firstName+" "+lastName, cardExpMonth, cardExpYear, CVV);
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickPlaceOrderBtn();
		currentPWSUrl=driver.getCurrentUrl();
		s_assert.assertFalse(storeFrontHomePage.verifyPWSAfterSuccessfulEnrollment(pws,currentPWSUrl),"PWS of the RC after enrollment is same as the one it started enrollment");
		s_assert.assertAll(); 
	}
	
	/***
	 * Test Summary:- Start from com pws site, during RC enrollment enter sponsor with pws and complete enrollment
	 *  Validation: 
	 *  * User should be on same pws from which it started
	 * @throws InterruptedException
	 */
	//Hybris Project-3855:Register as RC with Same CA Sponsor WITH Pulse(COM)
	@Test (priority=6)
	public void testRegisterAsRCWithSameCASponsorWithPulseCOM_3855() throws InterruptedException{
		//-----------------------------------------LOCAL VARIABLES---------------------------------------------------------------------------------------------
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String prefix = null;
		List<Map<String, Object>> sponserList =  null;
		String sponserHavingPulse = null;
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		String sponsorID=null;
		List<Map<String, Object>> sponsorIdList=null;
		String currentPWSUrl=null;
		String pws=null;
		//-----------------------------------------DB QUERIES---------------------------------------------------------------------------------------------------------------------------------------------------------------
		sponserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment(),driver.getCountry(),countryId),RFO_DB);
		sponserHavingPulse = String.valueOf(getValueFromQueryResult(sponserList, "AccountID"));
		prefix = String.valueOf(getValueFromQueryResult(sponserList, "prefix"));
		sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,sponserHavingPulse),RFO_DB);
		sponsorID = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
		//-----------------------------------------TEST--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		pws=storeFrontHomePage.createPWSFromPrefix(prefix,"com");
		storeFrontHomePage.openURL(pws);
		storeFrontHomePage.addAProductToCartAndCheckout();
		storeFrontHomePage.enterNewRCDetails(firstName,lastName,emailAddress, password);
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		storeFrontHomePage.clickOnNotYourSponsorLink();
		storeFrontHomePage.searchCID(sponsorID);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		storeFrontHomePage.enterBillingDetailsAndSave(cardNumber, firstName+" "+lastName, cardExpMonth, cardExpYear, CVV);
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickPlaceOrderBtn();
		currentPWSUrl=driver.getCurrentUrl();
		s_assert.assertFalse(storeFrontHomePage.verifyPWSAfterSuccessfulEnrollment(pws,currentPWSUrl),"PWS of the RC after enrollment is same as the one it started enrollment");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- Start from biz pws site, during RC enrollment enter sponsor with pws and complete enrollment
	 *  Validation: 
	 *  * User should be on same pws from which it started
	 * @throws InterruptedException
	 */
	//Hybris Project-3722:Register as RC with Same CA Sponsor WITH Pulse(BIZ)
	@Test (priority=7)
	public void testRegisterAsRCWithSameCASponsorWithPulseBIZ_3722() throws InterruptedException{
		//-----------------------------------------LOCAL VARIABLES---------------------------------------------------------------------------------------------
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		String prefix = null;
		List<Map<String, Object>> sponserList =  null;
		List<Map<String, Object>> sponsorIdList=null;
		String sponserHavingPulse = null;
		String sponsorID=null;
		String pws=null;
		String currentPWSUrl=null;
		//-----------------------------------------DB QUERIES---------------------------------------------------------------------------------------------------------------------------------------------------------------
		sponserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment(),driver.getCountry(),countryId),RFO_DB);
		sponserHavingPulse = String.valueOf(getValueFromQueryResult(sponserList, "AccountID"));
		prefix = String.valueOf(getValueFromQueryResult(sponserList, "prefix"));
		sponsorIdList  = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,sponserHavingPulse),RFO_DB);
		sponsorID = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
		//-----------------------------------------TEST--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		pws=storeFrontHomePage.createPWSFromPrefix(prefix,"biz");
		storeFrontHomePage.openURL(pws);
		storeFrontHomePage.addAProductToCartAndCheckout();
		storeFrontHomePage.enterNewRCDetails(firstName,lastName,emailAddress, password);
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		storeFrontHomePage.clickOnNotYourSponsorLink();
		storeFrontHomePage.searchCID(sponsorID);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		storeFrontHomePage.enterBillingDetailsAndSave(cardNumber, firstName+" "+lastName, cardExpMonth, cardExpYear, CVV);
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickPlaceOrderBtn();
		currentPWSUrl=driver.getCurrentUrl();
		s_assert.assertFalse(storeFrontHomePage.verifyPWSAfterSuccessfulEnrollment(pws,currentPWSUrl),"PWS of the RC after enrollment is same as the one it started enrollment");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- Start from biz pws site, during RC enrollment enter sponsor without pulse and complete enrollment
	 *  Validation: 
	 *  * User should be on same pws from which it started
	 * @throws InterruptedException
	 */
	//Hybris Project-3775:Register as RC with Different CA Sponsor WITHOUT Pulse
	@Test (priority=8)
	public void testRegisterAsRCWithDifferentSponserWithoutPulseBIZ_3775() throws InterruptedException {
		//-----------------------------------------LOCAL VARIABLES---------------------------------------------------------------------------------------------
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		List<Map<String, Object>> sponserList =  null;
		String sponsorID=null;
		String pws=null;
		String currentPWSUrl=null;
		//-----------------------------------------DB QUERIES---------------------------------------------------------------------------------------------------------------------------------------------------------------
		sponserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITHOUT_PULSE_RFO,countryId),RFO_DB);
		sponsorID = String.valueOf(getValueFromQueryResult(sponserList, "AccountNumber"));
		//-----------------------------------------TEST--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		pws = storeFrontHomePage.convertComSiteToBizSite(storeFrontHomePage.openPWSSite(country, driver.getEnvironment()));
		storeFrontHomePage.addAProductToCartAndCheckout();
		storeFrontHomePage.enterNewRCDetails(firstName,lastName,emailAddress, password);
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		storeFrontHomePage.clickOnNotYourSponsorLink();
		storeFrontHomePage.searchCID(sponsorID);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		storeFrontHomePage.enterBillingDetailsAndSave(cardNumber, firstName+" "+lastName, cardExpMonth, cardExpYear, CVV);
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickPlaceOrderBtn();
		currentPWSUrl=driver.getCurrentUrl();
		s_assert.assertFalse(storeFrontHomePage.verifyPWSAfterSuccessfulEnrollment(pws,currentPWSUrl),"PWS of the RC after enrollment is same as the one it started enrollment");
		s_assert.assertAll(); 
	}

	
	/***
	 * Test Summary:- Start from pws site, enroll RC without entering any billing, shipping details and without placing order
	 *  Validation: 
	 *  * Welcome dropdown is coming after enrolling RC without order
	 * @throws InterruptedException
	 */
	//Hybris Project-3856 :: Version : 1 :: Register as RC WITHOUT creating an ORDER
	@Test (priority=9)
	public void testRegisterAsRcWithoutCreatingAnOrder_3856() throws InterruptedException{
		//-----------------------------------------LOCAL VARIABLES---------------------------------------------------------------------------------------------
		int randomNum =  CommonUtils.getRandomNum(10000, 1000000);
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		//-----------------------------------------TEST--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		storeFrontHomePage.openPWSSite(country, env);
		storeFrontHomePage.addAProductToCartAndCheckout();
		storeFrontHomePage.enterNewRCDetails(firstName,lastName,emailAddress, password);
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		s_assert.assertAll();
	}

	//Hybris Project-2192:Enroll RC who lives in Quebec and then try to upgrade his membership from RC to Consultant. 
	@Test (priority=10) 
	public void testQuebecRCEnrollmentAndUpgradeMembershipFromRCToConsultant_2192() throws InterruptedException{
		if(country.equalsIgnoreCase("ca")){
			//-----------------------------------------LOCAL VARIABLES---------------------------------------------------------------------------------------------
			int randomNum = CommonUtils.getRandomNum(10000, 1000000);		
			String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
			String lastName = "lN";
			String firstName=TestConstants.FIRST_NAME+randomNum;
			String emailid = firstName+randomNum+TestConstants.EMAIL_ADDRESS_SUFFIX;
			String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
			String addressLine1_CA = null;
			String city1 = null;
			String postalCode1 = null;
			addressLine1 = TestConstants.ADDRESS_LINE_1_QUEBEC;
			city = TestConstants.CITY_QUEBEC;
			postalCode = TestConstants.POSTAL_CODE_QUEBEC;
			phoneNumber = TestConstants.PHONE_NUMBER_CA;
			addressLine1_CA = TestConstants.ADDRESS_LINE_1_CA;
			city1 = TestConstants.CITY_CA;
			state = TestConstants.PROVINCE_CA;
			postalCode1 = TestConstants.POSTAL_CODE_CA;
			regimenName = TestConstants.REGIMEN_NAME_REDEFINE;
			kitName = TestConstants.KIT_NAME_PERSONAL;
			enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
			storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
			storeFrontHomePage.selectProductAndProceedToBuy();
			storeFrontHomePage.clickOnCheckoutButton();
			storeFrontHomePage.enterNewRCDetails(TestConstants.FIRST_NAME+randomNum, TestConstants.LAST_NAME+randomNum, emailid, password);
			storeFrontHomePage.enterMainAccountInfo(addressLine1, city, TestConstants.PROVINCE_QUEBEC, postalCode, phoneNumber);
			storeFrontHomePage.clickOnContinueWithoutSponsorLink();
			storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
			storeFrontHomePage.clickOnShippingAddressNextStepBtn();
			storeFrontHomePage.enterBillingDetailsAndSave(cardNumber, firstName+" "+lastName, cardExpMonth, cardExpYear, CVV);
			storeFrontHomePage.clickOnBillingNextStepBtn();
			storeFrontHomePage.clickPlaceOrderBtn();
			storeFrontHomePage.clickOnRodanAndFieldsLogo();
			storeFrontRCUserPage.clickOnWelcomeDropDown();
			storeFrontAccountInfoPage = storeFrontRCUserPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
			storeFrontRCUserPage.clickOnYourAccountDropdown();
			storeFrontAccountTerminationPage = storeFrontAccountInfoPage.clickTerminateMyAccount();
			storeFrontAccountTerminationPage.selectTerminationReason();
			storeFrontAccountTerminationPage.enterTerminationComments();
			storeFrontAccountTerminationPage.selectCheckBoxForVoluntarilyTerminate();
			storeFrontAccountTerminationPage.clickSubmitToTerminateAccount();
			s_assert.assertTrue(storeFrontAccountTerminationPage.verifyPopupHeader(),"Account termination Page Pop Up Header is not Present");
			storeFrontAccountTerminationPage.clickOnConfirmTerminationPopup();
			storeFrontHomePage.loginAsRCUser(emailid,password);
			s_assert.assertTrue(storeFrontHomePage.isCurrentURLShowsError(),"Inactive User doesn't get Login failed");
			storeFrontHomePage.clickOnRodanAndFieldsLogo();
			storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
			storeFrontHomePage.searchCID(sponsor);
			storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
			storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);		
			storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
			storeFrontHomePage.enterEmailAddress(emailid);
			s_assert.assertTrue(storeFrontHomePage.verifyUpradingToConsulTantPopup(), "Upgrading to a consultant popup is not present");
			storeFrontHomePage.enterPassword(password);
			storeFrontHomePage.enterConfirmPassword(password);
			storeFrontHomePage.enterFirstName(newBillingProfileName);
			storeFrontHomePage.enterLastName(lastName);
			storeFrontHomePage.enterAddressLine1(addressLine1_CA);
			storeFrontHomePage.enterCity(city1);
			storeFrontHomePage.selectProvince(state);
			storeFrontHomePage.enterPostalCode(postalCode1);
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
			storeFrontHomePage.checkThePoliciesAndProceduresCheckBox();
			storeFrontHomePage.checkTheIAcknowledgeCheckBox();		
			storeFrontHomePage.checkTheIAgreeCheckBox();
			storeFrontHomePage.checkTheTermsAndConditionsCheckBox();
			storeFrontHomePage.clickOnEnrollMeBtn();
			storeFrontHomePage.clickOnConfirmAutomaticPayment();
			s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
			storeFrontHomePage.clickOnRodanAndFieldsLogo();
			storeFrontConsultantPage.clickOnWelcomeDropDown();
			s_assert.assertTrue(storeFrontConsultantPage.isEditCRPLinkPresent(), "Edit CRP Link is Present on Welcome Drop Down");
			s_assert.assertAll();
		}

	}

	/***
	 * Test Summary:- Start from com pws site, during PC enrollment enter other sponsor with pws and complete enrollment
	 *  Validation: 
	 *  * User should be on same pws from which it started
	 * @throws InterruptedException
	 */
	//Hybris Project-3974:During PC enrollment - search for Cons with PWS
	// Hybris Project-3865:PC Enrollment from PWS .COM Site
	@Test (priority=11)
	public void testRegisterAsPCWithDifferentSponserWithPulseCOM_3865_3974() throws InterruptedException {
		//-----------------------------------------LOCAL VARIABLES---------------------------------------------------------------------------------------------
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		List<Map<String, Object>> sponserList =  null;
		String sponserHavingPulse = null;
		List<Map<String, Object>> sponsorIdList=null;
		String sponsorID=null;
		String pws=null;
		String currentPWSUrl=null;
		//----------------------------------------DB QUERIES--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		sponserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment(),driver.getCountry(),countryId),RFO_DB);
		sponserHavingPulse = String.valueOf(getValueFromQueryResult(sponserList, "AccountID"));
		sponsorIdList= DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,sponserHavingPulse),RFO_DB);
		sponsorID = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
		//-----------------------------------------TEST--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		pws = storeFrontHomePage.convertBizSiteToComSite(storeFrontHomePage.openPWSSite(country, env));
		storeFrontHomePage.addAProductToCartAndCheckout();
		storeFrontHomePage.enterNewPCDetails(firstName,lastName,emailAddress, password);
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		storeFrontHomePage.clickOnNotYourSponsorLink();
		storeFrontHomePage.searchCID(sponsorID);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		storeFrontHomePage.enterBillingDetailsAndSave(cardNumber, firstName+" "+lastName, cardExpMonth, cardExpYear, CVV);
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickPlaceOrderBtn();
		currentPWSUrl=driver.getCurrentUrl();
		s_assert.assertFalse(storeFrontHomePage.verifyPWSAfterSuccessfulEnrollment(pws,currentPWSUrl),"PWS of the RC after enrollment is same as the one it started enrollment");
		s_assert.assertAll(); 
	}

	/***
	 * Test Summary:- Start from com pws site, during PC enrollment enter cross country sponsor with pws and complete enrollment
	 *  Validation: 
	 *  * User should be on same pws from which it started
	 * @throws InterruptedException
	 */
	// Hybris Project-4439:(Shipment) Register as PC customer with US sponsor from BIZ PWS site (ECOM-75)
	//Hybris Project-4440:(Login or Create an Account) Register as PC customer with US sponsor from BIZ PWS site (ECOM-75)
	//Hybris Project-3868:Cross Country: PC Enroll with US/CA sponsor from COM PWS site)
	@Test (priority=12)
	public void testRegisterAsPCWithDifferentCCSponserWithPulseCOM_3868_4440_4439() throws InterruptedException {
		//-----------------------------------------LOCAL VARIABLES---------------------------------------------------------------------------------------------
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		List<Map<String, Object>> sponserList =  null;
		String sponserHavingPulse = null;
		String cc_country="us";
		String cc_countryId="236";
		List<Map<String, Object>> sponsorIdList=null;
		String sponsorID=null;
		String pws=null;
		String currentPWSUrl=null;
		//----------------------------------------DB QUERIES--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		sponserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment(),cc_country,cc_countryId),RFO_DB);
		sponserHavingPulse = String.valueOf(getValueFromQueryResult(sponserList, "AccountID"));
		sponsorIdList= DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,sponserHavingPulse),RFO_DB);
		sponsorID = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
		pws = storeFrontHomePage.convertBizSiteToComSite(storeFrontHomePage.openPWSSite(country, driver.getEnvironment()));
		//-----------------------------------------TEST--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		storeFrontHomePage.addAProductToCartAndCheckout();
		storeFrontHomePage.enterNewPCDetails(firstName,lastName,emailAddress, password);
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		storeFrontHomePage.clickOnNotYourSponsorLink();
		storeFrontHomePage.searchCID(sponsorID);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		storeFrontHomePage.enterBillingDetailsAndSave(cardNumber, firstName+" "+lastName, cardExpMonth, cardExpYear, CVV);
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
		storeFrontHomePage.clickPlaceOrderBtn();
		currentPWSUrl=driver.getCurrentUrl();
		s_assert.assertFalse(storeFrontHomePage.verifyPWSAfterSuccessfulEnrollment(pws,currentPWSUrl),"PWS of the PC after enrollment is same as the one it started enrollment");
		s_assert.assertAll(); 
	}

	/***
	 * Test Summary:- Start from biz pws site, during PC enrollment enter other sponsor with pws and complete enrollment
	 *  Validation: 
	 *  * User should be on same pws from which it started
	 * @throws InterruptedException
	 */
	//Hybris Project-3879:COM: Join PCPerk in the shipment section - CA Spsonor with Pulse
	//Hybris Project-3741:(Shipment) Register as PC customer with CA sponsor from BIZ PWS site (ECOM-75)
	//Hybris Project-3726:(Login or Create an Account) Register as PC customer with CA sponsor from BIZ PWS site (ECOM-75)
	@Test (priority=13)
	public void testRegisterAsPCWithDifferentSponserWithPulseBIZ_3726_3741_3879() throws InterruptedException {
		//-----------------------------------------LOCAL VARIABLES---------------------------------------------------------------------------------------------
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		List<Map<String, Object>> sponserList =  null;
		String sponserHavingPulse = null;
		List<Map<String, Object>> sponsorIdList=null;
		String sponsorID=null;
		String pws=null;
		String currentPWSUrl=null;
		//----------------------------------------DB QUERIES--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		sponserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment(),driver.getCountry(),countryId),RFO_DB);
		sponserHavingPulse = String.valueOf(getValueFromQueryResult(sponserList, "AccountID"));
		sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,sponserHavingPulse),RFO_DB);
		sponsorID = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
		//-----------------------------------------TEST--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		pws = storeFrontHomePage.convertComSiteToBizSite(storeFrontHomePage.openPWSSite(country, env));
		storeFrontHomePage.addAProductToCartAndCheckout();
		storeFrontHomePage.enterNewPCDetails(firstName,lastName,emailAddress, password);
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		storeFrontHomePage.clickOnNotYourSponsorLink();
		storeFrontHomePage.searchCID(sponsorID);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		storeFrontHomePage.enterBillingDetailsAndSave(cardNumber, firstName+" "+lastName, cardExpMonth, cardExpYear, CVV);
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
		storeFrontHomePage.clickPlaceOrderBtn();
		currentPWSUrl=driver.getCurrentUrl();
		s_assert.assertFalse(storeFrontHomePage.verifyPWSAfterSuccessfulEnrollment(pws,currentPWSUrl),"PWS of the PC after enrollment is same as the one it started enrollment");
		s_assert.assertAll(); 
	}

	//Hybris Project-4647:PC Account Termination
	//Hybris Project-4648:Cancellation Message for PC account Termination
	//Hybris Project-4319:Soft-Terminated PC Cancel Reactivation
	// Hybris Project-4318 :: Version : 1 :: Soft-Terminated PC Customer reactivates his PC account and perform Ad Hoc order
	@Test (priority=14)
	public void testTerminatePCReactivatePCAccountAndPerformAdhocOrder_4318_4319_4648_4647() throws InterruptedException{
		//-----------------------------------------LOCAL VARIABLES---------------------------------------------------------------------------------------------
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		List<Map<String, Object>> randomPCUserList =  null;
		String pcUserEmailID = null;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String lastName = "lN";
		String accountId = null;
		//----------------------------------------DB QUERIES--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		while(true){
			randomPCUserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_AUTOSHIPS_RFO,countryId),RFO_DB);
			pcUserEmailID = (String) getValueFromQueryResult(randomPCUserList, "UserName");  
			accountId = String.valueOf(getValueFromQueryResult(randomPCUserList, "AccountID"));
			logger.info("Account Id of the user is "+accountId);
			storeFrontPCUserPage = storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+pcUserEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		} 
		//-----------------------------------------TEST--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		storeFrontPCUserPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontPCUserPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyAccountInfoPageIsDisplayed(),"Account Info page has not been displayed");
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountInfoPage.clickOnPcPerksStatus();
		storeFrontPCUserPage.clickDelayOrCancelPCPerks();
		s_assert.assertTrue(storeFrontAccountInfoPage.isYesChangeMyAutoshipDateButtonPresent(),"Yes Change My Autoship Date is Not Presnt on UI");
		s_assert.assertTrue(storeFrontAccountInfoPage.isCancelPCPerksLinkPresent(),"Cancel PC Perks link Not Present");
		storeFrontAccountTerminationPage = storeFrontAccountInfoPage.clickOnCancelPCPerks();
		storeFrontAccountTerminationPage.fillTheEntriesAndClickOnSubmitDuringTerminationForPC();
		storeFrontAccountTerminationPage.clickOnConfirmAccountTermination();
		storeFrontAccountTerminationPage.clickOnCloseWindowAfterTermination();
		storeFrontHomePage.addAProductToCartAndCheckout();
		storeFrontHomePage.enterEmailAddress(pcUserEmailID);
		s_assert.assertTrue(storeFrontHomePage.verifyReactiveYourPCAccountPopup(), "Reactivate Your PC account popup is not present");
		storeFrontHomePage.clickOnCnacelEnrollmentForPC();
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		s_assert.assertTrue(storeFrontHomePage.isCurrentURLShowsError(),"Terminated User doesn't get Login failed");  
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		storeFrontHomePage.addAProductToCartAndCheckout();
		storeFrontHomePage.enterEmailAddress(pcUserEmailID);
		s_assert.assertTrue(storeFrontHomePage.verifyReactiveYourPCAccountPopup(), "Reactivate Your PC account popup is not present");
		storeFrontHomePage.enterPasswordAfterTermination();
		storeFrontHomePage.clickOnLoginToReactiveMyAccount();
		storeFrontPCUserPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage.clickOnBuyNowButton();
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		String subtotal = storeFrontUpdateCartPage.getSubtotal();
		String tax = storeFrontUpdateCartPage.getTax();
		String total = storeFrontUpdateCartPage.getTotal();
		String shippingMethod = storeFrontUpdateCartPage.getShippingMethod();
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		storeFrontUpdateCartPage.clickOnDefaultBillingProfileEdit();
//		storeFrontHomePage.enterBillingDetailsAndSave(cardNumber, firstName+" "+lastName, cardExpMonth, cardExpYear, CVV);
		storeFrontUpdateCartPage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
		storeFrontUpdateCartPage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyOrderPlacedConfirmationMessage(), "Order has been not placed successfully");
		storeFrontConsultantPage = storeFrontUpdateCartPage.clickOnRodanAndFieldsLogo();
		storeFrontPCUserPage.clickOnWelcomeDropDown();
		storeFrontOrdersPage = storeFrontPCUserPage.clickOrdersLinkPresentOnWelcomeDropDown();
		String orderHistoryNumber = storeFrontOrdersPage.getFirstOrderNumberFromOrderHistory();
		storeFrontOrdersPage.clickOrderNumber(orderHistoryNumber);
		s_assert.assertTrue(storeFrontOrdersPage.getSubTotalFromAutoshipTemplate().contains(subtotal),"Adhoc Order template subtotal "+subtotal+" and on UI is "+storeFrontOrdersPage.getSubTotalFromAutoshipTemplate());
		s_assert.assertTrue(storeFrontOrdersPage.getTaxAmountFromAutoshipTemplate().contains(tax),"Adhoc Order template tax "+tax+" and on UI is "+storeFrontOrdersPage.getTaxAmountFromAdhocOrderTemplate());
		s_assert.assertTrue(storeFrontOrdersPage.getGrandTotalFromAutoshipTemplate().contains(total),"Adhoc Order template grand total "+total+" and on UI is "+storeFrontOrdersPage.getGrandTotalFromAutoshipTemplate());
		s_assert.assertTrue(shippingMethod.contains(storeFrontOrdersPage.getShippingMethodFromAutoshipTemplate()),"Adhoc Order template shipping method "+shippingMethod+" and on UI is "+storeFrontOrdersPage.getShippingMethodFromAutoshipTemplate());
		s_assert.assertAll();
	}

	// Hybris Project-138:Enroll and manage my autoships in Canada - PC
	@Test(priority=15)  
	public void testEnrollAndManageMyAutoshipsInCanada_138() throws InterruptedException {
		if(driver.getCountry().equalsIgnoreCase("ca")){
			//----------------------------------------LOCAL VARIABLES--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			List<Map<String, Object>> randomPCUserList =  null;
			String pcUserEmailID = null;
			String accountId = null;
			//----------------------------------------DB QUERIES--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			while(true){
				randomPCUserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
				pcUserEmailID = (String) getValueFromQueryResult(randomPCUserList, "UserName");		
				accountId = String.valueOf(getValueFromQueryResult(randomPCUserList, "AccountID"));
				logger.info("Account Id of the user is "+accountId);
				storeFrontPCUserPage = storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
				boolean isError = driver.getCurrentUrl().contains("error");
				if(isError){
					logger.info("Login Error for the user "+pcUserEmailID);
					driver.get(driver.getURL()+"/"+driver.getCountry());
				}
				else
					break;
			}	
			//----------------------------------------TEST--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			storeFrontPCUserPage.clickOnWelcomeDropDown();
			storeFrontAccountInfoPage = storeFrontPCUserPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
			storeFrontAccountInfoPage.changeMainAddressToQuebec();
			storeFrontAccountInfoPage.clickOnSaveButtonAfterAddressChange();
			s_assert.assertTrue(storeFrontAccountInfoPage.getAddressUpdateConfirmationMessageFromUI().equalsIgnoreCase("Your profile has been updated"),"Main address on ui is updated successfully");
			storeFrontAccountInfoPage.clickOnYourAccountDropdown();
			storeFrontShippingInfoPage=storeFrontAccountInfoPage.clickOnShippingInfoOnAccountInfoPage();
			storeFrontShippingInfoPage.clickOnEditForFirstAddress();
			storeFrontShippingInfoPage.changeMainAddressToQuebec();
			storeFrontShippingInfoPage.clickOnSaveShippingProfile();
			s_assert.assertTrue(storeFrontShippingInfoPage.getAddressUpdateConfirmationMessageFromUI().equalsIgnoreCase("Your address has been updated."),"Shipping address on ui is updated successfully");
			storeFrontShippingInfoPage.clickOnEditForFirstAddress();
			storeFrontShippingInfoPage.changeAddressToUSAddress();
			s_assert.assertTrue(storeFrontShippingInfoPage.getErrorMessageForUSAddressFromUI().equalsIgnoreCase("Please enter valid postal code."),"Accepting the us address for shipping info");
			s_assert.assertAll();
		}else{
			logger.info("NOT EXECUTED...Test is ONLY for CANADA env");
		}
	}
	
	//Hybris Project-3877:COM:Different sponsor other than PWS site owner - RC2PC via OrderSummary Section
		@Test(priority=16)
		public void testJoinPCPerksInOrderSummarySection_3877() throws InterruptedException{
			int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
			List<Map<String, Object>> randomConsultantList =null;
			List<Map<String, Object>> sponsorIdList=null;
			String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNumber;
			String lastName = "lN";
			String firstName=TestConstants.FIRST_NAME+randomNumber;
			String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
			//Get a Sponser for pc user.
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".com",country,countryId),RFO_DB);
			String emailAddressOfSponser= (String) getValueFromQueryResult(randomConsultantList, "Username"); 
			String comPWSOfSponser=String.valueOf(getValueFromQueryResult(randomConsultantList, "URL"));
			logger.info("COM PWS of sponsor is "+comPWSOfSponser);
			String accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			// sponser search by Account Number
			sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,accountID),RFO_DB);
			String sponserId = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
			//Get .biz PWS from database to start enrolling rc user and upgrading it to pc user
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".com",country,countryId),RFO_DB);
			String emailAddressOfConsultant= (String) getValueFromQueryResult(randomConsultantList, "Username"); 
			String comPWSOfConsultant=String.valueOf(getValueFromQueryResult(randomConsultantList, "URL"));
			// sponser search by Account Number
			sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,accountID),RFO_DB);
			//Open com pws of Sponser
			for(int i=0;i<TestConstants.LOOP_COUNT;i++) {
				storeFrontHomePage.openConsultantPWS(comPWSOfConsultant);
				if(driver.getCurrentUrl().toLowerCase().contains("error")||driver.getCurrentUrl().toLowerCase().contains("sitenotfound")||driver.getCurrentUrl().toLowerCase().contains("sitenotactive")){
					randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".com",country,countryId),RFO_DB);
					emailAddressOfConsultant= (String) getValueFromQueryResult(randomConsultantList, "Username"); 
					comPWSOfConsultant=String.valueOf(getValueFromQueryResult(randomConsultantList, "URL"));
					continue;
				}
				else{
					break;
				}
			}
			//Hover shop now and click all products link.
			storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
			// Products are displayed?
			s_assert.assertTrue(storeFrontHomePage.areProductsDisplayed(), "quickshop products not displayed");
			logger.info("Quick shop products are displayed");
			//Select a product and proceed to buy it
			storeFrontHomePage.clickAddToBagButton();
			//Cart page is displayed?
			s_assert.assertTrue(storeFrontHomePage.isCartPageDisplayed(), "Cart page is not displayed");
			logger.info("Cart page is displayed");
			//1 product is in the Shopping Cart?
			s_assert.assertTrue(storeFrontHomePage.verifyNumberOfProductsInCart("1"), "number of products in the cart is NOT 1");
			logger.info("1 product is successfully added to the cart");
			//Click on Check out
			storeFrontHomePage.clickOnCheckoutButton();
			//Log in or create an account page is displayed?
			s_assert.assertTrue(storeFrontHomePage.isLoginOrCreateAccountPageDisplayed(), "Login or Create Account page is NOT displayed");
			logger.info("Login or Create Account page is displayed");
			//Enter the User information and DO NOT check the "Become a Preferred Customer" checkbox and click the create account button
			storeFrontHomePage.enterNewRCDetails(firstName, TestConstants.LAST_NAME+randomNumber, emailAddress, password);
			//Assert the default consultant.
			s_assert.assertTrue(storeFrontHomePage.getSponserNameFromUIWhileEnrollingPCUser().contains(emailAddressOfConsultant.toLowerCase()),"Default consultant is not the one whose pws is used");
			//Assert continue without sponser link is not present
			s_assert.assertFalse(storeFrontHomePage.verifyContinueWithoutSponserLinkPresent(), "Continue without Sponser link is present on pws enrollment");
			s_assert.assertTrue(storeFrontHomePage.verifyNotYourSponsorLinkIsPresent(),"Not your Sponser link is not present.");
			//Click not your sponser link and verify continue without sponser link is present.
			storeFrontHomePage.clickOnNotYourSponsorLink();
			s_assert.assertTrue(storeFrontHomePage.verifySponserSearchFieldIsPresent(),"Sponser search field is not present");
			//Enter the Main account info and DO NOT check the "Become a Preferred Customer" and click next
			storeFrontHomePage.enterMainAccountInfo();
			logger.info("Main account details entered");
			//Search for sponser and ids.
			storeFrontHomePage.searchCID(sponserId);
			storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();
			//verify the  sponser is selected.
			s_assert.assertTrue(storeFrontHomePage.getSponserNameFromUIWhileEnrollingPCUser().contains(emailAddressOfSponser.toLowerCase()),"Default consultant is not the one whose pws is used");
			storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
			s_assert.assertTrue(storeFrontHomePage.isShippingAddressNextStepBtnIsPresent(),"Shipping Address Next Step Button Is not Present");
			storeFrontHomePage.clickOnShippingAddressNextStepBtn();
			//Enter Billing Profile
			storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
			storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
			storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
			storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
			storeFrontHomePage.selectNewBillingCardAddress();
			storeFrontHomePage.clickOnSaveBillingProfile();
			//check PC Perks Checkbox on Payment/order summary page
			s_assert.assertTrue(storeFrontHomePage.validatePCPerksCheckBoxIsDisplayed(),"PC Perks checkbox is not present");
			s_assert.assertFalse(storeFrontHomePage.verifyPCPerksCheckBoxIsSelected(),"pc perks checbox is  selected");
			storeFrontHomePage.checkPCPerksCheckBox();
			s_assert.assertTrue(storeFrontHomePage.verifyPCPerksCheckBoxIsSelected(),"pc perks checbox is not selected");
			storeFrontHomePage.clickOnShippingAddressNextStepBtn();
			storeFrontHomePage.clickOnBillingNextStepBtn();
			storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
			storeFrontHomePage.clickPlaceOrderBtn();
			storeFrontHomePage.clickOnRodanAndFieldsLogo();
			s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
			String currentURL=driver.getCurrentUrl();
			logger.info("Current URL is "+currentURL);
			s_assert.assertTrue(currentURL.toLowerCase().contains(comPWSOfSponser.split(":")[1].toLowerCase()),"After pc Enrollment the site does not navigated to expected url");
			s_assert.assertAll();
		}
}
