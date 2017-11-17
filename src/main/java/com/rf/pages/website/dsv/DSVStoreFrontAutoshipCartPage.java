package com.rf.pages.website.dsv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import com.rf.core.driver.website.RFWebsiteDriver;

public class DSVStoreFrontAutoshipCartPage extends DSVRFWebsiteBasePage{
	private static final Logger logger = LogManager
			.getLogger(DSVStoreFrontAutoshipCartPage.class.getName());

	private static final By CONTINUE_SHOPPING_LINK = By.xpath("//*[@value='ADD MORE ITEMS']");
	private static String RetailPrice =  "//p[@id='cart-retail-price']/span[contains(@class,'old-price')][contains(text(),'%s')]"; 	
	private static String allItemsNameInCart = "//div[@class='shopping-item row']//h3";

	public DSVStoreFrontAutoshipCartPage(RFWebsiteDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public DSVStoreFrontQuickShopPage clickTopContinueShoppingLink(){
		driver.quickWaitForElementPresent(CONTINUE_SHOPPING_LINK);
		driver.click(CONTINUE_SHOPPING_LINK);
		return new DSVStoreFrontQuickShopPage(driver);
	}

	public boolean isProductPresentOnCart(String retailPrice){
		return driver.isElementPresent((By.xpath(String.format(RetailPrice, retailPrice))));		
	}

	public void addQuantityOfProduct(String retailPrice,String quantity){
		driver.waitForElementPresent(By.xpath(String.format(RetailPrice, retailPrice)+"/preceding::div[1]//input[@name='quantity']"));
		driver.type(By.xpath(String.format(RetailPrice, retailPrice)+"/preceding::div[1]//input[@name='quantity']"), quantity);		
	}

	public void clickUpdateQuantityBtnOfProduct(String retailPrice){
		driver.click(By.xpath(String.format(RetailPrice, retailPrice)+"/preceding::div[1]//a[text()='Update']"));
		driver.pauseExecutionFor(2000);
		driver.waitForPageLoad();
	}

	public String getQuantityOfProduct(String retailPrice){
		return driver.findElement(By.xpath(String.format(RetailPrice, retailPrice)+"/preceding::div[1]//input[@name='quantity']")).getAttribute("value");
	}

	public void clickRemoveProduct(String retailPrice){
		driver.click(By.xpath(String.format(RetailPrice, retailPrice)+"/following::a[1][text()='Remove']"));
		driver.pauseExecutionFor(2000);
		driver.waitForPageLoad();
	}

	public boolean isThresholdMessageAppeared(){
		driver.quickWaitForElementPresent(By.xpath("//div[@id='globalMessages']//span[contains(text(),'greater than the threshold')]"));
		try{
			driver.findElement(By.xpath("//div[@id='globalMessages']//span[contains(text(),'greater than the threshold')]"));
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public boolean isVariantProductInAutoshipCart(){
		driver.waitForLoadingImageToDisappear();
		return driver.isElementPresent(By.xpath("//*[contains(text(),'ENLPK04-01Shade: Light')]"));
	}

	public boolean clickRemoveButtonForVarientProductAndVerify(){
		logger.info("Trying to Remove Varient Product from cart");
		driver.clickByJS(RFWebsiteDriver.driver, By.xpath("//*[contains(text(),'ENLPK04-01Shade: Light')]/ancestor::div[2]//a"));
		driver.waitForPageLoad();
		return driver.isElementPresent(By.xpath("//*[contains(text(),'ENLPK04-01Shade: Light')]"));
	}

	public boolean isAlertPresentForVariantProductsAutoship(){
		driver.waitForLoadingImageToDisappear();
		driver.waitForElementPresent(By.xpath("//a[contains(text(),'SPF 20 with Brush')]/ancestor::div[1]//select[@id='displayCodes']"));
		driver.clickByJS(RFWebsiteDriver.driver, By.xpath("//a[contains(text(),'SPF 20 with Brush')]/ancestor::div[1]//*[contains(text(),'ADD TO CRP') or (@value='ADD to PC Perks')]"));
		try{
			driver.switchTo().alert().accept();
			logger.info("Variant Product alert was accepted.");
			return true;
		}catch(Exception e){
			logger.info("Alert for Variant Product was not displayed");
			return false;
		}
	}
}
