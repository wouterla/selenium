package org.geenz.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TaskTest extends SeleniumTest {

	/** Our Page Object */
	TaskPage tp = null;
	
	@Before
	public void initialize() {
		tp = openTaskPage();
	}
	
	@After
	public void cleanUpAgileTestingGroup() {
		tp.cleanUpAgileTestingGroup();
	}
	
	@Test
	public void testAddAGroup() {
		tp.addGroup("agile testing");

		tp.verifyGroupExists("agile testing");
	}

	@Test
	public void testAddProject() {
		tp.addGroup("agile testing");
		tp.addProjectToGroup("agile testing", "session 2");

		tp.verifyProjectExists("agile testing", "session 2");
	}

	@Test
	public void testCreateFolder() {
		tp.addGroup("agile testing");
		tp.addProjectToGroup("agile testing", "session 2");
		tp.openProject("session 2");
		tp.addFolder("homework");

		tp.verifyFolderExists("homework");
	}

	@Test
	public void testCreateTask() {
		tp.addGroup("agile testing");
		tp.addProjectToGroup("agile testing", "session 2");
		tp.openProject("session 2");
		tp.addFolder("homework");

		tp.addTaskToFolder("homework", "a task");

		tp.verifyTaskExists("homework", "a task");
	}
	
	@Test
	public void testCreateTaskWitDueDate() {
		tp.addGroup("agile testing");
		tp.addProjectToGroup("agile testing", "session 2");
		tp.openProject("session 2");
		tp.addFolder("homework");

		tp.addTaskWithDueDate("homework", "another task", "05/15/13");
		
		tp.verifyTaskExists("homework", "another task");
		tp.verifyTaskDueDate("homework", "another task", "May 15 2013");
	}

	@Test
	public void testMarkTaskDone() {
		tp.addGroup("agile testing");
		tp.addProjectToGroup("agile testing", "session 2");
		tp.openProject("session 2");
		tp.addFolder("homework");

		tp.addTaskToFolder("homework", "a task");
		tp.markFirstTaskDone();
		
		tp.verifyFirstTaskDone();		
	}
}
