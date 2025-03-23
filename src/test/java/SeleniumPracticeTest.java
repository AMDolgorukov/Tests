import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uiTests.CookieFrame;
import uiTests.MainPage;

import java.time.Duration;

public class SeleniumPracticeTest {

    static WebDriver driver;

    MainPage mainPage;
//    MainPage mainPage = new MainPage(driver);


    @BeforeAll
    static void driverSetup() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void browserSetup() {
        driver = new ChromeDriver();
        driver.get("http://mts.by");
        mainPage = new MainPage(driver);
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//        try {
//            wait.until(ExpectedConditions.elementToBeClickable(cookieFrame.cookieBtn)).click();
//        } catch (TimeoutException ignored) {
//        }
        CookieFrame cookieFrame = new CookieFrame(driver);
        cookieFrame.cookieAgree();
    }

    @Test
    @DisplayName("Проверка названия блока «Онлайн пополнение без комиссии»")
    public void titleBlockTest() {
//        MainPage mainPage = new MainPage(driver);
//        WebElement payWrapper = driver.findElement(By.xpath("//*[@class='pay__wrapper']/h2"));
//        Assertions.assertEquals("Онлайн пополнение\n" + "без комиссии", payWrapper.getText());
        Assertions.assertEquals("Онлайн пополнение\n" + "без комиссии", mainPage.getPayWrapperName());
    }

    @Test
    @DisplayName("Проверка наличия логотипов платежных систем")
    public void payPartnersLogoTest() {
//        MainPage mainPage = new MainPage(driver);
//        Assertions.assertAll(
//                () -> Assertions.assertFalse(driver.findElements(mainPage.visaImg).isEmpty()),
//                () -> Assertions.assertFalse(driver.findElements(mainPage.verifiedByVisaImg).isEmpty()),
//                () -> Assertions.assertFalse(driver.findElements(mainPage.masterCardImg).isEmpty()),
//                () -> Assertions.assertFalse(driver.findElements(mainPage.masterCardSecureCodeImg).isEmpty()),
//                () -> Assertions.assertFalse(driver.findElements(mainPage.belcardImg).isEmpty())
//        );
        Assertions.assertTrue(mainPage.payPartnersLogo());
    }

    @Test
    @DisplayName("Проверка работы ссылки «Подробнее о сервисе»")
    public void serviceInfoLinkTest() {
//        WebElement serviceInfo = driver.findElement(mainPage.serviceInfo);
        WebElement serviceInfo = mainPage.serviceInfo();
        Assertions.assertAll(
                () -> Assertions.assertEquals("Подробнее о сервисе", serviceInfo.getText()),
                () -> Assertions.assertEquals("https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/", serviceInfo.getAttribute("href")),
                serviceInfo::click,
                () -> Assertions.assertTrue(new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/")))
        );
    }

    @Test
    @DisplayName("Проверка работы кнопки «Продолжить»")
    public void continueBtnTest() {
        WebElement continueBtn = driver.findElement(mainPage.continueBtn);
//        WebElement phoneField = driver.findElement(By.id("connection-phone"));
//        WebElement sumField = driver.findElement(By.id("connection-sum"));
//        phoneField.click();
//        phoneField.sendKeys("297777777");
//        sumField.click();
//        sumField.sendKeys("1");
        mainPage.payWrapperData();
        Assertions.assertAll(
                () -> Assertions.assertEquals("Продолжить", continueBtn.getText()),
                continueBtn::click,
                () -> Assertions.assertTrue(new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("iframe.bepaid-iframe"))).isEnabled())
        );
    }

    @AfterEach
    void driverClose() {
        driver.close();
    }
}