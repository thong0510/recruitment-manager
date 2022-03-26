package fpt.com.fresher.recruitmentmanager.object.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class QuizRequest implements Serializable {

    @NotBlank(message = "blank")
    @Size(min = 10, max = 255, message = "size(min:10,max:255)")
    private String title;

    @NotBlank(message = "blank")
    private String description;

    private MultipartFile imageFile;

    @NotNull(message = "null")
    private Long categoryId;

    public QuizRequest(String title, String description, long categoryId) {
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
    }
}
