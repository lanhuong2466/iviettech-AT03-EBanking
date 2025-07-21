package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Collectors;

public class AccountDetailsPage {
    private WebDriver webDriver;

    public AccountDetailsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }


    @Step("Get available balance from account details")
    public int getAvailableBalance() {
        List<String> cells = webDriver.findElements(By.xpath("//table//tr/td[1]/label"))
                .stream().map(e -> e.getText()).collect(Collectors.toList());

        int index = cells.indexOf("Số dư tài khoản");
        if (index != -1) {
            String balanceText = webDriver.findElement(By.xpath(String.format("//div[@id = 'j_idt29_content']/table//tr[%d]/td[2]", index + 1))).getText();
            return Integer.parseInt(balanceText.replace(" VNĐ", "").replace(",", ""));
        } else {
            throw new RuntimeException("Không tìm thấy thông tin số dư tài khoản");
        }
    }

}

