package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.contant.DifficultyLevel;
import fpt.com.fresher.recruitmentmanager.object.contant.DifficultyPoint;
import fpt.com.fresher.recruitmentmanager.object.entity.Difficulty;
import fpt.com.fresher.recruitmentmanager.object.exception.ResourceNotFoundException;
import fpt.com.fresher.recruitmentmanager.repository.DifficultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DifficultyServiceImpl {

    private final DifficultyRepository difficultyRepository;

    @PostConstruct
    private void init() {
        if (difficultyRepository.count() == 0) {
            List<Difficulty> difficultyList =
                    new ArrayList<>(Arrays.asList(new Difficulty(DifficultyLevel.EASY,
                                    DifficultyPoint.EASY),
                            new Difficulty(DifficultyLevel.MEDIUM,
                                    DifficultyPoint.MEDIUM),
                            new Difficulty(DifficultyLevel.HARD,
                                    DifficultyPoint.HARD)));
            difficultyRepository.saveAll(difficultyList);
        }
    }

    public List<Difficulty> findAllDifficulties() {
        return difficultyRepository.findAll();
    }

    public Difficulty findDifficultyById(long id) {
        return difficultyRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found any difficulty with id " + id));
    }

    public Difficulty findDifficultyByLevel(DifficultyLevel level) {
        return difficultyRepository.findByLevel(level).orElseThrow(
                () -> new ResourceNotFoundException("Not found any difficulty with level " + level));
    }
}
