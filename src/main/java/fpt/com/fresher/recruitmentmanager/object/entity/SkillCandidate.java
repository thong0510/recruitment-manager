package fpt.com.fresher.recruitmentmanager.object.entity;


import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"skill_id", "candidate_id"})}
)
@Data
@Builder
public class SkillCandidate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_candidate_id")
    private Long skillCandidateId;

    @NotNull(message = MessageConst.INVALID_SKILL_NULL)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "skill_id", referencedColumnName = "skill_id")
    private Skills skills;

    @NotNull(message = MessageConst.INVALID_CANDIDATE_NULL)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "candidate_id", referencedColumnName = "candidate_id")
    private Candidates candidates;

    public SkillCandidate() {

    }

    public SkillCandidate(Long skillCandidateId, Skills skills, Candidates candidates) {
        this.skillCandidateId = skillCandidateId;
        this.skills = skills;
        this.candidates = candidates;
    }
}
