package org.geenz.selenium;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class HomeWork_FVP {
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
	public void testHomeWork() throws Exception {
		login("guillaume@sample.com", "secret");
		WebElement newGroupElement = createNewGroup("TESTING TRAINING");
		WebElement newProjectElement = createNewProject(newGroupElement, "SESSION 2");
		WebElement folderElement = createNewFolder(newProjectElement, "Homework");
		WebElement task1Element = createNewTask(folderElement, "a task", null, null);
		System.out.println("before task2");
		String localeIndepentDate = "05/05/13"; // works with both NL and US Firefox locale
		WebElement task2Element = createNewTask(folderElement, "another task", localeIndepentDate, null);
		WebElement task3Element = createNewTask(folderElement, "third task", localeIndepentDate, "guillaume@sample.com");
		markTaskDone(task1Element);
		logout();
	}

	private WebElement createNewGroup(String groupName) {
		driver.findElement(By.id("newGroup")).click();
		WebElement input = getGroup("New group").findElement(By.cssSelector("input[type='text']"));
		enterNewText(input, groupName);
		return getGroup(groupName);
	}
	
	private WebElement getGroup(String groupName) {
		return driver.findElement(By.cssSelector("#projects li[data-group='" + groupName + "']"));
	}
	
	private WebElement createNewProject(WebElement groupElement, String projectName) {
		WebElement optionsElement = groupElement.findElement(By.cssSelector("dl.options"));
		optionsElement.findElement(By.tagName("dt")).click();
		optionsElement.findElement(By.cssSelector("button.newProject")).click();
		WebElement lastProjectElement = groupElement.findElement(By.cssSelector("ul li:last-child"));
		WebElement inputElement = lastProjectElement.findElement(By.cssSelector("input[type='text']"));
		enterNewText(inputElement, projectName);
		return lastProjectElement;
	}
	
	private WebElement createNewFolder(WebElement projectElement, String folderName) {
		projectElement.findElement(By.cssSelector("a.name")).click();
		driver.findElement(By.linkText("New folder")).click();
		WebElement newFolderElement = getFolder("New folder");
		WebElement inputElement = newFolderElement.findElement(By.cssSelector("header input[type='text']"));
		enterNewText(inputElement, folderName);
		return getFolder(folderName);
	}
	
	private WebElement getFolder(String folderName) {
		return driver.findElement(By.cssSelector("#main #tasks .folder[data-folder-id='" + folderName + "']"));
	}
	
	private WebElement createNewTask(WebElement folderElement, String taskName, String dueDate, String assignedTo) {
		WebElement addTaskForm = folderElement.findElement(By.className("addTask"));
		inputNewTextConditionally(addTaskForm, "taskBody", taskName);
		inputNewTextConditionally(addTaskForm, "dueDate", dueDate);
		inputNewTextConditionally(addTaskForm, "assignedTo", assignedTo);
		addTaskForm.submit();
		return getTask(folderElement, taskName);
	}
	
	private void inputNewTextConditionally(WebElement context, String inputName, String value) {
		if (value != null) inputNewText(context.findElement(By.name(inputName)), value);
	}
	
	private WebElement getTask(WebElement folderElement, String taskName) {
		return folderElement.findElement(By.xpath("//li[h4='" + taskName + "']"));
	}
	
	private void markTaskDone(WebElement taskElement) {
		taskElement.findElement(By.cssSelector("input.done")).click();
	}
	
	private void inputNewText(WebElement element, String text) {
		element.clear();
		element.sendKeys(text);
	}
	
	private void enterNewText(WebElement element, String text) {
		inputNewText(element, text + "\n");
	}

	private void login(String email, String password) {
		driver.get(baseUrl + "/login");
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys(email);
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button[type=\"submit\"]")).click();
	}

	private void logout() {
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
}
