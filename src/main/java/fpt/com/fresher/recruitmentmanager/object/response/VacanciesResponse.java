package fpt.com.fresher.recruitmentmanager.object.response;


import fpt.com.fresher.recruitmentmanager.object.entity.Major;
import fpt.com.fresher.recruitmentmanager.object.entity.MajorDetail;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class VacanciesResponse {

    private Long vacanciesId;

    private Integer quantity;

    private String description;

    private Major major;

    private MajorDetail majorDetail;
}
