package com.fzcode.elasticservice;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElasticServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticServiceApplication.class, args);

	}

}
