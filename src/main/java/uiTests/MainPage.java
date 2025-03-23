package uiTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {
    public By payWrapperName = By.xpath("//*[@class='pay__wrapper']/h2");
    public By visaImg = By.cssSelector("img[alt='Visa']");
    public By verifiedByVisaImg = By.cssSelector("img[alt='Verified By Visa']");
    public By masterCardImg = By.cssSelector("img[alt='MasterCard']");
    public By masterCardSecureCodeImg = By.cssSelector("img[alt='MasterCard Secure Code']");
    public By belcardImg = By.cssSelector("img[alt='Белкарт']");
    public By serviceInfo = By.xpath("//*[@class='pay__wrapper']/a");
    public By continueBtn = By.xpath("//*[@id='pay-connection']/button");
    public By phoneField = By.id("connection-phone");
    public By sumField = By.id("connection-sum");

    private WebDriver driver;

    public MainPage(WebDriver driver){
        this.driver = driver;
    }

    public String getPayWrapperName() {
        return driver.findElement(payWrapperName).getText();
    }

    public boolean payPartnersLogo(){
        return !driver.findElements(visaImg).isEmpty()&&!driver.findElements(verifiedByVisaImg).isEmpty()&&!driver.findElements(masterCardImg).isEmpty()&&!driver.findElements(masterCardSecureCodeImg).isEmpty()&&!driver.findElements(belcardImg).isEmpty();
    }

    public WebElement serviceInfo(){
        return driver.findElement(serviceInfo);
    }

    public void payWrapperData(){
        WebElement phone = driver.findElement(phoneField);
        WebElement sum = driver.findElement(sumField);
        phone.click();
        phone.sendKeys("297777777");
        sum.click();
        sum.sendKeys("1");
    }

//    public String getText(WebElement webElement){
//        return webElement.getText();
//    }
}
