package fpt.com.fresher.recruitmentmanager.service.interfaces;

import fpt.com.fresher.recruitmentmanager.object.filter.MajorDetailFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.MajorFilter;
import fpt.com.fresher.recruitmentmanager.object.request.MajorDetailRequest;
import fpt.com.fresher.recruitmentmanager.object.request.MajorRequest;
import fpt.com.fresher.recruitmentmanager.object.response.MajorDetailResponse;
import fpt.com.fresher.recruitmentmanager.object.response.MajorResponse;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface MajorDetailService {

    Page<MajorDetailResponse> getAllMajorDetail(MajorDetailFilter filter);

    MajorDetailResponse findOne(Long id);

    void deleteMajorDetail(Long id);

    void updateMajorDetail(MajorDetailRequest request) throws IOException;

    void createMajorDetail(MajorDetailRequest request);

}
