package fpt.com.fresher.recruitmentmanager.object.contant.enums;

public enum SystemRole {
    ADMIN("ROLE_ADMIN"), HR("ROLE_HR"), USER("ROLE_USER"), INTERVIEWER("ROLE_INTERVIEWER");

    private final String value;

    public String getValue() {
        return value;
    }
    SystemRole(String value) {
        this.value = value;
    }
}
