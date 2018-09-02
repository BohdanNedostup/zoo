package zoowebapp.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AnimalStatus {

    PATIENT("PATIENT"), HEALTHY("HEALTHY");

    private String status;
}
