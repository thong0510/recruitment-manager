package fpt.com.fresher.recruitmentmanager.object.mapper;

import fpt.com.fresher.recruitmentmanager.object.entity.Recruitment;
import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import fpt.com.fresher.recruitmentmanager.object.request.RecruitmentRequest;
import fpt.com.fresher.recruitmentmanager.object.request.SkillRequest;
import fpt.com.fresher.recruitmentmanager.object.response.RecruitmentResponse;
import fpt.com.fresher.recruitmentmanager.object.response.SkillResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecruitmentMapper {

    RecruitmentResponse entityToRecruitmentResponse(Recruitment recruitment);

    Recruitment recruitmentRequestToEntity(RecruitmentRequest request);

    Recruitment recruitmentResponseToEntity(RecruitmentResponse response);

    void updateEntity(@MappingTarget Recruitment recruitment, RecruitmentRequest request);
}
