package zoowebapp.service.utils;

import org.apache.commons.lang.RandomStringUtils;

/**
 * @author Zavada, Jun 15, 2018 (10:38:42 PM)
 */

public interface StringUtils {

    int DEFAULT_COUNT = 15;

    static String generate() {
        return RandomStringUtils.randomAlphanumeric(DEFAULT_COUNT).toLowerCase();
    }

    static String generateAlphaNumeric(int count) {
        return RandomStringUtils.randomAlphanumeric(count).toLowerCase();
    }

    static String generateAlphabetic(int count) {
        return RandomStringUtils.randomAlphabetic(count).toLowerCase();
    }

    static String generateNumeric(int count) {
        return RandomStringUtils.randomNumeric(count);
    }
}
