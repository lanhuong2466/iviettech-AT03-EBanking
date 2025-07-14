package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TransferDetailsForm {
    private WebDriver webDriver;
    final private By sourceAccountComboboxLocator = By.id("j_idt23:j_idt28_label");
    final private By destinationAccountTextboxLocator = By.id("j_idt23:j_idt35");
    final private By amountTextboxLocator = By.id("j_idt23:j_idt40");
    final private By transferMessageTextboxLocator = By.id("j_idt23:j_idt42");
    final private By confirmButtonLocator = By.name("j_idt23:j_idt44");

    public TransferDetailsForm(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Select source account")
    public void selectSourceAccount(int accountId) {
        webDriver.findElement(sourceAccountComboboxLocator).click();
        webDriver.findElement(By.xpath(String.format("//li[text() = '%s']", accountId))).click();
    }

    @Step("Enter destination account")
    public void enterDestinationAccount(int destinationAccount) {
        webDriver.findElement(destinationAccountTextboxLocator).
                sendKeys(String.valueOf(destinationAccount));
    }

    @Step("Enter amount")
    public void enterAmount(int amount) {
        webDriver.findElement(amountTextboxLocator).sendKeys(String.valueOf(amount));
    }

    @Step("Enter payment content")
    public void enterPaymentContent(String content) {
        webDriver.findElement(transferMessageTextboxLocator).sendKeys(content);
    }

    @Step("Click on Confirm button to open transaction confirm form")
    public void openTransactionConfirmationForm() {
        webDriver.findElement(confirmButtonLocator).click();
    }

    @Step("Enter transfer details")
    public void enterTransferDetails(int sourceAccount, int destinationAccount, int amount, String content) {
        selectSourceAccount(sourceAccount);
        enterDestinationAccount(destinationAccount);
        enterAmount(amount);
        enterPaymentContent(content);
    }

}
