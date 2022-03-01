package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Data
public class Roles extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleId;

    @NotBlank(message = MessageConst.INVALID_NAME)
    @Column(name = "role_name", nullable = false)
    private String roleName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roles")
    private Set<Account> accounts;

}
