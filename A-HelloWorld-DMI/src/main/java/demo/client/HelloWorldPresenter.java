package demo.client;


// GWT - Core, User
import com.google.gwt.core.client.Callback;

// SmartGWT - Widgets & Events
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;

// Google - Dependency Injection
import com.google.inject.Inject;

public class HelloWorldPresenter  {

	// Inner Interfaces ======================================================================
	public interface View {
		HasClickHandlers getHelloClickable();
		HasClickHandlers getNoNameClickable();
		void displayResponse(String response);
		void displayError(String errorMessage);
		Canvas asCanvas();
	}

	public interface Model {
		void setName(String name);
		void fetchGreeting(Callback<String,String> callback);
	}
	// ======================================================================================


	final Model model;
	final View view;

	@Inject
	public HelloWorldPresenter(Model model, View view) {
		this.model = model;
		this.view = view;

		bindView();
	}

	public Canvas getView() {
		return view.asCanvas();
	}

	void bindView() {

		// Add handler code for a button click
		view.getHelloClickable().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				model.setName("World");
				model.fetchGreeting(new Callback<String,String>() {

					public void onSuccess(String response) {
						view.displayResponse(response);
					}

					public void onFailure(String message) {
						view.displayError(message);
					}

				});
			}
			
		});


		// Add handler code for a button click
		view.getNoNameClickable().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				model.setName("");
				model.fetchGreeting(new Callback<String,String>() {

					public void onSuccess(String response) {
						view.displayResponse(response);
					}

					public void onFailure(String message) {
						view.displayError(message);
					}

				});
			}
			
		});
				
	}

}