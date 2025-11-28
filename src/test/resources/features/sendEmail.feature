Feature: Send Email
  As a mail user
  I want to manage my emails
  So that I can send, draft, and delete messages

  Background: User is logged in
    Given the user navigates to the mail application
    When the user logs in with valid credentials
    Then the user should be successfully logged in

  Scenario: Send email through drafts
    When the user composes an email with recipient "mateo.castilloa@cun.edu.co" and subject "Test Email" and body "This is a test email body."
    And the user saves the email as draft
    And the user opens the drafts folder
    Then the draft with subject "Test Email" should be visible
    When the user opens the draft message with subject "Test Email"
    And the user opens the draft email with subject "Test Email"
    And the user sends the email
    And the user opens the drafts folder
    Then the draft with subject "Test Email" should not exist in drafts
    And the user moves to element
    When the user opens the sent folder
    Then the email with subject "Test Email" should be in sent folder
    And the user logs out