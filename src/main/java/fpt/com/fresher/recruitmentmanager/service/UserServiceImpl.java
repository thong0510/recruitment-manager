package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.Users;
import fpt.com.fresher.recruitmentmanager.object.filter.UserFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.UserMapper;
import fpt.com.fresher.recruitmentmanager.object.request.UserRequest;
import fpt.com.fresher.recruitmentmanager.object.response.UserResponse;
import fpt.com.fresher.recruitmentmanager.repository.UserRepository;
import fpt.com.fresher.recruitmentmanager.repository.spec.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Page<UserResponse> getAllUser(UserFilter filter) {
        Specification<Users> specification = UserSpecification.getSpecification(filter);
        return userRepository.findAll(specification, filter.getPagination().getPageAndSort()).map(userMapper::entityToUserResponse);
    }

    @Override
    public UserResponse findOne(String userName) {
        Optional<Users> user = userRepository.findById(userName);
        return user.map(userMapper::entityToUserResponse).orElse(null);
    }

    @Override
    public void deleteUser(String userName) {
        Optional<Users> user = userRepository.findById(userName);
        user.ifPresent(userRepository::delete);
    }

    @Override
    public void updateUser(UserRequest request) {
        Optional<Users> user = userRepository.findById(request.getUserName());

        if (user.isPresent()) {
            userMapper.updateEntity(user.get(), request);
            LocalDate localDate = LocalDate.now();
            Date date = java.sql.Date.valueOf(localDate);
            user.get().setUpdatedDate(date);
            userRepository.save(user.get());

        }
    }

    @Override
    public void createUser(UserRequest request) {
        LocalDate localDate = LocalDate.now();
        Date date = java.sql.Date.valueOf(localDate);

        Users user = userMapper.userRequestToEntity(request);
        user.setCreatedDate(date);

        userRepository.save(user);
    }
}
