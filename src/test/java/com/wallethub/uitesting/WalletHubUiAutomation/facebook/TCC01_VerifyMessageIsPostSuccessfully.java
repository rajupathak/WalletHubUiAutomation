package com.wallethub.uitesting.WalletHubUiAutomation.facebook;

import java.io.IOException;



import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.wallethub.uitesting.WalletHubUiAutomation.testBase.TestBase;
import com.wallethub.uitesting.WalletHubUiAutomation.uiAction.FacebookLoginPage;

public class TCC01_VerifyMessageIsPostSuccessfully extends TestBase {

	FacebookLoginPage fbookObject;
	TestBase obj = new TestBase();
	
	@BeforeTest
	public void openTheBrowser() throws IOException{
		
		obj.init();
		
		obj.selectBrowser(TestBase.getProperties("browserName"));
		obj.deleteCookie(driver);
		obj.getURL(TestBase.getProperties("url"));
		obj.getScreenShot("Login Page");
		
		obj.implicitWait(20);
	}
	
	@Test
	public void faceBookTimLinePostValidation() throws IOException{
		
		fbookObject= new FacebookLoginPage(driver);
		fbookObject.loginWithValidCredentials(TestBase.getProperties("userName"), TestBase.getProperties("password"),driver);
		
		fbookObject.postmessageOnTimeline(TestBase.getProperties("Message"));
		
	}
	
	@AfterTest
	
	public void closetheBrowser(){
		driver.quit();
	}
}
