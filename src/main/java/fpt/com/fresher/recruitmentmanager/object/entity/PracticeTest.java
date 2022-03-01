package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.CommonConst;
import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Entity
@Data
public class PracticeTest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private int testId;

    @NotBlank(message = MessageConst.INVALID_NAME)
    @Column(name = "test_name", nullable = false)
    private String testName;

    @Column(name = "date_from", nullable = false)
    @DateTimeFormat(pattern = CommonConst.FORMAT_DATE)
    @Past(message = MessageConst.INVALID_DATE)
    private Date dateFrom;

    @Column(name = "date_to", nullable = false)
    @DateTimeFormat(pattern = CommonConst.FORMAT_DATE)
    @Past(message = MessageConst.INVALID_DATE)
    private Date dateTo;

    @Min(value = 15, message = MessageConst.INVALID_TIME)
    @Column(nullable = false)
    private int time;

    @Min(value = 1, message = MessageConst.INVALID_NO_OF_QUESTION)
    @Column(name = "no_of_question", nullable = false)
    private int noOfQuestion;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "practiceTest")
    private EntryTest entryTest;

    @NotNull(message = MessageConst.INVALID_QUESTION_NULL)
    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private Questions questions;

}
