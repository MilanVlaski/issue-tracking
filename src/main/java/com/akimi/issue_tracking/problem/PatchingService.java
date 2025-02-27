package com.akimi.issue_tracking.problem;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.service.AppDistribution;
import com.akimi.issue_tracking.problem.engineer.Engineer;
import com.akimi.issue_tracking.problem.engineer.Patch;

public class PatchingService {

    private Engineer engineer;
    private Patch patch;
    private Problem problem;
    private AppDistribution appDistribution;

    public PatchingService(Engineer engineer, Patch patch, Problem problem, AppDistribution appDistribution) {
	this.engineer = engineer;
	this.patch = patch;
	this.problem = problem;
	this.appDistribution = appDistribution;
    }

    public void createPatchedApplicationAndDistributeItToPreviousUsers() {
	Application newApp = engineer.patchProblem(patch, problem);
	appDistribution.sendApplicationToPreviousUsers(newApp);
    }

}
