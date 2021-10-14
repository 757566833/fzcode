package com.fzcode.fileblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
@Import(BeanValidatorPluginsConfiguration.class)
public class Knife4j {
    @Bean(value = "fileBlog")
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                //分组名称
                .groupName("v0")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.fzcode.fileblog.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
    private ApiInfo groupApiInfo(){
        return new ApiInfoBuilder()
                .title("file-blog")
                .description("#   file服务的文档，对外暴露的服务之一")
                .termsOfServiceUrl("/file/blog")
                .contact(new Contact("fzcode","www.fzcode.com","757566833@qq.com"))
                .version("0.0.1")
                .build();
    }
}
