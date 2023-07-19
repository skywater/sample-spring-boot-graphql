
/**
 * Project Name: sample-app-kickstart-anno-mybatisPlus
 * File Name: GenericFunction.java
 * @date 2023年7月19日 上午10:09:47
 * Copyright (c) 2023 jpq.com All Rights Reserved.
 */

package pl.piomin.samples.spring.graphql.annoMybatisPlus.config;

import static graphql.annotations.processor.util.NamingKit.toGraphqlName;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.apache.commons.lang3.reflect.FieldUtils;

import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.processor.ProcessingElementsContainer;
import graphql.annotations.processor.typeFunctions.DefaultTypeFunction;
import graphql.annotations.processor.typeFunctions.TypeFunction;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLType;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 针对泛型，实时生效 <br/>
 * @date 2023年7月19日 上午10:09:47
 * @author jiangpq
 * @version
 */
@Slf4j
@NoArgsConstructor
public class GenericFunction implements TypeFunction {

	private DefaultTypeFunction defaultTypeFunction;
    // 以下参数逻辑来自 ObjectFunction
	private CopyOnWriteArrayList<TypeFunction> typeFunctions;

    public GenericFunction(DefaultTypeFunction defTypeFunc) {
    	init(defTypeFunc);
    }
    public GenericFunction init(TypeFunction defTypeFunc) {
        this.defaultTypeFunction = (DefaultTypeFunction) defTypeFunc;
        List<Field> fieldsList = FieldUtils.getAllFieldsList(DefaultTypeFunction.class);
        fieldsList.forEach(e -> {
        	try {
	        	if(e.getType().isAssignableFrom(CopyOnWriteArrayList.class)) {
	        		typeFunctions = (CopyOnWriteArrayList<TypeFunction>) FieldUtils.readField(e, defTypeFunc, true);
	        	}
        	}catch (Exception ex) {
				log.error("", ex);
			}
        });
        return this;
    }


    @Override
    public String getTypeName(Class<?> aClass, AnnotatedType annotatedType) {
        GraphQLName name = aClass.getAnnotation(GraphQLName.class);
        return toGraphqlName(name == null ? aClass.getSimpleName() : name.value());
    }

    @Override
    public boolean canBuildType(Class<?> aClass, AnnotatedType annotatedType) {
        return annotatedType.isAnnotationPresent(graphql.annotations.annotationTypes.GraphQLType.class);
    }

    @Override
    public GraphQLType buildType(boolean inputType, Class<?> aClass, AnnotatedType annotatedType, ProcessingElementsContainer container) {
    	if(null == defaultTypeFunction) {
    		init(container.getDefaultTypeFunction());
    	}
    	if(!canBuildType(aClass, annotatedType)) { // 避免类中字段循环调用
    		return defaultTypeFunction.buildType(inputType, aClass, annotatedType, container);
    	}
    	// 最后一个是 ObjectFunction 
    	GraphQLType ret = typeFunctions.get(typeFunctions.size()-1).buildType(inputType, aClass, annotatedType, container);    	
    	List<Field> allFieldsList = FieldUtils.getAllFieldsList(aClass);
    	List<String> fieldNames = allFieldsList.stream().filter(e -> !Modifier.isStatic(e.getModifiers()) && e.getGenericType() instanceof ParameterizedType)
    			.map(e -> e.getName()).collect(Collectors.toList());
    	
    	Type anType = annotatedType.getType();
    	ParameterizedType ptype = (ParameterizedType) anType;
    	Type[] types = ptype.getActualTypeArguments();
//		Map<String, GraphQLType> typeRegistry = container.getTypeRegistry();
    	GraphQLObjectType retType = (GraphQLObjectType) ret;
    	StringBuilder typeName = new StringBuilder(retType.getName());
    	for(int i=0; i<types.length; i++) {
    		Type type = types[i];
    		String fieldName = fieldNames.get(i);
    		Class<?> clazz = (Class<?>)type;
    		Annotation[] annos = clazz.getDeclaredAnnotations();
    		for(Annotation anno : annos) {
    			if(!anno.annotationType().equals(GraphQLName.class)) {
    				continue;
    			}
    			typeName.append(clazz.getSimpleName());
        		AnnotatedType annoType = clazz.getDeclaredConstructors()[0].getAnnotatedReturnType();
				GraphQLType graphQLType = defaultTypeFunction.buildType(inputType, clazz, annoType, container);
				GraphQLFieldDefinition fieldDefinition = GraphQLFieldDefinition.newFieldDefinition().name(fieldName)
						.type(new GraphQLList(graphQLType)).build(); // 这里默认是 List<T>
				// 因为是泛型，可以有多个，所以要重命名！！！
				retType = retType.transform(b -> b.name(typeName.toString()).field(fieldDefinition));
				ret = (GraphQLObjectType) retType;
    		}
    	}
    	
    	return ret;
    }
}