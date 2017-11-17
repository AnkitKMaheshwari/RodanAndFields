package com.rf.test.website.storeFront.hybris.majorEnrollments;

import com.rf.test.website.RFWebsiteBaseTest;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import com.rf.core.utils.CommonUtils;
import com.rf.core.utils.DBUtil;
import com.rf.core.website.constants.TestConstants;
import com.rf.core.website.constants.dbQueries.DBQueries_RFO;

public class EnrollmentsTest extends RFWebsiteBaseTest{
	private static final Logger logger = LogManager
			.getLogger(EnrollmentsTest.class.getName());

	String CCS = null;

	/**
	 * Enrollment Type: Standard
	 * Regimen: Reverse
	 * Kit: Personal
	 * Pulse: Y
	 * CRP: Y
	 * 
	 * No validation of CRP and Pulse after enrollment
	 * @throws InterruptedException
	 */
	//Hybris Project-5284:CORP:Standard Enroll USD 395 Personal Results Kit, Personal Regimen REVERSE REGIME
	@Test(priority=1)
	public void testStandardEnroll_PersonalRegimenReverseRegime_5284() throws InterruptedException{
		//-------------------------local variables---------------------------------------------------------
		String randomNum = CommonUtils.getCurrentTimeStamp();
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		kitName = TestConstants.KIT_NAME_PERSONAL;
		String enrollmentType = TestConstants.STANDARD_ENROLLMENT;
		regimenName = TestConstants.REGIMEN_NAME_REVERSE;
		//-------------------------------------------------------------------------------------------------

		navigateToStoreFrontBaseURL();
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(sponsor);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, consultantFirstName, consultantLastName+randomNum, password, addressLine1, city,state, postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(consultantFirstName+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate();
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(consultantFirstName);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.uncheckPulseAndCRPEnrollment();
		storeFrontHomePage.checkCRPCheckBox();
		storeFrontHomePage.checkPulseCheckBox();
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.applyPriceFilterHighToLow();
		storeFrontHomePage.selectProductAndProceedToAddToCRP();
		storeFrontHomePage.clickNextOnCRPCartPage();
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		s_assert.assertAll();
	}

	/**
	 * Enrollment Type: Express
	 * Regimen: Unblemish
	 * Kit: Personal
	 * Pulse: Y
	 * CRP: Y
	 * 
	 * No validation of CRP and Pulse after enrollment
	 * @throws InterruptedException
	 */
	//Hybris Project-2147: Check Shipping and Handling Fee for UPS 2Day for Order total 0-999999-EnrollmentKit
	// Hybris Project-1287: Terms and Conditions (Express enrollment)
	@Test(priority=2)
	public void testTermsAndConditions_ExpressEnrollment_1287_2147() throws InterruptedException{
		//-------------------------local variables---------------------------------------------------------
		String randomNum = CommonUtils.getCurrentTimeStamp();
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		String enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
		regimenName = TestConstants.REGIMEN_NAME_UNBLEMISH;
		//-------------------------------------------------------------------------------------------------

		navigateToStoreFrontBaseURL();
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(sponsor);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, consultantFirstName,consultantLastName+randomNum, password, addressLine1, city,state, postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(consultantFirstName);
		storeFrontHomePage.selectNewBillingCardExpirationDate();
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(consultantFirstName);
		storeFrontHomePage.clickNextButton();
		s_assert.assertTrue(storeFrontHomePage.getEnrollmentKitDeliveryCharges().contains("0.00"),"enrollment delivery charges SHOULD be 0.00");
//		s_assert.assertTrue(storeFrontHomePage.getEmrollmentShippingMethod().contains("UPS 2Day"),"enrollment delivery chahrges SHOULD be")
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		s_assert.assertTrue(storeFrontHomePage.verifyPopUpForTermsAndConditions(), "PopUp for terms and conditions is not visible");
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		s_assert.assertAll();
	}

	/**
	 * Enrollment Type: Standard
	 * Regimen: Redefine
	 * Kit: Personal
	 * Pulse: N
	 * CRP: N
	 * 
	 * No validation of CRP and Pulse after enrollment
	 * @throws InterruptedException
	 */
	//Hybris Project-1291 Terms and Conditions- Standard enrollment with out CRP or Pulse
	// Hybris Project-3255 Standard EnrollmentTest without CRP and Pulse
	@Test(priority=3)
	public void testStandardEnrollmentTermsAndConditionsWithoutCRPOrPulse_1291_3255() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		String enrollmentType = TestConstants.STANDARD_ENROLLMENT;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(sponsor);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, firstName, TestConstants.LAST_NAME+randomNum, password, addressLine1, city, state,postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.uncheckPulseAndCRPEnrollment();
		s_assert.assertTrue(storeFrontHomePage.verifySubsribeToPulseCheckBoxIsNotSelected(), "Subscribe to pulse checkbox selected after uncheck");
		s_assert.assertTrue(storeFrontHomePage.verifyEnrollToCRPCheckBoxIsNotSelected(), "Enroll to CRP checkbox selected after uncheck");
		storeFrontHomePage.clickNextButton();
		s_assert.assertTrue(storeFrontHomePage.isTheTermsAndConditionsCheckBoxDisplayed(), "Terms and Conditions checkbox is not visible");
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		s_assert.assertTrue(storeFrontHomePage.verifyPopUpForPoliciesAndProcedures(), "PopUp for policies and procedures is not visible");
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		s_assert.assertAll();
	}

	/**
	 * Enrollment : PC
	 * Sponsor: No
	 * 
	 * Validation of sponsor after enrollment
	 * @throws InterruptedException
	 */
	//Hybris Project-2300 Become PC User After adding product to cart
	//Hybris Project-2187 Terms and Conditions - PC enrollment
	//Hybris Project-2160 Place and Order and Enroll for PC during Checkout and check disclaimers
	//Hybris Project-1308 PC EnrollmentTest  
	@Test(priority=4)
	public void testPCEnrollment_1308_2160_2187_2300() throws InterruptedException{
		//-------------------------local variables---------------------------------------------------------
		String randomNum = CommonUtils.getCurrentTimeStamp();
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		//-------------------------------------------------------------------------------------------------

		navigateToStoreFrontBaseURL();
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		//Enter the User information and DO NOT check the "Become a Preferred Customer" checkbox and click the create account button
		storeFrontHomePage.enterNewPCDetails(firstName,lastName,emailAddress, password);
		//validate 10% discount for PC User account in order summary section
		//Enter the Main account info and DO NOT check the "Become a Preferred Customer" and click next
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		logger.info("Main account details entered");
		storeFrontHomePage.clickOnContinueWithoutSponsorLink();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		//Enter Billing Profile
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+pcLastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate();
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		//validate bill to this card radio button selected under billing profile section
		s_assert.assertTrue(storeFrontHomePage.isTheBillingAddressPresentOnPage(newBillingProfileName),"Bill to this card radio button is not selected under billing profile");
		s_assert.assertTrue(storeFrontHomePage.isBillingProfileIsSelectedByDefault(newBillingProfileName),"Bill to this card radio button is not selected under billing profile");
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.getPCPerksTermsAndConditionsPopupText().toLowerCase().contains(TestConstants.PC_PERKS_TERMS_CONDITION_POPUP_TEXT.toLowerCase()),"PC perks terms and condition popup text is not as expected");
		s_assert.assertTrue(storeFrontHomePage.getPCPerksTermsAndConditionsPopupHeaderText().toLowerCase().contains(TestConstants.PC_PERKS_TERMS_CONDITION_POPUP_HEADER_TEXT.toLowerCase()),"PC perks terms and candition popup Header text is not as expected");
		s_assert.assertTrue(storeFrontHomePage.verifyPCPerksTermsAndConditionsPopup(),"PC Perks terms and conditions popup not visible when checkboxes for t&c not selected and place order button clicked");
		logger.info("PC Perks terms and conditions popup is visible when checkboxes for t&c not selected and place order button clicked");
		storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
		storeFrontHomePage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.isPCEnrolledCongratsMessagePresent(), "'Welcome to Rodan + Fields PC Perks' message has not appeared");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		s_assert.assertTrue(driver.getCurrentUrl().contains("corprfo")||driver.getCurrentUrl().contains("qa.rodanandfields"), "PC is not redirecting to corporate site after enrollment");
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		s_assert.assertAll();
	}

	/**
	 * Enrollment : RC
	 * Sponsor: No
	 * 
	 * Validation of sponsor after enrollment
	 * @throws InterruptedException
	 */
	//Hybris Project-2188 Terms and Conditions - RC enrollment
	//Hybris Project-2250 Corporate Sponsor: PC and RC can create an account without a Sponsor -- will be set to Corporate
	//Hybris Project-1307 RC EnrollmentTest 
	@Test(priority=5)
	public void testRCEnrollment_1307_2250_2188() throws InterruptedException{
		//-------------------------local variables---------------------------------------------------------
		String randomNum = CommonUtils.getCurrentTimeStamp();
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		//-------------------------------------------------------------------------------------------------

		navigateToStoreFrontBaseURL();
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		//Select a product and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		//Enter the User information and DO NOT check the "Become a Preferred Customer" checkbox and click the create account button
		storeFrontHomePage.enterNewRCDetails(firstName,lastName,emailAddress, password);
		//Enter the Main account info and DO NOT check the "Become a Preferred Customer" and click next
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		logger.info("Main account details entered");
		//		storeFrontHomePage.enterSponsorNameAndClickOnSearchForPCAndRC(sponsor);
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
		//validate bill to this card radio button selected under billing profile section
		s_assert.assertTrue(storeFrontHomePage.isTheBillingAddressPresentOnPage(newBillingProfileName),"Bill to this card radio button is not selected under billing profile");
		s_assert.assertTrue(storeFrontHomePage.isBillingProfileIsSelectedByDefault(newBillingProfileName),"Bill to this card radio button is not selected under billing profile");
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.isOrderPlacedSuccessfully(), "Order Not placed successfully");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		//		s_assert.assertTrue(driver.getCurrentUrl().contains("corprfo"), "RC is not registered to corporate site");
		//		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		s_assert.assertAll();
	}

	/**
	 * Enrollment Type: Standard
	 * Regimen: Redefine
	 * Kit: Personal
	 * Pulse: Y
	 * CRP: Y
	 * 
	 * No validation of CRP and Pulse after enrollment
	 * @throws InterruptedException
	 */
	//Hybris Project-2251 Global Sponsorship: Cross Country Sponsor(Standard)
	@Test(priority=6)
	public void testGlobalSponsorshipCrossCountrySponsorStandardEnrollment_2251() throws InterruptedException	{
		//-------------------------local variables---------------------------------------------------------
		String randomNum = CommonUtils.getCurrentTimeStamp();
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		String enrollmentType = TestConstants.STANDARD_ENROLLMENT;
		List<Map<String, Object>> sponsorIdList =  null;
		String countryId=null;
		if(country.equalsIgnoreCase("ca")){
			if(CommonUtils.tossTheCoin()=="heads"){
				countryId="14";
			}
			else
				countryId="236";
		}
		if(country.equalsIgnoreCase("au")){
			if(CommonUtils.tossTheCoin()=="heads"){
				countryId="40";
			}
			else
				countryId="236";
		}
		else{
			if(CommonUtils.tossTheCoin()=="heads"){
				countryId="40";
			}
			else
				countryId="14";
		}
		logger.info("Cross CountryId is "+countryId);
		sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_SPONSOR_ID,countryId),RFO_DB);
		CCS = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));		
		//-------------------------------------------------------------------------------------------------

		navigateToStoreFrontBaseURL();
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(CCS);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, consultantFirstName, consultantLastName+randomNum,password, addressLine1, city,state,postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(consultantFirstName);
		storeFrontHomePage.selectNewBillingCardExpirationDate();
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(consultantFirstName);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.checkPulseAndCRPEnrollment();
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.applyPriceFilterHighToLow();
		storeFrontHomePage.selectProductAndProceedToAddToCRP();
		storeFrontHomePage.clickOnNextBtnAfterAddingProductAndQty();
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		s_assert.assertAll();
	}

	/**
	 * Enrollment : PC
	 * Sponsor: cross Country
	 * 
	 * No Validation of sponsor after enrollment
	 * @throws InterruptedException
	 */
	//Hybris Project-3617 CCS PC EnrollmentTest under US Sponsor
	@Test(priority=8)
	public void testCcsPCEnrollmentUnderUSSponsor_3617() throws InterruptedException {
		//-------------------------local variables---------------------------------------------------------
		String randomNum = CommonUtils.getCurrentTimeStamp();
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		List<Map<String, Object>> sponsorIdList =  null;
		String countryId=null;
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		//-------------------------------------------------------------------------------------------------
		if(country.equalsIgnoreCase("ca")){
			countryId="236";
		}
		if(country.equalsIgnoreCase("au")){
			if(CommonUtils.tossTheCoin()=="heads"){
				countryId="40";
			}
			else
				countryId="236";
		}
		else{
			countryId="40";
		}

		logger.info("Cross CountryId is "+countryId);
		sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_SPONSOR_ID,countryId),RFO_DB);
		CCS = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));  
		navigateToStoreFrontBaseURL();
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		//Enter the User information and DO NOT check the "Become a Preferred Customer" checkbox and click the create account button
		storeFrontHomePage.enterNewPCDetails(firstName,lastName,emailAddress, password);
		//Enter the Main account info and DO NOT check the "Become a Preferred Customer" and click next
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		logger.info("Main account details entered");
		storeFrontHomePage.searchCID(CCS);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		//Enter Billing Profile
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+pcLastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate();
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
		storeFrontHomePage.clickPlaceOrderBtn();
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		//s_assert.assertTrue(storeFrontHomePage.getUserNameAForVerifyLogin(firstName).contains(firstName),"Profile Name After Login"+firstName+" and on UI is "+storeFrontHomePage.getUserNameAForVerifyLogin(firstName));
		s_assert.assertAll(); 
	}

	/**
	 * Enrollment : RC
	 * Sponsor: cross Country
	 * 
	 * No Validation of sponsor after enrollment
	 * @throws InterruptedException
	 */
	//Hybris Project-3618 CCS RC EnrollmentTest under US Sponsor
	@Test(priority=9)
	public void testCcsRCEnrollmentUnderUSSponsor_3618() throws InterruptedException	{
		//-------------------------local variables---------------------------------------------------------
		String randomNum = CommonUtils.getCurrentTimeStamp();
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		List<Map<String, Object>> sponsorIdList =  null;
		String countryId=null;
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		//-------------------------------------------------------------------------------------------------
		if(country.equalsIgnoreCase("ca")){
			if(CommonUtils.tossTheCoin()=="heads"){
				countryId="14";
			}
			else
				countryId="236";
		}
		if(country.equalsIgnoreCase("au")){
			if(CommonUtils.tossTheCoin()=="heads"){
				countryId="40";
			}
			else
				countryId="236";
		}
		else{
			if(CommonUtils.tossTheCoin()=="heads"){
				countryId="40";
			}
			else
				countryId="14";
		}
		logger.info("Cross CountryId is "+countryId);
		sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_SPONSOR_ID,countryId),RFO_DB);
		CCS = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));		
		navigateToStoreFrontBaseURL();
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		//Select a product and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		//Enter the User information and DO NOT check the "Become a Preferred Customer" checkbox and click the create account button
		storeFrontHomePage.enterNewRCDetails(firstName,lastName,emailAddress, password);
		//Enter the Main account info and DO NOT check the "Become a Preferred Customer" and click next
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		logger.info("Main account details entered");
		storeFrontHomePage.searchCID(CCS);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();
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
		s_assert.assertAll();	
	}
	
	/**
	  * Enrollment Type: Express
	  * Regimen: Redefine
	  * Kit: Personal
	  * Pulse: Y
	  * CRP: Y
	  * 
	  * No validation of CRP and Pulse after enrollment
	  * @throws InterruptedException
	  */
	 //Hybris Project-3924 CORP: Enroll CA/US User , Sponsor with PWS
	@Test(priority=10)
	 public void testEnrollCAOrUSUserAndSponsorWithPWS_3924() throws InterruptedException{
	  //-------------------------local variables---------------------------------------------------------
	  String randomNum = CommonUtils.getCurrentTimeStamp();
	  String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
	  String enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
	  List<Map<String, Object>> sponsorIdList =  null;
	  String currentURL = null;
	  String CCS = null;
	  List<Map<String, Object>> sponserList = null;
	  String sponserHavingPulse = null;
	  String idForConsultant = null;
	  // Get Canadian sponser with PWS from database
	  sponserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",driver.getCountry(),countryId),RFO_DB);
	  sponserHavingPulse = String.valueOf(getValueFromQueryResult(sponserList, "AccountID"));

	  // sponser search by Account Number
	  sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,sponserHavingPulse),RFO_DB);
	  idForConsultant = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
	  // Enroll with CA sponsor(Same country)

	  storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
	  storeFrontHomePage.searchCID(idForConsultant);
	  s_assert.assertTrue(storeFrontHomePage.isSponsorPresentInSearchList(), "No Sponsor present in search results");
	  storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
	  currentURL = storeFrontHomePage.getCurrentURL();
	  s_assert.assertTrue((!currentURL.contains("corprfo.")) && currentURL.contains(".biz") , "User is not redirecting to PWS after slecting sponsor");
	  storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, consultantFirstName, consultantLastName+randomNum,password, addressLine1, city,state,postalCode, phoneNumber);
	  storeFrontHomePage.clickNextButton();
	  storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
	  storeFrontHomePage.enterNameOnCard(consultantFirstName);
	  storeFrontHomePage.selectNewBillingCardExpirationDate();
	  storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
	  storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
	  storeFrontHomePage.enterNameAsItAppearsOnCard(consultantFirstName);
	  storeFrontHomePage.clickNextButton();
	  s_assert.assertTrue(storeFrontHomePage.isTheTermsAndConditionsCheckBoxDisplayed(), "Terms and Conditions checkbox is not visible");
	  storeFrontHomePage.selectAllTermsAndConditionsChkBox();
	  storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
	  storeFrontHomePage.clickOnConfirmAutomaticPayment();
	  s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
	  currentURL = storeFrontHomePage.getCurrentURL();
	  s_assert.assertTrue(currentURL.contains(randomNum), "User is not redirecting to own PWS after enrollment");
	  storeFrontHomePage.clickOnRodanAndFieldsLogo();
	  logout();
	  navigateToStoreFrontBaseURL();

	  //-------------------------------------------------------------------------------------------------
	  randomNum = CommonUtils.getCurrentTimeStamp();
	  socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
	  if(country.equalsIgnoreCase("ca")){
	   countryId="236";
	  }
	  if(country.equalsIgnoreCase("au")){
	   if(CommonUtils.tossTheCoin()=="heads"){
	    countryId="40";
	   }
	   else
	    countryId="236";
	  }

	  logger.info("Cross CountryId is "+countryId);
	  sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_SPONSOR_ID,countryId),RFO_DB);
	  CCS = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));  
	  storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
	  storeFrontHomePage.searchCID(CCS);
	  s_assert.assertTrue(storeFrontHomePage.isSponsorPresentInSearchList(), "No Sponsor present in search results");
	  storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
	  currentURL = storeFrontHomePage.getCurrentURL();
	  s_assert.assertTrue(currentURL.contains("corprfo."), "User is redirecting to PWS after selecting cross country sponsor");
	  storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, consultantFirstName, consultantLastName+randomNum,password, addressLine1, city,state,postalCode, phoneNumber);
	  storeFrontHomePage.clickNextButton();
	  storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
	  storeFrontHomePage.enterNameOnCard(consultantFirstName);
	  storeFrontHomePage.selectNewBillingCardExpirationDate();
	  storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
	  storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
	  storeFrontHomePage.enterNameAsItAppearsOnCard(consultantFirstName);
	  storeFrontHomePage.clickNextButton();
	  s_assert.assertTrue(storeFrontHomePage.isTheTermsAndConditionsCheckBoxDisplayed(), "Terms and Conditions checkbox is not visible");
	  storeFrontHomePage.selectAllTermsAndConditionsChkBox();
	  storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
	  storeFrontHomePage.clickOnConfirmAutomaticPayment();
	  s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
	  currentURL = storeFrontHomePage.getCurrentURL();
	  s_assert.assertTrue(currentURL.contains(randomNum), "User is not redirecting to own PWS after enrollment for cross country");
	  s_assert.assertAll();
	 }
}
