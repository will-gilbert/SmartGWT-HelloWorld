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


// Selenium - Browser Drivers
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.chrome.ChromeDriver;

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



// SmartClient/SmartGWT 'ID' locator
import static demo.SCLocator.bySCLocator;

public class SCLocatorTest {

    // Used by Safari when loading a page
    static final int PAUSE = 100;

    // The Safari driver v2.45.0; v2.46.0 did not work. Now using 2.48.0
    static WebDriver driver;
    static String browserType;
    static String urlToTest;

    /*
    ** Safari & FireFox have browser extensions which need to be installed.
    **
    ** Chrome needs the 'chromdriver' installed on you system. If placed
    **   on your PATH it will be found; If not, you need to create a
    **   System property "webdriver.chrome.driver" with the path to
    **   the 'chromedriver' executable.
    **
    **  NB: All browsers should be in their default locations 
    **    i.e. "/Applications" on Mac OSX
    */

    @BeforeClass
    public static void instanceWebDriver() {

        // Get the test URL; 
        urlToTest = System.getProperty("selenium.test.url");
        assertNotNull(urlToTest);

        // Get the test browser and create the correct WebDriver
        browserType = System.getProperty("selenium.test.browser");

        if(browserType == null)
            browserType = BrowserType.SAFARI;

        if(browserType.equals(BrowserType.SAFARI))
            driver = new SafariDriver();
        else if(browserType.equals(BrowserType.CHROME))
            driver = new ChromeDriver();
        else if(browserType.equals(BrowserType.FIREFOX))
            driver = new FirefoxDriver();

        assertNotNull(driver);

        if( browserType.equals(BrowserType.SAFARI) == false)
            driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }


    @Before
    public void launchWebBrowser() throws Exception {
        // Connect to the SmartGWT application
        driver.get(urlToTest);

        // No pageLoadTimeout on Safari; Use sleep instead
        if( browserType.equals(BrowserType.SAFARI))
            Thread.sleep(PAUSE);
    }

    @AfterClass
    public static void shutdown() {
        driver.quit();
    }

    // T E S T S  ==========================================================================================

    @Test
    public void locationById() {
        WebElement scButton = driver.findElement(bySCLocator("HelloWorld.button"));
        assertNotNull(scButton);
        assertEquals(scButton.getText(), "Hello?");
    }

    @Test
    public void locationByCSS() {
        WebElement cssButton = driver.findElement(By.cssSelector("td[class='button HelloWorld.button']"));
        assertNotNull(cssButton);
        assertEquals(cssButton.getText(), "Hello?");
    }

    @Test
    public void locationByXPath() {
        WebElement xpathButton = driver.findElement(By.xpath("//td[@class='button HelloWorld.button']"));
        assertNotNull(xpathButton);
        assertEquals(xpathButton.getText(), "Hello?");
    }

    @Test
    public void locationByClassContains() {
        WebElement containsButton = driver.findElement(By.xpath("//td[contains(@class,'HelloWorld.button')]"));
        assertNotNull(containsButton);
        assertEquals(containsButton.getText(), "Hello?");
    }

    @Test
    public void locationByText() {
        WebElement textButton = driver.findElement(By.xpath("//div[./text()='Hello?']"));
        assertNotNull(textButton);
        assertEquals(textButton.getText(), "Hello?");
    }

    @Test
    public void explictWait() throws Exception {

        WebElement helloWorldButton = explictWait(driver, bySCLocator("HelloWorld.button"));
        assertNotNull(helloWorldButton);

        // Click the button
        helloWorldButton.click();

        // Find the Dialog Box; Try every 200 ms with a 10 second timeout 
        WebElement dialogBox = explictWait(driver, By.xpath("//div[@role='alertdialog']"));
        assertNotNull(dialogBox);

        WebElement contents = dialogBox.findElement(By.xpath(".//table[@role='presentation']//td[1]"));
        assertNotNull(contents);
        assertTrue(contents.getText().contains("Hello, World!"));

        // Find the 'OK' button within the Dialog Box
        WebElement okButton = dialogBox.findElement(By.xpath(".//div[./text()='OK']"));
        assertNotNull(okButton);

        // Click the button the 'OK' button to dismiss
        okButton.click();

    }

    @Test
    public void fluentWait() throws Exception {

        WebElement helloWorldButton = fluentWait(driver, bySCLocator("HelloWorld.button"));
        assertNotNull(helloWorldButton);

        // Click the button
        helloWorldButton.click();

        // Find the Dialog Box; Try every 200 ms with a 10 second timeout 
        WebElement dialogBox = fluentWait(driver, By.xpath("//div[@role='alertdialog']"));
        assertNotNull(dialogBox);

        WebElement contents = fluentWait(dialogBox, By.xpath(".//table[@role='presentation']//td[1]"));
        assertNotNull(contents);
        assertTrue(contents.getText().contains("Hello, World!"));

        // Find the 'OK' button within the Dialog Box
        WebElement okButton = fluentWait(dialogBox, By.xpath(".//div[./text()='OK']"));
        assertNotNull(okButton);

        // Click the button
        okButton.click();

    }

    // e x p l i c t W a i t  ======================================================================

    private static WebElement explictWait(WebDriver driver, By selector) {
        return explictWait(driver, selector, 10, 200); 
    }

    private static WebElement explictWait(WebDriver driver, By selector, int timeOutSeconds, int retryMilliSeconds) {
        WebDriverWait webdriverWait = new WebDriverWait(driver, timeOutSeconds, retryMilliSeconds); 
        return webdriverWait.until(ExpectedConditions.presenceOfElementLocated(selector));
    }


    // f l u e n t W a i t  ========================================================================

    private static WebElement fluentWait(WebDriver driver, final By selector) {
        return fluentWait(driver, selector, 10, 200);
    }

    private static WebElement fluentWait(WebElement webElement, final By selector) {
        return fluentWait(webElement, selector, 10, 200);
    }

    private static WebElement fluentWait(WebDriver driver, final By selector, int timeOutSeconds, int retryMilliSeconds) {

        Wait<WebDriver> wait =  new FluentWait<WebDriver>(driver)
            .withTimeout(timeOutSeconds, TimeUnit.SECONDS)
            .pollingEvery(retryMilliSeconds, TimeUnit.MILLISECONDS)
            .ignoring(NoSuchElementException.class);

        return wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(selector);
            }
        });
        
    }

    private static WebElement fluentWait(WebElement webElement, final By selector, int timeOutSeconds, int retryMilliSeconds) {

        Wait<WebElement> wait =  new FluentWait<WebElement>(webElement)
            .withTimeout(timeOutSeconds, TimeUnit.SECONDS)
            .pollingEvery(retryMilliSeconds, TimeUnit.MILLISECONDS)
            .ignoring(NoSuchElementException.class);

        return wait.until(new Function<WebElement, WebElement>() {
            public WebElement apply(WebElement webElement) {
                return webElement.findElement(selector);
            }
        });
        
    }

}

