package com.akimi.issue_tracking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.problem.Problem;
import com.akimi.issue_tracking.problem.dto.ProblemReport;

@SpringBootTest
public class ProblemProcessingTest {

    @Test
    public void Processes_a_description_string_with_line_breaks_into_ordered_actions() {
        var application = new Application()
                .setName("Wow")
		.setVersion("1.0.0");
        var user = new User().setName("John").setEmail("john@example.com");

        final ProblemReport description = new ProblemReport("Description",
                "Bla bla\nAnd another bla\n");
        var problem = new Problem(description.description(), application, user, description.parseActions());

	assertEquals(2, problem.getActions().size());
    }

}
