package com.akimi.issue_tracking.problem.problemPages;

import com.akimi.issue_tracking.problem.Problem;
import com.akimi.issue_tracking.problem.ProblemState;
import com.akimi.issue_tracking.problem.dto.AnswerDto;
import com.akimi.issue_tracking.problem.dto.PatchUpload;
import com.akimi.issue_tracking.problem.dto.ProblemDto;
import com.akimi.issue_tracking.problem.dto.ProblemWithPatches;
import com.akimi.issue_tracking.problem.engineer.Engineer;
import com.akimi.issue_tracking.problem.service.ProblemRepository;
import com.akimi.issue_tracking.problem.service.ProblemProcessing;
import com.akimi.issue_tracking.security.CurrentUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class ProblemPages {

    private final CurrentUser currentLogin;

    private final ProblemProcessing problemProcessing;

    private final ProblemRepository problemRepository;

    @PersistenceContext
    private EntityManager em;

    public ProblemPages(CurrentUser currentLogin, ProblemProcessing problemProcessing, ProblemRepository problemRepository) {
        this.currentLogin = currentLogin;
        this.problemProcessing = problemProcessing;
        this.problemRepository = problemRepository;
    }

    @GetMapping("/engineer/problems")
    public String index(Model model, @RequestParam(required = false) String state) {
        handleShowProblems(model, state);
        return "engineerProblems";
    }

    @GetMapping("/engineer/problems/mine")
    public String mine(Model model, @RequestParam(required = false) String state) {
        handleShowProblems(model, state);
        return "engineerProblemsOwn";
    }

    private void handleShowProblems(Model model, String state) {
        List<ProblemDto> problems;
        if (!(state == null || state.isEmpty())) {
            var stateEnum = ProblemState.valueOfIgnoreCase(state);
            problems = problemRepository.findAllBelongingToEngineerByState(currentLogin.engineer(), stateEnum);
            model.addAttribute("state", stateEnum);
        } else {
            problems = problemRepository.findAllBelongingTo(currentLogin.engineer());
        }
        model.addAttribute("problems", problems);
    }

    @GetMapping("/problems")
    public String problems(Model model) {
        var user = currentLogin.user();
        var problemsWithPatches = em.createQuery(
                        "SELECT p FROM Problem p " +
                                "LEFT JOIN FETCH p.problemSolvers ps " +
                                "LEFT JOIN FETCH ps.patches patch " +
                                "WHERE p.user = :user",
                        Problem.class
                )
                .setParameter("user", user)
                .getResultList();

        model.addAttribute("problemDtos", mapProblemsToDTOs(problemsWithPatches));
        model.addAttribute("userRole", "USER");

        return "problemsAndSolutions";
    }

    @GetMapping("/engineer/problems/solutions")
    public String problemsAndSolutions(Model model) {
        TypedQuery<Problem> em1 = em.createQuery("SELECT p FROM Problem p " +
                "LEFT JOIN FETCH p.problemSolvers ps " +
                "LEFT JOIN FETCH ps.patches patch ", Problem.class);
        model.addAttribute("problemDtos", mapProblemsToDTOs(em1.getResultList()));
        model.addAttribute("userRole", "ENGINEER");
        return "problemsAndSolutions";
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
        model.addAttribute("problemStates", Arrays.stream(ProblemState.values())
                .map(ProblemState::getEnglish));
        return "answerProblem";
    }

    @PostMapping("/engineer/problems/{problemId}/answer")
    public String answerProblemPost(@PathVariable String problemId,
                                    @ModelAttribute AnswerDto answer, HttpServletRequest request,
                                    RedirectAttributes redirectAttributes) {
        var problem = em.find(Problem.class, problemId);
        problemProcessing.answerProblem(problem,
                answer.toEntity(),
                currentLogin.engineer(),
                ProblemState.valueOfIgnoreCase(answer.problemState())
        );
        redirectAttributes.addFlashAttribute("answerStatus", "success");
        return redirectToReferer(request);
    }

    @Transactional
    @PostMapping("/engineer/problems/{problemId}/assignEngineer")
    public String assignEngineer(@PathVariable String problemId,
                                 HttpServletRequest request) {
        Engineer engineer = currentLogin.engineer();
        var problem = em.find(Problem.class, problemId);
        problem.assignEngineer(engineer);
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
    public String uploadPatch(@PathVariable String problemId,
                              @ModelAttribute PatchUpload patchUpload, HttpServletRequest request,
                              RedirectAttributes redirectAttributes) {

        var problem = em.find(Problem.class, problemId);
        var newApp = problemProcessing.patchProblem(problem,
                patchUpload.toEntity(), currentLogin.engineer());
        redirectAttributes.addFlashAttribute("newApp", newApp);
        return redirectToReferer(request);
    }

    public static String redirectToReferer(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        return referer != null ? "redirect:" + referer : "redirect:/default";
    }

}
