import io.github.bonigarcia.wdm.WebDriverManager;
//import io.qameta.allure.Attachment;
//import io.qameta.allure.Description;
import io.qameta.allure.*;
import io.qameta.allure.selenide.LogType;
import junit.framework.TestListener;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uiTests.BePaidApp;
import uiTests.CookieFrame;
import uiTests.MainPage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.time.Duration;

@Epic("Allure")
@Feature("Главная страница")
public class SeleniumPracticeTest {

    static EventFiringWebDriver driver;

    MainPage mainPage;
    BePaidApp bePaidApp;

//    @Attachment(value = "Screenshot", type = "image/png")
//    public byte[] takeScreenshot(WebDriver driver) {
//        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//    }
//
//    public static void savePageScreenshot(WebDriver driver, Path path) {
//        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//        try {
//            Files.copy(screenshotFile.toPath(), path, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            System.out.println("Can't copy the screenshot file:" + e.getMessage());
//        }
//    }

    @BeforeAll
    static void driverSetup() {
        WebDriverManager.chromedriver().setup();
    }

    @Step("Открываем главную страницу")
    @BeforeEach
    void browserSetup() {
//        driver = new ChromeDriver();
        driver = new EventFiringWebDriver(new ChromeDriver());
        driver.get("http://mts.by");
        mainPage = new MainPage(driver);
        bePaidApp = new BePaidApp(driver);
        CookieFrame cookieFrame = new CookieFrame(driver);
        cookieFrame.cookieAgree();
    }

    @Test
    @Story("Проверка блока пополнения")
    @Description("Тест проверяет название блока 'Онлайн пополнение без комиссии'")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка названия блока «Онлайн пополнение без комиссии»")
    public void titleBlockTest() {
        Assertions.assertEquals("Онлайн пополнение\n" + "без комиссии", mainPage.getText(mainPage.payWrapperName));
//        Allure.step("Онлайн пополнение");
        Allure.addAttachment("Логи по первому тесту", String.valueOf(driver.manage().logs().get(String.valueOf(LogType.BROWSER)).getAll()));
    }

    @Test
    @DisplayName("Проверка наличия логотипов платежных систем")
    public void payPartnersLogoTest() {
        Assertions.assertTrue(mainPage.payPartnersLogo());
    }

    @Test
    @DisplayName("Проверка работы ссылки «Подробнее о сервисе»")
    public void serviceInfoLinkTest() {
        Assertions.assertAll(
                () -> Assertions.assertEquals("Подробнее о сервисе", mainPage.getText(mainPage.serviceInfo)),
                () -> Assertions.assertEquals("https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/", driver.findElement(mainPage.serviceInfo).getAttribute("href")),
                driver.findElement(mainPage.serviceInfo)::click,
                () -> Assertions.assertTrue(new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/")))
        );
    }

    @Test
    @DisplayName("Проверка работы кнопки «Продолжить»")
    public void continueBtnTest() {
        mainPage.paymentOptionsSelect(new WebDriverWait(driver, Duration.ofSeconds(3)), 0);
        mainPage.payWrapperData("297777777", 1.0);
        Assertions.assertAll(
                () -> Assertions.assertEquals("Продолжить", mainPage.getText(mainPage.continueBtn)),
                driver.findElement(mainPage.continueBtn)::click,
                () -> Assertions.assertTrue(new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(bePaidApp.bePaidFrame)).isEnabled())
        );
    }

    @Test
    @DisplayName("Проверка списка услуг для оплаты онлайн")
    public void selectListTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        mainPage.paymentOptionsSelect(wait, 0);
        Assertions.assertEquals("Номер телефона", driver.findElement(mainPage.connectPhone).getAttribute("placeholder"));
        Assertions.assertEquals("Сумма", driver.findElement(mainPage.connectSum).getAttribute("placeholder"));
        Assertions.assertEquals("E-mail для отправки чека", driver.findElement(mainPage.eMailField).getAttribute("placeholder"));
        mainPage.paymentOptionsSelect(wait, 1);
        Assertions.assertEquals("Номер абонента", driver.findElement(mainPage.internetPhone).getAttribute("placeholder"));
        Assertions.assertEquals("Сумма", driver.findElement(mainPage.internetSum).getAttribute("placeholder"));
        Assertions.assertEquals("E-mail для отправки чека", driver.findElement(mainPage.eMailField).getAttribute("placeholder"));
        mainPage.paymentOptionsSelect(wait, 2);
        Assertions.assertEquals("Номер счета на 44", driver.findElement(mainPage.scoreInstalment).getAttribute("placeholder"));
        Assertions.assertEquals("Сумма", driver.findElement(mainPage.sumInstalment).getAttribute("placeholder"));
        Assertions.assertEquals("E-mail для отправки чека", driver.findElement(mainPage.eMailField).getAttribute("placeholder"));
        mainPage.paymentOptionsSelect(wait, 3);
        Assertions.assertEquals("Номер счета на 2073", driver.findElement(mainPage.scoreArrears).getAttribute("placeholder"));
        Assertions.assertEquals("Сумма", driver.findElement(mainPage.sumArrears).getAttribute("placeholder"));
        Assertions.assertEquals("E-mail для отправки чека", driver.findElement(mainPage.eMailField).getAttribute("placeholder"));
    }

    @Test
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
    }

    @AfterEach
    void driverClose() {
        driver.close();
    }
}