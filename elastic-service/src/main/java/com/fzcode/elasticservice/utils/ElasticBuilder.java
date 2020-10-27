package com.fzcode.elasticservice.utils;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class ElasticBuilder {

    private static String hostname;
    @Value("${elastic.host}")
    public void setHostname(String hostname) {
        ElasticBuilder.hostname = hostname;
    }

    private static int port;
    @Value("${elastic.port}")
    public void setPort(int port) {
        ElasticBuilder.port = port;
    }

    private static String scheme;
    @Value("${elastic.scheme}")
    public void setScheme(String scheme) {
        ElasticBuilder.scheme = scheme;
    }

    public static RestHighLevelClient createElasticClient() throws IOException {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostname, port, scheme)
                ));
    }
}
