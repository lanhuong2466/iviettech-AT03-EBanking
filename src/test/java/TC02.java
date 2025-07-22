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

public class TC02 {

    @Test(description = "Verify successful transfer to an external bank account with full flow steps including Admin deposit")
    public void VerifySuccessfulFundTransferToExternalBankAccount() {

        // 0. Login Admin to deposit money first
        webDriver.get(Constants.ADMIN_EBANKING_URL);
        loginAdminPage.loginAdminAccount(Constants.ADMIN_USERNAME, Constants.ADMIN_PASSWORD);
        adminHomePage.openDepositMoneyForm();
        adminHomePage.depositMoneyIntoABankAccount(sourceAccountId,
                10_000_000, "Nap tien vao tai khoan " + sourceAccountId);

        // 1. Login EBanking user account
        webDriver.get(Constants.EBANKING_URL);
        loginPage.Login(Constants.USERNAME, Constants.PASSWORD);

        // 2. Get available balance before transfer
        leftMenu.openAccountForm();
        accountDetails.openAccountDetails(sourceAccountId);
        beforeAvailableBalance = accountDetails.getAvailableBalance();

        // 3. Open interbank transfer form and input details
        leftMenu.openInterbankTransferForm();
        interbankTransferDetailsForm.enterInterbankTransferDetails(
                sourceAccountId,
                recipientAccountNumber,
                recipientName,
                bankName,
                branchName,
                transferMessage,
                amount
        );

        // 4. Verify confirm form data matches detail form data
        softAssert.assertEquals(
                interbankTransferConfirmationForm.getSourceAccountId(),
                sourceAccountId,
                "Số tài khoản nguồn không khớp"
        );

        softAssert.assertEquals(
                interbankTransferConfirmationForm.getRecipientAccountId(),
                recipientAccountNumber,
                "Số tài khoản người nhận không khớp"
        );

        softAssert.assertEquals(
                interbankTransferConfirmationForm.getRecipientName(),
                recipientName,
                "Tên Tài Khoản không khớp"
        );

        softAssert.assertEquals(
                interbankTransferConfirmationForm.getTransferMessage(),
                transferMessage,
                "Nội dung chuyển khoản không khớp"
        );

        softAssert.assertEquals(
                interbankTransferConfirmationForm.getTransferAmount(),
                amount,
                0.01,
                "Số tiền chuyển khoản không khớp"
        );

        // 5. Click Confirm button to move to OTP entry form
        interbankTransferConfirmationForm.openOTPEntryForm();

        // 6. Switch to Yopmail tab to get OTP
        originalWindow = webDriver.getWindowHandle();
        webDriver.switchTo().newWindow(org.openqa.selenium.WindowType.TAB);

        webDriver.get(Constants.YOPMAIL_URL);
        homeYopMailPage.loginToYopMail(Constants.EMAIL);
        emailPage.clickRefreshButton();
        emailPage.openFirstMail();
        OTPCode = emailPage.getOTPCode();

        // 7. Switch back to EBanking to input OTP
        webDriver.close();
        webDriver.switchTo().window(originalWindow);

        otpEntryPage.submitOTPCode(OTPCode);

        // 8. Verify transfer success popup
        softAssert.assertTrue(
                otpEntryPage.isTransferSuccessPopupDisplayed(),
                "Transfer success popup not displayed"
        );

        homePage.closeTheNotificationPopup();

        // 9. Verify available balance after transfer (assume fee = 3300)
        leftMenu.openAccountForm();
        accountDetails.openAccountDetails(sourceAccountId);
        afterAvailableBalance = accountDetails.getAvailableBalance();

        softAssert.assertEquals(
                afterAvailableBalance,
                beforeAvailableBalance - amount - 3300,
                0.01,
                "Available balance incorrect after transfer"
        );

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
        accountDetails = new AccountDetails(webDriver);
        interbankTransferDetailsForm = new InterbankTransferDetailsForm(webDriver);
        interbankTransferConfirmationForm = new InterbankTransferConfirmationForm(webDriver);
        otpEntryPage = new OTPEntryPage(webDriver);

        // Mở trang admin trước khi test
        webDriver.get(Constants.ADMIN_EBANKING_URL);
    }

    @AfterMethod
    public void tearDown() {
        webDriver.quit();
    }

    // Variables
    WebDriver webDriver;
    SoftAssert softAssert;

    LoginAdminPage loginAdminPage;
    AdminHomePage adminHomePage;
    LoginPage loginPage;
    HomePage homePage;
    HomeYopMailPage homeYopMailPage;
    EmailPage emailPage;
    LeftMenu leftMenu;
    AccountDetails accountDetails;
    InterbankTransferDetailsForm interbankTransferDetailsForm;
    InterbankTransferConfirmationForm interbankTransferConfirmationForm;
    OTPEntryPage otpEntryPage;

    double beforeAvailableBalance;
    double afterAvailableBalance;
    String originalWindow;
    String OTPCode;

    final int amount = 996700;
    final String recipientAccountNumber = "10001111";
    final String recipientName = "Nguyen Van A";
    final String bankName = "Ngân hàng Đông Á";
    final String branchName = "Chi nhánh Đà Nẵng";
    final String transferMessage = "Chuyen tien lien ngan hang 996,700 dong";
    final int sourceAccountId = 100001403;
}
