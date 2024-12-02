package com.akimi.issue_tracking.problem;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.problem.dto.AnswerDto;
import com.akimi.issue_tracking.problem.dto.PatchUpload;
import com.akimi.issue_tracking.problem.dto.ProblemWithPatches;
import com.akimi.issue_tracking.problem.dto.ProblemReport;
import com.akimi.issue_tracking.security.CurrentUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProblemPages {

    @Autowired
    private CurrentUser currentLogin;

    @Autowired
    private ProblemProcessing problemProcessing;

    @PersistenceContext
    private EntityManager em;

    @GetMapping("/reportProblem")
    public String reportProblem(Model model) {
        var purchases = currentLogin.user().getPurchases();
        model.addAttribute("purchases", purchases);
        return "reportProblem";
    }

    @GetMapping("/application/{appId}/reportProblem")
    public String reportProblem() {
        return "describeProblem";
    }

    @PostMapping("/application/{appId}/reportProblem")
    public String reportProblemPost(@PathVariable String appId,
            @ModelAttribute ProblemReport problemReport,
            HttpServletRequest request
    ) {
        var application = em.find(Application.class, appId);
        var user = currentLogin.user();
        problemProcessing.report(problemReport, application, user);
        return redirectToReferer(request);
    }

    @GetMapping("/engineer/problems")
    public String index(Model model) {
        var problems = em.createQuery("select p from Problem p", Problem.class)
                         .getResultList();
        model.addAttribute("problems", problems);
        return "engineerProblems";
    }

    @GetMapping("/engineer/problems/mine")
    public String mine(Model model) {
        var currentEngineer = currentLogin.engineer();
        model.addAttribute(
                "problems",
                em.createQuery("select p from Problem p" +
                          " join p.engineers e where e.email = :email", Problem.class)
                  .setParameter("email", currentEngineer.getEmail())
                  .getResultList()
        );
        return "engineerProblemsOwn";
    }

    @GetMapping("/problems")
    public String problems(Model model) {
        var problems = em.createQuery(
                                 "select distinct p from Problem p where p.user = :user",
                                 Problem.class
                         )
                         .setParameter("user", currentLogin.user())
                         .getResultList();

        List<ProblemWithPatches> problemDTOs = mapProblemsToDTOs(problems);

        model.addAttribute("problemDtos", problemDTOs);
        return "userProblems";
    }

    public List<ProblemWithPatches> mapProblemsToDTOs(List<Problem> problems) {
        return problems.stream()
                       .map(ProblemWithPatches::new)
                       .toList();
    }

    @GetMapping("/engineer/problems/{problemId}")
    public String answerProblem(@PathVariable String problemId, Model model) {
        var problem = em.find(Problem.class, problemId);
        model.addAttribute("problem", problem);
        model.addAttribute("actions", problem.getActions());
        return "answerProblem";
    }

    @PostMapping("/engineer/problems/{problemId}/answer")
    public String answerProblemPost(@PathVariable String problemId,
            @ModelAttribute AnswerDto answer, HttpServletRequest request) {
        problemProcessing.solveProblem(em.find(Problem.class, problemId),
                answer.toEntity(),
                currentLogin.engineer()
        );
        return redirectToReferer(request);
    }

    @PostMapping("/engineer/problems/{problemId}/assignEngineer")
    public String assignEngineer(@PathVariable String problemId,
            HttpServletRequest request) {
        problemProcessing.assignEngineerToProblem(
                currentLogin.engineer(),
                em.find(Problem.class, problemId)
        );

        return redirectToReferer(request);
    }

    @GetMapping("/engineer/problems/{problemId}/uploadPatch")
    public String uploadPatchPage(Model model, @PathVariable String problemId) {
        var problem = em.find(Problem.class, problemId);
        model.addAttribute("problem", problem);
        model.addAttribute("application", problem.getApplication());
        return "uploadPatch";
    }

    @PostMapping("/engineer/problems/{problemId}/uploadPatch")
    public String uploadPatch(Model model, @PathVariable String problemId,
            @ModelAttribute PatchUpload patchUpload, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        var newApp = problemProcessing.patchProblem(em.find(Problem.class, problemId),
                patchUpload.toEntity(), currentLogin.engineer());
        redirectAttributes.addFlashAttribute("newApp", newApp);
        return redirectToReferer(request);
    }

    public static String redirectToReferer(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        return referer != null ? "redirect:" + referer : "redirect:/default";
    }

}
