package fpt.com.fresher.recruitmentmanager.controller;

import fpt.com.fresher.recruitmentmanager.object.filter.QuestionFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.QuizFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.CategoryMapper;
import fpt.com.fresher.recruitmentmanager.object.mapper.QuizMapper;
import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import fpt.com.fresher.recruitmentmanager.object.request.QuizRequest;
import fpt.com.fresher.recruitmentmanager.object.response.CategoryResponse;
import fpt.com.fresher.recruitmentmanager.object.response.QuizResponse;
import fpt.com.fresher.recruitmentmanager.service.CategoryServiceImpl;
import fpt.com.fresher.recruitmentmanager.service.QuizServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class QuizController {

    private final QuizServiceImpl quizService;
    private final CategoryServiceImpl categoryService;
    private final QuizMapper quizMapper;
    private final CategoryMapper categoryMapper;

    @GetMapping("hr/list-quiz")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAll(Model model,
                         @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "") String search,
                         @RequestParam(defaultValue = "") String category,
                         @RequestParam(defaultValue = "") String status,
                         @RequestParam Optional<String> successMessage,
                         @RequestParam Optional<String> errorMessage) {

        Pagination pagination = new Pagination();
        pagination.setPageNumber(page - 1);
        pagination.setPageSize(5);
        QuizFilter quizFilter = QuizFilter.builder()
                                            .categoryName(category)
                                            .status(status)
                                            .title(search)
                                            .pagination(pagination)
                                            .build();

        Page<QuizResponse> list = quizService.findAllQuizzes(quizFilter).map(quizMapper::entityToQuizResponse);
        List<CategoryResponse> listCategory = categoryService.findAllCategories().stream().map(categoryMapper::entityToCategoryResponse).collect(Collectors.toList());
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("listQuiz", list);
        model.addAttribute("categoryFilter", category);
        model.addAttribute("statusFilter", status);
        model.addAttribute("errorMessage", errorMessage.orElse(null));
        model.addAttribute("successMessage", successMessage.orElse(null));

        return "hr/ListQuizManage";
    }

    @GetMapping("hr/create-quiz")
    @PreAuthorize("isAuthenticated()")
    public String newQuiz(Model model) {
        List<CategoryResponse> listCategory = categoryService.findAllCategories().stream().map(categoryMapper::entityToCategoryResponse).collect(Collectors.toList());
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("newQuiz", new QuizRequest());
        return "hr/FormQuizManage";
    }

    @GetMapping("hr/update-quiz/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getQuiz(@PathVariable Long id,
                          Model model) {

        QuizResponse quiz = quizMapper.entityToQuizResponse(quizService.findQuizById(id));
        List<CategoryResponse> listCategory = categoryService.findAllCategories().stream().map(categoryMapper::entityToCategoryResponse).collect(Collectors.toList());

        QuizRequest quizRequest = new QuizRequest(quiz.getTitle(), quiz.getDescription(), quiz.getCategoryId());
        model.addAttribute("quizInfo", quiz);
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("quiz", quizRequest);

        return "hr/FormQuizManage";
    }

    @PostMapping("hr/update-quiz/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateQuiz(@PathVariable long id,
                             @ModelAttribute @Valid QuizRequest quiz,
                             RedirectAttributes redirectAttr) {
        try {
            quizService.updateQuiz(id, quiz);
            redirectAttr.addAttribute("successMessage", "Update quiz successfully");
            return "redirect:/hr/list-quiz";
        } catch (Exception e) {
            redirectAttr.addAttribute("errorMessage", "Something wrong, please try again");
            return "redirect:/hr/list-quiz" + id;
        }
    }

    @GetMapping("hr/delete-quiz/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteQuiz(@PathVariable long id,
                             RedirectAttributes redirectAttr) {
        quizService.deleteQuiz(id);
        redirectAttr.addAttribute("successMessage", "Delete quiz successfully");
        return "redirect:/hr/list-quiz";
    }

    @PostMapping("hr/create-quiz")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createQuiz(@ModelAttribute @Valid QuizRequest newQuiz,
                             RedirectAttributes redirect) {
        try {
            quizService.createQuiz(newQuiz);
            redirect.addAttribute("successMessage", "Create quiz successfully");
        } catch (Exception e) {
            redirect.addAttribute("errorMessage", "Something wrong, please try again");
            return "redirect:/hr/list-quiz";
        }
        return "redirect:/hr/list-quiz";
    }
}
