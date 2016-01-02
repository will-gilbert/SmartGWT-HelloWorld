package demo.client;


// SmartGWT - Widgets & Events
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.HasClickHandlers;

public interface HelloWorldView  {

	HasClickHandlers getClickable();
	void displayResponse(String response);
	void displayError(String errorMessage);
	Canvas asCanvas();

}