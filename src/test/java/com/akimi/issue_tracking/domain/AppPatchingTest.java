package com.akimi.issue_tracking.domain;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.problem.Action;
import com.akimi.issue_tracking.problem.Problem;
import com.akimi.issue_tracking.problem.engineer.Engineer;
import com.akimi.issue_tracking.problem.engineer.Patch;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppPatchingTest {

    private final String oldVersion = "1.2.0";
    private final Application brokenApp = new Application("Test", oldVersion, "Great!",
            LocalDate.now(), "url");

    private final Problem problem = new Problem("Won't load", brokenApp, new User(),
            List.of(new Action(1, "bla")));
    private final Engineer engineer = new Engineer("Marko", "Bachelor of CS",
            LocalDate.now(), 2500.00, "mik@email.com",
            "passmeby123");
    private final Patch patch = new Patch("Talk",
            new BigDecimal(100));

    @Test
    public void engineer_cant_patch_a_problem_they_are_not_assigned_to() {
        assertThrows(IllegalStateException.class, () -> {
            engineer.patchProblem(patch, problem);
        });
    }

    @Test
    public void when_engineer_patches_the_problem_they_are_assigned_to_a_new_application_with_incremented_version_is_created() {
        problem.assignEngineer(engineer);
        var newApp = engineer.patchProblem(patch, problem);
        assertEquals("1.2.1", newApp.getVersion());
        assertTrue(newApp.equalsExceptVersion(brokenApp));
    }
}
