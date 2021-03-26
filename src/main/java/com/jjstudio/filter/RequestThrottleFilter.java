package com.jjstudio.filter;

import com.jjstudio.service.JedisService;
import com.jjstudio.service.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import redis.clients.jedis.Jedis;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestThrottleFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(RequestThrottleFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JedisService jedisService;

    @Autowired
    private Jedis jedis;

    private final String LAST_REQUEST_TIMESTAMP = "timestamp";

    private final String REQUEST_COUNT = "request_count";

    private final int MAX_ALLOWED_REQUESTS_PER_MIN = 100;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        long currentTime = System.currentTimeMillis();
        String lastRequestTime = jedis.hget(username, LAST_REQUEST_TIMESTAMP);
        if (lastRequestTime != null && isUnderOneMinuteDifference(currentTime, Long.parseLong(lastRequestTime))) {
            int remainingRequests = Integer.parseInt(jedis.hget(username, REQUEST_COUNT));
            if (remainingRequests < 1) {
                // Block request
                response.sendError(429,
                        "Too many request! The limit for this API is 5 requests per minute. Please wait a moment before sending another request.");
                logger.debug("Rejected request due too many requests from the same user");
            } else {
                jedis.hset(username, LAST_REQUEST_TIMESTAMP, Long.toString(currentTime));
                jedis.hset(username, REQUEST_COUNT, Integer.toString(remainingRequests - 1));
            }
        } else {
            jedis.hset(username, LAST_REQUEST_TIMESTAMP, Long.toString(currentTime));
            jedis.hset(username, REQUEST_COUNT, Integer.toString(MAX_ALLOWED_REQUESTS_PER_MIN));
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return "/v1/auth".equals(request.getRequestURI());
    }

    private boolean isUnderOneMinuteDifference(long endTime, long startTime) {
        return endTime - startTime <= 60000;
    }
}
