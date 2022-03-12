package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE vacancies SET deleted = true WHERE vacancies_id=?")
@Where(clause = "deleted=false")
public class Vacancies extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacancies_id")
    private Long vacanciesId;

    @Min(value = 1, message = MessageConst.INVALID_QUANTITY)
    @Column(nullable = false)
    private Integer quantity;

    @Column
    private String description;

    @NotNull(message = MessageConst.INVALID_MAJOR_NULL)
    @ManyToOne
    @JoinColumn(name = "major_id", referencedColumnName = "major_id")
    private Major major;

    @NotNull(message = MessageConst.INVALID_MAJOR_NULL)
    @ManyToOne
    @JoinColumn(name = "major_detail_id", referencedColumnName = "major_detail_id")
    private MajorDetail majorDetail;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vacancies")
    private Set<Recruitment> recruitments;

    private Boolean deleted = false;

}
