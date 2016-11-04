package com.mycompany.client;

// SmartGWT - Mobile
import com.smartgwt.mobile.client.widgets.BaseButton;
import com.smartgwt.mobile.client.widgets.Canvas;
import com.smartgwt.mobile.client.widgets.Dialog;
import com.smartgwt.mobile.client.widgets.events.ClickEvent;
import com.smartgwt.mobile.client.widgets.events.ClickHandler;
import com.smartgwt.mobile.client.widgets.layout.NavStack;

import java.util.List;

public class SampleView implements SamplePresenter.View {

    // Handles application pages history and transitions
    private final NavStack navigationStack;

    private final List<String> sports;

    private final ClickHandler colorClickHandler;
    private final ClickHandler sportsClickHandler;

    public SampleView(List<String> colors, List<String> sports) {
        this.sports = sports;

        colorClickHandler = createColorClickHandler();
        sportsClickHandler = createSportsClickHandler();

        HomePage homePage = new HomePage(colors, colorClickHandler);
        navigationStack = new NavStack(homePage.asPanel());
    }

    public Canvas asCanvas() {
        return navigationStack;
    }

    private ClickHandler createColorClickHandler() {

        return event -> {
            BaseButton button = (BaseButton)event.getSource();
            String color = button.getTitle();
            SportsPage sportsPage = new SportsPage(color, sports, sportsClickHandler);
            navigationStack.push(sportsPage.asPanel());
        }; 
    }

    private ClickHandler createSportsClickHandler() {

        return event -> {
            BaseButton button = (BaseButton)event.getSource();
            String sport = button.getTitle();
            Dialog dialog = new Dialog("Do you like " + sport + "?");
            dialog.setButtons(Dialog.YES, Dialog.NO);
            dialog.show();
        };    


    }

}

