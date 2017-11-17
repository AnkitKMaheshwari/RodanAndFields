package com.rf.test.website.storeFront.hybris;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rf.core.utils.CommonUtils;
import com.rf.core.utils.DBUtil;
import com.rf.core.utils.QueryUtils;
import com.rf.core.website.constants.TestConstants;
import com.rf.core.website.constants.dbQueries.DBQueries_RFO;
import com.rf.pages.website.storeFront.StoreFrontAccountInfoPage;
import com.rf.pages.website.storeFront.StoreFrontConsultantPage;
import com.rf.pages.website.storeFront.StoreFrontHomePage;
import com.rf.pages.website.storeFront.StoreFrontPCUserPage;
import com.rf.pages.website.storeFront.StoreFrontUpdateCartPage;
import com.rf.test.website.RFWebsiteBaseTest;

public class AccountTest extends RFWebsiteBaseTest{
	private static final Logger logger = LogManager
			.getLogger(AccountTest.class.getName());

	/**
	 * login by: Consultant
	 * Validations: 
	 * MyAccount Phone Numbers
	 * * show error for 11 digit phone number if starts with other than 1
	 *  *accept 11 digit phone number if starts with 1
	 *  *accept 10 digit phone number
	 * @throws InterruptedException
	 */
	// Hybris Phase 2-2241 :: version 1 :: Verify the various field validations
	@Test(priority=1)
	public void testPhoneNumberFieldValidationForConsultant_2241() throws InterruptedException{
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyAccountInfoPageIsDisplayed(),"Account Info page has not been displayed");
		storeFrontAccountInfoPage.enterMainPhoneNumber(TestConstants.CONSULTANT_INVALID_11_DIGIT_MAIN_PHONE_NUMBER);
		if(driver.getCountry().equalsIgnoreCase("AU")){
			s_assert.assertTrue(storeFrontAccountInfoPage.verifyValidationMessageOfPhoneNumber(TestConstants.CONSULTANT_VALIDATION_MESSAGE_OF_MAIN_PHONE_NUMBER_AU),"Validation Message has not been displayed ");
		}else{
			s_assert.assertTrue(storeFrontAccountInfoPage.verifyValidationMessageOfPhoneNumber(TestConstants.CONSULTANT_VALIDATION_MESSAGE_OF_MAIN_PHONE_NUMBER),"Validation Message has not been displayed ");
		}
		storeFrontAccountInfoPage.enterMainPhoneNumber(TestConstants.CONSULTANT_VALID_11_DIGITMAIN_PHONE_NUMBER);
		s_assert.assertFalse(storeFrontAccountInfoPage.verifyValidationMessageOfPhoneNumber(TestConstants.CONSULTANT_VALIDATION_MESSAGE_OF_MAIN_PHONE_NUMBER),"Validation Message has been displayed For correct Phone Number");
		storeFrontAccountInfoPage.enterMainPhoneNumber(TestConstants.CONSULTANT_VALID_10_DIGIT_MAIN_PHONE_NUMBER);
		s_assert.assertFalse(storeFrontAccountInfoPage.verifyValidationMessageOfPhoneNumber(TestConstants.CONSULTANT_VALIDATION_MESSAGE_OF_MAIN_PHONE_NUMBER),"Validation Message has been displayed for ten digit phone number");
		s_assert.assertAll();
	}

	/**
	 * Login: Consultant and PC
	 * validations:
	 * * Welcome dropdown
	 * @throws InterruptedException
	 */
	//Hybris Phase 2-1977 :: verify with Valid credentials and Logout
	@Test(priority=2)
	public void testVerifyLogoutwithValidCredentials_1977() throws InterruptedException{
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		s_assert.assertTrue(storeFrontHomePage.isWelcomeDropDownPresent(),"Consultant not logged in successfully on corp");
		logout();
		s_assert.assertFalse(storeFrontHomePage.isWelcomeDropDownPresent(),"Consultant not logged out successfully on corp");
		storeFrontHomePage.openBizPWSSite(country, env);
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		s_assert.assertTrue(storeFrontHomePage.isWelcomeDropDownPresent(),"Consultant not logged in successfully on biz");
		logout();
		s_assert.assertFalse(storeFrontHomePage.isWelcomeDropDownPresent(),"Consultant not logged out successfully on biz");
		storeFrontHomePage.openComPWSSite(country, env);		
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfullyOnPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,country,countryId,env,TestConstants.SITE_TYPE_COM),"Not able to login to the application from PC");	
		s_assert.assertTrue(storeFrontHomePage.isWelcomeDropDownPresent(),"PC not logged in successfully on com");
		logout();
		s_assert.assertFalse(storeFrontHomePage.isWelcomeDropDownPresent(),"PC not logged out successfully on com");
		storeFrontHomePage.openBizPWSSite(country, env);
		storeFrontPCUserPage = storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		s_assert.assertTrue(storeFrontHomePage.isWelcomeDropDownPresent(),"PC not logged in successfully on biz");
		logout();
		s_assert.assertFalse(storeFrontHomePage.isWelcomeDropDownPresent(),"PC not logged out successfully on biz");
		s_assert.assertAll();
	}

	//Hybris Project-2512 :: Version : 1 :: Username validations.
	@Test (priority=3)
	public void testUsernameValidations_2512() throws InterruptedException {
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyAccountInfoPageIsDisplayed(),"Account Info page has not been displayed");
		storeFrontAccountInfoPage.enterUserName(TestConstants.CONSULTANT_USERNAME_BELOW_6_DIGITS);
		storeFrontAccountInfoPage.clickSaveAccountPageInfo();
		s_assert.assertTrue(storeFrontAccountInfoPage.isGlobalErrorMessageDisplayed()||storeFrontAccountInfoPage.isUsernameValidationErrorMsgPresent(),"Validation for username less than 6  characters IS NOT PRESENT");
		storeFrontAccountInfoPage.enterUserName(TestConstants.CONSULTANT_USERNAME_MORE_THAN_6_DIGITS);
		storeFrontAccountInfoPage.clickSaveAccountPageInfo();
		s_assert.assertTrue(storeFrontAccountInfoPage.isGlobalErrorMessageDisplayed()||storeFrontAccountInfoPage.isUsernameValidationErrorMsgPresent(),"Validation for username more than 6 characters with space IS NOT PRESENT");
		storeFrontAccountInfoPage.enterUserName(TestConstants.CONSULTANT_USERNAME_MORE_THAN_6_SPECIAL_CHARS);
		storeFrontAccountInfoPage.clickSaveAccountPageInfo();
		s_assert.assertTrue(storeFrontAccountInfoPage.isGlobalErrorMessageDisplayed()||storeFrontAccountInfoPage.isUsernameValidationErrorMsgPresent(),"Validation for username more than 6 special characters IS NOT PRESENT");
		storeFrontAccountInfoPage.enterUserName(TestConstants.CONSULTANT_USERNAME_MORE_THAN_6_ALPHANUMERIC_CHARS_WITH_SPCLCHAR);
		storeFrontAccountInfoPage.clickSaveAccountPageInfo();
		s_assert.assertTrue(storeFrontAccountInfoPage.isGlobalErrorMessageDisplayed()||storeFrontAccountInfoPage.isUsernameValidationErrorMsgPresent(),"Validation for username more than 6 alphanumeric characters IS NOT PRESENT");
		s_assert.assertAll();
	}

	/**
	 * Login: Consultant with pulse
	 * Validation:
	 * * Verify that pulse application is opening in separate tab while clicking on Check My Pulse form account dropdown 
	 * @throws InterruptedException
	 */
	//Hybris Project-1976:Navigation to Consultant Pulse page from My Account
	@Test(priority=5)
	public void testAutoshipModuleCheckMyPulseUI_1976() throws InterruptedException  {
		String consultantEmailID = QueryUtils.getRandomActiveConsultantWithPWSFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,env,".biz",country,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantWithPWSLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,env,".biz",country,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontConsultantPage.clickCheckMyPulseLinkPresentOnWelcomeDropDown();
		//pass driver control to child window
		storeFrontConsultantPage.switchToChildWindow();
		//validate home page for pulse
		s_assert.assertTrue(storeFrontConsultantPage.validatePulseHomePage(),"Home Page for pulse is not displayed");
		//s_assert.assertTrue(storeFrontAccountInfoPage.validateSubscribeToPulse(),"pulse subscription is not Present for the user");
		storeFrontConsultantPage.switchToPreviousTab();
		s_assert.assertAll();
	}

	/**
	 * Login: Consultant
	 * Validations:
	 * * Change password from my account and verify login working for the new password(then reset back)
	 * 
	 * @throws InterruptedException
	 */
	// Hybris Project-3009 :: Version : 1 :: Reset the password from the storefront and check login with new password
	@Test (priority=6)
	public void testResetPasswordFromStorefrontAndRecheckLogin_3009() throws InterruptedException {
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyAccountInfoPageIsDisplayed(),"Account Info page has not been displayed");
		storeFrontAccountInfoPage.enterOldPassword(password);
		storeFrontAccountInfoPage.enterNewPassword(TestConstants.CONSULTANT_NEW_PASSWORD);
		storeFrontAccountInfoPage.enterConfirmedPassword(TestConstants.CONSULTANT_NEW_PASSWORD);
		storeFrontAccountInfoPage.clickSaveAccountPageInfo();
		logout();
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID,TestConstants.CONSULTANT_NEW_PASSWORD);   
		s_assert.assertTrue(storeFrontConsultantPage.verifyConsultantPage(),"Consultant Page doesn't contain Welcome User Message");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyAccountInfoPageIsDisplayed(),"Account Info page has not been displayed");
		storeFrontAccountInfoPage.enterOldPassword(TestConstants.CONSULTANT_NEW_PASSWORD);
		storeFrontAccountInfoPage.enterNewPassword(password);
		storeFrontAccountInfoPage.enterConfirmedPassword(password);
		storeFrontAccountInfoPage.clickSaveAccountPageInfo();
		s_assert.assertAll(); 
	}

	/**
	 * Login: Consultant
	 * Validations:
	 * * Verify Accept, Cancel and X working fine for allow my spouse option on account info
	 * @throws InterruptedException
	 */
	//Hybris Project-86:Verify editing Spouse details under Account Info
	@Test(priority=7)
	public void testEditAllowMySpouseInMyAccount_86() throws InterruptedException {
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyAccountInfoPageIsDisplayed(),"Account Info page has not been displayed");
		storeFrontAccountInfoPage.checkAllowMySpouseCheckBox();
		s_assert.assertTrue(storeFrontAccountInfoPage.validateEnterSpouseDetailsAndAccept(),"Accept button not working in the popup");
		storeFrontAccountInfoPage.checkAllowMySpouseCheckBox();
		storeFrontAccountInfoPage.checkAllowMySpouseCheckBox();
		s_assert.assertTrue(storeFrontAccountInfoPage.validateClickCancelOnProvideAccessToSpousePopup(),"Cancel button not working in the popup");
		storeFrontAccountInfoPage.checkAllowMySpouseCheckBox();
		s_assert.assertTrue(storeFrontAccountInfoPage.validateClickXOnProvideAccessToSpousePopup(),"X button not working in the popup");
		s_assert.assertAll();
	}

	/**
	 * Login: Consultant
	 * Verifications:-
	 * * Login should fail from both email and username after user is terminated from store front
	 * @throws InterruptedException
	 */
	//Hybris Project-4281 :: Version : 1 :: Terminate User and Login with User Name
	@Test (priority=8)
	public void terminateUserAndLoginWithSameUsername_4281() throws InterruptedException{
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String userName="user"+randomNumber+TestConstants.EMAIL_ADDRESS_SUFFIX;
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyAccountInfoPageIsDisplayed(),"Account Info page has not been displayed");
		storeFrontAccountInfoPage.enterUserName(userName);
		storeFrontAccountInfoPage.clickSaveAccountPageInfo();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountTerminationPage = storeFrontAccountInfoPage.clickTerminateMyAccount();
		storeFrontAccountTerminationPage.fillTheEntriesAndClickOnSubmitDuringTermination();
		s_assert.assertTrue(storeFrontAccountTerminationPage.validateConfirmAccountTerminationPopUp(), "confirm account termination pop up is not displayed");
		storeFrontAccountTerminationPage.clickConfirmTerminationBtn();
		s_assert.assertFalse(storeFrontAccountTerminationPage.validateConfirmAccountTerminationPopUp(), "confirm account termination pop up is still displayed");
		storeFrontAccountTerminationPage.clickOnCloseWindowAfterTermination();
		storeFrontHomePage.clickOnCountryAtWelcomePage();
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		s_assert.assertTrue(storeFrontHomePage.isCurrentURLShowsError(),"Terminated User doesn't get Login failed using email ID");  
		navigateToStoreFrontBaseURL();
		storeFrontHomePage.loginAsConsultant(userName, password);
		s_assert.assertTrue(storeFrontHomePage.isCurrentURLShowsError(),"Terminated User doesn't get Login failed using username");  
		s_assert.assertAll();
	}

	/**
	 * Login: Consultant
	 * Validations:
	 * * Cancel Subscription and verify
	 * * Subscribe Pulse and verify
	 */
	// Hybris Project-2233:Verify that user can cancel Pulse subscription through my account.
	//Hybris Project-2134:EC-789- To Verify subscribe to pulse
	@Test(priority=9)
	public void testVerifyUserCanCancelPulseSubscriptionThroughMyAccount_2233_2134() throws InterruptedException {
		String consultantEmailID = QueryUtils.getRandomActiveConsultantWithPWSFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,env,".com",country,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantWithPWSLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,env,".com",country,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage=storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountInfoPage.clickOnAutoShipStatus();
		if(storeFrontAccountInfoPage.validatePulseCancelled()==true){
			storeFrontAccountInfoPage.clickOnSubscribeToPulseBtn();
			storeFrontUpdateCartPage.clickOnBillingNextStepBtn();
			storeFrontUpdateCartPage.clickOnSubscribePulseTermsAndConditionsChkbox();
			storeFrontUpdateCartPage.clickOnSubscribeBtn();
			storeFrontConsultantPage.clickOnWelcomeDropDown();
			storeFrontAccountInfoPage=storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
			storeFrontAccountInfoPage.clickOnYourAccountDropdown();
			storeFrontAccountInfoPage.clickOnAutoShipStatus();
		}
		storeFrontAccountInfoPage.cancelPulseSubscription();
		s_assert.assertTrue(storeFrontAccountInfoPage.validatePulseCancelled(),"pulse subscription is not cancelled for the user");
		storeFrontAccountInfoPage.clickOnSubscribeToPulseBtn();
		storeFrontUpdateCartPage.clickOnAccountInfoNextButton();
		storeFrontUpdateCartPage.clickOnSubscribePulseTermsAndConditionsChkbox();
		storeFrontUpdateCartPage.clickOnSubscribeBtn();
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountInfoPage.clickOnAutoShipStatus();
		s_assert.assertTrue(storeFrontAccountInfoPage.validateSubscribeToPulse(),"Cancel subscribtion button should be preesent");
		s_assert.assertFalse(storeFrontAccountInfoPage.validatePulseCancelled(),"Subscribe pulse button should not be present");
		s_assert.assertAll();
	}	

	/**
	 * Login: RC (from com PWS)
	 * Validations: verify login from the updated username
	 * @throws InterruptedException
	 */
	//Hybris Project-4660 :: Version : 1 :: Change the username of RC user and Login with updated username
	@Test(priority=10)
	public void testchangeUsernameOfRcUserWithUpdatedUserName_4660() throws InterruptedException{
		int randomNumber =  CommonUtils.getRandomNum(10000, 1000000);
		String newUserName = TestConstants.NEW_RC_USER_NAME+randomNumber+TestConstants.EMAIL_ADDRESS_SUFFIX;
		String rcUserEmailID = QueryUtils.getRandomActiveRCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId);
		storeFrontAccountInfoPage.openComPWSSite(country, env);
		storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasRCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),"Not able to login to the application from RC");
		String oldUserNameOnUI = storeFrontHomePage.fetchingUserName();
		storeFrontHomePage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontRCUserPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.enterPhoneNumberAndPostalCode();
		storeFrontAccountInfoPage.enterNewUserNameAndClicKOnSaveButton(newUserName);
		s_assert.assertTrue(storeFrontAccountInfoPage.getUsernameFromAccount().equalsIgnoreCase(newUserName),"Your Profile has not been Updated");
		logout();
		storeFrontHomePage.loginAsRCUser(newUserName, password);
		String newUserNameOnUI = storeFrontHomePage.fetchingUserName();
		s_assert.assertTrue(storeFrontHomePage.verifyUserNameAfterLoginAgain(oldUserNameOnUI,newUserNameOnUI),"Login is not successful with new UserName");
		s_assert.assertAll();
	}

	/**
	 * Login: PC
	 * Validations:
	 * * User should not be able to login on cancelling PC perks
	 * @throws InterruptedException
	 */
	//Hybris Project-2234:Verify that user can cancel PC Perks subscription through my account
	//Hybris Project-4803:PC account termination for US new PC
	@Test(priority=11)
	public void testPCUserCancelPcPerks_2234_4803() throws InterruptedException{
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");
		storeFrontPCUserPage.clickOnWelcomeDropDown();
		storeFrontPCUserPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontPCUserPage.clickOnYourAccountDropdown();
		storeFrontPCUserPage.clickOnPCPerksStatus();
		storeFrontPCUserPage.clickDelayOrCancelPCPerks();
		storeFrontPCUserPage.clickPleaseCancelMyPcPerksActBtn();
		storeFrontPCUserPage.cancelMyPCPerksAct();
		storeFrontHomePage.loginAsConsultant(pcUserEmailID, password);
		s_assert.assertTrue(storeFrontHomePage.isCurrentURLShowsError(),"Inactive User doesn't get Login failed");
		s_assert.assertAll();
	}

	//Hybris Phase 2-2235:Verify that user can change the information in 'my account info'.
	@Test(priority=12)
	public void testAccountInformationForUpdate_2235() throws InterruptedException{
		int randomNumPhone = CommonUtils.getRandomNum(10000, 99999);
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String city = null;
		String stateName = null;
		String postalCode = null;
		String phoneNumber = null;
		String addressLine1 = null;
		if(country.equalsIgnoreCase("CA")){
			city = TestConstants.CONSULTANT_CITY_FOR_ACCOUNT_INFORMATION_CA;
			postalCode = TestConstants.CONSULTANT_POSTAL_CODE_FOR_ACCOUNT_INFORMATION_CA;
			phoneNumber = "99999"+randomNumPhone;
			addressLine1 =  TestConstants.CONSULTANT_ADDRESS_LINE1_FOR_ACCOUNT_INFORMATION_CA;
			stateName = TestConstants.CONSULTANT_STATE_FOR_ACCOUNT_INFORMATION_CA;
		}else if(country.equalsIgnoreCase("US")){
			city = TestConstants.CONSULTANT_CITY_FOR_ACCOUNT_INFORMATION_US;
			postalCode = TestConstants.CONSULTANT_POSTAL_CODE_FOR_ACCOUNT_INFORMATION_US;
			phoneNumber = "99999"+randomNumPhone;
			addressLine1 =  TestConstants.CONSULTANT_ADDRESS_LINE1_FOR_ACCOUNT_INFORMATION_US;
			stateName = TestConstants.CONSULTANT_STATE_FOR_ACCOUNT_INFORMATION_US;
		}else{
			city = TestConstants.CITY_AU;
			postalCode = TestConstants.POSTAL_CODE_AU;
			phoneNumber = TestConstants.PHONE_NUMBER_AU;
			addressLine1 =  TestConstants.ADDRESS_LINE_1_AU;
			stateName = "South Australia";
		}
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyAccountInfoPageIsDisplayed(), "Account Info page has not been displayed");
		String firstName = TestConstants.CONSULTANT_FIRST_NAME_FOR_ACCOUNT_INFORMATION+randomNum;
		String lastName = TestConstants.CONSULTANT_LAST_NAME_FOR_ACCOUNT_INFORMATION+randomNum;
		storeFrontAccountInfoPage.updateFirstName(firstName);
		storeFrontAccountInfoPage.updateLastName(lastName);
		storeFrontAccountInfoPage.updateAddressWithCityAndPostalCode(addressLine1, city, postalCode);
		String state = storeFrontAccountInfoPage.updateStateAndReturnName(stateName);
		storeFrontAccountInfoPage.updateMainPhnNumber(phoneNumber);
		storeFrontAccountInfoPage.updateDateOfBirthAndGender();
		storeFrontAccountInfoPage.uncheckSpouseCheckBox();
		storeFrontAccountInfoPage.clickSaveAccountBtn();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyFirstNameFromUIForAccountInfo(firstName), "First Name on UI is not updated");
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyLasttNameFromUIForAccountInfo(lastName), "Last Name on UI is not updated");
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyCityFromUIForAccountInfo(city), "City on UI is not updated");
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyProvinceFromUIForAccountInfo(state), "State/Province on UI is not updated");
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyPostalCodeFromUIForAccountInfo(postalCode), "Postal Code on UI is not updated");
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyMainPhoneNumberFromUIForAccountInfo(phoneNumber), "Phone Number on UI is not updated");
		s_assert.assertAll();
	}

	/**
	 * Login: Consultant
	 * Validations:
	 * * Success message on saving Main Phone Number, Mobile Number, Gender, Allow Spouse details
	 * @throws InterruptedException
	 */
	//Hybris Project-5003:Add Birthday and gender and allow my sponsor information
	//Hybris Project-5002:Modify Main Phone Number and mobile phone information
	@Test(priority=13)
	public void testAddBirthDayAndGenderAndAllowMySponsorInformation_5003_5002() throws InterruptedException{
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.enterMainPhoneNumber(phoneNumber);
		s_assert.assertTrue(storeFrontAccountInfoPage.isGlobalUpdateMessageDisplayed(),"Profile updation message not present on UI");
		storeFrontAccountInfoPage.enterMobileNumber(phoneNumber);
		storeFrontAccountInfoPage.enterBirthDateOnAccountInfoPage(TestConstants.CONSULTANT_DAY_OF_BIRTH,TestConstants.CONSULTANT_MONTH_OF_BIRTH,TestConstants.CONSULTANT_YEAR_OF_BIRTH);
		storeFrontAccountInfoPage.clickOnGenderRadioButton(TestConstants.CONSULTANT_GENDER);
		storeFrontAccountInfoPage.clickOnAllowMySpouseOrDomesticPartnerCheckbox();
		storeFrontAccountInfoPage.enterSpouseFirstName(TestConstants.SPOUSE_FIRST_NAME);
		storeFrontAccountInfoPage.enterSpouseLastName(TestConstants.SPOUSE_LAST_NAME);
		storeFrontAccountInfoPage.clickOnSaveAfterEnterSpouseDetails();
		s_assert.assertTrue(storeFrontAccountInfoPage.isGlobalUpdateMessageDisplayed(),"Profile updation message not present on UI");	
		s_assert.assertAll();
	}

	//Hybris Project-4999:Modify name of the users
	@Test(priority=14)
	public void testModifyNameOfUser_4999() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String lastNameDB = null;
		String firstNameDB = null;
		List<Map<String, Object>> accountNameDetailsList =  null;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String lastName=TestConstants.LAST_NAME+randomNum;
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.enterNameOfUser(firstName,lastName);
		storeFrontAccountInfoPage.clickSaveAccountPageInfo();
		s_assert.assertTrue(storeFrontAccountInfoPage.isGlobalUpdateMessageDisplayed(),"Profile updation message not present on UI");
		s_assert.assertTrue(storeFrontAccountInfoPage.getFirstNameFromAccountInfo().equalsIgnoreCase(firstName),"First name was not updated");
		s_assert.assertTrue(storeFrontAccountInfoPage.getLastNameFromAccountInfo().equalsIgnoreCase(lastName),"Last name was not updated");
		accountNameDetailsList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NAME_DETAILS_QUERY, TestConstants.FIRST_NAME+randomNum+" "+lastName), RFO_DB);
		firstNameDB = (String) getValueFromQueryResult(accountNameDetailsList, "FirstName");
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyFirstNameFromUIForAccountInfo(firstNameDB),"First Name on UI is different from DB");
		accountNameDetailsList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NAME_DETAILS_QUERY, TestConstants.FIRST_NAME+randomNum+" "+lastName), RFO_DB);
		lastNameDB = (String) getValueFromQueryResult(accountNameDetailsList, "LastName");
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyLasttNameFromUIForAccountInfo(lastNameDB),"Last Name on UI is different from DB" );
		storeFrontAccountInfoPage.clickMeetYourConsultantLink();
		s_assert.assertAll();
	}

	/**
	 * Login: RC(from .biz site)
	 * Validations: 
	 * * Verify login with the updated username
	 * @throws InterruptedException
	 */
	//Hybris Project-4659:Change the username of RC user and Login with updated username
	@Test(priority=15)
	public void testChangeTheUserNameOfRCandLoginWithUpdateUsername_4659() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newUserName = TestConstants.FIRST_NAME+randomNum+TestConstants.EMAIL_ADDRESS_SUFFIX;
		String pws = storeFrontHomePage.getBizPWS(country,env);
		storeFrontHomePage.openPWSSite(pws);
		String rcUserEmailID = QueryUtils.getRandomActiveRCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId);
		storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasRCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),"Not able to login to the application from RC"); 
		storeFrontRCUserPage.clickOnWelcomeDropDown();
		storeFrontRCUserPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontRCUserPage.enterNewUserNameAndClickSaveButton(newUserName);
		logout();
		storeFrontHomePage.loginAsRCUser(newUserName, password);
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		s_assert.assertAll();
	}

	// Hybris Project-4275:verify Uniqueness of Username
	@Test
	public void testVerifyUniqnessOfUsername_4275() throws InterruptedException {
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		List<Map<String, Object>> randomConsultantList =  null;
		String crossCountryConsultantEmailID = null;
		String existingConsultantEmailID = null;
		String multipleWordsUserName="username1 usernamet"+randomNum;
		String cid=null;
		if(country.equalsIgnoreCase("ca")){
			cid="14";
		}else if(country.equalsIgnoreCase("au")){
			cid="40";
		}
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,cid),RFO_DB);
		crossCountryConsultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		existingConsultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage= storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.enterUserName(existingConsultantEmailID);
		storeFrontAccountInfoPage.clickSaveAccountPageInfo();
		s_assert.assertTrue(storeFrontAccountInfoPage.isGlobalErrorMessageDisplayed(),"existing consultant username should not be saved");
		storeFrontAccountInfoPage.enterUserName(crossCountryConsultantEmailID);
		storeFrontAccountInfoPage.clickSaveAccountPageInfo();
		s_assert.assertTrue(storeFrontAccountInfoPage.isGlobalErrorMessageDisplayed(),"cross country consultant username should not be saved");
		storeFrontAccountInfoPage.enterUserName(multipleWordsUserName);
		storeFrontAccountInfoPage.clickSaveAccountPageInfo();
		s_assert.assertTrue(storeFrontAccountInfoPage.isGlobalErrorMessageDisplayed(),"multi word consultant username should not be saved");
		storeFrontAccountInfoPage.enterUserName(consultantEmailID);
		storeFrontAccountInfoPage.clickSaveAccountPageInfo();
		s_assert.assertTrue(storeFrontAccountInfoPage.isGlobalUpdateMessageDisplayed(),"valid consultant username should be saved");
		s_assert.assertAll();
	}

	// Hybris Project-4276:VErify 2 fields for emails address and USerName is present
	@Test(priority=18)
	public void testVerifyUserNameAndEmailAddressFieldsValidation_4276() throws InterruptedException {
		int randomNum = CommonUtils.getRandomNum(10000, 100000);
		String userName="WestCoast"+randomNum;
		String userNameAU="WestCoast"+randomNum+TestConstants.EMAIL_ADDRESS_SUFFIX;
		String EmailAddress=TestConstants.FIRST_NAME+randomNum+TestConstants.NEW_EMAIL_ADDRESS_SUFFIX;
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage= storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		if(country.equalsIgnoreCase("AU")){
			storeFrontAccountInfoPage.enterUserName(userNameAU);
		}else{
			storeFrontAccountInfoPage.enterUserName(userName);
			storeFrontAccountInfoPage.enterEmailAddress(EmailAddress);
		}
		storeFrontAccountInfoPage.clickSaveAccountPageInfo();
		s_assert.assertTrue(storeFrontAccountInfoPage.isGlobalUpdateMessageDisplayed(),"Profile updation message not appear for correct username");
		logout();
		if(country.equalsIgnoreCase("US")||country.equalsIgnoreCase("CA")){
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(EmailAddress, password);
			s_assert.assertTrue(storeFrontConsultantPage.verifyConsultantPage(),"login is not successful");
			logout();
		}
		if(country.equalsIgnoreCase("AU")){
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(userNameAU, password);
		}else{
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(userName, password);
		}
		s_assert.assertTrue(storeFrontConsultantPage.verifyConsultantPage(),"login is not successful");
		s_assert.assertAll();
	}

	// Hybris Project-4277:Email and USer NAme has different values
	@Test(priority=19)
	public void testVerifyUserNameAndEmailAddressFieldsValidationWithDifferentValues_4277() throws InterruptedException {
		if(country.equalsIgnoreCase("ca")){
			int randomNum = CommonUtils.getRandomNum(10000, 100000);
			String userNameAU="WestCoast"+randomNum+TestConstants.EMAIL_ADDRESS_SUFFIX;
			String userName="WestCoast"+randomNum;
			String EmailAddress=TestConstants.FIRST_NAME+randomNum+TestConstants.NEW_EMAIL_ADDRESS_SUFFIX;
			String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
			storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
			storeFrontConsultantPage.clickOnWelcomeDropDown();
			storeFrontAccountInfoPage= storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
			if(country.equalsIgnoreCase("AU")){
				storeFrontAccountInfoPage.enterUserName(userNameAU);
			}else{
				storeFrontAccountInfoPage.enterUserName(userName);
				storeFrontAccountInfoPage.enterEmailAddress(EmailAddress);
			}
			storeFrontAccountInfoPage.clickSaveAccountPageInfo();
			s_assert.assertTrue(storeFrontAccountInfoPage.isGlobalUpdateMessageDisplayed(),"Profile updation message not appear for correct username");
			logout();
			if(country.equalsIgnoreCase("US")||country.equalsIgnoreCase("CA")){
				storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(EmailAddress, password);
				s_assert.assertTrue(storeFrontConsultantPage.verifyConsultantPage(),"login is not successful");
				logout();
			}
			if(country.equalsIgnoreCase("AU")){
				storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(userNameAU, password);
			}else{
				storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(userName, password);
			}
			s_assert.assertTrue(storeFrontConsultantPage.verifyConsultantPage(),"login is not successful");
			s_assert.assertAll();
		}
		else{
			logger.info("not a CA related test");
		}
	}

	/**
	 * Login: Consultant
	 * Validations:
	 * * Change username from account info page, logout and verify login success from username
	 * *  Verify saved username comes prepopulated on account info page
	 * @throws InterruptedException
	 */
	// Hybris Project-4260 :: Version : 1 :: UserName Field: Edit & Login 
	// Hybris Project-5000:Modify Email Address for User
	//Hybris Project-4274:UserName Field Validation
	// Hybris Project-4279:Change User Name Multiple Times and SAve
	@Test(priority=20)
	public void testVerifyChangeUserNameFieldsMultipleTimeAndValidateLoginWithLastChange_4279_4274_5000_4260() throws InterruptedException {
		int randomNum = CommonUtils.getRandomNum(10000, 100000);
		String userName="cons"+randomNum;
		int i=0;
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage= storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		for(i=1;i<=3;i++){
			storeFrontAccountInfoPage.enterUserName(userName+i+TestConstants.EMAIL_ADDRESS_SUFFIX);
			storeFrontAccountInfoPage.clickSaveAccountPageInfo();
		}
		logout();
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(userName+(i-1)+TestConstants.EMAIL_ADDRESS_SUFFIX, password);
		s_assert.assertTrue(storeFrontConsultantPage.verifyConsultantPage(),"login is not successful");
		s_assert.assertAll();
	}

	//Hybris Project-4278:Update USer name and Upgrade USer
	@Test(priority=21)
	public void testUpdateUserNameAndUpgradeUser_4278() throws InterruptedException{
		if(country.equalsIgnoreCase("ca")){
			int randomNum = CommonUtils.getRandomNum(10000, 1000000);
			int randomNumForRCToConsultant = CommonUtils.getRandomNum(10000, 1000000);
			int randomNumForPCToConsultant = CommonUtils.getRandomNum(10000, 1000000);
			String userName = TestConstants.FIRST_NAME+randomNum;
			List<Map<String, Object>> randomRCList =  null;
			String rcUserEmailID =null;
			String accountId = null;
			String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
			String firstName=TestConstants.FIRST_NAME+randomNum;
			String lastName = "lN";
			//String state = null;
			for(int i=0;i<TestConstants.LOOP_COUNT;i++) {
				randomRCList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),RFO_DB);
				rcUserEmailID = (String) getValueFromQueryResult(randomRCList, "UserName");  
				accountId = String.valueOf(getValueFromQueryResult(randomRCList, "AccountID"));
				randomRCList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_EMAIL_ID_FROM_ACCOUNT_ID,accountId),RFO_DB);
				rcUserEmailID = String.valueOf(getValueFromQueryResult(randomRCList, "EmailAddress"));
				logger.info("Account Id of the user is "+accountId);
				storeFrontRCUserPage = storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
				boolean isError = driver.getCurrentUrl().contains("error");
				if(isError){
					logger.info("login error for the user "+rcUserEmailID);
					driver.get(driver.getURL()+"/"+country);
				}
				else
					break;
			} 
			storeFrontRCUserPage.clickOnWelcomeDropDown();
			storeFrontAccountInfoPage = storeFrontRCUserPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
			storeFrontAccountInfoPage.enterUserName(userName);
			storeFrontAccountInfoPage.clickSaveAccountPageInfo();
			s_assert.assertTrue(storeFrontAccountInfoPage.isGlobalUpdateMessageDisplayed(),"Profile updation message not appear for correct username");
			logout();
			storeFrontHomePage.loginAsRCUser(userName, password);
			s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
			//Upgrade RC To PC
			storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
			storeFrontHomePage.clickAddToBagButton(country);
			storeFrontHomePage.clickOnCheckoutButton();
			storeFrontHomePage.clickOnContinueWithoutSponsorLink();
			storeFrontHomePage.checkPCPerksCheckBox();
			storeFrontHomePage.clickOnShippingAddressNextStepBtn();
			//Enter Billing Profile
			storeFrontUpdateCartPage.clickAddNewBillingProfileLink();
			storeFrontHomePage.enterBillingDetailsAndSave(cardNumber, firstName+" "+lastName, cardExpMonth, cardExpYear, CVV);
			storeFrontHomePage.clickOnBillingNextStepBtn();
			storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
			storeFrontHomePage.clickPlaceOrderBtn();
			storeFrontHomePage.clickOnRodanAndFieldsLogo();
			s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
			logout();
			// RC TO Consultant
			navigateToStoreFrontBaseURL();
			for(int i=0;i<TestConstants.LOOP_COUNT;i++) {
				randomRCList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),RFO_DB);
				rcUserEmailID = (String) getValueFromQueryResult(randomRCList, "UserName");  
				accountId = String.valueOf(getValueFromQueryResult(randomRCList, "AccountID"));
				randomRCList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_EMAIL_ID_FROM_ACCOUNT_ID,accountId),RFO_DB);
				rcUserEmailID = String.valueOf(getValueFromQueryResult(randomRCList, "EmailAddress"));
				logger.info("Account Id of the user is "+accountId);
				storeFrontRCUserPage = storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
				boolean isError = driver.getCurrentUrl().contains("error");
				if(isError){
					logger.info("login error for the user "+rcUserEmailID);
					driver.get(driver.getURL()+"/"+country);
				}
				else
					break;
			} 
			String userNameForConsultant = TestConstants.FIRST_NAME+randomNumForRCToConsultant;
			storeFrontRCUserPage.clickOnWelcomeDropDown();
			storeFrontAccountInfoPage = storeFrontRCUserPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
			storeFrontAccountInfoPage.enterUserName(userNameForConsultant);
			storeFrontAccountInfoPage.clickSaveAccountPageInfo();
			logout();
			storeFrontHomePage.loginAsRCUser(userNameForConsultant, password);
			s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
			logout();
			String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
			String socialInsuranceNumber2 = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
			//Enroll consultant with inactive rc user
			storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
			storeFrontHomePage.searchCID(sponsor);
			storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
			storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);  
			storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
			storeFrontHomePage.enterEmailAddress(rcUserEmailID);
			s_assert.assertTrue(storeFrontHomePage.verifyUpradingToConsulTantPopup(), "Upgrading to a consultant popup is not present");
			storeFrontHomePage.enterPasswordForUpgradeRCToConsultant();
			storeFrontHomePage.clickOnLoginToTerminateToMyRCAccount();
			s_assert.assertTrue(storeFrontHomePage.verifyAccountTerminationMessage(), "Rc user is not terminated successfully");
			storeFrontHomePage.enterFirstName(firstName);
			storeFrontHomePage.enterLastName(lastName);
			storeFrontHomePage.enterEmailAddress(rcUserEmailID);
			storeFrontHomePage.enterPassword(password);
			storeFrontHomePage.enterConfirmPassword(password);
			storeFrontHomePage.enterAddressLine1(addressLine1);
			storeFrontHomePage.enterCity(city);
			storeFrontHomePage.selectProvince(state);
			storeFrontHomePage.enterPostalCode(postalCode);
			storeFrontHomePage.enterPhoneNumber(phoneNumber);
			storeFrontHomePage.clickNextButton();
			storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
			storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
			storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
			storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
			storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
			storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
			storeFrontHomePage.clickNextButton();
			s_assert.assertTrue(storeFrontHomePage.isTheTermsAndConditionsCheckBoxDisplayed(), "Terms and Conditions checkbox is not visible");
			storeFrontHomePage.checkThePoliciesAndProceduresCheckBox();
			storeFrontHomePage.checkTheIAcknowledgeCheckBox();		
			storeFrontHomePage.checkTheIAgreeCheckBox();
			storeFrontHomePage.checkTheTermsAndConditionsCheckBox();
			storeFrontHomePage.clickOnEnrollMeBtn();
			storeFrontHomePage.clickOnConfirmAutomaticPayment();
			s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
			driver.get(driver.getURL()+"/"+country);
			//PC to consultant
			List<Map<String, Object>> randomPCUserList;
			String pcUserEmailID = null;
			for(int i=0;i<TestConstants.LOOP_COUNT;i++) {
				randomPCUserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_AUTOSHIPS_RFO,countryId),RFO_DB);
				pcUserEmailID = (String) getValueFromQueryResult(randomPCUserList, "UserName");
				accountId = String.valueOf(getValueFromQueryResult(randomPCUserList, "AccountID"));
				List<Map<String, Object>> randomPCUsernameList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_EMAIL_ID_FROM_ACCOUNT_ID,accountId),RFO_DB);
				pcUserEmailID = String.valueOf(getValueFromQueryResult(randomPCUsernameList, "EmailAddress"));
				logger.info("Account Id of the user is "+accountId);
				storeFrontPCUserPage = storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
				boolean isLoginError = driver.getCurrentUrl().contains("error");
				if(isLoginError){
					logger.info("Login error for the user "+pcUserEmailID);
					driver.get(driver.getURL()+"/"+country);
				}
				else
					break;
			}	
			String userNameForPCToConsultant = TestConstants.FIRST_NAME+randomNumForPCToConsultant;
			logger.info("login is successful");
			storeFrontPCUserPage.clickOnWelcomeDropDown();
			storeFrontAccountInfoPage = storeFrontPCUserPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
			storeFrontAccountInfoPage.enterUserName(userNameForPCToConsultant);
			storeFrontAccountInfoPage.clickSaveAccountPageInfo();
			s_assert.assertTrue(storeFrontAccountInfoPage.isGlobalUpdateMessageDisplayed(),"Profile updation message not appear for correct username");
			logout();
			storeFrontHomePage.loginAsRCUser(userNameForPCToConsultant, password);
			s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
			logout();
			//get sponsor ID
			List<Map<String, Object>> sponserList  = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",driver.getCountry(),countryId),RFO_DB);
			String sponserHavingPulse = String.valueOf(getValueFromQueryResult(sponserList, "AccountID"));
			// sponser search by Account Number
			List<Map<String, Object>> sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,sponserHavingPulse),RFO_DB);
			String sponsorId = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
			driver.get(driver.getStoreFrontURL()+"/"+country);
			storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
			storeFrontHomePage.searchCID(sponsorId);
			storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
			storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);  
			storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
			storeFrontHomePage.enterEmailAddress(pcUserEmailID);
			s_assert.assertTrue(storeFrontHomePage.verifyUpradingToConsulTantPopup(), "Upgrading to a consultant popup is not present");
			storeFrontHomePage.enterPasswordForUpgradePcToConsultant();
			storeFrontHomePage.clickOnLoginToTerminateToMyPCAccount();
			s_assert.assertTrue(storeFrontHomePage.verifyAccountTerminationMessage(), "Pc user is not terminated successfully");
			storeFrontHomePage.enterEmailAddress(pcUserEmailID);
			storeFrontHomePage.clickOnEnrollUnderLastUpline();
			logger.info("After click enroll under last upline we are on "+driver.getCurrentUrl());
			storeFrontHomePage.selectEnrollmentKitPage(TestConstants.KIT_NAME_BIG_BUSINESS, TestConstants.REGIMEN_NAME_REVERSE);  
			storeFrontHomePage.chooseEnrollmentOption(TestConstants.EXPRESS_ENROLLMENT);
			storeFrontHomePage.enterFirstName(firstName);
			storeFrontHomePage.enterLastName(lastName);
			storeFrontHomePage.enterEmailAddress(pcUserEmailID);
			storeFrontHomePage.enterPassword(password);
			storeFrontHomePage.enterConfirmPassword(password);
			storeFrontHomePage.enterAddressLine1(addressLine1);
			storeFrontHomePage.enterCity(city);
			storeFrontHomePage.selectProvince(state);
			storeFrontHomePage.enterPostalCode(postalCode);
			storeFrontHomePage.enterPhoneNumber(phoneNumber);
			storeFrontHomePage.clickNextButton();
			storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
			storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
			storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
			storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
			storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber2);
			storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
			storeFrontHomePage.clickNextButton();
			s_assert.assertTrue(storeFrontHomePage.isTheTermsAndConditionsCheckBoxDisplayed(), "Terms and Conditions checkbox is not visible");
			storeFrontHomePage.checkThePoliciesAndProceduresCheckBox();
			storeFrontHomePage.checkTheIAcknowledgeCheckBox();		
			storeFrontHomePage.checkTheIAgreeCheckBox();
			storeFrontHomePage.checkTheTermsAndConditionsCheckBox();
			storeFrontHomePage.clickOnEnrollMeBtn();
			storeFrontHomePage.clickOnConfirmAutomaticPayment();
			s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
			storeFrontHomePage.clickOnRodanAndFieldsLogo();
			storeFrontHomePage.clickOnWelcomeDropDown();
			storeFrontOrdersPage = storeFrontHomePage.clickOrdersLinkPresentOnWelcomeDropDown();
			// Verify Order is present
			s_assert.assertTrue(storeFrontOrdersPage.verifyAdhocOrderIsPresent(), "Adhoc Order is not present");
			s_assert.assertAll();
		}else{
			logger.info("NOT EXECUTED...Test is ONLY for CANADA env");
		}
	}

	//Hybris Project-4010:Look up with Terminated Preffered consultant's Full name/ Account ID
	@Test(priority=23)
	public void testLookUpWithTerminatedPCFullNameOrAccountID_4010() throws InterruptedException{
		List<Map<String, Object>> randomPCUserList =  null;
		String pcUserEmailID = null;
		String accountId = null;
		for(int i=0;i<TestConstants.LOOP_COUNT;i++) {
			randomPCUserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_AUTOSHIPS_RFO,countryId),RFO_DB);
			pcUserEmailID = (String) getValueFromQueryResult(randomPCUserList, "UserName");  
			accountId = String.valueOf(getValueFromQueryResult(randomPCUserList, "AccountID"));
			logger.info("Account Id of the user is "+accountId);
			storeFrontPCUserPage = storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
			boolean isError = driver.getCurrentUrl().contains("error");
			if(isError){
				logger.info("SITE NOT FOUND for the user "+pcUserEmailID);
				driver.get(driver.getURL()+"/"+country);
			}
			else
				break;
		} 
		logger.info("login is successful");
		storeFrontPCUserPage.clickOnWelcomeDropDown();
		storeFrontPCUserPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontPCUserPage.clickOnYourAccountDropdown();
		storeFrontPCUserPage.clickOnPCPerksStatus();
		storeFrontPCUserPage.clickDelayOrCancelPCPerks();
		storeFrontPCUserPage.clickPleaseCancelMyPcPerksActBtn();
		storeFrontPCUserPage.cancelMyPCPerksAct();
		navigateToStoreFrontBaseURL();
		storeFrontHomePage.clickConnectUnderConnectWithAConsultantSection();
		storeFrontHomePage.searchCID(accountId);
		s_assert.assertTrue(storeFrontHomePage.validateInvalidSponsor(),"Terminated PC User is Present!!!");
		s_assert.assertAll(); 
	}

	//Hybris Project-4012:Look up with Terminated Retail consultant's Full name/ Account ID
	@Test(priority=24)
	public void testLookUpWithTerminatedRCFullNameOrAccountID_4012() throws InterruptedException{
		List<Map<String, Object>> randomRCList =  null;
		String rcUserEmailID =null;
		String accountId = null;
		for(int i=0;i<TestConstants.LOOP_COUNT;i++) {
			randomRCList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),RFO_DB);
			rcUserEmailID = (String) getValueFromQueryResult(randomRCList, "UserName");  
			accountId = String.valueOf(getValueFromQueryResult(randomRCList, "AccountID"));
			logger.info("Account Id of the user is "+accountId);
			storeFrontRCUserPage = storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
			boolean isError = driver.getCurrentUrl().contains("error");
			if(isError){
				logger.info("login error for the user "+rcUserEmailID);
				driver.get(driver.getURL()+"/"+country);
			}
			else
				break;
		} 
		logger.info("login is successful");
		storeFrontRCUserPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontRCUserPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountTerminationPage=storeFrontAccountInfoPage.clickTerminateMyAccount();
		storeFrontAccountTerminationPage.selectTerminationReason();
		storeFrontAccountTerminationPage.enterTerminationComments();
		storeFrontAccountTerminationPage.selectCheckBoxForVoluntarilyTerminate();
		storeFrontAccountTerminationPage.clickSubmitToTerminateAccount();
		s_assert.assertTrue(storeFrontAccountTerminationPage.verifyPopupHeader(),"Account termination Page Pop Up Header is not Present");
		storeFrontAccountTerminationPage.clickOnConfirmTerminationPopup(); 
		//Navigate to the base url
		navigateToStoreFrontBaseURL();
		//connect with a consultant
		storeFrontHomePage.clickConnectUnderConnectWithAConsultantSection();
		//search with terminated consultant
		storeFrontHomePage.searchCID(accountId);
		//verify 'No Result found' is displayed
		s_assert.assertTrue(storeFrontHomePage.validateInvalidSponsor(),"Terminated RC User is Present!!!");
		s_assert.assertAll(); 
	}

	//Hybris Project-3920 :: Version : 1 :: Verify creating crp autoship from My Account under .biz site 
	@Test (priority=25)
	public void testCreatingCRPAutoshipUnderBizSite_3920() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		String enrollmentType = TestConstants.STANDARD_ENROLLMENT;
		storeFrontHomePage.openPWSSite(country, env);
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, TestConstants.FIRST_NAME+randomNum, TestConstants.LAST_NAME+randomNum, password, addressLine1, city,state,postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.uncheckPulseAndCRPEnrollment();
		s_assert.assertTrue(storeFrontHomePage.verifySubsribeToPulseCheckBoxIsNotSelected(), "Subscribe to pulse checkbox selected after uncheck");
		s_assert.assertTrue(storeFrontHomePage.verifyEnrollToCRPCheckBoxIsNotSelected(), "Enroll to CRP checkbox selected after uncheck");
		storeFrontHomePage.clickNextButton();
		s_assert.assertTrue(storeFrontHomePage.isTheTermsAndConditionsCheckBoxDisplayed(), "Terms and Conditions checkbox is not visible");
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnEnrollMeBtn();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyAccountInfoPageIsDisplayed(),"shipping info page has not been displayed");
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountInfoPage.clickOnAutoShipStatus();
		storeFrontAccountInfoPage.clickOnEnrollInCRP();;
		storeFrontHomePage.clickOnAddToCRPButtonCreatingCRPUnderBizSite();
		storeFrontHomePage.clickOnCRPCheckout();
		storeFrontHomePage.clickOnUpdateCartShippingNextStepBtnDuringEnrollment();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickOnSetupCRPAccountBtn();
		s_assert.assertTrue(storeFrontHomePage.verifyOrderConfirmation(), "Order Confirmation Message has not been displayed");
		storeFrontHomePage.clickOnGoToMyAccountToCheckStatusOfCRP();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountInfoPage.clickOnAutoShipStatus();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyCurrentCRPStatus(), "Current CRP Status has not been Enrolled");
		s_assert.assertAll();
	}

	//Hybris Project-3921 :: Version : 1 :: Verify subscribing to Pulse from My Account under .biz site 
	@Test(priority=26)
	public void testSubscribingPulseMyAccount_3921() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		String enrollmentType = TestConstants.STANDARD_ENROLLMENT;
		storeFrontHomePage.openPWSSite(country, env);
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();		
		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, TestConstants.FIRST_NAME+randomNum, TestConstants.LAST_NAME+randomNum, password, addressLine1, city, state,postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.uncheckPulseAndCRPEnrollment();
		s_assert.assertTrue(storeFrontHomePage.verifySubsribeToPulseCheckBoxIsNotSelected(), "Subscribe to pulse checkbox selected after uncheck");
		s_assert.assertTrue(storeFrontHomePage.verifyEnrollToCRPCheckBoxIsNotSelected(), "Enroll to CRP checkbox selected after uncheck");
		storeFrontHomePage.clickNextButton();
		s_assert.assertTrue(storeFrontHomePage.isTheTermsAndConditionsCheckBoxDisplayed(), "Terms and Conditions checkbox is not visible");
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnEnrollMeBtn();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		storeFrontHomePage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyAccountInfoPageIsDisplayed(),"shipping info page has not been displayed");
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountInfoPage.clickOnAutoShipStatus();
		storeFrontAccountInfoPage.clickOnSubscribeToPulseBtn();
		storeFrontUpdateCartPage.clickOnBillingNextStepBtn();
		storeFrontUpdateCartPage.clickOnSubscribePulseTermsAndConditionsChkbox();
		storeFrontUpdateCartPage.clickOnSubscribeBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyPulseOrderCreatedMsg(), "Pulse order created msg is NOT present,Pulse might NOT be subscribed successfully");
		s_assert.assertAll();
	}

	//Hybris Project-2232:Verify that user can cancel CRP subscription through my account.
	@Test(priority=28)
	public void testVerifyUserCanCancelCRPSubscriptionThroughMyAccount_2232() throws InterruptedException { 
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage=storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountInfoPage.clickOnAutoShipStatus();
		storeFrontAccountInfoPage.clickOnCancelMyCRP();
		//validate CRP has been cancelled..
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyCRPCancelled(), "CRP has not been cancelled");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontHomePage.clickOnAddToCRPButtonAfterCancelMyCRP();
		s_assert.assertTrue(storeFrontHomePage.verifyEnrollInCRPPopupAfterClickOnAddToCRP(), "Autoship Order get generated After cancel CRP");
		s_assert.assertAll();
	}

	//Hybris Project-135:Enroll in pulse from my account - enrolling from 1st till 17th
	@Test(priority=29)
	public void testEnrollInPulseFromMyAccountEnrolligFrom1stTill17th_135() throws InterruptedException{
		String accountId = null;
		List<Map<String, Object>> randomConsultantList =  null;
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String billingProfileName = TestConstants.BILLING_ADDRESS_NAME+randomNum;
		String userName = null;
		for(int i=0;i<TestConstants.LOOP_COUNT;i++) {
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITHOUT_PULSE_RFO,countryId),RFO_DB);
			userName = (String) getValueFromQueryResult(randomConsultantList, "UserEmail"); 
			//accountId = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			logger.info("Account Id of the user is "+accountId);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(userName, password);
			boolean isError = driver.getCurrentUrl().contains("error");
			if(isError){
				logger.info("login error for the user "+userName);
				driver.get(driver.getURL()+"/"+country);
			}
			else
				break;
		}  
		logger.info("login is successful");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontConsultantPage.clickOnYourAccountDropdown();
		storeFrontConsultantPage.clickOnAutoshipStatusLink();
		storeFrontAccountInfoPage.clickOnCancelMyPulseSubscription();
		storeFrontAccountInfoPage.clickOnOnlySubscribeToPulseBtn();
		storeFrontAccountInfoPage.clickOnNextDuringPulseSubscribtion();
		//Enter Billing Profile
		storeFrontAccountInfoPage.clickAddNewBillingProfileLink();
		storeFrontAccountInfoPage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontAccountInfoPage.enterNewBillingNameOnCard(billingProfileName);
		storeFrontAccountInfoPage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontAccountInfoPage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontAccountInfoPage.selectNewBillingCardAddress();
		storeFrontAccountInfoPage.clickOnSaveBillingProfile();
		storeFrontUpdateCartPage.clickOnAccountInfoNextButton();
		storeFrontUpdateCartPage.clickOnSubscribePulseTermsAndConditionsChkbox();
		storeFrontUpdateCartPage.clickOnSubscribeBtn();
		s_assert.assertTrue(storeFrontAccountInfoPage.isOrderPlacedSuccessfully(), "Order is not placed successfully");
		storeFrontAccountInfoPage.clickOnWelcomeDropDown();
		storeFrontOrdersPage = storeFrontAccountInfoPage.clickOrdersLinkPresentOnWelcomeDropDown();
		//Verify Status of CRP autoship template
		s_assert.assertTrue(storeFrontOrdersPage.getStatusOfSecondAutoshipTemplateID().toLowerCase().contains("pending"), "Expected status of second pulse autoship id is: pending and actual on UI is: "+storeFrontOrdersPage.getStatusOfSecondAutoshipTemplateID().toLowerCase());
		String pulseAutoshipID = storeFrontOrdersPage.getPulseAutoshipOrderNumber();
		String autoshipDate = storeFrontOrdersPage.getPulseAutoshipOrderDate();
		s_assert.assertTrue(storeFrontOrdersPage.validateSameDatePresentForAutoship(autoshipDate),"Same date is not present");
		s_assert.assertAll();
	}

	//Hybris Project-4765:Consultant can choose a default PWS prefix upon enrolling in Pulse
	@Test (priority=30)
	public void testConsultantCanChooseDefaultPWSPrefixUponEnrollingInPulse_4765() throws InterruptedException{
		List<Map<String, Object>> randomConsultantList =  null;
		String consultantEmailID = null;
		String accountID = null;
		for(int i=0;i<TestConstants.LOOP_COUNT;i++) {
			//randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.GET_RANDOM_CONSULTANT_NO_PWS_RFO,RFO_DB);
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",country,countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
			accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			logger.info("Account Id of the user is "+accountID);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				driver.get(driver.getURL()+"/"+country);
			}
			else
				break;
		}
		logger.info("login is successful");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountInfoPage.clickOnAutoShipStatus();
		storeFrontAccountInfoPage.clickOnCancelMyPulseSubscription();
		storeFrontAccountInfoPage.clickOnOnlySubscribeToPulseBtn();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyWebsitePrefixSuggestionIsPresent(), "There are no suggestions for website prefix");
		storeFrontAccountInfoPage.clickOnNextDuringPulseSubscribtion();
		storeFrontUpdateCartPage.clickOnAccountInfoNextButton();
		storeFrontUpdateCartPage.clickOnSubscribePulseTermsAndConditionsChkbox();
		storeFrontUpdateCartPage.clickOnSubscribeBtn();
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountInfoPage.clickOnAutoShipStatus();
		s_assert.assertTrue(storeFrontAccountInfoPage.validateSubscribeToPulse(),"pulse is not subscribed for the user");
		s_assert.assertAll();
	}

	//Hybris Project-4766:Consultant can choose a custom PWS prefix upon enrolling in Pulse
	@Test (priority=31)
	public void testVerifyConsultantCanChooseCustomPWSPrefixUponEnrollingInPulse_4766() throws InterruptedException{
		List<Map<String, Object>> randomConsultantList =  null;
		String consultantEmailID = null;
		String accountID = null;
		for(int i=0;i<TestConstants.LOOP_COUNT;i++) {
			//randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_CONSULTANT_NO_PWS_WITH_COUNTRY_RFO,countryId),RFO_DB);
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",driver.getCountry(),countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
			accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			logger.info("Account Id of the user is "+accountID);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				driver.get(driver.getURL()+"/"+country);
			}
			else
				break;
		}
		logger.info("login is successful");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountInfoPage.clickOnAutoShipStatus();
		storeFrontAccountInfoPage.clickOnCancelMyCRP();
		storeFrontAccountInfoPage.clickOnCancelMyPulseSubscription();
		storeFrontAccountInfoPage.clickOnOnlySubscribeToPulseBtn();
		int randomNum = CommonUtils.getRandomNum(1000, 100000);
		String websitePrefixName = TestConstants.FIRST_NAME+randomNum;
		storeFrontHomePage.enterWebsitePrefixName(websitePrefixName);
		//storeFrontAccountInfoPage.clickOnNextDuringPulseSubscribtion();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyWebsitePrefixSuggestionIsPresent(), "There are no suggestions for website prefix");
		storeFrontAccountInfoPage.clickOnNextDuringPulseSubscribtion();
		storeFrontUpdateCartPage.clickOnAccountInfoNextButton();
		storeFrontUpdateCartPage.clickOnSubscribePulseTermsAndConditionsChkbox();
		storeFrontUpdateCartPage.clickOnSubscribeBtn();
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountInfoPage.clickOnAutoShipStatus();
		s_assert.assertTrue(storeFrontAccountInfoPage.validateSubscribeToPulse(),"pulse is not subscribed for the user");
		s_assert.assertAll();
	}

	//Hybris Project-4769:customer cannot use existing active prefix while enlrolling for pulse.
	@Test 
	public void testCheckAnotherConsultantSitePrefixWhileEnrollingToPulse_4769() throws InterruptedException{
		String sitePrefix=null;
		List<Map<String, Object>> randomConsultantList=null;
		String consultantEmailID = null;
		for(int i=0;i<TestConstants.LOOP_COUNT;i++) {
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",country,countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
			String accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			List<Map<String, Object>> sitePrefixList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_ALREADY_EXISTING_SITE_PREFIX_RFO,countryId,accountID),RFO_DB);
			sitePrefix=String.valueOf(getValueFromQueryResult(sitePrefixList, "SitePrefix"));
			logger.info("Account Id of the user is "+accountID);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				driver.get(driver.getURL()+"/"+country);
			}
			else
				break;
		}
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage=storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontHomePage.clickOnYourAccountDropdown();
		storeFrontHomePage.clickOnAutoshipStatusLink();
		s_assert.assertTrue(storeFrontAccountInfoPage.validateSubscribeToPulse(),"This user does not have pulse subscribed");
		if(storeFrontAccountInfoPage.validateSubscribeToPulse()){
			storeFrontHomePage.cancelPulseSubscription();
			s_assert.assertTrue(storeFrontAccountInfoPage.validatePulseCancelled(),"pulse subscription is not cancelled for the user");
		}
		storeFrontAccountInfoPage.clickOnOnlySubscribeToPulseBtn();
		//s_assert.assertTrue(storeFrontAccountInfoPage.getWebsitePrefixName().contains(sitePrefix),"While subscribing to pulse the same website prefix is not suggested for user");
//		s_assert.assertFalse(storeFrontHomePage.verifyAnotherConsultantPrefixIsNotAllowed(sitePrefix),"Same Consultant site prefix is not present");
		//Get Another consultant site prefix.
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",country,countryId),RFO_DB);
		String accountIdUsedToGetAnotherConsultantSitePrefix = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
		List<Map<String, Object>> AnotherConsultantSitePrefixList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_ALREADY_EXISTING_SITE_PREFIX_RFO,countryId,accountIdUsedToGetAnotherConsultantSitePrefix),RFO_DB);
		String sitePrefixOfAnotherConsultant=String.valueOf(getValueFromQueryResult(AnotherConsultantSitePrefixList, "SitePrefix"));
		logger.info("Another Consultant website prefix is "+sitePrefixOfAnotherConsultant);
		//Validate already existing site prefix allows consultant enrollment.
		storeFrontHomePage.enterWebsitePrefixName(sitePrefixOfAnotherConsultant);
		s_assert.assertTrue(storeFrontHomePage.verifyAnotherConsultantPrefixIsNotAllowed(sitePrefixOfAnotherConsultant),"Another consultant site prefix is accepted");
		s_assert.assertAll();
	}

	//Test Case Hybris Phase 2-3720 :: Version : 1 :: Perform Consultant Account termination through my account
	@Test(enabled=false)//Duplicate test,covered in Enrollment validation TC-4308
	public void testAccountTerminationPageForConsultant_3720() throws InterruptedException {
		List<Map<String, Object>> randomConsultantList =  null;
		String consultantEmailID = null;
		String accountID = null;
		storeFrontHomePage = new StoreFrontHomePage(driver);
		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
			accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			logger.info("Account Id of the user is "+accountID);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		}
		//s_assert.assertTrue(storeFrontConsultantPage.verifyConsultantPage(),"Consultant Page doesn't contain Welcome User Message");
		logger.info("login is successful");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountTerminationPage = storeFrontAccountInfoPage.clickTerminateMyAccount();
		storeFrontAccountTerminationPage.fillTheEntriesAndClickOnSubmitDuringTermination();
		s_assert.assertTrue(storeFrontAccountTerminationPage.validateConfirmAccountTerminationPopUp(), "confirm account termination pop up is not displayed");
		s_assert.assertTrue(storeFrontAccountTerminationPage.verifyAccountTerminationIsConfirmedPopup(), "Account still exist");
		storeFrontAccountTerminationPage.clickConfirmTerminationBtn();		
		storeFrontAccountTerminationPage.clickOnCloseWindowAfterTermination();
		storeFrontHomePage.clickOnCountryAtWelcomePage();
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		s_assert.assertTrue(storeFrontHomePage.isCurrentURLShowsError(),"Terminated User doesn't get Login failed");  
		s_assert.assertAll(); 
	}

	//Test Case Hybris Phase 2-3719 :: Version : 1 :: Perform PC Account termination through my account
	@Test(enabled=false)//Duplicate test,covered in Enrollment validation TC-4318
	public void testAccountTerminationPageForPCUser_3719() throws InterruptedException{
		List<Map<String, Object>> randomPCUserList =  null;
		String pcUserEmailID = null;
		String accountId = null;
		storeFrontHomePage = new StoreFrontHomePage(driver);
		while(true){
			randomPCUserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_AUTOSHIPS_RFO,countryId),RFO_DB);
			pcUserEmailID = (String) getValueFromQueryResult(randomPCUserList, "UserName");		
			accountId = String.valueOf(getValueFromQueryResult(randomPCUserList, "AccountID"));
			logger.info("Account Id of the user is "+accountId);
			storeFrontPCUserPage = storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
			boolean isError = driver.getCurrentUrl().contains("error");
			if(isError){
				logger.info("SITE NOT FOUND for the user "+pcUserEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		}	
		//s_assert.assertTrue(storeFrontPCUserPage.verifyPCUserPage(),"PC User Page doesn't contain Welcome User Message");
		logger.info("login is successful");
		storeFrontPCUserPage.clickOnWelcomeDropDown();
		storeFrontPCUserPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontPCUserPage.clickOnYourAccountDropdown();
		storeFrontPCUserPage.clickOnPCPerksStatus();
		storeFrontPCUserPage.clickDelayOrCancelPCPerks();
		storeFrontPCUserPage.clickPleaseCancelMyPcPerksActBtn();
		storeFrontPCUserPage.cancelMyPCPerksAct();
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		s_assert.assertTrue(storeFrontHomePage.isCurrentURLShowsError(),"Inactive User doesn't get Login failed");
		s_assert.assertAll();	
	}

	// Hybris Phase 2-2228 :: Version : 1 :: Perform RC Account termination through my account
	@Test(enabled=false)//Duplicate test,covered in UpgradeDowngrade TC-4694
	public void testAccountTerminationPageForRCUser_2228() throws InterruptedException{
		List<Map<String, Object>> randomRCList =  null;
		String rcUserEmailID =null;
		String accountId = null;
		while(true){
			randomRCList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),RFO_DB);
			rcUserEmailID = (String) getValueFromQueryResult(randomRCList, "UserName");		
			accountId = String.valueOf(getValueFromQueryResult(randomRCList, "AccountID"));
			logger.info("Account Id of the user is "+accountId);
			storeFrontRCUserPage = storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
			boolean isError = driver.getCurrentUrl().contains("error");
			if(isError){
				logger.info("login error for the user "+rcUserEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		}	
		logger.info("login is successful");
		storeFrontRCUserPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontRCUserPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountTerminationPage=storeFrontAccountInfoPage.clickTerminateMyAccount();
		//s_assert.assertTrue(storeFrontAccountTerminationPage.verifyAccountTerminationPageIsDisplayed(),"Account Termination Page has not been displayed");
		storeFrontAccountTerminationPage.selectTerminationReason();
		storeFrontAccountTerminationPage.enterTerminationComments();
		storeFrontAccountTerminationPage.selectCheckBoxForVoluntarilyTerminate();
		storeFrontAccountTerminationPage.clickSubmitToTerminateAccount();
		s_assert.assertTrue(storeFrontAccountTerminationPage.verifyPopupHeader(),"Account termination Page Pop Up Header is not Present");
		storeFrontAccountTerminationPage.clickOnConfirmTerminationPopup();
		storeFrontHomePage.loginAsRCUser(rcUserEmailID,password);
		s_assert.assertTrue(storeFrontHomePage.isCurrentURLShowsError(),"Inactive User doesn't get Login failed");  
		s_assert.assertAll();
	}

	// Hybris Project-1975 :: Version : 1 :: Retail user termination
	@Test(enabled=false)//Duplicate test,covered in TC_4308
	public void testRetailUserTermination_1975() throws InterruptedException{
		RFO_DB = driver.getDBNameRFO();
		List<Map<String, Object>> randomRCList =  null;
		String rcUserEmailID =null;
		String accountId = null;
		storeFrontHomePage = new StoreFrontHomePage(driver);
		while(true){
			randomRCList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),RFO_DB);
			rcUserEmailID = (String) getValueFromQueryResult(randomRCList, "UserName");		
			accountId = String.valueOf(getValueFromQueryResult(randomRCList, "AccountID"));
			logger.info("Account Id of the user is "+accountId);

			storeFrontRCUserPage = storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
			boolean isError = driver.getCurrentUrl().contains("error");
			if(isError){
				logger.info("login error for the user "+rcUserEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		}	
		logger.info("login is successful");
		storeFrontRCUserPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontRCUserPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountTerminationPage=storeFrontAccountInfoPage.clickTerminateMyAccount();
		//s_assert.assertTrue(storeFrontAccountTerminationPage.verifyAccountTerminationPageIsDisplayed(),"Account Termination Page has not been displayed");
		storeFrontAccountTerminationPage.selectTerminationReason();
		storeFrontAccountTerminationPage.enterTerminationComments();
		storeFrontAccountTerminationPage.selectCheckBoxForVoluntarilyTerminate();
		storeFrontAccountTerminationPage.clickSubmitToTerminateAccount();
		s_assert.assertTrue(storeFrontAccountTerminationPage.verifyPopupHeader(),"Account termination Page Pop Up Header is not Present");
		storeFrontAccountTerminationPage.clickOnConfirmTerminationPopup();
		storeFrontHomePage.loginAsRCUser(rcUserEmailID,password);
		s_assert.assertTrue(storeFrontHomePage.isCurrentURLShowsError(),"Inactive User doesn't get Login failed");  
		s_assert.assertAll();
	}

	//Hybris Project-4663:Consultant Account Termination
	@Test(enabled=false)//Duplicate test,covered in Enrollment validation TC-4308
	public void testConsultantAccountTermination_4663() throws InterruptedException{
		RFO_DB = driver.getDBNameRFO(); 
		List<Map<String, Object>> randomConsultantList =  null;
		String consultantEmailID = null;
		String accountID = null;
		storeFrontHomePage = new StoreFrontHomePage(driver);
		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
			accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			logger.info("Account Id of the user is "+accountID);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		}
		//s_assert.assertTrue(storeFrontConsultantPage.verifyConsultantPage(),"Consultant Page doesn't contain Welcome User Message");
		logger.info("login is successful");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountTerminationPage = storeFrontAccountInfoPage.clickTerminateMyAccount();
		storeFrontAccountTerminationPage.clickSubmitToTerminateAccount();
		//1st combination for validation
		s_assert.assertTrue(storeFrontAccountTerminationPage.verifyFieldValidatonForReason(),"Field Validation Is not Present for Reason");
		s_assert.assertTrue(storeFrontAccountTerminationPage.verifyMessageWithoutComments(),"comment is required message not present on UI without select any reason");
		storeFrontAccountTerminationPage.selectTerminationReason();
		storeFrontAccountTerminationPage.clickSubmitToTerminateAccount();
		//2nd combination for validation
		s_assert.assertTrue(storeFrontAccountTerminationPage.verifyMessageWithoutComments(),"comment is required message not present on UI without select any reason");
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountInfoPage.clickTerminateMyAccount();
		storeFrontAccountTerminationPage.enterTerminationComments();
		storeFrontAccountTerminationPage.clickSubmitToTerminateAccount();
		// 3rd combination for validation
		s_assert.assertTrue(storeFrontAccountTerminationPage.verifyFieldValidatonForReason(),"Field Validation Is not Present for Reason After enter the comments");
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountInfoPage.clickTerminateMyAccount();
		storeFrontAccountTerminationPage.selectTerminationReason();
		storeFrontAccountTerminationPage.enterTerminationComments();
		storeFrontAccountTerminationPage.clickSubmitToTerminateAccount();
		//4th combination for validation
		s_assert.assertTrue(storeFrontAccountTerminationPage.verifyCheckBoxValidationIsPresent(),"Please click on checkbox to agree on the Terms and Conditions not present on UI");
		storeFrontAccountTerminationPage.clickOnAgreementCheckBox();
		storeFrontAccountTerminationPage.clickSubmitToTerminateAccount();
		s_assert.assertTrue(storeFrontAccountTerminationPage.verifyPopupHeader(),"Account termination Page Pop Up Header is Present");
		storeFrontAccountTerminationPage.clickCancelTerminationButton();
		s_assert.assertTrue(storeFrontAccountTerminationPage.verifyWelcomeDropdownToCheckUserRegistered(),"Account is Terminated");
		storeFrontAccountTerminationPage.clickSubmitToTerminateAccount();
		storeFrontAccountTerminationPage.clickOnConfirmTerminationPopup();
		storeFrontAccountTerminationPage.clickOnCloseWindowAfterTermination();
		storeFrontHomePage.clickOnCountryAtWelcomePage();
		storeFrontHomePage.loginAsRCUser(consultantEmailID,password);
		s_assert.assertTrue(storeFrontHomePage.isCurrentURLShowsError(),"Inactive User doesn't get Login failed"); 
		s_assert.assertAll();
	}

	// Hybris Project-2134:EC-789- To Verify subscribe to pulse
	@Test(enabled=false) // already covered in 2233
	public void testVerifySubscribeToPulse_2134() throws InterruptedException	{
		List<Map<String, Object>> randomConsultantList =  null;
		//List<Map<String, Object>> randomConsultantPWSList =  null;
		String consultantWithPWSEmailID = null;
		// Get Consultant with PWS from database
		randomConsultantList =DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment(),driver.getCountry(),countryId),RFO_DB);
		consultantWithPWSEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantWithPWSEmailID, password);
		logger.info("login is successful");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage=storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountInfoPage.clickOnAutoShipStatus();
		//Verify subscribe to pulse(pulse already subscribed)
		s_assert.assertTrue(storeFrontAccountInfoPage.validateSubscribeToPulse(),"pulse is not subscribed for the user");
		s_assert.assertAll();
	}

	//Hybris Project-4803:PC account termination for US new PC
	@Test(enabled=false)//Already covered in TC-2234
	public void testPCAccountTermination_4803() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);  
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String lastName = "lN";
		country = driver.getCountry();
		storeFrontHomePage = new StoreFrontHomePage(driver);
		String firstName=TestConstants.FIRST_NAME+randomNum;
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		//  storeFrontHomePage.applyPriceFilterLowToHigh();
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		//Enter the User information and DO NOT check the "Become a Preferred Customer" checkbox and click the create account button
		String emailAddress = firstName+randomNum+TestConstants.EMAIL_ADDRESS_SUFFIX;
		storeFrontHomePage.enterNewPCDetails(firstName, TestConstants.LAST_NAME+randomNum,emailAddress, password);
		//Enter the Main account info and DO NOT check the "Become a Preferred Customer" and click next
		storeFrontHomePage.enterMainAccountInfo();
		logger.info("Main account details entered");
		storeFrontHomePage.clickOnContinueWithoutSponsorLink();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		//Enter Billing Profile
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
		storeFrontHomePage.clickPlaceOrderBtn();
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		//s_assert.assertTrue(storeFrontHomePage.getUserNameAForVerifyLogin(firstName).contains(firstName),"Profile Name After Login"+firstName+" and on UI is	"+storeFrontHomePage.getUserNameAForVerifyLogin(firstName));
		storeFrontConsultantPage = new StoreFrontConsultantPage(driver);
		storeFrontPCUserPage = new StoreFrontPCUserPage(driver); 
		storeFrontPCUserPage.clickOnWelcomeDropDown();
		storeFrontPCUserPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontPCUserPage.clickOnYourAccountDropdown();
		storeFrontPCUserPage.clickOnPCPerksStatus();
		storeFrontPCUserPage.clickDelayOrCancelPCPerks();
		storeFrontPCUserPage.clickPleaseCancelMyPcPerksActBtn();
		storeFrontPCUserPage.cancelMyPCPerksAct();
		storeFrontHomePage.loginAsPCUser(emailAddress, password);
		s_assert.assertTrue(storeFrontHomePage.isCurrentURLShowsError(),"Inactive User doesn't get Login failed");
		s_assert.assertAll(); 
	}

	//Hybris Project-4648:Cancellation Message for PC account Termination
	@Test(enabled=false)//Duplicate test,covered in Enrollment validation TC-4318
	public void testCancellationMessageforPCaccountTermination_4648() throws InterruptedException{
		RFO_DB = driver.getDBNameRFO();
		List<Map<String, Object>> randomPCUserList =  null;
		String pcUserEmailID = null;
		String accountId = null;
		storeFrontHomePage = new StoreFrontHomePage(driver);
		while(true){
			randomPCUserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_AUTOSHIPS_RFO,countryId),RFO_DB);
			pcUserEmailID = (String) getValueFromQueryResult(randomPCUserList, "UserName");  
			accountId = String.valueOf(getValueFromQueryResult(randomPCUserList, "AccountID"));
			logger.info("Account Id of the user is "+accountId);
			storeFrontPCUserPage = storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
			boolean isError = driver.getCurrentUrl().contains("error");
			if(isError){
				logger.info("SITE NOT FOUND for the user "+pcUserEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		} 
		//s_assert.assertTrue(storeFrontPCUserPage.verifyPCUserPage(),"PC User Page doesn't contain Welcome User Message");
		logger.info("login is successful");
		storeFrontPCUserPage.clickOnWelcomeDropDown();
		storeFrontPCUserPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontPCUserPage.clickOnYourAccountDropdown();
		storeFrontPCUserPage.clickOnPCPerksStatus();
		storeFrontAccountInfoPage = new StoreFrontAccountInfoPage(driver);
		storeFrontAccountInfoPage.clickOndelayOrCancelPCPerks();
		s_assert.assertTrue(storeFrontAccountInfoPage.isYesChangeMyAutoshipDateButtonPresent(),"Yes Change My Autoship Date is Not Presnt on UI");
		s_assert.assertTrue(storeFrontAccountInfoPage.isCancelPCPerksLinkPresent(),"Cancel PC Perks link Not Present");
		s_assert.assertAll();
	}

	// Hybris Project-4647:PC Account Termination
	@Test(enabled=false)//Duplicate test,covered in Enrollment validation TC-4318
	public void testPCAccountTermination_4647() throws InterruptedException{
		country = driver.getCountry();
		RFO_DB = driver.getDBNameRFO();
		//		int randomNum = CommonUtils.getRandomNum(1,6);
		storeFrontHomePage = new StoreFrontHomePage(driver);
		//login As PC User User and verify Product Details and category.
		List<Map<String, Object>> randomPCUserList =  null;
		String pcUserEmailID = null;
		String accountIdForPCUser = null;
		while(true){
			randomPCUserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_AUTOSHIPS_RFO,countryId),RFO_DB);
			pcUserEmailID = (String) getValueFromQueryResult(randomPCUserList, "UserName");		
			accountIdForPCUser = String.valueOf(getValueFromQueryResult(randomPCUserList, "AccountID"));
			logger.info("Account Id of the user is "+accountIdForPCUser);
			storeFrontPCUserPage = storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
			boolean isError = driver.getCurrentUrl().contains("error");
			if(isError){
				logger.info("login error for the user "+pcUserEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		}
		logger.info("login is successful");
		storeFrontPCUserPage.clickOnWelcomeDropDown();
		storeFrontPCUserPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontPCUserPage.clickOnYourAccountDropdown();
		storeFrontPCUserPage.clickOnPCPerksStatus();
		storeFrontPCUserPage.clickDelayOrCancelPCPerks();
		storeFrontPCUserPage.clickPleaseCancelMyPcPerksActBtn();
		//verify account termination reasons present on dropdown.
		s_assert.assertTrue(storeFrontPCUserPage.getAccountterminationReasonTooMuchProduct().equalsIgnoreCase(TestConstants.TOO_MUCH_PRODUCT),"Too Much product option is not present");
		s_assert.assertTrue(storeFrontPCUserPage.getAccountterminationReasonTooExpensive().equalsIgnoreCase(TestConstants.TOO_EXPENSIVE),"Too Much Expensive option is not present");
		s_assert.assertTrue(storeFrontPCUserPage.getAccountterminationReasonEnrolledInAutoShipProgram().equalsIgnoreCase(TestConstants.ENROLLED_IN_AUTOSHIP_PROGRAM),"Did not know i was enrolled in autoship program option is not present");
		s_assert.assertTrue(storeFrontPCUserPage.getAccountterminationReasonProductNotRight().equalsIgnoreCase(TestConstants.PRODUCT_NOT_RIGHT),"Products were not right for me option is not present");
		s_assert.assertTrue(storeFrontPCUserPage.getAccountterminationReasonUpgradingToConsultant().equalsIgnoreCase(TestConstants.UPGRADING_TO_CONSULTANT),"I am upgrading to a Consultant option is not present");
		s_assert.assertTrue(storeFrontPCUserPage.getAccountterminationReasonReceiveProductTooOften().equalsIgnoreCase(TestConstants.RECEIVE_PRODUCT_TOO_OFTEN),"I received products too often option is not present");
		s_assert.assertTrue(storeFrontPCUserPage.getAccountterminationReasonDoNotWantToObligated().equalsIgnoreCase(TestConstants.DO_NOT_WANT_TO_OBLIGATED_TO_ORDER_PRODUCT),"Do not want to be obligated to order product option is not present");
		s_assert.assertTrue(storeFrontPCUserPage.getAccountterminationReasonOther().equalsIgnoreCase(TestConstants.OTHER_REASON),"Other option is not present");
		//assert Check Send cancellation message section 
		s_assert.assertTrue(storeFrontPCUserPage.verifyToSectionInSendcancellationMessageSection(),"To option in not present in send cancellation message section");
		s_assert.assertTrue(storeFrontPCUserPage.verifySubjectSectionInSendcancellationMessageSection(),"Subject option in not present in send cancellation message section");
		s_assert.assertTrue(storeFrontPCUserPage.verifyMessageSectionInSendcancellationMessageSection(),"Message option in not present in send cancellation message section");
		//assert Check 2 buttons at the bottom of the page
		s_assert.assertTrue(storeFrontPCUserPage.verifyIHaveChangedMyMindButton(),"To option in not present in send cancellation message section");
		s_assert.assertTrue(storeFrontPCUserPage.verifySendAnEmailToCancelAccountButton(),"Subject option in not present in send cancellation message section");
		//continue with pc User Account termination process.
		storeFrontPCUserPage.cancelMyPCPerksAct();
		storeFrontHomePage.loginAsConsultant(pcUserEmailID, password);
		s_assert.assertTrue(storeFrontHomePage.isCurrentURLShowsError(),"Inactive User doesn't get Login failed");
		s_assert.assertAll();	
	}


	//Hybris Project-5002:Modify Main Phone Number and mobile phone information
	@Test(enabled=false)//Covered in TC - 5003
	public void testModifyMainPhoneNmberAndMobilePhoneInfo_5002() throws InterruptedException{
		if(driver.getCountry().equalsIgnoreCase("ca")){
			RFO_DB = driver.getDBNameRFO();
			storeFrontHomePage = new StoreFrontHomePage(driver);
			String mainPhoneNumberDB = null;
			List<Map<String, Object>> mainPhoneNumberList =  null;
			List<Map<String, Object>> randomConsultantList =  null;
			String consultantEmailID = null;
			String accountIdForConsultant = null;
			while(true){
				randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
				consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
				accountIdForConsultant = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
				logger.info("Account Id of the user is "+accountIdForConsultant);
				storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
				boolean isError = driver.getCurrentUrl().contains("error");
				if(isError){
					logger.info("login error for the user "+consultantEmailID);
					driver.get(driver.getURL()+"/"+driver.getCountry());
				}
				else
					break;
			}
			//s_assert.assertTrue(storeFrontConsultantPage.verifyConsultantPage(),"Consultant User Page doesn't contain Welcome User Message");
			logger.info("login is successful");
			storeFrontConsultantPage.clickOnWelcomeDropDown();
			storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
			storeFrontAccountInfoPage.enterMainPhoneNumber(TestConstants.CONSULTANT_MAIN_PHONE_NUMBER_FOR_ACCOUNT_INFORMATION);
			s_assert.assertTrue(storeFrontAccountInfoPage.isGlobalUpdateMessageDisplayed(),"Profile updation message not present on UI");
			storeFrontAccountInfoPage.enterMobileNumber(TestConstants.PHONE_NUMBER_CA);
			s_assert.assertTrue(storeFrontAccountInfoPage.isGlobalUpdateMessageDisplayed(),"Profile updation message not present on UI");
			mainPhoneNumberList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_PHONE_NUMBER_QUERY_RFO, consultantEmailID), RFO_DB);
			mainPhoneNumberDB = (String) getValueFromQueryResult(mainPhoneNumberList, "PhoneNumberRaw");
			assertTrue("Main Phone Number on UI is different from DB", storeFrontAccountInfoPage.verifyMainPhoneNumberFromUIForAccountInfo(mainPhoneNumberDB));
			s_assert.assertAll();
		}else{
			logger.info("NOT EXECUTED...Test is ONLY for CANADA env");
		}
	}

	//  Hybris Project-5000:Modify Email Address for User
	@Test(enabled=false)//email address not present on account info page, so verifying th username only which is covered in TC-4260
	public void testModifyEmailAddressForUser_5000() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newUserName = TestConstants.CONSULTANT_USERNAME_PREFIX+randomNum+TestConstants.EMAIL_ADDRESS_SUFFIX;
		List<Map<String, Object>> randomConsultantList =  null;
		String consultantEmailID = null;
		String accountIdForConsultant = null;
		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
			accountIdForConsultant = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			logger.info("Account Id of the user is "+accountIdForConsultant);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isError = driver.getCurrentUrl().contains("error");
			if(isError){
				logger.info("login error for the user "+consultantEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		}
		logger.info("login is successful");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyEmailAddressOnAccountInfoPage(consultantEmailID),"Email address is not "+consultantEmailID+"");
		storeFrontAccountInfoPage.enterUserName(newUserName);
		storeFrontAccountInfoPage.clickOnSaveAfterEnterSpouseDetails();
		s_assert.assertTrue(storeFrontAccountInfoPage.isGlobalUpdateMessageDisplayed(),"Profile updation message not present on UI");
		logout();
		storeFrontHomePage.loginAsConsultant(newUserName, password);
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyEmailAddressOnAccountInfoPage(newUserName),"Email address is not "+newUserName+"");
		s_assert.assertAll();
	}

	// Hybris Project-4359:New autoship date for Reactivated Consultant
	@Test(enabled=false)//Test No longer valid
	public void testAutoshipDateForReactivatedConsultant_4359() throws InterruptedException, Exception{
		if(driver.getCountry().equalsIgnoreCase("ca")){
			RFO_DB = driver.getDBNameRFO(); 
			List<Map<String, Object>> randomConsultantList =  null;
			String consultantEmailID = null;
			String accountID = null;
			enrollmentType = TestConstants.EXPRESS_ENROLLMENT;
			regimenName =  TestConstants.REGIMEN_NAME_REDEFINE;
			while(true){
				randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment(),driver.getCountry(),countryId),RFO_DB);
				consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
				accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
				logger.info("Account Id of the user is "+accountID);
				storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
				boolean isLoginError = driver.getCurrentUrl().contains("error");
				if(isLoginError){
					logger.info("Login error for the user "+consultantEmailID);
					driver.get(driver.getURL()+"/"+driver.getCountry());
				}
				else
					break;
			}
			//s_assert.assertTrue(storeFrontConsultantPage.verifyConsultantPage(),"Consultant Page doesn't contain Welcome User Message");
			logger.info("login is successful");
			storeFrontConsultantPage.clickOnWelcomeDropDown();
			storeFrontOrdersPage = storeFrontHomePage.clickOrdersLinkPresentOnWelcomeDropDown();
			String dueAutoshipDateFromBeforeTerminationUI = storeFrontOrdersPage.getAutoshipOrderDate();
			storeFrontConsultantPage.clickOnWelcomeDropDown();
			storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
			storeFrontAccountInfoPage.clickOnYourAccountDropdown();
			storeFrontAccountTerminationPage = storeFrontAccountInfoPage.clickTerminateMyAccount();
			storeFrontAccountTerminationPage.fillTheEntriesAndClickOnSubmitDuringTermination();   
			s_assert.assertTrue(storeFrontAccountTerminationPage.verifyAccountTerminationIsConfirmedPopup(), "Account still exist");
			storeFrontAccountTerminationPage.clickOnConfirmTerminationPopup();
			storeFrontAccountTerminationPage.clickOnCloseWindowAfterTermination();
			storeFrontHomePage.clickOnCountryAtWelcomePage();
			//Again enroll the consultant with same eamil id
			kitName = TestConstants.KIT_NAME_BIG_BUSINESS; //TestConstants.KIT_PRICE_BIG_BUSINESS_CA;    
			addressLine1 = TestConstants.ADDRESS_LINE_1_CA;
			city = TestConstants.CITY_CA;
			postalCode = TestConstants.POSTAL_CODE_CA;
			phoneNumber = TestConstants.PHONE_NUMBER_CA;
			// Get Canadian sponser with PWS from database
			List<Map<String, Object>> sponserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment(),driver.getCountry(),countryId),RFO_DB);
			String sponserHavingPulse = String.valueOf(getValueFromQueryResult(sponserList, "AccountID"));
			// sponser search by Account Number
			List<Map<String, Object>> sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,sponserHavingPulse),RFO_DB);
			String sponsorId = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
			storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
			storeFrontHomePage.searchCID(sponsorId);
			storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
			storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);  
			storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
			storeFrontHomePage.enterEmailAddress(consultantEmailID);
			s_assert.assertTrue(storeFrontHomePage.verifyInvalidSponsorPopupIsPresent(), "Invalid Sponsor popup is not present");
			storeFrontHomePage.clickOnEnrollUnderLastUpline();
			// For enroll the same consultant
			storeFrontHomePage.selectEnrollmentKitPage(kitName, regimenName);  
			storeFrontHomePage.chooseEnrollmentOption(enrollmentType);
			storeFrontHomePage.enterEmailAddress(consultantEmailID);
			storeFrontHomePage.enterPasswordForReactivationForConsultant();
			storeFrontHomePage.clickOnLoginToReactiveMyAccountForConsultant();
			storeFrontHomePage.clickOnWelcomeDropDown();
			storeFrontOrdersPage = storeFrontHomePage.clickOrdersLinkPresentOnWelcomeDropDown();
			String dueAutoshipDateFromAfterReactivationUI = storeFrontOrdersPage.getAutoshipOrderDate();
			s_assert.assertTrue(dueAutoshipDateFromAfterReactivationUI.contains(dueAutoshipDateFromBeforeTerminationUI), "Autoship date after reactivation is not same as before account termination");
			s_assert.assertAll();
		}else{
			logger.info("NOT EXECUTED...Test is ONLY for CANADA env");
		}
	}

	//Hybris Project-4274:UserName Field Validation
	@Test(enabled=false)//already covered in TC-4260
	public void testVerifyUserNameFieldValidation_4274() throws InterruptedException {		
		int randomNum = CommonUtils.getRandomNum(10000, 100000);
		List<Map<String, Object>> randomConsultantList =  null;
		String consultantEmailID = null;
		String accountID = null;
		String specialSymbolUsername="test"+randomNum+TestConstants.EMAIL_ADDRESS_SUFFIX;
		//Get consultant from database .
		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
			accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			logger.info("Account Id of the user is "+accountID);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		}
		logger.info("login is successful");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage= storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		//String existingUserName=storeFrontAccountInfoPage.getExistingUserName();
		storeFrontAccountInfoPage.enterUserName(specialSymbolUsername);
		storeFrontAccountInfoPage.clickSaveAccountPageInfo();
		s_assert.assertTrue(storeFrontAccountInfoPage.isGlobalUpdateMessageDisplayed(),"Profile updation message not appear for correct username");
		logout();
		// login with new username.
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(specialSymbolUsername, password);
		s_assert.assertTrue(storeFrontConsultantPage.verifyConsultantPage(),"login is not successful");
		s_assert.assertAll();
	}

	// Hybris Project-4064:Access US Con's Canadian PWS as a Canadian Consultant W/O Pulse
	@Test(enabled=false)//cross country pws
	public void testAccessUSConsCanadianPWSAsACanadianConsultantWithoutPulse_4064() throws InterruptedException{
		RFO_DB = driver.getDBNameRFO();
		List<Map<String, Object>> randomConsultantList =  null;
		List<Map<String, Object>> randomConsultantList2 =  null;
		String usConsultantPWS = null;
		String consultantEmailID = null;
		String countryID ="236";
		String country = "us";
		storeFrontHomePage = new StoreFrontHomePage(driver);
		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITHOUT_PULSE_RFO,countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "EmailAddress"); 
			randomConsultantList2 =  DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",country,countryID), RFO_DB);
			usConsultantPWS = (String) getValueFromQueryResult(randomConsultantList2, "URL"); 
			String caConsultantPWS = usConsultantPWS.replaceAll("us", "ca");
			driver.get(caConsultantPWS);
			boolean isPWSError = driver.getCurrentUrl().contains("sitenotfound");
			if(isPWSError){
				logger.info("PWS error for the user "+consultantEmailID);
				driver.get(caConsultantPWS);
			}
			else
				break;
		}
		while(true){
			storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		}
		logger.info("login is successful");
		s_assert.assertTrue(driver.getCurrentUrl().contains("corprfo"),"current url is not a corp url");
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(),"welcome dropDown is not present after login");
		s_assert.assertAll();
	}

	// Hybris Project-4065:Login on US Con's Canadian PWS as a Canadian Consultant W/O Pulse
	@Test(enabled=false)//cross country pws
	public void testLoginOnUsConsCanadianPWSAsACanadianConsultantWithoutPulse_4065() throws InterruptedException{
		RFO_DB = driver.getDBNameRFO();
		List<Map<String, Object>> randomConsultantList =  null;
		List<Map<String, Object>> randomConsultantList2 =  null;
		String usConsultantPWS = null;
		String consultantEmailID = null;
		String countryID ="236";
		String country = "us";
		storeFrontHomePage = new StoreFrontHomePage(driver);
		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITHOUT_PULSE_RFO,countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "EmailAddress"); 
			randomConsultantList2 =  DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",country,countryID), RFO_DB);
			usConsultantPWS = (String) getValueFromQueryResult(randomConsultantList2, "URL"); 
			String caConsultantPWS = usConsultantPWS.replaceAll("us", "ca");
			driver.get(caConsultantPWS);
			boolean isPWSError = driver.getCurrentUrl().contains("sitenotfound");
			if(isPWSError){
				logger.info("PWS error for the user "+consultantEmailID);
				driver.get(caConsultantPWS);
			}
			else
				break;
		}
		while(true){
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		}
		logger.info("login is successful");
		s_assert.assertTrue(driver.getCurrentUrl().contains("corprfo"),"current url is not a corp url");
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(),"welcome dropDown is not present after login");
		s_assert.assertTrue(storeFrontConsultantPage.isAutoshipLinkPresentOnThePage(),"Autoship link is not present");
		s_assert.assertAll();
	}

	//Hybris Project-4814:Edit Existing Shipping Billing Address on Edit CRP Template
	@Test(enabled=false)//Already covered in shipping billing tests
	public void testEditExistingShippingAndBillingAddressOnEditCRPTemplate_4814() throws InterruptedException{
		RFO_DB = driver.getDBNameRFO(); 
		List<Map<String, Object>> randomConsultantList =  null;
		String consultantEmailID = null;
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		int randomNum1 = CommonUtils.getRandomNum(10000, 1000000);
		int randomNum2 = CommonUtils.getRandomNum(10000, 1000000);
		String profileName=null;
		String lastName = "lN";
		String state = null;
		String accountID = null;
		String billingProfileName = TestConstants.BILLING_ADDRESS_NAME+randomNumber;
		String newBillingProfileName = TestConstants.BILLING_ADDRESS_NAME+randomNum2;
		String newProfileName=TestConstants.NEW_SHIPPING_PROFILE_FIRST_NAME+randomNum1;
		storeFrontHomePage = new StoreFrontHomePage(driver);
		storeFrontUpdateCartPage = new StoreFrontUpdateCartPage(driver);
		if(driver.getCountry().equalsIgnoreCase("CA")){   
			profileName=TestConstants.NEW_SHIPPING_PROFILE_FIRST_NAME_CA+randomNum;
			addressLine1 = TestConstants.ADDRESS_LINE_1_CA;
			city = TestConstants.CITY_CA;
			postalCode = TestConstants.POSTAL_CODE_CA;
			phoneNumber = TestConstants.PHONE_NUMBER_CA;
			state = TestConstants.PROVINCE_CA;
		}else{
			profileName=TestConstants.NEW_SHIPPING_PROFILE_FIRST_NAME_US+randomNum;
			addressLine1 = TestConstants.NEW_ADDRESS_LINE1_US;
			city = TestConstants.NEW_ADDRESS_CITY_US;
			postalCode = TestConstants.NEW_ADDRESS_POSTAL_CODE_US;
			phoneNumber = TestConstants.NEW_ADDRESS_PHONE_NUMBER_US;
		}
		storeFrontHomePage = new StoreFrontHomePage(driver);
		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
			accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			logger.info("Account Id of the user is "+accountID);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		}
		logger.info("login is successful");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountInfoPage.clickOnAutoShipStatus();
		storeFrontAccountInfoPage.clickOnCancelMyCRP();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyCRPCancelled(), "CRP has not been cancelled");
		//Enroll new CRP
		storeFrontAccountInfoPage.clickOnEnrollInCRP();
		storeFrontHomePage.clickOnAddToCRPButtonCreatingCRPUnderBizSite();
		storeFrontHomePage.clickOnCRPCheckout();
		storeFrontHomePage.clickOnEditForDefaultShippingAddress();
		storeFrontHomePage.enterNewShippingAddressName(profileName+" "+lastName);
		storeFrontHomePage.enterNewShippingAddressLine1(addressLine1);
		storeFrontHomePage.enterNewShippingAddressCity(city);
		storeFrontHomePage.selectNewShippingAddressState(state);
		storeFrontHomePage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontHomePage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontHomePage.clickOnSaveShippingProfile();
		s_assert.assertTrue(storeFrontHomePage.isNewlyCreatedShippingProfileIsSelectedByDefault(profileName),"New Shipping Profile is not selected by default on CRP cart page");
		storeFrontHomePage.clickOnUpdateCartShippingNextStepBtnDuringEnrollment();
		storeFrontHomePage.clickOnDefaultBillingProfileEdit();
		storeFrontHomePage.enterEditedCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(billingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		s_assert.assertTrue(storeFrontHomePage.getDefaultSelectedBillingAddressName().contains(billingProfileName), "Expected shipping profile name is: "+billingProfileName+"Actual on UI is: "+storeFrontHomePage.getDefaultSelectedBillingAddressName());
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickOnSetupCRPAccountBtn();
		s_assert.assertTrue(storeFrontHomePage.verifyOrderConfirmation(), "Order Confirmation Message has not been displayed");
		// assert shipping and billing profile
		storeFrontUpdateCartPage = new StoreFrontUpdateCartPage(driver);
		storeFrontHomePage.clickOnAutoshipCart();
		storeFrontUpdateCartPage.clickOnUpdateMoreInfoButton();
		s_assert.assertTrue(storeFrontUpdateCartPage.validateNewlySelectedDefaultShippingProfileIsUpdatedInAutoshipShippingSection(profileName), "Autoship template does not contain shipping profile name");
		s_assert.assertTrue(storeFrontUpdateCartPage.validateNewlySelectedDefaultBillingProfileIsNotUpdatedInAutoshipBillingProfileSection(billingProfileName), "Autoship template does not contain billing profile name");
		// Edit shipping and billing
		storeFrontUpdateCartPage.clickOnEditShipping();
		storeFrontUpdateCartPage.clickOnEditForDefaultShippingAddress();
		storeFrontUpdateCartPage.enterNewShippingAddressName(newProfileName+" "+lastName);
		storeFrontUpdateCartPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontUpdateCartPage.enterNewShippingAddressCity(city);
		storeFrontUpdateCartPage.selectNewShippingAddressState(state);
		storeFrontUpdateCartPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontUpdateCartPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontUpdateCartPage.clickOnSaveShippingProfile();
		s_assert.assertTrue(storeFrontUpdateCartPage.isNewlyCreatedShippingProfileIsSelectedByDefault(newProfileName),"New Shipping Profile is not selected by default on CRP cart page");
		s_assert.assertTrue(storeFrontUpdateCartPage.getDefaultSelectedShippingAddress().contains(newProfileName), "Expected shipping profile name is: "+newProfileName+"Actual on UI is: "+storeFrontUpdateCartPage.getDefaultSelectedShippingAddress());
		storeFrontUpdateCartPage.clickOnEditPaymentBillingProfile();
		storeFrontUpdateCartPage.clickOnDefaultBillingProfileEdit();
		storeFrontUpdateCartPage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontUpdateCartPage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontUpdateCartPage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontUpdateCartPage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontUpdateCartPage.selectNewBillingCardAddress();
		storeFrontUpdateCartPage.clickOnSaveBillingProfile();
		s_assert.assertTrue(storeFrontUpdateCartPage.getDefaultSelectedBillingAddressName().contains(newBillingProfileName), "Expected shipping profile name is: "+newBillingProfileName+"Actual on UI is: "+storeFrontUpdateCartPage.getDefaultSelectedBillingAddressName());
		storeFrontUpdateCartPage.clickOnNextStepBtn();
		storeFrontUpdateCartPage.clickUpdateCartBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyUpdatedAddressPresentUpdateCartPg(newProfileName), "Autoship template does not contain shipping profile name in Edit section");
		s_assert.assertTrue(storeFrontUpdateCartPage.validateNewlySelectedDefaultBillingProfileIsNotUpdatedInAutoshipBillingProfileSection(newBillingProfileName), "Autoship template does not contain billing profile name in Edit section");
		s_assert.assertAll();
	}

	//Hybris Project-4813:Add New Shipping Billing Address on Edit CRP Template
	@Test(enabled=false)//ALready covered in billing shipping tests
	public void testAddNewShippingAndBillingAddressOnEditCRPTemplate_4813() throws InterruptedException{
		RFO_DB = driver.getDBNameRFO(); 
		List<Map<String, Object>> randomConsultantList =  null;
		String consultantEmailID = null;
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		int randomNum1 = CommonUtils.getRandomNum(10000, 1000000);
		int randomNum2 = CommonUtils.getRandomNum(10000, 1000000);
		int i=0;
		int countOfBilling = 0;
		String profileName=null;
		String lastName = "lN";
		String accountID = null;
		String state = null;
		String billingProfileName = TestConstants.BILLING_ADDRESS_NAME+randomNumber;
		String newBillingProfileName = TestConstants.BILLING_ADDRESS_NAME+randomNum2;
		String newProfileName=TestConstants.NEW_SHIPPING_PROFILE_FIRST_NAME+randomNum1;
		storeFrontHomePage = new StoreFrontHomePage(driver);
		if(driver.getCountry().equalsIgnoreCase("CA")){   
			profileName=TestConstants.NEW_SHIPPING_PROFILE_FIRST_NAME_CA+randomNum;
			addressLine1 = TestConstants.ADDRESS_LINE_1_CA;
			city = TestConstants.CITY_CA;
			state = TestConstants.PROVINCE_CA;
			postalCode = TestConstants.POSTAL_CODE_CA;
			phoneNumber = TestConstants.PHONE_NUMBER_CA;
		}else{
			profileName=TestConstants.NEW_SHIPPING_PROFILE_FIRST_NAME_US+randomNum;
			addressLine1 = TestConstants.NEW_ADDRESS_LINE1_US;
			city = TestConstants.NEW_ADDRESS_CITY_US;
			postalCode = TestConstants.NEW_ADDRESS_POSTAL_CODE_US;
			phoneNumber = TestConstants.NEW_ADDRESS_PHONE_NUMBER_US;
			state = TestConstants.PROVINCE_US;
		}
		storeFrontHomePage = new StoreFrontHomePage(driver);
		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
			accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			logger.info("Account Id of the user is "+accountID);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		}
		logger.info("login is successful");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountInfoPage.clickOnAutoShipStatus();
		storeFrontAccountInfoPage.clickOnCancelMyCRP();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyCRPCancelled(), "CRP has not been cancelled");
		//Enroll new CRP
		storeFrontAccountInfoPage.clickOnEnrollInCRP();
		storeFrontHomePage.clickOnAddToCRPButtonCreatingCRPUnderBizSite();
		storeFrontHomePage.clickOnCRPCheckout();
		//Add multiple shipping address
		for(i=0;i<2;i++){
			storeFrontHomePage.clickAddNewShippingProfileLink();
			storeFrontHomePage.enterNewShippingAddressName(profileName+i+" "+lastName);
			storeFrontHomePage.enterNewShippingAddressLine1(addressLine1);
			storeFrontHomePage.enterNewShippingAddressCity(city);
			storeFrontHomePage.selectNewShippingAddressState(state);
			storeFrontHomePage.enterNewShippingAddressPostalCode(postalCode);
			storeFrontHomePage.enterNewShippingAddressPhoneNumber(phoneNumber);
			storeFrontHomePage.clickOnSaveShippingProfile();
			s_assert.assertTrue(storeFrontHomePage.isNewlyCreatedShippingProfileIsSelectedByDefault(profileName+i),"New Shipping Profile is not selected by default on CRP cart page");
		}
		i=1;
		storeFrontHomePage.selectShippingAddress(profileName+i);
		s_assert.assertTrue(storeFrontHomePage.getDefaultSelectedShippingAddress().contains(profileName+i), "Expected shipping profile name is: "+profileName+i+"Actual on UI is: "+storeFrontHomePage.getDefaultSelectedShippingAddress());
		storeFrontHomePage.clickOnUpdateCartShippingNextStepBtnDuringEnrollment();
		for(countOfBilling=0;countOfBilling<2;countOfBilling++){
			storeFrontHomePage.clickAddNewBillingProfileLink();
			storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
			storeFrontHomePage.enterNewBillingNameOnCard(billingProfileName+countOfBilling+" "+lastName);
			storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
			storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
			storeFrontHomePage.selectNewBillingCardAddress();
			storeFrontHomePage.clickOnSaveBillingProfile();
		}
		countOfBilling = 1;
		storeFrontHomePage.selectBillingAddress(billingProfileName+countOfBilling);
		s_assert.assertTrue(storeFrontHomePage.getDefaultSelectedBillingAddressName().contains(billingProfileName+countOfBilling), "Expected shipping profile name is: "+billingProfileName+countOfBilling+"Actual on UI is: "+storeFrontHomePage.getDefaultSelectedBillingAddressName());
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickOnSetupCRPAccountBtn();
		s_assert.assertTrue(storeFrontHomePage.verifyOrderConfirmation(), "Order Confirmation Message has not been displayed");
		// assert shipping and billing profile
		storeFrontUpdateCartPage = new StoreFrontUpdateCartPage(driver);
		storeFrontHomePage.clickOnAutoshipCart();
		storeFrontUpdateCartPage.clickOnUpdateMoreInfoButton();
		s_assert.assertTrue(storeFrontUpdateCartPage.validateNewlySelectedDefaultShippingProfileIsUpdatedInAutoshipShippingSection(profileName+i), "Autoship template does not contain shipping profile name");
		s_assert.assertTrue(storeFrontUpdateCartPage.validateNewlySelectedDefaultBillingProfileIsNotUpdatedInAutoshipBillingProfileSection(billingProfileName+countOfBilling), "Autoship template does not contain billing profile name");
		// add shipping and billing
		storeFrontUpdateCartPage.clickOnEditShipping();
		for(i=1;i<=2;i++){
			storeFrontUpdateCartPage.clickAddNewShippingProfileLink();
			storeFrontUpdateCartPage.enterNewShippingAddressName(newProfileName+i+" "+lastName);
			storeFrontUpdateCartPage.enterNewShippingAddressLine1(addressLine1);
			storeFrontUpdateCartPage.enterNewShippingAddressCity(city);
			storeFrontUpdateCartPage.selectNewShippingAddressState(state);
			storeFrontUpdateCartPage.enterNewShippingAddressPostalCode(postalCode);
			storeFrontUpdateCartPage.enterNewShippingAddressPhoneNumber(phoneNumber);
			storeFrontUpdateCartPage.clickOnSaveShippingProfile();
			s_assert.assertTrue(storeFrontUpdateCartPage.isNewlyCreatedShippingProfileIsSelectedByDefault(newProfileName+i),"New Shipping Profile is not selected by default on CRP cart page");
		}
		i=1;
		storeFrontUpdateCartPage.selectShippingAddress(newProfileName+i);
		s_assert.assertTrue(storeFrontUpdateCartPage.getDefaultSelectedShippingAddress().contains(newProfileName+i), "Expected shipping profile name is: "+newProfileName+i+"Actual on UI is: "+storeFrontUpdateCartPage.getDefaultSelectedShippingAddress());
		storeFrontUpdateCartPage.clickOnUpdateCartShippingNextStepBtn();
		for(countOfBilling=1;countOfBilling<=2;countOfBilling++){
			storeFrontUpdateCartPage.clickAddNewBillingProfileLink();
			storeFrontUpdateCartPage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
			storeFrontUpdateCartPage.enterNewBillingNameOnCard(newBillingProfileName+countOfBilling+" "+lastName);
			storeFrontUpdateCartPage.selectNewBillingCardExpirationDate("OCT","2025");
			storeFrontUpdateCartPage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
			storeFrontUpdateCartPage.selectNewBillingCardAddress();
			storeFrontUpdateCartPage.clickOnSaveBillingProfile();
		}
		countOfBilling = 1;
		storeFrontUpdateCartPage.selectBillingAddress(newBillingProfileName+countOfBilling);
		s_assert.assertTrue(storeFrontUpdateCartPage.getDefaultSelectedBillingAddressName().contains(newBillingProfileName+countOfBilling), "Expected shipping profile name is: "+newBillingProfileName+countOfBilling+"Actual on UI is: "+storeFrontUpdateCartPage.getDefaultSelectedBillingAddressName());
		storeFrontUpdateCartPage.clickOnNextStepBtn();
		storeFrontUpdateCartPage.clickUpdateCartBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyUpdatedAddressPresentUpdateCartPg(newProfileName+i), "Autoship template does not contain shipping profile name in Edit section");
		s_assert.assertTrue(storeFrontUpdateCartPage.validateNewlySelectedDefaultBillingProfileIsNotUpdatedInAutoshipBillingProfileSection(newBillingProfileName+countOfBilling), "Autoship template does not contain billing profile name in Edit section");
		s_assert.assertAll();
	}
}

