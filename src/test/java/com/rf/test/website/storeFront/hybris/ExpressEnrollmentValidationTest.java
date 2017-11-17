package com.rf.test.website.storeFront.hybris;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rf.core.utils.CommonUtils;
import com.rf.core.utils.DBUtil;
import com.rf.core.utils.QueryUtils;
import com.rf.core.website.constants.TestConstants;
import com.rf.core.website.constants.dbQueries.DBQueries_RFO;
import com.rf.pages.website.storeFront.StoreFrontConsultantPage;
import com.rf.pages.website.storeFront.StoreFrontHomePage;
import com.rf.pages.website.storeFront.StoreFrontOrdersPage;
import com.rf.pages.website.storeFront.StoreFrontPCUserPage;
import com.rf.pages.website.storeFront.StoreFrontShippingInfoPage;
import com.rf.pages.website.storeFront.StoreFrontUpdateCartPage;
import com.rf.test.website.RFWebsiteBaseTest;

public class ExpressEnrollmentValidationTest extends RFWebsiteBaseTest{
	private static final Logger logger = LogManager
			.getLogger(ExpressEnrollmentValidationTest.class.getName());

	//Hybris Project-1274:9. Express enrollment -fields validation
	//Hybris Project-1361:Enroll as consultant using invalid card numbers
	// Hybris Project-82- Version : 1 :: Allow my Spouse through EnrollmentTest
	@Test(priority=1)
	public void testExpressEnrollmentFieldsValidationWithInvalidCardNumbers_1274_1361_82() throws InterruptedException {
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		String invalidSocialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(10000000, 99999999));
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String lastName = "lN";
		String firstCountryNameFromPopup = null;
		String secondCountryNameFromPopup = null;
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(TestConstants.SPONSOR_ID_US);
		s_assert.assertTrue(storeFrontHomePage.getErrorMessageForInvalidSponser().contains("No result found"), "Error message for invalid sponser id does not visible");
		storeFrontHomePage.searchCID(TestConstants.INVALID_SPONSOR_NAME);
		s_assert.assertTrue(storeFrontHomePage.getErrorMessageForInvalidSponser().contains("No result found"), "Error message for invalid sponser Name does not visible");
		storeFrontHomePage.searchCID(sponsor);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.selectKitAndEnrollmentType(kitName, regimenName, enrollmentType);
		storeFrontHomePage.enterInvalidPassword(TestConstants.PASSWORD_BELOW_6CHARS);
		s_assert.assertTrue(storeFrontHomePage.getInvalidPasswordMessage().contains("Please enter 6-24 characters, with at least 1 number and 1 letter."), "Error message for invalid password does not visible");
		storeFrontHomePage.enterPassword(password);
		storeFrontHomePage.enterInvalidConfirmPassword(TestConstants.PASSWORD_BELOW_6CHARS);
		s_assert.assertTrue(storeFrontHomePage.getInvalidPasswordNotmatchingMessage().contains("Your passwords do not match"), "Error message for invalid confirm password does not visible");
		storeFrontHomePage.enterUserInformationForEnrollment( TestConstants.FIRST_NAME+randomNum, TestConstants.LAST_NAME+randomNum, password, addressLine1, city, state,postalCode, phoneNumber);
		storeFrontHomePage.clickOnNotYourCountryLink();
		firstCountryNameFromPopup = storeFrontHomePage.getCountryNameFromNotYourCountryPopUp("1");
		secondCountryNameFromPopup = storeFrontHomePage.getCountryNameFromNotYourCountryPopUp("2");
		if(driver.getCountry().equalsIgnoreCase("AU")){
			s_assert.assertTrue(firstCountryNameFromPopup.contains("Switch to Canada") && secondCountryNameFromPopup.contains("Switch to United States"), "Not Your Country Popup should display country as Canada and United States");
		}
		else if(driver.getCountry().equalsIgnoreCase("CA")){
			s_assert.assertTrue(firstCountryNameFromPopup.contains("Switch to Australia")&& secondCountryNameFromPopup.contains("Switch to United States"), "Not Your Country Popup should display country as Australia and United States");
		}else if(driver.getCountry().equalsIgnoreCase("US")){
			s_assert.assertTrue(firstCountryNameFromPopup.contains("Switch to Australia")&& secondCountryNameFromPopup.contains("Switch to Canada"), "Not Your Country Popup should display country as Australia and canada");	
		}

		storeFrontHomePage.clickOnCancelButtonOfNotYourCountryPopUp();
		storeFrontHomePage.clickNextButton();
		s_assert.assertTrue(storeFrontHomePage.recurringMonthlyChargesSection(), "Recurring Monthly Charges Section should be displayed ");
		s_assert.assertTrue(storeFrontHomePage.pulseSubscriptionTextbox(), "user can enter their PWS prefix");
		if(driver.getCountry().equalsIgnoreCase("CA")){
			storeFrontHomePage.enterSocialInsuranceNumber(invalidSocialInsuranceNumber);
			s_assert.assertTrue(storeFrontHomePage.getErrorMessageForInvalidSSN().contains("Please enter a valid Social Security Number")||storeFrontHomePage.getErrorMessageForInvalidSSN().contains("Please enter a valid Social Insurance Number"), "Error message for invalid SSN does not visible");
		}
		storeFrontHomePage.enterCardNumber(TestConstants.INVALID_CARD_NUMBER_15DIGITS);
		s_assert.assertTrue(storeFrontHomePage.validateInvalidCreditCardMessage(), "Please enter a valid credit card message is displayed");
		storeFrontHomePage.clearCreditCardNumber();
		storeFrontHomePage.enterCardNumber(TestConstants.INVALID_CARD_NUMBER_17DIGITS);
		s_assert.assertTrue(storeFrontHomePage.validateInvalidCreditCardMessage(), "Please enter a valid credit card message is displayed");
		storeFrontHomePage.clearCreditCardNumber();
		storeFrontHomePage.enterCardNumber(TestConstants.INVALID_CARD_NUMBER_15DIGITS_WITH_SPECIAL_CHAR);
		s_assert.assertTrue(storeFrontHomePage.validateInvalidCreditCardMessage(), "Please enter a valid credit card message is displayed");
		storeFrontHomePage.clearCreditCardNumber();
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickOnAllowMySpouseOrDomesticPartnerCheckbox();
		storeFrontHomePage.enterSpouseFirstName(TestConstants.SPOUSE_FIRST_NAME);
		storeFrontHomePage.enterSpouseLastName(TestConstants.SPOUSE_LAST_NAME);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.acceptTheProvideAccessToSpousePopup();
		s_assert.assertTrue(storeFrontHomePage.isTheTermsAndConditionsCheckBoxDisplayed(), "Terms and Conditions checkbox is not visible");
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		s_assert.assertTrue(storeFrontHomePage.validateErrorMessageWithoutSelectingAllCheckboxes(), "A proper error message should be displayed when continuining without selecting all the checkboxes");
		storeFrontHomePage.closePopUp();
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		storeFrontHomePage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontHomePage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontAccountInfoPage.getSpouseFirstName().contains(TestConstants.SPOUSE_FIRST_NAME), "Spouse First name is not as provided during Enrollment.");
		s_assert.assertTrue(storeFrontAccountInfoPage.getSpouseLastName().contains(TestConstants.SPOUSE_LAST_NAME), "Spouse Last name is not as provided during Enrollment.");
		s_assert.assertAll(); 
	}

	// Hybris Project-2153 :: Version : 1 :: Check the shipping method disclaimers for " UPS Standard Overnight/FedEx Standard Overnight" 
	@Test(enabled=true)
	public void testCheckShippingMethodDisclaimersForUPSStandardOvernight_2153() throws InterruptedException{
		if(country.equalsIgnoreCase("ca")){
			int randomNum = CommonUtils.getRandomNum(10000, 1000000);
			String lastName = "lN";
			String firstName=TestConstants.FIRST_NAME+randomNum;
			String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
			storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
			storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
			storeFrontUpdateCartPage.clickOnBuyNowButton();
			storeFrontUpdateCartPage.clickOnCheckoutButton();
			storeFrontUpdateCartPage.selectShippingMethodUPSStandardOverNightForAdhocOrder();
			String selectedShippingMethodName = storeFrontUpdateCartPage.getSelectedShippingMethodName();
			storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
			storeFrontUpdateCartPage.clickOnDefaultBillingProfileEdit();
			storeFrontHomePage.enterBillingDetailsAndSave(cardNumber, firstName+" "+lastName, cardExpMonth, cardExpYear, CVV);
			storeFrontUpdateCartPage.clickOnBillingNextStepBtn();
			storeFrontUpdateCartPage.clickPlaceOrderBtn();
			s_assert.assertTrue(storeFrontUpdateCartPage.verifyOrderPlacedConfirmationMessage(), "Order has been not placed successfully");
			s_assert.assertTrue(storeFrontUpdateCartPage.verifyShippingAddressContainsShippingMethodNameAfterPlaceOrder(selectedShippingMethodName),"Thanku for your order page doesn't contain shipping method name "+selectedShippingMethodName);
			String shippingChargesOnOrderConfirmationPage = storeFrontUpdateCartPage.getShippingChargesAtOrderConfirmationPage();
			s_assert.assertTrue(selectedShippingMethodName.toLowerCase().contains(shippingChargesOnOrderConfirmationPage.toLowerCase()), "Expected shipping method cost is on order confirmation page "+selectedShippingMethodName+" Actual on UI is "+shippingChargesOnOrderConfirmationPage);
			String orderNumber = storeFrontUpdateCartPage.getOrderNumberAfterPlaceOrder();
			storeFrontUpdateCartPage.clickOnWelcomeDropDown();
			storeFrontOrdersPage = storeFrontUpdateCartPage.clickOrdersLinkPresentOnWelcomeDropDown();
			storeFrontOrdersPage.clickOrderNumber(orderNumber);
			s_assert.assertTrue(storeFrontOrdersPage.verifyShippingMethodOnTemplateAfterAdhocOrderPlaced(selectedShippingMethodName),"Adhoc order page doesn't contain shipping method name "+selectedShippingMethodName);
			String shippingChargesFromOrderDetails = storeFrontOrdersPage.getShippingChargesFormOrderDetailsPage();
			s_assert.assertTrue(selectedShippingMethodName.toLowerCase().contains(shippingChargesFromOrderDetails.toLowerCase()), "Expected shipping method cost is on order details page "+selectedShippingMethodName+" Actual on UI is "+shippingChargesFromOrderDetails);
			s_assert.assertAll();
		}else{
			logger.info("NOT EXECUTED...Test is ONLY for CANADA env");
		}
	}

	//Hybris Project-93 :: Version : 1 :: Express Enrollment Billing Profile Main Account Info - Edit 
	@Test(enabled=false)// Covered in TC-2202
	public void testExpressEnrollmentBillingProfileMainAccountInfoEdit_93() throws InterruptedException	{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		storeFrontHomePage.openPWSSite(country, env);
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, TestConstants.FIRST_NAME+randomNum, TestConstants.LAST_NAME+randomNum, password, addressLine1, city,state, postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		//click edit next to main account info and validate setup account page is displayed
		storeFrontHomePage.clickOnReviewAndConfirmShippingEditBtn();
		s_assert.assertTrue(storeFrontHomePage.validateSetUpAccountPageIsDisplayed(), "SetUp account page is not displayed");
		//Edit contact Info and re-enter password
		storeFrontHomePage.reEnterContactInfoAndPassword();
		//validate updated Account Info Details on review and confirmation page
		s_assert.assertTrue(storeFrontHomePage.validateUpdatedMainAccountInfo());
		storeFrontHomePage.checkThePoliciesAndProceduresCheckBox();
		storeFrontHomePage.checkTheIAcknowledgeCheckBox();  
		storeFrontHomePage.checkTheIAgreeCheckBox();
		storeFrontHomePage.checkTheTermsAndConditionsCheckBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		s_assert.assertAll(); 
	}

	//Hybris Project-4156:Consultant who cancels the pulse subscription should have their prefix active (only for the month)
	@Test
	public void testConsultantCancelPulseSubscriptionPrefixActive_4156() throws InterruptedException  {
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		String firstName = TestConstants.FIRST_NAME+randomNum;
		storeFrontConsultantPage=new StoreFrontConsultantPage(driver);
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID();
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
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		//validate on the confirmation page user lands on .biz site
		s_assert.assertTrue(storeFrontHomePage.validateUserLandsOnPWSbizSite(), "user didn't land on PWS .biz site");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		//Fetch the PWS url
		String currentPWSUrl=driver.getCurrentUrl();
		logger.info("PWS of the user is "+currentPWSUrl);
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		//s_assert.assertTrue(storeFrontHomePage.getUserNameAForVerifyLogin(firstName).contains(firstName),"Profile Name After Login"+firstName+" and on UI is "+storeFrontHomePage.getUserNameAForVerifyLogin(firstName));
		//	goto .com site and navigate to Account Info->Autoship tatus->Cancel pulse subscription
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontConsultantPage.clickOnYourAccountDropdown();
		storeFrontConsultantPage.clickOnAutoshipStatusLink();
		storeFrontConsultantPage.cancelPulseSubscription();
		logout();
		//driver.close();
		//validate with the current .biz PWSUrl
		driver.get(currentPWSUrl);
		s_assert.assertTrue(storeFrontHomePage.validatePWS(), "Dot biz PWS is not active");   
		//validate the .com PWSURL.
		String currentComUrl= storeFrontHomePage.createBizToCom(currentPWSUrl);
		driver.get(currentComUrl);
		s_assert.assertTrue(storeFrontHomePage.validatePWS(), "Dot com PWS is not active"); 
		s_assert.assertAll();
	}

	//Hybris Project-3619 :: Version : 1 :: CCS CA consultant Express Enrollment for Yukon province with US sponsor
	@Test
	public void ccsCAConsultantExpressEnrollmentWithUSSponsor_3619() throws InterruptedException	{
		if(driver.getCountry().equalsIgnoreCase("ca")){
			int randomNum = CommonUtils.getRandomNum(10000, 1000000);
			String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
			country = driver.getCountry();
			enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
			regimenName = TestConstants.REGIMEN_NAME_REVERSE;
			String firstName = TestConstants.FIRST_NAME+randomNum;
			String CCS = null;
			RFO_DB = driver.getDBNameRFO(); 
			List<Map<String, Object>> sponsorIdList =  null;
			storeFrontHomePage = new StoreFrontHomePage(driver);
			kitName = TestConstants.KIT_NAME_EXPRESS;			 
			addressLine1 = TestConstants.ADDRESS_LINE_1_CA;
			city = TestConstants.CITY_CA;
			postalCode = TestConstants.POSTAL_CODE_CA;
			phoneNumber = TestConstants.PHONE_NUMBER_CA;
			sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_SPONSOR_ID,countryId),RFO_DB);
			CCS = (String) getValueFromQueryResult(sponsorIdList, "AccountNumber");
			storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
			storeFrontHomePage.searchCID(CCS);
			storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
			storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, firstName, TestConstants.LAST_NAME+randomNum, password, addressLine1, city, TestConstants.PROVINCE_YUKON, postalCode, phoneNumber);
			storeFrontHomePage.clickNextButton();
			//storeFrontHomePage.acceptTheVerifyYourShippingAddressPop();		
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
			storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
			storeFrontHomePage.clickOnConfirmAutomaticPayment();
			s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
			s_assert.assertAll();
		}
		else {
			logger.info("NOT EXECUTED...Test is ONLY for CANADA env");
		}
	}

	//Hybris Project-4793:Ad-hoc Scenarios- Checking the consultant > consultant > PC for US User
	//Hybris Project-4792:Ad-hoc Scenarios- Checking the consultant > consultant > PC for Canada User
	@Test
	public void testRegisterAsConsultantWithDifferentConsultantAsSponser_4792_4793() throws InterruptedException {
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		String socialInsuranceNumbers = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String lastName = "lN";
		List<Map<String, Object>> randomConsultantList =  null;
		List<Map<String, Object>> sponsorIdList = null;
		List<Map<String, Object>> consultantSponsorIdList = null;
		String firstConsultantEmailID = firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String secondConsultantEmailID = TestConstants.FIRST_NAME+randomNumber+TestConstants.EMAIL_ADDRESS_SUFFIX;
		country = driver.getCountry();
		enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
		regimenName = TestConstants.REGIMEN_NAME_REVERSE;
		//get pws from query for assertion
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
		String emailIdOfFirstConsultantSponser = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
		String accountIDOfFirstConsultantSponser = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
		// sponser search by Account Number
		sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,accountIDOfFirstConsultantSponser),RFO_DB);
		String sponserIdOfFirstConsultant = (String) getValueFromQueryResult(sponsorIdList, "AccountNumber");
		//Enroll consultant one who will be Sponser of Second Enrolled Consultant
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(sponserIdOfFirstConsultant);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.enterUserInformationForEnrollmentWithEmail(kitName, regimenName, enrollmentType,firstName, TestConstants.LAST_NAME,firstConsultantEmailID, password, addressLine1, city,state, postalCode, phoneNumber);
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
		storeFrontHomePage.clickOnEnrollMeBtn();
		s_assert.assertTrue(storeFrontHomePage.verifyPopUpForTermsAndConditions(), "PopUp for terms and conditions is not visible");
		storeFrontHomePage.checkTheTermsAndConditionsCheckBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		logout();
		driver.get(driver.getURL()+"/"+driver.getCountry());
		//Enroll Second Consultant with consultant one as sponser
		// sponser search by Account Number
		consultantSponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FROM_EMAIL_ADDRESS,firstConsultantEmailID),RFO_DB);
		String sponserIdOfSecondConsultant = (String) getValueFromQueryResult(consultantSponsorIdList, "AccountNumber");
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(sponserIdOfSecondConsultant);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.enterUserInformationForEnrollmentWithEmail(kitName, regimenName, enrollmentType,TestConstants.FIRST_NAME+randomNumber, TestConstants.LAST_NAME,secondConsultantEmailID, password, addressLine1, city,state, postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNumber);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumbers);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		s_assert.assertTrue(storeFrontHomePage.isTheTermsAndConditionsCheckBoxDisplayed(), "Terms and Conditions checkbox is not visible");
		storeFrontHomePage.checkThePoliciesAndProceduresCheckBox();
		storeFrontHomePage.checkTheIAcknowledgeCheckBox();  
		storeFrontHomePage.checkTheIAgreeCheckBox();
		storeFrontHomePage.clickOnEnrollMeBtn();
		s_assert.assertTrue(storeFrontHomePage.verifyPopUpForTermsAndConditions(), "PopUp for terms and conditions is not visible");
		storeFrontHomePage.checkTheTermsAndConditionsCheckBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		logout();
		driver.get(driver.getURL()+"/"+driver.getCountry());
		//terminate First consultant account who is sponser of second consultant
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(firstConsultantEmailID, password);
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
		storeFrontHomePage.loginAsConsultant(firstConsultantEmailID, password);
		s_assert.assertTrue(storeFrontHomePage.isCurrentURLShowsError(),"Terminated User doesn't get Login failed"); 
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		//terminate second consultant account
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(secondConsultantEmailID, password);
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
		storeFrontHomePage.loginAsConsultant(secondConsultantEmailID, password);
		s_assert.assertTrue(storeFrontHomePage.isCurrentURLShowsError(),"Terminated User doesn't get Login failed"); 
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		//register as PC with email id of second terminate consultant
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		// Products are displayed?
		s_assert.assertTrue(storeFrontHomePage.areProductsDisplayed(), "quickshop products not displayed");
		logger.info("Quick shop products are displayed");
		storeFrontHomePage.selectProductAndProceedToBuy();
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
		storeFrontHomePage.enterEmailAddress(secondConsultantEmailID);
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
		storeFrontHomePage.enterNewPCDetails(firstName, lastName,secondConsultantEmailID, password);
		//verify for the sponser of pc user should be the sponser of terminated consultant one
		s_assert.assertTrue(storeFrontHomePage.getSponserNameFromUIWhileEnrollingPCUser().contains(emailIdOfFirstConsultantSponser),"Sponser for pc user is not as expected");
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
		s_assert.assertAll();
	}

	// Hybris Project-1295 Express Enrollment switch to Standard Enrollment - Step 4
	//Hybris Project-3957:During enrollment process, switch from Express Enrollment to standard Enrollment
	@Test
	public void testDuringEnrollmentSwitchFromExpressToStandardEnrollment_3957_1295() throws InterruptedException {
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(sponsor);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, TestConstants.FIRST_NAME+randomNum, TestConstants.LAST_NAME+randomNum, password, addressLine1, city,state, postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		////storeFrontHomePage.acceptTheVerifyYourShippingAddressPop();  
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		//click on switch to 'standard enrollment' link
		storeFrontHomePage.clickOnSwitchToStandardEnrollmentLink(); 
		storeFrontHomePage.checkPulseAndCRPEnrollment();
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.selectProductAndProceedToAddToCRP();
		storeFrontHomePage.addQuantityOfProduct("5");
		storeFrontHomePage.clickOnNextBtnAfterAddingProductAndQty();
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		s_assert.assertAll(); 
	}

	//Hybris Project-96:Express Enrollment Billing Info Shipping Info - New
	@Test(enabled=false)// Covered in TC-62
	public void testExpressEnrollmentBillingInfoShippingInfo_New_96() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		country = driver.getCountry();
		String newAddressLine1 = null;
		enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
		regimenName = TestConstants.REGIMEN_NAME_UNBLEMISH;
		String firstName=TestConstants.FIRST_NAME+randomNum;

		storeFrontHomePage = new StoreFrontHomePage(driver);
		storeFrontHomePage.openPWSSite(country, driver.getEnvironment());
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, firstName, TestConstants.LAST_NAME+randomNum, password, addressLine1, city,state, postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		//storeFrontHomePage.checkPulseAndCRPEnrollment();
		//storeFrontHomePage.uncheckCRPCheckBox();
		//storeFrontHomePage.uncheckPulseCheckBox();
		storeFrontHomePage.clickOnReviewAndConfirmShippingEditBtn();
		storeFrontHomePage.enterPassword(password);
		storeFrontHomePage.enterConfirmPassword(password);
		storeFrontHomePage.enterNewShippingAddressLine1DuringEnrollment(newAddressLine1);
		storeFrontHomePage.clickNextButton();
		s_assert.assertTrue(storeFrontHomePage.verifyNewAddressPresentInMainAccountInfo(newAddressLine1),"new address not present in main account info");
		s_assert.assertTrue(storeFrontHomePage.validateBillingAddressOnMainAccountInfo(addressLine1),"Billing address is not present as expected");
		s_assert.assertTrue(storeFrontHomePage.isTheTermsAndConditionsCheckBoxDisplayed(), "Terms and Conditions checkbox is not visible");
		storeFrontHomePage.checkThePoliciesAndProceduresCheckBox();
		storeFrontHomePage.checkTheIAcknowledgeCheckBox();  
		storeFrontHomePage.checkTheIAgreeCheckBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		s_assert.assertTrue(storeFrontHomePage.verifyPopUpForTermsAndConditions(), "PopUp for policies and procedures is not visible");
		storeFrontHomePage.checkTheTermsAndConditionsCheckBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		s_assert.assertAll();
	}

	//Hybris Project-95:Express Enrollment Billing Profile Main Account Info - New
	@Test(enabled=false)// Covered in TC-62
	public void testExpressEnrollmentBillingProfileMainAccountInfo_New_95() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		country = driver.getCountry();
		String newAddressLine1 = null;
		enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
		regimenName = TestConstants.REGIMEN_NAME_UNBLEMISH;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		storeFrontHomePage.openPWSSite(country, driver.getEnvironment());
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, firstName, TestConstants.LAST_NAME+randomNum, password, addressLine1, city,state, postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		//storeFrontHomePage.checkPulseAndCRPEnrollment();
		//storeFrontHomePage.uncheckCRPCheckBox();
		//storeFrontHomePage.uncheckPulseCheckBox();
		storeFrontHomePage.clickOnReviewAndConfirmShippingEditBtn();
		storeFrontHomePage.enterPassword(password);
		storeFrontHomePage.enterConfirmPassword(password);
		storeFrontHomePage.enterNewShippingAddressLine1DuringEnrollment(newAddressLine1);
		storeFrontHomePage.clickNextButton();
		s_assert.assertTrue(storeFrontHomePage.verifyNewAddressPresentInMainAccountInfo(newAddressLine1),"new address not present in main account info");
		s_assert.assertTrue(storeFrontHomePage.verifyPhoneNumberIsPresentInAccountInfo(),"Phone number is not present");
		s_assert.assertTrue(storeFrontHomePage.isTheTermsAndConditionsCheckBoxDisplayed(), "Terms and Conditions checkbox is not visible");
		storeFrontHomePage.checkThePoliciesAndProceduresCheckBox();
		storeFrontHomePage.checkTheIAcknowledgeCheckBox();  
		storeFrontHomePage.checkTheIAgreeCheckBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		s_assert.assertTrue(storeFrontHomePage.verifyPopUpForTermsAndConditions(), "PopUp for policies and procedures is not visible");
		storeFrontHomePage.checkTheTermsAndConditionsCheckBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		s_assert.assertAll();
	}

	//Hybris Project-5137:Global Sponsorship : Cross Country Sponsor
	@Test(enabled=false)//covered in TC-2251 Express
	public void testGlobalSponsorshipCrossCountrySponsor_5137() throws InterruptedException{
		RFO_DB = driver.getDBNameRFO(); 
		List<Map<String, Object>> randomConsultantList =  null;
		String accountID = null;

		country = driver.getCountry();
		String sponsorID = null;
		storeFrontHomePage = new StoreFrontHomePage(driver);

		// find cross country sponsor
		if(driver.getCountry().equalsIgnoreCase("us")){
			countryId = "40";
		}else{
			countryId = "236";
		}

		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
		accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
		logger.info("Account Id of the user is "+accountID);

		// Get Account Number
		List<Map<String, Object>>sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,accountID),RFO_DB);
		sponsorID = (String) getValueFromQueryResult(sponsorIdList, "AccountNumber");
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		s_assert.assertTrue(storeFrontHomePage.verifyFindYourSponsorPage(), "Find your sponsor page is not present");


		// assert for CID
		storeFrontHomePage.searchCID(sponsorID);
		s_assert.assertTrue(storeFrontHomePage.verifySponsorListIsPresentAfterClickOnSearch(), "sponsor list is not present when serach by CID");
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();

		String currentPWS = driver.getCurrentUrl();
		s_assert.assertFalse(currentPWS.contains("corp"), "After select sponsor current url does  contain corp");

		//complete the checkout process
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		country = driver.getCountry();
		enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
		regimenName = TestConstants.REGIMEN_NAME_UNBLEMISH;
		String firstName=TestConstants.FIRST_NAME+randomNum;

		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, firstName, TestConstants.LAST_NAME+randomNum, password, addressLine1, city,state, postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.checkThePoliciesAndProceduresCheckBox();
		storeFrontHomePage.checkTheIAcknowledgeCheckBox();  
		storeFrontHomePage.checkTheIAgreeCheckBox();
		storeFrontHomePage.checkTheTermsAndConditionsCheckBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		s_assert.assertAll();
	}

	//Hybris Project-4441:BIZ: Enroll CA/US User, Sponsor with PWS
	@Test(enabled=false)//Needs Re-work
	public void testEnrollCAOrUSUserAndSponsorWithPWS_4441() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		String country = driver.getCountry();
		RFO_DB = driver.getDBNameRFO();
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String lastName = "lN";
		country = driver.getCountry();
		enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
		regimenName = TestConstants.REGIMEN_NAME_REDEFINE;
		String enrolledConsultantPWS=null;
		String PWSAfterEnrollment = null;
		storeFrontHomePage = new StoreFrontHomePage(driver);
		//Get Biz PWS from database to start enrolling consultant. 
		List<Map<String, Object>> randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",driver.getCountry(),countryId),RFO_DB);
		String bizPWS=(String) getValueFromQueryResult(randomConsultantList, "URL"); 
		//Open Biz PWS.
		storeFrontHomePage.openConsultantPWS(bizPWS);
		while(true){
			if(driver.getCurrentUrl().contains("sitenotfound")){
				randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",driver.getCountry(),countryId),RFO_DB);
				bizPWS=(String) getValueFromQueryResult(randomConsultantList, "URL");  
				storeFrontHomePage.openConsultantPWS(bizPWS); 
				continue;
			}else
				break;
		} 
		logger.info("biz pws to start enroll is "+bizPWS);
		//Click Enroll now link.
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, TestConstants.FIRST_NAME+randomNum, TestConstants.LAST_NAME+randomNum, password, addressLine1, city,state,postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		//Enter Billing Profile
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		enrolledConsultantPWS = storeFrontHomePage.getDotBizPWS();
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		PWSAfterEnrollment = driver.getCurrentUrl();
		s_assert.assertTrue(PWSAfterEnrollment.contains(enrolledConsultantPWS.split("\\:")[1]), "Expected PWS after enrollment is "+enrolledConsultantPWS+" and on UI after complete the enrollment "+PWSAfterEnrollment);
		s_assert.assertAll();
	}

	//Hybris Project-3972:Verify if already existing prefix is allowed during consultant enrollment.
	@Test
	public void testVerifyAlreadyExistingPrefixDuringConsultantEnrollment_3972() throws InterruptedException{
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		List<Map<String, Object>> randomConsultantList=null;
		List<Map<String, Object>> sitePrefixList=null;
		String firstName=TestConstants.FIRST_NAME+randomNumber;
		//Get biz pws from database to start enrolling consultant.
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",driver.getCountry(),countryId),RFO_DB);
		String bizPWSForEnrollment=String.valueOf(getValueFromQueryResult(randomConsultantList, "URL"));
		//Open com pws of Sponser
		logger.info("biz pws to start enroll is "+bizPWSForEnrollment);
		storeFrontHomePage.openConsultantPWS(bizPWSForEnrollment);
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType,firstName, TestConstants.LAST_NAME+randomNumber, password, addressLine1, city,state, postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();

		//Get Current prefix name.
		String currentPrefix=storeFrontHomePage.getExistingWebsitePrefixName();
		logger.info("current website prefix is "+currentPrefix);
		//Get already existing prefix from database.
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",driver.getCountry(),countryId),RFO_DB);
		String accountIdUsedToGetSitePrefix = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
		sitePrefixList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_ALREADY_EXISTING_SITE_PREFIX_RFO,countryId,accountIdUsedToGetSitePrefix),RFO_DB);
		String sitePrefix=String.valueOf(getValueFromQueryResult(sitePrefixList, "SitePrefix"));
		logger.info("existing website prefix is "+sitePrefix);
		//Validate already existing site prefix allows consultant enrollment.
		storeFrontHomePage.enterWebsitePrefixName(sitePrefix);
		s_assert.assertTrue(storeFrontHomePage.verifyAlreadyExistingPrefixIsNotAllowed(),"Error message for already existing prefix is not present");

		//Enter current prefix
		storeFrontHomePage.enterWebsitePrefixName(currentPrefix);
		s_assert.assertFalse(storeFrontHomePage.verifyAlreadyExistingPrefixIsNotAllowed(),"Error message For current prefix is present");
		s_assert.assertAll(); 
	}

	//Hybris Project-94:Express Enrollment Billing Profile Billing Info - Edit
	@Test(enabled=false)// Covered in TC-2202
	public void testExpressEnrollmentBillingProfileBillingInfo_Edit_94() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		country = driver.getCountry();
		enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
		regimenName = TestConstants.REGIMEN_NAME_UNBLEMISH;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		storeFrontHomePage.openPWSSite(country, driver.getEnvironment());
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, firstName, TestConstants.LAST_NAME+randomNum, password, addressLine1, city,state, postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.clickOnReviewAndConfirmBillingEditBtn();
		storeFrontHomePage.enterEditedCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		String expirationMonth = storeFrontHomePage.getExpirationMonth();
		String expirationYear = storeFrontHomePage.getExpirationYear();
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.clickNextButton();
		s_assert.assertTrue(storeFrontHomePage.validateBillingExpirationDate(expirationYear),"billling date is not as expected");
		s_assert.assertTrue(storeFrontHomePage.validateBillingInfoUpdated(expirationMonth,expirationYear),"billing info is not updated as expected");
		s_assert.assertAll();
	}

	//Hybris Project-1666:Express Enrollment for Quebec province - not allowed
	@Test
	public void testExpressEnrollmentForQuebecProvince_notAllowed_1666() throws InterruptedException{
		if(driver.getCountry().trim().equalsIgnoreCase("CA")){
			enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
			regimenName = TestConstants.REGIMEN_NAME_REDEFINE;
			kitName = TestConstants.KIT_NAME_PERSONAL;    
			storeFrontHomePage = new StoreFrontHomePage(driver);
			storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
			storeFrontHomePage.searchCID();
			storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
			storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);  
			storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
			s_assert.assertTrue(storeFrontHomePage.verifyQuebecProvinceIsDisabled(),"Quebec province is enabled");
			s_assert.assertAll();
		}else{
			logger.info("NOT EXECUTED...Test is ONLY for CANADA env");
		}
	}	

	//Hybris Project-3613:20150203-11:53:35 Express Enrollment for Yukon province
	@Test
	public void testExpressEnrollmentForYukonProvince_3613() throws InterruptedException{
		if(driver.getCountry().equalsIgnoreCase("ca")){
			int randomNum = CommonUtils.getRandomNum(10000, 1000000);
			String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
			country = driver.getCountry();
			enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
			regimenName = TestConstants.REGIMEN_NAME_UNBLEMISH;
			String firstName=TestConstants.FIRST_NAME+randomNum;
			String emailAddress = firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
			String province = TestConstants.PROVINCE_YUKON;
			kitName = TestConstants.KIT_NAME_EXPRESS;    
			addressLine1 = TestConstants.ADDRESS_LINE_1_CA;
			city = TestConstants.CITY_CA;
			postalCode = TestConstants.POSTAL_CODE_CA;
			phoneNumber = TestConstants.PHONE_NUMBER_CA;
			state = TestConstants.PROVINCE_CA;
			storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
			storeFrontHomePage.searchCID();
			storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
			storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, firstName, TestConstants.LAST_NAME+randomNum, emailAddress,password, addressLine1, city,province, postalCode, phoneNumber);
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
			storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
			s_assert.assertTrue(storeFrontHomePage.verifyPopUpForTermsAndConditions(), "PopUp for policies and procedures is not visible");
			storeFrontHomePage.checkTheTermsAndConditionsCheckBox();
			storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
			storeFrontHomePage.clickOnConfirmAutomaticPayment();
			s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
			storeFrontHomePage.clickOnRodanAndFieldsLogo();
			s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
			s_assert.assertAll();
		}else{
			logger.info("NOT EXECUTED...Test is ONLY for CANADA env");
		}
	}

	//Hybris Project-4770:pulse cancellation
	@Test(enabled=false)//ALready covered in Accounts TC-2233
	public void testPulseCancellation_4770() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		String firstName = TestConstants.FIRST_NAME+randomNum;
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(sponsor);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, firstName, TestConstants.LAST_NAME+randomNum, password, 
				addressLine1, city,state, postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		//validate on the confirmation page user lands on .biz site
		//s_assert.assertTrue(storeFrontHomePage.validateUserLandsOnPWSbizSite(), "user didn't land on PWS .biz site");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		//Fetch the PWS url
		// String currentPWSUrl=driver.getCurrentUrl();
		//logger.info("PWS of the user is "+currentPWSUrl);
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage=storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountInfoPage.clickOnAutoshipStatusLink();
		storeFrontAccountInfoPage.cancelPulseSubscription();
		s_assert.assertTrue(storeFrontAccountInfoPage.validatePulseCancelled(),"pulse has not been cancelled for consultant");
		s_assert.assertAll();
	}

	//Hybris Project-2205:Consultant trying to enroll under invalid sponsor then follow 'select upline' flow and complete enro
	@Test
	public void testConsultantReenrollmentUnderInvalidSponsorThenSelectUplineFlowAndCompleteEnrollment_2205() throws InterruptedException{
		RFO_DB = driver.getDBNameRFO(); 
		List<Map<String, Object>> randomConsultantList =  null;
		String consultantEmailID = null;
		String accountID = null;
		enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
		regimenName =  TestConstants.REGIMEN_NAME_REDEFINE;
		storeFrontHomePage = new StoreFrontHomePage(driver);
		country = driver.getCountry();
		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment(),driver.getCountry(),countryId),RFO_DB);
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
		//Again enroll the consultant with same eamil id
		// Get sponser with PWS from database
		List<Map<String, Object>> sponserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment(),driver.getCountry(),countryId),RFO_DB);
		String sponserHavingPulse = String.valueOf(getValueFromQueryResult(sponserList, "AccountID"));
		// sponser search by Account Number
		List<Map<String, Object>> sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,sponserHavingPulse),RFO_DB);
		String sponsorId = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(sponsorId);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);  
		storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
		storeFrontHomePage.enterEmailAddress(consultantEmailID);
		s_assert.assertTrue(storeFrontHomePage.verifyInvalidSponsorPopupIsPresent(), "Invalid Sponsor popup is not present");
		storeFrontHomePage.clickOnEnrollUnderLastUpline();
		// For enroll the same consultant
		storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName); 
		storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
		storeFrontHomePage.enterEmailAddress(consultantEmailID);
		storeFrontHomePage.enterPasswordForReactivationForConsultant();
		storeFrontHomePage.clickOnLoginToReactiveMyAccountForConsultant();
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		s_assert.assertAll();
	}

	//Hybris Project-2252:Verify if inactive sponsor can be searched on Search Sponsor screen.
	@Test
	public void testInactiveSponsorSearch_2252() throws InterruptedException{
		//		RFO_DB = driver.getDBNameRFO(); 
		List<Map<String, Object>> randomInactiveConsultantList =  null;
		List<Map<String, Object>> randomInactiveConsultantAccountNumberList =  null;
		String accountID = null;
		String accountNumber = null;
		randomInactiveConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_CONSULTANT_INACTIVE_RFO_4179,countryId),RFO_DB);
		accountID = String.valueOf(getValueFromQueryResult(randomInactiveConsultantList, "AccountID"));
		logger.info("Account Id of the user is "+accountID);
		randomInactiveConsultantAccountNumberList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,accountID),RFO_DB);
		accountNumber = String.valueOf(getValueFromQueryResult(randomInactiveConsultantAccountNumberList, "AccountNumber"));
		logger.info("Account Number of the user is "+accountNumber);
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(accountNumber);
		s_assert.assertTrue(storeFrontHomePage.isNoSearchResultMsg(), "No such result message is not found for inactive account Number "+accountNumber);
		s_assert.assertAll();
	}

	// Hybris Project-3927:CORP:Enroll CA/US User, Sponsor without PWS
	@Test
	public void testEnrollCA_USUserSponsorWithoutPWS_3927() throws InterruptedException{
		List<Map<String, Object>> randomConsultantList =  null;
		String accountNumber = null;
		String currentURL = null;
		String CCS = null;
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		String enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
		String regimenName = TestConstants.REGIMEN_NAME_REVERSE;
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITHOUT_PULSE_RFO,"40"),RFO_DB);
		accountNumber = (String) getValueFromQueryResult(randomConsultantList, "AccountNumber");
		storeFrontHomePage = new StoreFrontHomePage(driver);
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(accountNumber);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		currentURL = storeFrontHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.contains("corprfo."), "User is redirecting to PWS after slecting sponsor");
		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, TestConstants.FIRST_NAME+randomNum, TestConstants.LAST_NAME+randomNum, password, addressLine1, city,state, postalCode, phoneNumber);
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
		s_assert.assertTrue(driver.getCurrentUrl().contains("biz"),"url is not a biz url");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		logout();
		navigateToStoreFrontBaseURL();

		//-------------------------------------------------------------------------------------------------
		//verify on cross country sponsor
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		// verify with cross country sponsor
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
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITHOUT_PULSE_RFO,"40"),RFO_DB);
		CCS = (String) getValueFromQueryResult(randomConsultantList, "AccountNumber");

		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(CCS);
		s_assert.assertTrue(storeFrontHomePage.isSponsorPresentInSearchList(), "No Sponsor present in search results");
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		currentURL = storeFrontHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.contains("corprfo."), "User is redirecting to PWS after slecting cross country sponsor");
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
		s_assert.assertTrue(currentURL.contains(""+randomNum), "User is not redirecting to own PWS after enrollment");
		s_assert.assertAll();

	}
	// Hybris Project-3966:Use email id of existing inactive consultant (more than 180 days) during consultant enrollment under different sponsor's .biz site.
	@Test(enabled=false)//Query giving null values
	public void testUseEmailIdOfExistingInactiveConsultantDuringEnrollmentUnderDifferentBizSite_3966() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		List<Map<String, Object>> randomConsultantList =  null;
		List<Map<String, Object>> randomInactiveConsultantList =  null;
		String consultantPWS = null;
		String consultantEmailID = null;
		while (true) {
			randomConsultantList = DBUtil.performDatabaseQuery(
					DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,
							driver.getEnvironment() + ".biz", country, countryId),RFO_DB);

			consultantPWS = (String) getValueFromQueryResult(randomConsultantList, "URL");
			for(int i=1;i<=5;i++){
				randomInactiveConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(
						DBQueries_RFO.GET_RANDOM_CONSULTANTS_INACTIVE_MORE_THAN_180_DAYS, countryId), RFO_DB);
				consultantEmailID = (String) getValueFromQueryResult(randomInactiveConsultantList, "EmailAddress");
				if(consultantEmailID==null){
					continue;
				}
				else 
					break;				
			}

			driver.get(consultantPWS);
			boolean isLoginError = driver.getCurrentUrl().contains("sitenotfound");
			if (isLoginError) {
				logger.info("Site not found for the consultant PWS " + consultantPWS);
			} else
				break;
		}

		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, TestConstants.FIRST_NAME+randomNum, TestConstants.LAST_NAME+randomNum, consultantEmailID, password, addressLine1, city,state, postalCode, phoneNumber);
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
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		s_assert.assertAll(); 
	}

	//Hybris Project-4309 :: Version : 1 :: Soft-Terminated Consultant Cancel Reactivation
	@Test (enabled=false)//Covered in TC 4308
	public void testTerminateConsultantAndCancelReactivation_4309() throws InterruptedException{
		RFO_DB = driver.getDBNameRFO(); 
		List<Map<String, Object>> randomConsultantList =  null;
		String consultantEmailID = null;
		String accountID = null;
		enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
		regimenName =  TestConstants.REGIMEN_NAME_REDEFINE;
		storeFrontHomePage = new StoreFrontHomePage(driver);
		country = driver.getCountry();

		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
			accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			logger.info("Account Id of the user is "+accountID);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				if(driver.getCountry().equalsIgnoreCase("ca")||driver.getCountry().equalsIgnoreCase("au")){
					driver.get(driver.getURL()+"/"+driver.getCountry());
				}else{
					driver.get(driver.getURL());
				}
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
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID();
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);  
		storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
		storeFrontHomePage.enterEmailAddress(consultantEmailID);
		s_assert.assertTrue(storeFrontHomePage.verifyInvalidSponsorPopupIsPresent(), "Invalid Sponsor popup is not present");
		storeFrontHomePage.clickOnCnacelEnrollment();

		//verify that user is inactive
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		s_assert.assertTrue(storeFrontHomePage.isCurrentURLShowsError(),"Terminated User doesn't get Login failed");  
		s_assert.assertAll();
	}

	//Hybris Project-4311 Reactivated Soft-Terminated Consultant should be in the search for Sponsor list
	//Hybris Project-4309 Soft-Terminated Consultant Cancel Reactivation
	//Hybris Project-4308 Soft-Terminated Consultant reactivates his account and perform Ad Hoc order
	//Hybris Project-4310 Soft-Terminate Consultant is not available for Sponsor's search
	//Hybris Project-3720 Perform Consultant Account termination through my account
	//Hybris Project-4663 Consultant Account Termination
	//Hybris Project-1975 Consultant Termination
	@Test 
	public void testTerminateConsultantAndReactivateAndPerformAdhocOrder_4308_4309_4310_4311_3720_4663_1975() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		List<Map<String, Object>> randomConsultantList =  null;
		List<Map<String, Object>> randomConsultantEmailList =  null;
		String consultantEmailID = null;
		String accountID = null;
		String lastName = "lN";
		enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
		regimenName =  TestConstants.REGIMEN_NAME_REDEFINE;
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME_US+randomNum;
		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
			//consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");		
			accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			randomConsultantEmailList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_EMAIL_ID_FROM_ACCOUNT_ID,accountID),RFO_DB);
			consultantEmailID = String.valueOf(getValueFromQueryResult(randomConsultantEmailList, "EmailAddress"));
			logger.info("Account Id of the user is "+accountID);
			logger.info("consultantEmailID of the user is "+consultantEmailID);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				if(driver.getCountry().equalsIgnoreCase("ca")||driver.getCountry().equalsIgnoreCase("au")){
					driver.get(driver.getURL()+"/"+driver.getCountry());
				}else{
					driver.get(driver.getURL());
				}
			}
			else
				break;
		}
		// Get Account Number
		List<Map<String, Object>>sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,accountID),RFO_DB);
		String CCS = (String) getValueFromQueryResult(sponsorIdList, "AccountNumber");
		//s_assert.assertTrue(storeFrontConsultantPage.verifyConsultantPage(),"Consultant Page doesn't contain Welcome User Message");
		logger.info("login is successful");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountTerminationPage = storeFrontAccountInfoPage.clickTerminateMyAccount();
		storeFrontAccountTerminationPage.fillTheEntriesAndClickOnSubmitDuringTermination();
		s_assert.assertTrue(storeFrontAccountTerminationPage.verifyAccountTerminationIsConfirmedPopup(), "Account still exist");
		storeFrontAccountTerminationPage.clickConfirmTerminationBtn();
		storeFrontAccountTerminationPage.clickOnCloseWindowAfterTermination();
		storeFrontHomePage.clickOnCountryAtWelcomePage();

		// search consultant as sponsor
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(CCS);
		s_assert.assertTrue(storeFrontHomePage.verifyTerminatedConsultantIsNotInSponsorList(), "Terminated Consultant is present in sponsor's list");

		//Again enroll the consultant with same eamil id for cancel reactivation
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID();
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);  
		storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
		storeFrontHomePage.enterEmailAddress(consultantEmailID);
		s_assert.assertTrue(storeFrontHomePage.verifyInvalidSponsorPopupIsPresent(), "Invalid Sponsor popup is not present");
		storeFrontHomePage.clickOnCnacelEnrollment();
		//verify that user is inactive
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		s_assert.assertTrue(storeFrontHomePage.isCurrentURLShowsError(),"Terminated User doesn't get Login failed");  
		//Again enroll the consultant with same eamil id
		driver.get(driver.getURL()+"/"+driver.getCountry());
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID();
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);		
		storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
		storeFrontHomePage.enterEmailAddress(consultantEmailID);
		s_assert.assertTrue(storeFrontHomePage.verifyInvalidSponsorPopupIsPresent(), "Invalid Sponsor popup is not present");
		storeFrontHomePage.clickOnEnrollUnderLastUpline();
		storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);		
		storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
		storeFrontHomePage.enterEmailAddress(consultantEmailID);
		storeFrontHomePage.enterPasswordForReactivationForConsultant();
		storeFrontHomePage.clickOnLoginToReactiveMyAccountForConsultant();
		logout();
		driver.get(driver.getURL()+"/"+driver.getCountry());
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(CCS);
		s_assert.assertTrue(storeFrontHomePage.verifyTerminatedConsultantPresentInSponsorList(), "Terminated Consultant is not present in sponsor's list");
		driver.get(driver.getURL()+"/"+driver.getCountry());
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		StoreFrontUpdateCartPage storeFrontUpdateCartPage = new StoreFrontUpdateCartPage(driver);
		storeFrontUpdateCartPage.clickAddToBagButton(driver.getCountry());
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		storeFrontUpdateCartPage.clickOnDefaultBillingProfileEdit();
		storeFrontUpdateCartPage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontUpdateCartPage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontUpdateCartPage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontUpdateCartPage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontUpdateCartPage.selectNewBillingCardAddress();
		storeFrontUpdateCartPage.clickOnSaveBillingProfile();
		storeFrontUpdateCartPage.clickOnBillingNextStepBtn();
		storeFrontUpdateCartPage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyOrderPlacedConfirmationMessage(), "Order has been not placed successfully");
		s_assert.assertAll();
	}

	//Hybris Project-4310 :: Version : 1 :: Soft-Terminate Consultant is not available for Sponsor's search
	@Test (enabled=false)//Covered in TC 4308
	public void testTerminatedConsultantIsNotAvailableAsSponsor_4310() throws InterruptedException{
		RFO_DB = driver.getDBNameRFO(); 
		List<Map<String, Object>> randomConsultantList =  null;
		String consultantEmailID = null;
		String accountID = null;
		enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
		regimenName =  TestConstants.REGIMEN_NAME_REDEFINE;
		storeFrontHomePage = new StoreFrontHomePage(driver);
		country = driver.getCountry();
		String CCS = null;
		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
			accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			logger.info("Account Id of the user is "+accountID);

			// Get Account Number
			List<Map<String, Object>>sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,accountID),RFO_DB);
			CCS = (String) getValueFromQueryResult(sponsorIdList, "AccountNumber");

			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				if(driver.getCountry().equalsIgnoreCase("ca")||driver.getCountry().equalsIgnoreCase("au")){
					driver.get(driver.getURL()+"/"+driver.getCountry());
				}else{
					driver.get(driver.getURL());
				}
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
		// search consultant as sponsor
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(CCS);
		s_assert.assertTrue(storeFrontHomePage.verifyTerminatedConsultantIsNotInSponsorList(), "Terminated Consultant is present in sponsor's list");
		s_assert.assertAll();
	}

	//Hybris Project-4311 :: Version : 1 :: Reactivated Soft-Terminated Consultant should be in the search for Sponsor list
	@Test (enabled=false)//Covered in TC 4308
	public void ReactivateTerminatedConsultantAndSearchInSponsorList_4311() throws InterruptedException{
		RFO_DB = driver.getDBNameRFO(); 
		List<Map<String, Object>> randomConsultantList =  null;
		String consultantEmailID = null;
		String accountID = null;
		enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
		regimenName =  TestConstants.REGIMEN_NAME_REDEFINE;
		storeFrontHomePage = new StoreFrontHomePage(driver);
		country = driver.getCountry();
		String  CCS = null;

		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
			accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			logger.info("Account Id of the user is "+accountID);

			// Get Account Number
			List<Map<String, Object>>sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,accountID),RFO_DB);
			CCS = (String) getValueFromQueryResult(sponsorIdList, "AccountNumber");
			logger.info("Account number of the consultant is "+CCS);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				if(driver.getCountry().equalsIgnoreCase("ca")||driver.getCountry().equalsIgnoreCase("au")){
					driver.get(driver.getURL()+"/"+driver.getCountry());
				}else{
					driver.get(driver.getURL());
				}
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

		driver.get(driver.getURL()+"/"+driver.getCountry());
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
		accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
		logger.info("Account Id of the user is "+accountID);

		// Get Account Number
		List<Map<String, Object>>sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,accountID),RFO_DB);
		CCS = (String) getValueFromQueryResult(sponsorIdList, "AccountNumber");


		storeFrontHomePage.searchCID(CCS);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);  
		storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
		storeFrontHomePage.enterEmailAddress(consultantEmailID);
		s_assert.assertTrue(storeFrontHomePage.verifyInvalidSponsorPopupIsPresent(), "Invalid Sponsor popup is not present");
		storeFrontHomePage.clickOnEnrollUnderLastUpline();

		// For enroll the same consultant
		storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);  
		storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
		storeFrontHomePage.enterEmailAddress(consultantEmailID);
		storeFrontHomePage.enterPasswordForReactivationForConsultant();
		storeFrontHomePage.clickOnLoginToReactiveMyAccountForConsultant();
		logout();
		driver.get(driver.getURL()+"/"+driver.getCountry());
		// search the same consultant in sponsor's list 
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(CCS);
		s_assert.assertTrue(storeFrontHomePage.verifyTerminatedConsultantPresentInSponsorList(), "Terminated Consultant is not present in sponsor's list");
		s_assert.assertAll();
	}

	//Hybris Project-1699 :: Version : 1 :: Express Enrollment for Nunavut province and tryto ship adhoc order at quebec address. 
	@Test(enabled=true)
	public void testExpressEnrollmentNunavutProvince_1699() throws InterruptedException{
		if(driver.getCountry().equalsIgnoreCase("ca")){
			int randomNum = CommonUtils.getRandomNum(10000, 1000000);
			String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
			enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
			regimenName = TestConstants.REGIMEN_NAME_REDEFINE;
			kitName = TestConstants.KIT_NAME_PERSONAL;    
			addressLine1 = TestConstants.ADDRESS_LINE_1_NUNAVUT;
			city = TestConstants.CITY_NUNAVUT;
			postalCode = TestConstants.POSTAL_CODE_NUNAVUT;
			phoneNumber = TestConstants.PHONE_NUMBER_NUNAVUT;
			String state = TestConstants.PROVINCE_QUEBEC;

			storeFrontHomePage = new StoreFrontHomePage(driver);
			storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();

			storeFrontHomePage.searchCID();
			storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
			storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, TestConstants.FIRST_NAME+randomNum, TestConstants.LAST_NAME+randomNum, password, addressLine1, city,TestConstants.PROVINCE_NUNAVUT, postalCode, phoneNumber);
			storeFrontHomePage.clickNextButtonWithUseAsEnteredButton();
			//storeFrontHomePage.acceptTheVerifyYourShippingAddressPop();  
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
			storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
			storeFrontHomePage.clickOnConfirmAutomaticPayment();
			s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
			storeFrontHomePage.clickOnRodanAndFieldsLogo();
			s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
			storeFrontConsultantPage = new StoreFrontConsultantPage(driver);
			storeFrontConsultantPage.clickOnWelcomeDropDown();
			storeFrontShippingInfoPage = storeFrontConsultantPage.clickShippingLinkPresentOnWelcomeDropDown();
			s_assert.assertTrue(storeFrontShippingInfoPage.verifyShippingInfoPageIsDisplayed(),"shipping info page has not been displayed");
			storeFrontShippingInfoPage.clickAddNewShippingProfileLink();
			String newShippingAddressName = TestConstants.ADDRESS_NAME_US+randomNum;
			String lastName = "ln";
			storeFrontShippingInfoPage.enterNewShippingAddressName(newShippingAddressName+" "+lastName);
			storeFrontShippingInfoPage.enterNewShippingAddressLine1(TestConstants.ADDRESS_LINE_1_QUEBEC);
			storeFrontShippingInfoPage.enterNewShippingAddressCity(TestConstants.CITY_QUEBEC);
			s_assert.assertTrue(storeFrontHomePage.verifyQuebecProvinceIsDisabled(),"Quebec province in the province drop down is not disabled");
			s_assert.assertAll();
		}else{
			logger.info("NOT EXECUTED...Test is ONLY for CANADA env");
		}

	}

	//Hybris Project-1698 :: Version : 1 :: Express Enrollment for Northwest territories province and adding qubec address and make it default.
	@Test(enabled=true)
	public void testExpressEnrollmentNorthWestProvince_1698() throws InterruptedException{
		if(driver.getCountry().equalsIgnoreCase("ca")){
			int randomNum = CommonUtils.getRandomNum(10000, 1000000);
			String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
			enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
			regimenName = TestConstants.REGIMEN_NAME_REDEFINE;
			kitName = TestConstants.KIT_NAME_PERSONAL;    
			addressLine1 = TestConstants.ADDRESS_LINE_1_NT;
			city = TestConstants.CITY_NT;
			postalCode = TestConstants.POSTAL_CODE_NT;
			phoneNumber = TestConstants.PHONE_NUMBER_NT;
			String state = TestConstants.PROVINCE_QUEBEC;

			storeFrontHomePage = new StoreFrontHomePage(driver);
			storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();

			storeFrontHomePage.searchCID();
			storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
			storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, TestConstants.FIRST_NAME+randomNum, TestConstants.LAST_NAME+randomNum, password, addressLine1, city,TestConstants.PROVINCE_NORTHWEST_TERRITORIES, postalCode, phoneNumber);
			storeFrontHomePage.clickNextButton();
			//storeFrontHomePage.acceptTheVerifyYourShippingAddressPop();  
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
			storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
			storeFrontHomePage.clickOnConfirmAutomaticPayment();
			s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
			storeFrontHomePage.clickOnRodanAndFieldsLogo();
			s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
			storeFrontConsultantPage = new StoreFrontConsultantPage(driver);
			storeFrontConsultantPage.clickOnWelcomeDropDown();
			storeFrontShippingInfoPage = storeFrontConsultantPage.clickShippingLinkPresentOnWelcomeDropDown();
			s_assert.assertTrue(storeFrontShippingInfoPage.verifyShippingInfoPageIsDisplayed(),"shipping info page has not been displayed");
			storeFrontShippingInfoPage.clickAddNewShippingProfileLink();
			String newShippingAddressName = TestConstants.ADDRESS_NAME_US+randomNum;
			String lastName = "ln";
			storeFrontShippingInfoPage.enterNewShippingAddressName(newShippingAddressName+" "+lastName);
			storeFrontShippingInfoPage.enterNewShippingAddressLine1(TestConstants.ADDRESS_LINE_1_QUEBEC);
			storeFrontShippingInfoPage.enterNewShippingAddressCity(TestConstants.CITY_QUEBEC);
			s_assert.assertTrue(storeFrontHomePage.verifyQuebecProvinceIsDisabled(),"Quebec province in the province drop down is not disabled");

			s_assert.assertAll();

		}else{
			logger.info("NOT EXECUTED...Test is ONLY for CANADA env");
		}
	}

	//Hybris Project-1292 :: Version : 1 :: Customer living in Quebec cannot be enrolled as consultant. 
	@Test
	public void testCustomerLivingInQuebecCannotEnroll_1292() throws InterruptedException{
		if(driver.getCountry().equalsIgnoreCase("ca")){
			enrollmentType = TestConstants.STANDARD_ENROLLMENT;
			regimenName = TestConstants.REGIMEN_NAME_REDEFINE;
			kitName = TestConstants.KIT_NAME_PERSONAL;			 
			addressLine1 = TestConstants.ADDRESS_LINE_1_QUEBEC;
			city = TestConstants.CITY_QUEBEC;
			postalCode = TestConstants.POSTAL_CODE_QUEBEC;
			phoneNumber = TestConstants.PHONE_NUMBER_CA;
			storeFrontHomePage = new StoreFrontHomePage(driver);
			storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
			storeFrontHomePage.searchCID();
			storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
			storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);		
			storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
			s_assert.assertTrue(storeFrontHomePage.verifyQuebecProvinceIsDisabled(),"Quebec province in the province drop down is not disabled");
			s_assert.assertAll();

		}else{
			logger.info("NOT EXECUTED...Test is ONLY for CANADA env");
		}
	}

	// Hybris Project-2190 :: Version : 1 :: Enrolling RC living in Quebec 
	@Test
	public void testQuebecRCEnrollment_2190() throws InterruptedException{
		if(driver.getCountry().equalsIgnoreCase("ca")){
			int randomNum = CommonUtils.getRandomNum(10000, 1000000);
			String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
			String lastName = "lN"+randomNum;;
			String firstName=TestConstants.FIRST_NAME+randomNum;
			String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
			addressLine1 = TestConstants.ADDRESS_LINE_1_QUEBEC;
			city = TestConstants.CITY_QUEBEC;
			postalCode = TestConstants.POSTAL_CODE_QUEBEC;
			phoneNumber = TestConstants.PHONE_NUMBER_CA;

			storeFrontHomePage = new StoreFrontHomePage(driver);
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

			//In the Cart page add one more product
			storeFrontHomePage.addAnotherProduct();

			//Two products are in the Shopping Cart?
			s_assert.assertTrue(storeFrontHomePage.verifyNumberOfProductsInCart("2"), "number of products in the cart is NOT 2");
			logger.info("2 products are successfully added to the cart");

			//Click on Check out
			storeFrontHomePage.clickOnCheckoutButton();

			//Log in or create an account page is displayed?
			s_assert.assertTrue(storeFrontHomePage.isLoginOrCreateAccountPageDisplayed(), "Login or Create Account page is NOT displayed");
			logger.info("Login or Create Account page is displayed");

			//Enter the User information and DO NOT check the "Become a Preferred Customer" checkbox and click the create account button
			storeFrontHomePage.enterNewRCDetails(firstName,lastName,emailAddress, password);

			//Enter the Main account info and DO NOT check the "Become a Preferred Customer" and click next
			storeFrontHomePage.enterMainAccountInfo(addressLine1, city, TestConstants.PROVINCE_QUEBEC, postalCode, phoneNumber);
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
			storeFrontHomePage.clickPlaceOrderBtn();
			s_assert.assertTrue(storeFrontHomePage.isOrderPlacedSuccessfully(), "Order Not placed successfully");
			s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
			s_assert.assertAll();	
		}else{
			logger.info("NOT EXECUTED...Test is ONLY for CANADA env");
		}
	}

	//Hybris Project-2189 :: Version : 1 :: Enrolling PC living in Quebec 
	@Test
	public void testQuebecPCEnrollment_2189() throws InterruptedException{
		if(driver.getCountry().equalsIgnoreCase("ca")){
			int randomNum = CommonUtils.getRandomNum(10000, 1000000);		
			String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
			String lastName = "lN"+randomNum;;
			String firstName=TestConstants.FIRST_NAME+randomNum;
			String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
			addressLine1 = TestConstants.ADDRESS_LINE_1_QUEBEC;
			city = TestConstants.CITY_QUEBEC;
			postalCode = TestConstants.POSTAL_CODE_QUEBEC;
			phoneNumber = TestConstants.PHONE_NUMBER_CA;
			storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
			//Select a product with the price less than $80 and proceed to buy it
			storeFrontHomePage.applyPriceFilterHighToLow();
			storeFrontHomePage.selectProductAndProceedToBuyWithoutFilter();
			storeFrontHomePage.clickOnCheckoutButton();
			//Enter the User information and DO NOT check the "Become a Preferred Customer" checkbox and click the create account button
			storeFrontHomePage.enterNewPCDetails(firstName,lastName,emailAddress, password);
			//Enter the Main account info and DO NOT check the "Become a Preferred Customer" and click next
			storeFrontHomePage.enterMainAccountInfo(addressLine1, city, TestConstants.PROVINCE_QUEBEC, postalCode, phoneNumber);
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
			storeFrontHomePage.clickOnRodanAndFieldsLogo();
			s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
			s_assert.assertAll();	
		}else{
			logger.info("NOT EXECUTED...Test is ONLY for CANADA env");
		}

	}

	// Hybris Project-2193 :: Version : 1 :: Verify if QAS gives quebec address suggestions for address entered during consultant enrollment.
	@Test
	public void testQASGivesSuggestionsForQuebecAddress_2193() throws InterruptedException{
		if(driver.getCountry().equalsIgnoreCase("ca")){
			int randomNum = CommonUtils.getRandomNum(10000, 1000000);
			enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
			regimenName = TestConstants.REGIMEN_NAME_REDEFINE;
			kitName = TestConstants.KIT_NAME_PERSONAL;    
			addressLine1 = TestConstants.ADDRESS_LINE_1_QUEBEC;
			city = TestConstants.CITY_QUEBEC;
			postalCode = TestConstants.POSTAL_CODE_QUEBEC;
			phoneNumber = TestConstants.PHONE_NUMBER_CA;

			storeFrontHomePage = new StoreFrontHomePage(driver);
			storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
			storeFrontHomePage.searchCID();
			storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
			storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, TestConstants.FIRST_NAME+randomNum, TestConstants.LAST_NAME+randomNum, password, addressLine1, city,TestConstants.PROVINCE_NUNAVUT, postalCode, phoneNumber);
			storeFrontHomePage.clickEnrollmentNextBtnWithoutHandlingPopUP();
			s_assert.assertTrue(storeFrontHomePage.verifyAndClickAcceptOnQASPopup(), "QAS pop up with Accept button for Quebec address suggestions has NOT appeared");
			s_assert.assertTrue(storeFrontHomePage.isQuebecNotEligibleAsConsultantErrorDisplayed(),"Quebec residents are ineligible to become a Consultant. message not displayed");
			s_assert.assertAll();
		}else{
			logger.info("NOT EXECUTED...Test is ONLY for CANADA env");
		}
	}

	// Hybris Project-1276:Email field validation for Active/Inactive users
	@Test
	public void testEmailValidationsDuringEnroll_1276() throws InterruptedException{
		if(country.equalsIgnoreCase("CA")) {
			List<Map<String, Object>> randomConsultantList =  null;
			List<Map<String, Object>> randomPCList =  null;
			List<Map<String, Object>> randomConsultantEmailList =  null;
			List<Map<String, Object>> randomPCEmailList =  null;
			List<Map<String, Object>> randomRCEmailList =  null;
			List<Map<String, Object>> randomRCUserList =  null;
			String terminatedConsultantEmailLessThan6month = null;
			String terminatedConsultantEmailMoreThan6month = null;
			String existingPCEmailID= null;
			String terminatedPCEmailLessThan90Days= null;
			String terminatedPCEmailMoreThan90Days= null;
			String rcUserEmailID = null;
			String consultantAccountID = null;
			String pcAccountId = null;

			String activeRCAccountId = null;
			//get terminated consultant user less than 6 month.
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_INACTIVE_CONSULTANT_LESS_THAN_6_MONTH_RFO,countryId),RFO_DB);
			consultantAccountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			randomConsultantEmailList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_EMAIL_ID_FROM_ACCOUNT_ID,consultantAccountID),RFO_DB);
			terminatedConsultantEmailLessThan6month = String.valueOf(getValueFromQueryResult(randomConsultantEmailList, "EmailAddress"));

			//get terminated consultant user more than 6 month.
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_INACTIVE_CONSULTANT_MORE_THAN_6_MONTH_RFO,countryId),RFO_DB);
			consultantAccountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			randomConsultantEmailList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_EMAIL_ID_FROM_ACCOUNT_ID,consultantAccountID),RFO_DB);
			terminatedConsultantEmailMoreThan6month = String.valueOf(getValueFromQueryResult(randomConsultantEmailList, "EmailAddress"));

			//get existing PC email
			randomPCList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_AUTOSHIPS_RFO,countryId),RFO_DB);
			pcAccountId = String.valueOf(getValueFromQueryResult(randomPCList, "AccountID"));
			randomPCEmailList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_EMAIL_ID_FROM_ACCOUNT_ID,pcAccountId),RFO_DB);
			existingPCEmailID = String.valueOf(getValueFromQueryResult(randomPCEmailList, "EmailAddress"));

			//get terminated PC user less than 90 days 
			randomPCList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_INACTIVE_PC_LESS_THAN_90_DAYS_RFO,countryId),RFO_DB);
			pcAccountId = String.valueOf(getValueFromQueryResult(randomPCList, "AccountID"));
			randomPCEmailList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_EMAIL_ID_FROM_ACCOUNT_ID,pcAccountId),RFO_DB);
			terminatedPCEmailLessThan90Days = String.valueOf(getValueFromQueryResult(randomPCEmailList, "EmailAddress"));

			//get terminated PC user more than 90 days 
			randomPCList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_INACTIVE_PC_MORE_THAN_90_DAYS_RFO,countryId),RFO_DB);
			pcAccountId = String.valueOf(getValueFromQueryResult(randomPCList, "AccountID"));
			randomPCEmailList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_EMAIL_ID_FROM_ACCOUNT_ID,pcAccountId),RFO_DB);
			terminatedPCEmailMoreThan90Days = String.valueOf(getValueFromQueryResult(randomPCEmailList, "EmailAddress"));

			//get existing RC email
			randomRCUserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),RFO_DB);
			activeRCAccountId = String.valueOf(getValueFromQueryResult(randomRCUserList, "AccountID"));
			randomRCEmailList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_EMAIL_ID_FROM_ACCOUNT_ID,activeRCAccountId),RFO_DB);
			rcUserEmailID = String.valueOf(getValueFromQueryResult(randomRCEmailList, "EmailAddress"));

			//initiate consultant enrollment
			storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
			storeFrontHomePage.searchCID(sponsor);
			storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
			storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);  
			storeFrontHomePage.chooseEnrollmentOption(TestConstants.STANDARD_ENROLLMENT);

			// assertion for Active PC
			storeFrontHomePage.enterEmailAddress(existingPCEmailID);
			s_assert.assertTrue(storeFrontHomePage.verifyPopUpForExistingActivePC() , "Existing Active PC User email id should not be acceptable");

			// assertion for Inactive PC less than 90 days
			storeFrontHomePage.enterEmailAddress(terminatedPCEmailLessThan90Days);
			s_assert.assertTrue(storeFrontHomePage.verifyPopUpForExistingInactivePC90Days() , "Inactive pc less than 90 days User email id should not be acceptable");

			// assertion for Inactive PC more than 90 days
			storeFrontHomePage.enterEmailAddress(terminatedPCEmailMoreThan90Days);
			s_assert.assertFalse(storeFrontHomePage.verifyPopUpForExistingInactivePC90Days() , "Inactive pc more than 90 days User email id should be acceptable");

			// assertion for terminated consultant less than 180 days
			storeFrontHomePage.enterEmailAddress(terminatedConsultantEmailLessThan6month);
			s_assert.assertTrue(storeFrontHomePage.verifyPopUpForExistingActiveCCLessThan6Month() , "Inactive Consultant less than 180 days User email id should not be acceptable");

			// assertion for terminated consultant more than 180 days
			storeFrontHomePage.enterEmailAddress(terminatedConsultantEmailMoreThan6month);
			s_assert.assertFalse(storeFrontHomePage.verifyPopUpForExistingActiveCCLessThan6Month() , "Inactive Consultant more than 180 days User email id should be acceptable");

			// assertion for Active RC
			storeFrontHomePage.enterEmailAddress(rcUserEmailID);
			s_assert.assertTrue(storeFrontHomePage.verifyPopUpForExistingActiveRC() , "Existing Active RC User email id should not be acceptable");
			s_assert.assertAll();
		}else {
			logger.info("Canada specific TC not executed on AU");
		}
	}


}