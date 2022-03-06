package fpt.com.fresher.recruitmentmanager.object.request;

import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class QuestionRequest {

    private Long questionId;

    private String questionName;


}
