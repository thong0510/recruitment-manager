package fpt.com.fresher.recruitmentmanager.object.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {

    @NotBlank(message = "blank")
    @Size(min = 10, max = 255, message = "size(min:10,max:255)")
    private String title;

    @NotNull(message = "null")
    private Long tagId;

    @NotNull(message = "null")
    private Long difficultyId;

    private Boolean isMultiple;

    @NotNull(message = "null")
    private Long quizId;

    @NotEmpty(message = "empty")
    private List<AnswerRequest> answers = new ArrayList<>();

    private List<String> answersText;

    private List<Long> answersId;

    private List<Integer> answersIsCorrect;

    public QuestionRequest(String title, Long tagId, Long difficultyId, Boolean isMultiple, Long quizId) {
        this.title = title;
        this.tagId = tagId;
        this.difficultyId = difficultyId;
        this.isMultiple = isMultiple;
        this.quizId = quizId;
    }

    public void addAnswer(AnswerRequest answerRequest){
        answers.add(answerRequest);
    }
}
