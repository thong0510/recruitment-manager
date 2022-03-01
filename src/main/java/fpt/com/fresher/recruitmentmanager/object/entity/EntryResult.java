package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class EntryResult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_result_id")
    private int entryResultId;

    @Min(value = 0, message = MessageConst.INVALID_IS_CORRECT)
    @Column(name = "no_correct")
    private int noCorrect;

    @Min(value = 0, message = MessageConst.INVALID_NO_CORRECT)
    @Column(name = "no_incorrect")
    private int noIncorrect;

    @Min(value = 0, message = MessageConst.INVALID_UN_CORRECT)
    @Column(name = "no_unanswered")
    private int noUnanswered;

    @Min(value = 0, message = MessageConst.INVALID_SCORE)
    @Column
    private int score;

    @NotBlank(message = MessageConst.INVALID_RANKING)
    @Column
    private String ranking;

    @NotNull(message = MessageConst.INVALID_ENTRY_TEST_NULL)
    @OneToOne
    @JoinColumn(name = "entry_test_id", referencedColumnName = "entry_test_id")
    private EntryTest entryTest;

}
