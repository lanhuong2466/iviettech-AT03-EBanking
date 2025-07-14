package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountDetails {
    private WebDriver webDriver;

    public AccountDetails(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Click on the account to view details")
    public void openAccountDetails(int accountId) {
        // Click on the account details link
        //dùng string format để thay thế
        webDriver.findElement(By.xpath(String.format("//*[@id ='j_idt27_data']//a[text() = '%s']",accountId))).click();
    }

    //tr[%d
    @Step("Get available balance from account details")
    public int getAvailableBalance() {
        int balance = Integer.parseInt(webDriver.findElement(By.xpath("//*[@id = 'j_idt29_content']//tr[6]/td[2]"))
                .getText().replace(" VNĐ", "").replace(",", ""));
        return balance;
    }
    // dem xem co bao nhieu tr o phia trc sau do + 1
}
