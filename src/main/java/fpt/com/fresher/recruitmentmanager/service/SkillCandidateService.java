package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.request.SkillRequest;
import fpt.com.fresher.recruitmentmanager.repository.SkillCandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillCandidateService {

    private final SkillCandidateRepository skillCandidateRepository;

}
