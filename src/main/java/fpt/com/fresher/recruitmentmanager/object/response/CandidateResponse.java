package fpt.com.fresher.recruitmentmanager.object.response;

import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;

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

    private Set<Skills> listOfSkill;

}
