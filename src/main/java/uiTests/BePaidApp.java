package uiTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BePaidApp {
    public By bePaidFrame = By.cssSelector("iframe.bepaid-iframe");
    public By payDescriptionText = By.xpath("//*[@class='pay-description__text']/span");
//    /html/body/div[8]/div/iframe
    //*[@class='pay-description__text']/span
    public By cardNumber = By.className("ng-tns-c2312288139-1 ng-star-inserted");

    private WebDriver driver;

    public BePaidApp(WebDriver driver){
        this.driver = driver;
    }
}
