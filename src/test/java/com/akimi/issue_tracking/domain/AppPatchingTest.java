package com.akimi.issue_tracking.domain;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.Purchase;
import com.akimi.issue_tracking.application.SupportType;
import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.problem.Action;
import com.akimi.issue_tracking.problem.AppDistribution;
import com.akimi.issue_tracking.problem.ApplicationOwners;
import com.akimi.issue_tracking.problem.Problem;
import com.akimi.issue_tracking.problem.engineer.Engineer;
import com.akimi.issue_tracking.problem.engineer.Patch;
import com.akimi.issue_tracking.problem.engineer.ProblemSolver;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AppPatchingTest {

    private final String version = "1.2.0";
    private final String appName = "Test";
    private final Application brokenApp = new Application(appName, version, "Great!",
            LocalDate.now(), "url");

    private final Problem problem = new Problem("Won't load", brokenApp, new User(),
            List.of(new Action(1, "bla")));
    private final Engineer engineer = new Engineer("Marko", "Bachelor of CS",
            LocalDate.now(), 2500.00, "mik@email.com",
            "passmeby123");
    private final Patch patch = new Patch("Talk",
            new BigDecimal(100));

    private final ApplicationOwners applicationOwners = mock(ApplicationOwners.class);
    private final AppDistribution appDistribution = new AppDistribution(applicationOwners);

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

    @Test
    public void when_an_application_is_patched_then_the_user_who_owns_the_broken_app_gets_the_patched_app_for_free() {
        var user = new User("Milan", "john@doe.com", "password",
                LocalDate.of(1989, 12, 12), "Kazakhstan");
        var purchase = new Purchase(user, brokenApp, new SupportType("1", "Bla", new BigDecimal("11.50")));
        var previousOwners = List.of(user);

        problem.assignEngineer(engineer);
        var newApp = engineer.patchProblem(patch, problem);

        // the variables passed here expose String coupling
        // The app name (or id) plus version should be a special class, with it's own
        // semantics, validation and so on.
        when(applicationOwners.withApplicationAndFeatureRelease(appName, newApp.getVersion()))
                .thenReturn(previousOwners);


        appDistribution.sendApplicationToPreviousUsers(newApp, purchase.getSupportType());
        assertTrue(user.ownsApplication(newApp));
    }
}
