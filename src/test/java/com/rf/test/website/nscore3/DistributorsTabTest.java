package com.rf.test.website.nscore3;

import java.util.List;
import java.util.Map;
import org.testng.annotations.Test;
import com.rf.core.utils.DBUtil;
import com.rf.core.website.constants.dbQueries.DBQueries_RFL;
import com.rf.pages.website.nscore.NSCore3HomePage;
import com.rf.test.website.RFNSCoreWebsiteBaseTest;

public class DistributorsTabTest extends RFNSCoreWebsiteBaseTest{
	private NSCore3HomePage nscore3HomePage;
	private String RFL_DB = null;
	
	// QTest ID TC-234 Distributors-Search by UserID
	@Test
	public void testDistributorsSearchByUserID_234() {
		RFL_DB = driver.getDBNameRFL();
		String userID = null;
		String firstName = null;
		String firstNameFromUI = null;
		String lastName = null;
		String lastNameFromUI = null;
		String accountNumber=null;
		String accountStatus=null;
		String distributorTab = "Distributors";
		String searchByFieldOndistributorTab = "User ID";
		List<Map<String, Object>> randomConsultantList =  null;
		nscore3HomePage = new NSCore3HomePage(driver);
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_EMAILID,RFL_DB);
		userID = (String) getValueFromQueryResult(randomConsultantList, "EmailAddress");
		accountNumber = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountNumber"));
		firstName = (String) getValueFromQueryResult(randomConsultantList, "FirstName");
		lastName = (String) getValueFromQueryResult(randomConsultantList, "LastName");
		nscore3HomePage.clickTab(distributorTab);
		nscore3HomePage.selectValueFromNameDDOnDistributorTab(searchByFieldOndistributorTab);
		nscore3HomePage.enterSearchForFieldOnDistributorTab(userID);
		nscore3HomePage.clickSearchOnDistributorTab();
		//verify all rows contains the searched complete name.
		firstNameFromUI=nscore3HomePage.getFirstNameOnDistributorTabThroughAccountNumber(accountNumber);
		lastNameFromUI=nscore3HomePage.getLastNameOnDistributorTabThroughAccountNumber(accountNumber);
		s_assert.assertTrue(firstNameFromUI.equalsIgnoreCase(firstName)," Expected Customer First name"+firstName+" While actual on UI is: "+firstNameFromUI);
		//Verify Last name
		s_assert.assertTrue(lastNameFromUI.equalsIgnoreCase(lastName)," Expected Customer last name"+lastName+" While actual on UI is: "+lastNameFromUI);
		// Verify Account Status of User
		accountStatus=nscore3HomePage.getAccountStatusOnDistributorTabThroughAccountNumber(accountNumber);
		s_assert.assertTrue(accountStatus.contains("Active"),"Account status of the user is not Active");
		s_assert.assertAll();
	}

	// QTest ID TC-236 Distributors-Search by SponsorID
	@Test
	public void testDistributorsSearchBySponsorID_236() {
		RFL_DB = driver.getDBNameRFL();
		String distributorTab = "Distributors";
		String searchByFieldOndistributorTab = "Sponsor ID";
		String accountNumber = null;
		String firstName = null;
		String lastName = null;
		String emailAddress = null;
		String sponserId = null;
		String sponserAccountNumber = null;
		String cellValue =null;
		String completeName=null;
		String firstNameColumnNumberFromName ="First Name";
		String LastNameColumnNumberFromName ="Last Name";
		String accountNumberColumnNumberFromName ="Account No.";
		String emailIdColumnNumberFromName = "E-Mail";
		List<Map<String, Object>> randomConsultantList =  null;
		List<Map<String, Object>> randomSponserAccountNumberList =  null;
		nscore3HomePage = new NSCore3HomePage(driver);
		randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_EMAILID,RFL_DB);
		emailAddress = (String) getValueFromQueryResult(randomConsultantList, "EmailAddress");
		accountNumber = String.valueOf(getValueFromQueryResult(randomConsultantList, "AccountNumber"));
		firstName = (String) getValueFromQueryResult(randomConsultantList, "FirstName");
		lastName = (String) getValueFromQueryResult(randomConsultantList, "LastName");
		sponserId = String.valueOf(getValueFromQueryResult(randomConsultantList, "SponsorID"));
		completeName = firstName+" "+lastName;
		randomSponserAccountNumberList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_SPONSER_ACCOUNT_NUMBER_FROM_SPONSER_ID, sponserId),RFL_DB);
		sponserAccountNumber = (String) getValueFromQueryResult(randomSponserAccountNumberList, "AccountNumber");
		nscore3HomePage.clickTab(distributorTab);
		nscore3HomePage.selectValueFromNameDDOnDistributorTab(searchByFieldOndistributorTab);
		nscore3HomePage.enterSearchForFieldOnDistributorTab(sponserAccountNumber);
		nscore3HomePage.clickSearchOnDistributorTab();
		//Get Row number having complete name.
		String rowNumber=nscore3HomePage.getRowNumberHavingTheCompleteName(completeName);
		int rowToProcess = Integer.parseInt(rowNumber);

		//Verify First name
		int firstNameColumnNumber=nscore3HomePage.getColumnNumberHavingExpectedColumnNameOnDistributorPage(firstNameColumnNumberFromName);
		cellValue=nscore3HomePage.getCellValueOnDistributorPage(rowToProcess, firstNameColumnNumber);
		s_assert.assertTrue(cellValue.equalsIgnoreCase(firstName)," Expected Customer first name"+firstName+" is not present in row and column"+firstNameColumnNumber+" While actual on UI is: "+cellValue);
		//Verify Last name
		int lastNameColumnNumber=nscore3HomePage.getColumnNumberHavingExpectedColumnNameOnDistributorPage(LastNameColumnNumberFromName);
		cellValue=nscore3HomePage.getCellValueOnDistributorPage(rowToProcess, lastNameColumnNumber);
		s_assert.assertTrue(cellValue.equalsIgnoreCase(lastName)," Expected Customer last name"+lastName+" is not present in row and column"+lastNameColumnNumber+" While actual on UI is: "+cellValue);
		//Verify account number column.
		int accountNumberColumnNumber=nscore3HomePage.getColumnNumberHavingExpectedColumnNameOnDistributorPage(accountNumberColumnNumberFromName);
		cellValue=nscore3HomePage.getCellValueOnDistributorPage(rowToProcess, accountNumberColumnNumber);
		s_assert.assertTrue(cellValue.equalsIgnoreCase(accountNumber)," Expected Customer account number"+accountNumber+" is not present in row and column"+accountNumberColumnNumber+" While actual on UI is: "+cellValue);
		//Verify Email address.
		int emailIdColumnNumber=nscore3HomePage.getColumnNumberHavingExpectedColumnNameOnDistributorPage(emailIdColumnNumberFromName);
		cellValue=nscore3HomePage.getCellValueOnDistributorPage(rowToProcess, emailIdColumnNumber);
		s_assert.assertTrue(cellValue.equalsIgnoreCase(emailAddress)," Expected Customer email address "+emailAddress+" is not present in row and column"+emailIdColumnNumber+" While actual on UI is: "+cellValue);
		s_assert.assertAll();
	}
	
	// QTest ID TC-237 Distributors-Search by Location
		@Test
		public void testDistributorsSearchByLocation_237() {
			RFL_DB = driver.getDBNameRFL();
			String emailID = null;
			String cityFromDB=null;
			String distributorTab = "Distributors";
			String searchByFieldOndistributorTab = "Location";
			List<Map<String, Object>> randomConsultantList =  null;
			List<Map<String, Object>> randomConsultantAddressList =  null;
			nscore3HomePage = new NSCore3HomePage(driver);

			randomConsultantList = DBUtil.performDatabaseQuery(DBQueries_RFL.GET_RANDOM_ACTIVE_CONSULTANT_EMAILID,RFL_DB);
			emailID = (String) getValueFromQueryResult(randomConsultantList, "EmailAddress");
			
			randomConsultantAddressList = DBUtil.performDatabaseQuery(DBQueries_RFL.callQueryWithArguement(DBQueries_RFL.GET_ACCOUNT_ADDRESS_DETAILS_FOR_ACCOUNT_INFO_QUERY_RFL,emailID), RFL_DB);
			cityFromDB = (String) getValueFromQueryResult(randomConsultantAddressList, "City");
			
			nscore3HomePage.clickTab(distributorTab);
			nscore3HomePage.selectValueFromNameDDOnDistributorTab(searchByFieldOndistributorTab);
			nscore3HomePage.enterSearchForFieldOnDistributorTab(cityFromDB);
			nscore3HomePage.clickSearchOnDistributorTab();
			
			//verify all rows contains the searched location name.
			s_assert.assertTrue(nscore3HomePage.isAllRowsContainsTheLocationName(cityFromDB),"Expected Location name on UI is not present");
			s_assert.assertAll();
		}
		
}
