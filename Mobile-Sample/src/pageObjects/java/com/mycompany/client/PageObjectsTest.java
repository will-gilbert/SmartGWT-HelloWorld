package com.mycompany.client;


// Selenide
import com.codeborne.selenide.Selenide;


// JUnit 4.x testing
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

// @Ignore
public class PageObjectsTest {

    HomePage homePage;

    @BeforeClass
    public static void launchWebBrowser()  {
        Selenide.open("index.html");
    }

    @Before
    public void instanceHomePage()  {

        if ( homePage == null ) {
            homePage = new HomePageImpl();
        }
    }


    @Test
    public void homePageColors() {
        assertEquals("blue", homePage.getBackgroundColor("blue"));
        assertEquals("red", homePage.getBackgroundColor("red"));
        assertEquals("yellow", homePage.getBackgroundColor("yellow"));
        assertEquals("green", homePage.getBackgroundColor("green"));
        assertEquals("gray", homePage.getBackgroundColor("gray"));
        assertEquals("white", homePage.getBackgroundColor("white"));
        assertEquals("black", homePage.getBackgroundColor("black"));
        assertEquals("pink", homePage.getBackgroundColor("pink"));
        assertEquals("brown", homePage.getBackgroundColor("brown"));
    }

    @Test
    public void selectBlueBaseball() {
        SportsPage sportsPage =  homePage.selectColor("blue");
        assertNotNull(sportsPage);

        Dialog dialog = sportsPage.selectSport("Baseball");
        assertEquals("Do you like Baseball?", dialog.getTitle());

        dialog.selectYes();
        assertFalse(dialog.isDisplayed());

        sportsPage.backNavigationAction();
    }

    @Test
    public void selectRedFootball() {
        SportsPage sportsPage =  homePage.selectColor("red");
        assertNotNull(sportsPage);

        Dialog dialog = sportsPage.selectSport("Football");
        assertEquals("Do you like Football?", dialog.getTitle());

        dialog.selectNo();
        assertFalse(dialog.isDisplayed());
        
        sportsPage.backNavigationAction();

    }

}
