import org.apache.commons.lang3.RandomUtils;
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

        loginAdminPage.loginAdminAccount(Constants.ADMIN_USERNAME, Constants.ADMIN_PASSWORD);

        adminHomePage.openDepositMoneyForm();

        adminHomePage.depositMoneyIntoABankAccount(100001403,
                1000000, "Nap tien vao tai khoan 100001399");


        webDriver.get(Constants.EBANKING_URL);
        loginPage.Login(Constants.USERNAME, Constants.PASSWORD);


        leftMenu.openAccountForm();

        sourceAccountId = 100001403;
        accountsPage.openAccountDetailsForm(sourceAccountId);
        beforeAvailableBalance = accountDetailsPage.getAvailableBalance();

        leftMenu.openTransferForm();

        amount = RandomUtils.nextInt(0, beforeAvailableBalance - 1100);
        transferDetailsPage.enterTransferDetails(sourceAccountId,
                100001399, amount,
                "Huong chuyen tien");


        softAssert.assertEquals(homePage.getAvailableBalance(),
                beforeAvailableBalance, "So du kha dung khong dung");

//        sourceAccountId = transferDetailsForm.getSourceAccountId();
        transferAmount = transferDetailsPage.getTransferAmount();
        transferMessage = transferDetailsPage.getTransferMessage();
        recipientAccountId = transferDetailsPage.getRecipientAccountId();
        recipientName = transferDetailsPage.getRecipientName();

        transferDetailsPage.openTransferConfirmationForm();

        //Kiểm tra hiển thị đúng thông tin chuyển tiền

        softAssert.assertEquals(transferConfirmationPage.getSourceAccountId(),
                sourceAccountId, "So tai khoan gui khong dung");

        softAssert.assertTrue(transferConfirmationPage.getTransferAmount() -
                transferAmount < epsilon, "So tien chuyen khoan khong dung");

        softAssert.assertEquals(transferConfirmationPage.getTransferMessage(),
                transferMessage, "Noi dung chuyen khoan khong dung");

        softAssert.assertEquals(transferConfirmationPage.getRecipientAccountId(),
                recipientAccountId, "So tai khoan nhan khong dung");

        softAssert.assertEquals(transferConfirmationPage.getRecipientName(),
                recipientName, "Ten nguoi nhan khong dung");

        softAssert.assertEquals(transferConfirmationPage.getReceiveOTPViaEmailText(),
                "Nhận qua Email", "Hinh thuc nhan ma OTP khong dung");


        transferConfirmationPage.openOTPEntryForm();


        originalWindow = webDriver.getWindowHandle();
        webDriver.switchTo().newWindow(org.openqa.selenium.WindowType.TAB);
        WindowSwitcher.switchToNewWindow(webDriver, originalWindow);

        webDriver.get(Constants.YOPMAIL_URL);
        homeYopMailPage.loginToYopMail(Constants.EMAIL);


        emailPage.clickRefreshButton();

        emailPage.openFirstMail();
        OTPCode = emailPage.getOTPCode();


        webDriver.close();
        webDriver.switchTo().window(originalWindow);

        otpEntryPage.submitOTPCode(OTPCode);

        softAssert.assertTrue(otpEntryPage.isTransferSuccessPopupDisplayed(),
                "Khong hien popup chuyen khoan thanh cong");

        homePage.closeTheNotificationPopup();

        leftMenu.openAccountForm();
        accountsPage.openAccountDetailsForm(sourceAccountId);
        afterAvailableBalance = accountDetailsPage.getAvailableBalance();


        softAssert.assertTrue(afterAvailableBalance - (beforeAvailableBalance - amount - 1100) < epsilon,
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
        loginAdminPage = new LoginAdminPage(webDriver);
        adminHomePage = new AdminHomePage(webDriver);
        loginPage = new LoginPage(webDriver);
        homePage = new HomePage(webDriver);
        homeYopMailPage = new HomeYopMailPage(webDriver);
        emailPage = new EmailPage(webDriver);
        leftMenu = new LeftMenu(webDriver);
        accountsPage = new AccountsPage(webDriver);
        accountDetailsPage = new AccountDetailsPage(webDriver);
        transferDetailsPage = new TransferDetailsPage(webDriver);
        transferConfirmationPage = new TransferConfirmationPage(webDriver);
        otpEntryPage = new OTPEntryPage(webDriver);
        webDriver.get(Constants.ADMIN_EBANKING_URL);

    }

    @AfterMethod
    public void tearDown() {
        webDriver.quit();
    }

    WebDriver webDriver;
    SoftAssert softAssert;
    LoginAdminPage loginAdminPage;
    AdminHomePage adminHomePage;
    LoginPage loginPage;
    HomePage homePage;
    HomeYopMailPage homeYopMailPage;
    EmailPage emailPage;
    LeftMenu leftMenu;
    AccountsPage accountsPage;
    AccountDetailsPage accountDetailsPage;
    TransferDetailsPage transferDetailsPage;
    TransferConfirmationPage transferConfirmationPage;
    OTPEntryPage otpEntryPage;
    int beforeAvailableBalance;
    int afterAvailableBalance;
    String originalWindow;
    String OTPCode;
    int amount;
    int sourceAccountId;
    int transferAmount;
    String transferMessage;
    int recipientAccountId;
    String recipientName;
    double epsilon = 0.0001;


    //((JavaScriptExecutor) webDriver).executeScript("return arguments[0].value"));

}
