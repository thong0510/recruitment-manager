package fpt.com.fresher.recruitmentmanager.controller;

import fpt.com.fresher.recruitmentmanager.object.contant.CommonConst;
import fpt.com.fresher.recruitmentmanager.object.exception.TokenExpiredException;
import fpt.com.fresher.recruitmentmanager.object.request.ResetPasswordRequest;
import fpt.com.fresher.recruitmentmanager.object.request.UserRequest;
import fpt.com.fresher.recruitmentmanager.service.SendEmailService;
import fpt.com.fresher.recruitmentmanager.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthenticationController {

    private final UserService userService;
    private final SendEmailService sendEmailService;

    @GetMapping("login")
    public String login(Model model,
                        @RequestParam(value = "error", defaultValue = "") String error,
                        @RequestParam(value = "success", defaultValue = "") String success) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!StringUtils.isEmpty(error)) {
            model.addAttribute("error", error);
        }

        if (!StringUtils.isEmpty(success)) {
            model.addAttribute("info", success);
        }

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "/authenticate/Login";
        } else {
            return "common/Home";
        }

    }

    @GetMapping("signup")
    public String signUp(UserRequest user,
                         Model model,
                         @RequestParam(value = "message", defaultValue = "") String message) {

        model.addAttribute("user", user);
        if (!StringUtils.isEmpty(message)) {
            model.addAttribute("error", message);
        }
        return "/authenticate/SignUp";

    }

    @PostMapping("/signup")
    public String signUpNewUser(@ModelAttribute @Valid UserRequest user,
                                Model model) {

        if (!userService.validateConcurrentUsername(user.getUserName())) {
            return "redirect:/auth/signup?message=UserName Has Already Registered!";
        }

        if (userService.validateConcurrentEmail(user.getEmail())) {
            return "redirect:/auth/signup?message=Email Has Already Registered!!";
        }

        if (!userService.validateConcurrentPhone(user.getPhone())) {
            return "redirect:/auth/signup?message=Phone Has Already Registered!!";
        }

        userService.createUser(user);

        return "redirect:/auth/login?success=Register successfully!";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "/authenticate/ForgotPassword";
    }

    @PostMapping("/forgot-password")
    public String sendEmailForgotPassword(@RequestParam String email,
                                          Model model) throws MessagingException, UnsupportedEncodingException {

        String token = userService.generateForgotPasswordToken(email);
        if (!StringUtils.isEmpty(token)) {
            String url = CommonConst.RESET_PASSWORD + "/" + token;
            sendEmailService.sendEmail(email, url);
            model.addAttribute("info", "Send Email Successful");
        } else {
            model.addAttribute("error", "Email didn't register. Please try the other email !!!");
        }
        return "/authenticate/ForgotPassword";

    }

    @GetMapping("/reset-password/{token}")
    public String checkTokenResetPassword(@PathVariable String token, Model model) {
        model.addAttribute("token", token);
        model.addAttribute("resetPassword", new ResetPasswordRequest());
        return "/authenticate/ResetPassword";

    }

    @PostMapping("/reset-password")
    public String resetPasswordByToken(@ModelAttribute @Valid ResetPasswordRequest requestBody,
                                       Model model) {

        try {
            Boolean result = userService.resetPassword(requestBody);
            if (result) {
                model.addAttribute("info", "Change New Password Successful");
                return "/authenticate/Login";
            }

        } catch (TokenExpiredException ex) {
            model.addAttribute("error", "Email Expired. Please Resend Email Again.");
        }
        return "/authenticate/ForgotPassword";

    }

}
