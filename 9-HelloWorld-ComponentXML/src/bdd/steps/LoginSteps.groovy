this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)


import demo.pageobjects.LoginPage

// Selenide & Cucumber
import com.codeborne.selenide.Selenide
import cucumber.api.PendingException

import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;
import static com.codeborne.selenide.Screenshots.takeScreenShot;


Given(~/^I open the application$/) { ->

    clearBrowserCache()

    // Shutdown the browser and reopen in order run a clean test
    Selenide.close()
    Selenide.open('http://localhost:9090/9-HelloWorld-ComponentXML')

    loginPage = new LoginPage()

    // Die early if this page is not visible
    assert loginPage.isVisible()
}

When(~/^I click on the 'No Name' button$/) { ->
    loginPage.clickNoNameButton()
}

When(~/^I click on the 'Name' button$/) { ->
    loginPage.clickHelloButton()
}

Then(~/^I see a dialog with the values from the server$/) { ->
    assert loginPage.isAlertDialogVisible()
}

When(~/^I click the dial 'OK' button$/) { ->
    loginPage.dismissDialogWithOk()
}

Then(~/^the dialog is no longer visible$/) { ->
    assert loginPage.isAlertDialogVisible() == false
}