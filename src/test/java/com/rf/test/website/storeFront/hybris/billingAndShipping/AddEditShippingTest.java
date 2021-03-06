package com.rf.test.website.storeFront.hybris.billingAndShipping;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.rf.core.utils.CommonUtils;
import com.rf.core.utils.DBUtil;
import com.rf.core.utils.QueryUtils;
import com.rf.core.website.constants.TestConstants;
import com.rf.core.website.constants.dbQueries.DBQueries_RFO;
import com.rf.pages.website.storeFront.StoreFrontAccountInfoPage;
import com.rf.pages.website.storeFront.StoreFrontBillingInfoPage;
import com.rf.pages.website.storeFront.StoreFrontCartAutoShipPage;
import com.rf.pages.website.storeFront.StoreFrontConsultantPage;
import com.rf.pages.website.storeFront.StoreFrontHomePage;
import com.rf.pages.website.storeFront.StoreFrontOrdersPage;
import com.rf.pages.website.storeFront.StoreFrontPCUserPage;
import com.rf.pages.website.storeFront.StoreFrontShippingInfoPage;
import com.rf.pages.website.storeFront.StoreFrontUpdateCartPage;
import com.rf.test.website.RFStoreFrontWebsiteBaseTest;
import com.rf.test.website.RFWebsiteBaseTest;

public class AddEditShippingTest extends RFWebsiteBaseTest{

	private static final Logger logger = LogManager
			.getLogger(AddEditShippingTest.class.getName());

	private int randomNum; 	
	List<Map<String, Object>> randomConsultantList =  null;
	String consultantEmailID = null;
	String accountID = null;

	@BeforeClass
	public void setupDataForAddShipping() throws InterruptedException{	
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		navigateToStoreFrontBaseURL();
		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
			accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			logger.info("Account Id of the user is "+accountID);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isError = driver.getCurrentUrl().contains("error");
			if(isError){
				logger.info("Login error for the user "+consultantEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());				
			}
			else{
				storeFrontConsultantPage.clickOnWelcomeDropDown();
				if(storeFrontHomePage.isEditCRPLinkPresent()==true){
					break;
				}
				else{
					driver.get(driver.getURL()+"/"+driver.getCountry());					
				}
			}

		}
		logger.info("login is successful");

	}

	//Hybris Project-2029 :: Version : 1 :: Add shipping address on 'Shipping Profile' page
	//Hybris Phase 2-2035 :: Version : 1 :: Edit shipping address on 'Shipping Profile' page
	@Test(priority=1)
	public void testAddAndEditShippingAddressOnShippingProfilePage_2029_2035() throws InterruptedException{
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String lastName = "lN";
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontShippingInfoPage = storeFrontConsultantPage.clickShippingLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontShippingInfoPage.verifyShippingInfoPageIsDisplayed(),"shipping info page has not been displayed");
		storeFrontShippingInfoPage.clickAddNewShippingProfileLink();
		String newShippingAddressName = TestConstants.ADDRESS_NAME+randomNum;
		storeFrontShippingInfoPage.enterNewShippingAddressName(newShippingAddressName+" "+lastName);
		storeFrontShippingInfoPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontShippingInfoPage.enterNewShippingAddressCity(city);
		storeFrontShippingInfoPage.selectNewShippingAddressState(state);
		storeFrontShippingInfoPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontShippingInfoPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontShippingInfoPage.selectUseThisShippingProfileFutureAutoshipChkbox();
		storeFrontShippingInfoPage.clickOnSaveShippingProfile();

		//--------------- Verify that Newly added Shipping is listed in the Shipping profiles section-----------------------------------------------------------------------------------------------------

		s_assert.assertTrue(storeFrontShippingInfoPage.isShippingAddressPresentOnShippingPage(newShippingAddressName), "Newly Added Shipping address is not listed on Shipping profile page");

		//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		//--------------- Verify That 'Autoship Order Address' Text is displayed under default shipping Address-------------------------------------------------------------------------------------------

		s_assert.assertTrue(storeFrontShippingInfoPage.isAutoshipOrderAddressTextPresent(newShippingAddressName), "Autoship order text is not present under the newly added Shipping Address when future autoship checkbox is selected");

		//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		// Edit the shipping address
		int randomNum1 = CommonUtils.getRandomNum(10000, 1000000);
		String editShippingAddressName = TestConstants.ADDRESS_NAME+randomNum1;
		storeFrontShippingInfoPage.clickOnEditOfShippingProfile(newShippingAddressName);
		storeFrontShippingInfoPage.enterNewShippingAddressName(editShippingAddressName+" "+lastName);
		storeFrontShippingInfoPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontShippingInfoPage.enterNewShippingAddressCity(city);
		storeFrontShippingInfoPage.selectNewShippingAddressState(state);
		storeFrontShippingInfoPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontShippingInfoPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontShippingInfoPage.selectUseThisShippingProfileFutureAutoshipChkbox();
		storeFrontShippingInfoPage.clickOnSaveShippingProfile();

		//--------------- Verify that Newly added Shipping is listed in the Billing profiles section-----------------------------------------------------------------------------------------------------

		s_assert.assertTrue(storeFrontShippingInfoPage.isShippingAddressPresentOnShippingPage(editShippingAddressName), "Newly Edited Shipping address is not selected listed on Shipping profile page");

		//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		//--------------- Verify That 'Autoship Order Address' Text is displayed under default shipping Address-------------------------------------------------------------------------------------------

		s_assert.assertTrue(storeFrontShippingInfoPage.isAutoshipOrderAddressTextPresent(editShippingAddressName), "Autoship order text not present under the newly edited Shipping Address");

		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontCartAutoShipPage = storeFrontConsultantPage.clickEditCrpLinkPresentOnWelcomeDropDown();
		storeFrontUpdateCartPage = storeFrontCartAutoShipPage.clickUpdateMoreInfoLink();
		storeFrontUpdateCartPage.clickOnEditShipping();

		//---------------Verify that the new added shipping address is displayed in 'Shipment' section on update autoship cart page------------------------------------------------------------------------

		s_assert.assertTrue(storeFrontUpdateCartPage.isShippingAddressPresent(editShippingAddressName), "Newly added/edited Shipping address NOT selected in update cart under shipping section");

		//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		s_assert.assertAll();		
	}

	//Hybris Project-2030 :: Version : 1 :: Add shipping address during checkout 
	//Hybris Project-2036 :: Version : 1 :: Edit shipping address during checkout
	@Test(priority=2)
	public void testAddAndEditShippingAddressDuringCheckout_2030_2036() throws InterruptedException{
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String lastName = "lN";
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontShippingInfoPage = storeFrontConsultantPage.clickShippingLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontShippingInfoPage.verifyShippingInfoPageIsDisplayed(),"shipping info page has not been displayed");
		String defaultSelectedShippingAddressNameOnShippingInfo = storeFrontShippingInfoPage.getDefaultSelectedShippingAddress();
		storeFrontConsultantPage = storeFrontHomePage.clickOnRodanAndFieldsLogo();
		//Continue with checkout page
		storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();		
		storeFrontUpdateCartPage.clickAddToBagButton(country);
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		storeFrontUpdateCartPage.clickOnAddANewShippingAddress();

		String newShippingAddressName = TestConstants.ADDRESS_NAME+randomNum;
		storeFrontUpdateCartPage.enterNewShippingAddressName(newShippingAddressName+" "+lastName);
		storeFrontUpdateCartPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontUpdateCartPage.enterNewShippingAddressCity(city);
		storeFrontUpdateCartPage.selectNewShippingAddressStateOnCartPage(state);
		storeFrontUpdateCartPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontUpdateCartPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontUpdateCartPage.clickOnSaveShippingProfileAfterEdit();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyNewShippingAddressIsSelectedByDefaultOnAdhocCart(newShippingAddressName), "Newly added Address has not got associated with the billing profile");

		//Edit shipping address
		int randomNum1 = CommonUtils.getRandomNum(10000, 1000000);
		String editShippingAddressName = TestConstants.ADDRESS_NAME+randomNum1;
		storeFrontUpdateCartPage.clickOnEditOfShippingProfile(newShippingAddressName);	
		storeFrontUpdateCartPage.enterNewShippingAddressName(editShippingAddressName+" "+TestConstants.LAST_NAME);
		storeFrontUpdateCartPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontUpdateCartPage.enterNewShippingAddressCity(city);
		storeFrontUpdateCartPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontUpdateCartPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontUpdateCartPage.clickOnSaveShippingProfileAfterEdit();
		// verify that updated address is listed on cart page
		s_assert.assertTrue(storeFrontUpdateCartPage.isShippingAddressPresent(editShippingAddressName), "Edited Shipping address NOT added to update cart");
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		storeFrontUpdateCartPage.clickOnBillingNextStepBtn();
		storeFrontUpdateCartPage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyOrderPlacedConfirmationMessage(),"order is not placed successfully");
		s_assert.assertTrue(storeFrontUpdateCartPage.getUpdatedAddressPresentOnOrderConfirmationPage().toLowerCase().contains(editShippingAddressName.toLowerCase()),"Edited shipping address name expected is"+editShippingAddressName+"while shippig address coming on order confirmation page is while edit the address "+storeFrontUpdateCartPage.getUpdatedAddressPresentOnOrderConfirmationPage());

		storeFrontConsultantPage.clickOnRodanAndFieldsLogo();
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontShippingInfoPage = storeFrontConsultantPage.clickShippingLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontShippingInfoPage.isShippingAddressPresentOnShippingPage(editShippingAddressName), "Newly/Edited Shipping address is not listed on Shipping profile page");
		String defaultSelectedShippingAddressNameAfterAddAShippingAddress = storeFrontShippingInfoPage.getDefaultSelectedShippingAddress();
		s_assert.assertTrue(storeFrontShippingInfoPage.verifyOldDefaultSelectAddress(defaultSelectedShippingAddressNameOnShippingInfo, defaultSelectedShippingAddressNameAfterAddAShippingAddress), "Newly/Edited Address has not got associated with the shipping profile");
		s_assert.assertAll();
	}

	//Hybris Project-2031 :: Version : 1 :: Add shipping address in autoship template 
	//Hybris Project-2037 :: Version : 1 :: Edit shipping address in autoship template
	@Test(priority=3)
	public void testAddAndEditShippingAddressInAutoshipTemplate_2031_2037() throws InterruptedException{
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String lastName = "lN";
		//get shipping address name on adhoc order
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		storeFrontOrdersPage.clickOnWelcomeDropDown();
		storeFrontCartAutoShipPage = storeFrontOrdersPage.clickEditCrpLinkPresentOnWelcomeDropDown();
		storeFrontUpdateCartPage = storeFrontCartAutoShipPage.clickUpdateMoreInfoLink();
		storeFrontUpdateCartPage.clickOnEditShipping();
		storeFrontUpdateCartPage.clickAddNewShippingProfileLink();
		String newShippingAddressName = TestConstants.ADDRESS_NAME+randomNum;

		storeFrontUpdateCartPage.enterNewShippingAddressName(newShippingAddressName+" "+lastName);
		storeFrontUpdateCartPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontUpdateCartPage.enterNewShippingAddressCity(city);
		storeFrontUpdateCartPage.selectNewShippingAddressState(state);
		storeFrontUpdateCartPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontUpdateCartPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontUpdateCartPage.clickOnSaveShippingProfile();
		//		storeFrontUpdateCartPage.clickOnUpdateCartShippingNextStepBtn();
		//		storeFrontUpdateCartPage.clickOnEditShipping();

		//storeFrontUpdateCartPage.clickOnEditShipping();

		//---------------Verify that the new added shipping address is displayed in 'Shipment' section on update autoship cart page------------------------------------------------------------------------

		s_assert.assertTrue(storeFrontUpdateCartPage.isShippingAddressPresent(newShippingAddressName), "Newly added Shipping address NOT selected in update cart under shipping section");
		s_assert.assertTrue(storeFrontUpdateCartPage.isNewlyCreatedShippingProfileIsSelectedByDefault(newShippingAddressName), "Newly added Shipping address NOT selected as default in update cart under shipping section");

		//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		//Edit shipping address
		int randomNum1 = CommonUtils.getRandomNum(10000, 1000000);
		String editShippingAddressName = TestConstants.ADDRESS_NAME+randomNum1;
		storeFrontUpdateCartPage.clickOnEditOfShippingProfile(newShippingAddressName);	
		storeFrontUpdateCartPage.enterNewShippingAddressName(editShippingAddressName+" "+TestConstants.LAST_NAME);
		storeFrontUpdateCartPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontUpdateCartPage.enterNewShippingAddressCity(city);
		storeFrontUpdateCartPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontUpdateCartPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontUpdateCartPage.clickOnSaveShippingProfile();
		//		storeFrontUpdateCartPage.clickOnUpdateCartShippingNextStepBtn();
		//		storeFrontUpdateCartPage.clickOnEditShipping();

		s_assert.assertTrue(storeFrontUpdateCartPage.isShippingAddressPresent(editShippingAddressName),"Edited Shipping Address is not on cart page");
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyNewlyEditedShippingAddressIsSelected(editShippingAddressName),"newly edited shipping address is not choosen by default");

		storeFrontUpdateCartPage.clickOnUpdateCartShippingNextStepBtn();
		storeFrontUpdateCartPage.clickOnNextStepBtn();
		storeFrontUpdateCartPage.clickUpdateCartBtn();
		storeFrontUpdateCartPage.clickOnRodanAndFieldsLogo();
		storeFrontUpdateCartPage.clickOnWelcomeDropDown();
		storeFrontShippingInfoPage = storeFrontUpdateCartPage.clickShippingLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontShippingInfoPage.verifyShippingInfoPageIsDisplayed(),"shipping info page has not been displayed");
		s_assert.assertTrue(storeFrontShippingInfoPage.isShippingAddressPresentOnShippingPage(editShippingAddressName), "Newly added/edited Shipping address is not listed on Shipping profile page");
		//s_assert.assertTrue(storeFrontShippingInfoPage.isAutoshipOrderAddressTextPresent(newShippingAddressName), "Autoship order text is not present under the new Shipping Address");
		s_assert.assertFalse(storeFrontShippingInfoPage.verifyRadioButtonNotSelectedByDefault(editShippingAddressName), "Newly added/edited shipping address is selected by default");
		s_assert.assertAll();
	} 

	//Hybris Project-2238:Verify that QAS validation gets perform everytime user adds a shipping address.
	//Hybris Project-2239:Verify that QAS validation gets perform everytime user edits a shipping address.
	@Test(priority=4)
	public void testQASValidationPerformEveryTimeUserAddsAndEditsAShippingAddress_2238_2239() throws InterruptedException{
		String newAddressLine1=null;
		if(country.equalsIgnoreCase("ca")){
			newAddressLine1=TestConstants.NEW_ADDRESS_LINE_1_CA;
		}
		else 	if(country.equalsIgnoreCase("au")){
			newAddressLine1=TestConstants.NEW_ADDRESS_LINE_1_AU;
		}
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String lastName = "lN";
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();  
		storeFrontUpdateCartPage.clickAddToBagButton(country);
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		//Add a new shipping address
		storeFrontUpdateCartPage.clickAddNewShippingProfileLink();
		String newShippingAddressName = TestConstants.ADDRESS_NAME+randomNum;

		storeFrontUpdateCartPage.enterNewShippingAddressName(newShippingAddressName+" "+lastName);
		storeFrontUpdateCartPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontUpdateCartPage.enterNewShippingAddressCity(city);
		storeFrontUpdateCartPage.selectNewShippingAddressState(state);
		storeFrontUpdateCartPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontUpdateCartPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontUpdateCartPage.clickOnSaveShippingProfileWithoutAcceptingQASValidationPopUp();
		//validate QAS Validation PopUp is Displayed after adding a Shipping Profile
		s_assert.assertTrue(storeFrontUpdateCartPage.validateQASValidationPopUpIsDisplayed(),"QAS Validation PopUp is Not Displayed After Adding A Shipping Profile");
		storeFrontUpdateCartPage.clickOnAcceptOfQASPopup();
		storeFrontUpdateCartPage.clickOnEditOfShippingProfile(newShippingAddressName);
		storeFrontUpdateCartPage.enterNewShippingAddressLine1(newAddressLine1);
		storeFrontUpdateCartPage.clickOnSaveShippingProfileWithoutAcceptingQASValidationPopUp();
		//validate QAS Validation PopUp is Displayed after adding a Shipping Profile
		s_assert.assertTrue(storeFrontUpdateCartPage.validateQASValidationPopUpIsDisplayed(),"QAS Validation PopUp is Not Displayed After Edit A Shipping Profile");
		s_assert.assertAll();
	}

	//Hybris Project-2094:Popup to update autoship shipping profile on changing default selection
	@Test(priority=5)
	public void testPopUpToUpdateAutoshipShippingProfileOnChangingDefaultSelection_2094() throws InterruptedException			 {
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String lastName = "lN";
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontShippingInfoPage=storeFrontHomePage.clickShippingLinkPresentOnWelcomeDropDown();

		storeFrontShippingInfoPage.clickAddNewShippingProfileLink();
		String newShippingAddressName = TestConstants.ADDRESS_NAME+randomNum;
		storeFrontShippingInfoPage.enterNewShippingAddressName(newShippingAddressName+" "+lastName);
		storeFrontShippingInfoPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontShippingInfoPage.enterNewShippingAddressCity(city);
		storeFrontShippingInfoPage.selectNewShippingAddressState(state);
		storeFrontShippingInfoPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontShippingInfoPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontShippingInfoPage.clickOnSaveShippingProfile();
		//change default shipping profile selection and validate Update AutoShip PopUp
		storeFrontShippingInfoPage.makeShippingProfileAsDefault(newShippingAddressName);
		s_assert.assertTrue(storeFrontShippingInfoPage.validateUpdateAutoShipPopUpPresent(), "Update AutoShip PopUp is not present!!");
		s_assert.assertAll();
	}

	//Hybris Phase 2-4326: View shipping address on 'Shipping Profile' page
	@Test(enabled=false)//Covered in other tests
	public void testShippingAddressOnShippingProfile_HP2_4326() throws InterruptedException, SQLException{
		List<Map<String, Object>> shippingAddressCountList =  null;
		List<Map<String, Object>> defaultShippingAddressList =  null;
		String shippingAddressName=null;

		int totalShippingAddressesFromDB = 0;
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontShippingInfoPage = storeFrontConsultantPage.clickShippingLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontShippingInfoPage.verifyShippingInfoPageIsDisplayed(),"shipping info page has not been displayed");

		//------------------The same number of billing addresses is shown in RFO and Front end----------------------------------------------------------------------------------------------------------------------------
		shippingAddressCountList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_SHIPPING_ADDRESS_COUNT_QUERY,consultantEmailID),RFO_DB);
		totalShippingAddressesFromDB = (Integer) getValueFromQueryResult(shippingAddressCountList, "count");			
		assertEquals("Shipping Addresses count on UI is different from DB", totalShippingAddressesFromDB,storeFrontShippingInfoPage.getTotalShippingAddressesDisplayed());			

		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		if(totalShippingAddressesFromDB > 1){

			//---------------------------------Radio button is checked for the default shipping address on Front end as per RFO--------------------------------------------------------------------------------------------
			defaultShippingAddressList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_DEFAULT_BILLING_ADDRESS_QUERY,consultantEmailID),RFO_DB);
			shippingAddressName = (String) getValueFromQueryResult(defaultShippingAddressList, "AddressLine1");
			assertTrue("Default radio button in Shipping page is not selected", storeFrontShippingInfoPage.isDefaultShippingAddressSelected(shippingAddressName));
		}

		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		s_assert.assertAll();
	}

	//Hybris Project-2034 :: Version : 1 :: Add shipping address during CRP enrollment through my account 
	//Hybris Project-2040 :: Version : 1 :: Edit shipping address during CRP enrollment through my account
	@Test(priority=7)
	public void testAddAndEditShippingAddressDuringCRPEnrollment_2034_2040() throws InterruptedException{
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		regimenName = TestConstants.REGIMEN_NAME_UNBLEMISH;
		String newShippingAddressName = TestConstants.FIRST_NAME+randomNum;
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontConsultantPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyAccountInfoPageIsDisplayed(),"shipping info page has not been displayed");
		storeFrontAccountInfoPage.clickOnYourAccountDropdown();
		storeFrontAccountInfoPage.clickOnAutoShipStatus();
		if(storeFrontAccountInfoPage.verifyCRPCancelled()==false){
			storeFrontAccountInfoPage.clickOnCancelMyCRP();
		}
		storeFrontAccountInfoPage.clickOnEnrollInCRP();
		storeFrontAccountInfoPage.clickOnAddToCRPButtonCreatingCRPUnderBizSite();
		storeFrontAccountInfoPage.clickOnCRPCheckout();
		storeFrontUpdateCartPage.clickAddNewShippingProfileLink();
		String lastName = "In";
		String newShippingName = newShippingAddressName+randomNum;
		storeFrontUpdateCartPage.enterNewShippingAddressName(newShippingName+" "+lastName);
		storeFrontUpdateCartPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontUpdateCartPage.enterNewShippingAddressCity(city);
		storeFrontUpdateCartPage.selectNewShippingAddressStateForDuringCRPEnrollment(state);
		storeFrontUpdateCartPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontUpdateCartPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontUpdateCartPage.clickOnSaveCRPShippingInfo();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyNewlyCreatedShippingAddressIsSelectedByDefault(newShippingName), "Newly created shipping address is not selected by default");
		int randomNum1 = CommonUtils.getRandomNum(10000, 1000000);
		String editedShippingName = newShippingAddressName+randomNum1;
		storeFrontUpdateCartPage.clickOnEditOfShippingProfile(newShippingName);
		storeFrontUpdateCartPage.enterNewShippingAddressName(editedShippingName+" "+lastName);
		storeFrontUpdateCartPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontUpdateCartPage.enterNewShippingAddressCity(city);
		storeFrontUpdateCartPage.selectNewShippingAddressStateForDuringCRPEnrollment(state);
		storeFrontUpdateCartPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontUpdateCartPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontUpdateCartPage.clickOnSaveCRPShippingInfo();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyNewlyCreatedShippingAddressIsSelectedByDefault(editedShippingName), "Newly edited shipping address is not selected by default while edit the address");
		storeFrontUpdateCartPage.clickOnUpdateCartShippingNextStepBtnDuringEnrollment();
		storeFrontUpdateCartPage.clickOnBillingNextStepBtn();
		storeFrontUpdateCartPage.clickOnSetupCRPAccountBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyOrderConfirmation(), "Order Confirmation Message has not been displayed");
		// verify on shipping info page
		storeFrontUpdateCartPage.clickOnWelcomeDropDown();
		storeFrontShippingInfoPage = storeFrontUpdateCartPage.clickShippingLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontShippingInfoPage.verifyShippingInfoPageIsDisplayed(),"shipping info page has not been displayed");
		s_assert.assertTrue(storeFrontShippingInfoPage.isShippingAddressPresentOnShippingPage(editedShippingName), "Newly/Edited Shipping address is not listed on Shipping profile page");
		s_assert.assertFalse(storeFrontShippingInfoPage.verifyRadioButtonIsSelectedByDefault(editedShippingName), "Shipping address was given in main account info is selected by default");
		s_assert.assertAll();
	}

	//Hybris Project-2032 :: Version : 1 :: Add shipping address during PC user or Retail user registration		
	//Hybris Project-2038 :: Version : 1 :: Edit shipping address during PC user or Retail user registration
	@Test(priority=8)
	public void testAddShippingAddressDuringPCRegistration_2032_2038() throws InterruptedException{
		randomNum = CommonUtils.getRandomNum(10000, 1000000);		
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String lastName = "lN";
		String firstName=TestConstants.FIRST_NAME+randomNum;
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		storeFrontHomePage.clickOnCheckoutButton();
		String pcEmailID = firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		storeFrontHomePage.enterNewPCDetails(firstName, TestConstants.LAST_NAME+randomNum, pcEmailID,password);
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		storeFrontHomePage.clickOnContinueWithoutSponsorLink();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate();
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		storeFrontHomePage.clickOKBtnOfJavaScriptPopUp();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontUpdateCartPage.clickOnEditShipping();
		storeFrontUpdateCartPage.clickOnAddANewShippingAddress();
		String newShippingAddressName = TestConstants.ADDRESS_NAME+randomNum;
		storeFrontUpdateCartPage.enterNewShippingAddressName(newShippingAddressName+" "+lastName);
		storeFrontUpdateCartPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontUpdateCartPage.enterNewShippingAddressCity(city);
		storeFrontUpdateCartPage.selectNewShippingAddressStateOnCartPage(state);
		storeFrontUpdateCartPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontUpdateCartPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontUpdateCartPage.clickOnSaveShippingProfileAfterEdit();
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		storeFrontUpdateCartPage.clickOnEditShipping();
		s_assert.assertTrue(storeFrontUpdateCartPage.isShippingAddressPresent(newShippingAddressName), "Newly added Shipping address NOT selected in update cart under shipping section");
		int randomNum1 = CommonUtils.getRandomNum(10000, 1000000);
		String editShippingAddressName = TestConstants.ADDRESS_NAME+randomNum1;
		storeFrontUpdateCartPage.clickOnEditOfShippingProfile(newShippingAddressName);	
		storeFrontUpdateCartPage.enterNewShippingAddressName(editShippingAddressName+" "+TestConstants.LAST_NAME);
		storeFrontUpdateCartPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontUpdateCartPage.enterNewShippingAddressCity(city);
		storeFrontUpdateCartPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontUpdateCartPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontUpdateCartPage.clickOnSaveShippingProfileAfterEdit();
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		storeFrontUpdateCartPage.clickOnEditShipping();
		s_assert.assertTrue(storeFrontUpdateCartPage.isShippingAddressPresent(editShippingAddressName), "Edited Shipping address NOT selected in update cart under shipping section");
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		storeFrontUpdateCartPage.clickOnBillingNextStepBtn();
		s_assert.assertTrue(storeFrontHomePage.verifyShippingAddressOnOrderPage(editShippingAddressName),"Newly/Edited Shipping address on order page is not the edited address");
		storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
		storeFrontHomePage.clickPlaceOrderBtn();
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		storeFrontUpdateCartPage.clickOnWelcomeDropDown();
		storeFrontShippingInfoPage = storeFrontUpdateCartPage.clickShippingLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontShippingInfoPage.verifyShippingInfoPageIsDisplayed(),"shipping info page has not been displayed");
		s_assert.assertTrue(storeFrontShippingInfoPage.isShippingAddressPresentOnShippingPage(editShippingAddressName), "Newly added/edited Shipping address is not listed on Shipping profile page");
		s_assert.assertFalse(storeFrontShippingInfoPage.verifyRadioButtonNotSelectedByDefault(editShippingAddressName), "Newly added/edited created shipping address is selected by default");
		s_assert.assertTrue(storeFrontShippingInfoPage.verifyRadioButtonIsSelectedByDefault(firstName), "Shipping address was given in main account info is not selected by default");
		storeFrontUpdateCartPage.clickOnAutoshipCart();
		storeFrontUpdateCartPage.clickOnUpdateMoreInfoButton();
		s_assert.assertTrue(storeFrontUpdateCartPage.isShippingAddressPresent(editShippingAddressName), "Newly added/edited Shipping address NOT listed in update cart under shipping section");
		s_assert.assertAll();

	}

	//Hybris Project-2033 :: Version : 1 :: Add shipping address during consultant enrollment
	//Hybris Project-2039 :: Version : 1 :: Edit shipping address during consultant enrollment
	@Test(priority=9)
	public void testAddAndEditShippingAddressDuringConsultantEnrollment_2033_2039() throws InterruptedException{
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		String  consultantEmailAddress=TestConstants.FIRST_NAME+randomNum+TestConstants.EMAIL_ADDRESS_SUFFIX;
		String firstName = TestConstants.FIRST_NAME+randomNum;
		//String shippingAddressName = firstName+" "+TestConstants.LAST_NAME;
		storeFrontHomePage.openPWSSite(country, env);
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.enterUserInformationForEnrollmentWithEmail(kitName, regimenName, enrollmentType, firstName, TestConstants.LAST_NAME,consultantEmailAddress, password, addressLine1, city,state, postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate();
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		s_assert.assertTrue(storeFrontHomePage.isShippingAddressPresentAtOrderAndReviewPage(firstName), "Newly added address is not present at order and revie confirm page");
		//Edit the shipping address
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String newShippingAddressName = TestConstants.FIRST_NAME+randomNumber;
		storeFrontAccountInfoPage=storeFrontHomePage.clickOnEditShippingOnReviewAndConfirmPage();
		storeFrontHomePage.editFirstName(newShippingAddressName);
		storeFrontHomePage.enterPassword(password);
		storeFrontHomePage.enterConfirmPassword(password);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.selectAllTermsAndConditionsChkBox();
		storeFrontHomePage.clickOnEnrollMeBtn();
		storeFrontHomePage.clickOnConfirmAutomaticPayment();
		s_assert.assertTrue(storeFrontHomePage.verifyCongratsMessage(), "Congrats Message is not visible");
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");

		storeFrontHomePage.clickOnWelcomeDropDown();
		storeFrontOrdersPage =  storeFrontHomePage.clickOrdersLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontOrdersPage.verifyOrdersPageIsDisplayed(),"Orders page has not been displayed");

		// Get Order Number
		String orderHistoryNumber = storeFrontOrdersPage.getFirstOrderNumberFromOrderHistory();
		storeFrontOrdersPage.clickOrderNumber(orderHistoryNumber);
		s_assert.assertTrue(storeFrontOrdersPage.getShippingAddressFromAdhocTemplate().contains(newShippingAddressName.toLowerCase()), "Adhoc Order template newly/edited shipping address on RFO is"+newShippingAddressName+" and on UI is "+storeFrontOrdersPage.getShippingAddressFromAdhocTemplate());

		// verify on shipping info page
		storeFrontHomePage.clickOnWelcomeDropDown();
		storeFrontShippingInfoPage = storeFrontHomePage.clickShippingLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontShippingInfoPage.verifyShippingInfoPageIsDisplayed(),"shipping info page has not been displayed");
		s_assert.assertTrue(storeFrontShippingInfoPage.isShippingAddressPresentOnShippingPage(newShippingAddressName), "Newly added/edited Shipping address is not listed on Shipping profile page");
		s_assert.assertTrue(storeFrontShippingInfoPage.verifyRadioButtonNotSelectedByDefault(newShippingAddressName), "Newly added/editedcreated shipping address is not selected by default");
		s_assert.assertTrue(storeFrontShippingInfoPage.verifyRadioButtonIsSelectedByDefault(newShippingAddressName), "newly added/edited Shipping address was given in main account info is selected by default");
		s_assert.assertAll();
	}

	// Hybris Project-2283:Add Multiple shippiing address from myaccounts
	@Test(priority=10)
	public void testAddMultipeShippingAddressFromMyAccounts_2283() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int i=0;
		String profileName=null;
		String lastName = "lN";
		profileName=TestConstants.NEW_SHIPPING_PROFILE_FIRST_NAME_CA+randomNum;
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontShippingInfoPage=storeFrontConsultantPage.clickShippingLinkPresentOnWelcomeDropDown();
		//Add multiple Shipping profile from my accounts.
		for(i=0;i<2;i++){
			storeFrontShippingInfoPage.clickAddNewShippingProfileLink();
			storeFrontShippingInfoPage.enterNewShippingAddressName(profileName+i+" "+lastName);
			storeFrontShippingInfoPage.enterNewShippingAddressLine1(addressLine1);
			storeFrontShippingInfoPage.enterNewShippingAddressCity(city);
			storeFrontShippingInfoPage.selectNewShippingAddressState(state);
			storeFrontShippingInfoPage.enterNewShippingAddressPostalCode(postalCode);
			storeFrontShippingInfoPage.enterNewShippingAddressPhoneNumber(phoneNumber);
			storeFrontShippingInfoPage.clickOnSaveShippingProfile();
			s_assert.assertTrue(storeFrontShippingInfoPage.isShippingAddressPresentOnShippingPage(profileName+i),"New Created Shipping Profile is not present on shipping info page");
		}
		i=1;
		storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage.clickAddToBagButton(country);
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		s_assert.assertTrue(storeFrontUpdateCartPage.isShippingAddressPresent(profileName+"0"),"Newly created shipping address is not present on shipment section during checkout");
		s_assert.assertTrue(storeFrontUpdateCartPage.isShippingAddressPresent(profileName+i),"Newly created shipping address is not present on shipment section during checkout");
		storeFrontUpdateCartPage.clickOnNewShipToThisAddressRadioButton(profileName+"0");
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		storeFrontUpdateCartPage.clickOnBillingNextStepBtn(); 
		storeFrontUpdateCartPage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.isNewEditedShippingProfileIsPresentOnOrderConfirmationPage(profileName+"0"),"New Edited Shipping Profile is not Present by default on Order Summary page");
		s_assert.assertTrue(storeFrontHomePage.isOrderPlacedSuccessfully(),"Order is not placed successfully");
		storeFrontConsultantPage = storeFrontUpdateCartPage.clickOnRodanAndFieldsLogo();
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontOrdersPage = storeFrontConsultantPage.clickOrdersLinkPresentOnWelcomeDropDown();
		storeFrontOrdersPage.clickOnFirstAdHocOrder();
		s_assert.assertTrue(storeFrontOrdersPage.isShippingAddressContainsName(profileName+"0"),"AdHoc Orders Page do not contain the newly edited shipping address");
		s_assert.assertAll();
	}

}