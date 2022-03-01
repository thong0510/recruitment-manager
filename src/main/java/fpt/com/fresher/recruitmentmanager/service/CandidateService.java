package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.Candidates;
import fpt.com.fresher.recruitmentmanager.object.filter.CandidateFilter;
import fpt.com.fresher.recruitmentmanager.object.request.CandidateRequest;
import org.springframework.data.domain.Page;

public interface CandidateService {

    Page<Candidates> getAllCandidates(CandidateFilter filter);

    Candidates findOne(int id);

    void deleteCandidate(int id);

    void updateCandidate(CandidateRequest request);

    void createCandidate(CandidateRequest request);

    Page<Candidates> findByName(String name);
}
