Feature: Report a problem and install a patch

  Scenario: User purchases an application
    Given a list of applications
    When the user purchases an application with any tech support
    Then the user is able to file a problem report on that application
#    When an engineer signs up to solve the reported problem
#    Then the engineer can post an answer to the problem
#    And the user can read it

#  Scenario: Enter actions that caused a problem with the application
#    Given a user has a problem with application Minga version 1.1
#    When someone enters the actions that caused the problem
#    Then an engineer can read those actions and try to solve the problem
#
#  Scenario: User installs a patch
#    Given a patch has been made for the application Minga version 1.1
#    Then the user can install it with a link