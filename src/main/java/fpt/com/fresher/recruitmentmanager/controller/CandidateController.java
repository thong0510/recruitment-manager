package fpt.com.fresher.recruitmentmanager.controller;

import fpt.com.fresher.recruitmentmanager.object.contant.enums.CandidateStatus;
import fpt.com.fresher.recruitmentmanager.object.entity.Candidates;
import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import fpt.com.fresher.recruitmentmanager.object.filter.CandidateFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.SkillFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.CandidateMapper;
import fpt.com.fresher.recruitmentmanager.object.mapper.SkillMapper;
import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import fpt.com.fresher.recruitmentmanager.object.model.Sorting;
import fpt.com.fresher.recruitmentmanager.object.request.CandidateRequest;
import fpt.com.fresher.recruitmentmanager.object.response.CandidateResponse;
import fpt.com.fresher.recruitmentmanager.object.response.SkillResponse;
import fpt.com.fresher.recruitmentmanager.service.CandidateService;
import fpt.com.fresher.recruitmentmanager.service.SkillService;
import fpt.com.fresher.recruitmentmanager.utils.SessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;
    private final SkillService skillService;
    private final SkillMapper skillMapper;

    @GetMapping("/hr/list-potential-candidate")
    public String getCandidatesList(Model model,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "") String status,
                                    @RequestParam(defaultValue = "") String gender,
                                    @RequestParam(defaultValue = "") String search) {

        Sorting sorting = new Sorting("candidateId", true);
        Pagination pagination = new Pagination(page - 1, 4, sorting);

        search = search.trim();
        CandidateFilter filter = CandidateFilter.builder()
                                                .pagination(pagination)
                                                .status(status)
                                                .gender(gender)
                                                .candidateName(search)
                                                .email(search)
                                                .phone(search)
                                                .experience(search)
                                                .build();

        Page<CandidateResponse> listOfCandidateResponse = candidateService.getAllCandidates(filter);

        SkillFilter skillFilter = new SkillFilter();
        List<SkillResponse> listSkillResponse = skillService.getAllSkills(skillFilter).getContent();

        model.addAttribute("listSkill", listSkillResponse);
        model.addAttribute("listC", listOfCandidateResponse);
        model.addAttribute("candidate", new CandidateRequest());
        model.addAttribute("filter", filter);

        return "hr/candidateManage";
    }

    @PostMapping("/hr/create-candidate")
    public String createCandidate(@ModelAttribute(name = "candidate") @Valid CandidateRequest candidateRequest,
                                  HttpServletRequest request) {
        String targetUrl = request.getHeader("Referer");

        candidateService.createCandidate(candidateRequest);
        return "redirect:" + targetUrl;
    }

    @GetMapping("/hr/info-potential-candidate")
    public String detailCandidates(Model model, @RequestParam(name = "id") Long id) {
        CandidateResponse candidateResponse = candidateService.findOne(id);
        model.addAttribute("candidate", candidateResponse);
        List<Long> list = candidateResponse.getListOfSkill();
        List<String> listSkill = new ArrayList<>();
        for (Long a: list) {
            listSkill.add(skillService.findOne(a).getSkillName());
        }
        model.addAttribute("list", listSkill);

        return "/hr/DetailCandidate";
    }

    @GetMapping("/hr/delete-potential-candidate")
    public String deleteCandidates(Model model, @RequestParam(name = "id") Long id) {

        candidateService.deleteCandidate(id);

        return "redirect:/hr/list-potential-candidate";
    }

    @GetMapping("/hr/update-potential-candidate")
    public String updateCandidates(Model model,
                                   @RequestParam(name = "id") Long id,
                                   HttpServletRequest request) {
        CandidateResponse candidateResponse = candidateService.findOne(id);

        SkillFilter skillFilter = new SkillFilter();
        List<SkillResponse> listSkillResponse = skillService.getAllSkills(skillFilter).getContent();

        model.addAttribute("candidate", candidateResponse);
        model.addAttribute("listSkill", listSkillResponse);

        String targetUrl = request.getHeader("Referer");

        if (!ObjectUtils.isEmpty(targetUrl))
            SessionUtils.putValue(request, "Referer", targetUrl, 3600);

        return "/hr/EditCandidate";
    }

    @PostMapping("/hr/update-potential-candidate")
    public String updateCandidates(@ModelAttribute(name = "candidate") @Valid CandidateRequest candidateRequest,
                                   HttpServletRequest request) throws IOException {
        candidateService.updateCandidate(candidateRequest);

        return "redirect:" + SessionUtils.getValue(request, "Referer");
    }

}
