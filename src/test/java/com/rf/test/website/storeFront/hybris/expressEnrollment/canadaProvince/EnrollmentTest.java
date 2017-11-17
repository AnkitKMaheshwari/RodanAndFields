package com.rf.test.website.storeFront.hybris.expressEnrollment.canadaProvince;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.rf.core.utils.CommonUtils;
import com.rf.core.utils.DBUtil;
import com.rf.core.website.constants.TestConstants;
import com.rf.core.website.constants.dbQueries.DBQueries_RFO;
import com.rf.pages.website.storeFront.StoreFrontConsultantPage;
import com.rf.pages.website.storeFront.StoreFrontHomePage;
import com.rf.pages.website.storeFront.StoreFrontShippingInfoPage;
import com.rf.test.website.RFWebsiteBaseTest;

public class EnrollmentTest extends RFWebsiteBaseTest{
	private static final Logger logger = LogManager
			.getLogger(EnrollmentTest.class.getName());

	//[Hybris Project-3612,Hybris Project-1670,Hybris Project-1669,Hybris Project-1668,Hybris Project-1667,Hybris Project-1665,Hybris Project-1664,Hybris Project-1663,Hybris Project-1662,Hybris Project-1661]
	@Test(dataProvider="rfTestData")//test needs updation 
	public void testEnrollmentCanadaProvince(String province) throws InterruptedException{
		if(driver.getCountry().equalsIgnoreCase("ca")){
			int randomNum = CommonUtils.getRandomNum(10000, 1000000);
			String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
			storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
			storeFrontHomePage.searchCID(sponsor);
			storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
			storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, TestConstants.FIRST_NAME+randomNum, TestConstants.LAST_NAME+randomNum, password, addressLine1, city,province, postalCode, phoneNumber);
			storeFrontHomePage.clickNextButtonWithUseAsEnteredButton();
			//storeFrontHomePage.acceptTheVerifyYourShippingAddressPop();  
			storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
			storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
			storeFrontHomePage.selectNewBillingCardExpirationDate();
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
			s_assert.assertAll();

		}
		else{
			logger.info("NOT EXECUTED...Test is ONLY for CANADA env");
		}
	}


	//Hybris Project-4066:Place an Order from US Con's PWS(.BIZ, .COM) as a Canadian Con W/O Pulse
	@Test(enabled=false)
	public void testPlaceAnOrderFromUSConPWSFromCanadianWithoutOPulse_4066() throws InterruptedException{
		RFO_DB = driver.getDBNameRFO();
		List<Map<String, Object>> randomConsultantList =  null;
		List<Map<String, Object>> randomConsultantList2 =  null;
		String consultantEmailID = null;
		String usConsultantPWS = null;
		String countryID ="236";
		String country = "us";
		storeFrontHomePage = new StoreFrontHomePage(driver);
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITHOUT_PULSE_RFO,countryId),RFO_DB);
		consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "EmailAddress");
		randomConsultantList2 =  DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",country,countryID), RFO_DB);
		usConsultantPWS = (String) getValueFromQueryResult(randomConsultantList2, "URL"); 
		driver.get(usConsultantPWS);
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontConsultantPage.clickAddToBagButtonWithoutFilter();
		storeFrontConsultantPage.clickOnPlaceOrderButton();
		storeFrontConsultantPage.clickOnOkButtonOnCheckoutConfirmationPopUp();
		storeFrontConsultantPage.clickOnShippingAddressNextStepBtn();
		storeFrontConsultantPage.clickOnBillingNextStepBtn();
		storeFrontConsultantPage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.isOrderPlacedSuccessfully(),"order is not placed successfully");
		s_assert.assertAll();
	}

}