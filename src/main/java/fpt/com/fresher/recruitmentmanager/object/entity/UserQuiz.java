package fpt.com.fresher.recruitmentmanager.object.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users_quiz")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserQuiz {

    @EmbeddedId
    private UserQuizId id;

    @ManyToOne
    @JoinColumn(name = "user_name", insertable = false, updatable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Quiz quiz;

    private Integer maxScore;

    private Integer remainingTime;

    @Column(name = "recent_active_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recentActiveDate;

    private Integer attempt;

    private Integer correctAnswers;

    private Integer wrongAnswers;
}
