package Tests;

import Factory.DriverFactory;
import Listeners.IInvokedMethodListeners;
import Listeners.ITestResultListeners;
import PageTesting.P01_LoginPage;
import PageTesting.P02_HomePage;
import Utilities.DataUtility;
import Utilities.LogsUtility;
import Utilities.Utility;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static Factory.DriverFactory.GetThreadDriver;
import static Factory.DriverFactory.SetupThreadDriver;

@Listeners({IInvokedMethodListeners.class, ITestResultListeners.class})
public class TC03_CardTest {

    private final String Browser_FileName = "environment";
    private final String Browser_Key = "Browser";
    private final String Base_URLKey = "Base_URL";
    private final String DataJsonFileName = "ValidLoginTestData";
    private final String usernamefield = "username";
    private final String passwordfield = "password";
    private final String homepage = "Home_URL";
    private final String cardpage = "Card_URL";
    /* Environment variables from properties */
    private final String BROWSER = DataUtility.GetPropertiesDataFromFile(Browser_FileName, Browser_Key);
    private final String URL = DataUtility.GetPropertiesDataFromFile(Browser_FileName, Base_URLKey);
    private final String HOME_URL = DataUtility.GetPropertiesDataFromFile(Browser_FileName, homepage);
    private final String Card_URL = DataUtility.GetPropertiesDataFromFile(Browser_FileName, cardpage);
    /* This Attributes for sending the username and password field using json file */
    private final String USERNAME = DataUtility.GetJsonDataFromFile(DataJsonFileName, usernamefield);
    private final String PASSWORD = DataUtility.GetJsonDataFromFile(DataJsonFileName, passwordfield);


    private final SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void SetUp() {

        try {

            SetupThreadDriver(BROWSER);

            LogsUtility.LoggerInfo("Edge is Opened Correctly");
            GetThreadDriver().get(URL);
            LogsUtility.LoggerInfo("Page is Redirected to the URL");

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    @Test(priority = 1)
    public void PricesCalculation() {

        String TotalPriceHomepage = new P01_LoginPage(GetThreadDriver())
                .EnterUserName(USERNAME)
                .EnterPassword(PASSWORD)
                .ClickOnLogin()
                .AddRandomProducttoCard(5, 3)
                .GetThePricesOfSelectedProductInHomePag();

        new P02_HomePage(GetThreadDriver())
                .ClickOnCardIcon()
                .ClickOnContinueShopping();
        softAssert.assertTrue(Utility.VerifyCurrentURLToExpected(GetThreadDriver(), HOME_URL));
        softAssert.assertAll();
    }

    @Test(priority = 2)
    public void ClickOnContinueButton() {

        String TotalPriceHomepage = new P01_LoginPage(GetThreadDriver())
                .EnterUserName(USERNAME)
                .EnterPassword(PASSWORD)
                .ClickOnLogin()
                .AddRandomProducttoCard(5, 3)
                .GetThePricesOfSelectedProductInHomePag();

        new P02_HomePage(GetThreadDriver())
                .ClickOnCardIcon()
                .ClickOnContinueShopping();
        softAssert.assertTrue(Utility.VerifyCurrentURLToExpected(GetThreadDriver(), HOME_URL));
        softAssert.assertAll();
    }

    @AfterMethod
    public void Quit() {

        DriverFactory.QuitThreadDriver();

    }
}
