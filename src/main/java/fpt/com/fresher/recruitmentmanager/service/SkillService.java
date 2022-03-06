package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.Candidates;
import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import fpt.com.fresher.recruitmentmanager.object.filter.CandidateFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.SkillFilter;
import fpt.com.fresher.recruitmentmanager.object.request.CandidateRequest;
import fpt.com.fresher.recruitmentmanager.object.request.SkillRequest;
import fpt.com.fresher.recruitmentmanager.object.response.CandidateResponse;
import fpt.com.fresher.recruitmentmanager.object.response.SkillResponse;
import org.springframework.data.domain.Page;

public interface SkillService {

    Page<Skills> getAllSkills(SkillFilter filter);

    Skills findOne(Long id);

    void deleteSkill(Long id);

    void updateSkill(SkillRequest request);

    void createSkill(SkillRequest request);

}
