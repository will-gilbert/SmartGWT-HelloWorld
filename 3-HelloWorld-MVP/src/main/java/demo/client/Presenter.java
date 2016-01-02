package demo.client;


// GWT - RPC
import com.google.gwt.user.client.rpc.AsyncCallback;

// SmartGWT - Widgets & Events
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;

// SmartGWT - Utility
import com.smartgwt.client.util.SC;

public class Presenter  {

	final Model model;
	final View view;

	public Presenter(Model model, View view) {
		this.model = model;
		this.view = view;

		bindView();	
	}

	public Canvas getView() {
		return view.asCanvas();
	}

	void bindView() {

		// Add handler code for a button click
		view.getClickable().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				model.invokeGreetingServiceWith("World", new AsyncCallback<String>() {

					public void onSuccess(String response) {
						view.displayResponse(response);
					}

					public void onFailure(Throwable caught) {
						view.displayError(caught.getMessage());
					}

				});
			}
			
		});
				
	}

}