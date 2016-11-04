this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

import cucumber.api.PendingException


Then(~/the app will navigate to the 'Sports' page/) { ->
    assert sportsPage.isDisplayed()
}

When(~/the '(.+)' sport button is clicked/) { sport ->
    dialog = sportsPage.selectSport(sport)
}

Then(~/the Sports page is accessible/) { ->
    assert sportsPage.isDisplayed()
}

When(~/the 'Home' navigation bar button is clicked/) { ->
    sportsPage.backNavigationAction()
}


