package zoowebapp.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AnimalGender {

    MALE("MALE"), FEMALE("FEMALE"), HERMAPHRODITE("HERMAPHRODITE"), OTHER("OTHER");

    private String gender;
}
