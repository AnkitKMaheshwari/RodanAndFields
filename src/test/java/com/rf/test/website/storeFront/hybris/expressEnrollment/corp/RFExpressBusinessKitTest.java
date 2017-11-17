package com.rf.test.website.storeFront.hybris.expressEnrollment.corp;

import org.testng.annotations.Test;
import com.rf.core.utils.CommonUtils;
import com.rf.core.website.constants.TestConstants;
import com.rf.test.website.RFWebsiteBaseTest;

public class RFExpressBusinessKitTest extends RFWebsiteBaseTest{
	
	//Hybris Project-94 Express Enrollment Billing Profile Billing Info - Edit
	//Hybris Project-93 Express Enrollment Billing Profile Main Account Info - Edit
	//Hybris Project-2202 CORP:Express Enrollment USD995 RF Express Business Kit, Personal Regimen REVERSE REGIMEN(CRP:N,P:Y) 
	@Test(priority=1)
	public void testExpressEnrollmentRFExpressKitReverseRegimen_2202_93_94() throws InterruptedException{
		//-------------------------local variables---------------------------------------------------------
			String randomNum = CommonUtils.getCurrentTimeStamp();
			String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
			enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
			regimenName = TestConstants.REGIMEN_NAME_REVERSE;
			kitName = TestConstants.KIT_NAME_EXPRESS;
		//-------------------------------------------------------------------------------------------------
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();  
		storeFrontHomePage.searchCID(sponsor);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.enterUserInformationForEnrollment(kitName,regimenName, enrollmentType,consultantFirstName, consultantLastName+randomNum, password, addressLine1, city,state, postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(consultantFirstName+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(consultantFirstName);
		storeFrontHomePage.clickNextButton();
		s_assert.assertTrue(storeFrontHomePage.isTheTermsAndConditionsCheckBoxDisplayed(), "Terms and Conditions checkbox is not visible");
		// verify Edit shipping info
		String newRandomNum = CommonUtils.getCurrentTimeStamp();
		storeFrontHomePage.clickOnReviewAndConfirmShippingEditBtn();
		String newFirstName = consultantFirstName+ newRandomNum;
		String newLastName = consultantLastName+newRandomNum;
		storeFrontHomePage.enterUserInformationForEnrollment(newFirstName, newLastName, password, addressLine1, city,state,postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		s_assert.assertTrue(storeFrontHomePage.isReviewAndConfirmPageContainsShippingAddress(addressLine1), "Shiiping address is not updated on Review and Confirm page after EDIT");
		s_assert.assertTrue(storeFrontHomePage.isReviewAndConfirmPageContainsFirstAndLastName(newFirstName+" "+newLastName), "First and last Name is not updated on Review and Confirm page after EDIT");

		//Verify Edit billing info
		newRandomNum = CommonUtils.getCurrentTimeStamp();
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
	
	//Hybris Project-2204 CORP:Express Enrollment Business Portfolio Kit(CRP:N,P:N)
	@Test(priority=2)
	public void testExpressEnrollmentBusinessPorfolioKitRedefineRegimen_2204() throws InterruptedException{
		//-------------------------local variables---------------------------------------------------------
			String randomNum = CommonUtils.getCurrentTimeStamp();
			String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
			enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
			regimenName = TestConstants.REGIMEN_NAME_REDEFINE;
			kitName = TestConstants.KIT_NAME_PORTFOLIO;
		//-------------------------------------------------------------------------------------------------
		
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();  
		storeFrontHomePage.searchCID(sponsor);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.enterUserInformationForEnrollment(kitName,regimenName,enrollmentType, consultantFirstName, consultantLastName+randomNum,  password, addressLine1, city,state, postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.uncheckCRPCheckBox();
		storeFrontHomePage.uncheckPulseCheckBox();
		storeFrontHomePage.clickNextButton();
//		s_assert.assertTrue(storeFrontHomePage.verifySubsribeToPulseCheckBoxIsSelected(), "Subscribe to pulse checkbox not selected");
//		s_assert.assertTrue(storeFrontHomePage.verifyEnrollToCRPCheckBoxIsSelected(), "Enroll to CRP checkbox not selected");
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		s_assert.assertAll();
	}
}
