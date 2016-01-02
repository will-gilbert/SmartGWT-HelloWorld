package demo.server;

// Java EE - HTTP 
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

// Java - I/O
import java.io.PrintWriter;
import java.io.IOException;

// Java - Collections
import java.util.Map;

@SuppressWarnings("serial")
public class GreetingService extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Set response content type
        String name = request.getParameter("name");

        // Actual logic goes here       
        String serverInfo = getServletContext().getServerInfo();
        String userAgent = request.getHeader("User-Agent");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println(greetServer(name, serverInfo, userAgent));

    }

  public String greetServer(String name, String serverInfo, String userAgent) {

    if(name == null)
        throw new IllegalArgumentException("Parameter 'name' cannot be null");

    return "Hello, " + name + "!<br><br>I am running " + serverInfo
        + ".<br><br>It looks like you are using:<br>" + userAgent;
  }

}
