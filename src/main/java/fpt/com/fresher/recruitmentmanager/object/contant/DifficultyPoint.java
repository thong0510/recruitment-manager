package fpt.com.fresher.recruitmentmanager.object.contant;

public enum DifficultyPoint {
    EASY(50), MEDIUM(75), HARD(100);

    DifficultyPoint(int point) {
        this.point = point;
    }

    private final int point;

    public int getPoint() {
        return point;
    }
}
