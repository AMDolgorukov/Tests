package uiTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

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
    public By eMailField = By.className("email");
    public By selectHeader = By.className("select__header");
    public By selectItem_1 = By.xpath("//*[@class='select__list']/li[1]");
    public By selectItem_2 = By.xpath("//*[@class='select__list']/li[2]");
    public By selectItem_3 = By.xpath("//*[@class='select__list']/li[3]");
    public By selectItem_4 = By.xpath("//*[@class='select__list']/li[4]");

    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void paymentOptionsSelect(WebDriverWait wait, int selectorIndex) {
        List<WebElement> selectList = new ArrayList<>();
        selectList.add(driver.findElement(selectItem_1));
        selectList.add(driver.findElement(selectItem_2));
        selectList.add(driver.findElement(selectItem_3));
        selectList.add(driver.findElement(selectItem_4));
        wait.until(ExpectedConditions.elementToBeClickable(selectHeader)).click();
        wait.until(ExpectedConditions.elementToBeClickable(selectList.get(selectorIndex))).click();
    }

    public boolean payPartnersLogo() {
        return !driver.findElements(visaImg).isEmpty()
                && !driver.findElements(verifiedByVisaImg).isEmpty()
                && !driver.findElements(masterCardImg).isEmpty()
                && !driver.findElements(masterCardSecureCodeImg).isEmpty()
                && !driver.findElements(belcardImg).isEmpty();
    }

    public void payWrapperData(String phone, Double sum) {
        WebElement phoneField = driver.findElement(connectPhone);
        WebElement sumField = driver.findElement(connectSum);
        phoneField.click();
        phoneField.sendKeys(phone);
        sumField.click();
        sumField.sendKeys(sum.toString());
    }

    public String getText(By locator) {
        return driver.findElement(locator).getText();
    }

    public void moveTo(By locator) {
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(locator)).build().perform();
    }
}