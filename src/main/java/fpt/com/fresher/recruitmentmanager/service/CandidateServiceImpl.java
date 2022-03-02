package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.Candidates;
import fpt.com.fresher.recruitmentmanager.object.filter.CandidateFilter;
import fpt.com.fresher.recruitmentmanager.object.request.CandidateRequest;
import fpt.com.fresher.recruitmentmanager.object.response.CandidateResponse;
import fpt.com.fresher.recruitmentmanager.object.mapper.CandidateMapper;
import fpt.com.fresher.recruitmentmanager.repository.CandidateRepository;
import fpt.com.fresher.recruitmentmanager.repository.spec.CandidateSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    @Override
    public Page<Candidates> getAllCandidates(CandidateFilter filter) {
        Specification<Candidates> specification = CandidateSpecification.getSpecification(filter);
        return candidateRepository.findAll(specification, filter.getPagination().getPageAndSort());
    }

    @Override
    public Candidates findOne(int id) {
        return null;
    }

    @Override
    public void deleteCandidate(int id) {

    }

    @Override
    public void updateCandidate(CandidateResponse request) {

    }

    @Override
    public void createCandidate(CandidateRequest request) {

        try {
            Candidates candidate = candidateMapper.candidateRequestToEntity(request);
            candidateRepository.save(candidate);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Page<Candidates> findByName(String name) {
        return null;
    }
}
