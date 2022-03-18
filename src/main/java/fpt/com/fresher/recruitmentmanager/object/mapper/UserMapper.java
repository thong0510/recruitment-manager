package fpt.com.fresher.recruitmentmanager.object.mapper;

import fpt.com.fresher.recruitmentmanager.object.entity.Users;
import fpt.com.fresher.recruitmentmanager.object.request.UserRequest;
import fpt.com.fresher.recruitmentmanager.object.response.UserResponse;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    public abstract UserResponse entityToUserResponse(Users user);

    public abstract Users userRequestToEntity(UserRequest request);

    public abstract Users userResponseToEntity(UserResponse response);

    public abstract void updateEntity(@MappingTarget Users users, UserRequest request);

    @AfterMapping
    public void mappingReqPropsToEntity(@MappingTarget Users users, UserRequest request) {
        users.setPassword(passwordEncoder.encode(request.getPassword()));
    }

}
