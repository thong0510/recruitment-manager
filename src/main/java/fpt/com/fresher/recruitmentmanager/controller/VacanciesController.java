package fpt.com.fresher.recruitmentmanager.controller;

import fpt.com.fresher.recruitmentmanager.object.filter.MajorDetailFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.MajorFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.VacanciesFilter;
import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import fpt.com.fresher.recruitmentmanager.object.model.Sorting;
import fpt.com.fresher.recruitmentmanager.object.request.VacanciesRequest;
import fpt.com.fresher.recruitmentmanager.object.response.MajorDetailResponse;
import fpt.com.fresher.recruitmentmanager.object.response.MajorResponse;
import fpt.com.fresher.recruitmentmanager.object.response.VacanciesResponse;
import fpt.com.fresher.recruitmentmanager.service.interfaces.MajorDetailService;
import fpt.com.fresher.recruitmentmanager.service.interfaces.MajorService;
import fpt.com.fresher.recruitmentmanager.service.interfaces.VacanciesService;
import fpt.com.fresher.recruitmentmanager.utils.SessionUtils;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class VacanciesController {

    @Autowired
    private VacanciesService vacanciesService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private MajorDetailService majorDetailService;

    @GetMapping("/hr/list-vacancies")
    public String listVacancies(Model model,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "") String search) {
        Sorting sorting = new Sorting("vacanciesId", true);
        Pagination pagination = new Pagination(page - 1, 10, sorting);

        search = search.trim();
        VacanciesFilter filter = VacanciesFilter.builder()
                .pagination(pagination)
                .description(search)
                .majorName(search)
                .majorDetailName(search)
                .build();

        Page<VacanciesResponse> vacanciesResponses = vacanciesService.getAllVacancies(filter);

        model.addAttribute("listVacancies", vacanciesResponses);
        model.addAttribute("filter", filter);
        model.addAttribute("vacancies", new VacanciesRequest());

        MajorFilter majorFilter = new MajorFilter();
        Page<MajorResponse> listMajor = majorService.getAllMajor(majorFilter);

        model.addAttribute("listMajor", listMajor);

        MajorDetailFilter majorDetailFilter = new MajorDetailFilter();
        Page<MajorDetailResponse> majorDetailResponses = majorDetailService.getAllMajorDetail(majorDetailFilter);

        model.addAttribute("listMajorDetail", majorDetailResponses);

        return "hr/listVacancies";
    }

    @PostMapping("/hr/create-vacancies")
    public String createVacancies(@ModelAttribute(name = "vacancies") @Valid VacanciesRequest vacanciesRequest,
                                  HttpServletRequest request) {
        String targetUrl = request.getHeader("Referer");
        try {
            Long majorDetailId1 = vacanciesRequest.getMajorDetail().getMajor().getMajorId();
            Long mjId = vacanciesRequest.getMajor().getMajorId();
            if (majorDetailId1.equals(mjId)) {
                vacanciesService.createVacancies(vacanciesRequest);
                return "redirect:" + targetUrl;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @GetMapping("/hr/update-vacancies")
    public String updateVacancies(Model model,
                                  @RequestParam(name = "id") Long id,
                                  HttpServletRequest request) {
        VacanciesResponse vacanciesResponse = vacanciesService.findOne(id);

        model.addAttribute("vacancies", vacanciesResponse);

        MajorFilter majorFilter = new MajorFilter();
        Page<MajorResponse> listMajor = majorService.getAllMajor(majorFilter);

        model.addAttribute("listMajor", listMajor);

        MajorDetailFilter majorDetailFilter = new MajorDetailFilter();
        Page<MajorDetailResponse> majorDetailResponses = majorDetailService.getAllMajorDetail(majorDetailFilter);

        model.addAttribute("listMajorDetail", majorDetailResponses);


        String targetUrl = request.getHeader("Referer");

        if (!ObjectUtils.isEmpty(targetUrl))
            SessionUtils.putValue(request, "Referer", targetUrl, 3600);

        return "/hr/EditVacancies";
    }

    @PostMapping("/hr/update-vacancies")
    public String updateVacancies(@ModelAttribute(name = "vacancies") @Valid VacanciesRequest vacanciesRequest,
                                  HttpServletRequest request) throws IOException {

        try {
            Long majorDetailId1 = vacanciesRequest.getMajorDetail().getMajor().getMajorId();
            Long mjId = vacanciesRequest.getMajor().getMajorId();
            if (majorDetailId1.equals(mjId)) {
                vacanciesService.updateVacancies(vacanciesRequest);
                return "redirect:" + SessionUtils.getValue(request, "Referer");
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/hr/delete-vacancies")
    public String deleteVacancies(Model model, @RequestParam(name = "id") Long id) {

        vacanciesService.deleteVacancies(id);

        return "redirect:/hr/list-vacancies";
    }


}
