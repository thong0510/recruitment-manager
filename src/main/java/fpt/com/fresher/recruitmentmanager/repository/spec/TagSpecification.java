package fpt.com.fresher.recruitmentmanager.repository.spec;

import fpt.com.fresher.recruitmentmanager.object.entity.Tag;
import fpt.com.fresher.recruitmentmanager.object.filter.TagFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public final class TagSpecification {
    public static Specification<Tag> getSpecification(TagFilter filter) {
        return Specification.where(hasTagName(filter.getName()));
    }

    private static Specification<Tag> hasTagName(String name) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(name)
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(
                          criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}
