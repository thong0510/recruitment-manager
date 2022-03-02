package fpt.com.fresher.recruitmentmanager.repository.spec;

import fpt.com.fresher.recruitmentmanager.object.entity.Candidates;
import fpt.com.fresher.recruitmentmanager.object.filter.CandidateFilter;
import fpt.com.fresher.recruitmentmanager.object.response.CandidateResponse;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public final class CandidateSpecification {

    public static Specification<Candidates> getSpecification(CandidateFilter filter) {
        return Specification.where(hasCandidateId(filter.getCandidateId()))
                .and(hasCandidateName(filter.getCandidateName()))
                .and(hasGender(filter.getGender()))
                .and(hasExperience(filter.getExperience()))
                .and(hasEmail(filter.getEmail()))
                .and(hasPhone(filter.getPhone()))
                .and(hasStatus(filter.getStatus()));
    }

    private static Specification<Candidates> hasCandidateId(Long candidateId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(candidateId)
                        ? builder.conjunction()
                        : builder.equal(root.get("candidateId"), candidateId);
    }

    private static Specification<Candidates> hasCandidateName(String candidateName) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(candidateName)
                        ? builder.conjunction()
                        : builder.equal(root.get("candidateName"), candidateName);
    }

    private static Specification<Candidates> hasGender(String gender) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(gender)
                        ? builder.conjunction()
                        : builder.equal(root.get("gender"), gender);
    }

    private static Specification<Candidates> hasEmail(String email) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(email)
                        ? builder.conjunction()
                        : builder.equal(root.get("email"), email);
    }

    private static Specification<Candidates> hasExperience(String experience) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(experience)
                        ? builder.conjunction()
                        : builder.equal(root.get("experience"), experience);
    }

    private static Specification<Candidates> hasPhone(String phone) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(phone)
                        ? builder.conjunction()
                        : builder.equal(root.get("phone"), phone);
    }

    private static Specification<Candidates> hasStatus(String status) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(status)
                        ? builder.conjunction()
                        : builder.equal(root.get("status").get("statusId"), status);
    }
}
