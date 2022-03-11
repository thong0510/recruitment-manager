package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@SQLDelete(sql = "UPDATE major SET deleted = true WHERE major_id=?")
@Where(clause = "deleted=false")
public class Major extends BaseEntity {
//    Ngành tuyển dụng

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_id")
    private Long majorId;

    @NotBlank(message = MessageConst.INVALID_NAME)
    @Column(name = "major_name", nullable = false)
    private String majorName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "major")
    private Set<MajorDetail> majorDetail;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "major")
    private Set<Vacancies> vacancies;

    private Boolean deleted = false;

}
