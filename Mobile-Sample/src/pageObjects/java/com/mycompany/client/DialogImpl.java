package com.mycompany.client;

// Selenide
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

// Static methods
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.appears;
import static com.codeborne.selenide.Condition.disappears;

public class DialogImpl implements Dialog {

    private static final String DIALOG = "div.windowBackground > div.windowHeader";
    private static final String TITLE = "div.windowHeaderLabel";
    private static final String YES = "div.dialogButtonsContainer > div.actionButton:nth-Child(1)";
    private static final String NO  = "div.dialogButtonsContainer > div.actionButton:nth-Child(2)";
    private static final String OUTSIDE = "div.modalMask";
    private static final int WAIT = 5000;


    public DialogImpl() {
        $(DIALOG).waitUntil(appears, WAIT);
    }

    public String getTitle() {
        SelenideElement element = $(TITLE);
        return (element != null && element.isDisplayed()) ? element.text() :"";
    }

    public void selectYes() {
        SelenideElement element = $(YES);

        if(element.isDisplayed()) {
            element.click();
            $(DIALOG).waitUntil(disappears, WAIT);
        }

    }

    public void selectNo() {
        SelenideElement element = $(NO);

        if(element.isDisplayed()) {
            element.click();
            $(DIALOG).waitUntil(disappears, WAIT);
        }
    }

    public void selectOutside() {
        SelenideElement element = $(OUTSIDE);
        if(element.isDisplayed()) {
            element.click();
            $(DIALOG).waitUntil(disappears, WAIT);
        }

    }

    public boolean isDisplayed() {
        return $(DIALOG).isDisplayed();
    }

}