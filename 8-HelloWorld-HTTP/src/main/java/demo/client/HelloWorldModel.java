package demo.client;

// GWT - Core, User
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Callback;

// SmartGWT - RPC
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.rpc.RPCCallback;

// Java - Collections
import java.util.Map;
import java.util.HashMap;


public class HelloWorldModel implements HelloWorldPresenter.Model  {

	private String name = null;

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void fetchGreeting(final Callback<String,String> callback) {

        RPCRequest request = new RPCRequest();

        // Create and add POST params
        Map<String,String> params = new HashMap<>();
        params.put("name", name);

        // Set up HTTP POST
        request.setParams(params);
        request.setActionURL(GWT.getModuleBaseURL() + "greet");
        request.setHttpMethod("POST");
        request.setUseSimpleHttp(true);

        RPCManager.sendRequest(request,
            new RPCCallback() {
                public void execute(RPCResponse response, Object obj, RPCRequest request) {
 
                    if (response.getStatus() == RPCResponse.STATUS_SUCCESS) {
                        callback.onSuccess(response.getDataAsString()); 
                    } else {
                        callback.onFailure("An error occured");
                    }
                }
            });
  }
}



