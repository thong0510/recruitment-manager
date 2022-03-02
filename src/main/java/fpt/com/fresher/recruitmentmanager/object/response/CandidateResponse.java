package fpt.com.fresher.recruitmentmanager.object.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

}
