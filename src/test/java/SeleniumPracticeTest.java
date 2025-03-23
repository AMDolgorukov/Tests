import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uiTests.BePaidApp;
import uiTests.CookieFrame;
import uiTests.MainPage;

import java.time.Duration;

public class SeleniumPracticeTest {

    static WebDriver driver;

    MainPage mainPage;
    BePaidApp bePaidApp;
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
        bePaidApp = new BePaidApp(driver);
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
        mainPage.payWrapperData("297777777","1");
        Assertions.assertAll(
                () -> Assertions.assertEquals("Продолжить", driver.findElement(mainPage.continueBtn).getText()),
                driver.findElement(mainPage.continueBtn)::click,
                () -> Assertions.assertTrue(new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(bePaidApp.bePaidFrame)).isEnabled())
        );
    }

    @Test
    @DisplayName("Список услуг для оплаты онлайн")
    public void selectListTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // Услуги связи
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.selectHeader)).click();
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.selectItem_1)).click();
        Assertions.assertEquals("Номер телефона", driver.findElement(mainPage.connectPhone).getAttribute("placeholder"));
        Assertions.assertEquals("Сумма", driver.findElement(mainPage.connectSum).getAttribute("placeholder"));

        // Домашний интернет
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.selectHeader)).click();
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.selectItem_2)).click();
        Assertions.assertEquals("Номер абонента", driver.findElement(mainPage.internetPhone).getAttribute("placeholder"));
        Assertions.assertEquals("Сумма", driver.findElement(mainPage.internetSum).getAttribute("placeholder"));

        // Рассрочка
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.selectHeader)).click();
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.selectItem_3)).click();
        Assertions.assertEquals("Номер счета на 44", driver.findElement(mainPage.scoreInstalment).getAttribute("placeholder"));
        Assertions.assertEquals("Сумма", driver.findElement(mainPage.sumInstalment).getAttribute("placeholder"));

        // Задолженность
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.selectHeader)).click();
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.selectItem_4)).click();
        Assertions.assertEquals("Номер счета на 2073", driver.findElement(mainPage.scoreArrears).getAttribute("placeholder"));
        Assertions.assertEquals("Сумма", driver.findElement(mainPage.sumArrears).getAttribute("placeholder"));
    }

    @Test
    @DisplayName("Проверка окна подтверждения платежа")
    public void bePaidFrameTest() {
//        String sum = "1";
//        String phone ="297777777";
//        mainPage.payWrapperData(phone,sum);
//        driver.findElement(mainPage.continueBtn).click();
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(bePaidApp.bePaidFrame)).isEnabled();
//        driver.switchTo().frame(driver.findElement(bePaidApp.bePaidFrame));
////        System.out.println(driver.findElement(bePaidApp.payDescriptionText).getAttribute());
//        System.out.println(driver.findElement(bePaidApp.cardNumber).getText());
    }

//    @AfterEach
//    void driverClose() {
//        driver.close();
//    }
}