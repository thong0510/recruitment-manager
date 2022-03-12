package fpt.com.fresher.recruitmentmanager.object.response;

import fpt.com.fresher.recruitmentmanager.object.entity.Role;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private String userName;

    private String fullName;

    private String phone;

    private String email;

    private String password;

    private Set<Role> roles;



}
