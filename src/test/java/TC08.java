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

public class TC08 {

    WebDriver webDriver;
    SoftAssert softAssert;
    LoginPage loginPage;
    HomePage homePage;
    LeftMenu leftMenu;
    AccountDetails accountDetails;

    @Test
    public void VerifyErrorMessageIsDisplayedWhenTransferringWithContentOver100Chars() {

        // Login
        loginPage.Login(Constants.USERNAME, Constants.PASSWORD);

        // Generate random string > 100 characters
        String longContent = RandomStringUtils.randomAlphabetic(101);

        // Get available balance before transfer
        leftMenu.openAccountDetailForm();
        accountDetails.openAccountDetails(100001403);
        int beforeAvailableBalance = accountDetails.getAvailableBalance();

        // Open transfer form and enter transfer details with long content
        leftMenu.openTransferForm();

        homePage.enterTranferDetails(
                100001403,   // Source account
                100001399,   // Destination account
                12000,       // Amount
                longContent  // Content over 100 characters
        );

        // Attempt to open transaction confirmation
        homePage.openTransactionConfirmationForm();

        // Verify popup error is displayed
        softAssert.assertTrue(homePage.isPopupInputContent100CharacterDisplayed(),
                "Popup thông báo nội dung vượt quá 100 ký tự không hiển thị");

        // Verify popup error text
        softAssert.assertEquals(homePage.getpopupInputContent100CharacterText(),
                "size must be between 0 and 100",
                "Nội dung thông báo không đúng");

        // Verify available balance is unchanged
        leftMenu.openAccountDetailForm();
        accountDetails.openAccountDetails(100001403);
        int afterAvailableBalance = accountDetails.getAvailableBalance();

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

        // Initialize SoftAssert
        softAssert = new SoftAssert();

        // Initialize Page Objects
        loginPage = new LoginPage(webDriver);
        homePage = new HomePage(webDriver);
        leftMenu = new LeftMenu(webDriver);
        accountDetails = new AccountDetails(webDriver);

        // Open EBANKING_URL
        webDriver.get(Constants.EBANKING_URL);
    }

    @AfterMethod
    public void tearDown() {
        webDriver.quit();
    }
}
