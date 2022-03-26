package fpt.com.fresher.recruitmentmanager.object.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserQuizIdRequest {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer quizId;
}
