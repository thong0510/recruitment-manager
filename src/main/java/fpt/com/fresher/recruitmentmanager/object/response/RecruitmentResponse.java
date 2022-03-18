package fpt.com.fresher.recruitmentmanager.object.response;

import fpt.com.fresher.recruitmentmanager.object.entity.Major;
import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import fpt.com.fresher.recruitmentmanager.object.entity.Users;
import fpt.com.fresher.recruitmentmanager.object.entity.Vacancies;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class RecruitmentResponse {

    private Long recruitmentId;

    private int quantity;

    private float fromSalary;

    private float toSalary;

    private Date dateStart;

    private Date dateEnd;

    private Major major;

    private Vacancies vacancies;

    private Users users;

    List<Long> listSkill;
}
