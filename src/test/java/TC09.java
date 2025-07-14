package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.EBanking.*;
import utils.Constants;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Duration;

public class TC09 {

    WebDriver webDriver;
    SoftAssert softAssert;
    LoginPage loginPage;
    HomePage homePage;
    LeftMenu leftMenu;
    TransferDetailsForm transferDetailsForm;
    String OTPCode;

    @Test(description = "Verify error message is displayed when entering incorrect OTP during fund transfer")
    public void VerifyErrorMessageIsDisplayedWhenEnteringIncorrectOTP() {

        // Login
        loginPage.Login(Constants.USERNAME, Constants.PASSWORD);

        // Open transfer form and enter transfer details
        leftMenu.openTransferForm();
        transferDetailsForm.enterTransferDetails(
                100001403,   // Source account
                100001399,   // Destination account
                12000,       // Amount
                "Test chuyen khoan voi OTP sai"
        );

        // Open transaction confirmation and OTP entry form
        transferDetailsForm.openTransactionConfirmationForm();
        homePage.openOTPEntryForm();

        // Generate random invalid OTP (10 alphanumeric characters)
        OTPCode = RandomStringUtils.randomAlphanumeric(10);

        // Enter invalid OTP
        homePage.enterOTPCode(OTPCode);

        // Click transfer button WITHOUT waiting for success popup
        homePage.clickTransferButtonWithoutWaitingSuccessPopup();

        // Verify error popup displayed for invalid OTP
        softAssert.assertTrue(homePage.isPopupFailOTPDisplayed(),
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
        leftMenu = new LeftMenu(webDriver);
        transferDetailsForm = new TransferDetailsForm(webDriver);

        webDriver.get(Constants.EBANKING_URL);
    }

    @AfterMethod
    public void tearDown() {
        webDriver.quit();
    }
}
