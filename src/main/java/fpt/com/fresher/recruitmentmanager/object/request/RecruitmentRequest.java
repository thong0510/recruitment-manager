package fpt.com.fresher.recruitmentmanager.object.request;

import fpt.com.fresher.recruitmentmanager.object.entity.Major;
import fpt.com.fresher.recruitmentmanager.object.entity.Users;
import fpt.com.fresher.recruitmentmanager.object.entity.Vacancies;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
public class RecruitmentRequest {

    private Long recruitmentId;

    private int quantity;

    private float fromSalary;

    private float toSalary;

    private Date dateStart;

    private Date dateEnd;

    private Major major;

    private Vacancies vacancies;

    List<Long> listSkill;


}
