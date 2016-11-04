package com.mycompany.client;

// Selenide
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

// Java Collections
import java.util.Map;
import java.util.HashMap;

// Static methods
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.appears;


public class HomePageImpl implements HomePage {

    private static final String PAGE = "div.sc-scrollable#home-page";
    private static final String BUTTONS = "div.actionButton";
    private static final int WAIT = 5000;

    private final Map<String,SelenideElement> buttons = new HashMap<>();

    public HomePageImpl() {

        $(PAGE).waitUntil(appears, WAIT);

        ElementsCollection elements = $$(BUTTONS);

        for (int i = 0; i < elements.size(); i++) {
            SelenideElement button = elements.get(i);
            if(button != null && button.isDisplayed())
                buttons.put(button.text(), button);
        }

    }

    public SportsPage selectColor(String color) {

        SportsPage sportsPage = null;
        SelenideElement element = getButtonElement(color);

        if(element != null) {
            element.click(); 
            sportsPage = new SportsPageImpl();   
        }

        return sportsPage;
    }

    public String getBackgroundColor(String color) {
        SelenideElement element = getButtonElement(color);
        
        return ( element != null ) ? getValueForStyle(element, "background-color") : "";
    }

    private SelenideElement getButtonElement(String name) {

        SelenideElement element = buttons.get(name);
        
        if(element != null && element.isDisplayed() == false)
            element = null;

        return element;
    }

    private String getValueForStyle(final SelenideElement element, final String property) {

        String style = element.attr("style");
        if(style == null || style.trim().length() == 0)
            return "";

        int indexOfProperty = style.indexOf(property);
        if(indexOfProperty == -1)
            return "";

        int indexOfSemicolon = style.indexOf(";", indexOfProperty);
        int indexOfValue = indexOfProperty + property.length() + 2;

        return style.substring(indexOfValue, indexOfSemicolon);

    }

    public boolean isDisplayed() {
        return $(PAGE).isDisplayed();
    }

}