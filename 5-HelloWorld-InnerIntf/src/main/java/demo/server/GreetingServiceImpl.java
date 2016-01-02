package demo.server;

// Applicaton - Service RPC
import demo.client.rpc.GreetingService;


// GWT - RPC
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import demo.shared.dto.GreetingServiceDTO;

/**
 * The server side implementation of the RPC service
 */


@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

    public GreetingServiceDTO greetServer(String name) {

        GreetingServiceDTO dto = new GreetingServiceDTO();
        dto.message = "Hello, " + name + "!";
        dto.serverInfo = getServletContext().getServerInfo();
        dto.userAgent = getThreadLocalRequest().getHeader("User-Agent");

        return dto;
    }
}
