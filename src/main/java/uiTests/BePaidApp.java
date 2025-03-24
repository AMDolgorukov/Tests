package uiTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BePaidApp {
    public By bePaidFrame = By.cssSelector("iframe.bepaid-iframe");
    public By payDescriptionText = By.xpath("//*[@class='pay-description__text']/span");
    public By payDescriptionCost = By.xpath("//*[@class='pay-description__cost']/span");
    public By payBtn = By.xpath("//*[@class='colored disabled']");
    public By cardNumber = By.xpath("//*[@class='ng-tns-c2312288139-1 ng-star-inserted']");
    public By validityPeriod = By.xpath("//*[@class='ng-tns-c2312288139-4 ng-star-inserted']");
    public By cardholderName = By.xpath("//*[@class='ng-tns-c2312288139-3 ng-star-inserted']");
    public By cvc = By.xpath("//*[@class='ng-tns-c2312288139-5 ng-star-inserted']");
    public By visaImg = By.xpath("//*[@class='cards-brands ng-tns-c2312288139-1']/div/img[1]");
    public By masterCardImg = By.xpath("//*[@class='cards-brands ng-tns-c2312288139-1']/div/img[2]");
    public By belcardImg = By.xpath("//*[@class='cards-brands ng-tns-c2312288139-1']/div/img[3]");
    public By maestroImg = By.xpath("//*[@class='cards-brands ng-tns-c2312288139-1']/div/div/img[1]");
    public By mirImg = By.xpath("//*[@class='cards-brands ng-tns-c2312288139-1']/div/div/img[2]");

    private WebDriver driver;

    public BePaidApp(WebDriver driver) {
        this.driver = driver;
    }

    public boolean payPartnersLogo() {
        return !driver.findElements(visaImg).isEmpty()
                && !driver.findElements(maestroImg).isEmpty()
                && !driver.findElements(masterCardImg).isEmpty()
                && !driver.findElements(mirImg).isEmpty()
                && !driver.findElements(belcardImg).isEmpty();
    }
}