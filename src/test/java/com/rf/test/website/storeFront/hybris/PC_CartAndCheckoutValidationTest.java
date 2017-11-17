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
import com.rf.pages.website.storeFront.StoreFrontCartAutoShipPage;
import com.rf.pages.website.storeFront.StoreFrontHomePage;
import com.rf.pages.website.storeFront.StoreFrontShippingInfoPage;
import com.rf.pages.website.storeFront.StoreFrontUpdateCartPage;
import com.rf.test.website.RFWebsiteBaseTest;

public class PC_CartAndCheckoutValidationTest extends RFWebsiteBaseTest{
	private static final Logger logger = LogManager
			.getLogger(PC_CartAndCheckoutValidationTest.class.getName());

	/**
	 * Test Summary: Login as PC and verify that PC Perks cart is displayed, then add product to cart and verify that mini cart is displayed
	 * Assertions:-
	 * PC Perks Cart
	 * Adhoc Mini Cart
	 * @throws InterruptedException
	 */
	//Hybris Project-2328 :: Version : 1 :: check Mini Cart - PC 
	@Test 
	public void testCheckMiniCartForPC_2328() throws InterruptedException {
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		s_assert.assertTrue(storeFrontPCUserPage.isNextPCPerksMiniCartDisplayed(), "next PC Perks Mini cart SHOULD be displayed");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		s_assert.assertTrue(storeFrontHomePage.isAdhocMiniCartDisplayed(), "mini cart SHOULD be displayed");
		s_assert.assertAll();
	}

	/***
	 * Test Summary: Login with PC, add a product to PC Perks cart, increase the qty, decrease the qty, remove it
	 * Assertions:
	 * Product present on cart after adding
	 * Increasing the quantity works fine
	 * Decreasing the quantity works fine
	 * Product NOT  present on cart after removing
	 * Global message on removing
	 * 
	 * */
	// Hybris Project-142 Autoship template - manage products in cart - PC perk 
	@Test 
	public void testAutoshipTemplateManagePoductsInCartPCPerk_142() throws InterruptedException {
		String productName=null;
		String positionOfProductOnAutoshipCart=null;
		int existingQuantityOfProduct=0;
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontHomePage.clickOnAutoshipCart();
		storeFrontUpdateCartPage.clickOnContinueShoppingLink();
		productName=storeFrontUpdateCartPage.selectProductAndProceedToAddToPCPerks();
		s_assert.assertTrue(storeFrontUpdateCartPage.isProductPresentInAutoshipCart(productName), productName+" SHOULD be present on autoship cart after adding it");
		positionOfProductOnAutoshipCart=storeFrontUpdateCartPage.getPositionOfProductOnAutoshipCart(productName);
		existingQuantityOfProduct= storeFrontUpdateCartPage.getQuantityOfProductOnAutoshipCartAndUpdate(positionOfProductOnAutoshipCart);
		storeFrontUpdateCartPage.setQuantityOfProductOnAutoshipCartAndUpdate(positionOfProductOnAutoshipCart, existingQuantityOfProduct+1);
		s_assert.assertEquals(storeFrontUpdateCartPage.getQuantityOfProductOnAutoshipCartAndUpdate(positionOfProductOnAutoshipCart), existingQuantityOfProduct+1,"Quantity has not increased");
		existingQuantityOfProduct= storeFrontUpdateCartPage.getQuantityOfProductOnAutoshipCartAndUpdate(positionOfProductOnAutoshipCart);
		storeFrontUpdateCartPage.setQuantityOfProductOnAutoshipCartAndUpdate(positionOfProductOnAutoshipCart, existingQuantityOfProduct-1);
		s_assert.assertEquals(storeFrontUpdateCartPage.getQuantityOfProductOnAutoshipCartAndUpdate(positionOfProductOnAutoshipCart), existingQuantityOfProduct-1,"Quantity has not decreased");
		storeFrontUpdateCartPage.removeProductFromAutoshipCart(positionOfProductOnAutoshipCart);
		s_assert.assertTrue(storeFrontUpdateCartPage.getGlobalMessageFromCart().contains("Product has been removed from your cart"),"global message SHOULD be present after removing the product");
		s_assert.assertFalse(storeFrontUpdateCartPage.isProductPresentInAutoshipCart(productName), productName+" SHOULD NOT be present on autoship cart after removing it");
		s_assert.assertAll();
	}

	/**
	 * Test Summary:- Login With PC and navigate to EditPCPerksLink(Welcome dropdown) and check that change next ship date functionality is not present
	 * Assertions:
	 * change next ship date functionality is not present
	 * @throws InterruptedException
	 */
	// Hybris Project-2130:To verify Change date functionality for PC shouldnt be present on the storefront
	@Test 
	public void testVerifyChangeDateFunctionalityForPCUser_2130() throws InterruptedException   {
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontPCUserPage.clickOnWelcomeDropDown();
		storeFrontPCUserPage.clickEditPCPerksLinkPresentOnWelcomeDropDown();
		storeFrontUpdateCartPage=storeFrontCartAutoShipPage.clickUpdateMoreInfoLink();
		storeFrontUpdateCartPage.clickOnEditShipping();
		s_assert.assertFalse(storeFrontUpdateCartPage.isChangeNextShipDateFunctionalityPresent(), "check next ship date functionality SHOULD NOT be present");
		s_assert.assertAll();
	}

	/**
	 * Test Summary:-Add a product to cart from Products Page and verify that it has been added and product quantity is 1. Test for PC, RC, Consultant and guest
	 * Assertions:
	 * Quantity is 1 after adding product to cart
	 *
	 */
	//Hybris Project-4013:Logged in RC/PC/ Consultant to Corp site and click on "Buy Now"
	//Hybris Project-2307:Product Listing Scren should have Buy Now button --> click Buy Now--> product is added to the cart
	@Test  
	public void testProductAddedToCartWithQuantityFromProductsPage_2307_4013(){
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		s_assert.assertTrue(storeFrontHomePage.verifyNumberOfProductsInCart("1"), "quantity in the cart SHOULD be 1 for PC");
		logout();
		navigateToStoreFrontBaseURL();
		String rcUserEmailID = QueryUtils.getRandomActiveRCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId);
		storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasRCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),"Not able to login to the application from RC");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		s_assert.assertTrue(storeFrontHomePage.verifyNumberOfProductsInCart("1"), "quantity in the cart SHOULD be 1 for RC");
		logout();
		navigateToStoreFrontBaseURL();
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		s_assert.assertTrue(storeFrontHomePage.verifyNumberOfProductsInCart("1"), "quantity in the cart SHOULD be 1 for Cons");
		logout();
		navigateToStoreFrontBaseURL();
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		s_assert.assertTrue(storeFrontHomePage.verifyNumberOfProductsInCart("1"), "quantity in the cart SHOULD be 1 for Guest");
		s_assert.assertAll();
	}

	/**
	 * Test Summary:-Add a product to cart from Quick Info Page and verify that it has been added and product quantity is 1. Test for PC, RC, Consultant
	 * Assertions:
	 * Modal Window after clicking quick info
	 * Quantity is 1 after adding product to cart
	 *
	 */
	//Hybris Project-2318:PC Perks Message
	//Hybris Project-2309:Add Product from Quick Info Screen
	@Test 
	public void testAddProductFromQuickInfoScreen_2309_2318() throws InterruptedException{
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.mouseHoverProductAndClickQuickInfo();
		s_assert.assertTrue(storeFrontHomePage.isModalWindowExists(),"modal window SHOULD be displayed for PC");
		storeFrontHomePage.clickAddToBagButtonOnQuickInfoPopup();
		s_assert.assertTrue(storeFrontHomePage.verifyNumberOfProductsInCart("1"), "quantity in the cart SHOULD be 1 for PC");
		logout();
		navigateToStoreFrontBaseURL();
		String rcUserEmailID = QueryUtils.getRandomActiveRCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId);
		storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasRCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),"Not able to login to the application from RC");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.mouseHoverProductAndClickQuickInfo();
		s_assert.assertTrue(storeFrontHomePage.isModalWindowExists(),"modal window SHOULD be displayed for RC");
		storeFrontHomePage.clickAddToBagButtonOnQuickInfoPopup();
		s_assert.assertTrue(storeFrontHomePage.verifyNumberOfProductsInCart("1"), "quantity in the cart SHOULD be 1 for RC");
		logout();
		navigateToStoreFrontBaseURL();
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.mouseHoverProductAndClickQuickInfo();
		s_assert.assertTrue(storeFrontHomePage.isModalWindowExists(),"modal window SHOULD be displayed for Cons");
		storeFrontHomePage.clickAddToBagButtonOnQuickInfoPopup();
		s_assert.assertTrue(storeFrontHomePage.verifyNumberOfProductsInCart("1"), "quantity in the cart SHOULD be 1 for Cons");
		logout();
		navigateToStoreFrontBaseURL();
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.mouseHoverProductAndClickQuickInfo();
		s_assert.assertTrue(storeFrontHomePage.isModalWindowExists(),"modal window SHOULD be displayed for guest");
		s_assert.assertTrue(storeFrontHomePage.getPCPerksMessageFromModalPopup().contains(TestConstants.PC_PERKS_MESSAGE_ON_MODAL_POPUP),"PC Perks message SHOULD be present on modal popup for guest");
		storeFrontHomePage.clickAddToBagButtonOnQuickInfoPopup();
		s_assert.assertTrue(storeFrontHomePage.verifyNumberOfProductsInCart("1"), "quantity in the cart SHOULD be 1 for guest");
		s_assert.assertAll();
	}

	//Hybris Project-2316:check Product Details APge Layout and information
	//Hybris Project-2317:Check View Product Details button from Quick Info pop-up
	// Hybris Project-2326:Category Product List for Retail Customers & PC
	//Hybris Project-2324:Category Product List page
	//Hybris Project-2319:Check Quick Info Screen layout and informations
	//Hybris Project-2323:Check Buy Now Link
	@Test 
	public void testCheckBuyNowLink_2323_2319_2324_2326_2317_2316() throws InterruptedException{
		String selectedProductName=null;
		String selectedProductPrice=null;
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		s_assert.assertTrue(storeFrontHomePage.verifyProductInfoPresentOnQuikShopProducts(),"Product Info SHOULD be present  in quickshop product page for PC");
		s_assert.assertTrue(storeFrontHomePage.verifyRetailPricePresentInProductInfo(),"Retail Price SHOULD be present  in product info for PC");
		s_assert.assertTrue(storeFrontHomePage.verifyPCPricePresentInProductInfo(),"PC Price SHOULD be present  in product info for PC");
		s_assert.assertTrue(storeFrontHomePage.verifyBuyNowButtonPresentInProductInfo(),"Buy now button SHOULD be present in product info for PC");
		s_assert.assertTrue(storeFrontHomePage.verifyAddToPCPerksButtonInProductInfo(),"Add to Pc Perks button SHOULD be present  below product info for PC");
		selectedProductName=storeFrontHomePage.getProductName();
		selectedProductPrice=storeFrontHomePage.getProductPrice();
		s_assert.assertTrue(storeFrontHomePage.verifyAddToBagButton(),"Add to Bag Button SHOULD be present at products page for PC");
		storeFrontHomePage.mouseHoverProductAndClickQuickInfo();
		s_assert.assertTrue(storeFrontHomePage.verifyAddToPCPerksButtonOnQuickInfoPopup(),"Add to PC Perks Button SHOULD be present  on quick info popup for PC");
		s_assert.assertTrue(storeFrontHomePage.verifyAddToBagButtonOnQuickInfoPopup(),"Add to Bag Button SHOULD be present at Quick Info page for PC");
		storeFrontHomePage.clickViewProductDetailLink();
		s_assert.assertTrue(storeFrontHomePage.verifyAddToBagButtonOnProductDetailPage(),"Add to Bag Button SHOULD be present at products details page for PC");
		s_assert.assertTrue(storeFrontHomePage.verifyAddToPCPerksButtonOnProductDetailPage(),"Add to PC Perks Button SHOULD be present on product detail page for PC");
		s_assert.assertTrue(storeFrontHomePage.isProductImageExist(),"Product Image  SHOULD be present at products details page for PC");
		s_assert.assertTrue(storeFrontHomePage.verifyProductName(selectedProductName),"Product name  SHOULD be present at products details page for PC");
		s_assert.assertTrue(storeFrontHomePage.verifyProductPrice(selectedProductPrice),"Product price  SHOULD be present at products details page for PC");
		s_assert.assertTrue(storeFrontHomePage.verifyProductLongDescription(),"Product Description SHOULD be present on product detail page for PC");
		s_assert.assertTrue(storeFrontHomePage.verifyProductUsageNotesBox(),"Product Usage Notes box SHOULD be present on product detail page for PC");
		s_assert.assertTrue(storeFrontHomePage.verifyProductIngredientsBox(),"Product Ingredients box SHOULD be present on product detail page for PC");
		logout();
		navigateToStoreFrontBaseURL();
		String rcUserEmailID = QueryUtils.getRandomActiveRCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId);
		storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasRCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),"Not able to login to the application from RC");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		s_assert.assertTrue(storeFrontHomePage.verifyProductInfoPresentOnQuikShopProducts(),"Product Info SHOULD be present  in quickshop product page for RC");
		s_assert.assertTrue(storeFrontHomePage.verifyRetailPricePresentInProductInfo(),"Retail Price SHOULD be present  in product info for RC");
		s_assert.assertTrue(storeFrontHomePage.verifyBuyNowButtonPresentInProductInfo(),"Buy now button SHOULD be present in product info for RC");
		selectedProductName=storeFrontHomePage.getProductName();
		selectedProductPrice=storeFrontHomePage.getProductPrice();
		s_assert.assertTrue(storeFrontHomePage.verifyAddToBagButton(),"Add to Bag Button SHOULD be present at products page for RC");
		storeFrontHomePage.mouseHoverProductAndClickQuickInfo();
		s_assert.assertTrue(storeFrontHomePage.verifyAddToBagButtonOnQuickInfoPopup(),"Add to Bag Button SHOULD be present at Quick Info page for RC");
		storeFrontHomePage.clickViewProductDetailLink();
		s_assert.assertTrue(storeFrontHomePage.verifyAddToBagButtonOnProductDetailPage(),"Add to Bag Button SHOULD be present at products details page for RC");
		s_assert.assertTrue(storeFrontHomePage.isProductImageExist(),"Product Image  SHOULD be present at products details page for RC");
		s_assert.assertTrue(storeFrontHomePage.verifyProductName(selectedProductName),"Product name  SHOULD be present at products details page for RC");
		s_assert.assertTrue(storeFrontHomePage.verifyProductLongDescription(),"Product Description SHOULD be present on product detail page for RC");
		logout();
		navigateToStoreFrontBaseURL();
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		selectedProductName=storeFrontHomePage.getProductName();
		selectedProductPrice=storeFrontHomePage.getProductPrice();
		s_assert.assertTrue(storeFrontHomePage.verifyAddToBagButton(),"Add to Bag Button SHOULD be present at products page for Consultant");
		storeFrontHomePage.mouseHoverProductAndClickQuickInfo();
		s_assert.assertTrue(storeFrontHomePage.verifyAddToCRPButtonOnQuickInfoPopup(),"Add to CRP Button SHOULD be present at Quick Info popup for Cons");
		s_assert.assertTrue(storeFrontHomePage.verifyAddToBagButtonOnQuickInfoPopup(),"Add to Bag Button SHOULD be present at Quick Info popup for Cons");
		storeFrontHomePage.clickViewProductDetailLink();
		s_assert.assertTrue(storeFrontHomePage.verifyAddToBagButtonOnProductDetailPage(),"Add to Bag Button SHOULD be present at products details page for Cons");
		//s_assert.assertTrue(storeFrontHomePage.verifyAddToCRPButtonOnProductDetailPage(),"Add to CRP Button SHOULD be  present on product detail page");
		s_assert.assertTrue(storeFrontHomePage.isProductImageExist(),"Product Image  SHOULD be present at products details page for Cons");
		s_assert.assertTrue(storeFrontHomePage.verifyProductName(selectedProductName),"Product name  SHOULD be present at products details page for Cons");
		s_assert.assertTrue(storeFrontHomePage.verifyProductPrice(selectedProductPrice),"Product price  SHOULD be present at products details page for Cons");
		s_assert.assertTrue(storeFrontHomePage.verifyProductLongDescription(),"Product Description SHOULD be present on product detail page for Cons");
		s_assert.assertAll();
	}


	//Hybris Project-3768:Update PCPerk Template from Different PWS site(Other Than Sponsor's PWS)*
	@Test 
	public void testUpdatePCPerkTemplateFromDifferentPWS_3768() throws InterruptedException{
		storeFrontHomePage.convertComSiteToBizSite(storeFrontHomePage.openPWSSite(country, driver.getEnvironment()));
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontHomePage.clickOnAutoshipCart();
		storeFrontUpdateCartPage.clickOnContinueShoppingLink();
		storeFrontUpdateCartPage.selectAProductAndAddItToPCPerks();
		s_assert.assertTrue(storeFrontUpdateCartPage.isCartUpdateGlobalMessageDisplayed(), "cart is not updated!! ");
		s_assert.assertAll(); 
	}

	//Hybris Project-2267:Login as Existing PC and Place an Adhoc Order, Check for Alert message
	@Test 
	public void testExistingPCPlaceAnAdhocOrderAndCheckForAlertMessage_2267() throws InterruptedException{
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontPCUserPage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontPCUserPage.clickAddToBagButton();
		storeFrontPCUserPage.clickOnCheckoutButtonAfterAddProduct();
		s_assert.assertTrue(storeFrontPCUserPage.verifyCheckoutConfirmationPOPupPresent(),"Checkout Confirmation pop up not present");
		s_assert.assertTrue(storeFrontPCUserPage.verifyCheckoutConfirmationPopUpMessagePC(),"Checkout Confirmation pop up message is not present as expected");
		storeFrontPCUserPage.clickOnOkButtonOnCheckoutConfirmationPopUp();
		s_assert.assertTrue(storeFrontPCUserPage.verifyAccountInfoPageHeaderPresent(),"Account info page header is not present");
		s_assert.assertAll();
	}

	//Hybris Project-2264:Check Savings Per Product as a PC user
	@Test 
	public void testSavingsPerProductAsAPCUser_2264() throws InterruptedException{
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontPCUserPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		s_assert.assertTrue(storeFrontPCUserPage.verifyRetailPriceIsAvailableOnProductsPage(),"Retail Price is not available");
		s_assert.assertTrue(storeFrontPCUserPage.verifyYourPriceIsAvailableOnProductsPage(),"Your Price is not available");
		storeFrontUpdateCartPage.clickOnBuyNowButton();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyRetailPriceIsAvailableOnAdhocCart(),"Retail Price is not available on Adhoc cart");
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyYourPriceIsAvailableOnAdhocCart(),"Your Price is not available on Adhoc cart");
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyTotalSavingsIsAvailableOnAdhocCart(),"Total Savings is not available on Adhoc cart");
		storeFrontUpdateCartPage.clickOnRodanAndFieldsLogo();
		storeFrontUpdateCartPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage.clickOnAddToPcPerksButton();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyRetailPriceIsAvailableOnAutoshipCart(),"Retail Price is not available on Autoship cart");
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyYourPriceIsAvailableOnAutoshipCart(),"Your Price is not available on Autoship cart");
		storeFrontUpdateCartPage.clickOnUpdateMoreInfoButton();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyTotalSavingsIsAvailableOnAutoshipCart(),"Total Savings is not available on Autoship cart");
		s_assert.assertAll();
	}

	// Hybris Project-139:PC Perks - Delay
	@Test 
	public void testDelayPCPerks_139() throws InterruptedException{
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontPCUserPage.clickOnWelcomeDropDown();
		storeFrontOrdersPage=storeFrontPCUserPage.clickOrdersLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontOrdersPage.verifyOrdersPageIsDisplayed(),"Order Page Is not Displayed");
		s_assert.assertTrue(storeFrontOrdersPage.verifyAutoshipOrderSectionOnOrderPage(),"Autoship order section is not on order page for pc user");
		s_assert.assertTrue(storeFrontOrdersPage.verifyOrderHistorySectionOnOrderPage(),"Order history section is not on order page for pc user");
		s_assert.assertTrue(storeFrontOrdersPage.verifyReturnOrderSectionOnOrderPage(),"Return order section is not on order page for pc user ");
		storeFrontPCUserPage.clickOnYourAccountDropdown();
		storeFrontPCUserPage.clickOnPCPerksStatus();
		s_assert.assertTrue(storeFrontPCUserPage.verifyPCPerksStatus(),"PC perks status page is not present");
		storeFrontPCUserPage.clickDelayOrCancelPCPerks();
		storeFrontPCUserPage.clickChangeMyAutoshipDateButton();
		s_assert.assertTrue(storeFrontPCUserPage.verifyNextAutoshipDateRadioButtons(),"Next AutoShip Date radio button are not present");
		String nextBillDate=storeFrontPCUserPage.getNextBillAndShipDate();
		String dateAfterOneMonth=storeFrontPCUserPage.getOneMonthOutDate(nextBillDate);
		String dateAfterTwoMonth=storeFrontPCUserPage.getOneMonthOutDate(dateAfterOneMonth);
		String dateAfterThreeMonth=storeFrontPCUserPage.getOneMonthOutDate(dateAfterTwoMonth);
		s_assert.assertTrue(storeFrontPCUserPage.getShipAndBillDateAfterOneMonthFromUI().contains(dateAfterOneMonth),"Date After one month is not as expected");
		s_assert.assertTrue(storeFrontPCUserPage.getShipAndBillDateAfterTwoMonthFromUI().contains(dateAfterTwoMonth),"Date after two month is not as expected");
		storeFrontPCUserPage.selectFirstAutoshipDateAndClickSave();
		s_assert.assertTrue(storeFrontOrdersPage.verifyAutoShipOrderDate(dateAfterOneMonth),"Next Selected autoship order date is not updated on order page");
		storeFrontOrdersPage.clickOnYourAccountDropdown();
		storeFrontPCUserPage.clickOnPCPerksStatus();
		s_assert.assertTrue(storeFrontPCUserPage.getNextBillAndShipDate().equalsIgnoreCase(dateAfterOneMonth),"Next Bill and ship date one month later is not the updated one");
		storeFrontPCUserPage.clickDelayOrCancelPCPerks();
		storeFrontPCUserPage.clickChangeMyAutoshipDateButton();
		storeFrontPCUserPage.selectSecondAutoshipDateAndClickSave();
		s_assert.assertTrue(storeFrontOrdersPage.verifyAutoShipOrderDate(dateAfterThreeMonth),"Next Selected autoship order date is not updated on order page");
		storeFrontOrdersPage.clickOnYourAccountDropdown();
		storeFrontPCUserPage.clickOnPCPerksStatus();
		s_assert.assertTrue(storeFrontPCUserPage.getNextBillAndShipDate().equalsIgnoreCase(dateAfterThreeMonth),"Next Bill and ship date is two month later not the updated one");
		s_assert.assertAll();
	}

	//Hybris Project-2090:10% off, free shipping for terms and conditions for the PC Perks Program
	@Test 
	public void testFreeShippingTermsAndConditionsForPCPerks_2090() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);  
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress = firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		storeFrontHomePage.clickOnCheckoutButton();
		storeFrontHomePage.enterNewPCDetails(firstName, TestConstants.LAST_NAME+randomNum, emailAddress,password);
		s_assert.assertTrue(storeFrontHomePage.validateDiscountForEnrollingAsPCUser(TestConstants.DISCOUNT_TEXT_FOR_PC_USER),"Discount text Checkbox is not checked for pc User");
		s_assert.assertAll();
	}

	//Hybris Project-145:Update PC Template -EDIT Cart , Shipping info, billing info and Save
	@Test 
	public void testUpdatePCTemplate_145() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String lastName = "ln";
		String newShipingAddressName = TestConstants.ADDRESS_NAME+randomNum;
		String newBillingAddressName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontPCUserPage.clickOnAutoshipCart();
		storeFrontPCUserPage.clickOnContinueShoppingLink();
		storeFrontPCUserPage.clickOnAddtoPCPerksButton();
		storeFrontCartAutoShipPage.clickUpdateMoreInfoLink();
		storeFrontUpdateCartPage.clickOnEditShipping();
		storeFrontUpdateCartPage.clickOnEditForDefaultShippingAddress();
		storeFrontUpdateCartPage.enterNewShippingAddressName(newShipingAddressName+" "+lastName);
		storeFrontUpdateCartPage.clickOnSaveShippingProfileAfterEditDuringEnrollment();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifySelectedShippingAddressIsDefault(newShipingAddressName),"selected shipping address is not default");
		String selectedMethodName = storeFrontUpdateCartPage.selectAndGetShippingMethodName();
		storeFrontUpdateCartPage.clickOnNextStepBtnShippingAddress();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifySelectedShippingMethodNameOnUI(selectedMethodName),"Selected Shipping method name is not present on UI");
		storeFrontUpdateCartPage.clickOnEditDefaultBillingProfile();
		storeFrontUpdateCartPage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontUpdateCartPage.clickOnSaveBillingProfile();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyErrorMessageForCreditCardSecurityCode(),"Error message for credit card security code is not present");
		storeFrontUpdateCartPage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontUpdateCartPage.enterNewBillingNameOnCard(newBillingAddressName);
		storeFrontUpdateCartPage.clickOnSaveBillingProfile();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifySelectedbillingProfileIsDefault(newBillingAddressName),"selected billing profile is not default");
		storeFrontUpdateCartPage.clickOnNextStepBtn();
		storeFrontUpdateCartPage.clickUpdateCartBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.isCartUpdateGlobalMessageDisplayed(),"Your Next cart has been updated message not present on UI");
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyUpdatedAddressPresentUpdateCartPg(newShipingAddressName+" "+lastName),"updated address not present on Updated cart page");
		s_assert.assertAll();
	}

	//Hybris Project-4781:Update PC Template -ADD Cart , Shipping info, billing info and Save
	@Test 
	public void testUpdatePCTemplateAddCart_ShippingInfo_billingInfo_Save_4781() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String lastName = "lN";
		String newShipingAddressName = TestConstants.ADDRESS_NAME+randomNum;
		String secondNewBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNumber;
		String newBillingAddressName = TestConstants.BILLING_ADDRESS_NAME+randomNumber;
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontPCUserPage.clickOnAutoshipCart();
		storeFrontPCUserPage.clickOnContinueShoppingLink();
		storeFrontPCUserPage.clickOnAddtoPCPerksButton();
		storeFrontCartAutoShipPage.clickUpdateMoreInfoLink();
		storeFrontUpdateCartPage = new StoreFrontUpdateCartPage(driver);
		storeFrontUpdateCartPage.clickOnEditPaymentBillingProfile();
		String defaultBillingProfile=storeFrontUpdateCartPage.getDefaultSelectedBillingAddressName();
		storeFrontUpdateCartPage.clickAddNewBillingProfileLink();
		storeFrontUpdateCartPage.enterNewBillingNameOnCard(secondNewBillingProfileName+" "+lastName);
		storeFrontUpdateCartPage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontUpdateCartPage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontUpdateCartPage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontUpdateCartPage.selectNewBillingCardAddress();
		storeFrontUpdateCartPage.clickAddANewAddressLink();
		storeFrontUpdateCartPage.enterNewBillingAddressName(newBillingAddressName+" "+lastName);
		storeFrontUpdateCartPage.enterNewBillingAddressLine1(addressLine1);
		storeFrontUpdateCartPage.enterNewBillingAddressCity(city);
		storeFrontUpdateCartPage.selectNewBillingAddressState();
		storeFrontUpdateCartPage.enterNewBillingAddressPostalCode(postalCode);
		storeFrontUpdateCartPage.enterNewBillingAddressPhoneNumber(phoneNumber);
		storeFrontUpdateCartPage.clickOnSaveBillingProfile();
		s_assert.assertTrue(storeFrontUpdateCartPage.isTheBillingAddressPresentOnPage(secondNewBillingProfileName),"Newly created billing address is not present on page");
		s_assert.assertTrue(storeFrontUpdateCartPage.isBillingProfileIsSelectedByDefault(defaultBillingProfile),"Default billing profile is not as expected");
		storeFrontUpdateCartPage.clickOnEditShipping();
		storeFrontUpdateCartPage.clickOnAddANewShippingAddress();
		storeFrontShippingInfoPage = new StoreFrontShippingInfoPage(driver);
		storeFrontShippingInfoPage.enterNewShippingAddressName(newShipingAddressName+" "+lastName);
		storeFrontShippingInfoPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontShippingInfoPage.enterNewShippingAddressCity(city);
		storeFrontShippingInfoPage.selectNewShippingAddressState(state);
		storeFrontShippingInfoPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontShippingInfoPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontUpdateCartPage.clickOnSaveCRPShippingInfo();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyNewlyCreatedShippingAddressIsSelectedByDefault(newShipingAddressName));
		s_assert.assertTrue(storeFrontUpdateCartPage.verifySelectedShippingAddressIsDefault(newShipingAddressName),"selected shipping address is not default");
		String selectedMethodName = storeFrontUpdateCartPage.selectAndGetShippingMethodName();
		storeFrontUpdateCartPage.clickOnNextStepBtnShippingAddress();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifySelectedShippingMethodNameOnUI(selectedMethodName),"Selected Shipping method name is not present on UI");
		storeFrontUpdateCartPage.clickOnNextStepBtn();
		storeFrontUpdateCartPage.clickUpdateCartBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.isCartUpdateGlobalMessageDisplayed(),"Your Next cart has been updated message not present on UI");
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyUpdatedAddressPresentUpdateCartPg(newShipingAddressName+" "+lastName),"updated address not present on Updated cart page");
		s_assert.assertAll();
	}

	//  Hybris Project-141:PC Perks Total Savings Calculation & disclaimer
	@Test 
	public void testPCPerksTotalSavingsCalculation_141() throws InterruptedException{
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontPCUserPage.clickOnWelcomeDropDown();
		storeFrontPCUserPage.clickEditPCPerksLinkPresentOnWelcomeDropDown();
		storeFrontUpdateCartPage = new StoreFrontUpdateCartPage(driver);
		storeFrontUpdateCartPage.clickOnUpdateMoreInfoButton();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyTotalSavingsIsAvailableOnAutoshipCart(),"Total Savings is not available on Autoship cart");
		s_assert.assertAll();
	}

	//Hybris Project-3880:BIZ:Join PCPerk in the Order Summary section -CA Sposnor WITHOUT Pulse
	@Test 
	public void testJoinPCPerksInOrderSummarySection_3880() throws InterruptedException{
		//-----------------------------------------LOCAL VARIABLES---------------------------------------------------------------------------------------------
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		List<Map<String, Object>> sponserList =  null;
		String sponsorID=null;
		//-----------------------------------------DB QUERIES---------------------------------------------------------------------------------------------------------------------------------------------------------------
		sponserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITHOUT_PULSE_RFO,countryId),RFO_DB);
		sponsorID = String.valueOf(getValueFromQueryResult(sponserList, "AccountNumber"));
		//-----------------------------------------TEST--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		storeFrontHomePage.convertComSiteToBizSite(storeFrontHomePage.openPWSSite(country, driver.getEnvironment()));
		storeFrontHomePage.addAProductToCartAndCheckout();
		storeFrontHomePage.enterNewRCDetails(firstName,lastName,emailAddress, password);
		storeFrontHomePage.checkPCPerksCheckBox();
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		storeFrontHomePage.clickOnNotYourSponsorLink();
		storeFrontHomePage.searchCID(sponsorID);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		storeFrontHomePage.enterBillingDetailsAndSave(cardNumber, firstName+" "+lastName, cardExpMonth, cardExpYear, CVV);
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
		storeFrontHomePage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.isPCEnrolledCongratsMessagePresent(), "'Welcome to Rodan + Fields PC Perks' message has not appeared");
		s_assert.assertAll();
	}

	//Hybris Project-3882:COM:Join PCPerk in the Order Summary section -US Sponsor WITH Pulse
	@Test 
	public void testJoinPCPerksInOrderSummarySection_3882() throws InterruptedException{
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNumber;
		String lastName = "lN";
		List<Map<String, Object>> randomConsultantList=null;
		List<Map<String, Object>> sponsorIdList = null;
		String requiredCountry=null;
		String requiredCountryId=null;
		storeFrontHomePage = new StoreFrontHomePage(driver);
		String firstName=TestConstants.FIRST_NAME+randomNumber;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		if(driver.getCountry().equalsIgnoreCase("ca")){
			requiredCountry="us";
			requiredCountryId="236";
		}else{
			requiredCountry="ca";
			requiredCountryId="40";
		}
		//Get Cross Country Sponser With Pulse And PWS.
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",requiredCountry,requiredCountryId),RFO_DB);
		String emailAddressOfSponser= (String) getValueFromQueryResult(randomConsultantList, "Username"); 
		String bizPWSOfSponser=String.valueOf(getValueFromQueryResult(randomConsultantList, "URL"));
		String accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
		// sponser search by Account Number
		sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,accountID),RFO_DB);
		String sponserId = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));

		String comURL=storeFrontHomePage.convertBizSiteToComSite(bizPWSOfSponser);
		String urlToAssert=storeFrontHomePage.convertCountryInPWS(comURL);
		logger.info(" pws to assert is "+urlToAssert);

		//Get .biz PWS from database to start enrolling rc user and upgrading it to pc user
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",driver.getCountry(),countryId),RFO_DB);
		String bizPWSForEnrollment=String.valueOf(getValueFromQueryResult(randomConsultantList, "URL"));
		String accountIdOfConsultant = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
		//Open biz pws of Sponser
		storeFrontHomePage.openConsultantPWS(bizPWSForEnrollment);
		while(true){
			if(driver.getCurrentUrl().contains("sitenotfound")){
				randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",driver.getCountry(),countryId),RFO_DB);
				bizPWSForEnrollment=String.valueOf(getValueFromQueryResult(randomConsultantList, "URL"));
				accountIdOfConsultant = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
				storeFrontHomePage.openConsultantPWS(bizPWSForEnrollment);	
				continue;
			}else
				break;
		}	
		logger.info("biz pws to start enroll is "+bizPWSForEnrollment);
		// sponser search by Account Number
		sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,accountIdOfConsultant),RFO_DB);

		//Hover shop now and click all products link.
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();

		storeFrontHomePage.clickAddToBagButton();

		//Cart page is displayed?
		//1 product is in the Shopping Cart?
		// *** ^^^ ABOVE CHECKS ASSERTED IN NEXT STEP ***
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();

		//Log in or create an account page is displayed?
		s_assert.assertTrue(storeFrontHomePage.isLoginOrCreateAccountPageDisplayed(), "Login or Create Account page is NOT displayed");
		logger.info("Login or Create Account page is displayed");

		//Enter the User information and DO NOT check the "Become a Preferred Customer" checkbox and click the create account button
		storeFrontHomePage.enterNewRCDetails(firstName, TestConstants.LAST_NAME+randomNumber, emailAddress, password);

		//Assert continue without sponser link is not present

		//check pc perks checkbox at checkout page in order summary section.
		storeFrontHomePage.checkPCPerksCheckBox();
		//Click not your sponser link and verify continue without sponser link is present.
		storeFrontHomePage.clickOnNotYourSponsorLink();

		//Enter the Main account info and DO NOT check the "Become a Preferred Customer" and click next
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		logger.info("Main account details entered");

		//Search for sponser and ids.
		storeFrontHomePage.searchCID(sponserId);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();
		//*** NOT REQUIRED CCS validation removed
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		//*** VERIFY NEXT BUTTON BY CLICKING IT ***
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
		String currentURL=driver.getCurrentUrl();
		logger.info(" pws after successful  enroll is "+currentURL);
		s_assert.assertAll();
	}

	//Hybris Project-2157:Place An order as logged in PC User and check for PC Perk Promo
	@Test 
	public void testPlacedAnAdhocOrderAsPCAndChekcForPCPerkPromo_2157() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME_US+randomNum;
		String lastName = "lN";
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontPCUserPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage = new StoreFrontUpdateCartPage(driver);
		storeFrontUpdateCartPage.clickOnBuyNowButton();
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		s_assert.assertFalse(storeFrontUpdateCartPage.verifyPCPerksPromoDuringPlaceAdhocOrder(), "Pc perk promo is available on Main Account Info Page");
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		s_assert.assertFalse(storeFrontUpdateCartPage.verifyPCPerksPromoDuringPlaceAdhocOrder(), "Pc perk promo is available on Billing Info Page");
		storeFrontUpdateCartPage.clickOnDefaultBillingProfileEdit();
		storeFrontUpdateCartPage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontUpdateCartPage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontUpdateCartPage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontUpdateCartPage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontUpdateCartPage.selectNewBillingCardAddress();
		storeFrontUpdateCartPage.clickOnSaveBillingProfile();
		storeFrontUpdateCartPage.clickOnBillingNextStepBtn();
		s_assert.assertFalse(storeFrontUpdateCartPage.verifyPCPerksPromoDuringPlaceAdhocOrder(), "Pc perk promo is available on order confirmation Page");
		storeFrontUpdateCartPage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyOrderPlacedConfirmationMessage(), "Order has been not placed successfully");
		s_assert.assertAll();
	}

	//Hybris Project-2312:Check Prices for PC
	@Test 
	public void testPricesForPC_2312() throws InterruptedException	{
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontHomePage.addAProductToCartAndCheckout();
		s_assert.assertTrue(storeFrontHomePage.validateProductPricingDetailOnSumaaryPage(), "Pricing information related to product is not present on summary page");
		s_assert.assertAll();
	}

	//Hybris Project-2313:Check Prices as Consultant & PC log in
	@Test 
	public void testProductPricesAsConsultantAndPC_2313() throws InterruptedException {
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");	
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();  
		s_assert.assertTrue(storeFrontHomePage.validateRetailProductProcesAttachedToProduct(), "retail product prices attached to product is not displayed");
		storeFrontHomePage.selectProductAndProceedToBuy();
		storeFrontHomePage.clickOnCheckoutButton();
		s_assert.assertTrue(storeFrontHomePage.validateProductPricingDetailOnSumaaryPage(), "Pricing information related to product is not present on summary page");
		storeFrontHomePage.hitBrowserBackBtn();
		logout();
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();  
		s_assert.assertTrue(storeFrontHomePage.validateRetailProductProcesAttachedToProduct(), "retail product prices attached to product is not displayed");
		s_assert.assertAll();
	}

	//Hybris Project-3762:Update the PC Perks Template from US sponsor's BIZ PWS who has Pulse/ PWS
	@Test(enabled=false)//Needs rework
	public void testUpdatePCPerksTemplateFromDifferentUSSponsorBizPWS_3762() throws InterruptedException{
		List<Map<String, Object>> randomPCUserList =  null;
		String pcUserEmailID =null;
		String cc_country="us";
		storeFrontHomePage.convertComSiteToBizSite(storeFrontHomePage.openPWSSite(cc_country, driver.getEnvironment()));
		while(true){
			randomPCUserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_AUTOSHIPS_RFO,countryId),RFO_DB);
			pcUserEmailID = (String) getValueFromQueryResult(randomPCUserList, "UserName");  
			boolean isSiteNotFoundPresent = driver.getCurrentUrl().contains("sitenotfound")|| driver.getCurrentUrl().contains("error");
			if(isSiteNotFoundPresent){
				logger.info("SITE NOT FOUND for the user "+pcUserEmailID);
				storeFrontHomePage.convertComSiteToBizSite(storeFrontHomePage.openPWSSite(cc_country, driver.getEnvironment()));
			}
			else
				break;
		}
		storeFrontHomePage.clickOnAutoshipCart();
		storeFrontUpdateCartPage.clickOnContinueShoppingLink();
		storeFrontUpdateCartPage.selectAProductAndAddItToPCPerks();
		s_assert.assertTrue(storeFrontUpdateCartPage.isCartUpdateGlobalMessageDisplayed(), "cart is not updated!! ");
		s_assert.assertAll(); 
	}

	//Hybris Project-2167:Login as Existing PC and Place an Adhoc Order, Check for Alert message
	@Test 
	public void testLoginAsPCAndPlaceAdhocOrder_CheckAlrtMessage_2167() throws InterruptedException{
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontPCUserPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage.clickOnBuyNowButton();
		storeFrontUpdateCartPage.clickOnCheckoutButtonAfterAddProduct();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyCheckoutConfirmationPOPupPresent(),"pop up not present");
		storeFrontUpdateCartPage.clickOnOkButtonOnCheckoutConfirmationPopUp();
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		storeFrontUpdateCartPage.clickOnBillingNextStepBtn();
		storeFrontUpdateCartPage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyOrderPlacedConfirmationMessage(), "Order has been not placed successfully");
		s_assert.assertAll();
	}

	//Hybris Project-2263:Check PC Perk 1st Order confirmation Screen
	@Test 
	public void testCheckPcPerkIstOrderConfirmationScreen_2263() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);  
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		storeFrontHomePage.clickOnCheckoutButton();
		storeFrontHomePage.enterNewPCDetails(firstName,lastName,emailAddress, password);
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		storeFrontHomePage.clickOnContinueWithoutSponsorLink();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.verifyPCPerksTermsAndConditionsPopup(),"PC Perks terms and conditions popup not visible when checkboxes for t&c not selected and place order button clicked");
		storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
		storeFrontHomePage.clickPlaceOrderBtn();
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		s_assert.assertAll();
	}

	//Hybris Project-3740:Update PC Perks template from sponsor's PWS site
	@Test 
	public void testUpdatePCPerksTemplateFromSponserBizPWS_3740() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);  
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String lastName = "lN";
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		storeFrontHomePage.addAProductToCartAndCheckout();
		storeFrontHomePage.enterNewPCDetails(firstName, TestConstants.LAST_NAME+randomNum, emailAddress,password);
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		//Get Sponser from database.
		List<Map<String, Object>> randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment(),driver.getCountry(),countryId),RFO_DB);
		String accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
		// sponser search by Account Number
		List<Map<String, Object>> sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,accountID),RFO_DB);
		String accountnumber = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
		//Search for sponser and ids.
		storeFrontHomePage.searchCID(accountnumber);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		//Enter Billing Profile
		storeFrontHomePage.enterBillingDetailsAndSave(cardNumber, firstName+" "+lastName, cardExpMonth, cardExpYear, CVV);
		storeFrontHomePage.clickOnPCPerksTermsAndConditionsCheckBoxes();
		storeFrontHomePage.clickPlaceOrderBtn();
		storeFrontHomePage.clickOnRodanAndFieldsLogo();
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		String comUrlOfSponser=driver.getCurrentUrl();
		String bizUrlOfSponser=storeFrontHomePage.convertComSiteToBizSite(comUrlOfSponser);
		logout();
		storeFrontHomePage.openConsultantPWS(bizUrlOfSponser);
		storeFrontPCUserPage=storeFrontHomePage.loginAsPCUser(emailAddress, password);
		storeFrontPCUserPage.clickOnAutoshipCart();
		storeFrontUpdateCartPage=new StoreFrontUpdateCartPage(driver);
		int getProductCountOnCartPage=Integer.parseInt(storeFrontUpdateCartPage.getProductCountOnAutoShipCartPage());
		int expectedProductCount=getProductCountOnCartPage+1;
		storeFrontUpdateCartPage.clickOnContinueShoppingLink();
		storeFrontUpdateCartPage.selectDifferentProductAndAddItToPCPerks();
		s_assert.assertTrue(storeFrontHomePage.verifyNumberOfProductsInAutoshipCart(Integer.toString(expectedProductCount)), "Product in Autoship cart is not as expected");
		//update quantity of existing product.
		int getNewProductCountOnCartPage=storeFrontUpdateCartPage.getDifferentProductCountOnAutoShipCartPage();
		if(getNewProductCountOnCartPage<=2){
			storeFrontUpdateCartPage.addQuantityOfProduct("5");
		}else{
			storeFrontUpdateCartPage.updateQuantityOfProductToTheSecondProduct("5");
		}
		s_assert.assertTrue(storeFrontUpdateCartPage.isCartUpdateGlobalMessageDisplayed(),"auto ship update cart message NOT  displayed ");
		s_assert.assertAll(); 
	}

	//Hybris Project-3860:Update the PC Perks Template from US sponsor's COMPWS who has Pulse/ PWS
	@Test(enabled=false) 
	public void testUpdateThePCPerksTemplateFromUSSponsorCOMPWSWithPulseAndPWS_3860() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);  
		RFO_DB = driver.getDBNameRFO();
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String lastName = "lN";
		country = driver.getCountry();
		storeFrontHomePage = new StoreFrontHomePage(driver);
		String firstName=TestConstants.FIRST_NAME+randomNum;
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		//Select a product with the price less than $80 and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();

		//Enter the User information and DO NOT check the "Become a Preferred Customer" checkbox and click the create account button
		String emailAddress = firstName+randomNum+TestConstants.EMAIL_ADDRESS_SUFFIX;
		storeFrontHomePage.enterNewPCDetails(firstName, TestConstants.LAST_NAME+randomNum,emailAddress, password);

		//Enter the Main account info and DO NOT check the "Become a Preferred Customer" and click next
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		logger.info("Main account details entered");

		String countryName = null;
		//get cross country sponsor
		if(driver.getCountry().equalsIgnoreCase("us")){
			countryId = "40";
			countryName = "ca";
		}else{
			countryId = "236";
			countryName = "us";
		}
		List<Map<String, Object>> sponserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",countryName,countryId),RFO_DB);
		String sponserHavingPulse = String.valueOf(getValueFromQueryResult(sponserList, "AccountID"));
		String sponsorPWS = String.valueOf(getValueFromQueryResult(sponserList, "URL"));
		// sponser search by Account Number
		List<Map<String, Object>> sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,sponserHavingPulse),RFO_DB);
		String idForConsultant = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
		storeFrontHomePage.searchCID(idForConsultant);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();

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
		storeFrontHomePage.clickOnWelcomeDropDown();
		storeFrontHomePage.clickEditPCPerksLinkPresentOnWelcomeDropDown();
		storeFrontCartAutoShipPage = new StoreFrontCartAutoShipPage(driver);
		storeFrontCartAutoShipPage.clickOnContinueShoppingLink();
		storeFrontCartAutoShipPage.clickOnAddToPcPerksButton();
		storeFrontCartAutoShipPage.clickUpdateMoreInfoLink();
		storeFrontCartAutoShipPage.clickUpdateCartBtn();
		storeFrontCartAutoShipPage.clickOnRodanAndFieldsLogo();
		logout();
		driver.get(driver.getURL()+"/"+driver.getCountry());
		storeFrontPCUserPage = storeFrontHomePage.loginAsPCUser(emailAddress, password);
		storeFrontPCUserPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage= new StoreFrontUpdateCartPage(driver);
		storeFrontUpdateCartPage.clickOnBuyNowButton();
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		storeFrontUpdateCartPage.clickEditMainAccountInfoOnCartPage();
		s_assert.assertFalse(storeFrontHomePage.verifyContinueWithoutSponserLinkPresent(),"continue without sponser link present");
		s_assert.assertFalse(storeFrontHomePage.verifyRequestASponsorBtn(),"Request a sponser button is present");
		s_assert.assertFalse(storeFrontHomePage.verifyNotYourSponsorLinkIsPresent(),"Not your Sponser link is not present.");

		storeFrontUpdateCartPage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		storeFrontUpdateCartPage.clickOnBillingNextStepBtn();
		storeFrontUpdateCartPage.clickPlaceOrderBtn();
		//String orderNumber = storeFrontUpdateCartPage.getOrderNumberAfterPlaceOrder();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyOrderPlacedConfirmationMessage(), "Order has been not placed successfully");
		s_assert.assertTrue(driver.getCurrentUrl().contains("corprfo")||driver.getCurrentUrl().contains("qa.rodanandfields"), "PC is not redirecting to corporate site after placed order");
		s_assert.assertAll();  
	}

	//Hybris Project-3879:COM: Join PCPerk in the shipment section - CA Spsonor with Pulse
	@Test(enabled=false)//Covered in PC RC enrollment testRegisterAsPCWithDifferentSponserWithPulseBIZ_3726_3741
	public void testJoinPCPerksInShipmentSection_3879() throws InterruptedException{
		RFO_DB = driver.getDBNameRFO();
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		List<Map<String, Object>> randomConsultantList =null;
		List<Map<String, Object>> sponsorIdList=null;
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNumber;
		String lastName = "lN";
		country = driver.getCountry();
		storeFrontHomePage = new StoreFrontHomePage(driver);
		String firstName=TestConstants.FIRST_NAME+randomNumber;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		String emailAddressOfConsultant = null;
		String bizPWSOfConsultant=null;
		//Get a Sponser for pc user.
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".com",driver.getCountry(),countryId),RFO_DB);
		String emailAddressOfSponser= (String) getValueFromQueryResult(randomConsultantList, "Username"); 
		String comPWSOfSponser=String.valueOf(getValueFromQueryResult(randomConsultantList, "URL"));
		String accountID = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
		// sponser search by Account Number
		sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,accountID),RFO_DB);
		String sponserId = String.valueOf(getValueFromQueryResult(sponsorIdList, "AccountNumber"));
		while(true){		
			//Get .biz PWS from database to start enrolling rc user and upgrading it to pc user
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment()+".biz",driver.getCountry(),countryId),RFO_DB);
			emailAddressOfConsultant= (String) getValueFromQueryResult(randomConsultantList, "Username"); 
			bizPWSOfConsultant=String.valueOf(getValueFromQueryResult(randomConsultantList, "URL"));
			// sponser search by Account Number
			sponsorIdList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_NUMBER_FOR_PWS,accountID),RFO_DB);
			//Open com pws of Sponser
			storeFrontHomePage.openConsultantPWS(bizPWSOfConsultant);
			boolean isError = driver.getCurrentUrl().toLowerCase().contains("sitenotfound");
			if(isError){
				continue;
			}
			else
				break;
		}	
		//Hover shop now and click all products link.
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();

		// Products are displayed?
		s_assert.assertTrue(storeFrontHomePage.areProductsDisplayed(), "quickshop products not displayed");
		logger.info("Quick shop products are displayed");
		//Select a product and proceed to buy it
		storeFrontHomePage.clickAddToBagButton();
		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();

		//Log in or create an account page is displayed?
		s_assert.assertTrue(storeFrontHomePage.isLoginOrCreateAccountPageDisplayed(), "Login or Create Account page is NOT displayed");
		logger.info("Login or Create Account page is displayed");

		//Enter the User information and DO NOT check the "Become a Preferred Customer" checkbox and click the create account button
		storeFrontHomePage.enterNewRCDetails(firstName, TestConstants.LAST_NAME+randomNumber, emailAddress, password);

		//Assert the default consultant.
		s_assert.assertTrue(storeFrontHomePage.getSponserNameFromUIWhileEnrollingPCUser().contains(emailAddressOfConsultant),"Default consultant is not the one whose pws is used");
		//Assert continue without sponser link is not present
		s_assert.assertFalse(storeFrontHomePage.verifyContinueWithoutSponserLinkPresent(), "Continue without Sponser link is present on pws enrollment");
		s_assert.assertTrue(storeFrontHomePage.verifyNotYourSponsorLinkIsPresent(),"Not your Sponser link is not present.");

		//Click not your sponser link and verify continue without sponser link is present.
		storeFrontHomePage.clickOnNotYourSponsorLink();
		s_assert.assertTrue(storeFrontHomePage.verifySponserSearchFieldIsPresent(),"Sponser search field is not present");

		//Search for sponser and ids.
		storeFrontHomePage.searchCID(sponserId);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinueForPCAndRC();
		//verify the  sponser is selected.
		//s_assert.assertTrue(storeFrontHomePage.getSponserNameFromUIWhileEnrollingPCUser().contains(emailAddressOfSponser),"Cross Country Sponser is not selected");
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		s_assert.assertTrue(storeFrontHomePage.isShippingAddressNextStepBtnIsPresent(),"Shipping Address Next Step Button Is not Present");

		//check pc perks checkbox at checkout page in Shipment section.
		storeFrontHomePage.checkPCPerksCheckBox();
		storeFrontHomePage.enterMainAccountInfo(addressLine1,city, state, postalCode, phoneNumber);
		logger.info("Main account details entered");
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
		String currentURL=driver.getCurrentUrl();
		logger.info("pws of sponsor after split is "+comPWSOfSponser.split(":")[1]);
		s_assert.assertTrue(currentURL.toLowerCase().contains(comPWSOfSponser.split(":")[1].toLowerCase()),"After pc Enrollment the site does not navigated to expected url");
		s_assert.assertAll();
	}

	// Hybris Project-2268:Check for PC Perk Promo as PC User and Consultant
	@Test 
	public void testCheckForPcPerkPromoAsPCAndConsultant_2268() throws InterruptedException{
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.mouseHoverProductAndClickQuickInfo();
		s_assert.assertTrue(storeFrontHomePage.isModalWindowExists(),"modal window not exists");
		s_assert.assertTrue(storeFrontHomePage.verifyPCPerksInfoOnModalWindow(),"PC Perks promo message is not present at modal window as a customer");
		storeFrontHomePage.clickOnModalWindowCloseIcon();
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.mouseHoverProductAndClickQuickInfo();
		s_assert.assertTrue(storeFrontHomePage.isModalWindowExists(),"modal window not exists");
		s_assert.assertFalse(storeFrontHomePage.verifyPCPerksInfoOnModalWindow(),"PC Perks promo message is not present at modal window as a customer");
		storeFrontHomePage.clickOnModalWindowCloseIcon();
		navigateToStoreFrontBaseURL();
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.mouseHoverProductAndClickQuickInfo();
		s_assert.assertTrue(storeFrontHomePage.isModalWindowExists(),"modal window not exists");
		s_assert.assertFalse(storeFrontHomePage.verifyPCPerksInfoOnModalWindow(),"PC Perks promo message is not present at modal window as a customer");
		storeFrontHomePage.clickOnModalWindowCloseIcon();
		s_assert.assertAll();
	}


	//Hybris Project-1891:Create Order With Shipping Method UPS Standard Overnight-CAD$30.00
	@Test 
	public void testCreateOrderWithShippingMethodUPStandardOvernight_1891() throws InterruptedException	{
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		storeFrontHomePage.clickOnCheckoutButton();
		String selectedShippingMethod=storeFrontHomePage.selectShippingMethodUPSStandardOvernightUnderShippingSectionAndGetName();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickPlaceOrderBtn();
		storeFrontHomePage.clickOnWelcomeDropDown();
		storeFrontOrdersPage = storeFrontPCUserPage.clickOrdersLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontOrdersPage.verifyOrdersPageIsDisplayed(),"Orders page has not been displayed");
		storeFrontOrdersPage.clickOnFirstAdHocOrder();
		s_assert.assertTrue(storeFrontOrdersPage.verifyShippingMethodOnTemplateAfterAdhocOrderPlaced(selectedShippingMethod), "Selected Shipping Method didn't match with the method on Orders page!!");
		s_assert.assertAll(); 
	}

	//Hybris Project-2143:Check Shipping and Handling Fee for UPS Ground for Order total 0-999999-PC Perk Autoship
	@Test 
	public void testCheckShippingAndHandlingFeeForUPSGround_2143() throws InterruptedException {
		if(country.equalsIgnoreCase("ca")){
			String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
			storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
			Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	 
			storeFrontPCUserPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
			storeFrontUpdateCartPage.clickAddToBagButton(driver.getCountry());
			storeFrontUpdateCartPage.clickOnCheckoutButton();
			storeFrontUpdateCartPage.selectShippingMethodUPSGroundForAdhocOrder();
			s_assert.assertTrue(storeFrontUpdateCartPage.isDeliveryChargesPresent(),"Shipping/Delivery charges on UI is not present");
			s_assert.assertAll();
		}else
			logger.info("CA specific test");	
	}

	// Hybris Project-2146:Check Shipping and Handling Fee for UPS 2Day for Order total 0-999999-PCPerk Autoship
	@Test 
	public void testCheckShippingAndHandlingFeeForUPS2Day_2146() throws InterruptedException{
		if(country.equalsIgnoreCase("ca")){
			String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
			storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
			Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
			storeFrontPCUserPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
			storeFrontUpdateCartPage.clickAddToBagButton(driver.getCountry());
			storeFrontUpdateCartPage.clickOnCheckoutButton();
			storeFrontUpdateCartPage.selectShippingMethod2DayForAdhocOrder();
			s_assert.assertTrue(storeFrontUpdateCartPage.isDeliveryChargesPresent(),"Shipping charges is not present on UI");
			s_assert.assertAll();
		}else
			logger.info("CA specific test");		
	}

	// Hybris Project-2150:Check Shipping and Handling Fee for UPS 1Day for Order total 0-999999-PCPerk
	@Test 
	public void testCheckShippingAndHandlingFeeForUPS1Day_2150() throws InterruptedException  {
		if(country.equalsIgnoreCase("ca")){
			String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
			storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
			Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");				storeFrontHomePage.addAProductToCartAndCheckout();
			storeFrontUpdateCartPage.selectShippingMethodUPSStandardOverNightForAdhocOrder();
			s_assert.assertTrue(storeFrontUpdateCartPage.isDeliveryChargesPresent(),"Shipping charges is not present on UI");
			s_assert.assertAll();		
		}else{
			logger.info("CA specific test");
		}
	}	

	//Hybris Project-1880:Create Adhoc Order with Multiple line items
	@Test(enabled=false)//Already covered in Orders Validation TC-2273
	public void testCreateAdhocOrderWithMultipleLineItems_1880() throws InterruptedException{
		RFO_DB = driver.getDBNameRFO();
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		List<Map<String, Object>> randomPCUserList =  null;
		String pcUserEmailID = null;
		String accountId = null;
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME_US+randomNum;
		String lastName = "lN";
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
		logger.info("login is successful");
		storeFrontPCUserPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage = new StoreFrontUpdateCartPage(driver);
		String firstProductName = storeFrontUpdateCartPage.clickAddToBagAndGetProductName("1");
		storeFrontUpdateCartPage.clickOnContinueShoppingLink();
		String secondProductName = storeFrontUpdateCartPage.clickAddToBagAndGetProductName("2");
		storeFrontUpdateCartPage.clickOnContinueShoppingLink();
		String thirdProductName = storeFrontPCUserPage.clickAddToBagAndGetProductName("3");
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		// get details of adhoc order
		String subtotal = storeFrontUpdateCartPage.getSubtotal();
		logger.info("subtotal ="+subtotal);
		String deliveryCharges = storeFrontUpdateCartPage.getDeliveryCharges();
		logger.info("deliveryCharges ="+deliveryCharges);
		/*		String handlingCharges = storeFrontUpdateCartPage.getHandlingCharges();
		logger.info("handlingCharges ="+handlingCharges);*/
		String tax = storeFrontUpdateCartPage.getTax();
		logger.info("tax ="+tax);
		String total = storeFrontUpdateCartPage.getTotal();
		logger.info("total ="+total);
		String shippingMethod = storeFrontUpdateCartPage.getShippingMethod();
		logger.info("shippingMethod ="+shippingMethod);
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		String BillingAddress = storeFrontUpdateCartPage.getSelectedBillingAddress();
		logger.info("BillingAddress ="+BillingAddress);
		storeFrontUpdateCartPage.clickOnDefaultBillingProfileEdit();
		storeFrontUpdateCartPage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontUpdateCartPage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontUpdateCartPage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontUpdateCartPage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontUpdateCartPage.selectNewBillingCardAddress();
		storeFrontUpdateCartPage.clickOnSaveBillingProfile();
		storeFrontUpdateCartPage.clickOnBillingNextStepBtn();
		storeFrontUpdateCartPage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyOrderPlacedConfirmationMessage(), "Order has been not placed successfully");
		//assert product names
		s_assert.assertTrue(storeFrontUpdateCartPage.getProductNameAtOrderConfirmationPage("2").contains(firstProductName),"When select first product, Product name "+firstProductName+" and at order confirmation page "+storeFrontUpdateCartPage.getProductNameAtOrderConfirmationPage("2"));
		s_assert.assertTrue(storeFrontUpdateCartPage.getProductNameAtOrderConfirmationPage("3").contains(secondProductName),"When select second product, Product name "+secondProductName+" and at order confirmation page "+storeFrontUpdateCartPage.getProductNameAtOrderConfirmationPage("3"));
		s_assert.assertTrue(storeFrontUpdateCartPage.getProductNameAtOrderConfirmationPage("4").contains(thirdProductName),"When select third product, Product name "+thirdProductName+" and at order confirmation page "+storeFrontUpdateCartPage.getProductNameAtOrderConfirmationPage("4"));
		storeFrontConsultantPage = storeFrontUpdateCartPage.clickOnRodanAndFieldsLogo();
		storeFrontPCUserPage.clickOnWelcomeDropDown();
		storeFrontOrdersPage = storeFrontPCUserPage.clickOrdersLinkPresentOnWelcomeDropDown();
		// Get Order Number
		String orderHistoryNumber = storeFrontOrdersPage.getFirstOrderNumberFromOrderHistory();
		storeFrontOrdersPage.clickOrderNumber(orderHistoryNumber);
		s_assert.assertTrue(storeFrontOrdersPage.getSubTotalFromAutoshipTemplate().contains(subtotal),"Adhoc Order template subtotal "+subtotal+" and on UI is "+storeFrontOrdersPage.getSubTotalFromAutoshipTemplate());
		s_assert.assertTrue(storeFrontOrdersPage.getTaxAmountFromAutoshipTemplate().contains(tax),"Adhoc Order template tax "+tax+" and on UI is "+storeFrontOrdersPage.getTaxAmountFromAdhocOrderTemplate());
		s_assert.assertTrue(storeFrontOrdersPage.getGrandTotalFromAutoshipTemplate().contains(total),"Adhoc Order template grand total "+total+" and on UI is "+storeFrontOrdersPage.getGrandTotalFromAutoshipTemplate());
		/*		s_assert.assertTrue(storeFrontOrdersPage.getHandlingAmountFromAutoshipTemplate().contains(handlingCharges),"Adhoc Order template handling amount "+handlingCharges+" and on UI is "+storeFrontOrdersPage.getHandlingAmountFromAutoshipTemplate());
		 */		s_assert.assertTrue(shippingMethod.contains(storeFrontOrdersPage.getShippingMethodFromAutoshipTemplate()),"Adhoc Order template shipping method "+shippingMethod+" and on UI is "+storeFrontOrdersPage.getShippingMethodFromAutoshipTemplate());
		 //assert product names at adhoc template
		 s_assert.assertTrue(storeFrontOrdersPage.getProductNameFromAdhocTemplate("2").contains(firstProductName),"When select first product, Product name "+firstProductName+" and at Adhoc template "+storeFrontOrdersPage.getProductNameFromAdhocTemplate("2"));
		 s_assert.assertTrue(storeFrontOrdersPage.getProductNameFromAdhocTemplate("3").contains(secondProductName),"When select second product, Product name "+secondProductName+" and at Adhoc template "+storeFrontOrdersPage.getProductNameFromAdhocTemplate("3"));
		 s_assert.assertTrue(storeFrontOrdersPage.getProductNameFromAdhocTemplate("4").contains(thirdProductName),"When select third product, Product name "+thirdProductName+" and at Adhoc template "+storeFrontOrdersPage.getProductNameFromAdhocTemplate("4"));
		 s_assert.assertAll();
	}

	//Hybris Project-4013:Logged in RC/PC/ Consultant to Corp site and click on "Buy Now"
	@Test(enabled=false)//Already covered in TC-2307
	public void testLoggedInPCtoCorpSiteAndClickOnBuyNow_4013() throws InterruptedException{
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
		storeFrontPCUserPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontPCUserPage.clickAddToBagButtonWithoutFilter();
		s_assert.assertTrue(storeFrontPCUserPage.isCartPageDisplayed(),"card page is not displayed after clicking buy now button");
		s_assert.assertAll();
	}

	//Hybris Project-4840:Product Prices and SV/QV Values display
	@Test 
	public void testProductPricesAndSVQVValuesDisplay_4840() throws InterruptedException {
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		s_assert.assertTrue(storeFrontHomePage.getPriceInformationOfProductsOnProductsPage().trim().contains("Your Price"),"Price is not shown for the products");
		if(driver.getCountry().equalsIgnoreCase("ca"))
			s_assert.assertTrue(storeFrontHomePage.getPriceInformationOfProductsOnProductsPage().trim().contains("QV"),"SV Value is not shown for the products 5");
		logout();
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		s_assert.assertTrue(storeFrontHomePage.getPriceInformationOfProductsOnProductsPage().trim().contains("Retail"),"Retail Price is not shown for the products");
		s_assert.assertFalse(storeFrontHomePage.getPriceInformationOfProductsOnProductsPage().trim().contains("SV"),"SV Value is shown for the products 6");
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		s_assert.assertTrue(storeFrontHomePage.getPriceInformationOfProductsOnProductsPage().trim().contains("Your Price"),"Price is not shown for the products");
		s_assert.assertFalse(storeFrontHomePage.getPriceInformationOfProductsOnProductsPage().trim().contains("SV"),"SV Value is shown for the products 1");
		logout();
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		s_assert.assertTrue(storeFrontHomePage.getPriceInformationOfProductsOnProductsPage().trim().contains("Retail"),"Retail Price is not shown for the products");
		s_assert.assertFalse(storeFrontHomePage.getPriceInformationOfProductsOnProductsPage().trim().contains("SV"),"SV Value is shown for the products 2");
		String rcUserEmailID = QueryUtils.getRandomActiveRCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId);
		storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasRCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),"Not able to login to the application from RC");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		s_assert.assertTrue(storeFrontHomePage.getPriceInformationOfProductsOnProductsPage().trim().contains("Retail"),"Retail Price is not shown for the products");
		s_assert.assertFalse(storeFrontHomePage.getPriceInformationOfProductsOnProductsPage().trim().contains("SV"),"SV Value is shown for the products 3");
		logout();
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		s_assert.assertTrue(storeFrontHomePage.getPriceInformationOfProductsOnProductsPage().trim().contains("Retail"),"Retail Price is not shown for the products");
		s_assert.assertFalse(storeFrontHomePage.getPriceInformationOfProductsOnProductsPage().trim().contains("SV"),"SV Value is shown for the products 4");
		s_assert.assertAll();
	}

}