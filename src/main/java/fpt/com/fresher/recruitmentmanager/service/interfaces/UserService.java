package fpt.com.fresher.recruitmentmanager.service.interfaces;

import fpt.com.fresher.recruitmentmanager.object.exception.TokenExpiredException;
import fpt.com.fresher.recruitmentmanager.object.filter.UserFilter;
import fpt.com.fresher.recruitmentmanager.object.request.ResetPasswordRequest;
import fpt.com.fresher.recruitmentmanager.object.request.UserRequest;
import fpt.com.fresher.recruitmentmanager.object.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Page<UserResponse> getAllUser(UserFilter filter);

    UserResponse findOne(String userName);

    void deleteUser(String userName);

    void updateUser(UserRequest request);

    void createUser(UserRequest request);

    boolean validateConcurrentUsername(String username);

    boolean validateConcurrentEmail(String email);

    boolean validateConcurrentPhone(String phone);

    String generateForgotPasswordToken(String email);

    Boolean resetPassword(ResetPasswordRequest request) throws TokenExpiredException;
}
