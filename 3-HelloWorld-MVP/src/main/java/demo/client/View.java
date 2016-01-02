package demo.client;


// SmartGWT - Widgets
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.HasClickHandlers;

// SmartGWT - Utility
import com.smartgwt.client.util.SC;

public class View  {

	// Create a button
	private final Button button = new Button("Hello?");

	public Canvas asCanvas() {
		return button;
	}

	public HasClickHandlers getClickable() {
		return button;
	}

	public void displayResponse(String response) {
		SC.say(response);	
	}

	public void displayError(String error) {
		SC.warn(error);	
	}

}