package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class MajorDetail extends BaseEntity {
    // chi tiết ngành tuyển dụng
    // Ví dụ : ngành IT thì ntn

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_detail_id")
    private Long majorDetailId;

    @NotBlank(message = MessageConst.INVALID_NAME)
    @Column(name = "major_detail_name", nullable = false)
    private String majorDetailName;

    @NotNull(message = MessageConst.INVALID_MAJOR_NULL)
    @ManyToOne
    @JoinColumn(name = "major_id", referencedColumnName = "major_id")
    private Major major;

}
