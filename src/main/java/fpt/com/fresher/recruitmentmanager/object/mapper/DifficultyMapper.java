package fpt.com.fresher.recruitmentmanager.object.mapper;

import fpt.com.fresher.recruitmentmanager.object.contant.DifficultyPoint;
import fpt.com.fresher.recruitmentmanager.object.entity.Difficulty;
import fpt.com.fresher.recruitmentmanager.object.response.DifficultyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DifficultyMapper {

    DifficultyResponse entityToDifficultyResponse(Difficulty difficulty);

    default Integer mapDifficultyPointToInteger(DifficultyPoint value) {
        return value.getPoint();
    }
}
