Feature: SmartGWT-Mobile Sample
    
    Scenario: Choose 'blue', navigate to the 'Sports' page; Select Baseball

        Given a running 'SmartGWT-mobile Sample' web application 

        When the color blue is clicked
            Then the app will navigate to the 'Sports' page

        When the 'Baseball' sport button is clicked
            Then a modal dialog is displayed

        When the user clicks outside of the dialog
            Then the dialog is dismissed
            And the Sports page is accessible

        When the 'Home' navigation bar button is clicked
            Then the application will return to the 'Home' page

    #===================================================================================
    
    Scenario: Choose 'red', navigate to the 'Sports' page; Select Football

        Given a running 'SmartGWT-mobile Sample' web application 

        When the color red is clicked
            Then the app will navigate to the 'Sports' page

        When the 'Football' sport button is clicked
            Then a modal dialog is displayed

        When the user clicks outside of the dialog
            Then the dialog is dismissed
            And the Sports page is accessible

        When the 'Home' navigation bar button is clicked
            Then the application will return to the 'Home' page

    #===================================================================================
    
    Scenario: Choose 'yellow', navigate to the 'Sports' page; Select Hockey

        Given a running 'SmartGWT-mobile Sample' web application 

        When the color yellow is clicked
            Then the app will navigate to the 'Sports' page

        When the 'Hockey' sport button is clicked
            Then a modal dialog is displayed

        When the user clicks outside of the dialog
            Then the dialog is dismissed
            And the Sports page is accessible

        When the 'Home' navigation bar button is clicked
            Then the application will return to the 'Home' page

    #===================================================================================

    Scenario: Choose 'blue', navigate to the 'Sports' page; Select Baseball, Football & Hockey

        Given a running 'SmartGWT-mobile Sample' web application 

        When the color blue is clicked
            Then the app will navigate to the 'Sports' page

        When the 'Baseball' sport button is clicked
            Then a modal dialog is displayed

        When the user clicks outside of the dialog
            Then the dialog is dismissed
            And the Sports page is accessible

        When the 'Football' sport button is clicked
            Then a modal dialog is displayed

        When the user clicks 'Yes' in the dialog
            Then the dialog is dismissed
            And the Sports page is accessible

        When the 'Hockey' sport button is clicked
            Then a modal dialog is displayed

        When the user clicks 'No' in the dialog
            Then the dialog is dismissed
            And the Sports page is accessible

        When the 'Home' navigation bar button is clicked
            Then the application will return to the 'Home' page
