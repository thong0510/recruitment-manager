package fpt.com.fresher.recruitmentmanager.object.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SQLDelete(sql = "UPDATE questions SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String title;

    @OneToMany(mappedBy = "question") @ToString.Exclude
    private Set<Answer> answers;

    private Boolean isMultiple = false;

    @ManyToOne @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne @JoinColumn(name = "difficulty_id")
    private Difficulty difficulty;

    private Boolean deleted = false;

    @ManyToOne @JoinColumn(name = "quiz_id") private Quiz quiz;

    public void addAnswer(Answer answer) {
        if (ObjectUtils.isEmpty(answers)) answers = new HashSet<>();
        answers.add(answer);
    }
}
