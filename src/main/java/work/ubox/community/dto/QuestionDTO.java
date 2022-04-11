package work.ubox.community.dto;

import lombok.Data;
import work.ubox.community.model.User;

@Data
public class QuestionDTO {
    private Long id;
    private String  title;
    private String  description;
    private String  tag;
    private Long  gmtCreate;
    private Long  gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
