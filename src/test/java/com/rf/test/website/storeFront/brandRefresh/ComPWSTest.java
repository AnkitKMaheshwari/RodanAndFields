package com.rf.test.website.storeFront.brandRefresh;

import java.util.List;
import java.util.Map;
import org.testng.annotations.Test;
import com.rf.core.utils.CommonUtils;
import com.rf.core.utils.DBUtil;
import com.rf.core.website.constants.TestConstantsRFL;
import com.rf.core.website.constants.dbQueries.DBQueries_RFL;
import com.rf.test.website.RFBrandRefreshWebsiteBaseTest;

public class ComPWSTest extends RFBrandRefreshWebsiteBaseTest{

	//QTest ID TC-418 Forgot Password from COM
	@Test
	public void testForgotPasswordPWS_418(){
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openCOMSite();
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

	// QTest ID TC-420 Shop Skincare-menu navigation
	@Test //Needs update
	public void testShopSkinCareMenuNavigation_420(){
		openCOMSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareOnPWS();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isRegimenNamePresent(TestConstantsRFL.REGIMEN_NAME_REDEFINE),"Redefine regimen name is not present on pws site");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isRegimenNamePresent(TestConstantsRFL.REGIMEN_NAME_REVERSE),"Reverse regimen name is not present on pws site");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isRegimenNamePresent(TestConstantsRFL.REGIMEN_NAME_UNBLEMISH),"Unblemish regimen name is not present on pws site");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isRegimenNamePresent(TestConstantsRFL.REGIMEN_NAME_SOOTHE),"Soothe regimen name is not present on pws site");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isRegimenNamePresent(TestConstantsRFL.REGIMEN_NAME_ENHANCEMENTS),"Enhancements regimen name is not present on pws site");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isRegimenNamePresent(TestConstantsRFL.REGIMEN_NAME_ESSENTIALS),"Essentials regimen name is not present on pws site");
		s_assert.assertAll();
	}

	//Shop Skincare-Consultants Only -buy business promotion
	@Test(enabled=false)//test no longer valid
	public void testShopSkinCareConsultantsOnlyBuyBusinessPromotion(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME+randomNumber;
		String regimen = "Promotions";
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openCOMSite();
		storeFrontBrandRefreshConsultantPage = storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshConsultantPage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("promotions"), "Expected regimen name is: promotions Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshHomePage.clickAddToCartButtonAfterLogin();
		storeFrontBrandRefreshConsultantPage.clickMyShoppingBagLink();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Adhoc order not placed successfully from biz pws for consultant user.");
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getOrderConfirmationTextMsgAfterOrderPlaced().contains("You will receive an email confirmation shortly"), "Order confirmation message does not contains email confirmation");
		s_assert.assertAll();
	}

	//QTest ID TC-422 Shop Skincare-Consultants Only -buy only products (enhancements micro dermabrasion paste packets)
	@Test
	public void testShopSkinCareConsultantsOnlyBuyProductPromotion_422(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME+randomNumber;
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openCOMSite();
		storeFrontBrandRefreshConsultantPage = storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshConsultantPage.mouseHoverOnShopSkinCareAndClickOnConsultantOnlyProductsLink();
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getCurrentURL().toLowerCase().contains("consultantsonly"), "Expected regimen name is: consultantsonly Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshConsultantPage.clickConsultantOnlyProductOnPWS(TestConstantsRFL.CONSULTANT_ONLY_PRODUCT);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("consultant-only"), "Expected url contains is:Consultant-Only Products but Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		storeFrontBrandRefreshHomePage.clickAddToCartButtonAfterLogin();
		//  storeFrontBrandRefreshConsultantPage.mouseHoverOnShopSkinCareAndClickOnConsultantOnlyProductsLink();
		storeFrontBrandRefreshHomePage.clickMyShoppingBagLink();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.isThankYouTextPresentAfterOrderPlaced(), "Order is not placed successfully");
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getOrderConfirmationTextMsgAfterOrderPlaced().contains("You will receive an email confirmation shortly"), "Order confirmation message does not contains email confirmation");
		s_assert.assertAll();
	}

	//QTest ID TC-2391 Log in as an existing consultant from COM
	//QTest ID TC-419 Shop_Skincare from COM
	@Test
	public void testShopSkinCarePWS_419_2391(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME+randomNumber;
		String regimen = "REVERSE";
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openCOMSite();
		storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		//click on 'our products' in tha nav menu
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		//select a product and add to cart
		storeFrontBrandRefreshHomePage.clickAddToCartButtonAfterLogin();
		storeFrontBrandRefreshHomePage.clickMyShoppingBagLink();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Order is not placed successfully");
		s_assert.assertAll();
	}

	//QTest ID TC-861 My account options as RC customer
	@Test 
	public void testMyAccountOptionsAsRCCustomer_861(){
		String orderHistory = "Order History";
		String rcEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_RC);
		openCOMSite();
		storeFrontBrandRefreshRCUserPage = storeFrontBrandRefreshHomePage.loginAsRCUser(rcEmailId,password);
		storeFrontBrandRefreshRCUserPage.clickHeaderLinkAfterLogin("my account");
		s_assert.assertTrue(storeFrontBrandRefreshRCUserPage.isOrderManagementSublinkPresent(orderHistory), "Order History link is not present in order management");
		storeFrontBrandRefreshRCUserPage.clickOrderManagementSublink(orderHistory);
		s_assert.assertTrue(storeFrontBrandRefreshRCUserPage.isOrderNumberPresentAtOrderHistoryPage(), "Order number is not present at order history page");
		s_assert.assertAll();
	}

	//QTest ID TC-2403 Footer- Privacy Policy link should be redirecting to the appropriate page
	@Test
	public void testFooterPrivacyPolicyLinkShouldRedirectionToAppropriatePage_2403(){
		openCOMSite();
		storeFrontBrandRefreshHomePage.clickPrivacyPolicyLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isPrivacyPolicyPagePresent(), "Privacy policy page is not present after clicked on privacy policy link");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("privacy"), "Expected url having privacy but actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertAll();
	}

	//QTest ID TC-2401 Satisfaction Guarantee-link should be redirecting properly 
	@Test
	public void testSatisfactionGuaranteeLinkShouldBeRedirectionProperly_2401(){
		openCOMSite();		
		storeFrontBrandRefreshHomePage.clickSatisfactionGuaranteeLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isSatisfactionGuaranteePagePresent(), "Satisfaction guarantee page is not present after clicked on Satisfaction guarantee link");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("guarantee"), "Expected url having guarantee but actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertAll();
	}

	//QTest ID TC-2402 Footer-Terms & Conditions link should redirecting to the appropriate page
	@Test
	public void testFooterTermsAndConditionLinkShouldRedirectionToAppropriatePage_2402(){
		String termsAndConditionsText = "TERMS & CONDITIONS";
		openCOMSite();
		storeFrontBrandRefreshHomePage.clickTermsAndConditionsLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isTextPresent(termsAndConditionsText), "TERMS & CONDITIONS page is not present after clicked on terms And Conditions link");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("terms"), "Expected url having terms but actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertAll();
	}

	//QTest ID TC-2404 Disclaimer-link should be redirecting properly from COM
	@Test
	public void testDisclaimerLinkShouldBeRedirectedProperlyFromCOM_2404(){
		String disclaimerText = "DISCLAIMER";
		openCOMSite();
		//verify Disclaimer in footer should redirect properly?
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateDisclaimerLinkInFooter(),"'Disclaimer Link' doesn't redirect to disclaimer page");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isTextPresent(disclaimerText), "disclaimer text page is not present after clicked on disclaimer link");
		s_assert.assertAll();
	}

	//QTest ID TC-2395 Contact us-link should be redirecting properly from COM
	@Test(enabled=true)
	public void testContactUsLinkShouldBeRedirectingProperlyFromCOM_2395(){
		String contactUsText = "CONTACT US";
		openCOMSite();
		storeFrontBrandRefreshHomePage.clickContactUsAtFooter();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains("Contact".toLowerCase()), "URL does not contain Contact Actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isTextPresent(contactUsText),"link is not redirected to contact us page");
		s_assert.assertAll();
	}

	// QTest ID TC-2415 PC User termination from COM
	@Test(enabled=true)
	public void testPCUserTerminationFromCOM_2415(){
		String pcEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		String changeMyPCPerksStatus = "Change my PC Perks Status";
		openCOMSite();
		storeFrontBrandRefreshPCUserPage = storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailId,password);
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
		storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailId,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isInvalidLoginPresent(),"Terminated User is able to Login");
		s_assert.assertAll();
	}

	//QTest ID TC-2413 My account options as PC customer from COM
	@Test(enabled=true) 
	public void testMyAccountOptionsAsPCCustomerFromCOM_2413(){
		String orderHistory = "Order History";
		String editOrder = "Edit Order";
		String changeMyPCPerksStatus = "Change my PC Perks Status";
		String pCPerksFAQs = "PC Perks FAQs";
		String pcEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		openCOMSite();
		storeFrontBrandRefreshPCUserPage = storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailId,password);
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

	//QTest ID TC-2417 PC Perks Delay - 60 days from COM
	@Test (enabled=true)
	public void testPCPerksDelay60DaysFromCOM_2417(){
		String changeMyPCPerksStatus = "Change my PC Perks Status";
		String pcEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);  
		openCOMSite();
		storeFrontBrandRefreshPCUserPage = storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailId, password);
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

	//QTest ID TC-912 PWS.com R+FInTheNews
	@Test(enabled=false)//no such links on com site
	public void testPWSCOMRPlusFInTheNews_912(){
		String expectedURL = "Company/PR";
		openCOMSite();
		storeFrontBrandRefreshHomePage.clickCompanyPressRoomLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURL), "Current url expected is: "+expectedURL+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		s_assert.assertAll();
	}

	//QTest ID TC-2392 Log in as valid PC customer from COM
	@Test(enabled=true)
	public void testLoginAsExistingPCFromCOM_2392(){
		String pcEmailID =getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		openCOMSite();
		storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"PC user is not logged in successfully");
		s_assert.assertAll();
	}

	//QTest ID TC-2393: Log in with a valid RC customer from COM
	@Test(enabled=true)
	public void testLoginAsExistingRCFromCOM_2393(){
		String rcEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_RC);
		openCOMSite();
		storeFrontBrandRefreshHomePage.loginAsPCUser(rcEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"RC user is not logged in successfully");
		s_assert.assertAll();
	}

	//QTest ID TC-2416 PC Perks Delay - 30 days from COM
	@Test (enabled=true)
	public void testPCPerksDelay30DaysFromCOM_2416(){
		String pcEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		String changeMyPCPerksStatus = "Change my PC Perks Status";
		openCOMSite();
		storeFrontBrandRefreshPCUserPage = storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailId, password);
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

	//*QTest ID TC-2399 Company-Careers link from COM
	@Test(enabled=true)
	public void testCompanyCarrersLink_2399(){
		String pageName="CAREERS";
		String expectedURL="careers";
		openCOMSite();
		storeFrontBrandRefreshHomePage.clickCareersLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURL), "Current url expected is: "+expectedURL+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isPageHeaderVisible(pageName),"User is not redirected to CAREERS Page");
		s_assert.assertAll();
	}

	//*QTest ID TC-955 Navigating to Events via Pulse
	@Test(enabled=false)
	public void testNavigatingToEventsViaPulse_955(){
		openCOMSite();
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		String expectedURL="pulserfo";
		String events="Events";
		storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshPulsePage=storeFrontBrandRefreshHomePage.clickCheckMyPulse();
		storeFrontBrandRefreshPulsePage.dismissPulsePopup();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURL), "Current url expected is: "+expectedURL+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshPulsePage.clickEventsOnPulsePage();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(events), "Current url expected is: "+events+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		s_assert.assertAll();
	}

	//*QTest ID TC-956 Navigating to Ecomms via Pulse
	@Test(enabled=false)
	public void testNavigatingToEcommsViaPulse_956(){
		openCOMSite();
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		String expectedURL="pulserfo";
		String ecomms="Communication";
		storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshPulsePage=storeFrontBrandRefreshHomePage.clickCheckMyPulse();
		storeFrontBrandRefreshPulsePage.dismissPulsePopup();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURL), "Current url expected is: "+expectedURL+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshPulsePage.clickECOMMSLinkOnPulsePage();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(ecomms), "Current url expected is: "+ecomms+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		s_assert.assertAll();
	}

	//*QTest ID TC-957 Navigating to My Account via Pulse
	@Test(enabled=false)
	public void testNavigatingToMyAccountViaPulse_957(){
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		String expectedURL="pulserfo";
		String myAccountText="MyAccount";
		openCOMSite();
		storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshPulsePage=storeFrontBrandRefreshHomePage.clickCheckMyPulse();
		storeFrontBrandRefreshPulsePage.dismissPulsePopup();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURL), "Current url expected is: "+expectedURL+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshPulsePage.clickMyAccountLinkOnPulsePage();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(myAccountText), "Current url expected is: "+myAccountText+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		s_assert.assertAll();
	}

	//*QTest ID TC-958 Navigating to Orders via Pulse
	@Test(enabled=false)
	public void testNavigatingToOrdersViaPulse_958(){
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		String expectedURL="pulserfo";
		String ordersText="Orders";
		openCOMSite();
		storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshPulsePage=storeFrontBrandRefreshHomePage.clickCheckMyPulse();
		storeFrontBrandRefreshPulsePage.dismissPulsePopup();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURL), "Current url expected is: "+expectedURL+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshPulsePage.clickOrdersLinkOnPulsePage();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(ordersText), "Current url expected is: "+ordersText+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		s_assert.assertAll();
	}

	//*QTest ID TC-959 Navigating to Contacts via Pulse
	@Test(enabled=false)
	public void testNavigatingToContactsViaPulse_959(){
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		String expectedURL="pulserfo";
		String contactsText="Contacts";
		openCOMSite();
		storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshPulsePage=storeFrontBrandRefreshHomePage.clickCheckMyPulse();
		storeFrontBrandRefreshPulsePage.dismissPulsePopup();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURL), "Current url expected is: "+expectedURL+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshPulsePage.clickContactsLinkOnPulsePage();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(contactsText), "Current url expected is: "+contactsText+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		s_assert.assertAll();
	}

	//*QTest ID TC-960 Navigating to Performance via Pulse
	@Test(enabled=false)
	public void testNavigatingToPerformanceViaPulse_960(){
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		String expectedURL="pulserfo";
		String performanceText="Performance";
		openCOMSite();
		storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshPulsePage=storeFrontBrandRefreshHomePage.clickCheckMyPulse();
		storeFrontBrandRefreshPulsePage.dismissPulsePopup();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURL), "Current url expected is: "+expectedURL+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshPulsePage.clickPerformanceLinkOnPulsePage();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(performanceText), "Current url expected is: "+performanceText+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		s_assert.assertAll();
	}

	//*QTest ID TC-961 Navigating to Biz Dev Library via Pulse
	@Test(enabled=false)
	public void testNavigatingToBizDevLibraryViaPulse_961(){
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		String expectedURL="pulserfo";
		openCOMSite();
		storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");

		//Navigating to pulse 
		storeFrontBrandRefreshPulsePage=storeFrontBrandRefreshHomePage.clickCheckMyPulse();
		storeFrontBrandRefreshPulsePage.dismissPulsePopup();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURL), "Current url expected is: "+expectedURL+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshPulsePage.clickBIZDEVLibraryLinkOnPulsePage();

		//login to pulse for accessing biz dev library
		storeFrontBrandRefreshPulsePage.loginToPulse(consultantEmailID, password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains("docurated"), "Current url should contain 'docurated' while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		s_assert.assertTrue(storeFrontBrandRefreshPulsePage.isUserRedirectedToBizDevLibraryPage(),"User is not redirected to 'Biz Dev Library' page");
		s_assert.assertAll();
	}

	//*QTest ID TC-962 Navigating to Dashboard via Pulse
	@Test(enabled=false)
	public void testNavigatingToDashboardViaPulse_962(){
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		String expectedURL="pulserfo";
		openCOMSite();
		storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		storeFrontBrandRefreshPulsePage=storeFrontBrandRefreshHomePage.clickCheckMyPulse();
		storeFrontBrandRefreshPulsePage.dismissPulsePopup();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains(expectedURL), "Current url expected is: "+expectedURL+" while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		storeFrontBrandRefreshPulsePage.clickDashboardLinkOnPulsePage();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getCurrentURL().contains("Home"), "Current url should contain 'Home' while actual on UI is "+storeFrontBrandRefreshHomePage.getCurrentURL());
		s_assert.assertTrue(storeFrontBrandRefreshPulsePage.isWelcomeUserTextDisplayedOnPulseHomePage(),"Welcome User Text is not Displayed on Pulse home Page");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate the redefine category links and their sublinks(Results and FAQs) under shopskincare from com site
	 *  
	 */
	//*QTest ID TC-887:Shop Shop Skincare-menu navigation REDEFINE (PWS.com)
	@Test(enabled=true)
	public void testShopSkincareMenuNavigationRedefineFromCom_887(){
		String category = "REDEFINE";
		String subLinkResults = "Results";
		String subLinkFAQ = "FAQ";
		String currentURL = null;
		openCOMSite();
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
	 * Test Summary:- validate the REVERSE category links and their sublinks(Results and FAQs) under shopskincare from com site
	 *  
	 */
	//*QTest ID TC-888:Shop Skincare-menu navigation REVERSE (PWS.com)
	@Test(enabled=true)
	public void testShopSkincareMenuNavigationReverseFromCom_888(){
		String category = "REVERSE";
		String subLinkResults = "Results";
		String subLinkFAQ = "FAQ";
		String currentURL = null;
		openCOMSite();
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
	 * Test Summary:- validate the UNBLEMISH category links and their sublinks(Results and FAQs) under shopskincare from com site
	 *  
	 */
	//*QTest ID TC-889:Shop Skincare-menu navigation UNBLEMISH (PWS.com)
	@Test(enabled=true)
	public void testShopSkincareMenuNavigationUnblemishFromCom_889(){
		String category = "UNBLEMISH";
		String subLinkResults = "Results";
		String subLinkFAQ = "FAQ";
		String currentURL = null;
		openCOMSite();
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
	 * Test Summary:- validate the SOOTHE category links and their sublinks(Results and FAQs) under shopskincare from com site
	 *  
	 */
	//*QTest ID TC-890:Shop Skincare-menu navigation SOOTHE (PWS.com)
	@Test(enabled=true)
	public void testShopSkincareMenuNavigationSootheFromCom_890(){
		String category = "SOOTHE";
		String subLinkResults = "Results";
		String subLinkFAQ = "FAQ";
		String currentURL = null;
		openCOMSite();
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
	 * Test Summary:- validate the ENHANCEMENTS category links under shopskincare from com site
	 *  
	 */
	//*QTest ID TC-891:Shop Skincare-menu navigation ENHANCEMENTS (PWS.com)
	@Test(enabled=true)
	public void testShopSkincareMenuNavigationEnhancementsFromCom_891(){
		String category = "ENHANCEMENTS";
		String currentURL = null;
		openCOMSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(category);
		currentURL = storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isExactCategoryNamePresent(category) && currentURL.contains(category.toLowerCase()),category+"category link is not redirected to"+category+" category page and current url is "+currentURL);
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate the ESSENTIALS category links under shopskincare from com site
	 *  
	 */
	//*QTest ID TC-892:Shop Skincare-menu navigation ESSENTIALS (PWS.com)
	@Test(enabled=true)
	public void testShopSkincareMenuNavigationEssentialsFromBiz_892(){
		String category = "ESSENTIALS";
		String currentURL = null;
		openCOMSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(category);
		currentURL = storeFrontBrandRefreshHomePage.getCurrentURL().toLowerCase();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isExactCategoryNamePresent(category) && currentURL.contains(category.toLowerCase()),category+"category link is not redirected to"+category+" category page and current url is "+currentURL);
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- placed an adhoc order with consultant only buy event support pack 
	 * and validate orders details like subtotal & product name from com site
	 */
	//*QTest ID TC-904 Consultants Only -buy event support pack 
	@Test(enabled=true)
	public void consultantsOnlyBuyEventSupportPack_904(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME+randomNumber;
		String subtotalFromCart = null;
		String productNameFromCart = null;
		String subtotalFromOrderConfirmation = null;
		String productNameFromOrderConfirmation = null;
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openCOMSite();
		storeFrontBrandRefreshConsultantPage = storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		//  storeFrontBrandRefreshConsultantPage.clickShopSkinCareBtn();
		//  storeFrontBrandRefreshConsultantPage.selectConsultantOnlyProductsRegimen();
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
	 *  and validate orders details like subtotal & product name from com site
	 */
	//*QTest ID TC-906 Consultants Only -buy product promotion 
	@Test(enabled=true)
	public void consultantsOnlyBuyProductPromotion_906(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME+randomNumber;
		String subtotalFromCart = null;
		String productNameFromCart = null;
		String subtotalFromOrderConfirmation = null;
		String productNameFromOrderConfirmation = null;
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openCOMSite();
		storeFrontBrandRefreshConsultantPage = storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
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
	 * Test Summary:- validate the sublink of About R+F from com site
	 */
	//*QTest ID TC-908 Company-Links should be present (Execute team, our history, carrers, PFC foundation, press room, contact us) 
	@Test(enabled=true)
	public void testVerfiyCompanyLinks_908(){
		String whoWeAre = "WHO WE ARE";
		String meetTheDoctors = "MEET THE DOCTORS";
		String givingBack = "GIVING BACK";
		String currentURL = null;
		openCOMSite();
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

		// assert Giving Back link
		storeFrontBrandRefreshHomePage.mouseHoverAboutRFAndClickLink("Giving Back");
		currentURL = driver.getCurrentUrl();
		s_assert.assertTrue(currentURL.contains("GivingBack"),"Giving Back link didn't work");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isLinkTextPresent(givingBack),givingBack+" link text is not present");
		s_assert.assertAll();
	}

	// QTest ID TC-2407 PC Enrollment from COM
	@Test(enabled=true)
	public void testPCEnrollmentFromCOM_2407() {
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME + randomNum;
		String shippingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String shippingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME + randomNumber;
		String emailAddress = firstName + randomNum + "@rodanandfields.com";
		String regimen = TestConstantsRFL.REGIMEN_NAME_REVERSE;
		String gender = TestConstantsRFL.GENDER_MALE;
		String addressName = "Home";
		String orderNumber=null;
		String subTotal = null;
		String tax = null; 
		String grandTotal = null;
		String grandTotalFromDB=null;
		String taxFromDB=null;
		String shippingFromDB=null;
		String subTotalFromDB=null;
		String orderHistory = "Order History";
		String billingName = TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME + randomNumber;
		// Navigating to COM PWS
		openCOMSite();
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
		//s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(),"Enrollment is not completed successfully");
		orderNumber = storeFrontBrandRefreshHomePage.getOrderNumberFromOrderConfirmationPage();
		storeFrontBrandRefreshHomePage.clickOnRodanAndFieldsLogo();
		storeFrontBrandRefreshPCUserPage.clickHeaderLinkAfterLogin("my account");
		storeFrontBrandRefreshPCUserPage.clickOrderManagementSublink(orderHistory);
		s_assert.assertTrue(storeFrontBrandRefreshPCUserPage.isOrderNumberPresentAtOrderHistoryPage(), "Order number is not present at order history page");
		storeFrontBrandRefreshHomePage.clickViewDetailsLinkInOrderHistoryForPC("1");
		subTotal = storeFrontBrandRefreshHomePage.getSubtotalFromOrderHistoryPageForPC();
		tax = storeFrontBrandRefreshHomePage.getTaxFromOrderHistoryPageForPC();
		grandTotal = storeFrontBrandRefreshHomePage.getGrandTotalFromOrderHistoryPageForPC();
		List<Map<String, Object>> orderList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_ORDER_DETAILS, orderNumber), RFL_DB);
		grandTotalFromDB = String.valueOf(getValueFromQueryResult(orderList, "GrandTotal"));
		s_assert.assertTrue(grandTotalFromDB.contains(grandTotal),"Grand Total from DB is:" + grandTotalFromDB + " different from UI is:" + grandTotal);
		taxFromDB = String.valueOf(getValueFromQueryResult(orderList, "TaxAmountTotal"));
		s_assert.assertTrue(taxFromDB.contains(tax), "Tax from DB is:" + taxFromDB + " different from UI is:" + tax);
		shippingFromDB = String.valueOf(getValueFromQueryResult(orderList, "ShippingTotal"));
		s_assert.assertTrue(shippingFromDB.contains("0.0"),"shipping Total from DB is:" + shippingFromDB + " different from UI is:'0.0'");
		subTotalFromDB = String.valueOf(getValueFromQueryResult(orderList, "Subtotal"));
		s_assert.assertTrue(subTotalFromDB.contains(subTotal),"SubTotal from DB is:" + subTotalFromDB + " different from UI is:" + subTotal);
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate product details before and after placing an order from com
	 */	
	//*QTest ID TC-836 Checkout as Consultant
	@Test(enabled=true)
	public void testCheckoutAsConsultant_836(){
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
		openCOMSite();
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
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

	//*QTest ID TC-698 Mouse hover on shopping bag should bring drop down screen
	@Test(enabled=true)
	public void testMouseHoverOnShoppingBagShouldBringDropDownScreen_698() {
		String regimen=TestConstantsRFL.REGIMEN_NAME_REDEFINE;
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartBtn();
		storeFrontBrandRefreshHomePage.clickMyShoppingBagLink();
		//verify the Qty price and item name 
		storeFrontBrandRefreshHomePage.checkProductNameThroughCart();
		storeFrontBrandRefreshHomePage.checkQtyThroughCart();
		storeFrontBrandRefreshHomePage.checkTotalPriceThroughCart();
		storeFrontBrandRefreshHomePage.checkProductPriceThroughCart();
		//Biz site
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonAfterLogin();
		storeFrontBrandRefreshHomePage.clickMyShoppingBagLink();
		storeFrontBrandRefreshHomePage.checkProductNameThroughCart();
		storeFrontBrandRefreshHomePage.checkQtyThroughCart();
		storeFrontBrandRefreshHomePage.checkTotalPriceThroughCart();
		storeFrontBrandRefreshHomePage.checkProductPriceThroughCart();
		//Com site
		openBIZSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonAfterLogin();
		storeFrontBrandRefreshHomePage.clickMyShoppingBagLink();
		storeFrontBrandRefreshHomePage.checkProductNameThroughCart();
		storeFrontBrandRefreshHomePage.checkQtyThroughCart();
		storeFrontBrandRefreshHomePage.checkTotalPriceThroughCart();
		storeFrontBrandRefreshHomePage.checkProductPriceThroughCart();
	}

	//*QTest ID TC-2406  RC Enrollment from COM
	@Test(enabled=true)
	public void testRCEnrollmentFromCOM_2406(){
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
		String orderNumber=null;
		String addressName = "Home";
		String subTotal = null;
		String shipping = null;
		String tax = null; 
		String grandTotal = null;
		String grandTotalFromDB=null;
		String taxFromDB=null;
		String shippingFromDB=null;
		String subTotalFromDB=null;
		openCOMSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.clickClickHereLinkForRC();
		s_assert.assertTrue(driver.getCurrentUrl().contains("Retail"), "After clicking click here link for RC not navigated to RC Enrollment page.");
		storeFrontBrandRefreshHomePage.enterProfileDetailsForPCAndRC(firstName,lastName,emailAddress,password,phnNumber,gender);
		storeFrontBrandRefreshHomePage.clickCreateMyAccountBtnOnCreateRetailAccountPage();
		storeFrontBrandRefreshHomePage.clickSelectAndContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.enterShippingProfileDetails(addressName, shippingProfileFirstName,shippingProfileLastName, addressLine1, postalCode, phnNumber);
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfoDetails(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickContinueBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Enrollment is not completed successfully");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isQuantityOnConfirmOrderpagePresent(), "Qty is not visible");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isSKUOnConfirmOrderPagePresent(), "SKU is not visible");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isItemOnConfirmOrderpagePresent(), "Item name is not visible");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isPriceOnConfirmOrderpagePresent(), "Price is not visible");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isTotalOnConfirmOrderpagePresent(), "Total is not visible");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isSubTotalOnConfirmOrderpagePresent(), "SubTotal is not visible");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isTaxAmountOnConfirmOrderpagePresent(), "Tax Amount is not visible");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isShippingAmountOnConfirmOrderpagePresent(), "Shipping Total is not visible");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isGrandTotalOnConfirmOrderpagePresent(), "Grand Total is not visible");
//		orderNumber=storeFrontBrandRefreshHomePage.getOrderNumberFromOrderConfirmationPage();
//		subTotal=storeFrontBrandRefreshHomePage.getSubtotalFromOrderConfirmationPage();
//		shipping=storeFrontBrandRefreshHomePage.getShippingTotalFromOrderConfirmationPage();
//		tax=storeFrontBrandRefreshHomePage.getTaxFromOrderConfirmationPage();
//		grandTotal=storeFrontBrandRefreshHomePage.getGrandTotalFromOrderConfirmationPage();
//		List<Map<String, Object>> orderList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_ORDER_DETAILS,orderNumber),RFL_DB);
//		grandTotalFromDB = String.valueOf(getValueFromQueryResult(orderList, "GrandTotal"));
//		s_assert.assertTrue(grandTotalFromDB.contains(grandTotal),"Grand Total from DB is:"+grandTotalFromDB+" different from UI is:"+grandTotal);
//		taxFromDB = String.valueOf(getValueFromQueryResult(orderList, "TaxAmountTotal"));
//		s_assert.assertTrue(taxFromDB.contains(tax),"Tax from DB is:"+taxFromDB+" different from UI is:"+tax);
//		shippingFromDB = String.valueOf(getValueFromQueryResult(orderList, "ShippingTotal"));
//		s_assert.assertTrue(shippingFromDB.contains(shipping),"shipping Total from DB is:"+shippingFromDB+" different from UI is:"+shipping);
//		subTotalFromDB = String.valueOf(getValueFromQueryResult(orderList, "Subtotal"));
//		s_assert.assertTrue(subTotalFromDB.contains(subTotal),"SubTotal from DB is:"+subTotalFromDB+" different from UI is:"+subTotal);
		s_assert.assertAll();
	}

	//*QTest ID TC-2421 Adhoc order from COM
	@Test(enabled=true)
	public void testAdhocOrderRCFromCOM_2421(){
		RFL_DB = driver.getDBNameRFL();
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String nameOnCard = firstName;
		String regimen = TestConstantsRFL.REGIMEN_NAME_REVERSE;
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String orderNumber=null;
		String subTotal = null;
		String shipping = null;
		String tax = null; 
		String grandTotal = null;
		String grandTotalFromDB=null;
		String taxFromDB=null;
		String shippingFromDB=null;
		String subTotalFromDB=null;
		String rcEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_RC);
		openCOMSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonForEssentialsAndEnhancementsAfterLogin();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.loginAsUserOnCheckoutPage(rcEmailID, password);
		s_assert.assertFalse(storeFrontBrandRefreshHomePage.isSignInButtonPresent(), "RC user not logged in successfully");
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.chooseShippingMethod("FedEx Grnd");
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Adhoc order not placed successfully from com site for RC user.");
//		orderNumber = storeFrontBrandRefreshHomePage.getOrderNumberFromOrderConfirmationPage();
//		subTotal=storeFrontBrandRefreshHomePage.getSubtotalFromOrderConfirmationPage();
//		shipping=storeFrontBrandRefreshHomePage.getShippingTotalFromOrderConfirmationPage();
//		tax=storeFrontBrandRefreshHomePage.getTaxFromOrderConfirmationPage();
//		grandTotal=storeFrontBrandRefreshHomePage.getGrandTotalFromOrderConfirmationPage();
//		List<Map<String, Object>> orderList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_ORDER_DETAILS,orderNumber),RFL_DB);
//		grandTotalFromDB = String.valueOf(getValueFromQueryResult(orderList, "GrandTotal"));
//		s_assert.assertTrue(grandTotalFromDB.contains(grandTotal),"Grand Total from DB is:"+grandTotalFromDB+" different from UI is:"+grandTotal);
//		taxFromDB = String.valueOf(getValueFromQueryResult(orderList, "TaxAmountTotal"));
//		s_assert.assertTrue(taxFromDB.contains(tax),"Tax from DB is:"+taxFromDB+" different from UI is:"+tax);
//		shippingFromDB = String.valueOf(getValueFromQueryResult(orderList, "ShippingTotal"));
//		s_assert.assertTrue(shippingFromDB.contains(shipping),"shipping Total from DB is:"+shippingFromDB+" different from UI is:"+shipping);
//		subTotalFromDB = String.valueOf(getValueFromQueryResult(orderList, "Subtotal"));
//		s_assert.assertTrue(subTotalFromDB.contains(subTotal),"SubTotal from DB is:"+subTotalFromDB+" different from UI is:"+subTotal);
		s_assert.assertAll();
	}	

	//*QTest ID TC-2422 Adhoc Order - Consultant Only Products Com site
	//>100 QV product is not avaialbe
	@Test(enabled=true)
	public void testAdhocOrderConsultantsOnlyProductsFromCOM_2422(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String orderNumber=null;
		String subTotal = null;
		String shipping = null;
		String tax = null; 
		String grandTotal = null;
		String grandTotalFromDB=null;
		String taxFromDB=null;
		String shippingFromDB=null;
		String subTotalFromDB=null;
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openCOMSite();
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
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getOrderConfirmationTextMsgAfterOrderPlaced().contains("You will receive an email confirmation shortly"), "Order confirmation message does not contains email confirmation");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isQuantityOnConfirmOrderpagePresent(), "Qty is not visible");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isSKUOnConfirmOrderPagePresent(), "SKU is not visible");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isItemOnConfirmOrderpagePresent(), "Item name is not visible");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isPriceOnConfirmOrderpagePresent(), "Price is not visible");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isTotalOnConfirmOrderpagePresent(), "Total is not visible");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isSubTotalOnConfirmOrderpagePresent(), "SubTotal is not visible");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isTaxAmountOnConfirmOrderpagePresent(), "Tax Amount is not visible");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isShippingAmountOnConfirmOrderpagePresent(), "Shipping Total is not visible");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isGrandTotalOnConfirmOrderpagePresent(), "Grand Total is not visible");
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

	//QTest TC-2418:PC Perks Template Update - Add/modify products from COM
	@Test(enabled=true)
	public void testPCPerksTemplateUpdateFromCOM_2418(){
		String updatedOrderTotal = null;
		String pcEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		openCOMSite();
		storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailId, password);
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
		updatedOrderTotal = storeFrontBrandRefreshHomePage.getOrderTotal();
		storeFrontBrandRefreshHomePage.clickOnRodanAndFieldsLogo();
		storeFrontBrandRefreshHomePage.clickHeaderLinkAfterLogin("my account");
		storeFrontBrandRefreshHomePage.clickEditOrderLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getOrderTotalAtOverview().contains(updatedOrderTotal),"order total is not updated at overview page");
		s_assert.assertAll();
	}

	//QTest TC-2419:PC Perks Template - Shipping Address from COM
	@Test(enabled=true)
	public void testPCPerksTemplateShippingAddressUpdateFromCOM_2419(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String addressName = "Home";
		String shippingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME+randomNumber;
		String shippingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME;
		String pcEmailId = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		openCOMSite();
		storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailId,password);
		storeFrontBrandRefreshHomePage.clickHeaderLinkAfterLogin("my account");
		storeFrontBrandRefreshHomePage.clickEditOrderLink();
		storeFrontBrandRefreshHomePage.clickChangeLinkUnderShippingToOnPWS();
		storeFrontBrandRefreshHomePage.enterShippingProfileDetailsForPWS(addressName, shippingProfileFirstName, shippingProfileLastName, addressLine1, postalCode, phnNumber);
		storeFrontBrandRefreshHomePage.clickUseThisAddressShippingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getShippingAddressName().contains(shippingProfileFirstName),"Shipping address name expected is "+shippingProfileFirstName+" While on UI is "+storeFrontBrandRefreshHomePage.getShippingAddressName());
		s_assert.assertAll();
	}

	//QTest TC-2420:PC Perks Template - Billing Profile from COM
	@Test(enabled=true)
	public void testPCPerksTemplateBillingAddressUpdateFromCOM_2420(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME+randomNumber;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME;
		String pcEmailId =getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		openCOMSite();
		storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailId,password);
		storeFrontBrandRefreshHomePage.clickHeaderLinkAfterLogin("my account");
		storeFrontBrandRefreshHomePage.clickEditOrderLink();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationLinkUnderBillingTabOnPWS();
		storeFrontBrandRefreshHomePage.enterBillingInfoForPWS(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getBillingAddressName().contains(billingProfileFirstName), "Expected billing profile name is: "+billingProfileFirstName+" Actual on UI is: "+storeFrontBrandRefreshHomePage.getBillingAddressName());
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open COM, Login with consultant, logout and Verify 
	 * Assertions:
	 * User is able to login successfully
	 * User is logout successfully
	 */
	//QTest ID TC-2394 COM> log out with a valid user  from COM PWS
	@Test(enabled=true)
	public void testLogoutWithAValidUserOnCOMPWS_2394(){
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		//Navigating to COM PWS
		openCOMSite();
		storeFrontBrandRefreshHomePage.loginAsConsultant(consultantEmailID,password);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		logout();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isForgotPasswordLinkPresent(),"User is not logout successfully");
		s_assert.assertAll();
	}	


	/***
	 * Test Summary:- validate RF Connection Link From Com PWS
	 * Assertions:
	 * Page URL should contain 'rfconnection'
	 * RF Connection should be visible on page Header
	 */	
	//*QTest ID TC-2396 RF Connection From COM PWS Site
	@Test(enabled=true)
	public void testRFConnectionFromCOMPWS_2396(){
		String rfConnection="RF Connection";
		String currentURL=null;
		openCOMSite();
		storeFrontBrandRefreshHomePage.clickBottomPageCategoryLink(rfConnection);
		currentURL=storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.contains("rfconnection"),"Expected URL should contain 'rfconnection' But actual on UI is: "+currentURL);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isRFConnectionPageHeaderVisible()," User is not redirected to RF Connection Page");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate DERM RF Link From COM PWS Site
	 * Assertions:
	 * Page URL should contain 'dermrf'
	 * DERM RF should be visible on page Header
	 */	
	//*QTest ID TC-2397 Derm Connection from COM PWS Site
	@Test(enabled=true)
	public void testDermConnectionFromCOMPWS_2397(){
		String dermRF="Derm RF";
		String currentURL=null;
		openCOMSite();
		storeFrontBrandRefreshHomePage.clickBottomPageCategoryLink(dermRF);
		currentURL=storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.contains("dermrf"),"Expected URL should contain 'dermrf' But actual on UI is: "+currentURL);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isDERMRFPageHeaderVisible()," User is not redirected to DERM RF Page");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate Shipping Link From COM PWS
	 * Assertions:
	 * Page URL should contain 'Shipping'
	 * Shipping should be visible on page Header
	 */	
	//*QTest ID TC-2400 Shipping from COM PWS
	@Test(enabled=true)
	public void testShippingFromCOMPWS_2400(){
		String shipping="Shipping";
		String currentURL=null;
		openCOMSite();
		storeFrontBrandRefreshHomePage.clickBottomPageCategoryLink(shipping);
		currentURL=storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.contains("Shipping"),"Expected URL should contain 'shipping' But actual on UI is: "+currentURL);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isShippingPageHeaderVisible()," User is not redirected to Shipping Page");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- validate California Supply Chains Act Link From COM PWS
	 * Assertions:
	 * Page URL should contain 'california-supply-chains-act'
	 * 'CALIFORNIA TRANSPARENCY IN SUPPLY CHAINS ACT' should be visible on page Header
	 */	
	//*QTest ID TC-2405 California Supply Chains Act from COM PWS
	@Test(enabled=true)
	public void testCaliforniaSupplyChainsActFromCOMPWS_2405(){
		String califSupplyChainsAct="California Supply Chains Act";
		String currentURL=null;
		openCOMSite();
		storeFrontBrandRefreshHomePage.clickBottomPageCategoryLink(califSupplyChainsAct);
		currentURL=storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.contains("california-supply-chains-act"),"Expected URL should contain 'california-supply-chains-act' But actual on UI is: "+currentURL);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCaliforniaSupplyChainsActPageHeaderVisible()," User is not redirected to 'California Supply Chains' Act Page");
		s_assert.assertAll();
	}

	/***
	 * Test Summary:- Open com url, Login with PC, start to place an adhoc order, add billing profile with INVALID credit card and place the order
	 * Assertions:
	 * Error message should be displayed for INVALID credit card after placed an order
	 */	
	//QTest ID TC-2409 COM> Payment error message
	@Test
	public void testPaymentErrorMessageFromCOM_2409(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String errorMessageFromUI = null;
		String errorMessage = TestConstantsRFL.ERROR_MSG_FOR_INVALID_CC;
		String regimen = TestConstantsRFL.REGIMEN_NAME_REVERSE;
		String pcEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		String cardNumber = TestConstantsRFL.INVALID_CARD_NUMBER;
		openCOMSite();
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
	 * Test Summary:- validate Shop Skincare-menu navigation PROMOTIONS (PWS.com)
	 * Assertions:
	 * Page URL should contain 'Promotions'
	 * 'FEATURED' should be visible on page Header
	 */	
	//*QTest ID TC-886 Shop Skincare-menu navigation PROMOTIONS (PWS.com)
	@Test(enabled=true)
	public void testShopSkincareFeaturedFromCOMPWS_886(){
		String promotions="Promotions";
		String currentURL=null;
		openCOMSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(TestConstantsRFL.REGIMEN_NAME_FEATURED);
		currentURL=storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentURL.contains(promotions),"Expected URL should contain"+promotions+" But actual on UI is: "+currentURL);
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isUserRedirectedToFeaturedPage()," User is not redirected to Promotions Act Page");
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open COM, Login with consultant, Navigate to 'Consultant only products' and click on 'Consultant only Event support' product, Add donation product to bag and complete place order and verify
	 * Assertions:
	 * Order placed successfully
	 * Verify grandTotal from DB
	 */
	//*QTest ID TC-2424 COM> Donation order 
	@Test(enabled=true)
	public void testDonationOrderFromCOM_2424(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME+randomNumber;
		List<Map<String, Object>> randomOrderList =  null;
		String grandTotal = null;
		String orderNumber=null;
		String totalAmount=null;
		String consultantEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_CONSULTANT);
		openCOMSite();
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
	 * Test Summary: Open COM, Click on Country Toggle Select AUS and verify
	 * Assertions:
	 * AUS Should be present in Country Toggle
	 * User should redirected to AUS Country after clicking on 'AUS' from country toggle
	 */
	//QTest ID TC-927 PWS COM> Verify the User is redirected to the Corporate Australian Home Page
	@Test
	public void testVerifyTheUserIsRedirectedToTheCorporateAustralianHomePageCOMPWS_927(){
		String countryName="AUS";
		String url="rodanandfields.com.au";
		String currentUrl=null;
		openCOMSite();
		storeFrontBrandRefreshHomePage.clickCountryToggle();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isCountryPresentInCountryToggle(countryName)," Expected country: "+countryName+" not present in country toggle");
		storeFrontBrandRefreshHomePage.selectCountry(countryName);
		currentUrl=storeFrontBrandRefreshHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(url),"User is not redirected to expected country:"+countryName+" Url should contains: "+url+" But actual on UI is: "+currentUrl);
		s_assert.assertAll();
	}	

	//QTest ID TC-2408 Registering the PC using Retail customer's email id from COM
	@Test
	public void testRegisteringThePCUsingRetailCustomerEmailId_2408(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String lastName = TestConstantsRFL.LAST_NAME+randomNum;
		String gender = TestConstantsRFL.GENDER_MALE;
		String rcEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_RC);
		openCOMSite();
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonAfterLogin();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.clickClickHereLinkForPC();
		storeFrontBrandRefreshHomePage.clickEnrollNowBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.enterProfileDetailsForPCAndRC(firstName,lastName,rcEmailID,password,phnNumber,gender);
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.validateExistingRCPopUpCom(),"Existing RC Pop Up is not displayed!!");
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open COM PWS, login with PC User, place Ad-hoc order with new
	 * Shipping address Assertions: Newly created Shipping profile at order
	 * confirmation page
	 */
	// QTest ID TC-2482 Adhoc Order with Newly created shipping address
	@Test
	public void testAdhocOrderWithNewlyCreatedShippingAddressFromCOM_2482() {
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String addressName = "Home";
		String shippingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME + randomNumber;
		String shippingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME;
		String pcUserEmailID = getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		String shippingProfileName = shippingProfileFirstName + " " + shippingProfileLastName;
		String shippingName = null;
		String shippingAddress = null;
		openCOMSite();
		storeFrontBrandRefreshHomePage.loginAsPCUser(pcUserEmailID, password);
		storeFrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLinkOnPWS(regimen);
		storeFrontBrandRefreshHomePage.clickAddToCartButtonAfterLogin();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshHomePage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeShippingAddressBtn();
		storeFrontBrandRefreshHomePage.enterShippingProfileDetailsForPWS(addressName, shippingProfileFirstName,shippingProfileLastName, addressLine1, postalCode, phnNumber);
		storeFrontBrandRefreshHomePage.clickUseThisAddressShippingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.clickContinueBtnOnBillingPage();
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		storeFrontBrandRefreshHomePage.clickOKBtnOnPopup();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(),"Adhoc order not placed successfully from COM PWS site.");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getOrderConfirmationTextMsgAfterOrderPlaced().contains("You will receive an email confirmation shortly"),"Order confirmation message does not contains email confirmation");
		shippingName = storeFrontBrandRefreshHomePage.getShippingNameFromOrderConfirmationPage();
		s_assert.assertTrue(shippingProfileName.contains(shippingName), "Shipping Profile is not the same as entered");
		shippingAddress = storeFrontBrandRefreshHomePage.getAddressFromOrderConfirmationPage();
		s_assert.assertTrue(shippingAddress.contains(addressLine1.toUpperCase()),"Shipping Address is not the same as entered");	
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Open COM PWS, login with PC User, place adhoc order with new billing address
	 * Assertions:
	 * Newly created billing profile at order confirmation page 
	 */
	//QTest ID TC-2483 Adhoc Order with Newly created billing address
	@Test
	public void testAdhocOrderWithNewlyCreatedBillingAddressFromOM_2483(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String nameOnCard = firstName;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String pcUserEmailID =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		openCOMSite();
		storeFrontBrandRefreshPCUserPage=storeFrontBrandRefreshHomePage.loginAsPCUser(pcUserEmailID,password);
		storeFrontBrandRefreshPCUserPage.mouseHoverShopSkinCareAndClickLink(regimen);
		storeFrontBrandRefreshPCUserPage.clickAddToCartButtonAfterLogin();
		storeFrontBrandRefreshHomePage.clickCheckoutBtn();
		storeFrontBrandRefreshConsultantPage.clickContinueBtnForPCAndRC();
		storeFrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		storeFrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		storeFrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		storeFrontBrandRefreshHomePage.clickUseAsEnteredBtn();
		storeFrontBrandRefreshHomePage.clickCompleteOrderBtn();
		storeFrontBrandRefreshHomePage.clickOKBtnOnPopup();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isThankYouTextPresentAfterOrderPlaced(), "Adhoc order not placed successfully from COM PWS site.");
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.getBillingAddressName().contains(billingProfileFirstName), "Expected billing profile name is: "+billingProfileFirstName+" Actual on UI is: "+storeFrontBrandRefreshHomePage.getBillingAddressName());
		s_assert.assertTrue(storeFrontBrandRefreshConsultantPage.getOrderConfirmationTextMsgAfterOrderPlaced().contains("You will receive an email confirmation shortly"), "Order confirmation message does not contains email confirmation");
		s_assert.assertAll();
	}
	
	/***
	 * Test Summary:- Open COM PWS, Login with PC User, and verify
	 * Assertions:
	 * Verify sponsor name on the top of the page
	 */ 
	//QTest ID TC-2153 Sponsor infor_PWS.com
	@Test
	public void testSponsorInfo_MyAccountFromCOMPWS_2153(){
		String pcEmailId =  getRandomUserFromDB(TestConstantsRFL.USER_TYPE_PC);
		openCOMSite();
		storeFrontBrandRefreshHomePage.loginAsPCUser(pcEmailId, password);
		storeFrontBrandRefreshHomePage.clickMyAccountLink();
		s_assert.assertTrue(storeFrontBrandRefreshHomePage.isSponsorInfoPresentOnTopOfPage(),"Sponsor Info not Available on Top of Page");
		s_assert.assertAll();
	}	
}