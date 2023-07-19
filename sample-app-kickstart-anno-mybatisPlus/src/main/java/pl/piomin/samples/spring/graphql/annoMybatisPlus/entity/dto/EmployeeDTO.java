package pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.dto;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//输入参数自动会带上 Input 前缀,所以命名去掉 Input ！！！
@GraphQLName("Employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
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
