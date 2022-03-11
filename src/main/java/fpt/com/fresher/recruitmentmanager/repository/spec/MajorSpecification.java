package fpt.com.fresher.recruitmentmanager.repository.spec;

import fpt.com.fresher.recruitmentmanager.object.entity.Candidates;
import fpt.com.fresher.recruitmentmanager.object.entity.Major;
import fpt.com.fresher.recruitmentmanager.object.filter.CandidateFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.MajorFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public final class MajorSpecification {
    public static Specification<Major> getSpecification(MajorFilter filter) {
        return Specification.where(hasMajorId(filter.getMajorId()))
                .and(hasMajorName(filter.getMajorName()));
    }

    private static Specification<Major> hasMajorId(Long majorId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(majorId)
                        ? builder.conjunction()
                        : builder.equal(root.get("majorId"), majorId);
    }

    private static Specification<Major> hasMajorName(String majorName) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(majorName)
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("majorName")), "%" + majorName.toLowerCase() + "%");
    }
}
