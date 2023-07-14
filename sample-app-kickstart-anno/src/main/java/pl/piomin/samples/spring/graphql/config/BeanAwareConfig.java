
/**
 * File Name: BeanAwareConfig.java
 * @date 2021年9月3日 上午9:33:00
 * Copyright (c) 2021 jpq.com All Rights Reserved.
 */

package pl.piomin.samples.spring.graphql.config;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.SmartInitializingSingleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import pl.piomin.samples.spring.graphql.repository.EmployeeRepository;



/**
 * TODO <br/>
 * @date 2021年9月3日 上午9:33:00
 * @author jpq
 * @version
 */
@Configuration
public class BeanAwareConfig implements SmartInitializingSingleton {
//	@Autowired
//	private WebMvcConfigurationSupport mvc;

	@Autowired
	private ApplicationContext appContext;
	@Autowired
	private MappingJackson2HttpMessageConverter convert;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private Environment env;
	@Autowired
	private EmployeeRepository repository;
//	@Autowired
//	private DataFetchingEnvironment dfEnv;
    
	@Override
	public void afterSingletonsInstantiated() {
		System.out.println(Arrays.asList(appContext.getBeanDefinitionNames()));
		Map<String, Object> component = appContext.getBeansWithAnnotation(Component.class);
		Map<String, DataFetcher> beansOfType = appContext.getBeansOfType(DataFetcher.class);
		System.out.println();
	}
}