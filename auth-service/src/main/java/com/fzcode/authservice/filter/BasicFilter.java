package com.fzcode.authservice.filter;

import com.alibaba.fastjson.JSON;
import com.fzcode.authservice.config.Services;
import com.fzcode.authservice.exception.CustomizeException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class BasicFilter implements Filter {
    private Services services;

    @Autowired
    public void setServices(Services services) {
        this.services = services;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (request.getRequestURI().contains("/pir/") && !auth.contains("basic")) {
            throw new CustomizeException("token不对");
        }
        String[] infoArray = auth.split(":");
        String serviceName = infoArray[0];
        String password = infoArray[1];
        if (services.getAuth().get(serviceName) != password) {
            throw new CustomizeException("token不对");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
