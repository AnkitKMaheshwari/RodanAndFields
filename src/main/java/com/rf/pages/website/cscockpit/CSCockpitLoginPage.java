package com.rf.pages.website.cscockpit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import com.rf.core.driver.website.RFWebsiteDriver;
import com.rf.core.website.constants.TestConstants;

public class CSCockpitLoginPage extends CSCockpitRFWebsiteBasePage{
	private static final Logger logger = LogManager
			.getLogger(CSCockpitLoginPage.class.getName());

	protected RFWebsiteDriver driver;

	private static final By LOGIN_BTN = By.xpath("//td[text()='Login']");
	private static final By USERNAME_TXT_FIELD = By.name("j_username");
	private static final By PASSWORD_TXT_FIELD = By.name("j_password");

	public CSCockpitLoginPage(RFWebsiteDriver driver) {
		super(driver);
		this.driver = driver;
	}	

	public void enterUsername(){
		driver.pauseExecutionFor(2000);
		driver.waitForElementPresent(USERNAME_TXT_FIELD);
		driver.type(USERNAME_TXT_FIELD, TestConstants.CSCOCKPIT_USERNAME);
		logger.info("username is "+TestConstants.CSCOCKPIT_USERNAME);
	}

	public void enterPassword(){
		driver.pauseExecutionFor(2000);
		driver.waitForElementPresent(PASSWORD_TXT_FIELD);
		driver.type(PASSWORD_TXT_FIELD, TestConstants.CSCOCKPIT_PASSWORD);
		logger.info("password is "+TestConstants.CSCOCKPIT_PASSWORD);
	}

	public CSCockpitCustomerSearchTabPage clickLoginBtn(){
		driver.waitForCSCockpitLoadingImageToDisappear();
		driver.waitForElementPresent(LOGIN_BTN);
		this.enterUsername();
		this.enterPassword();
		driver.click(LOGIN_BTN);
		driver.waitForCSCockpitLoadingImageToDisappear();
		driver.pauseExecutionFor(5000);
		return new CSCockpitCustomerSearchTabPage(driver);
	}

	public CSCockpitCustomerSearchTabPage clickLoginBtn(String userName, String passWord){
		driver.waitForCSCockpitLoadingImageToDisappear();
		driver.waitForElementPresent(LOGIN_BTN);
		enterUsername(userName);
		enterPassword(passWord);
		driver.click(LOGIN_BTN);
		driver.waitForCSCockpitLoadingImageToDisappear();
		driver.pauseExecutionFor(5000);
		return new CSCockpitCustomerSearchTabPage(driver);
	}

	public void enterUsername(String userName){
		driver.pauseExecutionFor(2000);
		driver.waitForElementPresent(USERNAME_TXT_FIELD);
		driver.type(USERNAME_TXT_FIELD, userName);
		logger.info("username is "+userName);
	}

	public void enterPassword(String password){
		driver.pauseExecutionFor(2000);
		driver.waitForElementPresent(PASSWORD_TXT_FIELD);
		driver.type(PASSWORD_TXT_FIELD, password);
		logger.info("password is "+password);
	}
}
