package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Data
public class Major extends BaseEntity {

    @Id
    @Column(name = "major_id")
    private String majorId;

    @NotBlank(message = MessageConst.INVALID_NAME)
    @Column(name = "major_name", nullable = false)
    private String majorName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "major")
    private Set<MajorDetail> majorDetail;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "major")
    private Set<Vacancies> vacancies;

}
