package com.mycompany.client;

// GWT - Core
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

// SmartGWT - Mobile
import com.smartgwt.mobile.client.widgets.Canvas;


public class Sample implements EntryPoint {

    interface Presenter {
        Canvas getView();    
    }

    public void onModuleLoad() {

        SamplePresenter.Model model = new SampleModel();
        SamplePresenter.View view = new SampleView(model.getColors(), model.getSports());
        Presenter presenter = new SamplePresenter(view, model);

        RootLayoutPanel.get().add(presenter.getView());
    }

   
}

