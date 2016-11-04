package com.mycompany.client;

// SmartGWT - Mobile
import com.smartgwt.mobile.client.widgets.Button;
import com.smartgwt.mobile.client.widgets.Panel;
import com.smartgwt.mobile.client.widgets.ScrollablePanel;
import com.smartgwt.mobile.client.widgets.events.ClickHandler;

import java.util.List;

public class HomePage {

    final static String ID = "home-page";
    final Panel container = new ScrollablePanel("Home");    

    public HomePage(List<String> colors, ClickHandler cickHandler) {

        container.setID(ID);
    
        colors.forEach ( color -> {

            Button button = new Button(color);
            button.setTintColor(color);
            button.addClickHandler(cickHandler);

            container.addMember(button);
        });
    }

    public Panel asPanel() {
        return container;
    }

}

