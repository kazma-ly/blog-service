package com.kazma233.common;


import java.util.UUID;

public class Utils {

    public static String generateID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
