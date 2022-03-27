package fpt.com.fresher.recruitmentmanager.controller;

import fpt.com.fresher.recruitmentmanager.object.filter.CategoryFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.QuizFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.CategoryMapper;
import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import fpt.com.fresher.recruitmentmanager.object.request.CategoryRequest;
import fpt.com.fresher.recruitmentmanager.object.response.CategoryResponse;
import fpt.com.fresher.recruitmentmanager.service.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping("hr/list-category")
    public String getAllCategories(Model model,
                                   @RequestParam(name = "search", required = false, defaultValue = "") String search,
                                   @RequestParam(name = "numberPages", required = false, defaultValue = "1") int numberPages) {

        Pagination pagination = new Pagination();
        pagination.setPageNumber(numberPages - 1);
        pagination.setPageSize(5);
        CategoryFilter categoryFilter = CategoryFilter.builder()
                .name(search)
                .pagination(pagination)
                .build();

        Page<CategoryResponse> list = categoryService.findAllCategories(categoryFilter).map(categoryMapper::entityToCategoryResponse);

        List<CategoryResponse> listOfCategory = list.getContent();

        long totalItems = list.getTotalElements();
        int totalPages = list.getTotalPages();

        model.addAttribute("listC", listOfCategory);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", numberPages);
        model.addAttribute("newCategory", new CategoryRequest());

        return "hr/ListCategoryManage";
    }

//    @PostMapping("create")
//    public String createCategory(@ModelAttribute @Valid CategoryRequest newCategory,
//                                 HttpServletRequest request,
//                                 HttpServletResponse response,
//                                 RedirectAttributes redirect) {
//
//        System.err.println("quiz1: " + newCategory);
//        AuthenticationResponse account = (AuthenticationResponse) request.getSession(true).getAttribute("account");
//        if (account != null && "ADMIN".equals(account.getUser().getRoleName())) {
//            if (!categoryService.createCategory(newCategory, request, response)) {
//                redirect.addAttribute("message", "Create category fail");
//            } else {
//                redirect.addAttribute("message", "Create category successfully");
//            }
//        }
//        return "ListCategoryManage";
//    }
}
