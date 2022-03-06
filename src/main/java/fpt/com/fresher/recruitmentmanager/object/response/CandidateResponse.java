package fpt.com.fresher.recruitmentmanager.object.response;

import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Getter
@Setter
public class CandidateResponse {

    private Long candidateId;

    private String candidateName;

    private String phone;

    private String gender;

    private String email;

    private String experience;

    private String photo;

    private String status;

    private List<SkillResponse> skills;

    private MultipartFile imageFile;

}
