package com.rf.test.website.storeFront.hybris.expressEnrollment.biz;

import org.testng.annotations.Test;
import com.rf.core.utils.CommonUtils;
import com.rf.core.website.constants.TestConstants;
import com.rf.test.website.RFWebsiteBaseTest;

public class BigBusinessLaunchKitTest extends RFWebsiteBaseTest{

	//Hybris Project-96 Express Enrollment Billing Info Shipping Info - New
	//Hybris Project-95 Express Enrollment Billing Profile Main Account Info - New
	//Hybris Project-62 Express EnrollmentTest USD 695 Big Business Launch Kit, Personal Regimen REVERSE REGIMEN
	@Test
	public void testExpressEnrollmentBusinessKitReverseRegimen_62_95_96() throws InterruptedException{
		//-------------------------local variables---------------------------------------------------------
			String randomNum = CommonUtils.getCurrentTimeStamp();
			String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
			enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
			regimenName = TestConstants.REGIMEN_NAME_REVERSE;
			kitName = TestConstants.KIT_NAME_EXPRESS;
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
		storeFrontHomePage.enterNameAsItAppearsOnCard(consultantFirstName);
		storeFrontHomePage.clickNextButton();
		s_assert.assertTrue(storeFrontHomePage.isTheTermsAndConditionsCheckBoxDisplayed(), "Terms and Conditions checkbox is not visible");
		// verify Edit shipping info
		String newRandomNum =CommonUtils.getCurrentTimeStamp();
		storeFrontHomePage.clickOnReviewAndConfirmShippingEditBtn();
		String newFirstName = consultantFirstName+newRandomNum;
		String newLastName = consultantLastName+newRandomNum;
		storeFrontHomePage.enterUserInformationForEnrollment(newFirstName, newLastName, password, addressLine1, city,state,postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		s_assert.assertTrue(storeFrontHomePage.isReviewAndConfirmPageContainsShippingAddress(addressLine1), "Shiiping address is not updated on Review and Confirm page after EDIT");
		s_assert.assertTrue(storeFrontHomePage.isReviewAndConfirmPageContainsFirstAndLastName(newFirstName+" "+newLastName), "First and last Name is not updated on Review and Confirm page after EDIT");

		//Verify Edit billing info
		newRandomNum =CommonUtils.getCurrentTimeStamp();
		storeFrontHomePage.clickOnReviewAndConfirmBillingEditBtn();
		storeFrontHomePage.enterEditedCardNumber(TestConstants.CARD_NUMBER);
		String editedBillingProfileName = consultantFirstName+newRandomNum;
		storeFrontHomePage.enterNameOnCard(editedBillingProfileName+" "+consultantLastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);;
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();		
		storeFrontHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		s_assert.assertAll(); 
	}
}
