package fpt.com.fresher.recruitmentmanager.object.mapper;

import fpt.com.fresher.recruitmentmanager.object.entity.Category;
import fpt.com.fresher.recruitmentmanager.object.request.CategoryRequest;
import fpt.com.fresher.recruitmentmanager.object.response.CategoryResponse;
import fpt.com.fresher.recruitmentmanager.utils.StringUtils;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    Category categoryRequestToEntity(CategoryRequest request);

    void updateEntity(@MappingTarget Category category, CategoryRequest request);

    CategoryResponse entityToCategoryResponse(Category category);

    @AfterMapping
    default void getSlug(@MappingTarget Category category, CategoryRequest request) {
        String slug = StringUtils.generateSlug(request.getName());
        category.setSlug(slug);
    }
}
