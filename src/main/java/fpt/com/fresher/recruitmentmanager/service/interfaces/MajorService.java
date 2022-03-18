package fpt.com.fresher.recruitmentmanager.service.interfaces;

import fpt.com.fresher.recruitmentmanager.object.filter.MajorFilter;
import fpt.com.fresher.recruitmentmanager.object.request.CandidateRequest;
import fpt.com.fresher.recruitmentmanager.object.request.MajorRequest;
import fpt.com.fresher.recruitmentmanager.object.response.MajorResponse;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface MajorService {

    Page<MajorResponse> getAllMajor(MajorFilter filter);

    MajorResponse findOne(Long id);

    void deleteMajor(Long id);

    void updateMajor(MajorRequest request) throws IOException;

    void createMajor(MajorRequest request);


}
