package demo.client;

import demo.client.events.FetchHelloWorldEvent;
import demo.client.events.DisplayHelloWorldEvent;
import demo.client.events.DisplayServiceErrorEvent;

// GWT - Core & Event Bus
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;

// SmartGWT - Widgets
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;

// SmartGWT - Utility
import com.smartgwt.client.util.SC;

// EventBinder
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

// Google - Dependency Injection
import com.google.inject.Inject;


public class HelloWorldPresenter  {

  	interface AnEventBinder extends EventBinder<HelloWorldPresenter> {}
  	private static final AnEventBinder eventBinder = GWT.create(AnEventBinder.class);

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
		eventBinder.bindEventHandlers(this, eventBus);
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

  	@EventHandler
  	void onDisplayHelloWorld(DisplayHelloWorldEvent event) {
		view.displayResponse(event.response);
	}	



}