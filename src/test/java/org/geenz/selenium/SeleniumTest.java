package org.geenz.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class SeleniumTest {

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	
	private final static String DEFAULT_LOGIN_NAME = "guillaume@sample.com";
	private final static String DEFAULT_PASSWORD = "secret";

	public WebDriver getDriver() {
		return driver;
	}

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:9000";
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();		
	}

	public String getErrorMessage() {
		return driver.findElement(By.cssSelector("p[class=\"error\"]"))
				.getText().trim();
	}

	public String getLoggedInUser() {
		return driver.findElement(By.id("user")).getText();
	}

	public void login(String email, String password) {
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys(email);
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button[type=\"submit\"]")).click();
	}
	
	public void login() {
		login(DEFAULT_LOGIN_NAME, DEFAULT_PASSWORD);
	}

	public void logout() {
		driver.findElement(By.linkText("Logout")).click();
	}
	
	public void verifyNotLoggedIn() {
		driver.get(baseUrl + "/login");
		if (loginFormFound()) {
			fail("Not in logged out state.");
		}
	}

	private boolean loginFormFound() {
		return null == driver.findElement(By.cssSelector("form[action=\"/login\"]"));
	}
	
	public void verifyLoggedIn() {
		driver.get(baseUrl);
		if (null == getLoggedInUser()) {
			fail("Not logged in when expected.");
		}
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

	protected TaskPage openTaskPage() {
		verifyNotLoggedIn();		
		login();
		return new TaskPage(driver);
	}

}
