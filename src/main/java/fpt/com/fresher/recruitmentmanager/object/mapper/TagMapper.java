package fpt.com.fresher.recruitmentmanager.object.mapper;

import fpt.com.fresher.recruitmentmanager.object.entity.Tag;
import fpt.com.fresher.recruitmentmanager.object.request.TagRequest;
import fpt.com.fresher.recruitmentmanager.object.response.TagResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    Tag tagRequestToEntity(TagRequest request);

    void updateEntity(@MappingTarget Tag tag, TagRequest request);

    TagResponse entityToTagResponse(Tag tag);
}
