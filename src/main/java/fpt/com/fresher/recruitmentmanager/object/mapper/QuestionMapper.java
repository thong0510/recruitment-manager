package fpt.com.fresher.recruitmentmanager.object.mapper;

import fpt.com.fresher.recruitmentmanager.object.entity.Questions;
import fpt.com.fresher.recruitmentmanager.object.request.QuestionRequest;
import fpt.com.fresher.recruitmentmanager.object.response.QuestionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionMapper {

    QuestionResponse entityToQuestionResponse(Questions question);

    Questions questionRequestToEntity(QuestionRequest request);
}
