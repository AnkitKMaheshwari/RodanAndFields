package com.rf.pages.website.nscore.dsv;

import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.rf.core.driver.website.RFWebsiteDriver;
import com.rf.pages.RFBasePage;

public class DSVNSCore4RFWebsiteBasePage extends RFBasePage{
	protected RFWebsiteDriver driver;
	private static final Logger logger = LogManager
			.getLogger(DSVNSCore4RFWebsiteBasePage.class.getName());
	public DSVNSCore4RFWebsiteBasePage(RFWebsiteDriver driver) {
		super(driver);
		this.driver = driver;
	}	

	public void clickOKBtnOfJavaScriptPopUp(){
		Alert alert = driver.switchTo().alert();
		alert.accept();
		logger.info("Ok button of java Script popup is clicked.");
		driver.waitForPageLoad();
	}

}
