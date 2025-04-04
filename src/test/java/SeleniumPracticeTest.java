import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import io.qameta.allure.selenide.LogType;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uiTests.BePaidApp;
import uiTests.CookieFrame;
import uiTests.MainPage;

import java.text.DecimalFormat;
import java.time.Duration;

@Epic("Aston AQA тестирование сайта MTS.BY")
@Feature("Набор тестов для задания по Selenium")
public class SeleniumPracticeTest extends BaseTest{

    MainPage mainPage;
    BePaidApp bePaidApp;
    CookieFrame cookieFrame;

    @Step("Открываем главную страницу")
    @BeforeEach
    void browserSetup() {
        driver = new ChromeDriver();
        driver.get("http://mts.by");
        mainPage = new MainPage(driver);
        bePaidApp = new BePaidApp(driver);
        cookieFrame = new CookieFrame(driver);
        cookieFrame.cookieAgree();
    }

    @Test
    @Step("Проверка блока пополнения")
    @Story("Пользователь ищет блок пополнения на главной странице")
    @Description("Тест проверяет название блока 'Онлайн пополнение без комиссии'")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка названия блока «Онлайн пополнение без комиссии»")
    public void titleBlockTest() {
        mainPage.moveTo(mainPage.continueBtn);
        Assertions.assertEquals("Онлайн пополнение\n" + "без комиссии", mainPage.getText(mainPage.payWrapperName));
        Allure.addAttachment("Проверка названия блока «Онлайн пополнение без комиссии»", String.valueOf(driver.manage().logs().get(String.valueOf(LogType.BROWSER)).getAll()));
    }

    @Test
    @Step("Проверка лого платежек")
    @Story("Пользователь видит возможные варианты платежный систем")
    @Description("Тест проверяет наличие логотипов платежных систем")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка наличия логотипов платежных систем")
    public void payPartnersLogoTest() {
        mainPage.moveTo(mainPage.continueBtn);
        Assertions.assertTrue(mainPage.payPartnersLogo());
        Allure.addAttachment("Проверка наличия логотипов платежных систем", String.valueOf(driver.manage().logs().get(String.valueOf(LogType.BROWSER)).getAll()));
    }

    @Test
    @Step("Ссылка «Подробнее о сервисе»")
    @Story("Пользователь переходит по ссылке с тайтлом «Подробнее о сервисе»")
    @Description("Тест проверяет наличие ссылки «Подробнее о сервисе», её кликабельность и конечный URL")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка работы ссылки «Подробнее о сервисе»")
    public void serviceInfoLinkTest() {
        driver.manage().window().maximize();
        mainPage.moveTo(mainPage.continueBtn);
        Assertions.assertAll(
                () -> Assertions.assertEquals("Подробнее о сервисе", mainPage.getText(mainPage.serviceInfo)),
                () -> Assertions.assertEquals("https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/", driver.findElement(mainPage.serviceInfo).getAttribute("href")),
                driver.findElement(mainPage.serviceInfo)::click,
                () -> Assertions.assertTrue(new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/")))
        );
        Allure.addAttachment("Проверка работы ссылки «Подробнее о сервисе»", String.valueOf(driver.manage().logs().get(String.valueOf(LogType.BROWSER)).getAll()));
    }

    @Test
    @Step("Кнопка «Продолжить»")
    @Story("Пользователь заполняет форму платежа и кликает кнопку «Продолжить»")
    @Description("Тест проверяет наличие кнопки «Продолжить», её кликабельность и появившейся фрейм")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка работы кнопки «Продолжить»")
    public void continueBtnTest() {
        mainPage.moveTo(mainPage.continueBtn);
        mainPage.paymentOptionsSelect(new WebDriverWait(driver, Duration.ofSeconds(3)), 0);
        mainPage.payWrapperData("297777777", 1.0);
        Assertions.assertAll(
                () -> Assertions.assertEquals("Продолжить", mainPage.getText(mainPage.continueBtn)),
                driver.findElement(mainPage.continueBtn)::click,
                () -> Assertions.assertTrue(new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(bePaidApp.bePaidFrame)).isEnabled())
        );
        Allure.addAttachment("Проверка работы кнопки «Продолжить»", String.valueOf(driver.manage().logs().get(String.valueOf(LogType.BROWSER)).getAll()));
    }

    @Test
    @Step("Селект меню услуг для оплаты онлайн")
    @Story("Пользователь выбирает из выпадающего списка услугу для оплаты онлайн")
    @Description("Тест проверяет наличие всех видов услуг в селекторе, а так же соответсвующие описания в полях на форме оплаты для каждой из услуг")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка списка услуг для оплаты онлайн")
    public void selectListTest() {
        WebElement emailField = driver.findElement(mainPage.eMailField);
        mainPage.moveTo(mainPage.continueBtn);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        Assertions.assertAll(
                () -> mainPage.paymentOptionsSelect(wait, 0),
                () -> Assertions.assertEquals("Номер телефона", driver.findElement(mainPage.connectPhone).getAttribute("placeholder")),
                () -> Assertions.assertEquals("Сумма", driver.findElement(mainPage.connectSum).getAttribute("placeholder")),
                () -> Assertions.assertEquals("E-mail для отправки чека", emailField.getAttribute("placeholder")),
                () -> mainPage.paymentOptionsSelect(wait, 1),
                () -> Assertions.assertEquals("Номер абонента", driver.findElement(mainPage.internetPhone).getAttribute("placeholder")),
                () -> Assertions.assertEquals("Сумма", driver.findElement(mainPage.internetSum).getAttribute("placeholder")),
                () -> Assertions.assertEquals("E-mail для отправки чека", emailField.getAttribute("placeholder")),
                () -> mainPage.paymentOptionsSelect(wait, 2),
                () -> Assertions.assertEquals("Номер счета на 44", driver.findElement(mainPage.scoreInstalment).getAttribute("placeholder")),
                () -> Assertions.assertEquals("Сумма", driver.findElement(mainPage.sumInstalment).getAttribute("placeholder")),
                () -> Assertions.assertEquals("E-mail для отправки чека", emailField.getAttribute("placeholder")),
                () -> mainPage.paymentOptionsSelect(wait, 3),
                () -> Assertions.assertEquals("Номер счета на 2073", driver.findElement(mainPage.scoreArrears).getAttribute("placeholder")),
                () -> Assertions.assertEquals("Сумма", driver.findElement(mainPage.sumArrears).getAttribute("placeholder")),
                () -> Assertions.assertEquals("E-mail для отправки чека", emailField.getAttribute("placeholder"))
        );
        Allure.addAttachment("Проверка списка услуг для оплаты онлайн", String.valueOf(driver.manage().logs().get(String.valueOf(LogType.BROWSER)).getAll()));
    }

    @Test
    @Step("Фрейм подтверждения платежа")
    @Story("Пользователь выбирает услугу для оплаты, заполняет форму платежа и нажимает кнопку «Продолжить»")
    @Description("Тест проверяет подписи всех полей на фрейме подтверждения оплаты")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка окна подтверждения платежа")
    public void bePaidFrameTest() {
        mainPage.paymentOptionsSelect(new WebDriverWait(driver, Duration.ofSeconds(3)), 0);
        Double sum = 1.0;
        String sumDouble = new DecimalFormat("#0.00").format(sum);
        String phone = "297777777";
        mainPage.payWrapperData(phone, sum);
        driver.findElement(mainPage.continueBtn).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.className("bepaid-iframe")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(bePaidApp.payDescriptionText));
        Assertions.assertEquals("Оплата: Услуги связи Номер:375" + phone, mainPage.getText(bePaidApp.payDescriptionText));
        Assertions.assertEquals(sumDouble.replace(",", ".") + " BYN", mainPage.getText(bePaidApp.payDescriptionCost));
        Assertions.assertEquals("Оплатить " + sumDouble.replace(",", ".") + " BYN", mainPage.getText(bePaidApp.payBtn));
        Assertions.assertEquals("Номер карты", mainPage.getText(bePaidApp.cardNumber));
        Assertions.assertEquals("Срок действия", mainPage.getText(bePaidApp.validityPeriod));
        Assertions.assertEquals("Имя держателя (как на карте)", mainPage.getText(bePaidApp.cardholderName));
        Assertions.assertEquals("CVC", mainPage.getText(bePaidApp.cvc));
        Assertions.assertTrue(bePaidApp.payPartnersLogo());
        Allure.addAttachment("Проверка окна подтверждения платежа", String.valueOf(driver.manage().logs().get(String.valueOf(LogType.BROWSER)).getAll()));
    }
}