package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver webDriver;
    final private By tranferLinkLocator = By.xpath("//*[@class = 'ui-menuitem-text'][text() = 'Chuyển  khoản']");
    final private By interbankTransferLinkLocator = By.xpath("//*[@class = 'ui-menuitem-text'][text() = 'Liên Ngân Hàng']");
    final private By sourceAccountComboboxLocator = By.id("j_idt23:j_idt28_label");
    final private By destinationAccountTextboxLocator = By.id("j_idt23:j_idt35");
    final private By amountTextboxLocator = By.id("j_idt23:j_idt40");
    final private By paymentContentTextboxLocator = By.id("j_idt23:j_idt42");
    final private By confirmButtonLocator = By.name("j_idt23:j_idt44");

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void openTransferForm() {
        webDriver.findElement(tranferLinkLocator).click();
    }

    public void openInterbankTransferForm() {
        webDriver.findElement(interbankTransferLinkLocator).click();
    }

    public void selectSourceAccount(int accountNumber) {
        webDriver.findElement(sourceAccountComboboxLocator).click();
        webDriver.findElement(By.xpath("//li[text() = '" + accountNumber + "']")).click();
    }

    public void enterDestinationAccount(int destinationAccount) {
        webDriver.findElement(destinationAccountTextboxLocator).sendKeys(String.valueOf(destinationAccount));
    }

    public void enterAmount(int amount) {
        webDriver.findElement(amountTextboxLocator).sendKeys(String.valueOf(amount));
    }

    public void enterPaymentContent(String content) {
        webDriver.findElement(paymentContentTextboxLocator).sendKeys(content);
    }

    public void openTransactionConfirmationForm() {
        webDriver.findElement(confirmButtonLocator).click();
    }

    public void enterTranferDetails(int sourceAccount, int destinationAccount, int amount, String content) {
        selectSourceAccount(sourceAccount);
        enterDestinationAccount(destinationAccount);
        enterAmount(amount);
        enterPaymentContent(content);
        openTransactionConfirmationForm();
    }

}
