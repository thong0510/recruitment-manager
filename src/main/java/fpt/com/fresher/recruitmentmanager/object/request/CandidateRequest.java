package fpt.com.fresher.recruitmentmanager.object.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
public class CandidateRequest {

    private String candidateName;

    private String phone;

    private String gender;

    private String email;

    private String experience;

    @NotEmpty(message = "empty")
    private List<Long> listOfSkill;

    private MultipartFile imageFile;

    private String status;

}
