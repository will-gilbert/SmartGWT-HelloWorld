package demo.client;

// GWT Mockito
import com.google.gwtmockito.fakes.FakeProvider;

// GWT Events
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;

// GWT EventBinder
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;
import com.google.web.bindery.event.shared.binder.GenericEvent;
import com.google.web.bindery.event.shared.binder.impl.GenericEventType;
import com.google.web.bindery.event.shared.binder.impl.GenericEventHandler;

// Java Reflection
import java.lang.reflect.Proxy;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;

// Java Collections
import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 **  From: http://klimer.eu/2014/08/27/testing-gwteventbinder-with-gwtmockito/
 **  
 **  Gwteventbinder and gwtmockito are great projects that are esential if you’re 
 **    writing applications in Google Web Toolkit/GWT. So it was a surprise to me 
 **    that they don’t play together well “out of the box”.
 **
 ** See, the problem is that gwtmockito injects it’s own (safe, not using any 
 **   code that requires running a browser) mocks when it encounters GWT.create
 **   in your code. That’s very cool, but when using gwteventbinder, you need to 
 **   call the bindEventHandlers method to, well, bind the event handler. And 
 **   the mock, obviously, doesn’t do that.
 **
 ** However, there’s a solution - FakeProvider. By registering one for the 
 **   EventBinder interface, it will be used for providing instances of 
 **   EventBinder and its subclasses. Unfortunately, EventBinder uses a 
 **   GWT Generator for it’s implementation, and to “emulate” that during 
 **   our tests, we have to resort to Java Reflection.
 **
 **  The following implementation is inspired by comments and suggestions from
 **    an issue</a> raised on gwteventbinder’s tracker. You can find alternative 
 **    solutions there, too.
 **
 **  It’s not as bad as it seems. When you get rid of the Java Reflection cruft, 
 **    it boils down to:
 **
 **   - Find all the methods on the presenter that are annotated with EventHandler
 **   - Register a new handler that invokes the callback method for the event 
 **       specified as it’s first argument.
 **   - Return a HandlerRegistration instance that on call to removeHandler
 **       removes all the handlers added in the previous point (this behavior 
 **       is copied from the actual implementation of gwteventbinder).
 **
 **  You might note that I don’t do any safe-checks… Well, assuming that you’ve 
 **    run your code before, using (Super)DevMode, the EventBinder’s generator 
 **    already checked that your code is sane and within the requirements of gwteventbinder.
 **
 **  Now, all that’s left is to register this provider, for example in your 
 **    @Before method:</p>
 **
 **        @Before
 **        public void setUpEventBinder() {
 **            GwtMockito.useProviderForType(EventBinder.class, new FakeEventBinderProvider());
 **        }
 **
 **  Like I’ve already mentioned, you only need to do this for the base interface, 
 **    EventBinder, since as the documentation for GwtMockito.useProviderForType says:
 **
 **      ...the given provider should be used to GWT.create instances of the given 
**          type and its subclasses...
 **
 **
 **  Source: https://gist.github.com/kevinfahy/8c411efd26c7696dc4c8
 **/

public class FakeEventBinderProvider implements FakeProvider<EventBinder<?>> {

    @Override
    public EventBinder<?> getFake(Class<?> type) {

        return (EventBinder<?>) Proxy.newProxyInstance(FakeEventBinderProvider.class.getClassLoader(), new Class<?>[] { type }, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, final Object[] args) throws Throwable {

                String methodName = method.getName();
                assert methodName.equals("bindEventHandlers");

                final List<HandlerRegistration> registrations = new LinkedList<HandlerRegistration>();
                EventBus eventBus = (EventBus) args[1];

                List<Method> presenterMethods = getAllMethods(args[0].getClass());

                for (final Method presenterMethod : presenterMethods) {
                    for (Class<? extends GenericEvent> eventType :
                            getEventTypesForMethod(presenterMethod)) {
                        registrations.add(eventBus.addHandler(GenericEventType.getTypeOf(eventType), new GenericEventHandler() {

                            @Override
                            public void handleEvent(GenericEvent event) {
                                try {
                                    presenterMethod.setAccessible(true);
                                    if (presenterMethod.getParameterTypes().length > 0) {
                                        presenterMethod.invoke(args[0], event);
                                    } else {
                                        presenterMethod.invoke(args[0]);
                                    }
                                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }));
                    }
                }

                return new HandlerRegistration() {
                    @Override
                    public void removeHandler() {
                        for (HandlerRegistration registration : registrations) {
                            registration.removeHandler();
                        }
                        registrations.clear();
                    }
                };
            }
        });
    }

    @SuppressWarnings("unchecked")
    private List<Class<? extends GenericEvent>> getEventTypesForMethod(Method method) {

        if (!method.isAnnotationPresent(EventHandler.class)) {
            return Collections.emptyList();
        }

        if (method.getParameterTypes().length > 0) {
            // Assume the event handler method has only one parameter.
            List<Class<? extends GenericEvent>> eventTypes = new ArrayList<>();
            eventTypes.add((Class<? extends GenericEvent>) method.getParameterTypes()[0]);
            return eventTypes;
        }

        EventHandler eventHandler = (EventHandler) method.getAnnotation(EventHandler.class);
        if (eventHandler.handles().length <= 0) {
            throw new IllegalStateException(
                    "Event handler method has neither annotations nor parameters.");
        }
        return Arrays.asList(eventHandler.handles());
    }

    private List<Method> getAllMethods(Class<?> type) {
        List<Method> methods = new LinkedList<Method>();
        methods.addAll(Arrays.asList(type.getDeclaredMethods()));
        if (type.getSuperclass() != null) {
            methods.addAll(getAllMethods(type.getSuperclass()));
        }
        return methods;
    }
}