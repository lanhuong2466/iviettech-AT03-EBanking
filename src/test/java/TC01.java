import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.EBanking.AccountDetails;
import pages.EBanking.HomePage;
import pages.EBanking.LeftMenu;
import pages.EBanking.LoginPage;
import pages.Yopmail.EmailPage;
import pages.Yopmail.HomeYopMailPage;
import utils.Constants;
import utils.WindowSwitcher;

import java.time.Duration;
import java.util.ArrayList;

public class TC01 {

    @Test
    public void VerifySuccessfulFundTransferBetweenTwoAccountsWithinTheSameBank() throws InterruptedException {

        loginPage.Login(Constants.USERNAME, Constants.PASSWORD);

        leftMenu.openTransferForm();

        homePage.enterTranferDetails(100001403,100001399, 12000,"Huong chuyen khoan 12000 dong");

        String originalWindow = webDriver.getWindowHandle();
//        webDriver.switchTo().newWindow(org.openqa.selenium.WindowType.TAB);
//        WindowSwitcher.switchToNewWindow(webDriver, originalWindow);
//
//        webDriver.get(Constants.EBANKING_URL);
//
//        leftMenu.openAccountDetailForm();
//        accountDetails.openAccountDetails(100001403);
//
//        int availableBalance = accountDetails.getAvailableBalance();
//
//        webDriver.close();
//        webDriver.switchTo().window(originalWindow);
//
//        softAssert.assertEquals(homePage.getAvailableBalance(),
//                availableBalance, "So du kha dung khong dung");

        homePage.openTransactionConfirmationForm();

        homePage.openOTPEntryForm();

        webDriver.switchTo().newWindow(org.openqa.selenium.WindowType.TAB);
        WindowSwitcher.switchToNewWindow(webDriver, originalWindow);

        webDriver.get(Constants.YOPMAIL_URL);

//        String yopMailWindow = new ArrayList<>(webDriver.getWindowHandles()).get(1);
//        webDriver.switchTo().window(yopMailWindow);
        homeYopMailPage.loginToYopMail(Constants.EMAIL);

        emailPage.clickRefreshButton();

        emailPage.openFirstMail();

        emailPage.getOTPCode();
        String OTPCode = emailPage.getOTPCode();

        webDriver.close();
        webDriver.switchTo().window(originalWindow);

        homePage.enterOTPCode(OTPCode);

        homePage.clickTransferButton();
//        softAssert.assertTrue(homePage.isTransferSuccessPopupDisplayed(), "Khong hien popup chuyen khoan thanh cong");


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

// pages chia lam 2: ebanking va yopmail,
    // leftmmenu taoj thanhf 1 page rieng biet
    //khong dung if-else trong tcs
    // thread.sleep() khong nen dung trong test case, chi dung trong test case nao can thiet
    //@step, allure step
}

