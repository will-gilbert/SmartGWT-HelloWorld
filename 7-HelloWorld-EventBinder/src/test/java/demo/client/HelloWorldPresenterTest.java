
package demo.client;

import demo.client.HelloWorldPresenter;
import demo.client.events.FetchHelloWorldEvent;
import demo.client.events.DisplayHelloWorldEvent;
import demo.client.events.DisplayServiceErrorEvent;

// GWT - Core, User
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.event.shared.testing.CountingEventBus;


import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;

import com.google.gwtmockito.GwtMockito;

// GWT EventBinder
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.GenericEvent;
import com.google.web.bindery.event.shared.binder.impl.GenericEventType;


import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

// Mockito
import org.mockito.Mockito;
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
public class HelloWorldPresenterTest  {

    // SUT: HelloWorldPresenter.java ===============================================================
 
    @GwtMock
    HelloWorldPresenter.View view;

    // Presenter inner classes
    ClickHandler helloClickHandler;
    ClickHandler noNameClickHandler;

    // EventType Constants
    GenericEventType fetchEventType = GenericEventType.getTypeOf(FetchHelloWorldEvent.class);
    GenericEventType errorEventType = GenericEventType.getTypeOf(DisplayServiceErrorEvent.class);

    final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

    // S E T U P  ==================================================================================

    @Before
    public void setupMocks() {

        // Access the anonymous inner class ClickHanders of the SUT via these 
        //   HasClickHandlers; Use two levels of mocks: View & HasClickHandlers

        HasClickHandlers hasHelloClickHandlers = mock(HasClickHandlers.class);
        when(view.getHelloClickable()).thenReturn(hasHelloClickHandlers);
        when(hasHelloClickHandlers.addClickHandler(any(ClickHandler.class)))
            .thenAnswer(
                new Answer<Object>() {
                    public Object answer(InvocationOnMock aInvocation) throws Throwable {
                        helloClickHandler = (ClickHandler) aInvocation.getArguments()[0];
                        return null;
                    }
                }
           );

        HasClickHandlers hasNoNameClickHandlers = mock(HasClickHandlers.class);
        when(view.getNoNameClickable()).thenReturn(hasNoNameClickHandlers);
        when(hasNoNameClickHandlers.addClickHandler(any(ClickHandler.class)))
            .thenAnswer(
                new Answer<Object>() {
                    public Object answer(InvocationOnMock aInvocation) throws Throwable {
                        noNameClickHandler = (ClickHandler) aInvocation.getArguments()[0];
                        return null;
                    }
                }
           );
    }

    @Before
    public void setupEventBinderFake() {
        GwtMockito.useProviderForType(EventBinder.class, new FakeEventBinderProvider());
    }


    // T E S T S  ==================================================================================
   
    @Test
    public void respondToSuccessEvent() throws Exception {

        // Given
        EventBus eventBus =  new SimpleEventBus();
        HelloWorldPresenter presenter = new HelloWorldPresenter(view, eventBus); 

        // When
        eventBus.fireEvent(new DisplayHelloWorldEvent("Hello, World"));

        // Then
        verify(view, times(1)).displayResponse(captor.capture());

        assertEquals("Hello, World", captor.getValue());
    }

    @Test
    public void ignoreFailureEvent() throws Exception {

        // Given
        EventBus eventBus =  new SimpleEventBus();
        HelloWorldPresenter presenter = new HelloWorldPresenter(view, eventBus); 

        // When
        eventBus.fireEvent(new DisplayServiceErrorEvent("Server Failure"));

        // Then
        verify(view, never()).displayResponse(anyString());
    }

    @Test
    public void receiveDisplayHelloWorldEvent() throws Exception {

        // Given
        EventBus eventBus = new SimpleEventBus();
        HelloWorldPresenter presenter = new HelloWorldPresenter(view, eventBus);

        // When
        eventBus.fireEvent(new DisplayHelloWorldEvent("Hello, World"));

        // Then
        verify(view, times(1)).displayResponse(captor.capture());

        assertEquals("Hello, World", captor.getValue());

    }

    @Test
    public void helloClickFiresEvent() {

        // Given
        CountingEventBus eventBus =  new CountingEventBus();
        HelloWorldPresenter presenter = new HelloWorldPresenter(view, eventBus);

        // And ensuring that 
        assertEquals(0, eventBus.getFiredCount(fetchEventType));

        // When
        helloClickHandler.onClick(new ClickEvent(null){});

        // Then
        assertEquals(1, eventBus.getFiredCount(fetchEventType));

    }

    @Test
    public void noNameClickFiresEvent() {

        // Given
        CountingEventBus eventBus =  new CountingEventBus();
        HelloWorldPresenter presenter = new HelloWorldPresenter(view, eventBus);

        // And ensuring that 
        assertEquals(0, eventBus.getFiredCount(fetchEventType));

        // When
        noNameClickHandler.onClick(new ClickEvent(null){});

        // Then
        assertEquals(1, eventBus.getFiredCount(fetchEventType));

    }




}
