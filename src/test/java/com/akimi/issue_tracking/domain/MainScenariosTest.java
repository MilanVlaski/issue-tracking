package com.akimi.issue_tracking.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.application.purchase.Purchase;
import com.akimi.issue_tracking.application.purchase.SupportType;
import com.akimi.issue_tracking.problem.Problem;
import com.akimi.issue_tracking.problem.dto.ProblemReport;
import com.akimi.issue_tracking.problem.engineer.Answer;
import com.akimi.issue_tracking.problem.engineer.Engineer;

public class MainScenariosTest {

    User user = new User("Josh Doe", "password", "Email@email.com", LocalDate.of(2025, 1, 5), "Just send me location",
	    "123");
    Application app = new Application("appName", "1.1.0", "Great!", LocalDate.now(), "url");
    SupportType support = new SupportType("1", "Forever", BigDecimal.valueOf(12.2));
    Engineer engineer = new Engineer("Jame Bon", "nothing", LocalDate.of(2024, 1, 1), 250.2, "as@mail.com", "password");

    @Test
    public void Users_problem_gets_answered() {
//	user.purchase(app, supportType);
//	user.reportProblemWithApp(problemReport, app);

	var purchase = new Purchase(user, app, support);

	var problemReport = new ProblemReport("App sucks", "Bla\nBla\n");
	var problem = new Problem(problemReport, app, user);

	var answer = new Answer("Hold on tight!");
	engineer.answer(problem, answer);

	var myProblem = user.getProblems().iterator().next();
	var receivedAnswer = myProblem.getAnswers().iterator().next();

	assertEquals(answer.getDescription(), receivedAnswer.getDescription());
    }

}
