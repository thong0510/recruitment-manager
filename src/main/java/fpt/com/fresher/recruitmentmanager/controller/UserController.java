package fpt.com.fresher.recruitmentmanager.controller;

import fpt.com.fresher.recruitmentmanager.object.filter.UserFilter;
import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import fpt.com.fresher.recruitmentmanager.object.model.Sorting;
import fpt.com.fresher.recruitmentmanager.object.request.UserRequest;
import fpt.com.fresher.recruitmentmanager.object.response.UserResponse;
import fpt.com.fresher.recruitmentmanager.service.interfaces.UserService;
import fpt.com.fresher.recruitmentmanager.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/list-user")
    public String listUser(Model model,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "") String search) {
        Sorting sorting = new Sorting("userName", true);
        Pagination pagination = new Pagination(page - 1, 10, sorting);

        search = search.trim();
        UserFilter filter = UserFilter.builder()
                .pagination(pagination)
                .userName(search)
                .email(search)
                .fullName(search)
                .phone(search)
                .build();

        Page<UserResponse> userResponses = userService.getAllUser(filter);

        model.addAttribute("listUser", userResponses);
        model.addAttribute("filter", filter);
        model.addAttribute("user", new UserRequest());

        return "admin/listUser";
    }

    @PostMapping("/admin/create-user")
    public String createUser(@ModelAttribute(name = "user") @Valid UserRequest userRequest,
                                  HttpServletRequest request) {
        String targetUrl = request.getHeader("Referer");

        userService.createUser(userRequest);
        return "redirect:" + targetUrl;
    }

    @GetMapping("/admin/update-user")
    public String updateUser(Model model,
                                   @RequestParam(name = "id") String userName,
                                   HttpServletRequest request) {
        UserResponse userResponse = userService.findOne(userName);

        model.addAttribute("user", userResponse);

        String targetUrl = request.getHeader("Referer");

        if (!ObjectUtils.isEmpty(targetUrl))
            SessionUtils.putValue(request, "Referer", targetUrl, 3600);

        return "/admin/EditUser";
    }

    @PostMapping("/admin/update-user")
    public String updateUser(@ModelAttribute(name = "user") @Valid UserRequest userRequest,
                                   HttpServletRequest request) throws IOException {

        userService.updateUser(userRequest);

        return "redirect:" + SessionUtils.getValue(request, "Referer");
    }

    @GetMapping("/admin/delete-user")
    public String deleteUser(Model model, @RequestParam(required = false, name = "id") String userName) {

        userService.deleteUser(userName);

        return "redirect:/admin/list-user";
    }




}
