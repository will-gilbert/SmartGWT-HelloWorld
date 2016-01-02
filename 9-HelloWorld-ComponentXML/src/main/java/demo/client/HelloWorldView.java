package demo.client;


// GWT - Core, User
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Window;

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

	HLayout container = (HLayout)HLayout.getById("ButtonLayout");

	// Create the buttons via ComponentXML
	private final Button helloButton = (Button)Canvas.getById("HelloWorld.button");
	private final Button noNameButton = (Button)Canvas.getById("HelloWorld.noname.button");

	public HelloWorldView() {
		
		// ComponentXML does not support IconButton but 
		//   we can add it programtically 

		container.addMember(createConsoleButton(), 0);
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

	@Override
	public void displayError(String error) {
		SC.warn(error);	
	}

	// Create a button to show the Developer console
	private Widget createConsoleButton() {

		IconButton iconButton =  new IconButton();

		iconButton.setIcon("icons/developer.png");
		iconButton.setShowButtonTitle(false);
		iconButton.setPrompt("Open the Developer Console");

		iconButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				SC.showConsole();
			}
		});

		return iconButton;
	}

}