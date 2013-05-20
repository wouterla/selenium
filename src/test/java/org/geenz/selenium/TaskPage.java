package org.geenz.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TaskPage {
	
	WebDriver driver = null;

	public TaskPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void verifyFirstTaskDone() {
		assertTrue(driver.findElement(By.className("done")).isSelected());
	}

	public void markFirstTaskDone() {
		driver.findElement(By.className("done")).click();
	}

	/**
	 * Doesn't really do what it says. Only checks that there is *a* date as given.
	 */
	public void verifyTaskDueDate(String folderName, String taskNameToFind, String expectedDate) {
		WebElement timeElement = driver.findElement(
				By.xpath("//li[@data-task-id]/time"));
		assertEquals(expectedDate, timeElement.getText());		
	}
	
	public void addTaskWithDueDate(String folderName, String taskName, String dueDate) {
		driver.findElement(By.name("taskBody")).click();
		driver.switchTo().activeElement().sendKeys(taskName);
		driver.switchTo().activeElement().sendKeys(Keys.TAB);
		driver.switchTo().activeElement().sendKeys(dueDate);
		driver.switchTo().activeElement().sendKeys(Keys.ENTER);
	}

	public void verifyTaskExists(String folderName, String taskNameToFind) {
		List<WebElement> tasks = driver.findElements(By.xpath("//li[@data-task-id]/h4"));
		boolean found = false;
		for (WebElement taskElement : tasks) {
			String taskName = taskElement.getText();
			found = found || taskNameToFind.equals(taskName);
		}
		assertTrue(found);
	}

	public void addTaskToFolder(String folderName, String taskName) {
		driver.findElement(By.name("taskBody")).click();
		driver.switchTo().activeElement().sendKeys(taskName);
		driver.switchTo().activeElement().sendKeys(Keys.ENTER);
	}

	public void verifyFolderExists(String folderName) {
		WebElement folder = driver.findElement(
				By.cssSelector("div[class=\"folder\"][data-folder-id=\""
						+ folderName + "\"]"));
		assertNotNull(folder);
	}

	public void addFolder(String folderName) {
		driver.findElement(By.linkText("New folder")).click();
		driver.switchTo().activeElement().sendKeys(folderName);
		driver.switchTo().activeElement().sendKeys(Keys.TAB);
	}

	public void openProject(String projectName) {
		driver.findElement(By.linkText(projectName)).click();
	}

	public void verifyProjectExists(String groupName,
			String expectedProjectName) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		By locator = By.xpath("//li[@data-group=\"" + groupName
				+ "\"]/ul/li[@data-project]/a[@class=\"name\"]");

		assertTrue(wait.until(ExpectedConditions.textToBePresentInElement(
				locator, expectedProjectName)));
	}

	public void addProjectToGroup(String groupName, String projectName) {
		driver.findElement(
				By.xpath("//li[@data-group=\"" + groupName
						+ "\"]/dl/dd/button[@class=\"newProject\"]")).click();
		driver.switchTo().activeElement().sendKeys(projectName);
		driver.switchTo().activeElement().sendKeys(Keys.TAB);

		driver.switchTo().defaultContent();
	}

	public void verifyGroupExists(String groupName) {
		assertNotNull(driver.findElement(
				By.cssSelector("li[data-group=\"" + groupName + "\"]")));
	}

	public void addGroup(String groupName) {
		clickButton("newGroup");
		setGroupName(groupName);
	}

	public void setGroupName(String groupName) {
		driver.findElement(
				By.xpath("//li[@data-group='New group']/input[@type='text']"))
				.sendKeys(groupName + "\n");
	}

	protected void clickButton(String id) {
		driver.findElement(By.id(id)).click();		
	}
	
	public void cleanUpAgileTestingGroup() {
		driver.findElement(By.xpath("//li[@data-group=\"agile testing\"]/dl[@class=\"options\"]/dt")).click();
		driver.findElement(By.xpath("//li[@data-group=\"agile testing\"]/dl/dd/button[@class=\"deleteGroup\"]")).click();
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

}
