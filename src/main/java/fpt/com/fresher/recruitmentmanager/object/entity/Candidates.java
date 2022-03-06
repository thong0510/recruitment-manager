package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.CommonConst;
import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import fpt.com.fresher.recruitmentmanager.object.contant.RegexConst;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
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

    @Size(min = 9, max = 12, message = MessageConst.INVALID_CARD)
    @Column(name = "card_id", unique = true)
    private int cardId;

    @Column
    private String gender;

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
    private String status;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "candidates")
    private EntryTest entryTest;

}
