package com.fzcode.internalcommon.http;

import com.fzcode.internalcommon.dto.http.ErrorResponse;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.exception.RestTemplateCustomException;
import com.fzcode.internalcommon.utils.JSONUtils;
import com.fzcode.internalcommon.utils.ObjectUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

public class Http {
    RestTemplate restTemplate;
    public Http(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    public <P> P  get (String url, Object params, HttpHeaders httpHeaders, Class<P> responseType) throws CustomizeException {
        HttpEntity<String> request =  new HttpEntity<>(null, httpHeaders);
        URI uri = ObjectUtils.object2URI(url,params);
        ResponseEntity<P> p;
        try {
            p  =  restTemplate.exchange(uri, HttpMethod.GET, request, responseType);
        }catch (RestTemplateCustomException e){
            ErrorResponse errorResponse = JSONUtils.parse(e.getBody(),ErrorResponse.class);
            throw new CustomizeException(errorResponse.getStatus(),errorResponse.getError());
        }
        return p.getBody();
    }
    public <P> P  get (String url, Object params, Class<P> responseType) throws CustomizeException {
        URI uri = ObjectUtils.object2URI(url,params);
        P p;
        try {
            p  =  restTemplate.getForObject(uri, responseType);
        }catch (RestTemplateCustomException e){
            ErrorResponse errorResponse = JSONUtils.parse(e.getBody(),ErrorResponse.class);
            throw new CustomizeException(errorResponse.getStatus(),errorResponse.getError());
        }
        return  p;
    }

    public <P> P  get (String url, HttpHeaders httpHeaders, Class<P> responseType) throws CustomizeException {
        HttpEntity<String> request =  new HttpEntity<>(null, httpHeaders);
        ResponseEntity<P> p;
        try {
            p  =  restTemplate.exchange(url, HttpMethod.GET, request, responseType);
        }catch (RestTemplateCustomException e){
            ErrorResponse errorResponse = JSONUtils.parse(e.getBody(),ErrorResponse.class);
            throw new CustomizeException(errorResponse.getStatus(),errorResponse.getError());
        }
        return p.getBody();
    }


    public <P> P  get (String url, Class<P> responseType) throws CustomizeException {
        P p;
        try {
            p =   restTemplate.getForObject(url, responseType);
        }catch (RestTemplateCustomException e){
            System.out.println("body"+e.getBody());
            ErrorResponse errorResponse = JSONUtils.parse(e.getBody(),ErrorResponse.class);
            throw new CustomizeException(errorResponse.getStatus(),errorResponse.getError());
        }
        return  p;
    }


    public <P> P  post (String url, Object params, HttpHeaders httpHeaders, Class<P> responseType) throws CustomizeException {
        HttpEntity<Map<String,String>> request =  new HttpEntity(params, httpHeaders);
        P p;
        try {
            p =   restTemplate.postForObject(url, request, responseType);
        }catch (RestTemplateCustomException e){
            ErrorResponse errorResponse = JSONUtils.parse(e.getBody(),ErrorResponse.class);
            throw new CustomizeException(errorResponse.getStatus(),errorResponse.getError());
        }
        return  p;
    }
    public <P> P  post (String url,Object params, Class<P> responseType) throws CustomizeException {
        P p;
        try {
            p =  restTemplate.postForObject(url, params, responseType);
        }catch (RestTemplateCustomException e){
            ErrorResponse errorResponse = JSONUtils.parse(e.getBody(),ErrorResponse.class);
            throw new CustomizeException(errorResponse.getStatus(),errorResponse.getError());
        }
        return  p;
    }

    public <P> P  post (String url, HttpHeaders httpHeaders, Class<P> responseType) throws CustomizeException {
        HttpEntity<String> request =  new HttpEntity<>(null, httpHeaders);
        P p;
        try {
            p = restTemplate.postForObject(url,request, responseType);
        }catch (RestTemplateCustomException e){
            ErrorResponse errorResponse = JSONUtils.parse(e.getBody(),ErrorResponse.class);
            throw new CustomizeException(errorResponse.getStatus(),errorResponse.getError());
        }
        return p;
    }
    public <P> P  post (String url, Class<P> responseType) throws CustomizeException {
        HttpEntity<String> request =  new HttpEntity<>(null, null);
        P p;
        try {
            p = restTemplate.postForObject(url,request, responseType);
        }catch (RestTemplateCustomException e){
            ErrorResponse errorResponse = JSONUtils.parse(e.getBody(),ErrorResponse.class);
            throw new CustomizeException(errorResponse.getStatus(),errorResponse.getError());
        }
        return p;
    }
}
