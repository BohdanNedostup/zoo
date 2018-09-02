package zoowebapp.service.utils;

import org.apache.commons.lang.RandomStringUtils;

public interface RandomToken {

    static String generateToken(){
        return RandomStringUtils.randomAlphanumeric(30);
    }

    static String generateToken(int count){
        return RandomStringUtils.randomAlphanumeric(count);
    }
}
