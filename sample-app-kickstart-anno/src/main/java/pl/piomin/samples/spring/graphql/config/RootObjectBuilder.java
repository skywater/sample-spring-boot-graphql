
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
	
	@Override
	public Object build() {
		return source;
	}

	@Override
	public Object build(HttpServletRequest req) {
		byte[] contentAsByteArray = reqWrapper.getContentAsByteArray();
		String body = new String(contentAsByteArray);
		GraphQLRequest graphqlRequest = null;
	    try {
	    	graphqlRequest = graphQLObjectMapper.readGraphQLRequest(body);
		} catch (IOException e) {
			log.error("请求参数={},转换异常：", body, e);
		}
	    String operationName = graphqlRequest.getOperationName();
	    String queryStr = graphqlRequest.getQuery();
	    for(String line : queryStr.split("\n")) {
	    	if(line.contains(operationName)) {
	    	    source = line.startsWith("query") ? query : mutable;
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

 