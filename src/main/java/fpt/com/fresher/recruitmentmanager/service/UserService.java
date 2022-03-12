package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.filter.SkillFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.UserFilter;
import fpt.com.fresher.recruitmentmanager.object.request.SkillRequest;
import fpt.com.fresher.recruitmentmanager.object.request.UserRequest;
import fpt.com.fresher.recruitmentmanager.object.response.SkillResponse;
import fpt.com.fresher.recruitmentmanager.object.response.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserResponse> getAllUser(UserFilter filter);

    UserResponse findOne(String userName);

    void deleteUser(String userName);

    void updateUser(UserRequest request);

    void createUser(UserRequest request);

}
