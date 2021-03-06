package demo.server;

import demo.client.rpc.GreetingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


/**
 * The server side implementation of the RPC service
 */

@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

  public String greetServer(String name) {

  	if(name == null)
  		throw new IllegalArgumentException("Parameter 'name' cannot be null");

    String serverInfo = getServletContext().getServerInfo();
    String userAgent = getThreadLocalRequest().getHeader("User-Agent");


    return "Hello, " + name + "!<br><br>I am running " + serverInfo
        + ".<br><br>It looks like you are using:<br>" + userAgent;
  }
}
