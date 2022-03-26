package fpt.com.fresher.recruitmentmanager.object.contant;

public enum DifficultyLevel {
    EASY("EASY"), MEDIUM("MEDIUM"), HARD("HARD"), RANDOM("RANDOM");

    DifficultyLevel(String level) {
        this.level = level;
    }

    private final String level;

    public String getLevel() {
        return level;
    }
}
