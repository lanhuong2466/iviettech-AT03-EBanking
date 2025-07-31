import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.EBanking.*;
import utils.Constants;

import java.time.Duration;

public class TC09 {

    @Test(description = "Verify error message is displayed when entering incorrect OTP during fund transfer")
    public void verifyErrorMessageIsDisplayedWhenEnteringIncorrectOTP() {
        // Initialize SoftAssert
        softAssert = new SoftAssert();

        // Login to user account
        loginPage.Login(Constants.USERNAME, Constants.PASSWORD);

        // Navigate to account and get available balance
        leftMenu.openAccountForm();
        sourceAccountId = 100001403;
        accountsPage.openAccountDetailsPage(sourceAccountId);
        beforeAvailableBalance = accountDetails.getAvailableBalance();

        // Open transfer form and input transfer details
        leftMenu.openTransferForm();
        transferDetailsPage.enterTransferDetails(
                sourceAccountId,    // Source account
                100001399,          // Destination account
                12,                 // Amount
                "Test chuyen khoan voi OTP sai"
        );

        // Proceed to transfer confirmation and OTP entry form
        transferDetailsPage.openTransferConfirmationForm();
        transferConfirmationPage.openOTPEntryForm();

        // Generate random invalid OTP (10 alphanumeric characters)
        OTPCode = RandomStringUtils.randomAlphanumeric(10);

        // Enter invalid OTP and click transfer
        otpEntryPage.enterOTPCode(OTPCode);
        otpEntryPage.clickTransferButtonWithoutWaitingSuccessPopup();

        // Verify error popup is displayed with appropriate message
        softAssert.assertTrue(
                otpEntryPage.isInvalidOTPPopupDisplayed(),
                "Popup error khong hien thi khi nhap sai OTP"
        );

        // Assert all results
        softAssert.assertAll();
    }

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.manage().window().maximize();

        // Initialize page objects
        loginPage = new LoginPage(webDriver);
        homePage = new HomePage(webDriver);
        leftMenu = new LeftMenu(webDriver);
        accountsPage = new AccountsPage(webDriver);
        accountDetails = new AccountDetailsPage(webDriver);
        transferDetailsPage = new TransferDetailsPage(webDriver);
        transferConfirmationPage = new TransferConfirmationPage(webDriver);
        otpEntryPage = new OTPEntryPage(webDriver);

        // Navigate to site
        webDriver.get(Constants.EBANKING_URL);
    }

    @AfterMethod
    public void tearDown() {
        webDriver.quit();
    }

    // ==== WebDriver & Page Objects ====
    private WebDriver webDriver;
    private SoftAssert softAssert;

    private LoginPage loginPage;
    private HomePage homePage;
    private LeftMenu leftMenu;
    private AccountsPage accountsPage;
    private AccountDetailsPage accountDetails;
    private TransferDetailsPage transferDetailsPage;
    private TransferConfirmationPage transferConfirmationPage;
    private OTPEntryPage otpEntryPage;

    // ==== Test Data ====
    private int beforeAvailableBalance;
    private String OTPCode;
    private int sourceAccountId;
}
