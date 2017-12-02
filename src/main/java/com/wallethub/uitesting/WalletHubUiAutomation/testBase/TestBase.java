package com.wallethub.uitesting.WalletHubUiAutomation.testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class TestBase {
	public static final Logger log = Logger.getLogger(TestBase.class.getName());
	public static WebDriver driver;
	JavascriptExecutor js = (JavascriptExecutor) driver;
	public static Alert alert = null;
	public static Properties prop;
	static FileReader fin;
	static File file;
	String browsername;
	String log4jConfigPath = "log4j.properties";

	public void init() {
		PropertyConfigurator.configure(log4jConfigPath);

	}

	public void selectBrowser(String browsername) {
		log.info("Creating object of " + browsername);
		if (browsername.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir")
							+ "\\Drivers\\geckodriver.exe");

			driver = new FirefoxDriver();
		}

		else if (browsername.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir")
							+ "\\Drivers\\chromedriver.exe");

			driver = new ChromeDriver();
		}

		else if (browsername.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir")
							+ "\\Drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}

	}

	public void getURL(String url) {
		log.info("Navigating to :" + url);
		driver.get(url);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public String getScreenShot(String screenshotName) {
		if (screenshotName.equals("")) {
			screenshotName = "blank";
		}

		Calendar calender = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		TakesScreenshot tc = (TakesScreenshot) driver;

		File sourceFile = tc.getScreenshotAs(OutputType.FILE);
		System.out.println(sourceFile);

		try {

			String reportDirectory = new File(System.getProperty("user.dir"))
					.getAbsolutePath()
					+ "\\src\\main\\java\\com\\wallethub\\uitesting\\WalletHubUiAutomation\\screenShots\\";
			System.out.println("Path:=" + reportDirectory);
			String actaulImageName = reportDirectory + screenshotName + "_"
					+ formater.format(calender.getTime()) + ".png";

			File desFile = new File(actaulImageName);
			FileUtils.copyFile(sourceFile, desFile);
			Reporter.log("<a href='" + desFile.getAbsolutePath()
					+ "'><img src='" + desFile.getAbsolutePath()
					+ "' height='100' width='100'/></a>");
			return actaulImageName;
		} catch (Exception e) {
			System.out.println("Exception is throwing during screen shot"
					+ e.getMessage());
		}
		return screenshotName;

	}

	public WebElement waitForTheWebElement(WebElement element,
			WebDriver driver, long time) {

		WebDriverWait wait = new WebDriverWait(driver, time);

		return wait.until(ExpectedConditions.visibilityOf(element));

	}

	public WebElement waitForTheWebElementWithPoolingInterval(
			WebElement element, WebDriver driver, long time) {

		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.pollingEvery(5, TimeUnit.SECONDS);
		wait.ignoring(NoSuchElementException.class);

		return wait.until(ExpectedConditions.visibilityOf(element));

	}

	public void implicitWait(long time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	public void highLighteBackground(WebDriver driver, WebElement element) {

		js.executeScript("arguments[0].style.border='6px groove yellow'",
				element);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		js.executeScript("arguments[0].style.border=''", element);

	}

	public void scroolTheWidnow() {
		js.executeScript("scroll(0,1200)");
	}

	public boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}

	}

	public void triggerClickOnWebElement(WebDriver driver, WebElement webElement) {
		if (isAlertPresent(driver)) {
			alert.dismiss();
			waitForTheWebElement(webElement, driver, 10);
			webElement.click();
		} else {
			waitForTheWebElement(webElement, driver, 10);
			webElement.click();
		}
	}

	public static void loadProperties() throws IOException {
		prop = new Properties();
		file = new File(
				System.getProperty("user.dir")
						+ "\\src\\main\\java\\com\\wallethub\\uitesting\\WalletHubUiAutomation\\properties\\credential.properties");

		fin = new FileReader(file);
		prop.load(fin);

	}

	public static String getProperties(String data) throws IOException {
		loadProperties();
		String Data = prop.getProperty(data);
		return Data;

	}

	public void deleteCookie(WebDriver driver) {
		this.driver = driver;
		
		driver.manage().deleteAllCookies();

	}

}
