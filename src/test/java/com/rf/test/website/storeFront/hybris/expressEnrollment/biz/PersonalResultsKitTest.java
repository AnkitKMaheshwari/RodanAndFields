package com.rf.test.website.storeFront.hybris.expressEnrollment.biz;

import org.testng.annotations.Test;
import com.rf.core.utils.CommonUtils;
import com.rf.core.website.constants.TestConstants;
import com.rf.test.website.RFWebsiteBaseTest;

public class PersonalResultsKitTest extends RFWebsiteBaseTest{

	//Hybris Project-67 Express EnrollmentTest USD 395 Personal Results Kit, Personal Regimen UNBLEMISH REGIMEN  
	@Test 
	public void testExpressEnrollmentPersonalResultsKitUnblemishRegimen_67() throws InterruptedException{
		//-------------------------local variables---------------------------------------------------------
			String randomNum = CommonUtils.getCurrentTimeStamp();
			String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
			enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
			regimenName = TestConstants.REGIMEN_NAME_UNBLEMISH;
			kitName = TestConstants.KIT_NAME_PERSONAL;
		//-------------------------------------------------------------------------------------------------
		
		storeFrontHomePage.openBizPWSSite(country, env);
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();  
		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, consultantFirstName, consultantLastName+randomNum, password, addressLine1, city,state, postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(consultantFirstName);
		storeFrontHomePage.selectNewBillingCardExpirationDate();
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		s_assert.assertAll(); 
	}

}
