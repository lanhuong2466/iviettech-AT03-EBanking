import pages.EmailPage;
import pages.HomePage;
import pages.HomeYopMailPage;
import pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.Constains;

import java.sql.Array;
import java.time.Duration;
import java.util.ArrayList;

public class TC01 {

    @Test
    public void VerifySuccessfulFundTransferBetweenTwoAccountsWithinTheSameBank() {

        loginPage.Login(Constains.USERNAME, Constains.PASSWORD);

//        String currHandle= webDriver.getWindowHandle();


        homePage.openTransferForm();
        homePage.enterTranferDetails(100001403,100001399, 12000,"Huong chuyen khoan 12000 dong");
        homePage.openTransferForm();

        webDriver.switchTo().newWindow(org.openqa.selenium.WindowType.TAB);
        webDriver.get(Constains.YOPMAIL_URL);
        String yopMailWindow = new ArrayList<>(webDriver.getWindowHandles()).get(1);
        webDriver.switchTo().window(yopMailWindow);
        homeYopMailPage.loginToYopMail(Constains.EMAIL);

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
        webDriver.get(Constains.EBANKING_URL);

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


}

