package com.akimi.issue_tracking;


import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.integration.BaseIntegrationTest;
import com.akimi.issue_tracking.problem.service.MyProblemRepository;
import com.akimi.issue_tracking.problem.Problem;
import com.akimi.issue_tracking.problem.service.ProblemProcessing;
import com.akimi.issue_tracking.problem.ProblemState;
import com.akimi.issue_tracking.problem.dto.ProblemReport;
import io.cucumber.java.After;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
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

    private Problem reportedProblem;
    private Problem solvedProblem;

    @BeforeEach
    public void createTwoProblems() {
        User user = new User("Mista", "email", "123", LocalDate.now(), "`123sdf");
        Application app = new Application("Wa", "1.2.3", "Good.", LocalDate.now(), "lol");
        reportedProblem = problemProcessing.report(new ProblemReport("Wwa", "minga\nmono\ndesne"), app, user);
        solvedProblem = reportedProblem.copy();
        solvedProblem.setState(ProblemState.SOLVED);
    }

    @Test
    public void when_i_change_the_problem_state_filter_it_reloads_the_page_filtering_the_problems() {
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

    @Test
    public void when_I_change_the_problem_state_to_something_it_changes_accordingly() {
        when(problemRepository.findAll()).thenReturn(List.of(reportedProblem, solvedProblem));
        when(problemRepository.findById(Long.valueOf(reportedProblem.getId()))).thenReturn(Optional.of(reportedProblem));

        driver.get(homepage(port) + "/engineer/problems");
        inputEngineerEmailAndPassword();


        var elements = driver.findElements(By.cssSelector("select[aria-label='Change Problem State']"));
        var changedSelect = new Select(elementWithValue("Reported", elements));

        var solved = "Solved";
        changedSelect.selectByValue(solved);
        assertEquals(solved, changedSelect.getFirstSelectedOption().getText());

        verify(problemRepository).save(reportedProblem);
    }

    private WebElement elementWithValue(String value, List<WebElement> elements) {
        for (WebElement element : elements) {
            if(element.getAttribute("value").equals(value)) {
                return element;
            }
        }
        return null;
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
