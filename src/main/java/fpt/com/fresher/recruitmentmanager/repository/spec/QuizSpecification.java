package fpt.com.fresher.recruitmentmanager.repository.spec;

import fpt.com.fresher.recruitmentmanager.object.contant.QuizStatus;
import fpt.com.fresher.recruitmentmanager.object.entity.Quiz;
import fpt.com.fresher.recruitmentmanager.object.filter.QuizFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public final class QuizSpecification {

    public static Specification<Quiz> getSpecification(QuizFilter filter) {
        return Specification.where(hasTitleContaining(filter.getTitle()))
                            .and(hasStatus(filter.getStatus()))
                            .and(hasCategoryName(filter.getCategoryName()))
                            .and(hasInstructor(filter.getInstructorId()));
    }


    //TODO bo phan biet hoa thuong
    private static Specification<Quiz> hasTitleContaining(String title) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(title)
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    private static Specification<Quiz> hasInstructor(Long instructorId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(instructorId)
                        ? builder.conjunction()
                        : builder.equal(root.get("instructor").get("id"), instructorId);
    }

    private static Specification<Quiz> hasStatus(String status) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(status)
                ? builder.conjunction()
                : builder.equal(root.get("status"), QuizStatus.valueOf(status));
    }

    private static Specification<Quiz> hasCategoryName(String categoryName) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(categoryName)
                ? builder.conjunction()
                : builder.equal(root.get("category").get("name"), categoryName);
    }
}
