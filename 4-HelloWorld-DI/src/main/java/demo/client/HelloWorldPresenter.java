package demo.client;


// GWT - RPC
import com.google.gwt.user.client.rpc.AsyncCallback;

// SmartGWT - Widgets
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;

// Google - Dependency Injection
import com.google.inject.Inject;

public class HelloWorldPresenter  {

	final HelloWorldModel model;
	final HelloWorldView view;

	@Inject
	public HelloWorldPresenter(HelloWorldModel model, HelloWorldView view) {
		this.model = model;
		this.view = view;

		bindView();
		model.setName("World");
	}

	public Canvas getView() {
		return view.asCanvas();
	}

	void bindView() {

		// Add handler code for a button click
		view.getClickable().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				model.fetchGreeting(new AsyncCallback<String>() {

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