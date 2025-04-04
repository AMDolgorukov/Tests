package uiTests;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CookieFrame {
    public By cookieBtn = By.id("cookie-agree");

    private ChromeDriver driver;

    public CookieFrame(ChromeDriver driver) {
        this.driver = driver;
    }

    public void cookieAgree() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(cookieBtn));
            cookieButton.click();
        } catch (TimeoutException ignored) {
        }
    }
}