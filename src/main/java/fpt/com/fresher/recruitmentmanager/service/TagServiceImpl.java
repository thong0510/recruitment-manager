package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.Tag;
import fpt.com.fresher.recruitmentmanager.object.exception.ResourceNotFoundException;
import fpt.com.fresher.recruitmentmanager.object.filter.TagFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.TagMapper;
import fpt.com.fresher.recruitmentmanager.object.request.TagRequest;
import fpt.com.fresher.recruitmentmanager.repository.TagRepository;
import fpt.com.fresher.recruitmentmanager.repository.spec.TagSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @PostConstruct
    private void init() {
        if (tagRepository.count() == 0) {
            List<Tag> initialTags = Arrays.asList(
                    new Tag("HTML5/CSS3"),
                    new Tag("Javascript"),
                    new Tag("Java"),
                    new Tag("Python"),
                    new Tag("DevOps"),
                    new Tag("Kernel/OS")
            );

            tagRepository.saveAll(initialTags);
        }
    }

    public Tag findTagByName(String name) {
        return tagRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Not found any tag with name " + name));
    }

    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    public Tag findTagById(long id) {
        return tagRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found any tag with id " + id));
    }

    public Page<Tag> findAllTags(TagFilter filter) {
        Specification<Tag> specification = TagSpecification.getSpecification(filter);

        return tagRepository.findAll(specification, filter.getPagination().getPageAndSort());
    }

    public Tag createTag(TagRequest requestBody) {
        Tag tag = tagMapper.tagRequestToEntity(requestBody);

        return tagRepository.save(tag);
    }

    public Tag updateTag(long id, TagRequest requestBody) {
        Tag tag = this.findTagById(id);
        tagMapper.updateEntity(tag, requestBody);

        return tagRepository.save(tag);
    }

    public void deleteTag(long id) {
        Tag tag = this.findTagById(id);

        tagRepository.delete(tag);
    }
}
