package demo.client;

// SmartGWT - Widgets & Events
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.HasClickHandlers;

// SmartGWT - Layout
import com.smartgwt.client.widgets.layout.HLayout;

// SmartGWT - Utility
import com.smartgwt.client.util.SC;

public class HelloWorldView implements HelloWorldPresenter.View  {

	// Create the view container
	private final HLayout container = new HLayout(10);

	// Create a button
	private final Button helloButton = new Button("Hello?");
	private final Button noNameButton = new Button("No Name");

	public HelloWorldView() {
		container.addMember(helloButton);
		container.addMember(noNameButton);
	}


	@Override
	public Canvas asCanvas() {
		return container;
	}

	@Override
	public HasClickHandlers getHelloClickable() {
		return helloButton;
	}

	@Override
	public HasClickHandlers getNoNameClickable() {
		return noNameButton;
	}

	@Override
	public void displayResponse(String response) {
		SC.say(response);	
	}

}