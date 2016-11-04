package com.mycompany.client;

// SmartGWT - Mobile
import com.smartgwt.mobile.client.widgets.Canvas;

import java.util.List;

public class SamplePresenter implements Sample.Presenter {

    interface View {
        Canvas asCanvas();    
    }

    interface Model {
        List<String> getColors();    
        List<String> getSports();    
    }

    private final View view;
    private final Model model;

    public SamplePresenter(View view, Model model) {
        this.view = view;
        this.model = model;
    }

    public Canvas getView() {
        return view.asCanvas();
    }

}

