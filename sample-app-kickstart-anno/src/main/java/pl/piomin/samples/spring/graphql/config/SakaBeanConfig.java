
/**
 * File Name: SakaBeanConfig.java
 * @date 2021年10月20日 下午1:32:47
 * Copyright (c) 2021 jpq.com All Rights Reserved.
 */

package pl.piomin.samples.spring.graphql.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import graphql.annotations.AnnotationsSchemaCreator;
import graphql.schema.GraphQLSchema;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pl.piomin.samples.spring.graphql.resolver.EmployeeMutableResolver;
import pl.piomin.samples.spring.graphql.resolver.EmployeeQueryResolver;

/**
 * sale配置bean类 <br/>
 * @date 2021年10月20日 下午1:32:47
 * @author jpq
 * @version
 */
@Configuration
@Slf4j
@Data
public class SakaBeanConfig {

    @Bean
    public GraphQLSchema graphQLSchema() {
        return AnnotationsSchemaCreator.newAnnotationsSchema()
	                  .query(EmployeeQueryResolver.class)
	                  .mutation(EmployeeMutableResolver.class)
	                  .build();
    }
    
}