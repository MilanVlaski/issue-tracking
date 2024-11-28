package com.akimi.issue_tracking;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.problem.Problem;
import com.akimi.issue_tracking.problem.ProblemProcessing;
import com.akimi.issue_tracking.problem.dto.ProblemReport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
public class ProblemProcessingTest {

    @Autowired
    TestEntityManager em;
    @Autowired
    ProblemProcessing problemProcessing;

    @Test
    public void Processes_a_description_string_with_line_breaks_into_ordered_actions() {
        var application = new Application()
                .setName("Wow")
                .setVersion("1.0");
        var user = new User().setName("John").setEmail("john@example.com");

        em.persist(application);
        em.persist(user);

        ProblemReport problemReport = new ProblemReport("Description" ,
                "Bla bla\nAnd another bla\n");
        problemProcessing.report(problemReport, application, user);

        var actions = em.find(Problem.class, 1L).getActions();
        assertEquals(2, actions.size());
    }

}
