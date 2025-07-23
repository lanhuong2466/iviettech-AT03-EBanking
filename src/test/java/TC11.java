import io.qameta.allure.Issue;
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

public class TC11 {

    @Issue("Bug03")
    @Test(description = "Verify error message is displayed when non-numeric value is entered as transfer amount")
    public void VerifyErrorMessageIsDisplayedWHenNonNumericValueIsEnteredAsTransferAmount() {
        loginAdminPage.loginAdminAccount(Constants.ADMIN_USERNAME, Constants.ADMIN_PASSWORD);

        adminHomePage.openDepositMoneyForm();


        adminHomePage.depositMoneyIntoABankAccount(sourceAccountId,
                1_000_000, "Nap tien vao tai khoan " + sourceAccountId);


        webDriver.get(Constants.EBANKING_URL);

        loginPage.Login(Constants.USERNAME, Constants.PASSWORD);

        leftMenu.openAccountForm();
        sourceAccountId = 100001403;
        accountsPage.openAccountDetailsPage(sourceAccountId);
        beforeAvailableBalance = accountDetails.getAvailableBalance();

        leftMenu.openTransferForm();

        transferDetailsPage.selectSourceAccount(sourceAccountId);
        transferDetailsPage.enterRecipientAccount(100001394);
        transferDetailsPage.enterAmount("Hello VietNam");
        transferDetailsPage.enterPaymentContent("Huong chuyen khoan 12000 dong");

        transferDetailsPage.openTransferConfirmationForm();

        softAssert.assertEquals(homePage.getpopupErrorText(),
                "Amount must be a postive number.",
                "Nội dung thông báo không đúng");

        leftMenu.openAccountForm();
        accountsPage.openAccountDetailsPage(sourceAccountId);

        afterAvailableBalance = accountDetails.getAvailableBalance();

        softAssert.assertEquals(beforeAvailableBalance, afterAvailableBalance,
                "Số dư khả dụng không đúng sau khi  nhập vào số tiền không phải là số");

        softAssert.assertAll();
    }

    @BeforeMethod
    public void setUp() {

        ChromeOptions options = new ChromeOptions();
        webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.manage().window().maximize();
        softAssert = new SoftAssert();
        loginAdminPage = new LoginAdminPage(webDriver);
        adminHomePage = new AdminHomePage(webDriver);
        loginPage = new LoginPage(webDriver);
        homePage = new HomePage(webDriver);
        homeYopMailPage = new HomeYopMailPage(webDriver);
        emailPage = new EmailPage(webDriver);
        leftMenu = new LeftMenu(webDriver);
        accountsPage = new AccountsPage(webDriver);
        accountDetails = new AccountDetailsPage(webDriver);
        transferDetailsPage = new TransferDetailsPage(webDriver);
        transferConfirmationPage = new TransferConfirmationPage(webDriver);
        webDriver.get(Constants.ADMIN_EBANKING_URL);
        sourceAccountId = 100001403;

    }

    @AfterMethod
    public void tearDown() {
        webDriver.quit();
    }

    WebDriver webDriver;
    SoftAssert softAssert;
    LoginAdminPage loginAdminPage;
    AdminHomePage adminHomePage;
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
