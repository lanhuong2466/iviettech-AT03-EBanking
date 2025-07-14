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
import utils.WindowSwitcher;

import java.math.BigDecimal;
import java.time.Duration;

public class TC01 {

    @Test(description = "Verify successful transfer between two accounts within the same bank")
    public void VerifySuccessfulFundTransferBetweenTwoAccountsWithinTheSameBank() {

        loginPage.Login(Constants.USERNAME, Constants.PASSWORD);

        // Mở trang chi tiết tài khoản để lấy số dư khả dụng trước khi chuyển tiền
        leftMenu.openAccountDetailForm();
        accountDetails.openAccountDetails(100001403);
        beforeAvailableBalance = accountDetails.getAvailableBalance();

        // Mở form chuyển tiền
        leftMenu.openTransferForm();

        amount = 12000.0;
        transferDetailsForm.enterTransferDetails(100001403,
                100001399, amount,
                "Huong chuyen khoan 12000 dong");

        // Kiểm tra hiển thị đúng số dư khả dụng
        softAssert.assertEquals(homePage.getAvailableBalance(),
                beforeAvailableBalance, "So du kha dung khong dung");

        transferDetailsForm.openTransferConfirmationForm();

        // Kiểm tra hiển thị đúng thông tin chuyển tiền
//        softAssert.assertEquals(transferConfirmationForm.getSourceAccountId(),
//                transferDetailsForm.getSourceAccountId(), "So tai khoan gui khong dung");



        transferConfirmationForm.openOTPEntryForm();

        // Chuyển sang tab Yopmail để lấy mã OTP
        originalWindow = webDriver.getWindowHandle();
        webDriver.switchTo().newWindow(org.openqa.selenium.WindowType.TAB);
        WindowSwitcher.switchToNewWindow(webDriver, originalWindow);

        webDriver.get(Constants.YOPMAIL_URL);
        homeYopMailPage.loginToYopMail(Constants.EMAIL);


        emailPage.clickRefreshButton();

        emailPage.openFirstMail();
        OTPCode = emailPage.getOTPCode();

        // Quay lại tab EBanking để nhập mã OTP
        webDriver.close();
        webDriver.switchTo().window(originalWindow);

        homePage.enterOTPCode(OTPCode);

        homePage.clickTransferButton();

        softAssert.assertTrue(homePage.isTransferSuccessPopupDisplayed(),
                "Khong hien popup chuyen khoan thanh cong");

        homePage.closeTheNotificationPopup();

        leftMenu.openAccountDetailForm();
        accountDetails.openAccountDetails(100001403);
        afterAvailableBalance = accountDetails.getAvailableBalance();


        softAssert.assertEquals(afterAvailableBalance, (beforeAvailableBalance - amount - 1100),
                "So du kha dung khong dung sau khi chuyen khoan thanh cong");

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
    String originalWindow;
    String OTPCode;
    Double amount;

// pages chia lam 2: ebanking va yopmail,
    // leftmmenu taoj thanhf 1 page rieng biet
    //khong dung if-else trong tcs
    // thread.sleep() khong nen dung trong test case, chi dung trong test case nao can thiet
    //@step, allure step

    //click nút xác nận 2,3 lần
    // refresh trang ma otp
    // Tài khoản gửi, Số tiền chuyển khoản, Nội dung chuyển khoản, Tài khoản nhận, Tên chủ tài khoản, Hình thức nhận mã giao dịch

}

