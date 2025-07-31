package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class InterbankTransferConfirmationForm {

    private WebDriver webDriver;
    private WebDriverWait wait;

    // Locator chính cho bảng xác nhận
    private final By confirmationTableRowsLocator = By.xpath("//table//tr/td[1]/label");

    // Locator của nút xác nhận
    private final By confirmButtonLocator = By.name("j_idt23:j_idt44");

    // Locator của ô nhập OTP (sau khi nhấn nút xác nhận)
    private final By otpFieldLocator = By.name("j_idt23:j_idt45");

    public InterbankTransferConfirmationForm(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    }

    @Step("Click confirm to proceed to OTP entry")
    public void openOTPEntryForm() {
        try {
            WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(confirmButtonLocator));
            confirmButton.click();
        } catch (Exception e) {
            System.out.println("[WARN] Normal click failed. Trying JavaScript click.");
            WebElement confirmButton = webDriver.findElement(confirmButtonLocator);
            ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", confirmButton);
        }

        // Wait until OTP field appears
        wait.until(ExpectedConditions.visibilityOfElementLocated(otpFieldLocator));
    }

    // ========================
    // Generic value retriever
    // ========================

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

    // ================
    // Getter methods
    // ================

    @Step("Get source account ID")
    public int getSourceAccountId() {
        return Integer.parseInt(getValueFromTable("Tài khoản gửi"));
    }

    @Step("Get recipient account ID")
    public int getRecipientAccountId() {
        return Integer.parseInt(getValueFromTable("Tài khoản nhận"));
    }

    @Step("Get transfer amount")
    public int getTransferAmount() {
        String text = getValueFromTable("Số tiền chuyển khoản")
                .replace(" VNĐ", "")
                .replace(",", "")
                .trim();
        return Integer.parseInt(text);
    }

    @Step("Get transfer amount in word")
    public String getTransferAmountInWord() {
        return getValueFromTable("Số tiền bằng chữ");
    }

    @Step("Get transfer message")
    public String getTransferMessage() {
        return getValueFromTable("Nội dung chuyển khoản");
    }

    @Step("Get recipient name")
    public String getRecipientName() {
        return getValueFromTable("Tên chủ tài khoản");
    }
}
