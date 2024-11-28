Feature: Assign problem

  Scenario: Engineer assigns a problem to themselves
    Given a problem has been reported on an application
    When an engineer sees a reported problem
    And the engineer assigns a problem to themselves
    Then they have it in their list of problems

#  Scenario: Engineer starts work on a problem
#    Given an engineer has an assigned problem
#    Then they can change it from "assigned" to "in progress"