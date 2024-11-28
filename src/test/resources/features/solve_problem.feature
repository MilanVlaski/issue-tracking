Feature: Report a problem and install a patch

  Scenario: User's problem gets answered
    Given a list of applications
    When the user purchases an application with any tech support
    Then the user is able to file a problem report on that application
    And an engineer can post an answer to the problem
    Then the user can look at the answer and be happy
