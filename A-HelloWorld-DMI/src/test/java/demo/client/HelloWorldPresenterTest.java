
package demo.client;


import demo.client.HelloWorldPresenter;


// GWT - Client
import com.google.gwt.core.client.Callback;

// SmartGWT - Events
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;

// Mockito Testing
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
import org.junit.runner.RunWith;


// Mokito methods
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;

// JUnit methods
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


public class HelloWorldPresenterTest  {

	@Mock
	HelloWorldPresenter.Model model;

	@Mock
	HelloWorldPresenter.View view;

    // Presenter inner classes
    ClickHandler helloClickHandler;
    ClickHandler noNameClickHandler;

    final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

    @Before
    public void setupMocks() {
        MockitoAnnotations.initMocks(this);

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

    
    @Test
    public void onSuccess() throws Exception {

        // Given
        HelloWorldPresenter presenter = new HelloWorldPresenter(model, view); 
        modelWillReturnSuccess();

        // When
        helloClickHandler.onClick(new ClickEvent(null){});

        // Expect
        verify(view, times(1)).displayResponse(captor.capture());
        verify(view, never()).displayError(anyString());

        assertEquals("Hello, World", captor.getValue());

    }

    @Test
    public void onFailure() throws Exception {

        // Given
        HelloWorldPresenter presenter = new HelloWorldPresenter(model, view); 
        modelWillReturnFailure();

        // When
        noNameClickHandler.onClick(new ClickEvent(null){});

        // Expect
        verify(view, never()).displayResponse(anyString());
        verify(view, times(1)).displayError(captor.capture());

        assertEquals("Server Failure", captor.getValue());

    }

    //================================================================================================

    private void modelWillReturnSuccess() {

        doAnswer(new Answer<Object>() {

            public Object answer(InvocationOnMock invocation) throws Throwable {
                Callback <String,String> callback = (Callback<String,String>) invocation.getArguments()[0];
                callback.onSuccess("Hello, World");
                return null;
            }

        }).when(model).fetchGreeting((Callback<String,String>) any());
        
    }

    private void modelWillReturnFailure() {

        doAnswer(new Answer<Object>() {

            public Object answer(InvocationOnMock invocation) throws Throwable {
                Callback <String,String> callback = (Callback<String,String>) invocation.getArguments()[0];
                callback.onFailure("Server Failure");
                return null;
            }

        }).when(model).fetchGreeting((Callback<String,String>) any());
        
    }


}
