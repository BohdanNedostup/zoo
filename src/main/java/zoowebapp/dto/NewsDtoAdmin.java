package zoowebapp.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NewsDtoAdmin {

    private Long id;
    private String title;
    private String text;
    private String imageUrl;
    private Date createdAt;
}
