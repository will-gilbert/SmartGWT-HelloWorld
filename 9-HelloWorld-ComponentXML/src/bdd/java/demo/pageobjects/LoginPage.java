package demo.pageobjects;

// Selenide & Selenium
import com.codeborne.selenide.SelenideElement;

// Static methods
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.appears;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.text;
import static demo.pageobjects.Constants.WAIT;
import static demo.pageobjects.Constants.SLEEP;



public class LoginPage  {

    private static final String PAGE_READY_CSS = "table[role='presentation']";
    private static final String HELLO_WORLD_BUTTON_CSS = "td[class='button HelloWorld.button'";
    private static final String NO_NAME_BUTTON_CSS = "td[class='button HelloWorld.noname.button']";

    private static final String ALERT_DIALOG_CSS = "div[role='alertdialog']";

    private SelenideElement pageReady;

    public LoginPage() {
        pageReady = $(PAGE_READY_CSS).waitUntil(appears, WAIT);
    }

    public boolean isVisible() {
        return pageReady.isDisplayed();
    }

    public void clickHelloButton() {
    	SelenideElement element =  $(HELLO_WORLD_BUTTON_CSS).should(exist);
    	element.shouldBe(visible, enabled);

    	element.click();

    	$(ALERT_DIALOG_CSS).should(appear);
    }

    public void clickNoNameButton() {
    	SelenideElement element =  $(NO_NAME_BUTTON_CSS).should(exist);
    	element.shouldBe(visible, enabled);

    	element.click();
    }

    public boolean isAlertDialogVisible() {
    	return $(ALERT_DIALOG_CSS).isDisplayed();
    }


	public void dismissDialogWithOk() {
		SelenideElement dialogElement = $(ALERT_DIALOG_CSS);
		dialogElement.should(exist).shouldBe(visible);

		SelenideElement element = dialogElement.find(byText("OK"));
		element.should(exist).shouldBe(visible, enabled);

		element.click();

		dialogElement.should(disappear);
	}
}
