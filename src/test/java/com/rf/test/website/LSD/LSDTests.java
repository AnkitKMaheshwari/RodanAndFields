package com.rf.test.website.LSD;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.testng.annotations.Test;
import com.rf.core.utils.CommonUtils;
import com.rf.core.website.constants.TestConstants;
import com.rf.test.website.RFLSDWebsiteBaseTest;

public class LSDTests extends RFLSDWebsiteBaseTest{

	// TC-1455 Link - Lead The Way
	@Test(priority=1)
	public void testVerifyLeadTheWayLink_1455(){
		String currentUrl = null;
		String urlToAssert = "leadtheway";
		String parentWindowHandle = null;
		parentWindowHandle = CommonUtils.getCurrentWindowHandle();
		lsdHomePage.selectSubMenuFromLinksMenu("Lead the Way");
		lsdHomePage.switchToChildWindow(parentWindowHandle);
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isLeadTheWayLinkPresentInTopNav(),"Lead the Way Link is not Present");
		lsdHomePage.switchToParentWindow(parentWindowHandle);
		s_assert.assertAll();
	}

	// TC-1456 Link - RF Connection
	@Test(priority=2)
	public void testVerifyRFConnectionLink_1456(){
		String currentUrl = null;
		String urlToAssert = "rfconnection";
		String parentWindowHandle = null;
		parentWindowHandle = CommonUtils.getCurrentWindowHandle();
		lsdHomePage.selectSubMenuFromLinksMenu("RF Connection");
		lsdHomePage.switchToChildWindow(parentWindowHandle);
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isRFConnectionPageHeaderPresent(),"RF Connection Page Header is not Present");
		lsdHomePage.switchToParentWindow(parentWindowHandle);
		s_assert.assertAll();
	}

	// TC-1457 Link - RF Journey
	@Test(priority=3)
	public void testVerifyRFJourneyLink_1457(){
		String currentUrl = null;
		String urlToAssert = "randfjourney";
		String parentWindowHandle = null;
		parentWindowHandle = CommonUtils.getCurrentWindowHandle();
		lsdHomePage.selectSubMenuFromLinksMenu("RF Journey");
		lsdHomePage.switchToChildWindow(parentWindowHandle);
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isRFJourneyPageHeaderPresent(),"RF Journey Page Header is not Present");
		lsdHomePage.switchToParentWindow(parentWindowHandle);
		s_assert.assertAll();
	}

	// TC-1458 Link - RF Mall
	@Test(priority=4)
	public void testVerifyRFMallLink_1458(){
		String currentUrl = null;
		String urlToAssert = "dsa-direct";
		String parentWindowHandle = null;
		parentWindowHandle = CommonUtils.getCurrentWindowHandle();
		lsdHomePage.selectSubMenuFromLinksMenu("RF Mall");
		lsdHomePage.switchToChildWindow(parentWindowHandle);
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isBusinessCardLinkPresentOnRFMallPage(),"Expected Business Link is not present on RF Mall Page");
		lsdHomePage.switchToParentWindow(parentWindowHandle);
		s_assert.assertAll();
	}

	// TC-1459 Link - RF Pay Day
	@Test(priority=5)
	public void testVerifyRFPaydayLink_1459(){
		String currentUrl = null;
		String urlToAssert = "payday";
		String parentWindowHandle = null;
		parentWindowHandle = CommonUtils.getCurrentWindowHandle();
		lsdHomePage.selectSubMenuFromLinksMenu("RF Payday");
		lsdHomePage.switchToChildWindow(parentWindowHandle);
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isWelcomeMsgPresentOnRFPaydayPage(),"Welcome Msg is not present on RF Payday Page");
		lsdHomePage.switchToParentWindow(parentWindowHandle);
		s_assert.assertAll();
	}

	// TC-1460 Link - Share
	@Test(priority=6)
	public void testVerifyShareLink_1460(){
		String currentUrl = null;
		String urlToAssert = "share";
		String parentWindowHandle = null;
		parentWindowHandle = CommonUtils.getCurrentWindowHandle();
		lsdHomePage.selectSubMenuFromLinksMenu("Share");
		lsdHomePage.switchToChildWindow(parentWindowHandle);
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isPulseShareHeadingPresentOnSharePage(),"Pulse Share Heading is not present on Share Page");
		s_assert.assertTrue(lsdHomePage.isLoginIdTFPresentOnSharePage(),"Login Id Text Field is not present on Share Page");
		lsdHomePage.switchToParentWindow(parentWindowHandle);
		s_assert.assertAll();
	}

	//TC-1447 Link - Comms Corner
	@Test(enabled=false,priority=7)//Link not working
	public void testVerifyLinkCommsCorner_1447(){
		String currentUrl = null;
		String urlToAssert = "market.php";
		String parentWindowHandle = null;
		parentWindowHandle = CommonUtils.getCurrentWindowHandle();
		lsdHomePage.selectSubMenuFromLinksMenu("Comms Corner");
		lsdHomePage.switchToChildWindow(parentWindowHandle);
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		//s_assert.assertTrue(lsdHomePage.isRFCommsCornerPagePresent(),"RF Comms corner page is not Present");
		lsdHomePage.switchToParentWindow(parentWindowHandle);
		s_assert.assertAll();
	}

	//TC-1448 Link - Consultant - LED Events
	@Test(priority=8)
	public void testVerifyLinkConsultantLEDEvents_1448(){
		String currentUrl = null;
		String urlToAssert = "rfconsultantevents";
		String parentWindowHandle = null;
		parentWindowHandle = CommonUtils.getCurrentWindowHandle();
		lsdHomePage.selectSubMenuFromLinksMenu("Consultant - Led Events");
		lsdHomePage.switchToChildWindow(parentWindowHandle);
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isRFConsultantEventPagePresent(),"RF Consultant led event page is not Present");
		lsdHomePage.switchToParentWindow(parentWindowHandle);
		s_assert.assertAll();
	}

	//TC-1449 Link - Corporate Events 
	@Test(priority=9)
	public void testVerifyLinkCorporateEvents_1449(){
		String currentUrl = null;
		String urlToAssert = "eventid";
		String parentWindowHandle = null;
		parentWindowHandle = CommonUtils.getCurrentWindowHandle();
		lsdHomePage.selectSubMenuFromLinksMenu("Corporate events");
		lsdHomePage.switchToChildWindow(parentWindowHandle);
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isRFCorporateEventPagePresent(),"RF Corporate event page is not Present");
		lsdHomePage.switchToParentWindow(parentWindowHandle);
		s_assert.assertAll();
	}

	//TC-1450 Link - Getting Started
	@Test(priority=10)
	public void testVerifyLinkGettingStarted_1450(){
		String currentUrl = null;
		String urlToAssert = "getstarted";
		String parentWindowHandle = null;
		parentWindowHandle = CommonUtils.getCurrentWindowHandle();
		lsdHomePage.selectSubMenuFromLinksMenu("Getting Started");
		lsdHomePage.switchToChildWindow(parentWindowHandle);
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isRFGettingStartedPagePresent(),"RF Getting started Page is not Present");
		lsdHomePage.switchToParentWindow(parentWindowHandle);
		s_assert.assertAll();
	}

	//TC-1451 Link - Insider Scoop
	@Test(priority=11)
	public void testVerifyLinkInsiderScoop_1451() throws InvalidPasswordException, IOException{
		String currentUrl = null;
		String urlToAssert = "Insider_Scoop.pdf";
		String parentWindowHandle = null;
		String pdfContent = null;
		String textToAssertInPdfContent = "THE INSIDER SCOOP";
		parentWindowHandle = CommonUtils.getCurrentWindowHandle();
		lsdHomePage.selectSubMenuFromLinksMenu("Insider Scoop");
		lsdHomePage.switchToChildWindow(parentWindowHandle);
		currentUrl = lsdHomePage.getCurrentURL();
		pdfContent = CommonUtils.getContentsOfPDF(currentUrl);
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(pdfContent.contains(textToAssertInPdfContent), "Required Text is not present in PDF. Expected is: THE INSIDER SCOOP");
		lsdHomePage.switchToParentWindow(parentWindowHandle);
		s_assert.assertAll();
	}

	// TC-1461 Logout
	@Test(priority=12)
	public void testVerifyLogout_1461(){
		String currentUrl = null;
		String urlToAssert = "login";
		lsdHomePage.clickLogout();
		s_assert.assertTrue(lsdLoginPage.isLoginBtnPresent(),"Login Btn is not present after Logout");
		currentUrl = lsdLoginPage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertAll();
	}

	//TC-1474 Home - Current Progress - Text
	@Test(enabled=false)
	public void testCurrentProgressText(){
		String colorOfSV = "rgb(169, 220, 163)";
		String colorOfPSQV = "rgb(84, 185, 72)";
		String colorOfSVFromUI = null;
		String colorOfPSQVFromUI = null;
		colorOfSVFromUI = lsdHomePage.getColorOfNumbersForSV();
		s_assert.assertTrue(colorOfSVFromUI.contains(colorOfSV), "Expected color of number for SV is "+colorOfSV+" but actual on UI is"+colorOfSVFromUI);
		colorOfPSQVFromUI = lsdHomePage.getColorOfNumbersForPSQV();
		s_assert.assertTrue(colorOfPSQVFromUI.contains(colorOfPSQV), "Expected color of number for PSQV is "+colorOfPSQV+" but actual on UI is"+colorOfPSQVFromUI);
		s_assert.assertAll();
	}

	//TC-1481 Home - Organization Updates
	@Test
	public void testOrganizationUpdates_1481(){
		String valueOnUI = null;
		String expectedValue = null;
		s_assert.assertTrue(lsdHomePage.verifyOrganizationUpdatesSectionIsPresent(),"Organization Updates section is not present");
		valueOnUI = lsdHomePage.getOrgainzationUpdatesInfo(TestConstants.ORG_UPDATES_PC_INFO_HEADING);
		expectedValue = TestConstants.ORG_UPDATES_PC_INFO_VALUE;
		s_assert.assertTrue(expectedValue.equals(valueOnUI),"Organization Updates PC Info is not present as expected.Actual : "+valueOnUI+". Expected : "+expectedValue);
		valueOnUI = lsdHomePage.getOrgainzationUpdatesInfo(TestConstants.ORG_UPDATES_CONSULTANT_INFO_HEADING);
		expectedValue = TestConstants.ORG_UPDATES_CONSULTANT_INFO_VALUE;
		s_assert.assertTrue(expectedValue.equals(valueOnUI),"Organization Updates Cons Info is not present as expected.Actual : "+valueOnUI+". Expected : "+expectedValue);
		valueOnUI = lsdHomePage.getOrgainzationUpdatesInfo(TestConstants.ORG_UPDATES_L1_PLUS_L2_INFO_HEADING);
		expectedValue = TestConstants.ORG_UPDATES_L1_PLUS_L2_INFO_VALUE;
		s_assert.assertTrue(expectedValue.equals(valueOnUI),"Organization Updates L1+L2 Info is not present as expected.Actual : "+valueOnUI+". Expected : "+expectedValue);
		valueOnUI = lsdHomePage.getOrgainzationUpdatesInfo(TestConstants.ORG_UPDATES_L1_L6_INFO_HEADING);
		expectedValue = TestConstants.ORG_UPDATES_L1_L6_INFO_VALUE;
		s_assert.assertTrue(expectedValue.equals(valueOnUI),"Organization Updates L1_L6 Info is not present as expected.Actual : "+valueOnUI+". Expected : "+expectedValue);
		lsdHomePage.clickWhatTheseNumbersMeanLinkInOrgUpdatesSection();
		String expectedDesc = TestConstants.ORG_UPDATES_DESC;
		s_assert.assertTrue(lsdHomePage.verifyOrgainizationUpdatesNumbersDescIsPresent(expectedDesc),"Organization Updates Numbers Desc is not present");
		s_assert.assertAll();

	}

	// TC-1482 Home - My Sponsor - Enrollment & Performance Sponsor are same
	@Test
	public void testMySponsorEnrollmentAndPerformanceSponsorAreSame_1482(){
		s_assert.assertTrue(lsdHomePage.verifySponsorSectionIsPresent(),"Sponsor Section is not present");
		s_assert.assertTrue(lsdHomePage.verifySponsorNameIsPresent(),"Sponsor Name is not present");
		s_assert.assertTrue(lsdHomePage.verifySponsorNameIsPresentInBoldLetters(),"Sponsor Name is not present in Bold Letters");
		s_assert.assertTrue(lsdHomePage.verifyStateAndCountryDetailsArePresent(),"State initials and country abbreviations are not present");
		s_assert.assertTrue(lsdHomePage.verifyStateInitialsIsPresentInLightFont(),"State initials and country abbreviations are not present in Light Font");
		s_assert.assertTrue(lsdHomePage.verifyNameInitialsIsPresent(),"Name Initials is not present");
		s_assert.assertTrue(lsdHomePage.verifyNameInitialsArePresentInCircleContainer(),"Name Initials is not present in Circular container");
		s_assert.assertAll();
	}

	// TC-1479 Home - EC LEGS and TITLES - VIEW TITLE HISTORY
	@Test
	public void testECLegsAndTitlesViewTitleHistory_1479(){
		lsdHomePage.clickViewTitleHistoryLink();
		s_assert.assertTrue(lsdHomePage.verifyViewTitleHistoryDescIsPresent(),"View Title History Description is not present");
		s_assert.assertTrue(lsdHomePage.verifyInfoOnViewTitleHistoryPage("Qualification Title"),"Qualification Title is not present on View Title History Page");
		s_assert.assertTrue(lsdHomePage.verifyInfoOnViewTitleHistoryPage("Paid-as Title"),"Paid-as Title is not present on View Title History Page");
		s_assert.assertTrue(lsdHomePage.verifyInfoOnViewTitleHistoryPage("Recognition Title"),"Recognition Title is not present on View Title History Page");
		s_assert.assertTrue(lsdHomePage.verifyInfoOnViewTitleHistoryPage("Highest Title"),"Highest Title is not present on View Title History Page");
		s_assert.assertTrue(lsdHomePage.verifyTimelineHeaderIsPresent(),"Timeline Header is not present on View Title History Page");
		s_assert.assertAll();
	}

	// TC-1484 Home - My Pages - Earnings Statements
	@Test(enabled=false)//Earning statement link navigating to invalid credential page
	public void testVerifyMyPageEarningStatements_1484(){
		String currentUrl = null;
		String urlToAssert = "ReportViewer";
		String parentWindowHandle = null;
		String linkName="earnings-statement";

		s_assert.assertTrue(lsdHomePage.isLinkPresentUnderMyPageSection(linkName),"Link "+linkName+" is not present under My page section");
		parentWindowHandle = CommonUtils.getCurrentWindowHandle();
		lsdHomePage.clickLinkUnderMyPageSection(linkName);
		lsdHomePage.switchToChildWindow(parentWindowHandle);
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertAll();
	}

	// TC-1486 Home - My Pages - Downline
	@Test(enabled=false)//Downline link not present under My Page section
	public void testVerifyMyPageDownLineLink_1486(){
		String currentUrl = null;
		String urlToAssert = "";
		String parentWindowHandle = null;
		String linkName="downline";

		s_assert.assertTrue(lsdHomePage.isLinkPresentUnderMyPageSection(linkName),"Link "+linkName+" is not present under My page section");
		parentWindowHandle = CommonUtils.getCurrentWindowHandle();
		lsdHomePage.clickLinkUnderMyPageSection(linkName);
		lsdHomePage.switchToChildWindow(parentWindowHandle);
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertAll();
	}

	// TC-1487 Home - My Pages - Solution Tool
	@Test(enabled=false)//Solution tool link not present under My Page section
	public void testVerifyMyPageSolutionToolLink_1487(){
		String currentUrl = null;
		String urlToAssert = "";
		String parentWindowHandle = null;
		String linkName="solutiontool";

		s_assert.assertTrue(lsdHomePage.isLinkPresentUnderMyPageSection(linkName),"Link "+linkName+" is not present under My page section");
		parentWindowHandle = CommonUtils.getCurrentWindowHandle();
		lsdHomePage.clickLinkUnderMyPageSection(linkName);
		lsdHomePage.switchToChildWindow(parentWindowHandle);
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertAll();
	}

	// TC-1488 Home - Header Section
	@Test
	public void testVerifyHomeHeaderSection_1488(){
		String currentUrl = null;
		String urlToAssert = null;
		String topMenuNamePulse="Pulse";
		String topMenuNameLinks="Links";

		s_assert.assertTrue(lsdHomePage.isTopNavigationHeaderDDMenuPresent(topMenuNamePulse),"Menu "+topMenuNamePulse+" is not present In top navigation");
		s_assert.assertTrue(lsdHomePage.isTopNavigationHeaderDDMenuPresent(topMenuNameLinks),"Menu "+topMenuNameLinks+" is not present In top navigation");
		//Verify Home Page link under pulse.
		urlToAssert = "#/home";
		lsdHomePage.selectSubMenuFromPulseMenu("Home");
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isHomePageOfPulsePresent(),"HomePage of pulse is not present");
		//Verify team link under pulse.
		urlToAssert = "#/team";
		lsdHomePage.selectSubMenuFromPulseMenu("Team");
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isTeamPageOfPulsePresent(),"Team Page of pulse is not present");
		//Verify customers link under pulse.
		urlToAssert = "#/pcs";
		lsdHomePage.selectSubMenuFromPulseMenu("Customers");
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isCustomerPageOfPulsePresent(),"Customer page of pulse is not present");
		//Verify Order link under pulse.
		urlToAssert = "#/orders";
		lsdHomePage.selectSubMenuFromPulseMenu("Orders");
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isOrderPageOfPulsePresent(),"Order Page of pulse is not present");
		s_assert.assertAll();
	}

	// TC-1489 Home - Overview Section
	@Test
	public void testVerifyHomePageOverviewSection_1489(){
		//Verify section in overview.
		s_assert.assertTrue(lsdHomePage.isHelloTextPresentInOverviewSection(),"Hello text not present in overview section");
		s_assert.assertTrue(lsdHomePage.isFirstNamePresentInOverviewSection(),"First name not present in overview section");
		s_assert.assertTrue(lsdHomePage.isQualificationtitlePresentInOverviewSection(),"Qualification title not present in overview section");
		s_assert.assertTrue(lsdHomePage.isEnrollmentDatePresentInOverviewSection(),"Enrollment date is not present in overview section");
		s_assert.assertAll();
	}

	//TC-1475 Home - Current Progress - Est. SV
	@Test
	public void testCurrentProgressEstSV_1475(){
		String SV = "2220";
		String SVFromUI = null;
		SVFromUI = lsdHomePage.getEstimatedSV();
		s_assert.assertTrue(SVFromUI.contains(SV), "Expected SV is "+SV+" but actual on UI is "+SVFromUI);
		lsdHomePage.clickEstimatedSV();
		s_assert.assertTrue(lsdHomePage.isTextPresentAtEstimatedPage("Estimated SV") && lsdHomePage.getCurrentURL().contains("/sv"), "Estimated SV page is not present");
		s_assert.assertAll();
	}

	//TC-1476 Home - Current Progress - Est. PSQV
	@Test
	public void testCurrentProgressEstPSQV_1476(){
		String PSQV = "25212";
		String PSQVFromUI = null;
		PSQVFromUI = lsdHomePage.getEstimatedPSQV();
		s_assert.assertTrue(PSQVFromUI.contains(PSQV), "Expected SV is "+PSQV+" but actual on UI is "+PSQVFromUI);
		lsdHomePage.clickEstimatedPSQV();
		s_assert.assertTrue(lsdHomePage.isTextPresentAtEstimatedPage("Estimated PSQV") && lsdHomePage.getCurrentURL().contains("/psqv"), "Estimated SV page is not present");
		s_assert.assertAll();
	}

	// TC-1477 Home - Current Progress - What These Numbers Mean
	@Test
	public void testCurrentProgressWhatTheseNumbersMean_1477(){
		String currentUrl = null;
		String expectedUrl = "what-these-numbers-mean-psqv";
		lsdHomePage.clickWhatTheseNumbersMeanLink();
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(expectedUrl),"Current URL : "+currentUrl+" does not contains "+expectedUrl);
		s_assert.assertTrue(lsdHomePage.isSVAndPSQVDescriptionPresent(),"SV and PSQV description is not present as expected");
		s_assert.assertAll();
	}

	//TC-1478 Home - EC LEGS and TITLES - Number of EC legs
	@Test
	public void testECLegsAndTitlesNumberOfECLegs_1478(){
		String expectedECLegs = TestConstants.EXPECTED_EC_LEGS_VALUE;
		String actualECLegs = null;
		actualECLegs = lsdHomePage.getECLegsValue();
		s_assert.assertTrue(lsdHomePage.isECLegsAndTitlesSectionPresent(),"EC Legs and Tiles section is not present");
		s_assert.assertTrue(actualECLegs.equals(expectedECLegs),"EC Legs Value is not found as Expected. Expected : "+expectedECLegs+" .  Actual : "+actualECLegs);
		s_assert.assertAll();
	}

	// TC-1485 Home - My Pages - My Australia Launch Page
	@Test
	public void testVerifyMyPageAustraliaLaunch_1485(){
		String currentUrl = null;
		String urlToAssert = ".au";
		String parentWindowHandle = null;
		String linkName="my-country-page";

		s_assert.assertTrue(lsdHomePage.isLinkPresentUnderMyPageSection(linkName),"Link "+linkName+" is not present under My page section");
		parentWindowHandle = CommonUtils.getCurrentWindowHandle();
		lsdHomePage.clickLinkUnderMyPageSection(linkName);
		lsdHomePage.switchToChildWindow(parentWindowHandle);
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isMyAustraliaLaunchPagePresent(),"Australia launch page is not present.");
		lsdHomePage.switchToParentWindow(parentWindowHandle);
		s_assert.assertAll();
	}

	// TC-1486 Home - Reports - Downline
	@Test(enabled=false)//Downline link navigating to invalid credential page
	public void testVerifyReportsSectionDownLineLink_1486(){
		String currentUrl = null;
		String urlToAssert = "";
		String parentWindowHandle = null;
		String linkName="report-downline";

		s_assert.assertTrue(lsdHomePage.isLinkPresentUnderReportsSection(linkName),"Link "+linkName+" is not present under Reports section");
		parentWindowHandle = CommonUtils.getCurrentWindowHandle();
		lsdHomePage.clickLinkUnderReportsSection(linkName);
		lsdHomePage.switchToChildWindow(parentWindowHandle);
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertAll();
	}

	// TC-1487 Home -Reports - Solution Tool
	@Test
	public void testVerifyReportsSectionSolutionToolLink_1487(){
		String currentUrl = null;
		String urlToAssert = "skin-solutions";
		String parentWindowHandle = null;
		String linkName="report-solution-tool";

		s_assert.assertTrue(lsdHomePage.isLinkPresentUnderReportsSection(linkName),"Link "+linkName+" is not present under Reports section");
		parentWindowHandle = CommonUtils.getCurrentWindowHandle();
		lsdHomePage.clickLinkUnderReportsSection(linkName);
		lsdHomePage.switchToChildWindow(parentWindowHandle);
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isLoginPageOfSolutionToolPresent(),"User is not navigated to home page of solution tool");
		lsdHomePage.switchToParentWindow(parentWindowHandle);
		s_assert.assertAll();
	}

	// TC-1481 Home - Keep in Mind
	@Test//Cannot be done for both type of user
	public void testVerifyHomePageKeepInMindSection_1481(){
		String pulseInfo = null;
		String crpInfo= null;
		String graceBalanceInfo = null;

		//Verify Subsection under keep in mind section.
		pulseInfo = lsdHomePage.getPulseProInfoFromKeepInMindSection();
		crpInfo = lsdHomePage.getCRPInfoFromKeepInMindSection();
		graceBalanceInfo = lsdHomePage.getGraceBalanceInfoFromKeepInMindSection();

		s_assert.assertTrue(pulseInfo.contains("Yes"),"Expected pulse info 'No' while actual"+pulseInfo);
		s_assert.assertTrue(crpInfo.contains("Yes"),"Expected CRP info 'No' while actual"+crpInfo);
		s_assert.assertTrue(graceBalanceInfo.contains("No"),"Expected Grace balance info 'No' while actual"+graceBalanceInfo);
		if(graceBalanceInfo.equals("No")){
			s_assert.assertTrue(lsdHomePage.isAnniversaryMonthPresentUnderKeepInMindSection(),"Anniversary month is not present under keep in mind section");	
		}
		s_assert.assertAll();
	}

	// TC-1491 Team - Verify the Number of days left in the current commission period is correct
	@Test(enabled=false)//Assertion for days is pending.
	public void testVerifyNumberOfdaysInCurrentCommissionPeriod_1491(){
		String currentUrl = null;
		String urlToAssert = "team";
		String currentPSTMonth = null;
		String daysLeft= null;
		String daysAndMonthLeftForComm=null;

		lsdHomePage.selectSubMenuFromPulseMenu("Team");
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isTeamPageOfPulsePresent(),"Team Page of pulse is not present");
		daysAndMonthLeftForComm=lsdHomePage.getEstimatedDaysLeftForCommission();
		currentPSTMonth=lsdHomePage.getCurrentMonthFromCurrentPSTDate();
		s_assert.assertTrue(daysAndMonthLeftForComm.contains(currentPSTMonth),"Current month for commission period is not correct.");
		s_assert.assertAll();
	}

	// TC-1492 Team - Number of consultants promoted to EC again.
	@Test
	public void testVerifyNumberOfConsultantsPromotedToEC_1492(){
		String currentUrl = null;
		String urlToAssert = "team";
		String consultantPromotedToEC=null;

		lsdHomePage.selectSubMenuFromPulseMenu("Team");
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isTeamPageOfPulsePresent(),"Team Page of pulse is not present");
		consultantPromotedToEC=lsdHomePage.getCountOfConsultantPromotedToEC();
		s_assert.assertTrue(consultantPromotedToEC.contains("8"),"Count of consultant promoted to EC expected 8 and actual"+consultantPromotedToEC);
		s_assert.assertAll();
	}

	// TC-1493 Team - Number of consultants getting there
	@Test
	public void testVerifyNumberOfConsultantsGettingThere_1493(){
		String currentUrl = null;
		String urlToAssert = "team";
		String consultantNotMatchingECCriteria=null;

		lsdHomePage.selectSubMenuFromPulseMenu("Team");
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isTeamPageOfPulsePresent(),"Team Page of pulse is not present");
		consultantNotMatchingECCriteria=lsdHomePage.getCountOfConsultantGettingThere();
		s_assert.assertTrue(consultantNotMatchingECCriteria.contains("0"),"Count of consultant not matching EC criteria expected 0 and actual"+consultantNotMatchingECCriteria);
		s_assert.assertAll();
	}

	// TC-1495 Team - Number of New Consultants
	@Test
	public void testVerifyNumberOfNewConsultants_1495(){
		String currentUrl = null;
		String urlToAssert = "team";
		String newEnrolledConsultants=null;

		lsdHomePage.selectSubMenuFromPulseMenu("Team");
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isTeamPageOfPulsePresent(),"Team Page of pulse is not present");
		newEnrolledConsultants=lsdHomePage.getCountOfNewEnrolledConsultants();
		s_assert.assertTrue(newEnrolledConsultants.contains("8"),"Count of newly enrolled consultant expected 8 and actual"+newEnrolledConsultants);
		s_assert.assertAll();
	}

	// TC-1496 Team - View all Consultants
	@Test
	public void testViewAllConsultants_1496(){
		String currentUrl = null;
		String urlToAssert = "team";
		String newEnrolledConsultants=null;

		lsdHomePage.selectSubMenuFromPulseMenu("Team");
		currentUrl = lsdHomePage.getCurrentURL();
		s_assert.assertTrue(currentUrl.contains(urlToAssert),"Current URL : "+currentUrl+" does not contains "+urlToAssert);
		s_assert.assertTrue(lsdHomePage.isTeamPageOfPulsePresent(),"Team Page of pulse is not present");
		newEnrolledConsultants=lsdHomePage.getCountOfNewEnrolledConsultants();
		s_assert.assertTrue(newEnrolledConsultants.contains("8"),"Count of newly enrolled consultant expected 8 and actual"+newEnrolledConsultants);
		s_assert.assertAll();
	}

	//	//Welcome page to login page TC-259
	//	@Test(priority=1)
	//	public void testWelcomeLoginPage(){
	//		String wrongPassword = "101Maiden$";
	//		lsdHomePage.clickLogout();
	//		lsdLoginPage.enterUsername(nonwhiteListedUserName);
	//		lsdLoginPage.enterPassword(password);
	//		lsdLoginPage.clickLoginBtn();
	//		s_assert.assertTrue(lsdLoginPage.isLoginFailErrorPresent(), "Login fail error not appeared for non-whiteListed user");
	//		lsdLoginPage.enterUsername(whiteListedUserName);
	//		lsdLoginPage.enterPassword(wrongPassword);
	//		lsdLoginPage.clickLoginBtn();
	//		s_assert.assertTrue(lsdLoginPage.isLoginFailErrorPresent(), "Login fail error not appeared for whiteListed user with wrong password");
	//		lsdLoginPage.enterUsername(whiteListedUserName);
	//		lsdLoginPage.enterPassword(password);
	//		lsdLoginPage.clickLoginBtn();
	//		s_assert.assertTrue(lsdHomePage.getCurrentURL().toLowerCase().contains("home"), "user is not on home page after login,the current url is expected to have 'home',but the current URL is "+lsdHomePage.getCurrentURL());
	//		s_assert.assertAll();
	//	}
	//
	//	//TC-1156 Order Summary - Design and data fields layout
	//	@Test(priority=2)
	//	public void testOrderSummary_1156(){
	//		lsdHomePage.navigateToHomePage();
	//		lsdHomePage.clickViewMyOrdersLink();
	//		lsdOrderPage.clickFirstProcessedOrder();
	//		s_assert.assertTrue(lsdOrderPage.getOrderNameAfterClickedOnOrder().toLowerCase().trim().contains(TestConstants.FIRST_ORDER_NAME.toLowerCase().trim()), "Expected Order Username is "+TestConstants.FIRST_ORDER_NAME+" but actual on UI is "+lsdOrderPage.getOrderNameAfterClickedOnOrder().trim());
	//		s_assert.assertTrue(lsdOrderPage.getOrderTypeAfterClickedOnOrder().trim().contains(TestConstants.ORDER_TYPE_CONSULTANT), "Expected Order type is "+TestConstants.ORDER_TYPE_CONSULTANT+" but actual on UI is "+lsdOrderPage.getOrderTypeAfterClickedOnOrder().trim());
	//		s_assert.assertTrue(lsdOrderPage.isConsultantMeTxtPresent(), "Consultant me txt is not present at order's page");
	//		s_assert.assertTrue(lsdOrderPage.isOrderLabelPresent("Order Date"), "Order date label is not present at order's page");
	//		s_assert.assertTrue(lsdOrderPage.isOrderLabelPresent("Commission Date"), "Commission Date label is not present at order's page");
	//		s_assert.assertTrue(lsdOrderPage.isOrderLabelPresent("Order Number"), "Order Number label is not present at order's page");
	//		s_assert.assertTrue(lsdOrderPage.isOrderLabelPresent("SV"), "SV label is not present at order's page");
	//		s_assert.assertFalse(lsdOrderPage.getOrderDate()==null, "Order date is blank");
	//		s_assert.assertFalse(lsdOrderPage.getCommisionDate()==null, "Commision date is blank");
	//		s_assert.assertFalse(lsdOrderPage.getOrderNumber()==null, "Order Number is blank");
	//		s_assert.assertTrue(lsdOrderPage.getPSQV().contains("0"), "PSQV is not 0");
	//		s_assert.assertTrue(lsdOrderPage.getOrderStatus().toLowerCase().contains("processed"), "Order status expected is processed but getting "+lsdOrderPage.getOrderStatus());
	//		//s_assert.assertFalse(lsdOrderPage.getOrderItems()==null, "Order Items are not present");
	//		//s_assert.assertTrue(lsdOrderPage.getFootNote().contains("Although you receive 0 PSQV"), "PSQV foot note is not present");
	//		lsdOrderPage.clickCloseIconOfOrder();
	//		s_assert.assertAll();
	//	}
	//
	//	//Main Menu TC-1151
	//	@Test(priority=3)
	//	public void testMainMenu_1151(){
	//		lsdCustomerPage = lsdHomePage.clickCustomersLink();
	//		s_assert.assertTrue(driver.getCurrentUrl().contains("customers"), "Expected Url should contains customers but actual on UI is: "+driver.getCurrentUrl());
	//		s_assert.assertTrue(lsdCustomerPage.isCustomerPagePresent(), "Customer page is not present");
	//		lsdOrderPage = lsdHomePage.clickOrdersLink();
	//		s_assert.assertTrue(driver.getCurrentUrl().contains("orders"), "Expected Url should contains customers but actual on UI is: "+driver.getCurrentUrl());
	//		s_assert.assertTrue(lsdOrderPage.isOrdersPagePresent(), "Orders page is not present");
	//		lsdHomePage.navigateToHomePage();
	//		s_assert.assertTrue(driver.getCurrentUrl().contains("home"), "Expected Url should contains customers but actual on UI is: "+driver.getCurrentUrl());
	//		s_assert.assertAll();
	//	}
	//
	//	//Feedback Option TC-272
	//	@Test(priority=4)
	//	public void testFeedbackoption_272(){
	//		String parentWindowHandle = driver.getWindowHandle();
	//		lsdFeedbackPage = lsdHomePage.clickFeedbackLink();
	//		s_assert.assertTrue(lsdFeedbackPage.isFeedbackPagePresent(parentWindowHandle), "Feedback page is not present");
	//		s_assert.assertAll();
	//	}
	//
	//	//Contact Card - Design and Navigation TC-270
	//	@Test(priority=5)
	//	public void testContactCardDesignAndNavigation_270(){
	//		lsdHomePage.navigateToHomePage();
	//		lsdHomePage.clickViewMyOrdersLink();
	//		lsdOrderPage.clickFirstProcessedOrder();
	//		s_assert.assertTrue(lsdOrderPage.isContactButtonPresentAtFooter(),"Contact button is not present at footer for any processed order");
	//		s_assert.assertTrue(lsdOrderPage.getContactNameFromContactButton().toLowerCase().trim().contains(TestConstants.FIRST_ORDER_CONTACT_NAME.toLowerCase().trim()), "Expected Contact name is:"+TestConstants.FIRST_ORDER_CONTACT_NAME+" Actual on UI is "+lsdOrderPage.getContactNameFromContactButton());
	//		lsdOrderPage.clickCloseIconOfOrder();
	//		lsdOrderPage.clickFirstProcessedPCAutishipOrder();
	//		s_assert.assertTrue(lsdOrderPage.isContactButtonPresentAtFooter(),"Contact button is not present at footer for PC autoship processed order");
	//		lsdOrderPage.clickCloseIconOfOrder();
	//		lsdOrderPage.clickFirstProcessedPCAutishipOrder();
	//		s_assert.assertTrue(lsdOrderPage.isContactButtonPresentAtFooter(),"Contact button is not present at footer for RC processed order");
	//		lsdOrderPage.clickContactButtonAtFooter();
	//		s_assert.assertTrue(lsdOrderPage.isContactDetailsPresent(),"Contact details is not present after clicked on contact button");
	//		lsdOrderPage.clickCloseIconOfContact();
	//		lsdOrderPage.clickCloseIconOfOrder();
	//		lsdHomePage.clickCustomersLink();
	//		lsdCustomerPage.clickExpandAndMinimizeButtonOfThisMonth();
	//		lsdCustomerPage.clickFirstProcessedOrderUnderCustomerSection();
	//		s_assert.assertTrue(lsdCustomerPage.getContactNameFromContactButtonInCustomerPage().toLowerCase().trim().contains(TestConstants.FIRST_ORDER_CONTACT_NAME_UNDER_CUSTOMER_SECTION.toLowerCase().trim()), "Expected Contact name is:"+TestConstants.FIRST_ORDER_CONTACT_NAME_UNDER_CUSTOMER_SECTION+" Actual on UI is "+lsdCustomerPage.getContactNameFromContactButtonInCustomerPage());
	//		lsdCustomerPage.clickBackArrowIcon();
	//		s_assert.assertAll();
	//	}
	//
	//	//Contact Card button interactions TC-271
	//	@Test(priority=6)
	//	public void testContactCardButtonInteractions_271(){
	//		lsdHomePage.navigateToHomePage();
	//		lsdHomePage.clickViewMyOrdersLink();
	//		lsdOrderPage.clickFirstProcessedOrder();
	//		s_assert.assertTrue(lsdOrderPage.isContactButtonPresentAtFooter(),"Contact button is not present at footer for any processed order");
	//		lsdOrderPage.clickContactButtonAtFooter();
	//		s_assert.assertTrue(lsdOrderPage.isPhoneIconPresent(),"Phone icon is not present after clicked on contact button");
	//		s_assert.assertTrue(lsdOrderPage.isPhoneIconPresent(),"Email icon is not present after clicked on contact button");
	//		lsdOrderPage.clickCloseIconOfContact();
	//		lsdOrderPage.clickCloseIconOfOrder();
	//		s_assert.assertAll();
	//	}
	//
	//	//Successful Log/Sign Out TC-273 
	//	@Test(priority=7)
	//	public void testSuccessfulLogInSignOut_273(){
	//		lsdHomePage.navigateToHomePage();
	//		lsdHomePage.clickLogout();
	//		s_assert.assertTrue(lsdHomePage.getCurrentURL().toLowerCase().contains("login"), "User is not on logout page, the current url is expected to have 'login',but the currrent URL is: "+lsdHomePage.getCurrentURL());
	//		lsdLoginPage.enterUsername(whiteListedUserName);
	//		lsdLoginPage.enterPassword(password);
	//		lsdLoginPage.clickLoginBtn();
	//		s_assert.assertTrue(lsdHomePage.getCurrentURL().toLowerCase().contains("home"), "user is not on home page after login,the current url is expected to have 'home',but the current URL is "+lsdHomePage.getCurrentURL());
	//		s_assert.assertAll();
	//	}
	//
	//	//Customers View TC-1149
	//	@Test(priority=8)
	//	public void testCustomersView_1149(){
	//		lsdHomePage.navigateToHomePage();
	//		lsdHomePage.clickCustomersLink();
	//		lsdCustomerPage.clickExpandAndMinimizeButtonOfThisMonth();
	//		s_assert.assertTrue(lsdCustomerPage.isAutoshipOrderSectionPresentAfterExpand(), "Expand button is not working for this month section");
	//		lsdCustomerPage.clickExpandAndMinimizeButtonOfThisMonth();
	//		lsdCustomerPage.clickExpandAndMinimizeButtonOfNextMonth();
	//		s_assert.assertTrue(lsdCustomerPage.isAutoshipOrderSectionPresentAfterExpand(), "Expand button is not working for Next month section");
	//		lsdCustomerPage.clickExpandAndMinimizeButtonOfNextMonth();
	//		lsdCustomerPage.clickExpandAndMinimizeButtonOfFurtherOut();
	//		s_assert.assertTrue(lsdCustomerPage.isAutoshipOrderSectionPresentAfterExpand(), "Expand button is not working for Further Out section");
	//		lsdCustomerPage.clickExpandAndMinimizeButtonOfFurtherOut();
	//		lsdCustomerPage.clickExpandAndMinimizeButtonOfThisMonth();
	//		s_assert.assertTrue(lsdCustomerPage.getCustomerNamePresentInFirstOrder().trim().contains(TestConstants.FIRST_PC_ORDER_NAME), "Expected PC order name is "+TestConstants.FIRST_PC_ORDER_NAME+" but actual on UI is "+lsdCustomerPage.getCustomerNamePresentInFirstOrder().trim());
	//		s_assert.assertTrue(lsdCustomerPage.getOrderTypeOfFirstOrder().trim().contains(TestConstants.ORDER_TYPE_PC), "Expected order type is "+TestConstants.ORDER_TYPE_PC+" but actual on UI is "+lsdCustomerPage.getOrderTypeOfFirstOrder().trim());
	//		s_assert.assertTrue(lsdCustomerPage.getOrderStatusOfFirstOrder().trim().toLowerCase().contains(TestConstants.PROCESSED_ORDER_STATUS), "Expected order status is "+TestConstants.PROCESSED_ORDER_STATUS+" but actual on UI is "+lsdCustomerPage.getOrderStatusOfFirstOrder().trim().toLowerCase());
	//		s_assert.assertFalse(lsdCustomerPage.getOrderDateOfFirstOrder()==null, "Order date is not present in order section");
	//		s_assert.assertTrue(lsdCustomerPage.isNewLabelPresentForFirstOrder(), "New label is not present for first PC order");
	//		s_assert.assertTrue(lsdCustomerPage.getClassNameOfFirstProcessedOrder().toLowerCase().contains("green"), "Expected colour of order status 'processed is green' but actual on UI is: "+lsdCustomerPage.getClassNameOfFirstProcessedOrder());
	//		//s_assert.assertTrue(lsdCustomerPage.getClassNameOfFirstFailedOrder().toLowerCase().contains("red"), "Expected colour of order status 'failed is red' but actual on UI is: "+lsdCustomerPage.getClassNameOfFirstFailedOrder());
	//		lsdCustomerPage.clickExpandAndMinimizeButtonOfThisMonth();
	//		lsdCustomerPage.clickExpandAndMinimizeButtonOfNextMonth();
	//		//s_assert.assertTrue(lsdCustomerPage.getClassNameOfFirstScheduledOrder().toLowerCase().contains("orange"), "Expected colour of order status 'scheduled is orange' but actual on UI is: "+lsdCustomerPage.getClassNameOfFirstScheduledOrder());
	//		s_assert.assertAll();
	//	}
	//
	//	//PC Profile TC-1150
	//	@Test(priority=9)
	//	public void testPCProfile_1150(){
	//		String orderStatus = "Order Status";
	//		String orderType = "Order Type";
	//		String orderNumber = "Order Number";
	//		String QV = "QV";
	//		lsdHomePage.navigateToHomePage();
	//		lsdHomePage.clickCustomersLink();
	//		lsdCustomerPage.clickExpandAndMinimizeButtonOfThisMonth();
	//		lsdCustomerPage.clickFirstProcessedOrderUnderCustomerSection();
	//		s_assert.assertTrue(lsdCustomerPage.getPCUserNamePresentInOrder().trim().contains(TestConstants.FIRST_PC_ORDER_NAME), "Expected PC order name is "+TestConstants.FIRST_PC_ORDER_NAME+" but actual on UI is "+lsdCustomerPage.getPCUserNamePresentInOrder().trim());
	//		s_assert.assertTrue(lsdCustomerPage.isNewLabelPresentForFirstOrder(), "New label is not present in first PC order");
	//		s_assert.assertTrue(lsdCustomerPage.isEnrolledOnTxtPresent(), "Enrolled on is not present at order");
	//		s_assert.assertTrue(lsdCustomerPage.isNextShipmentTxtPresent(), "Next shipment is not present at order");
	//		s_assert.assertTrue(lsdCustomerPage.isUpcomingOrderSectionPresent(), "upcoming order section is not present at order");
	//		s_assert.assertTrue(lsdCustomerPage.isOrderHistorySectionPresent(), "Order history section is not present at order");
	//		s_assert.assertFalse(lsdCustomerPage.getOrderDateAndTimeStampInUpcomingOrderSection()==null, "Order date and time is not present in order upcoming section");
	//		s_assert.assertTrue(lsdCustomerPage.isOrderDetailsPresentInOrderUpcomingSection(orderStatus), "Order status is not present in order upcoming section");
	//		s_assert.assertTrue(lsdCustomerPage.isOrderDetailsPresentInOrderUpcomingSection(orderType), "Order type is not present in order upcoming section");
	//		s_assert.assertTrue(lsdCustomerPage.isOrderDetailsPresentInOrderUpcomingSection(orderNumber), "Order status is not present in order upcoming section");
	//		s_assert.assertTrue(lsdCustomerPage.isOrderDetailsPresentInOrderUpcomingSection(QV), "Order status is not present in order upcoming section");
	//		s_assert.assertTrue(lsdCustomerPage.isOrderItemsPresentInUpcomingOrderSection(), "Order items are not present in upcoming order section");
	//		//s_assert.assertTrue(lsdCustomerPage.getOrderNote().toLowerCase().contains("note"), "Order not is not present");
	//		s_assert.assertFalse(lsdCustomerPage.getOrderDateAndTimeStampOfFirstOrderInOrderHistorySection()==null, "Order date and time is not present in order history section of first order");
	//		s_assert.assertTrue(lsdCustomerPage.isOrderDetailsPresentInOrderHistorySectionOfFirstOrder(orderStatus), "Order status is not present in order history section");
	//		s_assert.assertTrue(lsdCustomerPage.isOrderDetailsPresentInOrderHistorySectionOfFirstOrder(orderType), "Order type is not present in order history section");
	//		s_assert.assertTrue(lsdCustomerPage.isOrderDetailsPresentInOrderHistorySectionOfFirstOrder(orderNumber), "Order status is not present in order history section");
	//		s_assert.assertTrue(lsdCustomerPage.isOrderDetailsPresentInOrderHistorySectionOfFirstOrder(QV), "Order status is not present in order history section");
	//		s_assert.assertTrue(lsdCustomerPage.isOrderItemsPresentInOrderHistorySectionOfFirstOrder(), "Order items are not present in order history section of first order");
	//		lsdCustomerPage.clickBackArrowIcon();
	//		s_assert.assertAll();
	//	}
	//
	//	//Filters Functionality TC-264 
	//	@Test(priority=10)
	//	public void testFiltersFunctionality_LSD_TC_264(){
	//		lsdHomePage.navigateToHomePage();
	//		lsdHomePage.clickOrdersLink();
	//		lsdOrderPage.clickAddFilters();
	//		lsdOrderPage.clickConsultantOrderChkBoxInAllOrderTypes();
	//		lsdOrderPage.clickPreferredCustomerOrderOrderChkBoxInAllOrderTypes();
	//		lsdOrderPage.clickSetFiltersBtn();
	//		s_assert.assertTrue(lsdOrderPage.isFilterAppliedSuccessfully("Consultant Orders"), "Consultant order filter is not applied successfully");
	//		s_assert.assertTrue(lsdOrderPage.isFilterAppliedSuccessfully("Preferred Customer Orders"), "Preferred Customer Orders filter is not applied successfully");
	//		int totalNoOfOrders = lsdOrderPage.getCountOfTotalOrders();
	//		for(int i=1; i<=totalNoOfOrders; i++){
	//			//s_assert.assertTrue(lsdOrderPage.getOrderStatus(i).toLowerCase().contains("processed"), "Expected order Status is 'Processed' but actual on UI for order number:"+i+" is: "+lsdOrderPage.getOrderStatus(i));
	//			s_assert.assertTrue(lsdOrderPage.getOrderType(i).toLowerCase().contains("crp")|| lsdOrderPage.getOrderType(i).toLowerCase().contains("pc perks") || lsdOrderPage.getOrderType(i).toLowerCase().contains("consultant order") , "Expected order type is 'CRP' or 'Pulse Subscription' or 'consultant order' but actual on UI for order number:"+i+" is: "+lsdOrderPage.getOrderType(i));
	//		}
	//		lsdOrderPage.clickAddFilters();
	//		s_assert.assertTrue(lsdOrderPage.isConsultantOrdersCheckBoxIsChecked(), "Consultant order filter is not checked");
	//		s_assert.assertTrue(lsdOrderPage.isPreferredCustomerOrdersCheckBoxIsChecked(), "Preferred Customer Orders filter is not checked");
	//		lsdOrderPage.clickCloseIconOfFilter();
	//		lsdOrderPage.clickCloseIconOfFilter("Preferred Customer Orders");
	//		totalNoOfOrders = lsdOrderPage.getCountOfTotalOrders();
	//		for(int i=1; i<=totalNoOfOrders; i++){
	//			//s_assert.assertTrue(lsdOrderPage.getOrderStatus(i).toLowerCase().contains("processed"), "Expected order Status is 'Processed' but actual on UI for order number:"+i+" is: "+lsdOrderPage.getOrderStatus(i));
	//			s_assert.assertFalse(lsdOrderPage.getOrderType(i).toLowerCase().contains("pc perks"), "Expected order type is 'CRP' or 'consultant order' but actual on UI for order number:"+i+" is: "+lsdOrderPage.getOrderType(i));
	//		}
	//		lsdOrderPage.clickCloseIconOfFilter("Consultant Orders");
	//		s_assert.assertFalse(lsdOrderPage.isFilterAppliedSuccessfully("Consultant Orders"), "Consultant order filter is not removed successfully");
	//		s_assert.assertFalse(lsdOrderPage.isFilterAppliedSuccessfully("Preferred Customer Orders"), "Processed Orders filter is not removed successfully");
	//		s_assert.assertAll();
	//	}
	//
	//	//Order Details - Design and data fields layout TC-1157 
	//	@Test(priority=11)
	//	public void testOrderDetailsDesignAndDataFieldsLayout_LSD_TC_1157(){
	//		lsdHomePage.navigateToHomePage();
	//		lsdHomePage.clickOrdersLink();
	//		lsdOrderPage.clickFirstProcessedOrder();
	//		lsdOrderPage.clickViewDetailsBtn();
	//		s_assert.assertTrue(lsdOrderPage.getOrderNamePresentInViewOrderDetails().toLowerCase().trim().contains(TestConstants.FIRST_ORDER_NAME.toLowerCase().trim()), "Expected Order Username is "+TestConstants.FIRST_ORDER_NAME+" but actual on UI is "+lsdOrderPage.getOrderNamePresentInViewOrderDetails().trim());
	//		s_assert.assertTrue(lsdOrderPage.getOrderTypePresentInViewOrderDetails().trim().contains(TestConstants.ORDER_TYPE_CONSULTANT), "Expected Order type is "+TestConstants.ORDER_TYPE_CONSULTANT+" but actual on UI is "+lsdOrderPage.getOrderTypePresentInViewOrderDetails().trim());
	//		s_assert.assertTrue(lsdOrderPage.isOrderDetailsHeaderPresent("Order details"), "Order details header is not present");
	//		//s_assert.assertFalse(lsdOrderPage.getOrderNamePresentInViewOrderDetails()==null, "Order name is blank");
	//		//s_assert.assertFalse(lsdOrderPage.getOrderTypePresentInViewOrderDetails()==null, "Order type is blank");
	//		s_assert.assertFalse(lsdOrderPage.getEnrolledDatePresentInViewOrderDetails()==null, "Enrolled date is blank");
	//		s_assert.assertTrue(lsdOrderPage.isOrderHeaderPresent("Overview"), "Overview header is not present");
	//		s_assert.assertTrue(lsdOrderPage.isOverviewDetailsPresent("Order Date"), "Order Date is not present in overview section");
	//		s_assert.assertTrue(lsdOrderPage.isOverviewDetailsPresent("Commission Date"), "Commission Date is not present in overview section");
	//		s_assert.assertTrue(lsdOrderPage.isOverviewDetailsPresent("Order Number"), "Order number is not present in overview section");
	//		s_assert.assertTrue(lsdOrderPage.getSVValueFromViewOrderDetails().trim().contains(TestConstants.FIRST_ORDER_SV_VALUE), "Expected SV value is "+TestConstants.FIRST_ORDER_SV_VALUE+" but actual on UI is "+lsdOrderPage.getSVValueFromViewOrderDetails().trim());
	//		s_assert.assertTrue(lsdOrderPage.isOverviewDetailsPresent("Total"), "total is not present in overview section");
	//		s_assert.assertTrue(lsdOrderPage.isOrderHeaderPresent("Shipment details"), "Shipment details header is not present");
	//		s_assert.assertTrue(lsdOrderPage.isShipmentDetailsPresent("Order Status"), "Order Status is not present in shipment details section");
	//		//s_assert.assertTrue(lsdOrderPage.isShipmentDetailsPresent("Tracking"), "Tracking link is not present in shipment details section");
	//		s_assert.assertTrue(lsdOrderPage.isOrderItemsPresent(), "Order is not contain any item");
	//		s_assert.assertTrue(lsdOrderPage.isSKUValuePresentUnderOrderItems(), "SKU value is not present under item");
	//		//s_assert.assertTrue(lsdOrderPage.isTotalPricePresentUnderOrderItems(), "Total price is not present under item");
	//		s_assert.assertTrue(lsdOrderPage.getTotalPricePresentUnderOrderItems().trim().contains(TestConstants.TOTAL_PRICE), "Expected total price is "+TestConstants.TOTAL_PRICE+" but actual on UI is "+lsdOrderPage.getTotalPricePresentUnderOrderItems().trim());
	//		//s_assert.assertTrue(lsdOrderPage.isQuantityPresentUnderOrderItems(), "Quantity is not present under item");
	//		s_assert.assertTrue(lsdOrderPage.getQuantityPresentUnderOrderItems().trim().contains(TestConstants.QUANTITY_OF_PRODUCT), "Expected product quantity is "+TestConstants.QUANTITY_OF_PRODUCT+" but actual on UI is "+lsdOrderPage.getQuantityPresentUnderOrderItems().trim());
	//		//s_assert.assertTrue(lsdOrderPage.isTrackOrderBtnPresent(), "Track order btn is not present under item");
	//		s_assert.assertTrue(lsdOrderPage.isOrderHeaderPresent("Shipping details"), "Shipping details header is not present");
	//		s_assert.assertTrue(lsdOrderPage.isShippingDetailsSubHeadingPresent(), "Shipping details subheading is not present");
	//		s_assert.assertTrue(lsdOrderPage.getShippingAddressName().toLowerCase().trim().contains(TestConstants.FIRST_ORDER_NAME.toLowerCase().trim()), "Expected Order Username is "+TestConstants.FIRST_ORDER_NAME+" but actual on UI is "+lsdOrderPage.getShippingAddressName().trim());
	//		s_assert.assertTrue(lsdOrderPage.isShippingMethodPresent(), "Shipping details subheading is not present");
	//		s_assert.assertTrue(lsdOrderPage.getShippingMethodNameFromViewOrderDetails().toLowerCase().trim().contains(TestConstants.SHIPPING_METHOD.toLowerCase().trim()), "Expected shipping method is "+TestConstants.SHIPPING_METHOD+" but actual on UI is "+lsdOrderPage.getShippingMethodNameFromViewOrderDetails().trim());
	//		s_assert.assertTrue(lsdOrderPage.isOrderHeaderPresent("Billing details"), "Billing details header is not present");
	//		s_assert.assertTrue(lsdOrderPage.isBillingDetailsSubHeadingPresent(), "Billing details subheading is not present");
	//		s_assert.assertTrue(lsdOrderPage.getBillingProfileName().toLowerCase().trim().contains(TestConstants.FIRST_ORDER_NAME.toLowerCase().trim()), "Expected Billing name is "+TestConstants.FIRST_ORDER_NAME+" but actual on UI is "+lsdOrderPage.getBillingProfileName().trim());
	//		lsdOrderPage.clickBackArrowIconOfViewDetails();
	//		lsdOrderPage.clickCloseIconOfOrder();
	//		s_assert.assertAll();
	//	}
	//
	//	//Orders View - Design and data fields layout TC-1155
	//	@Test(priority=12)
	//	public void testOrdersViewDesignAndDataFieldsLayout_LSD_TC_1155(){
	//		String countOfPendingOrders = TestConstants.COUNT_OF_PENDING_ORDERS;
	//		String countOfProcessedOrders = TestConstants.COUNT_OF_PROCESSED_ORDERS;
	//		String countOfFailedOrders = TestConstants.COUNT_OF_FAILED_ORDERS;
	//		lsdHomePage.navigateToHomePage();
	//		lsdHomePage.clickOrdersLink();
	//		String date = lsdHomePage.getCurrentPSTDate();
	//		String currentDate[] = date.split("\\ ");
	//		String monthWithYear = currentDate[1].split("\\,")[0]+" "+currentDate[2];
	//		s_assert.assertTrue(lsdOrderPage.getCommissionTimePeriod().toLowerCase().contains(monthWithYear.toLowerCase()),"Expected commission time period is "+monthWithYear+"But actual on UI is "+lsdOrderPage.getCommissionTimePeriod().toLowerCase());
	//		s_assert.assertTrue(lsdOrderPage.isReturnedOrderPresent(), "Returned order is not present");
	//		s_assert.assertTrue(lsdOrderPage.getOrderMetricsCount("Pending").equalsIgnoreCase(countOfPendingOrders), "Expected pending order's count is: "+countOfPendingOrders+" but actual on UI is: "+lsdOrderPage.getOrderMetricsCount("Pending"));
	//		s_assert.assertTrue(lsdOrderPage.getOrderMetricsCount("Processed").equalsIgnoreCase(countOfProcessedOrders), "Expected Processed order's count is: "+countOfProcessedOrders+" but actual on UI is: "+lsdOrderPage.getOrderMetricsCount("Processed"));
	//		s_assert.assertTrue(lsdOrderPage.getOrderMetricsCount("Failed").equalsIgnoreCase(countOfFailedOrders), "Expected Failed order's count is: "+countOfFailedOrders+" but actual on UI is: "+lsdOrderPage.getOrderMetricsCount("Failed"));
	//		s_assert.assertTrue(lsdOrderPage.getFirstOrderUserName().trim().contains(TestConstants.FIRST_ORDER_NAME), "Expected Order Username is "+TestConstants.FIRST_ORDER_NAME+" but actual on UI is "+lsdOrderPage.getFirstOrderUserName().trim());
	//		s_assert.assertTrue(lsdOrderPage.getFirstOrderStatus().trim().toLowerCase().contains(TestConstants.PROCESSED_ORDER_STATUS), "Expected Order status is "+TestConstants.PROCESSED_ORDER_STATUS+" but actual on UI is "+lsdOrderPage.getFirstOrderStatus().trim());
	//		s_assert.assertTrue(lsdOrderPage.getFirstOrderType().trim().contains(TestConstants.ORDER_TYPE_CONSULTANT), "Expected Order type is "+TestConstants.ORDER_TYPE_CONSULTANT+" but actual on UI is "+lsdOrderPage.getFirstOrderType().trim());
	//		s_assert.assertTrue(lsdOrderPage.getFirstOrderPSQVValue().trim().contains(TestConstants.FIRST_ORDER_SV_VALUE), "Expected SV value is "+TestConstants.FIRST_ORDER_SV_VALUE+" but actual on UI is "+lsdOrderPage.getFirstOrderPSQVValue().trim());
	//		s_assert.assertTrue(lsdOrderPage.isFirstOrderDatePresent(), "First order date is not present");
	//		s_assert.assertAll();
	//	}
	//
	//	//Navigation of tracking link (Order Summary/Details) TC-1117
	//	@Test(enabled=false)
	//	public void testNavigationOfTrackingLink_LSD_TC_1117(){
	//		lsdHomePage.navigateToHomePage();
	//		lsdHomePage.clickOrdersLink();
	//		lsdOrderPage.clickFirstProcessedPCAutishipOrder();
	//		String parentWindowHandle = driver.getWindowHandle();
	//		String trackingNumber = lsdOrderPage.clickAndGetTrackingNumberLink();
	//		s_assert.assertTrue(lsdOrderPage.isTrackingWebsitePagePresent(parentWindowHandle, trackingNumber), "Tracking number link is not working");
	//		s_assert.assertAll();
	//	}



}
