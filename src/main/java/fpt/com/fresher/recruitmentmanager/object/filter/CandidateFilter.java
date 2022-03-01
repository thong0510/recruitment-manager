package fpt.com.fresher.recruitmentmanager.object.filter;

import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import fpt.com.fresher.recruitmentmanager.object.request.StatusRequest;
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

    private StatusRequest status;

    private Pagination pagination = new Pagination(10);

}
