package com.rf.test.website.nscore4.dsv;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.rf.core.utils.CommonUtils;
import com.rf.core.website.constants.TestConstantsRFL;
import com.rf.pages.website.nscore.dsv.DSVNSCore4HomePage;
import com.rf.pages.website.nscore.dsv.DSVNSCore4OrderDetailsPage;
import com.rf.test.website.RFDSVNSCoreWebsiteBaseTest;

@Listeners({com.rf.core.listeners.RetryListener.class,com.rf.core.listeners.TestListner.class})
public class NSCore4DSVTest extends RFDSVNSCoreWebsiteBaseTest{

	private DSVNSCore4HomePage dsvNSCore4HomePage;
	private DSVNSCore4OrderDetailsPage dsvNSCore4OrderDetailsPage;

	int randomNum = 0;
	String newShippingProfileName = null;
	String newShippingProfilePrefix = null;
	String attentionCO = null;;
	String addressLine1 = null;;
	String zipCode = null;
	String updatedShippingProfileName = null;
	String updatedShippingProfilePrefix = null;
	String newBillingProfileName = null;
	String newBillingProfilePrefix = null;
	String updatedBillingProfileName = null;
	String updatedBillingProfilePrefix = null;
	String lastNameForProfile = null;
	String nameOnCard = null;
	String cardNumber = null;
	String cvv = null;

	public NSCore4DSVTest() {
		dsvNSCore4HomePage = new DSVNSCore4HomePage(driver);
		newShippingProfilePrefix = TestConstantsRFL.NEW_SHIPPING_PROFILE_PREFIX;
		updatedShippingProfilePrefix = TestConstantsRFL.UPDATED_SHIPPING_PROFILE_PREFIX;
		attentionCO = TestConstantsRFL.ATTENTION_CO_NAME;
		addressLine1 = TestConstantsRFL.DSV_ADDRESS_LINE_1;
		zipCode = TestConstantsRFL.DSV_POSTAL_CODE;
		newBillingProfilePrefix = TestConstantsRFL.NEW_BILLING_PROFILE_PREFIX;
		updatedBillingProfilePrefix = TestConstantsRFL.UPDATED_BILLLING_PROFILE_PREFIX;
		lastNameForProfile = TestConstantsRFL.DSV_BILLING_PROFILE_LAST_NAME;
		nameOnCard = TestConstantsRFL.DSV_NAME_ON_CARD;
		cardNumber = TestConstantsRFL.DSV_CARD_NUMBER;
		cvv = TestConstantsRFL.DSV_SECURITY_CODE;
	}

	@Test(groups = {"Cons"},priority=1)
	public void testSearchFunctionalityWithAccountIdByBrowsingAccountsForCons(){
		String firstName = TestConstantsRFL.DSV_CONS_FIRST_NAME;
		String lastName = TestConstantsRFL.DSV_CONS_LAST_NAME;
		String consAccountNumber = TestConstantsRFL.CONS_ACCOUNT_NUMBER;
		String emailId = TestConstantsRFL.DSV_CONS_EMAIL_ID;
		dsvNSCore4HomePage.enterAccountInfoInAccountSearchField(consAccountNumber,emailId);
		s_assert.assertTrue(dsvNSCore4HomePage.verifyPresenceOfAccountNumberInSearchResults(consAccountNumber),"Account Number : "+consAccountNumber+" is not present in Search Results");
		s_assert.assertTrue(dsvNSCore4HomePage.isFirstNamePresentAsExpectedInSearchResults(consAccountNumber, firstName),"First Name is not present as Expected in Search Results");
		s_assert.assertTrue(dsvNSCore4HomePage.isLastNamePresentAsExpectedInSearchResults(consAccountNumber, lastName),"Last Name is not present as Expected in Search Results");
		dsvNSCore4HomePage.clickOnAccountNumberInSearchResults(consAccountNumber);
		s_assert.assertTrue(dsvNSCore4HomePage.isOrderPresentOnAccountOverview(),"No Order is present in order history for this User");
		dsvNSCore4OrderDetailsPage = dsvNSCore4HomePage.clickOrderNumberLinkFromAccountOverview();
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyCustomerInfoOnOrderDetailsPage(firstName),"First Name is not present as Expected on Order Details Page");
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyCustomerInfoOnOrderDetailsPage(lastName),"Last Name is not present as Expected on Order Details Page");
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyCustomerInfoOnOrderDetailsPage(consAccountNumber),"Account Number is not present as Expected on Order Details Page");
		s_assert.assertAll();
	}

	// Verify Full Order History and Order Details
	@Test(groups = {"Cons"},priority=2)
	public void testVerifyFullOrderHistoryAndOrderDetailsForCons(){
		String consAccountNumber = TestConstantsRFL.CONS_ACCOUNT_NUMBER;
		String firstName = TestConstantsRFL.DSV_CONS_FIRST_NAME;
		String lastName = TestConstantsRFL.DSV_CONS_LAST_NAME;
		String emailId = TestConstantsRFL.DSV_CONS_EMAIL_ID;
		String urlToAssert = "Orders/Details";
		String currentUrl = null;
		dsvNSCore4HomePage.enterAccountInfoInAccountSearchField(consAccountNumber,emailId);
		dsvNSCore4HomePage.clickOnAccountNumberInSearchResults(consAccountNumber);
		dsvNSCore4HomePage.clickFullOrderHistory();
		s_assert.assertTrue(dsvNSCore4HomePage.isOrdersPresentInOrderHistory(),
				"No Order is present in order history for this User");
		dsvNSCore4OrderDetailsPage = dsvNSCore4HomePage.clickOrderNumberLinkFromOrderHistory();
		currentUrl = dsvNSCore4OrderDetailsPage.getCurrentUrl();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL does not contain Expected URL. Current URL : "+currentUrl+". Expected Url : "+urlToAssert);
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyCustomerInfoOnOrderDetailsPage(firstName),"First Name is not present as Expected on Order Details Page");
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyCustomerInfoOnOrderDetailsPage(lastName),"Last Name is not present as Expected on Order Details Page");
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyCustomerInfoOnOrderDetailsPage(consAccountNumber),"Account Number is not present as Expected on Order Details Page");
		// Verifying Product Section
		/*s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyPresenceOfSKUInProductsSection(),"SKU is not present in Products Section");
		// Verifying Shipment Section
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyAddressInShipmentSection(),"Shipping Address is not present in Shipment section");
		// Verifying Payment Section
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyPresenceOfPaymentDetailsInPaymentSection(),"Payment Detail is not present in Payment Section");
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyPresenceOfTotalAmountInPaymentSection(),"Total Amount is not Present in Payment section");*/
		s_assert.assertAll();
	}

	// Verify Full Account Record
	@Test(groups = {"Cons"},priority=3)
	public void testVerifyFullAccountRecordForCons(){
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstantsRFL.DSV_CONS_FIRST_NAME;
		String lastName = TestConstantsRFL.DSV_CONS_LAST_NAME;
		String consAccountNumber = TestConstantsRFL.CONS_ACCOUNT_NUMBER;
		String userType = TestConstantsRFL.CONS_USER_TYPE;
		String emailId = TestConstantsRFL.DSV_CONS_EMAIL_ID;
		String updatedLastName = lastName + randomNum;
		dsvNSCore4HomePage.enterAccountInfoInAccountSearchField(consAccountNumber,emailId);
		dsvNSCore4HomePage.clickOnAccountNumberInSearchResults(consAccountNumber);
		dsvNSCore4HomePage.clickFullAccountRecordFromLeftNavigation();
		s_assert.assertTrue(dsvNSCore4HomePage.verifyAccountTypeInFullRecord(userType),"Account Type is found as Expected");
		s_assert.assertTrue(dsvNSCore4HomePage.verifyFirstNameFromPersonalInfo(firstName),"First Name is not present as expected in Personal Info");
		s_assert.assertTrue(dsvNSCore4HomePage.verifyLastNameFromPersonalInfo(lastName),"Last Name is not present as expected in Personal Info");
		dsvNSCore4HomePage.editLastNameFromPersonalInfo(updatedLastName);
		dsvNSCore4HomePage.clickSavePersonalInfoBtn();
		dsvNSCore4HomePage.clickAcceptButtonOnAddressVerificationPopup();
		dsvNSCore4HomePage.refreshPage();
		s_assert.assertTrue(dsvNSCore4HomePage.verifyUserInfoOnAccountOverviewPage(updatedLastName),"Updated Personal Info is not present on Account Overview Page");
		dsvNSCore4HomePage.editLastNameFromPersonalInfo(lastName);
		dsvNSCore4HomePage.clickSavePersonalInfoBtn();
		dsvNSCore4HomePage.clickAcceptButtonOnAddressVerificationPopup();
		dsvNSCore4HomePage.refreshPage();
		s_assert.assertTrue(dsvNSCore4HomePage.verifyUserInfoOnAccountOverviewPage(lastName),"Updated Personal Info is not present on Account Overview Page");
		s_assert.assertAll();
	}

	// Add, Edit, Delete Shipping Profile
	@Test(groups = {"Cons"},priority=4)
	public void testAddEditDeleteShippingProfileForConsultant(){
		String consAccountNumber = TestConstantsRFL.CONS_ACCOUNT_NUMBER;
		String emailId = TestConstantsRFL.DSV_CONS_EMAIL_ID;
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		newShippingProfileName = newShippingProfilePrefix + randomNum;
		updatedShippingProfileName = updatedShippingProfilePrefix + randomNum;
		dsvNSCore4HomePage.enterAccountInfoInAccountSearchField(consAccountNumber,emailId);
		dsvNSCore4HomePage.clickOnAccountNumberInSearchResults(consAccountNumber);
		//Navigate to Billing & Shipping Profile section
		dsvNSCore4HomePage.clickBillingAndShippingProfileLink();
		int totalNoOfAddressBeforeAdd = dsvNSCore4HomePage.getTotalNoOfShippingProfiles();

		// Add new Shipping Profile
		dsvNSCore4HomePage.clickShippingProfileAddLink();
		dsvNSCore4HomePage.enterShippingProfileDetails(newShippingProfileName, attentionCO, addressLine1, zipCode);
		dsvNSCore4HomePage.clickSaveAddressBtn();
		dsvNSCore4HomePage.refreshPage();
		s_assert.assertTrue(dsvNSCore4HomePage.isShippingProfilePresent(newShippingProfileName),
				"Newly created Shipping Profile " + newShippingProfileName + " is not Present");

		// Edit existing Billing Profile
		dsvNSCore4HomePage.editShippingProfile(newShippingProfileName);
		dsvNSCore4HomePage.enterShippingProfileDetails(updatedShippingProfileName, attentionCO, addressLine1, zipCode);
		dsvNSCore4HomePage.clickSaveAddressBtn();
		dsvNSCore4HomePage.refreshPage();
		s_assert.assertTrue(dsvNSCore4HomePage.isShippingProfilePresent(updatedShippingProfileName),
				"Updated Shipping Profile " + updatedShippingProfileName + " is not Present");

		//Delete created Profile
		dsvNSCore4HomePage.deleteAddressNewlyCreatedProfile(updatedShippingProfileName);
		int totalNoOfAddressAfterDelete = dsvNSCore4HomePage.getTotalNoOfShippingProfiles();
		s_assert.assertTrue(totalNoOfAddressAfterDelete ==  totalNoOfAddressBeforeAdd, "Expected count of shipping profile after delete is: "+totalNoOfAddressBeforeAdd+" Actual on UI is: "+totalNoOfAddressAfterDelete);

		s_assert.assertAll();    
	}

	// Add, Edit, Delete Billing Profile
	@Test(groups = {"Cons"},priority=5)
	public void testAddEditDeleteBillingProfileForConsultant(){
		String consAccountNumber = TestConstantsRFL.CONS_ACCOUNT_NUMBER;
		String emailId = TestConstantsRFL.DSV_CONS_EMAIL_ID;
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		newBillingProfileName = newBillingProfilePrefix + randomNum;
		updatedBillingProfileName = updatedBillingProfilePrefix + randomNum;
		dsvNSCore4HomePage.enterAccountInfoInAccountSearchField(consAccountNumber,emailId);
		dsvNSCore4HomePage.clickOnAccountNumberInSearchResults(consAccountNumber);
		//NAavigate to Billing & Shipping Profile section
		dsvNSCore4HomePage.clickBillingAndShippingProfileLink();

		// Add new Billing Profile
		dsvNSCore4HomePage.clickBillingProfileAddLink();
		dsvNSCore4HomePage.enterBillingProfileDetails(newBillingProfileName, lastNameForProfile, nameOnCard,cardNumber,cvv,addressLine1,zipCode);
		dsvNSCore4HomePage.clickSavePaymentMethodBtn();
		s_assert.assertTrue(dsvNSCore4HomePage.isBillingProfilePresent(newBillingProfileName),
				"Newly created Billing Profile " + newBillingProfileName + " is not Present");


		// Edit existing Billing Profile
		dsvNSCore4HomePage.editBillingProfile(newBillingProfileName);
		dsvNSCore4HomePage.enterBillingProfileDetails(updatedBillingProfileName, lastNameForProfile, nameOnCard, cardNumber,cvv,addressLine1,zipCode);
		dsvNSCore4HomePage.clickSavePaymentMethodBtn();
		s_assert.assertTrue(dsvNSCore4HomePage.isBillingProfilePresent(updatedBillingProfileName),
				"Updated Billing Profile " + newBillingProfileName + " is not Present");

		//Delete created Profile-
		dsvNSCore4HomePage.deleteNewlyCreatedBillingProfile(updatedBillingProfileName);
		s_assert.assertFalse(dsvNSCore4HomePage.isBillingProfilePresent(updatedBillingProfileName),"Billing profile is not deleted successfully");
		s_assert.assertAll();
	}

	// Verify Site Subscription
	@Test(groups = {"Cons"},priority=6)
	public void testSiteSubscriptionForCons(){
		String consAccountNumber = TestConstantsRFL.CONS_ACCOUNT_NUMBER;
		String emailId = TestConstantsRFL.DSV_CONS_EMAIL_ID;
		String urlToAssert = "Error/SiteNotFound";
		String comSiteURL = null;
		String bizSiteURL = null;
		String currentURL = null;
		dsvNSCore4HomePage.enterAccountInfoInAccountSearchField(consAccountNumber,emailId);
		dsvNSCore4HomePage.clickOnAccountNumberInSearchResults(consAccountNumber);
		dsvNSCore4HomePage.clickSiteSubscriptionsFromLeftNavigation();
		s_assert.assertTrue(dsvNSCore4HomePage.verifyPresenceOfSiteSubscriptionsHeader(),"Site Subscription Header is not Present");
		s_assert.assertTrue(dsvNSCore4HomePage.verifyPrefixPresentForSiteSubscription("Com"),"Prefix is not present for Com Site");
		s_assert.assertTrue(dsvNSCore4HomePage.verifyPrefixPresentForSiteSubscription("Biz"),"Prefix is not present for Biz Site");
		comSiteURL = dsvNSCore4HomePage.getURLForSpecificSite("Com");
		bizSiteURL = dsvNSCore4HomePage.getURLForSpecificSite("Biz");
		dsvNSCore4HomePage.navigatetoUrl(comSiteURL);
		currentURL = dsvNSCore4HomePage.getCurrentUrl();
		s_assert.assertFalse(currentURL.contains(urlToAssert),"Url should not contains Error/SiteNotFound");
		s_assert.assertTrue(dsvNSCore4HomePage.isLoginButtonPresent(),"Login Button is not present as expected on Cons com site");
		dsvNSCore4HomePage.navigatetoUrl(bizSiteURL);
		currentURL = dsvNSCore4HomePage.getCurrentUrl();
		s_assert.assertFalse(currentURL.contains(urlToAssert),"Url should not contain Error/SiteNotFound");
		s_assert.assertTrue(dsvNSCore4HomePage.isLoginButtonPresent(),"Login Button is not present as expected on Cons biz site");
		dsvNSCore4HomePage.navigatetoUrl(driver.getURL());
		s_assert.assertAll();
	}

	@Test(groups = {"PC"},priority=7)
	public void testSearchFunctionalityWithAccountIdByBrowsingAccountsForPC(){
		String firstName = TestConstantsRFL.DSV_PC_FIRST_NAME;
		String lastName = TestConstantsRFL.DSV_PC_LAST_NAME;
		String pcAccountNumber = TestConstantsRFL.PC_ACCOUNT_NUMBER;
		String emailId = TestConstantsRFL.DSV_PC_EMAIL_ID;
		dsvNSCore4HomePage.enterAccountInfoInAccountSearchField(pcAccountNumber,emailId);
		s_assert.assertTrue(dsvNSCore4HomePage.verifyPresenceOfAccountNumberInSearchResults(pcAccountNumber),"Account Number : "+pcAccountNumber+" is not present in Search Results");
		s_assert.assertTrue(dsvNSCore4HomePage.isFirstNamePresentAsExpectedInSearchResults(pcAccountNumber, firstName),"First Name is not present as Expected in Search Results");
		s_assert.assertTrue(dsvNSCore4HomePage.isLastNamePresentAsExpectedInSearchResults(pcAccountNumber, lastName),"Last Name is not present as Expected in Search Results");
		dsvNSCore4HomePage.clickOnAccountNumberInSearchResults(pcAccountNumber);
		s_assert.assertTrue(dsvNSCore4HomePage.isOrderPresentOnAccountOverview(),"No Order is present in order history for this User");
		dsvNSCore4OrderDetailsPage = dsvNSCore4HomePage.clickOrderNumberLinkFromAccountOverview();
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyCustomerInfoOnOrderDetailsPage(firstName),"First Name is not present as Expected on Order Details Page");
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyCustomerInfoOnOrderDetailsPage(lastName),"Last Name is not present as Expected on Order Details Page");
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyCustomerInfoOnOrderDetailsPage(pcAccountNumber),"Account Number is not present as Expected on Order Details Page");
		s_assert.assertAll();
	}

	// Verify Full Order History and Order Details
	@Test(groups = {"PC"},priority=8)
	public void testVerifyFullOrderHistoryAndOrderDetailsForPC(){
		String pcAccountNumber = TestConstantsRFL.PC_ACCOUNT_NUMBER;
		String firstName = TestConstantsRFL.DSV_PC_FIRST_NAME;
		String lastName = TestConstantsRFL.DSV_PC_LAST_NAME;
		String emailId = TestConstantsRFL.DSV_PC_EMAIL_ID;
		String urlToAssert = "Orders/Details";
		String currentUrl = null;
		dsvNSCore4HomePage.enterAccountInfoInAccountSearchField(pcAccountNumber,emailId);
		dsvNSCore4HomePage.clickOnAccountNumberInSearchResults(pcAccountNumber);
		dsvNSCore4HomePage.clickFullOrderHistory();
		s_assert.assertTrue(dsvNSCore4HomePage.isOrdersPresentInOrderHistory(),
				"No Order is present in order history for this User");
		dsvNSCore4OrderDetailsPage = dsvNSCore4HomePage.clickOrderNumberLinkFromOrderHistory();
		currentUrl = dsvNSCore4OrderDetailsPage.getCurrentUrl();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL does not contain Expected URL. Current URL : "+currentUrl+". Expected Url : "+urlToAssert);
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyCustomerInfoOnOrderDetailsPage(firstName),"First Name is not present as Expected on Order Details Page");
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyCustomerInfoOnOrderDetailsPage(lastName),"Last Name is not present as Expected on Order Details Page");
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyCustomerInfoOnOrderDetailsPage(pcAccountNumber),"Account Number is not present as Expected on Order Details Page");
		// Verifying Product Section
		/*s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyPresenceOfSKUInProductsSection(),"SKU is not present in Products Section");
		// Verifying Shipment Section
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyAddressInShipmentSection(),"Shipping Address is not present in Shipment section");
		// Verifying Payment Section
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyPresenceOfPaymentDetailsInPaymentSection(),"Payment Detail is not present in Payment Section");
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyPresenceOfTotalAmountInPaymentSection(),"Total Amount is not Present in Payment section");*/
		s_assert.assertAll();
	}

	// Verify Full Account Record
	@Test(groups = {"PC"},priority=9)
	public void testVerifyFullAccountRecordForPC(){
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstantsRFL.DSV_PC_FIRST_NAME;
		String lastName = TestConstantsRFL.DSV_PC_LAST_NAME;
		String pcAccountNumber = TestConstantsRFL.PC_ACCOUNT_NUMBER;
		String userType = TestConstantsRFL.PC_USER_TYPE;
		String emailId = TestConstantsRFL.DSV_PC_EMAIL_ID;
		String updatedLastName = lastName + randomNum;
		dsvNSCore4HomePage.enterAccountInfoInAccountSearchField(pcAccountNumber,emailId);
		dsvNSCore4HomePage.clickOnAccountNumberInSearchResults(pcAccountNumber);
		dsvNSCore4HomePage.clickFullAccountRecordFromLeftNavigation();
		s_assert.assertTrue(dsvNSCore4HomePage.verifyAccountTypeInFullRecord(userType),"Account Type is found as Expected");
		s_assert.assertTrue(dsvNSCore4HomePage.verifyFirstNameFromPersonalInfo(firstName),"First Name is not present as expected in Personal Info");
		s_assert.assertTrue(dsvNSCore4HomePage.verifyLastNameFromPersonalInfo(lastName),"Last Name is not present as expected in Personal Info");
		dsvNSCore4HomePage.editLastNameFromPersonalInfo(updatedLastName);
		dsvNSCore4HomePage.clickSavePersonalInfoBtn();
		dsvNSCore4HomePage.clickAcceptButtonOnAddressVerificationPopup();
		dsvNSCore4HomePage.refreshPage();
		s_assert.assertTrue(dsvNSCore4HomePage.verifyUserInfoOnAccountOverviewPage(updatedLastName),"Updated Personal Info is not present on Account Overview Page");
		dsvNSCore4HomePage.editLastNameFromPersonalInfo(lastName);
		dsvNSCore4HomePage.clickSavePersonalInfoBtn();
		dsvNSCore4HomePage.clickAcceptButtonOnAddressVerificationPopup();
		dsvNSCore4HomePage.refreshPage();
		s_assert.assertTrue(dsvNSCore4HomePage.verifyUserInfoOnAccountOverviewPage(lastName),"Updated Personal Info is not present on Account Overview Page");
		s_assert.assertAll();
	}

	// Add, Edit, Delete Shipping Profile
	@Test(groups = {"PC"},priority=10)
	public void testAddEditDeleteShippingProfileForPC(){
		String pcAccountNumber = TestConstantsRFL.PC_ACCOUNT_NUMBER;
		String emailId = TestConstantsRFL.DSV_PC_EMAIL_ID;
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		newShippingProfileName = newShippingProfilePrefix + randomNum;
		updatedShippingProfileName = updatedShippingProfilePrefix + randomNum;
		dsvNSCore4HomePage.enterAccountInfoInAccountSearchField(pcAccountNumber,emailId);
		dsvNSCore4HomePage.clickOnAccountNumberInSearchResults(pcAccountNumber);
		//Navigate to Billing & Shipping Profile section
		dsvNSCore4HomePage.clickBillingAndShippingProfileLink();
		int totalNoOfAddressBeforeAdd = dsvNSCore4HomePage.getTotalNoOfShippingProfiles();

		// Add new Shipping Profile
		dsvNSCore4HomePage.clickShippingProfileAddLink();
		dsvNSCore4HomePage.enterShippingProfileDetails(newShippingProfileName, attentionCO, addressLine1, zipCode);
		dsvNSCore4HomePage.clickSaveAddressBtn();
		dsvNSCore4HomePage.refreshPage();
		s_assert.assertTrue(dsvNSCore4HomePage.isShippingProfilePresent(newShippingProfileName),
				"Newly created Shipping Profile " + newShippingProfileName + " is not Present");

		// Edit existing Billing Profile
		dsvNSCore4HomePage.editShippingProfile(newShippingProfileName);
		dsvNSCore4HomePage.enterShippingProfileDetails(updatedShippingProfileName, attentionCO, addressLine1, zipCode);
		dsvNSCore4HomePage.clickSaveAddressBtn();
		dsvNSCore4HomePage.refreshPage();
		s_assert.assertTrue(dsvNSCore4HomePage.isShippingProfilePresent(updatedShippingProfileName),
				"Updated Shipping Profile " + updatedShippingProfileName + " is not Present");

		//Delete created Profile
		dsvNSCore4HomePage.deleteAddressNewlyCreatedProfile(updatedShippingProfileName);
		int totalNoOfAddressAfterDelete = dsvNSCore4HomePage.getTotalNoOfShippingProfiles();
		s_assert.assertTrue(totalNoOfAddressAfterDelete ==  totalNoOfAddressBeforeAdd, "Expected count of shipping profile after delete is: "+totalNoOfAddressBeforeAdd+" Actual on UI is: "+totalNoOfAddressAfterDelete);

		s_assert.assertAll();    
	}

	// Add, Edit, Delete Billing Profile
	@Test(groups = {"PC"},priority=11)
	public void testAddEditDeleteBillingProfileForPC(){
		String pcAccountNumber = TestConstantsRFL.PC_ACCOUNT_NUMBER;
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		newBillingProfileName = newBillingProfilePrefix + randomNum;
		updatedBillingProfileName = updatedBillingProfilePrefix + randomNum;
		String emailId = TestConstantsRFL.DSV_PC_EMAIL_ID;
		dsvNSCore4HomePage.enterAccountInfoInAccountSearchField(pcAccountNumber,emailId);
		dsvNSCore4HomePage.clickOnAccountNumberInSearchResults(pcAccountNumber);
		//NAavigate to Billing & Shipping Profile section
		dsvNSCore4HomePage.clickBillingAndShippingProfileLink();

		// Add new Billing Profile
		dsvNSCore4HomePage.clickBillingProfileAddLink();
		dsvNSCore4HomePage.enterBillingProfileDetails(newBillingProfileName, lastNameForProfile, nameOnCard,cardNumber,cvv,addressLine1,zipCode);
		dsvNSCore4HomePage.clickSavePaymentMethodBtn();
		s_assert.assertTrue(dsvNSCore4HomePage.isBillingProfilePresent(newBillingProfileName),
				"Newly created Billing Profile " + newBillingProfileName + " is not Present");

		// Edit existing Billing Profile
		dsvNSCore4HomePage.editBillingProfile(newBillingProfileName);
		dsvNSCore4HomePage.enterBillingProfileDetails(updatedBillingProfileName, lastNameForProfile, nameOnCard, cardNumber,cvv,addressLine1,zipCode);
		dsvNSCore4HomePage.clickSavePaymentMethodBtn();
		s_assert.assertTrue(dsvNSCore4HomePage.isBillingProfilePresent(updatedBillingProfileName),
				"Updated Billing Profile " + newBillingProfileName + " is not Present");

		//Delete created Profile-
		dsvNSCore4HomePage.deleteNewlyCreatedBillingProfile(updatedBillingProfileName);
		s_assert.assertFalse(dsvNSCore4HomePage.isBillingProfilePresent(updatedBillingProfileName),"Billing profile is not deleted successfully");
		s_assert.assertAll();
	}

	@Test(groups = {"RC"},priority=12)
	public void testSearchFunctionalityWithAccountIdByBrowsingAccountsForRC(){
		String firstName = TestConstantsRFL.DSV_RC_FIRST_NAME;
		String lastName = TestConstantsRFL.DSV_RC_LAST_NAME;
		String rcAccountNumber = TestConstantsRFL.RC_ACCOUNT_NUMBER;
		String emailId = TestConstantsRFL.DSV_RC_EMAIL_ID;
		dsvNSCore4HomePage.enterAccountInfoInAccountSearchField(rcAccountNumber,emailId);
		s_assert.assertTrue(dsvNSCore4HomePage.verifyPresenceOfAccountNumberInSearchResults(rcAccountNumber),"Account Number : "+rcAccountNumber+" is not present in Search Results");
		s_assert.assertTrue(dsvNSCore4HomePage.isFirstNamePresentAsExpectedInSearchResults(rcAccountNumber, firstName),"First Name is not present as Expected in Search Results");
		s_assert.assertTrue(dsvNSCore4HomePage.isLastNamePresentAsExpectedInSearchResults(rcAccountNumber, lastName),"Last Name is not present as Expected in Search Results");
		dsvNSCore4HomePage.clickOnAccountNumberInSearchResults(rcAccountNumber);
		s_assert.assertTrue(dsvNSCore4HomePage.isOrderPresentOnAccountOverview(),"No Order is present in order history for this User");
		dsvNSCore4OrderDetailsPage = dsvNSCore4HomePage.clickOrderNumberLinkFromAccountOverview();
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyCustomerInfoOnOrderDetailsPage(firstName),"First Name is not present as Expected on Order Details Page");
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyCustomerInfoOnOrderDetailsPage(lastName),"Last Name is not present as Expected on Order Details Page");
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyCustomerInfoOnOrderDetailsPage(rcAccountNumber),"Account Number is not present as Expected on Order Details Page");
		s_assert.assertAll();
	}

	// Verify Full Order History and Order Details
	@Test(groups = {"RC"},priority=13)
	public void testVerifyFullOrderHistoryAndOrderDetailsForRC(){
		String rcAccountNumber = TestConstantsRFL.RC_ACCOUNT_NUMBER;
		String firstName = TestConstantsRFL.DSV_RC_FIRST_NAME;
		String lastName = TestConstantsRFL.DSV_RC_LAST_NAME;
		String emailId = TestConstantsRFL.DSV_RC_EMAIL_ID;
		String urlToAssert = "Orders/Details";
		String currentUrl = null;
		dsvNSCore4HomePage.enterAccountInfoInAccountSearchField(rcAccountNumber,emailId);
		dsvNSCore4HomePage.clickOnAccountNumberInSearchResults(rcAccountNumber);
		dsvNSCore4HomePage.clickFullOrderHistory();
		s_assert.assertTrue(dsvNSCore4HomePage.isOrdersPresentInOrderHistory(),
				"No Order is present in order history for this User");
		dsvNSCore4OrderDetailsPage = dsvNSCore4HomePage.clickOrderNumberLinkFromOrderHistory();
		currentUrl = dsvNSCore4OrderDetailsPage.getCurrentUrl();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL does not contain Expected URL. Current URL : "+currentUrl+". Expected Url : "+urlToAssert);
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyCustomerInfoOnOrderDetailsPage(firstName),"First Name is not present as Expected on Order Details Page");
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyCustomerInfoOnOrderDetailsPage(lastName),"Last Name is not present as Expected on Order Details Page");
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyCustomerInfoOnOrderDetailsPage(rcAccountNumber),"Account Number is not present as Expected on Order Details Page");
		// Verifying Product Section
		/*s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyPresenceOfSKUInProductsSection(),"SKU is not present in Products Section");
		// Verifying Shipment Section
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyAddressInShipmentSection(),"Shipping Address is not present in Shipment section");
		// Verifying Payment Section
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyPresenceOfPaymentDetailsInPaymentSection(),"Payment Detail is not present in Payment Section");
		s_assert.assertTrue(dsvNSCore4OrderDetailsPage.verifyPresenceOfTotalAmountInPaymentSection(),"Total Amount is not Present in Payment section");*/
		s_assert.assertAll();
	}

	// Verify Full Account Record
	@Test(groups = {"RC"},priority=14)
	public void testVerifyFullAccountRecordForRC(){
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstantsRFL.DSV_RC_FIRST_NAME;
		String lastName = TestConstantsRFL.DSV_RC_LAST_NAME;
		String rcAccountNumber = TestConstantsRFL.RC_ACCOUNT_NUMBER;
		String userType = TestConstantsRFL.RC_USER_TYPE;
		String emailId = TestConstantsRFL.DSV_RC_EMAIL_ID;
		String updatedLastName = lastName + randomNum;
		dsvNSCore4HomePage.enterAccountInfoInAccountSearchField(rcAccountNumber,emailId);
		dsvNSCore4HomePage.clickOnAccountNumberInSearchResults(rcAccountNumber);
		dsvNSCore4HomePage.clickFullAccountRecordFromLeftNavigation();
		s_assert.assertTrue(dsvNSCore4HomePage.verifyAccountTypeInFullRecord(userType),"Account Type is found as Expected");
		s_assert.assertTrue(dsvNSCore4HomePage.verifyFirstNameFromPersonalInfo(firstName),"First Name is not present as expected in Personal Info");
		s_assert.assertTrue(dsvNSCore4HomePage.verifyLastNameFromPersonalInfo(lastName),"Last Name is not present as expected in Personal Info");
		dsvNSCore4HomePage.editLastNameFromPersonalInfo(updatedLastName);
		dsvNSCore4HomePage.clickSavePersonalInfoBtn();
		dsvNSCore4HomePage.clickAcceptButtonOnAddressVerificationPopup();
		dsvNSCore4HomePage.refreshPage();
		s_assert.assertTrue(dsvNSCore4HomePage.verifyUserInfoOnAccountOverviewPage(updatedLastName),"Updated Personal Info is not present on Account Overview Page");
		dsvNSCore4HomePage.editLastNameFromPersonalInfo(lastName);
		dsvNSCore4HomePage.clickSavePersonalInfoBtn();
		dsvNSCore4HomePage.clickAcceptButtonOnAddressVerificationPopup();
		dsvNSCore4HomePage.refreshPage();
		s_assert.assertTrue(dsvNSCore4HomePage.verifyUserInfoOnAccountOverviewPage(lastName),"Updated Personal Info is not present on Account Overview Page");
		s_assert.assertAll();
	}

	// Add, Edit, Delete Shipping Profile
	@Test(groups = {"RC"},priority=15)
	public void testAddEditDeleteShippingProfileForRC(){
		String rcAccountNumber = TestConstantsRFL.RC_ACCOUNT_NUMBER;
		String emailId = TestConstantsRFL.DSV_RC_EMAIL_ID;
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		newShippingProfileName = newShippingProfilePrefix + randomNum;
		updatedShippingProfileName = updatedShippingProfilePrefix + randomNum;
		dsvNSCore4HomePage.enterAccountInfoInAccountSearchField(rcAccountNumber,emailId);
		dsvNSCore4HomePage.clickOnAccountNumberInSearchResults(rcAccountNumber);
		//Navigate to Billing & Shipping Profile section
		dsvNSCore4HomePage.clickBillingAndShippingProfileLink();
		int totalNoOfAddressBeforeAdd = dsvNSCore4HomePage.getTotalNoOfShippingProfiles();

		// Add new Shipping Profile
		dsvNSCore4HomePage.clickShippingProfileAddLink();
		dsvNSCore4HomePage.enterShippingProfileDetails(newShippingProfileName, attentionCO, addressLine1, zipCode);
		dsvNSCore4HomePage.clickSaveAddressBtn();
		dsvNSCore4HomePage.refreshPage();
		s_assert.assertTrue(dsvNSCore4HomePage.isShippingProfilePresent(newShippingProfileName),
				"Newly created Shipping Profile " + newShippingProfileName + " is not Present");

		// Edit existing Billing Profile
		dsvNSCore4HomePage.editShippingProfile(newShippingProfileName);
		dsvNSCore4HomePage.enterShippingProfileDetails(updatedShippingProfileName, attentionCO, addressLine1, zipCode);
		dsvNSCore4HomePage.clickSaveAddressBtn();
		dsvNSCore4HomePage.refreshPage();
		s_assert.assertTrue(dsvNSCore4HomePage.isShippingProfilePresent(updatedShippingProfileName),
				"Updated Shipping Profile " + updatedShippingProfileName + " is not Present");

		//Delete created Profile
		dsvNSCore4HomePage.deleteAddressNewlyCreatedProfile(updatedShippingProfileName);
		int totalNoOfAddressAfterDelete = dsvNSCore4HomePage.getTotalNoOfShippingProfiles();
		s_assert.assertTrue(totalNoOfAddressAfterDelete ==  totalNoOfAddressBeforeAdd, "Expected count of shipping profile after delete is: "+totalNoOfAddressBeforeAdd+" Actual on UI is: "+totalNoOfAddressAfterDelete);

		s_assert.assertAll();    
	}

	// Add, Edit, Delete Billing Profile
	@Test(groups = {"RC"},priority=16)
	public void testAddEditDeleteBillingProfileForRC(){
		String rcAccountNumber = TestConstantsRFL.RC_ACCOUNT_NUMBER;
		String emailId = TestConstantsRFL.DSV_RC_EMAIL_ID;
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		newBillingProfileName = newBillingProfilePrefix + randomNum;
		updatedBillingProfileName = updatedBillingProfilePrefix + randomNum;
		dsvNSCore4HomePage.enterAccountInfoInAccountSearchField(rcAccountNumber,emailId);
		dsvNSCore4HomePage.clickOnAccountNumberInSearchResults(rcAccountNumber);
		//NAavigate to Billing & Shipping Profile section
		dsvNSCore4HomePage.clickBillingAndShippingProfileLink();

		// Add new Billing Profile
		dsvNSCore4HomePage.clickBillingProfileAddLink();
		dsvNSCore4HomePage.enterBillingProfileDetails(newBillingProfileName, lastNameForProfile, nameOnCard,cardNumber,cvv,addressLine1,zipCode);
		dsvNSCore4HomePage.clickSavePaymentMethodBtn();
		s_assert.assertTrue(dsvNSCore4HomePage.isBillingProfilePresent(newBillingProfileName),
				"Newly created Billing Profile " + newBillingProfileName + " is not Present");

		// Edit existing Billing Profile
		dsvNSCore4HomePage.editBillingProfile(newBillingProfileName);
		dsvNSCore4HomePage.enterBillingProfileDetails(updatedBillingProfileName, lastNameForProfile, nameOnCard, cardNumber,cvv,addressLine1,zipCode);
		dsvNSCore4HomePage.clickSavePaymentMethodBtn();
		s_assert.assertTrue(dsvNSCore4HomePage.isBillingProfilePresent(updatedBillingProfileName),
				"Updated Billing Profile " + newBillingProfileName + " is not Present");

		//Delete created Profile-
		dsvNSCore4HomePage.deleteNewlyCreatedBillingProfile(updatedBillingProfileName);
		s_assert.assertFalse(dsvNSCore4HomePage.isBillingProfilePresent(updatedBillingProfileName),"Billing profile is not deleted successfully");
		s_assert.assertAll();
	}

}