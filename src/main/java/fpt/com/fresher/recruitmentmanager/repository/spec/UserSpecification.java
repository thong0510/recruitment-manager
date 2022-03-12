package fpt.com.fresher.recruitmentmanager.repository.spec;

import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import fpt.com.fresher.recruitmentmanager.object.entity.Users;
import fpt.com.fresher.recruitmentmanager.object.filter.SkillFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.UserFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public final class UserSpecification {

    public static Specification<Users> getSpecification(UserFilter filter) {
        return Specification.where(hasUsername(filter.getUserName() ))
                .and(hasFullname(filter.getFullName()))
                .and(hasEmail(filter.getEmail()))
                .and(hasPhone(filter.getPhone()));

    }

    private static Specification<Users> hasUsername(String userName) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(userName)
                        ? builder.conjunction()
                        : builder.equal(root.get("userName"), userName);
    }

    private static Specification<Users> hasFullname(String fullname) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(fullname)
                        ? builder.conjunction()
                        : builder.equal(root.get("fullname"), fullname);
    }

    private static Specification<Users> hasEmail(String email) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(email)
                        ? builder.conjunction()
                        : builder.equal(root.get("email"), email);
    }

    private static Specification<Users> hasPhone(String phone) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(phone)
                        ? builder.conjunction()
                        : builder.equal(root.get("phone"), phone);
    }


}
