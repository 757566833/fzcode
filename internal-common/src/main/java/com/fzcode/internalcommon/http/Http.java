package com.fzcode.internalcommon.http;

import com.fzcode.internalcommon.dto.http.ErrorResponse;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.exception.RestTemplateCustomException;
import com.fzcode.internalcommon.utils.JSONUtils;
import com.fzcode.internalcommon.utils.ObjectUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownContentTypeException;

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
            if(errorResponse!=null){
                throw new CustomizeException(HttpStatus.valueOf(errorResponse.getStatus()),errorResponse.getError());
            }else{

                throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"未获取java的错误信息，可能跑ng上了");
            }
        } catch (RestClientResponseException e){
            throw new CustomizeException(HttpStatus.valueOf(e.getRawStatusCode()),e.getMessage());
        } catch (ResourceAccessException e){
            throw new CustomizeException(HttpStatus.GATEWAY_TIMEOUT,e.getMessage());
        } catch (UnknownContentTypeException e){
            throw new CustomizeException(HttpStatus.valueOf(e.getRawStatusCode()),e.getMessage());
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
            if(errorResponse!=null){
                throw new CustomizeException(HttpStatus.valueOf(errorResponse.getStatus()),errorResponse.getError());
            }else{

                throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"未获取java的错误信息，可能跑ng上了");
            }
        } catch (RestClientResponseException e){
            throw new CustomizeException(HttpStatus.valueOf(e.getRawStatusCode()),e.getMessage());
        } catch (ResourceAccessException e){
            throw new CustomizeException(HttpStatus.GATEWAY_TIMEOUT,e.getMessage());
        } catch (UnknownContentTypeException e){
            throw new CustomizeException(HttpStatus.valueOf(e.getRawStatusCode()),e.getMessage());
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
            if(errorResponse!=null){
                throw new CustomizeException(HttpStatus.valueOf(errorResponse.getStatus()),errorResponse.getError());
            }else{

                throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"未获取java的错误信息，可能跑ng上了");
            }
        } catch (RestClientResponseException e){
            throw new CustomizeException(HttpStatus.valueOf(e.getRawStatusCode()),e.getMessage());
        } catch (ResourceAccessException e){
            throw new CustomizeException(HttpStatus.GATEWAY_TIMEOUT,e.getMessage());
        } catch (UnknownContentTypeException e){
            throw new CustomizeException(HttpStatus.valueOf(e.getRawStatusCode()),e.getMessage());
        }
        return p.getBody();
    }


    public <P> P  get (String url, Class<P> responseType) throws CustomizeException {
        P p;
        try {
            p =   restTemplate.getForObject(url, responseType);
        }catch (RestTemplateCustomException e){
            ErrorResponse errorResponse = JSONUtils.parse(e.getBody(),ErrorResponse.class);
            if(errorResponse!=null){
                throw new CustomizeException(HttpStatus.valueOf(errorResponse.getStatus()),errorResponse.getError());
            }else{

                throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"未获取java的错误信息，可能跑ng上了");
            }
        } catch (RestClientResponseException e){
            throw new CustomizeException(HttpStatus.valueOf(e.getRawStatusCode()),e.getMessage());
        } catch (ResourceAccessException e){
            throw new CustomizeException(HttpStatus.GATEWAY_TIMEOUT,e.getMessage());
        } catch (UnknownContentTypeException e){
            throw new CustomizeException(HttpStatus.valueOf(e.getRawStatusCode()),e.getMessage());
        }
        return  p;
    }


    public <P> P  post (String url, Object params, HttpHeaders httpHeaders, Class<P> responseType) throws CustomizeException {
        System.out.println("post1");
        HttpEntity<Map<String,String>> request =  new HttpEntity(params, httpHeaders);
        P p;
        try {
            p =   restTemplate.postForObject(url, request, responseType);
        }catch (RestTemplateCustomException e){
            ErrorResponse errorResponse = JSONUtils.parse(e.getBody(),ErrorResponse.class);
            if(errorResponse!=null){
                throw new CustomizeException(HttpStatus.valueOf(errorResponse.getStatus()),errorResponse.getError());
            }else{

                throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"未获取java的错误信息，可能跑ng上了");
            }
        } catch (RestClientResponseException e){
            throw new CustomizeException(HttpStatus.valueOf(e.getRawStatusCode()),e.getMessage());
        } catch (ResourceAccessException e){
            throw new CustomizeException(HttpStatus.GATEWAY_TIMEOUT,e.getMessage());
        } catch (UnknownContentTypeException e){
            throw new CustomizeException(HttpStatus.valueOf(e.getRawStatusCode()),e.getMessage());
        }
        return  p;
    }
    public <P> P  post (String url,Object params, Class<P> responseType) throws CustomizeException {
        P p;
        try {
            p =  restTemplate.postForObject(url, params, responseType);
        }catch (RestTemplateCustomException e){
            ErrorResponse errorResponse = JSONUtils.parse(e.getBody(),ErrorResponse.class);
            if(errorResponse!=null){
                throw new CustomizeException(HttpStatus.valueOf(errorResponse.getStatus()),errorResponse.getError());
            }else{

                throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"未获取java的错误信息，可能跑ng上了");
            }
        } catch (RestClientResponseException e){
            throw new CustomizeException(HttpStatus.valueOf(e.getRawStatusCode()),e.getMessage());
        } catch (ResourceAccessException e){
            throw new CustomizeException(HttpStatus.GATEWAY_TIMEOUT,e.getMessage());
        } catch (UnknownContentTypeException e){
            throw new CustomizeException(HttpStatus.valueOf(e.getRawStatusCode()),e.getMessage());
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
            if(errorResponse!=null){
                throw new CustomizeException(HttpStatus.valueOf(errorResponse.getStatus()),errorResponse.getError());
            }else{

                throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"未获取java的错误信息，可能跑ng上了");
            }
        } catch (RestClientResponseException e){
            throw new CustomizeException(HttpStatus.valueOf(e.getRawStatusCode()),e.getMessage());
        } catch (ResourceAccessException e){
            throw new CustomizeException(HttpStatus.GATEWAY_TIMEOUT,e.getMessage());
        } catch (UnknownContentTypeException e){
            throw new CustomizeException(HttpStatus.valueOf(e.getRawStatusCode()),e.getMessage());
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
            if(errorResponse!=null){
                throw new CustomizeException(HttpStatus.valueOf(errorResponse.getStatus()),errorResponse.getError());
            }else{

                throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"未获取java的错误信息，可能跑ng上了");
            }
        } catch (RestClientResponseException e){
            throw new CustomizeException(HttpStatus.valueOf(e.getRawStatusCode()),e.getMessage());
        } catch (ResourceAccessException e){
            throw new CustomizeException(HttpStatus.GATEWAY_TIMEOUT,e.getMessage());
        } catch (UnknownContentTypeException e){
            throw new CustomizeException(HttpStatus.valueOf(e.getRawStatusCode()),e.getMessage());
        }
        return p;
    }
}
