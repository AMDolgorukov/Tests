import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeleniumPracticeTest {

    static WebDriver driver;

    @BeforeAll
    static void driverSetup() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void browserSetup() {
        driver = new ChromeDriver();
        driver.get("http://mts.by");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("cookie-agree"))).click();
        } catch (TimeoutException ignored) {
        }
    }

    @Test
    @DisplayName("Названия блока «Онлайн пополнение без комиссии»")
    public void titleBlockTest() {
        WebElement payWrapper = driver.findElement(By.xpath("//*[@class='pay__wrapper']/h2"));
        Assertions.assertEquals("Онлайн пополнение\n" + "без комиссии", payWrapper.getText());
    }

    @Test
    @DisplayName("Логотипы платежных систем")
    public void payPartnersLogoTest() {
        Assertions.assertAll(
                () -> Assertions.assertFalse(driver.findElements(By.cssSelector("img[alt='Visa']")).isEmpty()),
                () -> Assertions.assertFalse(driver.findElements(By.cssSelector("img[alt='Verified By Visa']")).isEmpty()),
                () -> Assertions.assertFalse(driver.findElements(By.cssSelector("img[alt='MasterCard']")).isEmpty()),
                () -> Assertions.assertFalse(driver.findElements(By.cssSelector("img[alt='MasterCard Secure Code']")).isEmpty()),
                () -> Assertions.assertFalse(driver.findElements(By.cssSelector("img[alt='Белкарт']")).isEmpty())
        );
    }

    @Test
    @DisplayName("Работа ссылки «Подробнее о сервисе»")
    public void serviceInfoLinkTest() {
        WebElement serviceInfo = driver.findElement(By.xpath("//*[@class='pay__wrapper']/a"));
        Assertions.assertAll(
                () -> Assertions.assertEquals("Подробнее о сервисе", serviceInfo.getText()),
                () -> Assertions.assertEquals("https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/", serviceInfo.getAttribute("href")),
                serviceInfo::click,
                () -> Assertions.assertTrue(new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/")))
        );
    }

    @Test
    @DisplayName("Работа кнопки «Продолжить»")
    public void continueBtnTest() {
        WebElement continueBtn = driver.findElement(By.xpath("//*[@id='pay-connection']/button"));
        WebElement phoneField = driver.findElement(By.id("connection-phone"));
        WebElement sumField = driver.findElement(By.id("connection-sum"));
        phoneField.click();
        phoneField.sendKeys("297777777");
        sumField.click();
        sumField.sendKeys("1");
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