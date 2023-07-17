
/**
 * Project Name: sample-app-kickstart-anno
 * File Name: RootObjectBuilder.java
 * @date 2023年7月14日 下午4:01:42
 * Copyright (c) 2023 jpq.com All Rights Reserved.
 */

package pl.piomin.samples.spring.graphql.config;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.HandshakeRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import graphql.kickstart.execution.GraphQLObjectMapper;
import graphql.kickstart.execution.GraphQLRequest;
import graphql.kickstart.servlet.core.GraphQLServletRootObjectBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.piomin.samples.spring.graphql.resolver.EmployeeMutableResolver;
import pl.piomin.samples.spring.graphql.resolver.EmployeeQueryResolver;

/**
 * TODO <br/>
 * @date 2023年7月14日 下午4:01:42
 * @author jiangpq
 * @version
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RootObjectBuilder extends OncePerRequestFilter implements GraphQLServletRootObjectBuilder {

	private final EmployeeQueryResolver query;
	private final EmployeeMutableResolver mutable;
	private final GraphQLObjectMapper graphQLObjectMapper;
	private ContentCachingRequestWrapper reqWrapper;
	private Object source;
	private static final String QUERY_STR = "query";
	private static final String MUTABLE_STR = "mutable";
	
	@Override
	public Object build() {
		return source;
	}

	@Override
	public Object build(HttpServletRequest req) {
		byte[] contentAsByteArray = reqWrapper.getContentAsByteArray();
		String body = new String(contentAsByteArray);
		GraphQLRequest graphqlReq = null;
	    try {
	    	graphqlReq = graphQLObjectMapper.readGraphQLRequest(body);
		} catch (IOException e) {
			log.error("请求参数={},转换异常：", body, e);
		}
	    String operationName = StringUtils.defaultIfBlank(graphqlReq.getOperationName(), null);
	    String queryStr = StringUtils.trim(graphqlReq.getQuery());
	    for(String line : queryStr.split("\n")) {
	    	line = StringUtils.trim(line);
	    	if((StringUtils.isBlank(operationName) && StringUtils.startsWithAny(line, QUERY_STR, MUTABLE_STR)) || StringUtils.contains(line, operationName)) {
	    	    source = line.startsWith(QUERY_STR) ? query : mutable;
	    		return source;
	    	}
	    }
	    log.error("graphql查询条件不正确，未找到对应的source");
		return null;
	}

	@Override
	public Object build(HandshakeRequest req) {
		return build();
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		reqWrapper = new ContentCachingRequestWrapper(req);
		filterChain.doFilter(reqWrapper, response);
	}
}

 