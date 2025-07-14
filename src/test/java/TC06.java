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

public class TC06 {

    @Test(description = "Verify error message is displayed when the source account " +
            "has insufficient funds / transfer amount exceeds available balance")
    public void VerifyErrorMessageIsDisplayedWhenTheSourceAccountHasInsufficientFunds() {

        loginPage.Login(Constants.USERNAME, Constants.PASSWORD);

        leftMenu.openAccountDetailForm();
        accountDetails.openAccountDetails(100001403);
        beforeAvailableBalance = accountDetails.getAvailableBalance();

        leftMenu.openTransferForm();

        transferDetailsForm.enterTransferDetails(100001403,
                100001399,
                beforeAvailableBalance + 1.0,
                "Huong chuyen khoan 12000 dong");

        softAssert.assertEquals(beforeAvailableBalance, homePage.getAvailableBalance(), "Số dư khả dụng không đúng");
        transferDetailsForm.openTransferConfirmationForm();

        softAssert.assertTrue(homePage.isPopupErrorDisplayed(), "Popup thông báo số tiền vượt mức không hiển thị");

        leftMenu.openAccountDetailForm();
        accountDetails.openAccountDetails(100001403);
        afterAvailableBalance = accountDetails.getAvailableBalance();

        softAssert.assertEquals(beforeAvailableBalance,
                afterAvailableBalance,
                "Số dư khả dụng không đúng sau khi chuyển số tiền vượt mức");
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
    double beforeAvailableBalance;
    double afterAvailableBalance;
}
