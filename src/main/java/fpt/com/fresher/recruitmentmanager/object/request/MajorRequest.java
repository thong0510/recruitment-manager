package fpt.com.fresher.recruitmentmanager.object.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Getter
@Setter
public class MajorRequest {
    private Long majorId;

    @NotBlank
    private String majorName;

    private Date createDate;

    private Date updateDate;

}
