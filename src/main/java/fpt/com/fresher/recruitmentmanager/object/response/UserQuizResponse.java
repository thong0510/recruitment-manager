package fpt.com.fresher.recruitmentmanager.object.response;

import lombok.Data;

import java.util.Date;

@Data
public class UserQuizResponse {
    private Long userId;

    private String fullName;

    private String username;

    private Long quizId;

    private String quizTitle;

    private String quizImage;

    private Integer maxScore;

    private Integer remainingTime;

    private Date recentActiveDate;

    private String dateToString;

    private Integer attempt;

    private Integer correctAnswers;

    private Integer wrongAnswers;
}
