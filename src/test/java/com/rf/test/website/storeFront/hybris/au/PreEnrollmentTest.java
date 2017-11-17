package com.rf.test.website.storeFront.hybris.au;

import org.testng.annotations.Test;

import com.rf.core.website.constants.TestConstants;
import com.rf.pages.website.au.softLaunch.SoftLaunchHomePage;
import com.rf.test.website.RFWebsiteBaseTest;

public class PreEnrollmentTest extends RFWebsiteBaseTest{
	SoftLaunchHomePage sfSoftLaunchHomePage = new SoftLaunchHomePage(driver);

	//TC-59 Verify Pre enrollee section
	@Test
	public void testVerifyPreEnrolleeSection_59(){
		String currentUrl = null;
		String callingAllPreEnroleesText = "It’s time to take the next step toward launching your Rodan + Fields";
		String callingAllPreEnroleesTextFromUI  = null;
		sfSoftLaunchHomePage.clickTheLinkOfHeaderSection("Business");
		currentUrl = sfSoftLaunchHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains("business"), "Expected business page is not present");
		s_assert.assertTrue(sfSoftLaunchHomePage.isTextPresentOnPages("READY TO START YOUR R+F JOURNEY?"), "READY TO START YOUR R+F JOURNEY? text is not present");
		callingAllPreEnroleesTextFromUI = sfSoftLaunchHomePage.getCallingAllPreEnroleesText();
		s_assert.assertTrue(callingAllPreEnroleesTextFromUI.contains(callingAllPreEnroleesText), callingAllPreEnroleesText+" is not present on UI at business page");
		s_assert.assertAll();
	}

	//TC-61 Verify launch timeline
	@Test
	public void testVerifyLaunchTimeline_61(){
		s_assert.assertTrue(sfSoftLaunchHomePage.isLoginIconPresent(), "Login icon is not present");
		s_assert.assertAll();
	}

	// TC-101 Verify  'Share your URL' should not be link anymore during 'Conversion'
	@Test
	public void testVerifyShareYourURLShouldNotBeLinkAnymoreDuringConversion_101(){
		String currentUrl = null;
		sfSoftLaunchHomePage.loginAsPreEnrollee(TestConstants.USERNAME_AU, password);
		s_assert.assertTrue(sfSoftLaunchHomePage.isUserLoggedInSuccessfully(), "User is not logged in successfully");
		sfSoftLaunchHomePage.clickTheLinkOfHeaderSection("ME");
		currentUrl = sfSoftLaunchHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains("me"), "Expected me page is not present");
		s_assert.assertTrue(sfSoftLaunchHomePage.isShareYourURLIsNotALink(), "Share my url is present in a link form");
		s_assert.assertAll();
	}

	//TC-102 Verify sponsor Relationship/Connection upon selecting the downline cont
	@Test
	public void testVerifySponsorRelationshipUponSelectingTheDownlineConsultant_102(){
		String totalPreEnroleeInMyTeamSection = "Total Pre-Enrolee(s) in my team";
		String consultantEnrolledSection = "Consultant(s) - Enrolled";
		String preEnroleeNotYetEnrolledSection = "Pre-enrolee(s) - Not yet enrolled";
		String currentUrl = null;
		sfSoftLaunchHomePage.loginAsPreEnrollee(TestConstants.USERNAME_AU, password);
		s_assert.assertTrue(sfSoftLaunchHomePage.isUserLoggedInSuccessfully(), "User is not logged in successfully");
		sfSoftLaunchHomePage.clickTheLinkOfHeaderSection("ME");
		currentUrl = sfSoftLaunchHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains("me"), "Expected me page is not present");
		s_assert.assertTrue(sfSoftLaunchHomePage.isTextPresentOnPages("MY TEAM"),"My TEAM text is not present");
		s_assert.assertTrue(sfSoftLaunchHomePage.isValuePresentUnderMyTeamSection(totalPreEnroleeInMyTeamSection), totalPreEnroleeInMyTeamSection+" is not having any count");
		s_assert.assertTrue(sfSoftLaunchHomePage.isValuePresentUnderMyTeamSection(consultantEnrolledSection), consultantEnrolledSection+" is not having any count");
		s_assert.assertTrue(sfSoftLaunchHomePage.isValuePresentUnderMyTeamSection(preEnroleeNotYetEnrolledSection), preEnroleeNotYetEnrolledSection+" is not having any count");
		s_assert.assertTrue(sfSoftLaunchHomePage.isTextPresentOnPages("MY SPONSOR"),"MY SPONSOR section is not present");
		s_assert.assertTrue(sfSoftLaunchHomePage.isSponsorNamePresent(), "sponsor name is not present under sponsor section");
		s_assert.assertAll();
	}

	//TC-127 AUS-60 : Verify the user should no longer have the ability to pre-enrol till August 31, 2017 at 10:00am AWST (19:00 PST August 30, 2017)
	@Test
	public void testVerifyTheUserShouldNoLongerHaveTheAbilityToPreEnrollTill31Aug_127(){
		String currentUrl = null;
		sfSoftLaunchHomePage.clickTheLinkOfHeaderSection("Business");
		currentUrl = sfSoftLaunchHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains("business"), "Expected business page is not present");
		s_assert.assertTrue(sfSoftLaunchHomePage.isWhatIfTextPresent(), "What if text is not present at business page");
		s_assert.assertTrue(sfSoftLaunchHomePage.isTextPresentOnPages("PREPARE FOR")&& sfSoftLaunchHomePage.isTextPresentOnPages("LAUNCH"), "Prepare for launch text is not present at business page");
		s_assert.assertAll();
	}

	// Verify Account info
	@Test
	public void testVerifyAccountInfo(){
		String currentUrl = null;
		sfSoftLaunchHomePage.loginAsPreEnrollee(TestConstants.USERNAME_AU, password);
		s_assert.assertTrue(sfSoftLaunchHomePage.isUserLoggedInSuccessfully(), "User is not logged in successfully");
		sfSoftLaunchHomePage.hoverOnProfileNameAndClickOnSettings();
		currentUrl = sfSoftLaunchHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains("setting"), "Expected profile page is not present");
		s_assert.assertTrue(sfSoftLaunchHomePage.isTextPresentOnPages("MY PROFILE"),"MY PROFILE text is not present");
		s_assert.assertTrue(sfSoftLaunchHomePage.isFirstNamePresent(),"First name is not present");
		s_assert.assertTrue(sfSoftLaunchHomePage.isLastNamePresent(),"Last name is not present");
		s_assert.assertTrue(sfSoftLaunchHomePage.isEmailIdPresent(),"email id is not present");
		s_assert.assertTrue(sfSoftLaunchHomePage.isPhoneNumberPresent(),"phone number is not present");
		s_assert.assertTrue(sfSoftLaunchHomePage.isAddress1Present(),"Address 1 is not present");
		s_assert.assertTrue(sfSoftLaunchHomePage.isCityPresent(),"city is not present");
		s_assert.assertTrue(sfSoftLaunchHomePage.isStatePresent(),"state is not present");
		s_assert.assertTrue(sfSoftLaunchHomePage.isPostalCodePresent(),"postal code is not present");
		s_assert.assertAll();
	}
	
	// Verify Change My Password section
	@Test
	public void testVerifyChangeMyPasswordSection(){
		String currentUrl = null;
		sfSoftLaunchHomePage.loginAsPreEnrollee(TestConstants.USERNAME_AU, password);
		s_assert.assertTrue(sfSoftLaunchHomePage.isUserLoggedInSuccessfully(), "User is not logged in successfully");
		sfSoftLaunchHomePage.hoverOnProfileNameAndClickOnSettings();
		currentUrl = sfSoftLaunchHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains("setting"), "Expected profile page is not present");
		s_assert.assertTrue(sfSoftLaunchHomePage.isTextPresentOnPages("CHANGE MY PASSWORD"),"CHANGE MY PASSWORD text is not present");
		s_assert.assertTrue(sfSoftLaunchHomePage.isLabelPresent("Current Password"),"Current Password label is not present");
		s_assert.assertTrue(sfSoftLaunchHomePage.isLabelPresent("New Password"),"New Password label is not present");
		s_assert.assertTrue(sfSoftLaunchHomePage.isLabelPresent("Confirm Password"),"Confirm Password label is not present");
		s_assert.assertAll();
	}

	// Verify logout functionality
	@Test
	public void verifyLogoutFunctionality(){
		sfSoftLaunchHomePage.loginAsPreEnrollee(TestConstants.USERNAME_AU, password);
		s_assert.assertTrue(sfSoftLaunchHomePage.isUserLoggedInSuccessfully(), "User is not logged in successfully");
		sfSoftLaunchHomePage.logout();
		s_assert.assertTrue(sfSoftLaunchHomePage.isLoginIconPresent(), "Login icon is not present, User is not logged out");
		s_assert.assertAll();
	}
}

