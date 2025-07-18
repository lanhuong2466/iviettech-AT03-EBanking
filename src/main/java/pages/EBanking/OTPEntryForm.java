package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OTPEntryForm {
    private WebDriver webDriver;
    final private By OTPTextboxLocator = By.name("j_idt23:j_idt46");
    final private By tranferButtonLocator = By.name("j_idt23:j_idt48");
    final private By popupTransferSuccessLocator = By.xpath(
            "//*[@id = 'primefacesmessagedlg']//div[text() = 'Chuyển tiền thành công']");


    public OTPEntryForm(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Click transfer button")
    public void clickTransferButton() {
        webDriver.findElement(tranferButtonLocator).click();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(popupTransferSuccessLocator));
    }

    @Step("Enter OTP code")
    public void enterOTPCode(String otp) {
        webDriver.findElement(OTPTextboxLocator).sendKeys(otp);
    }

    @Step("Verify transfer success popup is displayed")
    public boolean isTransferSuccessPopupDisplayed() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        try {
            wait.until(driver -> driver.findElement(popupTransferSuccessLocator).isDisplayed());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
