package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class EntryTest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_test_id")
    private int entryTestId;

    @NotNull(message = MessageConst.INVALID_CANDIDATE_NULL)
    @OneToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "candidate_id")
    private Candidates candidates;

    @NotNull(message = MessageConst.INVALID_TEST_NULL)
    @OneToOne
    @JoinColumn(name = "test_id", referencedColumnName = "test_id")
    private PracticeTest practiceTest;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "entryTest")
    private EntryResult entryResult;

}
