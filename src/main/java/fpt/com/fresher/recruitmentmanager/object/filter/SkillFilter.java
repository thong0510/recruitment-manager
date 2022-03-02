package fpt.com.fresher.recruitmentmanager.object.filter;

import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import lombok.Data;

@Data
public class SkillFilter {

    private int skillId;

    private String skillName;

    private String description;

    private Pagination pagination = new Pagination(10);

}
