package fpt.com.fresher.recruitmentmanager.object.mapper;

import fpt.com.fresher.recruitmentmanager.object.entity.Answer;
import fpt.com.fresher.recruitmentmanager.object.request.AnswerRequest;
import fpt.com.fresher.recruitmentmanager.object.response.AnswerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerMapper {

    Answer answerRequestToEntity(AnswerRequest request);

    void updateEntity(@MappingTarget Answer answer, AnswerRequest request);

    AnswerResponse entityToAnswerResponse(Answer answer);
}
