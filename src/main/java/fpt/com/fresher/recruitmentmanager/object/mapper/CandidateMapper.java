package fpt.com.fresher.recruitmentmanager.object.mapper;

import fpt.com.fresher.recruitmentmanager.object.entity.Candidates;
import fpt.com.fresher.recruitmentmanager.object.entity.SkillCandidate;
import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import fpt.com.fresher.recruitmentmanager.object.request.CandidateRequest;
import fpt.com.fresher.recruitmentmanager.object.response.CandidateResponse;
import fpt.com.fresher.recruitmentmanager.object.response.SkillResponse;
import lombok.AllArgsConstructor;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CandidateMapper {

    CandidateResponse entityToCandidateResponse(Candidates candidates);

    Candidates candidateRequestToEntity(CandidateRequest request);

    void updateEntity(@MappingTarget Candidates candidates, CandidateRequest request);

    @AfterMapping
    default void mappingReqPropsToResponse(@MappingTarget CandidateResponse candidateResponse, Candidates candidates) {
        candidateResponse.setListOfSkill(candidates.getSkillCandidates().stream().map(entity -> entity.getSkills().getSkillId()).collect(Collectors.toList()));
    }

}
