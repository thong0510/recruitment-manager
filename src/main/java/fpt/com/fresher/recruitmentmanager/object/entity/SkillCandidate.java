package fpt.com.fresher.recruitmentmanager.object.entity;


import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.*;
import lombok.Builder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table( name = "skill_candidate",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"skill_id", "candidate_id"})}
)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SkillCandidate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_candidate_id")
    private Long skillCandidateId;

    @NotNull(message = MessageConst.INVALID_SKILL_NULL)
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "skill_id", referencedColumnName = "skill_id")
    private Skills skills;

    @NotNull(message = MessageConst.INVALID_CANDIDATE_NULL)
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "candidate_id", referencedColumnName = "candidate_id")
    private Candidates candidates;

}
