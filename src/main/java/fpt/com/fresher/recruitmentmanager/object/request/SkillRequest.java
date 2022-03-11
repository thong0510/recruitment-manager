package fpt.com.fresher.recruitmentmanager.object.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
public class SkillRequest {

    private Long skillId;

    @NotBlank
    private String skillName;

    private String description;


}
