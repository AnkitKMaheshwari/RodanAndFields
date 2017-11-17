package com.rf.pages.website.au.softLaunch;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.rf.core.driver.website.RFWebsiteDriver;
import com.rf.pages.RFBasePage;

public class SoftLaunchRFWebsiteBasePage extends RFBasePage{
	protected RFWebsiteDriver driver;
	public SoftLaunchRFWebsiteBasePage(RFWebsiteDriver driver) {
		super(driver);
		this.driver = driver;
	}

	private static final Logger logger = LogManager
			.getLogger(SoftLaunchRFWebsiteBasePage.class.getName());

	private String labelNameLoc= "//label[text()='%s']";
	private String textLoc= "//div[contains(@class,'header-containers')]//a[text()='%s']";
	private String textAtBusinessPageLoc= "//*[text()='%s']";

	public boolean isTextPresent(String text){
		driver.waitForElementPresent(By.xpath(String.format(textLoc, text)));
		return driver.isElementPresent(By.xpath(String.format(textLoc, text)));
	}

	public boolean isTextPresentOnPages(String text){
		driver.waitForElementPresent(By.xpath(String.format(textAtBusinessPageLoc, text)));
		return driver.isElementPresent(By.xpath(String.format(textAtBusinessPageLoc, text)));
	}
	
	public boolean isLabelPresent(String labelName){
		driver.waitForElementPresent(By.xpath(String.format(labelNameLoc, labelName)));
		return driver.isElementPresent(By.xpath(String.format(labelNameLoc, labelName)));
	}

}