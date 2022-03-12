package fpt.com.fresher.recruitmentmanager.object.response;

import fpt.com.fresher.recruitmentmanager.object.entity.Major;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MajorDetailResponse {

    private Long majorDetailId;

    private String majorDetailName;

//    private Major major;
}
