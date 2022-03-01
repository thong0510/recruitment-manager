package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Data
public class Questions extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private int questionId;

    @NotBlank(message = MessageConst.INVALID_NAME)
    @Column(name = "question_name", nullable = false)
    private String questionName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questions")
    private Set<PracticeTest> tests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questions")
    private Set<Answers> answers;

}
