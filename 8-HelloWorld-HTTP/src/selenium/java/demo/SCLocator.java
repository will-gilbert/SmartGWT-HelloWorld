package demo;

// Selenium SDK
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;

// Java Collections
import java.util.List;

public class SCLocator {

    public static By bySCLocator(final String locator) {

      final String id = locator
          .replaceAll("'", "")
          .replaceAll("\"", "");

      return new By() {

        public List<WebElement> findElements(SearchContext ignored) {
            throw new UnsupportedOperationException("findElements");
        }
      
        public WebElement findElement(SearchContext context) {

          if ((context instanceof JavascriptExecutor) == false) {
                throw new NoSuchElementException("context must implement JavascriptExecutor");
          }
          
          JavascriptExecutor js = (JavascriptExecutor) context;
          
          return (WebElement) js.executeScript(
              "var o = window[arguments[0]]; " +
              "if (!o) { return null; } " +
              "var scLocator = '//' + o.getClassName() + '[ID=\"' + arguments[1] + '\"]'; " +
              "return window.isc.AutoTest.getElement(scLocator)", id, locator);
        }
      };
    }
}
