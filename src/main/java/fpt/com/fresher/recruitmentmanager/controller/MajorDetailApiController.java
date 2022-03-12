package fpt.com.fresher.recruitmentmanager.controller;

import fpt.com.fresher.recruitmentmanager.object.filter.MajorDetailFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.MajorFilter;
import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import fpt.com.fresher.recruitmentmanager.object.model.Sorting;
import fpt.com.fresher.recruitmentmanager.object.request.MajorDetailRequest;
import fpt.com.fresher.recruitmentmanager.object.request.MajorRequest;
import fpt.com.fresher.recruitmentmanager.object.response.MajorDetailResponse;
import fpt.com.fresher.recruitmentmanager.service.MajorDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class MajorDetailApiController {

    private final MajorDetailService majorDetailService;

    @PostMapping("/api/hr/list-major-detail")
    public ResponseEntity<?> listMajorDetail(@RequestBody MajorDetailFilter majorDetailFilter) {

        List<MajorDetailResponse> majorDetailResponses = majorDetailService.getAllMajorDetail(majorDetailFilter).get().collect(Collectors.toList());

        return ResponseEntity.ok(majorDetailResponses);
    }
}
