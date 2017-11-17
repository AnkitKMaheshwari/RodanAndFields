package com.rf.test.website.storeFront.hybris;

import java.sql.SQLException;
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
import com.rf.pages.website.storeFront.StoreFrontHomePage;
import com.rf.pages.website.storeFront.StoreFrontRCUserPage;
import com.rf.pages.website.storeFront.StoreFrontUpdateCartPage;
import com.rf.test.website.RFWebsiteBaseTest;

public class CartAndCheckoutValidationTest extends RFWebsiteBaseTest{
	private static final Logger logger = LogManager
			.getLogger(CartAndCheckoutValidationTest.class.getName());

	/**
	 * Test Summary: Non Logged in user dont see the mini cart. He adds the product into cart and the mini cart is displayed
	 * Assertions:
	 * No Adhoc cart displayed initially
	 * Adhoc cart is displayed after adding product to the cart
	 * @throws InterruptedException
	 */
	//qTest ID: TC-552 check Mini Cart - Not Logged in
	@Test(enabled=true)
	public void testCheckMiniCartForNotLoggedInUser_552q() throws InterruptedException {
		s_assert.assertFalse(storeFrontHomePage.isAdhocMiniCartDisplayed(), "mini cart is displayed for not registered user");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		s_assert.assertTrue(storeFrontHomePage.isAdhocMiniCartDisplayed(), "mini cart is not being displayed");
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Logged in consultant see autoship cart and when he adds the product into cart ,the mini cart is displayed
	 * Assertions:
	 * Autoship cart is  displayed
	 * Adhoc cart is displayed after adding product to the cart
	 * @throws InterruptedException
	 */
	//qTest ID: TC-545 check Mini Cart - Consultant
	@Test(enabled=true)
	public void testCheckMiniCartForConsultant_545q() throws InterruptedException  {
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application");
		s_assert.assertTrue(storeFrontConsultantPage.verifyConsultantPage(),"Consultant Page doesn't contain Welcome User Message");
		s_assert.assertTrue(storeFrontConsultantPage.validateNextCRPMiniCart(), "next CRP Mini cart is not displayed");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();  
		storeFrontHomePage.selectProductAndProceedToBuy();
		s_assert.assertTrue(storeFrontHomePage.isAdhocMiniCartDisplayed(), "mini cart is not being displayed");
		s_assert.assertAll();
	}

	/***
	 * Test Summary: Login with consultant, add a product to autoship cart, increase the qty, decrease the qty, remove it
	 * Assertions.:
	 * Product pre\
	 * sent on cart after adding
	 * Increasing the quantity works fine
	 * Decreasing the quantity works fine
	 * Product NOT  present on cart after removing
	 * Global message on removing
	 * 
	 * */
	//qTest ID: TC-577 CRP Template - Check Threshold by Add/remove products and increase/decrease qty
	@Test(enabled = true)
	public void testCheckThresholdByAddRemoveProductsAndIncreaseDecreaseQty_577q() {
		String productName=null;
		String positionOfProductOnAutoshipCart=null;
		int existingQuantityOfProduct=0;
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application");
		storeFrontHomePage.clickOnAutoshipCart();
		storeFrontUpdateCartPage.clickOnContinueShoppingLink();
		productName=storeFrontUpdateCartPage.selectProductAndProceedToAddToCRP();
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
	 * Test Summary: Login with consultant, add mulitple billing profiles during checkout and verify the default billing profile in order summary section
	 * Assertions:
	 * Default billing profile name on order summary section
	 */
	//qTest ID: TC-414 Add Multiple Billing profiles and during checkout
	@Test(enabled=true)
	public void testAddMultipeBillingProfileDuringCheckout_414q(){
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String lastName = "lN";
		String defaultBillingProfileName=null;
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application");
		storeFrontHomePage.addAProductToCartAndCheckout();
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		for(int i=1;i<=3;i++){
			firstName=TestConstants.FIRST_NAME+randomNum+i;
			storeFrontHomePage.clickAddNewBillingProfileLink();
			storeFrontHomePage.enterBillingDetailsAndSave(cardNumber, firstName+" "+lastName, cardExpMonth, cardExpYear, CVV);
			s_assert.assertTrue(storeFrontUpdateCartPage.isNewlyCreatedBillingProfileIsSelectedByDefault(firstName),"New Billing Profile is not selected by default on CRP cart page");
		}
		defaultBillingProfileName = storeFrontUpdateCartPage.getDefaultSelectedBillingAddressName();
		storeFrontUpdateCartPage.clickOnBillingNextStepBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.isBillingProfilePresentOnOrderSummarySection(defaultBillingProfileName), "default selected billing profile SHOULD be present in the order summary");
		s_assert.assertAll();
	}

	//Hybris Project-2297:Remove one of the product from Multiple Orderline Cart--> Subtotal is recalculated
	@Test(enabled=false)//covered in //Hybris Project-2302:REmove Product from CArt
	public void testModifyQtyInCartAndValidateSubTotal_2297() throws InterruptedException	 {
		
//		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
//		storeFrontHomePage.applyPriceFilterLowToHigh();
//		storeFrontHomePage.selectProductAndProceedToBuyWithoutFilter();
//		s_assert.assertTrue(storeFrontHomePage.verifyNumberOfProductsInCart("1"), "number of products in the cart is NOT 1");
//		storeFrontHomePage.addQuantityOfProduct("2"); 
//		storeFrontHomePage.addAnotherProduct();
//		storeFrontHomePage.updateQuantityOfProductToTheSecondProduct("2"); 
//		storeFrontHomePage.clickOnCheckoutButton();
//		storeFrontHomePage.clickEditShoppingCartLink();
//		storeFrontHomePage.addQuantityOfProduct("3"); 
//		storeFrontHomePage.updateQuantityOfProductToTheSecondProduct("1");
//		double subtotal1=storeFrontHomePage.getSubTotalOfFirstProduct();
//		double subtotal2=storeFrontHomePage.getSubTotalOfSecondProduct();
//		s_assert.assertTrue(storeFrontHomePage.validateSubTotal(subtotal1, subtotal2), "sub-total is not recalculated accordingly to the updated qty of product(s)");
//		storeFrontHomePage.removefirstProductFromTheCart();
//		s_assert.assertTrue(storeFrontHomePage.getProductRemovedAutoshipTemplateUpdatedMsg().contains(TestConstants.AUTOSHIP_TEMPLATE_PRODUCT_REMOVED_MSG),"Error message for product removal from UI is "+storeFrontHomePage.getProductRemovedAutoshipTemplateUpdatedMsg()+" while expected is "+TestConstants.AUTOSHIP_TEMPLATE_PRODUCT_REMOVED_MSG);
//		subtotal2=0;
//		s_assert.assertTrue(storeFrontHomePage.validateSubTotal(subtotal1, subtotal2), "sub-total is not recalculated accordingly to the updated qty of product(s)");
//		storeFrontHomePage.clickOnCheckoutButton();
//		s_assert.assertAll(); 
	}

	//Hybris Project-2297:Remove one of the product from Multiple Orderline Cart--> Subtotal is recalculated
	//Hybris Project-2121:Remove Product from the cart
	//Hybris Project-2120:Increase the Quantity
	//Hybris Project-2113:Reduce the Quantity
	//Hybris Project-2302:REmove Product from CArt
	@Test(enabled=true)
	public void testValidateRemoveProductsFromCart_2302_2113_2120_2121_2297() throws InterruptedException{
		String productName=null;
		String positionOfProductOnAutoshipCart=null;
		int existingQuantityOfProduct=0;
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application");		
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		productName=storeFrontUpdateCartPage.selectProductAndProceedToBuy();
		s_assert.assertTrue(storeFrontUpdateCartPage.isProductPresentInAutoshipCart(productName), productName+" SHOULD be present on autoship cart after adding it");
		positionOfProductOnAutoshipCart=storeFrontUpdateCartPage.getPositionOfProductOnAutoshipCart(productName);
		System.out.println(positionOfProductOnAutoshipCart);
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
	 * Test Summary: Login with a consultant,goto CRP autoship cart, choose the UPS Ground shipping method and verify its fee in order summary
	 * Assertions:
	 * The delivery charges in order summary should be corresponding to the shipping method selected
	 */
	//Hybris Project-2142:Check Shipping and Handling Fee for UPS Ground for Order total 0-999999
	@Test(enabled=true)
	public void testCheckShippingAndHandlingFeeForUPSGround_2142() {  
		if(country.equalsIgnoreCase("ca")){
			String deliveryChargesOfUPSGround=null;
			String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application");
			storeFrontHomePage.clickOnAutoshipCart();
			storeFrontUpdateCartPage.clickOnUpdateMoreInfoButton();
			storeFrontUpdateCartPage.clickOnEditShipping();
			deliveryChargesOfUPSGround = storeFrontUpdateCartPage.selectShippingMethodUPSGroundForAdhocOrder();
			storeFrontUpdateCartPage.clickOnUpdateCartShippingNextStepBtn();
			storeFrontUpdateCartPage.clickOnNextStepBtn();
			s_assert.assertTrue(storeFrontUpdateCartPage.getDeliveryCharges().contains(deliveryChargesOfUPSGround),"Delivery charges on order confirmation SHOULD be "+deliveryChargesOfUPSGround);
			s_assert.assertAll();
		}
	}

	/**
	 * Test Summary: Login with a consultant,goto CRP autoship cart, choose the UPS 2 Day shipping method and verify its fee in order summary
	 * Assertions:
	 * The delivery charges in order summary should be corresponding to the shipping method selected
	 */
	//qTest ID: TC-566 Check Shipping and Handling Fee for "2Day" for Order total 0-999999-CRP Autoship
	@Test(enabled=true)
	public void testCheckShippingAndHandlingFeeAsPerOrderTotal_566q() throws SQLException, InterruptedException{
		if(country.equalsIgnoreCase("ca")){
			String deliveryChargesOfUPS2Day=null;
			String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application");
			storeFrontHomePage.clickOnAutoshipCart();
			storeFrontUpdateCartPage.clickOnUpdateMoreInfoButton();
			storeFrontUpdateCartPage.clickOnEditShipping();
			deliveryChargesOfUPS2Day = storeFrontUpdateCartPage.selectShippingMethod2DayForAdhocOrder();
			storeFrontUpdateCartPage.clickOnUpdateCartShippingNextStepBtn();
			storeFrontUpdateCartPage.clickOnNextStepBtn();
			s_assert.assertTrue(storeFrontUpdateCartPage.getDeliveryCharges().contains(deliveryChargesOfUPS2Day),"Delivery charges on order confirmation SHOULD be "+deliveryChargesOfUPS2Day);
			s_assert.assertAll();
		}
	}
	
	/**
	 * Test Summary: Login with a consultant,goto CRP autoship cart, choose the UPS Overnight shipping method and verify its fee in order summary
	 * Assertions:
	 * The delivery charges in order summary should be corresponding to the shipping method selected
	 */
	//Hybris Project-2148:Check Shipping and Handling Fee for UPS 1Day for Order total 0-999999
	@Test(enabled=false)//Duplicate of TC - 2149
	public void testCheckShippingAndHandlingFeeForUPS1DayForOrderTotal_0_999999_2148() throws InterruptedException{
		if(country.equalsIgnoreCase("ca")){
			List<Map<String, Object>> randomConsultantList =  null;
			String consultantEmailID = null;
			String deliveryChargesOfUPSOvernight=null;
			boolean isLoginError=false;
			while(true){
				randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
				consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
				storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
				isLoginError = driver.getCurrentUrl().contains("error");
				if(isLoginError){
					logger.info("Login error for the user "+consultantEmailID);
					navigateToStoreFrontBaseURL();
				}
				else
					break;
			}
			storeFrontHomePage.clickOnAutoshipCart();
			storeFrontUpdateCartPage.clickOnUpdateMoreInfoButton();
			storeFrontUpdateCartPage.clickOnEditShipping();
			deliveryChargesOfUPSOvernight = storeFrontUpdateCartPage.selectShippingMethodUPSStandardOverNightForAdhocOrder();
			storeFrontUpdateCartPage.clickOnUpdateCartShippingNextStepBtn();
			storeFrontUpdateCartPage.clickOnNextStepBtn();
			s_assert.assertTrue(storeFrontUpdateCartPage.getDeliveryCharges().contains(deliveryChargesOfUPSOvernight),"Delivery charges on order confirmation SHOULD be "+deliveryChargesOfUPSOvernight);
			s_assert.assertAll();
		}
	}

	//qTest ID: TC-546 Verify The Mini Functionality
	@Test(enabled=true)
	public void testMiniFunctionality_546q() throws InterruptedException{
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		s_assert.assertTrue(storeFrontHomePage.areProductsDisplayed(), "quickshop products not displayed");
		storeFrontHomePage.selectProductAndProceedToBuyWithoutFilter();
		s_assert.assertTrue(storeFrontHomePage.getRemovableProductsCountInTheCart()==1, "number of products in the cart is NOT 1");
//		storeFrontHomePage.clickOnContinueShoppingLink();
//		s_assert.assertTrue(storeFrontHomePage.verifyNumberOfProductsInMiniCart(),"number of products in the mini cart is not 1");
		s_assert.assertAll();	
	}

	//Hybris Project-2113:Reduce the Quantity
	@Test(enabled=false)//covered in TC -2302
	public void testReduceTheQuantity_2113() throws InterruptedException{
		String qtyIncrease = "2";
		String qtyReduce = "1";
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.applyPriceFilterLowToHigh();
		storeFrontHomePage.clickAddToBagButtonWithoutFilter();
		double subTotalOfAddedProduct = storeFrontHomePage.getSubTotalOnShoppingCartPage();
		storeFrontHomePage.addQuantityOfProduct(qtyIncrease);
		s_assert.assertTrue(storeFrontHomePage.validateAutoshipTemplateUpdatedMsgAfterIncreasingQtyOfProducts(),"update message not coming as expected");
		double subTotalOfAfterUpdate = storeFrontHomePage.getSubTotalOnShoppingCartPage();
		s_assert.assertTrue(storeFrontHomePage.verifySubTotalAccordingToQuantity(qtyIncrease,subTotalOfAddedProduct,subTotalOfAfterUpdate),"subTotal is not updated with increased quantity");
		storeFrontHomePage.addQuantityOfProduct(qtyReduce);
		s_assert.assertTrue(storeFrontHomePage.validateAutoshipTemplateUpdatedMsgAfterIncreasingQtyOfProducts(),"update message not coming as expected");
		double subTotalAfterReduce = storeFrontHomePage.getSubTotalOnShoppingCartPage();
		s_assert.assertTrue(storeFrontHomePage.verifySubTotalAccordingToQuantity(qtyReduce,subTotalOfAddedProduct,subTotalAfterReduce),"subTotal is not updated with reduced quantity");
		s_assert.assertAll();	  
	}

	//Hybris Project-2120:Increase the Quantity
	@Test(enabled=false)//covered in TC -2302
	public void testIncreaseTheQuantity_2120() throws InterruptedException{
		String qtyIncrease = "2";
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.clickAddToBagButtonWithoutFilter();
		double subTotalOfAddedProduct = storeFrontHomePage.getSubTotalOnShoppingCartPage();
		storeFrontHomePage.addQuantityOfProduct(qtyIncrease);
		double subTotalOfAfterUpdate = storeFrontHomePage.getSubTotalOnShoppingCartPage();
		s_assert.assertTrue(storeFrontHomePage.verifySubTotalAccordingToQuantity(qtyIncrease,subTotalOfAddedProduct,subTotalOfAfterUpdate),"subTotal is not updated with increased quantity");
		s_assert.assertAll();
	}

	//Hybris Project-2121:Remove Product from the cart
	@Test(enabled=false)//covered in TC -2302
	public void testRemoveProductFromTheCart_2121() throws InterruptedException{
		String qtyIncrease = "2";
		String qtyReduce = "1";
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.applyPriceFilterLowToHigh();
		storeFrontHomePage.clickAddToBagButtonWithoutFilter();
		double subTotalOfAddedProduct = storeFrontHomePage.getSubTotalOnShoppingCartPage();
		storeFrontHomePage.addQuantityOfProduct(qtyIncrease);
		s_assert.assertTrue(storeFrontHomePage.validateAutoshipTemplateUpdatedMsgAfterIncreasingQtyOfProducts(),"update message not coming as expected");
		double subTotalOfAfterUpdate = storeFrontHomePage.getSubTotalOnShoppingCartPage();
		s_assert.assertTrue(storeFrontHomePage.verifySubTotalAccordingToQuantity(qtyIncrease,subTotalOfAddedProduct,subTotalOfAfterUpdate),"subTotal is not updated with increased quantity");
		storeFrontHomePage.addQuantityOfProduct(qtyReduce);
		s_assert.assertTrue(storeFrontHomePage.validateAutoshipTemplateUpdatedMsgAfterIncreasingQtyOfProducts(),"update message not coming as expected");
		double subTotalAfterReduce = storeFrontHomePage.getSubTotalOnShoppingCartPage();
		s_assert.assertTrue(storeFrontHomePage.verifySubTotalAccordingToQuantity(qtyReduce,subTotalOfAddedProduct,subTotalAfterReduce),"subTotal is not updated with reduced quantity");
		storeFrontHomePage.deleteTheOnlyAddedProductInTheCart();
		s_assert.assertTrue(storeFrontHomePage.getMessageFromTheCart().contains(TestConstants.PRODUCT_HAS_BEEN_REMOVED_FROM_CART_MSG.toLowerCase().trim()),"expected message after removing the product is "+TestConstants.PRODUCT_HAS_BEEN_REMOVED_FROM_CART_MSG.toLowerCase().trim()+" but getting "+storeFrontHomePage.getMessageFromTheCart());
		s_assert.assertTrue(storeFrontHomePage.isCartEmpty(), "cart is not empty after removing all the products");
		s_assert.assertAll();
	}

	//Hybris Project-2293 withoutloggin in add product to cart--> click ContinueShopping
	@Test(enabled=false)//covered in 2292
	public void testWithOutLoginQuickShopScreen_2293() throws InterruptedException	{
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		storeFrontHomePage.clickOnContinueShoppingLink();
		s_assert.assertTrue(storeFrontHomePage.validateQuickShopScreen(),"QuickShop page is not displayed");
		s_assert.assertAll();
	}

	//Hybris Project-2293 withoutloggin in add product to cart--> click ContinueShopping
	// Hybris Project-2292:Continue Shopping - logged in
	@Test(enabled=true)
	public void testQuickShopScreenWithRegisteredUser_2292_2293() throws InterruptedException {
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		storeFrontHomePage.clickOnContinueShoppingLink();
		s_assert.assertTrue(storeFrontHomePage.validateQuickShopScreen(),"QuickShop page is not displayed for Consultant");
		logout();
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		storeFrontHomePage.clickOnContinueShoppingLink();
		s_assert.assertTrue(storeFrontHomePage.validateQuickShopScreen(),"QuickShop page is not displayed for PC");
		logout();
		String rcUserEmailID = QueryUtils.getRandomActiveRCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId);
		storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasRCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),"Not able to login to the application from RC");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		storeFrontHomePage.clickOnContinueShoppingLink();
		s_assert.assertTrue(storeFrontHomePage.validateQuickShopScreen(),"QuickShop page is not displayed for RC");
		logout();
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductAndProceedToBuy();
		storeFrontHomePage.clickOnContinueShoppingLink();
		s_assert.assertTrue(storeFrontHomePage.validateQuickShopScreen(),"QuickShop page is not displayed for non logged in user");
		s_assert.assertAll();
	}

	//Hybris Project-2325:CAtegory Product List for Consultant
	@Test(enabled=true)
	public void testVerifyCategoryProductListPage_2325() throws InterruptedException{
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		s_assert.assertTrue(storeFrontHomePage.verifyProductInfoPresentOnQuikShopProducts(),"Product Info not present in quikshop product page");
		s_assert.assertTrue(storeFrontHomePage.verifyRetailPricePresentInProductInfo(),"Retail Price not present in product info");
		s_assert.assertTrue(storeFrontHomePage.verifyPCPricePresentInProductInfo(),"PC Price not present in product info");
		s_assert.assertTrue(storeFrontHomePage.verifyBuyNowButtonPresentInProductInfo(),"Buy now button not present in poduct info");
		s_assert.assertTrue(storeFrontHomePage.verifyAddToCRPButtonPresent(),"Add to crp Button not present in product info");
		s_assert.assertAll();
	}

	//Hybris Project-2262:View QV/SV value in the cart
	@Test(enabled=true)
	public void testCreateAdhocOrderConsultantAndVerifySVValue_2262() throws InterruptedException{
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage.clickAddToBagButton(driver.getCountry());
		s_assert.assertTrue(storeFrontUpdateCartPage.verifySVValueOnCartPage(),"SV Value is not present on cart page");
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifySVValueOnOrderSummaryPage(),"SV Value is not present on order summary page");
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		storeFrontUpdateCartPage.clickOnBillingNextStepBtn();
		storeFrontUpdateCartPage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifySVValueOnOrderConfirmationPage(), "SV Value is not present on order confirmation page");
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyOrderPlacedConfirmationMessage(), "Order has been not placed successfully");
		storeFrontConsultantPage = storeFrontUpdateCartPage.clickOnRodanAndFieldsLogo();
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontOrdersPage = storeFrontConsultantPage.clickOrdersLinkPresentOnWelcomeDropDown();
		String orderHistoryNumber = storeFrontOrdersPage.getFirstOrderNumberFromOrderHistory();
		storeFrontOrdersPage.clickOrderNumber(orderHistoryNumber);
		s_assert.assertTrue(storeFrontOrdersPage.verifySVValueOnOrderPage(),"Product SV Value is not present on order page.");
		s_assert.assertAll();
	}

	// Hybris Project-2305:Filter Product based on Price
	@Test(enabled=true)
	public void testFilterProductBasedOnPrice_2305() throws InterruptedException{
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.getProductPriceBeforeApplyFilter();
		storeFrontHomePage.applyPriceFilterLowToHigh();
		s_assert.assertTrue(storeFrontHomePage.verifyPriceFromLowToHigh(), "Prices are not in format from low to high");
		storeFrontHomePage.applyPriceFilterHighToLow();
		s_assert.assertTrue(storeFrontHomePage.verifyPriceFromHighTolow(), "Prices are not in format from high to low");
		s_assert.assertAll(); 

	}

	//Hybris Project-2306:Filter Product on Category
	@Test(enabled=true)
	public void testFilterProductOnCategory_2306() throws InterruptedException{
		int randomNumberForProductCategory = CommonUtils.getRandomNum(1,6);
		int randomNumberForProductPrice = CommonUtils.getRandomNum(1,3);
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.selectProductCategory(TestConstants.REGIMEN_NAME_REDEFINE);
		s_assert.assertTrue(storeFrontHomePage.isProductCategoryPageSelected(TestConstants.REGIMEN_NAME_REDEFINE),"Redefine product category page is not present.");
		storeFrontHomePage.clickOnClearAll();
		storeFrontHomePage.selectProductCategory(TestConstants.REGIMEN_NAME_REVERSE);
		s_assert.assertTrue(storeFrontHomePage.isProductCategoryPageSelected(TestConstants.REGIMEN_NAME_REVERSE),"Reverse product category page is not present. 8");
		storeFrontHomePage.clickOnClearAll();
		storeFrontHomePage.selectProductCategory(TestConstants.REGIMEN_NAME_SOOTHE);
		s_assert.assertTrue(storeFrontHomePage.isProductCategoryPageSelected(TestConstants.REGIMEN_NAME_SOOTHE),"Soothe product category page is not present15.");
		storeFrontHomePage.clickOnClearAll();
		if(driver.getCountry().equalsIgnoreCase("CA")){
			storeFrontHomePage.selectProductCategory(TestConstants.REGIMEN_NAME_UNBLEMISH);
			s_assert.assertTrue(storeFrontHomePage.isProductCategoryPageSelected(TestConstants.REGIMEN_NAME_UNBLEMISH),"Unblemish product category page is not present.22");
			storeFrontHomePage.clickOnClearAll();
		}
		storeFrontHomePage.selectProductCategory(TestConstants.REGIMEN_NAME_ESSENTIALS);
		s_assert.assertTrue(storeFrontHomePage.isProductCategoryPageSelected(TestConstants.REGIMEN_NAME_ESSENTIALS),"Essentials product category page is not present 29.");
		storeFrontHomePage.clickOnClearAll();
		storeFrontHomePage.selectProductCategory(TestConstants.REGIMEN_NAME_ENHANCEMENTS);
		s_assert.assertTrue(storeFrontHomePage.isProductCategoryPageSelected(TestConstants.REGIMEN_NAME_ENHANCEMENTS),"Enhancements product category page is not present36.");
		storeFrontHomePage.clickOnClearAll();
		storeFrontHomePage.selectRandomProductCategory(randomNumberForProductCategory);
		String productPriceRange = storeFrontHomePage.selectAndGetRandomProductPriceFilter(randomNumberForProductPrice);
		s_assert.assertTrue(storeFrontHomePage.verifyProductPriceForRandomProduct(productPriceRange),"Random Selected Product Price is Greater than the product price filter applied.");
		storeFrontHomePage.clickOnClearAll();
		storeFrontHomePage.applyPriceFilterHighToLow();
		s_assert.assertTrue(storeFrontHomePage.isHighToLowProductPriceFilterIsAppliedSuccessfully(),"Product Price filter from high to low price is not applied");
		storeFrontHomePage.applyPriceFilterLowToHigh();
		s_assert.assertTrue(storeFrontHomePage.isLowToHighProductPriceFilterIsAppliedSuccessfully(),"Product Price filter from low to high price is not applied");
		s_assert.assertAll();
	}

	//Hybris Project-2170:Login as Existing Consultant and Place an Adhoc Order - check Alert Message
	@Test(enabled=true)
	public void testExistingConsultantPlaceAnAdhocOrderAndCheckForAlertMessage_2170() throws InterruptedException{
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontConsultantPage.clickAddToBagButton();
		storeFrontConsultantPage.clickOnCheckoutButtonAfterAddProduct();
		s_assert.assertTrue(storeFrontConsultantPage.verifyCheckoutConfirmationPOPupPresent(),"Checkout Confirmation pop up not present");
		s_assert.assertTrue(storeFrontConsultantPage.verifyCheckoutConfirmationPopUpMessageConsultant(),"Checkout Confirmation pop up message is not present as expected");
		storeFrontConsultantPage.clickOnOkButtonOnCheckoutConfirmationPopUp();
		s_assert.assertTrue(storeFrontConsultantPage.verifyAccountInfoPageHeaderPresent(),"Account info page header is not present");
		s_assert.assertAll();
	}

	//Hybris Project-2141:Check Shipping and Handling Fee for UPS Ground for Order total 1000-999999
	@Test(enabled=false)//Duplicate of TC-2142
	public void testShippingAndHandlingFeeForUPSGroundForOrderTotal_1000_999999_2141() throws InterruptedException{
		List<Map<String, Object>> randomConsultantList =  null;
		String consultantEmailID = null;
		String accountId = null;
		String quantity = "10";
		while(true){
			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),RFO_DB);
			consultantEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");  
			accountId = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
			logger.info("Account Id of the user is "+accountId);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			boolean isLoginError = driver.getCurrentUrl().contains("error");
			if(isLoginError){
				logger.info("Login error for the user "+consultantEmailID);
				driver.get(driver.getURL()+"/"+driver.getCountry());
			}
			else
				break;
		}
		s_assert.assertTrue(storeFrontConsultantPage.verifyConsultantPage(),"Consultant User Page doesn't contain Welcome User Message");
		logger.info("login is successful");
		storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontConsultantPage.applyPriceFilterHighToLow();
		storeFrontConsultantPage.clickAddToBagButton(driver.getCountry());

		while(true){
			storeFrontConsultantPage.addQuantityOfProduct(quantity);
			String total = storeFrontUpdateCartPage.getTotalPriceOfProduct();
			String orderTotal = total.split("\\$")[1].trim();
			String[] getTotal = orderTotal.split("\\,");
			String finalTotal = getTotal[0]+getTotal[1];
			System.out.println("Order total for consultant"+finalTotal);
			double totalFromUI = Double.parseDouble(finalTotal);
			if(totalFromUI<1000){
				continue;
			}else{
				break;
			}
		}
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		storeFrontUpdateCartPage.selectShippingMethodUPSGroundForAdhocOrder();

		String deliveryCharges = String.valueOf(storeFrontUpdateCartPage.getDeliveryCharges());
		logger.info("deliveryCharges ="+deliveryCharges);
		if(driver.getCountry().equalsIgnoreCase("CA") || driver.getCountry().equalsIgnoreCase("AU")){
			s_assert.assertTrue(storeFrontUpdateCartPage.isDeliveryChargesPresent(),"Shipping charges is not present on UI");
		}else if(driver.getCountry().equalsIgnoreCase("US")){
			s_assert.assertTrue(deliveryCharges.equalsIgnoreCase("$25.00"),"Shipping charges is not present on UI");
		}
		storeFrontUpdateCartPage.clickOnRodanAndFieldsLogo();
		logout();

		driver.get(driver.getURL()+"/"+driver.getCountry());
		List<Map<String, Object>> randomPCUserList =  null;
		String pcUserEmailID = null;
		storeFrontHomePage = new StoreFrontHomePage(driver);
		while(true){
			randomPCUserList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_AUTOSHIPS_RFO,countryId),RFO_DB);
			pcUserEmailID = (String) getValueFromQueryResult(randomPCUserList, "UserName");  
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
		storeFrontPCUserPage.applyPriceFilterHighToLow();
		storeFrontPCUserPage.clickAddToBagButton(driver.getCountry());

		while(true){
			storeFrontPCUserPage.addQuantityOfProduct(quantity);
			String total = storeFrontUpdateCartPage.getTotalPriceOfProductForPC();
			String orderTotal = total.split("\\$")[1].trim();
			String[] getTotal = orderTotal.split("\\,");
			String finalTotal = getTotal[0]+getTotal[1];
			System.out.println("Order total for consultant"+finalTotal);
			double totalFromUI = Double.parseDouble(finalTotal);
			if(totalFromUI<1000){
				continue;
			}else{
				break;
			}
		}
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		storeFrontUpdateCartPage.selectShippingMethodUPSGroundForAdhocOrder();
		deliveryCharges = String.valueOf(storeFrontUpdateCartPage.getDeliveryCharges());
		logger.info("deliveryCharges ="+deliveryCharges);
		if(driver.getCountry().equalsIgnoreCase("CA") || driver.getCountry().equalsIgnoreCase("AU")){
			//Assert of shipping cost from UI
			s_assert.assertTrue(storeFrontUpdateCartPage.isDeliveryChargesPresent(),"Shipping charges is not present on UI");
		}else if(driver.getCountry().equalsIgnoreCase("US")){
			s_assert.assertTrue(deliveryCharges.equalsIgnoreCase("$25.00"),"Shipping charges on UI is not As per shipping method selected");
		}
		s_assert.assertAll();
	}
	
	//Hybris Project-2295:Without Loggin in, Add Multiple Order line to the cart with Multiple Quantity
	@Test(enabled=true)
	public void testWithoutLogin_AddMultipleOrderLineTotheCartWithMultipleQuantity_2295() throws InterruptedException{
		String quantity = "2";
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.applyPriceFilterLowToHigh();
		storeFrontHomePage.clickAddToBagButtonWithoutFilter();
		String subtotalOfAddedProduct = storeFrontHomePage.getSubTotalOfAddedProduct();
		storeFrontHomePage.addQuantityOfProduct(quantity);
		s_assert.assertTrue(storeFrontHomePage.validateSubTotalAfterQuantityIncreased(subtotalOfAddedProduct,quantity),"subtotal not present on UI as expected");
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Login with a consultant, add a product to adhoc cart,chekout, choose the UPS Ground shipping method and verify its fee in order summary
	 * Assertions:
	 * The delivery charges in order summary should be corresponding to the shipping method selected
	 */
	//Hybris Project-2151:Check the shipping method disclaimers for " UPS Ground (HD)/FedEX"
	@Test(enabled=true)
	public void testCheckShippingMethodForUPSGroundHD_FedEX_2151() throws InterruptedException{
		if(country.equalsIgnoreCase("ca")){
			String deliveryChargesOfUPSGround=null;
			String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
			storeFrontHomePage.addAProductToCartAndCheckout();
			storeFrontUpdateCartPage.clickOnEditShipping();
			deliveryChargesOfUPSGround = storeFrontUpdateCartPage.selectShippingMethodUPSGroundForAdhocOrder();
			storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		    storeFrontUpdateCartPage.clickOnBillingNextStepBtn();
			s_assert.assertTrue(storeFrontUpdateCartPage.getDeliveryCharges().contains(deliveryChargesOfUPSGround),"Delivery charges on order confirmation SHOULD be "+deliveryChargesOfUPSGround);
			s_assert.assertAll();
		}
	}

	/**
	 * Test Summary: Login with a consultant, add a product to adhoc cart,chekout, choose the UPS 2 Day shipping method and verify its fee in order summary
	 * Assertions:
	 * The delivery charges in order summary should be corresponding to the shipping method selected
	 */
	//Hybris Project-2152:Check the shipping method disclaimers for " UPS 2Day/FedEx 2Day"
	@Test(enabled=true)
	public void testCheckShippingMethodForUPS2Day_FedEx2Day_2152() throws InterruptedException{
		if(country.equalsIgnoreCase("ca")){
			String deliveryChargesOfUPS2Day=null;
			String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
			storeFrontHomePage.addAProductToCartAndCheckout();
			storeFrontUpdateCartPage.clickOnEditShipping();
			deliveryChargesOfUPS2Day = storeFrontUpdateCartPage.selectShippingMethod2DayForAdhocOrder();
			storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
			storeFrontUpdateCartPage.clickOnBillingNextStepBtn();
			s_assert.assertTrue(storeFrontUpdateCartPage.getDeliveryCharges().contains(deliveryChargesOfUPS2Day),"Delivery charges on order confirmation SHOULD be "+deliveryChargesOfUPS2Day);
			s_assert.assertAll();
		}
	}

	//Hybris Project-2314:Check Product prices without logging in
	@Test(enabled=true)
	public void testProductPricesWithOutLoggingIn_2314(){
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		s_assert.assertTrue(storeFrontHomePage.validateRetailProductProcesAttachedToProduct(), "retail product prices attached to product is not displayed");
		s_assert.assertAll();
	}

	// Hybris Project-2271:check with Browser back button from checkout to cart
	@Test(enabled=true)
	public void testBrowserBackButtonFromCheckoutToCart_2271() throws InterruptedException		{
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontHomePage.addAProductToCartAndCheckout();
		storeFrontHomePage.hitBrowserBackBtn();
		storeFrontHomePage.isAdhocMiniCartDisplayed();
		s_assert.assertAll();
	}

	//Hybris Project-2286:Place an Order with existing customer - AUTOMATION ONLY
	@Test(enabled=false)//No Such functionality on UI now
	public void testPlaceOrderWithExistingCustomer_2286() throws InterruptedException{
		List<Map<String, Object>> accountNameDetailsList = null;
		List<Map<String, Object>> randomConsultantList =  null;
		String firstNameDB = null;
		String lastNameDB = null;
		String consultantEmailID = null;
		String accountID = null;
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
		storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage.clickAddToBagButton(driver.getCountry());
		storeFrontUpdateCartPage.clickOnCheckoutButton();

		//assert First Name with RFO
		accountNameDetailsList = DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguement(DBQueries_RFO.GET_ACCOUNT_DETAILS_QUERY,consultantEmailID), RFO_DB);
		firstNameDB = (String) getValueFromQueryResult(accountNameDetailsList, "FirstName");
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyFirstNameAndLastNameAndEmailAddressFromUIOnAccountInfoPage(firstNameDB), "First Name on UI is different from DB");

		// assert Last Name with RFO
		lastNameDB = (String) getValueFromQueryResult(accountNameDetailsList, "LastName");
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyFirstNameAndLastNameAndEmailAddressFromUIOnAccountInfoPage(lastNameDB), "Last Name on UI is different from DB");
		storeFrontUpdateCartPage.clickEditMainAccountInfoOnCartPage();

		//assert for email address
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyFirstNameAndLastNameAndEmailAddressFromUIOnAccountInfoPage(consultantEmailID), "Email Address on UI is different from DB");

		//verify that user can edit main account info.
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyUserCanEditMainAccountInfoOnCartPage(),"User cannot edit main account info");
		s_assert.assertAll();
	}

	// Hybris Project-2291:Adhoc shopping cart is persistent when user is navigating among PWS sites ad corp site
	@Test(enabled=true)
	public void testAdhocShoppingCartIsPersistentForBizAndCom_2291() throws InterruptedException{
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage = new StoreFrontUpdateCartPage(driver);
		storeFrontUpdateCartPage.clickAddToBagButton(driver.getCountry());
		s_assert.assertTrue(storeFrontHomePage.isCartPageDisplayed(), "Cart page is not displayed");
		logout();
		String bizPWS=storeFrontHomePage.openPWSSite(country, env);
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage = new StoreFrontUpdateCartPage(driver);
		storeFrontUpdateCartPage.clickAddToBagButton(driver.getCountry());
		s_assert.assertTrue(storeFrontHomePage.isCartPageDisplayed(), "Cart page is not displayed");
		logger.info("Cart page is displayed");
		logout();
		String comPWS=storeFrontHomePage.convertBizSiteToComSite(bizPWS);
		storeFrontHomePage.openConsultantPWS(comPWS);
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage = new StoreFrontUpdateCartPage(driver);
		storeFrontUpdateCartPage.clickAddToBagButton(driver.getCountry());
		s_assert.assertTrue(storeFrontHomePage.isCartPageDisplayed(), "Cart page is not displayed");
		logger.info("Cart page is displayed");
		s_assert.assertAll();
	}

	// Hybris Project-4657:Place an adhoc order for RC user enrolled without creating an order
	@Test(enabled=true)
	public void testPlaceAdhocOrderForRcUserEnrolledWithoutCreatingAnOrder_4657() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String firstName = TestConstants.FIRST_NAME+randomNum;
		String lastName = TestConstants.LAST_NAME;
		String emailAddress = firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		storeFrontHomePage.openPWSSite(country, env);
		storeFrontHomePage.clickSignUpnowOnbizSite();
		storeFrontHomePage.enterNewRCDetails(firstName, lastName, emailAddress, password);
		s_assert.assertTrue(storeFrontHomePage.verifyWelcomeDropdownToCheckUserRegistered(), "User NOT registered successfully");
		storeFrontRCUserPage = new StoreFrontRCUserPage(driver);
		storeFrontRCUserPage.clickOnWelcomeDropDown();
		storeFrontAccountInfoPage = storeFrontRCUserPage.clickAccountInfoLinkPresentOnWelcomeDropDown();
		storeFrontAccountInfoPage.updateAccountInformation(firstName, lastName, addressLine1, city, postalCode, phoneNumber);
		s_assert.assertTrue(storeFrontAccountInfoPage.verifyConfirmationMessagePresentOnUI(),"Your profile has been updated message not present");
		storeFrontAccountInfoPage.clickOnWelcomeDropDown();
		storeFrontShippingInfoPage = storeFrontHomePage.clickShippingLinkPresentOnWelcomeDropDown();
		storeFrontShippingInfoPage.clickAddNewShippingProfileLink();
		storeFrontShippingInfoPage.enterNewShippingAddressName(firstName+" "+lastName);
		storeFrontShippingInfoPage.enterNewShippingAddressCity(city);
		storeFrontShippingInfoPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontShippingInfoPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontShippingInfoPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontShippingInfoPage.selectNewShippingAddressState(state);
		storeFrontShippingInfoPage.clickOnSaveShippingProfile();
		s_assert.assertTrue(storeFrontShippingInfoPage.verifyConfirmationMessagePresentOnUI(),"Your profile has been updated message not present");
		storeFrontShippingInfoPage.clickOnWelcomeDropDown();
		storeFrontBillingInfoPage = storeFrontShippingInfoPage.clickBillingInfoLinkPresentOnWelcomeDropDown();
		storeFrontBillingInfoPage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontBillingInfoPage.enterNewBillingNameOnCard(firstName);
		storeFrontUpdateCartPage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontBillingInfoPage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontBillingInfoPage.selectNewBillingCardAddress();
		storeFrontBillingInfoPage.clickOnSaveBillingProfile();
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontHomePage.clickAddToBagButton();
		storeFrontHomePage.clickOnCheckoutButton();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.isOrderPlacedSuccessfully(),"Order Not placed successfully");
		s_assert.assertAll();
	}

	// Hybris Project-3857:Place Adhoc Order as RC - AUTOMATION ONLY
	@Test(enabled=false)

	//	******IF THIS TEST FAILS, PULL IT OUT AS IT IS REDUNDANT ANYWAY****
	public void testPlaceAdhocOrderAsRC_3857() throws InterruptedException{
		RFO_DB = driver.getDBNameRFO();
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME+randomNum;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String lastName = "lN";
		storeFrontHomePage = new StoreFrontHomePage(driver);
		storeFrontHomePage.openPWSSite(driver.getCountry(), driver.getEnvironment());
		// Click on our product link that is located at the top of the page and then click in on quick shop

		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();

		// Products are displayed?
		s_assert.assertTrue(storeFrontHomePage.areProductsDisplayed(), "quickshop products not displayed");
		logger.info("Quick shop products are displayed");

		//Select a product and proceed to buy it
		storeFrontHomePage.selectProductAndProceedToBuy();

		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();

		//Log in or create an account page is displayed?
		s_assert.assertTrue(storeFrontHomePage.isLoginOrCreateAccountPageDisplayed(), "Login or Create Account page is NOT displayed");
		logger.info("Login or Create Account page is displayed");

		//Enter the User information and DO NOT check the "Become a Preferred Customer" checkbox and click the create account button
		String emailAddress = firstName+randomNum+TestConstants.EMAIL_ADDRESS_SUFFIX;
		storeFrontHomePage.enterNewRCDetails(firstName, TestConstants.LAST_NAME+randomNum, emailAddress, password);

		//Enter the Main account info and DO NOT check the "Become a Preferred Customer" and click next
		storeFrontHomePage.enterMainAccountInfo();
		logger.info("Main account details entered");

		//		String accountID;// = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountID"));
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();

		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		//Enter Billing Profile
		storeFrontHomePage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontUpdateCartPage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.selectNewBillingCardAddress();
		storeFrontHomePage.clickOnSaveBillingProfile();
		storeFrontHomePage.clickOnBillingNextStepBtn();
		storeFrontHomePage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontHomePage.isOrderPlacedSuccessfully(), "Order Not placed successfully");
		//**** TEST IS DONE HERE NO NEED TO CHANGE SPONSORS OR DO MORE ****
		s_assert.assertAll();	

	}

	//Hybris Project-2308:Add 20 Products to the Cart and Update Quantity from Product Details screen
	@Test(enabled=true)
	public void testAdd_20_ProductsToTheCartAndUpdateQuantityFromProductDetailsScreen_2308() throws InterruptedException{
		String qty = "20";
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");	
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontHomePage.clickAddToBagButton();
		storeFrontHomePage.addQuantityOfProduct(qty);
		s_assert.assertTrue(storeFrontHomePage.verifyNumberOfProductsInCart(qty),"number of products in cart has not been updated");
		s_assert.assertTrue(storeFrontHomePage.verifyConfirmationMessagePresentOnUI(),"confirmation message is not present on UI");
		s_assert.assertAll();
	}

	//Hybris Project-4773:Kit products are not displayed during adhoc order
	@Test(enabled=true)
	public void testVerifyKitProductAreNotDisplayedDuringAdhocOrder_4773() throws InterruptedException{
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		s_assert.assertTrue(storeFrontHomePage.areProductsDisplayed(), "quickshop products not displayed");
		s_assert.assertFalse(storeFrontHomePage.isKitProductPresent(), "Kit product is present");
		s_assert.assertTrue(storeFrontHomePage.verifyProductPriceAsPerCountry(driver.getCountry()), "Product Prices are not as per country selected");
		s_assert.assertAll();
	}

	//Hybris Project-2282:Try selecting 2 Shipping address; This is not allowed
	@Test
	public void testTrySelecting2ShippingAddress_2282() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String lastName = "lN";
		String pcUserEmailID = QueryUtils.getRandomActivePCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsPCUser(pcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasPCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_PC_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from PC");	
		storeFrontPCUserPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage.clickOnBuyNowButton();
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		storeFrontUpdateCartPage.clickOnAddANewShippingAddress();
		String firstShippingAddressName = TestConstants.ADDRESS_NAME+randomNum;
		storeFrontUpdateCartPage.enterNewShippingAddressName(firstShippingAddressName+" "+lastName);
		storeFrontUpdateCartPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontUpdateCartPage.enterNewShippingAddressCity(city);
		storeFrontUpdateCartPage.selectNewShippingAddressStateOnCartPage(state);
		storeFrontUpdateCartPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontUpdateCartPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontUpdateCartPage.clickOnSaveShippingProfileAfterEdit();
		randomNum = CommonUtils.getRandomNum(10000, 1000000);
		storeFrontUpdateCartPage.clickOnAddANewShippingAddress();
		String secondShippingAddressName = TestConstants.ADDRESS_NAME+randomNum;
		storeFrontUpdateCartPage.enterNewShippingAddressName(secondShippingAddressName+" "+lastName);
		storeFrontUpdateCartPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontUpdateCartPage.enterNewShippingAddressCity(city);
		storeFrontUpdateCartPage.selectNewShippingAddressStateOnCartPage(state);
		storeFrontUpdateCartPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontUpdateCartPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontUpdateCartPage.clickOnSaveShippingProfileAfterEdit();
		storeFrontUpdateCartPage.selectShippingAddress(firstShippingAddressName);
		s_assert.assertTrue(storeFrontUpdateCartPage.isShippingAddressSelected(firstShippingAddressName), " first shipping address has to be selected");
		s_assert.assertFalse(storeFrontUpdateCartPage.isShippingAddressSelected(secondShippingAddressName), "second shipping address should not  selected");
		storeFrontUpdateCartPage.selectShippingAddress(secondShippingAddressName);
		s_assert.assertFalse(storeFrontUpdateCartPage.isShippingAddressSelected(firstShippingAddressName), "first shipping address  should not be selected");
		s_assert.assertTrue(storeFrontUpdateCartPage.isShippingAddressSelected(secondShippingAddressName), "second shipping address  has to be  selected");
		s_assert.assertAll();		
	}

	//Hybris Project-2368:Check for Error Messages throughout Checkout Flow
	@Test(enabled=true)
	public void testValidateErrorMessagesThroughOutChkOutFlow_2368() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);  
		String lastName = "lN"+randomNum;;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		String emailAddress=firstName+TestConstants.EMAIL_ADDRESS_SUFFIX;
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		s_assert.assertTrue(storeFrontHomePage.areProductsDisplayed(), "quickshop products not displayed");
		storeFrontHomePage.applyPriceFilterLowToHigh();
		storeFrontHomePage.selectProductAndProceedToBuy();
		s_assert.assertTrue(storeFrontHomePage.isCartPageDisplayed(), "Cart page is not displayed");
		s_assert.assertTrue(storeFrontHomePage.verifyNumberOfProductsInCart("1"), "number of products in the cart is NOT 1");
		storeFrontHomePage.clickOnCheckoutButton();
		s_assert.assertTrue(storeFrontHomePage.isLoginOrCreateAccountPageDisplayed(), "Login or Create Account page is NOT displayed");
		storeFrontHomePage.clickCreateActBtnOnChkOutPage();
		s_assert.assertTrue(storeFrontHomePage.validateErrorMessagesForNewCustomerFields(), "Error message(s) for 'New Customer' respective fields are not populated!!");
		storeFrontHomePage.enterNewPCDetails(firstName,lastName,emailAddress, password);
		storeFrontHomePage.clickOnContinueWithoutSponsorLink();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		s_assert.assertTrue(storeFrontHomePage.validateErrorMessagesForMainActInfoFields(), "Error message(s) for respective fields are not populated!!");
		storeFrontHomePage.enterMainAccountInfo(addressLine1, city, state, postalCode, phoneNumber);
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontHomePage.clickOnShippingAddressNextStepBtn();
		storeFrontHomePage.clickOnSaveBillingProfile();
		s_assert.assertTrue(storeFrontHomePage.validateErrorMessagesForAddNewBillingInfoFields(), "Error message(s) for'Add new Billing Info' respective fields are not populated!!");
		s_assert.assertAll(); 
	}

	//Hybris Project-2280:Add Multiple cards and try to select Multiple cards for payment on checkout screen
	@Test(enabled=true)
	public void testAddMultipleCardsAndSelectMultipleCardForPaymentOnCheckoutScreen_2280() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME_US+randomNum;
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage = new StoreFrontUpdateCartPage(driver);
		storeFrontUpdateCartPage.clickAddToBagButton(driver.getCountry());
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		storeFrontUpdateCartPage.clickAddNewBillingProfileLink();
		storeFrontUpdateCartPage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontUpdateCartPage.enterNewBillingNameOnCard(newBillingProfileName);
		storeFrontUpdateCartPage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontUpdateCartPage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontUpdateCartPage.selectNewBillingCardAddress();
		storeFrontUpdateCartPage.clickOnSaveBillingProfile();
		String defaultBillingProfile =storeFrontUpdateCartPage.getDefaultSelectedBillingAddressName();
		storeFrontUpdateCartPage.selectAndGetBillingMethodName(driver.getCountry());
		s_assert.assertFalse(storeFrontUpdateCartPage.isNewlyCreatedBillingProfileIsSelectedByDefault(defaultBillingProfile),"Bill to this card radio button is selected old one");
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyIsOnlyOneRadioButtonSelected(driver.getCountry()),"Bill to this card radio button is more than one selected");
		s_assert.assertAll();
	}

	//Hybris Project-2284:Select Shipping Methods
	@Test(enabled=true)
	public void testSelectShippingMethod_2284() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String lN=TestConstants.LAST_NAME;
		String shippingProfileName=TestConstants.NEW_SHIPPING_PROFILE_FIRST_NAME+randomNum;
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage = new StoreFrontUpdateCartPage(driver);
		storeFrontUpdateCartPage.clickAddToBagButton(driver.getCountry());
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		s_assert.assertTrue(storeFrontUpdateCartPage.isShippingAddressNextStepBtnIsPresent(),"Shipping Address Next Step Button Is not Present");
		storeFrontUpdateCartPage.clickAddNewShippingProfileLink();
		storeFrontUpdateCartPage.enterNewShippingAddressName(shippingProfileName+" "+lN);
		storeFrontUpdateCartPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontUpdateCartPage.enterNewShippingAddressCity(city);
		storeFrontUpdateCartPage.selectNewShippingAddressState(state);
		storeFrontUpdateCartPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontUpdateCartPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontUpdateCartPage.clickOnSaveShippingProfile();
		String defaultSelectedShippingMethod=storeFrontUpdateCartPage.getDefaultSelectedShippingMethodName();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyNewlyCreatedShippingAddressIsSelectedByDefault(shippingProfileName),"Newly created shipping address is not selected by default");
		if(driver.getCountry().equalsIgnoreCase("ca"))
			s_assert.assertTrue(storeFrontUpdateCartPage.getSelectedShippingMethodName().toLowerCase().contains(defaultSelectedShippingMethod.toLowerCase()),"The default selected shipping method is not present in order summary shipping dropdown");
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		storeFrontUpdateCartPage.clickOnBillingNextStepBtn();
		storeFrontUpdateCartPage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyOrderPlacedConfirmationMessage(), "Order has been not placed successfully");
		storeFrontConsultantPage = storeFrontUpdateCartPage.clickOnRodanAndFieldsLogo();
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontOrdersPage = storeFrontConsultantPage.clickOrdersLinkPresentOnWelcomeDropDown();
		String orderHistoryNumber = storeFrontOrdersPage.getFirstOrderNumberFromOrderHistory();
		storeFrontOrdersPage.clickOrderNumber(orderHistoryNumber);
		s_assert.assertTrue(defaultSelectedShippingMethod.toLowerCase().contains(storeFrontOrdersPage.getShippingMethodFromAutoshipTemplate().toLowerCase()),"The default selected shipping method is not at order detail page");
		s_assert.assertAll();
	}

	//Hybris Project-2277:Add New Billing Address during checkout and Edit existing one
	@Test(enabled=true)
	public void testAddNewBillingAddressDuringCheckoutAndEditExistingOne_2277() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME_US+randomNum;
		String lastName = "lN";
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();  
		storeFrontUpdateCartPage.clickAddToBagButton(driver.getCountry());
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		storeFrontUpdateCartPage = new StoreFrontUpdateCartPage(driver);
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		storeFrontUpdateCartPage.clickAddNewBillingProfileLink();
		storeFrontUpdateCartPage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontUpdateCartPage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontUpdateCartPage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontUpdateCartPage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontUpdateCartPage.selectNewBillingCardAddress();
		storeFrontUpdateCartPage.clickAddANewAddressLink();
		storeFrontUpdateCartPage.enterNewBillingAddressName(newBillingProfileName+" "+lastName);
		storeFrontUpdateCartPage.enterNewBillingAddressLine1(addressLine1);
		storeFrontUpdateCartPage.enterNewBillingAddressCity(city);
		storeFrontUpdateCartPage.selectNewBillingAddressState();
		storeFrontUpdateCartPage.enterNewBillingAddressPostalCode(postalCode);
		storeFrontUpdateCartPage.enterNewBillingAddressPhoneNumber(phoneNumber);
		storeFrontUpdateCartPage.clickOnSaveBillingProfile();
		storeFrontUpdateCartPage.clickOnEditDefaultBillingProfile();
		storeFrontUpdateCartPage.selectNewBillingCardExpirationDate("OCT","2024");
		storeFrontUpdateCartPage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontUpdateCartPage.selectNewlyAddedBillingAddressName(newBillingProfileName);
		storeFrontUpdateCartPage.clickOnSaveBillingProfile();
		storeFrontUpdateCartPage.clickOnBillingNextStepBtn(); 
		s_assert.assertTrue(storeFrontUpdateCartPage.isNewBillingProfileIsSelectedByDefaultAfterClickOnEdit(newBillingProfileName),"New Billing Profile is not selected by default on CRP cart page");
		storeFrontUpdateCartPage.clickPlaceOrderBtn();
		storeFrontUpdateCartPage.clickOnWelcomeDropDown();
		storeFrontOrdersPage = storeFrontUpdateCartPage.clickOrdersLinkPresentOnWelcomeDropDown();
		storeFrontOrdersPage.clickOnFirstAdHocOrder();
		s_assert.assertTrue(storeFrontOrdersPage.validateBillingAddressOnOrderPage(newBillingProfileName),"billing address is not present on orders page");
		s_assert.assertAll();
	}

	//Hybris Project-2140:Check Shipping and Handling Fee for Order total 0-99.99
	@Test(enabled=true)
	public void testCheckShippingAndHandlingFeeForOrderTotal0To99_2140() throws InterruptedException{
		if(country.equalsIgnoreCase("ca")){
			String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
			storeFrontConsultantPage = storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
			storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
			storeFrontConsultantPage.applyPriceFilterLowToHigh();
			while(true){
				storeFrontConsultantPage.selectProductAndProceedToBuyWithoutFilter();
				String total = storeFrontUpdateCartPage.getTotalPriceOfProduct();
				String orderTotal = total.split("\\$")[1].trim();
				System.out.println("Order total for consultant"+orderTotal);
				double totalFromUI = Double.parseDouble(orderTotal);
				if(totalFromUI<99 && totalFromUI>0){
					break;
				}else{
					storeFrontConsultantPage.navigateToBackPage();
					continue;
				}
			}
			storeFrontUpdateCartPage.clickOnCheckoutButton();
			storeFrontUpdateCartPage.selectShippingMethod2DayForAdhocOrder();
			String deliveryCharges = String.valueOf(storeFrontUpdateCartPage.getDeliveryCharges());
			logger.info("deliveryCharges ="+deliveryCharges);
			if(driver.getCountry().equalsIgnoreCase("CA")){
				//Assert of shipping cost from UI
				s_assert.assertTrue(storeFrontUpdateCartPage.isDeliveryChargesPresent(),"Shipping charges is not present on UI");
			}else if(driver.getCountry().equalsIgnoreCase("US")){
				s_assert.assertTrue(deliveryCharges.contains(TestConstants.SHIPPING_CHARGES_FOR_UPS2DAY_US),"Shipping charges on UI is not As per shipping method selected at order history page");
			}
			storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
			storeFrontUpdateCartPage.clickOnBillingNextStepBtn();
			storeFrontUpdateCartPage.clickPlaceOrderBtn();
			s_assert.assertTrue(storeFrontUpdateCartPage.verifyOrderPlacedConfirmationMessage(), "Order has been not placed successfully");
			//assert shipping and handling fee
			deliveryCharges = storeFrontUpdateCartPage.getShippingChargesAtOrderConfirmationPage();
			logger.info("deliveryCharges 1 ="+deliveryCharges);
			if(driver.getCountry().equalsIgnoreCase("CA")){
				//Assert of shipping cost from UI
				s_assert.assertTrue(storeFrontUpdateCartPage.isShippingChargesPresentAtOrderConfirmationPage(),"Shipping charges is not present on UI at order confirmation page");
			}else if(driver.getCountry().equalsIgnoreCase("US")){
				s_assert.assertTrue(deliveryCharges.contains(TestConstants.SHIPPING_CHARGES_FOR_UPS2DAY_US),"Shipping charges on UI is not As per shipping method selected at order history page");
			}
			//storeFrontConsultantPage = storeFrontUpdateCartPage.clickRodanAndFieldsLogo();
			storeFrontConsultantPage.clickOnWelcomeDropDown();
			storeFrontOrdersPage = storeFrontConsultantPage.clickOrdersLinkPresentOnWelcomeDropDown();

			// Get Order Number
			String orderHistoryNumber = storeFrontOrdersPage.getFirstOrderNumberFromOrderHistory();
			storeFrontOrdersPage.clickOrderNumber(orderHistoryNumber);
			deliveryCharges = storeFrontOrdersPage.getShippingAmountFromAutoshipTemplate();
			logger.info("deliveryCharges 2 ="+deliveryCharges);
			if(driver.getCountry().equalsIgnoreCase("CA")){
				//Assert of shipping cost from UI
				s_assert.assertTrue(storeFrontOrdersPage.isShippingAmountFromAutoshipTemplatePresent(),"Shipping charges is not present on UI at autoship template");
			}else if(driver.getCountry().equalsIgnoreCase("US")){
				s_assert.assertTrue(deliveryCharges.contains(TestConstants.SHIPPING_CHARGES_FOR_UPS2DAY_US),"Shipping charges on UI is not As per shipping method selected at order history page");
			}

			// assert for order total > 100
			storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();		
			while(true){
				storeFrontConsultantPage.clickAddToBagButton(driver.getCountry());
				String total = storeFrontUpdateCartPage.getTotalPriceOfProduct();
				String orderTotal = total.split("\\$")[1].trim();
				System.out.println("Order total for consultant"+orderTotal);
				double totalFromUI = Double.parseDouble(orderTotal);
				if(totalFromUI>99){
					break;
				}else{
					storeFrontConsultantPage.navigateToBackPage();
					continue;
				}
			}
			storeFrontUpdateCartPage.clickOnCheckoutButton();
			storeFrontUpdateCartPage.selectShippingMethod2DayForAdhocOrder();

			deliveryCharges = String.valueOf(storeFrontUpdateCartPage.getDeliveryCharges());
			logger.info("deliveryCharges ="+deliveryCharges);
			System.out.println("shipping charges at shipping page "+deliveryCharges);
			logger.info("deliveryCharges 3 ="+deliveryCharges);
			if(driver.getCountry().equalsIgnoreCase("CA")){
				//Assert of shipping cost from UI
				s_assert.assertTrue(storeFrontUpdateCartPage.isDeliveryChargesPresent(),"Shipping charges is not present on UI");
			}else if(driver.getCountry().equalsIgnoreCase("US")){
				s_assert.assertTrue(deliveryCharges.contains(TestConstants.SHIPPING_CHARGES_FOR_UPS2DAY_AND_TOTAL_GREATER_THAN_99),"When order total>100 Shipping charges on UI is not As per shipping method selected at order history page");
			}
			storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
			storeFrontUpdateCartPage.clickOnBillingNextStepBtn();
			storeFrontUpdateCartPage.clickPlaceOrderBtn();
			s_assert.assertTrue(storeFrontUpdateCartPage.verifyOrderPlacedConfirmationMessage(), "Order has been not placed successfully");
			//assert shipping and handling fee
			deliveryCharges = storeFrontUpdateCartPage.getShippingChargesAtOrderConfirmationPage();
			logger.info("deliveryCharges 4 ="+deliveryCharges);
			if(driver.getCountry().equalsIgnoreCase("CA")){
				//Assert of shipping cost from UI
				s_assert.assertTrue(storeFrontUpdateCartPage.isShippingChargesPresentAtOrderConfirmationPage(),"Shipping charges is not present on UI at order confirmation page");
			}else if(driver.getCountry().equalsIgnoreCase("US")){
				s_assert.assertTrue(deliveryCharges.contains(TestConstants.SHIPPING_CHARGES_FOR_UPS2DAY_AND_TOTAL_GREATER_THAN_99),"When order total>100 Shipping charges on UI is not As per shipping method selected at order history page");
			}
			storeFrontConsultantPage.clickOnWelcomeDropDown();
			storeFrontOrdersPage = storeFrontConsultantPage.clickOrdersLinkPresentOnWelcomeDropDown();
			// Get Order Number
			orderHistoryNumber = storeFrontOrdersPage.getFirstOrderNumberFromOrderHistory();
			storeFrontOrdersPage.clickOrderNumber(orderHistoryNumber);
			deliveryCharges = storeFrontOrdersPage.getShippingAmountFromAutoshipTemplate();
			logger.info("deliveryCharges 5 ="+deliveryCharges);
			if(driver.getCountry().equalsIgnoreCase("CA")){
				//Assert of shipping cost from UI
				s_assert.assertTrue(storeFrontOrdersPage.isShippingAmountFromAutoshipTemplatePresent(),"Shipping charges is not present on UI at autoship template");
			}else if(driver.getCountry().equalsIgnoreCase("US")){
				s_assert.assertTrue(deliveryCharges.contains(TestConstants.SHIPPING_CHARGES_FOR_UPS2DAY_AND_TOTAL_GREATER_THAN_99),"When order total>100 Shipping charges on UI is not As per shipping method selected at order history page");
			}
			s_assert.assertAll();
		}else
			logger.info("CA specific test");
		
	}

	//Hybris Project-1885:Create Adhoc Order with American Express Card
	@Test(enabled=true)
	public void testCreateAdhocOrderWithAmericanExpressCard_1885() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME_US+randomNum;
		String lastName = "lN";
		String rcUserEmailID = QueryUtils.getRandomActiveRCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId);
		storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasRCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),"Not able to login to the application from RC");
		storeFrontRCUserPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage.clickOnBuyNowButton();
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		storeFrontUpdateCartPage.clickOnContinueWithoutSponsorLink();
		storeFrontUpdateCartPage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		storeFrontUpdateCartPage.clickAddNewBillingProfileLink();
		storeFrontUpdateCartPage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontUpdateCartPage.enterNewBillingCardNumber(TestConstants.AMERICAN_EXPRESS_CARD_NUMBER);
		storeFrontUpdateCartPage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontUpdateCartPage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE_FOR_SPECIAL_CARDS);
		storeFrontUpdateCartPage.selectNewBillingCardAddress();
		storeFrontUpdateCartPage.clickOnSaveBillingProfile();
		storeFrontUpdateCartPage.clickOnBillingNextStepBtn(); 
		storeFrontUpdateCartPage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyOrderPlacedConfirmationMessage(), "Order has been not placed successfully");
		s_assert.assertAll();
	}

	//Hybris Project-1888:Create Adhoc Order with Visa Card
	@Test(enabled=true)
	public void testCreateAdhocOrderWithVisaCard_1888() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME_US+randomNum;
		String lastName = "lN";
		String rcUserEmailID = QueryUtils.getRandomActiveRCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId);
		storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasRCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),"Not able to login to the application from RC");
		storeFrontRCUserPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage.clickOnBuyNowButton();
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		storeFrontUpdateCartPage.clickOnContinueWithoutSponsorLink();
		storeFrontUpdateCartPage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		storeFrontUpdateCartPage.clickAddNewBillingProfileLink();
		storeFrontUpdateCartPage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontUpdateCartPage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontUpdateCartPage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontUpdateCartPage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontUpdateCartPage.selectNewBillingCardAddress();
		storeFrontUpdateCartPage.clickOnSaveBillingProfile();
		storeFrontUpdateCartPage.clickOnBillingNextStepBtn(); 
		storeFrontUpdateCartPage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyOrderPlacedConfirmationMessage(), "Order has been not placed successfully");
		 s_assert.assertAll();
	}

	//Hybris Project-1890:Create Order With Shipping Method UPS 2Day-CAD$20.00
	@Test(enabled=true)
	public void testCreateOrderWithShippingMethodUPS2Day_1890() throws InterruptedException {
		if(driver.getCountry().equalsIgnoreCase("ca")){
			String rcUserEmailID = QueryUtils.getRandomActiveRCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId);
			storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
			Assert.assertTrue(storeFrontHomePage.hasRCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),"Not able to login to the application from RC"); 
			storeFrontHomePage.addAProductToCartAndCheckout();
			storeFrontHomePage.enterMainAccountInfo(addressLine1, city,state, postalCode, phoneNumber);
			storeFrontHomePage.clickOnContinueWithoutSponsorLink();
			storeFrontHomePage.clickOnNextButtonAfterSelectingSponsor();
			String selectedShippingMethod=storeFrontHomePage.selectShippingMethodUPS2DayUnderShippingSectionAndGetName();
			storeFrontHomePage.clickOnShippingAddressNextStepBtn();
			storeFrontHomePage.clickOnBillingNextStepBtn();
			storeFrontHomePage.clickPlaceOrderBtn();
			storeFrontHomePage.clickOnWelcomeDropDown();
			storeFrontOrdersPage = storeFrontRCUserPage.clickOrdersLinkPresentOnWelcomeDropDown();
			s_assert.assertTrue(storeFrontOrdersPage.verifyOrdersPageIsDisplayed(),"Orders page has not been displayed");
			storeFrontOrdersPage.clickOnFirstAdHocOrder();
			s_assert.assertTrue(storeFrontOrdersPage.verifyShippingMethodOnTemplateAfterAdhocOrderPlaced(selectedShippingMethod), "Selected Shipping Method didn't match with the method on Orders page!!");
			s_assert.assertAll(); 
		}else{
			logger.info("NOT EXECUTED...Test is ONLY for CA env");
		}
	}

	// Hybris Project-2108:Do not update billing profile for autoship on changing default selection
	@Test(enabled=true)
	public void testDoNotUpdateBillingProfileForAutoShipOnChangingDefaultSelection_2108() throws InterruptedException	 {
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME_US+randomNum;
		String lastName = "lN";
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontBillingInfoPage=storeFrontHomePage.clickBillingInfoLinkPresentOnWelcomeDropDown();
		storeFrontBillingInfoPage.clickAddNewBillingProfileLink();
		storeFrontBillingInfoPage.enterNewBillingCardNumber(TestConstants.CARD_NUMBER);
		storeFrontBillingInfoPage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontBillingInfoPage.selectNewBillingCardExpirationDate(TestConstants.CARD_EXP_MONTH, TestConstants.CARD_EXP_YEAR);
		storeFrontBillingInfoPage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontBillingInfoPage.selectNewBillingCardAddress();
		storeFrontBillingInfoPage.clickOnSaveBillingProfile();
		storeFrontBillingInfoPage.makeBillingProfileDefault(newBillingProfileName);
		s_assert.assertTrue(storeFrontBillingInfoPage.validateBillingProfileUpdated(),"Billing Profile is not updated!!");
		storeFrontHomePage.clickOnAutoshipCart();
		storeFrontUpdateCartPage.clickOnUpdateMoreInfoButton();
		s_assert.assertFalse(storeFrontUpdateCartPage.validateNewlySelectedDefaultBillingProfileIsNotUpdatedInAutoshipBillingProfileSection(newBillingProfileName),"Newly selected default biiling profile is updated under Autoship Billing Section");
		s_assert.assertAll();
	}

	//Hybris Project-2390:Shipping address selected in autoship cart.
	@Test(enabled=true)
	public void testShippingAddressSelectedInAutoShipCart_2390() throws InterruptedException {
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String lastName = "lN";
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
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
		storeFrontShippingInfoPage.selectUseThisShippingProfileFutureAutoshipChkbox();
		storeFrontShippingInfoPage.clickOnSaveShippingProfile();
		storeFrontShippingInfoPage.makeShippingProfileAsDefault(newShippingAddressName);
		s_assert.assertTrue(storeFrontShippingInfoPage.validateUpdateAutoShipPopUpPresent(), "Update AutoShip PopUp is not present!!");
		storeFrontHomePage.clickOnAutoshipCart();
		storeFrontUpdateCartPage.clickOnUpdateMoreInfoButton();
		s_assert.assertTrue(storeFrontUpdateCartPage.validateNewlySelectedDefaultShippingProfileIsUpdatedInAutoshipShippingSection(newShippingAddressName),"Newly selected default Shipping profile is NOT updated under Autoship Shipping Section");
		s_assert.assertAll();
	}

	/**
	 * Test Summary: Login with a consultant,goto CRP autoship cart, choose the UPS Overnight shipping method and verify its fee in order summary
	 * Assertions:
	 * The delivery charges in order summary should be corresponding to the shipping method selected
	 */
	//qTest ID: TC-565 Check Shipping and Handling Fee for "Standard Overnight" for Order total 0-999999-CRPAutoship
	@Test(enabled=true)
	public void testCheckShippingAndHandlingFeeForUPS1DayCRPAutoship_565q() throws InterruptedException {
		if(country.equalsIgnoreCase("ca")){
			String deliveryChargesOfUPSOvernight=null;
			String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
			storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
			Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
			storeFrontHomePage.clickOnAutoshipCart();
			storeFrontUpdateCartPage.clickOnUpdateMoreInfoButton();
			storeFrontUpdateCartPage.clickOnEditShipping();
			deliveryChargesOfUPSOvernight = storeFrontUpdateCartPage.selectShippingMethodUPSStandardOverNightForAdhocOrder();
			storeFrontUpdateCartPage.clickOnUpdateCartShippingNextStepBtn();
			storeFrontUpdateCartPage.clickOnNextStepBtn();
			s_assert.assertTrue(storeFrontUpdateCartPage.getDeliveryCharges().contains(deliveryChargesOfUPSOvernight),"Delivery charges on order confirmation SHOULD be "+deliveryChargesOfUPSOvernight);
			s_assert.assertAll();
		}
	
	}

	//Hybris Project-2147:Check Shipping and Handling Fee for UPS 2Day for Order total 0-999999-EnrollmentKit
	@Test(enabled=false)//Already covered in Enrollment Test TC-1287
	public void testCheckShippingAndHandlingFeeForUPS2DayConsultantEnrollmentKit_2147() throws InterruptedException {
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String socialInsuranceNumber = String.valueOf(CommonUtils.getRandomNum(100000000, 999999999));
		enrollmentType = TestConstants.STANDARD_ENROLLMENT;
		regimenName = TestConstants.REGIMEN_NAME_UNBLEMISH;
		String firstName=TestConstants.FIRST_NAME+randomNum;
		storeFrontHomePage.hoverOnBecomeAConsultantAndClickEnrollNowLink();
		storeFrontHomePage.searchCID(sponsor);
		storeFrontHomePage.mouseHoverSponsorDataAndClickContinue();
		storeFrontHomePage.enterUserInformationForEnrollment(kitName, regimenName, enrollmentType, firstName, TestConstants.LAST_NAME+randomNum, password, addressLine1, city, state,postalCode, phoneNumber);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.enterCardNumber(TestConstants.CARD_NUMBER);
		storeFrontHomePage.enterNameOnCard(TestConstants.FIRST_NAME+randomNum);
		storeFrontHomePage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontHomePage.enterSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontHomePage.enterSocialInsuranceNumber(socialInsuranceNumber);
		storeFrontHomePage.enterNameAsItAppearsOnCard(TestConstants.FIRST_NAME);
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.checkPulseAndCRPEnrollment();
		storeFrontHomePage.clickNextButton();
		storeFrontHomePage.applyPriceFilterHighToLow();
		storeFrontHomePage.selectProductAndProceedToAddToCRP();
		storeFrontHomePage.addQuantityOfProduct("5");
		storeFrontHomePage.clickOnNextBtnAfterAddingProductAndQty();
		//Validate shipping cost from UI
		s_assert.assertTrue(storeFrontHomePage.isShippingChargesPresentOnReviewOrderPage(),"Shipping charges is not present on UI");
		s_assert.assertAll();
	}

	//Hybris Project-1889:Create Adhoc Order with Master Card
	@Test(enabled=true)
	public void testCreateAdhocOrderWithMasterCard_1889() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME_US+randomNum;
		String lastName = "lN";
		String rcUserEmailID = QueryUtils.getRandomActiveRCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId);
		storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasRCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),"Not able to login to the application from RC");
		storeFrontRCUserPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage.clickOnBuyNowButton();
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		storeFrontUpdateCartPage.clickOnContinueWithoutSponsorLink();
		storeFrontUpdateCartPage.clickOnNextButtonAfterSelectingSponsor();
		String subtotal = storeFrontUpdateCartPage.getSubtotal();
		String total = storeFrontUpdateCartPage.getTotal();
		String shippingMethod = storeFrontUpdateCartPage.getShippingMethod();
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		storeFrontUpdateCartPage.clickAddNewBillingProfileLink();
		storeFrontUpdateCartPage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontUpdateCartPage.enterNewBillingCardNumber(TestConstants.MASTER_CARD_NUMBER);
		storeFrontUpdateCartPage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontUpdateCartPage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontUpdateCartPage.selectNewBillingCardAddress();
		storeFrontUpdateCartPage.clickOnSaveBillingProfile();
		storeFrontUpdateCartPage.clickOnBillingNextStepBtn(); 
		storeFrontUpdateCartPage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyOrderPlacedConfirmationMessage(), "Order has been not placed successfully");
		storeFrontUpdateCartPage.clickOnRodanAndFieldsLogo();
		storeFrontRCUserPage.clickOnWelcomeDropDown();
		storeFrontOrdersPage = storeFrontRCUserPage.clickOrdersLinkPresentOnWelcomeDropDown();
		String orderHistoryNumber = storeFrontOrdersPage.getFirstOrderNumberFromOrderHistory();
		storeFrontOrdersPage.clickOrderNumber(orderHistoryNumber);
		s_assert.assertTrue(storeFrontOrdersPage.getSubTotalFromAutoshipTemplate().contains(subtotal),"Adhoc Order template subtotal "+subtotal+" and on UI is "+storeFrontOrdersPage.getSubTotalFromAutoshipTemplate());
		s_assert.assertTrue(storeFrontOrdersPage.getGrandTotalFromAutoshipTemplate().contains(total),"Adhoc Order template grand total "+total+" and on UI is "+storeFrontOrdersPage.getGrandTotalFromAutoshipTemplate());
		s_assert.assertTrue(shippingMethod.contains(storeFrontOrdersPage.getShippingMethodFromAutoshipTemplate()),"Adhoc Order template shipping method "+shippingMethod+" and on UI is "+storeFrontOrdersPage.getShippingMethodFromAutoshipTemplate());
		 s_assert.assertTrue(storeFrontOrdersPage.getCreditCardNumber().contains(TestConstants.MASTER_CARD_NUMBER.substring(12)),"Adhoc Order template credit card number "+TestConstants.MASTER_CARD_NUMBER.substring(11)+" and on UI is "+storeFrontOrdersPage.getCreditCardNumber());
		 s_assert.assertAll();
	}

	//Hybris Project-1886:Shouldn't allow Discover Card on the storefront
	@Test(enabled=true)
	public void testShouldNotAllowDiscoverCardOnTHeStoreFront_1886() throws InterruptedException{
		int randomNum = CommonUtils.getRandomNum(10000, 1000000);
		String newBillingProfileName = TestConstants.NEW_BILLING_PROFILE_NAME_US+randomNum;
		String lastName = "lN";
		String rcUserEmailID = QueryUtils.getRandomActiveRCFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId);
		storeFrontHomePage.loginAsRCUser(rcUserEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasRCLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_RC_HAVING_ORDERS_RFO,countryId),"Not able to login to the application from RC");
		storeFrontRCUserPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage.clickOnBuyNowButton();
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		storeFrontUpdateCartPage.clickOnContinueWithoutSponsorLink();
		storeFrontUpdateCartPage.clickOnNextButtonAfterSelectingSponsor();
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		storeFrontUpdateCartPage.clickAddNewBillingProfileLink();
		storeFrontUpdateCartPage.enterNewBillingNameOnCard(newBillingProfileName+" "+lastName);
		storeFrontUpdateCartPage.enterNewBillingCardNumber(TestConstants.DISCOVER_CARD_NUMBER);
		storeFrontUpdateCartPage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontUpdateCartPage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontUpdateCartPage.clickOnSaveBillingProfile();
		s_assert.assertTrue(storeFrontHomePage.validateInvalidCreditCardMessage(), "Please enter a valid credit card message is displayed");
		s_assert.assertAll();
	}

	// Hybris Project-2092:"Edit Shopping Cart" while checking out
	@Test(enabled=true)
	public void testEditShoppingCartWhileCheckingOut_2092() throws InterruptedException {
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.applyPriceFilterLowToHigh();
		storeFrontHomePage.selectProductAndProceedToBuyWithoutFilter();
		s_assert.assertTrue(storeFrontHomePage.verifyNumberOfProductsInCart("1"), "number of products in the cart is NOT 1");
		storeFrontHomePage.addQuantityOfProduct("2"); 
		storeFrontHomePage.addAnotherProduct();
		storeFrontHomePage.updateQuantityOfProductToTheSecondProduct("2"); 
		storeFrontHomePage.clickOnCheckoutButton();
		storeFrontHomePage.clickEditShoppingCartLink();
		storeFrontHomePage.addQuantityOfProduct("3"); 
		storeFrontHomePage.updateQuantityOfProductToTheSecondProduct("1");
		double subtotal1=storeFrontHomePage.getSubTotalOfFirstProduct();
		double subtotal2=storeFrontHomePage.getSubTotalOfSecondProduct();
		s_assert.assertTrue(storeFrontHomePage.validateSubTotal(subtotal1, subtotal2), "sub-total is not recalculated accordingly to the updated qty of product(s)");
		storeFrontHomePage.clickOnCheckoutButton();
		s_assert.assertTrue(storeFrontHomePage.validate2ProductsAreDisplayedInReviewOrderSection().trim().equalsIgnoreCase("2"),"Two products are not displayed in review order section");
		s_assert.assertAll(); 
	}

	// Hybris Project-2303:Navigate to existing Cart from Mini cart in the header
	@Test(enabled=true)
	public void testNavigateToExistingCartFromMiniCartInTheHeader_2303() throws InterruptedException{
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		s_assert.assertTrue(storeFrontConsultantPage.validateNextCRPMiniCart(), "next CRP Mini cart is not displayed");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();  
		storeFrontHomePage.selectProductAndProceedToBuy();
		s_assert.assertTrue(storeFrontHomePage.isAdhocMiniCartDisplayed(), "mini cart is not being displayed");
		s_assert.assertTrue(storeFrontHomePage.clickMiniCartAndValidatePreaddedProductsOnCartPage(),"detailed view of cart is not displayed");
		s_assert.assertAll();
	}	

	//Hybris Project-3748:Click On "Buy Now" for a logged in Consultant.
	@Test(enabled=true)
	public void testClickOnBuyNowForALoggedInConsultant_3748() throws InterruptedException{
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontConsultantPage.clickAddToBagButtonWithoutFilter();
		s_assert.assertTrue(storeFrontConsultantPage.isCartPageDisplayed(),"cart page is not displayed");
		s_assert.assertAll();
	}

	//Hybris Project-4014:Guest user on the BIZ and Click on "Buy Now"
	@Test(enabled=true)
	public void testGuestUserOnTheBizAndClickOnBuyNow_4014() throws InterruptedException{
		storeFrontHomePage.openPWSSite(country, env);
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontHomePage.clickAddToBagButtonWithoutFilter();
		s_assert.assertTrue(storeFrontHomePage.isCartPageDisplayed(),"card page is not displayed after clicking buy now button");
		s_assert.assertAll();
	}

	//Hybris Project-4015:Guest user on the COM and Click on "Buy Now"
	@Test(enabled=true)
	public void testGuestUserOnTheComAndClickOnBuyNow_4015() throws InterruptedException{
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontHomePage.clickAddToBagButtonWithoutFilter();
		s_assert.assertTrue(storeFrontHomePage.isCartPageDisplayed(),"card page is not displayed after clicking buy now button");
		s_assert.assertAll();
	}

	//Hybris Project-3737:Place an order from consultant's COM site with "BUY Now" button on category landing page
	@Test(enabled=true)
	public void testPlaceOrderConsultantPWSWithBuyNowBtnOnCategoryLandingPage_3737() throws InterruptedException{
		List<Map<String, Object>> randomConsultantList =  null;
		String consultantWithPWSEmailID = null; //TestConstants.CONSULTANT_USERNAME;
		String consultantPWSURL = null; //TestConstants.CONSULTANT_COM_URL;
		randomConsultantList =DBUtil.performDatabaseQuery(DBQueries_RFO.callQueryWithArguementPWS(DBQueries_RFO.GET_RANDOM_CONSULTANT_WITH_PWS_RFO,driver.getEnvironment(),driver.getCountry(),countryId),RFO_DB);
		consultantWithPWSEmailID = (String) getValueFromQueryResult(randomConsultantList, "UserName");
		consultantPWSURL = (String) getValueFromQueryResult(randomConsultantList, "URL");

		// For .com site
		consultantPWSURL = storeFrontHomePage.convertBizSiteToComSite(consultantPWSURL);
		storeFrontHomePage.openPWS(consultantPWSURL);
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		// Products are displayed?
		s_assert.assertTrue(storeFrontHomePage.areProductsDisplayed(), "quickshop products not displayed");
		logger.info("Quick shop products are displayed");
		storeFrontHomePage.selectProductAndProceedToBuy();
		//Find a Sponsor page is not displayed?
		s_assert.assertFalse(storeFrontHomePage.verifyFindYourSponsorPage(), "Find your sponsor page is present");

		//Cart page is displayed?
		s_assert.assertTrue(storeFrontHomePage.isCartPageDisplayed(), "Cart page is not displayed");
		logger.info("Cart page is displayed");

		//Click on Check out
		storeFrontHomePage.clickOnCheckoutButton();
		s_assert.assertAll(); 
	}

	//Hybris Project-2296:Edit the Cart with quanty of one of the Orderline (Increase/Decrease) Subtotal is recalculated corret
	@Test(enabled=true)
	public void testEditCartSubtotalIsRecalculated_2296() throws InterruptedException{
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		storeFrontHomePage.applyPriceFilterLowToHigh();
		storeFrontHomePage.selectProductAndProceedToBuyWithoutFilter();
		s_assert.assertTrue(storeFrontHomePage.verifyNumberOfProductsInCart("1"), "number of products in the cart is NOT 1");
		storeFrontHomePage.addQuantityOfProduct("2"); 
		storeFrontHomePage.addAnotherProduct();
		storeFrontHomePage.updateQuantityOfProductToTheSecondProduct("2"); 
		storeFrontHomePage.clickOnCheckoutButton();
		storeFrontHomePage.clickEditShoppingCartLink();
		storeFrontHomePage.addQuantityOfProduct("3"); 
		storeFrontHomePage.updateQuantityOfProductToTheSecondProduct("1");
		double subtotal1=storeFrontHomePage.getSubTotalOfFirstProduct();
		double subtotal2=storeFrontHomePage.getSubTotalOfSecondProduct();
		s_assert.assertTrue(storeFrontHomePage.validateSubTotal(subtotal1, subtotal2), "sub-total is not recalculated accordingly to the updated qty of product(s)");
		s_assert.assertAll(); 
	}

	// Hybris Project-146:Autoship Update - Flow & Review and Confirm step - consultant
	@Test(enabled=true)
	public void testAutoshipUpdateFlowReviewAndConfirm_146() throws InterruptedException{
		String profileName=TestConstants.ADDRESS_NAME;
		String lastName = "lN"; 
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontHomePage.clickOnAutoshipCart();
		storeFrontUpdateCartPage=storeFrontCartAutoShipPage.clickUpdateMoreInfoLink();
		storeFrontUpdateCartPage.clickOnEditPaymentBillingProfile();
		storeFrontUpdateCartPage.clickOnDefaultBillingProfileEdit();
		storeFrontUpdateCartPage.selectNewBillingCardExpirationDate("OCT","2025");
		storeFrontUpdateCartPage.enterNewBillingSecurityCode(TestConstants.SECURITY_CODE);
		storeFrontUpdateCartPage.clickOnSaveBillingProfile();
		storeFrontUpdateCartPage.clickOnEditShipping();
		storeFrontUpdateCartPage.clickAddNewShippingProfileLink();
		storeFrontUpdateCartPage.enterNewShippingAddressName(profileName+" "+lastName);
		storeFrontUpdateCartPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontUpdateCartPage.enterNewShippingAddressCity(city);
		storeFrontUpdateCartPage.selectNewShippingAddressState(state);
		storeFrontUpdateCartPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontUpdateCartPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontUpdateCartPage.clickOnSaveShippingProfile();
		storeFrontUpdateCartPage.clickOnNextStepBtnShippingAddress();
		storeFrontUpdateCartPage.clickOnNextStepBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.validateHeaderContent(), "header content is not displayed properly");
		storeFrontUpdateCartPage.clickUpdateCartBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.verifyCartUpdateMessage(), "cart is not updated!! ");
		s_assert.assertAll();
	}

	//Hybris Project-2281:Add/Edit Multiple shipping address and during checkout
	@Test(enabled=true)
	public void testAddMultipeShippingProfileDuringCheckout_2281() throws InterruptedException{
		int randomNumber = CommonUtils.getRandomNum(10000, 1000000);
		int i=0;
		String profileName=TestConstants.ADDRESS_NAME;
		String newShippingProfileName = TestConstants.NEW_SHIPPING_PROFILE_FIRST_NAME+randomNumber;
		String lastName = "lN";
		String consultantEmailID = QueryUtils.getRandomActiveConsultantFromDB(RFO_DB, DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId);
		storeFrontHomePage.loginAsConsultant(consultantEmailID, password);
		Assert.assertTrue(storeFrontHomePage.hasConsultantLoggedInSuccessfully(DBQueries_RFO.GET_RANDOM_ACTIVE_CONSULTANT_WITH_ORDERS_AND_AUTOSHIPS_RFO,countryId),"Not able to login to the application from consultant");
		storeFrontConsultantPage.hoverOnShopLinkAndClickAllProductsLinksAfterLogin();
		storeFrontUpdateCartPage.clickAddToBagButton(driver.getCountry());
		storeFrontUpdateCartPage.clickOnCheckoutButton();
		for(i=0;i<2;i++){
			storeFrontUpdateCartPage.clickAddNewShippingProfileLink();
			storeFrontUpdateCartPage.enterNewShippingAddressName(profileName+i+" "+lastName);
			storeFrontUpdateCartPage.enterNewShippingAddressLine1(addressLine1);
			storeFrontUpdateCartPage.enterNewShippingAddressCity(city);
			storeFrontUpdateCartPage.selectNewShippingAddressState(state);
			storeFrontUpdateCartPage.enterNewShippingAddressPostalCode(postalCode);
			storeFrontUpdateCartPage.enterNewShippingAddressPhoneNumber(phoneNumber);
			storeFrontUpdateCartPage.clickOnSaveShippingProfile();
			s_assert.assertTrue(storeFrontUpdateCartPage.isNewlyCreatedShippingProfileIsSelectedByDefault(profileName+i),"New Shipping Profile is not selected by default on CRP cart page");
		}
		i=1;
		storeFrontUpdateCartPage.clickOnEditOnNotDefaultAddressOfShipping();
		storeFrontUpdateCartPage.enterNewShippingAddressName(newShippingProfileName+" "+lastName);
		storeFrontUpdateCartPage.enterNewShippingAddressLine1(addressLine1);
		storeFrontUpdateCartPage.enterNewShippingAddressCity(city);
		storeFrontUpdateCartPage.selectNewShippingAddressState(state);
		storeFrontUpdateCartPage.enterNewShippingAddressPostalCode(postalCode);
		storeFrontUpdateCartPage.enterNewShippingAddressPhoneNumber(phoneNumber);
		storeFrontUpdateCartPage.clickOnSaveShippingProfile();
		s_assert.assertTrue(storeFrontUpdateCartPage.isNewlyCreatedShippingProfileIsSelectedByDefault(newShippingProfileName),"New Edited Shipping Profile is not selected by default on CRP cart page");
		storeFrontUpdateCartPage.clickOnShippingAddressNextStepBtn();
		storeFrontUpdateCartPage.clickOnBillingNextStepBtn(); 
		storeFrontUpdateCartPage.clickPlaceOrderBtn();
		s_assert.assertTrue(storeFrontUpdateCartPage.isNewEditedShippingProfileIsPresentOnOrderConfirmationPage(newShippingProfileName),"New Edited Shipping Profile is not Present by default on Order Summary page");
		s_assert.assertTrue(storeFrontHomePage.isOrderPlacedSuccessfully(),"Order is not placed successfully");
		storeFrontConsultantPage = storeFrontUpdateCartPage.clickOnRodanAndFieldsLogo();
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontOrdersPage = storeFrontConsultantPage.clickOrdersLinkPresentOnWelcomeDropDown();
		storeFrontOrdersPage.clickOnFirstAdHocOrder();
		s_assert.assertTrue(storeFrontOrdersPage.isShippingAddressContainsName(newShippingProfileName),"AdHoc Orders Page do not contain the newly edited shipping address");
		storeFrontConsultantPage.clickOnWelcomeDropDown();
		storeFrontShippingInfoPage = storeFrontConsultantPage.clickShippingLinkPresentOnWelcomeDropDown();
		s_assert.assertTrue(storeFrontShippingInfoPage.verifyShippingInfoPageIsDisplayed(),"Shipping Info page has not been displayed");
		s_assert.assertTrue(storeFrontShippingInfoPage.isShippingAddressPresentOnShippingPage(newShippingProfileName),"Newly added Shipping profile is NOT listed on the Shipping info page");
		s_assert.assertTrue(storeFrontShippingInfoPage.isShippingAddressPresentOnShippingPage(profileName+i),"Newly added Shipping profile is NOT listed on the Shipping info page");
		s_assert.assertAll();
	}

	//Hybris Project-2304 :: Version : 1 :: check Cart from Mini cart after adding product
	@Test
	public void testCheckCartFromMiniCartAfterAddingProduct_2304() throws InterruptedException {
		storeFrontHomePage.hoverOnShopLinkAndClickAllProductsLinks();
		String productName = storeFrontHomePage.selectProductAndProceedToBuy();
		s_assert.assertTrue(storeFrontHomePage.getNameOfTheOnlyAddedProductOnCart().toLowerCase().trim().contains(productName.toLowerCase().trim()),"Added product name is "+productName.toLowerCase().trim()+" while on cart is "+storeFrontHomePage.getNameOfTheOnlyAddedProductOnCart().toLowerCase().trim());
		s_assert.assertTrue(storeFrontHomePage.isAdhocMiniCartDisplayed(), "mini cart is not being displayed");
		s_assert.assertTrue(storeFrontHomePage.getNumberOfProductsDisplayedOnMiniCart().contains("1"), "number of products displayed in the mini cart expected is 1 but getting "+storeFrontHomePage.getNumberOfProductsDisplayedOnMiniCart());
		storeFrontHomePage.mouseHoverOnMiniCart();
		s_assert.assertTrue(storeFrontHomePage.getNameOfOnlyProductAddedOnMiniCart().toLowerCase().trim().contains(productName.toLowerCase().trim()),"Added product name is "+productName.toLowerCase().trim()+" while on mini cart is "+storeFrontHomePage.getNameOfOnlyProductAddedOnMiniCart().toLowerCase().trim());
		storeFrontHomePage.clickOnViewShippingCartBtnOnMiniCart();
		s_assert.assertTrue(storeFrontHomePage.isCartPageDisplayed(),"cart page is not displayed after clicking view shipping cart button on mini cart");
		s_assert.assertTrue(storeFrontHomePage.clickMiniCartAndValidatePreaddedProductsOnCartPage(), "preadded products on cart page is not displayed");  
		s_assert.assertAll();
	}

}