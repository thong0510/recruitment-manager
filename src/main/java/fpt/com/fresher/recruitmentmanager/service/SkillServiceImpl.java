package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import fpt.com.fresher.recruitmentmanager.object.filter.SkillFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.SkillMapper;
import fpt.com.fresher.recruitmentmanager.object.request.SkillRequest;
import fpt.com.fresher.recruitmentmanager.object.response.SkillResponse;
import fpt.com.fresher.recruitmentmanager.repository.SkillRepository;
import fpt.com.fresher.recruitmentmanager.repository.spec.SkillSpecification;
import fpt.com.fresher.recruitmentmanager.service.interfaces.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    @Override
    public Page<SkillResponse> getAllSkills(SkillFilter filter) {
        Specification<Skills> specification = SkillSpecification.getSpecification(filter);
        return skillRepository.findAll(specification, filter.getPagination().getPageAndSort()).map(skillMapper::entityToSkillResponse);
    }

    @Override
    public SkillResponse findOne(Long id) {
        Optional<Skills> skill = skillRepository.findById(id);
        return skill.map(skillMapper::entityToSkillResponse).orElse(null);
    }

    @Override
    public Skills findOneSkills(Long id) {
        Optional<Skills> skill = skillRepository.findById(id);
        return skill.orElse(null);
    }

    @Override
    public void deleteSkill(Long id) {
        Optional<Skills> skill = skillRepository.findById(id);
        skill.ifPresent(skillRepository::delete);
    }

    @Override
    public void updateSkill(SkillRequest request) {
        Optional<Skills> skill = skillRepository.findById(request.getSkillId());

        if (skill.isPresent()) {
            skillMapper.updateEntity(skill.get(), request);
            LocalDate localDate = LocalDate.now();
            Date date = java.sql.Date.valueOf(localDate);
            skill.get().setUpdatedDate(date);
            skillRepository.save(skill.get());

        }
    }

    @Override
    public void createSkill(SkillRequest request) {
        LocalDate localDate = LocalDate.now();
        Date date = java.sql.Date.valueOf(localDate);

        Skills skill = skillMapper.skillRequestToEntity(request);
        skill.setCreatedDate(date);

        skillRepository.save(skill);
    }
}
