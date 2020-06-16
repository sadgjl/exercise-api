package cucumber.Options;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
		features = "src\\test\\java\\features",
		plugin ="html:target/jsonReports/cucumber-report.html",
		glue = {"stepDefinitions"},
		tags = "@ValidateResponse"
)
public class TestRunner extends AbstractTestNGCucumberTests {

}
//@RunWith(Cucumber.class)

