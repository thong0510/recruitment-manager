package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.Category;
import fpt.com.fresher.recruitmentmanager.object.exception.ResourceNotFoundException;
import fpt.com.fresher.recruitmentmanager.object.filter.CategoryFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.CategoryMapper;
import fpt.com.fresher.recruitmentmanager.object.request.CategoryRequest;
import fpt.com.fresher.recruitmentmanager.repository.CategoryRepository;
import fpt.com.fresher.recruitmentmanager.repository.spec.CategorySpecification;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private final CloudinaryService cloudinaryService;

    @PostConstruct
    private void init() {
        if (categoryRepository.count() == 0) {
            List<Category> initialCategories = Arrays.asList(
                    new Category("Development", "development", "https://res.cloudinary.com/fpt-software-quiz/image/upload/v1644660774/developement_zah93x.jpg"),
                    new Category("Business", "business", "https://res.cloudinary.com/fpt-software-quiz/image/upload/v1644660774/business_niyc4b.jpg"),
                    new Category("Design", "design", "https://res.cloudinary.com/fpt-software-quiz/image/upload/v1644660774/design_k3sin6.jpg"),
                    new Category("IT and Software", "it-and-software", "https://res.cloudinary.com/fpt-software-quiz/image/upload/v1644660774/it-and-software_zguif9.jpg"),
                    new Category("Marketing", "marketing", "https://res.cloudinary.com/fpt-software-quiz/image/upload/v1644660774/marketing_bliev0.jpg"),
                    new Category("Music", "music", "https://res.cloudinary.com/fpt-software-quiz/image/upload/v1644660774/music_t4mncd.jpg"),
                    new Category("Personal Development", "personal-development", "https://res.cloudinary.com/fpt-software-quiz/image/upload/v1644660774/personal-developement_npjujq.jpg"),
                    new Category("Photography", "photography", "https://res.cloudinary.com/fpt-software-quiz/image/upload/v1644660774/photography_kdazmf.jpg")
            );
            categoryRepository.saveAll(initialCategories);
        }
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findCategoryByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Not found any category with name " + name));
    }

    public Page<Category> findAllCategories(CategoryFilter filter) {
        Specification<Category> specification = CategorySpecification.getSpecification(filter);

        return categoryRepository.findAll(specification, filter.getPagination().getPageAndSort());
    }

    public Category findCategoryById(long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found any category with id " + id));
    }

    public Category createCategory(CategoryRequest requestBody) {
        Category category = categoryMapper.categoryRequestToEntity(requestBody);

        if (!ObjectUtils.isEmpty(requestBody.getImageFile())) {
            String image = cloudinaryService.uploadImage(null, requestBody.getImageFile());
            if (image != null) category.setImage(image);
        }

        return categoryRepository.save(category);
    }

    public Category updateCategory(long id, CategoryRequest requestBody) {
        Category category = this.findCategoryById(id);
        categoryMapper.updateEntity(category, requestBody);

        if (!ObjectUtils.isEmpty(requestBody.getImageFile())) {
            String image = cloudinaryService.uploadImage(category.getImage(), requestBody.getImageFile());
            if (image != null) category.setImage(image);
        }

        return categoryRepository.save(category);
    }

    public void deleteCategory(long id) {
        Category category = this.findCategoryById(id);

        categoryRepository.delete(category);
    }

}
