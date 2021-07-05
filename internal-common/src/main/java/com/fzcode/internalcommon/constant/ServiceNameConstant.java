package com.fzcode.internalcommon.constant;

public class ServiceNameConstant {
    public static final String API_BLOG = "API-BLOG";
    public static final String CLOUD_CONFIG="CLOUD-CONFIG";
    public static final String CLOUD_EUREKA="CLOUD-EUREKA";
    public static final String CLOUD_GATE="CLOUD-GATE";
    public static final String SERVICE_AUTH="SERVICE-AUTH";
    public static final String SERVICE_FILE="SERVICE-FILE";
    public static final String SERVICE_MAIL="SERVICE-MAIL";
    public static final String SERVICE_NOTE="SERVICE-NOTE";

    public static final String LB_API_BLOG = "lb://"+ ServiceNameConstant.API_BLOG;
    public static final String LB_CLOUD_CONFIG="lb://"+ ServiceNameConstant.CLOUD_CONFIG;
    public static final String LB_CLOUD_EUREKA="lb://"+ ServiceNameConstant.CLOUD_EUREKA;
    public static final String LB_CLOUD_GATE="lb://"+ ServiceNameConstant.CLOUD_GATE;
    public static final String LB_SERVICE_AUTH="lb://"+ ServiceNameConstant.SERVICE_AUTH;
    public static final String LB_SERVICE_FILE="lb://"+ ServiceNameConstant.SERVICE_FILE;
    public static final String LB_SERVICE_MAIL="lb://"+ ServiceNameConstant.SERVICE_MAIL;
    public static final String LB_SERVICE_NOTE="lb://"+ ServiceNameConstant.SERVICE_NOTE;

}
