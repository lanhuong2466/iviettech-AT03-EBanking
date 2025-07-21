package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LeftMenu {
    private WebDriver webDriver;
    final private By tranferMenuItemLocator = By.xpath("//*[@class = 'ui-menuitem-text'][text() = 'Chuyển  khoản']");
    final private By interbankTransferMenuItemLocator = By.xpath("//*[@class = 'ui-menuitem-text'][text() = 'Liên Ngân Hàng']");
    final private By accountMenuItemLocator = By.xpath("//*[@class = 'ui-menuitem-text'][text() = 'Tài khoản']");


    public LeftMenu(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Click on transfer menu item")
    public void openTransferForm() {
        webDriver.findElement(tranferMenuItemLocator).click();
    }

    @Step("Click on interbank transfer menu item")
    public void openInterbankTransferForm() {
        webDriver.findElement(interbankTransferMenuItemLocator).click();
    }

    @Step("Click on account menu item")
    public void openAccountForm() {
        webDriver.findElement(accountMenuItemLocator).click();
    }
}
