package com.rf.test.website.crm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.rf.test.website.RFCRMWebsiteBaseTest;

public class CRMCaseManagementTest extends RFCRMWebsiteBaseTest{
	private static final Logger logger = LogManager
			.getLogger(CRMCaseManagementTest.class.getName());

	// QTest ID TC-1235 Verify WhiteList Queue - open case status
	@Test
	public void testVerifyWhiteListQueueOpenCaseStatus_1235q() throws InterruptedException{
		String caseValue = "Cases";
		String typeValue = "Suspicious Activity";
		String subypeValue = "Whitelist Request";
		String caseOwnerName = "Whitelist Request";
		String caseOwnerNameFromUI = null;
		String caseNumber = null;
		String statusType = "Resolved";
		String statusFromUI = null;
		loginUser(CRMSalesSupportCoordinatorUserName, CRMSalesSupportCoordinatorPassword);
		crmHomePage.selectValueFromServiceCloudDD(caseValue);
		crmHomePage.clickNewCaseButton();
		crmHomePage.fillTheDetailsAndClickSaveForNewCase(typeValue, subypeValue);
		caseNumber = crmHomePage.getCaseNumberFromTab();
		crmHomePage.closeAllOpenedTabs();
		crmHomePage.enterTextInSearchFieldAndHitEnter(caseNumber);
		crmHomePage.clickCaseNumberAfterSearch(caseNumber);
		caseOwnerNameFromUI = crmHomePage.getCaseOwnerName();
		s_assert.assertTrue(caseOwnerNameFromUI.contains(caseOwnerName), "Expected case owner name is "+caseOwnerName+" but actual on UI is "+caseOwnerNameFromUI);
		crmHomePage.changeStatusTypeAndUpdate(statusType);
		statusFromUI = crmHomePage.getStatusOfCase();
		s_assert.assertTrue(statusFromUI.contains(statusType), "Expected case status is "+statusType+" but actual on UI is "+statusFromUI);
		s_assert.assertAll();
	}

	// QTest ID TC-1244 Verify  resolve case owner
	@Test
	public void testVerifyResolveCaseOwner_1244q() throws InterruptedException{
		String caseValue = "Cases";
		String typeValue = "Suspicious Activity";
		String caseOwnerName = "Automation_ SSCoordinator";
		String caseOwnerNameFromUI = null;
		String statusType = "Resolved";
		String statusFromUI = null;
		loginUser(CRMSalesSupportCoordinatorUserName, CRMSalesSupportCoordinatorPassword);
		crmHomePage.selectValueFromServiceCloudDD(caseValue);
		crmHomePage.clickNewCaseButton();
		crmHomePage.fillTheDetailsAndClickSaveForNewCase(typeValue, "2","2",statusType);
		caseOwnerNameFromUI = crmHomePage.getCaseOwnerName();
		s_assert.assertTrue(caseOwnerNameFromUI.contains(caseOwnerName), "Expected case owner name is "+caseOwnerName+" but actual on UI is "+caseOwnerNameFromUI);
		statusFromUI = crmHomePage.getStatusOfCase();
		s_assert.assertTrue(statusFromUI.contains(statusType), "Expected case status is "+statusType+" but actual on UI is "+statusFromUI);
		crmHomePage.closeAllOpenedTabs();
		crmHomePage.clickNewCaseButton();
		typeValue = "Spam";
		caseOwnerName = "Spam Queue";
		crmHomePage.selectTypeForNewCaseByValue(typeValue);
		crmHomePage.selectStatusForNewCaseByValue(statusType);
		crmHomePage.clickSaveBtnForNewCase();
		caseOwnerNameFromUI = crmHomePage.getCaseOwnerName();
		s_assert.assertTrue(caseOwnerNameFromUI.contains(caseOwnerName), "Expected case owner name is "+caseOwnerName+" but actual on UI for spam type is "+caseOwnerNameFromUI);
		statusFromUI = crmHomePage.getStatusOfCase();
		s_assert.assertTrue(statusFromUI.contains(statusType), "Expected case status is "+statusType+" but actual on UI for spam type is "+statusFromUI);
		s_assert.assertAll();
	}

	// QTest ID TC-1245 Verify case functionality for AU Salessupport
	@Test
	public void testVerifyCaseFunctionalityForAUSalesSupport_1245q(){
		String caseValue = "Cases";
		String typeValue = "Suspicious Activity";
		String statusType = "Resolved";
		String statusTypeOpen = "Open";
		String statusFromUI = null;
		String caseNumber = null;
		String salesSupportAustraliaQueueValue = "SalesSupport Australia";
		String caseOwnerNameFromUI = null;
		String caseOwnerName = "Automation_ SSCoordinator";
		loginUser(CRMAUSalesSupportCoordinatorUserName, CRMAUSalesSupportCoordinatorPassword);
		crmHomePage.selectValueFromServiceCloudDD(caseValue);
		crmHomePage.clickNewCaseButton();
		crmHomePage.fillTheDetailsAndClickSaveForNewCase(typeValue,"2","2",statusType);
		statusFromUI = crmHomePage.getStatusOfCase();
		s_assert.assertTrue(statusFromUI.contains(statusType), "Case is not created and Expected case status is "+statusType+" but actual on UI for spam type is "+statusFromUI);
		crmHomePage.closeAllOpenedTabs();
		crmHomePage.selectValueFromSubDD(salesSupportAustraliaQueueValue);
		caseNumber = crmHomePage.changeCaseOwner(statusTypeOpen,"3" ,caseOwnerName);
		crmHomePage.enterTextInSearchFieldAndHitEnter(caseNumber);
		crmHomePage.clickCaseNumberAfterSearch(caseNumber);
		caseOwnerNameFromUI = crmHomePage.getCaseOwnerName();
		s_assert.assertTrue(caseOwnerNameFromUI.contains(caseOwnerName), "Expected case owner name is "+caseOwnerName+" but actual on UI is "+caseOwnerNameFromUI);
		s_assert.assertAll();
	}

	// QTest ID TC-1226 Verify Compliance Queue
	@Test
	public void testVerifyComplianceQueue_1226q() throws InterruptedException{
		String caseValue = "Cases";
		String typeValue = "Compliance";
		String subType = "Account Maintenance";
		String typeDetail = "Death and Incapacity";
		String expectedCaseText=" The case has been successfully saved and assigned to the appropriate user or queue according to the active assignment rule. You no longer have access to view or edit this case.";
		String caseTextFromUI = null;
		String subDDValue = "Compliance Queue - Unresolved VIEW";
		String statusType = "Open";
		String caseOwnerName = "Automation_ Compliance";
		String caseNumber = null;
		String newStatusType = "Resolved";
		String statusFromUI = null;
		String caseOwnerNameFromUI = null;
		//Login as sales support coordinator.
		loginUser(CRMSalesSupportCoordinatorUserName, CRMSalesSupportCoordinatorPassword);
		crmHomePage.selectValueFromServiceCloudDD(caseValue);
		crmHomePage.clickNewCaseButton();
		crmHomePage.fillTheDetailsAndClickSaveForNewCase(typeValue,subType,typeDetail);
		caseTextFromUI=crmHomePage.getCaseSavedText();
		s_assert.assertTrue(caseTextFromUI.contains(expectedCaseText),"Expected case text is "+expectedCaseText+" but actual on UI is "+caseTextFromUI);
		crmHomePage.closeAllOpenedTabs();
		crmLogout();
		//Login as compliance user.
		loginUser(CRMComplianceUserName, CRMCompliancePassword);
		crmHomePage.selectValueFromServiceCloudDD(caseValue);
		crmHomePage.selectValueFromSubDD(subDDValue);
		//Change case owner status
		caseNumber=crmHomePage.changeCaseOwner(statusType, "1", caseOwnerName);
		crmHomePage.enterTextInSearchFieldAndHitEnter(caseNumber);
		crmHomePage.clickCaseNumberAfterSearch(caseNumber);
		//Change status type.
		crmHomePage.changeStatusTypeAndUpdate(newStatusType);
		//Assert case owner name.
		caseOwnerNameFromUI = crmHomePage.getCaseOwnerName();
		s_assert.assertTrue(caseOwnerNameFromUI.contains(caseOwnerName), "Expected case owner name is "+caseOwnerName+" but actual on UI is "+caseOwnerNameFromUI);
		//Assert case owner status
		statusFromUI = crmHomePage.getStatusOfCase();
		s_assert.assertTrue(statusFromUI.contains(newStatusType), "Expected case status is "+newStatusType+" but actual on UI is "+statusFromUI);
		s_assert.assertAll();
	}

	// QTest ID TC-1229 Verify RFConnectionTier2  Queue
	@Test(enabled=true)//reason mention in comment in code)
	public void testRFConnectionTier2Queue_1229(){
		String caseValue = "Cases";
		String typeValue = "RF Connection";
		String subDDValue = "RFConnection Tier2";
		String statusType = "Open";
		String caseOwnerName = "RFConnection";
		String caseNumber = null;
		String newStatusType = "Resolved";
		String statusFromUI = null;
		String caseOwnerNameFromUI = null;
		//Login as sales support coordinator.
		loginUser(CRMSalesSupportCoordinatorUserName, CRMSalesSupportCoordinatorPassword);
		crmHomePage.selectValueFromServiceCloudDD(caseValue);
		crmHomePage.clickNewCaseButton();
		crmHomePage.fillTheDetailsAndClickSaveForNewCase(typeValue,"2","2");
		caseNumber = crmHomePage.getCaseNumberFromTab();
		crmHomePage.closeAllOpenedTabs();
		crmLogout();
		//Login as sales support manager user.
		loginUser(CRMSalesSupportManagerUserName, CRMSalesSupportManagerPassword);
		crmHomePage.enterTextInSearchFieldAndHitEnter(caseNumber);
		s_assert.assertTrue(crmHomePage.isCreatedCasePresentInSearchResult(caseNumber),"Created case number "+caseNumber+" is not present in search result");
		crmLogout();
		//Login as RF connection user.
		loginUser(CRMRFConnectionUserName, CRMRFConnectionPassword);
		crmHomePage.selectValueFromServiceCloudDD(caseValue);
		crmHomePage.selectValueFromSubDD(subDDValue);//Value not present in sub-dropdown.
		//Change case owner status
		caseNumber=crmHomePage.changeCaseOwner(statusType, "1",caseOwnerName);
		crmHomePage.enterTextInSearchFieldAndHitEnter(caseNumber);
		crmHomePage.clickCaseNumberAfterSearch(caseNumber);
		//Change status type.
		crmHomePage.changeStatusTypeAndUpdate(newStatusType);
		//Assert case owner name.
		caseOwnerNameFromUI = crmHomePage.getCaseOwnerName();
		s_assert.assertTrue(caseOwnerNameFromUI.contains(caseOwnerName), "Expected case owner name is "+caseOwnerName+" but actual on UI is "+caseOwnerNameFromUI);
		//Assert case owner status.
		statusFromUI = crmHomePage.getStatusOfCase();
		s_assert.assertTrue(statusFromUI.contains(newStatusType), "Expected case status is "+newStatusType+" but actual on UI is "+statusFromUI);
		s_assert.assertAll();
	}

	// QTest ID TC-1232 Verify OrderManagement Queue - pending case status
	@Test
	public void testVerifyOrderManagementQueue_1232q(){
		String caseValue = "Cases";
		String orderManagmentQueueValue = "Order Management Queue";
		String caseStatusType = "In Progress";
		String caseOwnerName = "Automation_ SSCoordinator";
		String caseOwnerNameFromUI = null;
		String caseNumber = null;
		String statusType = "In Progress";
		String statusTypeResolve = "Resolved";
		String statusFromUI = null;
		loginUser(CRMSalesSupportCoordinatorUserName, CRMSalesSupportCoordinatorPassword);
		crmHomePage.selectValueFromServiceCloudDD(caseValue);
		crmHomePage.selectValueFromSubDD(orderManagmentQueueValue);
		crmHomePage.setNumberOfRecordsPerPageFromDD("200");
		caseNumber = crmHomePage.changeCaseOwneAsPerStatus(caseStatusType ,caseOwnerName);
		crmHomePage.closeAllOpenedTabs();
		crmHomePage.enterTextInSearchFieldAndHitEnter(caseNumber);
		crmHomePage.clickCaseNumberAfterSearch(caseNumber);
		caseOwnerNameFromUI = crmHomePage.getCaseOwnerName();
		s_assert.assertTrue(caseOwnerNameFromUI.contains(caseOwnerName), "Expected case owner name is "+caseOwnerName+" but actual on UI is "+caseOwnerNameFromUI);
		crmHomePage.changeStatusTypeAndUpdate(statusTypeResolve);
		statusFromUI = crmHomePage.getStatusOfCase();
		s_assert.assertTrue(statusFromUI.contains(statusTypeResolve), "Expected case status is "+statusType+" but actual on UI is "+statusFromUI);
		s_assert.assertAll();
	}


}