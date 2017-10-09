Feature: Exercise 

  As a ... I want to ....

  [LoginTest]

  Background:
    Given I open the application

  Scenario: Click the name button
    When I click on the 'Name' button
    Then I see a dialog with the values from the server

    When I click the dial 'OK' button
    Then the dialog is no longer visible


  Scenario: Click the 'No Name' button
    When I click on the 'No Name' button
    Then I see a dialog with the values from the server

    When I click the dial 'OK' button
    Then the dialog is no longer visible