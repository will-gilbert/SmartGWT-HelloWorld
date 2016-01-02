
package demo.client;


import demo.client.HelloWorldPresenter;
import demo.client.events.HelloWorldEvents;
import demo.client.events.FetchHelloWorldEvent;
import demo.client.events.DisplayHelloWorldEvent;
import demo.client.events.DisplayServiceErrorEvent;

// GWT - Core, User
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.event.shared.EventBus;
import com.google.web.bindery.event.shared.Event;

import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;


import static org.mockito.Mockito.*;

// Mockito
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

// JUnit 4.x testing
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



public class HelloWorldPresenterTest  {
 
	@Mock
	HelloWorldPresenter.View view;

	@Mock
	HasClickHandlers hasClickHandlers; 
 
    ClickHandler clickHandler;


    final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        // The view communicates via the a 'ClickHandler' interface 
        when(view.getHelloClickable()).thenReturn(hasClickHandlers);
        when(view.getNoNameClickable()).thenReturn(hasClickHandlers);

        // When the mock 'hasClickHandlers' is registered get the Presenters
        //  real 'ClickHandler' from the invocation arguments
        
        when(hasClickHandlers.addClickHandler(any(ClickHandler.class)))
            .thenAnswer(
                new Answer<Object>() {
                    public Object answer(InvocationOnMock aInvocation) throws Throwable {
                        clickHandler = (ClickHandler) aInvocation.getArguments()[0];
                        return null;
                    }
                }
           );
    }
   
    @Test
    public void fetchIsSuccessful() throws Exception {

        // Given
        EventBus eventBus = createSuccessfulEventBusResponse();
        HelloWorldPresenter presenter = new HelloWorldPresenter(view, eventBus); 

        // When
        clickHandler.onClick(new ClickEvent(null){});

        // Then
        verify(view, times(1)).displayResponse(captor.capture());

        assertEquals("Hello, World", captor.getValue());
    }

    @Test
    public void fetchFailed() throws Exception {

        // Given
        EventBus eventBus = createFailedEventBusResponse();
        HelloWorldPresenter presenter = new HelloWorldPresenter(view, eventBus); 

        // When
        clickHandler.onClick(new ClickEvent(null){});

        // Then
        verify(view, never()).displayResponse(anyString());
    }

    //===================================================================================================

    private EventBus createSuccessfulEventBusResponse() {

        final EventBus eventBus = new SimpleEventBus();

        eventBus.addHandler(FetchHelloWorldEvent.TYPE,
            new HelloWorldEvents.FetchHelloWorldEvent() {
                public void processEvent(FetchHelloWorldEvent event) {
                	eventBus.fireEvent(new DisplayHelloWorldEvent("Hello, World"));;
                }
            }
        );

    	return eventBus;
    }


    private EventBus createFailedEventBusResponse() {

        final EventBus eventBus = new SimpleEventBus();

        eventBus.addHandler(FetchHelloWorldEvent.TYPE,
            new HelloWorldEvents.FetchHelloWorldEvent() {
                public void processEvent(FetchHelloWorldEvent event) {
                	eventBus.fireEvent(new DisplayServiceErrorEvent("Server Failure"));;
                }
            }
        );

    	return eventBus;
    }


}
