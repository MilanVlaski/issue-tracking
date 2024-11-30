Feature: Report a problem and have it be resolved

  Scenario: User's problem gets answered
    Given a list of applications
    When the user purchases an application with any tech support
    Then the user is able to file a problem report with description "Issue with login button"
    And an engineer can post an answer to the problem
    Then the user can look at the answer and be happy

  Scenario: User's problem gets patched
    Given a list of applications
    When the user purchases an application with any tech support
    Then the user is able to file a problem report with description "App crashes on startup"
    When an engineer sees a reported problem with description "App crashes on startup"
    And the engineer assigns this problem to themselves
    When the engineer patches the problem
    Then the user can install the patch

  Scenario: Engineer assigns a problem to themselves
    Given a problem has been reported on an application with description "Feature X not working"
    When an engineer sees a reported problem with description "Feature X not working"
    And the engineer assigns that problem to themselves
    Then they have it in their list of problems with description "Feature X not working"
