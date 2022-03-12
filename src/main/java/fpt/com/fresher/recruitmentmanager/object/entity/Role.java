package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.enums.SystemRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "role_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<Users> users;

    public Role(String name) {
        this.name = name;
    }
}
