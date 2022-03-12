package fpt.com.fresher.recruitmentmanager.repository.spec;

import fpt.com.fresher.recruitmentmanager.object.entity.Major;
import fpt.com.fresher.recruitmentmanager.object.entity.Vacancies;
import fpt.com.fresher.recruitmentmanager.object.filter.MajorFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.VacanciesFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public final class VacanciesSpecification {
    public static Specification<Vacancies> getSpecification(VacanciesFilter filter) {
        return Specification.where(hasVacanciesId(filter.getVacanciesId()))
                .and(hasMajorName(filter.getMajorName()))
                .and(hasMajorDetailName(filter.getMajorDetailName()));
    }

    private static Specification<Vacancies> hasVacanciesId(Long vacanciesId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(vacanciesId)
                        ? builder.conjunction()
                        : builder.equal(root.get("vacanciesId"), vacanciesId);
    }

    private static Specification<Vacancies> hasMajorName(String majorName) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(majorName)
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("majorName")), "%" + majorName.toLowerCase() + "%");
    }

    private static Specification<Vacancies> hasMajorDetailName(String majorDetailName) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(majorDetailName)
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("majorDetailName")), "%" + majorDetailName.toLowerCase() + "%");
    }
}
