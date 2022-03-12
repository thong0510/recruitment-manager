package fpt.com.fresher.recruitmentmanager.object.request;

import fpt.com.fresher.recruitmentmanager.object.entity.Role;
import fpt.com.fresher.recruitmentmanager.object.entity.Users;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
public class UserRequest {

    private String userName;

    private String fullName;

    private String phone;

    private String email;

    private String password;

    private List<String> listRole;


}
