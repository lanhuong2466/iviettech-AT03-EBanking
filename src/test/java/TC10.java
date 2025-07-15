package tests;

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

public class TC10 {

    @Test(description = "Verify error message is displayed when transferring to the same account")
    public void VerifyErrorMessageIsDisplayedWhenTransferringToSameAccount() {

        // Login
        loginPage.Login(Constants.USERNAME, Constants.PASSWORD);

        // Get available balance before transfer
        leftMenu.openAccountDetailForm();
        accountDetails.openAccountDetails(100001403);
        beforeAvailableBalance = accountDetails.getAvailableBalance();

        // Open transfer form and enter transfer details with same source & destination account
        leftMenu.openTransferForm();
        transferDetailsForm.enterTransferDetails(
                100001403,   // Source account
                100001403,   // Destination account (same as source)
                12000,       // Amount
                "Test chuyển tiền cùng account"
        );

        // Attempt to open transaction confirmation
        transferDetailsForm.openTransferConfirmationForm();

        // Verify popup error displayed for transferring to same account
        softAssert.assertTrue(homePage.isPopupErrorDisplayed(),
                "Không hiển thị popup lỗi khi chuyển cùng số tài khoản");

        // Verify available balance is unchanged
        leftMenu.openAccountDetailForm();
        accountDetails.openAccountDetails(100001403);
        afterAvailableBalance = accountDetails.getAvailableBalance();

        softAssert.assertEquals(beforeAvailableBalance,
                afterAvailableBalance,
                "Số dư khả dụng bị thay đổi khi chuyển cùng số tài khoản");

        // Report all asserts
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
        transferDetailsForm = new TransferDetailsForm(webDriver);
        transferConfirmationForm = new TransferConfirmationForm(webDriver);
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
    TransferDetailsForm transferDetailsForm;
    TransferConfirmationForm transferConfirmationForm;
    double beforeAvailableBalance;
    double afterAvailableBalance;
}
