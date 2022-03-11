package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Data
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

}
