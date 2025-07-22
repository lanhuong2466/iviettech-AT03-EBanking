package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Collectors;

public class TransferConfirmationPage {
    private WebDriver webDriver;
    final private By confirmButtonLocator = By.name("j_idt23:j_idt44");

    public TransferConfirmationPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }


    @Step("Click on confirm button to open OTP entry form")
    public void openOTPEntryForm() {
        webDriver.findElement(confirmButtonLocator).click();
    }

    @Step("Get receive OTP via email text from transfer confirmation form")
    public String getReceiveOTPViaEmailText() {
        String receiveOTPViaEmailText = getValueFromTable("Hình thức nhận mã giao dịch");
        return receiveOTPViaEmailText;
    }

    @Step("Get recipient name from transfer confirmation form")
    public String getRecipientName() {
        String recipientNameText = getValueFromTable("Tên chủ tài khoản");
        return recipientNameText;
    }

    @Step("Get recipient account ID from transfer confirmation form")
    public int getRecipientAccountId() {
        String recipientAccountIdText = getValueFromTable("Tài khoản nhận");
        return Integer.parseInt(recipientAccountIdText);
    }

    @Step("Get transfer message from transfer confirmation form")
    public String getTransferMessage() {
        String transferMessageText = getValueFromTable("Nội dung chuyển khoản");
        return transferMessageText;
    }


    @Step("Get transfer amount from transfer confirmation form")
    public int getTransferAmount() {
        String transferAmountText = getValueFromTable("Số tiền chuyển khoản");
        return Integer.parseInt(transferAmountText.replace(" VNĐ", "").replace(",", ""));
    }

    @Step("Get source account ID from transfer confirmation form")
    public int getSourceAccountId() {
        String sourceAccountIdText = getValueFromTable("Tài khoản gửi");
        return Integer.parseInt(sourceAccountIdText);
    }

    @Step("Get value from table by label")
    private String getValueFromTable(String label) {
        List<String> cells = webDriver.findElements(By.xpath("//table//tr/td[1]/label"))
                .stream().map(e -> e.getText().trim()).collect(Collectors.toList());

        int index = cells.indexOf(label);
        if (index != -1) {
            return webDriver.findElement(By.xpath(
                    String.format("//table//tr[%d]/td[2]/label", index + 1)
            )).getText().trim();
        } else {
            throw new RuntimeException("Không tìm thấy thông tin với label: " + label);
        }
    }

}





