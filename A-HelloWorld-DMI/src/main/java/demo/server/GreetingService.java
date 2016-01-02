package demo.server;

// Java - Collections
import java.util.Map;
import java.util.HashMap;

// Java - Util
import java.util.Date;

@SuppressWarnings("serial")
public class GreetingService {

    static int count = 0;

    public Map<String,Object> greetServer(Map<String,Object> map, Date now) {

        String name = map.get("name").toString();
        if(name == null || name.trim().length() == 0)
            throw new IllegalArgumentException("Parameter 'name' cannot be null or empty");

        count++;

        Map<String,Object> results = new HashMap<>();
        results.put("greetings", "Hello, " + name + "!");
        results.put("count", count);
        results.put("date", now);
        results.put("boolean", ((Boolean)map.get("boolean")).booleanValue() ? Boolean.FALSE : Boolean.TRUE);

        return results;
  }

}

