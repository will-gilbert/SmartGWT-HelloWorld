package demo;

// Selenium - SDK
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;


// SmartClient -  WebDrivers & SClocator
import com.isomorphic.webdriver.SmartClientWebDriver;
import com.isomorphic.webdriver.SmartClientChromeDriver;
import com.isomorphic.webdriver.ByScLocator;


// Java - Util
import java.util.concurrent.TimeUnit;
import com.google.common.base.Function;

// JUnit 4.x testing
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

@Ignore
public class SCLocatorWithChromeTest {

    // Used by Safari when loading a page
    static final int PAUSE = 100;

    // The Safari driver v2.45.0; v2.46.0 did not work. Now using 2.48.0
    static SmartClientWebDriver driver;
    static String browserType;
    static String baseUrl;
    static String getUrl;

    /*
    **
    ** Chrome needs the 'chromdriver' installed on you system. If placed
    **   on your PATH it will be found; If not, you need to create a
    **   System property "webdriver.chrome.driver" with the path to
    **   the 'chromedriver' executable.
    **
    **  NB: Chorme should be in its default location
    **    i.e. "/Applications/Chrome" on Mac OSX
    */

    @BeforeClass
    public static void instanceWebDriver() {

        // Get the test URL; 
        baseUrl = System.getProperty("selenium.test.baseUrl");
        assertNotNull(baseUrl);

        getUrl = System.getProperty("selenium.test.getUrl");
        assertNotNull(getUrl);

        driver = new SmartClientChromeDriver();
        assertNotNull(driver);

    }


    @Before
    public void launchWebBrowser() throws Exception {
        // Connect to the SmartGWT application
        driver.setBaseUrl(baseUrl);
        driver.get(getUrl);
        driver.manage().window().maximize();
    }

    @AfterClass
    public static void shutdown() {
        driver.quit();
    }

    // T E S T S  ==========================================================================================


    @Test
    public void locationByScLocator() {
        By by = ByScLocator.scLocator("//Button[ID='HelloWorld.button']");
        assertTrue(driver.waitForElementClickable(by));

        // SmartClientWebDriver builtin text assertion
        driver.verifyText(by, "Hello?");

        // SmartClientWebDriver getText methods
        assertEquals("Hello?", driver.getText(by));
    }


    @Test
    public void clickButtonAndDismissAlert() throws Exception {

        By by = ByScLocator.scLocator("HelloWorld.button");
        driver.waitForElementClickable(by);

        // Click the button via SmartClientWebDriver
        driver.click(by);

        // // Find the Dialog Box; Try every 200 ms with a 10 second timeout 
        WebElement dialogBox = driver.findElement(By.xpath("//div[@role='alertdialog']"));
        assertNotNull(dialogBox);

        WebElement contents = dialogBox.findElement(By.xpath(".//table[@role='presentation']//td[1]"));
        assertNotNull(contents);
        assertTrue(contents.getText().contains("Hello, World!"));

        // // Find the 'OK' button within the Dialog Box
        WebElement okButton = dialogBox.findElement(By.xpath(".//div[./text()='OK']"));
        assertNotNull(okButton);

        // // Click the button the 'OK' button to dismiss (via WebDriver)
        okButton.click();

    }

}

