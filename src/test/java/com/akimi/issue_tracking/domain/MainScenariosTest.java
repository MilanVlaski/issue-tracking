package com.akimi.issue_tracking.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.application.purchase.SupportType;
import com.akimi.issue_tracking.application.service.AppDistribution;
import com.akimi.issue_tracking.application.service.ApplicationOwners;
import com.akimi.issue_tracking.application.service.UserPurchaseInfo;
import com.akimi.issue_tracking.problem.PatchingService;
import com.akimi.issue_tracking.problem.ProblemState;
import com.akimi.issue_tracking.problem.dto.ProblemReport;
import com.akimi.issue_tracking.problem.engineer.Answer;
import com.akimi.issue_tracking.problem.engineer.Engineer;
import com.akimi.issue_tracking.problem.engineer.Patch;

public class MainScenariosTest {

    User user = new User("Josh Doe", "password", "Email@email.com", LocalDate.of(2025, 1, 5), "Just send me location",
	    "123");
    Application app = new Application("appName", "1.1.0", "Great!", LocalDate.now(), "url");
    SupportType support = new SupportType("1", "Forever", BigDecimal.valueOf(12.2));
    Engineer engineer = new Engineer("Jame Bon", "nothing", LocalDate.of(2024, 1, 1), 250.2, "as@mail.com", "password");
    ProblemReport problemReport = new ProblemReport("App sucks", "Bla\nBla\n");
    Answer answer = new Answer("Hold on tight!");

    @Test
    public void Users_problem_gets_answered() {
	user.purchase(app, support);

	var problem = user.reportProblemWithApp(problemReport, app);

	engineer.answer(problem, answer);

	var myProblem = user.getProblems().iterator().next();
	var receivedAnswer = myProblem.getAnswers().iterator().next();

	assertEquals(answer.getDescription(), receivedAnswer.getDescription());
    }

    @Test
    public void Users_problem_gets_patched() {
	ApplicationOwners mockAppOwners = Mockito.mock(ApplicationOwners.class);
	when(mockAppOwners.withApplicationAndMajorVersion(app.getName(), "1.1"))
		.thenReturn(List.of(new UserPurchaseInfo(user, support)));

	user.purchase(app, support);

	var problem = user.reportProblemWithApp(problemReport, app);
	problem.assignEngineer(engineer);

	var patch = new Patch("telephone", BigDecimal.valueOf(120));
	new PatchingService(engineer, patch, problem, new AppDistribution(mockAppOwners))
		.createPatchedApplicationAndDistributeItToPreviousUsers();

	var usersProblem = user.getProblems().iterator().next();
	assertEquals(ProblemState.SOLVED, usersProblem.getState());
	assertEquals(2, user.getPurchases().size());

	var iterator = user.getPurchases().iterator();
	iterator.next();
	var newApp = iterator.next().getApplication();
	assertEquals("1.1.1", newApp.getVersion());
    }

}
