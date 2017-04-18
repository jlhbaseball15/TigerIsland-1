package AcceptanceTest;

import cucumber.api.junit.Cucumber;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features={"test/AcceptanceTest"}
)
public class CukesRunner {}