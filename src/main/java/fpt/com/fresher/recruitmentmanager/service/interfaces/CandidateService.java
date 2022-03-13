package fpt.com.fresher.recruitmentmanager.service.interfaces;

import fpt.com.fresher.recruitmentmanager.object.entity.Candidates;
import fpt.com.fresher.recruitmentmanager.object.filter.CandidateFilter;
import fpt.com.fresher.recruitmentmanager.object.request.CandidateRequest;
import fpt.com.fresher.recruitmentmanager.object.response.CandidateResponse;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface CandidateService {

    Page<CandidateResponse> getAllCandidates(CandidateFilter filter);

    CandidateResponse findOne(Long id);

    void deleteCandidate(Long id);

    void updateCandidate(CandidateRequest request) throws IOException;

    void createCandidate(CandidateRequest request);

}
