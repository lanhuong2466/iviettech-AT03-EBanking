package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Collectors;

public class AccountsPage {
    private WebDriver webDriver;


    public AccountsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Click on the account to view details")
    public void openAccountDetailsPage(int accountId) {
        List<String> cells = webDriver.findElements(By.xpath("//table//tr/td[1]/a"))
                .stream().map(e -> e.getText()).collect(Collectors.toList());

        int index = cells.indexOf(String.valueOf(accountId));
        if (index != -1) {
            webDriver.findElement(By.xpath(String.format("//div[@id = 'j_idt25_content']/table//tr/td[1]/a[%d]", index + 1))).click();
        }
    }

}
