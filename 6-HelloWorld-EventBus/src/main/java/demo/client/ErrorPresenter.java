package demo.client;

// Application Events
import demo.client.events.HelloWorldEvents;
import demo.client.events.DisplayServiceErrorEvent;

// GWT - Core, User
import com.google.gwt.event.shared.EventBus;

// SmartGWT - Utility
import com.smartgwt.client.util.SC;

// Google - Dependency Injection
import com.google.inject.Inject;


public class ErrorPresenter  {

	final EventBus eventBus;

	@Inject
	public ErrorPresenter(EventBus eventBus) {
		this.eventBus = eventBus;
		bindEvents();
	}

	private void bindEvents() {

		// Receive and process a server error
        eventBus.addHandler(DisplayServiceErrorEvent.TYPE,
            new HelloWorldEvents.DisplayServiceErrorEvent() {
                public void processEvent(DisplayServiceErrorEvent event) {
                	SC.warn(event.errorMessage);
                }
            }
        );



	}


}