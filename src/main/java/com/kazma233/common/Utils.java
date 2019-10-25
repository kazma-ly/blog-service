package com.kazma233.common;


public class Utils {

    private static final SnowflakeIdWorker SNOWFLAKE_ID_WORKER = new SnowflakeIdWorker(1, 1);

    public static String generateID() {
        return String.valueOf(SNOWFLAKE_ID_WORKER.nextId());
    }

}
