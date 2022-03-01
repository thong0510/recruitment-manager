package fpt.com.fresher.recruitmentmanager.object.entity;


import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"skill_id", "recruitment_id"})}
)
@Data
public class SkillRecruitment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_recruitment_id")
    private int skillRecruitmentId;

    @NotNull(message = MessageConst.INVALID_SKILL_NULL)
    @ManyToOne
    @JoinColumn(name = "skill_id", referencedColumnName = "skill_id")
    private Skills skills;

    @NotNull(message = MessageConst.INVALID_RECRUITMENT_NULL)
    @ManyToOne
    @JoinColumn(name = "recruitment_id", referencedColumnName = "recruitment_id")
    private Recruitment recruitment;

}
