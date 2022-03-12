package fpt.com.fresher.recruitmentmanager.object.filter;

import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFilter {

    private String userName;

    private String fullName;

    private String phone;

    private String email;

    private Pagination pagination = new Pagination(10);

}
