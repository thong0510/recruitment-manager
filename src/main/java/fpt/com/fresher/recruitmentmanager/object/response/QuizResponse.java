package fpt.com.fresher.recruitmentmanager.object.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {

    private Long id;
    private String title;
    private String description;
    private String image;
    private String status;
    private String categoryName;
    private long categoryId;
    private String instructorName;
}
