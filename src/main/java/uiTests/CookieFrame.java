package uiTests;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CookieFrame {
    public By cookieBtn = By.id("cookie-agree");

    private WebDriver driver;

    public CookieFrame(WebDriver driver) {
        this.driver = driver;
    }

    public void cookieAgree() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(cookieBtn)).click();
        } catch (TimeoutException ignored) {
        }
    }
}