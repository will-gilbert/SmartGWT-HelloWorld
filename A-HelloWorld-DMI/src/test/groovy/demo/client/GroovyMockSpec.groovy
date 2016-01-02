package demo.client


import spock.lang.Specification

class GroovyMockSpec extends Specification {
    
    def "Test adding a items to a Set vs a List"() {
        
        given:
            def list = [4, 6, 3, 2]
            def set = [4, 6, 3, 2] as Set
        
        when:
            list << 1 ; list << 4
            set << 1 ; set << 4

        then:
            set.size() == 5
            list.size() == 6

    }


    def "Buy tickets for a movie"() {

        given:
            def purchase = new Purchase("Lord of the Rings", 2)

            MovieTheater theater = Mock()
            theater.hasSeatsAvailable("Lord of the Rings", 2) >> true // Seats are always available for this movie

        when:
            purchase.attemptPurchase(theater)

        then:
            purchase.completed == true
            1 * theater.purchaseTicket("Lord of the Rings", 2)
    } 



    def "Cannot buy a ticket if the movie is sold out"() {

        given:
            def purchase = new Purchase("Lord of the rings", 2)
            MovieTheater theater = Mock()
            theater.hasSeatsAvailable(_, _) >> false // Seats are not available, anywhere

        when:
            purchase.attemptPurchase(theater)
    
        then:
            purchase.completed == false
            0 * theater.purchaseTicket(_, _)
    }

    def "On couples night tickets are sold in pairs"() {

        given:
            def purchaseTwo = new Purchase("Lord of the Rings", 2)
            def purchaseSingle = new Purchase("Spectre", 1)
            MovieTheater theater = Mock()
            theater.hasSeatsAvailable(_, _) >> true // Seats are available for all movies

        when:
            purchaseTwo.attemptPurchase(theater)
            purchaseSingle.attemptPurchase(theater)

        then:
            1 * theater.purchaseTicket(_, { it %2 == 0} ) // Tickets sold in pairs
            1 * theater.purchaseTicket(_, 1 )             // Single tickets
    }


}

//===  Classes under Test =============================================================================


interface MovieTheater {
    void purchaseTicket(movieTitle, numberOfTickets)
    boolean hasSeatsAvailable(movieTitle, numberOfTickets)
}

class Purchase {

    def movieTitle, numberOfTickets, completed = false

    Purchase(movieTitle, numberOfTickets) {
        this.movieTitle = movieTitle
        this.numberOfTickets = numberOfTickets
    }

    def attemptPurchase(theater) {
        if (theater.hasSeatsAvailable(movieTitle, numberOfTickets)) {
            theater.purchaseTicket(movieTitle, numberOfTickets)
            completed = true
        }
    } 
}