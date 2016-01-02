package demo.client

import com.smartgwt.client.widgets.events.HasClickHandlers;

import groovy.mock.interceptor.MockFor
import groovy.mock.interceptor.StubFor

// By entending 'GroovyTestCase' inhert the power assert and prefix any test
//   with 'test' no need for org.junit.Text annotation

class GroovyTesting extends GroovyTestCase {
    

    //=====================================================================================
    // Simple Groovy test of 'assert'

    void testOne() {

        // No parenthesis required
        assert(1 == 1)
        assert 1 == 1

        // JUnit vs Power Assert
        assertEquals("test", "test")
        assert "test" == "test"

        // Groovy Sameness
        def x = "42"
        def y = x
        assert x.is(y)

        // Testing for 'null' and not 'null'
        assert x != null
        x = null
        assert x == null

    }


    //=====================================================================================
    // Create a 'Test Double' using a Map of closures with 'as' keyword
    //   The closure could have returned a constant response, here it
    //   mimics the actual 'Service' class method 'convert'.

    void testTwo() {
        def service = [convert: { String key -> key + ".text" }] as TranslationService
        assert 'some.text' == service.convert('some')
    }


    //=====================================================================================
    // Here 'Family' is the CUT (Class under test) and we create a mock for 'Person'
    //   the methods 'getFirst' and 'getLast' will be invoked once in order until the
    //   ordered list is exhausted.
    //   Here the methods are invoked in order.

    void testThree() {

        def mock = new MockFor(Person)

        // Order dependent invocation
        mock.demand.with {
            getFirst{ -> 'dummy' }
            getLast{ -> 'name' }
            getFirst{ -> 'DUMMY' }
            getLast{ -> 'NAME' }
        }

        mock.use {                          
            def mary = new Person(first:'Mary', last:'Smith')
            def f = new Family(mother:mary)
            assert f.nameOfMother() == 'dummy name'

            def joe = new Person(first:'Joe', last:'Smith')
            f = new Family(mother: mary, father: joe)
            assert f.nameOfFather() == 'DUMMY NAME'
        }
        
        // 'verify' ensures all demands were used
        mock.expect.verify()                
    }

    //=====================================================================================
    // Here 'Family' is the CUT (Class under test) and we create a stub for 'Person'
    //   A 'Stub' allows methods to be called out of order until the list is exhausted.
    //   Here there are two invocations each of 'getFirst' and 'getLast'.

    void testFour() {
        def stub = new StubFor(Person)      
        stub.demand.with {
            getFirst{ 'dummy' }
            getFirst{ 'DUMMY' }
            getLast{ 'name' }
            getLast{ 'NAME' }
        }

        stub.use {                          
            def mary = new Person(first:'Mary', last:'Smith')
            def f = new Family(mother:mary)
            assert f.nameOfMother() == 'dummy name'

            def joe = new Person(first:'Joe', last:'Smith')
            f = new Family(mother: mary, father: joe)
            assert f.nameOfFather() == 'DUMMY NAME'
        }
        
        // 'verify' ensures all demands were used
        stub.expect.verify()                
    }


    void testFarmMachinesWithFakes() {

        final LOW_LOAD = 5, HIGH_LOAD = 10

        // Create an array of 'Fake' objects
        def farm = [machines: [
                new FakeMachine(load:HIGH_LOAD), 
                new FakeMachine(load:HIGH_LOAD), 
                new FakeMachine(load:HIGH_LOAD), 
                new FakeMachine(load:HIGH_LOAD), 
                new FakeMachine(load:HIGH_LOAD), 
                new FakeMachine(load:LOW_LOAD)
            ]
        ]

        // CUT: LoadBalancer; We are really testing the sorting of the farm's machine by load
        def loadBalancer = new LoadBalancer()
        assert LOW_LOAD == loadBalancer.relay("", farm).load
    }

    // Stubs are not 'demand' order execution dependent
    void testFarmMachinesWithStubs() {

        final LOW_LOAD = 5, HIGH_LOAD = 10

        def farmStub = new StubFor(Farm)

        farmStub.demand.aMethod { true }
        farmStub.demand.getMachines { [ 
                new FakeMachine(load:HIGH_LOAD), 
                new FakeMachine(load:HIGH_LOAD), 
                new FakeMachine(load:HIGH_LOAD), 
                new FakeMachine(load:HIGH_LOAD), 
                new FakeMachine(load:HIGH_LOAD), 
                new FakeMachine(load:LOW_LOAD)
            ] }

        def loadBalancer = new LoadBalancer()

        farmStub.use {
            assert LOW_LOAD == loadBalancer.relay("").load
            assert loadBalancer.invokeAMethod()
       }

        farmStub.expect.verify()
    }


    // Mocks are 'demand' order execution dependent; Mock are usefull for
    //  protocol testing where order of invocation matters
    void testFarmMachinesWithMocks() {

        final LOW_LOAD = 5, HIGH_LOAD = 10

        def farmMock = new MockFor(Farm)

        farmMock.demand.getMachines { [ 
                new FakeMachine(load:HIGH_LOAD), 
                new FakeMachine(load:HIGH_LOAD), 
                new FakeMachine(load:HIGH_LOAD), 
                new FakeMachine(load:HIGH_LOAD), 
                new FakeMachine(load:HIGH_LOAD), 
                new FakeMachine(load:LOW_LOAD)
            ] }

        farmMock.demand.aMethod { true }

        def loadBalancer = new LoadBalancer()

        farmMock.use {
            assert LOW_LOAD == loadBalancer.relay("").load
            assert loadBalancer.invokeAMethod()
        }

    }



/*
    void testFive() {

        def view = [
            getHelloClickable:{ -> 
                println 'getHelloClickable'; 
                return new MockFor(HasClickHandlers) as HasClickHandlers
            },
            getNoNameClickable:{ -> 
                println 'getNoNameClickable'; 
                return new MockFor(HasClickHandlers) as HasClickHandlers
            }
        ] as HelloWorldPresenter.View

        def model = [
            fetchGreeting:{ x -> println 'fetchGreeting'}
        ] as HelloWorldPresenter.Model


        //def presenter = new HelloWorldPresenter(model, view)
    }
*/

}


//===  Classes under Test =============================================================================


class TranslationService {
    String convert(String key) {
        return key + '.test'
    }
}

class Person {
    String first, last
}

class Family {
    Person father, mother
    def nameOfMother() { "$mother.first $mother.last" }
    def nameOfFather() { "$father.first $father.last" }
}

class LoadBalancer {
    def relay(request, farm) {
        farm.machines.sort{it.load}[0].send(request)
    }

    def relay(request) {
        new Farm().getMachines().sort{it.load}[0].send(request)
    }

    // Used to show order dependency for Mocks vs Stubs
    def invokeAMethod(){
        new Farm().aMethod()
    }

}

class Farm {
    def getMachines() {
        // Expensive polling operation or cached result
        return []
     }

     def aMethod() {return false}
}

//=== Classes create for testing purposes ===========================================================


// A 'Fake' class simulates a real classes behavior; Here we need to establish a load 
//   for testing purposes and provide a 'send' method which does nothing.  

class FakeMachine {
    def load
    def send(request) {return this}
}

