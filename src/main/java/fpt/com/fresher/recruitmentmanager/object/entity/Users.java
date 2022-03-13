package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import fpt.com.fresher.recruitmentmanager.object.contant.RegexConst;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE user_name=?")
@Where(clause = "deleted=false")
public class Users extends BaseEntity {

    @Id
    @Column(name = "user_name", unique = true)
    private String userName;

    @NotBlank(message = MessageConst.INVALID_NAME)
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Pattern(regexp = RegexConst.REGEX_PHONE, message = MessageConst.INVALID_PHONE)
    @Column(nullable = false, unique = true)
    private String phone;

    @Email(regexp = RegexConst.REGEX_EMAIL, message = MessageConst.INVALID_EMAIL)
    @Column(nullable = false, unique = true)
    private String email;

//    @Pattern(regexp = RegexConst.REGEX_PASSWORD, message = MessageConst.INVALID_PASSWORD)
    @Column(nullable = false)
    private String password;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private Set<Recruitment> recruitments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private Set<Interview> interviews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private Set<Report> reports;

    @Column(nullable = true)
    private String photo;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = { @JoinColumn(referencedColumnName = "user_name") },
            inverseJoinColumns = { @JoinColumn(referencedColumnName = "role_id") })
    private Collection<Role> roles;

    private Boolean deleted = false;

}
