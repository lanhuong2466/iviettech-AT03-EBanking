package tests;

import org.apache.commons.lang3.RandomStringUtils;
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

public class TC09 {


    @Test(description = "Verify error message is displayed when entering incorrect OTP during fund transfer")
    public void VerifyErrorMessageIsDisplayedWhenEnteringIncorrectOTP() {

        // Login
        loginPage.Login(Constants.USERNAME, Constants.PASSWORD);

        leftMenu.openAccountForm();
        sourceAccountId = 100001403;
        accountsPage.openAccountDetailsForm(sourceAccountId);
        beforeAvailableBalance = accountDetails.getAvailableBalance();

        // Open transfer form and enter transfer details
        leftMenu.openTransferForm();
        transferDetailsPage.enterTransferDetails(
                sourceAccountId,   // Source account
                100001399,   // Destination account
                12000,       // Amount
                "Test chuyen khoan voi OTP sai"
        );

        // Open transaction confirmation and OTP entry form
        transferDetailsPage.openTransferConfirmationForm();
        transferConfirmationPage.openOTPEntryForm();

        // Generate random invalid OTP (10 alphanumeric characters)
        OTPCode = RandomStringUtils.randomAlphanumeric(10);

        // Enter invalid OTP
        otpEntryPage.enterOTPCode(OTPCode);

        // Click transfer button WITHOUT waiting for success popup
        otpEntryPage.clickTransferButton();

        // Verify error popup displayed for invalid OTP
        softAssert.assertTrue(homePage.isPopupErrorDisplayed(),
                "Khong hien popup OTP khong dung");

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
        accountsPage = new AccountsPage(webDriver);
        accountDetails = new AccountDetailsPage(webDriver);
        transferDetailsPage = new TransferDetailsPage(webDriver);
        transferConfirmationPage = new TransferConfirmationPage(webDriver);
        otpEntryPage = new OTPEntryPage(webDriver);
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
    OTPEntryPage otpEntryPage;
    int beforeAvailableBalance;
    int afterAvailableBalance;
    String OTPCode;
    int sourceAccountId;
}
