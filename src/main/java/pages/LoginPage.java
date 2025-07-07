package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;
    final private By usernameTextBoxLocator = By.name("j_idt10:j_idt12");
    final private By passwordTextBoxLocator = By.name("j_idt10:j_idt14");
    final private By loginButtonLocator = By.name("j_idt10:j_idt16");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        driver.findElement(usernameTextBoxLocator).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordTextBoxLocator).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButtonLocator).click();
    }

    public void Login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
}
