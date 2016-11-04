
package com.mycompany.client;

import com.smartgwt.mobile.client.widgets.events.ClickHandler;
import com.smartgwt.mobile.client.widgets.ScrollablePanel;
import com.smartgwt.mobile.client.widgets.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.junit.client.GWTTestCase;

// JUnit 4.x testing
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gwtmockito.GwtMock;
import com.google.gwtmockito.GwtMockito;

import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.gwtmockito.WithClassesToStub;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.anyString;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@Ignore

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ScrollablePanel.class })
public class SportsPageTest  {

    @Test
    public void asPanel() throws Exception {

        // Given
        ScrollablePanel container = mock(ScrollablePanel.class);
        whenNew(ScrollablePanel.class)
            .withArguments(anyString())
            .thenReturn(container);
    
        List<String> sports = Collections.EMPTY_LIST;
        ClickHandler sportsClickHandler = mock(ClickHandler.class);

        // And the CUT
        SportsPage page = new SportsPage("blue", sports, sportsClickHandler);

        // When
        Panel panel = page.asPanel();

        // Then
        assertNotNull(panel);

    }
}
