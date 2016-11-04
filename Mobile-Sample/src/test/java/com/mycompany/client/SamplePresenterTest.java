
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


public class SamplePresenterTest  {

    @Test
    public void getView() {

        // Given
        SamplePresenter.Model model = mock(SamplePresenter.Model.class);
        SamplePresenter.View view = mock(SamplePresenter.View.class);
        SamplePresenter presenter = new SamplePresenter(view, model);

        // When
        presenter.getView();

        // Then
        verify(view).asCanvas();

    }
}
