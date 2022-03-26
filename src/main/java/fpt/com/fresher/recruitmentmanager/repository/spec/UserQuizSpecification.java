package fpt.com.fresher.recruitmentmanager.repository.spec;

import fpt.com.fresher.recruitmentmanager.object.entity.UserQuiz;
import fpt.com.fresher.recruitmentmanager.object.filter.UserQuizFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.Date;

public class UserQuizSpecification {

    public static Specification<UserQuiz> getSpecification(UserQuizFilter filter) {
        return Specification.where(hasQuizId(filter.getId().getQuizId()))
                .and(hasUserId(filter.getId().getUserId()))
                .and(hasMaxScore(filter.getMaxScore()))
                .and(hasRemainingTime(filter.getRemainingTime()))
                .and(hasRecentActiveDate(filter.getRecentActiveDate()));
    }

    private static Specification<UserQuiz> hasQuizId(Integer quizId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(quizId)
                        ? builder.conjunction()
                        : builder.equal(root.get("id").get("quizId"), quizId);
    }

    private static Specification<UserQuiz> hasUserId(Integer userId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(userId)
                        ? builder.conjunction()
                        : builder.equal(root.get("id").get("userId"), userId);
    }

    private static Specification<UserQuiz> hasMaxScore(Integer maxScore) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(maxScore)
                        ? builder.conjunction()
                        : builder.equal(root.get("maxScore"), maxScore);
    }

    private static Specification<UserQuiz> hasRemainingTime(Integer remainingTime) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(remainingTime)
                        ? builder.conjunction()
                        : builder.equal(root.get("remainingTime"), remainingTime);
    }

    private static Specification<UserQuiz> hasRecentActiveDate(Date recentActiveDate) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(recentActiveDate)
                        ? builder.conjunction()
                        : builder.equal(root.get("recentActiveDate"), recentActiveDate);
    }
}
