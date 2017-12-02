package com.wallethub.uitesting.WalletHubUiAutomation.uiAction;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.wallethub.uitesting.WalletHubUiAutomation.testBase.TestBase;

public class FacebookLoginPage extends TestBase {

	TestBase obj = new TestBase();
	public static final Logger log = Logger.getLogger(FacebookLoginPage.class
			.getName());
	WebDriver driver;
	@FindBy(xpath = ".//*[contains(@id,'email')]")
	WebElement userName;

	@FindBy(xpath = "(.//*[contains(@id,'pass')])[1]")
	WebElement password;

	@FindBy(xpath = "//table[@role='presentation']/tbody/tr[2]/td[3]/label/input[@value='Log In']")
	WebElement loginButton;

	@FindBy(xpath = "//a[@action='cancel']")
	WebElement notNow;

	@FindBy(xpath = "//div[@class='_4bl9 _42n-']/textarea [@name='xhpc_message']")
	WebElement textArea;

	@FindBy(xpath = "//div[@class='_1j2v']/div[3]/div/div[2]/div/button[@type='submit']")
	WebElement postButton;

	public FacebookLoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void loginWithValidCredentials(String UserName, String Password,
			WebDriver driver) {
		log.info("insideloginWithValidCredentials method ");
		this.driver = driver;

		obj.highLighteBackground(driver, userName);
		userName.sendKeys(UserName);

		obj.highLighteBackground(driver, password);
		password.sendKeys(Password);
		obj.getScreenShot("After Entering Valid Crdentials");
		loginButton.click();
	}

	public void postmessageOnTimeline(String message) {

		Actions action = new Actions(driver);
		notNow.click();
		obj.highLighteBackground(driver, textArea);
		action.moveToElement(textArea).click().sendKeys(message).build()
				.perform();
		obj.getScreenShot("After Entering the Post Message");
		postButton.click();
		obj.getScreenShot("After Post the Message");

	}

}
