package fpt.com.fresher.recruitmentmanager.object.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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



}
