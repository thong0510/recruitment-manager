package fpt.com.fresher.recruitmentmanager.repository.spec;

import fpt.com.fresher.recruitmentmanager.object.entity.Candidates;
import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import fpt.com.fresher.recruitmentmanager.object.filter.SkillFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public final class SkillSpecification {

    public static Specification<Skills> getSpecification(SkillFilter filter) {
        return Specification.where(hasSkillId(filter.getSkillId() ))
                .and(hasSkillName(filter.getSkillName()))
                .and(hasSkillDes(filter.getDescription()));

    }

    private static Specification<Skills> hasSkillId(int skillId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(skillId)
                        ? builder.conjunction()
                        : builder.equal(root.get("skillId"), skillId);
    }

    private static Specification<Skills> hasSkillName(String skillName) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(skillName)
                        ? builder.conjunction()
                        : builder.equal(root.get("skillName"), skillName);
    }

    private static Specification<Skills> hasSkillDes(String des) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(des)
                        ? builder.conjunction()
                        : builder.equal(root.get("des"), des);
    }


}
