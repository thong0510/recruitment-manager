package fpt.com.fresher.recruitmentmanager.repository.spec;

import fpt.com.fresher.recruitmentmanager.object.contant.DifficultyLevel;
import fpt.com.fresher.recruitmentmanager.object.entity.Question;
import fpt.com.fresher.recruitmentmanager.object.filter.QuestionFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public final class QuestionSpecification {

    public static Specification<Question> getSpecification(Long quizId, QuestionFilter filter) {
        return Specification.where(hasQuizId(quizId))
                            .and(hasTitle(filter.getTitle()))
                            .and(hasTagId(filter.getTagId()))
                            .and(hasDifficultyLevel(filter.getLevel()))
                            .and(hasDifficultyId(filter.getDifficultyId()));
    }

    private static Specification<Question> hasQuizId(Long quizId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(quizId)
                ? builder.conjunction()
                : builder.equal(root.get("quiz").get("id"), quizId);
    }

    private static Specification<Question> hasTitle(String title) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(title)
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(
                          criteriaBuilder.lower(root.get("title")), "%"+ title.toLowerCase() + "%");
    }

    private static Specification<Question> hasTagId(Long tagId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(tagId) || tagId <= 0
                ? builder.conjunction()
                : builder.equal(root.get("tag").get("id"), tagId);
    }

    private static Specification<Question> hasDifficultyLevel(DifficultyLevel level) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(level)
                        ? builder.conjunction()
                        : builder.equal(root.get("difficulty").get("level"), level);
    }

    private static Specification<Question> hasDifficultyId(Long difficultyId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(difficultyId) || difficultyId <= 0
                        ? builder.conjunction()
                        : builder.equal(root.get("difficulty").get("id"), difficultyId);
    }
}
