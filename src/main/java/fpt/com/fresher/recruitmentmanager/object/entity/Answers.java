package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Answers extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private int answerId;

    @NotBlank(message = MessageConst.INVALID_NAME)
    @Column(name = "answer_name", nullable = false)
    private String answerName;

    @Min(value = 0, message = MessageConst.INVALID_IS_CORRECT)
    @Column(name = "is_correct")
    private int isCorrect;

    @NotNull(message = MessageConst.INVALID_QUESTION_NULL)
    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private Questions questions;

}
