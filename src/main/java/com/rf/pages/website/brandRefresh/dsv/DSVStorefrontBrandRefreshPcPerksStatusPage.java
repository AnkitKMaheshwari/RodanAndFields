package com.rf.pages.website.brandRefresh.dsv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import com.rf.core.driver.website.RFWebsiteDriver;

public class DSVStorefrontBrandRefreshPcPerksStatusPage extends DSVStorefrontBrandRefreshWebsiteBasePage {

	private static final Logger logger = LogManager
			.getLogger(DSVStorefrontBrandRefreshHomePage.class.getName());

	private static final By PC_PERKS_STATUS_ACTIVE_TXT_LOC = By.xpath("//div[contains(text(),'Active')]");
	private static final By NEXTBILL_SHIP_DATE_TXT_LOC = By.xpath("//div[contains(text(),'Next Bill & Ship Date')]");

	public DSVStorefrontBrandRefreshPcPerksStatusPage(RFWebsiteDriver driver) {
		super(driver);

	}
	public boolean isVerifyPcPerksStatusActive(){
		return driver.isElementPresent(PC_PERKS_STATUS_ACTIVE_TXT_LOC);
	}
	public boolean isVerifyNextBillShipDate(){
		return driver.isElementPresent(NEXTBILL_SHIP_DATE_TXT_LOC);
	}
}
