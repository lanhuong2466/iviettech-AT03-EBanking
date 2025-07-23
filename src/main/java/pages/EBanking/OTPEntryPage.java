package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OTPEntryPage {
    private WebDriver webDriver;

    // Locators
    final private By OTPTextboxLocator = By.xpath("//input[@type = 'text']");
    final private By transferButtonLocator = By.xpath("//input[@type = 'submit']");
    final private By transferSuccessDialogLocator = By.xpath(
            "//*[@id = 'primefacesmessagedlg']//div[text() = 'Chuyển tiền thành công']");
    final private By popupOTPInvalidLocator = By.xpath("//span[@class='ui-growl-title' and text()='Sai mã OTP']");

    public OTPEntryPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Enter OTP code")
    public void enterOTPCode(String otp) {
        webDriver.findElement(OTPTextboxLocator).sendKeys(otp);
    }

    @Step("Click transfer button")
    public void clickTransferButton() {
        webDriver.findElement(transferButtonLocator).click();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(transferSuccessDialogLocator));
    }

    @Step("Click transfer button without waiting for success popup")
    public void clickTransferButtonWithoutWaitingSuccessPopup() {
        webDriver.findElement(transferButtonLocator).click();
    }

    @Step("Verify transfer success popup is displayed")
    public boolean isTransferSuccessDialogDisplayed() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        try {
            wait.until(driver -> driver.findElement(transferSuccessDialogLocator).isDisplayed());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Verify invalid OTP popup is displayed")
    public boolean isInvalidOTPPopupDisplayed() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(popupOTPInvalidLocator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Submit OTP code")
    public void submitOTPCode(String otp) {
        enterOTPCode(otp);
        webDriver.findElement(transferButtonLocator).click();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(transferSuccessDialogLocator));
    }
}
