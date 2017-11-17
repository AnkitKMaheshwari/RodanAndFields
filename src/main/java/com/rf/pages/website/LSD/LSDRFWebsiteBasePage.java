package com.rf.pages.website.LSD;

import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rf.core.driver.website.RFWebsiteDriver;
import com.rf.pages.RFBasePage;

public class LSDRFWebsiteBasePage extends RFBasePage{

	private static final Logger logger = LogManager
			.getLogger(LSDRFWebsiteBasePage.class.getName());

	protected RFWebsiteDriver driver;
	public LSDRFWebsiteBasePage(RFWebsiteDriver driver) {
		super(driver);
		this.driver = driver;
	}	

	public String getCurrentURL(){
		return driver.getCurrentUrl();
	}

	public void navigateToHomePage(){
		if(driver.getCurrentUrl().contains("home")==false){
			driver.get(driver.getURL()+"/#/home");
			driver.waitForLSDLoaderAnimationImageToDisappear();
			driver.pauseExecutionFor(2000);
		}	

	}

	public String getCurrentPSTDate(){
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
		df.setTimeZone(TimeZone.getTimeZone("PST"));
		final String dateString = df.format(new Date());
		String[] datePST = dateString.split(" ");
		System.out.println(dateString);
		String splittedDateForMonth = datePST[1]+" "+datePST[2]+" "+datePST[3];
		return splittedDateForMonth;
	}

	/***
	 * This method switch the window
	 * 
	 * @param parent
	 *            window handle
	 * @return LSDRFWebsiteBasePage object
	 * 
	 */
	public LSDRFWebsiteBasePage switchToParentWindow(String parentWindowID) {
		driver.close();
		driver.switchTo().window(parentWindowID);
		logger.info("Switched to parent window");
		return this;
	}

	/***
	 * This method switch the window from parent to child
	 * 
	 * @param parent
	 *            window handle
	 * @return LSDRFWebsiteBasePage object
	 * 
	 */
	public LSDRFWebsiteBasePage switchToChildWindow(String parentWindowID) {
		driver.pauseExecutionFor(1000);
		Set<String> set = driver.getWindowHandles();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String childWindowID = it.next();
			if (!parentWindowID.equalsIgnoreCase(childWindowID)) {
				driver.switchTo().window(childWindowID);
				logger.info("Switched to child window");
			}
		}
		driver.pauseExecutionFor(2000);
		driver.waitForPageLoad();
		return this;
	}

	public String getCurrentMonthFromCurrentPSTDate(){
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
		df.setTimeZone(TimeZone.getTimeZone("PST"));
		final String dateString = df.format(new Date());
		String[] datePST = dateString.split(" ");
		System.out.println(dateString);
		String splittedMonthFromDate = datePST[1];
		return splittedMonthFromDate;
	}
}