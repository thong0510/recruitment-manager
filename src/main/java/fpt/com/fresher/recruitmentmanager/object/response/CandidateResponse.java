package fpt.com.fresher.recruitmentmanager.object.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
public class CandidateResponse {

    private Long candidateId;

    private String candidateName;

    private String phone;

    private String gender;

    private String cardId;

    private String email;

    private String experience;

    private String photo;

    private String status;

    private List<Long> listOfSkill;

    private MultipartFile imageFile;

}
