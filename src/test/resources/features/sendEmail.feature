Feature: Send Email
  As a mail user
  I want to manage my emails
  So that I can send, draft, and verify sent messages

  Background: User is logged in
    Given the user opens the Yandex Mail login page
    When the user enters valid credentials
    Then the user should be logged into the mailbox

  Scenario: Send an email from a draft
    When the user composes an email with recipient "recipient", subject "subject" and body "body"
    And the user saves the email as draft
    And the user opens the drafts folder
    Then the email with subject "subject" should appear in drafts
    And the user sends the email
    When the user opens the sent folder
    Then the email with subject containing "subject" should appear in sent
    And the user logs out
