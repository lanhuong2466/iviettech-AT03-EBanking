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

public class TC03 {
    @Test(description = "Verify error message is displayed when source account is not selected")
    public void VerifyErrorWhenNoSourceAccountIsSelected() {
        loginPage.Login(Constants.USERNAME, Constants.PASSWORD);

        leftMenu.openTransferForm();

        transferDetailsPage.enterRecipientAccount(100001399);
        transferDetailsPage.enterAmount(12000);
        transferDetailsPage.enterPaymentContent("Huong chuyen khoan 12000 dong");
        transferDetailsPage.openTransferConfirmationForm();

        softAssert.assertTrue(homePage.isPopupSelectAnAccountDisplayed(),
                "Popup Mời chọn tài khoản không hiển thị");

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
//        accountsForm = new AccountsForm(webDriver);
        transferDetailsPage = new TransferDetailsPage(webDriver);
        accountDetails = new AccountDetailsPage(webDriver);
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

}
