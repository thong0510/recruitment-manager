package fpt.com.fresher.recruitmentmanager.object.filter;

import fpt.com.fresher.recruitmentmanager.object.contant.DifficultyLevel;
import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionFilter {

    private Long id;
    private String title;
    private Long tagId;
    private DifficultyLevel level;
    private Long difficultyId;
    private Pagination pagination = new Pagination(10);
}
