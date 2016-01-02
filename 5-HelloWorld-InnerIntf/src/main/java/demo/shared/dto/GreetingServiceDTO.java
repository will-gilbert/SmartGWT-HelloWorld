package demo.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GreetingServiceDTO implements IsSerializable {

    public String message;
    public String serverInfo;
    public String userAgent;

}
