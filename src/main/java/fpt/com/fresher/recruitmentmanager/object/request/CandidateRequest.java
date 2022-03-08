package fpt.com.fresher.recruitmentmanager.object.request;

import fpt.com.fresher.recruitmentmanager.object.contant.enums.Gender;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
public class CandidateRequest {

    private Long candidateId;

    @NotBlank
    private String candidateName;

    @NotBlank
    private String phone;

    @NotBlank
    private String gender;

    @NotBlank
    private String cardId;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String experience;

    @NotEmpty(message = "empty")
    private List<Long> listOfSkill;

    private MultipartFile imageFile;

    private String status;

}
