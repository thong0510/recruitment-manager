package fpt.com.fresher.recruitmentmanager.object.response;

import lombok.Data;


@Data
public class StatusResponse {

    private int statusId;
    private String statusName;
    private String description;
}
