package com.fzcode.internal.common.constant;

public class ServiceName {
    public static final String API_BLOG = "apiBlog";
    public static final String CLOUD_CONFIG="cloudConfig";
    public static final String CLOUD_EUREKA="cloudEureka";
    public static final String CLOUD_GATE="cloudGate";
    public static final String SERVICE_AUTH="serviceAuth";
    public static final String SERVICE_FILE="serviceFile";
    public static final String SERVICE_MAIL="serviceMail";
    public static final String SERVICE_NOTE="serviceNote";

    public static final String LB_API_BLOG = "lb://"+ServiceName.API_BLOG;
    public static final String LB_CLOUD_CONFIG="lb://"+ServiceName.CLOUD_CONFIG;
    public static final String LB_CLOUD_EUREKA="lb://"+ServiceName.CLOUD_EUREKA;
    public static final String LB_CLOUD_GATE="lb://"+ServiceName.CLOUD_GATE;
    public static final String LB_SERVICE_AUTH="lb://"+ServiceName.SERVICE_AUTH;
    public static final String LB_SERVICE_FILE="lb://"+ServiceName.SERVICE_FILE;
    public static final String LB_SERVICE_MAIL="lb://"+ServiceName.SERVICE_MAIL;
    public static final String LB_SERVICE_NOTE="lb://"+ServiceName.SERVICE_NOTE;

}
