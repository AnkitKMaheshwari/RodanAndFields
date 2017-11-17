package com.rf.core.driver.website;

import java.io.File;

import org.openqa.selenium.Dimension;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xpath.CachedXPathAPI;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.lang.reflect.Method;
import com.rf.core.driver.RFDriver;
import com.rf.core.listeners.TestListner;
import com.rf.core.utils.CommonUtils;
import com.rf.core.utils.DBUtil;
import com.rf.core.utils.PropertyFile;
import com.rf.core.utils.SoftAssert;

import io.appium.java_client.android.AndroidDriver;

/**
 * @author ShubhamMathur RFWebsiteDriver extends implements Webdriver and
 *         uses Firefox/Internet/Chrome for implementation, customized reusable
 *         functions are added along with regular functions
 */
public class RFWebsiteDriver implements RFDriver,WebDriver {
	public static WebDriver driver; // added static and changed visibility from public to private
	private PropertyFile propertyFile;
	private static int DEFAULT_TIMEOUT = 20;
	private static int DEFAULT_TIMEOUT_CSCOCKPIT = 70;
	String browser = null;
	String dbIP = null;
	String baseURL  =null;
	String country = null;
	String environment = null;
	public RFWebsiteDriver(PropertyFile propertyFile) {
		//super();
		this.propertyFile = propertyFile;			
	}

	private static final Logger logger = LogManager
			.getLogger(RFWebsiteDriver.class.getName());


	/**
	 * @throws MalformedURLException
	 *             Prepares the environment that tests to be run on
	 */
	public void loadApplication() throws MalformedURLException {
		browser=System.getProperty("browser");
		if(StringUtils.isEmpty(browser)){
			browser = propertyFile.getProperty("browser");
		}

		if(propertyFile.getProperty("device").equalsIgnoreCase("mobile")){
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setPlatform(Platform.ANDROID);
			capabilities.setBrowserName(BrowserType.CHROME);
			capabilities.setCapability("deviceName", "MOTO G3");
			capabilities.setCapability("platformVersion", "6.0.1");
			capabilities.setCapability("newCommandTimeout", 200000);
			driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		}else{
			FirefoxProfile prof = new FirefoxProfile();
			prof.setPreference("brower.startup.homepage", "about:blank");
			prof.setPreference("startup.homepage_welcome_url", "about:blank");
			prof.setPreference("startup.homepage_welcome_url.additional",  "about:blank");
			if (browser.equalsIgnoreCase("firefox"))
				driver = new FirefoxDriver(prof);
			else if (browser.equalsIgnoreCase("chrome")){
				System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("no-sandbox");
				options.addArguments("chrome.switches","--disable-extensions");
				options.addArguments("disable-popup-blocking");
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				// for clearing cache
				capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
				driver = new ChromeDriver(capabilities);
			}
			else if(browser.equalsIgnoreCase("headless")){
				driver = new HtmlUnitDriver(true);
			}
			else if(browser.equalsIgnoreCase("ie")){
				System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
				DesiredCapabilities capabilities = new DesiredCapabilities();
				// for clearing cache
				capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
				driver = new InternetExplorerDriver(capabilities);
				if (browser.equalsIgnoreCase("firefox")){
					System.out.println(driver.manage().window().getSize());
					Dimension d = new Dimension(1936, 1056);
					driver.manage().window().setSize(d);
					System.out.println("Dimension reset to larger");
					System.out.println(driver.manage().window().getSize());	
				}
			}
			driver.manage().window().maximize();
		}
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		logger.info("Window is maximized");
		// for clearing cookies
		driver.manage().deleteAllCookies();
		logger.info("All cookies deleted");
		//driver.get(propertyFile.getProperty("baseUrl"));
	}

	public void setDBConnectionString(){
		String dbUsername = null;
		String dbPassword = null;
		String dbDomain = null;

		dbIP=System.getProperty("dbIP");
		if(StringUtils.isEmpty(dbIP)){
			dbIP = propertyFile.getProperty("dbIP");
		}
		String authentication = null;
		dbUsername = propertyFile.getProperty("dbUsername");
		dbPassword = propertyFile.getProperty("dbPassword");
		dbDomain = propertyFile.getProperty("dbDomain");		 
		authentication = propertyFile.getProperty("authentication");
		DBUtil.setDBDetails(dbIP, dbUsername, dbPassword, dbDomain, authentication);
		logger.info("DB connections are set");
	}

	public String getDBIP2(){
		return propertyFile.getProperty("dbIP2");
	}

	public void selectCountry(String country){
		driver.findElement(By.xpath("//div[@class='btn-group']")).click();
		if(country.equalsIgnoreCase("ca")){
			driver.findElement(By.xpath("//div[contains(@class,'btn-group')]//a[@class='dropdownCA']")).click();
		}
		else{
			driver.findElement(By.xpath("//div[contains(@class,'btn-group')]//a[@class='dropdownUS']")).click();
		}
		waitForPageLoad();
	}

	public String getURL() {
		baseURL=System.getProperty("baseURL");
		if(StringUtils.isEmpty(baseURL)){
			baseURL = propertyFile.getProperty("baseUrl");
		}
		return baseURL;
	}

	public String getBrowser(){
		return browser;
	}
	public String getBizPWSURL() {
		if (propertyFile.getProperty("environment").equalsIgnoreCase("tst1"))
		{
			return propertyFile.getProperty("pwsBase")+".biz";
		}
		else
		{
			return propertyFile.getProperty("pwsBase")+getEnvironment()+".biz";
		}
	}

	public String getComPWSURL() {
		//		return propertyFile.getProperty("pwsComBase");
		if (propertyFile.getProperty("environment").equalsIgnoreCase("tst1"))
		{
			return propertyFile.getProperty("pwsBase")+".com";
		}
		else
		{
			return propertyFile.getProperty("pwsBase")+getEnvironment()+".com";
		}
	}

	public String getDBNameRFL(){
		return propertyFile.getProperty("databaseNameRFL");
	}

	public String getDBNameRFO(){
		return propertyFile.getProperty("databaseNameRFO");
	}

	public String getCountry(){
		country=System.getProperty("country");
		if(StringUtils.isEmpty(country)){
			country = propertyFile.getProperty("country");
		}
		return country;
	}

	public String getEnvironment(){
		environment=System.getProperty("env");
		if(StringUtils.isEmpty(environment)){
			environment = propertyFile.getProperty("environment");
		}
		return environment;
	}
	public String getStoreFrontPassword(){
		return propertyFile.getProperty("storeFrontPassword");
	}

	public String getCSCockpitURL(){
		return propertyFile.getProperty("csCockpitUrl");
	}

	public String getStoreFrontURL(){
		return propertyFile.getProperty("storeFrontUrl");
	}

	public String isUserFetchedFromDB(){
		return propertyFile.getProperty("fetchUsersFromDB");
	}

	/**
	 * @param locator
	 * @return
	 */
	public boolean isElementPresent(By locator) {
		turnOffImplicitWaits();
		try{
			if (driver.findElements(locator).size() > 0) {
				return true;
			} else
				return false;
		}
		catch(Exception e){
			return false;
		}

		finally{
			turnOnImplicitWaits();
		}
	}

	public boolean waitForElementPresent(By locator) {
		logger.info("wait started for "+locator);
		int timeout = 7;
		turnOffImplicitWaits();
		boolean isElementFound = false;
		for(int i=1;i<=timeout;i++){		
			if(driver.findElements(locator).size()==0){
				pauseExecutionFor(1000);
				logger.info("waiting...");
				continue;
			}else{
				logger.info("wait over,element found");
				isElementFound =true;					
				pauseExecutionFor(1000);
				break;
			}			
		}
		if(isElementFound ==false)
			logger.info("ELEMENT NOT FOUND");	
		turnOnImplicitWaits();
		return isElementFound;
	}

	public void waitForElementPresent(By locator, int timeout) {
		logger.info("wait started for "+locator);
		turnOffImplicitWaits();
		boolean isElementFound = false;
		for(int i=1;i<=timeout;i++){  
			//			try{
			if(driver.findElements(locator).size()==0){
				pauseExecutionFor(1000);
				logger.info("waiting...");
				continue;
			}else{
				logger.info("wait over,element found");
				isElementFound =true;					
				pauseExecutionFor(1000);
				break;
			}   
			//			}catch(Exception e){
			//				continue;
			//			}
		}
		if(isElementFound ==false)
			logger.info("ELEMENT NOT FOUND");  
		turnOnImplicitWaits();
	}

	public boolean quickWaitForElementPresent(By locator){
		logger.info("quick wait started for "+locator);
		int timeout = 2;
		turnOffImplicitWaits();
		boolean isElementFound = false;
		for(int i=1;i<=timeout;i++){
			//			try{
			if(driver.findElements(locator).size()==0){
				pauseExecutionFor(1000);
				logger.info("waiting...");
				continue;
			}else{
				logger.info("wait over,element found");
				isElementFound =true;
				turnOnImplicitWaits();
				break;
			}			
			//			}catch(Exception e){
			//				continue;
			//			}
		}

		if(isElementFound==false)
			logger.info("ELEMENT NOT FOUND");
		turnOnImplicitWaits();
		return isElementFound;
	}

	public boolean quickWaitForElementPresent(By locator, int timeout){
		logger.info("quick wait started for "+locator);
		turnOffImplicitWaits();
		boolean isElementFound = false;
		for(int i=1;i<=timeout;i++){
			if(driver.findElements(locator).size()==0){
				pauseExecutionFor(1000);
				logger.info("waiting...");
				continue;
			}else{
				logger.info("wait over,element found");
				isElementFound =true;
				turnOnImplicitWaits();
				break;
			}			
		}

		if(isElementFound==false)
			logger.info("ELEMENT NOT FOUND");
		turnOnImplicitWaits();
		return isElementFound;
	}

	public void waitForElementNotPresent(By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
			logger.info("waiting for locator " + locator);
			wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(locator)));
			logger.info("Element found");
		} catch (Exception e) {
			e.getStackTrace();
		}		
	}

	public void waitForLoadingImageToDisappear(){
		int DEFAULT_TIMEOUT = 10;
		turnOffImplicitWaits();
		By locator = By.xpath("//div[@id='blockUIBody']");
		if(driver.findElements(locator).size()==1){
			logger.info("Waiting for loading image to get disappear");
			for(int i=1;i<=DEFAULT_TIMEOUT;i++){			
				try{
					if(driver.findElements(locator).size()==1){
						pauseExecutionFor(1000);
						logger.info("waiting..");
						continue;
					}else{
						turnOnImplicitWaits();
						logger.info("loading image disappears");
						break;
					}			
				}catch(Exception e){
					continue;
				}
			}

		}
	}

	public void waitForLoadingImageToDisappear(int timeout){
		int DEFAULT_TIMEOUT = timeout;
		turnOffImplicitWaits();
		By locator = By.xpath("//div[@id='blockUIBody']");
		logger.info("Waiting for loading image to get disappear");
		for(int i=1;i<=DEFAULT_TIMEOUT;i++){			
			try{
				if(driver.findElements(locator).size()==1){
					pauseExecutionFor(1000);
					logger.info("waiting..");
					continue;
				}else{
					turnOnImplicitWaits();
					logger.info("loading image disappears");
					break;
				}			
			}catch(Exception e){
				continue;
			}
		}

	}

	public void waitForCSCockpitLoadingImageToDisappear(){
		turnOffImplicitWaits();
		By locator = By.xpath("//div[@class='z-loading-indicator']");
		logger.info("Waiting for cscockpit loading image to get disappear");
		for(int i=1;i<=DEFAULT_TIMEOUT_CSCOCKPIT;i++){			
			try{
				if(driver.findElements(locator).size()==1){
					pauseExecutionFor(1000);
					logger.info("waiting..");
					continue;
				}else{
					turnOnImplicitWaits();
					logger.info("cscockpit loading image disappears");
					break;
				}			
			}catch(Exception e){
				continue;
			}
		}

	}

	public void waitForCSCockpitLoadingImageToDisappear(int time){
		turnOffImplicitWaits();
		By locator = By.xpath("//div[@class='z-loading-indicator']");
		logger.info("Waiting for cscockpit loading image to get disappear");
		for(int i=1;i<=time;i++){			
			try{
				if(driver.findElements(locator).size()==1){
					pauseExecutionFor(1000);
					logger.info("waiting..");
					continue;
				}else{
					turnOnImplicitWaits();
					logger.info("cscockpit loading image disappears");
					break;
				}			
			}catch(Exception e){
				continue;
			}
		}

	}

	public void waitForCRMLoadingImageToDisappear(){
		turnOffImplicitWaits();
		By locator = By.xpath("//span[contains(text(),'Loading')]");
		logger.info("Waiting for crm loading image to get disappear");
		for(int i=1;i<=DEFAULT_TIMEOUT;i++){			
			try{
				if(driver.findElements(locator).size()==1){
					pauseExecutionFor(1000);
					logger.info("waiting..");
					continue;
				}else{
					turnOnImplicitWaits();
					logger.info(" crm loading image disappears");
					break;
				}			
			}catch(Exception e){
				continue;
			}
		}

	}	

	public void waitForLoadingImageToAppear(){
		turnOffImplicitWaits();
		By locator = By.xpath("//div[@id='blockUIBody']");
		int timeout = 3;

		for(int i=1;i<=timeout;i++){			
			try{
				if(driver.findElements(locator).size()==0){
					pauseExecutionFor(1000);

					continue;
				}else{
					turnOnImplicitWaits();

					break;
				}			
			}catch(Exception e){
				continue;
			}
		}

	}

	public void waitForSpinImageToDisappear(){
		turnOffImplicitWaits();
		By locator = By.xpath("//span[@id='email-ajax-spinner'][contains(@style,'display: inline;')]");
		logger.info("Waiting for spin image to get disappear");
		for(int i=1;i<=DEFAULT_TIMEOUT;i++){
			try{
				if(driver.findElements(locator).size()==1){
					pauseExecutionFor(1000);
					logger.info("waiting..");
					continue;
				}else {
					logger.info("spin image disappears");
					turnOnImplicitWaits();
					break;
				}			
			}catch(Exception e){
				continue;
			}
		}
	}

	public void waitForStorfrontLegacyLoadingImageToDisappear(){
		turnOffImplicitWaits();
		By locator = By.xpath("//div[contains(@id,'UpdateProgress')][contains(@style,'display: block;')]");
		logger.info("Waiting for storefront legacy loading image to get disappear");
		for(int i=1;i<=DEFAULT_TIMEOUT;i++){   
			try{
				if(driver.findElements(locator).size()==1){
					pauseExecutionFor(1000);
					logger.info("waiting..");
					continue;
				}else{
					turnOnImplicitWaits();
					logger.info("Storefront legacy loading image disappears");
					break;
				}   
			}catch(Exception e){
				continue;
			}
		}

	}

	public void waitForNSCore4LoadingImageToDisappear(){
		turnOffImplicitWaits();
		By locator = By.xpath("//div[@style='display: block;']/img");
		logger.info("Waiting for NSCore4 loading image to get disappear");
		for(int i=1;i<=DEFAULT_TIMEOUT;i++){   
			try{
				if(driver.findElements(locator).size()==1){
					pauseExecutionFor(1000);
					logger.info("waiting..");
					continue;
				}else{
					turnOnImplicitWaits();
					logger.info("NSCore4 loading image disappears");
					break;
				}   
			}catch(Exception e){
				continue;
			}
		}

	}

	public void waitForNSCore4ProcessImageToDisappear(){
		turnOffImplicitWaits();
		By locator = By.xpath("//div[@class='HModalOverlay']");
		logger.info("Waiting for NSCore4 process loading image to get disappear");
		for(int i=1;i<=DEFAULT_TIMEOUT;i++){   
			try{
				if(driver.findElements(locator).size()==1){
					pauseExecutionFor(1000);
					logger.info("waiting..");
					continue;
				}else{
					turnOnImplicitWaits();
					logger.info("NSCore4 process loading image disappears");
					break;
				}   
			}catch(Exception e){
				continue;
			}
		}
	}

	public void waitForLSDJustAMomentImageToDisappear(){
		turnOffImplicitWaits();
		By locator = By.xpath("//div[@id='preload'][not(@style='display:none')]");
		logger.info("Waiting for LSD loading image to get disappear");
		for(int i=1;i<=DEFAULT_TIMEOUT;i++){   
			try{
				if(driver.findElements(locator).size()==1){
					pauseExecutionFor(1000);
					logger.info("waiting..");
					continue;
				}else{
					turnOnImplicitWaits();
					logger.info("LSD loading image disappears");
					break;
				}   
			}catch(Exception e){
				continue;
			}
		}
	}

	public void waitForLSDLoaderAnimationImageToDisappear(){
		turnOffImplicitWaits();
		By locator = By.xpath("//div[contains(@class,'loader-animation')][(@style='')]");
		logger.info("Waiting for LSD loading image to get disappear");
		for(int i=1;i<=DEFAULT_TIMEOUT;i++){   
			try{
				if(driver.findElements(locator).size()==1){
					pauseExecutionFor(1000);
					logger.info("waiting..");
					continue;
				}else{
					turnOnImplicitWaits();
					logger.info("LSD Animation image disappears");
					break;
				}   
			}catch(Exception e){
				continue;
			}
		}
	}

	public void waitForElementTobeEnabled(By locator){
		for(int time=1;time<=30;time++){
			if(driver.findElement(locator).isEnabled()==true){
				break;
			}		
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void waitForElementTobeEnabled(By locator,int timeout){
		for(int time=1;time<=timeout;time++){
			if(driver.findElement(locator).isEnabled()==true){
				break;
			}		
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void switchAndAcceptAlert(){
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
		}
	}

	public void get(String Url) {
		logger.info("URL opened is "+Url);
		driver.get(Url);
		waitForPageLoad();
	}

	public void click(By locator) {		
		try{
		waitForElementToBeClickable(locator, 10);
		waitForElementTobeEnabled(locator,10);
		waitForElementToBeVisible(locator, 10);
		}catch(Exception e){
			pauseExecutionFor(1000);
		}
		//quickWaitForElementPresent(locator);
		//movetToElementJavascript(locator);
		turnOffImplicitWaits();
		try{
//			clickByJS(RFWebsiteDriver.driver,locator);
			findElement(locator).click();			
		}catch(Exception e){
			retryingFindClick(locator);
		}
		//retryClick(locator, "WebElement");
		turnOnImplicitWaits();
	}


	public void click(By locator, String elementClicked) {		
		try{
			waitForElementToBeClickable(locator, 10);
			waitForElementTobeEnabled(locator,10);
			waitForElementToBeVisible(locator, 10);
			}catch(Exception e){
				pauseExecutionFor(1000);
			}
		//quickWaitForElementPresent(locator);
		//movetToElementJavascript(locator);
		turnOffImplicitWaits();
		try{
//			clickByJS(RFWebsiteDriver.driver,locator);
			findElement(locator).click();			
		}catch(Exception e){
			retryingFindClick(locator);
		}
		//retryClick(locator, "WebElement");
		turnOnImplicitWaits();
		logger.info("CLICKED on "+elementClicked);
	}

	public void retryClick(By by, String msg){
		for(int i=1;i<=3;i++){
			try{
				if(isElementVisible(by)==true && i==1){
					findElement(by).click();
					pauseExecutionFor(1000);
					continue;
				}
				else if(isElementVisible(by)==true && i==2){
					clickByJS(RFWebsiteDriver.driver, by);
					pauseExecutionFor(1000);
					continue;
				}
				else if(isElementVisible(by)==true && i==2){
					Actions actions = new Actions(RFWebsiteDriver.driver);
					actions.click(driver.findElement(by)).build().perform();
					pauseExecutionFor(1000);
					continue;
				}
				else
					break;
			}catch(Exception e){
				break;
			}
			//			if(isElementPresent(by)){
			//				logger.info("click not worked for  "+msg+" trying again..");
			//				driver.findElement(by).click();
			//			}
			//			else{
			//				break;
			//			}
		}
	}

	public void type(By locator, String input) {
		/*		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		 */		
		// quickWaitForElementPresent(locator);
		for(int i=1;i<=3;i++){
			try{
				findElement(locator).clear();
				break;
			}catch(Exception e){				
				clearTxtField(locator);
			}

		}

		findElement(locator).sendKeys(input);
	}

	public void clearTxtField(By by){
		Actions action = new Actions(driver);
		int lenText = driver.findElement(by).getText().length();

		for(int i = 0; i < lenText; i++){
			action.sendKeys(Keys.ARROW_LEFT);
		}
		action.build().perform();

		for(int i = 0; i < lenText; i++){
			action.sendKeys(Keys.DELETE);
		}
		pauseExecutionFor(1000);
		action.build().perform();
	}

	public void type(By locator, String input, String element) {
		/*		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		 */		
		// quickWaitForElementPresent(locator);
		try{
			findElement(locator).clear();
		}catch(Exception e){
			pauseExecutionFor(1000);
			findElement(locator).clear();	
		}
		findElement(locator).sendKeys(input);
		logger.info("TYPED text as = "+input+" in "+element+" text field");
	}


	public void quit() {
		driver.quit();
	}

	public String getCurrentUrl() {
		String currentURL = driver.getCurrentUrl();
		logger.info("current URL is "+currentURL);
		return currentURL;
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public List<WebElement> findElements(By by) {
		// movetToElementJavascript(by);
		return driver.findElements(by);
	}

	public WebElement findElement(By by) {
		return driver.findElement(by);
	}

	public WebElement findElementWithoutMove(By by) {
		// movetToElementJavascript(by);
		return driver.findElement(by);
	}

	public String getPageSource() {
		return driver.getPageSource();
	}

	public void close() {
		driver.close();
	}

	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	public String getWindowHandle() {
		return driver.getWindowHandle();
	}

	public TargetLocator switchTo() {
		return driver.switchTo();
	}

	public Navigation navigate() {
		return driver.navigate();

	}

	public Options manage() {
		return driver.manage();
	}

	public Select getSelect(By by) {
		return new Select(findElement(by));
	}

	public void scrollToBottomJS() throws InterruptedException {
		((JavascriptExecutor) driver)
		.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
	}

	public void clear(By by) {
		//		quickWaitForElementPresent(by);
		findElement(by).clear();
	}

	public void movetToElementJavascript(By locator) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);",
				driver.findElement(locator));
	}

	public String getAttribute(By by, String name) {
		return findElement(by).getAttribute(name);
	}

	/**
	 * 
	 * Purpose:This Method return text value of Element.
	 * 
	 * @author: 
	 * @date:
	 * @returnType: String
	 * @param by
	 * @return
	 */
	public String getText(By by) {
		return findElement(by).getText();
	}

	/**
	 * 
	 * Purpose:This Method return text value of Element.
	 * 
	 * @author: 
	 * @date:
	 * @returnType: String
	 * @param by
	 * @return
	 */
	public String getText(By by,String msg) {
		String text =  findElement(by).getText();
		logger.info("TEXT fetched for "+msg+" is="+text);
		return text;
	}

	public void fnDragAndDrop(By by) throws InterruptedException {
		WebElement Slider = driver.findElement(by);
		Actions moveSlider = new Actions(driver);
		moveSlider.clickAndHold(Slider);
		moveSlider.dragAndDropBy(Slider, 150, 0).build().perform();
	}

	public Actions action() {
		return new Actions(driver);
	}

	public void pauseExecutionFor(long lTimeInMilliSeconds) {
		try {
			Thread.sleep(lTimeInMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if element is visible Purpose:
	 * 
	 * @author: 
	 * @date:
	 * @returnType: WebElement
	 */
	public boolean IsElementVisible(WebElement element) {
		return element.isDisplayed() ? true : false;
	}

	/**
	 * Checks if element is visible Purpose:
	 * 
	 * @author: 
	 * @date:
	 * @returnType: By locator
	 */
	public boolean isElementVisible(By locator) {
		return driver.findElement(locator).isDisplayed() ? true : false;
	}

	/**
	 * 
	 * Purpose:Waits for element to be visible
	 * 
	 * @author: 
	 * @date:
	 * @returnType: WebElement
	 */
	public boolean waitForElementToBeVisible(By locator, int timeOut) {
		try{
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		WebElement element = wait.until(ExpectedConditions
				.visibilityOfElementLocated(locator));
		return IsElementVisible(element);
		}catch(Exception e){
			return false;
		}
	}

	/**
	 * 
	 * Purpose: Waits and matches the exact title
	 * 
	 * @author: 
	 * @date:
	 * @returnType: boolean
	 */
	public boolean IsTitleEqual(By locator, int timeOut, String title) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.titleIs(title));
	}

	/**
	 * Waits and matches if title contains the text Purpose:
	 * 
	 * @author: 
	 * @date:
	 * @returnType: boolean
	 */
	public boolean IsTitleContains(By locator, int timeOut, String title) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.titleContains(title));
	}

	/**
	 * Wait for element to be clickable Purpose:
	 * 
	 * @author: 
	 * @date:
	 * @returnType: WebElement
	 */
	public WebElement waitForElementToBeClickable(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		WebElement element = wait.until(ExpectedConditions
				.elementToBeClickable(locator));
		return element;
	}

	/**
	 * 
	 * Purpose:Wait for element to disappear
	 * 
	 * @author: 
	 * @date:
	 * @returnType: boolean
	 */
	public boolean waitForElementToBeInVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions
				.invisibilityOfElementLocated(locator));
	}

	public boolean waitForPageLoad() {
		boolean isLoaded = false;
		try {
			logger.info("Waiting For Page load via JS");
			ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return ((JavascriptExecutor) driver).executeScript(
							"return document.readyState").equals("complete");
				}
			};
			WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
			wait.until(pageLoadCondition);
			isLoaded = true;
		} catch (Exception e) {
			logger.error("Error Occured waiting for Page Load "
					+ driver.getCurrentUrl());
		}
		logger.info("page load complete..");
		return isLoaded;
	}

	public void selectFromList(List<WebElement> lstElementList,
			String sValueToBeSelected) throws Exception {
		logger.info("START OF FUNCTION->selectFromList");
		boolean flag = false;
		logger.info("Total element found --> " + lstElementList.size());
		logger.info("Value to be selected " + sValueToBeSelected + " from list"
				+ lstElementList);
		for (WebElement e : lstElementList) {
			logger.info(">>>>>>>>>>>>>" + e.getText() + "<<<<<<<<<<<<<<<");
			if (e.getText().equalsIgnoreCase(sValueToBeSelected)) {
				logger.info("Value matched in list as->" + e.getText());
				e.click();
				flag = true;
				break;
			}
		}
		if (flag == false) {
			throw new Exception("No match found in the list for value->"
					+ sValueToBeSelected);
		}
		logger.info("END OF FUNCTION->selectFromList");
	}

	public void selectByVisibleText(WebElement webElement, String visibleText,String element){
		Select select = new Select(webElement);
		select.selectByVisibleText(visibleText);
		logger.info("SELECTED "+visibleText+" from "+element+" dropdown");
	}

	public WebElement waitForElementToBeClickable(WebElement element,
			int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		return element;
	}

	/**
	 * 
	 * Purpose: Helps in case of Stalement Exception
	 * 
	 * @author: 
	 * @date:
	 * @returnType: boolean
	 */
	public boolean retryingFindClick(By by) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 5) {
			try {
				clickByJS(driver, by);
				result = true;
				break;
			} catch (Exception e) {
			}
			attempts++;
			pauseExecutionFor(1000);
		}
		return result;
	}

	public boolean deleteAllCookies(WebDriver driver) {
		boolean IsDeleted = false;
		try {
			driver.manage().deleteAllCookies();
			IsDeleted = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return IsDeleted;
	}

	public static void scrollToElement(WebDriver driver, WebElement element) {
		logger.info("Scrolling to Element");
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", element);
	}

	public void turnOffImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
	
	public void turnOffImplicitWaits(int seconds) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	public void turnOnImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	public void clickByJS(WebDriver driver, WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	public void clickByJS(WebDriver driver, WebElement webElement, String element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", webElement);
		logger.info("CLICKED(JS) on "+element );
	}

	public void clickByJS(WebDriver driver, By by) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", driver.findElement(by));
	}

	public static String takeSnapShotAndRetPath(WebDriver driver,String screenshotName,boolean isScreenshotFolderCreated ) throws Exception {
		String FullSnapShotFilePath = "";
		String methodName = TestListner.getMethodName();
		try {
			logger.info("Taking Screenshot");
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			String sFilename = null;
			if(isScreenshotFolderCreated==false){
				CommonUtils.createFolder("output\\Screenshots\\"+methodName);
			}
			sFilename = "\\output\\Screenshots\\"+methodName +"\\"+ screenshotName+".png";
			FullSnapShotFilePath = System.getProperty("user.dir")+sFilename;
			FileUtils.copyFile(scrFile, new File(FullSnapShotFilePath));
		} catch (Exception e) {

		}

		return FullSnapShotFilePath;
	}

	//	public static String takeSnapShotAndRetPath(WebDriver driver,Method methodName, String screenshotName) throws Exception {
	//		String FullSnapShotFilePath = "";
	//		try {
	//			logger.info("Taking Screenshot");
	//			File scrFile = ((TakesScreenshot) driver)
	//					.getScreenshotAs(OutputType.FILE);
	//			String sFilename = null;
	//			sFilename = "Screenshot-" +methodName+"_"+screenshotName + ".png";
	//			if(SoftAssert.isScreenshotFolderCreated()==false){
	//				CommonUtils.createFolder("output\\Screenshots\\"+methodName);
	//				sFilename="output\\Screenshots\\"+methodName+sFilename;
	//			}
	//			else{
	//				sFilename=	"\\output\\ScreenShots\\" + sFilename;
	//			}
	//			FullSnapShotFilePath = System.getProperty("user.dir")+sFilename;
	//			FileUtils.copyFile(scrFile, new File(FullSnapShotFilePath));
	//		} catch (Exception e) {
	//
	//		}
	//
	//		return FullSnapShotFilePath;
	//	}


	/**
	 * Returns current Date Time
	 * 
	 * @return
	 */
	public static String getDateTime() {
		String sDateTime = "";
		try {
			SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
			Date now = new Date();
			String strDate = sdfDate.format(now);
			String strTime = sdfTime.format(now);
			strTime = strTime.replace(":", "-");
			sDateTime = "D" + strDate + "_T" + strTime;
		} catch (Exception e) {
			System.err.println(e);
		}
		return sDateTime;
	}

	public String switchToSecondWindow(){
		Set<String> allWindows = driver.getWindowHandles();
		logger.info("total windows opened = "+allWindows.size());
		String parentWindow = driver.getWindowHandle();
		for(String allWin : allWindows){
			if(!allWin.equalsIgnoreCase(parentWindow)){
				driver.switchTo().window(allWin);
				break;
			}

		}
		logger.info("Switched to second window whose title is "+driver.getTitle());	
		return parentWindow;
	}

	public void switchToChildWindow(String parentWinHandle){
		Set<String> allWindows = driver.getWindowHandles();
		logger.info("total windows opened = "+allWindows.size());
		for(String allWin : allWindows){
			if(!allWin.equalsIgnoreCase(parentWinHandle)){
				driver.switchTo().window(allWin);
				break;
			}

		}
		logger.info("Switched to second window whose title is "+driver.getTitle());	

	}

	public String getCrmURL(){
		return propertyFile.getProperty("crmUrl");
	}

	public void moveToELement(By locator) {
		Actions build = new Actions(driver);
		build.moveToElement(driver.findElement(locator)).build().perform();
	}

	public void waitForExpectedURLPresent(String URL) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
			logger.info("waiting for URL " + URL);
			wait.until(ExpectedConditions.urlContains(URL));
			logger.info("Element found");
		} catch (Exception e) {
			e.getStackTrace();
		}  
	}

}

