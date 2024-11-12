Feature: Report a problem and install a patch

  Scenario: User purchases an application
    Given a list of applications
    When the user purchases an application with any tech support
    Then the user is able to file a problem report on that application
    And an engineer can post an answer to the problem
    Then the user can look at the answer and be happy

#  Scenario: User reports a problem with the application
#    Given a user has a problem with an application
#    When the user enters the actions that caused the problem
#    Then an engineer can read those actions and try to solve the problem
#
#  Scenario: Engineer answers a problem
#  Scenario: Engineer patches a problem
