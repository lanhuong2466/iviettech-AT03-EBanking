package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountsPage {
    private WebDriver webDriver;


    public AccountsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Click on the account to view details")
    public void openAccountDetailsForm(int accountId) {
        webDriver.findElement(By.xpath(String.format("//*[@id ='j_idt27_data']//a[text() = '%s']", accountId))).click();
    }


}
