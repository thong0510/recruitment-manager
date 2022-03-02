package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.Candidates;
import fpt.com.fresher.recruitmentmanager.object.filter.CandidateFilter;
import fpt.com.fresher.recruitmentmanager.object.request.CandidateRequest;
import fpt.com.fresher.recruitmentmanager.object.response.CandidateResponse;
import org.springframework.data.domain.Page;

public interface CandidateService {

    Page<Candidates> getAllCandidates(CandidateFilter filter);

    CandidateResponse findOne(Long id);

    void deleteCandidate(Long id);

    void updateCandidate(CandidateRequest request);

    void createCandidate(CandidateRequest request);

}
