package fpt.com.fresher.recruitmentmanager.object.entity;


import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"skill_id", "candidate_id"})}
)
@Data
public class SkillCandidate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_candidate_id")
    private int skillCandidateId;

    @NotNull(message = MessageConst.INVALID_SKILL_NULL)
    @ManyToOne
    @JoinColumn(name = "skill_id", referencedColumnName = "skill_id")
    private Skills skills;

    @NotNull(message = MessageConst.INVALID_CANDIDATE_NULL)
    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "candidate_id")
    private Candidates candidates;

}
