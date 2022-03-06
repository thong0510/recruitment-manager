package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.Candidates;
import fpt.com.fresher.recruitmentmanager.object.entity.SkillCandidate;
import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import fpt.com.fresher.recruitmentmanager.object.filter.CandidateFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.SkillMapper;
import fpt.com.fresher.recruitmentmanager.object.request.CandidateRequest;
import fpt.com.fresher.recruitmentmanager.object.response.CandidateResponse;
import fpt.com.fresher.recruitmentmanager.object.mapper.CandidateMapper;
import fpt.com.fresher.recruitmentmanager.object.response.SkillResponse;
import fpt.com.fresher.recruitmentmanager.repository.CandidateRepository;
import fpt.com.fresher.recruitmentmanager.repository.SkillCandidateRepository;
import fpt.com.fresher.recruitmentmanager.repository.spec.CandidateSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final CloudinaryService cloudinaryService;
    private final SkillService skillService;
    private final SkillCandidateRepository skillCandidateRepository;

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

        Candidates candidates = candidateRepository.getById(request.getCandidateId());

        Set<SkillCandidate> listOfSkillCandidate = candidates.getSkillCandidates();

        List<Long> listOfSkillId = candidates.getSkillCandidates().stream()
                .map(e -> e.getSkills().getSkillId()).collect(Collectors.toList());

        List<Long> listOfSkillIdUpdate = request.getListOfSkill();

        candidateMapper.updateEntity(candidates, request);

        for (Long id : listOfSkillId) {

            if (!listOfSkillIdUpdate.contains(id)) {

                SkillCandidate skillCandidate = listOfSkillCandidate.stream()
                            .filter(e -> e.getSkills().getSkillId().equals(id)).collect(Collectors.toList()).get(0);

                skillCandidateRepository.delete(skillCandidate);
            }

        }

        for (Long id : listOfSkillIdUpdate) {

            if (!listOfSkillId.contains(id)) {

                SkillCandidate skillCandidate = SkillCandidate
                        .builder()
                        .candidates(candidates)
                        .skills(skillService.findOne(id))
                        .build();

                skillCandidateRepository.save(skillCandidate);
            }

        }

        candidateRepository.save(candidates);

    }

    @Override
    public void createCandidate(CandidateRequest requestBody) {

        Candidates candidate = candidateMapper.candidateRequestToEntity(requestBody);

        Set<SkillCandidate> listOfSkillCandidate = new HashSet<>();

        for (Long id : requestBody.getListOfSkill()) {
            Skills skills = skillService.findOne(id);
            SkillCandidate skillCandidate = SkillCandidate
                                                        .builder()
                                                        .candidates(candidate)
                                                        .skills(skills)
                                                        .build();
            listOfSkillCandidate.add(skillCandidate);
        }

        candidate.setSkillCandidates(listOfSkillCandidate);

        if (!ObjectUtils.isEmpty(requestBody.getImageFile())) {
            String image = cloudinaryService.uploadImage(null, requestBody.getImageFile());
            if (image != null) candidate.setPhoto(image);
        }

        candidateRepository.save(candidate);

    }

}
