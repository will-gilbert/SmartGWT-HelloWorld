package demo.client.rpc;

// GWT RPC
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


import demo.shared.dto.GreetingServiceDTO;

/**
 * The interface for the RPC service
 */

@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {

	// Submit a name, then receive a response
 	GreetingServiceDTO greetServer(String name);

}
