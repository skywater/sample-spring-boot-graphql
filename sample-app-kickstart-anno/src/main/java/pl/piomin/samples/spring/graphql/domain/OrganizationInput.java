package pl.piomin.samples.spring.graphql.domain;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@GraphQLName("OrganizationInput")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationInput {
    @GraphQLField
    @GraphQLNonNull
	private String name;
}
