package org.geenz.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class CreateTaskTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:9000";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testCreateTask() throws Exception {
    driver.get(baseUrl + "/login");

    login("guillaume@sample.com","secret");

    creategroup("TESTING TRAINING");

    logout();
  }

  public void login(String email, String password) throws Exception {
    driver.findElement(By.name("email")).clear();
    driver.findElement(By.name("email")).sendKeys(email);
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys(password);
    driver.findElement(By.cssSelector("button[type=\"submit\"]")).click();
  }

  public void creategroup(String groupname) throws Exception {
    driver.findElement(By.id("newGroup")).click();
    driver.findElement(By.xpath("//ul[@id='projects']/li[4]/h4")).click();
    driver.findElement(By.xpath("(//input[@class~='groupname'])[13]")).clear();
    driver.findElement(By.xpath("(//input[@type='text'])[13]")).sendKeys("ZWAAPJE");
    //    driver.findElement(By.xpath("(//input[@type='text'])[13]")).clear();
    // driver.findElement(By.xpath("(//input[@type='text'])[13]")).sendKeys(groupname);
  }
    //driver.switchTo().activelement()  sendKeys(KEYS.enter) TOEVOEGEN!!!!
    //WebDriverWait wait = new WebDriverWait(driver,0)
    //AssertTrue(wait.until(ExpectedCodnitions.....
  public void logout() throws Exception {
    driver.findElement(By.linkText("Logout")).click();
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
}
