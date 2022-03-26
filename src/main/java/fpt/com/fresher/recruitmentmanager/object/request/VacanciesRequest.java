package fpt.com.fresher.recruitmentmanager.object.request;

import fpt.com.fresher.recruitmentmanager.object.entity.Major;
import fpt.com.fresher.recruitmentmanager.object.entity.MajorDetail;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
public class VacanciesRequest {

    private Long vacanciesId;

    private Integer quantity;

    private String description;

    private Long majorId;

    private Long majorDetailId;
}
