package fpt.com.fresher.recruitmentmanager.object.mapper;

import fpt.com.fresher.recruitmentmanager.object.entity.Candidates;
import fpt.com.fresher.recruitmentmanager.object.entity.SkillCandidate;
import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import fpt.com.fresher.recruitmentmanager.object.request.CandidateRequest;
import fpt.com.fresher.recruitmentmanager.object.response.CandidateResponse;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CandidateMapper {

    CandidateResponse entityToCandidateResponse(Candidates candidates);

    Candidates candidateRequestToEntity(CandidateRequest request);

    void updateEntity(@MappingTarget Candidates candidates, CandidateRequest request);

    @AfterMapping
    default void mappingReqPropsToResponse(@MappingTarget CandidateResponse candidateResponse, Candidates candidates) {
        Set<Skills> skills = new HashSet<>();

        for(SkillCandidate entity: candidates.getSkillCandidates()) {
            skills.add(entity.getSkills());
        }

        candidateResponse.setListOfSkill(skills);
    }

}
