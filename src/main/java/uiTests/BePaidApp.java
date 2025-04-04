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
    public By visaImg = By.xpath("//img[@src='assets/images/payment-icons/card-types/visa-system.svg']");
    public By masterCardImg = By.xpath("//img[@src='assets/images/payment-icons/card-types/mastercard-system.svg']");
    public By belcardImg = By.xpath("//img[@src='assets/images/payment-icons/card-types/belkart-system.svg']");
    public By maestroImg = By.xpath("//img[@src='assets/images/payment-icons/card-types/maestro-system.svg']");
    public By mirImg = By.xpath("//img[@src='assets/images/payment-icons/card-types/mir-system-ru.svg']");

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