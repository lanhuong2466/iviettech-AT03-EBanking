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

public class TC08 {

    @Test
    public void VerifyErrorMessageIsDisplayedWhenTransferringWithContentOver100Chars() {

        // Login
        loginPage.Login(Constants.USERNAME, Constants.PASSWORD);

        // Generate random string > 100 characters
        String longContent = RandomStringUtils.randomAlphabetic(101);

        // Get available balance before transfer
        leftMenu.openAccountDetailForm();
        accountDetails.openAccountDetails(100001403);
        beforeAvailableBalance = accountDetails.getAvailableBalance();

        // Open transfer form and enter transfer details with long content
        leftMenu.openTransferForm();

        transferDetailsForm.enterTransferDetails(
                100001403,   // Source account
                100001399,   // Destination account
                12000,       // Amount
                longContent  // Content over 100 characters
        );

        // Attempt to open transaction confirmation
        transferDetailsForm.openTransferConfirmationForm();

        // Verify popup error is displayed
        softAssert.assertTrue(homePage.isPopupErrorDisplayed(),
                "Popup thông báo nội dung vượt quá 100 ký tự không hiển thị");

        // Verify popup error text
        softAssert.assertEquals(homePage.getpopupErrorText(),
                "size must be between 0 and 100",
                "Nội dung thông báo không đúng");

        // Verify available balance is unchanged
        leftMenu.openAccountDetailForm();
        accountDetails.openAccountDetails(100001403);
        afterAvailableBalance = accountDetails.getAvailableBalance();

        softAssert.assertEquals(beforeAvailableBalance,
                afterAvailableBalance,
                "Số dư khả dụng bị thay đổi khi nhập nội dung > 100 ký tự");

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
