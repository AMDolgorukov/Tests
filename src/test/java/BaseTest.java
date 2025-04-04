import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.ByteArrayInputStream;

public class BaseTest {

    protected ChromeDriver driver;

    @BeforeAll
    static void driverSetup() {
        WebDriverManager.chromedriver().setup();
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] takeScreenshot(WebDriver driver) {
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment("Screenshot", "image/png", new ByteArrayInputStream(screenshot), "png");
        return screenshot;
    }

    @AfterEach
    public void driverClose() {
        takeScreenshot(this.driver);
        if (driver != null) {
            driver.close();
        }
    }
}