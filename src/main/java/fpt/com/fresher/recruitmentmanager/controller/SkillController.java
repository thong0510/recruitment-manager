package fpt.com.fresher.recruitmentmanager.controller;

import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import fpt.com.fresher.recruitmentmanager.object.filter.SkillFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.SkillMapper;
import fpt.com.fresher.recruitmentmanager.object.request.SkillRequest;
import fpt.com.fresher.recruitmentmanager.object.response.SkillResponse;
import fpt.com.fresher.recruitmentmanager.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SkillController {

    @Autowired
    SkillService skillService;

    @Autowired
    SkillMapper skillMapper;

    @GetMapping("/hr/list-skill")
    public String listSkill(Model model) {

        SkillFilter skillFilter = new SkillFilter();
        Page<Skills> listSkill = skillService.getAllSkills(skillFilter);
        List<SkillResponse> listSkillResponse = listSkill.getContent().stream().map(skillMapper::entityToSkillResponse).collect(Collectors.toList());

        model.addAttribute("skills", listSkillResponse);
        return "hr/listSkill";
    }

    @GetMapping("/hr/detail-skill")
    public String detailSkill(Model model, @RequestParam(name = "id") Long id) {
        Skills skill = skillService.findOne(id);
        SkillResponse skillResponse = skillMapper.entityToSkillResponse(skill);
        model.addAttribute("skill", skillResponse);
        return "hr/skillDetail";
    }

    @GetMapping("/hr/edit-skill")
    public String edit(Model model, @RequestParam Long id) {
        Skills skill = skillService.findOne(id);
        SkillResponse skillResponse = skillMapper.entityToSkillResponse(skill);
        model.addAttribute("skill", skillResponse);
        return "hr/editSkill";
    }

    @PostMapping("/hr/edit-skill")
    public String edit(@ModelAttribute SkillRequest skill) {
        skillService.updateSkill(skill);
        return "redirect:/hr/list-skill";
    }

    @GetMapping("/hr/create-skill")
    public String createSkill(Model model) {
        SkillRequest skillRequest = new SkillRequest();
        model.addAttribute("skill", skillRequest);
        return "hr/createSkill";
    }

    @PostMapping("/hr/create-skill")
    public String createSkill(@ModelAttribute SkillRequest skill) {
        skillService.createSkill(skill);
        return "redirect:/hr/list-skill";
    }

    @GetMapping("/hr/delete-skill")
    public String delete(@RequestParam Long id) {

        skillService.deleteSkill(id);
        return "redirect:/hr/list-skill";

    }
}
