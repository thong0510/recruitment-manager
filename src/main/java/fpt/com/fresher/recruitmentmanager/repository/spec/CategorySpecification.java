package fpt.com.fresher.recruitmentmanager.repository.spec;

import fpt.com.fresher.recruitmentmanager.object.entity.Category;
import fpt.com.fresher.recruitmentmanager.object.filter.CategoryFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public final class CategorySpecification {

    public static Specification<Category> getSpecification(CategoryFilter filter) {
        return Specification.where(hasCategoryName(filter.getName()));
    }

    private static Specification<Category> hasCategoryName(String name) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(name)
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(
                          criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

}
