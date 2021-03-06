package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.Major;
import fpt.com.fresher.recruitmentmanager.object.entity.MajorDetail;
import fpt.com.fresher.recruitmentmanager.object.entity.Vacancies;
import fpt.com.fresher.recruitmentmanager.object.filter.VacanciesFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.VacanciesMapper;
import fpt.com.fresher.recruitmentmanager.object.request.VacanciesRequest;
import fpt.com.fresher.recruitmentmanager.object.response.VacanciesResponse;
import fpt.com.fresher.recruitmentmanager.repository.MajorDetailRepository;
import fpt.com.fresher.recruitmentmanager.repository.MajorRepository;
import fpt.com.fresher.recruitmentmanager.repository.VacanciesRepository;
import fpt.com.fresher.recruitmentmanager.repository.spec.VacanciesSpecification;
import fpt.com.fresher.recruitmentmanager.service.interfaces.VacanciesService;
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
public class VacanciesServiceImpl implements VacanciesService {

    private final VacanciesRepository vacanciesRepository;
    private final VacanciesMapper vacanciesMapper;
    private final MajorRepository majorRepository;
    private final MajorDetailRepository majorDetailRepository;


    @Override
    public Page<VacanciesResponse> getAllVacancies(VacanciesFilter filter) {

        Specification<Vacancies> specification = VacanciesSpecification.getSpecification(filter);
        return vacanciesRepository.findAll(specification, filter.getPagination().getPageAndSort()).map(vacanciesMapper::entityToVacanciesResponse);
    }

    @Override
    public VacanciesResponse findOne(Long id) {

        Optional<Vacancies> vacancies = vacanciesRepository.findById(id);
        return vacancies.map(vacanciesMapper::entityToVacanciesResponse).orElse(null);
    }

    @Override
    public void deleteVacancies(Long id) {

        Optional<Vacancies> vacancies = vacanciesRepository.findById(id);
        vacancies.ifPresent(vacanciesRepository::delete);
    }

    @Override
    public void updateVacancies(VacanciesRequest request) {
        Optional<Vacancies> vacancies = vacanciesRepository.findById(request.getVacanciesId());

        if (vacancies.isPresent()) {
            vacanciesMapper.updateEntity(vacancies.get(), request);
            LocalDate localDate = LocalDate.now();
            Date date = java.sql.Date.valueOf(localDate);
            vacancies.get().setUpdatedDate(date);
            vacanciesRepository.save(vacancies.get());

        }
    }

    @Override
    public void createVacancies(VacanciesRequest request) {

        LocalDate localDate = LocalDate.now();
        Date date = java.sql.Date.valueOf(localDate);

        Vacancies vacancies = vacanciesMapper.vacanciesRequestToEntity(request);
        Major major = majorRepository.findById(request.getMajorId()).orElse(null);
        MajorDetail majorDetail = majorDetailRepository.findById(request.getMajorDetailId()).orElse(null);

        vacancies.setMajor(major);
        vacancies.setMajorDetail(majorDetail);

        vacancies.setCreatedDate(date);

        vacanciesRepository.save(vacancies);
    }

}
