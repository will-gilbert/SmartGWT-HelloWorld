package demo.client;


// Greeting RPC Service
import demo.client.rpc.GreetingService; 
import demo.client.rpc.GreetingServiceAsync; 

// GWT - Core, User
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Callback;
import com.google.gwt.user.client.rpc.AsyncCallback;

import demo.shared.dto.GreetingServiceDTO;

// Google Inject
import com.google.inject.Inject;

public class HelloWorldModel implements HelloWorldPresenter.Model  {

	// Service Provider
	final GreetingServiceAsync rpcService;

	private String name;

	@Inject
	public HelloWorldModel(GreetingServiceAsync rpcService) {
		this.rpcService = rpcService;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void fetchGreeting(final Callback<String,String> callback) {

		rpcService.greetServer(name, new AsyncCallback<GreetingServiceDTO>() {

			public void onSuccess(GreetingServiceDTO dto) {
				String response = greetingToHTML(dto);
				callback.onSuccess(response);
			}

			public void onFailure(Throwable caught) {
				callback.onFailure(caught.getMessage());
			}

		});
	}

	//==============================================================================================

	private String greetingToHTML(GreetingServiceDTO dto) {

		return new StringBuffer()
				.append(dto.message).append("<br><br>")
				.append("I am running ").append(dto.serverInfo).append(".<br><br>")
				.append("It looks like you are using:<br>").append(dto.userAgent)
				.toString();
	}


}
