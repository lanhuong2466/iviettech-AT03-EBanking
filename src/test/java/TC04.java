import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.EBanking.AccountDetails;
import pages.EBanking.HomePage;
import pages.EBanking.LeftMenu;
import pages.EBanking.LoginPage;
import pages.Yopmail.EmailPage;
import pages.Yopmail.HomeYopMailPage;
import utils.Constants;
import utils.WindowSwitcher;

import java.time.Duration;

public class TC04 {
    @Test
    public void VerifyErrorPopupIsDisplayedWhenTheDestinationAccountIsInvalidOrNotEntered() {
        loginPage.Login(Constants.USERNAME, Constants.PASSWORD);

        leftMenu.openTransferForm();

        homePage.enterTranferDetails(100001403,
                100002000,
                12000,
                "Huong chuyen khoan 12000 dong");

        homePage.openTransactionConfirmationForm();

        softAssert.assertTrue(homePage.isPopupInvalidAccountDisplayed(), "Popup Tài khoản không hợp lệ, quý khách vui lòng kiểm tra lại không hiển thị");

        webDriver.switchTo().newWindow(WindowType.TAB);
        WindowSwitcher.switchToNewWindow(webDriver, webDriver.getWindowHandle());
        webDriver.get(Constants.EBANKING_URL);

        leftMenu.openTransferForm();

        homePage.selectSourceAccount(100001403);
        homePage.enterAmount(12000);
        homePage.enterPaymentContent("Huong chuyen khoan 12000 dong");
        homePage.openTransactionConfirmationForm();

        softAssert.assertTrue(homePage.isPopupInvalidAccountDisplayed(), "Popup Tài khoản không hợp lệ, quý khách vui lòng kiểm tra lại không hiển thị");
        softAssert.assertAll();
    }

    @BeforeMethod
    public void setUp() {

        ChromeOptions options = new ChromeOptions();
        webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.manage().window().maximize();
        softAssert = new SoftAssert();
        loginPage = new LoginPage(webDriver);
        homePage = new HomePage(webDriver);
        homeYopMailPage = new HomeYopMailPage(webDriver);
        emailPage = new EmailPage(webDriver);
        leftMenu = new LeftMenu(webDriver);
        accountDetails = new AccountDetails(webDriver);
        webDriver.get(Constants.EBANKING_URL);

    }

    @AfterMethod
    public void tearDown() {
        webDriver.quit();
    }

    WebDriver webDriver;
    SoftAssert softAssert;
    LoginPage loginPage;
    HomePage homePage;
    HomeYopMailPage homeYopMailPage;
    EmailPage emailPage;
    LeftMenu leftMenu;
    AccountDetails accountDetails;

}
