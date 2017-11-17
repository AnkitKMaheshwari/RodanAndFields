package com.rf.test.website.cscockpit.dsv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.rf.core.utils.CommonUtils;
import com.rf.core.website.constants.TestConstants;
import com.rf.pages.website.cscockpit.CSCockpitAutoshipCartTabPage;
import com.rf.pages.website.cscockpit.CSCockpitAutoshipTemplateTabPage;
import com.rf.pages.website.cscockpit.CSCockpitAutoshipTemplateUpdateTabPage;
import com.rf.pages.website.cscockpit.CSCockpitCartTabPage;
import com.rf.pages.website.cscockpit.CSCockpitCheckoutTabPage;
import com.rf.pages.website.cscockpit.CSCockpitCustomerSearchTabPage;
import com.rf.pages.website.cscockpit.CSCockpitCustomerTabPage;
import com.rf.pages.website.cscockpit.CSCockpitLoginPage;
import com.rf.pages.website.cscockpit.CSCockpitOrderSearchTabPage;
import com.rf.pages.website.cscockpit.CSCockpitOrderTabPage;
import com.rf.test.website.RFDSVCscockpitWebsiteBaseTest;


public class CSCockpitDSVTest extends RFDSVCscockpitWebsiteBaseTest{
	private static final Logger logger = LogManager
			.getLogger(CSCockpitDSVTest.class.getName());

	//-------------------------------------------------Pages---------------------------------------------------------
	private CSCockpitLoginPage cscockpitLoginPage;	
	private CSCockpitCheckoutTabPage cscockpitCheckoutTabPage;
	private CSCockpitCustomerSearchTabPage cscockpitCustomerSearchTabPage;
	private CSCockpitCustomerTabPage cscockpitCustomerTabPage;
	private CSCockpitOrderSearchTabPage cscockpitOrderSearchTabPage;
	private CSCockpitOrderTabPage cscockpitOrderTabPage;
	private CSCockpitCartTabPage cscockpitCartTabPage;
	private CSCockpitAutoshipTemplateTabPage cscockpitAutoshipTemplateTabPage;
	private CSCockpitAutoshipCartTabPage cscockpitAutoshipCartTabPage;
	private CSCockpitAutoshipTemplateUpdateTabPage cscockpitAutoshipTemplateUpdateTabPage;

	//-----------------------------------------------------------------------------------------------------------------

	public CSCockpitDSVTest() {
		cscockpitLoginPage = new CSCockpitLoginPage(driver);
		cscockpitCheckoutTabPage = new CSCockpitCheckoutTabPage(driver);
		cscockpitCustomerSearchTabPage = new CSCockpitCustomerSearchTabPage(driver);
		cscockpitCustomerTabPage = new CSCockpitCustomerTabPage(driver);
		cscockpitOrderSearchTabPage = new CSCockpitOrderSearchTabPage(driver);
		cscockpitOrderTabPage = new CSCockpitOrderTabPage(driver);
		cscockpitCartTabPage = new CSCockpitCartTabPage(driver);
		cscockpitAutoshipCartTabPage= new CSCockpitAutoshipCartTabPage(driver);
		cscockpitAutoshipTemplateTabPage = new CSCockpitAutoshipTemplateTabPage(driver);
		cscockpitAutoshipTemplateUpdateTabPage = new CSCockpitAutoshipTemplateUpdateTabPage(driver);
	}

	//Hybris Project-5493:Verify Consultant Search Criteria
	@Test(priority=1)
	public void testVerifyConsultantSearchCriteria_5493() throws InterruptedException{
		cscockpitCustomerSearchTabPage.clickCustomerSearchTab();
		cscockpitCustomerSearchTabPage.selectCustomerTypeFromDropDownInCustomerSearchTab("CONSULTANT");
		cscockpitCustomerSearchTabPage.selectAccountStatusFromDropDownInCustomerSearchTab("Active");
		cscockpitCustomerSearchTabPage.enterEmailIdInSearchFieldInCustomerSearchTab(consultantEmailId);
		cscockpitCustomerSearchTabPage.clickSearchBtn();
		cscockpitCustomerSearchTabPage.verifyCountForCustomerFromSearchResult();
		s_assert.assertTrue(cscockpitCustomerSearchTabPage.getTotalResultsInCustomerSearchOnCustomerSearchTab()==1, "searched consultant is not showing 1 result");
		cscockpitCustomerSearchTabPage.clickCIDNumberInCustomerSearchTab("1");
		s_assert.assertTrue(cscockpitCustomerTabPage.getEmailAddressFromTopSectionInCustomerTabPage().equalsIgnoreCase(consultantEmailId), "Customer details page doesn't contain the email Id of consultant as "+consultantEmailId);
		s_assert.assertTrue(cscockpitCustomerTabPage.getUserNameAndCIDStringFromTopSectionInCustomerTabPage().contains(consultantCID), "Customer details page doesn't contain the CID of consultant as "+consultantCID);
		s_assert.assertAll();
	}

	//Hybris Project-5494:Verify Preferred Customer Search Criteria
	@Test(priority=2)
	public void testVerifyPreferredCustomerSearchCriteria_5494() throws InterruptedException{
		cscockpitCustomerSearchTabPage.clickCustomerSearchTab();
		cscockpitCustomerSearchTabPage.selectCustomerTypeFromDropDownInCustomerSearchTab("PC");
		cscockpitCustomerSearchTabPage.selectAccountStatusFromDropDownInCustomerSearchTab("Active");
		cscockpitCustomerSearchTabPage.enterEmailIdInSearchFieldInCustomerSearchTab(pcEmailId);
		cscockpitCustomerSearchTabPage.clickSearchBtn();
		cscockpitCustomerSearchTabPage.verifyCountForCustomerFromSearchResult();
		s_assert.assertTrue(cscockpitCustomerSearchTabPage.getTotalResultsInCustomerSearchOnCustomerSearchTab()==1, "searched pc is not showing 1 result");
		cscockpitCustomerSearchTabPage.clickCIDNumberInCustomerSearchTab("1");
		s_assert.assertTrue(cscockpitCustomerTabPage.getEmailAddressFromTopSectionInCustomerTabPage().equalsIgnoreCase(pcEmailId), "Customer details page doesn't contain the email Id of PC as "+pcEmailId);
		s_assert.assertTrue(cscockpitCustomerTabPage.getUserNameAndCIDStringFromTopSectionInCustomerTabPage().contains(pcCID), "Customer details page doesn't contain the CID of PC as "+pcCID);
		s_assert.assertAll();
	}

	// Hybris Project-5495:Verify Retail Customer Search Criteria
	@Test(priority=3)
	public void testVerifyRetailCustomerSearchCriteria_5495() throws InterruptedException{
		cscockpitCustomerSearchTabPage.clickCustomerSearchTab();
		cscockpitCustomerSearchTabPage.selectCustomerTypeFromDropDownInCustomerSearchTab("RETAIL");
		cscockpitCustomerSearchTabPage.selectAccountStatusFromDropDownInCustomerSearchTab("Active");
		cscockpitCustomerSearchTabPage.enterEmailIdInSearchFieldInCustomerSearchTab(rcEmailId);
		cscockpitCustomerSearchTabPage.clickSearchBtn();
		cscockpitCustomerSearchTabPage.verifyCountForCustomerFromSearchResult();
		s_assert.assertTrue(cscockpitCustomerSearchTabPage.getTotalResultsInCustomerSearchOnCustomerSearchTab()==1, "searched RC is not showing 1 result");
		cscockpitCustomerSearchTabPage.clickCIDNumberInCustomerSearchTab("1");
		s_assert.assertTrue(cscockpitCustomerTabPage.getEmailAddressFromTopSectionInCustomerTabPage().equalsIgnoreCase(rcEmailId), "Customer details page doesn't contain the email Id of RC as "+rcEmailId);
		s_assert.assertTrue(cscockpitCustomerTabPage.getUserNameAndCIDStringFromTopSectionInCustomerTabPage().contains(rcCID), "Customer details page doesn't contain the CID of RC as "+rcCID);
		s_assert.assertAll();
	}

	// Hybris Project-5496:Verify Add and Edit Billing for Consultant
	@Test(priority=4)
	public void testVerifyAddAndEditBillingForConsultant_5496() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(1000, 9999);
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;		
		String editBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum+" edit";
		cscockpitCustomerSearchTabPage.clickCustomerSearchTab();
		cscockpitCustomerSearchTabPage.selectCustomerTypeFromDropDownInCustomerSearchTab("CONSULTANT");
		cscockpitCustomerSearchTabPage.selectAccountStatusFromDropDownInCustomerSearchTab("Active");
		cscockpitCustomerSearchTabPage.enterEmailIdInSearchFieldInCustomerSearchTab(consultantEmailId);
		cscockpitCustomerSearchTabPage.clickSearchBtn();
		cscockpitCustomerSearchTabPage.verifyCountForCustomerFromSearchResult();
		s_assert.assertTrue(cscockpitCustomerSearchTabPage.getTotalResultsInCustomerSearchOnCustomerSearchTab()==1, "searched consultant is not showing 1 result");
		cscockpitCustomerSearchTabPage.clickCIDNumberInCustomerSearchTab("1");
		s_assert.assertTrue(cscockpitCustomerTabPage.getEmailAddressFromTopSectionInCustomerTabPage().equalsIgnoreCase(consultantEmailId), "Customer details page doesn't contain the email Id of consultant as "+consultantEmailId);
		s_assert.assertTrue(cscockpitCustomerTabPage.getUserNameAndCIDStringFromTopSectionInCustomerTabPage().contains(consultantCID), "Customer details page doesn't contain the CID of consultant as "+consultantCID);
		cscockpitCustomerTabPage.clickAddCardButtonInCustomerTab();
		cscockpitCustomerTabPage.enterBillingInfo(newBillingProfileName,cardNumber,"AMEX",CVV,expMonthNumber,expYear);
		cscockpitCustomerTabPage.clickSaveAddNewPaymentProfilePopUP();
		s_assert.assertTrue(cscockpitCustomerTabPage.isBillingListContainsBillingName(newBillingProfileName), "New Billing profile with the name "+newBillingProfileName+" is not added");
		cscockpitCustomerTabPage.clickEditBtnForTheBillingProfile(newBillingProfileName);
		cscockpitCustomerTabPage.enterBillingInfoWithoutSelectingAddress(editBillingProfileName,cardNumber,"AMEX",CVV,expMonthNumber,expYear);
		cscockpitCustomerTabPage.clickSaveAddNewPaymentProfilePopUP();
		s_assert.assertTrue(cscockpitCustomerTabPage.isBillingListContainsBillingName(editBillingProfileName), "Billing profile with the name "+newBillingProfileName+" is not edited");	
		s_assert.assertAll();
	}

	//Hybris Project-5497:Verify Add and Edit Billing for PC
	@Test(priority=5)
	public void testVerifyAddAndEditBillingForPC_5497() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(1000, 9999);
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+" "+randomNum;
		String editBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+" "+randomNum+" edit";
		cscockpitCustomerSearchTabPage.clickCustomerSearchTab();
		cscockpitCustomerSearchTabPage.selectCustomerTypeFromDropDownInCustomerSearchTab("PC");
		cscockpitCustomerSearchTabPage.selectAccountStatusFromDropDownInCustomerSearchTab("Active");
		cscockpitCustomerSearchTabPage.enterEmailIdInSearchFieldInCustomerSearchTab(pcEmailId);
		cscockpitCustomerSearchTabPage.clickSearchBtn();
		cscockpitCustomerSearchTabPage.verifyCountForCustomerFromSearchResult();
		s_assert.assertTrue(cscockpitCustomerSearchTabPage.getTotalResultsInCustomerSearchOnCustomerSearchTab()==1, "searched PC is not showing 1 result");
		cscockpitCustomerSearchTabPage.clickCIDNumberInCustomerSearchTab("1");
		s_assert.assertTrue(cscockpitCustomerTabPage.getEmailAddressFromTopSectionInCustomerTabPage().equalsIgnoreCase(pcEmailId), "Customer details page doesn't contain the email Id of PC as "+pcEmailId);
		s_assert.assertTrue(cscockpitCustomerTabPage.getUserNameAndCIDStringFromTopSectionInCustomerTabPage().contains(pcCID), "Customer details page doesn't contain the CID of PC as "+pcCID);
		cscockpitCustomerTabPage.clickAddCardButtonInCustomerTab();
		cscockpitCustomerTabPage.enterBillingInfo(newBillingProfileName,cardNumber,"AMEX",CVV,expMonthNumber,expYear);
		cscockpitCustomerTabPage.clickSaveAddNewPaymentProfilePopUP();
		s_assert.assertTrue(cscockpitCustomerTabPage.isBillingListContainsBillingName(newBillingProfileName), "New Billing profile with the name "+newBillingProfileName+" is not added");
		cscockpitCustomerTabPage.clickEditBtnForTheBillingProfile(newBillingProfileName);
		cscockpitCustomerTabPage.enterBillingInfoWithoutSelectingAddress(editBillingProfileName,cardNumber,"AMEX",CVV,expMonthNumber,expYear);
		cscockpitCustomerTabPage.clickSaveAddNewPaymentProfilePopUP();
		s_assert.assertTrue(cscockpitCustomerTabPage.isBillingListContainsBillingName(editBillingProfileName), "Billing profile with the name "+newBillingProfileName+" is not edited");	
		s_assert.assertAll();
	}

	// Hybris Project-5498:Verify Add and Edit Billing for RC
	@Test(priority=6)
	public void testVerifyAddAndEditBillingForRC_5498() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(1000, 9999);
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String editBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum+" edit";
		cscockpitCustomerSearchTabPage.clickCustomerSearchTab();
		cscockpitCustomerSearchTabPage.selectCustomerTypeFromDropDownInCustomerSearchTab("RETAIL");
		cscockpitCustomerSearchTabPage.selectAccountStatusFromDropDownInCustomerSearchTab("Active");
		cscockpitCustomerSearchTabPage.enterEmailIdInSearchFieldInCustomerSearchTab(rcEmailId);
		cscockpitCustomerSearchTabPage.clickSearchBtn();
		cscockpitCustomerSearchTabPage.verifyCountForCustomerFromSearchResult();
		s_assert.assertTrue(cscockpitCustomerSearchTabPage.getTotalResultsInCustomerSearchOnCustomerSearchTab()==1, "searched RC is not showing 1 result");
		cscockpitCustomerSearchTabPage.clickCIDNumberInCustomerSearchTab("1");
		s_assert.assertTrue(cscockpitCustomerTabPage.getEmailAddressFromTopSectionInCustomerTabPage().equalsIgnoreCase(rcEmailId), "Customer details page doesn't contain the email Id of consultant as "+rcEmailId);
		s_assert.assertTrue(cscockpitCustomerTabPage.getUserNameAndCIDStringFromTopSectionInCustomerTabPage().contains(rcCID), "Customer details page doesn't contain the CID of consultant as "+rcCID);
		cscockpitCustomerTabPage.clickAddCardButtonInCustomerTab();
		cscockpitCustomerTabPage.enterBillingInfo(newBillingProfileName,cardNumber,"AMEX",CVV,expMonthNumber,expYear);
		cscockpitCustomerTabPage.clickSaveAddNewPaymentProfilePopUP();
		s_assert.assertTrue(cscockpitCustomerTabPage.isBillingListContainsBillingName(newBillingProfileName), "New Billing profile with the name "+newBillingProfileName+" is not added");
		cscockpitCustomerTabPage.clickEditBtnForTheBillingProfile(newBillingProfileName);
		cscockpitCustomerTabPage.enterBillingInfoWithoutSelectingAddress(editBillingProfileName,cardNumber,"AMEX",CVV,expMonthNumber,expYear);
		cscockpitCustomerTabPage.clickSaveAddNewPaymentProfilePopUP();
		s_assert.assertTrue(cscockpitCustomerTabPage.isBillingListContainsBillingName(editBillingProfileName), "Billing profile with the name "+newBillingProfileName+" is not edited");	
		s_assert.assertAll();
	}

	// Hybris Project-5499:Verify Add and Edit Shipping for Consultant
	@Test(priority=7)
	public void testVerifyAddAndEditShippingForConsultant_5499() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstants.DSV_FIRST_NAME;
		String lastName = ""+randomNum;
		String lastNameAfterEdit = randomNum+"edit";
		String attendentName = firstName+" "+lastName;
		String attendentNameAfterEdit = firstName+" "+lastNameAfterEdit;

		cscockpitCustomerSearchTabPage.clickCustomerSearchTab();
		cscockpitCustomerSearchTabPage.selectCustomerTypeFromDropDownInCustomerSearchTab("CONSULTANT");
		cscockpitCustomerSearchTabPage.selectAccountStatusFromDropDownInCustomerSearchTab("Active");
		cscockpitCustomerSearchTabPage.enterEmailIdInSearchFieldInCustomerSearchTab(consultantEmailId);
		cscockpitCustomerSearchTabPage.clickSearchBtn();
		cscockpitCustomerSearchTabPage.verifyCountForCustomerFromSearchResult();
		s_assert.assertTrue(cscockpitCustomerSearchTabPage.getTotalResultsInCustomerSearchOnCustomerSearchTab()==1, "searched consultant is not showing 1 result");
		cscockpitCustomerSearchTabPage.clickCIDNumberInCustomerSearchTab("1");
		s_assert.assertTrue(cscockpitCustomerTabPage.getEmailAddressFromTopSectionInCustomerTabPage().equalsIgnoreCase(consultantEmailId), "Customer details page doesn't contain the email Id of consultant as "+consultantEmailId);
		s_assert.assertTrue(cscockpitCustomerTabPage.getUserNameAndCIDStringFromTopSectionInCustomerTabPage().contains(consultantCID), "Customer details page doesn't contain the CID of consultant as "+consultantCID);
		cscockpitCustomerTabPage.clickAddButtonOfCustomerAddressInCustomerTab();
		cscockpitCustomerTabPage.enterShippingInfoInAddNewPaymentProfilePopupWithoutSaveBtn(attendentName, addressLine1, city, postalCode, countryFullName, state, phoneNumber);
		cscockpitCustomerTabPage.clickCreateNewAddressBtn();
		cscockpitCustomerTabPage.clickUseEnteredAddressBtn();
		s_assert.assertTrue(cscockpitCustomerTabPage.isShippingProfilePresent(lastName), "shipping profile name expected in customer tab page "+attendentName+" hut was not found");
		cscockpitCustomerTabPage.clickEditButtonOfShippingAddressInCustomerTab(lastName);
		cscockpitCustomerTabPage.enterShippingInfoInAddNewPaymentProfilePopupWithoutSaveBtn(attendentNameAfterEdit, addressLine1, city, postalCode, countryFullName, state, phoneNumber);
		cscockpitCustomerTabPage.clickUpdateAddressBtn();
		cscockpitCustomerTabPage.clickUseEnteredAddressBtn();
		s_assert.assertTrue(cscockpitCustomerTabPage.isShippingProfilePresent(lastNameAfterEdit), "shipping profile name expected in customer tab page "+attendentNameAfterEdit+" but was not found");
		s_assert.assertAll();
	}

	//Hybris Project-5500:Verify Add and Edit Shipping for PC
	@Test(priority=8)
	public void testVerifyAddAndEditShippingForPC_5500() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstants.DSV_FIRST_NAME;
		String lastName = ""+randomNum;
		String lastNameAfterEdit = randomNum+"edit";
		String attendentName = firstName+" "+lastName;
		String attendentNameAfterEdit = firstName+" "+lastNameAfterEdit;

		cscockpitCustomerSearchTabPage.clickCustomerSearchTab();
		cscockpitCustomerSearchTabPage.selectCustomerTypeFromDropDownInCustomerSearchTab("PC");
		cscockpitCustomerSearchTabPage.selectAccountStatusFromDropDownInCustomerSearchTab("Active");
		cscockpitCustomerSearchTabPage.enterEmailIdInSearchFieldInCustomerSearchTab(pcEmailId);
		cscockpitCustomerSearchTabPage.clickSearchBtn();
		cscockpitCustomerSearchTabPage.verifyCountForCustomerFromSearchResult();
		s_assert.assertTrue(cscockpitCustomerSearchTabPage.getTotalResultsInCustomerSearchOnCustomerSearchTab()==1, "searched PC is not showing 1 result");
		cscockpitCustomerSearchTabPage.clickCIDNumberInCustomerSearchTab("1");
		s_assert.assertTrue(cscockpitCustomerTabPage.getEmailAddressFromTopSectionInCustomerTabPage().equalsIgnoreCase(pcEmailId), "Customer details page doesn't contain the email Id of pc as "+pcEmailId);
		s_assert.assertTrue(cscockpitCustomerTabPage.getUserNameAndCIDStringFromTopSectionInCustomerTabPage().contains(pcCID), "Customer details page doesn't contain the CID of pc as "+pcCID);
		cscockpitCustomerTabPage.clickAddButtonOfCustomerAddressInCustomerTab();
		cscockpitCustomerTabPage.enterShippingInfoInAddNewPaymentProfilePopupWithoutSaveBtn(attendentName, addressLine1, city, postalCode, countryFullName, state, phoneNumber);
		cscockpitCustomerTabPage.clickCreateNewAddressBtn();
		cscockpitCustomerTabPage.clickUseEnteredAddressBtn();
		s_assert.assertTrue(cscockpitCustomerTabPage.isShippingProfilePresent(randomNum), "shipping profile name expected in customer tab page "+attendentName+" But was not found");
		cscockpitCustomerTabPage.clickEditButtonOfShippingAddressInCustomerTab(randomNum);
		cscockpitCustomerTabPage.enterShippingInfoInAddNewPaymentProfilePopupWithoutSaveBtn(attendentNameAfterEdit, addressLine1, city, postalCode, countryFullName, state, phoneNumber);
		cscockpitCustomerTabPage.clickUpdateAddressBtn();
		cscockpitCustomerTabPage.clickUseEnteredAddressBtn();
		s_assert.assertTrue(cscockpitCustomerTabPage.isShippingProfilePresent(lastNameAfterEdit), "shipping profile name expected in customer tab page "+attendentNameAfterEdit+" but was not found");
		s_assert.assertAll();
	}

	//Hybris Project-5501:Verify Add and Edit Shipping for RC
	@Test(priority=9)
	public void testVerifyAddAndEditShippingForRC_5501() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(1000, 9999);
		String firstName = TestConstants.DSV_FIRST_NAME;
		String lastName = ""+randomNum;
		String lastNameAfterEdit = randomNum+"edit";
		String attendentName = firstName+" "+lastName;
		String attendentNameAfterEdit = firstName+" "+lastNameAfterEdit;

		cscockpitCustomerSearchTabPage.clickCustomerSearchTab();
		cscockpitCustomerSearchTabPage.selectCustomerTypeFromDropDownInCustomerSearchTab("RETAIL");
		cscockpitCustomerSearchTabPage.selectAccountStatusFromDropDownInCustomerSearchTab("Active");
		cscockpitCustomerSearchTabPage.enterEmailIdInSearchFieldInCustomerSearchTab(rcEmailId);
		cscockpitCustomerSearchTabPage.clickSearchBtn();
		cscockpitCustomerSearchTabPage.verifyCountForCustomerFromSearchResult();
		s_assert.assertTrue(cscockpitCustomerSearchTabPage.getTotalResultsInCustomerSearchOnCustomerSearchTab()==1, "searched RC is not showing 1 result");
		cscockpitCustomerSearchTabPage.clickCIDNumberInCustomerSearchTab("1");
		s_assert.assertTrue(cscockpitCustomerTabPage.getEmailAddressFromTopSectionInCustomerTabPage().equalsIgnoreCase(rcEmailId), "Customer details page doesn't contain the email Id of RC as "+rcEmailId);
		s_assert.assertTrue(cscockpitCustomerTabPage.getUserNameAndCIDStringFromTopSectionInCustomerTabPage().contains(rcCID), "Customer details page doesn't contain the CID of RC as "+rcCID);
		cscockpitCustomerTabPage.clickAddButtonOfCustomerAddressInCustomerTab();
		cscockpitCustomerTabPage.enterShippingInfoInAddNewPaymentProfilePopupWithoutSaveBtn(attendentName, addressLine1, city, postalCode, countryFullName, state, phoneNumber);
		cscockpitCustomerTabPage.clickCreateNewAddressBtn();
		cscockpitCustomerTabPage.clickUseEnteredAddressBtn();
		s_assert.assertTrue(cscockpitCustomerTabPage.isShippingProfilePresent(randomNum), "shipping profile name expected in customer tab page "+attendentName+" But was not found");
		cscockpitCustomerTabPage.clickEditButtonOfShippingAddressInCustomerTab(randomNum);
		cscockpitCustomerTabPage.enterShippingInfoInAddNewPaymentProfilePopupWithoutSaveBtn(attendentNameAfterEdit, addressLine1, city, postalCode, countryFullName, state, phoneNumber);
		cscockpitCustomerTabPage.clickUpdateAddressBtn();
		cscockpitCustomerTabPage.clickUseEnteredAddressBtn();
		s_assert.assertTrue(cscockpitCustomerTabPage.isShippingProfilePresent(lastNameAfterEdit), "shipping profile name expected in customer tab page "+attendentNameAfterEdit+" but was not found");
		s_assert.assertAll();
	}

	//Hybris Project-5502:Verify Order Search for Consultant
	@Test(priority=10)
	public void testVerifyOrderSearchForConsultant_5502(){  
		cscockpitCustomerSearchTabPage.clickOrderSearchTab();
		cscockpitOrderSearchTabPage.selectOrderStatusOnOrderSearchTab(consultantOrderStatus);
		cscockpitOrderSearchTabPage.enterCIDOnOrderSearchTab(consultantCIDForOrderSearch);
		cscockpitOrderSearchTabPage.enterOrderNumberInOrderSearchTab(consultantOrderNumber);
		cscockpitOrderSearchTabPage.clickSearchBtn();
		cscockpitOrderSearchTabPage.clickOrderNumberInOrderSearchResultsInOrderSearchTab("1");
		s_assert.assertTrue(cscockpitOrderTabPage.verifyOrderDetailsIsPresentInOrderTab(consultantOrderNumber),"Order number "+consultantOrderNumber+" is not present on Order details page");
		s_assert.assertTrue(cscockpitOrderTabPage.verifyOrderDetailsIsPresentInOrderTab(consultantCIDForOrderSearch),"CID number "+consultantCID+" is not present on Order details page");  
		s_assert.assertAll();
	}

	// Hybris Project-5503:Verify Order Search for PC
	@Test(priority=11)
	public void testVerifyOrderSearchForPC_5503(){  
		cscockpitCustomerSearchTabPage.clickOrderSearchTab();
		cscockpitOrderSearchTabPage.selectOrderStatusOnOrderSearchTab(pcOrderStatus);
		cscockpitOrderSearchTabPage.enterCIDOnOrderSearchTab(pcCIDForOrderSearch);
		cscockpitOrderSearchTabPage.enterOrderNumberInOrderSearchTab(pcOrderNumber);
		cscockpitOrderSearchTabPage.clickSearchBtn();
		cscockpitOrderSearchTabPage.clickOrderNumberInOrderSearchResultsInOrderSearchTab("1");
		s_assert.assertTrue(cscockpitOrderTabPage.verifyOrderDetailsIsPresentInOrderTab(pcOrderNumber),"Order number "+pcOrderNumber+" is not present on Order details page");
		s_assert.assertTrue(cscockpitOrderTabPage.verifyOrderDetailsIsPresentInOrderTab(pcCIDForOrderSearch),"CID number "+pcCID+" is not present on Order details page");  
		s_assert.assertAll();
	}

	//Hybris Project-5504:Verify Order Search for RC
	@Test(priority=12)
	public void testVerifyOrderSearchForRC_5504(){  
		cscockpitCustomerSearchTabPage.clickOrderSearchTab();
		cscockpitOrderSearchTabPage.selectOrderStatusOnOrderSearchTab(rcOrderStatus);
		cscockpitOrderSearchTabPage.enterCIDOnOrderSearchTab(rcCIDForOrderSearch);
		cscockpitOrderSearchTabPage.enterOrderNumberInOrderSearchTab(rcOrderNumber);
		cscockpitOrderSearchTabPage.clickSearchBtn();
		cscockpitOrderSearchTabPage.clickOrderNumberInOrderSearchResultsInOrderSearchTab("1");
		s_assert.assertTrue(cscockpitOrderTabPage.verifyOrderDetailsIsPresentInOrderTab(rcOrderNumber),"Order number "+rcOrderNumber+" is not present on Order details page");
		s_assert.assertTrue(cscockpitOrderTabPage.verifyOrderDetailsIsPresentInOrderTab(rcCIDForOrderSearch),"CID number "+rcCID+" is not present on Order details page");  
		s_assert.assertAll();
	}

	//Hybris Project-5505:Verify Update CRP Autoship for Consultant
	@Test(enabled=false)//WIP
	public void testVerifyUpdateCRPAutoshipConsultant_5505(){		
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);		
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		cscockpitCustomerSearchTabPage.clickCustomerSearchTab();
		cscockpitCustomerSearchTabPage.selectCustomerTypeFromDropDownInCustomerSearchTab("Consultant");
		cscockpitCustomerSearchTabPage.selectAccountStatusFromDropDownInCustomerSearchTab("Active");
		cscockpitCustomerSearchTabPage.enterEmailIdInSearchFieldInCustomerSearchTab(consultantEmailId);
		cscockpitCustomerSearchTabPage.clickSearchBtn();
		cscockpitCustomerSearchTabPage.verifyCountForCustomerFromSearchResult();
		s_assert.assertTrue(cscockpitCustomerSearchTabPage.getTotalResultsInCustomerSearchOnCustomerSearchTab()==1, "searched Consultant is not showing 1 result");
		cscockpitCustomerSearchTabPage.clickCIDNumberInCustomerSearchTab("1");
		cscockpitCustomerTabPage.clickAutoshipIdOnCustomerTab();
		cscockpitAutoshipTemplateTabPage.clickEditAutoshiptemplate();
		cscockpitAutoshipTemplateTabPage.clickAddMoreLinesLinkInAutoShipTemplateTab();
		cscockpitAutoshipCartTabPage.selectValueFromSortByDDInCartTab("Price: High to Low");
		cscockpitAutoshipCartTabPage.selectCatalogFromDropDownInCartTab(); 
		String randomProductSequenceNumber = String.valueOf(cscockpitAutoshipCartTabPage.getRandomProductWithSKUFromSearchResult()); 
		String SKUValue = cscockpitAutoshipCartTabPage.getCustomerSKUValueInCartTab(randomProductSequenceNumber);
		cscockpitAutoshipCartTabPage.searchSKUValueInCartTab(SKUValue);
		SKUValue = cscockpitAutoshipCartTabPage.clickAddToCartBtnInCartTab(SKUValue);
		cscockpitAutoshipCartTabPage.clickCheckoutBtnInCartTab();		
		s_assert.assertAll();
	}

}