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

public class TC04 {


    @Test(description = "Verify error message is displayed when the destination account does not exist")
    public void VerifyErrorPopupIsDisplayedWhenTheDestinationAccountIsInvalid() {
        loginAdminPage.loginAdminAccount(Constants.ADMIN_USERNAME, Constants.ADMIN_PASSWORD);

        adminHomePage.openDepositMoneyForm();


        adminHomePage.depositMoneyIntoABankAccount(sourceAccountId,
                1_000_000, "Nap tien vao tai khoan " + sourceAccountId);


        webDriver.get(Constants.EBANKING_URL);
        loginPage.Login(Constants.USERNAME, Constants.PASSWORD);

        leftMenu.openAccountForm();

        accountsPage.openAccountDetailsPage(sourceAccountId);
        beforeAvailableBalance = accountDetails.getAvailableBalance();

        leftMenu.openTransferForm();

        transferDetailsPage.enterTransferDetails(sourceAccountId,
                100002000,
                12000,
                "Huong chuyen khoan 12000 dong");

        transferDetailsPage.openTransferConfirmationForm();

        softAssert.assertTrue(homePage.isPopupErrorDisplayed(), "Popup Tài khoản không hợp lệ, quý khách vui lòng kiểm tra lại không hiển thị");

        leftMenu.openAccountForm();
        accountsPage.openAccountDetailsPage(sourceAccountId);
        afterAvailableBalance = accountDetails.getAvailableBalance();

        softAssert.assertEquals(beforeAvailableBalance, afterAvailableBalance,
                "Số dư khả dụng không đúng sau khi chuyển tiền đến tài khoản không hợp lệ");

        softAssert.assertAll();
    }

    @Test(description = "Verify error message is displayed when the destination account is not entered")
    public void VerifyErrorPopupIsDisplayedWhenTheDestinationAccountIsNotEntered() {
        loginAdminPage.loginAdminAccount(Constants.ADMIN_USERNAME, Constants.ADMIN_PASSWORD);

        adminHomePage.openDepositMoneyForm();


        adminHomePage.depositMoneyIntoABankAccount(sourceAccountId,
                1_000_000, "Nap tien vao tai khoan " + sourceAccountId);


        webDriver.get(Constants.EBANKING_URL);
        loginPage.Login(Constants.USERNAME, Constants.PASSWORD);

        leftMenu.openAccountForm();
        accountsPage.openAccountDetailsPage(sourceAccountId);
        beforeAvailableBalance = accountDetails.getAvailableBalance();

        leftMenu.openTransferForm();
        transferDetailsPage.selectSourceAccount(100001403);
        transferDetailsPage.enterAmount(12000);
        transferDetailsPage.enterPaymentContent("Huong chuyen khoan 12000 dong");
        transferDetailsPage.openTransferConfirmationForm();

        softAssert.assertTrue(homePage.isPopupErrorDisplayed(), "Popup Tài khoản không hợp lệ, quý khách vui lòng kiểm tra lại không hiển thị");

        leftMenu.openAccountForm();
        accountsPage.openAccountDetailsPage(sourceAccountId);
        afterAvailableBalance = accountDetails.getAvailableBalance();

        softAssert.assertEquals(beforeAvailableBalance, afterAvailableBalance,
                "Số dư khả dụng không đúng sau khi chuyển tiền đến tài khoản không hợp lệ");

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
        loginAdminPage = new LoginAdminPage(webDriver);
        adminHomePage = new AdminHomePage(webDriver);
        homePage = new HomePage(webDriver);
        homeYopMailPage = new HomeYopMailPage(webDriver);
        emailPage = new EmailPage(webDriver);
        leftMenu = new LeftMenu(webDriver);
        accountsPage = new AccountsPage(webDriver);
        accountDetails = new AccountDetailsPage(webDriver);
        transferDetailsPage = new TransferDetailsPage(webDriver);
        webDriver.get(Constants.ADMIN_EBANKING_URL);
        sourceAccountId = 100001403;

    }

    @AfterMethod
    public void tearDown() {
        webDriver.quit();
    }

    WebDriver webDriver;
    SoftAssert softAssert;
    LoginPage loginPage;
    LoginAdminPage loginAdminPage;
    AdminHomePage adminHomePage;
    HomePage homePage;
    HomeYopMailPage homeYopMailPage;
    EmailPage emailPage;
    LeftMenu leftMenu;
    AccountsPage accountsPage;
    AccountDetailsPage accountDetails;
    TransferDetailsPage transferDetailsPage;
    int beforeAvailableBalance;
    int afterAvailableBalance;
    int sourceAccountId;

}
