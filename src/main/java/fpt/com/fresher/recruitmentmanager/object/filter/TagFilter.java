package fpt.com.fresher.recruitmentmanager.object.filter;

import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import lombok.Data;

@Data
public class TagFilter {
    private String name;
    private Pagination pagination = new Pagination(5);
}
