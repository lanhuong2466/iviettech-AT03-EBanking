import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.EBanking.*;
import pages.Yopmail.EmailPage;
import pages.Yopmail.HomeYopMailPage;
import utils.Constants;

import java.time.Duration;

public class TC07 {

    @Test
    public void VerifyErrorMessageIsDisplayedWhenTransferringWithNoContent() {
        loginPage.Login(Constants.USERNAME, Constants.PASSWORD);

        leftMenu.openAccountForm();
        sourceAccountId = 100001403;
        accountsPage.openAccountDetailsPage(sourceAccountId);
        beforeAvailableBalance = accountDetails.getAvailableBalance();

        leftMenu.openTransferForm();

        transferDetailsPage.enterTransferDetails(sourceAccountId,
                100001399,
                12000,
                "");

        transferDetailsPage.openTransferConfirmationForm();

        softAssert.assertTrue(homePage.isPopupErrorDisplayed(), "Popup thông báo Nhập nội dung không hiển thị");
        softAssert.assertEquals(homePage.getpopupErrorText(), "Nhập nội dung", "Nội dung thông báo không đúng");

        leftMenu.openAccountForm();
        accountsPage.openAccountDetailsPage(sourceAccountId);
        afterAvailableBalance = accountDetails.getAvailableBalance();

        softAssert.assertEquals(beforeAvailableBalance,
                afterAvailableBalance,
                "Số dư khả dụng không đúng sau khi chuyển tiền mà không nhập nội dung");

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
        accountsPage = new AccountsPage(webDriver);
        accountDetails = new AccountDetailsPage(webDriver);
        transferDetailsPage = new TransferDetailsPage(webDriver);
        transferConfirmationPage = new TransferConfirmationPage(webDriver);
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
    AccountsPage accountsPage;
    AccountDetailsPage accountDetails;
    TransferDetailsPage transferDetailsPage;
    TransferConfirmationPage transferConfirmationPage;
    int beforeAvailableBalance;
    int afterAvailableBalance;
    int sourceAccountId;

}
