package fpt.com.fresher.recruitmentmanager.object.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(columnDefinition = "varchar(50)", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "tag") @ToString.Exclude
    private Set<Question> questions;

    public Tag(String name) {
        this.name = name;
    }
}
