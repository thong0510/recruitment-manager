package fpt.com.fresher.recruitmentmanager.service.interfaces;

import fpt.com.fresher.recruitmentmanager.object.filter.SkillFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.VacanciesFilter;
import fpt.com.fresher.recruitmentmanager.object.request.SkillRequest;
import fpt.com.fresher.recruitmentmanager.object.request.VacanciesRequest;
import fpt.com.fresher.recruitmentmanager.object.response.SkillResponse;
import fpt.com.fresher.recruitmentmanager.object.response.VacanciesResponse;
import org.springframework.data.domain.Page;

public interface VacanciesService {

    Page<VacanciesResponse> getAllVacancies(VacanciesFilter filter);

    VacanciesResponse findOne(Long id);

    void deleteVacancies(Long id);

    void updateVacancies(VacanciesRequest request);

    void createVacancies(VacanciesRequest request);

}
