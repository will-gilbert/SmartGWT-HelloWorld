
package demo.client;

import demo.client.Application;
import demo.client.events.FetchHelloWorldEvent;
import demo.client.events.DisplayHelloWorldEvent;
import demo.client.events.DisplayServiceErrorEvent;

// GWT - Core, User
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.event.shared.testing.CountingEventBus;

import demo.client.rpc.GreetingServiceAsync; 
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.google.gwtmockito.GwtMockito;

// GWT EventBinder
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.GenericEvent;
import com.google.web.bindery.event.shared.binder.impl.GenericEventType;


import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

// Mockito
import org.mockito.Mockito;
import org.mockito.Matchers;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.gwtmockito.GwtMock;


// JUnit 4.x testing
import org.junit.runner.RunWith;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(GwtMockitoTestRunner.class)
public class ApplicationTest  {

    // SUT: Application.java =======================================================================

    @GwtMock
    GreetingServiceAsync rpcService;

    // EventType Constants
    GenericEventType fetchEventType = GenericEventType.getTypeOf(FetchHelloWorldEvent.class);
    GenericEventType displayEventType = GenericEventType.getTypeOf(DisplayHelloWorldEvent.class);
    GenericEventType errorEventType = GenericEventType.getTypeOf(DisplayServiceErrorEvent.class);

    // S E T U P  ==================================================================================

    @Before
    public void setupMocks() {

        // Mokito handles methods with 'void' returns differently;
        //  "any(String.class)" allows null input
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                AsyncCallback<String> callback = (AsyncCallback<String>)invocation.getArguments()[1];
                Object arg0 = invocation.getArguments()[0];
                if ( arg0 == null) 
                    callback.onFailure(new Throwable());
                else 
                    callback.onSuccess(arg0.toString());
                
                return null;
            }
        }).when(rpcService).greetServer(any(String.class), Matchers.<AsyncCallback<String>>any() );

    }

    @Before
    public void setupEventBinderFake() {
        GwtMockito.useProviderForType(EventBinder.class, new FakeEventBinderProvider());
    }


    // T E S T S  ==================================================================================
   
    @Test
    public void eventWithValidName() throws Exception {

        // Given
        CountingEventBus eventBus =  new CountingEventBus();
        Application presenter = new Application(eventBus, rpcService); 

        // When
        eventBus.fireEvent(new FetchHelloWorldEvent("World"));

        // Then
        assertEquals(1, eventBus.getFiredCount(fetchEventType));
        assertEquals(1, eventBus.getFiredCount(displayEventType));
        assertEquals(0, eventBus.getFiredCount(errorEventType));

    }


    @Test
    public void eventWithInvalidName() throws Exception {

        // Given
        CountingEventBus eventBus =  new CountingEventBus();
        Application presenter = new Application(eventBus, rpcService); 

        // When
        eventBus.fireEvent(new FetchHelloWorldEvent(null));

        // Then
        assertEquals(1, eventBus.getFiredCount(fetchEventType));
        assertEquals(0, eventBus.getFiredCount(displayEventType));
        assertEquals(1, eventBus.getFiredCount(errorEventType));

    }



}
