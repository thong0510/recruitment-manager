package fpt.com.fresher.recruitmentmanager.object.filter;

import fpt.com.fresher.recruitmentmanager.object.entity.Major;
import fpt.com.fresher.recruitmentmanager.object.entity.Vacancies;
import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruitmentFilter {

    private Long recruitmentId;

    private float fromSalary;

    private float toSalary;

    private String dateStart;

    private String dateEnd;

    private String majorName;

    private String vacanciesName;

    private Pagination pagination = new Pagination(10);

}
