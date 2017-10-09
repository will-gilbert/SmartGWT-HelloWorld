package glue

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    features='src/bdd/resources/Login.feature',
    glue=[
    	'src/bdd/steps/LoginSteps.groovy'
    ],
    tags=['~@Skip']

)

public class LoginTest {

	static {System.out.println("LoginTest");}


}
