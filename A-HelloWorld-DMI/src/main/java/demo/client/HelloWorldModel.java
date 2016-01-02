package demo.client;

// GWT - Core, User
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Callback;

// SmartGWT - RPC
import com.smartgwt.client.rpc.DMI;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.rpc.RPCCallback;

// Java - Collections
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

// Java - Util
import java.util.Date;

// Constants
import static com.smartgwt.client.rpc.RPCResponse.STATUS_SUCCESS;

public class HelloWorldModel implements HelloWorldPresenter.Model  {

	private String name = null;

	@Override
	public void setName(String name) {
		this.name = name;
	}

    @Override
    public void fetchGreeting(final Callback<String,String> callback) {

        // Putting the name in a Map and adding a other objects for illustration
        Map<String,Object> map = new HashMap<>();
        map.put("name", name);
        map.put("boolean", true);
        map.put("int", 1);
        map.put("date", new Date());

        // Package the method arguments in an List; Will get converted to an array for the DMI
        List<Object> arguments = new ArrayList<>();
        arguments.add(map);
        arguments.add(new Date());

        DMI.call("demo", "GreetingService", "greetServer", 
            new RPCCallback() {
                public void execute(RPCResponse response, Object rawData, RPCRequest request) {

                    if (response.getStatus() == STATUS_SUCCESS) {

                        Map<String,Object> data = response.getDataAsMap();
                        String greetings = data.get("greetings").toString();
                        int count = ((Integer)data.get("count")).intValue();
                        
                        StringBuffer buffer = new StringBuffer()
                            .append(greetings).append("<br>")
                            .append("You've accessed me ")
                            .append(count)
                            .append(" times.");

                        callback.onSuccess(buffer.toString());
                    } else
                        callback.onFailure("Failure: HelloWorldModel.fetchGreeting");

                }
            }, arguments.toArray());
    }
}



