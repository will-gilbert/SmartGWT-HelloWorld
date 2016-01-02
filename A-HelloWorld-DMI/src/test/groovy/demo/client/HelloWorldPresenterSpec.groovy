package demo.client

import demo.client.HelloWorldPresenter

import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;

import com.google.gwt.core.client.Callback;

import spock.lang.Specification

class HelloWorldPresenterSpec extends Specification {
          
    
    def "Hello is clicked"() {
        
        given:

            final name = 'World'
            final response = 'Hello, World!'

            def view = Mock(HelloWorldPresenter.View) {
                getHelloClickable() >> Mock(HasClickHandlers) {
                    addClickHandler(_) >> { ClickHandler clickHandler -> 
                        clickHandler.onClick(new ClickEvent(null){})
                    }
                }
                getNoNameClickable() >> Mock(HasClickHandlers)
            }

            def model = Mock(HelloWorldPresenter.Model) {
                fetchGreeting(_) >> { Callback<String,String> callback ->  
                    callback.onSuccess(response) 
                }
            }


        when:
            def presenter = new HelloWorldPresenter(model, view);

        then:
            1 * model.setName(name)

        then:
            1 * view.displayResponse(response)
    }


    def "NoName is clicked"() {
        
        given:

            final name = ''
            final response = 'Failure: HelloWorldModel.fetchGreeting'

            def view = Mock(HelloWorldPresenter.View) {
                getHelloClickable() >> Mock(HasClickHandlers)
                getNoNameClickable() >> Mock(HasClickHandlers) {
                    addClickHandler(_) >> { ClickHandler clickHandler -> 
                        clickHandler.onClick(new ClickEvent(null){})
                    }
                }
            }

            def model = Mock(HelloWorldPresenter.Model) {
                fetchGreeting(_) >> { Callback<String,String> callback ->  
                    callback.onFailure(response) 
                }
            }

        when:
            def presenter = new HelloWorldPresenter(model, view);

        then:
            1 * model.setName(name)

        then:
            1 * view.displayError(response)
    }


}