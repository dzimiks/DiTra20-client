package com.example.si_broker.utils;

public class Constants {
    /**
     * DB Utils
     */
    public static final String DB_NAME = "tim_403_2_si2019";
    public static final String DB_USERNAME = "tim_403_2_si2019";
    public static final String DB_PASSWORD = "QJfcAmGb";
    public static final String DB_HOST = "64.225.110.65";
    public static final String DB_PORT = "3306";
    // DB_URL: jdbc:mysql://tim_403_2_si2019:QJfcAmGb@64.225.110.65:3306/tim_403_2_si2019
    public static final String DB_URL = "jdbc:mysql://" + DB_USERNAME + ":" + DB_PASSWORD + "@" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static final String MONGO_DB_NAME = "tim_403_2_broker_si2019";
    public static final String MONGO_DB_PASSWORD = "8B#9#g@E!Vz=?wyV";
    public static final int MONGO_DB_PORT = 27017;

    public static final String USERS_BASE_URL = "/api/v1/users";
}
