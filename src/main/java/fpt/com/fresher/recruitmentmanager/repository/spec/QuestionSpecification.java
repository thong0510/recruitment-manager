package fpt.com.fresher.recruitmentmanager.repository.spec;

import fpt.com.fresher.recruitmentmanager.object.entity.Questions;
import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import fpt.com.fresher.recruitmentmanager.object.filter.QuestionFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.SkillFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public final class QuestionSpecification {

    public static Specification<Questions> getSpecification(QuestionFilter filter) {
        return Specification.where(hasQuestionId(filter.getQuestionId() ))
                .and(hasQuestionName(filter.getQuestionName()));

    }

    private static Specification<Questions> hasQuestionId(Long questionId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(questionId)
                        ? builder.conjunction()
                        : builder.equal(root.get("questionId"), questionId);
    }

    private static Specification<Questions> hasQuestionName(String questionName) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(questionName)
                        ? builder.conjunction()
                        : builder.equal(root.get("questionName"), questionName);
    }


}
