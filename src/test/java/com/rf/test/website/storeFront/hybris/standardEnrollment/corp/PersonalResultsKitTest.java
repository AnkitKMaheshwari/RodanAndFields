package com.rf.test.website.storeFront.hybris.standardEnrollment.corp;

import org.testng.annotations.Test;
import com.rf.core.utils.CommonUtils;
import com.rf.core.website.constants.TestConstants;
import com.rf.test.website.RFWebsiteBaseTest;

public class PersonalResultsKitTest extends RFWebsiteBaseTest{

	//Hybris Project-50 CORP:Standard Enroll USD 395 Personal Results Kit, Personal Regimen REVERSE REGIMEN
	@Test
	public void testStandardEnrollmentPersonalKitReverseRegimen_50() throws InterruptedException{
		//-------------------------local variables---------------------------------------------------------
			String randomNum = CommonUtils.getCurrentTimeStamp();
			String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
			enrollmentType = TestConstants.STANDARD_ENROLLMENT;
			regimenName = TestConstants.REGIMEN_NAME_REVERSE;
			kitName = TestConstants.KIT_NAME_PERSONAL;
		//-------------------------------------------------------------------------------------------------
	
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(sponsor);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, consultantFirstName, consultantLastName+randomNum, password, addressLine1, city,state, postalCode, phoneNumber);
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
		storeFrontHomePage.selectProductAndProceedToAddToCRP();
		//storeFrontHomePage.addQuantityOfProduct("10");
		storeFrontHomePage.clickOnNextBtnAfterAddingProductAndQty();
		s_assert.assertTrue(storeFrontHomePage.isTheTermsAndConditionsCheckBoxDisplayed(), "Terms and Conditions checkbox is not visible");
		
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		s_assert.assertAll();
	}

}
