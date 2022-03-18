package fpt.com.fresher.recruitmentmanager.object.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResetPasswordRequest {

    @NotNull
    String token;

    @NotNull
    String password;

    String rePassword;
}
