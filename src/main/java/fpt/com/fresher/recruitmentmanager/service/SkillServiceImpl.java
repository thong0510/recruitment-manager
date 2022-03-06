package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import fpt.com.fresher.recruitmentmanager.object.filter.SkillFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.SkillMapper;
import fpt.com.fresher.recruitmentmanager.object.request.SkillRequest;
import fpt.com.fresher.recruitmentmanager.repository.SkillRepository;
import fpt.com.fresher.recruitmentmanager.repository.spec.SkillSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Skills findOne(Long id) {
        return skillRepository.getById(id);
    }

    @Override
    public void deleteSkill(Long id) {
        try {
        Skills skill = skillRepository.getById(id);

            skillRepository.delete(skill);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateSkill(SkillRequest request) {
        Skills skill = skillMapper.skillRequestToEntity(request);
        skillRepository.save(skill);

    }

    @Override
    public void createSkill(SkillRequest request) {

        skillRepository.save(skillMapper.skillRequestToEntity(request));

    }
}
