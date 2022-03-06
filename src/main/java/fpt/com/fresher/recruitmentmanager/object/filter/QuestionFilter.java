package fpt.com.fresher.recruitmentmanager.object.filter;

import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import lombok.Data;

@Data
public class QuestionFilter {

    private Long questionId;

    private String questionName;

    private Pagination pagination = new Pagination(10);

}
