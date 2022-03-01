package fpt.com.fresher.recruitmentmanager.object.request;

import lombok.Data;


@Data
public class StatusRequest {

    private int statusId;
    private String statusName;
    private String description;
}
