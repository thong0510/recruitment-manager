package fpt.com.fresher.recruitmentmanager.object.filter;

import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import lombok.Data;

@Data
public class CandidateFilter {

    private Long candidateId;

    private String candidateName;

    private String phone;

    private String gender;

    private String email;

    private String experience;

    private String photo;

    private String status;

    private Pagination pagination = new Pagination(10);

}
