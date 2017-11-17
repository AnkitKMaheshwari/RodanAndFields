package com.rf.pages.website.nscore.dsv;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.rf.core.driver.website.RFWebsiteDriver;
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

public class DSVNSCore4HomePage extends DSVNSCore4RFWebsiteBasePage{

	private static final Logger logger = LogManager
			.getLogger(DSVNSCore4HomePage.class.getName());

	int allBillingProfilesSize = 0;

	public DSVNSCore4HomePage(RFWebsiteDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	private String newlyCeatedShippingProfile = "//div[@id='ContentWrap']//table//a[contains(text(),'%s')]";
	private String deleteAddressnewlyCeatedShippingProfile = "//div[@id='ContentWrap']//table//a[contains(text(),'%s')]/preceding::a[contains(text(),'Delete Address')][1]";
	private String deletePaymentMethodLinkLoc = "//div[@id='paymentMethods']//a[contains(text(),'%s')]/preceding::a[contains(text(),'Delete Payment Method')][1]";
	private String billingProfileInPaymentMethodsLoc = "//div[@id='paymentMethods']//a[contains(text(),'%s')]";
	private String editLinkForShippingProfileLoc = "//div[@id='addresses']//a[contains(text(),'%s')]";
	private String orderHistoryColumnWithLinkLoc = "//table[@id='orders']/tbody/tr[1]/td[%s]/a";
	private String userNameInLeftNavigationLoc = "//div[@class='TagInfo']//h1[contains(text(),'%s')]";
	private String accountNumberInLeftNavigationLoc = "//p[@class='DistributorStatus' and contains(text(),'%s')]";
	private String prefixInWebsiteUrlsLoc = "//td[contains(text(),'Website URL')]//following::input[%s]";
	private String domainInWebsiteUrlsLoc = "//td[contains(text(),'Website URL')]//following::select[%s]/option[@selected]";
	private String accountNumberInAutoSuggestionLoc = "//div[contains(@class,'jsonSuggestResults')][1]//p[contains(text(),'%s') and contains(text(),'%s')]//strong[contains(text(),'%s')]";
	private String accountNumberInSearchedResults = "//div[@class='ui-grid-canvas']/descendant::div[1]//a[text()='%s']";
	private String firstNameForSepecificAccountLoc = "//div[@class='ui-grid-canvas']/descendant::div[1]//a[text()='%s']/ancestor::div[@id][1]/following-sibling::div[1]/div";
	private String lastNameForSepecificAccountLoc = "//div[@class='ui-grid-canvas']/descendant::div[1]//a[text()='%s']/ancestor::div[@id][1]/following-sibling::div[2]/div";
	private String orderLinkFromAccountOverviewOrdersLoc = "//div[@class='ui-grid-canvas']/div[%s]//a";
	
	private static final By ORDERS_IN_ACCOUNT_OVERVIEW_LOC =  By.xpath("//div[@class='ui-grid-canvas']/div");
	private static final By ACCOUNT_NUB_TF_LOC = By.xpath("//label[contains(text(),'CID')]/following-sibling::input[1]");
	private static final By EMAIL_ID_TF_LOC = By.xpath("//label[contains(text(),'Email')]/following-sibling::input[1]");
	private static final By SEARCH_ACCOUNT_BTN_LOC = By.xpath("//button[contains(@title,'Search Accounts')]");
	private static final By USE_AS_ENTERED_BTN = By.xpath("//span[contains(text(),'Use as entered')]/..[@aria-disabled='false']");
	private static final By ACCEPT_BTN = By.xpath("//span[contains(text(),'Accept')]");
	private static final By USE_ADDRESS_AS_ENTERED = By.id("QAS_AcceptOriginal");
	private static final By BILLING_AND_SHIPPING_PROFILE_LINK_LOC = By.xpath("//span[text()='Billing & Shipping Profiles']");
	private static final By SHIPPING_PROFILE_ADD_LINK_LOC = By.xpath("//a[@id='btnAddShippingAddress']");
	private static final By ADD_SHIPPING_ADDRESS_PROFILE_NAME_LOC = By.xpath("//input[@id='profileName']");
	private static final By ADD_SHIPPING_ADDRESS_ATTENTION_LOC = By.xpath("//input[@id='attention']");
	private static final By ADD_SHIPPING_ADDRESS_LINE1_LOC = By.xpath("//input[@id='addressLine1']");
	private static final By ADD_SHIPPING_ADDRESS_ZIPCODE_LOC = By.xpath("//input[@id='zip']");
	private static final By STATE_DD_LOC  = By.id("state");
	private static final By STATE_DD_OPTION_LOC  = By.xpath("//select[@id='state']/option[2]");
	private static final By SAVE_ADDRESS_BTN_LOC = By.xpath("//a[@id='btnSaveAddress']");
	private static final By USE_ADDRESS_AS_ENTERED_BTN_LOC = By.xpath("//input[@id='QAS_AcceptOriginal']");
	private static final By BILLING_PROFILE_ADD_LINK_LOC = By.xpath("//a[@id='btnAddBillingAddress']");
	private static final By ADD_NEW_BILLING_ADDRESS_DROP_DOWN_LOC = By.id("existingAddress");
	private static final By ADD_PAYMENT_METHOD_FIRST_NAME_LOC = By.xpath("//input[@id='uxAttentionFirstName']");
	private static final By ADD_PAYMENT_METHOD_LAST_NAME_LOC = By.xpath("//input[@id='uxAttentionLastName']");
	private static final By ADD_PAYMENT_METHOD_NAME_ON_CARD_LOC = By.xpath("//input[@id='nameOnCard']");
	private static final By ADD_PAYMENT_METHOD_CREDIT_CARD_NO_LOC = By.xpath("//input[@id='accountNumber']");
	private static final By ADD_NEW_PAYMENT_EXPIRATION_YEAR_DROP_DOWN_LOC = By.id("expYear");
	private static final By SAVE_PAYMENT_METHOD_BTN_LOC = By.xpath("//a[@id='btnSavePaymentMethod']");
	private static final By USE_AS_ENTERED_BTN_LOC = By.xpath("//*[@id='QAS_AcceptOriginal']");
	private static final By ACCEPT_BTN_LOC = By.xpath("//button/span[text()='Accept']");
	private static final By SHIPPING_PROFILES_LOC  = By.xpath("//div[@id='addresses']/div");
	private static final By ADD_NEW_PAYMENT_METHOD_LINK  = By.xpath("//a[contains(text(),'Add New Payment Method')]");
	private static final By ADD_PAYMENT_METHOD_CVV_LOC = By.xpath("//input[@id='cvvNumber']");
	private static final By ACCOUNT_SEARCH_TXTFIELD = By.id("txtSearch");
	private static final By SKU_SEARCH_FIRST_RESULT = By.xpath("//div[contains(@class,'jsonSuggestResults')][1]//p[1]");

	private static final By ORDERS_IN_ORDER_HISTORY_LOC =  By.xpath("//table[@id='orders']/tbody/tr");
	private static final By FULL_ORDER_HOSTORY_LOC = By.xpath("//span[contains(text(),'Full Order History')]/ancestor::a[1]");
	private static final By ACCOUNT_SEARCH_RESULTS = By.xpath("//div[contains(@class,'resultItem')][1]//p");
	private static final By USER_TYPE_LOC = By.xpath("//td[contains(text(),'Type:')]/following-sibling::td[1]");
	private static final By BROWSE_ACCOUNTS_LINK = By.xpath("//span[contains(text(),'Browse Accounts')]//ancestor::a[1]");
	private static final By USER_TYPE_DROPDOWN_LOC = By.xpath("//select[@id='typeFilter']");
	private static final By USER_STATUS_DROPDOWN_LOC = By.xpath("//select[@id='statusFilter']");
	private static final By MARKET_DROPDOWN_LOC = By.xpath("//select[@id='marketFilter']");
	private static final By ACCOUNT_INFO_TF_LOC = By.xpath("//input[@id='txtAccountNumberOrNameFilter']");
	private static final By FULL_ACCOUNT_RECORD_LINK_LOC = By.xpath("//span[contains(text(),'Full Account Record')]/ancestor::a[1]");
	private static final By ACCOUNT_TYPE_IN_FULL_RECORD_LOC = By.xpath("//td[contains(text(),'Account Type')]//following::select[1]/option[@selected]"); 
	private static final By GO_BTN_LOC = By.xpath("//a[@id='btnSearchAccounts']");
	private static final By FIRST_NAME_IN_PERSONAL_INFO_LOC = By.xpath("//input[@id='txtFirstName']");
	private static final By LAST_NAME_IN_PERSONAL_INFO_LOC = By.xpath("//input[@id='txtLastName']");
	private static final By SAVE_PERSONAL_INFO_BTN_LOC = By.xpath("//a[@id='btnSaveAccount']");
	private static final By ACCEPT_BTN_ON_ADDRESS_VERIFICATION_POPUP_L0C = By.xpath("//span[contains(text(),'Accept')]/ancestor::button[1]");
	private static final By SITE_SUSCRIPTION_LINK_LOC = By.xpath("//span[contains(text(),'Site Subscriptions')]/ancestor::a[1]");
	private static final By SITE_SUBSCRIPTION_HEADER_LOC = By.xpath("//h2[contains(text(),'Site Subscriptions')]");


	public void refreshPage(){
		driver.navigate().refresh();
		driver.pauseExecutionFor(2000);
	}


	public void clickUseAsEnteredbtn(){
		try{if(driver.isElementPresent(USE_AS_ENTERED_BTN)==true){
			driver.waitForElementPresent(USE_AS_ENTERED_BTN);
			driver.click(USE_AS_ENTERED_BTN);
			logger.info("Use as entered button clicked for Account record");
		}else{
			driver.click(ACCEPT_BTN);
			logger.info("Accept button clicked for Account record");
		}
		}catch(Exception e){
			driver.click(USE_ADDRESS_AS_ENTERED);
			logger.info("Usee address as entered button clicked for Account record");
		}
		driver.waitForPageLoad();
	}

	public void clickBillingAndShippingProfileLink(){
		driver.quickWaitForElementPresent(BILLING_AND_SHIPPING_PROFILE_LINK_LOC);
		driver.click(BILLING_AND_SHIPPING_PROFILE_LINK_LOC);
		logger.info("Billing & Shipping Profile link clicked");
		driver.waitForPageLoad(); 
	}

	public void clickShippingProfileAddLink(){
		driver.quickWaitForElementPresent(SHIPPING_PROFILE_ADD_LINK_LOC);
		driver.click(SHIPPING_PROFILE_ADD_LINK_LOC);
		logger.info("Shipping Profile -Add link clicked");
		driver.waitForPageLoad(); 
	}

	public void enterShippingProfileDetails(String profileName,String attention,String addressLine1,String zipCode){
		driver.type(ADD_SHIPPING_ADDRESS_PROFILE_NAME_LOC, profileName);
		driver.type(ADD_SHIPPING_ADDRESS_ATTENTION_LOC, attention);
		driver.type(ADD_SHIPPING_ADDRESS_LINE1_LOC, addressLine1);
		driver.type(ADD_SHIPPING_ADDRESS_ZIPCODE_LOC, zipCode+"\t");
		driver.waitForNSCore4LoadingImageToDisappear();
		//	driver.click(STATE_DD_LOC);
		//	driver.click(STATE_DD_OPTION_LOC);
		driver.pauseExecutionFor(3500);
	}

	public void clickSaveAddressBtn(){
		driver.quickWaitForElementPresent(SAVE_ADDRESS_BTN_LOC);
		driver.click(SAVE_ADDRESS_BTN_LOC);
		driver.pauseExecutionFor(5000);
		clickAcceptButtonOnAddressVerificationPopup();
		driver.waitForPageLoad();
	}

	public boolean isShippingProfilePresent(String shippingProfileName){
		driver.quickWaitForElementPresent(By.xpath(String.format(newlyCeatedShippingProfile, shippingProfileName)));
		return driver.isElementPresent(By.xpath(String.format(newlyCeatedShippingProfile, shippingProfileName)));
	}

	public void deleteAddressNewlyCreatedProfile(String shippingProfileName){
		driver.quickWaitForElementPresent(By.xpath(String.format(deleteAddressnewlyCeatedShippingProfile, shippingProfileName)));
		driver.click(By.xpath(String.format(deleteAddressnewlyCeatedShippingProfile, shippingProfileName)));
		logger.info("delete link clicked of"+shippingProfileName);
		driver.pauseExecutionFor(2000);
		//switch to Alert to delete payment method-
		clickOKBtnOfJavaScriptPopUp();
		driver.pauseExecutionFor(4000);
	}

	public void clickBillingProfileAddLink(){
		driver.quickWaitForElementPresent(BILLING_PROFILE_ADD_LINK_LOC);
		driver.click(BILLING_PROFILE_ADD_LINK_LOC);
		logger.info("Billing Profile -Add link clicked");
		driver.waitForPageLoad(); 
	}

	public void enterBillingProfileDetails(String firstName,String lastName,String nameOnCard,String cardNumber,String cvv,String addressLine1,String zipCode){
		driver.pauseExecutionFor(3000);
		driver.type(By.id("zip"), zipCode+"\t");
		//Add 'Payment-Method'
		driver.type(ADD_PAYMENT_METHOD_FIRST_NAME_LOC, firstName);
		driver.type(ADD_PAYMENT_METHOD_LAST_NAME_LOC, lastName);
		driver.type(ADD_PAYMENT_METHOD_NAME_ON_CARD_LOC, nameOnCard);
		driver.type(ADD_PAYMENT_METHOD_CREDIT_CARD_NO_LOC, cardNumber);
		driver.type(ADD_PAYMENT_METHOD_CVV_LOC, cvv);
		//select 'Expiration' Date(change year)
		Select sel = new Select(driver.findElement(ADD_NEW_PAYMENT_EXPIRATION_YEAR_DROP_DOWN_LOC));
		sel.selectByIndex(4);
		driver.type(By.id("profileName"), firstName+" "+lastName);
		driver.type(By.id("addressLine1"), addressLine1);		
		driver.pauseExecutionFor(2000);
	}

	public void clickSavePaymentMethodBtn(){
		driver.quickWaitForElementPresent(SAVE_PAYMENT_METHOD_BTN_LOC);
		//driver.click(SAVE_PAYMENT_METHOD_BTN_LOC);
		driver.clickByJS(RFWebsiteDriver.driver, driver.findElement(SAVE_PAYMENT_METHOD_BTN_LOC));
		logger.info("Save Payment Method Btn clicked");
		driver.pauseExecutionFor(3000);
		clickAcceptButtonOnAddressVerificationPopup();
		driver.waitForPageLoad();
	}

	public int getTotalNoOfShippingProfiles(){
		driver.waitForElementPresent(SHIPPING_PROFILES_LOC);
		int noOfShippingProfile = driver.findElements(SHIPPING_PROFILES_LOC).size();
		logger.info("Total no of shipping profiles is: "+noOfShippingProfile);
		return noOfShippingProfile;
	}

	public void addPaymentMethod(String firstName,String lastName,String nameOnCard,String cardNumber){
		driver.waitForElementPresent(ADD_NEW_PAYMENT_METHOD_LINK);
		driver.click(ADD_NEW_PAYMENT_METHOD_LINK);
		logger.info("Add new payment method link clicked");
		driver.pauseExecutionFor(3000);
		//enter billing info
		//select 'Main-Billing' as billing address-
		Select select = new Select(driver.findElement(ADD_NEW_BILLING_ADDRESS_DROP_DOWN_LOC));
		select.selectByIndex(1);
		driver.pauseExecutionFor(4500);
		//Add 'Payment-Method'
		driver.type(ADD_PAYMENT_METHOD_FIRST_NAME_LOC, firstName);
		logger.info("First name entered for billing profile is: "+firstName);
		driver.type(ADD_PAYMENT_METHOD_LAST_NAME_LOC, lastName);
		logger.info("last name entered for billing profile is: "+lastName);
		driver.type(ADD_PAYMENT_METHOD_NAME_ON_CARD_LOC, nameOnCard);
		logger.info("name on card entered for billing profile is: "+nameOnCard);
		driver.type(ADD_PAYMENT_METHOD_CREDIT_CARD_NO_LOC, cardNumber);
		logger.info("card number entered for billing profile is: "+cardNumber);
		//select 'Expiration' Date(change year)
		Select sel = new Select(driver.findElement(ADD_NEW_PAYMENT_EXPIRATION_YEAR_DROP_DOWN_LOC));
		sel.selectByIndex(7);
		logger.info("Credit card expire year selected");
	}

	public void editShippingProfile(String profileName){
		driver.click(By.xpath(String.format(editLinkForShippingProfileLoc,profileName)));
		logger.info("Click Edit Link for Shipping Profile : "+profileName);
	}


	public boolean isBillingProfilePresent(String billingProfileName){
		driver.pauseExecutionFor(2000);
		driver.quickWaitForElementPresent(By.xpath(String.format(billingProfileInPaymentMethodsLoc, billingProfileName)));
		return driver.isElementPresent(By.xpath(String.format(billingProfileInPaymentMethodsLoc, billingProfileName)));
	} 


	public void editBillingProfile(String profileName){
		driver.click(By.xpath(String.format(billingProfileInPaymentMethodsLoc,profileName)));
		logger.info("Click Edit Link for Billing Profile : "+profileName);
	}



	public void deleteNewlyCreatedBillingProfile(String profileName){
		driver.click(By.xpath(String.format(deletePaymentMethodLinkLoc,profileName)));
		logger.info("Delete button clicked for Billing Profile : " + profileName);
		driver.pauseExecutionFor(2000);
		//switch to Alert to delete payment method-
		try{
			Alert alt =driver.switchTo().alert();
			alt.accept();
			logger.info("Alert handled");
		}catch(Exception e){

		}
		driver.pauseExecutionFor(2000);
		driver.waitForPageLoad();
	}

	public void enterAccountInfoInAccountSearchField(String accountinfo){
		driver.quickWaitForElementPresent(By.id("txtSearch"));
		driver.type(ACCOUNT_SEARCH_TXTFIELD, accountinfo);
		driver.pauseExecutionFor(2000);
		logger.info("account info entered in search field is "+accountinfo);
	}


	public void clickFirstSKUSearchResultOfAutoSuggestion(){
		driver.waitForElementPresent(SKU_SEARCH_FIRST_RESULT);
		driver.click(SKU_SEARCH_FIRST_RESULT);	
		logger.info("sku first result clicked");
	}



	public boolean isOrdersPresentInOrderHistory(){

		return driver.findElements(ORDERS_IN_ORDER_HISTORY_LOC).size() > 0;
	}

	public void clickFullOrderHistory(){
		driver.click(FULL_ORDER_HOSTORY_LOC);
		logger.info("Clicked on Full Order History from Left Navigation");
	}

	public boolean isFirstAndLastNamePresentinSearchResults(String firstName,String lastName){
		driver.waitForElementPresent(ACCOUNT_SEARCH_RESULTS);
		return driver.findElement(By.xpath("//div[contains(@class,'resultItem')][1]//p")).getText().contains(firstName)
				&& driver.findElement(By.xpath("//div[contains(@class,'resultItem')][1]//p")).getText().contains(lastName);
	}


	public boolean isUserInfoPresentinSearchResults(String detail){
		driver.waitForElementPresent(ACCOUNT_SEARCH_RESULTS);
		return driver.getText(ACCOUNT_SEARCH_RESULTS).contains(detail);
	}

	public boolean verifyUserInfoOnAccountOverviewPage(String userInfo){
		return driver.isElementPresent(By.xpath(String.format(userNameInLeftNavigationLoc,userInfo)));
	}

	public boolean verifyAccountNumberOnAccountOverviewPage(String accountNumber){
		return driver.isElementPresent(By.xpath(String.format(accountNumberInLeftNavigationLoc,accountNumber)));
	}

	public boolean verifyUserTpyeOnAccountOverviewPage(String expectedUserType){
		return driver.getText(USER_TYPE_LOC).trim().contains(expectedUserType);
	}

	public DSVNSCore4OrderDetailsPage clickOrderNumberLinkFromOrderHistory(){
		driver.waitForElementPresent(By.xpath(String.format(orderHistoryColumnWithLinkLoc,"1")));
		driver.click(By.xpath(String.format(orderHistoryColumnWithLinkLoc,"1")));
		logger.info("Clicked on First Product Link from Order History");
		return new DSVNSCore4OrderDetailsPage(driver);
	}


	public void clickBrowseAccounts(){
		driver.click(BROWSE_ACCOUNTS_LINK);
		logger.info("Clicked Browse Accounts Link");
		driver.waitForPageLoad();
		waitForLoadingImageToDisappear();
	}


	public void selectUserTypeFromDropdown(String userType){
		Select select = new Select(driver.findElement(USER_TYPE_DROPDOWN_LOC));
		select.selectByVisibleText(userType);
		logger.info("Selected User Type as "+userType);
	}


	public void selectUserStatusFromDrodown(String userStatus){
		Select select = new Select(driver.findElement(USER_STATUS_DROPDOWN_LOC));
		select.selectByVisibleText(userStatus);
		logger.info("Selected User Status as "+userStatus);
	}


	public void selectUserMarketFromDrodown(String market){
		Select select = new Select(driver.findElement(MARKET_DROPDOWN_LOC));
		select.selectByVisibleText(market);
		logger.info("Selected User Status as "+market);
	}

	public void enterAccountInfoAndSearch(String accountInfo){
		driver.type(ACCOUNT_INFO_TF_LOC, accountInfo);
		//driver.pauseExecutionFor(2000);
		driver.clickByJS(RFWebsiteDriver.driver, driver.findElement(GO_BTN_LOC));
		//driver.click(GO_BTN_LOC);
		logger.info("Enter Account Info : "+accountInfo+" and Clicked on Go Button");
		waitForLoadingImageToDisappear();
	}

	public boolean verifyPresenceOfAccountNumberInSearchResults(String accountNumber){
		driver.waitForElementPresent(By.xpath(String.format(accountNumberInSearchedResults,accountNumber)),30);
		return driver.isElementPresent(By.xpath(String.format(accountNumberInSearchedResults,accountNumber)));
	}

	public boolean isFirstNamePresentAsExpectedInSearchResults(String accountNumber, String firstName){
		return driver.findElement(By.xpath(String.format(firstNameForSepecificAccountLoc,accountNumber))).getText().trim().contains(firstName);
	}


	public boolean isLastNamePresentAsExpectedInSearchResults(String accountNumber, String lastName){
		return driver.findElement(By.xpath(String.format(lastNameForSepecificAccountLoc,accountNumber))).getText().trim().contains(lastName);
	}

	public void clickOnAccountNumberInSearchResults(String accountNumber){
		driver.waitForElementPresent(By.xpath(String.format(accountNumberInSearchedResults,accountNumber)), 30);
		driver.click(By.xpath(String.format(accountNumberInSearchedResults,accountNumber)));
		waitForLoadingImageToDisappear();
	}

	public void waitForLoadingImageToDisappear(){
		driver.waitForElementToBeInVisible(By.xpath("//img[contains(@alt,'loading')]"),50);
	}

	public boolean verifyAccountTypeInFullRecord(String userType){
		return driver.getText(ACCOUNT_TYPE_IN_FULL_RECORD_LOC).trim().contains(userType);
	}

	public void clickFullAccountRecordFromLeftNavigation(){
		driver.click(FULL_ACCOUNT_RECORD_LINK_LOC);
		logger.info("Clciked on Full Account Record from Left Navigation");
	}


	public boolean verifyFirstNameFromPersonalInfo(String firstName){
		return driver.getAttribute(FIRST_NAME_IN_PERSONAL_INFO_LOC,"value").trim().contains(firstName);
	}


	public boolean verifyLastNameFromPersonalInfo(String lastName){
		return driver.getAttribute(LAST_NAME_IN_PERSONAL_INFO_LOC,"value").trim().contains(lastName);
	}

	public void editLastNameFromPersonalInfo(String updatedLastName){
		driver.type(LAST_NAME_IN_PERSONAL_INFO_LOC, updatedLastName);
	}


	public void clickSavePersonalInfoBtn(){
		driver.pauseExecutionFor(2000);
		driver.click(SAVE_PERSONAL_INFO_BTN_LOC);
		logger.info("Clciked on Save Personal Info Button ");
	}


	public void clickAcceptButtonOnAddressVerificationPopup(){
		driver.waitForElementToBeVisible(ACCEPT_BTN_ON_ADDRESS_VERIFICATION_POPUP_L0C,10);
		driver.click(ACCEPT_BTN_ON_ADDRESS_VERIFICATION_POPUP_L0C);
	}


	public void clickSiteSubscriptionsFromLeftNavigation(){
		driver.click(SITE_SUSCRIPTION_LINK_LOC);
		logger.info("Clicked on Site Subscription from Left Navigation");
	}


	public boolean verifyPresenceOfSiteSubscriptionsHeader(){
		return driver.isElementPresent(SITE_SUBSCRIPTION_HEADER_LOC);
	}


	public boolean verifyPrefixPresentForSiteSubscription(String siteType){
		if(siteType.contains("Com")){
			return driver.isElementPresent(By.xpath(String.format(prefixInWebsiteUrlsLoc,"1")));
		}
		else{
			return driver.isElementPresent(By.xpath(String.format(prefixInWebsiteUrlsLoc,"2")));
		}
	}


	public String getURLForSpecificSite(String siteType){
		String subDomain = "";
		String domain = "";
		if(siteType.contains("Com")){
			subDomain = driver.getAttribute(By.xpath(String.format(prefixInWebsiteUrlsLoc,"1")),"value").trim();
			domain = driver.getText(By.xpath(String.format(domainInWebsiteUrlsLoc,"1"))).trim();
			return "https://"+subDomain+"."+domain;
		}
		else{
			subDomain = driver.getAttribute(By.xpath(String.format(prefixInWebsiteUrlsLoc,"2")),"value").trim();
			domain = driver.getText(By.xpath(String.format(domainInWebsiteUrlsLoc,"2"))).trim();
			return "https://"+subDomain+"."+domain;
		}
	}


	//	public void clickSpecificAccountFromAutoSuggestion(String name, String accountNumber){
	//		driver.waitForElementPresent(SKU_SEARCH_FIRST_RESULT);
	//		driver.click(By.xpath(String.format(accountNumberInAutoSuggestionLoc,name,accountNumber)));
	//	}

	public void clickSpecificAccountFromAutoSuggestion(String firstName,String lastName, String accountNumber){
		driver.waitForElementPresent(SKU_SEARCH_FIRST_RESULT);
		driver.click(By.xpath(String.format(accountNumberInAutoSuggestionLoc,firstName,lastName,accountNumber)));
	}

	public String getCurrentUrl() {
		String currentURL = driver.getCurrentUrl();
		logger.info("current URL is "+currentURL);
		return currentURL;
	}

	public void navigatetoUrl(String url){
		driver.get(url);
		logger.info("Navigated to "+url);
	}

	public boolean isLoginButtonPresent(){
		return driver.isElementPresent(By.xpath("//a[@id='loginButton']"));
	}

	public void enterAccountInfoInAccountSearchField(String accountNumber, String emailId){
		driver.quickWaitForElementPresent(ACCOUNT_NUB_TF_LOC);
		driver.type(ACCOUNT_NUB_TF_LOC, accountNumber);
		driver.pauseExecutionFor(2000);
		driver.type(EMAIL_ID_TF_LOC, emailId);
		logger.info("Account Number " + accountNumber + " and Email Id : "+ emailId +" entered in search field");
		driver.click(SEARCH_ACCOUNT_BTN_LOC);
		logger.info("Search Accounts Btn Clicked");
	}

	public boolean isOrderPresentOnAccountOverview(){  
		return driver.findElements(ORDERS_IN_ACCOUNT_OVERVIEW_LOC).size()>0;
	}

	public DSVNSCore4OrderDetailsPage clickOrderNumberLinkFromAccountOverview(){
		driver.waitForElementPresent(By.xpath(String.format(orderLinkFromAccountOverviewOrdersLoc,"1")));
		driver.click(By.xpath(String.format(orderLinkFromAccountOverviewOrdersLoc,"1")));
		logger.info("Clicked on First Product Link from Account Overview");
		return new DSVNSCore4OrderDetailsPage(driver);
	}

}