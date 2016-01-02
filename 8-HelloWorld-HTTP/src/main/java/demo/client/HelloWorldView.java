package demo.client;

// SmartGWT - Widgets
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.IconButton;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;

import com.smartgwt.client.widgets.layout.HLayout;

// SmartGWT - Utility
import com.smartgwt.client.util.SC;

public class HelloWorldView implements HelloWorldPresenter.View  {

	HLayout container = new HLayout(25);

	// Create a button
	private final Button button = new Button("Hello?");

	public HelloWorldView() {
		button.setID("HelloWorld.button");
		button.setBaseStyle(button.getBaseStyle() + " " + button.getID());

		container.addMember(createDeveloperButton());
		container.addMember(button);
	}

	@Override
	public Canvas asCanvas() {
		return container;
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

	// Create a button to show the Developer console
	private Canvas createDeveloperButton() {

		IconButton button = new IconButton();
		button.setIcon("icons/developer.png");
		button.setShowButtonTitle(false);
		button.setPrompt("Open the Developer Console");

		button.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				SC.showConsole();
			}
		});

		return button;
	}

}