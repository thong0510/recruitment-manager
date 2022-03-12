package fpt.com.fresher.recruitmentmanager.object.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
public class UserRequest {

    private String userName;

    private String fullName;

    private String phone;

    private String email;

    private String password;


}
