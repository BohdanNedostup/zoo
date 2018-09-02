package zoowebapp.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NewsDtoBoard {

    private Long id;
    private String title;
    private Date createdAt;
}