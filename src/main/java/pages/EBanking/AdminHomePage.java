package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminHomePage {
    private WebDriver webDriver;
    private By depositMoneyMenuItemLocator = By.xpath("//span[@class = 'ui-menuitem-text'][text() = 'Nộp Tiền']");
    private By exitButtonLocator = By.name("j_idt9:j_idt19");

    public AdminHomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Click on the 'Nộp Tiền' menu item")
    public void openDepositMoneyForm() {
        webDriver.findElement(depositMoneyMenuItemLocator).click();
    }

    @Step("Enter recipient account ID")
    public void enterRepicientAccountId(int repicientAccountId) {
        By rowLocator = By.xpath("//table//tr[td[text()='Tài khoản nhận']]");
        webDriver.findElement(rowLocator).findElement(By.tagName("input"))
                .sendKeys(String.valueOf(repicientAccountId));
    }

    @Step("Enter amount to deposit")
    public void enterAmount(int amount) {
        By rowLocator = By.xpath("//table//tr[td[text()='Số tiền']]");
        webDriver.findElement(rowLocator).findElement(By.tagName("input"))
                .sendKeys(String.valueOf(amount));
    }

    @Step("Enter message for the transaction")
    public void enterMessage(String message) {
        By rowLocator = By.xpath("//table//tr[td[text()='Nội dung thanh toán']]");
        webDriver.findElement(rowLocator).findElement(By.tagName("input"))
                .sendKeys(message);
    }

    @Step("Click on the submit button to confirm the deposit")
    public void clickSubmitButton() {
        webDriver.findElement(By.xpath("//input[@value='Xác nhận']")).click();
    }

    @Step("Click on the exit button")
    public void clickExitButton() {
        webDriver.findElement(exitButtonLocator).click();
    }

    @Step("Deposit money into a bank account")
    public void depositMoneyIntoABankAccount(int recipientAccountId, int amount, String message) {
        enterRepicientAccountId(recipientAccountId);
        enterAmount(amount);
        enterMessage(message);
        clickSubmitButton();
        clickExitButton();
    }
}
