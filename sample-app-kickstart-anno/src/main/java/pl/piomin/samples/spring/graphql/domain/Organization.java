package pl.piomin.samples.spring.graphql.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

import java.util.Set;

@GraphQLName("Organization")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Organization {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
    @GraphQLField
    @GraphQLNonNull
	private Integer id;

    @GraphQLField
    @GraphQLNonNull
	private String name;

    @GraphQLField
	@OneToMany(mappedBy = "organization")
	private Set<Department> departments;

    @GraphQLField
	@OneToMany(mappedBy = "organization")
	private Set<Employee> employees;
}
