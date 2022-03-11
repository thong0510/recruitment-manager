package fpt.com.fresher.recruitmentmanager.controller;

import fpt.com.fresher.recruitmentmanager.object.filter.CandidateFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.MajorFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.SkillFilter;
import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import fpt.com.fresher.recruitmentmanager.object.model.Sorting;
import fpt.com.fresher.recruitmentmanager.object.request.CandidateRequest;
import fpt.com.fresher.recruitmentmanager.object.request.MajorRequest;
import fpt.com.fresher.recruitmentmanager.object.response.CandidateResponse;
import fpt.com.fresher.recruitmentmanager.object.response.MajorResponse;
import fpt.com.fresher.recruitmentmanager.object.response.SkillResponse;
import fpt.com.fresher.recruitmentmanager.service.MajorService;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
public class MajorController {

    @Autowired
    private MajorService majorService;

    @GetMapping("/hr/list-major")
    public String listMajor(Model model,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "") String search) {
        Sorting sorting = new Sorting("majorId", true);
        Pagination pagination = new Pagination(page - 1, 10, sorting);

        search = search.trim();
        MajorFilter filter = MajorFilter.builder()
                .pagination(pagination)
                .majorName(search)
                .build();

        Page<MajorResponse> majorResponses = majorService.getAllMajor(filter);

        model.addAttribute("listMajor", majorResponses);
        model.addAttribute("filter", filter);
        model.addAttribute("major", new MajorRequest());

        return "hr/listMajor";
    }

    @PostMapping("/hr/create-major")
    public String createMajor(@ModelAttribute(name = "major") @Valid MajorRequest majorRequest,
                                  HttpServletRequest request) {
        String targetUrl = request.getHeader("Referer");

        majorService.createMajor(majorRequest);
        return "redirect:" + targetUrl;
    }

    @GetMapping("/hr/update-major")
    public String updateMajor(Model model,
                                   @RequestParam(name = "id") Long id,
                                   HttpServletRequest request) {
        MajorResponse majorResponse = majorService.findOne(id);

        model.addAttribute("major", majorResponse);

        String targetUrl = request.getHeader("Referer");

        if (!ObjectUtils.isEmpty(targetUrl))
            SessionUtils.putValue(request, "Referer", targetUrl, 3600);

        return "/hr/EditMajor";
    }

    @PostMapping("/hr/update-major")
    public String updateMajor(@ModelAttribute(name = "major") @Valid MajorRequest majorRequest,
                                   HttpServletRequest request) throws IOException {

        majorService.updateMajor(majorRequest);

        return "redirect:" + SessionUtils.getValue(request, "Referer");
    }

    @GetMapping("/hr/delete-major")
    public String deleteMajor(Model model, @RequestParam(name = "id") Long id) {

        majorService.deleteMajor(id);

        return "redirect:/hr/list-major";
    }




}
