package zoowebapp.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Country {

    SELECT_COUNTRY("Select country"),
    AF("Afghanistan"), AT("Austria"), BE("Belgium"), BG("Bulgaria"), BY("Belarus"), CH("Switzerland"),
    CZ("Czech Republic"), DE("Germany"), DK("Denmark"), EE("Estonia"), GB("Great Britain"), GE("Georgia"),
    GR("Greece"), HR("Croatia"), HU("Hungary"), IT("Italy"), KZ("Kazakhstan"), LT("Lithuania"), LV("Latvia"),
    MD("Moldova"), NL("Netherlands"), NO("Norway"), PL("Poland"), PT("Portugal"), RO("Romania"), RS("Serbia"),
    RU("Russian Federation"), SE("Sweden"), SI("Slovenia"), SK("Slovakia"), TR("Turkey"), UA("Ukraine"),
    US("United States");

    private String country;
}