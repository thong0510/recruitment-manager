package fpt.com.fresher.recruitmentmanager.object.mapper;

import fpt.com.fresher.recruitmentmanager.object.entity.UserQuiz;
import fpt.com.fresher.recruitmentmanager.object.request.UserQuizRequest;
import fpt.com.fresher.recruitmentmanager.object.response.UserQuizResponse;
import org.mapstruct.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserQuizMapper {

    @Mapping(target = "fullName", source = "user.fullName")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "quizId", source = "quiz.id")
    @Mapping(target = "quizTitle", source = "quiz.title")
    @Mapping(target = "quizImage", source = "quiz.image")
    @Mapping(target = "dateToString", ignore = true)
    UserQuizResponse entityToUserQuizResponse(UserQuiz userQuiz);

    @AfterMapping
    default void mappingReqPropsToResponse(@MappingTarget UserQuizResponse userQuizResponse, UserQuiz userQuiz) {
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        String dateText = df.format(userQuiz.getRecentActiveDate());

        userQuizResponse.setDateToString(dateText);
    }

    UserQuiz userQuizRequestToEntity(UserQuizRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget UserQuiz userQuiz, UserQuizRequest request);
}
