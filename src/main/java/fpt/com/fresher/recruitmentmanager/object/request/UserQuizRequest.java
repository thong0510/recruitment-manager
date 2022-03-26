package fpt.com.fresher.recruitmentmanager.object.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UserQuizRequest {

    @NotNull(message = "null")
    private UserQuizIdRequest id;

    @NotNull(message = "null")
    private int maxScore;

    @NotNull(message = "null")
    private int remainingTime;

    @NotNull(message = "null")
    private Date recentActiveDate;

    @NotNull(message = "null")
    private Integer attempt;

    @NotNull(message = "null")
    private Integer correctAnswers;

    @NotNull(message = "null")
    private Integer wrongAnswers;

}
