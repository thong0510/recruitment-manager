package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Data
public class Vacancies extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacancies_id")
    private int vacanciesId;

    @NotBlank(message = MessageConst.INVALID_NAME)
    @Column(name = "vacancies_name", nullable = false)
    private String vacanciesName;

    @Min(value = 1, message = MessageConst.INVALID_QUANTITY)
    @Column(nullable = false)
    private int quantity;

    @Column
    private String description;

    @NotNull(message = MessageConst.INVALID_MAJOR_NULL)
    @ManyToOne
    @JoinColumn(name = "major_id", referencedColumnName = "major_id")
    private Major major;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vacancies")
    private Set<Recruitment> recruitments;

}
