package demo.client;


// SmartGWT - Widgets
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.HasClickHandlers;

// SmartGWT - Utility
import com.smartgwt.client.util.SC;

public class HelloWorldView implements HelloWorldPresenter.View  {

	// Create a button
	private final Button button = new Button("Hello?");

	public HelloWorldView() {
		button.setID("HelloWorld.button");
		button.setBaseStyle(button.getBaseStyle() + " " + button.getID());
	}

	@Override
	public Canvas asCanvas() {
		return button;
	}

	@Override
	public HasClickHandlers getClickable() {
		return button;
	}

	@Override
	public void displayResponse(String response) {
		SC.say(response);	
	}

	@Override
	public void displayError(String error) {
		SC.warn(error);	
	}

}