package fpt.com.fresher.recruitmentmanager.object.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
public class BaseEntity {

    @Temporal(TemporalType.DATE)
    @Column(name = "created_date")
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "is_delete")
    private Boolean isDelete;

    public Date getCreatedDate() {
        return createdDate;
    }

    public BaseEntity setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public BaseEntity setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public BaseEntity setDelete(Boolean delete) {
        isDelete = delete;
        return this;
    }

}
