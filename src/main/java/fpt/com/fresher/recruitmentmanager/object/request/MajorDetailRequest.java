package fpt.com.fresher.recruitmentmanager.object.request;

import fpt.com.fresher.recruitmentmanager.object.entity.Major;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Getter
@Setter
public class MajorDetailRequest {

    private Long majorDetailId;

    @NotBlank
    private String majorDetailName;

    private Long majorId;
}
