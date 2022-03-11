package fpt.com.fresher.recruitmentmanager.object.mapper;

import fpt.com.fresher.recruitmentmanager.object.entity.Major;
import fpt.com.fresher.recruitmentmanager.object.request.MajorRequest;
import fpt.com.fresher.recruitmentmanager.object.response.MajorResponse;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MajorMapper {

    MajorResponse entityToMajorResponse(Major major);

    Major majorRequestToEntity(MajorRequest request);

    void updateEntity(@MappingTarget Major major, MajorRequest request);

    @AfterMapping
    default void mappingReqPropsToResponse(@MappingTarget MajorResponse majorResponse, Major major) {
        majorResponse.setUpdateDate(major.getUpdatedDate());
    }

}
