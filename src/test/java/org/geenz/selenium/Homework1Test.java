package org.geenz.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Homework1Test {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:9000/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testHomework1() throws Exception {
    doLogin();
    driver.get(baseUrl + "");
    driver.findElement(By.id("newGroup")).click();
    driver.findElement(By.xpath("//ul[@id='projects']/li[4]/input")).clear();
    driver.findElement(By.xpath("//ul[@id='projects']/li[4]/input")).sendKeys("testing training");
    driver.findElement(By.xpath("//ul[@id='projects']/li[4]/dl/dt")).click();
    driver.findElement(By.xpath("//ul[@id='projects']/li[4]/dl/dd/button")).click();
    driver.findElement(By.xpath("//ul[@id='projects']/li[4]/ul/li/input")).clear();
    driver.findElement(By.xpath("//ul[@id='projects']/li[4]/ul/li/input")).sendKeys("session 2\n");
    driver.switchTo().defaultContent();
    // ERROR: Caught exception [ERROR: Unsupported command [fireEvent | //ul[@id='projects']/li[4]/ul/li/input | blur]]
    driver.findElement(By.linkText("session 2")).click();
    driver.findElement(By.linkText("New folder")).click();
    for (int second = 0;; second++) {
    	if (second >= 60) fail("timeout");
    	try { if (isElementPresent(By.xpath("//article[@id='tasks']/div/header/input[2]"))) break; } catch (Exception e) {}
    	Thread.sleep(1000);
    }

    driver.findElement(By.xpath("//article[@id='tasks']/div/header/input[2]")).clear();
    driver.findElement(By.xpath("//article[@id='tasks']/div/header/input[2]")).sendKeys("Homework");
    driver.switchTo().defaultContent();
    driver.findElement(By.name("taskBody")).clear();
    driver.findElement(By.name("taskBody")).sendKeys("a task\n");
    driver.findElement(By.name("taskBody")).clear();
    driver.findElement(By.name("taskBody")).sendKeys("another task\t");
    driver.findElement(By.name("dueDate")).clear();
    driver.findElement(By.name("dueDate")).sendKeys("15/05/13\n");
    //driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.name("taskBody")).clear();
    driver.findElement(By.name("taskBody")).sendKeys("third task\t");
    driver.findElement(By.name("dueDate")).clear();
    driver.findElement(By.name("dueDate")).sendKeys("15/05/13\t");
    driver.findElement(By.name("assignedTo")).clear();
    driver.findElement(By.name("assignedTo")).sendKeys("guillaume@sample.com\n");
    //driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.xpath("//div[@data-folder-id='Homework']/ul/li[1]/input")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private void doLogin() {
    driver.get(baseUrl + "login");
    driver.findElement(By.name("email")).clear();
    driver.findElement(By.name("email")).sendKeys("guillaume@sample.com");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("secret");
    driver.findElement(By.cssSelector("button[type=\"submit\"]")).click();
    try {
      assertTrue(driver.findElement(By.id("user")).getText().matches("^Guillaume Bort[\\s\\S]*$"));
    } catch (Error e) {
      verificationErrors.append(e.toString());
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
