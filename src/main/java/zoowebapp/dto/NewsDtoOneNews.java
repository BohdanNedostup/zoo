package zoowebapp.dto;

import lombok.Data;

@Data
public class NewsDtoOneNews {

    private String title;
    private String text;
    private String imageUrl;
    private String createdAt;
}
