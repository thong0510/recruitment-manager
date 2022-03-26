package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.Major;
import fpt.com.fresher.recruitmentmanager.object.entity.MajorDetail;
import fpt.com.fresher.recruitmentmanager.object.filter.MajorDetailFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.MajorDetailMapper;
import fpt.com.fresher.recruitmentmanager.object.mapper.MajorMapper;
import fpt.com.fresher.recruitmentmanager.object.request.MajorDetailRequest;
import fpt.com.fresher.recruitmentmanager.object.response.MajorDetailResponse;
import fpt.com.fresher.recruitmentmanager.object.response.MajorResponse;
import fpt.com.fresher.recruitmentmanager.repository.MajorDetailRepository;
import fpt.com.fresher.recruitmentmanager.repository.MajorRepository;
import fpt.com.fresher.recruitmentmanager.repository.spec.MajorDetailSpecification;
import fpt.com.fresher.recruitmentmanager.service.interfaces.MajorDetailService;
import fpt.com.fresher.recruitmentmanager.service.interfaces.MajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MajorDetailServiceImpl implements MajorDetailService {

    private final MajorDetailRepository majorDetailRepository;
    private final MajorRepository majorRepository;
    private final MajorDetailMapper majorDetailMapper;
    private final MajorService majorService;
    private final MajorMapper majorMapper;

    @Override
    public Page<MajorDetailResponse> getAllMajorDetail(MajorDetailFilter filter) {
        Specification<MajorDetail> majorDetailSpecification = MajorDetailSpecification.getSpecification(filter);
        return majorDetailRepository.findAll(majorDetailSpecification, filter.getPagination().getPageAndSort()).map(majorDetailMapper::entityToMajorDetailResponse);
    }

    @Override
    public MajorDetailResponse findOne(Long id) {
        Optional<MajorDetail> majorDetail = majorDetailRepository.findById(id);
        return majorDetail.map(majorDetailMapper::entityToMajorDetailResponse).orElse(null);
    }

    @Override
    public void deleteMajorDetail(Long id) {
        Optional<MajorDetail> majorDetail = majorDetailRepository.findById(id);
        majorDetail.ifPresent(majorDetailRepository::delete);
    }

    @Override
    public void updateMajorDetail(MajorDetailRequest request) throws IOException {
        Optional<MajorDetail> majorDetail = majorDetailRepository.findById(request.getMajorDetailId());

        if (majorDetail.isPresent()) {
            majorDetailMapper.updateEntity(majorDetail.get(), request);
            LocalDate localDate = LocalDate.now();
            Date date = java.sql.Date.valueOf(localDate);
            majorDetail.get().setUpdatedDate(date);
            majorDetailRepository.save(majorDetail.get());

        }
    }

    @Override
    public void createMajorDetail(MajorDetailRequest request) {

        LocalDate localDate = LocalDate.now();
        Date date = java.sql.Date.valueOf(localDate);

        Major major = majorRepository.findById(request.getMajorId()).orElse(null);

        MajorDetail majorDetail = majorDetailMapper.majorDetailRequestToEntity(request);
        majorDetail.setCreatedDate(date);
        majorDetail.setMajor(major);

        majorDetailRepository.save(majorDetail);
    }
}
