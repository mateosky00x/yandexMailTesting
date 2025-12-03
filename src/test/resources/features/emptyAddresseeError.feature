Feature: Empty Addressee Error
  As a mail user
  I want to see an error when sending without a recipient
  So that I avoid sending emails to nobody

  Background:
    Given the user navigates to the mail application
    When the user logs in with valid credentials
    Then the user should be successfully logged in

  Scenario: Sending an email without a recipient shows an error
    When the user composes an email without recipient with subject "Test Missing Addressee" and body "This email has no recipient."
    And the user sends the email
    Then an error popup should be displayed
    And the error message should contain "Message not sent"
    And the user closes the error popup
    And the user logs out
