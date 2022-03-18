package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.Recruitment;
import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import fpt.com.fresher.recruitmentmanager.object.filter.RecruitmentFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.RecruitmentMapper;
import fpt.com.fresher.recruitmentmanager.object.request.RecruitmentRequest;
import fpt.com.fresher.recruitmentmanager.object.response.RecruitmentResponse;
import fpt.com.fresher.recruitmentmanager.repository.RecruitmentRepository;
import fpt.com.fresher.recruitmentmanager.repository.spec.RecruitmentSpecification;
import fpt.com.fresher.recruitmentmanager.repository.spec.SkillSpecification;
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
public class RecruitmentServiceImpl implements RecruitmentService {

    private final RecruitmentMapper recruitmentMapper;
    private final RecruitmentRepository recruitmentRepository;

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
        recruitment.setCreatedDate(date);

        recruitmentRepository.save(recruitment);
    }
}
