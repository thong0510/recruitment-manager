package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.contant.enums.SystemRole;
import fpt.com.fresher.recruitmentmanager.object.entity.Role;
import fpt.com.fresher.recruitmentmanager.object.entity.Users;
import fpt.com.fresher.recruitmentmanager.object.exception.ResourceNotFoundException;
import fpt.com.fresher.recruitmentmanager.object.exception.TokenExpiredException;
import fpt.com.fresher.recruitmentmanager.object.filter.UserFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.UserMapper;
import fpt.com.fresher.recruitmentmanager.object.request.ResetPasswordRequest;
import fpt.com.fresher.recruitmentmanager.object.request.UserRequest;
import fpt.com.fresher.recruitmentmanager.object.response.UserResponse;
import fpt.com.fresher.recruitmentmanager.repository.RoleRepository;
import fpt.com.fresher.recruitmentmanager.repository.UserRepository;
import fpt.com.fresher.recruitmentmanager.repository.spec.UserSpecification;
import fpt.com.fresher.recruitmentmanager.service.interfaces.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final RedissonClient redissonClient;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           RoleRepository roleRepository,
                           RedissonClient redissonClient,
                           @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.redissonClient = redissonClient;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void init() {
        userEmailMap = redissonClient.getMapCache("USER_EMAIL_MAP");
        if (userRepository.count() == 0) {
            Users admin = new Users();
            admin.setUserName("admin");
            admin.setEmail("admin123@gmail.com");
            admin.setPhone("0976809331");
            admin.setPassword(new BCryptPasswordEncoder().encode("123456"));
            admin.setFullName("ADMIN USER");
            admin.setStatus(true);
            admin.setRoles(List.of(roleRepository.findByRole(SystemRole.ADMIN)));
            userRepository.save(admin);
        }
    }

    private RMapCache<String, String> userEmailMap;
    private final TimeUnit timeUnit = TimeUnit.MINUTES;

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

    public Users findUserByUsername(String username) {
        return userRepository.findByUserName(username).orElseThrow(
                () -> new BadCredentialsException("User " + username + " not found"));
    }

    @Override
    public void updateUser(UserRequest request) {
        Optional<Users> user = userRepository.findById(request.getUserName());

        if (user.isPresent()) {
            userMapper.updateEntity(user.get(), request);

            Set<Role> roles = new HashSet<>();
            user.get().setRoles(roles);

            Set<Role> roles1 = new HashSet<>();

            for (String r : request.getListRole()) {
                Role role = roleRepository.findByRole(SystemRole.valueOf(r));
                roles1.add(role);
            }

            LocalDate localDate = LocalDate.now();
            Date date = java.sql.Date.valueOf(localDate);

            user.get().setUpdatedDate(date);
            user.get().setRoles(roles1);

            userRepository.save(user.get());

        }
    }

    @Override
    public void createUser(UserRequest request) {
        LocalDate localDate = LocalDate.now();
        Date date = java.sql.Date.valueOf(localDate);

        Users user = userMapper.userRequestToEntity(request);
        user.setCreatedDate(date);

        Set<Role> roles = new HashSet<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            roles.add(roleRepository.findByRole(SystemRole.USER));
        } else {
            for (String r : request.getListRole()) {
                Role role = roleRepository.findByRole(SystemRole.valueOf(r));
                roles.add(role);
            }
        }

        user.setRoles(roles);

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.findByUserName(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("UserName Is Not Exits");
        }

        Collection<? extends GrantedAuthority> grantedAuthorities =
                !ObjectUtils.isEmpty(user.get().getRoles())
                        ? user.get().getRoles().stream().map(o ->
                          new SimpleGrantedAuthority(o.getRole().getValue())).collect(Collectors.toList())
                        : null;

        return new org.springframework.security.core.userdetails.User(
                user.get().getUsername(), user.get().getPassword(), user.get().isEnabled(), true, true, true, grantedAuthorities
        );
    }

    @Override
    public boolean validateConcurrentUsername(String username) {
        return !userRepository.existsByUserName(username);
    }

    @Override
    public boolean validateConcurrentPhone(String phone) {
        return !userRepository.existsByPhone(phone);
    }

    @Override
    public boolean validateConcurrentEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public String generateForgotPasswordToken(String email) {

        if (validateConcurrentEmail(email)) {
            String token = UUID.randomUUID().toString();
            userEmailMap.fastPut(token, email, 15, timeUnit);
            return token;
        }
        return "";

    }

    @Override
    public Boolean resetPassword(ResetPasswordRequest request) throws TokenExpiredException {

        String email = verifyExpirationResetPassword(request.getToken());
        Optional<Users> user = userRepository.findByEmail(email);

        if (user.isPresent()) {

            user.get().setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user.get());

            userEmailMap.remove(request.getToken());
        }
        return true;
    }

    private String verifyExpirationResetPassword(String token) throws TokenExpiredException {
        if (userEmailMap.remainTimeToLive(token) > 0) {
            return userEmailMap.get(token);
        } else {
            userEmailMap.remove(token);
            throw new TokenExpiredException("Refresh token has been expired");
        }
    }

}
