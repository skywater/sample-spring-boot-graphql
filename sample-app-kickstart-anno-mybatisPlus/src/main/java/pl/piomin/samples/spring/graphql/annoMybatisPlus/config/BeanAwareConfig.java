
/**
 * File Name: BeanAwareConfig.java
 * @date 2021年9月3日 上午9:33:00
 * Copyright (c) 2021 jpq.com All Rights Reserved.
 */

package pl.piomin.samples.spring.graphql.annoMybatisPlus.config;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.SmartInitializingSingleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.annotations.processor.DirectiveAndWiring;
import graphql.annotations.processor.GraphQLAnnotations;
import graphql.annotations.processor.ProcessingElementsContainer;
import graphql.annotations.processor.typeFunctions.DefaultTypeFunction;
import graphql.annotations.processor.typeFunctions.TypeFunction;
import graphql.relay.Relay;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.resolver.MutationResolver;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.resolver.QueryResolver;



/**
 * TODO <br/>
 * @date 2021年9月3日 上午9:33:00
 * @author jpq
 * @version
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class BeanAwareConfig implements SmartInitializingSingleton {
	@Autowired
    private DataSource dataSource;
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
	private final List<TypeFunction> typeFunctions;
	private final GraphQLSchema graphQLSchema;
	private final GraphQLAnnotations graphQLAnnotations;
//	@Autowired
//	private DataFetchingEnvironment dfEnv;
    
	@Override
	public void afterSingletonsInstantiated() {
//		readSql("classpath:data.sql");
		ProcessingElementsContainer container = graphQLAnnotations.getContainer();
		Map<String, GraphQLType> typeRegistry = graphQLAnnotations.getTypeRegistry();
        Map<String, DirectiveAndWiring> directiveRegistry = container.getDirectiveRegistry();
        Map<Class<?>, Set<Class<?>>> extensionsTypeRegistry = container.getExtensionsTypeRegistry();
		System.out.println("typeRegistry" + new TreeSet<>(typeRegistry.keySet()));
		System.out.println("directiveRegistry" + new TreeSet<>(directiveRegistry.keySet()));
		System.out.println("extensionsTypeRegistry" + new TreeSet<>(extensionsTypeRegistry.keySet()));
		System.out.println(1);
	}
	
	private void readSql(String location) { // ScriptRunner
        if (StringUtils.hasText(location)) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.setContinueOnError(true);
//            populator.setSeparator(";");
            try {
                ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                populator.addScripts(resolver.getResources(location));
                DatabasePopulatorUtils.execute(populator, dataSource);
            } catch (DataAccessException e) {
                log.warn("execute sql error", e);
            } catch (Exception e1) {
                log.warn("failed to initialize dataSource from schema file {} ", location, e1);
            }
        }
	}
}