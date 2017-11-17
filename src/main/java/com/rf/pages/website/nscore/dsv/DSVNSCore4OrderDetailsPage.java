package com.rf.pages.website.nscore.dsv;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.rf.core.driver.website.RFWebsiteDriver;

public class DSVNSCore4OrderDetailsPage extends DSVNSCore4RFWebsiteBasePage{

	private static final Logger logger = LogManager
			.getLogger(DSVNSCore4OrderDetailsPage.class.getName());

	public DSVNSCore4OrderDetailsPage(RFWebsiteDriver driver) {
		super(driver);
	}
	
	
    private static final By PAYMENT_SECTION_PAYMENT_DETILS_LOC =  By.xpath("//th[contains(text(),'Payment')]/following::tbody[1]//a[@title='View payment details']");
    private static final By PAYMENT_SECTION_TOTAL_AMOUNT_LOC = By.xpath("//th[contains(text(),'Payment')]/following::tbody[1]//td[2]");
    private static final By CUSTOMER_INFO_IN_CUSTOMER_SECTION_LOC = By.xpath("//div[@class='CustomerLabel']//a");
    private static final By PAYMENT_APPLIED_LOC = By.xpath("//td[contains(text(),'Payments Applied')]/following::b[2]");
	private static final By BALANCE_DUE_LOC = By.xpath("//td[contains(text(),'Payments Applied')]/following::b[2]/span");
	private static final By TRACKING_NUMBER_IN_SHIPMENT_SECTION_LOC = By.xpath("//th[contains(text(),'Tracking')]/following::tbody[1]//a[@title='Tracking Number']");
	private static final By PRODUCT_SKU_IN_PRODUCTS_SECTION_LOC = By.xpath("//th[contains(text(),'SKU')]//following::tbody[1]//td[1]");
	private static final By ADDRESS_IN_SHIPMENT_SECTION_LOC = By.xpath("//th[contains(text(),'Address')]/following::tbody[1]/tr/td[2]");
	
	
	private String productsSectionGridTotalLoc = "//td[contains(@class,'FLabel') and contains(text(),'Products')]/following::table[1]//tr[@class='GridTotalBar']//*[contains(text(),'%s')]";
	
	public boolean verifyPresenceOfGridTotalInProductsSection(String totalType){
		String total = driver.findElement(By.xpath(String.format(productsSectionGridTotalLoc,totalType))).getText().replace("("+totalType+")","").trim();
		logger.info(totalType + " is found as " + total);
		return StringUtils.isNotEmpty(total);
	}
	
	
	public boolean verifyPresenceOfPaymentDetailsInPaymentSection(){
		boolean isPaymentDetailPresent = driver.isElementPresent(PAYMENT_SECTION_PAYMENT_DETILS_LOC);
		if(isPaymentDetailPresent){
			String paymentDetails = driver.getText(PAYMENT_SECTION_PAYMENT_DETILS_LOC).trim();
			return paymentDetails.contains("Exp");
		}
		return false;
	}
	
	
	public boolean verifyPresenceOfTotalAmountInPaymentSection(){
		boolean isTotalAmountPresent = driver.isElementPresent(PAYMENT_SECTION_TOTAL_AMOUNT_LOC);
		if(isTotalAmountPresent){
			String paymentDetails = driver.getText(PAYMENT_SECTION_TOTAL_AMOUNT_LOC).trim();
			return paymentDetails.contains("$");
		}
		return false;
	}
	
	public boolean verifyTrackingNumberInShipmentSection(){
		boolean isTrackingNumberPresent = driver.isElementPresent(TRACKING_NUMBER_IN_SHIPMENT_SECTION_LOC);
		if(isTrackingNumberPresent){
			String trackingNumber = driver.getText(TRACKING_NUMBER_IN_SHIPMENT_SECTION_LOC).trim();
			return StringUtils.isNotEmpty(trackingNumber);
		}
		return false;
	}
	
	public boolean verifyPresenceOfPaymentAppliedInPaymentSection(){
		boolean isPaymentAppliedPresent = driver.isElementPresent(PAYMENT_APPLIED_LOC);
		if(isPaymentAppliedPresent){
			String paymentApplied = driver.getText(PAYMENT_APPLIED_LOC).trim();
			return StringUtils.isNotEmpty(paymentApplied) && paymentApplied.contains("$");
		}
		return false;
	}
	
	public boolean verifyPresenceOfBalanceDueInPaymentSection(){
		boolean isBalanceDuePresent = driver.isElementPresent(BALANCE_DUE_LOC);
		if(isBalanceDuePresent){
			String balanceDue = driver.getText(BALANCE_DUE_LOC).trim();
			return StringUtils.isNotEmpty(balanceDue) && balanceDue.contains("$");
		}
		return false;
	}
	
	public boolean verifyCustomerInfoOnOrderDetailsPage(String info){
		return driver.getText(CUSTOMER_INFO_IN_CUSTOMER_SECTION_LOC).trim().contains(info);
	}
	
	public boolean verifyPresenceOfSKUInProductsSection(){
		boolean isSKUPresent = driver.isElementPresent(PRODUCT_SKU_IN_PRODUCTS_SECTION_LOC);
		if(isSKUPresent){
			String sku = driver.getText(PRODUCT_SKU_IN_PRODUCTS_SECTION_LOC).trim();
			return StringUtils.isNotEmpty(sku);
		}
		return false;
	}
	
	public String getCurrentUrl() {
		String currentURL = driver.getCurrentUrl();
		logger.info("current URL is "+currentURL);
		return currentURL;
	}
	
	
	public boolean verifyAddressInShipmentSection(){
		boolean isAddressPresent = driver.isElementPresent(ADDRESS_IN_SHIPMENT_SECTION_LOC);
		if(isAddressPresent){
			String address = driver.getText(ADDRESS_IN_SHIPMENT_SECTION_LOC).trim();
			return StringUtils.isNotEmpty(address);
		}
		return false;
	}
	
	
	
	
	
	

	
	
	
	
	
	
	
	
			
    
    
    
}