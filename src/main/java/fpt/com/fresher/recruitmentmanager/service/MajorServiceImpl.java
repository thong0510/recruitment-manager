package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.Major;
import fpt.com.fresher.recruitmentmanager.object.filter.MajorFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.MajorMapper;
import fpt.com.fresher.recruitmentmanager.object.request.MajorRequest;
import fpt.com.fresher.recruitmentmanager.object.response.MajorResponse;
import fpt.com.fresher.recruitmentmanager.repository.MajorRepository;
import fpt.com.fresher.recruitmentmanager.repository.spec.MajorSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MajorServiceImpl implements MajorService{

    private final MajorRepository majorRepository;

    private final MajorMapper majorMapper;

    @Override
    public Page<MajorResponse> getAllMajor(MajorFilter filter) {
        Specification<Major> majorSpecification = MajorSpecification.getSpecification(filter);
        return majorRepository.findAll(majorSpecification, filter.getPagination().getPageAndSort()).map(majorMapper::entityToMajorResponse);
    }

    @Override
    public MajorResponse findOne(Long id) {
        Optional<Major> major = majorRepository.findById(id);
        return major.map(majorMapper::entityToMajorResponse).orElse(null);
    }

    @Override
    public void deleteMajor(Long id) {
        Optional<Major> major = majorRepository.findById(id);
        major.ifPresent(majorRepository::delete);

    }

    @Override
    public void updateMajor(MajorRequest request) throws IOException {

        Optional<Major> major = majorRepository.findById(request.getMajorId());

        if (major.isPresent()) {
            majorMapper.updateEntity(major.get(), request);
            LocalDate localDate = LocalDate.now();
            Date date = java.sql.Date.valueOf(localDate);
            major.get().setUpdatedDate(date);
            majorRepository.save(major.get());

        }


    }

    @Override
    public void createMajor(MajorRequest request) {

        LocalDate localDate = LocalDate.now();
        Date date = java.sql.Date.valueOf(localDate);

        Major major = majorMapper.majorRequestToEntity(request);
        major.setCreatedDate(date);

        majorRepository.save(major);

    }
}
