package fpt.com.fresher.recruitmentmanager.object.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AnswerRequest {

    private Long id;

    @NotBlank(message = "blank")
    private String text;

    private Boolean isCorrect;
}
