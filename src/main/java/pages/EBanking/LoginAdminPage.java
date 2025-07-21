package pages.EBanking;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginAdminPage {
    private WebDriver driver;
    final private By usernameAdminTextBoxLocator = By.id("j_idt9:id1");
    final private By passwordAdminTextBoxLocator = By.id("j_idt9:pwd1");
    final private By loginButtonLocator = By.id("j_idt9:j_idt15");


    public LoginAdminPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Enter username")
    public void enterUsernameAdmin(String username) {
        driver.findElement(usernameAdminTextBoxLocator).sendKeys(username);
    }

    @Step("Enter password")
    public void enterPasswordAdmin(String password) {
        driver.findElement(passwordAdminTextBoxLocator).sendKeys(password);
    }

    @Step("Click login button")
    public void clickLoginButton() {
        driver.findElement(loginButtonLocator).click();
    }

    @Step("Login with username and password")
    public void loginAdminAccount(String username, String password) {
        enterUsernameAdmin(username);
        enterPasswordAdmin(password);
        clickLoginButton();
    }
}
