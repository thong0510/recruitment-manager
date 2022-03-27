package fpt.com.fresher.recruitmentmanager.controller;

import fpt.com.fresher.recruitmentmanager.object.entity.Users;
import fpt.com.fresher.recruitmentmanager.object.filter.CategoryFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.QuizFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.CategoryMapper;
import fpt.com.fresher.recruitmentmanager.object.mapper.QuizMapper;
import fpt.com.fresher.recruitmentmanager.object.mapper.UserQuizMapper;
import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import fpt.com.fresher.recruitmentmanager.object.response.CategoryResponse;
import fpt.com.fresher.recruitmentmanager.object.response.QuizResponse;
import fpt.com.fresher.recruitmentmanager.object.response.UserQuizResponse;
import fpt.com.fresher.recruitmentmanager.service.CategoryServiceImpl;
import fpt.com.fresher.recruitmentmanager.service.QuizServiceImpl;
import fpt.com.fresher.recruitmentmanager.service.UserQuizHistoryService;
import fpt.com.fresher.recruitmentmanager.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CategoryServiceImpl categoryService;
    private final QuizServiceImpl quizService;
    private final CategoryMapper categoryMapper;
    private final QuizMapper quizMapper;
    private final UserQuizMapper userQuizMapper;
    private final UserQuizHistoryService userQuizHistoryService;

    @GetMapping("home")
    public String homePage(Model model) {

        List<CategoryResponse> categoryList = categoryService.findAllCategories().stream().map(categoryMapper::entityToCategoryResponse).collect(Collectors.toList());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof Users) {
            List<UserQuizResponse> quizRecentList = userQuizHistoryService.findQuizRecent(authentication.getName()).stream().map(userQuizMapper::entityToUserQuizResponse).collect(Collectors.toList());
            model.addAttribute("quizRecentList", quizRecentList);
        }
        model.addAttribute("listC", categoryList);

        return "common/Home";
    }

    @GetMapping("/quiz")
    public String showPage(@RequestParam String category,
                           Model model) {

        Pagination pagination = new Pagination();
        pagination.setPageNumber(0);
        pagination.setPageSize(6);
        QuizFilter quizFilter = QuizFilter.builder()
                .categoryName(category)
                .pagination(pagination)
                .build();

        List<QuizResponse> list = quizService.findAllQuizzes(quizFilter).map(quizMapper::entityToQuizResponse).stream().toList();
        model.addAttribute("listQuizzes", list);

        return "quiz/ViewQuiz";
    }
}
