package pl.piomin.samples.spring.graphql.anno.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLName("Employee")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
    @GraphQLField
    @GraphQLNonNull
	private Integer id;

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
	@ManyToOne(fetch = FetchType.LAZY)
	private Department department;

    @GraphQLField
	@ManyToOne(fetch = FetchType.LAZY)
	private Organization organization;
}
