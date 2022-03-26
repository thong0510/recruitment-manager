package fpt.com.fresher.recruitmentmanager.object.response;

import lombok.Data;

@Data
public class AnswerResponse {

    private Long id;
    private String text;
    private Boolean isCorrect;
}
