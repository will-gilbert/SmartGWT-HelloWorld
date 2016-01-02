package demo.client;

import demo.client.events.HelloWorldEvents;
import demo.client.events.FetchHelloWorldEvent;
import demo.client.events.DisplayHelloWorldEvent;
import demo.client.events.DisplayServiceErrorEvent;

// GWT - Event Bus
import com.google.gwt.event.shared.EventBus;

// SmartGWT - Widgets
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;

// SmartGWT - Utility
import com.smartgwt.client.util.SC;

// Google - Dependency Injection
import com.google.inject.Inject;


public class HelloWorldPresenter  {

	//=============================================================================
	public interface View {
		HasClickHandlers getHelloClickable();
		HasClickHandlers getNoNameClickable();
		void displayResponse(String response);
		Canvas asCanvas();
	}

	//===========================================================================

	final View view;
	final EventBus eventBus;

	@Inject
	public HelloWorldPresenter(View view, EventBus eventBus) {
		this.view = view;
		this.eventBus = eventBus;

		bindView();
		bindEvents();
	}

	public Canvas getView() {
		return view.asCanvas();
	}

	private void bindView() {

		view.getHelloClickable().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new FetchHelloWorldEvent("World"));
			}
			
		});

		view.getNoNameClickable().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new FetchHelloWorldEvent(null));
			}
			
		});
				
	}

	private void bindEvents() {

        eventBus.addHandler(DisplayHelloWorldEvent.TYPE,
            new HelloWorldEvents.DisplayHelloWorldEvent() {
                public void processEvent(DisplayHelloWorldEvent event) {
                	view.displayResponse(event.response);
                }
            }
        );
	
	}


}