package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.*;
import fpt.com.fresher.recruitmentmanager.object.filter.RecruitmentFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.RecruitmentMapper;
import fpt.com.fresher.recruitmentmanager.object.request.RecruitmentRequest;
import fpt.com.fresher.recruitmentmanager.object.response.RecruitmentResponse;
import fpt.com.fresher.recruitmentmanager.repository.MajorRepository;
import fpt.com.fresher.recruitmentmanager.repository.RecruitmentRepository;
import fpt.com.fresher.recruitmentmanager.repository.UserRepository;
import fpt.com.fresher.recruitmentmanager.repository.VacanciesRepository;
import fpt.com.fresher.recruitmentmanager.repository.spec.RecruitmentSpecification;
import fpt.com.fresher.recruitmentmanager.service.interfaces.RecruitmentService;
import fpt.com.fresher.recruitmentmanager.service.interfaces.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class RecruitmentServiceImpl implements RecruitmentService {

    private final RecruitmentMapper recruitmentMapper;
    private final RecruitmentRepository recruitmentRepository;
    private final SkillService skillService;
    private final MajorRepository majorRepository;
    private final VacanciesRepository vacanciesRepository;
    private final UserRepository userRepository;

    @Override
    public Page<RecruitmentResponse> getAllRecruitment(RecruitmentFilter filter) {
        Specification<Recruitment> specification = RecruitmentSpecification.getSpecification(filter);
        return recruitmentRepository.findAll(specification, filter.getPagination().getPageAndSort()).map(recruitmentMapper::entityToRecruitmentResponse);
    }

    @Override
    public RecruitmentResponse findOne(Long id) {
        Optional<Recruitment> recruitment = recruitmentRepository.findById(id);
        return recruitment.map(recruitmentMapper::entityToRecruitmentResponse).orElse(null);
    }

    @Override
    public Recruitment findOneRecruitment(Long id) {
        Optional<Recruitment> recruitment = recruitmentRepository.findById(id);
        return recruitment.orElse(null);
    }

    @Override
    public void deleteRecruitment(Long id) {
        Optional<Recruitment> recruitment = recruitmentRepository.findById(id);
        recruitment.ifPresent(recruitmentRepository::delete);
    }

    @Override
    public void updateRecruitment(RecruitmentRequest request) {
        Optional<Recruitment> recruitment = recruitmentRepository.findById(request.getRecruitmentId());

        if (recruitment.isPresent()) {
            recruitmentMapper.updateEntity(recruitment.get(), request);
            LocalDate localDate = LocalDate.now();
            Date date = java.sql.Date.valueOf(localDate);
            recruitment.get().setUpdatedDate(date);
            recruitmentRepository.save(recruitment.get());

        }
    }

    @Override
    public void createRecruitment(RecruitmentRequest request) {
        LocalDate localDate = LocalDate.now();
        Date date = java.sql.Date.valueOf(localDate);

        Recruitment recruitment = recruitmentMapper.recruitmentRequestToEntity(request);

        Set<SkillRecruitment> listSkillRecruitment = new HashSet<>();

        for (Long id : request.getListOfSkill()) {
            Skills skill = skillService.findOneSkills(id);

            SkillRecruitment skillRecruitment = SkillRecruitment
                    .builder()
                    .recruitment(recruitment)
                    .skills(skill)
                    .build();
            listSkillRecruitment.add(skillRecruitment);
        }

        recruitment.setSkillRecruitment(listSkillRecruitment);

        Major major = majorRepository.findById(request.getMajorId()).orElse(null);
        Vacancies vacancies = vacanciesRepository.findById(request.getVacanciesId()).orElse(null);
        recruitment.setMajor(major);
        recruitment.setVacancies(vacancies);

//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (principal instanceof UserDetails) {
//            String username = ((UserDetails) principal).getUsername();
//            Optional<Users> users = userRepository.findByUsername(username);
//
//        } else {
//            String username = principal.toString();
//            Optional<Users> users = userRepository.findByUsername(username);
//        }

        recruitment.setCreatedDate(date);

        recruitmentRepository.save(recruitment);
    }
}
