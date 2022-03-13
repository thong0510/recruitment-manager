package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.enums.SystemRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Long id;

    @Column(name = "role_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private SystemRole role;

    @ManyToMany(mappedBy = "roles")
    private Set<Users> users;

    public Role(SystemRole role) {
        this.role = role;
    }
}
