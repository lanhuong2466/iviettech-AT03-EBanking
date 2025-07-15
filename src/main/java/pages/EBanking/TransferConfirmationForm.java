package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TransferConfirmationForm {
    private WebDriver webDriver;
    final private By confirmButtonLocator = By.name("j_idt23:j_idt44");
    final private By sourceAccountIdLocator = By.id("j_idt23:j_idt27");
    final private By transferAmountLocator = By.id("j_idt23:j_idt31");
    final private By transferMessageLocator = By.id("j_idt23:j_idt35");
    final private By recipientAccountIdLocator = By.id("j_idt23:j_idt37");
    final private By recipientNameLocator = By.id("j_idt23:j_idt39");
    final private By receiveOTPViaEmailLocator = By.id("j_idt23:j_idt43");

    public TransferConfirmationForm(WebDriver webDriver) {
        this.webDriver = webDriver;
    }


    @Step("Click on confirm button to open OTP entry form")
    public void openOTPEntryForm() {
        webDriver.findElement(confirmButtonLocator).click();
    }

    @Step("Get receive OTP via email text from transfer confirmation form")
    public String getReceiveOTPViaEmailText() {
        return webDriver.findElement(receiveOTPViaEmailLocator).getText();
    }

    @Step("Get recipient name from transfer confirmation form")
    public String getRecipientName() {
        return webDriver.findElement(recipientNameLocator).getText();
    }

    @Step("Get recipient account ID from transfer confirmation form")
    public int getRecipientAccountId() {
        return Integer.parseInt(webDriver.findElement(recipientAccountIdLocator).getText());
    }

    @Step("Get transfer message from transfer confirmation form")
    public String getTransferMessage() {
        return webDriver.findElement(transferMessageLocator).getText();
    }


    @Step("Get transfer amount from transfer confirmation form")
    public double getTransferAmount() {
        return Double.parseDouble(webDriver.findElement(transferAmountLocator)
                .getText()
                .replace(" VNĐ", "")
                .replace(",", ""));
    }

    @Step("Get source account ID from transfer confirmation form")
    public int getSourceAccountId() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(sourceAccountIdLocator));
        return Integer.parseInt(element.getText().trim());
    }

}


