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

    // Cached data
    private int cachedSourceAccountId;
    private String cachedRecipientAccountId;
    private String cachedRecipientName;
    private String cachedBankName;
    private String cachedBranchName;
    private String cachedTransferMessage;
    private double cachedAmount;

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

        cachedSourceAccountId = accountId;
    }

    @Step("Select bank: {bankName}")
    public void selectBank(String bankName) {
        waitAndClick(bankComboboxLocator);
        By option = By.xpath(String.format("//li[normalize-space()='%s']", bankName));
        wait.until(ExpectedConditions.visibilityOfElementLocated(option)).click();
        cachedBankName = bankName;
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
        cachedBranchName = branchName;
    }

    @Step("Enter recipient account: {recipientAccount}")
    public void enterRecipientAccount(String recipientAccount) {
        clearAndType(recipientAccountTextboxLocator, recipientAccount);
        cachedRecipientAccountId = recipientAccount;
    }

    @Step("Enter recipient name: {recipientName}")
    public void enterRecipientName(String recipientName) {
        clearAndType(recipientNameTextboxLocator, recipientName);
        cachedRecipientName = recipientName;
    }

    @Step("Enter transfer amount: {amount}")
    public void enterAmount(double amount) {
        clearAndType(transferAmountTextboxLocator, String.valueOf(amount));
        cachedAmount = amount;
    }

    @Step("Enter payment content: {content}")
    public void enterPaymentContent(String content) {
        clearAndType(transferMessageTextboxLocator, content);
        cachedTransferMessage = content;
    }

    @Step("Click Confirm button to open transaction confirmation form")
    public void openTransferConfirmationForm() {
        waitAndClick(confirmButtonLocator);
    }

    @Step("Enter all interbank transfer details in order")
    public void enterInterbankTransferDetails(int sourceAccountId, String recipientAccountNumber,
                                              String recipientName, String bankName, String branchName,
                                              String transferMessage, double amount) {
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

    private void clearAndType(By locator, String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).clear();
        webDriver.findElement(locator).sendKeys(text);
    }

    // ✅ Getter methods for cached data if needed (commented out)
    /*
    public int getCachedSourceAccountId() { return cachedSourceAccountId; }
    public String getCachedRecipientAccountId() { return cachedRecipientAccountId; }
    public String getCachedRecipientName() { return cachedRecipientName; }
    public String getCachedBankName() { return cachedBankName; }
    public String getCachedBranchName() { return cachedBranchName; }
    public String getCachedTransferMessage() { return cachedTransferMessage; }
    public double getCachedAmount() { return cachedAmount; }
    */
}
