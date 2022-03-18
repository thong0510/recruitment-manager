package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.Recruitment;
import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import fpt.com.fresher.recruitmentmanager.object.filter.RecruitmentFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.SkillFilter;
import fpt.com.fresher.recruitmentmanager.object.request.RecruitmentRequest;
import fpt.com.fresher.recruitmentmanager.object.request.SkillRequest;
import fpt.com.fresher.recruitmentmanager.object.response.RecruitmentResponse;
import fpt.com.fresher.recruitmentmanager.object.response.SkillResponse;
import org.springframework.data.domain.Page;

public interface RecruitmentService {

    Page<RecruitmentResponse> getAllRecruitment(RecruitmentFilter filter);

    RecruitmentResponse findOne(Long id);

    Recruitment findOneRecruitment(Long id);

    void deleteRecruitment(Long id);

    void updateRecruitment(RecruitmentRequest request);

    void createRecruitment(RecruitmentRequest request);

}
