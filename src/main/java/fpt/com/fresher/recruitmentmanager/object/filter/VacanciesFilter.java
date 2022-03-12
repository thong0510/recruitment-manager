package fpt.com.fresher.recruitmentmanager.object.filter;

import fpt.com.fresher.recruitmentmanager.object.entity.Major;
import fpt.com.fresher.recruitmentmanager.object.entity.MajorDetail;
import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VacanciesFilter {

    private Long vacanciesId;

    private Integer quantity;

    private String description;

    private String majorName;

    private String majorDetailName;

    private Pagination pagination = new Pagination(10);

}
