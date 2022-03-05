package fpt.com.fresher.recruitmentmanager.object.mapper;

import fpt.com.fresher.recruitmentmanager.object.entity.Candidates;
import fpt.com.fresher.recruitmentmanager.object.request.CandidateRequest;
import fpt.com.fresher.recruitmentmanager.object.response.CandidateResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CandidateMapper {

    CandidateResponse entityToCandidateResponse(Candidates candidates);

    Candidates candidateRequestToEntity(CandidateRequest request);

    void updateEntity(@MappingTarget Candidates candidates, CandidateRequest request);
}
