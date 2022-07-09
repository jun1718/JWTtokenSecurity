package com.example.security_jwt.filter;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class ApiCheckFilter extends OncePerRequestFilter {
    private AntPathMatcher antPathMatcher;
    private String pattern;

    public ApiCheckFilter(String pattern) {
        this.antPathMatcher = new AntPathMatcher();
        this.pattern = pattern;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("APiCheckFilter-----------------------------------1");
        log.info("------- match : " + antPathMatcher.match(pattern, request.getRequestURI()));

        if (antPathMatcher.match(pattern, request.getRequestURI())) {
            log.info("Matched-----------------------------------2");

            // TODO 3 : 토큰해석기
            boolean checkHeader = checkAuthHeader(request); /// Authorization header 값(jwt) 확인
            if (checkHeader) {
//                filterChain.doFilter(request, response);
                response.setStatus(HttpServletResponse.SC_OK);
                JSONObject json = new JSONObject();
                json.put("result", "success");

                PrintWriter out = response.getWriter();
                out.print(json);
                return;
            }

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=utf-8");
            JSONObject json = new JSONObject();
            json.put("code", "403");
            json.put("message", "FAIL CHECK API TOKEN");

            PrintWriter out = response.getWriter();
            out.print(json);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean checkAuthHeader(HttpServletRequest request) {
        boolean checkResult = false;
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authHeader)) {
            log.info("Authorization exist: " + authHeader);
            if (authHeader.equals("12345678")) { // jwt
                checkResult = true;
            }
        }

        return checkResult;
    }
}
