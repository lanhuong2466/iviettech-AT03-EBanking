package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TransferConfirmationForm {
    private WebDriver webDriver;
    final private By confirmButtonLocator = By.name("j_idt23:j_idt44");

    public TransferConfirmationForm(WebDriver webDriver) {
        this.webDriver = webDriver;
    }


    @Step("Click on confirm button to open OTP entry form")
    public void openOTPEntryForm() {
        webDriver.findElement(confirmButtonLocator).click();
    }

    @Step("Get receive OTP via email text from transfer confirmation form")
    public String getReceiveOTPViaEmailText() {
        // Tạo XPath động
        String xpath = String.format("//*[@id = 'j_idt29_content']//tr[%d]/td[2]", getCountRows());

        String receiveOTPViaEmail = webDriver.findElement(By.xpath(xpath)).getText();
        return receiveOTPViaEmail;
    }

    @Step("Get recipient name from transfer confirmation form")
    public String getRecipientName() {

        // Tạo XPath động
        String xpath = String.format("//*[@id = 'j_idt29_content']//tr[%d]/td[2]", getCountRows() - (getCountRows() - 7));

        String recipientName = webDriver.findElement(By.xpath(xpath)).getText();
        return recipientName;
    }

    @Step("Get recipient account ID from transfer confirmation form")
    public int getRecipientAccountId() {

        // Tạo XPath động
        String xpath = String.format("//*[@id = 'j_idt29_content']//tr[%d]/td[2]", getCountRows() - (getCountRows() - 6));

        int recipientAccountId = Integer.parseInt(webDriver.findElement(By.xpath(xpath)).getText());
        return recipientAccountId;
    }

    @Step("Get transfer message from transfer confirmation form")
    public String getTransferMessageNumber() {

        // Tạo XPath động
        String xpath = String.format("//*[@id = 'j_idt29_content']//tr[%d]/td[2]", getCountRows() - (getCountRows() - 5));

        String transferAmountMessage = webDriver.findElement(By.xpath(xpath)).getText();
        return transferAmountMessage;
    }

    @Step("Get transfer amount from transfer confirmation form")
    public double getTransferAmountNumber() {

        // Tạo XPath động
        String xpath = String.format("//*[@id = 'j_idt29_content']//tr[%d]/td[2]", getCountRows() - (getCountRows() - 3));

        double transferAmountNumber = Double.parseDouble(webDriver.findElement(By.xpath(xpath)).getText());
        return transferAmountNumber;
    }

    @Step("Get source account ID from transfer confirmation form")
    public int getSourceAccountId() {

        // Tạo XPath động
        String xpath = String.format("//*[@id = 'j_idt29_content']//tr[%d]/td[2]", getCountRows() - (getCountRows() - 1));

        int sourceAccountId = Integer.parseInt(webDriver.findElement(By.xpath(xpath)).getText());
        return sourceAccountId;
    }

    @Step("Get count of rows in the transfer confirmation form")
    public int getCountRows() {
        List<WebElement> rows = webDriver.findElements(By.xpath("//*[@id = 'j_idt23:j_idt24_content']//tr"));
        return rows.size();
    }

}
