package pages.Yopmail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomeYopMailPage {
    private WebDriver webDriver;
    private By emailTextBoxLocator = By.id("login");
    private By refreshButtonLocator = By.id("refreshbut");

    public HomeYopMailPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void enterEmail(String email) {
        webDriver.findElement(emailTextBoxLocator).sendKeys(email);
    }

    public void openEmailPage() {
        webDriver.findElement(refreshButtonLocator).click();
    }

    public void loginToYopMail(String email) {
        enterEmail(email);
        openEmailPage();
    }
}
