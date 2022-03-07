package fpt.com.fresher.recruitmentmanager.object.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sorting {

    @JsonProperty("criteria")
    private String fieldName;

    private boolean asc = true;
}
