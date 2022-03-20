Feature: Print most expensive and cheapest courses
  Scenario:
    Given Open browser "chrome"
    When Open page program courses
    When Go to and click
    When Go to Game Dev courses and click
    Then Opened course GameDev page
    When Search First and print
    Then Search Last and print
    Then Close browser