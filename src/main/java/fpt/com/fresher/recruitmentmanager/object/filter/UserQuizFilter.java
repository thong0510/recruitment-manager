package fpt.com.fresher.recruitmentmanager.object.filter;

import fpt.com.fresher.recruitmentmanager.object.entity.UserQuizId;
import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import lombok.Data;

import java.util.Date;

@Data
public class UserQuizFilter {

    private UserQuizId id;

    private Integer maxScore;

    private Integer remainingTime;

    private Date recentActiveDate;

    private Pagination pagination = new Pagination(10);

}
