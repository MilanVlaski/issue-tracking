package com.akimi.issue_tracking.domain;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.problem.Action;
import com.akimi.issue_tracking.problem.Problem;
import com.akimi.issue_tracking.problem.engineer.Engineer;
import com.akimi.issue_tracking.problem.engineer.Patch;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AppPatchingTest {

    @Test
    public void engineer_cant_patch_a_problem_they_are_not_assigned_to() {
        var brokenApp = new Application("Test", "1.2", "Great!",
                LocalDate.now(), "url");
        var engineer = new Engineer("Marko", "Bachelor of CS",
                LocalDate.now(), 2500.00, "mik@email.com",
                "passmeby123");
        var problem = new Problem("Won't load", brokenApp, new User(),
                List.of(new Action(1, "bla")));

        Assertions.assertThrows(IllegalStateException.class, () -> {
            engineer.patchProblem(new Patch("Talk",
                    new BigDecimal(100)), problem);
        });
    }
}
