package fpt.com.fresher.recruitmentmanager.object.mapper;

import fpt.com.fresher.recruitmentmanager.object.entity.Question;
import fpt.com.fresher.recruitmentmanager.object.request.QuestionRequest;
import fpt.com.fresher.recruitmentmanager.object.response.QuestionResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = DifficultyMapper.class)
public interface QuestionMapper {

    Question questionRequestToEntity(QuestionRequest request);

    void updateEntity(@MappingTarget Question question, QuestionRequest request);

    @Mapping(target = "quizId", source = "quiz.id")
    QuestionResponse entityToQuestionResponse(Question question);
}
