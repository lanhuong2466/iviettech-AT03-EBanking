package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AccountDetails {
    private WebDriver webDriver;

    public AccountDetails(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Click on the account to view details")
    public void openAccountDetails(int accountId) {
        // Click on the account details link
        //dùng string format để thay thế
        webDriver.findElement(By.xpath(String.format("//*[@id ='j_idt27_data']//a[text() = '%s']", accountId))).click();
    }

    //tr[%d] lấy số dư tài khoản: get all text -> index of
    @Step("Get available balance from account details")
    public double getAvailableBalance() {

        List<WebElement> rows = webDriver.findElements(By.xpath("//*[@id = 'j_idt29_content']//tr"));
        int lastRowIndex = rows.size();

        // Tạo XPath động
        String xpath = String.format("//*[@id = 'j_idt29_content']//tr[%d]/td[2]", lastRowIndex - 1);

        // Lấy và xử lý số dư
        double balance = Double.parseDouble(webDriver.findElement(By.xpath(xpath))
                .getText().replace(" VNĐ", "").replace(",", ""));
        return balance;
    }
}
