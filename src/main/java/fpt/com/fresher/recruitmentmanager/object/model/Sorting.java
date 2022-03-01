package fpt.com.fresher.recruitmentmanager.object.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Sorting {

    @JsonProperty("criteria")
    private String fieldName;

    private boolean asc = true;
}
