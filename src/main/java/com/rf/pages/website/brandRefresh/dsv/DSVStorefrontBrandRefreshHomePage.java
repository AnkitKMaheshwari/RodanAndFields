package com.rf.pages.website.brandRefresh.dsv;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.rf.core.driver.website.RFWebsiteDriver;
import com.rf.core.website.constants.TestConstantsRFL;

public class DSVStorefrontBrandRefreshHomePage extends DSVStorefrontBrandRefreshWebsiteBasePage {
	private static final Logger logger = LogManager
			.getLogger(DSVStorefrontBrandRefreshHomePage.class.getName());

	public DSVStorefrontBrandRefreshHomePage(RFWebsiteDriver driver) {
		super(driver);		
	}

	private static final By ITEM_COUNT_LOC = By.xpath("//div[@id='MyAutoshipItems']//span");
	private static final By FIRST_PRODUCT_NAME_IN_AUTOSHIP_CART = By.xpath("//div[@id='MyAutoshipItems']//li[1]");
	private static final By LOGOUT_LINK_LOC = By.xpath("//a[text()='Log-Out']");
	private static final By DISMISS_ALERT_LOC=By.xpath("//span[contains(text(),'Dismiss')]");
	private static final By MY_ACCOUNT_LINK_LOC = By.xpath("//a[contains(text(),'My Account')]");
	private static final By CLICK_CHANGE_PC_PERKS_STATUS_LOC = By.xpath("//span[contains(text(),'Change my PC Perks Status')]");
	private static final By HELLO_OR_WELCOME_TXT_ON_CORP = By.xpath("//*[contains(text(),'Hello') or contains(text(),'Welcome')]");
	private static final By SHOP_SKINCARE_LOC = By.xpath("//span[text()='Shop Skincare']");
	private static final By ADD_TO_BAG_BTN_LOC = By.xpath(".//*[contains(text(),'Add to Bag')]");
	private static final By EDIT_ORDER_UNDER_MY_ACCOUNT_LOC = By.xpath("//span[text()=' Edit Order']");
	private static final By CHANGE_LINK_FOR_SHIPPING_INFO_ON_PWS = By.xpath("//a[contains(@id,'uxChangeShippingLink')]");
	private static final By ADDRESS_NAME_FOR_SHIPPING_PROFILE = By.xpath("//input[contains(@id,'uxAddressName')]");
	private static final By ATTENTION_FIRST_NAME = By.xpath("//div[@class='form-group']//input[contains(@id,'uxAttentionFirstName')][1]");
	private static final By ATTENTION_LAST_NAME = By.xpath("//div[@class='form-group']//input[contains(@id,'uxAttentionLastName')][1]");
	private static final By ADDRESS_LINE_1 = By.xpath("//input[contains(@id,'uxAddressLine1')]");
	private static final By ZIP_CODE = By.xpath("//input[contains(@id,'uxZipCode')]");
	private static final By CITY_DD = By.xpath("//input[contains(@id,'uxCityDropDown_Input')]/following::a[text()='select']");
	private static final By FIRST_VALUE_OF_CITY_DD = By.xpath("//div[contains(@id,'uxCityDropDown_DropDown')]//ul[@class='rcbList']/li");
	private static final By COUNTRY_DD = By.xpath("//input[contains(@id,'uxCountyDropDown_Input')]/following::a[text()='select']");
	private static final By FIRST_VALUE_OF_COUNTRY_DD = By.xpath("//div[contains(@id,'uxCountyDropDown_DropDown')]//ul[@class='rcbList']/li");
	private static final By PHONE_NUMBER_SHIPPING_PROFILE_PAGE = By.xpath("//input[contains(@id,'uxShippingEditor_AppPhone')]");
	private static final By SHIPPING_ADDRESS_NAME_LOC = By.xpath("//*[text()='Shipping to:']/../span[1]");
	private static final By USE_THIS_ADDRESS_SHIPPING_INFORMATION = By.xpath("//a[contains(@id,'uxUseNewAddress') or contains(@id,'uxContinueAndEdit')]");
	private static final By CHANGE_BILLING_INFO_LINK_ON_PWS = By.xpath("//a[contains(@id,'uxChangeBillingLink')]");
	private static final By USE_THIS_BILLING_INFORMATION = By.xpath("//a[contains(@id,'uxUseNewPayment') or contains(@id,'uxContinueAndEdit')]");
	private static final By BILLING_NAME_FOR_BILLING_PROFILE = By.xpath("//input[contains(@id,'uxBillingProfileName')]");
	private static final By NAME_ON_CARD = By.xpath("//input[contains(@id,'uxNameOnCard')]");
	private static final By CREDIT_CARD_NUMBER_INPUT_FIELD = By.xpath("//input[contains(@id,'uxCreditCardNumber')]");
	private static final By EXPIRATION_DATE_MONTH_DD = By.xpath("//select[contains(@id,'uxMonthDropDown')]");
	private static final By EXPIRATION_DATE_YEAR_DD = By.xpath("//select[contains(@id,'uxYearDropDown')]");
	private static final By PHONE_NUMBER_BILLING_PROFILE_PAGE = By.xpath("//input[contains(@id,'uxPhone')]");
	private static final By SHOP_SKINCARE_HEADER_LOC = By.xpath("//span[text()='Shop Skincare']");
	private static final By ADD_TO_CART_BTN_LOC = By.xpath("//div[@class='productList']/div[1]/div[1]/a[2]");
	private static final By CHECKOUT_BTN = By.xpath("//span[text()='Checkout']");
	private static final By CONTINUE_WITHOUT_CONSULTANT_LINK = By.xpath("//a[contains(@id,'uxSkipStep')]");
	private static final By CHANGE_BILLING_INFO = By.xpath("//a[contains(@id,'uxBillingInfo_uxChange')]");
	private static final By CHANGE_SHIPPING_ADDRESS_LOC = By.xpath("//span[contains(text(),'Change Shipping Address')]/ancestor::a[1]");
	private static final By CONTINUE_BTN_AT_BILLING_INFO_LOC = By.xpath("//a[contains(@id,'uxBillingInfo_uxContinue')]");
	private static final By CONTINUE_BTN_AT_SHIPPING_INFO_LOC = By.xpath("//a[contains(@id,'uxShippingInformation_uxContinue')]");
	private static final By ORDER_HISTORY_LINK_LOC = By.xpath("//span[contains(text(),'Order History')]");
	private static final By VIEW_DETAILS_LINK_LOC = By.xpath("//a[contains(text(),'View Details')]");
	private static final By VERIFY_SHIPPING_ADDRESS_TXT_LOC = By.xpath("//div[@id='shippingDiv']");
	private static final By VERIFY_BILLING_ADDRESS_TXT_LOC = By.xpath("//div[@id='billingDiv']/following::p[1]");
	private static final By ORDER_NUMBER_ON_ORDER_HISTORY_LOC = By.xpath("//div[@class='divTableBody']/div[1]/div[1]");
	private static final By ORDER_DATE_ON_ORDER_HISTORY_LOC = By.xpath("//div[@class='divTableBody']/div[1]/div[2]");
	private static final By ORDER_ITEM_ON_ORDER_HISTORY_LOC = By.xpath("//div[@class='divTableBody']/div[1]/div[3]");
	private static final By ORDER_GRAND_TOTAL_ON_ORDER_HISTORY_LOC = By.xpath("//div[@class='divTableBody']/div[1]/div[4]");
	private static final By VERIFY_PRODUCT_NAME_ORDER_DETAIL_LOC = By.xpath("//div[@class='divTableRow']/div[contains(@class,'itemDescription')]");
	private static final By GRAND_TOTAL_ORDER_DETAIL_LOC = By.xpath("//span[contains(text(),'Grand Total')]/preceding::div/p[4]");
	private static final By EDIT_ORDER_BTN_LOC = By.xpath("//a[contains(text(),'Edit Order')]");
	private static final By AUTOSHIP_TOTAL_LOC = By.xpath("//p[contains(@id,'uxAutoshipTotal')]");
	private static final By UPDATE_ORDER_BTN_LOC = By.xpath("//div[@id='MyAutoshipItems']/following::input[@value='Update Order']");
	private static final By ORDER_UPDATION_MSG_LOC = By.xpath("//div[contains(@id,'uxStatusPanel')]//p");
	private static final By REMOVE_LINK_LOC=By.xpath("//div[@id='MyAutoshipItems']//li[1]//a");
	private static final By ALL_PRODUCT_ON_CRP_OVERVIEW_PAGE_LOC=By.xpath("//div[@id='MyAutoshipItems']//li");

	private static String addToBagButtonForProductNameLoc = "//a[contains(text(),'%s')]/ancestor::div[1]//span[contains(text(),'Add to Bag')]";
	private static String productNameInCartLoc = "//div[contains(text(),'%s')]";
	private static String shadeDDLoc = "//a[contains(text(),'%s')]/following::select[contains(@id,'variantRelation')][1]";

	private static String linkUnderShopSkinCareOrBeAConsultant = "//div[@id='LeftNav']//a/span[text()='%s']";
	private String deleteButtonForProfileLoc = "//span[contains(text(),'%s')]/following::a[text()='Delete'][1]";
	private String specificprofileInSavedProfilesLoc = "//.[contains(text(),'Saved') and contains(text(),'')]/following-sibling::div[1]//span[contains(text(),'%s')]";
	private static String expiryMonthLoc= "//select[contains(@id,'uxMonthDropDown')]//option[@value='%s']";
	private static String expiryYearLoc= "//select[contains(@id,'uxYearDropDown')]//option[@value='%s']";

	public boolean isUserLoggedIn(){
		driver.waitForElementPresent(LOGOUT_LINK_LOC);
		return driver.isElementPresent(LOGOUT_LINK_LOC);
	}

	private static final By CHECK_MY_PULSE_LINK_LOC = By.xpath("//a[text()='Check My Pulse']");

	public DSVStorefrontBrandRefreshPulsePage clickCheckMyPulse(){
		driver.click(CHECK_MY_PULSE_LINK_LOC);
		logger.info("Check My Pulse link clicked");
		if(driver.isElementPresent(DISMISS_ALERT_LOC))
			driver.click(DISMISS_ALERT_LOC);
		return new DSVStorefrontBrandRefreshPulsePage(driver);
	}

	public DSVStorefrontBrandRefreshHomePage dismissPolicyPopup(){
		try {	
			driver.quickWaitForElementPresent(By.id("agree"));
			WebElement we = driver.findElement(By.xpath("//div[@class='shipping-popup-gray']/span[1]"));
			if (we.isDisplayed()){
				we.click();
				driver.click(By.xpath("//input[@value='Continue']"));
				driver.waitForLoadingImageToDisappear();
			}
		}
		catch (Exception e) {
			System.out.println("Policy Popup Dialog not seen.");
		}
		return this;
	} 


	public DSVStorefrontBrandRefreshHomePage clickMyAccount(){
		driver.click(MY_ACCOUNT_LINK_LOC);
		logger.info("clicked on my account");
		return this;
	}

	public DSVStorefrontBrandRefreshPcPerksStatusPage clickChangePcPerksStatus(){
		driver.click(CLICK_CHANGE_PC_PERKS_STATUS_LOC);
		logger.info("Clicked on change pc perks status");
		return new DSVStorefrontBrandRefreshPcPerksStatusPage(driver);
	}

	public boolean verifyUserSuccessfullyLoggedIn() {
		driver.waitForPageLoad();
		driver.waitForElementPresent(HELLO_OR_WELCOME_TXT_ON_CORP);
		return driver.isElementPresent(HELLO_OR_WELCOME_TXT_ON_CORP);
	}

	public DSVStorefrontBrandRefreshHomePage mouseHoverShopSkinCareAndClickLink(String link){
		Actions actions =  new Actions(RFWebsiteDriver.driver);
		driver.pauseExecutionFor(2000);
		actions =  new Actions(RFWebsiteDriver.driver);
		actions.moveToElement(driver.findElement(SHOP_SKINCARE_LOC)).build().perform();
		logger.info("hover on Products link now as shop skincare");
		driver.click(By.xpath(String.format(linkUnderShopSkinCareOrBeAConsultant, link)));
		logger.info("Clicked "+link+" link is clicked after hovering shop skincare.");
		driver.waitForPageLoad();
		return this;
	}

	public boolean isAddToBagBtnPresent(){
		driver.waitForElementPresent(ADD_TO_BAG_BTN_LOC);
		return driver.isElementPresent(ADD_TO_BAG_BTN_LOC);
	}

	public DSVStorefrontBrandRefreshHomePage clickEditOrderLink(){
		driver.quickWaitForElementPresent(EDIT_ORDER_UNDER_MY_ACCOUNT_LOC);
		driver.click(EDIT_ORDER_UNDER_MY_ACCOUNT_LOC);
		logger.info("edit order link is clicked"); 
		return this;
	}

	public DSVStorefrontBrandRefreshHomePage clickChangeLinkUnderShippingToOnPWS(){
		driver.quickWaitForElementPresent(CHANGE_LINK_FOR_SHIPPING_INFO_ON_PWS);
		driver.click(CHANGE_LINK_FOR_SHIPPING_INFO_ON_PWS);
		logger.info("Change Link under shipping to clicked");
		driver.waitForPageLoad();
		return this;
	}

	public DSVStorefrontBrandRefreshHomePage enterShippingProfileDetailsForPWS(String addressName, String firstName,String lastName,String addressLine1,String postalCode,String phnNumber){
		driver.type(ADDRESS_NAME_FOR_SHIPPING_PROFILE, addressName);
		logger.info("Address name entered as: "+addressName);
		driver.type(ATTENTION_FIRST_NAME, firstName);
		logger.info("Attention first name entered as: "+firstName);
		driver.type(ATTENTION_LAST_NAME, lastName);
		logger.info("Attention last name entered as: "+lastName);
		driver.type(ADDRESS_LINE_1, addressLine1);
		logger.info("Address line 1 entered as: "+addressLine1);
		driver.type(ZIP_CODE, postalCode+"\t");
		driver.waitForStorfrontLegacyLoadingImageToDisappear();
		logger.info("Postal code entered as: "+postalCode);
		driver.click(By.xpath("//input[contains(@id,'uxCityDropDown_Input')]"));
		driver.waitForLoadingImageToDisappear();
		driver.pauseExecutionFor(2000);
		Actions actions = new Actions(RFWebsiteDriver.driver);
		actions.moveToElement(driver.findElement(CITY_DD)).click().build().perform();
		logger.info("City dropdown clicked");
		driver.waitForElementPresent(FIRST_VALUE_OF_CITY_DD);
		//driver.click(FIRST_VALUE_OF_CITY_DD);
		driver.clickByJS(RFWebsiteDriver.driver, driver.findElement(FIRST_VALUE_OF_CITY_DD));
		logger.info("First value of City selected");
		driver.waitForElementPresent(COUNTRY_DD);
		driver.click(COUNTRY_DD);
		logger.info("Country dropdown clicked");
		//driver.click(FIRST_VALUE_OF_COUNTRY_DD);
		driver.clickByJS(RFWebsiteDriver.driver, driver.findElement(FIRST_VALUE_OF_COUNTRY_DD));
		logger.info("First value of Country selected");
		driver.type(PHONE_NUMBER_SHIPPING_PROFILE_PAGE,phnNumber);
		logger.info("Phone number entered as: "+phnNumber);
		return this;
	}

	public String getShippingAddressName(){
		String name = driver.findElement(SHIPPING_ADDRESS_NAME_LOC).getText();
		logger.info("Fetched shipping address name is: "+name);
		return name;
	}

	public DSVStorefrontBrandRefreshHomePage selectDeleteForProfileAndAcceptConfirmationAlert(String profile){
		driver.click(By.xpath(String.format(deleteButtonForProfileLoc,profile)));
		logger.info("Delete Link Clicked for "+profile);
		driver.switchAndAcceptAlert();
		driver.navigate().refresh();
		return this;
	}

	public boolean isProfilePresentInSavedProfiles(String profile){
		return driver.isElementPresent(By.xpath(String.format(specificprofileInSavedProfilesLoc, profile)));
	}

	public void clickUseThisAddressShippingInformationBtn(){
		driver.quickWaitForElementPresent(USE_THIS_ADDRESS_SHIPPING_INFORMATION);
		driver.click(USE_THIS_ADDRESS_SHIPPING_INFORMATION);
		logger.info("Use this Address shipping information clicked");
		driver.waitForPageLoad();
	}

	public void clickChangeBillingInformationLinkUnderBillingTabOnPWS(){
		driver.quickWaitForElementPresent(CHANGE_BILLING_INFO_LINK_ON_PWS);
		driver.click(CHANGE_BILLING_INFO_LINK_ON_PWS);
		logger.info("Change Billing info link clicked");
		driver.waitForPageLoad();
	}

	public void clickUseThisBillingInformationBtn(){
		driver.quickWaitForElementPresent(USE_THIS_BILLING_INFORMATION);
		driver.click(USE_THIS_BILLING_INFORMATION);
		logger.info("Use this billing information clicked");
		driver.waitForPageLoad();
	}

	public String getBillingAddressName(){
		driver.findElement(By.xpath("//span[contains(@id,'lblBillingAddrName')]"));
		String name =  driver.findElement(By.xpath("//span[contains(@id,'lblBillingAddrName')]")).getText();
		logger.info("Billing profile name is "+name);
		return name;
	}

	public void enterBillingInfoForPWS(String billingName, String firstName,String lastName,String cardName,String cardNumer,String month,String year,String addressLine1,String postalCode,String phnNumber, String CVV){
		driver.type(BILLING_NAME_FOR_BILLING_PROFILE, billingName);
		logger.info("Billing profile name entered as: "+billingName);
		driver.type(ATTENTION_FIRST_NAME, firstName);
		//driver.type(BILLING_FIRST_NAME, firstName);
		logger.info("Attention first name entered as: "+firstName);
		driver.type(ATTENTION_LAST_NAME, lastName);
		//driver.type(BILLING_LAST_NAME, lastName);
		logger.info("Attention last name entered as: "+lastName);
		driver.type(NAME_ON_CARD, cardName);
		logger.info("Card Name entered as: "+cardName);
		driver.type(CREDIT_CARD_NUMBER_INPUT_FIELD, cardNumer);
		logger.info("Card number entered as: "+cardNumer);
		driver.type(By.id("ctl00_ContentPlaceHolder1_uxBillingEditor_uxCreditCardCvv"), CVV);
		logger.info("CVV entered as: "+CVV);
		driver.click(EXPIRATION_DATE_MONTH_DD);
		logger.info("Expiration month dropdown clicked");
		driver.click(By.xpath(String.format(expiryMonthLoc, month)));
		logger.info("Expiry month selected is: "+month);
		driver.click(EXPIRATION_DATE_YEAR_DD);
		logger.info("Expiration year dropdown clicked");
		driver.click(By.xpath(String.format(expiryYearLoc, year)));
		logger.info("Expiry year selected is: "+year);
		driver.type(ADDRESS_LINE_1, addressLine1);
		logger.info("Billing street address entered as: "+addressLine1);
		driver.type(ZIP_CODE, postalCode+"\t");
		logger.info("Postal code entered as: "+postalCode);
		driver.pauseExecutionFor(5000);
		driver.findElement(By.xpath("//input[contains(@id,'uxCityDropDown_Input')]")).click();
		driver.waitForLoadingImageToDisappear();
		driver.pauseExecutionFor(2000);
		//driver.click(CITY_DD);
		logger.info("City dropdown clicked");
		driver.waitForElementPresent(FIRST_VALUE_OF_CITY_DD);
		/*driver.click(FIRST_VALUE_OF_CITY_DD);*/
		driver.clickByJS(RFWebsiteDriver.driver, driver.findElement(FIRST_VALUE_OF_CITY_DD));
		logger.info("City selected");
		driver.waitForStorfrontLegacyLoadingImageToDisappear();
		driver.type(PHONE_NUMBER_BILLING_PROFILE_PAGE,phnNumber);
		logger.info("Phone number entered as: "+phnNumber);
	}


	public void clickShopSkinCareHeader() {
		driver.waitForElementPresent(SHOP_SKINCARE_HEADER_LOC);
		driver.click(SHOP_SKINCARE_HEADER_LOC);
		logger.info("shop skincare link on Header clicked");
	}

	public void selectRegimen(String regimen){
		regimen = regimen.toUpperCase();
		driver.get(driver.getURL()+"/Shop/REVERSE-BRIGHTENING/products");

		//		driver.quickWaitForElementPresent(By.xpath(String.format(regimenLoc, regimen)));
		//		driver.click(By.xpath(String.format(regimenLoc, regimen)));
		logger.info("Regimen selected is: "+regimen);
	}

	public void clickAddToCartBtn(){		
		driver.quickWaitForElementPresent(By.xpath("//*[@id='FullPageItemList']/div[2]//*[contains(text(),'Add to Bag')]"));
		if(driver.isElementPresent(By.xpath("//*[@id='FullPageItemList']/div[2]//*[contains(text(),'Add to Bag')]"))){
			driver.click(By.xpath("//*[@id='FullPageItemList']/div[2]//*[contains(text(),'Add to Bag')]"));	
		}
		else if(driver.isElementPresent(ADD_TO_CART_BTN_LOC)){
			driver.click(ADD_TO_CART_BTN_LOC);	
		}
		else{
			driver.click(By.xpath("//a[text()='Add to Cart']"));
		}		
		logger.info("Add to cart button is clicked");
		driver.waitForPageLoad();		
	}

	public void clickCheckoutBtn(){
		driver.waitForElementPresent(CHECKOUT_BTN);
		driver.click(CHECKOUT_BTN);
		logger.info("Checkout button clicked");
		driver.waitForPageLoad();
	}

	public void clickContinueWithoutConsultantLink(){
		driver.quickWaitForElementPresent(CONTINUE_WITHOUT_CONSULTANT_LINK);
		driver.click(CONTINUE_WITHOUT_CONSULTANT_LINK);
		logger.info("Continue without sponser link clicked");
	}

	public void clickChangeBillingInformationBtn(){
		if(driver.isElementPresent(CHANGE_BILLING_INFO)){
			driver.click(CHANGE_BILLING_INFO);
			logger.info("Change billing information button clicked");
			driver.waitForPageLoad();
			driver.pauseExecutionFor(3000);
		}
		else{
			logger.info("User does not have any Billing Information Yet");
		}
	}

	public void clickChangeShippingAddressBtn(){
		if(driver.isElementPresent(CHANGE_SHIPPING_ADDRESS_LOC)){
			driver.click(CHANGE_SHIPPING_ADDRESS_LOC);
			logger.info("Change Shipping Address button clicked");
			driver.waitForPageLoad();
			driver.pauseExecutionFor(3000);
		}
		else{
			logger.info("User does not have any Shipping Address Yet");
		}
	}

	public void enterBillingInfo(String billingName, String firstName,String lastName,String cardName,String cardNumer,String month,String year,String addressLine1,String postalCode,String phnNumber,String CVV){
		driver.type(BILLING_NAME_FOR_BILLING_PROFILE, billingName);
		logger.info("Billing profile name entered as: "+billingName);
		driver.type(ATTENTION_FIRST_NAME, firstName);
		//driver.type(BILLING_FIRST_NAME, firstName);
		logger.info("Attention first name entered as: "+firstName);
		driver.type(ATTENTION_LAST_NAME, lastName);
		//driver.type(BILLING_LAST_NAME, lastName);
		logger.info("Attention last name entered as: "+lastName);
		driver.type(NAME_ON_CARD, cardName);
		logger.info("Card Name entered as: "+cardName);
		driver.type(CREDIT_CARD_NUMBER_INPUT_FIELD, cardNumer);
		logger.info("Card number entered as: "+cardNumer);
		driver.type(By.xpath("//input[contains(@id,'uxCreditCardCvv')]"), CVV);
		logger.info("CVV entered as: "+CVV);
		driver.click(EXPIRATION_DATE_MONTH_DD);
		logger.info("Expiration month dropdown clicked");
		driver.click(By.xpath(String.format(expiryMonthLoc, month)));
		logger.info("Expiry month selected is: "+month);
		driver.click(EXPIRATION_DATE_YEAR_DD);
		logger.info("Expiration year dropdown clicked");
		driver.click(By.xpath(String.format(expiryYearLoc, year)));
		logger.info("Expiry year selected is: "+year);
		driver.type(ADDRESS_LINE_1, addressLine1);
		logger.info("Billing street address entered as: "+addressLine1);
		driver.type(ZIP_CODE, postalCode+"\t");
		logger.info("Postal code entered as: "+postalCode);
		driver.pauseExecutionFor(3000);
		//  driver.findElement(By.xpath("//input[contains(@id,'uxCityDropDown_Input')]")).click();
		//  driver.waitForStorfrontLegacyLoadingImageToDisappear();
		//driver.pauseExecutionFor(5000);
		driver.waitForSpinImageToDisappear();
		driver.pauseExecutionFor(3000);
		/*Actions actions = new Actions(RFWebsiteDriver.driver);
		  actions.moveToElement(driver.findElement(CITY_DD)).click().build().perform();*/
		driver.clickByJS(RFWebsiteDriver.driver, driver.findElement(CITY_DD));
		//driver.click(CITY_DD);
		logger.info("City dropdown clicked");
		driver.waitForElementPresent(FIRST_VALUE_OF_CITY_DD);
		driver.clickByJS(RFWebsiteDriver.driver, driver.findElement(FIRST_VALUE_OF_CITY_DD));
		logger.info("City selected");
		//  driver.type(CITY_DD, "Fremont");
		//  logger.info("City Selected");
		driver.type(PHONE_NUMBER_BILLING_PROFILE_PAGE,phnNumber);
		logger.info("Phone number entered as: "+phnNumber);
	}

	public DSVStorefrontBrandRefreshHomePage clickContinueOnBillingInfoPage(){
		driver.quickWaitForElementPresent(CONTINUE_BTN_AT_BILLING_INFO_LOC);
		driver.click(CONTINUE_BTN_AT_BILLING_INFO_LOC);
		logger.info("Continue btn clicked on billing info change"); 
		return this;
	}

	public DSVStorefrontBrandRefreshHomePage clickContinueOnShippingInfoPage(){
		driver.quickWaitForElementPresent(CONTINUE_BTN_AT_SHIPPING_INFO_LOC);
		driver.click(CONTINUE_BTN_AT_SHIPPING_INFO_LOC);
		logger.info("Continue btn clicked on shipping info change"); 
		return this;
	}

	public DSVStorefrontBrandRefreshHomePage clickOrderHistory(){
		driver.click(ORDER_HISTORY_LINK_LOC);
		logger.info("Clicked on Order History");
		return this ;

	}
	public DSVStorefrontBrandRefreshHomePage clickViewDetails(){
		driver.click(VIEW_DETAILS_LINK_LOC);
		logger.info("Clicked on View Details");
		return this ;

	}

	public boolean isShippingAdressPresentOnOrderDetails(){
		return driver.isElementPresent(VERIFY_SHIPPING_ADDRESS_TXT_LOC);
	}

	public boolean isBillingAdressPresentOnOrderDetails(){
		return driver.isElementPresent(VERIFY_BILLING_ADDRESS_TXT_LOC);
	}

	public boolean isOrderNumberPresentOnOrderHistory(){
		return (!driver.findElement(ORDER_NUMBER_ON_ORDER_HISTORY_LOC).getText().isEmpty());
	}

	public boolean isOrderDatePresentOnOrderHistory(){
		return (!driver.findElement(ORDER_DATE_ON_ORDER_HISTORY_LOC).getText().isEmpty());
	}

	public boolean isOrderItemPresentOnOrderHistory(){
		return (!driver.findElement(ORDER_ITEM_ON_ORDER_HISTORY_LOC).getText().isEmpty());
	}

	public boolean isOrderGrandTotalPresentOnOrderHistory(){
		return (!driver.findElement(ORDER_GRAND_TOTAL_ON_ORDER_HISTORY_LOC).getText().isEmpty());
	}

	public boolean isProductNamePresentOnOrderDetails(){
		return driver.isElementPresent(VERIFY_PRODUCT_NAME_ORDER_DETAIL_LOC);
	}

	public boolean isGrandTotalPresentOnOrderDetails(){
		return (!driver.findElement(GRAND_TOTAL_ORDER_DETAIL_LOC).getText().isEmpty());
	}

	public DSVStorefrontBrandRefreshHomePage clickEditOrderBtn(){
		driver.quickWaitForElementPresent(EDIT_ORDER_BTN_LOC);
		driver.click(EDIT_ORDER_BTN_LOC);
		logger.info("edit order link is clicked"); 
		return this;
	}

	public double getAutoshipOrderTotal(){
		driver.quickWaitForElementPresent(AUTOSHIP_TOTAL_LOC);
		String total = driver.getText(AUTOSHIP_TOTAL_LOC).split("\\$")[1];
		logger.info("Autoship total is "+total); 
		Double autoshipTotal = Double.parseDouble(total);
		return autoshipTotal;
	}

	public DSVStorefrontBrandRefreshHomePage removeAllProductFromAutoshipCart(){
		while(true){
			driver.pauseExecutionFor(2000);
			if(driver.isElementPresent(REMOVE_LINK_LOC)){
				driver.pauseExecutionFor(2000);
				driver.clickByJS(RFWebsiteDriver.driver,driver.findElement(REMOVE_LINK_LOC));
				driver.waitForSpinImageToDisappear();
				driver.pauseExecutionFor(5000);
			}else {
				break;
			}
		}
		return this;
	}


	public String getOrderUpdationMessage(){
		String message = driver.getText(ORDER_UPDATION_MSG_LOC);
		logger.info("message of updation"+message);
		return message.trim();
	}

	public boolean compareTotal(double priceBeforeAdd, double priceAfterAdd){
		return priceAfterAdd>priceBeforeAdd;
	}

	public DSVStorefrontBrandRefreshHomePage removeFirstProductFromAutoshipCart(){
		driver.pauseExecutionFor(2000);
		driver.clickByJS(RFWebsiteDriver.driver,driver.findElement(REMOVE_LINK_LOC));
		logger.info("Remove link clicked of first product");
		driver.pauseExecutionFor(2000);
		return this;
	}

	public String getFirstProductNamePresentIntoAutoshipCart(){
		driver.pauseExecutionFor(2000);
		String productName = driver.getText(FIRST_PRODUCT_NAME_IN_AUTOSHIP_CART).trim().toLowerCase();
		logger.info("First product in to autoship cart "+productName);
		return productName;
	}

	public String getItemCount(){
		String itemCount = driver.getText(ITEM_COUNT_LOC);
		logger.info("message of updation"+itemCount);
		return itemCount.trim();
	}

	public String addProductToBagForPCTillUpdateOrderBtnIsEnabled(int productNumber) {
		String productName = null;
		while(true){
			productName = driver.findElement(By.xpath(String.format("//div[@id='ContentWrapper']/descendant::a[text()='Add to Bag'][%s]/preceding::h3[@class='ProductName'][1]", productNumber))).getText().toLowerCase().trim();
			driver.click(By.xpath(String.format(addToBagProduct,productNumber)));
			driver.pauseExecutionFor(3000);
			logger.info("Product is added into cart");
			if(driver.isElementPresent(REMOVE_LINK_LOC)){
				break;
			}
		}
		return productName;

	}

	public DSVStorefrontBrandRefreshHomePage clickUpdateOrderBtnAndAcceptConfirmationOnAlert(){
		driver.pauseExecutionFor(3000);
		driver.quickWaitForElementPresent(UPDATE_ORDER_BTN_LOC);
		driver.clickByJS(RFWebsiteDriver.driver, driver.findElement(UPDATE_ORDER_BTN_LOC));
		logger.info("update order button is clicked"); 
		driver.switchAndAcceptAlert();
		driver.waitForSpinImageToDisappear();
		driver.pauseExecutionFor(3000);
		return this;
	}

	public boolean isSponsorInfoPresentOnPage(){
		logger.info("Waiting for Sponsor information on Page");
		driver.waitForElementPresent(By.linkText("R+F Independent Consultant"));
		return driver.findElement(By.linkText("R+F Independent Consultant")).isDisplayed();
	}

	public void selectVariantAndAddProductToAdHocCart(String productName, String shade){
		logger.info("Waiting for Variant Product");
		driver.waitForElementPresent(By.xpath(String.format(addToBagButtonForProductNameLoc, productName)));
		Select selectVarient=new Select(driver.findElement(By.xpath(String.format(shadeDDLoc, TestConstantsRFL.DSV_VARIANT_PRODUCT_FULL_NAME))));
		logger.info("Variant Product is available. Selecting value from dropdown.");
		selectVarient.selectByVisibleText(shade);
		driver.click(By.xpath(String.format(addToBagButtonForProductNameLoc, productName)));
		logger.info("Variant Product"+productName+ "added to Ad-hoc cart");
		driver.waitForPageLoad();
	}

	public boolean isVariantProductAvailableInCart(String productName){
		if(driver.findElement(By.xpath(String.format(productNameInCartLoc, productName))).isDisplayed()){
			return true;
		}else{
			return false;
		}
	}

	public boolean isProductPresentOnPCAutoshipOverViewPage(String productName){
		boolean flag=false;
		driver.waitForElementPresent(ALL_PRODUCT_ON_CRP_OVERVIEW_PAGE_LOC);
		List<WebElement> lis = driver.findElements(ALL_PRODUCT_ON_CRP_OVERVIEW_PAGE_LOC);
		for (WebElement wb : lis) {
			if (wb.getText().toLowerCase().contains(productName.toLowerCase())) {
				flag= true;
				break;
			}
		}
		return flag;
	}

	public int getPositionOfProductInPCOverViewPage(String productName) {
		List<WebElement> lis = driver.findElements(ALL_PRODUCT_ON_CRP_OVERVIEW_PAGE_LOC);
		int i = 1;
		for (WebElement wb : lis) {
			if (wb.getText().toLowerCase().contains(productName.toLowerCase())) {
				break;
			}
			i++;
		}
		logger.info("Value of i is"+i);
		return i;
	}
}
