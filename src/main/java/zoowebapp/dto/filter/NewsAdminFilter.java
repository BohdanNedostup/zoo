package zoowebapp.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class NewsAdminFilter {

    private String title;
    private Date createdAtFrom;
    private Date createdAtTo;

    private String sortBy;
    private String ascOrDesc;
}
