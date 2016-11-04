package com.mycompany.client;

// SmartGWT - Mobile
import com.smartgwt.mobile.client.widgets.BaseButton;
import com.smartgwt.mobile.client.widgets.Canvas;
import com.smartgwt.mobile.client.widgets.Dialog;
import com.smartgwt.mobile.client.widgets.events.ClickEvent;
import com.smartgwt.mobile.client.widgets.events.ClickHandler;
import com.smartgwt.mobile.client.widgets.layout.NavStack;

import java.util.List;
import java.util.Arrays;


public class SampleModel implements SamplePresenter.Model {

    private final String[] colors = new String[] { 
        "blue", "red", "yellow", "green", "gray", "white", 
        "black", "pink", "brown" 
    };

    private final String[] sports = new String[] { 
        "Baseball", "Basketball", "Football", "Hockey", "Volleyball" 
    };


    public List<String> getColors() {
        return Arrays.asList(colors);
    }
    public List<String> getSports() {
        return Arrays.asList(sports);        
    }

}

