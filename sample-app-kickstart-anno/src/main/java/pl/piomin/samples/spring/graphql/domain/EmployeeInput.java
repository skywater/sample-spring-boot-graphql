package pl.piomin.samples.spring.graphql.domain;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@GraphQLName("EmployeeInput")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInput {
    @GraphQLField
    @GraphQLNonNull
	private String firstName;

    @GraphQLField
    @GraphQLNonNull
	private String lastName;
    
    @GraphQLField
    @GraphQLNonNull
	private String position;
    
    @GraphQLField
	private Integer salary;

    @GraphQLField
	private Integer age;

    @GraphQLField
    @GraphQLNonNull
	private Integer departmentId;

    @GraphQLField
    @GraphQLNonNull
	private Integer organizationId;
}
