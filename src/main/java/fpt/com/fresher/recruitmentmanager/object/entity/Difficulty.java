package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.DifficultyLevel;
import fpt.com.fresher.recruitmentmanager.object.contant.DifficultyPoint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "difficulties")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Difficulty {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DifficultyLevel level;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private DifficultyPoint point;

    @OneToMany(mappedBy = "difficulty") @ToString.Exclude
    private Set<Question> questions;

    public Difficulty(DifficultyLevel level, DifficultyPoint point) {
        this.level = level;
        this.point = point;
    }
}
