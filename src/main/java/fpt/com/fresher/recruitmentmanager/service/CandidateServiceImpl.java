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

import java.io.IOException;
import java.util.*;
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
    private final SkillMapper skillMapper;

    @Override
    public Page<CandidateResponse> getAllCandidates(CandidateFilter filter) {
        Specification<Candidates> specification = CandidateSpecification.getSpecification(filter);
        return candidateRepository.findAll(specification, filter.getPagination().getPageAndSort()).map(candidateMapper::entityToCandidateResponse);
    }

    @Override
    public CandidateResponse findOne(Long id) {
        Optional<Candidates> candidates = candidateRepository.findById(id);
        return candidates.map(candidateMapper::entityToCandidateResponse).orElse(null);
    }

    @Override
    public void deleteCandidate(Long id) {
        Optional<Candidates> candidates = candidateRepository.findById(id);
        candidates.ifPresent(candidateRepository::delete);
    }

    @Override
    public void updateCandidate(CandidateRequest request) throws IOException {

        Optional<Candidates> candidates = candidateRepository.findById(request.getCandidateId());

        if (candidates.isPresent()) {

            Set<SkillCandidate> listOfSkillCandidate = candidates.get().getSkillCandidates();

            Set<Long> listOfSkillId = candidates.get().getSkillCandidates().stream()
                    .map(e -> e.getSkills().getSkillId()).collect(Collectors.toCollection(HashSet::new));

            Set<Long> listOfSkillIdUpdate = new HashSet<>(request.getListOfSkill());

            candidateMapper.updateEntity(candidates.get(), request);

            for (Long id : listOfSkillId) {

                if (!listOfSkillIdUpdate.contains(id)) {

                    listOfSkillCandidate = listOfSkillCandidate.stream()
                            .filter(e -> {
                                if (!e.getSkills().getSkillId().equals(id)) {
                                    return true;
                                } else {
                                    skillCandidateRepository.delete(e);
                                    return false;
                                }
                            }).collect(Collectors.toSet());

                }

            }

            for (Long id : listOfSkillIdUpdate) {

                if (!listOfSkillId.contains(id)) {
                    Skills skills = skillService.findOneSkills(id);

                    SkillCandidate skillCandidate = SkillCandidate
                            .builder()
                            .candidates(candidates.get())
                            .skills(skills)
                            .build();

                    listOfSkillCandidate.add(skillCandidate);

                }

            }

            if (!ObjectUtils.isEmpty(request.getImageFile().getBytes())) {
                String image = cloudinaryService.uploadImage(candidates.get().getPhoto(), request.getImageFile());
                if (image != null) candidates.get().setPhoto(image);
            }

            candidates.get().setSkillCandidates(listOfSkillCandidate);

            candidateRepository.save(candidates.get());
        }

    }

    @Override
    public void createCandidate(CandidateRequest requestBody) {

        Candidates candidate = candidateMapper.candidateRequestToEntity(requestBody);

        Set<SkillCandidate> listOfSkillCandidate = new HashSet<>();

        for (Long id : requestBody.getListOfSkill()) {
            SkillResponse skillResponse = skillService.findOne(id);

            SkillCandidate skillCandidate = SkillCandidate
                                                        .builder()
                                                        .candidates(candidate)
                                                        .skills(skillMapper.skillResponseToEntity(skillResponse))
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
