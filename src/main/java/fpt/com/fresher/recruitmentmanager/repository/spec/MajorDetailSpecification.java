package fpt.com.fresher.recruitmentmanager.repository.spec;

import fpt.com.fresher.recruitmentmanager.object.entity.Major;
import fpt.com.fresher.recruitmentmanager.object.entity.MajorDetail;
import fpt.com.fresher.recruitmentmanager.object.filter.MajorDetailFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.MajorFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public final class MajorDetailSpecification {
    public static Specification<MajorDetail> getSpecification(MajorDetailFilter filter) {
        return Specification.where(hasMajorDetailId(filter.getMajorDetailId()))
                .and(hasMajorDetailName(filter.getMajorDetailName()))
                .and(hasMajorId(filter.getMajorId()));
    }

    private static Specification<MajorDetail> hasMajorDetailId(Long majorDetailId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(majorDetailId)
                        ? builder.conjunction()
                        : builder.equal(root.get("majorDetailId"), majorDetailId);
    }

    private static Specification<MajorDetail> hasMajorId(Long majorId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(majorId)
                        ? builder.conjunction()
                        : builder.equal(root.get("major").get("majorId"), majorId);
    }

    private static Specification<MajorDetail> hasMajorDetailName(String majorDetailName) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(majorDetailName)
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("majorDetailName")), "%" + majorDetailName.toLowerCase() + "%");
    }
}
