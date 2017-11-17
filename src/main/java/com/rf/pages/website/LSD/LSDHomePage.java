package com.rf.pages.website.LSD;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import com.rf.core.driver.website.RFWebsiteDriver;

public class LSDHomePage extends LSDRFWebsiteBasePage{

	private static final Logger logger = LogManager
			.getLogger(LSDHomePage.class.getName());

	public LSDHomePage(RFWebsiteDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	private static String selectDDValuesInSearchByDD= "//select[contains(@id,'OrderSearchField')]//option[contains(text(),'%s')]"; 
	private String subLinkUnderLinksLoc = "//a[contains(text(),'%s')]";
	private static String textLoc = "//div[@id='sub-stage']//*[contains(text(),'%s')]";
	private String subLinkUnderReportsSectionLoc = "//h3[contains(text(),'Reports')]/following::section[@id='%s']";

	private static final By MY_AUSTRALIA_LAUNCH_PAGE_LOC = By.xpath("//div[@class='login-block']/a[text()='LOG IN']");
	private static final By HOME_PAGE_OF_SOLUTION_TOOL_LOC = By.id("search_key"); 
	private static final By ESTIMATED_SV_LOC = By.xpath("//span[contains(@id,'estimated-sv')]");
	private static final By ESTIMATED_PSQV_LOC = By.xpath("//span[contains(@id,'estimated-psqv')]");
	private static final By MY_SPONSOR_SECTION_LOC = By.id("my-sponsor");
	private static final By ORGANIZATION_UPDATES_SECTION_LOC = By.id("organization-updates");
	private static final By WHAT_THESE_NUMBERS_MEAN_LINK_IN_ORG_UPDATES_SEC_LOC = By.xpath("//section[contains(@id,'organization-updates')]//span[contains(text(),'What these numbers mean')][1]/ancestor::a[1]");
	private static final By VIEW_TITLE_HISTORY_LOC = By.xpath("//span[contains(text(),'View Title History')]/ancestor::a[1]");
	private static final By VIEW_TITLE_HISTORY_DESC_LOC = By.xpath("//h1[contains(text(),'Title History')]/following-sibling::div[1]/p");
	private static final By TIMELINE_HEADING_LOC = By.xpath("//h5[contains(text(),'Timeline')]");
	private static final By LINKS_IN_TOP_NAV_LOC = By.xpath("//div[@id='rf-nav-wrapper']/descendant::span[text()='Links'][1]");
	private static final By RF_CONNECTION_PAGE_HEADER_LOC = By.xpath("//h1[contains(text(),'WELCOME TO THE RF CONNECTION')]");
	private static final By RF_JOURNEY_PAGE_HEADER_LOC = By.xpath("//h1[contains(text(),'R+F Journey')]");
	private static final By BUSINESS_CARD_LINK_LOC = By.xpath("//a[contains(text(),'Business Cards')]");
	private static final By RF_PAYDAY_WELCOME_MSG_LOC = By.xpath("//span[@class='landing-page-welcome-message']/span[contains(text(),'Payday')]");
	private static final By PULSE_SHARE_HEADING_LOC = By.xpath("//div[contains(text(),'PULSE SHARE')]");
	private static final By LOGIN_ID_TF_LOC = By.id("inputLoginID");
	private static final By LEAD_THE_WAY_LINK_IN_TOP_NAV_LOC = By.xpath("//div[@id='topmenu']//a[text()='Lead The Way']");
	private static final By LOGOUT_BTN = By.id("nav-logout") ;
	private static final By VIEW_MY_ORDERS_LINK = By.xpath("//span[text()='View my orders']") ;
	private static final By CUSTOMERS_LINK = By.id("nav-customers");
	private static final By ORDERS_LINK = By.id("nav-orders");
	private static final By FEEDBACK_LINK = By.id("nav-feedback");
	private static final By RF_CONSULTANT_EVENT_PAGE_LOC = By.xpath("//h1[text()='R+F CONSULTANT-LED EVENT CALENDER']");
	private static final By RF_GETTING_STARTED_PAGE_LOC = By.id("username");
	private static final By RF_CORPORATE_EVENT_PAGE_LOC = By.xpath("//h2[text()='Event Calendar']");
	private static final By RF_COMMS_CORNER_PAGE_LOC = By.id("username");
	private static final By PULSE_IN_TOP_NAV_LOC = By.xpath("//div[@id='rf-nav-wrapper']/descendant::span[text()='Pulse'][1]");
	private static final By NUMBER_OF_SV_LOC = By.id("sv");
	private static final By NUMBER_OF_PSQV_LOC = By.id("psqv");
	private static final By PULSE_MENU_IN_TOP_NAV_LOC = By.xpath("//div[@id='rf-nav-wrapper']/descendant::span[text()='Pulse'][1]");
	private static final By HOME_PAGE_OF_PULSE_LOC = By.xpath("//h4[text()='Qualification Title:']/preceding::div[text()='Hello,']");
	private static final By TEAM_PAGE_OF_PULSE_LOC = By.xpath("//h1[contains(text(),'Check In With Your Team')]");
	private static final By CUSTOMER_PAGE_OF_PULSE_LOC = By.xpath("//h1[contains(text(),'Build Customer Loyalty')]");
	private static final By ORDER_PAGE_OF_PULSE_LOC = By.xpath("//h3[contains(text(),'View orders for')]");
	private static final By HELLO_TEXT_OVERVIEW_SECTION_LOC = By.xpath("//div[text()='Hello,']");
	private static final By FIRST_NAME_OVERVIEW_SECTION_LOC = By.xpath("//div[text()='Hello,']/following-sibling::div");
	private static final By QUALIFICATION_TITLE_OVERVIEW_SECTION_LOC = By.xpath("//h4[text()='Qualification Title:']/following-sibling::div[1]");
	private static final By ENROLLMENT_DATE_OVERVIEW_SECTION_LOC = By.xpath("//h4[text()='Qualification Title:']/following-sibling::div[2]");
	private static final By PULSE_INFO_UNDER_KEEP_IN_MIND_LOC = By.xpath("//section[@id='keep-in-mind']/div[1]/span/span");
	private static final By CRP_INFO_UNDER_KEEP_IN_MIND_LOC = By.xpath("//section[@id='keep-in-mind']/div[2]/span/span");
	private static final By GRACE_BALANCE_INFO_UNDER_KEEP_IN_MIND_LOC = By.xpath("//section[@id='keep-in-mind']/div[3]/div[1]/span/span");
	private static final By ANNIVERSARY_MONTH_UNDER_KEEP_IN_MIND_LOC = By.xpath("//section[@id='keep-in-mind']/div[3]/div[2]/span");
	private static final By WHAT_THESE_NUMBERS_MEAN_LINK_LOC = By.xpath("//span[contains(@id,'estimated-sv')]/following::span[contains(text(),'What these numbers mean')][1]/ancestor::a[1]");
	private static final By SV_PSQV_DESC_LOC = By.xpath("//h1[contains(text(),'What these numbers mean')]/following-sibling::div[1]/p");
	private static final By NAME_INITIALS_LOC = By.xpath("//section[@id='my-sponsor']//div[contains(@class,'initials pull-right')]//span");
	private static final By SPONSOR_NAME_LOC = By.xpath("//section[@id='my-sponsor']//div[contains(@class,'initials pull-right')]/following::div[1]/span[1]");
	private static final By STATE_INITIALS_LOC = By.xpath("//section[@id='my-sponsor']//div[contains(@class,'initials pull-right')]/following::div[1]/span[2]");
	private static final By NAME_INITIALS_CONTAINER_LOC = By.xpath("//section[@id='my-sponsor']//div[contains(@class,'initials pull-right')]//span/..");
	private static final By EC_LEGS_AND_TITLES_SEC_LOC = By.id("ec-legs-titles");
	private static final By EC_LEGS_VALUE_LOC = By.xpath("//span[contains(text(),'EC Legs')]/../preceding-sibling::span[1]");
	private static final By ESTIMATED_DAYS_LEFT_FOR_COMM_LOC = By.xpath("//span[contains(text(),'commissions period')]/..");
	private static final By PROMOTED_CONSULTANT_TO_EC_LOC = By.xpath("//span[text()='have promoted to EC again.']/preceding::span[2]");
	private static final By CONSULTANTS_GETTING_THERE_LOC = By.xpath("//span[text()='are getting there.']/preceding::span[2]");
	private static final By NEW_ENROLLED_CONSULTANT_LOC = By.xpath("//h3[contains(text(),'View Your Team’s Progress')]/following::p[contains(text(),'New Consultants')]");

	private String organizationUpdatesNumberDescLoc = "//h1[contains(text(),'What these numbers mean')]/following-sibling::div[1]/p[contains(text(),'%s')]"; 
	private String organizationUpdatesInfoLoc = "//span[contains(text(),'%s')]/../preceding-sibling::span[1]";
	private String titleHistoryHeadingLoc = "//span[contains(text(),'%s')]";
	private String subLinkUnderMyPageSectionLoc = "//h3[contains(text(),'My Pages')]/following::section[@id='%s']";
	private String topNavigationDDMenuLoc = "//div[@id='rf-nav-wrapper']/descendant::span[text()='%s'][1]";

	public void clickViewMyOrdersLink(){
		driver.quickWaitForElementPresent(VIEW_MY_ORDERS_LINK);
		driver.click(VIEW_MY_ORDERS_LINK);
		driver.waitForLSDLoaderAnimationImageToDisappear();
	}

	public LSDCustomerPage clickCustomersLink(){
		driver.waitForElementPresent(CUSTOMERS_LINK);
		driver.click(CUSTOMERS_LINK);
		logger.info("Customers link clicked from main menu");
		driver.waitForLSDLoaderAnimationImageToDisappear();
		return new LSDCustomerPage(driver);
	}

	public LSDOrderPage clickOrdersLink(){
		driver.waitForElementPresent(ORDERS_LINK);
		driver.click(ORDERS_LINK);
		logger.info("Orders link clicked from main menu");
		driver.waitForLSDLoaderAnimationImageToDisappear();
		return new LSDOrderPage(driver);
	}
	public LSDFeedbackPage clickFeedbackLink(){
		driver.click(FEEDBACK_LINK);
		logger.info("Feedback link clicked from main menu");
		driver.pauseExecutionFor(2000);
		return new LSDFeedbackPage(driver);
	}


	public boolean isRFConsultantEventPagePresent(){
		driver.waitForElementToBeVisible(RF_CONSULTANT_EVENT_PAGE_LOC,20);
		return driver.IsElementVisible(driver.findElement(RF_CONSULTANT_EVENT_PAGE_LOC ));
	}

	public String getColorOfNumbersForSV(){
		String colorCode = driver.getAttribute(NUMBER_OF_SV_LOC, "style");
		logger.info("color code of SV is "+colorCode);
		return colorCode;
	}

	public String getColorOfNumbersForPSQV(){
		String colorCode = driver.getAttribute(NUMBER_OF_PSQV_LOC, "style");
		logger.info("color code of PSQV is "+colorCode);
		return colorCode;
	}

	public boolean verifySponsorSectionIsPresent(){
		return driver.isElementVisible(MY_SPONSOR_SECTION_LOC);
	}


	public boolean verifyOrganizationUpdatesSectionIsPresent(){
		return driver.isElementVisible(ORGANIZATION_UPDATES_SECTION_LOC);
	}


	public void clickWhatTheseNumbersMeanLink(){
		driver.click(WHAT_THESE_NUMBERS_MEAN_LINK_LOC);
		logger.info("Clicked on What these Numbers mean Link");
	}

	public String getSVAndPSQVDescription(){
		return driver.getText(SV_PSQV_DESC_LOC);
	}

	public void clickWhatTheseNumbersMeanLinkInOrgUpdatesSection(){
		driver.click(WHAT_THESE_NUMBERS_MEAN_LINK_IN_ORG_UPDATES_SEC_LOC);
		logger.info("Clicked on What these Numbers mean Link in Org Updates Section");
	}

	public boolean verifyOrgainizationUpdatesNumbersDescIsPresent(String desc){
		return driver.isElementVisible(By.xpath(String.format(organizationUpdatesNumberDescLoc,desc)));
	}

	public boolean verifySponsorNameIsPresentInBoldLetters(){
		return driver.getAttribute(SPONSOR_NAME_LOC,"class").contains("weight-medium");
	}

	public boolean verifyStateInitialsIsPresentInLightFont(){
		return driver.getAttribute(STATE_INITIALS_LOC,"class").contains("weight-light");
	}

	public boolean verifyNameInitialsIsPresent(){
		driver.waitForElementToBeVisible(NAME_INITIALS_LOC,10);
		if(driver.isElementVisible(NAME_INITIALS_LOC)){
			driver.pauseExecutionFor(3000);
			return !driver.getText(NAME_INITIALS_LOC).isEmpty(); 
		}
		return false;
	}

	public boolean verifyNameInitialsArePresentInCircleContainer(){
		return driver.getAttribute(NAME_INITIALS_CONTAINER_LOC,"class").contains("circle");
	}

	public boolean verifySponsorNameIsPresent(){
		driver.waitForElementToBeVisible(SPONSOR_NAME_LOC,10);
		if(driver.isElementVisible(SPONSOR_NAME_LOC)){
			driver.pauseExecutionFor(3000);
			return !driver.getText(SPONSOR_NAME_LOC).isEmpty(); 
		}
		return false;
	}

	public boolean verifyStateAndCountryDetailsArePresent(){
		driver.waitForElementToBeVisible(STATE_INITIALS_LOC,10);
		if(driver.isElementVisible(STATE_INITIALS_LOC)){
			driver.pauseExecutionFor(3000);
			return !driver.getText(STATE_INITIALS_LOC).isEmpty(); 
		}
		return false;
	}

	public void clickViewTitleHistoryLink(){
		driver.click(VIEW_TITLE_HISTORY_LOC);
		logger.info("Clicked View Title History Link");
	}

	public boolean verifyViewTitleHistoryDescIsPresent(){
		if(driver.isElementVisible(VIEW_TITLE_HISTORY_DESC_LOC)){
			driver.pauseExecutionFor(3000);
			return !driver.getText(VIEW_TITLE_HISTORY_DESC_LOC).isEmpty();
		}
		return false;
	}

	public boolean verifyInfoOnViewTitleHistoryPage(String heading){
		return driver.isElementVisible(By.xpath(String.format(titleHistoryHeadingLoc,heading)));
	}

	public boolean verifyTimelineHeaderIsPresent(){
		return driver.isElementVisible(TIMELINE_HEADING_LOC);
	}

	public boolean isLinkPresentUnderMyPageSection(String linkName){
		driver.waitForElementToBeVisible(By.xpath(String.format(subLinkUnderMyPageSectionLoc,linkName)),5);
		return driver.IsElementVisible(driver.findElement(By.xpath(String.format(subLinkUnderMyPageSectionLoc, linkName)) ));
	}

	public void clickLinkUnderMyPageSection(String linkName){
		driver.quickWaitForElementPresent(By.xpath(String.format(subLinkUnderMyPageSectionLoc,linkName)));
		driver.click(By.xpath(String.format(subLinkUnderMyPageSectionLoc, linkName)));
		logger.info("Link "+linkName+" clicked under my page section");
	}

	public boolean isTopNavigationHeaderDDMenuPresent(String menuName){
		driver.waitForElementToBeVisible(By.xpath(String.format(topNavigationDDMenuLoc,menuName)),5);
		return driver.IsElementVisible(driver.findElement(By.xpath(String.format(topNavigationDDMenuLoc, menuName))));
	}

	public void selectSubMenuFromPulseMenu(String subLink){
		driver.click(PULSE_MENU_IN_TOP_NAV_LOC);
		logger.info("Clicked on Pulse Submenu from Top Navigation");
		driver.waitForElementToBeVisible(By.xpath(String.format(subLinkUnderLinksLoc,subLink)),10);
		driver.click(By.xpath(String.format(subLinkUnderLinksLoc,subLink)));
		logger.info("SubMenu Link : " + subLink+ " clicked");
	}

	public boolean isHomePageOfPulsePresent(){
		driver.waitForElementToBeVisible(HOME_PAGE_OF_PULSE_LOC,5);
		return driver.IsElementVisible(driver.findElement(HOME_PAGE_OF_PULSE_LOC));
	}
	public boolean isTeamPageOfPulsePresent(){
		driver.waitForElementToBeVisible(TEAM_PAGE_OF_PULSE_LOC,5);
		return driver.IsElementVisible(driver.findElement(TEAM_PAGE_OF_PULSE_LOC));
	}

	public boolean isCustomerPageOfPulsePresent(){
		driver.waitForElementToBeVisible(CUSTOMER_PAGE_OF_PULSE_LOC,5);
		return driver.IsElementVisible(driver.findElement(CUSTOMER_PAGE_OF_PULSE_LOC));
	}

	public boolean isOrderPageOfPulsePresent(){
		driver.waitForElementToBeVisible(ORDER_PAGE_OF_PULSE_LOC,5);
		return driver.IsElementVisible(driver.findElement(ORDER_PAGE_OF_PULSE_LOC));
	}

	public boolean isHelloTextPresentInOverviewSection(){
		driver.waitForElementToBeVisible(HELLO_TEXT_OVERVIEW_SECTION_LOC,5);
		return driver.IsElementVisible(driver.findElement(HELLO_TEXT_OVERVIEW_SECTION_LOC));
	}

	public boolean isFirstNamePresentInOverviewSection(){
		driver.waitForElementToBeVisible(FIRST_NAME_OVERVIEW_SECTION_LOC,5);
		return !(driver.findElement(FIRST_NAME_OVERVIEW_SECTION_LOC).getText().isEmpty());
	}

	public boolean isQualificationtitlePresentInOverviewSection(){
		driver.waitForElementToBeVisible(QUALIFICATION_TITLE_OVERVIEW_SECTION_LOC,5);
		return !(driver.findElement(QUALIFICATION_TITLE_OVERVIEW_SECTION_LOC).getText().isEmpty());
	}

	public boolean isEnrollmentDatePresentInOverviewSection(){
		driver.waitForElementToBeVisible(ENROLLMENT_DATE_OVERVIEW_SECTION_LOC,5);
		return driver.findElement(ENROLLMENT_DATE_OVERVIEW_SECTION_LOC).getText().contains("Enrolled:");
	}

	public String getPulseProInfoFromKeepInMindSection(){
		driver.waitForElementToBeVisible(PULSE_INFO_UNDER_KEEP_IN_MIND_LOC,5);
		String pulseStatus = driver.findElement(PULSE_INFO_UNDER_KEEP_IN_MIND_LOC).getText();
		return pulseStatus;
	}

	public String getCRPInfoFromKeepInMindSection(){
		driver.waitForElementToBeVisible(CRP_INFO_UNDER_KEEP_IN_MIND_LOC,5);
		String crpStatus = driver.findElement(PULSE_INFO_UNDER_KEEP_IN_MIND_LOC).getText();
		return crpStatus;
	}

	public String getGraceBalanceInfoFromKeepInMindSection(){
		driver.waitForElementToBeVisible(GRACE_BALANCE_INFO_UNDER_KEEP_IN_MIND_LOC,5);
		return driver.findElement(GRACE_BALANCE_INFO_UNDER_KEEP_IN_MIND_LOC).getText();
	}

	public boolean isAnniversaryMonthPresentUnderKeepInMindSection(){
		driver.waitForElementToBeVisible(ANNIVERSARY_MONTH_UNDER_KEEP_IN_MIND_LOC,5);
		return driver.IsElementVisible(driver.findElement(ANNIVERSARY_MONTH_UNDER_KEEP_IN_MIND_LOC));
	}

	public boolean isSVAndPSQVDescriptionPresent(){
		driver.waitForElementToBeVisible(SV_PSQV_DESC_LOC,10);
		return driver.isElementVisible(SV_PSQV_DESC_LOC);
	}

	public boolean isRFConnectionPageHeaderPresent(){
		driver.waitForElementToBeVisible(RF_CONNECTION_PAGE_HEADER_LOC,20);
		return driver.IsElementVisible(driver.findElement(RF_CONNECTION_PAGE_HEADER_LOC ));
	}

	public boolean isRFJourneyPageHeaderPresent(){
		driver.waitForElementToBeVisible(RF_JOURNEY_PAGE_HEADER_LOC,20);
		return driver.IsElementVisible(driver.findElement(RF_JOURNEY_PAGE_HEADER_LOC));
	}

	public boolean isBusinessCardLinkPresentOnRFMallPage(){
		driver.waitForElementToBeVisible(BUSINESS_CARD_LINK_LOC,20);
		return driver.IsElementVisible(driver.findElement(BUSINESS_CARD_LINK_LOC));
	}

	public boolean isWelcomeMsgPresentOnRFPaydayPage(){
		driver.waitForElementToBeVisible(RF_PAYDAY_WELCOME_MSG_LOC,20);
		return driver.IsElementVisible(driver.findElement(RF_PAYDAY_WELCOME_MSG_LOC));
	}

	public boolean isPulseShareHeadingPresentOnSharePage(){
		driver.waitForElementToBeVisible(PULSE_SHARE_HEADING_LOC,20);
		return driver.IsElementVisible(driver.findElement(PULSE_SHARE_HEADING_LOC));
	}

	public boolean isLoginIdTFPresentOnSharePage(){
		driver.waitForElementToBeVisible(LOGIN_ID_TF_LOC,20);
		return driver.IsElementVisible(driver.findElement(LOGIN_ID_TF_LOC));
	}

	public boolean isLeadTheWayLinkPresentInTopNav(){
		driver.waitForElementToBeVisible(LEAD_THE_WAY_LINK_IN_TOP_NAV_LOC,20);
		return driver.IsElementVisible(driver.findElement(LEAD_THE_WAY_LINK_IN_TOP_NAV_LOC));
	}

	public boolean isRFGettingStartedPagePresent(){
		driver.waitForElementToBeVisible(RF_GETTING_STARTED_PAGE_LOC,20);
		return driver.IsElementVisible(driver.findElement(RF_GETTING_STARTED_PAGE_LOC ));
	}

	public boolean isRFCorporateEventPagePresent(){
		driver.waitForElementToBeVisible(RF_CORPORATE_EVENT_PAGE_LOC,20);
		return driver.IsElementVisible(driver.findElement(RF_CORPORATE_EVENT_PAGE_LOC ));
	}

	public boolean isRFCommsCornerPagePresent(){
		driver.waitForElementToBeVisible(RF_COMMS_CORNER_PAGE_LOC,20);
		return driver.IsElementVisible(driver.findElement(RF_COMMS_CORNER_PAGE_LOC ));
	}

	public void selectSubMenuFromLinksMenu(String subLink){
		driver.moveToELement(LINKS_IN_TOP_NAV_LOC);
		logger.info("Mouse Hover on Links Submenu from Top Navigation");
		driver.waitForElementToBeVisible(By.xpath(String.format(subLinkUnderLinksLoc,subLink)),10);
		driver.click(By.xpath(String.format(subLinkUnderLinksLoc,subLink)));
		logger.info("SubMenu Link : " + subLink+ " clicked");
	}

	public void clickLogout(){
		driver.pauseExecutionFor(10000);
		driver.movetToElementJavascript(PULSE_IN_TOP_NAV_LOC);
		logger.info("Mouse Hover on Pulse Section from Top Navigation");
		//driver.waitForElementToBeVisible(LOGOUT_BTN, 10);
		driver.clickByJS(RFWebsiteDriver.driver, driver.findElement(LOGOUT_BTN));
		driver.waitForLSDJustAMomentImageToDisappear();
	}

	public boolean isECLegsAndTitlesSectionPresent(){
		return driver.waitForElementToBeVisible(EC_LEGS_AND_TITLES_SEC_LOC,10);
	}

	public String getECLegsValue(){
		driver.pauseExecutionFor(5000);
		return driver.getText(EC_LEGS_VALUE_LOC).trim();
	}

	public String getOrgainzationUpdatesInfo(String info){
		driver.pauseExecutionFor(7000);
		return driver.getText(By.xpath(String.format(organizationUpdatesInfoLoc,info))).trim();
	}

	public void clickEstimatedSV(){
		driver.waitForElementPresent(ESTIMATED_SV_LOC);
		driver.click(ESTIMATED_SV_LOC);
		logger.info("Clicked Estimated SV");
	}

	public String getEstimatedSV(){
		driver.waitForElementPresent(ESTIMATED_SV_LOC);
		String SV = driver.getText(ESTIMATED_SV_LOC).replaceAll(",","");
		logger.info("Estimated SV is "+SV);
		return SV;
	}

	public boolean isTextPresentAtEstimatedPage(String text){
		return driver.isElementPresent(By.xpath(String.format(textLoc, text)));
	}

	public void clickEstimatedPSQV(){
		driver.waitForElementPresent(ESTIMATED_PSQV_LOC);
		driver.click(ESTIMATED_PSQV_LOC);
		logger.info("Clicked Estimated PSQV");
	}

	public String getEstimatedPSQV(){
		driver.waitForElementPresent(ESTIMATED_PSQV_LOC);
		String PSQV = driver.getText(ESTIMATED_PSQV_LOC).replaceAll(",","");
		logger.info("Estimated PSQV is "+PSQV);
		return PSQV;
	}

	public boolean isMyAustraliaLaunchPagePresent(){
		driver.waitForElementToBeVisible(MY_AUSTRALIA_LAUNCH_PAGE_LOC,5);
		return driver.IsElementVisible(driver.findElement(MY_AUSTRALIA_LAUNCH_PAGE_LOC));
	}
	public boolean isLinkPresentUnderReportsSection(String linkName){
		driver.waitForElementToBeVisible(By.xpath(String.format(subLinkUnderReportsSectionLoc,linkName)),5);
		return driver.IsElementVisible(driver.findElement(By.xpath(String.format(subLinkUnderReportsSectionLoc, linkName)) ));
	}

	public void clickLinkUnderReportsSection(String linkName){
		driver.quickWaitForElementPresent(By.xpath(String.format(subLinkUnderReportsSectionLoc,linkName)));
		driver.click(By.xpath(String.format(subLinkUnderReportsSectionLoc, linkName)));
		logger.info("Link "+linkName+" clicked under Reports section");
	}
	public boolean isLoginPageOfSolutionToolPresent(){
		driver.waitForElementToBeVisible(HOME_PAGE_OF_SOLUTION_TOOL_LOC,5);
		return driver.IsElementVisible(driver.findElement(HOME_PAGE_OF_SOLUTION_TOOL_LOC));
	}

	public String getEstimatedDaysLeftForCommission(){
		driver.waitForElementPresent(ESTIMATED_DAYS_LEFT_FOR_COMM_LOC);
		String EstimatedDays = driver.getText(ESTIMATED_DAYS_LEFT_FOR_COMM_LOC);
		logger.info("Estimated Days left for commission period "+EstimatedDays);
		return EstimatedDays.toLowerCase();
	}
	public String getCountOfConsultantPromotedToEC(){
		driver.pauseExecutionFor(2000);
		driver.waitForElementPresent(PROMOTED_CONSULTANT_TO_EC_LOC);
		String promotedConsultant = driver.getText(PROMOTED_CONSULTANT_TO_EC_LOC);
		logger.info("Count Of promoted consultant to EC : "+promotedConsultant);
		return promotedConsultant;
	}
	public String getCountOfConsultantGettingThere(){
		driver.pauseExecutionFor(2000);
		driver.waitForElementPresent(CONSULTANTS_GETTING_THERE_LOC);
		String consultantGettingThere = driver.getText(CONSULTANTS_GETTING_THERE_LOC);
		logger.info("Count Of consultant who doesn't met EC criteria : "+consultantGettingThere);
		return consultantGettingThere;
	}
	public String getCountOfNewEnrolledConsultants(){
		driver.pauseExecutionFor(2000);
		driver.waitForElementPresent(NEW_ENROLLED_CONSULTANT_LOC);
		String newlyEnrolledConsultant = driver.getText(NEW_ENROLLED_CONSULTANT_LOC);
		logger.info("Count Of newly enrolled consultant : "+newlyEnrolledConsultant);
		return newlyEnrolledConsultant;
	}
}