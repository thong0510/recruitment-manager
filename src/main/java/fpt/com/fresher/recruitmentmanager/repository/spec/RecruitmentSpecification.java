package fpt.com.fresher.recruitmentmanager.repository.spec;

import fpt.com.fresher.recruitmentmanager.object.entity.Recruitment;
import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import fpt.com.fresher.recruitmentmanager.object.filter.RecruitmentFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.SkillFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public final class RecruitmentSpecification {

    public static Specification<Recruitment> getSpecification(RecruitmentFilter filter) {
        return Specification.where(hasRecruitmentId(filter.getRecruitmentId() ))
                .and(hasDateEnd(filter.getDateEnd()))
                .and(hasDateStart(filter.getDateStart()))
                .and(hasFromSalary(filter.getFromSalary()))
                .and(hasToSalary(filter.getToSalary()))
                .and(hasVacanciesName(filter.getVacanciesName()))
                .and(hasMajorName(filter.getMajorName()));

    }

    private static Specification<Recruitment> hasRecruitmentId(Long recruitmentId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(recruitmentId)
                        ? builder.conjunction()
                        : builder.equal(root.get("recruitmentId"), recruitmentId);
    }

    private static Specification<Recruitment> hasDateEnd(String dateEnd) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(dateEnd)
                        ? builder.conjunction()
                        : builder.equal(root.get("dateEnd"), dateEnd);
    }

    private static Specification<Recruitment> hasDateStart(String dateStart) {
            return (root, query, builder) ->
                    ObjectUtils.isEmpty(dateStart)
                            ? builder.conjunction()
                            : builder.equal(root.get("dateStart"), dateStart);
        }

    private static Specification<Recruitment> hasFromSalary(float fromSalary) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(fromSalary)
                        ? builder.conjunction()
                        : builder.equal(root.get("fromSalary"), fromSalary);
    }

    private static Specification<Recruitment> hasToSalary(float toSalary) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(toSalary)
                        ? builder.conjunction()
                        : builder.equal(root.get("toSalary"), toSalary);
    }

    private static Specification<Recruitment> hasVacanciesName(String vacanciesName) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(vacanciesName)
                        ? builder.conjunction()
                        : builder.equal(root.get("vacanciesName"), vacanciesName);
    }

     private static Specification<Recruitment> hasMajorName(String majorName) {
            return (root, query, builder) ->
                    ObjectUtils.isEmpty(majorName)
                            ? builder.conjunction()
                            : builder.equal(root.get("majorName"), majorName);
        }


}
