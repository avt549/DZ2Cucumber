package steps;

import com.codeborne.selenide.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import exeptions.BrowserNotSupported;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class MyStepdefs {
    public  ElementsCollection elCurses = $$x("//div[@class='lessons__new-item-container']");

    public String url = "https://otus.ru/categories/programming/";


    @Given("Open browser {string}")
    public void openBrowserChrome(String browserType) throws BrowserNotSupported {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 50;
        Configuration.startMaximized = true;
        switch (browserType) {
            case "chrome":
                Configuration.browser = "chrome";
                break;
            case "opera":
                Configuration.browser = "opera";
                break;
            case "firefox":
                Configuration.browser = "fireFox";
                break;
            default:
                throw new BrowserNotSupported();
        }

    }

    @When("Open page program courses")
    public void openPageProgramCourses() {
        open(url);
    }

    @When("Search course with name {string} and click")
    public SelenideElement searchCourseWithNameJavaAndClick(String searchValue) {

            elCurses.get(elCurses.size()-1).scrollIntoView(true);
            ElementsCollection elements = elCurses.filter(Condition.text(searchValue));
            Assert.assertTrue(
                    elements.size()>0,
                    "Ошибка!!! Не найден элемент со значением: "+ searchValue+" !!!"
            );
            elements.first().click();
            return elements.first();
    }

    @Then("Opened course {string} page")
    public void openedCourseJavaDeveloperPage(String nameOfCourses) {
        ElementsCollection courseName = $$x("//*[@class=\"course-header2__title\"]")
                .shouldBe(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(15));
        String name = courseName.get(0).getText();
        System.out.println(name);
        Assert.assertTrue(name.contains(nameOfCourses));
        WebDriverWait wait = new WebDriverWait(currentDriver(),40);
        wait.until(ExpectedConditions.urlContains(""));

    }

    @Then("Opened course GameDev page")
    public void openedCourseJavaDeveloperPage1() {
        ElementsCollection courseName = $$x("//*[@class='title-new__text']/h1")
                .shouldBe(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(15));
        String name = courseName.get(0).getText();
        System.out.println(name);
        Assert.assertTrue(name.contains("GameDev"));

    }

    public static WebDriver currentDriver() {
       return getWebDriver();


    }


    @Then("Close browser")
    public void closeBrowser() {
        Selenide.closeWindow();
    }

    @When("Search course that started in data or later")
    public void searchCourseThatStartedInDataOrLater() {

    }

    @Then("Opened course in in data or later")
    public void openedCourseInInDataOrLater() {
    }

    @When("Go to and click")
    public void goToCoursesAndClick() {
        ElementsCollection button = $$x("(//*[@class=\"header2-menu__item-text\"])[1]").shouldBe(CollectionCondition.sizeGreaterThan(0),Duration.ofSeconds(15));
        moveToElementAndClick(button.get(0));
    }

    @When("Go to Game Dev courses and click")
    public void goToPreparatoryCoursesAndClick() {
       ElementsCollection button = $$x("(//*[@href=\"/categories/gamedev/\"])[1]").shouldBe(CollectionCondition.sizeGreaterThan(0),Duration.ofSeconds(15));
        moveToElementAndClick(button.get(0));
    }

    @When("Search Last and print")
    public void searchMostExpensiveAndPrint() {
            SimpleDateFormat date = new SimpleDateFormat("dd MMMM",new Locale("ru"));
            ElementsCollection elCurses = $$x("//div[@class='lessons__new-item-container']");
            SelenideElement LastCurs = elCurses
                    .filter(Condition.not(Condition.text("О дате старта будет")))
                    .filter(Condition.not(Condition.text("10 000 ₽")))
                    .filter(Condition.not(Condition.text("10 ₽")))
                    .filter(Condition.not(Condition.text("В марте")))
                    .filter(Condition.not(Condition.text("В мае")))
                    .filter(Condition.not(Condition.text("В апреле")))
                    .filter(Condition.not(Condition.text("В январе")))
                    .filter(Condition.not(Condition.text("В феврале")))
                    .filter(Condition.not(Condition.text("В июле")))
                    .filter(Condition.not(Condition.text("В сенятабре")))
                    .filter(Condition.not(Condition.text("В авгутсе")))
                    .filter(Condition.not(Condition.text("В июне")))
                    .filter(Condition.not(Condition.text("В сентябре")))
                    .filter(Condition.not(Condition.text("В ноябре")))
                    .filter(Condition.not(Condition.text("В декабре")))
                    .stream()
                    .reduce((a, b) ->
                    {
                        try {
                            return date.parse(a.$x(".//div[@class='lessons__new-item-start']").getText().substring(2))
                                    .after(date.parse(b.$x(".//div[@class='lessons__new-item-start']").getText().substring(2))) ? a : b;
                        } catch (ParseException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }).orElseThrow(RuntimeException::new);
        System.out.println("Last course is :"+LastCurs.getText());
        }

    @Then("Search First and print")
    public void SearchFirstAndPrint() {
        SimpleDateFormat date = new SimpleDateFormat("dd MMMM",new Locale("ru"));
        SelenideElement FirstCurs = elCurses
                .filter(Condition.not(Condition.text("О дате старта будет")))
                .filter(Condition.not(Condition.text("10 000 ₽")))
                .filter(Condition.not(Condition.text("10 ₽")))
                .filter(Condition.not(Condition.text("В марте")))
                .filter(Condition.not(Condition.text("В мае")))
                .filter(Condition.not(Condition.text("В апреле")))
                .filter(Condition.not(Condition.text("В январе")))
                .filter(Condition.not(Condition.text("В феврале")))
                .filter(Condition.not(Condition.text("В июле")))
                .filter(Condition.not(Condition.text("В сенятабре")))
                .filter(Condition.not(Condition.text("В авгутсе")))
                .filter(Condition.not(Condition.text("В июне")))
                .filter(Condition.not(Condition.text("В сентябре")))
                .filter(Condition.not(Condition.text("В ноябре")))
                .filter(Condition.not(Condition.text("В декабре")))
                .stream()
                .reduce((a, b) ->
                {
                    try {
                        return date.parse(a.$x(".//div[@class='lessons__new-item-start']").getText().substring(2))
                                .after(date.parse(b.$x(".//div[@class='lessons__new-item-start']").getText().substring(2))) ? b : a;
                    } catch (ParseException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }).orElseThrow(RuntimeException::new);
        System.out.println("First course is :"+FirstCurs.getText());
    }
;






    public MyStepdefs moveToElementAndClick(SelenideElement element) {
        Actions actions = new Actions(getWebDriver());
        actions.moveToElement(element).click().build().perform();
        return new MyStepdefs();
    }


}
