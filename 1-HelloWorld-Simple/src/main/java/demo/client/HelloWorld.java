package demo.client;

// GWT - Core, User
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.Window;

// SmartGWT - Widgets
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;

// SmartGWT - Utility
import com.smartgwt.client.util.SC;


/**
 * Entry point classes define onModuleLoad()
 */

public class HelloWorld implements EntryPoint {

	// Application starting point	
	public void onModuleLoad() {
		
		// Create a button
		Button button = new Button("Hello?");

		GWT.log("HelloWorld running");

		// Add handler code for a button click
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String userAgent = Window.Navigator.getUserAgent();
				SC.say("Hello, World!<br><br>" +
					   "It looks like I'm using:<br>" + 
					    userAgent
				);
			}
		});
				
		// Display the button
		RootPanel.get().add(button);
	}

}