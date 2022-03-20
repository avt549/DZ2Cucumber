Feature: Find Course by Data
  Scenario:
    Given Open browser "chrome"
    When Open page program courses
    When Search course that started in data or later
    Then Opened course in in data or later
    Then Close browser