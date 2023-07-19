package pl.piomin.samples.spring.graphql.annoMybatisPlus.entity.dto;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 输入参数自动会带上 Input 前缀,所以命名去掉 Input ！！！
@GraphQLName("Department")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    @GraphQLField
    @GraphQLNonNull
	private String name;
    
    @GraphQLField
	private Integer organizationId;
}
