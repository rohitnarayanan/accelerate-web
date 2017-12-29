package accelerate.cucumber.stepdefs;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import accelerate.AccelerateWebApp;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = AccelerateWebApp.class)
public abstract class StepDefs {

	protected ResultActions actions;

}
