Feature: Empty Addressee Error
  As a mail user
  I want to see an error when sending without a recipient
  So that I avoid sending emails to nobody

  Background: User is logged in
    Given the user opens the Yandex Mail login page
    When the user enters valid credentials
    Then the user should be logged into the mailbox

  Scenario: Sending an email without a recipient shows an error
    When the user composes an email without a recipient, with subject "subject" and body "body"
    And the user sends the email
    Then an error popup should be displayed
    And the user logs out
