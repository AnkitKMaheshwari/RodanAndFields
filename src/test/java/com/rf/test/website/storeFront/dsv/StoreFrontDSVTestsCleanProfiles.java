package com.rf.test.website.storeFront.dsv;

import org.testng.annotations.Test;

import com.rf.pages.website.dsv.DSVStoreFrontBillingInfoPage;
import com.rf.pages.website.dsv.DSVStoreFrontHomePage;
import com.rf.pages.website.dsv.DSVStoreFrontShippingInfoPage;
import com.rf.test.website.RFDSVStoreFrontWebsiteBaseTest;

public class StoreFrontDSVTestsCleanProfiles extends RFDSVStoreFrontWebsiteBaseTest{
	private DSVStoreFrontHomePage dsvStoreFrontHomePage;
	private DSVStoreFrontBillingInfoPage dsvStoreFrontBillingInfoPage;
	private DSVStoreFrontShippingInfoPage dsvStoreFrontShippingInfoPage;

	@Test(groups="consultant", priority=1)
	public void cleanAllShippingProfilesConsultant(){
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontShippingInfoPage= dsvStoreFrontHomePage.clickShippingInfoLinkFromWelcomeDropDown();
		int totalShippingProfiles = dsvStoreFrontShippingInfoPage.getTotalShippingProfiles();
		for(int i=1;i<=totalShippingProfiles;i++){
			if(dsvStoreFrontShippingInfoPage.areMoreShippingProfilesLeftForDeletion()==true){
				dsvStoreFrontShippingInfoPage.deleteShippingOrBillingProfiles();
			}
			else{
				break;
			}

		}
		s_assert.assertAll();
	}

	@Test(groups="consultant", priority=2)
	public void cleanAllBillingProfilesConsultant(){
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontBillingInfoPage = dsvStoreFrontHomePage.clickBillingInfoLinkFromWelcomeDropDown();
		int totalBillingProfiles = dsvStoreFrontBillingInfoPage.getTotalBillingProfiles();
		for(int i=1;i<=totalBillingProfiles;i++){
			if(dsvStoreFrontBillingInfoPage.areMoreBillingProfilesLeftForDeletion()==true){
				dsvStoreFrontBillingInfoPage.deleteShippingOrBillingProfiles();
			}
			else{
				break;
			}

		}
		s_assert.assertAll();
	}

	@Test(groups="pc", priority=3)
	public void cleanAllShippingProfilesPC(){
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontShippingInfoPage= dsvStoreFrontHomePage.clickShippingInfoLinkFromWelcomeDropDown();
		int totalShippingProfiles = dsvStoreFrontShippingInfoPage.getTotalShippingProfiles();
		for(int i=1;i<=totalShippingProfiles;i++){
			if(dsvStoreFrontShippingInfoPage.areMoreShippingProfilesLeftForDeletion()==true){
				dsvStoreFrontShippingInfoPage.deleteShippingOrBillingProfiles();
			}
			else{
				break;
			}

		}
		s_assert.assertAll();
	}

	@Test(groups="pc", priority=4)
	public void cleanAllBillingProfilesPC(){
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontBillingInfoPage = dsvStoreFrontHomePage.clickBillingInfoLinkFromWelcomeDropDown();
		int totalBillingProfiles = dsvStoreFrontBillingInfoPage.getTotalBillingProfiles();
		for(int i=1;i<=totalBillingProfiles;i++){
			if(dsvStoreFrontBillingInfoPage.areMoreBillingProfilesLeftForDeletion())
				dsvStoreFrontBillingInfoPage.deleteShippingOrBillingProfiles();
			else
				break;
		}
		s_assert.assertAll();
	}

	@Test(groups="rc", priority=5)
	public void cleanAllShippingProfilesRC(){
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontShippingInfoPage= dsvStoreFrontHomePage.clickShippingInfoLinkFromWelcomeDropDown();
		int totalShippingProfiles = dsvStoreFrontShippingInfoPage.getTotalShippingProfiles();
		for(int i=1;i<=totalShippingProfiles;i++){
			if(dsvStoreFrontShippingInfoPage.areMoreShippingProfilesLeftForDeletion()==true){
				dsvStoreFrontShippingInfoPage.deleteShippingOrBillingProfiles();
			}
			else{
				break;
			}

		}
		s_assert.assertAll();
	}

	@Test(groups="rc", priority=6)
	public void cleanAllBillingProfilesRC() throws Exception{
		dsvStoreFrontHomePage = new DSVStoreFrontHomePage(driver);
		dsvStoreFrontHomePage.clickWelcomeDropDown();
		dsvStoreFrontBillingInfoPage = dsvStoreFrontHomePage.clickBillingInfoLinkFromWelcomeDropDown();
		int totalBillingProfiles = dsvStoreFrontBillingInfoPage.getTotalBillingProfiles();
		for(int i=1;i<=totalBillingProfiles;i++){
			if(dsvStoreFrontBillingInfoPage.areMoreBillingProfilesLeftForDeletion())
				dsvStoreFrontBillingInfoPage.deleteShippingOrBillingProfiles();
			else
				break;
		}
		s_assert.assertAll();
	}
}
