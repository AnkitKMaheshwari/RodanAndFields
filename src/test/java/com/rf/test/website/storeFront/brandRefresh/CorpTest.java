package com.rf.test.website.storeFront.brandRefresh;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import com.rf.core.utils.CommonUtils;
import com.rf.core.utils.DBUtil;
import com.rf.core.website.constants.TestConstantsRFL;
import com.rf.core.website.constants.dbQueries.DBQueries_RFL;
import com.rf.core.website.constants.dbQueries.DBQueries_RFO;
import com.rf.pages.website.storeFrontBrandRefresh.StoreFrontBrandRefreshHomePage;
import com.rf.test.website.RFBrandRefreshWebsiteBaseTest;

public class CorpTest extends RFBrandRefreshWebsiteBaseTest{
	private static final Logger logger = LogManager
			.getLogger(CorpTest.class.getName());

	//QTest ID TC-506  RC Enrollment from corp site.
	@Test (priority=1)//smoke
	public void testRCEnrollment_506(){
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
		String sponsorID = null;
		String addressName = "Home";
		sponsorID = getRandomSponsorFromDB();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartBtn();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.clickClickHereLinkForRC();
		s_assert.assertTrue(driver.getCurrentUrl().contains("Retail"), "After clicking click here link for RC not navigated to RC Enrollment page.");
		storeFrontBrandRefreshHomePage.enterProfileDetailsForPCAndRC(firstName,lastName,emailAddress,password,phnNumber,gender);
		storeFrontBrandRefreshHomePage.clickCreateMyAccountBtnOnCreateRetailAccountPage();
		storeFrontBrandRefreshHomePage.enterIDNumberAsSponsorForPCAndRC(sponsorID);
		storeFrontBrandRefreshHomePage.clickBeginSearchBtn();
		storeFrontBrandRefreshHomePage.selectSponsorRadioBtn();
		storeFrontBrandRefreshHomePage.clickSelectAndContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.enterShippingProfileDetails(addressName, shippingProfileFirstName,shippingProfileLastName, addressLine1, postalCode, phnNumber);
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfoDetails(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickContinueBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Enrollment is not completed successfully");
		s_assert.assertAll(); 
	}

	//QTest ID TC-507 PC Enrollment From Corp site
	@Test(priority=2)//smoke
	public void testPCEnrollment_507(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String shippingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String shippingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String gender = TestConstantsRFL.GENDER_MALE;
		String sponsorID = null;
		String addressName = "Home";
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		sponsorID = getRandomSponsorFromDB();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartBtn();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.clickClickHereLinkForPC();
		s_assert.assertTrue(driver.getCurrentUrl().contains("PCPerks"), "After clicking click here link for PC not navigated to PC Enrollment page.");
		storeFrontBrandRefreshHomePage.clickEnrollNowBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.enterProfileDetailsForPCAndRC(firstName,lastName,emailAddress,password,phnNumber,gender);
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.enterIDNumberAsSponsorForPCAndRC(sponsorID);
		storeFrontBrandRefreshHomePage.clickBeginSearchBtn();
		storeFrontBrandRefreshHomePage.selectSponsorRadioBtn();
		storeFrontBrandRefreshHomePage.clickSelectAndContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyErrorMessageForTermsAndConditionsForPCAndRC(), "Terms and candition error message not present for PC User.");
		storeFrontBrandRefreshHomePage.checkTermsAndConditionChkBoxForPCAndRC();
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickContinueBtnOnAutoshipSetupPageForPC();
		storeFrontBrandRefreshHomePage.clickContinueBtn();
		storeFrontBrandRefreshHomePage.enterShippingProfileDetails(addressName, shippingProfileFirstName,shippingProfileLastName, addressLine1, postalCode, phnNumber);
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfoDetails(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickContinueBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.clickCompleteEnrollmentBtn();
		storeFrontBrandRefreshHomePage.clickOKBtnOfJavaScriptPopUp();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isPCEnrollmentCompletedSuccessfully(), "PC enrollment is not completed successfully");
		s_assert.assertAll();
	}

	//QTest ID TC-505 Consultant Express Enrollment from Corp
	@Test(priority=3)//smoke
	public void testConsultantExpressEnrollment_505(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(10, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String enrollemntType = "Express";
		String sponsorID = getRandomSponsorFromDB();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.enterCID(sponsorID);
		storeFrontBrandRefreshHomePage.clickSearchResults();
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password, addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear, CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		//storeFrontBrandRefreshHomePage.enterPWS(firstName+lastName+randomNum);
		storeFrontBrandRefreshHomePage.clickCompleteAccountNextBtn();
		storeFrontBrandRefreshHomePage.clickChargeMyCardAndEnrollMeWithOutConfirmAutoship();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyErrorMessageForTermsAndConditionsForConsultant(), "Error message is not present for consultant for terms & conditions");
		storeFrontBrandRefreshHomePage.clickTermsAndConditions();
		storeFrontBrandRefreshHomePage.chargeMyCardAndEnrollMe();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCongratulationsMessageAppeared(),"");
		s_assert.assertAll();
	}

	//QTest ID TC-503 Consultant Standard Enrollment
	@Test(priority=4)//smoke
	public void testConsultantStandardEnrollment_503(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(11, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String enrollemntType = "Standard";
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		String sponsorID = getRandomSponsorFromDB();
		storeFrontBrandRefreshHomePage.enterCID(sponsorID);
		storeFrontBrandRefreshHomePage.clickSearchResults();
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
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

	//QTest ID TC-394 My account options as RC customer
	@Test(priority=5)
	public void testMyAccountOptionAsRCCustomer_394(){
		String rcEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_RC);
		storeFrontBrandRefreshHomePage.loginAsRCUser(rcEmailID, password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedInOnCorpSite(),"RC user is not logged in successfully");
		storeFrontBrandRefreshHomePage.clickMyAccountLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyLinkPresentUnderMyAccount("Order History"),"order history link is not present");
		storeFrontBrandRefreshHomePage.clickOrderManagementSublink("Order History");
		int orderNumber =storeFrontBrandRefreshHomePage.clickViewDetailsForOrderAndReturnOrderNumber();
		if(orderNumber!=0){
			s_assert.assertTrue(storeFrontBrandRefreshHomePage.isOrderDetailsPopupPresent(),"Order details popup not present after clicking view order details link.");
			storeFrontBrandRefreshHomePage.clickCloseOfOrderDetailsPopup();
		}
		s_assert.assertAll();
	}

	//Search for a Sponsor
	@Test(enabled=false)
	public void testSearchForASponser(){
		String sponsorID = null;
		String PWS = null;
		sponsorID = getRandomSponsorFromDB();
		storeFrontBrandRefreshHomePage.clickFindAConsultantImageLink();
		storeFrontBrandRefreshHomePage.enterIDNumberAsSponsorForPCAndRC(sponsorID);
		storeFrontBrandRefreshHomePage.clickBeginSearchBtn();
		PWS = storeFrontBrandRefreshHomePage.clickAndReturnPWSFromFindConsultantPage();
		s_assert.assertTrue(driver.getCurrentUrl().contains(PWS.split("//")[1]), "User has not been navigated to pws site for searched consultant Expected: "+PWS.split("//")[1]+" While actual: "+driver.getCurrentUrl());
		s_assert.assertAll();
	}

	//QTest ID TC-2339 Crop > My account options as PC customer
	@Test(priority=6)
	public void testMyAccountOptionsAs_PC_Customer_2339(){
		String pcEmailId =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		storeFrontBrandRefreshPCUserPage = storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailId, password);
		storeFrontBrandRefreshPCUserPage.clickMyAccountLink();
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.verifyLinkPresentUnderMyAccount("Order History"),"order history link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.verifyLinkPresentUnderMyAccount("Edit Order"),"Edit Order link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.verifyLinkPresentUnderMyAccount("Change my PC Perks Status"),"Change my PC Perks Status link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.verifyLinkPresentUnderMyAccount("PC Perks FAQs"),"PC Perks FAQs Status link is not present");
		storeFrontBrandRefreshPCUserPage.clickOrderManagementSublink("Order History");
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.verifyOrderHistoryPresent("Order History"),"section order history not present");
		storeFrontBrandRefreshPCUserPage.navigateToBackPage();
		storeFrontBrandRefreshPCUserPage.clickOrderManagementSublink("Edit Order");
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.isEditOrderPagePresent(),"Edit order page is not present");
		storeFrontBrandRefreshPCUserPage.navigateToBackPage();
		storeFrontBrandRefreshPCUserPage.clickOrderManagementSublink("Change my PC Perks Status");
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.verifyCurrentPage("PcPerksStatus"),"URL does not contain pcPerksStatus");
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.isPcPerksStatusLinkPresent(),"Delay or Cancel PC Perks link is not present");
		storeFrontBrandRefreshHomePage.clickBackToMyAccountBtn();
		storeFrontBrandRefreshPCUserPage.clickOrderManagementSublink("PC Perks FAQs");
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.verifyFaqPagePresent(),"This is not faq's page");
		s_assert.assertAll();
	}

	//PC Edit order as a PC User
	@Test(priority=7)
	public void testPCEditOrderAsAPCUser(){
		String pcEmailId =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		storeFrontBrandRefreshPCUserPage = storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailId, password);
		storeFrontBrandRefreshPCUserPage.clickMyAccountLink();
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.verifyLinkPresentUnderMyAccount("Order History"),"order history link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.verifyLinkPresentUnderMyAccount("Edit Order"),"Edit Order link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.verifyLinkPresentUnderMyAccount("Change my PC Perks Status"),"Change my PC Perks Status link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.verifyLinkPresentUnderMyAccount("PC Perks FAQs"),"PC Perks FAQs Status link is not present");
		storeFrontBrandRefreshPCUserPage.clickOrderManagementSublink("Edit Order");
		storeFrontBrandRefreshHomePage.clickEditOrderbtn();
		storeFrontBrandRefreshHomePage.clickAddToCartBtnForHighPriceItems();
		storeFrontBrandRefreshHomePage.clickOnUpdateOrderBtn();
		storeFrontBrandRefreshHomePage.handleAlertAfterUpdateOrder();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getConfirmationMessage().contains("Replenishment Order items successfully updated!"),"No Message appearing after order update");
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open Corp , go to my account , click edit order link and change billing profile 
	 * Assertions:
	 * changed billing profile should ne present 
	 */
	//QTest TC-2345:PC Perks Template - Billing Profile
	@Test(enabled=true)
	public void testPCPerksTemplateBillingAddressUpdateFromCorp_2345(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME+randomNumber;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME;
		String pcEmailId =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		storeFrontBrandRefreshPCUserPage = storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailId, password);
		storeFrontBrandRefreshHomePage.clickMyAccountLink();
		storeFrontBrandRefreshHomePage.clickEditOrderLink();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationLinkUnderBillingTabOnPWS();
		storeFrontBrandRefreshHomePage.enterBillingInfoForPWS(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getBillingAddressName().contains(billingProfileFirstName), "Expected billing profile name is: "+billingProfileFirstName+" Actual on UI is: "+storeFrontBrandRefreshHomePage.getBillingAddressName());
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open Corp and initiate consultant express enrollment 
	 * Assertions:
	 * Consultant user enrolled successfully
	 */
	//QTest ID TC-2349 Consultant Express Enrollment
	@Test
	public void testConsultantExpressEnrollmentFromCorp_2349(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(10, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String enrollemntType = TestConstantsRFL.EXPRESS_ENROLLMENT;
		String sponsorID = getRandomSponsorFromDB();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.enterCID(sponsorID);
		storeFrontBrandRefreshHomePage.clickSearchResults();
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password, addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear, CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		//storeFrontBrandRefreshHomePage.enterPWS(firstName+lastName+randomNum);
		storeFrontBrandRefreshHomePage.clickCompleteAccountNextBtn();
		storeFrontBrandRefreshHomePage.clickChargeMyCardAndEnrollMeWithOutConfirmAutoship();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyErrorMessageForTermsAndConditionsForConsultant(), "Error message is not present for consultant for terms & conditions");
		storeFrontBrandRefreshHomePage.clickTermsAndConditions();
		storeFrontBrandRefreshHomePage.chargeMyCardAndEnrollMe();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCongratulationsMessageAppeared(),"");
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open Corp Site, Enroll consultant, goto My account and click on Edit My PWS link and verify
	 * Assertions:
	 * User is Enrolled Successfully
	 * Upload a new photo button is present on UI or not
	 */
	//QTest ID TC-2340 My account- As a consultant customer for Corp Site
	@Test(enabled=true)
	public void testMyAccountAsAConsultantCustomerCorp_2340(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(10, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String kitName = TestConstantsRFL.CONSULTANT_RFX_EXPRESS_BUSINESS_KIT;
		String regimen = TestConstantsRFL.REGIMEN_NAME_REDEFINE;
		String enrollemntType = TestConstantsRFL.EXPRESS_ENROLLMENT;
		String editMyPWS = "edit my pws";
		String sponsorID = getRandomSponsorFromDB();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.enterCID(sponsorID);
		storeFrontBrandRefreshHomePage.clickSearchResults();
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

	/**
	 * Test Summary: Open Corp Site, Search for Sponsor id and Existing Consultant Prefix, Start Enrolling Consultant using Express Enrollment and enter existing user prefix and verify
	 * Assertions:
	 * prefix for com is unavailable
	 * prefix for biz is unavailable
	 */
	//*QTest ID TC-874 Enroll a consultant using existing CA Prefix (Cross Country Sponsor) on Corporate Site
	@Test(enabled=true) 
	public void testEnrollConsultantUsingExistingCAPrefixCrossCountryOnCorp_874(){
		String dbIP2 = driver.getDBIP2();
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(00, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String enrollemntType = TestConstantsRFL.EXPRESS_ENROLLMENT;
		String countryID ="40";
		List<Map<String, Object>> sitePrefixList=null;
		String sitePrefix=null;
		String sponsorID=null;
		sponsorID = getRandomSponsorFromDB();
		//Fetch cross country Email address from database.
		sitePrefixList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_ALREADY_EXISTING_SITE_PREFIX_RFO,countryID,sponsorID),RFO_DB,dbIP2);
		sitePrefix=String.valueOf(getValueFromQueryResult(sitePrefixList, "SitePrefix"));
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.enterCID(sponsorID);
		storeFrontBrandRefreshHomePage.clickSearchResults();
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

	/***
	 * Test Summary:- Open corp url, initiate consultant enrollment, add billing profile with INVALID credit card and complete enrollment
	 * Assertions:
	 * Error message should be displayed for INVALID credit card after complete the enrollment
	 */	
	//QTest ID TC-1945 Payment error message - Enrollment
	@Test
	public void testPaymentErrorMessageFromCorp_1945(){
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
		String sponsorID = getRandomSponsorFromDB();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.enterCID(sponsorID);
		storeFrontBrandRefreshHomePage.clickSearchResults();
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

	/**
	 * Test Summary: Open Corp, Fetch Inactive Consultant User from DB, Enroll with inactive consultant user and verify
	 * Assertions:
	 * User is Enrolled Successfully
	 * User is navigating to their own Biz PWS site
	 * Verify Account status id from DB
	 */
	//QTest ID TC-868 Registering the consultant using existing consultant's email id which terminated from Corp
	@Test (enabled=true)
	public void testRegisterUsingExistingCustomerEmailIdFromBIZ_868(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(10, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String enrollemntType = TestConstantsRFL.EXPRESS_ENROLLMENT;
		List<Map<String, Object>> randomInactiveConsultantList =  null;
		List<Map<String, Object>> accountStatusIDList =  null;
		String consultantEmailID = null;
		String sponsorID=null;
		String statusID = null;
		sponsorID = getRandomSponsorFromDB();
		while(true) {
			//Fetching Inactive consultant from database
			randomInactiveConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_INACTIVE_CONSULTANT_EMAILID,RFL_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomInactiveConsultantList, "EmailAddress");
			//Checking user is inactive or not
			storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
			if(storeFrontBrandRefreshHomePage.isLoginFailed()){
				storeFrontBrandRefreshHomePage.refreshThePage();
				break;
			}else{
				continue;
			}

		}
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.enterCID(sponsorID);
		storeFrontBrandRefreshHomePage.clickSearchResults();
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
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains("biz")," User is not redirecting to Own PWS after Enrollment");

		//verify Account status
		accountStatusIDList =  DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_ACCOUNT_STATUS_ID, consultantEmailID), RFL_DB);
		statusID = String.valueOf(getValueFromQueryResult(accountStatusIDList, "StatusID"));
		s_assert.assertTrue(statusID.contains("1"), "Account status is not active");
		s_assert.assertAll();
	}



	/**
	 * Test Summary: Open Corp, Enroll a consultant user with existing SSN Number and click on cancel enrollment repeat enrollment with same SSN and click on 'send emil to reset password' and verify
	 * Assertions:
	 * Existing user popup should present
	 * User should redirect to home page after clicking on Cancel Enrollment
	 * Email verification message should present on UI After clicking on click 'send to reset password'
	 */
	//QTest ID TC-869 Register the consultant using existing consultant's SSN from Corp
	@Test 
	public void testRegisterTheConsultantUsingExistingConsultantSSNFromCorp_869(){
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
		String kitName = TestConstantsRFL.CONSULTANT_RFX_EXPRESS_BUSINESS_KIT;
		String enrollemntType = "Express";
		String sponsorID=null;
		sponsorID = getRandomSponsorFromDB();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.enterCID(sponsorID);
		storeFrontBrandRefreshHomePage.clickSearchResults();
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
		storeFrontBrandRefreshHomePage.hoverOnBeAConsultantAndClickLinkOnEnrollMe();
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
		storeFrontBrandRefreshHomePage.hoverOnBeAConsultantAndClickLinkOnEnrollMe();
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

	/**
	 * Test Summary: Open Corp, Enroll a consultant User with special character in Prefix and verify 
	 * Assertions: 
	 * Website prefix field should not accept special character
	 */
	// *QTest ID TC-872 Enroll a consultant using special characters in the prefix
	// field from Corp
	@Test(enabled = true)
	public void testEnrollConsultantUsingSpecialCharsInPrefixFieldFromCorp_872() {
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(10, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME + randomNum;
		String emailAddress = firstName + randomNum + "@rodanandfields.com";
		String PWSSpclChars = TestConstantsRFL.PWS_SPCLCHARS;
		String enrollemntType = TestConstantsRFL.EXPRESS_ENROLLMENT;
		String sponsorID = null;
		sponsorID = getRandomSponsorFromDB();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.enterCID(sponsorID);
		storeFrontBrandRefreshHomePage.clickSearchResults();
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password,addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear, CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		storeFrontBrandRefreshHomePage.enterSpecialCharacterInWebSitePrefixField(PWSSpclChars);
		storeFrontBrandRefreshHomePage.clickCompleteAccountNextBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isValidationMessagePresentForPrefixField(),"WebSite Prefix Field accepts Special Character");
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open Corp, Enroll a consultant User with Existing Prefix and verify
	 * Assertions: 
	 * Website prefix for Biz pws is unavailable
	 * Website prefix for COM pws is unavailable
	 */
	// *QTest ID TC-873 Enroll a consultant using existing Prefix for Corp
	@Test(enabled = true)
	public void testEnrollAConsultantUsingExistingPrefixFromCorp_873() {
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(00, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME + randomNum;
		String emailAddress = firstName + randomNum + "@rodanandfields.com";
		String enrollemntType = TestConstantsRFL.EXPRESS_ENROLLMENT;
		String url = null;
		String prefix = null;
		List<Map<String, Object>> randomConsultant = null;
		randomConsultant = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_EXISTING_CONSULTANT_SITE_URL, RFL_DB);
		url = (String) getValueFromQueryResult(randomConsultant, "URL");
		String sponsorID = getRandomSponsorFromDB();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.enterCID(sponsorID);
		storeFrontBrandRefreshHomePage.clickSearchResults();
		prefix = storeFrontBrandRefreshHomePage.getSplittedPrefixFromConsultantUrl(url);
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password,addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear, CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		storeFrontBrandRefreshHomePage.enterUserPrefixInPrefixField(prefix);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getPrefixMessageForBiz().contains("unavailable"),"Expected message is unavailable for .biz but actual on UI is: "+ storeFrontBrandRefreshHomePage.getPrefixMessageForBiz());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getPrefixMessageForCom().contains("unavailable"),"Expected message is unavailable for .com but actual on UI is: "+ storeFrontBrandRefreshHomePage.getPrefixMessageForCom());
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open Corp Site, Start Enrolling consultant Using GUAM Address and verify
	 * Assertions:
	 * Shipping price on CRP Review and Confirmation page should be 30$
	 * Verify shipping price from DB
	 */
	//QTest ID TC-1001 Corp> Guam consultant express enrollment
	@Test //* Incomplete DB Assertions
	public void testGuamConsultantExpressEnrollmentCorp_1001(){
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
		String sponsorID = getRandomSponsorFromDB();

		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.enterCID(sponsorID);
		storeFrontBrandRefreshHomePage.clickSearchResults();
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollmentType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password, TestConstantsRFL.GUAM_ADDRESS_LINE1, TestConstantsRFL.GUAM_POSTAL_CODE, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear, CVV);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		storeFrontBrandRefreshHomePage.clickCompleteAccountNextBtn();
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
	 * Test Summary: Open Corp Site, Start Enrolling consultant Using Puerto Rico Address and verify
	 * Assertions:
	 * Shipping price on CRP Review and Confirmation page should be 30$
	 * Verify shipping price from DB
	 */
	//QTest ID TC-1002 Corp: Puerto Rico consultant standard enrollment
	@Test //* Incomplete DB Assertions
	public void testPuertoRicoConsultantStandardEnrollmentCorp_1002(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(10, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String shippingPrice="30.00";
		String shippingFromUI=null;
		String sponsorID = getRandomSponsorFromDB();

		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Enroll Now");
		storeFrontBrandRefreshHomePage.enterCID(sponsorID);
		storeFrontBrandRefreshHomePage.clickSearchResults();
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollmentType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password, TestConstantsRFL.PUERTO_RICO_ADDRESS_LINE1, TestConstantsRFL.PUERTO_RICO_POSTAL_CODE, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredForGuamAndPuertoRicoAddress();
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
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCongratulationsMessageAppeared(),"Congratulations Message not appeared");
		s_assert.assertAll();
	}

	//QTest ID TC-343 Products-Redefine-Regimen-Links should be redirecting to the appropriate page
	@Test
	public void testProductsRedefineRegimenLinksRedirectingToTheAppropriatePage_343(){
		String subLinkRegimen = TestConstantsRFL.REGIMEN_NAME_REDEFINE;
		String subLinkProducts = "Products";
		String subLinkResults = "Results";
		String subLinkFAQ = "FAQs";
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(subLinkRegimen);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(subLinkRegimen.toLowerCase()), "Expected regimen name is "+subLinkRegimen.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(subLinkProducts.toLowerCase()), "Expected sublink in url is "+subLinkProducts.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickSubLink(subLinkRegimen, subLinkResults);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(subLinkResults.toLowerCase()), "Expected sublink in url is "+subLinkResults.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToResultsPage(subLinkRegimen),"user is not on results page");
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickSubLink(subLinkRegimen,subLinkFAQ);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("CommonQuestions".toLowerCase()), "Expected sublink in url is "+"CommonQuestions".toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToFAQsPage(subLinkRegimen),"user is not on FAQs page");
		s_assert.assertAll();
	}

	//QTest ID TC-346 Reverse Products-links should be working properly (products, testimonials, in the news, FAQ's, advice, glossary)
	@Test
	public void testReverseProductsLinksWorkingProperly_346(){
		String subLinkRegimen = TestConstantsRFL.REGIMEN_NAME_REVERSE;
		String subLinkProducts = "Products";
		String subLinkFAQ = "FAQs";
		String subLinkResults = "Results";
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(subLinkRegimen);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(subLinkRegimen.toLowerCase()), "Expected regimen name is "+subLinkRegimen.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(subLinkProducts.toLowerCase()), "Expected sublink in url is "+subLinkProducts.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickSubLink(subLinkRegimen,subLinkFAQ);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("CommonQuestions".toLowerCase()), "Expected sublink in url is "+"CommonQuestions".toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToFAQsPage(subLinkRegimen),"user is not on FAQs page");
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickSubLink(subLinkRegimen, subLinkResults);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(subLinkResults.toLowerCase()), "Expected sublink in url is "+subLinkResults.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToResultsPage(subLinkRegimen),"user is not on results page");
		s_assert.assertAll();
	}

	//QTest ID TC-349 Unblemish Products-links should be working properly (products, testimonials, in the news, FAQ's, advice, glossary)
	@Test
	public void testUnblemishProductsLinksWorkingProperly_349(){
		String subLinkRegimen = TestConstantsRFL.REGIMEN_NAME_UNBLEMISH;
		String subLinkProducts = "Products";
		String subLinkFAQ = "FAQs";
		String subLinkResults = "Results";
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(subLinkRegimen);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(subLinkRegimen.toLowerCase()), "Expected regimen name is "+subLinkRegimen.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(subLinkProducts.toLowerCase()), "Expected sublink in url is "+subLinkProducts.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickSubLink(subLinkRegimen,subLinkFAQ);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("CommonQuestions".toLowerCase()), "Expected sublink in url is "+"CommonQuestions".toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToFAQsPage(subLinkRegimen),"user is not on FAQs page");
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickSubLink(subLinkRegimen, subLinkResults);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(subLinkResults.toLowerCase()), "Expected sublink in url is "+subLinkResults.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToResultsPage(subLinkRegimen),"user is not on results page");
		s_assert.assertAll();
	}

	//QTest ID TC-350 Soothe Products-links should be working properly (products, testimonials, in the news, FAQ's, advice, glossary)
	@Test
	public void testSootheProductsLinksWorkingProperly_350(){
		String subLinkRegimen = TestConstantsRFL.REGIMEN_NAME_SOOTHE;
		String subLinkProducts = "Products";
		String subLinkFAQ = "FAQs";
		String subLinkResults = "Results";
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(subLinkRegimen);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(subLinkRegimen.toLowerCase()), "Expected regimen name is "+subLinkRegimen.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(subLinkProducts.toLowerCase()), "Expected sublink in url is "+subLinkProducts.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickSubLink(subLinkRegimen,subLinkFAQ);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("CommonQuestions".toLowerCase()), "Expected sublink in url is "+"CommonQuestions".toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToFAQsPage(subLinkRegimen),"user is not on FAQs page");
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickSubLink(subLinkRegimen, subLinkResults);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(subLinkResults.toLowerCase()), "Expected sublink in url is "+subLinkResults.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToResultsPage(subLinkRegimen),"user is not on results page");
		s_assert.assertAll();
	}

	//product philosophy link should be working
	@Test(enabled=false)//needs updation
	public void testProductsPhilosophyLinkShouldWorkingProper(){
		storeFrontBrandRefreshHomePage.clickShopSkinCareBtn();
		//verify Product Philosophy link working?
		storeFrontBrandRefreshHomePage.clickProductPhilosophyLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateProductPhilosohyPageDisplayed(),"Product Philosophy page is not displayed!!");
		s_assert.assertAll();
	}

	//Digital Product Catalog- Links should be displayed the information properly
	@Test(enabled=false)//needs updation
	public void testDigitalProductCatalogLinkShouldDisplayInformationProperly(){
		storeFrontBrandRefreshHomePage.clickShopSkinCareBtn();
		//verify Digital Product Catalog- Link should be displayed the information properly?
		storeFrontBrandRefreshHomePage.clickDigitalProductCatalogLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateRealResultsLink(),"Real Results link didn't work");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateSolutionToolLink(),"Solution Tool link didn't work");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validatePCPerksLink(),"PC Perks link didn't work");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateDigitalProductCatalogLink(),"Digital Product link didn't work");
		s_assert.assertAll();
	}

	//Company Links Should be Present
	@Test(enabled=false)//needs updation
	public void testCompanyLinksShouldBePresent(){
		storeFrontBrandRefreshHomePage.clickAboutRFBtn();
		//verify company links is present?
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateExecutiveTeamLinkPresent(),"Executive Team Link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateContactUsLinkPresent(),"Contact Us Link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateWhoWeAreLinkPresent(),"WhoWeAre/Our History Link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validatePressRoomLinkPresent(),"Press Room Link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateCareersLinkPresent(),"Careers Link is not present");
		s_assert.assertAll();
	}

	//QTest ID TC-368 Footer- Privacy Policy link should be redirecting to the appropriate page
	@Test
	public void testFooterPrivacyPolicyLinkShouldRedirectionToAppropriatePage_368(){
		storeFrontBrandRefreshHomePage.clickPrivacyPolicyLink();
		/*s_assert.assertTrue(storeFrontBrandRefreshHomePage.isPrivacyPolicyPagePresent(), "Privacy policy page is not present after clicked on privacy policy link");*/
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("privacy"), "Expected url having privacy but actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertAll();
	}

	//QTest ID TC-367 Footer-Terms & Conditions link should redirecting to the appropriate page
	@Test
	public void testFooterTermsAndConditionLinkShouldRedirectionToAppropriatePage_367(){
		storeFrontBrandRefreshHomePage.clickTermsAndConditionsLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("terms"), "Expected url having terms but actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertAll();
	}

	//QTest ID TC-369 Satisfaction Guarantee-link should be redirecting properly 
	@Test
	public void testSatisfactionGuaranteeLinkShouldBeRedirectionProperly_369(){
		storeFrontBrandRefreshHomePage.clickSatisfactionGuaranteeLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isSatisfactionGuaranteePagePresent(), "Satisfaction guarantee page is not present after clicked on privacy policy link");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("guarantee"), "Expected url having guarantee but actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertAll();
	}

	//QTest ID TC-353 Real results products- links should be redirecting to the appropriate page
	//QTest ID TC-352 Enhancements Products-links should be working properly (Real results, PC perks, solution tool, digital product catalog)
	//QTest ID TC-351 Essentials Products-links should be working properly
	@Test
	public void testProductsLinkShouldBeRedirectionToAppropriatePage_353_351_352(){
		//For REDEFINE
		String regimen = TestConstantsRFL.REGIMEN_NAME_REDEFINE;
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(regimen.toLowerCase()), "Expected regimen name is "+regimen.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.isRegimenNamePresentAfterClickedOnRegimen(regimen), "regimen name i.e.: "+regimen+" not present");
		//For REVERSE
		regimen = TestConstantsRFL.REGIMEN_NAME_REVERSE;
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(regimen.toLowerCase()), "Expected regimen name is "+regimen.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.isRegimenNamePresentAfterClickedOnRegimen(regimen), "regimen name i.e.: "+regimen+" not present");
		//For UNBLEMISH

		regimen = TestConstantsRFL.REGIMEN_NAME_UNBLEMISH;
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(regimen.toLowerCase()), "Expected regimen name is "+regimen.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.isRegimenNamePresentAfterClickedOnRegimen(regimen), "regimen name i.e.: "+regimen+" not present");
		//For SOOTHE

		regimen = TestConstantsRFL.REGIMEN_NAME_SOOTHE;
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(regimen.toLowerCase()), "Expected regimen name is "+regimen.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.isRegimenNamePresentAfterClickedOnRegimen(regimen), "regimen name i.e.: "+regimen+" not present");
		//For PROMOTIONS

		regimen = TestConstantsRFL.REGIMEN_NAME_PROMOTIONS;
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(TestConstantsRFL.REGIMEN_NAME_FEATURED);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(regimen.toLowerCase()), "Expected regimen name is "+regimen.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.isRegimenNamePresentAfterClickedOnRegimen(regimen), "regimen name i.e.: "+regimen+" not present");
		//For ENHANCEMENTS

		regimen = TestConstantsRFL.REGIMEN_NAME_ENHANCEMENTS;
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(regimen.toLowerCase()), "Expected regimen name is "+regimen.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.isRegimenNamePresentAfterClickedOnRegimen(regimen), "regimen name i.e.: "+regimen+" not present");
		//For ESSENTIALS

		regimen = TestConstantsRFL.REGIMEN_NAME_ESSENTIALS;
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(regimen.toLowerCase()), "Expected regimen name is "+regimen.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.isRegimenNamePresentAfterClickedOnRegimen(regimen), "regimen name i.e.: "+regimen+" not present");
		s_assert.assertAll();
	}

	//QTest ID TC-372 Log in as an existen consultant
	@Test
	public void testLoginAsExistingConsultant_372(){
		String consultantEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		s_assert.assertAll();
	}

	//QTest ID TC-373 Log in as valid PC customer
	@Test
	public void testLoginAsExistingPC_373(){
		String pcEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"PC user is not logged in successfully");
		s_assert.assertAll();
	}

	//QTest ID TC-374 Log in with a valid RC customer
	@Test
	public void testLoginAsExistingRC_374(){
		String rcEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_RC);
		storeFrontBrandRefreshHomePage.loginAsRCUser(rcEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"RC user is not logged in successfully");
		s_assert.assertAll();
	}

	//* QTest ID TC-354 Solution Tool-Find a Rodan + Fields consultant should be working properly
	@Test
	public void testSolutionToolFindARodanFieldsConsultantShouldBeWorkingProperly_354(){
		String sponsorID = TestConstantsRFL.CID_CONSULTANT;
		String fetchedPWS = null;
		storeFrontBrandRefreshHomePage.clickSolutionToolHomePage();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySolutionToolPage(),"Solution tool page is displayed");
		storeFrontBrandRefreshHomePage.clickConnectWithAConsultant();
		storeFrontBrandRefreshHomePage.enterIDNumberAsSponsorForPCAndRC(sponsorID);
		storeFrontBrandRefreshHomePage.clickBeginSearchBtn();
		fetchedPWS = storeFrontBrandRefreshHomePage.getPWSFromFindConsultantPage();
		storeFrontBrandRefreshHomePage.selectSponsorRadioBtnOnFindConsultantPage();
		storeFrontBrandRefreshHomePage.clickSelectAndContinueBtnForPCAndRC();
		s_assert.assertFalse(storeFrontBrandRefreshHomePage.getCurrentURL().contains(fetchedPWS),"Expected pws is: "+fetchedPWS +"While actual on UI: "+storeFrontBrandRefreshHomePage.getCurrentURL());
		s_assert.assertAll(); 
	}

	//QTest ID TC-342 Redefine-Sub links should be displayed properly(regimen, products, results, testimonials, in the news, FAQs, Advice, Glossary)
	@Test
	public void testVerifyRedefineRegimenLinksDisplayedProperly_342(){
		String subSectionResults = "Results";
		String subSectionFAQ = "FAQs";
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCare();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyRedefineRegimenSections(subSectionResults),"Redefine regimen section Results is not displayed");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyRedefineRegimenSections(subSectionFAQ),"Redefine regimen section FAQ is not displayed");
		s_assert.assertAll();
	}

	//QTest ID TC-375 log out with a valid user
	@Test
	public void testLogoutWithAValidUser_375(){
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		logout();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isForgotPasswordLinkPresent(),"User is not logout successfully");
		s_assert.assertAll();
	}

	//Corporate_BUsinessSystem_ Direct Selling
	@Test(enabled=false)
	public void testCorporateBusinessSystemDirectSelling(){
		storeFrontBrandRefreshHomePage.clickAboutRFBtn();
		storeFrontBrandRefreshHomePage.mouseHoverAboutRFAndClickLink("Who We Are");
		storeFrontBrandRefreshHomePage.clickClickhereLinkToLearnDirectSelling();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isClickHereLinkRedirectinToAppropriatePage("directselling.org"), "Click here link of business system is not redirecting to http://directselling.org/ page");
		s_assert.assertAll();
	}

	//Corporate_BUsinessSystem_GettingStarted 
	@Test(enabled=false)//needs updation
	public void testCorporateBusinessSystemGettingStarted(){
		String gettingStarted = "Getting Started";
		String whyRF = "Why R+F";
		String redefineYourFuture = "Redefine Your Future";
		String enrollNow = "Enroll Now";
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
		String CID = TestConstantsRFL.CID_CONSULTANT;
		String kitName = "Big Business Launch Kit";
		String regimen = "Redefine";
		String enrollemntType = "Express";
		String phnNumber1 = "415";
		String phnNumber2 = "780";
		String phnNumber3 = "9099";

		storeFrontBrandRefreshHomePage.clickBeAConsultantBtn();
		storeFrontBrandRefreshHomePage.clickSublinkOfBusinessSystem(whyRF);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isSublinkOfBusinessSystemPresent(gettingStarted), "Getting Started link is not present under Why R+F for business system");
		storeFrontBrandRefreshHomePage.clickSublinkOfBusinessSystem(gettingStarted);
		storeFrontBrandRefreshHomePage.clickClickhereLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isClickHereLinkRedirectinToAppropriatePage("PP_11th_Edition.pdf"), "Click here link of business system is not redirecting to PP_11th_Edition.pdf page");
		storeFrontBrandRefreshHomePage.clickSublinkOfBusinessSystem(redefineYourFuture);
		storeFrontBrandRefreshHomePage.clickDetailsLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isClickHereLinkRedirectinToAppropriatePage("REDEFINE-Your-Future-with-BBL-020813.pdf"), "Details link of redefine your future is not redirecting to REDEFINE-Your-Future-with-BBL-020813.pdf");
		storeFrontBrandRefreshHomePage.clickSublinkOfBusinessSystem(enrollNow);
		storeFrontBrandRefreshHomePage.enterCID(CID);
		storeFrontBrandRefreshHomePage.clickSearchResults();
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password, addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		storeFrontBrandRefreshHomePage.clickCompleteAccountNextBtn();
		storeFrontBrandRefreshHomePage.clickTermsAndConditions();
		storeFrontBrandRefreshHomePage.chargeMyCardAndEnrollMe();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCongratulationsMessageAppeared(),"Congratulations Message not appeared");
		s_assert.assertAll();
	}

	//Corporate_BUsinessSystem_ SuccessStories
	@Test(enabled=false)//No more visible on UI
	public void testCorporateBusinessSystemSuccessStories(){
		String meetOurCommunity = "Meet Our Community";
		String rfxCircleAG = "RFXcircleAG";
		String rfxCircleHZ = "RFXcircleHZ";
		String eliteVAL = "EliteVAL";
		String eliteVMZ = "EliteVMZ";
		String carAchieversAC= "CarAchieversAC";
		String carAchieversDE = "CarAchieversDE";
		String carAchieversFG = "CarAchieversFG";
		String carAchieversHL = "CarAchieversHL";
		String carAchieversMR = "CarAchieversMR";
		String carAchieversSZ = "CarAchieversSZ";

		//storeFrontBrandRefreshHomePage.clickBeAConsultantBtn();
		//storeFrontBrandRefreshHomePage.clickSublinkOfBusinessSystem(meetOurCommunity);
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink(meetOurCommunity);
		storeFrontBrandRefreshHomePage.clickSublinkOfBusinessSystem(rfxCircleAG);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains("RFXcircleAG"), "Expected url contains is: RFxcircleAG but Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshHomePage.navigateToBackPage();
		storeFrontBrandRefreshHomePage.clickSublinkOfBusinessSystem(rfxCircleHZ);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains("RFXcircleHZ"), "Expected url contains is: RFxcircleHZ but Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshHomePage.navigateToBackPage();
		storeFrontBrandRefreshHomePage.clickSublinkOfBusinessSystem(eliteVAL);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains("EliteVAL"), "Expected url contains is: EliteVAL but Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshHomePage.navigateToBackPage();
		storeFrontBrandRefreshHomePage.clickSublinkOfBusinessSystem(eliteVMZ);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains("EliteVMZ"), "Expected url contains is: EliteVMZ but Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshHomePage.navigateToBackPage();
		storeFrontBrandRefreshHomePage.clickSublinkOfBusinessSystem(carAchieversAC);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains("CarAchieversAC"), "Expected url contains is: CarAchieversAC but Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshHomePage.navigateToBackPage();
		storeFrontBrandRefreshHomePage.clickSublinkOfBusinessSystem(carAchieversDE);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains("CarAchieversDE"), "Expected url contains is: CarAchieversDE but Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshHomePage.navigateToBackPage();
		storeFrontBrandRefreshHomePage.clickSublinkOfBusinessSystem(carAchieversFG);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains("CarAchieversFG"), "Expected url contains is: CarAchieversFG but Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshHomePage.navigateToBackPage();
		storeFrontBrandRefreshHomePage.clickSublinkOfBusinessSystem(carAchieversHL);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains("CarAchieversHL"), "Expected url contains is: CarAchieversHL but Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshHomePage.navigateToBackPage();
		storeFrontBrandRefreshHomePage.clickSublinkOfBusinessSystem(carAchieversMR);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains("CarAchieversMR"), "Expected url contains is: CarAchieversMR but Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshHomePage.navigateToBackPage();
		storeFrontBrandRefreshHomePage.clickSublinkOfBusinessSystem(carAchieversSZ);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains("CarAchieversSZ"), "Expected url contains is: CarAchieversSZ but Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		s_assert.assertAll();
	}

	//QTest ID TC-379 The Compensation Plan section Compensation Plan is displayed
	@Test//(enabled=false)//needs updation
	public void testCompensationPlanSectionIsDisplayed_379(){
		String firstSubSectionUnderBusinessSystem = "Why R+F";
		String secondSubSectionUnderBusinessSystem = "Programs and Incentives";
		//String thirdSubSectionUnderBusinessSystem = "Income Illustrator";
		String fourthSubSectionUnderBusinessSystem = "Events";
		String fifthSubSectionUnderBusinessSystem = "Meet Our Community";
		String sixthSubSectionUnderBusinessSystem = "Enroll Now";
		String secondSubSectionUnderProgramsAndIncentives = "Programs & Incentives";
		String thirdSubSectionUnderProgramsAndIncentives = "Enroll Now";
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultant();
		//storeFrontBrandRefreshHomePage.clickBeAConsultantBtn();
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("Business".toLowerCase()), "URL does not contain Business Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtBusinessSystemPage(firstSubSectionUnderBusinessSystem),""+firstSubSectionUnderBusinessSystem+" subTitle not present under business system page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtBusinessSystemPage(secondSubSectionUnderBusinessSystem),""+secondSubSectionUnderBusinessSystem+" subTitle not present under business system page");
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtBusinessSystemPage(thirdSubSectionUnderBusinessSystem),""+thirdSubSectionUnderBusinessSystem+" subTitle not present under business system page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtBusinessSystemPage(fourthSubSectionUnderBusinessSystem),""+fourthSubSectionUnderBusinessSystem+" subTitle not present under business system page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtBusinessSystemPage(fifthSubSectionUnderBusinessSystem),""+fifthSubSectionUnderBusinessSystem+" subTitle not present under business system page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtBusinessSystemPage(sixthSubSectionUnderBusinessSystem),""+sixthSubSectionUnderBusinessSystem+" subTitle not present under business system page");
		storeFrontBrandRefreshHomePage.clickSubSectionUnderBusinessSystem(secondSubSectionUnderBusinessSystem);
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.getSelectedHighlightLinkName().contains("Programs and Incentives"), "Expected selected and highlight link name is: "+"Programs and Incentives but Actual on UI: "+storeFrontBrandRefreshHomePage.getSelectedHighlightLinkName());
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtProgramsAndIncentives(firstSubSectionUnderProgramsAndIncentives),""+firstSubSectionUnderProgramsAndIncentives+" subTitle not present under Programs and Incentives page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtProgramsAndIncentives(secondSubSectionUnderProgramsAndIncentives),""+secondSubSectionUnderProgramsAndIncentives+" subTitle not present under Programs and Incentives page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtProgramsAndIncentives(thirdSubSectionUnderProgramsAndIncentives),""+thirdSubSectionUnderProgramsAndIncentives+" subTitle not present under Programs and Incentives page");
		storeFrontBrandRefreshHomePage.clickToReadIncomeDisclosure();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentUrlOpenedWindow().contains("RF-Income-Disclosure-Statement.pdf"),"current url is not a valid and Expected url");
		s_assert.assertAll();
	}

	//QTest ID TC-380 The Compensation Plan section Programs and Incentives is displayed
	@Test//(enabled=false)//needs updation
	public void testCompensationPlanProgramsAndIncentivesIsDisplayed_380(){
		String firstSubSectionUnderBusinessSystem = "Why R+F";
		String secondSubSectionUnderBusinessSystem = "Programs and Incentives";
		//String thirdSubSectionUnderBusinessSystem = "Income Illustrator";
		String fourthSubSectionUnderBusinessSystem = "Events";
		String fifthSubSectionUnderBusinessSystem = "Meet Our Community";
		String sixthSubSectionUnderBusinessSystem = "Enroll Now";

		//String firstSubSectionUnderProgramsAndIncentives = "Compensation Plan";
		String secondSubSectionUnderProgramsAndIncentives = "Programs & Incentives";
		String thirdSubSectionUnderProgramsAndIncentives = "Enroll Now";

		//storeFrontBrandRefreshHomePage.clickBeAConsultantBtn();
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultant();
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("Business".toLowerCase()), "URL does not contain Business Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtBusinessSystemPage(firstSubSectionUnderBusinessSystem),""+firstSubSectionUnderBusinessSystem+" subTitle not present under business system page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtBusinessSystemPage(secondSubSectionUnderBusinessSystem),""+secondSubSectionUnderBusinessSystem+" subTitle not present under business system page");
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtBusinessSystemPage(thirdSubSectionUnderBusinessSystem),""+thirdSubSectionUnderBusinessSystem+" subTitle not present under business system page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtBusinessSystemPage(fourthSubSectionUnderBusinessSystem),""+fourthSubSectionUnderBusinessSystem+" subTitle not present under business system page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtBusinessSystemPage(fifthSubSectionUnderBusinessSystem),""+fifthSubSectionUnderBusinessSystem+" subTitle not present under business system page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtBusinessSystemPage(sixthSubSectionUnderBusinessSystem),""+sixthSubSectionUnderBusinessSystem+" subTitle not present under business system page");
		storeFrontBrandRefreshHomePage.clickSubSectionUnderBusinessSystem(secondSubSectionUnderBusinessSystem);
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtProgramsAndIncentives(firstSubSectionUnderProgramsAndIncentives),""+firstSubSectionUnderProgramsAndIncentives+" subTitle not present under Programs and Incentives page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtProgramsAndIncentives(secondSubSectionUnderProgramsAndIncentives),""+secondSubSectionUnderProgramsAndIncentives+" subTitle not present under Programs and Incentives page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtProgramsAndIncentives(thirdSubSectionUnderProgramsAndIncentives),""+thirdSubSectionUnderProgramsAndIncentives+" subTitle not present under Programs and Incentives page");
		s_assert.assertAll();
	}

	//The Compensation Plan section Enroll Now is displayed
	@Test(enabled=false)//needs updation
	public void testCompensationPlanSectionEnrollNowIsDisplayed(){
		String firstSubSectionUnderBusinessSystem = "Why R+F";
		String secondSubSectionUnderBusinessSystem = "Programs and Incentives";
		String thirdSubSectionUnderBusinessSystem = "Income Illustrator";
		String fourthSubSectionUnderBusinessSystem = "Events";
		String fifthSubSectionUnderBusinessSystem = "Meet Our Community";
		String sixthSubSectionUnderBusinessSystem = "Enroll Now";
		String firstSubSectionUnderProgramsAndIncentives = "Compensation Plan";
		String secondSubSectionUnderProgramsAndIncentives = "Programs and Incentives";
		String thirdSubSectionUnderProgramsAndIncentives = "Enroll Now";
		storeFrontBrandRefreshHomePage.clickBeAConsultantBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("Business".toLowerCase()), "URL does not contain Business Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtBusinessSystemPage(firstSubSectionUnderBusinessSystem),""+firstSubSectionUnderBusinessSystem+" subTitle not present under business system page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtBusinessSystemPage(secondSubSectionUnderBusinessSystem),""+secondSubSectionUnderBusinessSystem+" subTitle not present under business system page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtBusinessSystemPage(thirdSubSectionUnderBusinessSystem),""+thirdSubSectionUnderBusinessSystem+" subTitle not present under business system page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtBusinessSystemPage(fourthSubSectionUnderBusinessSystem),""+thirdSubSectionUnderBusinessSystem+" subTitle not present under business system page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtBusinessSystemPage(fifthSubSectionUnderBusinessSystem),""+fifthSubSectionUnderBusinessSystem+" subTitle not present under business system page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtBusinessSystemPage(sixthSubSectionUnderBusinessSystem),""+sixthSubSectionUnderBusinessSystem+" subTitle not present under business system page");
		storeFrontBrandRefreshHomePage.clickSubSectionUnderBusinessSystem(secondSubSectionUnderBusinessSystem);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtProgramsAndIncentives(firstSubSectionUnderProgramsAndIncentives),""+firstSubSectionUnderProgramsAndIncentives+" subTitle not present under Programs and Incentives page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtProgramsAndIncentives(secondSubSectionUnderProgramsAndIncentives),""+secondSubSectionUnderProgramsAndIncentives+" subTitle not present under Programs and Incentives page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifySubSectionPresentAtProgramsAndIncentives(thirdSubSectionUnderProgramsAndIncentives),""+thirdSubSectionUnderProgramsAndIncentives+" subTitle not present under Programs and Incentives page");
		storeFrontBrandRefreshHomePage.clickSubSectionUnderBusinessSystem(thirdSubSectionUnderProgramsAndIncentives);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains("NewEnrollment/SearchSponsor"),"current url is not a valid and Expected url");
		s_assert.assertAll();
	}

	//QTest ID TC-371 Contact us-link should be redirecting properly
	@Test
	public void testContactUsLinkShouldBeRedirectingProperly_371(){
		storeFrontBrandRefreshHomePage.clickContactUsAtFooter();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("Contact".toLowerCase()), "URL does not contain Contact Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifylinkIsRedirectedToContactUsPage(),"link is not redirected to contact us page");
		s_assert.assertAll();
	}

	//Essentials Products-links should be working properly (Real results, PC perks, solution tool, digital product catalog)
	@Test(enabled=false)//needs updation
	public void testVerifyEssentialRegimenProductLinksWorkingProperly(){
		String regimen = TestConstantsRFL.REGIMEN_NAME_ESSENTIALS;
		String subSectionLinkRealResults = "Real Results";
		String subSectionLinkPCPerks = "PC PERKS";
		String subSectionSolutionTool = "Solution Tool";
		String subSectionDigitalProductCatalogue = "Digital Product Catalog";
		storeFrontBrandRefreshHomePage.clickShopSkinCareBtn();
		storeFrontBrandRefreshHomePage.selectRegimen(regimen);

		//Verify visibility of Essentials regimen Sections.
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyEssentialsRegimenSections(subSectionLinkRealResults),"Essential regimen section real results is not displayed");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyEssentialsRegimenSections(subSectionLinkPCPerks),"Essential regimen section PC Perks is not displayed");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyEssentialsRegimenSections(subSectionSolutionTool),"Essential regimen section Solution tool is not displayed");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyEssentialsRegimenSections(subSectionDigitalProductCatalogue),"Essential regimen section Digital product catalogue is not displayed");

		//Verify Visibility of Essentials Regimen Real Results subSections.	
		storeFrontBrandRefreshHomePage.clickSubSectionUnderRegimen(subSectionLinkRealResults);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("Results".toLowerCase()), "Expected regimen name is "+"Results".toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToRealResultsPage(),"user is not on Real results page");
		storeFrontBrandRefreshHomePage.navigateToBackPage();

		//Verify Visibility of Essentials Regimen PC Perks subSections.		
		storeFrontBrandRefreshHomePage.clickSubSectionUnderRegimen(subSectionLinkPCPerks);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("PC".toLowerCase()), "Expected regimen name is "+"PCPerks".toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());		
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToPCPerksPage(),"user is not on PC Perks page");
		storeFrontBrandRefreshHomePage.navigateToBackPage();

		//Verify Visibility of Essentials Regimen Solution tool subSections.	
		storeFrontBrandRefreshHomePage.clickSubSectionUnderRegimen(subSectionSolutionTool);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("Solution".toLowerCase()), "Expected regimen name is "+"SolutionTool".toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToSolutionToolPage(),"user is not on Solution tool page");
		storeFrontBrandRefreshHomePage.navigateToBackPage();

		//Verify Visibility of Essentials Regimen Digital product catalogue subSections.	
		storeFrontBrandRefreshHomePage.clickSubSectionUnderRegimen(subSectionDigitalProductCatalogue);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("Digimag".toLowerCase()), "Expected regimen name is "+"Digimag".toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToDigitalProductCataloguePage(),"user is not on Digital product catalogue page");
		storeFrontBrandRefreshHomePage.navigateToBackPage();
		s_assert.assertAll();
	}

	//Enhancements Products-links should be working properly (Real results, PC perks, solution tool, digital product catalog)
	@Test(enabled=false)//needs updation
	public void testVerifyEnhancementsRegimenProductLinksWorkingProperly(){
		String regimen = TestConstantsRFL.REGIMEN_NAME_ENHANCEMENTS;
		String subSectionLinkRealResults = "Real Results";
		String subSectionLinkPCPerks = "PC PERKS";
		String subSectionSolutionTool = "Solution Tool";
		String subSectionDigitalProductCatalogue = "Digital Product Catalog";


		storeFrontBrandRefreshHomePage.clickShopSkinCareBtn();
		storeFrontBrandRefreshHomePage.selectRegimen(regimen);

		//Verify visibility of Essentials regimen Sections.
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyEnhancementsRegimenSections(subSectionLinkRealResults),"Enhancements regimen section real results is not displayed");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyEnhancementsRegimenSections(subSectionLinkPCPerks),"Enhancements regimen section PC Perks is not displayed");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyEnhancementsRegimenSections(subSectionSolutionTool),"Enhancements regimen section Solution tool is not displayed");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyEnhancementsRegimenSections(subSectionDigitalProductCatalogue),"Enhancements regimen section Digital product catalogue is not displayed");

		//Verify Visibility of Essentials Regimen Real Results subSections.	
		storeFrontBrandRefreshHomePage.clickSubSectionUnderRegimen(subSectionLinkRealResults);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("Results".toLowerCase()), "Expected regimen name is "+"Results".toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToRealResultsPage(),"user is not on Real results page");
		storeFrontBrandRefreshHomePage.navigateToBackPage();

		//Verify Visibility of Essentials Regimen PC Perks subSections.		
		storeFrontBrandRefreshHomePage.clickSubSectionUnderRegimen(subSectionLinkPCPerks);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("PC".toLowerCase()), "Expected regimen name is "+"PCPerks".toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());		
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToPCPerksPage(),"user is not on PC Perks page");
		storeFrontBrandRefreshHomePage.navigateToBackPage();

		//Verify Visibility of Essentials Regimen Solution tool subSections.	
		storeFrontBrandRefreshHomePage.clickSubSectionUnderRegimen(subSectionSolutionTool);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("Solution".toLowerCase()), "Expected regimen name is "+"SolutionTool".toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToSolutionToolPage(),"user is not on Solution tool page");
		storeFrontBrandRefreshHomePage.navigateToBackPage();

		//Verify Visibility of Essentials Regimen Digital product catalogue subSections.	
		storeFrontBrandRefreshHomePage.clickSubSectionUnderRegimen(subSectionDigitalProductCatalogue);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("Digimag".toLowerCase()), "Expected regimen name is "+"Digimag".toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserIsRedirectedToDigitalProductCataloguePage(),"user is not on Digital product catalogue page");
		storeFrontBrandRefreshHomePage.navigateToBackPage();
		s_assert.assertAll();
	}

	//Corporate_ FindAConsultant
	@Test(enabled=false)
	public void testCorporateFindAConsultant() throws InterruptedException{
		String expectedURL = "LocatePWS.aspx?fromHome=1";
		storeFrontBrandRefreshHomePage = new StoreFrontBrandRefreshHomePage(driver);
		storeFrontBrandRefreshHomePage.clickConnectWithAConsultant();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentUrlOpenedWindow().contains(expectedURL), "Current url expected is: "+expectedURL+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		s_assert.assertAll();
	}

	//Corporate_BUsinessSystem_CompensationPlan
	@Test(enabled=false)//needs updation
	public void testCorporateBusinessCompensationPlan(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int ssnRandomNum1 = CommonUtils.getRandomNum(100, 999);
		int ssnRandomNum2 = CommonUtils.getRandomNum(00, 99);
		int ssnRandomNum3 = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String emailAddress = firstName+randomNum+"@rodanandfields.com";
		String CID = TestConstantsRFL.CID_CONSULTANT;
		String enrollemntType = "Express";
		String secondSubSectionUnderBusinessSystem = "Programs and Incentives";
		String firstSubSectionUnderProgramsAndIncentives = "Compensation Plan";
		String secondSubSectionUnderProgramsAndIncentives = "Programs and Incentives";

		storeFrontBrandRefreshHomePage.clickBeAConsultantBtn();
		storeFrontBrandRefreshHomePage.clickSubSectionUnderBusinessSystem(secondSubSectionUnderBusinessSystem);
		storeFrontBrandRefreshHomePage.clickSubSectionUnderBusinessSystem(firstSubSectionUnderProgramsAndIncentives);
		//Verify Compensation page
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getSelectedHighlightLinkName().contains("Compensation Plan"), "Expected selected and highlight link name is: "+"Compensation Plan"+" Actual on UI: "+storeFrontBrandRefreshHomePage.getSelectedHighlightLinkName());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("Compensation".toLowerCase()), "Expected url should contain: "+"Compensation"+" Actual on UI: "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshHomePage.clickToReadIncomeDisclosure();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentUrlOpenedWindow().contains("RF-Income-Disclosure-Statement.pdf"),"current url is not a valid and Expected url");
		//Click program and incentives link
		storeFrontBrandRefreshHomePage.clickSubSectionUnderBusinessSystem(secondSubSectionUnderProgramsAndIncentives);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("ProgramsIncentives".toLowerCase()), "Expected url should contain: "+"ProgramsIncentives"+" Actual on UI: "+storeFrontBrandRefreshHomePage.getCurrentURL());
		//click details link under fast start program section
		storeFrontBrandRefreshHomePage.clickDetailsLinkUnderProgramsIncentivePage("Fast Start Program");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentUrlOpenedWindow().contains("Fast_Start_Flyer_2013_Secured.pdf"),"current url is not as expected under detail page of fast start program section page");
		//click details link under Road to RFx car incentive program section
		storeFrontBrandRefreshHomePage.clickDetailsLinkUnderProgramsIncentivePage("Road to RF");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentUrlOpenedWindow().contains("Road-to-RFx-Car-Incentive-Program-Flyer-09.24.11.pdf"),"current url is not as expected under detail page of road to rfx car incentive program section page");
		//click details link under Elite V  program section
		storeFrontBrandRefreshHomePage.clickDetailsLinkUnderProgramsIncentivePage("Elite V Program");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentUrlOpenedWindow().contains("Elite_V_Flyer_Hawaii_2016.pdf"),"current url is not as expected under detail page of Elite V  program section page");
		//click details link under RFx Circle program section
		storeFrontBrandRefreshHomePage.clickDetailsLinkUnderProgramsIncentivePage("RF");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentUrlOpenedWindow().contains("2016_RFx_Circle_TCs.pdf"),"current url is not as expected under detail page of RFx Circle  program section page");
		storeFrontBrandRefreshHomePage.navigateToBackPage();
		storeFrontBrandRefreshHomePage.navigateToBackPage();
		//complete enroll flow.
		storeFrontBrandRefreshHomePage.clickEnrollNowBtnOnBusinessPage();
		storeFrontBrandRefreshHomePage.enterCID(CID);
		storeFrontBrandRefreshHomePage.clickSearchResults();
		storeFrontBrandRefreshHomePage.selectEnrollmentKit(kitName);
		storeFrontBrandRefreshHomePage.selectRegimenAndClickNext(regimen);
		storeFrontBrandRefreshHomePage.selectEnrollmentType(enrollemntType);
		storeFrontBrandRefreshHomePage.enterSetUpAccountInformation(firstName, lastName, emailAddress, password, addressLine1, postalCode, phnNumber1, phnNumber2, phnNumber3);
		storeFrontBrandRefreshHomePage.clickSetUpAccountNextBtn();
		storeFrontBrandRefreshHomePage.enterBillingInformation(cardNumber, nameOnCard, expMonth, expYear);
		storeFrontBrandRefreshHomePage.enterAccountInformation(ssnRandomNum1, ssnRandomNum2, ssnRandomNum3, firstName);
		storeFrontBrandRefreshHomePage.clickCompleteAccountNextBtn();
		storeFrontBrandRefreshHomePage.clickTermsAndConditions();
		storeFrontBrandRefreshHomePage.chargeMyCardAndEnrollMe();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCongratulationsMessageAppeared(),"");
		s_assert.assertAll();
	}

	//Corporate_BUsinessSystem_ IncomeIllustrator  
	@Test(enabled=false)//needs updation
	public void corporateBusinessSystemIncomeIllustrator(){
		String incomeIllustrator = "Income Illustrator";
		storeFrontBrandRefreshHomePage.clickBeAConsultantBtn();
		storeFrontBrandRefreshHomePage.clickSublinkOfBusinessSystem(incomeIllustrator);
		storeFrontBrandRefreshHomePage.clickStartNowBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isStartNowBtnRedirectinToAppropriatePage("IncomeIllustrator/index.html"), "start now btn of income illustrator is not redirecting to IncomeIllustrator/index.html");
		s_assert.assertAll();

	}

	//The Getting Started section Redefine Your Future is displayed
	@Test(enabled=false)
	public void testGettingStartedSectionRedefineYourFutureIsDisplayed(){
		storeFrontBrandRefreshHomePage.clickBeAConsultantBtn();
		//verify the sub-menu title under the title business system?
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateEnrollNowLinkPresent(),"Enroll Now Link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateMeetOurCommunityLinkPresent(),"Meet Our Community is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateEventsLinkPresent(),"Events Link is not present");
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateIncomeIllustratorLinkPresent(),"Income Illustrator Link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateProgramsAndIncentivesLinkPresent(),"ProgramIncentives Link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateWhyRFLinkPresent(),"Why RF Link is not present");
		//Navigate to Redefine Your Future section-
		storeFrontBrandRefreshHomePage.clickWhyRFLinkUnderBusinessSystem();
		storeFrontBrandRefreshHomePage.clickRedefineYourFutureLinkUnderWhyRF();
		//verify url for 'Redefine Your Future'
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateRedefineYourFuturePageDisplayed(),"'Redefine Your Future' Page Is not displayed");
		s_assert.assertAll();
	}

	//The Getting Started Section Business Kit is displayed
	@Test(enabled=false)
	public void testGettingStartedSectionBusinessKitDisplayed(){
		storeFrontBrandRefreshHomePage.clickBeAConsultantBtn();
		//verify the sub-menu title under the title business system?
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateEnrollNowLinkPresent(),"Enroll Now Link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateMeetOurCommunityLinkPresent(),"Meet Our Community is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateEventsLinkPresent(),"Events Link is not present");
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateIncomeIllustratorLinkPresent(),"Income Illustrator Link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateProgramsAndIncentivesLinkPresent(),"ProgramIncentives Link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateWhyRFLinkPresent(),"Why RF Link is not present");
		//Navigate to business kit section-
		storeFrontBrandRefreshHomePage.clickWhyRFLinkUnderBusinessSystem();
		storeFrontBrandRefreshHomePage.clickBusinessKitsUnderWhyRF();
		//verify that the 'Business Kits' Section displays the information?
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateBusinessKitSectionIsDisplayed(),"Business Kit Section is not displayed with the Information");
		s_assert.assertAll();
	}

	//Company Contact us Link should be displayed properly
	@Test(enabled=false)
	public void testCompanyContactUsLink(){
		storeFrontBrandRefreshHomePage.clickAboutRFBtn();
		//verify company Contact Us Link?
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateCompanyContactUsLink(),"Company Contact Us link didn't work");
		s_assert.assertAll();
	}

	//Company Press Room Link should be displayed properly
	@Test(enabled=false)
	public void testCompanyPressRoomLink(){
		storeFrontBrandRefreshHomePage.clickAboutRFBtn();
		//verify company Press Room Link?
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateCompanyPressRoomLink(),"Company Press Room link didn't work");
		s_assert.assertAll();
	}

	//Company Careers Link should be displayed properly
	@Test(enabled=false)
	public void testCompanyCareersLink(){
		storeFrontBrandRefreshHomePage.clickAboutRFBtn();
		//verify company careers Link?
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateCompanyCareersLink(),"Company careers link didn't work");
		s_assert.assertAll();
	}

	//Company Our Story Link should be displayed properly
	@Test(enabled=false)
	public void testCompanyOurStoryLink(){
		storeFrontBrandRefreshHomePage.clickAboutRFBtn();
		//verify Our Story Link?
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateWhoWeAreLink(),"Who We Are link didn't work");
		s_assert.assertAll();
	}

	//Company Execute Link should be displayed properly
	@Test(enabled=false)
	public void testCompanyExecuteTeamLink(){
		storeFrontBrandRefreshHomePage.clickAboutRFBtn();
		//verify Execute Team Link?
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateExecuteTeamLink(),"Execute Team link didn't work");
		s_assert.assertAll();
	}

	//The Getting Started section Enroll Now is displayed
	@Test(enabled=false)//needs updation
	public void testGettingStartedSectionEnrollNowIsDisplayed(){
		storeFrontBrandRefreshHomePage.clickBeAConsultantBtn();
		//verify the sub-menu title under the title business system?
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateEnrollNowLinkPresent(),"Enroll Now Link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateMeetOurCommunityLinkPresent(),"Meet Our Link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateEventsLinkPresent(),"Events Link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateIncomeIllustratorLinkPresent(),"Income Illustrator Link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateProgramsAndIncentivesLinkPresent(),"ProgramIncentives Link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateWhyRFLinkPresent(),"Why RF Link is not present");
		//Navigate to Redefine Your Future section-
		storeFrontBrandRefreshHomePage.clickWhyRFLinkUnderBusinessSystem();
		storeFrontBrandRefreshHomePage.clickEnrollNowLinkUnderWhyRF();
		//verify url for 'Enroll Now'
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateSearchSponsorPageDisplayed(),"'Search Sponsor' Page Is not displayed");
		s_assert.assertAll();
	}

	//QTest ID TC-370 Disclaimer-link should be redirecting properly
	@Test
	public void testDisclaimerLinkShouldBeRedirectedProperly_370(){
		//verify Disclaimer in footer should redirect properly?
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateDisclaimerLinkInFooter(),"'Disclaimer Link' doesn't redirect to disclaimer page");
		s_assert.assertAll();
	}

	//Company-PFC Foundation link should be redirecting properly 
	@Test(enabled=false)//needs updation
	public void testCompanyPFCFoundationLinkShouldRedirectProperly(){
		storeFrontBrandRefreshHomePage.clickAboutRFBtn();
		//verify company PFC Foundation link?
		storeFrontBrandRefreshHomePage.clickGivingBackLinkUnderAboutRF();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateCompanyPFCFoundationPageDisplayed(),"'Company PFC Foundation' page is not displayed");
		s_assert.assertAll();
	}

	//QTest ID TC-392 Corporate_ R+FInTheNews
	@Test(enabled=true)
	public void testCorporateRFInTheNews_392(){
		String expectedURL = "Company/PR";
		//storeFrontBrandRefreshHomePage.mouseHoverAboutRFAndClickLink("Press Room");
		storeFrontBrandRefreshHomePage.clickCompanyPressRoomLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURL), "Current url expected is: "+expectedURL+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		s_assert.assertAll();
	}

	//TC-696 Shopping bag icon appear on all sites regardless product added or not(corp,com,pws)
	@Test(enabled=true)
	public void ShoppingBagIconAppearOnAllSitesRegardlessProductAddedOrNot_696() throws InterruptedException {
		String regimen=TestConstantsRFL.REGIMEN_NAME_REDEFINE;
		String regimen1=TestConstantsRFL.REGIMEN_NAME_REVERSE;
		Boolean isPWSSiteActive=false;
		//corp site
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyShoppingBagAppears(), "shopping bag doesnot appear");
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getNumberOfItemsFromShoppingBag().contains("1"), "No. of items is not updated");
		logger.info("1 product is successfully added to the cart");
		//update qty to 3 of the first product
		storeFrontBrandRefreshHomePage.addQuantityOfProduct("3"); 
		storeFrontBrandRefreshHomePage.clickContinueShoppingButton();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen1);
		storeFrontBrandRefreshHomePage.clickAddToCartBtn();
		storeFrontBrandRefreshHomePage.clickMyShoppingBagLink();
		openBIZSite();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyShoppingBagAppears(), "shopping bag doesnot appear");

		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonAfterLogin();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getNumberOfItemsFromShoppingBag().contains("1"), "No. of items is not updated");
		logger.info("1 product is successfully added to the cart");
		//update qty to 3 of the first product
		storeFrontBrandRefreshHomePage.addQuantityOfProduct("3"); 
		storeFrontBrandRefreshHomePage.clickContinueShoppingButton();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen1);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonAfterLogin();
		storeFrontBrandRefreshHomePage.clickMyShoppingBagLink();
		openCOMSite();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyShoppingBagAppears(), "shopping bag doesnot appear");
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonAfterLogin();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getNumberOfItemsFromShoppingBag().contains("1"), "No. of items is not updated");
		logger.info("1 product is successfully added to the cart");
		//update qty to 3 of the first product
		storeFrontBrandRefreshHomePage.addQuantityOfProduct("3"); 
		storeFrontBrandRefreshHomePage.clickContinueShoppingButton();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen1);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonAfterLogin();
		storeFrontBrandRefreshHomePage.clickMyShoppingBagLink();		
		s_assert.assertAll();
	}

	//*TC-697 Login user should able to view Shopping bag icon appear on all sites regardless product added or not(corp,com,pws)
	@Test(enabled=true)
	public void LoginUserShouldAbleToViewShoppingBagIconAppearOnAllSitesRegardlessProductAddedOrNot_697() throws InterruptedException {
		String regimen=TestConstantsRFL.REGIMEN_NAME_REDEFINE;
		String regimen1=TestConstantsRFL.REGIMEN_NAME_REVERSE;
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyShoppingBagAppears(), "shopping bag doesnot appear");
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getNumberOfItemsFromShoppingBag().contains("1"), "No. of items is not updated");
		logger.info("1 product is successfully added to the cart");
		//update qty to 3 of the first product
		storeFrontBrandRefreshHomePage.addQuantityOfProduct("3"); 
		storeFrontBrandRefreshHomePage.clickContinueShoppingButton();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen1);
		storeFrontBrandRefreshHomePage.clickAddToCartBtn();
		storeFrontBrandRefreshHomePage.clickMyShoppingBagLink();
		openBIZSite();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyShoppingBagAppears(), "shopping bag doesnot appear");
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonAfterLogin();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getNumberOfItemsFromShoppingBag().contains("1"), "No. of items is not updated");
		logger.info("1 product is successfully added to the cart");
		//update qty to 3 of the first product
		storeFrontBrandRefreshHomePage.addQuantityOfProduct("3"); 
		storeFrontBrandRefreshHomePage.clickContinueShoppingButton();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen1);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonAfterLogin();
		storeFrontBrandRefreshHomePage.clickMyShoppingBagLink();
		openCOMSite();
		String pcEmailId =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailId,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyShoppingBagAppears(), "shopping bag doesnot appear");
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonAfterLogin();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getNumberOfItemsFromShoppingBag().contains("1"), "No. of items is not updated");
		storeFrontBrandRefreshHomePage.addQuantityOfProduct("3"); 
		storeFrontBrandRefreshHomePage.clickContinueShoppingButton();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen1);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonAfterLogin();
		storeFrontBrandRefreshHomePage.clickMyShoppingBagLink();		
		s_assert.assertAll();
	}

	//*TC-702 Shopping bag shouldn't show number after purchasing product
	@Test(enabled=true)
	public void ShoppingBagShouldnotShowNumberAfterPurchasingProduct_702() throws InterruptedException {
		String regimen=TestConstantsRFL.REGIMEN_NAME_REDEFINE;
		String rcEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_RC);
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME+randomNumber;
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartBtn();
		storeFrontBrandRefreshHomePage.clickMyShoppingBagLink();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.loginAsRCUserCorpSite(rcEmailID, password);
		storeFrontBrandRefreshHomePage.clickContinueWithoutConsultantLink();
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Enrollment is not completed successfully");
		storeFrontBrandRefreshHomePage.clickOnRodanAndFieldsLogo();
		//verify that the shopping bag doesnot show the number
		s_assert.assertFalse(storeFrontBrandRefreshHomePage.verifyShoppingBagDoesnotShowNumber(), "Cart is not empty");
		//verify that the shopping cart doesnot contain any product
		s_assert.assertFalse(storeFrontBrandRefreshHomePage.verifyIfProductIsPresent(), "product is not present");
		logger.info("Cart is empty");
		s_assert.assertAll(); 
	}

	/***
	 * Test Summary:- placed an adhoc order with consultant only buy event support pack
	 *  and validate orders details like subtotal & product name from biz site
	 */
	//*QTest ID TC-357 Consultants Only -buy event support pack 
	@Test(enabled=true)
	public void testConsultantsOnlyBuyEventSupportPack_357(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME+randomNumber;
		String subtotalFromCart = null;
		String productNameFromCart = null;
		String subtotalFromOrderConfirmation = null;
		String productNameFromOrderConfirmation = null;
		String consultantEmailID = 	getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		storeFrontBrandRefreshConsultantPage = storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
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
	 * Test Summary:- validate the sublink of About R+F from Corporate site
	 */
	//*QTest ID TC-360 Company-Links should be present (Execute team, our history, carrers, PFC foundation, press room, contact us) 
	@Test(enabled=true)
	public void testVerfiyCompanyLinksOnCorp_360(){
		String whoWeAre = "WHO WE ARE";
		String meetTheDoctors = "MEET THE DOCTORS";
		String executiveTeam = "EXECUTIVE TEAM";
		String givingBack = "GIVING BACK";
		String currentURL = null;

		//verify company careers Link?
		storeFrontBrandRefreshHomePage.mouseHoverAboutRFAndClickLink("Who We Are");
		currentURL = driver.getCurrentUrl();
		s_assert.assertTrue(currentURL.contains("Company"),"Company link didn't work");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isLinkTextPresent(whoWeAre),whoWeAre+" link text is not present");

		// assert meet the doctors link
		storeFrontBrandRefreshHomePage.mouseHoverAboutRFAndClickLink("Meet the Doctors");
		currentURL = driver.getCurrentUrl();
		s_assert.assertTrue(currentURL.contains("meet-the-doctors"),"MeetTheDoctors link didn't work");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isLinkTextPresent(meetTheDoctors),meetTheDoctors+" link text is not present");

		// assert Executive Team link
		storeFrontBrandRefreshHomePage.mouseHoverAboutRFAndClickLink("Executive Team");
		currentURL = driver.getCurrentUrl();
		s_assert.assertTrue(currentURL.contains("Executives"),"Executive Team link didn't work");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isLinkTextPresent(executiveTeam),executiveTeam+" link text is not present");

		// assert Giving Back link
		storeFrontBrandRefreshHomePage.mouseHoverAboutRFAndClickLink("Giving Back");
		currentURL = driver.getCurrentUrl();
		s_assert.assertTrue(currentURL.contains("Giving-Back"),"Giving Back link didn't work");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isLinkTextPresent(givingBack),givingBack+" link text is not present");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate product details before and after placing an order from Corporate site
	 */	
	//*QTest ID TC-2352 Checkout as Consultant
	@Test(enabled=true)
	public void testCheckoutAsConsultant_2352(){
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
		String consultantEmailID = 	getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(), "User is not logged in successfully");
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(category);
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

	//* QTest ID TC-2360 Add Product to the Cart - Checkout as PC on Corporate site
	@Test(enabled=true)
	public void testAddProductToTheCartCheckoutAsPCOnCorp_2360() {
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String pcEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String nameOnCard = firstName;
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
		String regimen = TestConstantsRFL.REGIMEN_NAME_ESSENTIALS;
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.loginAsUserOnCheckoutPage(pcEmailID, password);
		s_assert.assertFalse(storeFrontBrandRefreshHomePage.isSignInButtonPresent(), "PC user not logged in successfully");
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
		orderNumber=storeFrontBrandRefreshHomePage.getOrderNumberFromOrderConfirmationPage();
		subTotal=storeFrontBrandRefreshHomePage.getSubtotalFromOrderConfirmationPage();
		shipping=storeFrontBrandRefreshHomePage.getShippingTotalFromOrderConfirmationPage();
		tax=storeFrontBrandRefreshHomePage.getTaxFromOrderConfirmationPage();
		grandTotal=storeFrontBrandRefreshHomePage.getGrandTotalFromOrderConfirmationPage();
		List<Map<String, Object>> orderList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_ORDER_DETAILS,orderNumber),RFL_DB);
		grandTotalFromDB = String.valueOf(getValueFromQueryResult(orderList, "GrandTotal"));
		s_assert.assertTrue(grandTotalFromDB.contains(grandTotal),"Grand Total from DB is:"+grandTotalFromDB+" different from UI is:"+grandTotal);
		taxFromDB = String.valueOf(getValueFromQueryResult(orderList, "TaxAmountTotal"));
		s_assert.assertTrue(taxFromDB.contains(tax),"Tax from DB is:"+taxFromDB+" different from UI is:"+tax);
		shippingFromDB = String.valueOf(getValueFromQueryResult(orderList, "ShippingTotal"));
		s_assert.assertTrue(shippingFromDB.contains(shipping),"shipping Total from DB is:"+shippingFromDB+" different from UI is:"+shipping);
		subTotalFromDB = String.valueOf(getValueFromQueryResult(orderList, "Subtotal"));
		s_assert.assertTrue(subTotalFromDB.contains(subTotal),"SubTotal from DB is:"+subTotalFromDB+" different from UI is:"+subTotal);
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open BIZ, Login with PC, goto My account and click on Edit order link and verify
	 * Assertions:
	 * Autoship date is changed or not
	 * Confirmation msg at Orders page
	 */
	//*QTest ID TC-2342 PC Perks Delay - 30 days Corp Site
	@Test(enabled=true)
	public void testPCPerksDelay30DaysCorp_2342(){
		String changeMyPCPerksStatus = "Change my PC Perks Status";
		String pcEmailId =getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		storeFrontBrandRefreshPCUserPage = storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailId, password);
		storeFrontBrandRefreshPCUserPage.clickMyAccountLink();
		storeFrontBrandRefreshPCUserPage.clickOrderManagementSublink(changeMyPCPerksStatus);
		storeFrontBrandRefreshPCUserPage.clickDelayPCPerksLink();
		storeFrontBrandRefreshHomePage.clickChangeAutohipDateBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyConfirmationMessage(),"confirmation msg not present as expected");
		storeFrontBrandRefreshHomePage.clickBackToMyAccountBtn();
		storeFrontBrandRefreshHomePage.clickEditOrderLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyConfirmationMessageInOrders(),"Confirmation msg not present at orders page as expected");
		s_assert.assertAll();
	}	

	/**
	 * Test Summary: Login with PC User, goto My account and click on Edit order link, enter new shipping  address and verify
	 * Assertions:
	 * Newly added shipping address is present
	 */
	//*QTest ID TC-2344  PC Perks Template - Shipping Address (Corp Site)
	@Test(enabled=true)
	public void testPCPerksTemplateShippingAddressUpdateOnCorpSite_2344(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String addressName = "Home";
		String shippingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME+randomNumber;
		String shippingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME;
		String pcEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);

		storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailId,password);
		storeFrontBrandRefreshHomePage.clickMyAccountLink();
		storeFrontBrandRefreshHomePage.clickEditOrderLink();
		storeFrontBrandRefreshHomePage.clickChangeLinkUnderShippingToOnPWS();
		storeFrontBrandRefreshHomePage.enterShippingProfileDetailsForPWS(addressName, shippingProfileFirstName, shippingProfileLastName, addressLine1, postalCode, phnNumber);
		storeFrontBrandRefreshHomePage.clickUseThisAddressShippingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getShippingAddressName().contains(shippingProfileFirstName),"Shipping address name expected is "+shippingProfileFirstName+" While on UI is "+storeFrontBrandRefreshHomePage.getShippingAddressName());
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate RF Connection Link From Corporate Site
	 * Assertions:
	 * Page URL should contain 'rfconnection'
	 * RF Connection should be visible on page Header
	 */	
	//*QTest ID TC-2332 RF Connection From Corp Site
	@Test(enabled=true)
	public void testRFConnectionFromCorp_2332(){
		String rfConnection="RF Connection";
		String currentURL=null;
		storeFrontBrandRefreshHomePage.clickBottomPageCategoryLink(rfConnection);
		currentURL=storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.contains("rfconnection"),"Expected URL should contain 'rfconnection' But actual on UI is: "+currentURL);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isRFConnectionPageHeaderVisible()," User is not redirected to RF Connection Page");
		s_assert.assertAll();
	}


	/***
	 * Test Summary:- validate DERM RF Link From Corporate Site
	 * Assertions:
	 * Page URL should contain 'dermrf'
	 * DERM RF should be visible on page Header
	 */	
	//*QTest ID TC-2333 Derm Connection from Corporate Site
	@Test(enabled=true)
	public void testDermConnectionFromCorp_2333(){
		String dermRF="Derm RF";
		String currentURL=null;
		storeFrontBrandRefreshHomePage.clickBottomPageCategoryLink(dermRF);
		currentURL=storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.contains("dermrf"),"Expected URL should contain 'dermrf' But actual on UI is: "+currentURL);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isDERMRFPageHeaderVisible()," User is not redirected to DERM RF Page");
		s_assert.assertAll();
	}


	/***
	 * Test Summary:- validate Shipping Link From Corporate Site
	 * Assertions:
	 * Page URL should contain 'shipping'
	 * Shipping should be visible on page Header
	 */	
	//*QTest ID TC-2336 Shipping from Corporate Site
	@Test(enabled=true)
	public void testShippingFromCorp_2336(){
		String shipping="Shipping";
		String currentURL=null;
		storeFrontBrandRefreshHomePage.clickBottomPageCategoryLink(shipping);
		currentURL=storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.contains("shipping"),"Expected URL should contain 'shipping' But actual on UI is: "+currentURL);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isShippingPageHeaderVisible()," User is not redirected to Shipping Page");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate California Supply Chains Act Link From Corporate Site
	 * Assertions:
	 * Page URL should contain 'california-supply-chains-act'
	 * 'CALIFORNIA TRANSPARENCY IN SUPPLY CHAINS ACT' should be visible on page Header
	 */	
	//*QTest ID TC-2338 California Supply Chains Act from Corporate Site
	@Test(enabled=true)
	public void testCaliforniaSupplyChainsActFromCorp_2338(){
		String califSupplyChainsAct="California Supply Chains Act";
		String currentURL=null;
		storeFrontBrandRefreshHomePage.clickBottomPageCategoryLink(califSupplyChainsAct);
		currentURL=storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.contains("california-supply-chains-act"),"Expected URL should contain 'california-supply-chains-act' But actual on UI is: "+currentURL);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCaliforniaSupplyChainsActPageHeaderVisible()," User is not redirected to 'California Supply Chains' Act Page");
		s_assert.assertAll();
	}

	//* QTest ID TC-361 About R+F > Execute team link should be displayed properly on Corporate site
	@Test(enabled=true)
	public void AboutRFExecuteTeamLinkShouldBeDisplayedProperly_361() {
		storeFrontBrandRefreshHomePage.mouseHoverAboutRFAndClickLink("Executive Team");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateExecutiveTeamLinkPresent(), "Executive team link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateExecuteTeamLink(), "Not navigated to Executive team link");
	}

	//* QTest ID TC-363 Carrers on Corporate site
	@Test(enabled=true)
	public void Carrers_363() {
		storeFrontBrandRefreshHomePage.clickCareersLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateCareersLinkPresent(), "career link is not present");
	}

	//* QTest ID TC-379 BecomeAConsultantProgramsIncentivesCompensationPlanHereLink on Corporate site
	@Test(enabled=true)
	public void BecomeAConsultantProgramsIncentivesCompensationPlanHereLink_379() {		
		//storeFrontBrandRefreshHomePage.val
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Programs and Incentives");
		storeFrontBrandRefreshHomePage.clickCompensationPlanHereLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentUrlOpenedWindow().contains("Comp-PlanForUS_.pdf"), "compansation plan link is not clicked");
		storeFrontBrandRefreshHomePage.clickToReadIncomeDisclosure();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentUrlOpenedWindow().contains("Income-Disclosure-Statement.pdf"), "Income disclosure plan link is not clicked");
	}

	//* QTest ID TC-383 The Events section Leadership Summit is displayed Corporate site
	@Test(enabled=true)
	public void TheEventsSectionLeadershipSummitIsDisplayed_383() {	
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Events");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyBusinessPresentationSectionUnderEvents(), "Business Presentation Section is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyRFConventionSectionUnderEvents(), "RF Convention Section is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyBussinessRedefinedLearningSectionUnderEvents(), "Bussiness Redefined Learning Section is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyConsultantLedEventsSectionUnderEvents(), "Consultant Led Events Section is not present");
		storeFrontBrandRefreshHomePage.clickBusinessPresentationEventsCalendarLink();
		s_assert.assertEquals(storeFrontBrandRefreshHomePage.verifyTextCalendarPageisDisplyed(), "calendar page is not displayed");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.eventsCalendarDetails(), "event calendar details is not present");
	}

	//* QTest ID TC-381 Corp> Programs And Incentives Page displaying "Enroll Now" button
	@Test(enabled=false)//fix
	public void testCorpProgramsAndIncentivesPageDisplayingEnrollNowButton_381() {		
		String expectedURL="SearchSponsor";
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Programs and Incentives");
		storeFrontBrandRefreshHomePage.clickEnrollNowUnderStartYourJourney();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURL), "Current url expected is: "+expectedURL+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		s_assert.assertAll();
	}

	//* QTest ID TC-382 Corp> Become a consultant user > Program And Incentives > RF Events link
	@Test(enabled=true)
	public void testBecomeAConsultantUserProgramAndIncentivesRFEventsLink_382() {
		String expectedURL="Events";
		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Programs and Incentives");
		storeFrontBrandRefreshHomePage.clickRFEventslinkUnderLearningAndLeadershipSection();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURL), "Current url expected is: "+expectedURL+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
	}

	//* QTest ID TC-390 Corp> Become A Consultant > Meet Our Community
	@Test(enabled=true)
	public void testBecomeAConsultantMeetOurCommunity_390() {
		String fb="Facebook" ;
		String twitter="Twitter" ;
		String insta="Instagram" ;
		String dermrf="DermRF" ;
		String pinterest="Pinterest" ;
		String youtube="YouTube" ;

		String expectedURL="SearchSponsor";
		String expectedURLFB="facebook";
		String expectedURLtwitter="twitter";
		String expectedURLinstagram="instagram";
		String expectedURLdermrf="dermrf";
		String expectedURLpinterest="pinterest";
		String expectedURLyoutube="youtube";

		storeFrontBrandRefreshHomePage.mouseHoverBeAConsultantAndClickLink("Meet Our Community");
		storeFrontBrandRefreshHomePage.verifyMeetOurCommunityPage();
		storeFrontBrandRefreshHomePage.clickOnReadTheirInspiringStoriesLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyReadTheirInspiringStoriespage(), "read their inspiring stories page is not displayed");
		storeFrontBrandRefreshHomePage.clickEnrollNowBtnOnMeetOurCommunityPage();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURL), "Current url expected is: "+expectedURL+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshHomePage.closeChildSwitchToParentWindow();
		storeFrontBrandRefreshHomePage.clickLinksOnMeetourCommunityPage(fb);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURLFB), "Current url expected is: "+expectedURLFB+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshHomePage.closeChildSwitchToParentWindow();
		storeFrontBrandRefreshHomePage.clickLinksOnMeetourCommunityPage(twitter);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURLtwitter), "Current url expected is: "+expectedURLtwitter+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshHomePage.closeChildSwitchToParentWindow();
		storeFrontBrandRefreshHomePage.clickLinksOnMeetourCommunityPage(insta);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURLinstagram), "Current url expected is: "+expectedURLinstagram+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshHomePage.closeChildSwitchToParentWindow();
		storeFrontBrandRefreshHomePage.clickLinksOnMeetourCommunityPage(dermrf);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURLdermrf), "Current url expected is: "+expectedURLdermrf+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshHomePage.closeChildSwitchToParentWindow();
		storeFrontBrandRefreshHomePage.clickLinksOnMeetourCommunityPage(pinterest);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURLpinterest), "Current url expected is: "+expectedURLpinterest+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshHomePage.closeChildSwitchToParentWindow();
		storeFrontBrandRefreshHomePage.clickLinksOnMeetourCommunityPage(youtube);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURLyoutube), "Current url expected is: "+expectedURLyoutube+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
	}

	//QTest ID TC-2438 CORP > Forgot Password
	@Test
	public void testCORPForgotPassword_2438(){
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		storeFrontBrandRefreshHomePage.clickForgotPasswordLinkOnBizHomePage();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateChangePasswordMessagePrompt(),"Message not prompted for 'change password'");
		storeFrontBrandRefreshHomePage.enterEmailAddressToRecoverPassword(consultantEmailID);
		storeFrontBrandRefreshHomePage.clickSendEmailButton();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validatePasswordChangeAndEmailSent(),"resetting your password mail is not displayed!!");
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open Corp, Click on Country Toggle Select AUS and verify
	 * Assertions:
	 * AUS Should be present in Country Toggle
	 * User should redirected to AUS Country after clicking on 'AUS' from country toggle
	 */
	//QTest ID TC-925 Corp> Verify the User is redirected to the Corporate Australian Home Page
	@Test
	public void testVerifyTheUserIsRedirectedToTheCorporateAustralianHomePageCorp_925(){
		String countryName="AUS";
		String url="rodanandfields.com.au";
		String currentUrl=null;

		storeFrontBrandRefreshHomePage.clickCountryToggle();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCountryPresentInCountryToggle(countryName)," Expected country: "+countryName+" not present in country toggle");
		storeFrontBrandRefreshHomePage.selectCountry(countryName);
		currentUrl=storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(url),"User is not redirected to expected country:"+countryName+" Url should contains: "+url+" But actual on UI is: "+currentUrl);
		s_assert.assertAll();
	}


	/**
	 * Test Summary: Open Corp, click WHY R+F link , click Enroll now button and verify search sponsor page
	 * Assertions:
	 * verify search sponsor page is getting displayed
	 */
	//QTest ID TC-391 Corporate> Why R+F
	@Test
	public void testCorporateWhyRF_391(){
		storeFrontBrandRefreshHomePage.clickBeAConsultantBtn();
		storeFrontBrandRefreshHomePage.clickWhyRFLinkUnderBusinessSystem();
		storeFrontBrandRefreshHomePage.clickEnrollNowBtnOnWhyRFPage();
		//verify url for 'Enroll Now'
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateSearchSponsorPageDisplayed(),"'Search Sponsor' Page Is not displayed");
		s_assert.assertAll();
	}

	//QTest ID TC-2429 Login as user and check the You may also Enjoy These Products Section Visible
	@Test(enabled=false)//Should not be a part of automation
	public void testLoginAsUserAndCheckTheYouMayAlsoEnjoyTheseProductsSectionVisible_2429(){
		String rcEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_RC);
		//corp site
		storeFrontBrandRefreshHomePage.loginAsRCUser(rcEmailID, password);
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isYouMayAlsoFindTheseProductsSectionVisible(), "you may also find this section is not present");
		openBIZSite();
		storeFrontBrandRefreshHomePage.loginAsRCUser(rcEmailID, password);
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isYouMayAlsoFindTheseProductsSectionVisibleBiz(), "you may also find this section is not present");
		openCOMSite();
		storeFrontBrandRefreshHomePage.loginAsRCUser(rcEmailID, password);
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isYouMayAlsoFindTheseProductsSectionVisibleBiz(), "you may also find this section is not present");
		s_assert.assertAll();
	}

	//QTest ID TC-2428 selects a product from the carousel System will direct the user to the product's PDP & Opens in same tab(corp, biz)
	@Test
	public void testSelectsAProductFromTheCarouselSystemWillDirectTheUserToTheProductPDPAndOpensInSameTab_2428(){
		String prodname= null;
		String prodname1=null;
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		storeFrontBrandRefreshHomePage.isYouMayAlsoFindTheseProductsSectionVisible();
		prodname=storeFrontBrandRefreshHomePage.selectProductFromCarouselcorp();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isUserRedirectedToCarouselProductPDPcorp(prodname),"user not redirected to products PDP page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCarouselProductOpenedInTheSameTab(),"Carousel product page not opened");
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isYouMayAlsoFindTheseProductsSectionVisibleBiz(), "you may also find this section is not present");
		prodname1=storeFrontBrandRefreshHomePage.selectProductFromCarousel();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isUserRedirectedToCarouselProductPDP(prodname1),"user not redirected to products PDP page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCarouselProductOpenedInTheSameTab(),"Carousel product page not opened");
		openCOMSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isYouMayAlsoFindTheseProductsSectionVisibleBiz(), "you may also find this section is not present");
		prodname1=storeFrontBrandRefreshHomePage.selectProductFromCarousel();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isUserRedirectedToCarouselProductPDP(prodname1),"user not redirected to products PDP page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCarouselProductOpenedInTheSameTab(),"Carousel product page not opened");
		s_assert.assertAll();
	}

	//Consultants Only - buy business promotion 
	@Test(enabled=false)//test no longer valid
	public void testConsultantsOnlyBuyBusinessPromotion(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME+randomNumber;
		String consultantEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openCOMSite();
		storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink("Promotions");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("promotions"), "Expected regimen name is: promotions Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshHomePage.clickAddToCartBtn();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Order is not placed successfully");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getOrderConfirmationTextMsgAfterOrderPlaced().contains("You will receive an email confirmation shortly"), "Order confirmation message does not contains email confirmation");
		s_assert.assertAll();
	}

	//Consultants Only -buy event support pack 
	@Test(enabled=false)//needs updation
	public void consultantsOnlyBuyEventSupportPack(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME+randomNumber;
		String consultantEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		storeFrontBrandRefreshConsultantPage = storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshConsultantPage.mouseHoverShopSkinCareAndClickLink("CONSULTANT-ONLY PRODUCTS");
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getCurrentURL().toLowerCase().contains("consultantsonly"), "Expected regimen name is: consultantsonly Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshConsultantPage.clickConsultantOnlyProduct(TestConstantsRFL.CONSULTANT_ONLY_EVENT_SUPPORT);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("event"), "Expected url contains is: Event but Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshConsultantPage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		storeFrontBrandRefreshConsultantPage.clickCheckoutBtn();
		storeFrontBrandRefreshConsultantPage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshConsultantPage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.isThankYouTextPresentAfterOrderPlaced(), "Order is not placed successfully");
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getOrderConfirmationTextMsgAfterOrderPlaced().contains("You will receive an email confirmation shortly"), "Order confirmation message does not contains email confirmation");
		s_assert.assertAll();
	}

	//QTest ID TC-358 Consultants Only -buy product promotion 
	@Test(enabled=true)//needs updation
	public void testconsultantsOnlyBuyProductPromotion_358(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME+randomNumber;
		String consultantEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		storeFrontBrandRefreshConsultantPage = storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshConsultantPage.mouseHoverShopSkinCareAndClickLink("CONSULTANT-ONLY PRODUCTS");
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getCurrentURL().toLowerCase().contains("consultantsonly"), "Expected regimen name is: consultantsonly Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshConsultantPage.clickConsultantOnlyProduct(TestConstantsRFL.CONSULTANT_ONLY_PRODUCT_PROMOTION);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("promotion"), "Expected url contains is: promotion but Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshConsultantPage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		storeFrontBrandRefreshConsultantPage.clickCheckoutBtn();
		storeFrontBrandRefreshConsultantPage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshConsultantPage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.isThankYouTextPresentAfterOrderPlaced(), "Order is not placed successfully");
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getOrderConfirmationTextMsgAfterOrderPlaced().contains("You will receive an email confirmation shortly"), "Order confirmation message does not contains email confirmation");
		s_assert.assertAll();
	}

	//The Events section Upcoming events is displayed
	@Test(enabled=false)
	public void testEventsSectionUpcomingEventsIsDisplayed(){		
		storeFrontBrandRefreshHomePage.clickBeAConsultantBtn();
		//verify the sub-menu title under the title business system?
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateEnrollNowLinkPresent(),"Enroll Now Link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateMeetOurCommunityLinkPresent(),"Meet Our Link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateEventsLinkPresent(),"Events Link is not present");
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateIncomeIllustratorLinkPresent(),"Income Illustrator Link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateProgramsAndIncentivesLinkPresent(),"ProgramIncentives Link is not present");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateWhyRFLinkPresent(),"Why RF Link is not present");
		//Navigate to Events Section
		storeFrontBrandRefreshHomePage.clickEventsLinkUnderBusinessSystem();
		//verify UPCOMING EVENTS is displayed?
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateUpcomingEventsLinkIsPresent(),"Upcoming Events Link is not present");
		s_assert.assertAll();
	}

	//QTest ID TC-510 Add Product to the Cart - Checkout as PC
	@Test(priority=1)
	public void testPCAdhocOrderFromCorp_510(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String postalCode = TestConstantsRFL.POSTAL_CODE;
		String cardNumber = TestConstantsRFL.CARD_NUMBER;
		String nameOnCard = firstName;
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String pcEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		String regimen = TestConstantsRFL.REGIMEN_NAME_REVERSE;
		storeFrontBrandRefreshHomePage.clickShopSkinCareHeader();
		storeFrontBrandRefreshHomePage.selectRegimen(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartBtn();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.loginAsUserOnCheckoutPage(pcEmailID, password);
		s_assert.assertFalse(storeFrontBrandRefreshHomePage.isSignInButtonPresent(), "PC user not logged in successfully");
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		storeFrontBrandRefreshHomePage.clickOKBtnOnPopup();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Adhoc order not placed successfully from corp site.");
		s_assert.assertAll();
	}

	//QTest ID TC-508 Adhoc order - RC corp
	@Test(priority=2)
	public void testPlaceAdhocOrderFromCorpSiteForRC_508(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String postalCode = TestConstantsRFL.POSTAL_CODE;
		String cardNumber = TestConstantsRFL.CARD_NUMBER;
		String nameOnCard = firstName;
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String rcEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_RC);
		//  storeFrontBrandRefreshHomePage.clickShopSkinCareHeader();
		//  storeFrontBrandRefreshHomePage.selectRegimen(regimen);
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
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Adhoc order not placed successfully from com site for RC user.");
		s_assert.assertAll();
	}

	//QTest ID TC-2479 Adhoc Order with Newly created billing address
	//QTest ID TC-509 Adhoc Order - Consultant Only Products
	@Test(priority=3)
	public void AdhocOrderConsultantsOnlyProducts_509_2479(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String postalCode = TestConstantsRFL.POSTAL_CODE;
		String cardNumber = TestConstantsRFL.CARD_NUMBER;
		String nameOnCard = firstName;
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String consultantEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		storeFrontBrandRefreshConsultantPage = storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshConsultantPage.mouseHoverShopSkinCareAndClickLink("CONSULTANT-ONLY PRODUCTS");
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getCurrentURL().toLowerCase().contains("consultantsonly"), "Expected regimen name is: consultantsonly Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshConsultantPage.clickConsultantOnlyProduct(TestConstantsRFL.CONSULTANT_ONLY_BUSINESS_PROMOTION);
		storeFrontBrandRefreshConsultantPage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		storeFrontBrandRefreshConsultantPage.clickCheckoutBtn();
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

	//PC Adhoc Order – Adding new billing profile with existing user
	@Test
	public void testAddNewBillingProfileDuringPCAdhocOrderFromCorp(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String postalCode = TestConstantsRFL.POSTAL_CODE;
		String cardNumber = TestConstantsRFL.CARD_NUMBER;
		String nameOnCard = firstName;
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME+randomNumber;
		String pcEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		String regimen = TestConstantsRFL.REGIMEN_NAME_REVERSE;
		//storeFrontBrandRefreshHomePage.clickShopSkinCareHeader();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		storeFrontBrandRefreshHomePage.selectRegimen(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartBtn();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.loginAsUserOnCheckoutPage(pcEmailID, password);
		s_assert.assertFalse(storeFrontBrandRefreshHomePage.isSignInButtonPresent(), "PC user not logged in successfully");
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getBillingAddressName().contains(billingProfileFirstName), "Newly created billing profile is not selected for new order.");
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		storeFrontBrandRefreshHomePage.clickOKBtnOnPopup();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Adhoc order not placed successfully from corp site.");
		s_assert.assertAll();
	}

	//PC Adhoc Order – with existing billing profile
	@Test
	public void testPCAdhocOrderFromCorpWithExistingBillingProfile(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String pcEmailID = null;
		String firstName = TestConstantsRFL.FIRST_NAME;
		String cardNumber = TestConstantsRFL.CARD_NUMBER_SECOND;
		String nameOnCard = firstName;
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME+randomNumber;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME;
		pcEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		String regimen = TestConstantsRFL.REGIMEN_NAME_REVERSE;
		//storeFrontBrandRefreshHomePage.clickShopSkinCareHeader();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		storeFrontBrandRefreshHomePage.selectRegimen(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartBtn();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.loginAsUserOnCheckoutPage(pcEmailID, password);
		s_assert.assertFalse(storeFrontBrandRefreshHomePage.isSignInButtonPresent(), "PC user not logged in successfully");
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getBillingAddressName().contains(billingProfileFirstName)||storeFrontBrandRefreshHomePage.getBillingAddress().contains(addressLine1), "Existing billing profile is not selected for new order.");
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		storeFrontBrandRefreshHomePage.clickOKBtnOnPopup();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Adhoc order not placed successfully from corp site.");
		s_assert.assertAll();
	}

	//Variant Product Add to Ad-hoc and Autoship cart From Category Page for Consultant 
	@Test
	public void testVariantProductFromCategoryForConsultant(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		int productPosition=0;
		String parentWindowHandle=null;
		String productName = TestConstantsRFL.VARIANT_PRODUCT_NAME;
		String fullProductName = TestConstantsRFL.VARIANT_PRODUCT_FULL_NAME;
		String shade = "Medium";
		String firstName = TestConstantsRFL.FIRST_NAME;
		String nameOnCard = firstName;
		String expMonth = TestConstantsRFL.EXP_MONTH;
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		boolean flag=false;
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		storeFrontBrandRefreshConsultantPage = storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		String regimen = TestConstantsRFL.REGIMEN_NAME_ENHANCEMENTS;
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(regimen.toLowerCase()), "Expected regimen name is "+regimen.toLowerCase()+" Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshHomePage.selectVariantOfProduct(fullProductName, shade);
		storeFrontBrandRefreshHomePage.addProductToAdHocCart(productName);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isProductAvailableInCart(productName),"Variant Product is not available on Ad-hoc cart page");
		storeFrontBrandRefreshConsultantPage.clickCheckoutBtn();
		storeFrontBrandRefreshConsultantPage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshConsultantPage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.isThankYouTextPresentAfterOrderPlaced(), "Order is not placed successfully");
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getOrderConfirmationTextMsgAfterOrderPlaced().contains("You will receive an email confirmation shortly"), "Order confirmation message does not contains email confirmation");
		storeFrontBrandRefreshHomePage.clickOnRodanAndFieldsLogo();
		storeFrontBrandRefreshPulsePage =storeFrontBrandRefreshHomePage.clickCheckMyPulse();
		storeFrontBrandRefreshPulsePage.dismissPulsePopup();
		parentWindowHandle=storeFrontBrandRefreshPulsePage.getParentWindowHandle();
		storeFrontBrandRefreshPulsePage.clickMyAccountLinkOnPulsePage();
		storeFrontBrandRefreshPulsePage.clickEditCRP();
		flag=storeFrontBrandRefreshPulsePage.isProductPresentOnCRPOverViewPage(fullProductName);
		if(flag){
			productPosition=storeFrontBrandRefreshPulsePage.getPositionOfProductInCRPOverViewPage(fullProductName);
			storeFrontBrandRefreshPulsePage.clickEditReplenishmentOrder();
			storeFrontBrandRefreshPulsePage.removeProductFromAutoshipCart(productPosition);
			storeFrontBrandRefreshPulsePage.clickUpdateOrder();
		}
		s_assert.assertFalse(storeFrontBrandRefreshPulsePage.isProductPresentOnCRPOverViewPage(fullProductName),"Expected Product "+fullProductName+" still present at CRP Overview Page");
		storeFrontBrandRefreshPulsePage.clickEditReplenishmentOrder();
		storeFrontBrandRefreshPulsePage.clickLinksInHeader(regimen);
		storeFrontBrandRefreshPulsePage.selectVariantOfAProduct(fullProductName, shade);
		storeFrontBrandRefreshPulsePage.addProductToAutoshipCart(fullProductName);
		storeFrontBrandRefreshPulsePage.clickUpdateOrder();
		s_assert.assertTrue(storeFrontBrandRefreshPulsePage.isProductPresentOnCRPOverViewPage(fullProductName),"Expected Product "+fullProductName+" is not present at CRP Overview Page");
		storeFrontBrandRefreshPulsePage.closeChildAndSwitchToParentWindow(parentWindowHandle);
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- Open corp url, Login with PC, start to place an adhoc order, add billing profile with INVALID credit card and place the order
	 * Assertions:
	 * Error message should be displayed for INVALID credit card after complete the enrollment
	 */	
	//QTest ID TC-1944 Payment error message - CORP
	@Test
	public void testPaymentErrorMessageWhilePlacingAnAdhocOrderThroughPCFromCorp_1944(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String cardNumber = TestConstantsRFL.INVALID_CARD_NUMBER;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String errorMessageFromUI = null;
		String errorMessage = TestConstantsRFL.ERROR_MSG_FOR_INVALID_CC;
		String pcEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		String regimen = TestConstantsRFL.REGIMEN_NAME_REVERSE;
		storeFrontBrandRefreshHomePage.clickShopSkinCareHeader();
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
	 * Test Summary:- Open corp url, Login with RC, start to place an adhoc order, at checkout page select FedEx 1 day and FedEx 2 Day shipping method and verify shipping charges respectively 
	 * Assertions:
	 * Shipping charges for FedEx 1 day and FedEx 2 Day shipping method respectively  
	 */	
	//QTest ID TC-653 Verify 1-2 day shipping ad-hoc US RC order on Corporate site
	@Test
	public void testAdhocOrderConsultantsOnlyProductsFromCorp_653(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String rcEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_RC);
		String shippingMethodFedEx1Day = "FedEx 1 day";
		String shippingMethodFedEx2Day = "FedEx 2 day";
		String shippingChargesForFedEx1Day = "25.00";
		String shippingChargesForFedEx2Day = "15.00";
		String shippingChargesFromUI = null;
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
		storeFrontBrandRefreshHomePage.selectShippingMethod(shippingMethodFedEx1Day);
		shippingChargesFromUI = storeFrontBrandRefreshHomePage.getShippingTotalFromOrderConfirmationPage();
		s_assert.assertTrue(shippingChargesFromUI.contains(shippingChargesForFedEx1Day), "Expected shipping charges are"+shippingChargesForFedEx1Day+" for shipping method "+shippingMethodFedEx1Day+" but actual on UI is "+shippingChargesFromUI);
		storeFrontBrandRefreshHomePage.selectShippingMethod(shippingChargesForFedEx2Day);
		shippingChargesFromUI = storeFrontBrandRefreshHomePage.getShippingTotalFromOrderConfirmationPage();
		s_assert.assertTrue(shippingChargesFromUI.contains(shippingChargesForFedEx2Day), "Expected shipping charges are"+shippingChargesForFedEx2Day+" for shipping method "+shippingMethodFedEx2Day+" but actual on UI is "+shippingChargesFromUI);
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Adhoc order not placed successfully from com site for RC user.");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- Open corp url, Login with PC, select a product(price<$80) for adhoc, at checkout page verify shipping charges respectively 
	 * Assertions:
	 * verify shipping charges for a product(price<$80)  
	 */	
	//QTest ID TC-669 Verify shipping fee for ad-hoc US PC order < $80
	@Test
	public void testVerifyShippingFeeForAdhocPCOrderLessThan80FromCorp_669(){
		String regimen = TestConstantsRFL.REGIMEN_NAME_ENHANCEMENTS;
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String pcEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);;
		String shippingChargesFromUI = null;
		String shippingCharges = "9.95";
		int productPosition = 0;
		double productPrice = 80.00;
		storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailID, password);
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		productPosition = storeFrontBrandRefreshHomePage.getPositionOfProductForLessPrice(productPrice);
		storeFrontBrandRefreshHomePage.addProductToAdHocCartThroughProductNumber(""+productPosition);
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		shippingChargesFromUI = storeFrontBrandRefreshHomePage.getShippingTotalFromOrderConfirmationPage();
		s_assert.assertTrue(shippingChargesFromUI.equalsIgnoreCase(shippingCharges), "Expected shipping charges are"+shippingCharges+" but actual on UI is "+shippingChargesFromUI);
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		storeFrontBrandRefreshHomePage.clickOKBtnOnPopup();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Adhoc order not placed successfully from corp site.");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- Open corp url, Login with PC, select a product(price>$80) for adhoc, at checkout page verify shipping charges respectively 
	 * Assertions:
	 * verify shipping charges for a product(price>$80)  
	 */	
	//QTest ID TC-668 Verify shipping fee for ad-hoc US PC order > $80
	@Test
	public void testVerifyShippingFeeForAdhocPCOrderMoreThan80FromCorp_668(){
		String regimen = TestConstantsRFL.REGIMEN_NAME_ENHANCEMENTS;
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String pcEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		String shippingChargesFromUI = null;
		String shippingCharges = "FREE";
		int productPosition = 0;
		double productPrice = 80.00;
		storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailID, password);
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		productPosition = storeFrontBrandRefreshHomePage.getPositionOfProductForMorePrice(productPrice);
		storeFrontBrandRefreshHomePage.addProductToAdHocCartThroughProductNumber(""+productPosition);
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		shippingChargesFromUI = storeFrontBrandRefreshHomePage.getShippingTotalFromOrderHistoryPageForPC();
		s_assert.assertTrue(shippingChargesFromUI.equalsIgnoreCase(shippingCharges), "Expected shipping charges are"+shippingCharges+" but actual on UI is "+shippingChargesFromUI);
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		storeFrontBrandRefreshHomePage.clickOKBtnOnPopup();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Adhoc order not placed successfully from corp site.");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- Open corp url, Login with Consultant, select a product(SV price>$100) for adhoc, at checkout page verify shipping charges respectively 
	 * Assertions:
	 * verify shipping charges for a product(SV price>$100)  
	 */	
	//QTest ID TC-671 Verify shipping fee for ad-hoc US Consultant order > $100
	@Test
	public void testVerifyShippingFeeForAdhocConsultantOrderMoreThan100FromCorp_671(){
		int productPosition = 0;
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		double productPrice = 100.00;
		String shippingChargesFromUI = null;
		String shippingCharges = "9.95";
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		storeFrontBrandRefreshConsultantPage = storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		productPosition = storeFrontBrandRefreshHomePage.getPositionOfProductForMoreSVPrice(productPrice);
		storeFrontBrandRefreshHomePage.addProductToAdHocCartThroughProductNumber(""+productPosition);
		storeFrontBrandRefreshConsultantPage.clickCheckoutBtn();
		storeFrontBrandRefreshConsultantPage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		shippingChargesFromUI = storeFrontBrandRefreshHomePage.getShippingTotalFromOrderConfirmationPage();
		s_assert.assertTrue(shippingChargesFromUI.equalsIgnoreCase(shippingCharges), "Expected shipping charges are"+shippingCharges+" but actual on UI is "+shippingChargesFromUI);
		storeFrontBrandRefreshConsultantPage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.isThankYouTextPresentAfterOrderPlaced(), "Order is not placed successfully");
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getOrderConfirmationTextMsgAfterOrderPlaced().contains("You will receive an email confirmation shortly"), "Order confirmation message does not contains email confirmation");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- Open corp url, Login with Consultant, select a product(SV price<$100) for adhoc, at checkout page verify shipping charges respectively 
	 * Assertions:
	 * verify shipping charges for a product(SV price<$100)  
	 */	
	//QTest ID TC-672 Verify shipping fee for ad-hoc US Consultant order < $100
	@Test
	public void testVerifyShippingFeeForAdhocConsultantOrderLessThan100FromCorp_672(){
		int productPosition = 0;
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		double productPrice = 100.00;
		String shippingChargesFromUI = null;
		String shippingCharges = "11.95";
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		storeFrontBrandRefreshConsultantPage = storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		productPosition = storeFrontBrandRefreshHomePage.getPositionOfProductForLessSVPrice(productPrice);
		storeFrontBrandRefreshHomePage.addProductToAdHocCartThroughProductNumber(""+productPosition);
		storeFrontBrandRefreshConsultantPage.clickCheckoutBtn();
		storeFrontBrandRefreshConsultantPage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		shippingChargesFromUI = storeFrontBrandRefreshHomePage.getShippingTotalFromOrderConfirmationPage();
		s_assert.assertTrue(shippingChargesFromUI.equalsIgnoreCase(shippingCharges), "Expected shipping charges are"+shippingCharges+" but actual on UI is "+shippingChargesFromUI);
		storeFrontBrandRefreshConsultantPage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.isThankYouTextPresentAfterOrderPlaced(), "Order is not placed successfully");
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getOrderConfirmationTextMsgAfterOrderPlaced().contains("You will receive an email confirmation shortly"), "Order confirmation message does not contains email confirmation");
		s_assert.assertAll();
	}

	// QTest ID TC-2341  Corp: PC User termination
	@Test 
	public void testPCUserTermination_2341(){
		List<Map<String, Object>> accountStatusIDList= null;
		String statusID= null;
		String pcEmailId = null;
		String changeMyPCPerksStatus = "Change my PC Perks Status";  
		pcEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		storeFrontBrandRefreshPCUserPage = storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailId,password);
		storeFrontBrandRefreshPCUserPage.clickMyAccountLink();
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
		storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailId,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isInvalidLoginPresent(),"Terminated User is able to Login");
		//verify Account status
		accountStatusIDList =  DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_ACCOUNT_STATUS_ID_OF_INACTIVE_PC, pcEmailId), RFL_DB);
		statusID = String.valueOf(getValueFromQueryResult(accountStatusIDList, "StatusID"));
		s_assert.assertTrue(statusID.contains("2"), "Account status is active");
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open Corporate site, Login with consultant, Navigate to 'Consultant only products' and click on 'Consultant only Event support' product, Add donation product to bag and complete place order and verify
	 * Assertions:
	 * Order placed successfully
	 * Verify grandTotal from DB
	 */
	//*QTest ID TC-2379 Donation order 
	@Test(enabled=true)
	public void testDonationOrderFromCorp_2379(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME+randomNumber;
		String consultantEmailID = null;
		List<Map<String, Object>> randomConsultantList =  null;
		List<Map<String, Object>> randomOrderList =  null;
		String grandTotal = null;
		String orderNumber=null;
		String totalAmount=null;

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

	/***
	 * Test Summary:- Open corp url, Login with Consultant, Add a product to cart, Add new shipping address and complete place order and verify
	 * Assertions:
	 * verify Verify that the order was placed properly with newly created shipping profile
	 */ 
	//QTest ID TC-2478 Adhoc Order with Newly created shipping address
	@Test
	public void testAdhocOrderWithNewlyCreatedShippingAddressFromCorp_2478(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String addressName = "Home";
		String shippingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME+randomNumber;
		String shippingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		String shippingProfileName=shippingProfileFirstName+" "+shippingProfileLastName;
		String shippingName=null;
		String shippingAddress=null;

		storeFrontBrandRefreshConsultantPage = storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		storeFrontBrandRefreshConsultantPage.clickCheckoutBtn();
		storeFrontBrandRefreshConsultantPage.clickContinueBtnForPCAndRC();
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
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getOrderConfirmationTextMsgAfterOrderPlaced().contains("You will receive an email confirmation shortly"), "Order confirmation message does not contains email confirmation");
		shippingName=storeFrontBrandRefreshHomePage.getShippingNameFromOrderConfirmationPage();
		s_assert.assertTrue(shippingProfileName.contains(shippingName),"Shipping Profile is not the same as entered");
		shippingAddress=storeFrontBrandRefreshHomePage.getAddressFromOrderConfirmationPage();
		s_assert.assertTrue(shippingAddress.contains(addressLine1.toUpperCase()),"Shipping Address is not the same as entered");
		s_assert.assertAll();
	}
	
	/***
	 * Test Summary:- Open corp url, Login with PC User, and verify
	 * Assertions:
	 * Verify sponsor name on the top of the page
	 */ 
	//QTest ID TC-2151 Sponsor Info_My account
	@Test
	public void testSponsorInfo_MyAccountFromCorp_2151(){
		String pcEmailId =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailId, password);
		storeFrontBrandRefreshHomePage.clickMyAccountLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isSponsorInfoPresentOnTopOfPage(),"Sponsor Info not Available on Top of Page");
		s_assert.assertAll();
	}
	

}