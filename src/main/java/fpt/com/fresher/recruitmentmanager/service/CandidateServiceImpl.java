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
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public Page<Candidates> getAllCandidates(CandidateFilter filter) {
        Specification<Candidates> specification = CandidateSpecification.getSpecification(filter);
        return candidateRepository.findAll(specification, filter.getPagination().getPageAndSort());
    }

    @Override
    public CandidateResponse findOne(Long id) {
        Candidates candidates = candidateRepository.getById(id);
        CandidateResponse candidateResponse = candidateMapper.entityToCandidateResponse(candidates);
        return candidateResponse;
    }

    @Override
    public void deleteCandidate(Long id) {

        Candidates candidates = candidateRepository.getById(id);
        candidateRepository.delete(candidates);

    }

    @Override
    public void updateCandidate(CandidateRequest request) {

        Candidates candidates = candidateMapper.candidateRequestToEntity(request);
        candidateRepository.save(candidates);

    }

    @Override
    public void createCandidate(CandidateRequest requestBody) {

        Candidates candidate = candidateMapper.candidateRequestToEntity(requestBody);

        if (!ObjectUtils.isEmpty(requestBody.getImageFile())) {
            String image = cloudinaryService.uploadImage(null, requestBody.getImageFile());
            if (image != null) candidate.setPhoto(image);
        }
        candidateRepository.save(candidate);

    }

}
