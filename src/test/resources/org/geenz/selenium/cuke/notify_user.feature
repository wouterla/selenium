Feature: Receive task due date notifications
	As a user
	I want to receive a notification that one of my tasks has a nearing due date
	So that I can complete the task in time 

Background: Given that each notification sent by the system leaves a record in a log table

Scenario: Receiving a notification for a due task
	Given a not completed task with a due date "daysInFuture" days in the future
	When the due date is less than "daysToExpiration" in the future
	Then a notification has been sent to the task's user, with a corresponding record in the log table
	