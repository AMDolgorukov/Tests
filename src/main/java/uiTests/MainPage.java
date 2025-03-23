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

    public By connectPhone = By.id("connection-phone");
    public By connectSum = By.id("connection-sum");
    public By internetPhone = By.id("internet-phone");
    public By internetSum = By.id("internet-sum");
    public By scoreInstalment = By.id("score-instalment");
    public By sumInstalment = By.id("instalment-sum");
    public By scoreArrears = By.id("score-arrears");
    public By sumArrears = By.id("arrears-sum");
    public By selectHeader = By.className("select__header");
    public By selectList = By.className("select__list");

    // можно попробывать list
    public By selectItem_1 = By.xpath("//*[@class='select__list']/li[1]");
    public By selectItem_2 = By.xpath("//*[@class='select__list']/li[2]");
    public By selectItem_3 = By.xpath("//*[@class='select__list']/li[3]");
    public By selectItem_4 = By.xpath("//*[@class='select__list']/li[4]");

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

    public void payWrapperData(String phone, String sum){
        WebElement phoneField = driver.findElement(connectPhone);
        WebElement sumField = driver.findElement(connectSum);
        phoneField.click();
        phoneField.sendKeys(phone);
        sumField.click();
        sumField.sendKeys(sum);
    }

//    public String getText(WebElement webElement){
//        return webElement.getText();
//    }
}
