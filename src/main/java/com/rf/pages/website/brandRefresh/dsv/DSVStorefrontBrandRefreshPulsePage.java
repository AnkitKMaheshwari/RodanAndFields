package com.rf.pages.website.brandRefresh.dsv;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.rf.core.driver.website.RFWebsiteDriver;
import com.rf.core.website.constants.TestConstants;

public class DSVStorefrontBrandRefreshPulsePage extends DSVStorefrontBrandRefreshWebsiteBasePage {
	private static final Logger logger = LogManager
			.getLogger(DSVStorefrontBrandRefreshPulsePage.class.getName());

	public DSVStorefrontBrandRefreshPulsePage(RFWebsiteDriver driver) {
		super(driver);		
	}

	private static final By PHONE_NUMBER_FIELD_LOC = By.id("phone");
	private static final By USERNAME_DD_FOR_PULSE_LOC = By.xpath("//span[@class='UserID']");
	private static final By MY_ACCOUNT_TAB_LOC = By.xpath("//span[text()='My Account']");
	private static final By DEFAULT_SHIPPING_ADDRESS = By.xpath("//h5[contains(text(),'My Shipping Profiles')]/following::span[contains(@class,'profileDisplay')][1]");
	private static final By DEFAULT_BILLING_ADDRESS = By.xpath("//h5[contains(text(),'My Shipping Profiles')]/following::span[contains(@class,'profileDisplay')][2]");
	private static final By NEXT_RUN_DATE_CRP_TXT_LOC = By.xpath("//h5[contains(text(),'CRP')]/following::label[contains(text(),'Next run date')][1]");
	private static final By NEXT_RUN_DATE_PULSE_TXT_LOC = By.xpath("//h5[contains(text(),'CRP')]/following::label[contains(text(),'Next run date')][2]");
	private static final By ADD_NEW_SHIPPING_PROFILE_LINK_LOC = By.id("addNewShipping");
	private static final By ADDRESS_NAME_FIELD_LOC = By.id("AddressName");
	private static final By ATTENTION_NAME_FIELD_LOC = By.id("attention");
	private static final By ADDRESS_FIELD_LOC = By.id("street");
	private static final By POSTAL_CODE_FIELD_LOC = By.id("postalcode");
	private static final By SAVE_PROFILE_CHANGES_LOC = By.id("submit");
	private static final By DELETE_PROFILE_LINK_LOC = By.id("deleteAddress");
	private static final By ORDERS_TAB=By.xpath("//span[contains(text(),'Orders')]");
	private static final By ORDER_VIEW_DETAILS=By.xpath("//a[contains(text(),'view details')]");
	private static final By SHIPPING_DETAILS_ON_ORDER_DETAILS_PAGE_LOC=By.xpath("//label[contains(text(),'Shipped To')]/following::div[1]");
	private static final By BILLING_DETAILS_ON_ORDER_DETAILS_PAGE_LOC=By.xpath("//label[contains(text(),'Billed To')]/following::div[1]");
	private static final By PRODUCT_NAME_ON_ORDER_DETAILS_PAGE=By.xpath("//table[@class='Itemized']//tr[2]//td[3]");
	private static final By ORDER_TOTAL_ON_ORDER_DETAILS_PAGE_LOC=By.xpath("//table[@class='Itemized']//tr[2]//td[6]");
	private static final By MY_ACCOUNT_LOC=By.xpath("//span[contains(text(),'My Account')]");
	private static final By ADD_NEW_BILLING_PROFILE_LOC = By.xpath("//*[@id='addNewBilling']/span");
	private static final By PROFILE_NAME_LOC = By.xpath("//input[@id='PaymentName']");
	private static final By CARD_NUMBER_LOC = By.xpath("//input[@id='cardNumber']");
	private static final By NAME_ON_CARD_LOC = By.xpath("//input[@id='NameOnCard']");
	private static final By EXPIRY_MONTH_DROP_DOWN = By.id("ExpirationMonth");
	private static final By EXPIRY_YEAR_DROP_DOWN = By.id("ExpirationYear");
	private static final By SECURITY_CODE_LOC = By.id("securityCode");
	private static final By ADDRESS_LINE1_LOC = By.id("street");
	private static final By POSTAL_CODE_LOC = By.id("postalcode");
	private static final By PHONE_NUMBER_LOC = By.id("phone");
	private static final By SAVE_BILLING_PROFILE_LOC = By.xpath("//*[@id='submit']/span");
	private static final By QAS_POPUP_LOC = By.id("QAS_AcceptOriginal");
	private static final By BILLING_PROFILE_DELETE_LOC = By.xpath("//a[@id='deleteAddress']/span");
	private static final By MAIN_ACCOUNT_INFO_EDIT_LOC = By.xpath("//h5[contains(text(),'My Primary Account Record')]/following::a[1]");
	private static final By ADDRESS_LINE1_MAIN_ACCOUNT_INFO_LOC = By.id("street");
	private static final By POSTAL_CODE_MAIN_ACCOUNT_INFO_LOC = By.id("postalcode");
	private static final By PHONE_NUMBER_MAIN_ACCOUNT_INFO_LOC = By.id("MainPhone");
	private static final By MAIN_ACCOUNT_INFO_PAGE_LOC = By.xpath("//label[contains(text(),'Main Address:')]/following-sibling::p");
	private static final By PHONE_NUMBER_MAIN_ACCOUNT_INFO_PAGE_LOC = By.xpath("//label[contains(text(),'Phone:')]/following-sibling::p");
	private static final By EDIT_CRP_LOC=By.xpath("//a[contains(@id,'EditMyCRP')]");
	private static final By EDIT_REPLENISHMENT_ORDER_BUTTON_LOC=By.xpath("//a[contains(text(),'Edit Replenishment Order')]");
	private static final By UPDATE_ORDER_LOC=By.xpath("//a[contains(text(),'Update Order')]");
	private static final By ALL_PRODUCT_ON_CRP_OVERVIEW_PAGE_LOC=By.xpath("//td[@class='CartThumb']/following-sibling::td[1]");
	private static final By PRODUCT_AVAILABLE_IN_CART_LOC=By.xpath("//div[@id='cartHtmlHolder']//li");
	private static final By PRODUCT_POSITION_CRP_PRODUCT_PAGE_LOC=By.xpath("//div[@id='cartHtmlHolder']//li");
	private static final By CROOS_ICON_OF_VIEW_DETAILS_POPUP = By.xpath("//div[@id='dialog']//a[@class='FR']//canvas");
	private static final By MY_COM_PWS_LINK_FROM_USER_LINK=By.xpath("//ul[@id='userLinks']//a[contains(@class,'pwsLink')]/span[contains(normalize-space(),'.com PWS')]");
	private static final By MY_BIZ_PWS_LINK_FROM_USER_LINK=By.xpath("//ul[@id='userLinks']//a[contains(@class,'pwsLink')]/span[contains(normalize-space(),'.biz PWS')]");
	private static final By PERIOD_DROPDOWN = By.id("PeriodId");
	private static final By RUN_REPORT_BUTTON = By.xpath("//div[@id='orderFilters']//span[text()='Run Report']");

	private static String productQuantityFromProductPage="//div[@id='ContentWrapper']/descendant::input[%s]";
	private static String productQuantityFromCRPCart="//div[@class='mkc-container']//tr[@class='tdcontent'][%s]//td[3]";
	private static String productName="//div[@id='ContentWrapper']/descendant::h3[%s]";
	private static String detailsLoc = "//label[contains(text(),'%s')]/following::p[1]";
	private static String profileLoc = "//a[contains(text(),'%s')]";
	private static String expiryMonth= "//select[@id='ExpirationMonth']/option[@value='%s']";
	private static String expiryYear= "//select[@id='ExpirationYear']/option[@value='%s']";

	public boolean isUserAutoLoginInPulse(){
		driver.waitForElementPresent(USERNAME_DD_FOR_PULSE_LOC);
		return driver.isElementPresent(USERNAME_DD_FOR_PULSE_LOC);
	}


	public DSVStorefrontBrandRefreshPulsePage clickMyAccountTab(){
		driver.click(MY_ACCOUNT_TAB_LOC);
		logger.info("clicked on My Account tab");
		return this;
	}

	public boolean isMainAddressPresent(String labelName){
		driver.waitForElementPresent(By.xpath(String.format(detailsLoc, labelName)));
		String details = driver.getText(By.xpath(String.format(detailsLoc, labelName)));
		logger.info("Main address is "+details);
		return (!details.isEmpty());
	}

	public boolean isDefaultShippingAddressPresent(){
		driver.waitForElementPresent(DEFAULT_SHIPPING_ADDRESS);
		String details = driver.getText(DEFAULT_SHIPPING_ADDRESS);
		logger.info("shipping address is "+details);
		return (!details.isEmpty());
	}

	public boolean isDefaultBillingAddressPresent(){
		driver.waitForElementPresent(DEFAULT_BILLING_ADDRESS);
		String details = driver.getText(DEFAULT_BILLING_ADDRESS);
		logger.info("billing address is "+details);
		return (!details.isEmpty());
	}

	public boolean isUserCRPEnrolled(){
		driver.waitForElementPresent(NEXT_RUN_DATE_CRP_TXT_LOC);
		return driver.isElementPresent(NEXT_RUN_DATE_CRP_TXT_LOC);
	}

	public boolean isUserPulseEnrolled(){
		driver.waitForElementPresent(NEXT_RUN_DATE_PULSE_TXT_LOC);
		return driver.isElementPresent(NEXT_RUN_DATE_PULSE_TXT_LOC);
	}

	public DSVStorefrontBrandRefreshPulsePage clickAddNewShippingProfileLink(){
		driver.click(ADD_NEW_SHIPPING_PROFILE_LINK_LOC);
		logger.info("clicked on add new shipping profile link");
		driver.waitForPageLoad();
		return this;
	}

	public DSVStorefrontBrandRefreshPulsePage enterShippingDetails(String addressName, String attentionName, String address, String postalCode, String phoneNumber){
		driver.type(ADDRESS_NAME_FIELD_LOC, addressName);
		logger.info("Address name entered as "+addressName);
		driver.type(ATTENTION_NAME_FIELD_LOC, attentionName);
		logger.info("attention name entered as "+attentionName);
		driver.type(ADDRESS_FIELD_LOC, address);
		logger.info("address entered as "+address);
		driver.type(POSTAL_CODE_FIELD_LOC, postalCode+"\t");
		logger.info("postal code  entered as "+postalCode);
		driver.type(PHONE_NUMBER_FIELD_LOC, phoneNumber);
		logger.info("phone number entered as "+phoneNumber);
		return this;
	}

	public DSVStorefrontBrandRefreshPulsePage clickSaveProfileChanges(){
		driver.click(SAVE_PROFILE_CHANGES_LOC);
		logger.info("clicked on save profile changes btn");
		acceptQASPopup();
		driver.waitForPageLoad();
		return this;
	}

	public boolean isProfilePresent(String profileName){
		driver.waitForElementPresent(By.xpath(String.format(profileLoc, profileName)));
		return driver.isElementPresent(By.xpath(String.format(profileLoc, profileName)));
	}

	public DSVStorefrontBrandRefreshPulsePage clickProfileName(String profileName){
		driver.click(By.xpath(String.format(profileLoc, profileName)));
		logger.info("clicked on"+profileName+" profile");
		driver.waitForPageLoad();
		return this;
	}

	public DSVStorefrontBrandRefreshPulsePage deleteProfile(){
		driver.click(DELETE_PROFILE_LINK_LOC);
		logger.info("Delete profile link clicked");
		driver.waitForPageLoad();
		return this;
	}

	//================================================********Abhishek*****=============================================================================//


	public DSVStorefrontBrandRefreshPulsePage clickOrdersTab(){
		driver.click(ORDERS_TAB);
		logger.info("clicked on orders tab");
		return this;
	}

	public DSVStorefrontBrandRefreshPulsePage clickViewOrderDetails(){
		driver.click(ORDER_VIEW_DETAILS);
		logger.info("clicked on Order details tab");
		return this;
	}

	public boolean isShippingDetailsPresentOnOrderDetailsPage(){
		return driver.isElementPresent(SHIPPING_DETAILS_ON_ORDER_DETAILS_PAGE_LOC);
	}

	public boolean isBillingDetailsPresentOnOrderDetailsPage(){
		return driver.isElementPresent(BILLING_DETAILS_ON_ORDER_DETAILS_PAGE_LOC);
	}

	public boolean isProductNamePresentOnOrderDetailsPage(){
		return driver.isElementPresent(PRODUCT_NAME_ON_ORDER_DETAILS_PAGE);
	}

	public boolean isOrderTotalPresentOnOrderDetailsPage(){
		return driver.isElementPresent(ORDER_TOTAL_ON_ORDER_DETAILS_PAGE_LOC);
	}


	public void clickAddNewBillingProfile(){
		driver.quickWaitForElementPresent(ADD_NEW_BILLING_PROFILE_LOC);
		driver.click(ADD_NEW_BILLING_PROFILE_LOC);
		driver.waitForPageLoad();
	}	
	public void enterBillingDetails(String profileName,String cardNumber,String nameOnCard,String expMonth,String expYear,String cvv,String addressLine1,String postalCode,String phoneNumber){
		driver.type(PROFILE_NAME_LOC, profileName);
		driver.type(CARD_NUMBER_LOC, cardNumber);
		driver.type(NAME_ON_CARD_LOC, nameOnCard);
		driver.click(EXPIRY_MONTH_DROP_DOWN);
		driver.click(By.xpath(String.format(expiryMonth,expMonth)));
		driver.click(EXPIRY_YEAR_DROP_DOWN);
		driver.click(By.xpath(String.format(expiryYear,expYear)));
		driver.type(SECURITY_CODE_LOC,cvv);
		driver.type(ADDRESS_LINE1_LOC, addressLine1);
		driver.type(POSTAL_CODE_LOC, postalCode);
		driver.type(PHONE_NUMBER_LOC, phoneNumber);		
	}

	public boolean isBillingProfilePresentOnPage(String billingName){
		driver.pauseExecutionFor(3000);
		return driver.isElementPresent(By.xpath("//a[contains(text(),'"+billingName+"')]"));
	}

	public void clickEditBillingProfileLink(String billingName){
		driver.click(By.linkText(billingName));
		driver.waitForPageLoad();
	}

	public void enterBillingProfileName(String billingProfileName){
		driver.type(PROFILE_NAME_LOC, billingProfileName);
		logger.info("Edited profile name is"+billingProfileName);
	}

	public void deleteBillingProfile(){
		driver.quickWaitForElementPresent(BILLING_PROFILE_DELETE_LOC);
		driver.click(BILLING_PROFILE_DELETE_LOC);
		driver.waitForPageLoad();
	}	

	public void editMainAccountInfo(){
		driver.quickWaitForElementPresent(MAIN_ACCOUNT_INFO_EDIT_LOC);
		driver.click(MAIN_ACCOUNT_INFO_EDIT_LOC);
		driver.waitForPageLoad();
	}

	public void enterAddressLineForAccountInfo(String addressLine1){
		driver.type(ADDRESS_LINE1_MAIN_ACCOUNT_INFO_LOC, addressLine1);
		logger.info("Address line1 for main account info"+addressLine1);
	}

	public void enterPostalCodeForAccountInfo(String postalCode){
		driver.type(POSTAL_CODE_MAIN_ACCOUNT_INFO_LOC, postalCode);
		logger.info("Postal code for main account info"+postalCode);
	}

	public void enterPhoneNumberForAccountInfo(String phoneNumber){
		driver.type(PHONE_NUMBER_MAIN_ACCOUNT_INFO_LOC, phoneNumber);
		logger.info("Phone Number for main account info"+phoneNumber);
	}

	public boolean isAddressLineForAccountInfoPresent(String addressLine1){
		return driver.findElement(MAIN_ACCOUNT_INFO_PAGE_LOC).getText().trim().toLowerCase().contains(addressLine1.trim().toLowerCase());
	}

	public boolean isPostalCodeForAccountInfoPresent(String postalCode){
		return driver.findElement(MAIN_ACCOUNT_INFO_PAGE_LOC).getText().contains(postalCode);
	}

	public boolean isPhoneNumberForAccountInfoPresent(String phonenumber){
		String phone= driver.findElement(PHONE_NUMBER_MAIN_ACCOUNT_INFO_PAGE_LOC).getText().replaceAll("-","");
		return	phone.contains(phonenumber);
	}

	public DSVStorefrontBrandRefreshPulsePage clickEditCRP(){
		driver.click(EDIT_CRP_LOC);
		driver.switchToSecondWindow();
		logger.info("Clicked on Edit My CRP");
		return this;
	}

	public DSVStorefrontBrandRefreshPulsePage clickEditReplenishmentOrder(){
		driver.waitForPageLoad();
		driver.click(EDIT_REPLENISHMENT_ORDER_BUTTON_LOC);
		logger.info("Clicked on Edit Replenishment Order Button");
		return this;
	}

	public String getProductName(int productNumber){
		String ProductName=null;
		ProductName=driver.getText(By.xpath(String.format(productName, productNumber)));
		return ProductName;
	}

	public DSVStorefrontBrandRefreshPulsePage clickUpdateOrder(){
		driver.pauseExecutionFor(5000);
		driver.click(UPDATE_ORDER_LOC);
		logger.info("Clicked on Update Order Button");
		return this;
	}

	public boolean isProductPresentOnCRPOverViewPage(String productName){
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

	public int getPositionOfProductInCRPOverViewPage(String productName) {
		List<WebElement> lis = driver.findElements(ALL_PRODUCT_ON_CRP_OVERVIEW_PAGE_LOC);
		int i = 1;
		for (WebElement wb : lis) {
			if (wb.getText().toLowerCase().contains(productName.toLowerCase())) {
				break;
			}
			i++;
		}
		logger.info("Value of i Is"+i);
		return i;
	}

	public String getProductQuantity(int productPosition){
		return driver.getText(By.xpath(String.format(productQuantityFromCRPCart, productPosition)));
	}

	public String getProductQuantityFromProductPage(int productNumber){
		return driver.getText(By.xpath(String.format(productQuantityFromProductPage, productNumber)));
	}

	public int getProductCount(){
		int size=driver.findElements(PRODUCT_AVAILABLE_IN_CART_LOC).size();
		return size;
	}

	public int getPositionOfProductFromCRPProductPage(String productName) {
		List<WebElement> lis = driver.findElements(PRODUCT_POSITION_CRP_PRODUCT_PAGE_LOC);
		int i = 1;
		for (WebElement wb : lis) {
			logger.info("list contains product "+wb.getText());
			if (wb.getText().toLowerCase().contains(productName.toLowerCase())) {
				break;
			}
			i++;
		}
		logger.info("Value of i Is"+i);
		return i;
	}

	public DSVStorefrontBrandRefreshPulsePage clickCrossIconOfViewOrderDetailsPopup(){
		driver.click(CROOS_ICON_OF_VIEW_DETAILS_POPUP);
		logger.info("Clicked on cross icon of view details popup");
		return this;
	}

	public DSVStorefrontBrandRefreshPulsePage clickComPWSLinkFromUserLinks(){
		driver.click(MY_COM_PWS_LINK_FROM_USER_LINK);
		logger.info("Clicked on .COM link from the user links");
		return this;
	}

	public DSVStorefrontBrandRefreshPulsePage clickBizPWSLinkFromUserLinks(){
		driver.click(MY_BIZ_PWS_LINK_FROM_USER_LINK);
		logger.info("Clicked on .BIZ link from the user links");
		return this;
	}

	public String getParentWindowHandle(){
		return driver.getWindowHandle();
	}

	public void switchToChildWindow(){
		driver.switchToSecondWindow();
	}

	public void closeChildAndSwitchToParentWindow(String parentWindowHandle){
		driver.switchToChildWindow(parentWindowHandle);
		driver.close();
		driver.switchTo().window(parentWindowHandle);
		logger.info("Child window closed and swiched to Parent");
	}

	public boolean isOrdersPresentOnOrdersDetailsPage(){
		if(driver.isElementPresent(ORDER_VIEW_DETAILS)){
			return driver.isElementPresent(ORDER_VIEW_DETAILS);
		}else{
			logger.info("Searching orders for previous month");
			Select selectPeriodFromDropdown = new Select(driver.findElement(PERIOD_DROPDOWN));
			selectPeriodFromDropdown.selectByVisibleText("Oct 2017");
			driver.click(RUN_REPORT_BUTTON);
			driver.waitForElementPresent(ORDER_VIEW_DETAILS);
			return driver.isElementPresent(ORDER_VIEW_DETAILS);
		}
	}

}

