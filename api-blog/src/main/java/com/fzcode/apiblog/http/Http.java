package com.fzcode.apiblog.http;

import com.fzcode.apiblog.exception.CustomizeException;
import com.fzcode.internalcommon.exception.RestTemplateCustomException;
import com.fzcode.internalcommon.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Component
public class Http {
    RestTemplate restTemplate;
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public <P> P  get (String url, Object params, HttpHeaders httpHeaders, Class<P> responseType){
        HttpEntity<String> request =  new HttpEntity<>(null, httpHeaders);
        URI uri = ObjectUtils.object2URI(url,params);
        ResponseEntity<P> resEntity =  restTemplate.exchange(uri, HttpMethod.GET, request, responseType);
        return resEntity.getBody();
    }
    public <P> P  get (String url, Object params, Class<P> responseType){
        URI uri = ObjectUtils.object2URI(url,params);
        return  restTemplate.getForObject(uri, responseType);
    }

    public <P> P  get (String url, HttpHeaders httpHeaders, Class<P> responseType){
        HttpEntity<String> request =  new HttpEntity<>(null, httpHeaders);
        ResponseEntity<P> resEntity =  restTemplate.exchange(url, HttpMethod.GET, request, responseType);
        return resEntity.getBody();
    }


    public <P> P  get (String url, Class<P> responseType){
        return  restTemplate.getForObject(url, responseType);
    }


    public <P> P  post (String url, Object params, HttpHeaders httpHeaders, Class<P> responseType){
        HttpEntity< Map<String,String>> request =  new HttpEntity(params, httpHeaders);
        return   restTemplate.postForObject(url, request, responseType);
    }
    public <P> P  post (String url,Object params, Class<P> responseType){
        return   restTemplate.postForObject(url, params, responseType);
    }

    public <P> P  post (String url, HttpHeaders httpHeaders, Class<P> responseType){
        HttpEntity<String> request =  new HttpEntity<>(null, httpHeaders);
        return   restTemplate.postForObject(url, request, responseType);
    }
    public <P> P  post (String url, Class<P> responseType) throws CustomizeException {
        HttpEntity<String> request =  new HttpEntity<>(null, null);
        ResponseEntity<P> p = null;
        try {
            p = restTemplate.postForEntity(url,request, responseType);
        }catch (RestTemplateCustomException e){
           System.out.println(e.getMessage());
            System.out.println(e.getBody());
            throw new CustomizeException("错了");
        }
        return p.getBody();
    }

}
