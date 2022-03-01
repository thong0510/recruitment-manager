package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private int reportId;

    @NotBlank(message = MessageConst.INVALID_EVALUATE)
    @Column(nullable = false)
    private String evaluate;

    @Column
    private String note;

    @NotNull(message = MessageConst.INVALID_USER_NULL)
    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    private Users users;

    @NotNull(message = MessageConst.INVALID_CANDIDATE_NULL)
    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "candidate_id")
    private Candidates candidates;

}
