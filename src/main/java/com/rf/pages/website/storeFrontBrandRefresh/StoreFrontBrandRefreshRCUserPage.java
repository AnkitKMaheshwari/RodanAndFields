package com.rf.pages.website.storeFrontBrandRefresh;

import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import com.rf.core.driver.website.RFWebsiteDriver;

public class StoreFrontBrandRefreshRCUserPage extends StoreFrontBrandRefreshWebsiteBasePage{
	private static final Logger logger = LogManager
			.getLogger(StoreFrontBrandRefreshRCUserPage.class.getName());

	public StoreFrontBrandRefreshRCUserPage(RFWebsiteDriver driver) {
		super(driver);
	}
	
}