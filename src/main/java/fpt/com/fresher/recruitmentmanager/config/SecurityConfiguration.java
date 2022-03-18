package fpt.com.fresher.recruitmentmanager.config;

import fpt.com.fresher.recruitmentmanager.object.oauth2.CustomOAuth2UserService;
import fpt.com.fresher.recruitmentmanager.object.oauth2.OAuth2LoginFailureHandler;
import fpt.com.fresher.recruitmentmanager.object.oauth2.OAuth2LoginSuccessHandler;
import fpt.com.fresher.recruitmentmanager.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Properties;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final CustomOAuth2UserService oauth2UserService;
    private final OAuth2LoginSuccessHandler oauthLoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

    public SecurityConfiguration(UserService userService,
                                 @Lazy CustomOAuth2UserService oauth2UserService,
                                 @Lazy OAuth2LoginSuccessHandler oauthLoginSuccessHandler,
                                 @Lazy OAuth2LoginFailureHandler oAuth2LoginFailureHandler) {
        this.userService = userService;
        this.oauth2UserService = oauth2UserService;
        this.oauthLoginSuccessHandler = oauthLoginSuccessHandler;
        this.oAuth2LoginFailureHandler = oAuth2LoginFailureHandler;
    }

    @Value("${spring.email.name}")
    private String username;

    @Value("${spring.email.password}")
    private String password;

//    @Autowired
//    private UserLoginFailureHandler userLoginFailureHandler;
//
//    @Autowired
//    private UserLoginSuccessHandler userLoginSuccessHandler;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager Bean
        return super.authenticationManagerBean();
    }

    @Bean
    @Lazy
    public JavaMailSender getJavaMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.timeout", 5000);
        props.put("mail.smtp.connectiontimeout", 5000);
        props.put("mail.smtp.writetimeout", 5000);

        return mailSender;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/registration/**", "/js/**", "/forgot-password/**", "/expired/**",
                                "/css/**", "/img/**", "/reset-password/**", "/test")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successForwardUrl("/home")
                .permitAll()
                .and()
                .logout()
                .deleteCookies("remember-me")
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oauth2UserService)
                .and()
                .successHandler(oauthLoginSuccessHandler)
                .failureHandler(oAuth2LoginFailureHandler);
    }

}
