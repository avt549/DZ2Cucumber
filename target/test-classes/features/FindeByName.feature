Feature: Test Finder By Name
  Scenario:
    Given Open browser "chrome"
    When Open page program courses
    When Search course with name "Java" and click
    Then Opened course "Java" page
    Then Close browser


