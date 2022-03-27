package fpt.com.fresher.recruitmentmanager.object.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequest {

    private Long id;

    @NotBlank(message = "blank")
    private String text;

    private Boolean isCorrect;
}
