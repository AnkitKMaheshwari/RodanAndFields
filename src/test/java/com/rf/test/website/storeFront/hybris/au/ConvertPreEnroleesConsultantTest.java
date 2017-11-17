package com.rf.test.website.storeFront.hybris.au;

import org.testng.annotations.Test;
import com.rf.core.utils.CommonUtils;
import com.rf.core.website.constants.TestConstants;
import com.rf.pages.website.au.softLaunch.SoftLaunchHomePage;
import com.rf.test.website.RFWebsiteBaseTest;

public class ConvertPreEnroleesConsultantTest extends RFWebsiteBaseTest{
	SoftLaunchHomePage sfSoftLaunchHomePage = new SoftLaunchHomePage(driver);

	@Test(dataProvider="rfTestData")
	public void testPreEnrolleConversion(String emailId) throws InterruptedException{
		String randomNum = CommonUtils.getCurrentTimeStamp();
		String kitName = null;
		String regimenName = null;
		String enrollmentType = null;
		String successMessage = null; 
//		String emailId=TestConstants.CONSULTANT_EMAIL_ID_AU;
		kitName = TestConstants.KIT_NAME_BIG_BUSINESS; 	
		enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
		regimenName = TestConstants.REGIMEN_NAME_REDEFINE;
		sfSoftLaunchHomePage.loginAsPreEnrollee(emailId, password);
		s_assert.assertTrue(sfSoftLaunchHomePage.isUserLoggedInSuccessfully(), "User is not logged in successfully");
		sfSoftLaunchHomePage.clickEnrollNowBtn();
		sfSoftLaunchHomePage.selectEnrollmentKitPage(kitName, regimenName);
		sfSoftLaunchHomePage.chooseEnrollmentOption(enrollmentType);
		sfSoftLaunchHomePage.enterPassword(password);
		sfSoftLaunchHomePage.enterConfirmPassword(password);
		sfSoftLaunchHomePage.clickNextButton();
		sfSoftLaunchHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		sfSoftLaunchHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		sfSoftLaunchHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		sfSoftLaunchHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		sfSoftLaunchHomePage.clickNextButton();
		sfSoftLaunchHomePage.selectAllTermsAndConditionsChkBox();
		sfSoftLaunchHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		sfSoftLaunchHomePage.clickOnConfirmAutomaticPayment();
		successMessage = sfSoftLaunchHomePage.getCongratulationsMessage();
		s_assert.assertTrue(successMessage.contains("Congratulations") && successMessage.contains("Welcome to Rodan + Fields"), "Expected congrats message should contain Congratulations and Welcome to Rodan + Fields but actual on UI is "+successMessage);
		s_assert.assertAll();
	}

	/**
	 * With CRP without Pulse
	 * @throws InterruptedException
	 */
	@Test(dataProvider="rfTestData")
	public void testConvertPreEnrolleSEWCRPWOP(String emailId) throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String kitName = null;
		String regimenName = null;
		String enrollmentType = null;
		String successMessage = null; 
		kitName = TestConstants.KIT_NAME_BIG_BUSINESS; 	
		enrollmentType = TestConstants.STANDARD_ENROLLMENT;
		regimenName = TestConstants.REGIMEN_NAME_REDEFINE;
		sfSoftLaunchHomePage.loginAsPreEnrollee(emailId, password);
		s_assert.assertTrue(sfSoftLaunchHomePage.isUserLoggedInSuccessfully(), "User is not logged in successfully");
		sfSoftLaunchHomePage.clickEnrollNowBtn();
		sfSoftLaunchHomePage.selectEnrollmentKitPage(kitName, regimenName);
		sfSoftLaunchHomePage.chooseEnrollmentOption(enrollmentType);
		sfSoftLaunchHomePage.enterPassword(password);
		sfSoftLaunchHomePage.enterConfirmPassword(password);
		sfSoftLaunchHomePage.enterAddressLine1(addressLine1);
		sfSoftLaunchHomePage.enterCity(city);
		sfSoftLaunchHomePage.selectProvince(state);
		sfSoftLaunchHomePage.enterPostalCode(postalCode);
		sfSoftLaunchHomePage.clickNextButton();
		sfSoftLaunchHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		sfSoftLaunchHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		sfSoftLaunchHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		sfSoftLaunchHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		sfSoftLaunchHomePage.clickNextButton();
		sfSoftLaunchHomePage.checkCRPCheckBox();
		sfSoftLaunchHomePage.clickNextButton();
		sfSoftLaunchHomePage.selectProductAndProceedToAddToCRP();
		sfSoftLaunchHomePage.clickOnNextBtnAfterAddingProductAndQty();
		sfSoftLaunchHomePage.selectAllTermsAndConditionsChkBox();
		sfSoftLaunchHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		sfSoftLaunchHomePage.clickOnConfirmAutomaticPayment();
		successMessage = sfSoftLaunchHomePage.getCongratulationsMessage();
		s_assert.assertTrue(successMessage.contains("Congratulations") && successMessage.contains("Welcome to Rodan + Fields"), "Expected congrats message should contain Congratulations and Welcome to Rodan + Fields but actual on UI is "+successMessage);
		s_assert.assertAll();
	}

	/**
	 * With CRP with Pulse
	 * @throws InterruptedException
	 */
	@Test(dataProvider="rfTestData")
	public void testConvertPreEnrolleSEWCRPWP(String emailId) throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String kitName = null;
		String regimenName = null;
		String enrollmentType = null;
		String successMessage = null; 
		kitName = TestConstants.KIT_NAME_EXPRESS; 	
		enrollmentType = TestConstants.STANDARD_ENROLLMENT;
		regimenName = TestConstants.REGIMEN_NAME_REDEFINE;
		sfSoftLaunchHomePage.loginAsPreEnrollee(emailId, password);
		s_assert.assertTrue(sfSoftLaunchHomePage.isUserLoggedInSuccessfully(), "User is not logged in successfully");
		sfSoftLaunchHomePage.clickEnrollNowBtn();
		sfSoftLaunchHomePage.selectEnrollmentKitPage(kitName, regimenName);
		sfSoftLaunchHomePage.chooseEnrollmentOption(enrollmentType);
		sfSoftLaunchHomePage.enterPassword(password);
		sfSoftLaunchHomePage.enterConfirmPassword(password);
		sfSoftLaunchHomePage.enterAddressLine1(addressLine1);
		sfSoftLaunchHomePage.enterCity(city);
		sfSoftLaunchHomePage.selectProvince(state);
		sfSoftLaunchHomePage.enterPostalCode(postalCode);
		sfSoftLaunchHomePage.clickNextButton();
		sfSoftLaunchHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		sfSoftLaunchHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		sfSoftLaunchHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		sfSoftLaunchHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		sfSoftLaunchHomePage.clickNextButton();
		sfSoftLaunchHomePage.checkCRPCheckBox();
		sfSoftLaunchHomePage.checkPulseCheckBox();
		sfSoftLaunchHomePage.clickNextButton();
		sfSoftLaunchHomePage.selectProductAndProceedToAddToCRP();
		sfSoftLaunchHomePage.clickOnNextBtnAfterAddingProductAndQty();
		sfSoftLaunchHomePage.selectAllTermsAndConditionsChkBox();
		sfSoftLaunchHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		sfSoftLaunchHomePage.clickOnConfirmAutomaticPayment();
		successMessage = sfSoftLaunchHomePage.getCongratulationsMessage();
		s_assert.assertTrue(successMessage.contains("Congratulations") && successMessage.contains("Welcome to Rodan + Fields"), "Expected congrats message should contain Congratulations and Welcome to Rodan + Fields but actual on UI is "+successMessage);
		s_assert.assertAll();
	}
	
	/**
	 * Without CRP without Pulse
	 * @throws InterruptedException
	 */
	@Test(dataProvider="rfTestData")
	public void testConvertPreEnrolleSEWOCRPWOP(String emailId) throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String kitName = null;
		String regimenName = null;
		String enrollmentType = null;
		String successMessage = null; 
		kitName = TestConstants.KIT_NAME_PERSONAL; 	
		enrollmentType = TestConstants.STANDARD_ENROLLMENT;
		regimenName = TestConstants.REGIMEN_NAME_REDEFINE;
		sfSoftLaunchHomePage.loginAsPreEnrollee(emailId, password);
		s_assert.assertTrue(sfSoftLaunchHomePage.isUserLoggedInSuccessfully(), "User is not logged in successfully");
		sfSoftLaunchHomePage.clickEnrollNowBtn();
		sfSoftLaunchHomePage.selectEnrollmentKitPage(kitName, regimenName);
		sfSoftLaunchHomePage.chooseEnrollmentOption(enrollmentType);
		sfSoftLaunchHomePage.enterPassword(password);
		sfSoftLaunchHomePage.enterConfirmPassword(password);
		sfSoftLaunchHomePage.clickNextButton();
		sfSoftLaunchHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		sfSoftLaunchHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		sfSoftLaunchHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		sfSoftLaunchHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		sfSoftLaunchHomePage.clickNextButton();
//		sfSoftLaunchHomePage.uncheckPulseAndCRPEnrollment();
//		sfSoftLaunchHomePage.checkCRPCheckBox();
		sfSoftLaunchHomePage.clickNextButton();
//		sfSoftLaunchHomePage.selectProductAndProceedToAddToCRP();
//		sfSoftLaunchHomePage.clickOnNextBtnAfterAddingProductAndQty();
		sfSoftLaunchHomePage.selectAllTermsAndConditionsChkBox();
		sfSoftLaunchHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		sfSoftLaunchHomePage.clickOnConfirmAutomaticPayment();
		successMessage = sfSoftLaunchHomePage.getCongratulationsMessage();
		s_assert.assertTrue(successMessage.contains("Congratulations") && successMessage.contains("Welcome to Rodan + Fields"), "Expected congrats message should contain Congratulations and Welcome to Rodan + Fields but actual on UI is "+successMessage);
		s_assert.assertAll();
	}
	
	/**
	 * Without CRP with Pulse
	 * @throws InterruptedException
	 */
	@Test(dataProvider="rfTestData")
	public void testConvertPreEnrolleSEWOCRPWP(String emailId) throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String kitName = null;
		String regimenName = null;
		String enrollmentType = null;
		String successMessage = null; 
		kitName = TestConstants.KIT_NAME_BIG_BUSINESS; 	
		enrollmentType = TestConstants.STANDARD_ENROLLMENT;
		regimenName = TestConstants.REGIMEN_NAME_REDEFINE;
		sfSoftLaunchHomePage.loginAsPreEnrollee(emailId, password);
		s_assert.assertTrue(sfSoftLaunchHomePage.isUserLoggedInSuccessfully(), "User is not logged in successfully");
		sfSoftLaunchHomePage.clickEnrollNowBtn();
		sfSoftLaunchHomePage.selectEnrollmentKitPage(kitName, regimenName);
		sfSoftLaunchHomePage.chooseEnrollmentOption(enrollmentType);
		sfSoftLaunchHomePage.enterPassword(password);
		sfSoftLaunchHomePage.enterConfirmPassword(password);
//		sfSoftLaunchHomePage.enterConfirmPassword(password);
		sfSoftLaunchHomePage.enterAddressLine1(addressLine1);
		sfSoftLaunchHomePage.enterCity(city);
		sfSoftLaunchHomePage.selectProvince(state);
		sfSoftLaunchHomePage.enterPostalCode(postalCode);
		sfSoftLaunchHomePage.clickNextButton();
		sfSoftLaunchHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		sfSoftLaunchHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		sfSoftLaunchHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		sfSoftLaunchHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		sfSoftLaunchHomePage.clickNextButton();
//		sfSoftLaunchHomePage.uncheckPulseAndCRPEnrollment();
		sfSoftLaunchHomePage.checkPulseCheckBox();
		sfSoftLaunchHomePage.clickNextButton();
		sfSoftLaunchHomePage.selectAllTermsAndConditionsChkBox();
		sfSoftLaunchHomePage.clickOnChargeMyCardAndEnrollMeBtn();
		sfSoftLaunchHomePage.clickOnConfirmAutomaticPayment();
		successMessage = sfSoftLaunchHomePage.getCongratulationsMessage();
		s_assert.assertTrue(successMessage.contains("Congratulations") && successMessage.contains("Welcome to Rodan + Fields"), "Expected congrats message should contain Congratulations and Welcome to Rodan + Fields but actual on UI is "+successMessage);
		s_assert.assertAll();
	}

}

