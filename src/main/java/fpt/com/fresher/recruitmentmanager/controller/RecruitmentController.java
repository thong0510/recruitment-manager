package fpt.com.fresher.recruitmentmanager.controller;

import fpt.com.fresher.recruitmentmanager.object.filter.MajorFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.RecruitmentFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.SkillFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.VacanciesFilter;
import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import fpt.com.fresher.recruitmentmanager.object.model.Sorting;
import fpt.com.fresher.recruitmentmanager.object.request.RecruitmentRequest;
import fpt.com.fresher.recruitmentmanager.object.request.SkillRequest;
import fpt.com.fresher.recruitmentmanager.object.response.MajorResponse;
import fpt.com.fresher.recruitmentmanager.object.response.RecruitmentResponse;
import fpt.com.fresher.recruitmentmanager.object.response.SkillResponse;
import fpt.com.fresher.recruitmentmanager.object.response.VacanciesResponse;
import fpt.com.fresher.recruitmentmanager.service.RecruitmentService;
import fpt.com.fresher.recruitmentmanager.service.interfaces.MajorService;
import fpt.com.fresher.recruitmentmanager.service.interfaces.VacanciesService;
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
public class RecruitmentController {

    @Autowired
    private RecruitmentService recruitmentService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private VacanciesService vacanciesService;

    @GetMapping("/hr/list-recruitment")
    public String listRecruitment(Model model,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "") String search) {
        Sorting sorting = new Sorting("recruitmentId", true);
        Pagination pagination = new Pagination(page - 1, 10, sorting);

        search = search.trim();
        RecruitmentFilter filter = RecruitmentFilter.builder()
                .pagination(pagination)
                .dateEnd(search)
                .dateStart(search)
                .vacanciesName(search)
                .majorName(search)
                .build();

        Page<RecruitmentResponse> recruitmentResponses = recruitmentService.getAllRecruitment(filter);

        model.addAttribute("listRecruitment", recruitmentResponses);
        model.addAttribute("filter", filter);
        model.addAttribute("recruitment", new RecruitmentRequest());

        MajorFilter majorFilter = new MajorFilter();
        Page<MajorResponse> listMajor = majorService.getAllMajor(majorFilter);

        model.addAttribute("listMajor", listMajor);

        VacanciesFilter vacanciesFilter = new VacanciesFilter();
        Page<VacanciesResponse> listVacancies = vacanciesService.getAllVacancies(vacanciesFilter);
        model.addAttribute("listVacancies", listVacancies);
        System.out.println("ppp" + listVacancies);

        return "hr/recruitmentManage";
    }

    @PostMapping("/hr/create-recruitment")
    public String createRecruitment(@ModelAttribute(name = "recruitment") @Valid RecruitmentRequest recruitmentRequest,
                                  HttpServletRequest request) {
        String targetUrl = request.getHeader("Referer");

        recruitmentService.createRecruitment(recruitmentRequest);
        return "redirect:" + targetUrl;
    }

    @GetMapping("/hr/update-recruitment")
    public String updateRecruitment(Model model,
                                   @RequestParam(name = "id") Long id,
                                   HttpServletRequest request) {
        RecruitmentResponse recruitmentResponse = recruitmentService.findOne(id);

        model.addAttribute("recruitment", recruitmentResponse);

        String targetUrl = request.getHeader("Referer");

        if (!ObjectUtils.isEmpty(targetUrl))
            SessionUtils.putValue(request, "Referer", targetUrl, 3600);

        return "/hr/EditRecruitment";
    }

    @PostMapping("/hr/update-recruitment")
    public String updateRecruitment(@ModelAttribute(name = "recruitment") @Valid RecruitmentRequest recruitmentRequest,
                                   HttpServletRequest request) throws IOException {

        recruitmentService.updateRecruitment(recruitmentRequest);

        return "redirect:" + SessionUtils.getValue(request, "Referer");
    }

    @GetMapping("/hr/delete-recruitment")
    public String deleteRecruitment(Model model, @RequestParam(name = "id") Long id) {

        recruitmentService.deleteRecruitment(id);

        return "redirect:/hr/list-recruitment";
    }




}
