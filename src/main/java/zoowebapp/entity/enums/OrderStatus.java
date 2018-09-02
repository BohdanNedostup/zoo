package zoowebapp.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus{

    SENT("SENT"), CANCELED("CANCELED"), PROCESSING("PROCESSING"), NOT_CONFIRMED("NOT_CONFIRMED");

    private String status;
}
