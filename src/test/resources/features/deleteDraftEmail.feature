Feature: Delete Draft Email
  As a mail user
  I want to delete draft emails
  So that I can remove unwanted drafts

  Background: User is logged in
    Given the user opens the Yandex Mail login page
    When the user enters valid credentials
    Then the user should be logged into the mailbox

  Scenario: Delete draft email
    When the user composes an email with recipient "recipient", subject "subject" and body "body"
    And the user saves the email as draft
    And the user opens the drafts folder
    Then the email with subject "subject" should appear in drafts
    And the user saves the email as draft
    And the user deletes the draft
    And the user opens the drafts folder
    Then the email with subject "subject" should not appear in drafts
    And the user logs out

  Scenario Outline: Delete multiple drafts
    When the user composes an email with recipient "<recipient>", subject "<subject>" and body "<body>"
    And the user saves the email as draft
    And the user opens the drafts folder
    Then the email with subject "<subject>" should appear in drafts
    And the user saves the email as draft
    And the user deletes the draft
    And the user opens the drafts folder
    Then the email with subject "<subject>" should not appear in drafts
    And the user logs out

    Examples:
      | recipient                    | subject              | body                          |
      | mateo.castilloa@cun.edu.co  | Draft to Delete 1    | First draft to be deleted     |
      | mateo.castilloa@cun.edu.co  | Draft to Delete 2    | Second draft to be deleted    |