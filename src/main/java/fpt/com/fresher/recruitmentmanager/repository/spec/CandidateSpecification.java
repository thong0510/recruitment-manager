package fpt.com.fresher.recruitmentmanager.repository.spec;

import fpt.com.fresher.recruitmentmanager.object.contant.enums.CandidateStatus;
import fpt.com.fresher.recruitmentmanager.object.contant.enums.Gender;
import fpt.com.fresher.recruitmentmanager.object.entity.Candidates;
import fpt.com.fresher.recruitmentmanager.object.filter.CandidateFilter;
import fpt.com.fresher.recruitmentmanager.object.response.CandidateResponse;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.Locale;

public final class CandidateSpecification {

    public static Specification<Candidates> getSpecification(CandidateFilter filter) {
        return Specification.where(hasCandidateId(filter.getCandidateId()))
                .and(hasCandidateName(filter.getCandidateName()))
                .or(hasEmail(filter.getEmail()))
                .or(hasExperience(filter.getExperience()))
                .or(hasPhone(filter.getPhone()))
                .and(hasStatus(filter.getStatus()))
                .and(hasGender(filter.getGender()));
    }

    private static Specification<Candidates> hasCandidateId(Long candidateId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(candidateId)
                        ? builder.conjunction()
                        : builder.equal(root.get("candidateId"), candidateId);
    }

    private static Specification<Candidates> hasCandidateName(String candidateName) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(candidateName)
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(
                          criteriaBuilder.lower(root.get("candidateName")), "%" + candidateName.toLowerCase() + "%");
    }

    private static Specification<Candidates> hasGender(String gender) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(gender)
                        ? builder.conjunction()
                        : builder.equal(root.get("gender"), Gender.valueOf(gender));
    }

    private static Specification<Candidates> hasEmail(String email) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(email)
                        ? builder.conjunction()
                        : builder.like(root.get("email"), "%" + email + "%");
    }

    private static Specification<Candidates> hasExperience(String experience) {
        return (root, query, criteriaBuilder) ->
                ObjectUtils.isEmpty(experience)
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(
                          criteriaBuilder.lower(root.get("experience")), "%" + experience.toLowerCase() + "%");
    }

    private static Specification<Candidates> hasPhone(String phone) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(phone)
                        ? builder.conjunction()
                        : builder.like(root.get("phone"), "%" + phone + "%");
    }

    private static Specification<Candidates> hasStatus(String status) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(status)
                        ? builder.conjunction()
                        : builder.equal(root.get("status"), CandidateStatus.valueOf(status));
    }
}
