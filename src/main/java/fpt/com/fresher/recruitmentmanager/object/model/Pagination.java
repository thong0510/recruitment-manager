package fpt.com.fresher.recruitmentmanager.object.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {

    @JsonProperty("page")
    private int pageNumber;

    @JsonProperty("limit")
    private int pageSize;

    @JsonProperty("sort")
    private Sorting sorting;

    public Pagination(int pageSize) {
        this.pageSize = pageSize;
    }

    public Pageable getPageAndSort() {
        Sort sort = ObjectUtils.isEmpty(sorting)
                    ? Sort.unsorted()
                    : Sort.by(sorting.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC,
                              sorting.getFieldName());
        return PageRequest.of(pageNumber, pageSize, sort);
    }
}
