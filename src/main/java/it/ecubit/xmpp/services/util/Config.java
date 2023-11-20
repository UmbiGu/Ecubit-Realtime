package it.ecubit.xmpp.services.util;

import org.springframework.beans.factory.annotation.Value;

public class Config {

    @Value("${application.name}")
    public static final String appName = "";
    @Value("${application.version}")
    public static final String appVersionName = "";
    public static final int appVersionCode = 1;
}
