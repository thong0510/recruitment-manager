package fpt.com.fresher.recruitmentmanager.object.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
public class MajorResponse {
    private Long majorId;

    private String majorName;

    private Date createDate;

    private Date updateDate;

}
