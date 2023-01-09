package testrunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "C:\\Praveena\\SeleniumTableCucumber\\SeleniumTableCucumber\\src\\test\\java\\feature",

		glue = { "stepdef" }, plugin = { "pretty", "html:target/html",
				"json:taget/report.json" }, monochrome = true, dryRun = true, strict = true)

public class TestRunner {

}
