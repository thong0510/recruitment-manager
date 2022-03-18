package fpt.com.fresher.recruitmentmanager.controller;

import fpt.com.fresher.recruitmentmanager.object.filter.MajorDetailFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.MajorFilter;
import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import fpt.com.fresher.recruitmentmanager.object.model.Sorting;
import fpt.com.fresher.recruitmentmanager.object.request.MajorDetailRequest;
import fpt.com.fresher.recruitmentmanager.object.response.MajorDetailResponse;
import fpt.com.fresher.recruitmentmanager.object.response.MajorResponse;
import fpt.com.fresher.recruitmentmanager.service.interfaces.MajorDetailService;
import fpt.com.fresher.recruitmentmanager.service.interfaces.MajorService;
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
public class MajorDetailController {

    @Autowired
    private MajorDetailService majorDetailService;

    @Autowired
    private MajorService majorService;

    @GetMapping("/hr/list-majorDetail")
    public String listMajorDetail(Model model,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "") String search) {

        Sorting sorting = new Sorting("majorDetailId", true);
        Pagination pagination = new Pagination(page - 1, 10, sorting);

        search = search.trim();
        MajorDetailFilter filter = MajorDetailFilter.builder()
                .pagination(pagination)
                .majorDetailName(search)
                .build();

        Page<MajorDetailResponse> majorDetailResponses = majorDetailService.getAllMajorDetail(filter);

        model.addAttribute("listMajorDetail", majorDetailResponses);
        model.addAttribute("filter", filter);
        model.addAttribute("majorDetail", new MajorDetailRequest());

        MajorFilter majorFilter = new MajorFilter();
        Page<MajorResponse> listMajor = majorService.getAllMajor(majorFilter);

        model.addAttribute("listMajor", listMajor);

        return "hr/listMajorDetail";
    }

    @PostMapping("/hr/create-majorDetail")
    public String createMajorDetail(@ModelAttribute(name = "majorDetail") @Valid MajorDetailRequest majorDetailRequest,
                                  HttpServletRequest request) {
        String targetUrl = request.getHeader("Referer");

        majorDetailService.createMajorDetail(majorDetailRequest);
        return "redirect:" + targetUrl;
    }

    @GetMapping("/hr/update-majorDetail")
    public String updateMajorDetail(Model model,
                                   @RequestParam(name = "id") Long id,
                                   HttpServletRequest request) {
        MajorDetailResponse majorDetailResponse = majorDetailService.findOne(id);

        model.addAttribute("majorDetail", majorDetailResponse);

        MajorFilter majorFilter = new MajorFilter();
        Page<MajorResponse> listMajor = majorService.getAllMajor(majorFilter);

        model.addAttribute("listMajor", listMajor);

        String targetUrl = request.getHeader("Referer");

        if (!ObjectUtils.isEmpty(targetUrl))
            SessionUtils.putValue(request, "Referer", targetUrl, 3600);

        return "/hr/EditMajorDetail";
    }

    @PostMapping("/hr/update-majorDetail")
    public String updateMajorDetail(@ModelAttribute(name = "majorDetail") @Valid MajorDetailRequest majorDetailRequest,
                                   HttpServletRequest request) throws IOException {

        majorDetailService.updateMajorDetail(majorDetailRequest);

        return "redirect:" + SessionUtils.getValue(request, "Referer");
    }

    @GetMapping("/hr/delete-majorDetail")
    public String deleteMajorDetail(Model model, @RequestParam(name = "id") Long id) {

        majorDetailService.deleteMajorDetail(id);

        return "redirect:/hr/list-majorDetail";
    }




}
