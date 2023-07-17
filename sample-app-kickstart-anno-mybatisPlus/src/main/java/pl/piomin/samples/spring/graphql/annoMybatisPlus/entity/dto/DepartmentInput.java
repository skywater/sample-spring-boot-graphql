package pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.dto;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@GraphQLName("DepartmentInput")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentInput {
    @GraphQLField
    @GraphQLNonNull
	private String name;
    
    @GraphQLField
	private Integer organizationId;
}
