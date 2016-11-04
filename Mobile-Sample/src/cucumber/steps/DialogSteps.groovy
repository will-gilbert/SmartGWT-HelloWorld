this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

import cucumber.api.PendingException


Then(~/a modal dialog is displayed/) { ->
    assert dialog
    assert dialog.isDisplayed() == true
}

When(~/the user clicks 'Yes' in the dialog/) { ->
    dialog.selectYes()
}

When(~/the user clicks 'No' in the dialog/) { ->
    dialog.selectNo()
}

When(~/the user clicks outside of the dialog/) { ->
    dialog.selectOutside()
}

Then(~/the dialog is dismissed/) { ->
    assert dialog.isDisplayed() == false
}

