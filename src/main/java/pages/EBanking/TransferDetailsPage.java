package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TransferDetailsPage {
    private WebDriver webDriver;
    final private By sourceAccountComboboxLocator = By.id("j_idt23:j_idt28_label");
    final private By recipientAccountTextboxLocator = By.id("j_idt23:j_idt35");
    final private By amountTextboxLocator = By.id("j_idt23:j_idt40");
    final private By transferMessageTextboxLocator = By.id("j_idt23:j_idt42");
    final private By confirmButtonLocator = By.name("j_idt23:j_idt44");
    final private By recipientNameLocator = By.id("j_idt23:out");

    public TransferDetailsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Get transfer message from transfer details form")
    public String getTransferMessage() {
        return webDriver.findElement(transferMessageTextboxLocator).getAttribute("value");
    }

    @Step("Get transfer amount from transfer details form")
    public int getTransferAmount() {
        String amountValue = webDriver.findElement(amountTextboxLocator).getAttribute("value");

        amountValue = amountValue.replaceAll("[^\\d]", "");  // chỉ giữ lại số

        return Integer.parseInt(amountValue);
    }

    //xy ve sua may cai con lai
    @Step("Get recipient account ID from transfer details form")
    public int getRecipientAccountId() {
        String recipientId = webDriver.findElement(recipientAccountTextboxLocator).getAttribute("value");

        return Integer.parseInt(recipientId);
    }

    @Step("Get recipient account name from transfer details form")
    public String getRecipientName() {
        String recipientName = webDriver.findElement(recipientNameLocator).getText();
        return recipientName;
    }


    @Step("Get source account ID from transfer details form")
    public int getSourceAccountId() {
        String sourceAcountId = webDriver.findElement(sourceAccountComboboxLocator).getText();

        return Integer.parseInt(sourceAcountId);
    }

    @Step("Select source account")
    public void selectSourceAccount(int accountId) {
        webDriver.findElement(sourceAccountComboboxLocator).click();
        webDriver.findElement(By.xpath(String.format("//li[text() = '%s']", accountId))).click();
    }

    @Step("Enter recipient account number")
    public void enterRecipientAccount(int recipientAccount) {
        webDriver.findElement(recipientAccountTextboxLocator).
                sendKeys(String.valueOf(recipientAccount));
    }

    @Step("Enter amount")
    public void enterAmount(String amount) {
        webDriver.findElement(amountTextboxLocator).sendKeys(amount);
    }

    @Step("Enter amount")
    public void enterAmount(double amount) {
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

