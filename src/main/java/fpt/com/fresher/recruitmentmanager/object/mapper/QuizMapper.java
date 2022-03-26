package fpt.com.fresher.recruitmentmanager.object.mapper;

import fpt.com.fresher.recruitmentmanager.object.entity.Quiz;
import fpt.com.fresher.recruitmentmanager.object.request.QuizRequest;
import fpt.com.fresher.recruitmentmanager.object.response.QuizResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuizMapper {

    Quiz quizRequestToEntity(QuizRequest request);

    void updateEntity(@MappingTarget Quiz quiz, QuizRequest request);

    @Mapping(target = "instructorName", source = "instructor.fullName")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "categoryId", source = "category.id")
    QuizResponse entityToQuizResponse(Quiz quiz);
}
