package com.rf.test.website.storeFront.brandRefresh;

import java.util.List;
import java.util.Map;
import org.testng.annotations.Test;
import com.rf.core.utils.CommonUtils;
import com.rf.core.utils.DBUtil;
import com.rf.core.website.constants.TestConstantsRFL;
import com.rf.core.website.constants.dbQueries.DBQueries_RFL;
import com.rf.core.website.constants.dbQueries.DBQueries_RFO;
import com.rf.test.website.RFBrandRefreshWebsiteBaseTest;

public class BizPWSTest extends RFBrandRefreshWebsiteBaseTest{

	/**
	 * Test Summary: Open BIZ, Login with consultant, goto My account and click on Edit order link, enter new shipping  address and verify
	 * Assertions:
	 * Newly added shipping address is present
	 */
	//QTest ID TC-403  PC Perks Template - Shipping Address from BIZ
	@Test
	public void testPCPerksTemplateShippingAddressUpdateFromBIZ_403(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String addressName = "Home";
		String shippingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME+randomNumber;
		String shippingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME;
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsPCUser(userEmailId,password);
		storeFrontBrandRefreshHomePage.clickHeaderLinkAfterLogin("my account");
		storeFrontBrandRefreshHomePage.clickEditOrderLink();
		storeFrontBrandRefreshHomePage.clickChangeLinkUnderShippingToOnPWS();
		storeFrontBrandRefreshHomePage.enterShippingProfileDetailsForPWS(addressName, shippingProfileFirstName, shippingProfileLastName, addressLine1, postalCode, phnNumber);
		storeFrontBrandRefreshHomePage.clickUseThisAddressShippingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getShippingAddressName().contains(shippingProfileFirstName),"Shipping address name expected is "+shippingProfileFirstName+" While on UI is "+storeFrontBrandRefreshHomePage.getShippingAddressName());
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open BIZ, Login with consultant, add a CONSULTANT-ONLY PRODUCTS(Business Promotion) and place order
	 * Assertions:
	 * Thank you message after placing order
	 * Message "You will receive an email confirmation shortly" after placing order
	 */
	//QTest ID TC-511 Checkout as Consultant from BIZ
	@Test
	public void testCheckoutAsConsultantFromBIZ_511(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME+randomNumber;
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsConsultant(userEmailId,password);
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS("CONSULTANT-ONLY PRODUCTS");
		storeFrontBrandRefreshHomePage.clickConsultantOnlyProductOnPWS(TestConstantsRFL.CONSULTANT_ONLY_BUSINESS_PROMOTION);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		storeFrontBrandRefreshHomePage.clickMyShoppingBagLink();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber, CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Adhoc order not placed successfully from biz site for Consultant user.");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getOrderConfirmationTextMsgAfterOrderPlaced().contains("You will receive an email confirmation shortly"), "Order confirmation message does not contains email confirmation");
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open BIZ , Login with PC, navigate to my account and verify the following links are present and accessbile:-
	 * Order History
	 * Edit Order
	 * Change my PC Perks Status
	 * PC Perks FAQs
	 * 
	 * Assertions:
	 * Visibility of the above links and if they are navigating to respective pages
	 */
	//QTest ID TC-397 My account options as PC customer from BIZ
	@Test
	public void testMyAccountOptionsAsPCCustomerFromBIZ_397(){
		String orderHistory = "Order History";
		String editOrder = "Edit Order";
		String changeMyPCPerksStatus = "Change my PC Perks Status";
		String pCPerksFAQs = "PC Perks FAQs";
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsPCUser(userEmailId,password);
		storeFrontBrandRefreshPCUserPage.clickHeaderLinkAfterLogin("my account");
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.isOrderManagementSublinkPresent(orderHistory), "Order History link is not present in order management");
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.isOrderManagementSublinkPresent(editOrder), "Edit order link is not present in order management");
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.isOrderManagementSublinkPresent(changeMyPCPerksStatus), "Change my pc perks status link is not present in order management");
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.isOrderManagementSublinkPresent(pCPerksFAQs), "PC Perks FAQs link is not present in order management");
		storeFrontBrandRefreshPCUserPage.clickOrderManagementSublink(orderHistory);
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.isOrderNumberPresentAtOrderHistoryPage(), "Order number is not present at order history page");
		storeFrontBrandRefreshPCUserPage.navigateToBackPage();
		storeFrontBrandRefreshPCUserPage.clickOrderManagementSublink(editOrder);
		storeFrontBrandRefreshPCUserPage.clickEditOrderBtn();
		storeFrontBrandRefreshPCUserPage.clickAddToCartBtnForEditOrder();
		storeFrontBrandRefreshPCUserPage.clickUpdateOrderBtn();
		storeFrontBrandRefreshPCUserPage.clickOKBtnOfJavaScriptPopUp();
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.getOrderUpdateMessage().contains("successfully updated"), "Expected order update message is successfully updated but actual on UI is: "+storeFrontBrandRefreshPCUserPage.getOrderUpdateMessage());
		storeFrontBrandRefreshPCUserPage.clickOnRodanAndFieldsLogo();
		storeFrontBrandRefreshPCUserPage.clickHeaderLinkAfterLogin("my account");
		storeFrontBrandRefreshPCUserPage.clickOrderManagementSublink(changeMyPCPerksStatus);
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.isDelayOrCancelPCPerksLinkPresent(), "Delay or cancel PC perks link is not present");
		storeFrontBrandRefreshPCUserPage.navigateToBackPage();
		storeFrontBrandRefreshPCUserPage.clickOrderManagementSublink(pCPerksFAQs);
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.ispCPerksFAQsLinkRedirectingToAppropriatePage("PC-Perks-FAQs.pdf"), "PC Perks FAQs link is not redirecting to appropriate page");
		s_assert.assertAll();
	}

	//QTest ID TC-504 Consultant enrollment-Sign up
	@Test
	public void testConsultantEnrollmentSignUp_504(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(10, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String enrollmentType = "Express";
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenForConsultant(regimen);
		storeFrontBrandRefreshHomePage.clickNextBtnAfterSelectRegimen();
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollmentType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password, addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear,CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		String bizPWS = storeFrontBrandRefreshHomePage.getBizPWSBeforeEnrollment();
		storeFrontBrandRefreshHomePage.clickCompleteAccountNextBtn();
		storeFrontBrandRefreshHomePage.clickTermsAndConditions();
		storeFrontBrandRefreshHomePage.chargeMyCardAndEnrollMe();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCongratulationsMessageAppeared(),"Enrollment not completed successfully");
		s_assert.assertTrue(driver.getCurrentUrl().contains(bizPWS.split("\\//")[1]), "Expected biz PWS is: "+bizPWS.split("\\//")[1]+" but Actual on UI is: "+driver.getCurrentUrl());
		s_assert.assertAll();
	}

	//QTest ID TC-402 PC Perks Template Update - Add/modify products from BIZ
	@Test(enabled=true)
	public void testPCPerksTemplateUpdateFromBIZ_402(){
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsPCUser(userEmailId,password);
		storeFrontBrandRefreshHomePage.clickHeaderLinkAfterLogin("my account");
		storeFrontBrandRefreshHomePage.clickEditOrderLink();
		storeFrontBrandRefreshHomePage.clickEditOrderbtn();
		storeFrontBrandRefreshHomePage.clickRemoveLinkAboveTotal();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isStatusMessageDisplayed(),"status message is not displayed as expected");
		storeFrontBrandRefreshHomePage.clickAddToCartBtnForLowPriceItems();
		storeFrontBrandRefreshHomePage.clickAddToCartBtnForHighPriceItems();
		storeFrontBrandRefreshHomePage.clickOnUpdateOrderBtn();
		storeFrontBrandRefreshHomePage.handleAlertAfterUpdateOrder();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getConfirmationMessage().contains("Replenishment Order items successfully updated!"),"No Message appearing after order update");
		String updatedOrderTotal = storeFrontBrandRefreshHomePage.getOrderTotal();
		storeFrontBrandRefreshHomePage.clickOnRodanAndFieldsLogo();
		storeFrontBrandRefreshHomePage.clickHeaderLinkAfterLogin("my account");
		storeFrontBrandRefreshHomePage.clickEditOrderLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getOrderTotalAtOverview().contains(updatedOrderTotal),"order total is not updated at overview page");
		s_assert.assertAll();
	}

	//QTest ID TC-404 PC Perks Template -  Billing Profile from BIZ
	@Test
	public void testPCPerksTemplateBillingAddressUpdateFromBIZ_404(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME+randomNumber;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME;
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsPCUser(userEmailId,password);
		storeFrontBrandRefreshHomePage.clickHeaderLinkAfterLogin("my account");
		storeFrontBrandRefreshHomePage.clickEditOrderLink();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationLinkUnderBillingTabOnPWS();
		storeFrontBrandRefreshHomePage.enterBillingInfoForPWS(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.getOrderBillingDetailsUpdateMessage().contains("Order billing information successfully updated!"), "Expected order Billing update message is Replenishment Order billing information successfully updated! but actual on UI is: "+storeFrontBrandRefreshHomePage.getOrderBillingDetailsUpdateMessage());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getBillingAddressName().contains(billingProfileFirstName), "Expected billing profile name is: "+billingProfileFirstName+" Actual on UI is: "+storeFrontBrandRefreshHomePage.getBillingAddressName());
		s_assert.assertAll();
	}

	//QTest ID TC-409 Registering the consultant using existing consultant's email id which terminated from BIZ
	@Test(enabled=true)
	public void testRegisterUsingExistingCustomerEmailIdFromBIZ_409(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(10, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String enrollemntType = "Express";
		List<Map<String, Object>> randomConsultantList =  null;
		String consultantEmailID = null;
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_INACTIVE_CONSULTANT_EMAILID,RFL_DB);
		consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "EmailAddress");
		storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isInvalidLoginPresent(),"consultant is logged in successfully with terminated user");
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		storeFrontBrandRefreshHomePage.enterEmailAddress(consultantEmailID);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextButton();
		s_assert.assertTrue(driver.getCurrentUrl().contains("SetupAccount"), "Set up account page is not present");
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, consultantEmailID, password, addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear,CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		storeFrontBrandRefreshHomePage.clickBillingInfoNextBtn();
		storeFrontBrandRefreshHomePage.clickTermsAndConditions();
		storeFrontBrandRefreshHomePage.chargeMyCardAndEnrollMe();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCongratulationsMessageAppeared(),"Enrollment is not successful using terminated consultant email id.");
		//verify Account status
		List<Map<String, Object>> accountStatusIDList =  null;
		String statusID = null;
		accountStatusIDList =  DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_ACCOUNT_STATUS_ID, consultantEmailID), RFL_DB);
		statusID = String.valueOf(getValueFromQueryResult(accountStatusIDList, "StatusID"));
		s_assert.assertTrue(statusID.contains("1"), "Account status is not active");
		s_assert.assertAll();
	}

	//Enroll a consultant using existing CA Prefix (Cross Country Sponsor)   
	@Test(enabled=false)
	public void testEnrollAConsultantUsingExistingCAPrefix(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(00, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String addressLine1 =  TestConstantsRFL.ADDRESS_LINE1;
		String postalCode = TestConstantsRFL.POSTAL_CODE;
		String cardNumber = TestConstantsRFL.CARD_NUMBER;
		String nameOnCard = firstName;
		String expMonth = TestConstantsRFL.EXP_MONTH;
		String expYear = TestConstantsRFL.EXP_YEAR;
		String kitName = "Big Business Launch Kit";
		String regimen = "Redefine";
		String enrollemntType = "Express";
		String phnNumber1 = "415";
		String phnNumber2 = "780";
		String phnNumber3 = "9099";
		String countryID ="40";
		String country = "ca";
		String bizPWSToAssert = null;
		String comPWSToAssert = null;
		String emailToAssert = null;
		String availableText = " is unavailable";
		String dbIP2 = driver.getDBIP2();
		List<Map<String, Object>> randomConsultant = null;
		//Fetch cross country site prefix from database.
		randomConsultant = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",country,countryID), RFO_DB, dbIP2);
		String url = (String) getValueFromQueryResult(randomConsultant, "URL");
		String pwsPrefix = storeFrontBrandRefreshHomePage.getSplittedPrefixFromConsultantUrl(url);
		bizPWSToAssert = storeFrontBrandRefreshHomePage.getModifiedPWSValue(url,availableText);
		comPWSToAssert=storeFrontBrandRefreshHomePage.convertBizSiteToComSite(bizPWSToAssert);
		emailToAssert =storeFrontBrandRefreshHomePage.getModifiedEmailValue(url);
		String PWS = "https://"+pwsPrefix+driver.getBizPWSURL();
		driver.get(PWS);
		storeFrontBrandRefreshHomePage.clickHeaderLinkAfterLogin("BECOME A CONSULTANT");
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password, addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear,CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		storeFrontBrandRefreshHomePage.enterUserPrefixInPrefixField(prefix);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getDotComPWS().contains(comPWSToAssert),"Com pws is available for cross country user site prefix");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getDotBizPWS().contains(bizPWSToAssert),"Biz pws is available for cross country user site prefix");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getEmailId().contains(emailToAssert),"Email is available for cross country user site prefix");
		s_assert.assertAll();
	}

	//QTest ID TC-405 Registering the consultant using existing consultant's email id from BIZ
	@Test
	public void RegisterConsultantUsingExistingConsultantEmailIdFromBIZ_405(){
		String enrollmentType = "Express";
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollmentType);
		//Enter SetUp Account Information and validate existing consultant?
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateExistingConsultantPopUp(consultantEmailID),"Existing Consultant Pop Up is not displayed!!");
		s_assert.assertAll();
	}

	//QTest ID TC-406 Registering the consultant using preffered customer's email id from BIZ
	@Test
	public void RegisterConsultantUsingExistingPrefferedCustomerEmailIdFromBIZ_406(){
		String enrollmentType = "Express";
		String pcEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollmentType);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateExistingPCPopUp(pcEmailID),"Existing PC Pop Up is not displayed!!");
		s_assert.assertAll();
	}


	//QTest ID TC-407 Registering the consultant using Retail customer's email id
	@Test
	public void RegisterConsultantUsingExistingRetailCustomerEmailId_407(){
		String enrollemntType = "Express";
		String rcEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_RC);
		openBIZSite();	
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateExistingRCPopUp(rcEmailID),"Existing RC Pop Up is not displayed!!");
		s_assert.assertAll();
	}

	//QTest ID TC-766 Register the consultant using existing consultant's SSN from BIZ
	@Test
	public void testRegisterTheConsultantUsingExistingConsultantSSNFromBIZ_766(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int randomNum2 = CommonUtils.getRandomNum(10000, 1000000);
		int randomNum3 = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(10, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String lastNameForReEnrollment = TestConstantsRFL.LAST_NAME+randomNum2;
		String emailAddressForReEnrollment = firstName+randomNum2+"@rodanandfields.com";
		String lastName2 = TestConstantsRFL.LAST_NAME+randomNum3;
		String emailAddress2 = firstName+randomNum3+"@rodanandfields.com";
		String addressLine1 =  TestConstantsRFL.ADDRESS_LINE1;
		String postalCode = TestConstantsRFL.POSTAL_CODE;
		String cardNumber = TestConstantsRFL.CARD_NUMBER;
		String nameOnCard = firstName;
		String expMonth = TestConstantsRFL.EXP_MONTH;
		String expYear = TestConstantsRFL.EXP_YEAR;
		String kitName = TestConstantsRFL.CONSULTANT_RFX_EXPRESS_BUSINESS_KIT;
		String regimen = TestConstantsRFL.REGIMEN_NAME_REDEFINE;
		String enrollemntType = "Express";
		String phnNumber1 = "415";
		String phnNumber2 = "780";
		String phnNumber3 = "9099";
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenForConsultant(regimen);
		storeFrontBrandRefreshHomePage.clickNextBtnAfterSelectRegimen();
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password, addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear,CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		storeFrontBrandRefreshHomePage.clickCompleteAccountNextBtn();
		storeFrontBrandRefreshHomePage.clickTermsAndConditions();
		storeFrontBrandRefreshHomePage.chargeMyCardAndEnrollMe();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCongratulationsMessageAppeared(),"Enrollment not completed successfully");
		storeFrontBrandRefreshHomePage.clickOnRodanAndFieldsLogo();
		logout();
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenForConsultant(regimen);
		storeFrontBrandRefreshHomePage.clickNextBtnAfterSelectRegimen();
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastNameForReEnrollment, emailAddressForReEnrollment, password, addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear,CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isExistingConsultantPopupPresent(), "Existing consultant popup is not present");
		storeFrontBrandRefreshHomePage.clickCancelEnrollmentBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isLoginButtonPresent(),"User is not redirected to home page after clicked on cancel enrollment button");
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		/*storeFrontLegacyHomePage.clickEnrollNowBtnAtWhyRFPage();*/
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenForConsultant(regimen);
		storeFrontBrandRefreshHomePage.clickNextBtnAfterSelectRegimen();
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName2, emailAddress2, password, addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear,CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isExistingConsultantPopupPresent(), "Existing consultant popup is not present");
		storeFrontBrandRefreshHomePage.clickSendEmailToResetMyPassword();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isEmailVerificationTextPresent(), "Email verification message is not present");
		s_assert.assertAll();
	}

	//QTest ID TC-398 My account- As a consultant customer
	@Test(enabled=true)
	public void testMyAccountAsAConsultantCustomer_398(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(10, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String enrollemntType = "Express";
		String editMyPWS = "edit my pws";
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenForConsultant(regimen);
		storeFrontBrandRefreshHomePage.clickNextBtnAfterSelectRegimen();
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password, addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear,CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		storeFrontBrandRefreshHomePage.clickCompleteAccountNextBtn();
		storeFrontBrandRefreshHomePage.clickTermsAndConditions();
		storeFrontBrandRefreshHomePage.chargeMyCardAndEnrollMe();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCongratulationsMessageAppeared(),"Enrollment not completed successfully");
		storeFrontBrandRefreshHomePage.clickOnRodanAndFieldsLogo();
		storeFrontBrandRefreshHomePage.clickHeaderLinkAfterLogin(editMyPWS);
		storeFrontBrandRefreshHomePage.clickEditMyPhotoLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isUploadANewPhotoButtonPresent(),"Upload a new photo button is not present");
		s_assert.assertAll();
	}

	//QTest ID TC-408 Registering the consultant using existing CA consultant email id (Cross County Sponsor) from BIZ
	@Test
	public void RegisterConsultantUsingExistingCrossCountryConsultantEmailIdFromBIZ_408(){
		RFO_DB = driver.getDBNameRFO();
		RFL_DB = driver.getDBNameRFL();
		String dbIP2 = driver.getDBIP2();
		String kitName = "Big Business Launch Kit";
		String regimen = "Redefine";
		String enrollemntType = "Express";
		String countryID ="40";
		String country = "ca";
		List<Map<String, Object>> randomConsultantList =  null;
		List<Map<String, Object>> randomEmailIdList =  null;
		String consultantEmailID = null;
		//Fetch cross country Email address from database.
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",country,countryID), RFO_DB, dbIP2);
		String accountID= String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
		//Get email id from account id
		randomEmailIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_EMAIL_ID_FROM_ACCOUNT_ID,accountID), RFO_DB,dbIP2);
		consultantEmailID = (String) getValueFromQueryResult(randomEmailIdList, "EmailAddress");
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		//Enter SetUp Account Information and validate existing consultant?
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateExistingConsultantPopUp(consultantEmailID),"Existing Consultant Pop Up is not displayed!!");
		s_assert.assertAll();
	}

	//RC -  Shipping Address
	@Test(enabled=false)
	public void testRCUserShippingAddressUpdate(){
		RFL_DB = driver.getDBNameRFL();
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String regimen = TestConstantsRFL.REGIMEN_NAME_REVERSE;
		String addressName = "Home";
		String shippingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME+randomNumber;
		String shippingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME;
		String addressLine1 =  TestConstantsRFL.ADDRESS_LINE1;
		String postalCode = TestConstantsRFL.POSTAL_CODE;
		String phnNumber = TestConstantsRFL.NEW_ADDRESS_PHONE_NUMBER_US;

		List<Map<String, Object>> randomRCList =  null;
		String rcEmailID = null;

		randomRCList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_RC_EMAILID,RFL_DB);
		rcEmailID = (String) getValueFromQueryResult(randomRCList, "EmailAddress");
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartBtn();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.loginAsUserOnCheckoutPage(rcEmailID, password);
		s_assert.assertFalse(storeFrontBrandRefreshHomePage.isSignInButtonPresent(), "RC user not logged in successfully");
		s_assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains("consultantlocator"), "RC user not redirected to consultant locator after login");
		storeFrontBrandRefreshHomePage.clickContinueWithoutConsultantLink();
		storeFrontBrandRefreshHomePage.clickChangeShippingAddressBtn();
		storeFrontBrandRefreshHomePage.enterShippingProfileDetailsForPWS(addressName, shippingProfileFirstName, shippingProfileLastName, addressLine1, postalCode, phnNumber);
		storeFrontBrandRefreshHomePage.clickUseThisAddressShippingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getShippingAddressName().contains(shippingProfileFirstName),"Shipping address name expected is "+shippingProfileFirstName+" While on UI is "+storeFrontBrandRefreshHomePage.getShippingAddressName());
		s_assert.assertAll();
	}

	//RC - Billing Profile
	@Test(enabled=false)
	public void testRCUserBillingProfileUpdate(){
		RFL_DB = driver.getDBNameRFL();
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String postalCode = TestConstantsRFL.POSTAL_CODE;
		String cardNumber = TestConstantsRFL.CARD_NUMBER;
		String nameOnCard = firstName;
		String expMonth = TestConstantsRFL.EXP_MONTH;
		String expYear = TestConstantsRFL.EXP_YEAR;
		String regimen = TestConstantsRFL.REGIMEN_NAME_REVERSE;
		String phnNumber = TestConstantsRFL.NEW_ADDRESS_PHONE_NUMBER_US;
		String addressLine1 =  TestConstantsRFL.ADDRESS_LINE1;
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		List<Map<String, Object>> randomRCList =  null;
		String rcEmailID = null;

		randomRCList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_RC_EMAILID,RFL_DB);
		rcEmailID = (String) getValueFromQueryResult(randomRCList, "EmailAddress");
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartBtn();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.loginAsUserOnCheckoutPage(rcEmailID, password);
		s_assert.assertFalse(storeFrontBrandRefreshHomePage.isSignInButtonPresent(), "RC user not logged in successfully");
		s_assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains("consultantlocator"), "RC user not redirected to consultant locator after login");
		storeFrontBrandRefreshHomePage.clickContinueWithoutConsultantLink();
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getBillingAddressName().contains(billingProfileFirstName), "Expected billing profile name is: "+billingProfileFirstName+" Actual on UI is: "+storeFrontBrandRefreshHomePage.getBillingAddressName());
		s_assert.assertAll();
	}

	//QTest ID TC-400 PC Perks Delay - 30 days from BIZ
	@Test(enabled=true)
	public void testPCPerksDelay30DaysFromBIZ_400(){
		String changeMyPCPerksStatus = "Change my PC Perks Status";
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsPCUser(userEmailId,password);
		storeFrontBrandRefreshPCUserPage.clickHeaderLinkAfterLogin("my account");
		storeFrontBrandRefreshPCUserPage.clickOrderManagementSublink(changeMyPCPerksStatus);
		storeFrontBrandRefreshPCUserPage.clickDelayPCPerksLink();
		storeFrontBrandRefreshHomePage.clickChangeAutohipDateBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyConfirmationMessage(),"confirmation msg not present as expected");
		storeFrontBrandRefreshHomePage.clickBackToMyAccountBtn();
		storeFrontBrandRefreshHomePage.clickEditOrderLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyConfirmationMessageInOrders(),"Confirmation msg not present at orders page as expected");
		s_assert.assertAll();
	}

	//QTest ID TC-401 PC Perks Delay - 60 days
	@Test(enabled=true)
	public void testPCPerksDelay60Days_401(){
		String changeMyPCPerksStatus = "Change my PC Perks Status";
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsPCUser(userEmailId,password);
		storeFrontBrandRefreshPCUserPage.clickHeaderLinkAfterLogin("my account");
		storeFrontBrandRefreshPCUserPage.clickOrderManagementSublink(changeMyPCPerksStatus);
		storeFrontBrandRefreshPCUserPage.clickDelayPCPerksLink();
		storeFrontBrandRefreshHomePage.selectSecondRadioButton();
		storeFrontBrandRefreshHomePage.clickChangeAutohipDateBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyConfirmationMessage(),"confirmation msg not present as expected");
		storeFrontBrandRefreshHomePage.clickBackToMyAccountBtn();
		storeFrontBrandRefreshHomePage.clickEditOrderLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyConfirmationMessageInOrders(),"Confirmation msg not present at orders page as expected");
		s_assert.assertAll();
	}

	// QTest ID TC-399  PC User termination from BIZ
	@Test
	public void testPCUserTerminationFromBIZ_399(){
		String changeMyPCPerksStatus = "Change my PC Perks Status";
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsPCUser(userEmailId,password);
		storeFrontBrandRefreshPCUserPage.clickHeaderLinkAfterLogin("my account");
		storeFrontBrandRefreshPCUserPage.clickOrderManagementSublink(changeMyPCPerksStatus);
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.isDelayOrCancelPCPerksLinkPresent(), "Delay or cancel PC perks link is not present");
		storeFrontBrandRefreshPCUserPage.clickCancelPCPerksLink();
		storeFrontBrandRefreshPCUserPage.clickChangedMyMindBtn();
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.getCurrentURL().contains("Dashboard"), "User is not redirecting to dashboard after clicked on changed my mind button");
		storeFrontBrandRefreshPCUserPage.clickOrderManagementSublink(changeMyPCPerksStatus);
		storeFrontBrandRefreshPCUserPage.clickCancelPCPerksLink();
		storeFrontBrandRefreshPCUserPage.selectReasonForPCTermination();
		storeFrontBrandRefreshPCUserPage.enterMessageForPCTermination();
		storeFrontBrandRefreshPCUserPage.clickSendEmailToCancelBtn();
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.getEmailConfirmationMsgAfterPCTermination().contains("you will be receiving a confirmation e-mail shortly"), "Expected email confirmation message is: you will be receiving a confirmation e-mail shortly, but actual on UI is: "+storeFrontBrandRefreshPCUserPage.getEmailConfirmationMsgAfterPCTermination());
		storeFrontBrandRefreshPCUserPage.clickGoToRFHomeButton();
		storeFrontBrandRefreshHomePage.loginAsPCUser(userEmailId,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isInvalidLoginPresent(),"Terminated User is able to Login");
		s_assert.assertAll();
	}

	//QTest ID TC-860 My account options as RC customer
	@Test(enabled=true)
	public void testMyAccountOptionsAsRCCustomer_860(){
		String orderHistory = "Order History";
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_RC);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsRCUser(userEmailId,password);
		storeFrontBrandRefreshRCUserPage.clickHeaderLinkAfterLogin("my account");
		s_assert.assertTrue(storeFrontBrandRefreshRCUserPage.isOrderManagementSublinkPresent(orderHistory), "Order History link is not present in order management");
		storeFrontBrandRefreshRCUserPage.clickOrderManagementSublink(orderHistory);
		s_assert.assertTrue(storeFrontBrandRefreshRCUserPage.isOrderNumberPresentAtOrderHistoryPage(), "Order number is not present at order history page");
		s_assert.assertAll();
	}

	//QTest ID TC-911 PWS.biz R+FInTheNews
	@Test(enabled=false)
	public void testPWSbizRPlusFInTheNews_911(){
		String expectedURL = "Company/PR";
		openBIZSite();
		storeFrontBrandRefreshHomePage.clickCompanyPressRoomLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURL), "Current url expected is: "+expectedURL+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		s_assert.assertAll();
	}

	//QTest ID TC-2363: Log in with a valid RC customer from BIZ
	@Test(enabled=true)
	public void testLoginAsExistingRCFromBIZ_2363(){
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_RC);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsRCUser(userEmailId,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"RC user is not logged in successfully");
		s_assert.assertAll();
	}

	//*QTest ID TC-913 Company-Press room link
	@Test(enabled=true)
	public void testCompanyPressRoomLink_913(){
		String pageName="PRESS ROOM";
		String expectedURL="PressRoom";
		openBIZSite();
		storeFrontBrandRefreshHomePage.clickCompanyPressRoomLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURL), "Current url expected is: "+expectedURL+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isPageHeaderVisible(pageName),"User is not redirected to PressRoom Page");
		s_assert.assertAll();
	}

	//*QTest ID TC-916 Company-Carrers link
	@Test(enabled=true)
	public void testCompanyCarrersLinkBiz_916(){
		String pageName="CAREERS";
		String expectedURL="careers";
		openBIZSite();
		storeFrontBrandRefreshHomePage.clickCareersLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURL), "Current url expected is: "+expectedURL+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isPageHeaderVisible(pageName),"User is not redirected to CAREERS Page");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate the redefine category links and their sublinks(Results and FAQs) under shopskincare from biz site
	 *  
	 */
	//*QTest ID TC-879:Shop Skincare-menu navigation REDEFINE (PWS.biz)
	@Test(enabled=true)
	public void testShopSkincareMenuNavigationRedefineFromBiz_879(){
		String category = "REDEFINE";
		String subLinkResults = "Results";
		String subLinkFAQ = "FAQS";
		String currentURL = null;
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(category);
		currentURL = storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCategoryNamePresent(category) && currentURL.contains(category),category+"category link is not redirected to"+category+" category page and current url is "+currentURL);
		storeFrontBrandRefreshHomePage.clickOnRodanAndFieldsLogo();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickSubLink(category, subLinkResults);
		currentURL = storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.toLowerCase().contains(subLinkResults.toLowerCase()), "Expected sublink in url is "+subLinkResults.toLowerCase()+" Actual on UI is "+currentURL.toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToResultsPage(category),"user is not on results page");
		storeFrontBrandRefreshHomePage.clickOnRodanAndFieldsLogo();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickSubLink(category,subLinkFAQ);
		currentURL = storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.toLowerCase().contains("CommonQuestions".toLowerCase()), "Expected sublink in url is CommonQuestions".toLowerCase()+" Actual on UI is "+currentURL.toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToFAQsPage(category),"user is not on FAQs page");
		s_assert.assertAll();
	}	


	/***
	 * Test Summary:- validate the REVERSE category links and their sublinks(Results and FAQs) under shopskincare from biz site
	 *  
	 */
	//*QTest ID TC-880:Shop Skincare-menu navigation REVERSE (PWS.biz)
	@Test(enabled=true)
	public void testShopSkincareMenuNavigationReverseFromBiz_880(){
		String category = "REVERSE";
		String subLinkResults = "Results";
		String subLinkFAQ = "FAQ";
		String currentURL = null;
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(category);
		currentURL = storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCategoryNamePresent(category) && currentURL.contains(category),category+"category link is not redirected to"+category+" category page and current url is "+currentURL);
		storeFrontBrandRefreshHomePage.clickOnRodanAndFieldsLogo();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickSubLink(category, subLinkResults);
		currentURL = storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.toLowerCase().contains(subLinkResults.toLowerCase()), "Expected sublink in url is "+subLinkResults.toLowerCase()+" Actual on UI is "+currentURL.toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToResultsPage(category),"user is not on results page");
		storeFrontBrandRefreshHomePage.clickOnRodanAndFieldsLogo();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickSubLink(category,subLinkFAQ);
		currentURL = storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.toLowerCase().contains("CommonQuestions".toLowerCase()), "Expected sublink in url is CommonQuestions".toLowerCase()+" Actual on UI is "+currentURL.toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToFAQsPage(category),"user is not on FAQs page");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate the UNBLEMISH category links and their sublinks(Results and FAQs) under shopskincare from biz site
	 *  
	 */
	//*QTest ID TC-881:Shop Skincare-menu navigation UNBLEMISH (PWS.biz)
	@Test(enabled=true)
	public void testShopSkincareMenuNavigationUnblemishFromBiz_881(){
		String category = "UNBLEMISH";
		String subLinkResults = "Results";
		String subLinkFAQ = "FAQ";
		String currentURL = null;
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(category);
		currentURL = storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isExactCategoryNamePresent(category) && currentURL.contains(category.toLowerCase()),category+"category link is not redirected to"+category+" category page and current url is "+currentURL);
		storeFrontBrandRefreshHomePage.clickOnRodanAndFieldsLogo();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickSubLink(category, subLinkResults);
		currentURL = storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.toLowerCase().contains(subLinkResults.toLowerCase()), "Expected sublink in url is "+subLinkResults.toLowerCase()+" Actual on UI is "+currentURL.toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToResultsPage(category),"user is not on results page");
		storeFrontBrandRefreshHomePage.clickOnRodanAndFieldsLogo();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickSubLink(category,subLinkFAQ);
		currentURL = storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.toLowerCase().contains("CommonQuestions".toLowerCase()), "Expected sublink in url is CommonQuestions".toLowerCase()+" Actual on UI is "+currentURL.toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToFAQsPage(category),"user is not on FAQs page");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate the SOOTHE category links and their sublinks(Results and FAQs) under shopskincare from biz site
	 *  
	 */
	//*QTest ID TC-882:Shop Skincare-menu navigation SOOTHE (PWS.biz)
	@Test(enabled=true)
	public void testShopSkincareMenuNavigationSootheFromBiz_882(){
		String category = "SOOTHE";
		String subLinkResults = "Results";
		String subLinkFAQ = "FAQ";
		String currentURL = null;
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(category);
		currentURL = storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isExactCategoryNamePresent(category) && currentURL.contains(category.toLowerCase()),category+"category link is not redirected to"+category+" category page and current url is "+currentURL);
		storeFrontBrandRefreshHomePage.clickOnRodanAndFieldsLogo();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickSubLink(category, subLinkResults);
		currentURL = storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.toLowerCase().contains(subLinkResults.toLowerCase()), "Expected sublink in url is "+subLinkResults.toLowerCase()+" Actual on UI is "+currentURL.toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToResultsPage(category),"user is not on results page");
		storeFrontBrandRefreshHomePage.clickOnRodanAndFieldsLogo();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickSubLink(category,subLinkFAQ);
		currentURL = storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.toLowerCase().contains("CommonQuestions".toLowerCase()), "Expected sublink in url is CommonQuestions".toLowerCase()+" Actual on UI is "+currentURL.toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToFAQsPage(category),"user is not on FAQs page");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate the ENHANCEMENTS category links under shopskincare from biz site
	 *  
	 */
	//*QTest ID TC-883:Shop Skincare-menu navigation ENHANCEMENTS (PWS.biz)
	@Test(enabled=true)
	public void testShopSkincareMenuNavigationEnhancementsFromBiz_883(){
		String category = "ENHANCEMENTS";
		String currentURL = null;
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(category);
		currentURL = storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isExactCategoryNamePresent(category) && currentURL.contains(category.toLowerCase()),category+"category link is not redirected to"+category+" category page and current url is "+currentURL);
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate the ESSENTIALS category links under shopskincare from biz site
	 *  
	 */
	//*QTest ID TC-884:Shop Skincare-menu navigation ESSENTIALS (PWS.biz)
	@Test(enabled=true)
	public void testShopSkincareMenuNavigationEssentialsFromBiz_884(){
		String category = "ESSENTIALS";
		String currentURL = null;
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(category);
		currentURL = storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isExactCategoryNamePresent(category) && currentURL.contains(category.toLowerCase()),category+"category link is not redirected to"+category+" category page and current url is "+currentURL);
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- placed an adhoc order with consultant only buy event support pack
	 *  and validate orders details like subtotal & product name from biz site
	 */
	//*QTest ID TC-903 Consultants Only -buy event support pack 
	@Test(enabled=true)
	public void consultantsOnlyBuyEventSupportPack_903(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME+randomNumber;
		String subtotalFromCart = null;
		String productNameFromCart = null;
		String subtotalFromOrderConfirmation = null;
		String productNameFromOrderConfirmation = null;
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsConsultant(userEmailId,password);
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshConsultantPage.mouseHoverShopSkinCareAndClickLink("CONSULTANT-ONLY PRODUCTS");
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getCurrentURL().toLowerCase().contains("consultantsonly"), "Expected regimen name is: consultantsonly Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshConsultantPage.clickConsultantOnlyProduct(TestConstantsRFL.CONSULTANT_ONLY_EVENT_SUPPORT);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("event"), "Expected url contains is: Event but Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshConsultantPage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		subtotalFromCart = storeFrontBrandRefreshConsultantPage.getSubtotalFromCheckout();
		productNameFromCart = storeFrontBrandRefreshConsultantPage.getProductNameThroughProductNumber("1");
		storeFrontBrandRefreshConsultantPage.clickCheckoutBtn();
		storeFrontBrandRefreshConsultantPage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshConsultantPage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.isThankYouTextPresentAfterOrderPlaced(), "Order is not placed successfully");
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getOrderConfirmationTextMsgAfterOrderPlaced().contains("You will receive an email confirmation shortly"), "Order confirmation message does not contains email confirmation");
		subtotalFromOrderConfirmation = storeFrontBrandRefreshConsultantPage.getSubtotalFromOrderConfirmation();
		productNameFromOrderConfirmation = storeFrontBrandRefreshConsultantPage.getProductNameThroughProductNumber("1");
		s_assert.assertTrue(subtotalFromOrderConfirmation.contains(subtotalFromCart), "Expected subtotal is "+subtotalFromCart+" and on UI is "+subtotalFromOrderConfirmation);
		s_assert.assertTrue(productNameFromOrderConfirmation.contains(productNameFromCart), "Expected product name is "+productNameFromCart+" and on UI is "+productNameFromOrderConfirmation);
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- placed an adhoc order with consultant only buy product promotion
	 *  and validate orders details like subtotal & product name from biz site
	 */
	//*QTest ID TC-905 Consultants Only -buy product promotion 
	@Test(enabled=true)
	public void consultantsOnlyBuyProductPromotion_905(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME+randomNumber;
		String subtotalFromCart = null;
		String productNameFromCart = null;
		String subtotalFromOrderConfirmation = null;
		String productNameFromOrderConfirmation = null;
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsConsultant(userEmailId,password);
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshConsultantPage.mouseHoverShopSkinCareAndClickLink("CONSULTANT-ONLY PRODUCTS");
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getCurrentURL().toLowerCase().contains("consultantsonly"), "Expected regimen name is: consultantsonly Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshConsultantPage.clickConsultantOnlyProduct(TestConstantsRFL.CONSULTANT_ONLY_PRODUCT_PROMOTION);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("promotion"), "Expected url contains is: promotion but Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshConsultantPage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		subtotalFromCart = storeFrontBrandRefreshConsultantPage.getSubtotalFromCheckout();
		productNameFromCart = storeFrontBrandRefreshConsultantPage.getProductNameThroughProductNumber("1");
		storeFrontBrandRefreshConsultantPage.clickCheckoutBtn();
		storeFrontBrandRefreshConsultantPage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshConsultantPage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.isThankYouTextPresentAfterOrderPlaced(), "Order is not placed successfully");
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getOrderConfirmationTextMsgAfterOrderPlaced().contains("You will receive an email confirmation shortly"), "Order confirmation message does not contains email confirmation");
		subtotalFromOrderConfirmation = storeFrontBrandRefreshConsultantPage.getSubtotalFromOrderConfirmation();
		productNameFromOrderConfirmation = storeFrontBrandRefreshConsultantPage.getProductNameThroughProductNumber("1");
		s_assert.assertTrue(subtotalFromOrderConfirmation.contains(subtotalFromCart), "Expected subtotal is "+subtotalFromCart+" and on UI is "+subtotalFromOrderConfirmation);
		s_assert.assertTrue(productNameFromOrderConfirmation.contains(productNameFromCart), "Expected product name is "+productNameFromCart+" and on UI is "+productNameFromOrderConfirmation);
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate the sublink of About R+F from biz site
	 */
	//*QTest ID TC-907 Company-Links should be present (Execute team, our history, carrers, PFC foundation, press room, contact us) 
	@Test(enabled=true)
	public void testVerfiyCompanyLinks_907(){
		String whoWeAre = "WHO WE ARE";
		String meetTheDoctors = "MEET THE DOCTORS";
		String executiveTeam = "EXECUTIVE TEAM";
		String givingBack = "GIVING BACK";
		String currentURL = null;
		openBIZSite();
		//verify company careers Link?
		storeFrontBrandRefreshHomePage.mouseHoverAboutRFAndClickLink("Company");
		currentURL = driver.getCurrentUrl();
		s_assert.assertTrue(currentURL.contains("Company"),"Company link didn't work");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isLinkTextPresent(whoWeAre),whoWeAre+" link text is not present");

		// assert meet the doctors link
		storeFrontBrandRefreshHomePage.mouseHoverAboutRFAndClickLink("Meet the Doctors");
		currentURL = driver.getCurrentUrl();
		s_assert.assertTrue(currentURL.contains("MeetTheDoctors"),"MeetTheDoctors link didn't work");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isLinkTextPresent(meetTheDoctors),meetTheDoctors+" link text is not present");

		// assert Executive Team link
		storeFrontBrandRefreshHomePage.mouseHoverAboutRFAndClickLink("Executive Team");
		currentURL = driver.getCurrentUrl();
		s_assert.assertTrue(currentURL.contains("ExecutiveTeam"),"Executive Team link didn't work");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isLinkTextPresent(executiveTeam),executiveTeam+" link text is not present");

		// assert Giving Back link
		storeFrontBrandRefreshHomePage.mouseHoverAboutRFAndClickLink("Giving Back");
		currentURL = driver.getCurrentUrl();
		s_assert.assertTrue(currentURL.contains("GivingBack"),"Giving Back link didn't work");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isLinkTextPresent(givingBack),givingBack+" link text is not present");
		s_assert.assertAll();
	}

	//*QTest ID TC-743 Enroll a consultant using special characters in the prefix field from BIZ
	@Test(enabled=true)
	public void testEnrollConsultantUsingSpecialCharsInPrefixFieldFromBIZ_743(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(10, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String PWSSpclChars = TestConstantsRFL.PWS_SPCLCHARS;
		String enrollemntType = "Express";
		String phnNumber1 = "415";
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		//storeFrontBrandRefreshHomePage.clickSearchResults();
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password, addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear,CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		storeFrontBrandRefreshHomePage.enterSpecialCharacterInWebSitePrefixField(PWSSpclChars);
		storeFrontBrandRefreshHomePage.clickCompleteAccountNextBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isValidationMessagePresentForPrefixField(),"WebSite Prefix Field accepts Special Character");
		s_assert.assertAll();
	}

	//*QTest ID TC-742 Enroll a consultant using existing Prefix From BIZ
	@Test(enabled=true)
	public void testEnrollAConsultantUsingExistingPrefixFromBIZ_742(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(00, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String enrollemntType = "Express";
		String url=null;
		String prefix =null;
		List<Map<String, Object>> randomConsultant = null;
		randomConsultant = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_EXISTING_CONSULTANT_SITE_URL, RFL_DB);
		url = (String) getValueFromQueryResult(randomConsultant, "URL");
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		prefix = storeFrontBrandRefreshHomePage.getSplittedPrefixFromConsultantUrl(url);
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password, addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear,CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		storeFrontBrandRefreshHomePage.enterUserPrefixInPrefixField(prefix);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getPrefixMessageForBiz().contains("unavailable"),"Expected message is unavailable for .biz but actual on UI is: "+storeFrontBrandRefreshHomePage.getPrefixMessageForBiz());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getPrefixMessageForCom().contains("unavailable"),"Expected message is unavailable for .com but actual on UI is: "+storeFrontBrandRefreshHomePage.getPrefixMessageForCom());
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.getPrefixMessageForEmail().contains("unavailable"),"Expected message is unavailable for email but actual on UI is: "+storeFrontBrandRefreshHomePage.getPrefixMessageForEmail());
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- placed an adhoc order with consultant only buy only products
	 *  and validate orders details like subtotal & product name from biz site
	 */
	//*QTest ID TC-778 Shop Skincare-Consultants Only -buy only products (enhancements micro dermabrasion paste packets) 
	@Test(enabled=true)
	public void consultantsOnlyBuyOnlyProducts_778(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME+randomNumber;
		String subtotalFromCart = null;
		String productNameFromCart = null;
		String subtotalFromOrderConfirmation = null;
		String productNameFromOrderConfirmation = null;
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsConsultant(userEmailId,password);
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshConsultantPage.mouseHoverShopSkinCareAndClickLink("CONSULTANT-ONLY PRODUCTS");
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getCurrentURL().toLowerCase().contains("consultantsonly"), "Expected regimen name is: consultantsonly Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshConsultantPage.clickConsultantOnlyProduct(TestConstantsRFL.CONSULTANT_ONLY_PRODUCT);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("consultant-only"), "Expected url contains is: consultant-only but Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshConsultantPage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		subtotalFromCart = storeFrontBrandRefreshConsultantPage.getSubtotalFromCheckout();
		productNameFromCart = storeFrontBrandRefreshConsultantPage.getProductNameThroughProductNumber("1");
		storeFrontBrandRefreshConsultantPage.clickCheckoutBtn();
		storeFrontBrandRefreshConsultantPage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshConsultantPage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.isThankYouTextPresentAfterOrderPlaced(), "Order is not placed successfully");
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getOrderConfirmationTextMsgAfterOrderPlaced().contains("You will receive an email confirmation shortly"), "Order confirmation message does not contains email confirmation");
		subtotalFromOrderConfirmation = storeFrontBrandRefreshConsultantPage.getSubtotalFromOrderConfirmation();
		productNameFromOrderConfirmation = storeFrontBrandRefreshConsultantPage.getProductNameThroughProductNumber("1");
		s_assert.assertTrue(subtotalFromOrderConfirmation.contains(subtotalFromCart), "Expected subtotal is "+subtotalFromCart+" and on UI is "+subtotalFromOrderConfirmation);
		s_assert.assertTrue(productNameFromOrderConfirmation.contains(productNameFromCart), "Expected product name is "+productNameFromCart+" and on UI is "+productNameFromOrderConfirmation);
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- placed an adhoc order with consultant only buy only products
	 *  and validate orders details like subtotal & product name from biz site
	 */
	//*QTest ID TC-769 Enroll a consultant using existing CA Prefix (Cross Country Sponsor) 
	@Test(enabled=true)
	public void testEnrollAConsultantUsingExistingCAPrefix_769(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(00, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String enrollemntType = "Express";
		List<Map<String, Object>> randomConsultant = null;
		randomConsultant = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_EXISTING_CONSULTANT_SITE_URL, RFL_DB);
		String url = (String) getValueFromQueryResult(randomConsultant, "URL");
		openBIZSite();
		String prefix = storeFrontBrandRefreshHomePage.getSplittedPrefixFromConsultantUrl(url);
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password, addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear,CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		storeFrontBrandRefreshHomePage.enterUserPrefixInPrefixField(prefix);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getPrefixMessageForBiz().contains("unavailable"),"Expected message is unavailable for .biz but actual on UI is: "+storeFrontBrandRefreshHomePage.getPrefixMessageForBiz());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getPrefixMessageForCom().contains("unavailable"),"Expected message is unavailable for .com but actual on UI is: "+storeFrontBrandRefreshHomePage.getPrefixMessageForCom());
		s_assert.assertFalse(storeFrontBrandRefreshHomePage.getPrefixMessageForEmail().contains("unavailable"),"Expected message is unavailable for email but actual on UI is: "+storeFrontBrandRefreshHomePage.getPrefixMessageForEmail());
		s_assert.assertAll();
	}

	//QTest ID TC-2350  RC Enrollment from BIZ site
	@Test(enabled=true)
	public void testRCEnrollmentFromBIZ_2350(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumbers = CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String shippingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String shippingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME+randomNumbers;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME;
		String gender = TestConstantsRFL.GENDER_MALE;
		String addressName = "Home";
		String orderNumber=null;
		String subTotal = null;
		String shipping = null;
		String tax = null; 
		String grandTotal = null;
		String grandTotalFromDB=null;
		String taxFromDB=null;
		String shippingFromDB=null;
		String subTotalFromDB=null;
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		storeFrontBrandRefreshHomePage.clickMyShoppingBagLink();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.clickClickHereLinkForRC();
		s_assert.assertTrue(driver.getCurrentUrl().contains("Retail"), "After clicking click here link for RC not navigated to RC Enrollment page.");
		storeFrontBrandRefreshHomePage.enterProfileDetailsForPCAndRC(firstName,lastName,emailAddress,password,phnNumber,gender);
		storeFrontBrandRefreshHomePage.clickCreateMyAccountBtnOnCreateRetailAccountPage();
		storeFrontBrandRefreshHomePage.enterShippingProfileDetails(addressName, shippingProfileFirstName,shippingProfileLastName, addressLine1, postalCode, phnNumber);
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfoDetails(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickContinueBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Enrollment is not completed successfully");
//		orderNumber=storeFrontBrandRefreshHomePage.getOrderNumberFromOrderConfirmationPage();
//		subTotal=storeFrontBrandRefreshHomePage.getSubtotalFromOrderConfirmationPage();
//		shipping=storeFrontBrandRefreshHomePage.getShippingTotalFromOrderConfirmationPage();
//		tax=storeFrontBrandRefreshHomePage.getTaxFromOrderConfirmationPage();
//		grandTotal=storeFrontBrandRefreshHomePage.getGrandTotalFromOrderConfirmationPage();
//
//		List<Map<String, Object>> orderList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_ORDER_DETAILS,orderNumber),RFL_DB);
//		grandTotalFromDB = String.valueOf(getValueFromQueryResult(orderList, "GrandTotal"));
//		s_assert.assertTrue(grandTotalFromDB.contains(grandTotal),"Grand Total from DB is:"+grandTotalFromDB+" different from UI is:"+grandTotal);
//
//		taxFromDB = String.valueOf(getValueFromQueryResult(orderList, "TaxAmountTotal"));
//		s_assert.assertTrue(taxFromDB.contains(tax),"Tax from DB is:"+taxFromDB+" different from UI is:"+tax);
//
//		shippingFromDB = String.valueOf(getValueFromQueryResult(orderList, "ShippingTotal"));
//		s_assert.assertTrue(shippingFromDB.contains(shipping),"shipping Total from DB is:"+shippingFromDB+" different from UI is:"+shipping);
//
//		subTotalFromDB = String.valueOf(getValueFromQueryResult(orderList, "Subtotal"));
//		s_assert.assertTrue(subTotalFromDB.contains(subTotal),"SubTotal from DB is:"+subTotalFromDB+" different from UI is:"+subTotal);
		s_assert.assertAll();
	}

	// QTest ID TC-2360 Add Product to the Cart - Checkout as PC from BIZ
	@Test(enabled=true)
	public void testAddProductToTheCartCheckoutAsPCFromBIZ_2360() {
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		List<Map<String, Object>> randomPCList =  null;
		String pcEmailID = null;
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME+randomNumber;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME;
		String orderNumber=null;
		String subTotal = null;
		String shipping = null;
		String tax = null; 
		String grandTotal = null;
		String grandTotalFromDB=null;
		String taxFromDB=null;
		String shippingFromDB=null;
		String subTotalFromDB=null;
		randomPCList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_PC_EMAILID,RFL_DB);
		pcEmailID = (String) getValueFromQueryResult(randomPCList, "EmailAddress");
		String regimen = TestConstantsRFL.REGIMEN_NAME_ESSENTIALS;
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.loginAsUserOnCheckoutPage(pcEmailID, password);
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedInOnCorpSite(), "PC user not logged in successfully");
		s_assert.assertFalse(storeFrontBrandRefreshHomePage.isSignInButtonPresent(), "PC user not logged in successfully");
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		//String existingBillingProfile = storeFrontBrandRefreshHomePage.getExistingBillingProfileName();
		//storeFrontBrandRefreshHomePage.clickContinueBtnOnBillingPage();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getBillingAddressName().contains(billingProfileFirstName)||storeFrontBrandRefreshHomePage.getBillingAddress().contains(addressLine1), "Existing billing profile is not selected for new order.");
		storeFrontBrandRefreshHomePage.chooseShippingMethod("FedEx Grnd");
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		storeFrontBrandRefreshHomePage.clickOKBtnOnPopup();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Adhoc order not placed successfully from corp site.");
//		orderNumber=storeFrontBrandRefreshHomePage.getOrderNumberFromOrderConfirmationPage();
//		subTotal=storeFrontBrandRefreshHomePage.getSubtotalFromOrderConfirmationPage();
//		shipping=storeFrontBrandRefreshHomePage.getShippingTotalFromOrderConfirmationPage();
//		tax=storeFrontBrandRefreshHomePage.getTaxFromOrderConfirmationPage();
//		grandTotal=storeFrontBrandRefreshHomePage.getGrandTotalFromOrderConfirmationPage();

//		List<Map<String, Object>> orderList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_ORDER_DETAILS,orderNumber),RFL_DB);
//		grandTotalFromDB = String.valueOf(getValueFromQueryResult(orderList, "GrandTotal"));
//		s_assert.assertTrue(grandTotalFromDB.contains(grandTotal),"Grand Total from DB is:"+grandTotalFromDB+" different from UI is:"+grandTotal);
//
//		taxFromDB = String.valueOf(getValueFromQueryResult(orderList, "TaxAmountTotal"));
//		s_assert.assertTrue(taxFromDB.contains(tax),"Tax from DB is:"+taxFromDB+" different from UI is:"+tax);
//
//		shippingFromDB = String.valueOf(getValueFromQueryResult(orderList, "ShippingTotal"));
//		s_assert.assertTrue(shippingFromDB.contains(shipping),"shipping Total from DB is:"+shippingFromDB+" different from UI is:"+shipping);
//
//		subTotalFromDB = String.valueOf(getValueFromQueryResult(orderList, "Subtotal"));
//		s_assert.assertTrue(subTotalFromDB.contains(subTotal),"SubTotal from DB is:"+subTotalFromDB+" different from UI is:"+subTotal);
		s_assert.assertAll();
	}

	// QTest ID TC-775 Shop Skincare
	@Test(enabled=true)
	public void testShopSkincare_775() {
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME+randomNumber;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME;
		String regimen = TestConstantsRFL.REGIMEN_NAME_REVERSE;
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_RC);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsRCUser(userEmailId,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedInOnCorpSite(),"RC user is not logged in successfully");
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		//storeFrontBrandRefreshHomePage.clickContinueWithoutConsultantLink();
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getBillingAddressName().contains(billingProfileFirstName)||storeFrontBrandRefreshHomePage.getBillingAddress().contains(addressLine1), "Existing billing profile is not selected for new order.");
		storeFrontBrandRefreshHomePage.chooseShippingMethod("FedEx Grnd");
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		storeFrontBrandRefreshHomePage.clickOKBtnOnPopup();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Adhoc order not placed successfully from corp site.");
		s_assert.assertAll();
	}

	//QTest ID TC-828 Footer-Terms & Conditions link should redirecting to the appropriate page
	@Test(enabled=true)
	public void testFooterTermsAndConditionsLinkShouldRedirectingToTheAppropriatePage_828(){
		openBIZSite();
		storeFrontBrandRefreshHomePage.clickTermsAndConditionsLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("terms"), "Expected url having terms but actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertAll();
	}

	//QTest ID TC-831 Footer- Privacy Policy link should be redirecting to the appropriate page
	@Test(enabled=true)
	public void testFooterPrivacyPolicyLinkShouldBeRedirectingToTheAppropriatePage_831(){
		openBIZSite();
		storeFrontBrandRefreshHomePage.clickPrivacyPolicyLink();
		/*s_assert.assertTrue(storeFrontBrandRefreshHomePage.isPrivacyPolicyPagePresent(), "Privacy policy page is not present after clicked on privacy policy link");*/
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("privacy"), "Expected url having privacy but actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate product details before and after placing an order from biz
	 */	
	//*QTest ID TC-822 Checkout as Consultant
	@Test(enabled=true) 
	public void testCheckoutAsConsultant_822(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME+randomNumber;
		String subTotalFromOrderReview = null;
		String shippingFromOrderReview = null;
		String taxFromOrderReview = null;
		String grandTotalFromOrderReview = null;
		String quantityFromOrderReview = null;
		String SKUFromOrderReview = null;
		String itemNameFromOrderReview = null;
		String priceFromOrderReview = null;
		String totalFromOrderReview = null;
		String quantityFromOrderConfirmation = null;
		String SKUFromOrderConfirmation = null;
		String itemNameFromOrderConfirmation = null;
		String priceFromOrderConfirmation = null;
		String totalFromOrderConfirmation = null;
		String subTotalFromOrderConfirmation = null;
		String shippingFromOrderConfirmation = null;
		String taxFromOrderConfirmation = null;
		String grandTotalFromOrderConfirmation = null;
		String category = "REDEFINE";
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsConsultant(userEmailId,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(), "User is not logged in successfully");
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(category);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonAfterLogin();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber, CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		subTotalFromOrderReview = storeFrontBrandRefreshHomePage.getSubtotalFromOrderConfirmation();
		shippingFromOrderReview = storeFrontBrandRefreshHomePage.getShippingTotalFromOrderConfirmationPage();
		taxFromOrderReview = storeFrontBrandRefreshHomePage.getTaxFromOrderConfirmationPage();
		grandTotalFromOrderReview = storeFrontBrandRefreshHomePage.getGrandTotalFromOrderConfirmationPage();
		quantityFromOrderReview = storeFrontBrandRefreshHomePage.getProductDetailsFromCartAccordingToProductNumber("1","1");
		SKUFromOrderReview = storeFrontBrandRefreshHomePage.getProductDetailsFromCartAccordingToProductNumber("1","2");
		itemNameFromOrderReview = storeFrontBrandRefreshHomePage.getProductDetailsFromCartAccordingToProductNumber("1","3");
		priceFromOrderReview = storeFrontBrandRefreshHomePage.getProductDetailsFromCartAccordingToProductNumber("1","5");
		totalFromOrderReview = storeFrontBrandRefreshHomePage.getProductDetailsFromCartAccordingToProductNumber("1","6");

		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		// get details from order confirmation
		subTotalFromOrderConfirmation = storeFrontBrandRefreshHomePage.getSubtotalFromOrderConfirmation();
		shippingFromOrderConfirmation = storeFrontBrandRefreshHomePage.getShippingTotalFromOrderConfirmationPage();
		taxFromOrderConfirmation = storeFrontBrandRefreshHomePage.getTaxFromOrderConfirmationPage();
		grandTotalFromOrderConfirmation = storeFrontBrandRefreshHomePage.getGrandTotalFromOrderConfirmationPage();
		quantityFromOrderConfirmation = storeFrontBrandRefreshHomePage.getProductDetailsFromCartAccordingToProductNumber("1","1");
		SKUFromOrderConfirmation = storeFrontBrandRefreshHomePage.getProductDetailsFromCartAccordingToProductNumber("1","2");
		itemNameFromOrderConfirmation = storeFrontBrandRefreshHomePage.getProductDetailsFromCartAccordingToProductNumber("1","3");
		priceFromOrderConfirmation = storeFrontBrandRefreshHomePage.getProductDetailsFromCartAccordingToProductNumber("1","5");
		totalFromOrderConfirmation = storeFrontBrandRefreshHomePage.getProductDetailsFromCartAccordingToProductNumber("1","6");

		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Adhoc order not placed successfully from biz site for Consultant user.");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getOrderConfirmationTextMsgAfterOrderPlaced().contains("You will receive an email confirmation shortly"), "Order confirmation message does not contains email confirmation");
		//assert all product details

		s_assert.assertTrue(quantityFromOrderConfirmation.equalsIgnoreCase(quantityFromOrderReview), "Expected qunatity of product is "+quantityFromOrderReview+ "but actual on UI "+quantityFromOrderConfirmation);
		s_assert.assertTrue(SKUFromOrderConfirmation.contains(SKUFromOrderReview), "Expected SKU value of product is "+SKUFromOrderReview+ "but actual on UI "+SKUFromOrderConfirmation);
		s_assert.assertTrue(itemNameFromOrderConfirmation.contains(itemNameFromOrderReview), "Expected name of product is "+itemNameFromOrderReview+ "but actual on UI "+itemNameFromOrderConfirmation);
		s_assert.assertTrue(priceFromOrderConfirmation.contains(priceFromOrderReview), "Expected price of product is "+priceFromOrderReview+ "but actual on UI "+priceFromOrderConfirmation);
		s_assert.assertTrue(totalFromOrderConfirmation.contains(totalFromOrderReview), "Expected total value of product is "+totalFromOrderReview+ "but actual on UI "+totalFromOrderConfirmation);
		s_assert.assertTrue(subTotalFromOrderConfirmation.contains(subTotalFromOrderReview), "Expected subtotal of product is "+subTotalFromOrderReview+ "but actual on UI "+subTotalFromOrderConfirmation);
		s_assert.assertTrue(shippingFromOrderConfirmation.contains(shippingFromOrderReview), "Expected shipping value of product is "+shippingFromOrderReview+ "but actual on UI "+shippingFromOrderConfirmation);
		s_assert.assertTrue(taxFromOrderConfirmation.contains(taxFromOrderReview), "Expected tax of product is "+taxFromOrderReview+ "but actual on UI "+taxFromOrderConfirmation);
		s_assert.assertTrue(grandTotalFromOrderConfirmation.contains(grandTotalFromOrderReview), "Expected grand total of product is "+grandTotalFromOrderReview+ "but actual on UI "+grandTotalFromOrderConfirmation);
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate Satisfaction Guarantee-link from biz
	 */ 
	//*QTest ID TC-734 Satisfaction Guarantee-link should be redirecting properly 
	@Test(enabled=true)
	public void testSatisfactionGuaranteeLinkShouldBeRedirectionProperly_734(){
		openBIZSite();
		storeFrontBrandRefreshHomePage.clickSatisfactionGuaranteeLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isSatisfactionGuaranteePagePresent(), "Satisfaction guarantee page is not present after clicked on privacy policy link");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("guarantee"), "Expected url having guarantee but actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertAll();
	}


	/***
	 * Test Summary:- validate Disclaimer-link from biz
	 */	
	//*QTest ID TC-735 Disclaimer-link should be redirecting properly
	@Test(enabled=true)
	public void testDisclaimerLinkShouldBeRedirectedProperly_735(){
		openBIZSite();
		//verify Disclaimer in footer should redirect properly?
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateDisclaimerLinkInFooter(),"'Disclaimer Link' doesn't redirect to disclaimer page");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate Contact us-link from biz
	 */	
	//*QTest ID TC-736 Contact us-link should be redirecting properly
	@Test(enabled=true)
	public void testContactUsLinkShouldBeRedirectingProperly_736(){
		openBIZSite();
		storeFrontBrandRefreshHomePage.clickContactUsAtFooter();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("Contact".toLowerCase()), "URL does not contain Contact Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifylinkIsRedirectedToContactUsPage(),"link is not redirected to contact us page");
		s_assert.assertAll();
	}

	//*QTest ID TC-769 Enroll a consultant using existing CA Prefix (Cross Country Sponsor)
	@Test(enabled=true) 
	public void testEnrollConsultantUsingExistingCAPrefixCrossCountry_769(){
		String dbIP2 = driver.getDBIP2();
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(00, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String enrollemntType = "Express";
		String countryID ="40";
		String country = "ca";
		List<Map<String, Object>> randomConsultantList =  null;
		List<Map<String, Object>> sitePrefixList=null;
		String sitePrefix=null;

		//Fetch cross country Email address from database.
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",country,countryID), RFO_DB, dbIP2);
		String accountID= String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));

		sitePrefixList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_ALREADY_EXISTING_SITE_PREFIX_RFO,countryID,accountID),RFO_DB,dbIP2);
		sitePrefix=String.valueOf(getValueFromQueryResult(sitePrefixList, "SitePrefix"));
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password, addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear,CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		storeFrontBrandRefreshHomePage.enterUserPrefixInPrefixField(sitePrefix);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getPrefixMessageForBiz().contains("unavailable"),"Expected message is unavailable for .biz but actual on UI is: "+storeFrontBrandRefreshHomePage.getPrefixMessageForBiz());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getPrefixMessageForCom().contains("unavailable"),"Expected message is unavailable for .com but actual on UI is: "+storeFrontBrandRefreshHomePage.getPrefixMessageForCom());
		s_assert.assertAll();
	}

	//*QTest ID TC-731 Registering the consultant using Retail customer's email id from BIZ
	@Test(enabled=true)
	public void testRegisterConsultantUsingExistingRetailCustomerEmailIdFromBIZ_731(){
		String enrollemntType = "Express";
		String rcEmailID =getRandomUserFromDB(TestConstantsRFL.USER_TYPE_RC);
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		//Enter SetUp Account Information and validate existing PC PopUp?
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateExistingRCPopUp(rcEmailID),"Existing RC Pop Up is not displayed!!");
		s_assert.assertAll();
	}

	//*QTest ID TC-2359 Adhoc Order - Consultant Only Products from Biz site
	//>100 QV product is not avaialbe
	@Test(enabled=true)
	public void testAdhocOrderConsultantsOnlyProductsBiz_2359(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String consultantEmailID = null;
		openBIZSite();
		consultantEmailID =getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		storeFrontBrandRefreshConsultantPage = storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshConsultantPage.mouseHoverShopSkinCareAndClickLink("CONSULTANT-ONLY PRODUCTS");
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getCurrentURL().toLowerCase().contains("consultantsonly"), "Expected regimen name is: consultantsonly Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshConsultantPage.clickConsultantOnlyProduct(TestConstantsRFL.CONSULTANT_ONLY_PRODUCT);
		storeFrontBrandRefreshConsultantPage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		storeFrontBrandRefreshConsultantPage.clickCheckoutBtn();
		storeFrontBrandRefreshConsultantPage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.chooseShippingMethod("FedEx Grnd");
		storeFrontBrandRefreshConsultantPage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.isThankYouTextPresentAfterOrderPlaced(), "Order is not placed successfully");
		s_assert.assertAll();
	}

	//QTest ID TC-2364 log out with a valid user on Biz PWS
	@Test(enabled=true)
	public void testLogoutWithAValidUserOnBizPWS_2364(){
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsConsultant(userEmailId,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		logout();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isForgotPasswordLinkPresent(),"User is not logout successfully");
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open BIZ, initiate PC enrollment
	 * Assertions:
	 * PC user enrolled successfully
	 */
	//* QTest ID TC-2351 PC Enrollment
	@Test
	public void testPCEnrollment_2351() {
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME + randomNum;
		String shippingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String shippingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME + randomNumber;
		String emailAddress = firstName + randomNum + "@rodanandfields.com";
		String gender = TestConstantsRFL.GENDER_MALE;
		String addressName = "Home";
		String billingName = TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME + randomNumber;
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.clickClickHereLinkForPC();
		s_assert.assertTrue(driver.getCurrentUrl().contains("PCProgram"),"After clicking click here link for PC not navigated to PC Enrollment page.");
		storeFrontBrandRefreshHomePage.clickEnrollNowBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.enterProfileDetailsForPCAndRC(firstName, lastName, emailAddress, password,phnNumber, gender);
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyErrorMessageForTermsAndConditionsForPCAndRC(),"Terms and candition error message not present for PC User.");
		storeFrontBrandRefreshHomePage.checkTermsAndConditionChkBoxForPCAndRC();
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickContinueBtnOnAutoshipSetupPageForPC();
		storeFrontBrandRefreshHomePage.clickContinueBtn();
		storeFrontBrandRefreshHomePage.enterShippingProfileDetails(addressName, shippingProfileFirstName,shippingProfileLastName, addressLine1, postalCode, phnNumber);
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfoDetails(billingName, billingProfileFirstName,billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickContinueBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.clickCompleteEnrollmentBtn();
		storeFrontBrandRefreshHomePage.clickOKBtnOfJavaScriptPopUp();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isPCEnrollmentCompletedSuccessfully(),"PC enrollment is not completed successfully");
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open BIZ, Login with RC and place an adhoc order
	 * Assertions:
	 * Order is placed successfully
	 */
	// QTest ID TC-2358 Adhoc Order- RC
	@Test
	public void testAdhocOrderThroughRCFromBIZ_2358() {
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME+randomNumber;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME;
		String regimen = TestConstantsRFL.REGIMEN_NAME_REVERSE;
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_RC);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsRCUser(userEmailId,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedInOnCorpSite(),"RC user is not logged in successfully");
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		//storeFrontBrandRefreshHomePage.clickContinueWithoutConsultantLink();
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getBillingAddressName().contains(billingProfileFirstName)||storeFrontBrandRefreshHomePage.getBillingAddress().contains(addressLine1), "Existing billing profile is not selected for new order.");
		storeFrontBrandRefreshHomePage.chooseShippingMethod("FedEx Grnd");
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		storeFrontBrandRefreshHomePage.clickOKBtnOnPopup();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Adhoc order not placed successfully from corp site.");
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open BIZ, Login with consultant
	 * Assertions:
	 * User is able to logged in successfully
	 */
	//QTest ID TC-2361 Log in as an existing consultant from BIZ
	@Test
	public void testLoginAsAnExistingConsultantFromBIZ_2361(){
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsConsultant(userEmailId,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"Consultant user is not logged in successfully");
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open BIZ, Login with PC
	 * Assertions:
	 * User is able to logged in successfully
	 */
	//QTest ID TC-2362: Log in as valid PC customer from BIZ
	@Test
	public void testLoginAsExistingPCFromBIZ_2362(){
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsPCUser(userEmailId,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"PC user is not logged in successfully");
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open BIZ, click forgot password and enter email to recover password
	 * Assertions:
	 * Change password message popup
	 * Change password email has been sent successfully
	 */
	//QTest ID TC-2376 Forgot Password from BIZ
	@Test
	public void testForgotPasswordFromBIZ_2376(){
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openBIZSite();
		//click 'forgot password' on biz home page
		storeFrontBrandRefreshHomePage.clickForgotPasswordLinkOnBizHomePage();
		//verify a message prompt to change the password displayed?
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateChangePasswordMessagePrompt(),"Message not prompted for 'change password'");
		//verify user is able to change the password and email is being sent?
		storeFrontBrandRefreshHomePage.enterEmailAddressToRecoverPassword(consultantEmailID);
		storeFrontBrandRefreshHomePage.clickSendEmailButton();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validatePasswordChangeAndEmailSent(),"resetting your password mail is not displayed!!");
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open BIZ, Login with PC USer, goto My account and Remove or add products and verify
	 * Assertions:
	 * Status message after removing products
	 * Replenishment Order items successfully updated!
	 * verify order total at Overview page
	 */
	//QTest ID TC-2343 PC Perks Template Update - Add/modify products (Biz PWS)
	@Test(enabled=true)
	public void testPCPerksTemplateUpdateOnBizPWS_2343(){
		String userEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsPCUser(userEmailId,password);
		storeFrontBrandRefreshHomePage.clickHeaderLinkAfterLogin("my account");
		storeFrontBrandRefreshHomePage.clickEditOrderLink();
		storeFrontBrandRefreshHomePage.clickEditOrderbtn();
		storeFrontBrandRefreshHomePage.clickRemoveLinkAboveTotal();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isStatusMessageDisplayed(),"status message is not displayed as expected");
		storeFrontBrandRefreshHomePage.clickAddToCartBtnForLowPriceItems();
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.isStatusMessageDisplayed(),"status message is not displayed as expected for add to bag order");
		storeFrontBrandRefreshHomePage.clickAddToCartBtnForHighPriceItems();
		storeFrontBrandRefreshHomePage.clickOnUpdateOrderBtn();
		storeFrontBrandRefreshHomePage.handleAlertAfterUpdateOrder();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getConfirmationMessage().contains("Replenishment Order items successfully updated!"),"No Message appearing after order update");
		String updatedOrderTotal = storeFrontBrandRefreshHomePage.getOrderTotal();
		storeFrontBrandRefreshHomePage.clickOnRodanAndFieldsLogo();
		storeFrontBrandRefreshHomePage.clickHeaderLinkAfterLogin("my account");
		storeFrontBrandRefreshHomePage.clickEditOrderLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getOrderTotalAtOverview().contains(updatedOrderTotal),"order total is not updated at overview page");
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open BIZ, Enroll Consultant User, Enroll them into CRP and Pulse and Verify
	 * Assertions:
	 * User is Enrolled Successfully
	 */
	//QTest ID TC-2347 Consultant Standard Enrollment (Biz PWS)
	@Test
	public void testConsultantStandardEnrollmentBizPWS_2347(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(11, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollmentType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password, addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear, CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		storeFrontBrandRefreshHomePage.clickBillingInfoNextBtn();
		storeFrontBrandRefreshHomePage.clickYesSubscribeToPulseNow();
		storeFrontBrandRefreshHomePage.clickYesEnrollInCRPNow();
		storeFrontBrandRefreshHomePage.clickAutoShipOptionsNextBtn();
		storeFrontBrandRefreshHomePage.selectProductToAddToCart();
		storeFrontBrandRefreshHomePage.clickYourCRPOrderPopUpNextBtn();
		storeFrontBrandRefreshHomePage.clickChargeMyCardAndEnrollMeWithOutConfirmAutoship();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyErrorMessageForTermsAndConditionsForConsultant(), "Error message is not present for consultant for terms & conditions");
		storeFrontBrandRefreshHomePage.clickTermsAndConditions();
		storeFrontBrandRefreshHomePage.chargeMyCardAndEnrollMe();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCongratulationsMessageAppeared(),"Congratulations Message not appeared");
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open BIZ, Enroll Consultant User using Express Enrollment, and Verify
	 * Assertions:
	 * User is Enrolled Successfully
	 * User is navigating to BizPWS after Enrollment
	 */
	//QTest ID TC-2348 Consultant enrollment-Sign up (Biz PWS)
	@Test
	public void testConsultantEnrollmentSignUpOnBizPWS_2348(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(10, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String kitName = TestConstantsRFL.CONSULTANT_RFX_EXPRESS_BUSINESS_KIT;
		String regimen = TestConstantsRFL.REGIMEN_NAME_REVERSE;
		String enrollemntType = TestConstantsRFL.EXPRESS_ENROLLMENT;
		String bizPWS=null;
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenForConsultant(regimen);
		storeFrontBrandRefreshHomePage.clickNextBtnAfterSelectRegimen();
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password, addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear,CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		bizPWS = storeFrontBrandRefreshHomePage.getBizPWSBeforeEnrollment();
		storeFrontBrandRefreshHomePage.clickCompleteAccountNextBtn();
		storeFrontBrandRefreshHomePage.clickTermsAndConditions();
		storeFrontBrandRefreshHomePage.chargeMyCardAndEnrollMe();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCongratulationsMessageAppeared(),"Enrollment not completed successfully");
		s_assert.assertTrue(driver.getCurrentUrl().contains(bizPWS.split("\\//")[1]), "Expected biz PWS is: "+bizPWS.split("\\//")[1]+" but Actual on UI is: "+driver.getCurrentUrl());
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate RF Connection Link From Biz PWS
	 * Assertions:
	 * Page URL should contain 'rfconnection'
	 * RF Connection should be visible on page Header
	 */	
	//*QTest ID TC-2366 RF Connection From Biz PWS Site
	@Test(enabled=true)
	public void testRFConnectionFromBizPWS_2366(){
		String rfConnection="RF Connection";
		String currentURL=null;
		openBIZSite();
		storeFrontBrandRefreshHomePage.clickBottomPageCategoryLink(rfConnection);
		currentURL=storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.contains("rfconnection"),"Expected URL should contain 'rfconnection' But actual on UI is: "+currentURL);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isRFConnectionPageHeaderVisible()," User is not redirected to RF Connection Page");
		s_assert.assertAll();
	}


	/***
	 * Test Summary:- validate DERM RF Link From Biz PWS Site
	 * Assertions:
	 * Page URL should contain 'dermrf'
	 * DERM RF should be visible on page Header
	 */	
	//*QTest ID TC-2367 Derm Connection from Biz PWS Site
	@Test(enabled=true)
	public void testDermConnectionFromBizPWS_2367(){
		String dermRF="Derm RF";
		String currentURL=null;
		openBIZSite();
		storeFrontBrandRefreshHomePage.clickBottomPageCategoryLink(dermRF);
		currentURL=storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.contains("dermrf"),"Expected URL should contain 'dermrf' But actual on UI is: "+currentURL);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isDERMRFPageHeaderVisible()," User is not redirected to DERM RF Page");
		s_assert.assertAll();
	}


	/***
	 * Test Summary:- validate Shipping Link From Biz PWS
	 * Assertions:
	 * Page URL should contain 'Shipping'
	 * Shipping should be visible on page Header
	 */	
	//*QTest ID TC-2370 Shipping from Biz PWS
	@Test(enabled=true)
	public void testShippingFromBizPWS_2370(){
		String shipping="Shipping";
		String currentURL=null;
		openBIZSite();
		storeFrontBrandRefreshHomePage.clickBottomPageCategoryLink(shipping);
		currentURL=storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.contains("Shipping"),"Expected URL should contain 'shipping' But actual on UI is: "+currentURL);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isShippingPageHeaderVisible()," User is not redirected to Shipping Page");
		s_assert.assertAll();
	}


	/***
	 * Test Summary:- validate California Supply Chains Act Link From Biz PWS
	 * Assertions:
	 * Page URL should contain 'california-supply-chains-act'
	 * 'CALIFORNIA TRANSPARENCY IN SUPPLY CHAINS ACT' should be visible on page Header
	 */	
	//*QTest ID TC-2375 California Supply Chains Act From Biz PWS
	@Test(enabled=true)
	public void testCaliforniaSupplyChainsActFromBizPWS_2375(){
		String califSupplyChainsAct="California Supply Chains Act";
		String currentURL=null;
		openBIZSite();
		storeFrontBrandRefreshHomePage.clickBottomPageCategoryLink(califSupplyChainsAct);
		currentURL=storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.contains("california-supply-chains-act"),"Expected URL should contain 'california-supply-chains-act' But actual on UI is: "+currentURL);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCaliforniaSupplyChainsActPageHeaderVisible()," User is not redirected to 'California Supply Chains' Act Page");
		s_assert.assertAll();
	}

	//QTest ID TC-2358 Adhoc Order- RC on Biz PWS
	@Test(enabled=true)
	public void testadhocOrderRC_2358(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		openBIZSite();
		String rcEmailID =getRandomUserFromDB(TestConstantsRFL.USER_TYPE_RC);
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.loginAsUserOnCheckoutPage(rcEmailID, password);
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Adhoc order not placed successfully from com site for RC user.");
	}

	/***
	 * Test Summary:- Open biz url, Login with PC, start to place an adhoc order, add billing profile with INVALID credit card and place the order
	 * Assertions:
	 * Error message should be displayed for INVALID credit card after placed an order
	 */	
	//QTest ID TC-2353 BIZ: Payment error message
	@Test
	public void testPaymentErrorMessageWhilePlacingAnAdhocOrderThroughPCFromBIZ_2353(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String cardNumber = TestConstantsRFL.INVALID_CARD_NUMBER;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String errorMessageFromUI = null;
		String errorMessage = TestConstantsRFL.ERROR_MSG_FOR_INVALID_CC;
		String pcEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		storeFrontBrandRefreshHomePage.selectRegimen(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartBtn();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.loginAsUserOnCheckoutPage(pcEmailID, password);
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		storeFrontBrandRefreshHomePage.clickOKBtnOnPopup();
		errorMessageFromUI = storeFrontBrandRefreshHomePage.getErrorMessageForInvalidCreditCard();
		s_assert.assertTrue(errorMessageFromUI.contains(errorMessage), "Expected error message is "+errorMessage+"actual on UI is "+errorMessageFromUI);
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- Open biz url, initiate consultant enrollment, add billing profile with INVALID credit card and complete enrollment
	 * Assertions:
	 * Error message should be displayed for INVALID credit card after complete the enrollment
	 */	
	//QTest ID TC-2354 Payment error message - Enrollment
	@Test
	public void testPaymentErrorMessageFromBiz_2354(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(10, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String cardNumber = TestConstantsRFL.INVALID_CARD_NUMBER;
		String enrollemntType = "Express";
		String errorMessageFromUI = null;
		String errorMessage = TestConstantsRFL.ERROR_MSG_FOR_INVALID_CC;
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password, addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear, CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		storeFrontBrandRefreshHomePage.clickCompleteAccountNextBtn();
		storeFrontBrandRefreshHomePage.clickTermsAndConditions();
		storeFrontBrandRefreshHomePage.chargeMyCardAndEnrollMe();
		errorMessageFromUI = storeFrontBrandRefreshHomePage.getErrorMessageForInvalidCreditCard();
		s_assert.assertTrue(errorMessageFromUI.contains(errorMessage), "Expected error message is "+errorMessage+"actual on UI is "+errorMessageFromUI);
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate BIZ: Shop Skincare> Featured
	 * Assertions:
	 * Page URL should contain 'Promotions'
	 * 'FEATURED' should be visible on page Header
	 */	
	//*QTest ID TC-885 BIZ: Shop Skincare> Featured
	@Test(enabled=true)
	public void testShopSkincareFeaturedFromBizPWS_885(){
		String promotions="Promotions";
		String currentURL=null;
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(TestConstantsRFL.REGIMEN_NAME_FEATURED);
		currentURL=storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.contains(promotions),"Expected URL should contain"+promotions+" But actual on UI is: "+currentURL);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isUserRedirectedToFeaturedPage()," User is not redirected to Promotions Act Page");
		s_assert.assertAll();
	}		

	/**
	 * Test Summary: Open BIZ, Login with consultant, Navigate to 'Consultant only products' and click on 'Consultant only Event support' product, Add donation product to bag and complete place order and verify
	 * Assertions:
	 * Order placed successfully
	 * Verify grandTotal from DB
	 */
	//*QTest ID TC-2378 BIZ > Donation order 
	@Test(enabled=true)
	public void testDonationOrderFromBiz_2378(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME+randomNumber;
		String consultantEmailID = null;
		List<Map<String, Object>> randomConsultantList =  null;
		List<Map<String, Object>> randomOrderList =  null;
		String grandTotal = null;
		String orderNumber=null;
		String totalAmount=null;
		openBIZSite();
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_EMAILID,RFL_DB);
		consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "EmailAddress");
		storeFrontBrandRefreshConsultantPage = storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshConsultantPage.mouseHoverShopSkinCareAndClickLink("CONSULTANT-ONLY PRODUCTS");
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getCurrentURL().toLowerCase().contains("consultantsonly"), "Expected regimen name is: consultantsonly Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshConsultantPage.clickConsultantOnlyProduct(TestConstantsRFL.CONSULTANT_ONLY_EVENT_SUPPORT);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("event"), "Expected url contains is: Event but Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshHomePage.clickDonationProduct();
		storeFrontBrandRefreshHomePage.addDonationProductToBag();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber, CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Adhoc order not placed successfully from biz site for Consultant user.");
		orderNumber = storeFrontBrandRefreshHomePage.getOrderNumberFromOrderConfirmationPage();
		grandTotal = storeFrontBrandRefreshHomePage.getGrandTotalFromOrderConfirmationPage();

		randomOrderList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_ORDER_DETAILS_FOR_CONSULTANT_RFL_4289, orderNumber),RFL_DB);
		totalAmount = String.valueOf(getValueFromQueryResult(randomOrderList, "Total"));
		//totalAmount = (String) getValueFromQueryResult(randomOrderList, "Total");
		s_assert.assertTrue(totalAmount.contains(grandTotal)," GrandTotal from UI is: "+grandTotal+" But from DB is: "+totalAmount);
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open biz, Click on Country Toggle Select AUS and verify
	 * Assertions:
	 * AUS Should be present in Country Toggle
	 * User should redirected to AUS Country after clicking on 'AUS' from country toggle
	 */
	//QTest ID TC-926 PWS.biz> Verify the User is redirected to the Corporate Australian Home Page
	@Test
	public void testVerifyTheUserIsRedirectedToTheCorporateAustralianHomePageBizPWS_926(){
		String countryName="AUS";
		String url="rodanandfields.com.au";
		String currentUrl=null;
		openBIZSite();
		storeFrontBrandRefreshHomePage.clickCountryToggle();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCountryPresentInCountryToggle(countryName)," Expected country: "+countryName+" not present in country toggle");
		storeFrontBrandRefreshHomePage.selectCountry(countryName);
		currentUrl=storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(url),"User is not redirected to expected country:"+countryName+" Url should contains: "+url+" But actual on UI is: "+currentUrl);
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open biz, login with consultant, place adhoc order with new shipping address
	 * Assertions:
	 * Newly created shipping profile at order confirmation page 
	 */
	//QTest ID TC-2480 Adhoc Order with Newly created shipping address
	@Test
	public void testAdhocOrderWithNewlyCreatedShippingAddressFromBIZ_2480(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber1 = CommonUtils.getRandomNum(10000, 1000000);
		String addressName = "Home";
		String shippingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME+randomNumber1;
		String shippingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME;
		String firstName = TestConstantsRFL.FIRST_NAME;
		String postalCode = TestConstantsRFL.POSTAL_CODE;
		String cardNumber = TestConstantsRFL.CARD_NUMBER;
		String nameOnCard = firstName;
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String consultantEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openBIZSite();
		storeFrontBrandRefreshConsultantPage = storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshConsultantPage.mouseHoverShopSkinCareAndClickLink("CONSULTANT-ONLY PRODUCTS");
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getCurrentURL().toLowerCase().contains("consultantsonly"), "Expected regimen name is: consultantsonly Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshConsultantPage.clickConsultantOnlyProduct(TestConstantsRFL.CONSULTANT_ONLY_BUSINESS_PROMOTION);
		storeFrontBrandRefreshConsultantPage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		storeFrontBrandRefreshConsultantPage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.clickChangeShippingAddressBtn();
		storeFrontBrandRefreshHomePage.enterShippingProfileDetailsForPWS(addressName, shippingProfileFirstName, shippingProfileLastName, addressLine1, postalCode, phnNumber);
		storeFrontBrandRefreshHomePage.clickUseThisAddressShippingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshConsultantPage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.isThankYouTextPresentAfterOrderPlaced(), "Order is not placed successfully");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getShippingAddressName().contains(shippingProfileFirstName),"Shipping address name expected is "+shippingProfileFirstName+" While on UI is "+storeFrontBrandRefreshHomePage.getShippingAddressName());
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open biz, login with consultant, place adhoc order with new billing address
	 * Assertions:
	 * Newly created billing profile at order confirmation page 
	 */
	//QTest ID TC-2482 Adhoc Order with Newly created billing address
	@Test
	public void testAdhocOrderWithNewlyCreatedBillingAddressFormBIZ_2481(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String postalCode = TestConstantsRFL.POSTAL_CODE;
		String cardNumber = TestConstantsRFL.CARD_NUMBER;
		String nameOnCard = firstName;
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String consultantEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openBIZSite();
		storeFrontBrandRefreshConsultantPage = storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		storeFrontBrandRefreshConsultantPage.mouseHoverShopSkinCareAndClickLink("CONSULTANT-ONLY PRODUCTS");
		storeFrontBrandRefreshConsultantPage.clickConsultantOnlyProduct(TestConstantsRFL.CONSULTANT_ONLY_BUSINESS_PROMOTION);
		storeFrontBrandRefreshConsultantPage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshConsultantPage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshConsultantPage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.isThankYouTextPresentAfterOrderPlaced(), "Order is not placed successfully");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getBillingAddressName().contains(billingProfileFirstName), "Expected billing profile name is: "+billingProfileFirstName+" Actual on UI is: "+storeFrontBrandRefreshHomePage.getBillingAddressName());
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getOrderConfirmationTextMsgAfterOrderPlaced().contains("You will receive an email confirmation shortly"), "Order confirmation message does not contains email confirmation");
		s_assert.assertAll();
	}


	/**
	 * Test Summary: Open Biz PWS, Start Enrolling consultant Using GUAM Address and verify
	 * Assertions:
	 * Shipping price on CRP Review and Confirmation page should be 30$
	 * Verify shipping price from DB
	 */
	//QTest ID TC-1003 Biz> Guam consultant standard enrollment
	@Test //* Incomplete DB Assertions
	public void testBizGuamConsultantStandardEnrollment_1003(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(10, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String shippingPrice="30.00";
		String shippingFromUI=null;
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollmentType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password, TestConstantsRFL.GUAM_ADDRESS_LINE1, TestConstantsRFL.GUAM_POSTAL_CODE, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear, CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		storeFrontBrandRefreshHomePage.clickBillingInfoNextBtn();
		storeFrontBrandRefreshHomePage.clickYesSubscribeToPulseNow();
		storeFrontBrandRefreshHomePage.clickYesEnrollInCRPNow();
		storeFrontBrandRefreshHomePage.clickAutoShipOptionsNextBtn();
		storeFrontBrandRefreshHomePage.selectProductToAddToCart();
		storeFrontBrandRefreshHomePage.clickYourCRPOrderPopUpNextBtn();
		shippingFromUI=storeFrontBrandRefreshHomePage.getShippingPriceFromCRPReviewAndConfirmPage();
		s_assert.assertTrue(shippingPrice.contains(shippingFromUI),"Shipping price should be:"+shippingPrice+" But from UI is:"+shippingFromUI);
		storeFrontBrandRefreshHomePage.clickChargeMyCardAndEnrollMeWithOutConfirmAutoship();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyErrorMessageForTermsAndConditionsForConsultant(), "Error message is not present for consultant for terms & conditions");
		storeFrontBrandRefreshHomePage.clickTermsAndConditions();
		storeFrontBrandRefreshHomePage.chargeMyCardAndEnrollMe();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCongratulationsMessageAppeared(),"Congratulations message is not appeared");
		s_assert.assertAll();
	}	


	/**
	 * Test Summary: Open Biz PWS, Start Enrolling consultant Using Puerto Rico Address and verify
	 * Assertions:
	 * Shipping price on CRP Review and Confirmation page should be 30$
	 * Verify shipping price from DB
	 */
	//QTest ID TC-1004 BIZ> Puerto Rico consultant express enrollment
	@Test //* Incomplete DB Assertions
	public void testBIZPuertoRicoConsultantExpressEnrollment_1004(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(10, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String enrollmentType = TestConstantsRFL.EXPRESS_ENROLLMENT;
		String shippingPrice="30.00";
		String shippingFromUI=null;
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollmentType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password, TestConstantsRFL.PUERTO_RICO_ADDRESS_LINE1, TestConstantsRFL.PUERTO_RICO_POSTAL_CODE, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredForGuamAndPuertoRicoAddress();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear, CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		storeFrontBrandRefreshHomePage.clickCompleteAccountNextBtn();
		shippingFromUI=storeFrontBrandRefreshHomePage.getShippingPriceFromCRPReviewAndConfirmPage();
		s_assert.assertTrue(shippingPrice.contains(shippingFromUI),"Shipping price should be:"+shippingPrice+" But from UI is:"+shippingFromUI);
		storeFrontBrandRefreshHomePage.clickChargeMyCardAndEnrollMeWithOutConfirmAutoship();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyErrorMessageForTermsAndConditionsForConsultant(), "Error message is not present for consultant for terms & conditions");
		storeFrontBrandRefreshHomePage.clickTermsAndConditions();
		storeFrontBrandRefreshHomePage.chargeMyCardAndEnrollMe();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCongratulationsMessageAppeared(),"Congratulations Message not appeared");
		s_assert.assertAll();
	}

	//QTest ID TC-2425 You May Also Enjoy These Products section visible on the product detail page(corp, biz)
	@Test(enabled=false)//should be a manual test case
	public void testYouMayAlsoEnjoyTheseProductsSectionVisibleOnTheProductDetailPage_2425(){
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isYouMayAlsoFindTheseProductsSectionVisible(), "you may also find this section is not present");
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isYouMayAlsoFindTheseProductsSectionVisibleBiz(), "you may also find this section is not present");
		s_assert.assertAll();
	}
	
	/***
	 * Test Summary:- Open Biz PWS, Login with PC User, and verify
	 * Assertions:
	 * Verify sponsor name on the top of the page
	 */ 
	//QTest ID TC-2152 Sponsor infor_PWS.biz
	@Test
	public void testSponsorInfo_MyAccountFromBizPWS_2152(){
		String pcEmailId =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailId, password);
		storeFrontBrandRefreshHomePage.clickMyAccountLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isSponsorInfoPresentOnTopOfPage(),"Sponsor Info not Available on Top of Page");
		s_assert.assertAll();
	}	
	
	//QTest ID TC-2427 Carousel should be continuous
	@Test
	public void testCarouselShouldBeContinuous_2427(){
		String category = "REVERSE";
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		storeFrontBrandRefreshHomePage.isYouMayAlsoFindTheseProductsSectionVisible();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCarouselIsContinous(),"Carousel is not continous");
		
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(category);
		storeFrontBrandRefreshHomePage.isYouMayAlsoFindTheseProductsSectionVisibleBiz();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCarouselIsContinousBiz(),"Carousel is not continous");
		
		openCOMSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(category);
		storeFrontBrandRefreshHomePage.isYouMayAlsoFindTheseProductsSectionVisibleBiz();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCarouselIsContinousBiz(),"Carousel is not continous");
		
		s_assert.assertAll();
	}
}