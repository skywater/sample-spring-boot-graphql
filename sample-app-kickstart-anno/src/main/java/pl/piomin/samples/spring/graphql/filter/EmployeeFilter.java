package pl.piomin.samples.spring.graphql.filter;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import lombok.Data;

@GraphQLName("EmployeeFilter")
@Data
public class EmployeeFilter {
    @GraphQLField
	private FilterField salary;

    @GraphQLField
	private FilterField age;

    @GraphQLField
	private FilterField position;
}
