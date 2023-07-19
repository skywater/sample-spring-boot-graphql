
/**
 * File Name: SakaBeanConfig.java
 * @date 2021年10月20日 下午1:32:47
 * Copyright (c) 2021 jpq.com All Rights Reserved.
 */

package pl.piomin.samples.spring.graphql.annoMybatisPlus.config;


import static graphql.Scalars.GraphQLBoolean;
import static graphql.Scalars.GraphQLString;
import static graphql.Scalars.GraphQLInt;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLNonNull.nonNull;
import static graphql.schema.GraphQLObjectType.newObject;
import static java.util.Objects.nonNull;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

import graphql.annotations.AnnotationsSchemaCreator;
import graphql.annotations.processor.GraphQLAnnotations;
import graphql.annotations.processor.ProcessingElementsContainer;
import graphql.annotations.processor.typeFunctions.DefaultTypeFunction;
import graphql.kickstart.autoconfigure.annotations.GraphQLAnnotationsProperties;
import graphql.relay.Relay;
import graphql.scalar.GraphqlStringCoercing;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLScalarType;
import graphql.schema.GraphQLSchema;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.resolver.MutationResolver;
import pl.piomin.samples.spring.graphql.annoMybatisPlus.resolver.QueryResolver;

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
	private final GraphQLAnnotationsProperties graphQLAnnotationsProperties;
//    private final DefaultTypeFunction defaultTypeFunction;
	
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 若要兼容 pagehelper，这里必须注释掉
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }
    
    @Bean
    @Primary
    public GraphQLSchema custGraphQLSchema(GraphQLAnnotations graphQLAnnotations) {
    	GraphQLSchema ret = AnnotationsSchemaCreator.newAnnotationsSchema()
    			.setAnnotationsProcessor(graphQLAnnotations).query(QueryResolver.class)
    			.mutation(MutationResolver.class).build();
        return ret;
    }

    // GraphQLAnnotationsAutoConfiguration
    @Bean
    public GraphQLAnnotations graphQLAnnotations() {
        GraphQLAnnotations graphQLAnnotations = new GraphQLAnnotations();
        ProcessingElementsContainer container = graphQLAnnotations.getContainer();
        if (nonNull(graphQLAnnotationsProperties.getInputPrefix())) {
        	container.setInputPrefix(graphQLAnnotationsProperties.getInputPrefix());
        }
        if (nonNull(graphQLAnnotationsProperties.getInputSuffix())) {
        	container.setInputSuffix(graphQLAnnotationsProperties.getInputSuffix());
        }        

//        Relay relay = container.getRelay();
//        Map<String, GraphQLType> typeRegistry = container.getTypeRegistry(); // Relay.pageInfoType
//        GraphQLObjectType connectionType = relay.connectionType("Default", Relay.pageInfoType, Collections.emptyList());
        
//        GraphQLObjectType defPageInfoType = newObject()
//                .name("DefPageInfo")
//                .description("Information about pagination in a connection.")
//                .field(newFieldDefinition().name("hasNextPage").type(nonNull(GraphQLBoolean))
//                        .description("When paginating forwards, are there more items?"))
//                .field(newFieldDefinition().name("hasPreviousPage").type(nonNull(GraphQLBoolean))
//                        .description("When paginating backwards, are there more items?"))
//                .field(newFieldDefinition().name("startCursor").type(GraphQLString)
//                        .description("When paginating backwards, the cursor to continue."))
//                .field(newFieldDefinition().name("endCursor").type(GraphQLString)
//                        .description("When paginating forwards, the cursor to continue."))
//                .field(newFieldDefinition().name("pageNum").type(GraphQLInt)
//                        .description("When paginating forwards, the cursor to continue."))
//                .field(newFieldDefinition().name("pageSize").type(GraphQLInt)
//                        .description("When paginating forwards, the cursor to continue."))
//                .field(newFieldDefinition().name("pages").type(GraphQLInt)
//                        .description("When paginating forwards, the cursor to continue."))
//                .field(newFieldDefinition().name("total").type(GraphQLInt)
//                        .description("When paginating forwards, the cursor to continue."))
//                .build();
//        typeRegistry.put("DefPageInfo", defPageInfoType);
        DefaultTypeFunction defaultTypeFunction = (DefaultTypeFunction) container.getDefaultTypeFunction();
        defaultTypeFunction.register(new GenericFunction(defaultTypeFunction));
        return graphQLAnnotations;
      }
    
    @Deprecated
    public static final GraphQLScalarType GraphQLList = GraphQLScalarType.newScalar()
            .name("List").description("Built-in List").coercing(new GraphqlStringCoercing()).build();

    @Deprecated
    public static final GraphQLObjectType naivePageInfoType = newObject()
            .name("NaivePageInfo")
            .description("Information about pagination in a connection.")
            .field(newFieldDefinition()
                    .name("list")
                    .type(nonNull(GraphQLList))
                    .description("When paginating datas, are there more items?"))
            .build();
}