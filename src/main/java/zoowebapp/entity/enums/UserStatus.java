package zoowebapp.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserStatus {

    ACTIVE("ACTIVE"), ACTIVATION_PENDING("PENDING"), NOT_ACTIVATED("NOT ACTIVATED"), ONLINE("ONLINE"), OFFLINE("OFFLINE");

    private String status;

}
