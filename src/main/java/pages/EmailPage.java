package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class EmailPage {
    private WebDriver webDriver;
    private By firstMailLocator = By.className("m");
    private By mailContentLocator = By.id("mail");
    private By refreshButtonLocator = By.id("refresh");
    private By listEmailLocator = By.name("ifinbox");
    private By ContentEmailLocator = By.name("ifmail");

    public EmailPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void clickRefreshButton() {
        webDriver.findElement(refreshButtonLocator).click();
    }

    public void openFirstMail() {
        //switch to the iframe that contains the email list
        webDriver.switchTo().frame(webDriver.findElement(listEmailLocator));
        List<WebElement> emails = webDriver.findElements(firstMailLocator);
        if (!emails.isEmpty()) {
            emails.get(0).click();
        } else {
            throw new IllegalStateException("No emails found in inbox.");
        }
    }

    public String getOTPCode() {
        //switch to default content to access the email content
        webDriver.switchTo().defaultContent();
        //switch to the iframe that contains the email content
        webDriver.switchTo().frame(webDriver.findElement(ContentEmailLocator));
        String OTPCode = webDriver.findElement(mailContentLocator).getText().replace("OTP:  ","");
        return OTPCode;
    }
}
