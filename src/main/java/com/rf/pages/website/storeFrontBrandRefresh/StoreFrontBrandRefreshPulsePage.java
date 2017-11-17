package com.rf.pages.website.storeFrontBrandRefresh;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.rf.core.driver.website.RFWebsiteDriver;

public class StoreFrontBrandRefreshPulsePage extends StoreFrontBrandRefreshWebsiteBasePage{

	private static final Logger logger = LogManager
			.getLogger(StoreFrontBrandRefreshPulsePage.class.getName());

	public StoreFrontBrandRefreshPulsePage(RFWebsiteDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	private static final By PULSE_DISMISS_BUTTON_LOC=By.xpath("//span[contains(text(),'Dismiss')]");
	private static final By EVENTS_LINK_LOC=By.xpath("//div[@id='SiteNav']//span[contains(text(),'Events')]");
	private static final By ECOMMS_LINK_LOC=By.xpath("//div[@id='SiteNav']//span[contains(text(),'EComms')]");
	private static final By MY_ACCOUNT_LINK_LOC=By.xpath("//div[@id='SiteNav']//span[contains(text(),'My Account')]");
	private static final By ORDERS_LINK_LOC=By.xpath("//div[@id='SiteNav']//span[contains(text(),'Orders')]");
	private static final By CONTACTS_LINK_LOC=By.xpath("//div[@id='SiteNav']//span[contains(text(),'Contacts')]");
	private static final By PERFORMANCE_LINK_LOC=By.xpath("//div[@id='SiteNav']//span[contains(text(),'Performance')]");
	private static final By DASHBOARD_LINK_LOC=By.xpath("//div[@id='SiteNav']//span[contains(text(),'Dashboard')]");
	private static final By BIZ_DEV_LIBRARY_LOC=By.xpath("//div[@id='SiteNav']//span[contains(text(),'Biz Dev Library')]");
	private static final By WELCOME_TEXT_ON_PULSE_HOMEPAGE_LOC=By.xpath("//p[contains(text(),' Welcome,')]");
	private static final By EMAIL_ADDRESS_LOC=By.id("UserName");
	private static final By PASSWORD_LOC=By.id("Password");
	private static final By LIBRARY_TEXT_ON_BIZ_DEV_LIBRARY_PAGE_LOC=By.xpath("//a[contains(text(),'LIBRARY')]");
	private static final By WELCOME_TO_NEW_LIBRARY_TEXT_LOC=By.xpath("//h2[contains(text(),'WELCOME TO YOUR NEW LIBRARY')]");
	private static final By SIGN_IN_BUTTON_LOC=By.xpath("//span[contains(text(),'Sign In')]");
	private static final By EDIT_CRP_LOC=By.xpath("//a[contains(@id,'EditMyCRP')]");
	private static final By ALL_PRODUCT_ON_CRP_OVERVIEW_PAGE_LOC=By.xpath("//td[@class='CartThumb']/following-sibling::td[1]");
	private static final By EDIT_REPLENISHMENT_ORDER_BUTTON_LOC=By.xpath("//a[contains(text(),'Edit Replenishment Order')]");
	private static final By UPDATE_ORDER_LOC=By.xpath("//a[contains(text(),'Update Order')]");

	private static String addToBagButtonInAutoshipForProductNameLoc = "//h3[contains(text(),'%s')]/following::a[contains(text(),'Add to Bag')][1]";
	private static String shadeDDForAutoshipLoc = "//h3[contains(text(),'%s')]/following::select[1]";
	private static String headerLinkLoc = "//div[@id='ContentWrapper']//a[text()='%s']";
	private static String REMOVE_PRODUCT_LINK_LOC="//div[@id='MyAutoshipItems']//li[%s]/a";

	public boolean isWelcomeUserTextDisplayedOnPulseHomePage() {
		return driver.isElementVisible(WELCOME_TEXT_ON_PULSE_HOMEPAGE_LOC);
	}

	public void loginToPulse(String email, String pwd) {
		driver.pauseExecutionFor(2000);
		driver.waitForElementPresent(EMAIL_ADDRESS_LOC);
		driver.type(EMAIL_ADDRESS_LOC, email);
		logger.info("Email Address entered is: "+email);
		driver.type(PASSWORD_LOC, pwd);
		logger.info("Password entered is: "+pwd);
		driver.click(SIGN_IN_BUTTON_LOC);
		logger.info("clicked on sign in button");
	}

	public boolean isUserRedirectedToBizDevLibraryPage() {
		return driver.isElementVisible(WELCOME_TO_NEW_LIBRARY_TEXT_LOC) && driver.isElementVisible(LIBRARY_TEXT_ON_BIZ_DEV_LIBRARY_PAGE_LOC);
	}

	public void dismissPulsePopup() {
		try {
			driver.click(PULSE_DISMISS_BUTTON_LOC);
			logger.info("clicked on dismiss button on pulse homepage");
		}catch(Exception E) {
			logger.info("Pulse Dismiss popup not present");
		}

	}

	public void clickEventsOnPulsePage() {
		//driver.clickByJS(RFWebsiteDriver.driver, EVENTS_LINK_LOC);
		driver.click(EVENTS_LINK_LOC);
		logger.info("clicked on Events Tab on pulse homepage");
		driver.waitForPageLoad();
		driver.pauseExecutionFor(3000);
	}

	public void clickECOMMSLinkOnPulsePage() {
		driver.click(ECOMMS_LINK_LOC);
		//driver.clickByJS(RFWebsiteDriver.driver, ECOMMS_LINK_LOC);
		logger.info("clicked on ECOMMS Tab on pulse homepage");
		driver.waitForPageLoad();
		driver.pauseExecutionFor(3000);
	}

	public void clickMyAccountLinkOnPulsePage() {
		driver.click(MY_ACCOUNT_LINK_LOC);
		//driver.clickByJS(RFWebsiteDriver.driver, MY_ACCOUNT_LINK_LOC);
		logger.info("clicked on My Account Tab on pulse homepage");
		driver.waitForPageLoad();
		driver.pauseExecutionFor(3000);
	}

	public void clickOrdersLinkOnPulsePage() {
		driver.click(ORDERS_LINK_LOC);
		//driver.clickByJS(RFWebsiteDriver.driver, ORDERS_LINK_LOC);
		logger.info("clicked on Orders Tab on pulse homepage");
		driver.waitForPageLoad();
		driver.pauseExecutionFor(3000);
	}

	public void clickContactsLinkOnPulsePage() {
		driver.click(CONTACTS_LINK_LOC);
		//driver.clickByJS(RFWebsiteDriver.driver, CONTACTS_LINK_LOC);
		logger.info("clicked on Contacts Tab on pulse homepage");
		driver.waitForPageLoad();
		driver.pauseExecutionFor(3000);
	}

	public void clickPerformanceLinkOnPulsePage() {
		driver.click(PERFORMANCE_LINK_LOC);
		//driver.clickByJS(RFWebsiteDriver.driver, PERFORMANCE_LINK_LOC);
		logger.info("clicked on Performance Tab on pulse homepage");
		driver.waitForPageLoad();
		driver.pauseExecutionFor(3000);
	}

	public void clickDashboardLinkOnPulsePage() {
		driver.click(DASHBOARD_LINK_LOC);
		//driver.clickByJS(RFWebsiteDriver.driver, DASHBOARD_LINK_LOC);
		logger.info("clicked on DASHBOARD Tab on pulse homepage");
		driver.waitForPageLoad();
		driver.pauseExecutionFor(3000);
	}

	public void clickBIZDEVLibraryLinkOnPulsePage() {
		String parentWinHandle=driver.getWindowHandle();
		driver.click(BIZ_DEV_LIBRARY_LOC);
		//driver.clickByJS(RFWebsiteDriver.driver, BIZ_DEV_LIBRARY_LOC);
		logger.info("clicked on Biz Dev library Tab on pulse homepage");
		driver.waitForPageLoad();
		driver.pauseExecutionFor(3000);
		driver.switchToChildWindow(parentWinHandle);
	}

	public void clickEditCRP(){
		driver.click(EDIT_CRP_LOC, "Edit CRP Link");
		driver.switchToSecondWindow();
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

	public void clickEditReplenishmentOrder(){
		driver.waitForPageLoad();
		driver.click(EDIT_REPLENISHMENT_ORDER_BUTTON_LOC,"Edit Replenishment Order Button");
	}

	public void removeProductFromAutoshipCart(int productPosition){
		driver.click(By.xpath(String.format(REMOVE_PRODUCT_LINK_LOC, productPosition)),"Remove link of product");
	}

	public void clickUpdateOrder(){
		driver.pauseExecutionFor(5000);
		driver.click(UPDATE_ORDER_LOC,"Update Order Button");
	}

	public void clickLinksInHeader(String linkName){
		driver.click(By.xpath(String.format(headerLinkLoc, linkName)),linkName+"link");
		driver.waitForPageLoad();
	}

	public void selectVariantOfAProduct(String productName, String shade){
		Select selectVarient=new Select(driver.findElement(By.xpath(String.format(shadeDDForAutoshipLoc, productName))));
		logger.info("Variant Product is available. Selecting value from dropdown.");
		selectVarient.selectByVisibleText(shade);
	}

	public void addProductToAutoshipCart(String productName){
		driver.waitForElementPresent(By.xpath(String.format(addToBagButtonInAutoshipForProductNameLoc, productName)));
		driver.click(By.xpath(String.format(addToBagButtonInAutoshipForProductNameLoc, productName)),productName+"product");
		driver.waitForPageLoad();
	}
}
