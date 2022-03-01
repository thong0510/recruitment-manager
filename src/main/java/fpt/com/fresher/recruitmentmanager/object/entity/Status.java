package fpt.com.fresher.recruitmentmanager.object.entity;

import fpt.com.fresher.recruitmentmanager.object.contant.MessageConst;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class Status extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private int statusId;

    @NotBlank(message = MessageConst.INVALID_NAME)
    @Column(name = "status_name", nullable = false)
    private String statusName;

    @Column
    private String description;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "status")
    private Candidates candidates;

}