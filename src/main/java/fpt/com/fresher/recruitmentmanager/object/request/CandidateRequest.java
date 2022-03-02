package fpt.com.fresher.recruitmentmanager.object.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CandidateRequest {

    private Long candidateId;

    private String candidateName;

    private String phone;

    private String gender;

    private String email;

    private String experience;

    private String photo;

    private String status;

}