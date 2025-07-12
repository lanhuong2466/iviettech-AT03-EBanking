package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver webDriver;
    final private By sourceAccountComboboxLocator = By.id("j_idt23:j_idt28_label");
    final private By destinationAccountTextboxLocator = By.id("j_idt23:j_idt35");
    final private By amountTextboxLocator = By.id("j_idt23:j_idt40");
    final private By paymentContentTextboxLocator = By.id("j_idt23:j_idt42");
    final private By confirmButtonLocator = By.name("j_idt23:j_idt44");
    final private By OTPTextboxLocator = By.name("j_idt23:j_idt46");
    final private By TranferButtonLocator = By.name("j_idt23:j_idt48");
    final private By availableBalanceLocator = By.id("j_idt23:amount");
    final private By popupTransferSuccessLocator = By.xpath(
            "//*[@id = 'primefacesmessagedlg']//div[text() = 'Chuyển tiền thành công']");
    final private By popupSelectAnAccountLocator = By.xpath(
            "//span[@class = 'ui-growl-title'][text() = 'Mời chọn tài khoản']");
    final private By popupInvalidAccountLocator = By.xpath(
            "//span[@class = 'ui-growl-title'][text() = 'Tài khoản không hợp lệ, quý khách vui lòng kiểm tra lại.']");
    final private By popupNegativeAmountLocator = By.xpath(
            "//span[@class = 'ui-growl-title'][text() = 'must be greater than or equal to 0']");
    final private By popupInsufficientFundsLocator = By.xpath(
            "//span[@class = 'ui-growl-title'][text() = 'Số tiền vượt mức']");


    @Step("Verify the popup for insufficient funds is displayed")
    public boolean isPopupInsufficientFundsDisplayed() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(popupInsufficientFundsLocator));
        return true;
    }

    @Step("Get text from negative amount popup")
    public String getpopupNegativeAmountText() {
        return webDriver.findElement(popupNegativeAmountLocator).getText();
    }

    @Step("Verify the popup for negative amount is displayed")
    public boolean isPopupNegativeAmountDisplayed() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(popupNegativeAmountLocator));
        return true;

    }

    @Step("Verify the popup for invalid account is displayed")
    public boolean isPopupInvalidAccountDisplayed() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(popupInvalidAccountLocator));
        return true;

    }

    @Step("Verify the popup to select an account is displayed")
    public boolean isPopupSelectAnAccountDisplayed() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(popupSelectAnAccountLocator));
        return true;
    }

    @Step("Get available balance")
    public int getAvailableBalance() {
        int balance = Integer.parseInt(webDriver.findElement(availableBalanceLocator).getText().replace(" VNĐ", "").replace(",", ""));
        return balance;
    }

    @Step("Click transfer button")
    public void clickTransferButton() {
        webDriver.navigate().refresh();
        webDriver.findElement(TranferButtonLocator).click();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(popupTransferSuccessLocator));
    }

    @Step("Enter OTP code")
    public void enterOTPCode(String otp) {
        webDriver.navigate().refresh();
        webDriver.findElement(OTPTextboxLocator).sendKeys(otp);
    }

    @Step("Select source account")
    public void selectSourceAccount(int accountId) {
        webDriver.findElement(sourceAccountComboboxLocator).click();
        webDriver.findElement(By.xpath("//li[text() = '" + String.valueOf(accountId) + "']")).click();
    }

    @Step("Enter destination account")
    public void enterDestinationAccount(int destinationAccount) {
        webDriver.findElement(destinationAccountTextboxLocator).sendKeys(String.valueOf(destinationAccount));
    }

    @Step("Enter amount")
    public void enterAmount(int amount) {
        webDriver.findElement(amountTextboxLocator).sendKeys(String.valueOf(amount));
    }

    @Step("Enter payment content")
    public void enterPaymentContent(String content) {
        webDriver.findElement(paymentContentTextboxLocator).sendKeys(content);
    }

    @Step("Click on Confirm button to open transaction confirm form")
    public void openTransactionConfirmationForm() {
        webDriver.findElement(confirmButtonLocator).click();
    }

    @Step("Enter transfer details")
    public void enterTranferDetails(int sourceAccount, int destinationAccount, int amount, String content) {
        selectSourceAccount(sourceAccount);
        enterDestinationAccount(destinationAccount);
        enterAmount(amount);
        enterPaymentContent(content);
    }

    @Step("Click on confirm button to open OTP entry form")
    public void openOTPEntryForm() {
        webDriver.findElement(confirmButtonLocator).click();
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

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

}

