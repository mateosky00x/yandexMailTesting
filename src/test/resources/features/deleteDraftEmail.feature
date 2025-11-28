Feature: Delete Draft Email
  As a mail user
  I want to delete draft emails
  So that I can remove unwanted drafts

  Background: User is logged in
    Given the user navigates to the mail application
    When the user logs in with valid credentials
    Then the user should be successfully logged in

  Scenario: Delete draft email
    When the user composes an email with recipient "mateo.castilloa@cun.edu.co" and subject "Test Draft Deletion" and body "This is a test draft email to be deleted."
    And the user saves the email as draft
    And the user opens the drafts folder
    Then the draft with subject "Test Draft Deletion" should be visible
    When the user opens the draft message with subject "Test Draft Deletion"
    And the user opens the draft email with subject "Test Draft Deletion"
    And the user saves the email as draft
    And the user deletes the draft
    And the user opens the drafts folder
    Then the draft should not exist after deletion
    And the user logs out

  Scenario Outline: Delete multiple drafts
    When the user composes an email with recipient "<recipient>" and subject "<subject>" and body "<body>"
    And the user saves the email as draft
    And the user opens the drafts folder
    Then the draft with subject "<subject>" should be visible
    When the user opens the draft message with subject "<subject>"
    And the user opens the draft email with subject "<subject>"
    And the user saves the email as draft
    And the user deletes the draft
    And the user opens the drafts folder
    Then the draft should not exist after deletion
    And the user logs out

    Examples:
      | recipient                    | subject              | body                          |
      | mateo.castilloa@cun.edu.co  | Draft to Delete 1    | First draft to be deleted     |
      | mateo.castilloa@cun.edu.co  | Draft to Delete 2    | Second draft to be deleted    |