package com.rf.pages.website.brandRefresh.dsv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.rf.core.driver.website.RFWebsiteDriver;
import com.rf.pages.RFBasePage;
import com.rf.pages.website.dsv.DSVRFWebsiteBasePage;

public class DSVStorefrontBrandRefreshWebsiteBasePage extends RFBasePage {
	private static final Logger logger = LogManager
			.getLogger(DSVStorefrontBrandRefreshWebsiteBasePage.class.getName());
	protected RFWebsiteDriver driver;

	protected static String addToBagProduct="//div[@id='ContentWrapper']/descendant::a[text()='Add to Bag'][%s]";
	private static String addToBagButtonInAutoshipForProductNameLoc = "//h3[contains(text(),'%s')]/following::a[contains(text(),'Add to Bag')][1]";
	private static String shadeDDForAutoshipLoc = "//h3[contains(text(),'%s')]/following::select[1]";
	private static String headerLinkLoc = "//div[@id='ContentWrapper']//a[text()='%s']";
	private static String REMOVE_PRODUCT_LINK_LOC="//div[@id='MyAutoshipItems']//li[%s]/a";

	public DSVStorefrontBrandRefreshWebsiteBasePage(RFWebsiteDriver driver){		
		super(driver);
		this.driver = driver;
	}

	public String getCurrentURL(){
		driver.waitForPageLoad();
		return driver.getCurrentUrl();
	}

	public DSVStorefrontBrandRefreshWebsiteBasePage addProductToBag(int productNumber) {
		driver.click(By.xpath(String.format(addToBagProduct,productNumber)));
		logger.info("Product is added into cart");
		return this;
	}


	public DSVStorefrontBrandRefreshWebsiteBasePage refreshPage(){
		driver.navigate().refresh();
		driver.waitForPageLoad();
		return this;
	}

	public DSVStorefrontBrandRefreshWebsiteBasePage switchToWindow(String windowHandle){
		driver.close();
		driver.switchTo().window(windowHandle);
		return this;
	}

	public DSVStorefrontBrandRefreshWebsiteBasePage acceptQASPopup(){
		driver.click(By.xpath("//*[text()='Accept']"));
		logger.info("clicked on accept");
		return this;
	}

	public boolean isCurrentUrlContains(String url){
		driver.waitForExpectedURLPresent(url);
		return driver.getCurrentUrl().contains(url);
	}

	public DSVStorefrontBrandRefreshWebsiteBasePage clickLinksInHeader(String linkName){
		driver.click(By.xpath(String.format(headerLinkLoc, linkName)));
		logger.info("Clicked on "+linkName+" link from the header links");
		driver.waitForPageLoad();
		return this;
	}

	public DSVStorefrontBrandRefreshWebsiteBasePage selectVariantAndAddProductToAutoshipCart(String productName, String shade){
		logger.info("Waiting for Variant Product");
		driver.waitForElementPresent(By.xpath(String.format(addToBagButtonInAutoshipForProductNameLoc, productName)));
		Select selectVarient=new Select(driver.findElement(By.xpath(String.format(shadeDDForAutoshipLoc, productName))));
		logger.info("Variant Product is available. Selecting value from dropdown.");
		selectVarient.selectByVisibleText(shade);
		driver.click(By.xpath(String.format(addToBagButtonInAutoshipForProductNameLoc, productName)));
		logger.info("Variant Product"+productName+ "added to Ad-hoc cart");
		driver.waitForPageLoad();
		return this;
	}

	public DSVStorefrontBrandRefreshWebsiteBasePage removeProductFromAutoshipCart(int productPosition){
		driver.click(By.xpath(String.format(REMOVE_PRODUCT_LINK_LOC, productPosition)));
		return this;
	}
}
