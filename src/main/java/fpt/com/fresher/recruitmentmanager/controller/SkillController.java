package fpt.com.fresher.recruitmentmanager.controller;

import fpt.com.fresher.recruitmentmanager.object.filter.SkillFilter;
import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import fpt.com.fresher.recruitmentmanager.object.model.Sorting;
import fpt.com.fresher.recruitmentmanager.object.request.SkillRequest;
import fpt.com.fresher.recruitmentmanager.object.response.SkillResponse;
import fpt.com.fresher.recruitmentmanager.service.interfaces.SkillService;
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
public class SkillController {

    @Autowired
    private SkillService skillService;

    @GetMapping("/hr/list-skill")
    public String listSkill(Model model,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "") String search) {
        Sorting sorting = new Sorting("skillId", true);
        Pagination pagination = new Pagination(page - 1, 10, sorting);

        search = search.trim();
        SkillFilter filter = SkillFilter.builder()
                .pagination(pagination)
                .skillName(search)
                .description(search)
                .build();

        Page<SkillResponse> skillResponses = skillService.getAllSkills(filter);

        model.addAttribute("listSkill", skillResponses);
        model.addAttribute("filter", filter);
        model.addAttribute("skill", new SkillRequest());

        return "hr/listSkill";
    }

    @PostMapping("/hr/create-skill")
    public String createSkill(@ModelAttribute(name = "skill") @Valid SkillRequest skillRequest,
                                  HttpServletRequest request) {
        String targetUrl = request.getHeader("Referer");

        skillService.createSkill(skillRequest);
        return "redirect:" + targetUrl;
    }

    @GetMapping("/hr/update-skill")
    public String updateSkill(Model model,
                                   @RequestParam(name = "id") Long id,
                                   HttpServletRequest request) {
        SkillResponse skillResponse = skillService.findOne(id);

        model.addAttribute("skill", skillResponse);

        String targetUrl = request.getHeader("Referer");

        if (!ObjectUtils.isEmpty(targetUrl))
            SessionUtils.putValue(request, "Referer", targetUrl, 3600);

        return "/hr/EditSkill";
    }

    @PostMapping("/hr/update-skill")
    public String updateSkill(@ModelAttribute(name = "skill") @Valid SkillRequest skillRequest,
                                   HttpServletRequest request) throws IOException {

        skillService.updateSkill(skillRequest);

        return "redirect:" + SessionUtils.getValue(request, "Referer");
    }

    @GetMapping("/hr/delete-skill")
    public String deleteSkill(Model model, @RequestParam(name = "id") Long id) {

        skillService.deleteSkill(id);

        return "redirect:/hr/list-skill";
    }




}
