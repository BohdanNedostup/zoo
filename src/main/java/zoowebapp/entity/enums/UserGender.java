package zoowebapp.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserGender {
    MALE("MALE"), FEMALE("FEMALE");

    private String genderName;
}
