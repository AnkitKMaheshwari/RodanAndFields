package com.rf.test.website.storeFront.brandRefresh.dsv;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.rf.core.utils.CommonUtils;
import com.rf.core.utils.DBUtil;
import com.rf.core.website.constants.TestConstantsRFL;
import com.rf.core.website.constants.dbQueries.DBQueries_RFL;
import com.rf.pages.website.brandRefresh.dsv.DSVStorefrontBrandRefreshHomePage;
import com.rf.pages.website.brandRefresh.dsv.DSVStorefrontBrandRefreshPcPerksStatusPage;
import com.rf.pages.website.brandRefresh.dsv.DSVStorefrontBrandRefreshPulsePage;
import com.rf.pages.website.storeFrontBrandRefresh.StoreFrontBrandRefreshHomePage;
import com.rf.test.website.RFDSVStoreFrontBrandRefreshWebsiteBaseTest;

public class StoreFrontBrandRefreshDSVTest extends RFDSVStoreFrontBrandRefreshWebsiteBaseTest{
	private static final Logger logger = LogManager
			.getLogger(StoreFrontBrandRefreshDSVTest.class.getName());
	private DSVStorefrontBrandRefreshHomePage dsvStorefrontBrandRefreshHomePage;
	private DSVStorefrontBrandRefreshPulsePage dsvStorefrontBrandRefreshPulsePage;
	private DSVStorefrontBrandRefreshPcPerksStatusPage dsvStorefrontBrandrefreshPcPerksStatusPage;

	public StoreFrontBrandRefreshDSVTest() {
		dsvStorefrontBrandRefreshHomePage = new DSVStorefrontBrandRefreshHomePage(driver);
	}

	//Verify Product category sections
	@Test(groups = { "nonLogin" },priority=1)
	public void testVerifyProductCategorySection() throws MalformedURLException{
		//logout();
		//For REDEFINE
		String regimen = TestConstantsRFL.REGIMEN_NAME_REDEFINE;
		dsvStorefrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(regimen.toLowerCase()), "Expected regimen name is "+regimen.toLowerCase()+" Actual on UI is "+dsvStorefrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isAddToBagBtnPresent(), "Products are not getting displayed for regimen "+regimen);
		//For REVERSE
		regimen = TestConstantsRFL.REGIMEN_NAME_REVERSE;
		dsvStorefrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(regimen.toLowerCase()), "Expected regimen name is "+regimen.toLowerCase()+" Actual on UI is "+dsvStorefrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isAddToBagBtnPresent(), "Products are not getting displayed for regimen "+regimen);
		//For UNBLEMISH

		regimen = TestConstantsRFL.REGIMEN_NAME_UNBLEMISH;
		dsvStorefrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(regimen.toLowerCase()), "Expected regimen name is "+regimen.toLowerCase()+" Actual on UI is "+dsvStorefrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isAddToBagBtnPresent(), "Products are not getting displayed for regimen "+regimen);
		//For SOOTHE

		regimen = TestConstantsRFL.REGIMEN_NAME_SOOTHE;
		dsvStorefrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(regimen.toLowerCase()), "Expected regimen name is "+regimen.toLowerCase()+" Actual on UI is "+dsvStorefrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isAddToBagBtnPresent(), "Products are not getting displayed for regimen "+regimen);
		//For PROMOTIONS

		regimen = TestConstantsRFL.REGIMEN_NAME_PROMOTIONS;
		dsvStorefrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(TestConstantsRFL.REGIMEN_NAME_FEATURED);
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(regimen.toLowerCase()), "Expected regimen name is "+regimen.toLowerCase()+" Actual on UI is "+dsvStorefrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isAddToBagBtnPresent(), "Products are not getting displayed for regimen "+regimen);
		//For ENHANCEMENTS

		regimen = TestConstantsRFL.REGIMEN_NAME_ENHANCEMENTS;
		dsvStorefrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(regimen.toLowerCase()), "Expected regimen name is "+regimen.toLowerCase()+" Actual on UI is "+dsvStorefrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isAddToBagBtnPresent(), "Products are not getting displayed for regimen "+regimen);
		//For ESSENTIALS

		regimen = TestConstantsRFL.REGIMEN_NAME_ESSENTIALS;
		dsvStorefrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(regimen.toLowerCase()), "Expected regimen name is "+regimen.toLowerCase()+" Actual on UI is "+dsvStorefrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isAddToBagBtnPresent(), "Products are not getting displayed for regimen "+regimen);
		s_assert.assertAll();
	}

	//User Account login As Consultant
	@Test(groups = { "consultant" },priority=2)
	public void testLoginAsConsultant(){
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"consultant is not logged in successfully");
		s_assert.assertAll();
	}

	//Verify Check my pulse Auto login
	@Test(groups = { "consultant" },priority=3)
	public void testVerifyCheckMyPulseAutoLogin(){
		dsvStorefrontBrandRefreshPulsePage = dsvStorefrontBrandRefreshHomePage.clickCheckMyPulse();
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isUserAutoLoginInPulse(), "User is not auto logged in for pulse");
		s_assert.assertAll();
	}

	//Verify My Account section within Pulse
	@Test(groups = { "consultant" },priority=4)
	public void testVerifyMyAccountSectionWithInPulse(){
		String mainAddress = "Main Address";
		String email = "Email";
		String phone = "Phone";
		String enrollmentDate = "Enrollment Date";
		String nextRenewalDate = " Next Renewal Date";
		dsvStorefrontBrandRefreshPulsePage = dsvStorefrontBrandRefreshHomePage.clickCheckMyPulse();
		dsvStorefrontBrandRefreshPulsePage.clickMyAccountTab();
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isMainAddressPresent(mainAddress), "Main address is not present in pulse");
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isMainAddressPresent(email), "email address is not present in pulse");
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isMainAddressPresent(phone), "phone is not present in pulse");
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isMainAddressPresent(enrollmentDate), "enrollment date is not present in pulse");
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isMainAddressPresent(nextRenewalDate), "next renewal date is not present in pulse");
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isDefaultShippingAddressPresent(), "default shipping addressis not present in pulse");
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isDefaultBillingAddressPresent(), "default billing addressis not present in pulse");
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isUserCRPEnrolled(), "User is not enrolled in CRP");
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isUserPulseEnrolled(), "User is not enrolled in pulse");
		s_assert.assertAll();
	}

	//Add/Edit/Delete Shipping Profile within Pulse My Account
	@Test(groups = { "consultant" },priority=5)
	public void testAddEditDeleteShippingProfileWithInPulseMyAccount(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String addressName = TestConstantsRFL.DSV_FIRST_NAME+" "+TestConstantsRFL.DSV_LAST_NAME+randomNumber;
		String attentionName = TestConstantsRFL.DSV_FIRST_NAME+" "+TestConstantsRFL.DSV_LAST_NAME;
		String address = TestConstantsRFL.DSV_ADDRESS_LINE_1;
		String postalCode = TestConstantsRFL.DSV_POSTAL_CODE;
		String phoneNumber = TestConstantsRFL.DSV_PHONE_NUMBER;
		dsvStorefrontBrandRefreshPulsePage = dsvStorefrontBrandRefreshHomePage.clickCheckMyPulse();
		dsvStorefrontBrandRefreshPulsePage.clickMyAccountTab();
		dsvStorefrontBrandRefreshPulsePage.clickAddNewShippingProfileLink();
		dsvStorefrontBrandRefreshPulsePage.enterShippingDetails(addressName, attentionName, address, postalCode, phoneNumber);
		dsvStorefrontBrandRefreshPulsePage.clickSaveProfileChanges();
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isProfilePresent(TestConstantsRFL.DSV_LAST_NAME+randomNumber), "Newly added shipping profile is not present");
		// Edit shipping profile
		dsvStorefrontBrandRefreshPulsePage.clickProfileName(TestConstantsRFL.DSV_LAST_NAME+randomNumber);
		randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		addressName = TestConstantsRFL.DSV_FIRST_NAME+" "+TestConstantsRFL.DSV_LAST_NAME+randomNumber;
		dsvStorefrontBrandRefreshPulsePage.enterShippingDetails(addressName, attentionName, address, postalCode, phoneNumber);
		dsvStorefrontBrandRefreshPulsePage.clickSaveProfileChanges();
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isProfilePresent(TestConstantsRFL.DSV_LAST_NAME+randomNumber), "edited shipping profile is not present");
		//delete shipping profile
		dsvStorefrontBrandRefreshPulsePage.clickProfileName(TestConstantsRFL.DSV_LAST_NAME+randomNumber);
		dsvStorefrontBrandRefreshPulsePage.deleteProfile();
		s_assert.assertFalse(dsvStorefrontBrandRefreshPulsePage.isProfilePresent(TestConstantsRFL.DSV_LAST_NAME+randomNumber), "Shipping profile is not delete successfully");
		s_assert.assertAll();
	}

	//Add/Edit/Delete Billing Profile within Pulse My Account
	@Test(groups = { "consultant" },priority=6)
	public void testAddEditDeleteBillingProfile(){
		int randomNum = CommonUtils.getRandomNum(1000, 99999);
		int randomNumber = CommonUtils.getRandomNum(1000, 99999);
		String fName = TestConstantsRFL.DSV_BILLING_PROFILE_NAME+randomNum; 
		String editedFName = TestConstantsRFL.DSV_BILLING_PROFILE_NAME+randomNumber; 
		dsvStorefrontBrandRefreshPulsePage=dsvStorefrontBrandRefreshHomePage.clickCheckMyPulse();
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isUserAutoLoginInPulse(),"Pulse autologged page not present");
		dsvStorefrontBrandRefreshPulsePage.clickMyAccountTab();
		dsvStorefrontBrandRefreshPulsePage.clickAddNewBillingProfile();
		dsvStorefrontBrandRefreshPulsePage.enterBillingDetails(fName,TestConstantsRFL.DSV_CARD_NUMBER,TestConstantsRFL.DSV_NAME_ON_CARD,TestConstantsRFL.DSV_EXPIRY_MONTH_NUMBER,TestConstantsRFL.DSV_EXPIRY_YEAR_NUMBER,TestConstantsRFL.DSV_SECURITY_CODE,TestConstantsRFL.DSV_BILLING_ADDRESS_LINE1,TestConstantsRFL.DSV_POSTAL_CODE,TestConstantsRFL.DSV_PHONE_NUMBER);
		dsvStorefrontBrandRefreshPulsePage.clickSaveProfileChanges();
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isBillingProfilePresentOnPage(fName),"Newly created billing profile is not present on page");
		//Edit the billing profile
		dsvStorefrontBrandRefreshPulsePage.clickEditBillingProfileLink(fName);
		dsvStorefrontBrandRefreshPulsePage.enterBillingProfileName(editedFName);
		dsvStorefrontBrandRefreshPulsePage.clickSaveProfileChanges();
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isBillingProfilePresentOnPage(editedFName),"Edited billing profile is not present on page");
		//Delete the billing profile.
		dsvStorefrontBrandRefreshPulsePage.clickEditBillingProfileLink(editedFName);
		dsvStorefrontBrandRefreshPulsePage.deleteBillingProfile();
		s_assert.assertFalse(dsvStorefrontBrandRefreshPulsePage.isBillingProfilePresentOnPage(editedFName),"Deleted billing profile is present on page");
		s_assert.assertAll();
	}

	//Edit Account info within Pulse My Account
	@Test(groups = { "consultant" },priority=7)
	public void testEditMainAccountInfo(){
		dsvStorefrontBrandRefreshPulsePage=dsvStorefrontBrandRefreshHomePage.clickCheckMyPulse();
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isUserAutoLoginInPulse(),"Pulse autologged page not present");
		dsvStorefrontBrandRefreshPulsePage.clickMyAccountTab();
		dsvStorefrontBrandRefreshPulsePage.editMainAccountInfo();
		dsvStorefrontBrandRefreshPulsePage.enterAddressLineForAccountInfo(TestConstantsRFL.DSV_ADDRESS_LINE_1);
		dsvStorefrontBrandRefreshPulsePage.enterPostalCodeForAccountInfo(TestConstantsRFL.DSV_POSTAL_CODE);
		dsvStorefrontBrandRefreshPulsePage.enterPhoneNumberForAccountInfo(TestConstantsRFL.DSV_PHONE_NUMBER);
		dsvStorefrontBrandRefreshPulsePage.clickSaveProfileChanges();
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isAddressLineForAccountInfoPresent(TestConstantsRFL.DSV_ADDRESS_LINE_1),"Updated Address line for main Account Info not present");
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isPostalCodeForAccountInfoPresent(TestConstantsRFL.DSV_POSTAL_CODE),"Updated Postal code for main Account Info not present");
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isPhoneNumberForAccountInfoPresent(TestConstantsRFL.DSV_PHONE_NUMBER),"Updated Phone Number for main Account Info not present");
		s_assert.assertAll();
	}

	//Verify Autoship templates within Pulse My Account
	@Test(groups = { "consultant" },priority=8)
	public void testVerifyCRPAndPulseAutoshipTemplate(){
		dsvStorefrontBrandRefreshPulsePage=dsvStorefrontBrandRefreshHomePage.clickCheckMyPulse();
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isUserAutoLoginInPulse(),"Pulse autologged page not present");
		dsvStorefrontBrandRefreshPulsePage.clickMyAccountTab();
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isUserCRPEnrolled(),"CRP Autoship section not present for user");
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isUserPulseEnrolled(),"Pulse section not present for user");
		s_assert.assertAll();
	}

	//Edit CRP within Pulse My Account
	@Test(groups = { "consultant" },priority=9)
	public void testEditCRPWithinPulseMyAccount(){
		String productName=null;
		String quantity=null;
		String quantityFromCRPCart=null;
		int productPosition=0;
		int productCount=0;
		boolean flag=false;
		String parentWindowHandle = driver.getWindowHandle();
		dsvStorefrontBrandRefreshPulsePage=dsvStorefrontBrandRefreshHomePage.clickCheckMyPulse();
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isUserAutoLoginInPulse(),"Pulse is not Auto logged in");
		dsvStorefrontBrandRefreshPulsePage.clickMyAccountTab();
		dsvStorefrontBrandRefreshPulsePage.clickEditCRP();
		dsvStorefrontBrandRefreshPulsePage.clickEditReplenishmentOrder();
		productName=dsvStorefrontBrandRefreshPulsePage.getProductName(1);
		dsvStorefrontBrandRefreshPulsePage.clickUpdateOrder();
		flag=dsvStorefrontBrandRefreshPulsePage.isProductPresentOnCRPOverViewPage(productName);

		if(flag){
			productPosition=dsvStorefrontBrandRefreshPulsePage.getPositionOfProductInCRPOverViewPage(productName);
			quantityFromCRPCart=dsvStorefrontBrandRefreshPulsePage.getProductQuantity(productPosition);
			dsvStorefrontBrandRefreshPulsePage.clickEditReplenishmentOrder();
			quantity=dsvStorefrontBrandRefreshPulsePage.getProductQuantityFromProductPage(1);
			productName=dsvStorefrontBrandRefreshPulsePage.getProductName(1);
			dsvStorefrontBrandRefreshPulsePage.addProductToBag(1);
			dsvStorefrontBrandRefreshPulsePage.clickUpdateOrder();
			s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.getProductQuantity(productPosition).contains(quantity+1),"Expected Product is not added to cart");
		}
		else{
			dsvStorefrontBrandRefreshPulsePage.clickEditReplenishmentOrder();
			quantity=dsvStorefrontBrandRefreshPulsePage.getProductQuantityFromProductPage(1);
			productName=dsvStorefrontBrandRefreshPulsePage.getProductName(1);
			dsvStorefrontBrandRefreshPulsePage.addProductToBag(1);
			dsvStorefrontBrandRefreshPulsePage.clickUpdateOrder();
			productPosition=dsvStorefrontBrandRefreshPulsePage.getPositionOfProductInCRPOverViewPage(productName);
			s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.getProductQuantity(productPosition).contains(quantity),"Product is not added to CRP cart");
		}

		productPosition=dsvStorefrontBrandRefreshPulsePage.getPositionOfProductInCRPOverViewPage(productName);
		dsvStorefrontBrandRefreshPulsePage.clickEditReplenishmentOrder();

		while (true) {
			productCount = dsvStorefrontBrandRefreshPulsePage.getProductCount();
			if (productCount <=1) {
				productName=dsvStorefrontBrandRefreshPulsePage.getProductName(3);
				dsvStorefrontBrandRefreshPulsePage.addProductToBag(3);
				continue;
			}
			else{
				break;
			}
		}

		productPosition = dsvStorefrontBrandRefreshPulsePage.getPositionOfProductFromCRPProductPage(productName);
		dsvStorefrontBrandRefreshPulsePage.removeProductFromAutoshipCart(productPosition);
		dsvStorefrontBrandRefreshPulsePage.clickUpdateOrder();
		s_assert.assertFalse(dsvStorefrontBrandRefreshPulsePage.isProductPresentOnCRPOverViewPage(productName),"Expected Product "+productName+" still present at CRP Overview Page");
		dsvStorefrontBrandRefreshHomePage.switchToWindow(parentWindowHandle);
		s_assert.assertAll();
	}

	//Verify Orders within Pulse My Account
	@Test(groups = { "consultant" },priority=10)
	public void testVerifyOrdersWithinPulseMyAccount(){
		dsvStorefrontBrandRefreshPulsePage=dsvStorefrontBrandRefreshHomePage.clickCheckMyPulse();
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isUserAutoLoginInPulse(),"Pulse is not Auto logged in");
		dsvStorefrontBrandRefreshPulsePage.clickOrdersTab();
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isOrdersPresentOnOrdersDetailsPage(),"Orders Not Present in Orders History");
		dsvStorefrontBrandRefreshPulsePage.clickViewOrderDetails();
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isShippingDetailsPresentOnOrderDetailsPage(),"Shipping Details not present on Order details page");
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isBillingDetailsPresentOnOrderDetailsPage(),"Billing Details not present on Order details page");
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isProductNamePresentOnOrderDetailsPage(),"Product Name is not Present on Order details Page");
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isOrderTotalPresentOnOrderDetailsPage(),"Order Total Not Present on Order Details Page");
		dsvStorefrontBrandRefreshPulsePage.clickCrossIconOfViewOrderDetailsPopup();
		s_assert.assertAll();  
	}

	//Verify PWS links from Pulse
	@Test(groups = { "consultant" },priority=11)
	public void testVerifyConsultantBizComPWSLink(){
		String parentWindowHandle=null;
		dsvStorefrontBrandRefreshPulsePage=dsvStorefrontBrandRefreshHomePage.clickCheckMyPulse();
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isUserAutoLoginInPulse(),"Pulse auto logged page not present");
		parentWindowHandle=dsvStorefrontBrandRefreshPulsePage.getParentWindowHandle();
		dsvStorefrontBrandRefreshPulsePage.clickComPWSLinkFromUserLinks();
		dsvStorefrontBrandRefreshPulsePage.switchToChildWindow();
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isCurrentUrlContains("myrandf.com"),"User is not on .COM PWS site");
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isSponsorInfoPresentOnPage(),"Sponsor information not present on .com PWS");
		dsvStorefrontBrandRefreshPulsePage.closeChildAndSwitchToParentWindow(parentWindowHandle);
		dsvStorefrontBrandRefreshPulsePage.clickBizPWSLinkFromUserLinks();
		dsvStorefrontBrandRefreshPulsePage.switchToChildWindow();
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isCurrentUrlContains("myrandf.biz"),"User is not on .BIZ PWS site");
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isSponsorInfoPresentOnPage(),"Sponsor information not present on .Biz PWS");
		dsvStorefrontBrandRefreshPulsePage.closeChildAndSwitchToParentWindow(parentWindowHandle);
		s_assert.assertAll();
	}

	//Variant Product Add to Ad-hoc and Autoship cart From Category Page for Consultant 
	@Test(groups = { "consultant" },priority=12)
	public void testVariantProductFromCategoryForConsultant(){
		String parentWindowHandle=null;
		String productName = TestConstantsRFL.DSV_VARIANT_PRODUCT_NAME;
		String fullProductName = TestConstantsRFL.DSV_VARIANT_PRODUCT_FULL_NAME;
		String shade = "Medium";
		int productPosition=0;
		boolean flag=false;
		//For ENHANCEMENTS
		String regimen = TestConstantsRFL.REGIMEN_NAME_ENHANCEMENTS;
		dsvStorefrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(regimen.toLowerCase()), "Expected regimen name is "+regimen.toLowerCase()+" Actual on UI is "+dsvStorefrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isAddToBagBtnPresent(), "Products are not getting displayed for regimen "+regimen);
		dsvStorefrontBrandRefreshHomePage.selectVariantAndAddProductToAdHocCart(productName,shade);
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isVariantProductAvailableInCart(productName),"Variant Product is not available on Ad-hoc cart page");
		//assert for autoship
		dsvStorefrontBrandRefreshPulsePage=dsvStorefrontBrandRefreshHomePage.clickCheckMyPulse();
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isUserAutoLoginInPulse(),"Pulse auto logged page not present");
		parentWindowHandle=dsvStorefrontBrandRefreshPulsePage.getParentWindowHandle();
		dsvStorefrontBrandRefreshPulsePage.clickMyAccountTab();
		dsvStorefrontBrandRefreshPulsePage.clickEditCRP();
		flag=dsvStorefrontBrandRefreshPulsePage.isProductPresentOnCRPOverViewPage(fullProductName);
		if(flag){
			productPosition=dsvStorefrontBrandRefreshPulsePage.getPositionOfProductInCRPOverViewPage(fullProductName);
			dsvStorefrontBrandRefreshPulsePage.clickEditReplenishmentOrder();
			dsvStorefrontBrandRefreshPulsePage.removeProductFromAutoshipCart(productPosition);
			dsvStorefrontBrandRefreshPulsePage.clickUpdateOrder();
		}
		s_assert.assertFalse(dsvStorefrontBrandRefreshPulsePage.isProductPresentOnCRPOverViewPage(fullProductName),"Expected Product "+fullProductName+" still present at CRP Overview Page");
		dsvStorefrontBrandRefreshPulsePage.clickEditReplenishmentOrder();
		dsvStorefrontBrandRefreshPulsePage.clickLinksInHeader(regimen);
		dsvStorefrontBrandRefreshPulsePage.selectVariantAndAddProductToAutoshipCart(fullProductName, shade);
		dsvStorefrontBrandRefreshPulsePage.clickUpdateOrder();
		s_assert.assertTrue(dsvStorefrontBrandRefreshPulsePage.isProductPresentOnCRPOverViewPage(fullProductName),"Expected Product "+fullProductName+" is not present at CRP Overview Page");
		dsvStorefrontBrandRefreshPulsePage.closeChildAndSwitchToParentWindow(parentWindowHandle);
		s_assert.assertAll();
	}

	//User Account Login as PC
	@Test(groups = { "pc" },priority=13)
	public void testLoginAsPC(){
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"PC is not logged in successfully");
		s_assert.assertAll();
	}

	//Edit pc perks
	@Test(groups = { "pc" },priority=14)
	public void testEditPCPerks(){
		double totalBeforeAdd = 0.00;
		double totalAfterAdd = 0.00;
		double totalAfterRemove = 0.00;
		String productName = null;
		String productNameInAutoshipCart = null;
		String itemCount = null;
		dsvStorefrontBrandRefreshHomePage.clickMyAccount();
		dsvStorefrontBrandRefreshHomePage.clickEditOrderLink();
		dsvStorefrontBrandRefreshHomePage.clickEditOrderBtn();
		dsvStorefrontBrandRefreshHomePage.removeAllProductFromAutoshipCart();

		totalBeforeAdd = dsvStorefrontBrandRefreshHomePage.getAutoshipOrderTotal();
		productName = dsvStorefrontBrandRefreshHomePage.addProductToBagForPCTillUpdateOrderBtnIsEnabled(1);
		dsvStorefrontBrandRefreshHomePage.clickUpdateOrderBtnAndAcceptConfirmationOnAlert();
		productNameInAutoshipCart = dsvStorefrontBrandRefreshHomePage.getFirstProductNamePresentIntoAutoshipCart();
		s_assert.assertTrue(productNameInAutoshipCart.contains(productName),"Product "+productName+" is not present in autoship cart");
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.getOrderUpdationMessage().contains("successfully updated") ,"Order item is not updated successfully while adding product");
		dsvStorefrontBrandRefreshHomePage.refreshPage();
		totalAfterAdd = dsvStorefrontBrandRefreshHomePage.getAutoshipOrderTotal();
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.compareTotal(totalBeforeAdd, totalAfterAdd), "Product is not added, expected total after add is"+totalAfterAdd+" before add is "+totalBeforeAdd);
		dsvStorefrontBrandRefreshHomePage.removeFirstProductFromAutoshipCart();
		//Remove product
		dsvStorefrontBrandRefreshHomePage.clickUpdateOrderBtnAndAcceptConfirmationOnAlert();
		//s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.getOrderUpdationMessage().contains("successfully updated") ,"Order item is not updated successfully while removing product");
		totalAfterRemove = dsvStorefrontBrandRefreshHomePage.getAutoshipOrderTotal();
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.compareTotal(totalAfterRemove,totalAfterAdd), "Product is not removed, expected total after remove is"+totalAfterRemove+" before add is "+totalAfterAdd);
		itemCount = dsvStorefrontBrandRefreshHomePage.getItemCount();
		s_assert.assertTrue(itemCount.equalsIgnoreCase("0"), "Prduct is not removed successfully from autoship cart");
		s_assert.assertAll();
	}

	//Add/Delete Shipping profile
	@Test(groups = { "pc" },priority=15)
	public void testAddShippingThroughMyAccountForPC(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String addressName = "Home";
		String shippingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME+randomNumber;
		String shippingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME;
		String addressLine1 =  TestConstantsRFL.DSV_ADDRESS_LINE_1;
		String postalCode = TestConstantsRFL.DSV_POSTAL_CODE;
		String phnNumber = TestConstantsRFL.NEW_ADDRESS_PHONE_NUMBER_US;
		dsvStorefrontBrandRefreshHomePage.clickMyAccount();
		dsvStorefrontBrandRefreshHomePage.clickEditOrderLink();
		dsvStorefrontBrandRefreshHomePage.clickChangeLinkUnderShippingToOnPWS();
		dsvStorefrontBrandRefreshHomePage.enterShippingProfileDetailsForPWS(addressName, shippingProfileFirstName, shippingProfileLastName, addressLine1, postalCode, phnNumber);
		dsvStorefrontBrandRefreshHomePage.clickUseThisAddressShippingInformationBtn();
		dsvStorefrontBrandRefreshHomePage.acceptQASPopup();
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.getShippingAddressName().contains(shippingProfileFirstName),"Shipping address name expected is "+shippingProfileFirstName+" While on UI is "+dsvStorefrontBrandRefreshHomePage.getShippingAddressName());
		dsvStorefrontBrandRefreshHomePage.clickChangeLinkUnderShippingToOnPWS();
		dsvStorefrontBrandRefreshHomePage.selectDeleteForProfileAndAcceptConfirmationAlert(shippingProfileFirstName);
		s_assert.assertFalse(dsvStorefrontBrandRefreshHomePage.isProfilePresentInSavedProfiles(shippingProfileFirstName),"Shipping Profile "+shippingProfileFirstName+" is still present after Deletion");
		s_assert.assertAll();
	}

	//Add/Delete Billing profile
	@Test(groups = { "pc" },priority=16)
	public void testAddShippingBillingWhileAdhocCheckoutForPC(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.BILLING_PROFILE_FIRST_NAME+randomNumber;
		String billingProfileLastName = TestConstantsRFL.BILLING_PROFILE_LAST_NAME;
		String cardNumber = TestConstantsRFL.DSV_CARD_NUMBER;
		String nameOnCard = TestConstantsRFL.FIRST_NAME;
		String expMonth = TestConstantsRFL.DSV_EXPIRY_MONTH_NUMBER;
		String expYear = TestConstantsRFL.DSV_EXPIRY_YEAR_NUMBER;
		String CVV = TestConstantsRFL.DSV_SECURITY_CODE;
		String addressLine1 =  TestConstantsRFL.DSV_BILLING_ADDRESS_LINE1;
		String postalCode = TestConstantsRFL.DSV_POSTAL_CODE;
		String phnNumber = TestConstantsRFL.DSV_PHONE_NUMBER;
		dsvStorefrontBrandRefreshHomePage.clickMyAccount();
		dsvStorefrontBrandRefreshHomePage.clickEditOrderLink();
		dsvStorefrontBrandRefreshHomePage.clickChangeBillingInformationLinkUnderBillingTabOnPWS();
		dsvStorefrontBrandRefreshHomePage.enterBillingInfoForPWS(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		dsvStorefrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		dsvStorefrontBrandRefreshHomePage.acceptQASPopup();
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.getBillingAddressName().contains(billingProfileFirstName), "Expected billing profile name is: "+billingProfileFirstName+" Actual on UI is: "+dsvStorefrontBrandRefreshHomePage.getBillingAddressName());
		dsvStorefrontBrandRefreshHomePage.clickChangeBillingInformationLinkUnderBillingTabOnPWS();
		dsvStorefrontBrandRefreshHomePage.selectDeleteForProfileAndAcceptConfirmationAlert(billingProfileFirstName);
		s_assert.assertFalse(dsvStorefrontBrandRefreshHomePage.isProfilePresentInSavedProfiles(billingProfileFirstName),"Billing Profile "+billingProfileFirstName+" is still present after Deletion");
		s_assert.assertAll();
	}

	//Verify Order History
	//View Order details
	@Test(groups = { "pc" },priority=17)
	public void testVerifyOrderHistoryAndViewOrderdetailsForPC(){
		dsvStorefrontBrandRefreshHomePage.clickMyAccount();
		dsvStorefrontBrandRefreshHomePage.clickOrderHistory();
		//Verify order history section
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isOrderNumberPresentOnOrderHistory(), "Order Number not present on order history page");
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isOrderDatePresentOnOrderHistory(), "Order Date not present on order history page");
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isOrderItemPresentOnOrderHistory(), "Order Items not present on order history page");
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isOrderGrandTotalPresentOnOrderHistory(), "Order Grand total not present on order history page");
		dsvStorefrontBrandRefreshHomePage.clickViewDetails();
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isShippingAdressPresentOnOrderDetails(), "Shipping Address is not present on order details");
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isBillingAdressPresentOnOrderDetails(), "Billing Address is not present on order details");
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isProductNamePresentOnOrderDetails(), "Product name  is not present on order details");
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isGrandTotalPresentOnOrderDetails(), "Grand total is not present on order details");
		s_assert.assertAll();
	}

	// Verify PC perks status
	@Test(groups = { "pc" },priority=18)
	public void testVerifyPcPerksStatus() {
		dsvStorefrontBrandRefreshHomePage.clickMyAccount();
		dsvStorefrontBrandrefreshPcPerksStatusPage =  dsvStorefrontBrandRefreshHomePage.clickChangePcPerksStatus();
		s_assert.assertTrue(dsvStorefrontBrandrefreshPcPerksStatusPage.isVerifyPcPerksStatusActive(), " Pc perks Status is not Active");
		s_assert.assertTrue(dsvStorefrontBrandrefreshPcPerksStatusPage.isVerifyNextBillShipDate(), " Next bill and ship date is not comming on pc perks Status");
		s_assert.assertAll();
	}

	//Variant Product Add to Ad-hoc and Autoship cart From Category Page for PC 
	@Test(groups = { "pc" },priority=19)
	public void testVariantProductFromCategoryForPC(){
		String productName = TestConstantsRFL.DSV_VARIANT_PRODUCT_NAME;
		String fullProductName = TestConstantsRFL.DSV_VARIANT_PRODUCT_FULL_NAME;
		String shade = "Medium";
		int productPosition=0;
		boolean flag=false;
		//For ENHANCEMENTS
		String regimen = TestConstantsRFL.REGIMEN_NAME_ENHANCEMENTS;
		dsvStorefrontBrandRefreshHomePage.mouseHoverShopSkinCareAndClickLink(regimen);
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.getCurrentURL().toLowerCase().contains(regimen.toLowerCase()), "Expected regimen name is "+regimen.toLowerCase()+" Actual on UI is "+dsvStorefrontBrandRefreshHomePage.getCurrentURL().toLowerCase());
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isAddToBagBtnPresent(), "Products are not getting displayed for regimen "+regimen);
		dsvStorefrontBrandRefreshHomePage.selectVariantAndAddProductToAdHocCart(productName,shade);
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isVariantProductAvailableInCart(productName),"Variant Product is not available on Ad-hoc cart page");
		dsvStorefrontBrandRefreshHomePage.clickMyAccount();
		dsvStorefrontBrandRefreshHomePage.clickEditOrderLink();
		dsvStorefrontBrandRefreshHomePage.clickEditOrderBtn();
		flag=dsvStorefrontBrandRefreshHomePage.isProductPresentOnPCAutoshipOverViewPage(fullProductName);
		if(flag){
			productPosition=dsvStorefrontBrandRefreshHomePage.getPositionOfProductInPCOverViewPage(fullProductName);
			dsvStorefrontBrandRefreshHomePage.removeProductFromAutoshipCart(productPosition);
			dsvStorefrontBrandRefreshHomePage.clickUpdateOrderBtnAndAcceptConfirmationOnAlert();
			s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.getOrderUpdationMessage().contains("successfully updated") ,"Order item is not updated successfully while removing product");
			dsvStorefrontBrandRefreshHomePage.refreshPage();
		}
		s_assert.assertFalse(dsvStorefrontBrandRefreshHomePage.isProductPresentOnPCAutoshipOverViewPage(fullProductName),"Expected Product "+fullProductName+" still present at PC autoship Overview Page");
		dsvStorefrontBrandRefreshHomePage.clickLinksInHeader(regimen);
		dsvStorefrontBrandRefreshHomePage.selectVariantAndAddProductToAutoshipCart(fullProductName, shade);
		dsvStorefrontBrandRefreshHomePage.clickUpdateOrderBtnAndAcceptConfirmationOnAlert();
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.getOrderUpdationMessage().contains("successfully updated") ,"Order item is not updated successfully while adding product");
		dsvStorefrontBrandRefreshHomePage.refreshPage();
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isProductPresentOnPCAutoshipOverViewPage(fullProductName),"Expected Product "+fullProductName+" is not present at PC autoship Overview Page");
		s_assert.assertAll();
	}


	//User Account Login as RC
	@Test(groups = { "rc" },priority=20)
	public void testLoginAsRC(){
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.verifyUserSuccessfullyLoggedIn(),"RC is not logged in successfully");
		s_assert.assertAll();
	}

	//Add/Delete Shipping profile
	@Test(enabled=false) //(groups = { "rc" },priority=21)
	public void testAddShippingThroughMyAccountForRC(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String postalCode = TestConstantsRFL.DSV_POSTAL_CODE;
		String phnNumber = TestConstantsRFL.NEW_ADDRESS_PHONE_NUMBER_US;
		String addressLine1 =  TestConstantsRFL.DSV_ADDRESS_LINE_1;
		String regimen = TestConstantsRFL.REGIMEN_NAME_REVERSE;
		String addressName = "Home";
		String shippingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME+randomNumber;
		String shippingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME;
		dsvStorefrontBrandRefreshHomePage.clickShopSkinCareHeader();
		dsvStorefrontBrandRefreshHomePage.selectRegimen(regimen);
		dsvStorefrontBrandRefreshHomePage.clickAddToCartBtn();
		dsvStorefrontBrandRefreshHomePage.clickCheckoutBtn();
		dsvStorefrontBrandRefreshHomePage.clickContinueWithoutConsultantLink();
		dsvStorefrontBrandRefreshHomePage.clickChangeShippingAddressBtn();
		dsvStorefrontBrandRefreshHomePage.enterShippingProfileDetailsForPWS(addressName, shippingProfileFirstName, shippingProfileLastName, addressLine1, postalCode, phnNumber);
		dsvStorefrontBrandRefreshHomePage.clickUseThisAddressShippingInformationBtn();
		dsvStorefrontBrandRefreshHomePage.acceptQASPopup();
		dsvStorefrontBrandRefreshHomePage.clickContinueOnBillingInfoPage();
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.getShippingAddressName().contains(shippingProfileFirstName),"Shipping address name expected is "+shippingProfileFirstName+" While on UI is "+dsvStorefrontBrandRefreshHomePage.getShippingAddressName());
		dsvStorefrontBrandRefreshHomePage.clickChangeLinkUnderShippingToOnPWS();
		dsvStorefrontBrandRefreshHomePage.selectDeleteForProfileAndAcceptConfirmationAlert(shippingProfileFirstName);
		s_assert.assertFalse(dsvStorefrontBrandRefreshHomePage.isProfilePresentInSavedProfiles(shippingProfileFirstName),"Shipping Profile "+shippingProfileFirstName+" is still present after Deletion");
		s_assert.assertAll();
	}

	//Add/Delete Billing profile
	@Test(enabled=false) //(groups = { "rc" },priority=22)
	public void testAddBillingThroughMyAccountForRC(){
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstantsRFL.FIRST_NAME;
		String postalCode = TestConstantsRFL.DSV_POSTAL_CODE;
		String cardNumber = TestConstantsRFL.CARD_NUMBER;
		String nameOnCard = firstName;
		String expMonth = TestConstantsRFL.EXP_MONTH;
		String expYear = TestConstantsRFL.EXP_YEAR;
		String phnNumber = TestConstantsRFL.NEW_ADDRESS_PHONE_NUMBER_US;
		String addressLine1 =  TestConstantsRFL.DSV_ADDRESS_LINE_1;
		String billingName =TestConstantsRFL.BILLING_PROFILE_NAME;
		String billingProfileFirstName = TestConstantsRFL.SHIPPING_PROFILE_FIRST_NAME;
		String billingProfileLastName = TestConstantsRFL.SHIPPING_PROFILE_LAST_NAME+randomNumber;
		String regimen = TestConstantsRFL.REGIMEN_NAME_REVERSE;
		String CVV = TestConstantsRFL.DSV_SECURITY_CODE;
		dsvStorefrontBrandRefreshHomePage.clickShopSkinCareHeader();
		dsvStorefrontBrandRefreshHomePage.selectRegimen(regimen);
		dsvStorefrontBrandRefreshHomePage.clickAddToCartBtn();
		dsvStorefrontBrandRefreshHomePage.clickCheckoutBtn();
		dsvStorefrontBrandRefreshHomePage.clickContinueWithoutConsultantLink();
		dsvStorefrontBrandRefreshHomePage.clickContinueOnShippingInfoPage();
		dsvStorefrontBrandRefreshHomePage.clickChangeBillingInformationBtn();
		dsvStorefrontBrandRefreshHomePage.enterBillingInfo(billingName, billingProfileFirstName, billingProfileLastName, nameOnCard, cardNumber, expMonth, expYear, addressLine1, postalCode, phnNumber,CVV);
		dsvStorefrontBrandRefreshHomePage.clickUseThisBillingInformationBtn();
		dsvStorefrontBrandRefreshHomePage.acceptQASPopup();
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.getBillingAddressName().contains(billingProfileFirstName), "Expected billing profile name is: "+billingProfileFirstName+" Actual on UI is: "+dsvStorefrontBrandRefreshHomePage.getBillingAddressName());
		dsvStorefrontBrandRefreshHomePage.clickChangeBillingInformationLinkUnderBillingTabOnPWS();
		dsvStorefrontBrandRefreshHomePage.selectDeleteForProfileAndAcceptConfirmationAlert(billingProfileLastName);
		s_assert.assertFalse(dsvStorefrontBrandRefreshHomePage.isProfilePresentInSavedProfiles(billingProfileLastName),"billing Profile "+billingProfileLastName+" is still present after Deletion");
		s_assert.assertAll();
	}

	//Verify Order History
	//View Order details
	@Test(groups = { "rc" },priority=23)
	public void testVerifyOrderHistoryAndViewOrderdetailsForRC(){
		dsvStorefrontBrandRefreshHomePage.clickMyAccount();
		dsvStorefrontBrandRefreshHomePage.clickOrderHistory();
		//Verify order history section
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isOrderNumberPresentOnOrderHistory(), "Order Number not present on order history page");
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isOrderDatePresentOnOrderHistory(), "Order Date not present on order history page");
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isOrderItemPresentOnOrderHistory(), "Order Items not present on order history page");
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isOrderGrandTotalPresentOnOrderHistory(), "Order Grand total not present on order history page");
		//Verify order details section
		dsvStorefrontBrandRefreshHomePage.clickViewDetails();
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isShippingAdressPresentOnOrderDetails(), "Shipping Address is not present on order details");
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isBillingAdressPresentOnOrderDetails(), "Billing Address is not present on order details");
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isProductNamePresentOnOrderDetails(), "Product name  is not present on order details");
		s_assert.assertTrue(dsvStorefrontBrandRefreshHomePage.isGrandTotalPresentOnOrderDetails(), "Grand total is not present on order details");
		s_assert.assertAll();
	}

}