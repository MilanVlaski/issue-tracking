package com.akimi.issue_tracking;


import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.domain.AppPatchingTest;
import com.akimi.issue_tracking.integration.BaseIntegrationTest;
import com.akimi.issue_tracking.problem.MyProblemRepository;
import com.akimi.issue_tracking.problem.Problem;
import com.akimi.issue_tracking.problem.ProblemProcessing;
import com.akimi.issue_tracking.problem.ProblemState;
import com.akimi.issue_tracking.problem.dto.ProblemReport;
import io.cucumber.java.After;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestEntityManager
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class ProblemTableTest extends BaseIntegrationTest {

    @LocalServerPort
    protected int port;

    @Autowired
    ProblemProcessing problemProcessing;

    @MockBean
    MyProblemRepository problemRepository;

    @Test
    public void when_i_change_the_problem_state_filter_it_reloads_the_page_filtering_the_problems() {
        var user = new User("Mista", "email", "123", LocalDate.now(), "`123sdf");
        var app = new Application("Wa", "1.2.3", "Good.", LocalDate.now(), "lol");
        // imagine if this did no persistence, then that would be great
        var reportedProblem = problemProcessing.report(new ProblemReport("Wwa", "minga\nmono\ndesne"), app, user);

        var solvedProblem = reportedProblem.copy();
        solvedProblem.setState(ProblemState.SOLVED);

        var requestedState = "Reported";
        when(problemRepository.findAll()).thenReturn(List.of(reportedProblem, solvedProblem));
        when(problemRepository.findByState(requestedState)).thenReturn(List.of(reportedProblem));


        driver.get(homepage(port) + "/engineer/problems");
        inputEngineerEmailAndPassword();

        assertEquals(2, driver.findElements(By.className("problem")).size());

        new Select(
                driver.findElement(By.name("state"))
        ).selectByValue(requestedState);

        var filteredProblems = driver.findElements(By.className("problem"));
        assertEquals(1, filteredProblems.size());
        assertElementContainingTextExists(requestedState);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
