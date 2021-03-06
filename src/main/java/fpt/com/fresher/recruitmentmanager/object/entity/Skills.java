package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE skills SET deleted = true WHERE skill_id=?")
@Where(clause = "deleted=false")
public class Skills extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long skillId;

    @NotBlank(message = MessageConst.INVALID_NAME)
    @Column(name = "skill_name", nullable = false)
    private String skillName;

    @Column
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skills")
    private Set<SkillRecruitment> skillRecruitments;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "skills")
    private Set<SkillCandidate> skillCandidates;

    private Boolean deleted = false;

}
