package fpt.com.fresher.recruitmentmanager.object.response;

import lombok.Data;

import java.util.List;

@Data
public class QuestionResponse {

    private Long id;

    private String title;

    private TagResponse tag;

    private DifficultyResponse difficulty;

    private Boolean isMultiple;

    private List<AnswerResponse> answers;

    private long quizId;
}
