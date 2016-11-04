package com.mycompany.client;

// SmartGWT - Mobile
import com.smartgwt.mobile.client.widgets.Panel;
import com.smartgwt.mobile.client.widgets.ScrollablePanel;
import com.smartgwt.mobile.client.widgets.events.ClickHandler;
import com.smartgwt.mobile.client.widgets.toolbar.ToolStrip;
import com.smartgwt.mobile.client.widgets.toolbar.ToolStripButton;

import java.util.List;


public class SportsPage {

    final static String ID = "sports-page";
    final Panel container = new ScrollablePanel("Sports");    


    public SportsPage(String color, List<String> sports, ClickHandler clickHandler) {

        container.setID(ID);

        sports.forEach( sport -> {

            // Create a colored sports button
            ToolStripButton button = new ToolStripButton(sport);
            button.setInheritTint(true);
            button.addClickHandler(clickHandler); 
            
            // Create a tool bar; Add the sports button
            ToolStrip toolbar = new ToolStrip();
            toolbar.setTintColor(color);
            toolbar.addMember(button);
            
            // Add the toolbar to the container
            container.addMember(toolbar);
        });
        
    }

    public Panel asPanel() {
        return container;
    }

}

