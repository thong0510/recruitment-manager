package fpt.com.fresher.recruitmentmanager.object.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) @Column(name = "id", nullable = false)
    private Long id;

    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String name;

    //TODO add slug
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String slug;

    @Column(columnDefinition = "text")
    private String image;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL) @ToString.Exclude
    private Set<Quiz> quizzes;

    public Category(String name, String slug, String image) {
        this.name = name;
        this.slug = slug;
        this.image = image;
    }
}
