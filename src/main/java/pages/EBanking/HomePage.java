package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver webDriver;
    final private By confirmButtonLocator = By.name("j_idt23:j_idt44");
    final private By availableBalanceLocator = By.id("j_idt23:amount");
    final private By popupErrorLocator = By.xpath("//span[@class = 'ui-growl-title']");
    final private By popupCloseButtonLocator = By.xpath("//*[@id = 'primefacesmessagedlg']//a");


    @Step("Click close button of the notification popup ")
    public void closeTheNotificationPopup() {
        webDriver.findElement(popupCloseButtonLocator).click();
    }

    @Step("Get text from negative amount popup")
    public String getpopupErrorText() {
        return webDriver.findElement(popupErrorLocator).getText();
    }

    @Step("Verify the popup for negative amount is displayed")
    public boolean isPopupErrorDisplayed() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(popupErrorLocator));
        return true;

    }

    @Step("Verify the popup to select an account is displayed")
    public boolean isPopupSelectAnAccountDisplayed() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(popupErrorLocator));
        return true;
    }

    @Step("Get available balance")
    public double getAvailableBalance() {
        double balance = Double.parseDouble(webDriver.findElement(availableBalanceLocator).
                getText().replace(" VNĐ", "").replace(",", ""));
        return balance;
    }

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

}

