package fpt.com.fresher.recruitmentmanager.object.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class SkillResponse {

    private Long skillId;

    private String skillName;

    private String description;
}
