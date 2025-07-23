package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver webDriver;
    final private By availableBalanceLocator = By.id("j_idt23:amount");
    final private By popupErrorLocator = By.xpath("//span[@class = 'ui-growl-title']");
    final private By closeButtonDialogLocator = By.xpath("//*[@id = 'primefacesmessagedlg']//a");
    final private By successTransferDialogLocator = By.xpath(
            "//*[@id = 'primefacesmessagedlg']//div[text()= 'Chuyển tiền thành công']");

    @Step("Click close button of the notification popup ")
    public void closeTheNotificationDialog() {
        webDriver.findElement(closeButtonDialogLocator).click();
    }

    @Step("Get text from success transfer dialog")
    public String getSuccessTransferDialogText() {
        return webDriver.findElement(successTransferDialogLocator).getText();
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
    public int getAvailableBalance() {
        int balance = Integer.parseInt(webDriver.findElement(availableBalanceLocator).
                getText().replace(" VNĐ", "").replace(",", ""));
        return balance;
    }

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

}

