package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.CommonConst;
import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.Set;

@Entity
@Data
public class Recruitment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_id")
    private int recruitmentId;

    @Min(value = 0, message = MessageConst.INVALID_SALARY)
    @Column
    private float salary;

    @Column(name = "date_start", nullable = false)
    @DateTimeFormat(pattern = CommonConst.FORMAT_DATE)
    @Past(message = MessageConst.INVALID_DATE)
    private Date dateStart;

    @Column(name = "date_end", nullable = false)
    @DateTimeFormat(pattern = CommonConst.FORMAT_DATE)
    @Past(message = MessageConst.INVALID_DATE)
    private Date dateEnd;

    @NotNull(message = MessageConst.INVALID_VACANCIES_NULL)
    @ManyToOne
    @JoinColumn(name = "vacancies_id", referencedColumnName = "vacancies_id")
    private Vacancies vacancies;

    @NotNull(message = MessageConst.INVALID_USER_NULL)
    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    private Users users;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recruitment")
    private Set<SkillRecruitment> skill_recruitments;

}
