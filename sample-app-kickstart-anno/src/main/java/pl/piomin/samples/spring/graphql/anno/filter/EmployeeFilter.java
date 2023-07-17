package pl.piomin.samples.spring.graphql.anno.filter;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@GraphQLName("EmployeeFilter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeFilter {
    @GraphQLField
	private FilterField salary;

    @GraphQLField
	private FilterField age;

    @GraphQLField
	private FilterField position;
}
