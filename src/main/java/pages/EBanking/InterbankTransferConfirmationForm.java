package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InterbankTransferConfirmationForm {

    private WebDriver webDriver;
    private WebDriverWait wait;

    // Locators
    final private By sourceAccountIdLocator = By.xpath("//table/tbody/tr[1]/td[2]");
    final private By recipientAccountIdLocator = By.xpath("//table/tbody/tr[3]/td[2]");
    final private By transferAmountLocator = By.xpath("//table/tbody/tr[4]/td[2]");
    final private By transferAmountInWordLocator = By.xpath("//table/tbody/tr[5]/td[2]");
    final private By transferMessageLocator = By.xpath("//table/tbody/tr[6]/td[2]");
    final private By recipientNameLocator = By.xpath("//table/tbody/tr[7]/td[2]");
    final private By confirmButtonLocator = By.name("j_idt23:j_idt44");

    // Placeholder: update with actual OTP input field locator in your page
    final private By otpFieldLocator = By.name("j_idt23:j_idt45");

    public InterbankTransferConfirmationForm(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    }

    @Step("Click on confirm button to open OTP entry form")
    public void openOTPEntryForm() {
        try {
            WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(confirmButtonLocator));
            confirmButton.click();
        } catch (Exception e) {
            System.out.println("[WARN] Normal click failed on Confirm button, attempting JS click");
            WebElement confirmButton = webDriver.findElement(confirmButtonLocator);
            ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", confirmButton);
        }

        // Wait OTP entry field visible to ensure page loaded
        wait.until(ExpectedConditions.visibilityOfElementLocated(otpFieldLocator));
    }

    @Step("Get source account ID from interbank transfer confirmation form")
    public int getSourceAccountId() {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(sourceAccountIdLocator)).getText().trim();
        return Integer.parseInt(text);
    }

    @Step("Get recipient account ID from interbank transfer confirmation form")
    public String getRecipientAccountId() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(recipientAccountIdLocator)).getText().trim();
    }

    @Step("Get transfer amount from interbank transfer confirmation form")
    public double getTransferAmount() {
        String amountText = wait.until(ExpectedConditions.visibilityOfElementLocated(transferAmountLocator))
                .getText()
                .replace(" VNĐ", "")
                .replace(",", "")
                .trim();
        return Double.parseDouble(amountText);
    }

    @Step("Get transfer amount in word from interbank transfer confirmation form")
    public String getTransferAmountInWord() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(transferAmountInWordLocator)).getText().trim();
    }

    @Step("Get transfer message from interbank transfer confirmation form")
    public String getTransferMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(transferMessageLocator)).getText().trim();
    }

    @Step("Get recipient name from interbank transfer confirmation form")
    public String getRecipientName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(recipientNameLocator)).getText().trim();
    }
}
