package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.enums.CandidateStatus;
import fpt.com.fresher.recruitmentmanager.object.contant.enums.Gender;
import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import fpt.com.fresher.recruitmentmanager.object.contant.RegexConst;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@SQLDelete(sql = "UPDATE candidates SET deleted = true WHERE candidate_id=?")
@Where(clause = "deleted=false")
public class Candidates extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_id")
    private Long candidateId;

    @NotBlank(message = MessageConst.INVALID_NAME)
    @Column(name = "candidate_name", nullable = false)
    private String candidateName;

    @Pattern(regexp = RegexConst.REGEX_PHONE, message = MessageConst.INVALID_PHONE)
    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String cardId;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Boolean deleted = false;

    @Email(regexp = RegexConst.REGEX_EMAIL, message = MessageConst.INVALID_EMAIL)
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = MessageConst.INVALID_EXP)
    @Column(nullable = false)
    private String experience;

    @Column(nullable = true)
    private String photo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidates")
    private Set<Report> reports;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidates")
    private Set<Interview> interviews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidates")
    private Set<SkillCandidate> skillCandidates;

    @Column
    @Enumerated(EnumType.STRING)
    private CandidateStatus status;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "candidates")
    private EntryTest entryTest;

    @PostPersist
    public void postPersist() {
        status = CandidateStatus.OPEN;
    }

}
