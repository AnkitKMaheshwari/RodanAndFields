package com.rf.test.website.storeFront.dsv;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import com.rf.core.utils.CommonUtils;
import com.rf.pages.website.dsv.DSVStoreFrontAccountInfoPage;
import com.rf.pages.website.dsv.DSVStoreFrontAutoshipCartPage;
import com.rf.pages.website.dsv.DSVStoreFrontBillingInfoPage;
import com.rf.pages.website.dsv.DSVStoreFrontHomePage;
import com.rf.pages.website.dsv.DSVStoreFrontQuickShopPage;
import com.rf.pages.website.dsv.DSVStoreFrontShippingInfoPage;
import com.rf.test.website.RFDSVStoreFrontWebsiteBaseTest;

public class StorefrontDSVTest extends RFDSVStoreFrontWebsiteBaseTest{
	private static final Logger logger = LogManager
			.getLogger(StorefrontDSVTest.class.getName());

	//-------------------------------------------------Pages---------------------------------------------------------
	private DSVStoreFrontHomePage dsvStoreFrontHomePage;
	private DSVStoreFrontAutoshipCartPage dsvStoreFrontAutoshipCartPage;
	private DSVStoreFrontQuickShopPage dsvStoreFrontQuickShopPage;
	private DSVStoreFrontShippingInfoPage dsvStoreFrontShippingInfoPage;
	private DSVStoreFrontBillingInfoPage dsvStoreFrontBillingInfoPage;
	private DSVStoreFrontAccountInfoPage dsvStoreFrontAccountInfoPage;
	//-----------------------------------------------------------------------------------------------------------------

	private String variantProductName = "SPF 20 with Brush";
	private String variantProductFirstHalfName = "SPF 20";
	private String variantProductSecondHalfName = "with Brush";
	private String variantOption = "Light";
	private String variantProductCategory = "Enhancements";
	
	//Hybris Project-5314:User Account login As Consultant
	@Test(groups = { "consultant" },priority=1)
	public void testUserAccountLoginAsConsultant_5314(){
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "Consultant is not on PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.isUserNameDropDownPresent(), "Home page doesn't have the username dropdown");
		//s_assert.assertTrue(dsvStoreFrontHomePage.isCRPCartImagePresent(), "CRP Cart image is not present on home page");
		dsvStoreFrontHomePage.clickLogo();
		s_assert.assertAll();		
	}

	//Hybris Project-5315:Edit CRP Autoship Template
	@Test(groups = { "consultant" },priority=2)
	public void testEditCRPAutoshipTemplate_5315(){
		String quantityOfProduct = "10";
		String secondProductRetailPrice=null;
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "Consultant is not on PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.isUserNameDropDownPresent(), "Home page doesn't have the username dropdown");
		dsvStoreFrontAutoshipCartPage = dsvStoreFrontHomePage.clickOnCRPCartImg();  
		dsvStoreFrontQuickShopPage = dsvStoreFrontAutoshipCartPage.clickTopContinueShoppingLink();
		String firstProductRetailPrice = dsvStoreFrontQuickShopPage.getFirstProductRetailPrice();
		String firstProductName = dsvStoreFrontQuickShopPage.getFirstProductName();
		logger.info("Name of first product added to CRP is "+firstProductName);
		logger.info("Retail price of first product added to CRP is "+firstProductRetailPrice);
		dsvStoreFrontAutoshipCartPage = dsvStoreFrontQuickShopPage.clickAddToCRPForFirstProduct();
		//s_assert.assertTrue(dsvStoreFrontAutoshipCartPage.isProductPresentOnCart(firstProductRetailPrice), firstProductName+" is not added to the cart");
		dsvStoreFrontAutoshipCartPage.addQuantityOfProduct(firstProductRetailPrice, quantityOfProduct);
		dsvStoreFrontAutoshipCartPage.clickUpdateQuantityBtnOfProduct(firstProductRetailPrice);
		s_assert.assertTrue(dsvStoreFrontAutoshipCartPage.getQuantityOfProduct(firstProductRetailPrice).contains(quantityOfProduct), "Quantity of "+firstProductName+" expected is "+quantityOfProduct+"but on UI is "+dsvStoreFrontAutoshipCartPage.getQuantityOfProduct(firstProductRetailPrice));
		dsvStoreFrontAutoshipCartPage.clickRemoveProduct(firstProductRetailPrice);
		if(dsvStoreFrontAutoshipCartPage.isThresholdMessageAppeared()==true){
			dsvStoreFrontQuickShopPage = dsvStoreFrontAutoshipCartPage.clickTopContinueShoppingLink();
			secondProductRetailPrice = dsvStoreFrontQuickShopPage.getSecondProductRetailPrice();
			logger.info("Retail price of second product added to CRP is "+secondProductRetailPrice);
			dsvStoreFrontAutoshipCartPage = dsvStoreFrontQuickShopPage.clickAddToCRPForSecondProduct();
			dsvStoreFrontAutoshipCartPage.addQuantityOfProduct(secondProductRetailPrice, quantityOfProduct);
			dsvStoreFrontAutoshipCartPage.clickUpdateQuantityBtnOfProduct(secondProductRetailPrice);
			dsvStoreFrontAutoshipCartPage.clickRemoveProduct(firstProductRetailPrice);
			s_assert.assertFalse(dsvStoreFrontAutoshipCartPage.isProductPresentOnCart(firstProductRetailPrice), secondProductRetailPrice+" is not removed from the cart");
		}
		else{
			s_assert.assertFalse(dsvStoreFrontAutoshipCartPage.isProductPresentOnCart(firstProductRetailPrice), firstProductRetailPrice+" is not removed from the cart");	
		}
		dsvStoreFrontHomePage.clickLogo();
		s_assert.assertAll();
	}

	//Hybris Project-5316:Filtering on all Products Page for Consultant CRP Edit Template
	@Test(groups = { "consultant" },priority=3)
	public void testFilteringAllProductsOnEditCRPAutoshipTemplate_5316(){
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "Consultant is not on PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.isUserNameDropDownPresent(), "Home page doesn't have the username dropdown");
		//		dsvStoreFrontAutoshipCartPage = dsvStoreFrontHomePage.clickOnCRPCartImg();
		//		dsvStoreFrontQuickShopPage = dsvStoreFrontAutoshipCartPage.clickTopContinueShoppingLink();
		dsvStoreFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		dsvStoreFrontQuickShopPage = new DSVStoreFrontQuickShopPage(driver);
		dsvStoreFrontQuickShopPage.clickProductFilterDropDown();
		List<String> allProductsList = dsvStoreFrontQuickShopPage.getAllProductsFromProductFilterList();
		String selectedProduct = dsvStoreFrontQuickShopPage.selectAndReturnTheSelectedProductFromFilter();
		System.out.println("selected product is "+selectedProduct);
		s_assert.assertTrue(dsvStoreFrontQuickShopPage.isProductFilterApplied(selectedProduct),"Product filter is not applied on products");
		for(String nonSelectedProductName : allProductsList){			
			if(nonSelectedProductName.equalsIgnoreCase(selectedProduct)==false)
				s_assert.assertFalse(dsvStoreFrontQuickShopPage.isProductFilterApplied(nonSelectedProductName),nonSelectedProductName + "product is still on the page after after applying the filter for "+selectedProduct);	
		}
		String selectedPrice = dsvStoreFrontQuickShopPage.selectAndReturnTheSelectedPriceFromFilter();
		Double selectedPriceDoubleValue = Double.parseDouble(selectedPrice);
		System.out.println("maximum price of selected price range in double is "+selectedPriceDoubleValue);
		s_assert.assertTrue(selectedPriceDoubleValue>dsvStoreFrontQuickShopPage.getPriceOfRandomProductAfterPriceFilterApplied(), "Price filter is not applied on products");
		dsvStoreFrontQuickShopPage.clickClearAllLink();
		String selectedOrder = dsvStoreFrontQuickShopPage.selectAndReturnTheSelectedSortOrderFromFilter();
		s_assert.assertTrue(dsvStoreFrontQuickShopPage.isSortOrderApplied(selectedOrder),selectedOrder+" order is not applied");
		dsvStoreFrontHomePage.clickLogo();
		s_assert.assertAll();
	}


	//Variant Product Add to Ad-hoc and Autoship cart From Category Page for Consultant 
	@Test(groups = { "consultant" },priority=4)
	public void testVariantProductFromCategoryForConsultant(){
		if(driver.getCountry().equals("ca")){
			dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
			s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "Consultant is not on PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
			dsvStoreFrontHomePage.hoverOnShopLinkAndClickCategoryLink(variantProductCategory);
			s_assert.assertTrue(dsvStoreFrontHomePage.isAlertPresentForVariantProductsAdHoc(),"Alert not displayed while adding product to Ad-hoc cart without selecting a Variant");
			dsvStoreFrontHomePage.addProductToAdHocCart(variantProductName, variantOption);
			s_assert.assertTrue(dsvStoreFrontHomePage.isVariantProductAvailableInCart(variantProductFirstHalfName, variantProductSecondHalfName, variantOption),"Variant Product is not available on Ad-hoc cart page");
			dsvStoreFrontAutoshipCartPage = dsvStoreFrontHomePage.clickAutoshipMiniBag();
			if(dsvStoreFrontAutoshipCartPage.isVariantProductInAutoshipCart()==true){
				s_assert.assertFalse(dsvStoreFrontAutoshipCartPage.clickRemoveButtonForVarientProductAndVerify(), "Variant Product was not removed from cart");
			}
			dsvStoreFrontAutoshipCartPage.clickLogo();
			dsvStoreFrontHomePage.hoverOnShopLinkAndClickCategoryLink(variantProductCategory);
			s_assert.assertTrue(dsvStoreFrontAutoshipCartPage.isAlertPresentForVariantProductsAutoship(),"Alert not displayed while adding product to Autoship cart without selecting a Variant");
			dsvStoreFrontHomePage.addProductToAutoshipCart(variantProductName, variantOption);
			s_assert.assertTrue(dsvStoreFrontAutoshipCartPage.isVariantProductInAutoshipCart(), "Varient Product was not added to Autoship Cart");
			s_assert.assertFalse(dsvStoreFrontAutoshipCartPage.clickRemoveButtonForVarientProductAndVerify(), "Verient Product was not removed from cart");
			dsvStoreFrontAutoshipCartPage.clickLogo();
			s_assert.assertAll();
		}
	}

	//Variant Product Add to Ad-hoc and Autoship cart From Search Results for Consultant 
	@Test(groups = { "consultant" },priority=5)
	public void testVariantProductFromSearchResultsForConsultant(){
		if(driver.getCountry().equals("ca")){
			dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
			s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "Consultant is not on PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
			dsvStoreFrontHomePage.searchVariantProductFromHeaderSearch();
			s_assert.assertTrue(dsvStoreFrontHomePage.isAlertPresentForVariantProductsAdHoc(),"Alert not displayed while adding product to Ad-hoc cart without selecting a Variant");
			dsvStoreFrontHomePage.addProductToAdHocCart(variantProductName, variantOption);
			s_assert.assertTrue(dsvStoreFrontHomePage.isVariantProductAvailableInCart(variantProductFirstHalfName, variantProductSecondHalfName, variantOption),"Variant Product is not available on Ad-hoc cart page");
			dsvStoreFrontAutoshipCartPage = dsvStoreFrontHomePage.clickAutoshipMiniBag();
			if(dsvStoreFrontAutoshipCartPage.isVariantProductInAutoshipCart()==true){
				s_assert.assertFalse(dsvStoreFrontAutoshipCartPage.clickRemoveButtonForVarientProductAndVerify(), "Variant Product was not removed from cart");
			}
			dsvStoreFrontAutoshipCartPage.clickLogo();
			dsvStoreFrontHomePage.searchVariantProductFromHeaderSearch();
			s_assert.assertTrue(dsvStoreFrontAutoshipCartPage.isAlertPresentForVariantProductsAutoship(),"Alert not displayed while adding product to Autoship cart without selecting a Variant");
			dsvStoreFrontHomePage.addProductToAutoshipCart(variantProductName, variantOption);
			s_assert.assertTrue(dsvStoreFrontAutoshipCartPage.isVariantProductInAutoshipCart(), "Varient Product was not added to Autoship Cart");
			s_assert.assertFalse(dsvStoreFrontAutoshipCartPage.clickRemoveButtonForVarientProductAndVerify(), "Verient Product was not removed from cart");
			dsvStoreFrontAutoshipCartPage.clickLogo();
			s_assert.assertAll();
		}
	}

	//Hybris Project-5317 Updating Account info As Consultant
	@Test(groups = { "consultant" },priority=6)
	public void testAccountInfoUpdateAsConsultant_5317() throws Exception{
		int randomNum = CommonUtils.getRandomNum(5000, 9999);
		int randomDOB = CommonUtils.getRandomNum(1, 12);
		String fName = "RFTestC"+randomNum;
		String lName = "RFTestU"+randomNum;
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "Consultant is not on PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		//s_assert.assertTrue(dsvStoreFrontHomePage.getWelcomeText().contains("Welcome"), "Home page doesn't have the 'Welcome' link");
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontAccountInfoPage  = dsvStoreFrontHomePage.clickAccountInfoLinkFromWelcomeDropDown();  
		dsvStoreFrontAccountInfoPage.enterFirstNameOfUser(fName);
		dsvStoreFrontAccountInfoPage.enterLastNameOfUser(lName);
		dsvStoreFrontAccountInfoPage.enterPhoneNumber(editPhoneNumber+randomNum);
		dsvStoreFrontAccountInfoPage.selectDOBDate(randomDOB);
		dsvStoreFrontAccountInfoPage.selectDOBMonth(randomDOB);
		dsvStoreFrontAccountInfoPage.selectDOBYear(randomDOB);
		dsvStoreFrontAccountInfoPage.clickOnSaveButton();
		s_assert.assertTrue(dsvStoreFrontAccountInfoPage.isSuccessMessagePresentOnPage(), "Account information was not updated correctly");
		dsvStoreFrontHomePage.clickLogo();
		s_assert.assertAll();
	}

	//Hybris Project-5319:Adding new and Editing existing Billing Profile AS Consultant
	@Test(groups = { "consultant" },priority=7)
	public void testAddAndEditBillingProfileAsConsultant() throws Exception{
		int randomNum = CommonUtils.getRandomNum(1000, 9999);
		String fName = "RFAutoSF"; 
		String lName1 = String.valueOf(randomNum);
		String lName2 = lName1+" edit";
		String name1 = fName+" "+lName1;
		String name2 = fName+" "+lName2;
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "Consultant is not on PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		//s_assert.assertTrue(dsvStoreFrontHomePage.getWelcomeText().contains("Welcome"), "Home page doesn't have the 'Welcome' link");
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontBillingInfoPage = dsvStoreFrontHomePage.clickBillingInfoLinkFromWelcomeDropDown();
		dsvStoreFrontBillingInfoPage.clickAddANewBillingProfileLink();
		dsvStoreFrontBillingInfoPage.enterNewBillingProfileDetails(cardNumber, name1,expMonth, expYear, CVV);
		dsvStoreFrontBillingInfoPage.clickSaveBillingProfileBtn();
		s_assert.assertTrue(dsvStoreFrontBillingInfoPage.isBillingProfilePresentonPage(lName1), name1+" billing profile is not added on the page");
		dsvStoreFrontBillingInfoPage.clickEditBillingProfileLink(lName1);
		dsvStoreFrontBillingInfoPage.enterNameAndSecurityCode(name2,CVV);
		dsvStoreFrontBillingInfoPage.clickSaveBillingProfileBtn();
		s_assert.assertTrue(dsvStoreFrontBillingInfoPage.isBillingProfilePresentonPage(lName2), name1+" billing profile is not edited on the page");
		dsvStoreFrontHomePage.clickLogo();
		s_assert.assertAll();
	}

	//Hybris Project-5318:Adding new and Editing existing Shipping Profile AS Consultant
	@Test(groups = { "consultant" },priority=8)
	public void testAddAndEditShippingProfileAsConsultant_5318(){
		int randomNum = CommonUtils.getRandomNum(1000, 9999);
		String fName = "RFAutoSF"; 
		String lName1 = String.valueOf(randomNum);
		String lName2 = lName1+" edit";
		String name1 = fName+" "+lName1;
		String name2 = fName+" "+lName2;
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "Consultant is not on PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		//		s_assert.assertTrue(dsvStoreFrontHomePage.getWelcomeText().contains("Welcome"), "Home page doesn't have the 'Welcome' link");
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontShippingInfoPage = dsvStoreFrontHomePage.clickShippingInfoLinkFromWelcomeDropDown();
		dsvStoreFrontShippingInfoPage.clickAddANewShippingAddressLink();
		dsvStoreFrontShippingInfoPage.enterNewShippingAddressDetails(name1, addressLine1, city, state,postalCode, phoneNumber,CVV);
		dsvStoreFrontShippingInfoPage.clickSaveAddressBtn();
		s_assert.assertTrue(dsvStoreFrontShippingInfoPage.isShippingProfilePresentonPage(lName1), name1+" shipping profile is not added on the page");
		dsvStoreFrontShippingInfoPage.clickEditShippingProfileLink(lName1);
		dsvStoreFrontShippingInfoPage.enterNameWithCardNumberAndSecurityCode(name2,CVV);
		dsvStoreFrontShippingInfoPage.clickSaveAddressBtn();
		s_assert.assertTrue(dsvStoreFrontShippingInfoPage.isShippingProfilePresentonPage(lName2), name1+" shipping profile is not edited on the page");
		dsvStoreFrontHomePage.clickLogo();
		s_assert.assertAll();
	}

	//Hybris Project : Delete Shipping Profile AS Consultant
	@Test(groups = { "consultant" },priority=9)
	public void testDeleteShippingProfileAsConsultant(){
		int randomNum = CommonUtils.getRandomNum(1000, 9999);
		String fName = "RFAutoSF"; 
		String lName1 = String.valueOf(randomNum);
		String name1 = fName+" "+lName1;
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "Consultant is not on PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		//		s_assert.assertTrue(dsvStoreFrontHomePage.getWelcomeText().contains("Welcome"), "Home page doesn't have the 'Welcome' link");
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontShippingInfoPage = dsvStoreFrontHomePage.clickShippingInfoLinkFromWelcomeDropDown();
		dsvStoreFrontShippingInfoPage.clickAddANewShippingAddressLink();
		dsvStoreFrontShippingInfoPage.enterNewShippingAddressDetails(name1, addressLine1, city, state,postalCode, phoneNumber,CVV);
		dsvStoreFrontShippingInfoPage.clickSaveAddressBtn();
		s_assert.assertTrue(dsvStoreFrontShippingInfoPage.isShippingProfilePresentonPage(lName1), name1+" shipping profile is not added on the page");
		dsvStoreFrontShippingInfoPage.clickDeleteShippingOrBillingProfileLink(lName1);
		s_assert.assertFalse(dsvStoreFrontShippingInfoPage.isShippingProfilePresentonPage(lName1), name1+" shipping profile is not deleted from the page");
		dsvStoreFrontHomePage.clickLogo();
		s_assert.assertAll();
	}

	//Hybris Project-5320:Deleting Billing Profile for Consultant
	@Test(groups = { "consultant" },priority=10)
	public void testDeleteBillingProfileAsConsultant_5320() throws Exception{
		int randomNum = CommonUtils.getRandomNum(1000, 9999);
		String fName = "RF"; 
		String lName1 = "AutoSF "+randomNum;
		String name1 = fName+" "+lName1;		
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "Consultant is not on PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		//		s_assert.assertTrue(dsvStoreFrontHomePage.getWelcomeText().contains("Welcome"), "Home page doesn't have the 'Welcome' link");
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontBillingInfoPage = dsvStoreFrontHomePage.clickBillingInfoLinkFromWelcomeDropDown();
		dsvStoreFrontBillingInfoPage.clickAddANewBillingProfileLink();
		dsvStoreFrontBillingInfoPage.enterNewBillingProfileDetails(cardNumber, name1,expMonth, expYear, CVV);
		dsvStoreFrontBillingInfoPage.clickSaveBillingProfileBtn();
		s_assert.assertTrue(dsvStoreFrontBillingInfoPage.isBillingProfilePresentonPage(lName1), name1+" billing profile is not added on the page");
		dsvStoreFrontBillingInfoPage. clickDeleteShippingOrBillingProfileLink(lName1);
		s_assert.assertFalse(dsvStoreFrontBillingInfoPage.isBillingProfilePresentonPage(lName1), name1+" billing profile is not deleted from the page");
		dsvStoreFrontHomePage.clickLogo();
		System.out.println("logo clicked..url is "+driver.getCurrentUrl());
		s_assert.assertAll();
	}

	//Hybris Project-5332:Access .biz and .com with non-secure url
	@Test(groups = { "consultant" },priority=11)
	public void testAccessBizAndComWithNonSecureURL_5332(){
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		dsvStoreFrontHomePage.openURL(dsvStoreFrontHomePage.convertHTTPS_To_HTTP(getComPWS()));
		//logout();		
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "Consultant is not on PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.isLoginOrWelcomePresent(),"Login link or Welcome Text has not appeared after hitting .com non-secure pws");
		dsvStoreFrontHomePage.openURL(dsvStoreFrontHomePage.convertComToBizOrBizToComURL(dsvStoreFrontHomePage.convertHTTPS_To_HTTP(getComPWS())));
		s_assert.assertTrue(dsvStoreFrontHomePage.isLoginOrWelcomePresent(),"Login link or Welcome Text has not appeared after hitting .biz non-secure pws");
		s_assert.assertAll();
	}

	//Hybris Project-5333:Access .biz and .com with secure url
	@Test(groups = { "consultant" },priority=12)
	public void testAccessBizAndComWithSecureURL_5333(){
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		dsvStoreFrontHomePage.openURL(getComPWS());
		//logout();		
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "Consultant is not on PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.isLoginOrWelcomePresent(),"Login link or Welcome Text has not appeared after hitting .com secure pws");
		dsvStoreFrontHomePage.openURL(dsvStoreFrontHomePage.convertComToBizOrBizToComURL(getComPWS()));
		s_assert.assertTrue(dsvStoreFrontHomePage.isLoginOrWelcomePresent(),"Login link or Welcome Text has not appeared after hitting .biz secure pws");
		s_assert.assertAll();
	}

	//Hybris Project-5321:User Account login As PC
	@Test(groups = { "pc" },priority=13)
	public void testUserAccountLoginAsPC_5321(){
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		String baseURL = dsvStoreFrontHomePage.getBaseURL();	
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(baseURL)||dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "PC is not corp site or .com pws site after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.isUserNameDropDownPresent(), "Home page doesn't have the username dropdown");		
		dsvStoreFrontHomePage.clickLogo();
		s_assert.assertAll();		
	}

	//Hybris Project_5322:Edit PC Perks Autoship Template
	@Test(groups = { "pc" },priority=14)
	public void testEditPcPerksAutoshipTemplate_5322(){
		String quantityOfProduct = "10";
		String secondProductRetailPrice=null;
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		String baseURL = dsvStoreFrontHomePage.getBaseURL();
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(baseURL)||dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "PC is not corp site or Sponsor's PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.isUserNameDropDownPresent(), "Home page doesn't have the username dropdown");
		dsvStoreFrontAutoshipCartPage = dsvStoreFrontHomePage.clickOnPCPerksCartImg();
		dsvStoreFrontQuickShopPage = dsvStoreFrontAutoshipCartPage.clickTopContinueShoppingLink();
		String firstProductRetailPrice = dsvStoreFrontQuickShopPage.getFirstProductRetailPrice();
		String firstProductName = dsvStoreFrontQuickShopPage.getFirstProductName();
		logger.info("Name of first product added to CRP is "+firstProductName);
		logger.info("Retail price of first product added to CRP is "+firstProductRetailPrice);
		dsvStoreFrontAutoshipCartPage = dsvStoreFrontQuickShopPage.clickAddToPCPerksForFirstProduct();
		s_assert.assertTrue(dsvStoreFrontAutoshipCartPage.isProductPresentOnCart(firstProductRetailPrice), firstProductName+" is not added to the cart");
		dsvStoreFrontAutoshipCartPage.addQuantityOfProduct(firstProductRetailPrice, quantityOfProduct);
		dsvStoreFrontAutoshipCartPage.clickUpdateQuantityBtnOfProduct(firstProductRetailPrice);
		s_assert.assertTrue(dsvStoreFrontAutoshipCartPage.getQuantityOfProduct(firstProductRetailPrice).contains(quantityOfProduct), "Quantity of "+firstProductName+" expected is "+quantityOfProduct+"but on UI is "+dsvStoreFrontAutoshipCartPage.getQuantityOfProduct(firstProductRetailPrice));
		dsvStoreFrontAutoshipCartPage.clickRemoveProduct(firstProductRetailPrice);
		if(dsvStoreFrontAutoshipCartPage.isThresholdMessageAppeared()==true){
			dsvStoreFrontQuickShopPage = dsvStoreFrontAutoshipCartPage.clickTopContinueShoppingLink();
			secondProductRetailPrice = dsvStoreFrontQuickShopPage.getSecondProductRetailPrice();
			logger.info("Retail price of second product added to CRP is "+secondProductRetailPrice);
			dsvStoreFrontAutoshipCartPage = dsvStoreFrontQuickShopPage.clickAddToPCPerksForSecondProduct();
			dsvStoreFrontAutoshipCartPage.addQuantityOfProduct(secondProductRetailPrice, quantityOfProduct);
			dsvStoreFrontAutoshipCartPage.clickUpdateQuantityBtnOfProduct(secondProductRetailPrice);
			dsvStoreFrontAutoshipCartPage.clickRemoveProduct(firstProductRetailPrice);
			s_assert.assertFalse(dsvStoreFrontAutoshipCartPage.isProductPresentOnCart(firstProductRetailPrice), firstProductRetailPrice+" is not removed from the cart");
		}
		else{
			s_assert.assertFalse(dsvStoreFrontAutoshipCartPage.isProductPresentOnCart(firstProductRetailPrice), firstProductRetailPrice+" is not removed from the cart"); 
		}
		dsvStoreFrontHomePage.clickLogo();
		s_assert.assertAll();
	}


	//Variant Product Add to Ad-hoc and Autoship cart From Category Page for PC 
	@Test(groups = { "pc" },priority=15)
	public void testVariantProductFromCategoryForPC(){
		if(driver.getCountry().equals("ca")){
			dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
			String baseURL = dsvStoreFrontHomePage.getBaseURL();
			s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(baseURL)||dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "PC is not corp site or Sponsor's PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
			dsvStoreFrontHomePage.hoverOnShopLinkAndClickCategoryLink(variantProductCategory);
			s_assert.assertTrue(dsvStoreFrontHomePage.isAlertPresentForVariantProductsAdHoc(),"Alert not displayed while adding product to Ad-hoc cart without selecting a Variant");
			dsvStoreFrontHomePage.addProductToAdHocCart(variantProductName, variantOption);
			s_assert.assertTrue(dsvStoreFrontHomePage.isVariantProductAvailableInCart(variantProductFirstHalfName, variantProductSecondHalfName, variantOption),"Variant Product is not available on Ad-hoc cart page");
			dsvStoreFrontAutoshipCartPage = dsvStoreFrontHomePage.clickAutoshipMiniBag();
			if(dsvStoreFrontAutoshipCartPage.isVariantProductInAutoshipCart()==true){
				s_assert.assertFalse(dsvStoreFrontAutoshipCartPage.clickRemoveButtonForVarientProductAndVerify(), "Varient Product was not removed from cart");
			}
			dsvStoreFrontAutoshipCartPage.clickLogo();
			dsvStoreFrontHomePage.hoverOnShopLinkAndClickCategoryLink(variantProductCategory);
			s_assert.assertTrue(dsvStoreFrontAutoshipCartPage.isAlertPresentForVariantProductsAutoship(),"Alert not displayed while adding product to Autoship cart without selecting a Variant");
			dsvStoreFrontHomePage.addProductToAutoshipCart(variantProductName, variantOption);
			s_assert.assertTrue(dsvStoreFrontAutoshipCartPage.isVariantProductInAutoshipCart(), "Varient Product was not added to Autoship Cart");
			s_assert.assertFalse(dsvStoreFrontAutoshipCartPage.clickRemoveButtonForVarientProductAndVerify(), "Verient Product was not removed from cart");
			dsvStoreFrontAutoshipCartPage.clickLogo();
			s_assert.assertAll();
		}
	}

	//Variant Product Add to Ad-hoc and Autoship cart From Search Results Page for PC
	@Test(groups = { "pc" },priority=16)
	public void testVariantProductFromSearchResultsForPC(){
		if(driver.getCountry().equals("ca")){
			dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
			s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "Consultant is not on PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
			dsvStoreFrontHomePage.searchVariantProductFromHeaderSearch();
			s_assert.assertTrue(dsvStoreFrontHomePage.isAlertPresentForVariantProductsAdHoc(),"Alert not displayed while adding product to Ad-hoc cart without selecting a Variant");
			dsvStoreFrontHomePage.addProductToAdHocCart(variantProductName, variantOption);
			s_assert.assertTrue(dsvStoreFrontHomePage.isVariantProductAvailableInCart(variantProductFirstHalfName, variantProductSecondHalfName, variantOption),"Variant Product is not available on Ad-hoc cart page");
			dsvStoreFrontAutoshipCartPage = dsvStoreFrontHomePage.clickAutoshipMiniBag();
			if(dsvStoreFrontAutoshipCartPage.isVariantProductInAutoshipCart()==true){
				s_assert.assertFalse(dsvStoreFrontAutoshipCartPage.clickRemoveButtonForVarientProductAndVerify(), "Verient Product was not removed from cart");
			}
			dsvStoreFrontAutoshipCartPage.clickLogo();
			dsvStoreFrontHomePage.searchVariantProductFromHeaderSearch();
			s_assert.assertTrue(dsvStoreFrontAutoshipCartPage.isAlertPresentForVariantProductsAutoship(),"Alert not displayed while adding product to Autoship cart without selecting a Variant");
			dsvStoreFrontHomePage.addProductToAutoshipCart(variantProductName, variantOption);
			s_assert.assertTrue(dsvStoreFrontAutoshipCartPage.isVariantProductInAutoshipCart(), "Varient Product was not added to Autoship Cart");
			s_assert.assertFalse(dsvStoreFrontAutoshipCartPage.clickRemoveButtonForVarientProductAndVerify(), "Verient Product was not removed from cart");
			dsvStoreFrontAutoshipCartPage.clickLogo();
			s_assert.assertAll();
		}
	}

	//Hybris Project-5323 Updating Account info As PC
	@Test(groups = { "pc" },priority=17)
	public void testAccountInfoUpdateAsPC_5323() throws Exception{
		int randomNum = CommonUtils.getRandomNum(5000, 9999);
		int randomDOB = CommonUtils.getRandomNum(1, 12);
		String fName = "RFTestP"+randomNum;
		String lName = "RFTestC"+randomNum;
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		String baseURL = dsvStoreFrontHomePage.getBaseURL(); 
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(baseURL)||dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "PC is not corp site or .com pws site after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.isUserNameDropDownPresent(), "Home page doesn't have the username dropdown"); 
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontAccountInfoPage  = dsvStoreFrontHomePage.clickAccountInfoLinkFromWelcomeDropDown();  
		dsvStoreFrontAccountInfoPage.enterFirstNameOfUser(fName);
		dsvStoreFrontAccountInfoPage.enterLastNameOfUser(lName);
		dsvStoreFrontAccountInfoPage.enterPhoneNumber(editPhoneNumber+randomNum);
		dsvStoreFrontAccountInfoPage.selectDOBDate(randomDOB);
		dsvStoreFrontAccountInfoPage.selectDOBMonth(randomDOB);
		dsvStoreFrontAccountInfoPage.selectDOBYear(randomDOB);
		dsvStoreFrontAccountInfoPage.clickOnSaveButton();
		s_assert.assertTrue(dsvStoreFrontAccountInfoPage.isSuccessMessagePresentOnPage(), "Account information was not updated correctly");
		dsvStoreFrontHomePage.clickLogo();
		s_assert.assertAll();
	}

	//Hybris Project-5325:Adding new and Editing existing Billing Profile As PC
	@Test(groups = { "pc" },priority=18)
	public void testAddAndEditBillingProfileAsPC_5325() throws Exception{
		int randomNum = CommonUtils.getRandomNum(1000, 9999);
		String fName = "RFAutoSF"; 
		String lName1 = String.valueOf(randomNum);
		String lName2 = lName1+" edit";
		String name1 = fName+" "+lName1;
		String name2 = fName+" "+lName2;
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		String baseURL = dsvStoreFrontHomePage.getBaseURL();
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(baseURL)||dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "PC is not corp site or .com pws site after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.isUserNameDropDownPresent(), "Home page doesn't have the username dropdown");		
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontBillingInfoPage = dsvStoreFrontHomePage.clickBillingInfoLinkFromWelcomeDropDown();
		dsvStoreFrontBillingInfoPage.clickAddANewBillingProfileLink();
		dsvStoreFrontBillingInfoPage.enterNewBillingProfileDetails(cardNumber, name1,expMonth, expYear, CVV);
		dsvStoreFrontBillingInfoPage.clickSaveBillingProfileBtn();
		s_assert.assertTrue(dsvStoreFrontBillingInfoPage.isBillingProfilePresentonPage(lName1), name1+" billing profile is not added on the page");
		dsvStoreFrontBillingInfoPage.clickEditBillingProfileLink(lName1);
		dsvStoreFrontBillingInfoPage.enterNameAndSecurityCode(name2,CVV);
		dsvStoreFrontBillingInfoPage.clickSaveBillingProfileBtn();
		s_assert.assertTrue(dsvStoreFrontBillingInfoPage.isBillingProfilePresentonPage(lName2), name1+" billing profile is not edited on the page");
		dsvStoreFrontHomePage.clickLogo();
		s_assert.assertAll();
	}


	// Hybris Project-5324:Adding new and Editing existing Shipping Profile As PC
	@Test(groups = { "pc" },priority=19)
	public void testAddAndEditShippingProfileAsPC_5324() throws Exception{
		int randomNum = CommonUtils.getRandomNum(1000, 9999);
		String fName = "RFAutoSF"; 
		String lName1 = String.valueOf(randomNum);
		String lName2 = lName1+" edit";
		String name1 = fName+" "+lName1;
		String name2 = fName+" "+lName2;		
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		String baseURL = dsvStoreFrontHomePage.getBaseURL();
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(baseURL)||dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "PC is not corp site or .com pws site after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.isUserNameDropDownPresent(), "Home page doesn't have the username dropdown");		
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontShippingInfoPage = dsvStoreFrontHomePage.clickShippingInfoLinkFromWelcomeDropDown();
		dsvStoreFrontShippingInfoPage.clickAddANewShippingAddressLink();
		dsvStoreFrontShippingInfoPage.enterNewShippingAddressDetails(name1, addressLine1, city, state,postalCode, phoneNumber,CVV);
		dsvStoreFrontShippingInfoPage.clickSaveAddressBtn();
		s_assert.assertTrue(dsvStoreFrontShippingInfoPage.isShippingProfilePresentonPage(lName1), name1+" shipping profile is not added on the page");
		dsvStoreFrontShippingInfoPage.clickEditShippingProfileLink(lName1);
		dsvStoreFrontShippingInfoPage.enterNameWithCardNumberAndSecurityCode(name2,CVV);
		dsvStoreFrontShippingInfoPage.clickSaveAddressBtn();
		s_assert.assertTrue(dsvStoreFrontShippingInfoPage.isShippingProfilePresentonPage(lName2), name1+" shipping profile is not edited on the page");
		dsvStoreFrontHomePage.clickLogo();
		s_assert.assertAll();
	}

	//Hybris Project : Delete Shipping Profile AS PC
	@Test(groups = { "pc" },priority=20)
	public void testDeleteShippingProfileAsPC(){
		int randomNum = CommonUtils.getRandomNum(1000, 9999);
		String fName = "RFAutoSF"; 
		String lName1 = String.valueOf(randomNum);
		String name1 = fName+" "+lName1;
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "PC is not on PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		//		s_assert.assertTrue(dsvStoreFrontHomePage.getWelcomeText().contains("Welcome"), "Home page doesn't have the 'Welcome' link");
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontShippingInfoPage = dsvStoreFrontHomePage.clickShippingInfoLinkFromWelcomeDropDown();
		dsvStoreFrontShippingInfoPage.clickAddANewShippingAddressLink();
		dsvStoreFrontShippingInfoPage.enterNewShippingAddressDetails(name1, addressLine1, city, state,postalCode, phoneNumber,CVV);
		dsvStoreFrontShippingInfoPage.clickSaveAddressBtn();
		s_assert.assertTrue(dsvStoreFrontShippingInfoPage.isShippingProfilePresentonPage(lName1), name1+" shipping profile is not added on the page");
		dsvStoreFrontShippingInfoPage.clickDeleteShippingOrBillingProfileLink(lName1);
		s_assert.assertFalse(dsvStoreFrontShippingInfoPage.isShippingProfilePresentonPage(lName1), name1+" shipping profile is not deleted from the page");
		dsvStoreFrontHomePage.clickLogo();
		s_assert.assertAll();
	}

	//Hybris Project-5326:Deleting Billing Profile for PC
	@Test(groups = { "pc" },priority=21)
	public void testDeleteBillingProfileAsPC_5326() throws Exception{
		int randomNum = CommonUtils.getRandomNum(1000, 9999);
		String fName = "RF"; 
		String lName1 = "AutoSF "+randomNum;		
		String name1 = fName+" "+lName1;		
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		String baseURL = dsvStoreFrontHomePage.getBaseURL();
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(baseURL)||dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "PC is not corp site or .com pws site after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.isUserNameDropDownPresent(), "Home page doesn't have the username dropdown");		
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontBillingInfoPage = dsvStoreFrontHomePage.clickBillingInfoLinkFromWelcomeDropDown();
		dsvStoreFrontBillingInfoPage.clickAddANewBillingProfileLink();
		dsvStoreFrontBillingInfoPage.enterNewBillingProfileDetails(cardNumber, name1,expMonth, expYear, CVV);
		dsvStoreFrontBillingInfoPage.clickSaveBillingProfileBtn();
		s_assert.assertTrue(dsvStoreFrontBillingInfoPage.isBillingProfilePresentonPage(lName1), name1+" billing profile is not added on the page");
		dsvStoreFrontBillingInfoPage.clickDeleteShippingOrBillingProfileLink(lName1);
		s_assert.assertFalse(dsvStoreFrontBillingInfoPage.isBillingProfilePresentonPage(lName1), name1+" billing profile is not deleted from the page");
		dsvStoreFrontHomePage.clickLogo();
		s_assert.assertAll();
	}

	//Hybris Project-5327:User Account login As RC
	@Test(groups = { "rc" },priority=22)
	public void testUserAccountLoginAsRC_5327(){
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		String baseURL = dsvStoreFrontHomePage.getBaseURL();		
		s_assert.assertFalse(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "RC is on PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(baseURL)||dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(baseURL+"/"), "RC is not corp site after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.isUserNameDropDownPresent(), "Home page doesn't have the username dropdown");		
		dsvStoreFrontHomePage.clickLogo();
		s_assert.assertAll();		
	}

	//Hybris Project-5328 Updating Account info As RC
	@Test(groups = { "rc" },priority=23)
	public void testAccountInfoUpdateAsRC_5328() throws Exception{
		int randomNum = CommonUtils.getRandomNum(5000, 9999);
		int randomDOB = CommonUtils.getRandomNum(1, 12);
		String fName = "RFTestR"+randomNum;
		String lName = "RFTestC"+randomNum;
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		String baseURL = dsvStoreFrontHomePage.getBaseURL();  
		s_assert.assertFalse(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "RC is on PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(baseURL)||dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(baseURL+"/"), "RC is not corp site after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.isUserNameDropDownPresent(), "Home page doesn't have the username dropdown");
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontAccountInfoPage  = dsvStoreFrontHomePage.clickAccountInfoLinkFromWelcomeDropDown();  
		dsvStoreFrontAccountInfoPage.enterFirstNameOfUser(fName);
		dsvStoreFrontAccountInfoPage.enterLastNameOfUser(lName);
		dsvStoreFrontAccountInfoPage.enterPhoneNumber(editPhoneNumber+randomNum);
		dsvStoreFrontAccountInfoPage.selectDOBDate(randomDOB);
		dsvStoreFrontAccountInfoPage.selectDOBMonth(randomDOB);
		dsvStoreFrontAccountInfoPage.selectDOBYear(randomDOB);
		dsvStoreFrontAccountInfoPage.clickOnSaveButton();
		s_assert.assertTrue(dsvStoreFrontAccountInfoPage.isSuccessMessagePresentOnPage(), "Account information was not updated correctly");
		dsvStoreFrontHomePage.clickLogo();
		s_assert.assertAll();
	}

	//Hybris Project-5330:Adding new and Editing existing Billing Profile As RC
	@Test(groups = { "rc" },priority=24)
	public void testAddAndEditBillingProfileAsRC_5330() throws Exception{
		int randomNum = CommonUtils.getRandomNum(1000, 9999);
		String fName = "RFAutoSF"; 
		String lName1 = String.valueOf(randomNum);
		String lName2 = lName1+" edit";
		String name1 = fName+" "+lName1;
		String name2 = fName+" "+lName2;
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		String baseURL = dsvStoreFrontHomePage.getBaseURL();
		s_assert.assertFalse(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "RC is on PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(baseURL)||dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(baseURL+"/"), "RC is not corp site after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.isUserNameDropDownPresent(), "Home page doesn't have the username dropdown");		
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontBillingInfoPage = dsvStoreFrontHomePage.clickBillingInfoLinkFromWelcomeDropDown();
		dsvStoreFrontBillingInfoPage.clickAddANewBillingProfileLink();
		dsvStoreFrontBillingInfoPage.enterNewBillingProfileDetails(cardNumber, name1,expMonth, expYear, CVV);
		dsvStoreFrontBillingInfoPage.clickSaveBillingProfileBtn();
		s_assert.assertTrue(dsvStoreFrontBillingInfoPage.isBillingProfilePresentonPage(lName1), name1+" billing profile is not added on the page");
		dsvStoreFrontBillingInfoPage.clickEditBillingProfileLink(lName1);
		dsvStoreFrontBillingInfoPage.enterNameAndSecurityCode(name2,CVV);
		dsvStoreFrontBillingInfoPage.clickSaveBillingProfileBtn();
		s_assert.assertTrue(dsvStoreFrontBillingInfoPage.isBillingProfilePresentonPage(lName2), name1+" billing profile is not edited on the page");
		dsvStoreFrontHomePage.clickLogo();
		s_assert.assertAll();
	}

	//Hybris Project-5329:Adding new and Editing existing Shipping Profile As RC
	@Test(groups = { "rc" },priority=25)
	public void testAddAndEditShippingProfileAsRC_5329() throws Exception{
		int randomNum = CommonUtils.getRandomNum(1000, 9999);
		String fName = "RFAutoSF"; 
		String lName1 = String.valueOf(randomNum);
		String lName2 = lName1+" edit";
		String name1 = fName+" "+lName1;
		String name2 = fName+" "+lName2;
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		String baseURL = dsvStoreFrontHomePage.getBaseURL();
		System.out.println("baseURL.."+baseURL);
		System.out.println("currentURL.."+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(baseURL)||dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(baseURL+"/"), "RC is not corp site after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.isUserNameDropDownPresent(), "Home page doesn't have the username dropdown");		
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontShippingInfoPage = dsvStoreFrontHomePage.clickShippingInfoLinkFromWelcomeDropDown();
		dsvStoreFrontShippingInfoPage.clickAddANewShippingAddressLink();
		dsvStoreFrontShippingInfoPage.enterNewShippingAddressDetails(name1, addressLine1, city,state, postalCode, phoneNumber,CVV);
		dsvStoreFrontShippingInfoPage.clickSaveAddressBtn();
		s_assert.assertTrue(dsvStoreFrontShippingInfoPage.isShippingProfilePresentonPage(lName1), name1+" shipping profile is not added on the page");
		dsvStoreFrontShippingInfoPage.clickEditShippingProfileLink(lName1);
		dsvStoreFrontShippingInfoPage.enterNameWithCardNumberAndSecurityCode(name2,CVV);
		dsvStoreFrontShippingInfoPage.clickSaveAddressBtn();
		s_assert.assertTrue(dsvStoreFrontShippingInfoPage.isShippingProfilePresentonPage(lName2), name1+" shipping profile is not edited on the page");
		dsvStoreFrontHomePage.clickLogo();
		s_assert.assertAll();
	}

	//Hybris Project : Delete Shipping Profile AS RC
	@Test(groups = { "rc" },priority=26)
	public void testDeleteShippingProfileAsRC(){
		int randomNum = CommonUtils.getRandomNum(1000, 9999);
		String fName = "RFAutoSF"; 
		String lName1 = String.valueOf(randomNum);
		String name1 = fName+" "+lName1;
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		String baseURL = dsvStoreFrontHomePage.getBaseURL();
		System.out.println("baseURL.."+baseURL);
		System.out.println("currentURL.."+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(baseURL)||dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(baseURL+"/"), "RC is not corp site after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.isUserNameDropDownPresent(), "Home page doesn't have the username dropdown");		
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontShippingInfoPage = dsvStoreFrontHomePage.clickShippingInfoLinkFromWelcomeDropDown();
		dsvStoreFrontShippingInfoPage.clickAddANewShippingAddressLink();
		dsvStoreFrontShippingInfoPage.enterNewShippingAddressDetails(name1, addressLine1, city, state,postalCode, phoneNumber,CVV);
		dsvStoreFrontShippingInfoPage.clickSaveAddressBtn();
		s_assert.assertTrue(dsvStoreFrontShippingInfoPage.isShippingProfilePresentonPage(lName1), name1+" shipping profile is not added on the page");
		dsvStoreFrontShippingInfoPage.clickDeleteShippingOrBillingProfileLink(lName1);
		s_assert.assertFalse(dsvStoreFrontShippingInfoPage.isShippingProfilePresentonPage(lName1), name1+" shipping profile is not deleted from the page");
		dsvStoreFrontHomePage.clickLogo();
		s_assert.assertAll();
	}

	//Hybris Project-5331:Deleting Billing Profile for RC
	@Test(groups = { "rc" },priority=27)
	public void testDeleteBillingProfileAsRC_5331() throws Exception{
		int randomNum = CommonUtils.getRandomNum(1000, 9999);
		String fName = "RF"; 
		String lName1 = "AutoSF "+randomNum;		
		String name1 = fName+" "+lName1;		
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		String baseURL = dsvStoreFrontHomePage.getBaseURL();
		s_assert.assertFalse(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(pwsSuffix), "RC is on PWS after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(baseURL)||dsvStoreFrontHomePage.getWebdriver().getCurrentUrl().contains(baseURL+"/"), "RC is not corp site after login,the url coming is "+dsvStoreFrontHomePage.getWebdriver().getCurrentUrl());
		s_assert.assertTrue(dsvStoreFrontHomePage.isUserNameDropDownPresent(), "Home page doesn't have the username dropdown");		
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontBillingInfoPage = dsvStoreFrontHomePage.clickBillingInfoLinkFromWelcomeDropDown();
		dsvStoreFrontBillingInfoPage.clickAddANewBillingProfileLink();
		dsvStoreFrontBillingInfoPage.enterNewBillingProfileDetails(cardNumber, name1,expMonth, expYear, CVV);
		dsvStoreFrontBillingInfoPage.clickSaveBillingProfileBtn();
		s_assert.assertTrue(dsvStoreFrontBillingInfoPage.isBillingProfilePresentonPage(lName1), name1+" billing profile is not added on the page");
		dsvStoreFrontBillingInfoPage.clickDeleteShippingOrBillingProfileLink(lName1);
		s_assert.assertFalse(dsvStoreFrontBillingInfoPage.isBillingProfilePresentonPage(lName1), name1+" billing profile is not deleted from the page");
		s_assert.assertAll();
	}

	//Hybris Project-5334:Select Canadian/Aus sponsor with PWS for enrolment
	@Test(groups = { "nonLogin" },priority=28)
	public void testCanadianSponsorWithPwsForEnrollment_5334(){
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		dsvStoreFrontHomePage.openURL(driver.getURL()+"/"+country);
		dsvStoreFrontHomePage.hoverOnOurBusinessAndClickEnrollNow();
		dsvStoreFrontHomePage.enterSponsorAndSearch(sponsorWithPWS);
		dsvStoreFrontHomePage.mouseHoverOnSponsorAndClickSelectAndContinue(1);
		s_assert.assertTrue(dsvStoreFrontHomePage.validateCurrentURLContainsBiz(),"url does not contain biz");
		s_assert.assertAll();
	}

	//Hybris Project-5335:Select US sponsor for enrolment 
	@Test(groups = { "nonLogin" },priority=29)
	public void testSelectUSSponsorForEnrolment_5335(){
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		dsvStoreFrontHomePage.openURL(driver.getURL()+"/"+country);
		dsvStoreFrontHomePage.hoverOnOurBusinessAndClickEnrollNow();
		dsvStoreFrontHomePage.enterSponsorAndSearch(sponsorWithPWS_US);
		dsvStoreFrontHomePage.mouseHoverOnSponsorAndClickSelectAndContinue();
		s_assert.assertTrue(dsvStoreFrontHomePage.validateCurrentURLContainsCom(),"url does not contain rodanandfields.com");
		s_assert.assertAll();
	}

	//	//Hybris Project-5336:Select Canadian sponsor with no PWS for enrolment
	//	@Test(groups = { "nonLogin" },priority=27)
	//	public void testSelectCanadianSponsorWithNoPWSForEnrolment_5336(){
	//		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
	//		dsvStoreFrontHomePage.openURL(driver.getURL()+"/"+country);
	//		dsvStoreFrontHomePage.hoverOnOurBusinessAndClickEnrollNow();
	//		dsvStoreFrontHomePage.enterSponsorAndSearch(TestConstants.DSV_CA_SPONSOR_WITH_NO_PWS);
	//		dsvStoreFrontHomePage.mouseHoverOnSponsorAndClickSelectAndContinue();
	//		s_assert.assertTrue(dsvStoreFrontHomePage.validateCurrentURLContainsCom(),"url does not contain rodanandfields.com");
	//		s_assert.assertAll();
	//	}


}