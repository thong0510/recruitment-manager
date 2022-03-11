package fpt.com.fresher.recruitmentmanager.object.mapper;

import fpt.com.fresher.recruitmentmanager.object.entity.Major;
import fpt.com.fresher.recruitmentmanager.object.entity.MajorDetail;
import fpt.com.fresher.recruitmentmanager.object.request.MajorDetailRequest;
import fpt.com.fresher.recruitmentmanager.object.request.MajorRequest;
import fpt.com.fresher.recruitmentmanager.object.response.MajorDetailResponse;
import fpt.com.fresher.recruitmentmanager.object.response.MajorResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MajorDetailMapper {

    MajorDetailResponse entityToMajorDetailResponse(MajorDetail majorDetail);

    MajorDetail majorDetailRequestToEntity(MajorDetailRequest request);

    void updateEntity(@MappingTarget MajorDetail majorDetail, MajorDetailRequest request);

//    @AfterMapping
//    default void mappingReqPropsToResponse(@MappingTarget MajorResponse majorResponse, Major major) {
//        majorResponse.setUpdateDate(major.getUpdatedDate());
//    }

}
