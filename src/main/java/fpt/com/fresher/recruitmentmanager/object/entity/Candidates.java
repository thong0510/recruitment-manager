package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import fpt.com.fresher.recruitmentmanager.object.contant.RegexConst;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
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

    @Column
    private String gender;

    @Email(regexp = RegexConst.REGEX_EMAIL, message = MessageConst.INVALID_EMAIL)
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = MessageConst.INVALID_EXP)
    @Column(nullable = false)
    private String experience;

    @NotBlank(message = MessageConst.INVALID_PHOTO)
    @Column(nullable = true)
    private String photo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidates")
    private Set<Report> reports;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidates")
    private Set<Interview> interviews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidates")
    private Set<SkillCandidate> skillCandidates;

    @NotNull(message = MessageConst.INVALID_STATUS_NULL)
    @OneToOne
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    private Status status;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "candidates")
    private EntryTest entryTest;

}
