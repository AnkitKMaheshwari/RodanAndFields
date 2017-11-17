package com.rf.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import com.rf.core.utils.PropertyFile;

/**
 * @author ShubhamMathur RFBasePage is the super class for all the page
 *         classes
 */
public class RFBasePage {
	public WebDriver webdriver;
	protected PropertyFile propertyFile;
	//protected RFBaseTest rfBaseTest;
	public RFBasePage(WebDriver driver) {
		//rfBaseTest = new RFBaseTest();
		webdriver = driver;
		propertyFile= new PropertyFile();
	}

	public WebDriver getWebdriver(){
		return webdriver;
	}

	public void highlightElement(WebElement element){
		WrapsDriver wrappedElement = (WrapsDriver) element;
		JavascriptExecutor js = (JavascriptExecutor)wrappedElement.getWrappedDriver();
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		for (int i = 0; i < 5; i++) {		
			js.executeScript("arguments[0].setAttribute('style',arguments[1]);",element, "color: yellow; border: 2px solid red;");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			js.executeScript("arguments[0].setAttribute('style',arguments[1]);",element, "");
		}
	}
	
	public boolean isMobile(){
		if(propertyFile.getProperty("device").equalsIgnoreCase("mobile")){
			return true;
		}		
		return false;
	}
}
