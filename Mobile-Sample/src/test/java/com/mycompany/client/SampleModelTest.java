
package com.mycompany.client;


// JUnit 4.x testing
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;


public class SampleModelTest  {

    // Class under test
    SampleModel model;

    @Before
    public void createCUT() {

        // Given
        model = new SampleModel();
    }

    @Test
    public void getcolors() {

        // When
        List<String> colors = model.getColors();

        // Then
        assertNotNull(colors);
        assertEquals(9, colors.size());

    }

    @Test
    public void getSports() {

        // When
        List<String> sports = model.getSports();

        // Then
        assertNotNull(sports);
        assertEquals(5, sports.size());

    }

}
