package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TransferDetailsForm {
    private WebDriver webDriver;
    final private By sourceAccountComboboxLocator = By.id("j_idt23:j_idt28_label");
    final private By recipientAccountTextboxLocator = By.id("j_idt23:j_idt35");
    final private By amountTextboxLocator = By.id("j_idt23:j_idt40");
    final private By transferMessageTextboxLocator = By.id("j_idt23:j_idt42");
    final private By confirmButtonLocator = By.name("j_idt23:j_idt44");

    public TransferDetailsForm(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Click on confirm button to open OTP entry form")
    public void openOTPEntryForm() {
        webDriver.findElement(confirmButtonLocator).click();
    }

//    @Step("Get receive OTP via email text from transfer confirmation form")
//    public String getReceiveOTPViaEmailText() {
//        String receiveOTPViaEmail = webDriver.findElement(By.xpath(xpath)).getText();
//        return receiveOTPViaEmail;
//    }
//
//    @Step("Get recipient account name from transfer confirmation form")
//    public String getRecipientAccountName() {
//        String recipientAccountName = webDriver.findElement(By.xpath(xpath)).getText();
//        return recipientAccountName;
//    }
//
//    @Step("Get recipient account ID from transfer confirmation form")
//    public int getRecipientAccountId() {
//        int recipientAccountId = Integer.parseInt(webDriver.findElement(By.xpath(xpath)).getText());
//        return recipientAccountId;
//    }
//
//    @Step("Get transfer message from transfer confirmation form")
//    public String getTransferMessageNumber() {
//        String transferAmountNumber = webDriver.findElement(By.xpath(xpath)).getText();
//        return transferAmountNumber;
//    }
//
//    @Step("Get transfer amount from transfer confirmation form")
//    public int getTransferAmountNumber(int transferAmountNumber) {
//        int transferAmountNumber = Integer.parseInt(webDriver.findElement(amountTextboxLocator).getAttribute(value));
//        return transferAmountNumber;
//    }

    @Step("Get source account ID from transfer details form")
    public int getSourceAccountId() {
        int sourceAccountId = Integer.parseInt(webDriver.findElement(sourceAccountComboboxLocator).getText());
        return sourceAccountId;
    }

    @Step("Select source account")
    public void selectSourceAccount(int accountId) {
        webDriver.findElement(sourceAccountComboboxLocator).click();
        webDriver.findElement(By.xpath(String.format("//li[text() = '%s']", accountId))).click();
    }

    @Step("Enter characters into the recipient account input field")
    public void enterRecipientAccount(String recipientAccount) {
        webDriver.findElement(recipientAccountTextboxLocator).
                sendKeys(recipientAccount);
    }

    @Step("Enter recipient account number")
    public void enterRecipientAccount(int recipientAccount) {
        webDriver.findElement(recipientAccountTextboxLocator).
                sendKeys(String.valueOf(recipientAccount));
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
    public void openTransferConfirmationForm() {
        webDriver.findElement(confirmButtonLocator).click();
    }

    @Step("Enter transfer details")
    public void enterTransferDetails(int sourceAccount, int destinationAccount, int amount, String content) {
        selectSourceAccount(sourceAccount);
        enterRecipientAccount(destinationAccount);
        enterAmount(amount);
        enterPaymentContent(content);
    }

}
