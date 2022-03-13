package fpt.com.fresher.recruitmentmanager.object.oauth2;

import fpt.com.fresher.recruitmentmanager.object.contant.enums.SystemRole;
import fpt.com.fresher.recruitmentmanager.object.entity.Role;
import fpt.com.fresher.recruitmentmanager.object.entity.Users;
import fpt.com.fresher.recruitmentmanager.object.oauth2.user.OAuth2UserInfo;
import fpt.com.fresher.recruitmentmanager.object.oauth2.user.OAuth2UserInfoFactory;
import fpt.com.fresher.recruitmentmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Lazy
    private final UserRepository userRepository;

    @Lazy
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
        String oauth2ClientName = oauth2User.getOauth2ClientName();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                oauth2ClientName, oauth2User.getAttributes());

        Optional<Users> users;

        if (!userRepository.existsByEmail(oAuth2UserInfo.getEmail())) {
            users = Optional.of(registerNewUser(oAuth2UserInfo));
        } else {
            users = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        }
        
        if (users.isPresent()) {

            Users user = users.get();

            Collection<? extends GrantedAuthority> grantedAuthorities =
                                        !ObjectUtils.isEmpty(user.getRoles())
                                                ? user.getRoles().stream().map(o ->
                                                  new SimpleGrantedAuthority(o.getRole().getValue())).collect(Collectors.toList())
                                                : null;

            UsernamePasswordAuthenticationToken authenticationUpdate =
                    new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);

            SecurityContextHolder.getContext().setAuthentication(authenticationUpdate);
        }

        response.sendRedirect("/");

    }

    private Users registerNewUser(OAuth2UserInfo oAuth2UserInfo) {

        Users user = Users.builder()
                            .userName(oAuth2UserInfo.getUsername())
                            .fullName(oAuth2UserInfo.getFirstName() + " " + oAuth2UserInfo.getLastName())
                            .email(oAuth2UserInfo.getEmail())
                            .photo(oAuth2UserInfo.getImageUrl())
                            .roles(Collections.singleton(new Role(SystemRole.USER)))
                            .password(passwordEncoder.encode("123456"))
                            .build();

        return userRepository.save(user);
    }

}
