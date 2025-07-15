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

        //lấy số dư rồi trừ thuế tùm lum rồi ra số tiền chuyển
        amount = 12000.0;
        transferDetailsForm.enterTransferDetails(100001403,
                100001399, amount,
                "Huong chuyen khoan 12000 dong");

        // Kiểm tra hiển thị đúng số dư khả dụng
        softAssert.assertEquals(homePage.getAvailableBalance(),
                beforeAvailableBalance, "So du kha dung khong dung");

        sourceAccountId = transferDetailsForm.getSourceAccountId();
        transferAmount = transferDetailsForm.getTransferAmount();
        transferMessage = transferDetailsForm.getTransferMessage();
        recipientAccountId = transferDetailsForm.getRecipientAccountId();
        recipientName = transferDetailsForm.getRecipientName();

        transferDetailsForm.openTransferConfirmationForm();

        //Kiểm tra hiển thị đúng thông tin chuyển tiền
        // Tài khoản gửi, Số tiền chuyển khoản, Nội dung chuyển khoản,
        // Tài khoản nhận, Tên chủ tài khoản, Hình thức nhận mã giao dịch

//        leftMenu.openTransferForm();
//        transferDetailsForm.selectSourceAccount(100001403);
//

        softAssert.assertEquals(transferConfirmationForm.getSourceAccountId(),
                sourceAccountId, "So tai khoan gui khong dung");

        softAssert.assertEquals(transferConfirmationForm.getTransferAmount(),
                transferAmount, "So tien chuyen khoan khong dung");

        softAssert.assertEquals(transferConfirmationForm.getTransferMessage(),
                transferMessage, "Noi dung chuyen khoan khong dung");

        softAssert.assertEquals(transferConfirmationForm.getRecipientAccountId(),
                recipientAccountId, "So tai khoan nhan khong dung");

        softAssert.assertEquals(transferConfirmationForm.getRecipientName(),
                recipientName, "Ten nguoi nhan khong dung");

        softAssert.assertEquals(transferConfirmationForm.getReceiveOTPViaEmailText(),
                "Nhận qua Email", "Hinh thuc nhan ma OTP khong dung");


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

        otpEntryForm.enterOTPCode(OTPCode);

        otpEntryForm.clickTransferButton();

        softAssert.assertTrue(otpEntryForm.isTransferSuccessPopupDisplayed(),
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
        otpEntryForm = new OTPEntryForm(webDriver);
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
    OTPEntryForm otpEntryForm;
    double beforeAvailableBalance;
    double afterAvailableBalance;
    String originalWindow;
    String OTPCode;
    double amount;
    int sourceAccountId;
    double transferAmount;
    String transferMessage;
    int recipientAccountId;
    String recipientName;

// pages chia lam 2: ebanking va yopmail,
    // leftmmenu taoj thanhf 1 page rieng biet
    //khong dung if-else trong tcs
    // thread.sleep() khong nen dung trong test case, chi dung trong test case nao can thiet
    //@step, allure step

    //click nút xác nận 2,3 lần
    // refresh trang ma otp

    //c1: tạo 1 tài khoản mới luôn
    //c2: vào tài khoản ở leftmenu rồi bốc 1 tài khoản bất kỳ ra để test
    //tách thêm 1 page là entryOTPForm

    //((JavaScriptExecutor) webDriver).executeScript("return arguments[0].value"));

}

