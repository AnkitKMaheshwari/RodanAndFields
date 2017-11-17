package com.rf.pages.website.au.softLaunch;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.rf.core.driver.website.RFWebsiteDriver;
import com.rf.core.website.constants.TestConstants;
import com.rf.pages.website.storeFront.StoreFrontConsultantPage;

public class SoftLaunchHomePage extends SoftLaunchRFWebsiteBasePage {

	public SoftLaunchHomePage(RFWebsiteDriver driver) {
		super(driver);
	}

	private static final Logger logger = LogManager
			.getLogger(SoftLaunchHomePage.class.getName());

	private final By LOGIN_LINK_LOC = By.xpath("//div[@class='login-block']");
	private final By USERNAME_TXTFLD_LOC = By.id("emailAddress");
	private final By PASSWORD_TXTFLD_LOC = By.id("password");
	private final By LOGIN_BTN_LOC = By.id("login");
	private final By CALLING_PRE_ENROLLEES_TEXT_LOC = By.xpath("//div[@class='learn-txt']");
	private final By SHARE_YOUR_URL_LOC = By.xpath("//p[@class='desktop-link']//em[text()='Share your URL to build your network']");
	private final By WHAT_IF_TXT_LOC = By.id("what-if");
	private final By PROFILE_NAME_LOC = By.xpath("//div[@class='profile-image']");
	private final By SPONSOR_NAME_LOC = By.xpath("//div[@class='sponcer-name']");
	private final By SETTINGS_UNDER_PROFILE_NAME_LOC = By.xpath("//div[contains(@class,'profile-header')]//a[text()='Settings']");
	private final By FIRST_NAME_UNDER_MY_PROFILE_LOC = By.id("first-Name");
	private final By LAST_NAME_UNDER_MY_PROFILE_LOC = By.id("last-Name");
	private final By EMAIL_ID_UNDER_MY_PROFILE_LOC = By.id("email-account");
	private final By PHONE_NUMBER_UNDER_MY_PROFILE_LOC = By.id("phonenumber");
	private final By ADDRESS_1_UNDER_MY_PROFILE_LOC = By.id("address-1");
	private final By CITY_UNDER_MY_PROFILE_LOC = By.id("city");
	private final By STATE_UNDER_MY_PROFILE_LOC = By.id("state");
	private final By POSTAL_CODE_UNDER_MY_PROFILE_LOC = By.id("postal-code");
	private final By SIGN_OUT_UNDER_PROFILE_NAME_LOC = By.xpath("//div[contains(@class,'profile-header')]//a[text()='Sign Out']");
	private final By ENROLL_NOW_BTN_LOC = By.xpath("//a[text()='ENROL NOW']");

	private String headerLinkLoc= "//div[contains(@class,'header-containers')]//a[contains(text(),'%s')]";
	private String valuesUnderMyTeamSectionLoc= "//div[contains(@class,'myteam-block')]/descendant::*[contains(text(),'%s')][1]/preceding::span[1]";

	public SoftLaunchHomePage loginAsPreEnrollee(String username,String password){
		driver.waitForElementPresent(LOGIN_LINK_LOC,5);
		driver.click(LOGIN_LINK_LOC);
		logger.info("login link clicked");
		logger.info("login username is: "+username);
		logger.info("login password is: "+password);
		driver.waitForElementPresent(USERNAME_TXTFLD_LOC);
		driver.type(USERNAME_TXTFLD_LOC, username);
		driver.type(PASSWORD_TXTFLD_LOC, password);   
		driver.click(LOGIN_BTN_LOC);
		logger.info("login button clicked");
		driver.waitForPageLoad();
		return new SoftLaunchHomePage(driver);
	}

	public SoftLaunchHomePage clickTheLinkOfHeaderSection(String linkName){
		driver.click(By.xpath(String.format(headerLinkLoc, linkName)));
		logger.info(linkName+" link clicked from header");
		return new SoftLaunchHomePage(driver);
	}

	public String getCallingAllPreEnroleesText(){
		String text = driver.getText(CALLING_PRE_ENROLLEES_TEXT_LOC);
		logger.info("Calling pre enrolees text is "+text);
		return text;
	}

	public boolean isShareYourURLIsNotALink(){
		driver.waitForElementPresent(SHARE_YOUR_URL_LOC);
		return driver.isElementPresent(SHARE_YOUR_URL_LOC);
	}

	public boolean isWhatIfTextPresent(){
		driver.waitForElementPresent(WHAT_IF_TXT_LOC);
		return driver.isElementPresent(WHAT_IF_TXT_LOC);
	}

	public boolean isLoginIconPresent(){
		driver.waitForElementPresent(LOGIN_LINK_LOC);
		return driver.isElementPresent(LOGIN_LINK_LOC);
	}

	public String getCurrentURL(){
		driver.waitForPageLoad();
		String url = driver.getCurrentUrl().toLowerCase();
		logger.info("current url is "+url);
		return url;
	}

	public boolean isUserLoggedInSuccessfully(){
		driver.waitForElementPresent(PROFILE_NAME_LOC);
		return driver.isElementPresent(PROFILE_NAME_LOC);
	}

	public boolean isValuePresentUnderMyTeamSection(String sectionName){
		String value = driver.findElement(By.xpath(String.format(valuesUnderMyTeamSectionLoc, sectionName))).getText();
		return (!value.isEmpty());
	}

	public boolean isSponsorNamePresent(){
		String value = driver.findElement(SPONSOR_NAME_LOC).getText();
		return (!value.isEmpty());
	}

	public SoftLaunchHomePage hoverOnProfileNameAndClickOnSettings(){
		driver.moveToELement(PROFILE_NAME_LOC);
		logger.info("hover on profile name");
		driver.click(SETTINGS_UNDER_PROFILE_NAME_LOC);
		logger.info("Clicked on settings under profile name");
		return new SoftLaunchHomePage(driver);
	}

	public boolean isFirstNamePresent(){
		String name =  driver.findElement(FIRST_NAME_UNDER_MY_PROFILE_LOC).getAttribute("value");
		logger.info("First name is "+name);
		return (!name.isEmpty());
	}

	public boolean isLastNamePresent(){
		String name =  driver.findElement(LAST_NAME_UNDER_MY_PROFILE_LOC).getAttribute("value");
		logger.info("Last name is "+name);
		return (!name.isEmpty());
	}

	public boolean isEmailIdPresent(){
		String email =  driver.findElement(EMAIL_ID_UNDER_MY_PROFILE_LOC).getAttribute("value");
		logger.info("email field is "+email);
		return (!email.isEmpty());
	}

	public boolean isPhoneNumberPresent(){
		String phoneNumber =  driver.findElement(PHONE_NUMBER_UNDER_MY_PROFILE_LOC).getAttribute("value");
		logger.info("phone number is "+phoneNumber);
		return (!phoneNumber.isEmpty());
	}

	public boolean isAddress1Present(){
		String address1 =  driver.findElement(ADDRESS_1_UNDER_MY_PROFILE_LOC).getAttribute("value");
		logger.info("address is "+address1);
		return (!address1.isEmpty());
	}

	public boolean isCityPresent(){
		String city =  driver.findElement(CITY_UNDER_MY_PROFILE_LOC).getAttribute("value");
		logger.info("city is "+city);
		return (!city.isEmpty());
	}

	public boolean isStatePresent(){
		String state =  driver.findElement(STATE_UNDER_MY_PROFILE_LOC).getAttribute("value");
		logger.info("state is "+state);
		return (!state.isEmpty());
	}

	public boolean isPostalCodePresent(){
		String postalCode =  driver.findElement(POSTAL_CODE_UNDER_MY_PROFILE_LOC).getAttribute("value");
		logger.info("postal sode is "+postalCode);
		return (!postalCode.isEmpty());
	}

	public SoftLaunchHomePage logout(){
		driver.moveToELement(PROFILE_NAME_LOC);
		logger.info("hover on profile name");
		driver.click(SIGN_OUT_UNDER_PROFILE_NAME_LOC);
		logger.info("Clicked on sign out link under profile name");
		return new SoftLaunchHomePage(driver);
	}


	/***
	 * This method click the enroll now button 
	 * 
	 * @param
	 * @return SofLaunch Home page object
	 * 
	 */
	public SoftLaunchHomePage clickEnrollNowBtn(){
		driver.waitForElementPresent(ENROLL_NOW_BTN_LOC);
		driver.click(ENROLL_NOW_BTN_LOC);
		logger.info("Enroll now button clicked");
		return new SoftLaunchHomePage(driver);
	}

	public void selectEnrollmentKitPage(String kitName,String regimenName){
		driver.click(By.xpath("//*[contains(text(),'"+kitName+"')]"));
		driver.waitForLoadingImageToDisappear();
		regimenName = regimenName.toUpperCase();
		Actions actions = new Actions(RFWebsiteDriver.driver);
		actions.moveToElement(driver.findElement(By.xpath("//span[@class='regimen-name' and contains(.,'"+regimenName+"')]"))).click();
		logger.info("Regimen is selected as "+regimenName);
		driver.click (By.id("next-button")); // - old UI (By.cssSelector("input[value='Next']"));
		logger.info("Next button clicked after selected Kit and regimen");
	}

	public void chooseEnrollmentOption(String option){
		option = option.toUpperCase();
		if(option.equalsIgnoreCase("EXPRESS ENROLLMENT")){
			driver.click(By.id("express-enrollment"));
			logger.info("Express EnrollmentTest is clicked");
		}
		else{
			driver.waitForElementPresent(By.id("standard-enrollment"));
			driver.click(By.id("standard-enrollment"));
			logger.info("Standard EnrollmentTest is clicked");
		}
		driver.click(By.cssSelector("input[value='Next']"));
		logger.info("Next button is clicked after selecting enrollment type");
		//		driver.waitForLoadingImageToDisappear();
	}

	public void enterPassword(String password){
		driver.type(By.id("new-password-account"),password);
		logger.info("Password entered is "+password);
	}

	public void enterConfirmPassword(String password){
		driver.type(By.id("new-password-account2"),password);
	}
	
	public void enterAddressLine1(String addressLine1){
		driver.type(By.id("address-1"),addressLine1);
		logger.info("Address Line 1 entered is "+addressLine1);
	}

	public void enterCity(String city){
		driver.type(By.id("city"),city);
		logger.info("City entered is "+city);
	}

	public void selectProvince(String province){ 
		if(driver.getCountry().equalsIgnoreCase("au")){
			driver.clickByJS(RFWebsiteDriver.driver, driver.findElement(By.id("state")));
			driver.click(By.xpath(String.format("//select[@id='state']//option[@value='%s']", province)));
		}else{
			WebElement stateDD = driver.findElement(By.id("state"));
			Select sel = new Select(stateDD);
			sel.selectByVisibleText(province);
		}
		logger.info("state selected");
	}
	
	public void enterPostalCode(String postalCode){
		driver.type(By.id("postcode"),postalCode);
		logger.info("postal code entered is "+postalCode);
	}

	public void enterPhoneNumber(String phnNum){
		driver.type(By.id("phonenumber"),phnNum);
		logger.info("phone number entered is "+phnNum);
	}

	public void clickNextButton(){
		  driver.waitForElementPresent(By.id("enrollment-next-button"));
		  driver.clickByJS(RFWebsiteDriver.driver, driver.findElement(By.id("enrollment-next-button")));
		  // driver.click(By.id("enrollment-next-button"));
		  logger.info("EnrollmentTest Next Button clicked");
		  driver.waitForLoadingImageToDisappear();
		  if(driver.isElementPresent(By.xpath("//label[contains(text(),'Postcode must be 4 digits long')]"))){
		   driver.type(By.id("postcode"), TestConstants.POSTAL_CODE_AU);
		   logger.info("Postal code entered");
		   driver.clickByJS(RFWebsiteDriver.driver, driver.findElement(By.id("enrollment-next-button")));
		   driver.waitForLoadingImageToDisappear();
		  }
		  
		  try{
		   driver.quickWaitForElementPresent(By.xpath("//input[@value='Accept']"),5);
		   driver.clickByJS(RFWebsiteDriver.driver, driver.findElement(By.xpath("//input[@value='Accept']")));
		   logger.info("Accept the original button clicked");
		  }
		  catch(Exception e){
		   logger.info("Accept the original pop up was NOT present");
		  }
		  
		  driver.waitForLoadingImageToDisappear();
		 }

	public void enterCardNumber(String cardNumber){
		driver.waitForElementPresent(By.id("card-nr"));
		driver.type(By.id("card-nr"),cardNumber);
		driver.pauseExecutionFor(2000);
		driver.clickByJS(RFWebsiteDriver.driver, driver.findElement(By.xpath("//*[@id='expiryYear']")));
		driver.type(By.id("security-code"),"123");
		driver.clear(By.id("security-code"));
		logger.info("card number entered as "+cardNumber);
	}

	public void enterNameOnCard(String nameOnCard){
		driver.type(By.id("card-name"),nameOnCard);
		logger.info("name on card entered is "+nameOnCard);
	}

	public void selectNewBillingCardExpirationDate(String month,String year){
		//driver.click(By.id("expiryMonth"));
		driver.clickByJS(RFWebsiteDriver.driver,driver.findElement(By.id("expiryMonth")));
		driver.waitForElementPresent(By.xpath("//select[@id='expiryMonth']/option[text()='"+month.toUpperCase()+"']"));
		driver.click(By.xpath("//select[@id='expiryMonth']/option[text()='"+month.toUpperCase()+"']"));
		//driver.click(By.id("expiryYear"));
		driver.clickByJS(RFWebsiteDriver.driver,driver.findElement(By.id("expiryYear")));
		driver.waitForElementPresent(By.xpath("//select[@id='expiryYear']/option[text()='"+year+"']"));
		driver.click(By.xpath("//select[@id='expiryYear']/option[text()='"+year+"']"));
	}

	public void enterSecurityCode(String securityCode){
		driver.type(By.id("security-code"),securityCode);
		logger.info("security code entered is "+securityCode);
	}

	public void selectAllTermsAndConditionsChkBox(){
		List<WebElement> all = driver.findElements(By.xpath("//form[@id='placeOrderForm']/div/descendant::input/.."));
		for(WebElement chkbox : all){
			driver.clickByJS(RFWebsiteDriver.driver, chkbox);
		}
	}

	public void clickOnChargeMyCardAndEnrollMeBtn() throws InterruptedException{
		driver.clickByJS(RFWebsiteDriver.driver, driver.findElement(By.id("enroll-button")));
		logger.info("Charge my card button clicked");
		driver.waitForLoadingImageToDisappear();  
	}

	public void clickOnConfirmAutomaticPayment() throws InterruptedException{
		try{
			driver.pauseExecutionFor(4000);
			driver.waitForElementPresent(By.xpath("//input[@id='enroll']"));
			driver.clickByJS(RFWebsiteDriver.driver,driver.findElement(By.xpath("//input[@id='enroll']")));
			logger.info("Automatic payment confirmation button clicked");
			driver.waitForLoadingImageToDisappear();
		}catch(NoSuchElementException e){
			logger.info("Confirmation Automatic Payment popup not present.");
		}
	}

	public String getCongratulationsMessage(){
		driver.waitForElementPresent(By.id("Congrats"));
		String message = driver.getText(By.id("Congrats"));
		logger.info("Congratulations message is "+message);
		return message;
	}

	public void checkPulseAndCRPEnrollment() throws InterruptedException{
		if(verifySubsribeToPulseCheckBoxIsNotSelected()){
			driver.waitForElementPresent(By.xpath("//input[@id='pulse-check']/.."));
			driver.click(By.xpath("//input[@id='pulse-check']/.."));
			logger.info("Yes,Subscribe me to pulse checkbox is checked");			
		}
		if(verifyEnrollToCRPCheckBoxIsNotSelected()){
			driver.waitForElementPresent(By.xpath("//input[@id='CRP-check']/.."));
			driver.click(By.xpath("//input[@id='CRP-check']/.."));
			logger.info("Yes,enroll me in CRP checkbox is checked");	
		}
	}

	public boolean verifySubsribeToPulseCheckBoxIsNotSelected(){
		driver.pauseExecutionFor(3000);
		driver.waitForElementPresent(By.xpath("//input[@id='pulse-check']"));
		return !driver.findElement(By.xpath("//input[@id='pulse-check']")).getAttribute("class").contains("checked");
	}

	public boolean verifyEnrollToCRPCheckBoxIsNotSelected(){
		driver.waitForElementPresent(By.xpath("//input[@id='CRP-check']"));
		return !driver.findElement(By.xpath("//input[@id='CRP-check']")).getAttribute("class").contains("checked");
	}

	public void selectProductAndProceedToAddToCRP() throws InterruptedException{
		driver.quickWaitForElementPresent(By.xpath("//div[@id='main-content']/descendant::*[@value='Add to crp' or @value='ADD TO CRP'][1]"));
		driver.clickByJS(RFWebsiteDriver.driver, driver.findElement(By.xpath("//div[@id='main-content']/descendant::*[@value='Add to crp' or @value='ADD TO CRP'][1]")));
		driver.waitForLoadingImageToDisappear();
		logger.info("Add to CRP button clicked");
		try{
			driver.quickWaitForElementPresent(By.xpath("//input[@value='OK']"));
			driver.click(By.xpath("//input[@value='OK']"));
		}catch(Exception e){

			driver.waitForLoadingImageToDisappear();
			driver.waitForPageLoad();
		}
	}

	public void clickOnNextBtnAfterAddingProductAndQty() throws InterruptedException{
		driver.clickByJS(RFWebsiteDriver.driver, driver.findElement(By.id("submitForm")));
		logger.info("Next button after adding quantity clicked");
		driver.waitForLoadingImageToDisappear();
	}
	
	public void uncheckPulseAndCRPEnrollment() throws InterruptedException{
		if(verifySubsribeToPulseCheckBoxIsSelected())
		{
//			driver.waitForElementPresent(By.xpath("//input[@id='pulse-check']"));
			driver.clickByJS(RFWebsiteDriver.driver, driver.findElement(By.xpath("//input[@id='pulse-check']/..")));
			// driver.click(By.xpath("//li[text()='Yes, subscribe me to Pulse Pro.']/preceding::div[1]/input/.."));
			logger.info("Yes,Subscribe me to pulse checkbox is unchecked");   
		}
		if(verifyEnrollToCRPCheckBoxIsSelected())
		{
//			driver.waitForElementPresent(By.xpath("//input[@id='CRP-check']"));
			driver.clickByJS(RFWebsiteDriver.driver, driver.findElement(By.xpath("//input[@id='CRP-check']/..")));
			logger.info("Yes,enroll me in CRP checkbox is unchecked"); 
		}  
	}
	
	public boolean verifySubsribeToPulseCheckBoxIsSelected() throws InterruptedException{  
//		driver.waitForElementPresent(By.xpath("//input[@id='pulse-check']/ancestor::div[@class='repaired-checkbox checked']"));
		return driver.isElementPresent(By.xpath("//input[@id='pulse-check']/ancestor::div[@class='repaired-checkbox checked']"));
	}
	
	public boolean verifyEnrollToCRPCheckBoxIsSelected(){
//		driver.waitForElementPresent(By.xpath("//input[@id='CRP-check']/ancestor::div[@class='repaired-checkbox checked']"));
		return driver.isElementPresent(By.xpath("//input[@id='CRP-check']/ancestor::div[@class='repaired-checkbox checked']"));
	}
	
	public void checkPulseCheckBox(){
		driver.click(By.xpath("//input[@id='pulse-check']/.."));
}

public void checkCRPCheckBox(){
	driver.waitForElementPresent(By.xpath("//input[@id='CRP-check']/.."));
	driver.click(By.xpath("//input[@id='CRP-check']/.."));
}


}
