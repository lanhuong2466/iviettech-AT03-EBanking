package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class InterbankTransferDetailsForm {
    private WebDriver webDriver;
    private WebDriverWait wait;

    // Locators
    private final By sourceAccountComboboxLocator = By.id("j_idt23:j_idt28_label");
    private final String sourceAccountOptionXpath = "//li[@data-label='%s']";

    private final By bankComboboxLocator = By.id("j_idt23:country_label");
    private final By branchComboboxLocator = By.id("j_idt23:city_label");
    private final By recipientAccountTextboxLocator = By.id("j_idt23:soucre");
    private final By recipientNameTextboxLocator = By.id("j_idt23:nameSoucre");
    private final By transferAmountTextboxLocator = By.id("j_idt23:tranf");
    private final By transferMessageTextboxLocator = By.id("j_idt23:j_idt45");
    private final By confirmButtonLocator = By.name("j_idt23:j_idt48");


    public InterbankTransferDetailsForm(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    }

    @Step("Select source account: {accountId}")
    public void selectSourceAccount(int accountId) {
        // Click combobox to open dropdown
        waitAndClick(sourceAccountComboboxLocator);

        // Build xpath for option
        By option = By.xpath(String.format(sourceAccountOptionXpath, accountId));

        // Wait option visible then click
        wait.until(ExpectedConditions.visibilityOfElementLocated(option));
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();

    }

    @Step("Select bank: {bankName}")
    public void selectBank(String bankName) {
        waitAndClick(bankComboboxLocator);
        By option = By.xpath(String.format("//li[normalize-space()='%s']", bankName));
        wait.until(ExpectedConditions.visibilityOfElementLocated(option)).click();
    }

    @Step("Select branch: {branchName}")
    public void selectBranch(String branchName) {
        // Wait after selecting bank to ensure branch is loaded
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        waitAndClick(branchComboboxLocator);
        By option = By.xpath(String.format("//li[@data-label='%s']", branchName));
        wait.until(ExpectedConditions.visibilityOfElementLocated(option)).click();
    }

    @Step("Enter recipient account number: {recipientAccount}")
    public void enterRecipientAccount(int recipientAccount) {
       webDriver.findElement(recipientAccountTextboxLocator).sendKeys(String.valueOf(recipientAccount));
    }

    @Step("Enter recipient name: {recipientName}")
    public void enterRecipientName(String recipientName) {
        webDriver.findElement(recipientNameTextboxLocator).sendKeys(recipientName);
    }

    @Step("Enter transfer amount: {amount}")
    public void enterAmount(int amount) {
        webDriver.findElement(transferAmountTextboxLocator).sendKeys(String.valueOf(amount));
    }

    @Step("Enter payment content: {content}")
    public void enterPaymentContent(String content) {
        webDriver.findElement(transferMessageTextboxLocator).sendKeys(content);
    }

    @Step("Click Confirm button to open transaction confirmation form")
    public void openTransferConfirmationForm() {
        waitAndClick(confirmButtonLocator);
    }

    @Step("Enter all interbank transfer details in order")
    public void enterInterbankTransferDetails(int sourceAccountId, int recipientAccountNumber,
                                              String recipientName, String bankName, String branchName,
                                              String transferMessage, int amount) {
        selectSourceAccount(sourceAccountId);
        enterRecipientAccount(recipientAccountNumber);
        enterRecipientName(recipientName);
        selectBank(bankName);
        selectBranch(branchName);
        enterPaymentContent(transferMessage);
        enterAmount(amount);
        openTransferConfirmationForm();
    }

    // ✅ Utility methods

    private void waitAndClick(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

}
