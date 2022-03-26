package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.QuizStatus;
import lombok.*;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String image;

    @Enumerated(EnumType.STRING)
    private QuizStatus status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Question> questions;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Users instructor;

    @PostPersist
    public void postPersist() {
        status = QuizStatus.DRAFT;
    }

    public void addQuestion(Question question) {
        if (ObjectUtils.isEmpty(questions)) questions = new HashSet<>();
        questions.add(question);
        question.setQuiz(this);
    }
}
