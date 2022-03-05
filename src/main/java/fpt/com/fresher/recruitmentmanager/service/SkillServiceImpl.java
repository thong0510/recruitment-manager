package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import fpt.com.fresher.recruitmentmanager.object.filter.SkillFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.SkillMapper;
import fpt.com.fresher.recruitmentmanager.object.request.SkillRequest;
import fpt.com.fresher.recruitmentmanager.object.response.SkillResponse;
import fpt.com.fresher.recruitmentmanager.repository.SkillRepository;
import fpt.com.fresher.recruitmentmanager.repository.spec.SkillSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService{

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    @Override
    public Page<Skills> getAllSkills(SkillFilter filter) {
        Specification<Skills> specification = SkillSpecification.getSpecification(filter);
        return skillRepository.findAll(specification, filter.getPagination().getPageAndSort());
    }

    @Override
    public SkillResponse findOne(int id) {
        Skills skills = skillRepository.getById(id);
        return skillMapper.entityToSkillResponse(skills);
    }

    @Override
    public void deleteSkill(int id) {

    }

    @Override
    public void updateSkill(SkillRequest request) {

    }

    @Override
    public void createSkill(SkillRequest request) {

    }
}