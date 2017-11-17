package com.rf.pages.website.nscore;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.rf.core.driver.website.RFWebsiteDriver;
import com.rf.core.utils.CommonUtils;

public class NSCore4ProductsTabPage extends NSCore4RFWebsiteBasePage{

	private static final Logger logger = LogManager
			.getLogger(NSCore4ProductsTabPage.class.getName());

	private static final By CREATE_A_NEW_CATALOGUE_LINK_LOC = By.linkText("Create a New Catalog");
	private static final By SAVE_CATALOGUE_BTN_LOC = By.xpath("//a[@id='btnSave']");
	private static final By CATALOGUE_SAVE_MESSAGE1_LOC = By.xpath("//div[@id='messageCenter']");
	private static final By NAME_TXT_FIELD_LOC = By.id("txtName");
	private static final By STORE_FRONT_CHECKBOX_LOC = By.xpath("//td[contains(text(),'Store')]/following-sibling::td/input[6]");
	private static final By QUICK_PRODUCT_ADD_TXT_FIELD_LOC = By.id("productQuickAdd");
	private static final By ADD_LINK_LOC = By.xpath("//a[@id='btnQuickAdd']");
	private static final By ADDED_PRODUCT_LOC = By.xpath("//table[@id='catalogItems']/tbody/tr");
	private static final By CATALOGUE_LIST_LOC = By.xpath("//table[@class='DataGrid']//tr");
	private static final By CATEGORY_TREES_LINK_LOC = By.linkText("Category Trees");
	private static final By CATALOGUE_MANAGEMENT_LINK_LOC = By.linkText("Catalog Management");
	private static final By LIST_OF_CATALOG_LOC = By.xpath("//tr[@class='GridColHead']//th[contains(text(),'Used By (Store Front/Catalog)')]//following::tr[1]/td[3]/a");
	private static final By CREATE_TREE_CONFIRMATION_MSG_BOX_LOC = By.xpath("//div[@id='categoryMessageCenter']");
	private static final By TREE_NAME_TXT_FIELD_LOC = By.id("treeName");
	private static final By CREATE_TREE_BTN_LOC = By.id("btnCreateTree");
	private static final By SAVE_CATEGORY_BTN_LOC = By.xpath("//a[@id='btnSave']");
	public static final By CREATE_NEW_CATEGORY_TREE_LINK_LOC = By.xpath("//div[@class='SectionHeader']/a[contains(text(),'Create a New Category Tree')]");
	private static final By CATEGORY_DETAILS_TXT_BOX_LOC = By.id("txtName");
	private static final By CATEGORY_DETAILS_SAVE_BTN_LOC = By.xpath("//a[@id='btnSave']");
	private static final By SUB_CATEGORY_LOC = By.xpath("//div[@id='categoryTree']//li/a");
	private static final By BROWSE_CATEGORY_TREES_LINK_LOC =  By.xpath("//div[@class='SectionHeader']/a[contains(text(),'Browse Category Trees')]");	
	private static final By DELETE_SELECTED_BTN_LOC = By.xpath("//a[text()='Delete Selected']");
	private static final By PRODUCT_MANAGEMENT_SUBLINK_LOC = By.xpath("//li[@class='SubTab']//a[contains(text(),'Management')]");
	private static final By PRODUCT_TYPE_DD_LOC = By.xpath("//select[@id='sProductType']");
	private static final By PRODUCT_STATUS_DD_LOC = By.xpath("//select[@id='sStatus']");
	private static final By FIRST_PRODUCT_NAME_LOC = By.xpath("//table[@id='products']/tbody/tr[1]/td/a");
	private static final By PRODUCT_AVAILIBLITY_CHECKBOX_LOC = By.xpath("//p[@id='catalogList']/span[1]/following-sibling::b[text()='No Autoship - All Customer Types']/preceding-sibling::input[1]");
	private static final By PRODUCT_AVAILABILYT_SAVE_BTN_LOC = By.xpath("//a[@id='btnSave']");
	private static final By SAVE_MSG_LOC = By.xpath("//div[@id='messageCenterModal' and contains(@style,'display: block')]");
	private static final By PRODUCT_TYPE_DD_AT_DETAILS_LOC = By.xpath("//select[@id='sProductType']");
	private static final By PRICING_TXT_BOX1_LOC = By.id("priceType1");
	private static final By PRICING_TXT_BOX3_LOC = By.id("priceType3");
	private static final By PRICING_TXT_BOX4_LOC = By.id("priceType4");
	private static final By PRICING_TXT_BOX5_LOC = By.id("priceType5");
	private static final By PRICING_SAVE_BTN_LOC = By.xpath("//a[@id='btnSavePrices']");
	private static final By CMS_SHORT_DESC_TEXTAREA_LOC = By.xpath("//textarea[@id='txtShortDescription']");
	private static final By CMS_SHORT_DESC_SAVE_BTN_LOC = By.xpath("//a[@id='btnSaveDescriptions']");
	private static final By ENTER_SKU_TEXT_BOX_LOC = By.id("txtProductRelationshipSearch");
	private static final By SKU_SUGGESTION_LIST_LOC = By.xpath("//div[contains(@class,'jsonSuggestResults')]//p");
	private static final By RELATION_ADD_BTN_LOC = By.xpath("//a[@id='btnAddRelation']/img");
	private static final By REMOVE_SELECTED_RELATION_BTN_LOC = By.xpath("//a[@id='btnRemoveRelation']");
	private static final By ADD_A_NEW_PRODUCT_TYPE_LINK_LOC = By.xpath("//a[text()='Add new product type']");
	private static final By PRODUCT_TYPE_NAME_TEXT_BOX_LOC = By.id("name");
	private static final By PRODUCT_TYPE_SAVE_BTN_LOC = By.xpath("//a[@id='btnSave']");
	private static final By DROPDOWN_TEXT_TXT_BOX_LOC = By.id("txtDropDown");
	private static final By SKU_WITHOUT_FULFILLMENT_LEVEL_LOC = By.xpath("//table[@id='products']/descendant::td[contains(text(),'Fulfillment details cannot be found.')][1]//preceding-sibling::td//input[@id='productName']/ancestor::td[1]");
	private static final By ITEM_NUM_TF_LOC = By.id("itemNumber");
	private static final By UPC_CODE_TF_LOC = By.id("UPC");
	private static final By LOT_NUM_TF_LOC = By.id("lotNumber");
	private static final By SAVE_FULLFILMENT_BTN_LOC = By.id("saveFulfillment");
	private static final By FULLFILMENT_DETAILS_SAVED_MSG_lOC = By.xpath("//div[contains(text(),'Product Fulfillment Details were saved!')]");
	private static final By FULLFILMENT_DETAILS_DELETE_MSG_lOC = By.xpath("//div[contains(text(),'Delete successful')]");
	private static final By DELETE_PRODUCT_TYPE_BTN_LOC = By.id("btnDelete"); 
	private static final By PRODUCT_SKU_TF_LOC = By.id("txtSKU");
	private static final By PRODUCT_NAME_TF_LOC = By.id("txtName");
	private static final By PRODUCT_WEIGHT_TF_LOC = By.id("txtWeight");
	private static final By ACTION_SUCCESS_MSG_LOC = By.xpath("//div[@id='messageCenter']//div[contains(@id,'message')]");
	private static final By ALL_CATEGORIES_CHECKBOX_LOC = By.xpath("//div[contains(@id,'categoryContainer')]//span[@id]/preceding-sibling::input[1]");
	private static final By FIRST_PRODUCT_SKU_LOC = By.xpath("//table[@id='products']/descendant::tr[2]/td[2]");
	private static final By RELATION_TYPE_DD_LOC = By.id("relationshipType");
	private static final By ALL_CARTS_RADIO_BTN_LOC = By.xpath("//label[contains(text(),'All Carts')]/preceding-sibling::input[1]");
	private static final By PRODUCT_OVERVIEW_LINK_LOC = By.xpath("//span[contains(text(),'Product Overview')]"); 
	private static final By PRICING_TXT_BOX2_LOC = By.id("priceType2");

    private static String activateDeActivateSelectedLinkLoc = "//a[text()='%s']";
	private String skuInCurrentRelationShipBoxLoc = "//select[@id='relations']//option[contains(text(),'%s')]";
	private String linkInLeftNavLoc = "//ul[@class='SectionLinks']//span[contains(text(),'%s')]";
	private String itemNumForSpecificSKUlOC = "//td[contains(text(),'%s')]//following-sibling::td[3]";
	private String NoFullfilmentRecordTextForSpecificSkuLoc = "//td[contains(text(),'%s')]//following-sibling::td[contains(text(),'Fulfillment details cannot be found')]";
	private String updateLinkForSpecificSkuLoc = "//td[contains(text(),'%s')]//following-sibling::td/a[contains(text(),'Update')][1]";
	private String deleteLinkForSpecificSkuLoc = "//td[contains(text(),'%s')]//following-sibling::td/a[contains(text(),'Delete')][1]";
	private String addLinkForSpecificSkuLoc = "//td[contains(text(),'%s')]//following-sibling::td/a[contains(text(),'Add')][1]";
	private static String sublinkAccordingToLabelLoc = "//h3[contains(text(),'%s')]//a";
	private static String chkBoxOfCategoryLoc = "//span[contains(text(),'%s')]/preceding::input[1]";
	private static String CatalogueListCatalogNameLOC = "//tr[@class='GridColHead']//th[contains(text(),'Catalog Name')]/following::tr[%s]/td[3]";
	private static String newlyCreatedCatalogLoc = "//tr[@class='GridColHead']//th[contains(text(),'Catalog Name')]/following::tr//a[contains(text(),'%s')]";
	private static String relationProductLoc = "//select[@id='relations']/option[contains(text(),'%s')]";
	private static String productCheckBoxLoc = "//table[@id='products']/tbody//a[text()='%s']/preceding::input[1]";
	private static String activated_product_loc = "//table[@id='products']/tbody/tr[%s]/td[2]/a";
	private static String newlyCreatedTreeLoc = "//a[contains(text(),'%s')]";
	private static String newTreeCheckBoxLoc = "//a[contains(text(),'%s')]/preceding::input[1]";
	private static String newProductTypeInListLoc = "//div[@class='SectionHeader']/following::ul//a[text()='%s']";
	private static String productManagementOptionsLoc = "//li[@class='SubTab']//a[contains(text(),'Management')]/following::span[text()='%s']";


	public NSCore4ProductsTabPage(RFWebsiteDriver driver) {
		super(driver);
	}

	public void clickCreateANewCatalogLink() {
		driver.click(CREATE_A_NEW_CATALOGUE_LINK_LOC);
		logger.info("create a new catalogue link is clicked");
	}

	public void enterCatalogInfo(String catalogueName) {
		driver.clear(NAME_TXT_FIELD_LOC);
		driver.type(NAME_TXT_FIELD_LOC, catalogueName);
		logger.info("name text field entered with: "+catalogueName);
		driver.click(STORE_FRONT_CHECKBOX_LOC);
	}

	public void clickSaveCatalogBtn() {
		driver.click(SAVE_CATALOGUE_BTN_LOC);
		logger.info("save button clicked");
	}

	public void enterSkuQuickProductAddField(String sKU) {
		driver.type(QUICK_PRODUCT_ADD_TXT_FIELD_LOC, sKU);
		driver.click(ADD_LINK_LOC);
		logger.info("product has been added and add link clicked");
		driver.waitForElementPresent(ADDED_PRODUCT_LOC);
		clickSaveCatalogBtn();
	}

	public void clickCatalogManagementLink() {
		driver.click(CATALOGUE_MANAGEMENT_LINK_LOC);
		logger.info("catalog management link is clicked");
	}

	public boolean isNewCatalogPresentInList(String catalogueName) {
		int numberOfRows = driver.findElements(CATALOGUE_LIST_LOC).size();
		boolean status = false;
		for(int i = 2;i<=numberOfRows;i++){
			String listName = driver.findElement(By.xpath(String.format(CatalogueListCatalogNameLOC,i))).getText();
			if(listName.contains(catalogueName)){
				status = true;
				break;
			}
		}
		return status;
	}

	public void clicknewlyCreatedCatalogName(String catalogName) {
		driver.click(By.xpath(String.format(newlyCreatedCatalogLoc,catalogName)));
		logger.info("catalog name is clicked to update");
	}

	public boolean isSuccessMessagePresent() {
		driver.waitForElementToBeVisible(CATALOGUE_SAVE_MESSAGE1_LOC, 2);
		if(driver.findElement(CATALOGUE_SAVE_MESSAGE1_LOC).isDisplayed()){
			return true;}
		else
			return false;

	}

	public void clickCategoryTreesLink() {
		driver.quickWaitForElementPresent(CATEGORY_TREES_LINK_LOC);
		driver.click(CATEGORY_TREES_LINK_LOC);
		logger.info("Category Trees link is clicked");
		driver.waitForPageLoad();
	}

	public boolean verifyCurrentPage(String pageName) {
		return driver.getCurrentUrl().contains(pageName);
	}

	public boolean isCatalogAvailableOnPage() {
		int numberOFCatalogPresent = driver.findElements(LIST_OF_CATALOG_LOC).size();
		if(numberOFCatalogPresent>0){
			return true;}
		else
			return false;
	}

	public void clickCreateANewCategoryTree() {
		driver.click(CREATE_NEW_CATEGORY_TREE_LINK_LOC);
		logger.info("create new Category Trees link is clicked");
	}

	public void enterTreeNameAndClickCreateTreeBtn(String treeName) {
		driver.type(TREE_NAME_TXT_FIELD_LOC, treeName);
		logger.info("tree name entered: "+treeName);
		driver.click(CREATE_TREE_BTN_LOC);
		logger.info("create tree btn clicked");
	}

	public boolean verifyConfirmationMessage() {
		return driver.findElement(CREATE_TREE_CONFIRMATION_MSG_BOX_LOC).isDisplayed();
	}

	public void enterLanguageNameAndClickSave(String subTreeName) {
		driver.type(CATEGORY_DETAILS_TXT_BOX_LOC,subTreeName);
		logger.info("category details text entered by: "+subTreeName);
		driver.click(CATEGORY_DETAILS_SAVE_BTN_LOC);
		logger.info("category details save btn clicked");
		driver.waitForPageLoad();
	}

	public boolean isSubTreeCreatedUnderTree() {
		return driver.isElementPresent(SUB_CATEGORY_LOC);
	}

	public void clickBrowseCategoryTreesLink() {

		driver.quickWaitForElementPresent(BROWSE_CATEGORY_TREES_LINK_LOC);
		driver.click(BROWSE_CATEGORY_TREES_LINK_LOC);
		logger.info("browse category trees link is clickeed");
	}

	public void selectAndDeleteCreatedTree(String treeName) {
		driver.click(By.xpath(String.format(newTreeCheckBoxLoc,treeName)));
		logger.info("checkbox checked");
		driver.click(DELETE_SELECTED_BTN_LOC);
		logger.info("delete selected link is clicked");
	}

	public void handleAlertPop(String treeName) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 2);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
			logger.info("popup ok button clicked");
			driver.pauseExecutionFor(5000);
			driver.waitForPageLoad();
		} catch (Exception e) {
			//exception handling
		}
	}

	public void hoverProductManagementAndClickOption(String option) {
		Actions actions = new Actions(RFWebsiteDriver.driver);
		driver.waitForElementPresent(PRODUCT_MANAGEMENT_SUBLINK_LOC); 
		WebElement productManagement = driver.findElement(PRODUCT_MANAGEMENT_SUBLINK_LOC);
		WebElement clickLink = driver.findElement(By.xpath(String.format(productManagementOptionsLoc, option)));
		actions.moveToElement(productManagement).pause(1000).click(clickLink).build().perform();
		driver.waitForPageLoad();
	}

	public void chooseProductTypeAndStatus(String productType, String productStatus) {
		Select selectTypes = new Select(driver.findElement(PRODUCT_TYPE_DD_LOC));
		selectTypes.selectByVisibleText(productType);
		logger.info("product type selected is:" +productType);
		driver.waitForNSCore4LoadingImageToDisappear();
		Select selectStatus = new Select(driver.findElement(PRODUCT_STATUS_DD_LOC));
		selectStatus.selectByVisibleText(productStatus);
		logger.info("status type is selected is: "+productStatus);
		driver.waitForNSCore4LoadingImageToDisappear();
		driver.waitForPageLoad();
	}

	public String getFirstProductNameFromBrowseProducts() {
		return driver.findElement(FIRST_PRODUCT_NAME_LOC).getText();
	}


	public void selectProductAndClickActivateSelectedLink(String productName) {
		driver.waitForElementPresent(By.xpath(String.format(productCheckBoxLoc, productName)));
		driver.click(By.xpath(String.format(productCheckBoxLoc, productName)));
		logger.info("product checkbox checked");
	}


	public void clickActivateOrDeActivateSelectedLink(String string) {
		driver.click(By.xpath(String.format(activateDeActivateSelectedLinkLoc, string)));
		logger.info("link clicked is:"+string);
		driver.waitForNSCore4LoadingImageToDisappear();
	}

	public boolean verifyProductBecomeActivated(String firstProductName, String firstProductNameInList){
		if(!firstProductName.equals(firstProductNameInList)){
			return true;
		}
		else
			return false;
	}



	public void selectARandomProduct() {
		int randomNumber = CommonUtils.getRandomNum(2, 9);
		driver.click(By.xpath(String.format(activated_product_loc, randomNumber)));
		logger.info("random product clicked");
	}

	public void editProductAvailablityAndSave() {
		driver.waitForElementPresent(PRODUCT_AVAILIBLITY_CHECKBOX_LOC);
		driver.click(PRODUCT_AVAILIBLITY_CHECKBOX_LOC);
		driver.quickWaitForElementPresent(PRODUCT_AVAILABILYT_SAVE_BTN_LOC);
		driver.click(PRODUCT_AVAILABILYT_SAVE_BTN_LOC);
		logger.info("product availability save link clicked");
	}


	public boolean verifySavedMessagePresent() {
		driver.quickWaitForElementPresent(SAVE_MSG_LOC);
		return driver.isElementPresent(SAVE_MSG_LOC);
	}

	public void editProductDetailsAndSave() {
		Select select = new Select(driver.findElement(PRODUCT_TYPE_DD_AT_DETAILS_LOC));
		select.selectByVisibleText("Kits");
		driver.quickWaitForElementPresent(PRODUCT_AVAILABILYT_SAVE_BTN_LOC);
		driver.click(PRODUCT_AVAILABILYT_SAVE_BTN_LOC); 
		logger.info("product details save btn clicked");
	}

	public void editPricingAndSave() {
		String pricingValue = "200.00";
		driver.clear(PRICING_TXT_BOX1_LOC);
		driver.type(PRICING_TXT_BOX1_LOC, pricingValue);
		driver.clear(PRICING_TXT_BOX3_LOC);
		driver.type(PRICING_TXT_BOX3_LOC, pricingValue);
		driver.clear(PRICING_TXT_BOX4_LOC);
		driver.type(PRICING_TXT_BOX4_LOC, pricingValue);
		driver.clear(PRICING_TXT_BOX5_LOC);
		driver.type(PRICING_TXT_BOX5_LOC, pricingValue);
		driver.quickWaitForElementPresent(PRICING_SAVE_BTN_LOC);
		driver.click(PRICING_SAVE_BTN_LOC);
		logger.info("pricing save btn clicked");
	}

	public void editCMSDescriptionAndSave() {
		driver.clear(CMS_SHORT_DESC_TEXTAREA_LOC);
		driver.type(CMS_SHORT_DESC_TEXTAREA_LOC, "edit short description of product");
		driver.pauseExecutionFor(10000);
		driver.quickWaitForElementPresent(CMS_SHORT_DESC_SAVE_BTN_LOC);
		driver.click(CMS_SHORT_DESC_SAVE_BTN_LOC);
		logger.info("CMS Description save btn clicked");
	}

	public void selectAProductAndRemoveFromRelationShip(String sKU) {
		driver.clear(ENTER_SKU_TEXT_BOX_LOC);
		driver.type(ENTER_SKU_TEXT_BOX_LOC, sKU);
		logger.info("sku added is: "+sKU);
		driver.quickWaitForElementPresent(SKU_SUGGESTION_LIST_LOC);
		driver.click(SKU_SUGGESTION_LIST_LOC);
		driver.waitForElementPresent(RELATION_ADD_BTN_LOC);
		driver.click(RELATION_ADD_BTN_LOC);
		logger.info("relation add button clicked");
		driver.waitForElementPresent(By.xpath(String.format(relationProductLoc, sKU)));
		driver.click(By.xpath(String.format(relationProductLoc, sKU)));
		driver.click(REMOVE_SELECTED_RELATION_BTN_LOC);
		logger.info("remove btn clicked");
		driver.pauseExecutionFor(5000);
	}

	public boolean isRelationPresentInBox(String skuAddedInTextBox) {
		if(driver.isElementPresent(By.xpath(String.format(relationProductLoc, skuAddedInTextBox))))
			return false;
		else 
			return true;
	}

	public void clickAddANewProductTypeLink() {
		driver.quickWaitForElementPresent(ADD_A_NEW_PRODUCT_TYPE_LINK_LOC);
		driver.click(ADD_A_NEW_PRODUCT_TYPE_LINK_LOC);
		logger.info("add a new product type link is clicked under header");
	}

	public void enterProductTypeAndSave(String newProductType) {
		// TODO Auto-generated method stub
		driver.type(PRODUCT_TYPE_NAME_TEXT_BOX_LOC, newProductType);
		logger.info("product type entered in text box as:"+newProductType);
		driver.click(PRODUCT_TYPE_SAVE_BTN_LOC);
		logger.info("Product type save btn clicked");
	}

	public boolean isNewlyAddedProductTypePresentInList(String newProductTypeName) {
		return driver.isElementPresent(By.xpath(String.format(newProductTypeInListLoc, newProductTypeName)));
	}

	public void clickOnNewlyAddedType(String newProductTypeName, String productTypeEditedName) {
		driver.click(By.xpath(String.format(newProductTypeInListLoc, newProductTypeName)));
		enterProductTypeAndSave(productTypeEditedName);
	}

	public void clickOnEditedNameAndDelete(String productTypeEditedName) {
		driver.click(By.xpath(String.format(newProductTypeInListLoc, productTypeEditedName)));
		driver.click(By.xpath("//a[@id='btnDelete']"));
		logger.info("delete btn clicked for edited name");
	}

	public void clickSublinkAccordingToLabel(String labelName){
		driver.waitForElementPresent(By.xpath(String.format(sublinkAccordingToLabelLoc, labelName)));
		driver.click(By.xpath(String.format(sublinkAccordingToLabelLoc, labelName)));
		logger.info("Clicked on the sublink of "+labelName);
	}

	public void changeCategoriesAndSave(String categoryName){
		driver.waitForElementPresent(By.xpath(String.format(chkBoxOfCategoryLoc, categoryName)));
		driver.click(By.xpath(String.format(chkBoxOfCategoryLoc, categoryName)));
		logger.info(categoryName+" category selected");
		driver.click(SAVE_CATEGORY_BTN_LOC);
		logger.info("Save button clicked");
	}

	public void enterProductPrioritiesANdClickOnSave(String txtName){
		driver.pauseExecutionFor(2000);
		driver.waitForElementPresent(DROPDOWN_TEXT_TXT_BOX_LOC);
		driver.type(DROPDOWN_TEXT_TXT_BOX_LOC, txtName);
		logger.info("Ingredients name entered as"+txtName);
		driver.click(SAVE_CATEGORY_BTN_LOC);
		logger.info("Save button clicked");
	}

	public String getProductSkuWithoutFulfillmentDetails(){
		return driver.getText(SKU_WITHOUT_FULFILLMENT_LEVEL_LOC).trim();
	}

	public void clickAddFulfillmentDetailsLink(String sku){
		driver.click(By.xpath(String.format(addLinkForSpecificSkuLoc,sku)));
		logger.info("Add Fulfillment Details Link Clicked for SKU : "+sku);
	}


	public void enterFulfillmentDetails(String itemNum, String upcCode, String lotNum){
		driver.type(ITEM_NUM_TF_LOC, itemNum);
		logger.info("Entered Item Number : "+itemNum);
		driver.type(UPC_CODE_TF_LOC, upcCode);
		logger.info("Entered UPC Code : "+upcCode);
		driver.type(LOT_NUM_TF_LOC, lotNum);
		logger.info("Entered Lot Number : "+lotNum);
	}


	public void clickSaveFulfillmentBtn(){
		driver.click(SAVE_FULLFILMENT_BTN_LOC);
		logger.info("Clicked Save Fulfillment Button");
	}


	public void clickUpdateLinkForSKU(String sku){
		driver.click(By.xpath(String.format(updateLinkForSpecificSkuLoc,sku)));
		logger.info("Clicked Update Link for SKU : "+sku);
	}

	public void clickDeleteLinkForSKU(String sku){
		driver.click(By.xpath(String.format(deleteLinkForSpecificSkuLoc,sku)));
		logger.info("Clicked Delete Link for SKU : "+sku);
	}


	public boolean verifySavedFulfillmentDetailsSuccessfulMsg(){
		return driver.isElementVisible(FULLFILMENT_DETAILS_SAVED_MSG_lOC);
	}

	public boolean verifyDeleteFulfillmentDetailsSuccessfulMsg(){
		return driver.isElementVisible(FULLFILMENT_DETAILS_DELETE_MSG_lOC);
	}

	public String getItemNumberForSku(String sku){
		driver.pauseExecutionFor(2000);
		return driver.getText(By.xpath(String.format(itemNumForSpecificSKUlOC,sku))).trim();
	}

	public boolean isFulfillmentDetailsNotPresentForSKU(String sku){
		return driver.isElementVisible(By.xpath(String.format(NoFullfilmentRecordTextForSpecificSkuLoc,sku)));  
	}

	public void clickOnProductTypeFromList(String productTypeName) {
		driver.click(By.xpath(String.format(newProductTypeInListLoc, productTypeName)));
	}

	public void clickDeleteProductTypeBtn() {
		driver.click(DELETE_PRODUCT_TYPE_BTN_LOC);
		logger.info("Clicked Delete Product Type Button");
	}

	public void enterNewProductDetails(String productSku, String productName, String productWeight){
		driver.type(PRODUCT_SKU_TF_LOC, productSku);
		logger.info("Product SKU entered as : "+productSku);
		driver.type(PRODUCT_NAME_TF_LOC, productName);
		logger.info("Product Name entered as : "+productName);
		driver.type(PRODUCT_WEIGHT_TF_LOC, productWeight);
		logger.info("Product Weight entered as : "+productWeight);
	}
	public String getActionSuccessMsg(){
		return driver.getText(ACTION_SUCCESS_MSG_LOC).trim();
	}


	public void clickLinkFromLeftNav(String link){
		driver.clickByJS(RFWebsiteDriver.driver,driver.findElement(By.xpath(String.format(linkInLeftNavLoc,link))));
		logger.info("Clicked on "+link+" from Left Navigation");
	}


	public void updateSKUOfProduct(String sku){
		driver.type(PRODUCT_SKU_TF_LOC, sku);
		logger.info("Product SKU entered as : "+sku);
	}


	public void enterPriceDetailsForProduct(String retailPrice,String pcPrice,String consPrice){
		driver.type(PRICING_TXT_BOX1_LOC,retailPrice);
		logger.info("Retail Price entered as : "+retailPrice);
		driver.type(PRICING_TXT_BOX2_LOC,pcPrice);
		logger.info("PC Price entered as : "+pcPrice);
		driver.type(PRICING_TXT_BOX3_LOC,consPrice);
		logger.info("Cons Price entered as : "+consPrice);
	}


	public void clickSavePricesBtn(){
		driver.click(PRICING_SAVE_BTN_LOC);
		logger.info("Clicked Save Prices Btn");
	}

	public void enterShortDescOnCMSTab(String shortDesc){
		driver.type(CMS_SHORT_DESC_TEXTAREA_LOC, shortDesc);
		logger.info("Short Description entered as : "+shortDesc);
	}


	public void clickSaveDescriptionsBtn(){
		driver.click(CMS_SHORT_DESC_SAVE_BTN_LOC);
		logger.info("Clicked on Saved Descriptions Button");
	}

	public void selectRandomCatgory(){
		List<WebElement> categories = driver.findElements(ALL_CATEGORIES_CHECKBOX_LOC);
		int randomNum = CommonUtils.getRandomNum(0,categories.size()-1);
		categories.get(randomNum).click();
		logger.info("Clicked on Category index : "+randomNum);
	}

	public void enterProductInRelationshipTB(String sku) {
		driver.clear(ENTER_SKU_TEXT_BOX_LOC);
		driver.type(ENTER_SKU_TEXT_BOX_LOC, sku);
		logger.info("SKU eneterd as : "+sku);
		driver.quickWaitForElementPresent(SKU_SUGGESTION_LIST_LOC);
		driver.click(SKU_SUGGESTION_LIST_LOC);
	}


	public String getFirstProductSKU(){
		return driver.getText(FIRST_PRODUCT_SKU_LOC).trim();
	}


	public void selectRelationTypeFromDD(String type){
		Select select = new Select(driver.findElement(RELATION_TYPE_DD_LOC));
		select.selectByVisibleText(type);
	}

	public void clickAddRelationBtn(){
		driver.click(RELATION_ADD_BTN_LOC);
		logger.info("Add Relatioj Btn Clicked");
	}


	public boolean isSKUPresentInRelationShipBox(String sku){
		return driver.isElementVisible(By.xpath(String.format(skuInCurrentRelationShipBoxLoc,sku)));
	}

	public void selectAllCartsRadioBtn(){
		driver.click(ALL_CARTS_RADIO_BTN_LOC);
		logger.info("Clicked All Carts Radio Btn");
	}

	public boolean verifyProductOverviewLinkIsPresent(){
		driver.waitForElementToBeVisible(PRODUCT_OVERVIEW_LINK_LOC,10);
		return driver.isElementVisible(PRODUCT_OVERVIEW_LINK_LOC);
	}
	
	public boolean isNewlyCreatedTreePresent(String treeName) {
		driver.waitForPageLoad();
		driver.pauseExecutionFor(2000);
		return driver.isElementPresent(By.xpath(String.format(newlyCreatedTreeLoc,treeName)));
	}

}