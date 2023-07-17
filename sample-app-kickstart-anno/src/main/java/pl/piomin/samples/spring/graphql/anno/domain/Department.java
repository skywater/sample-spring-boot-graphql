package pl.piomin.samples.spring.graphql.anno.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

import java.util.Set;


@GraphQLName("Department")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
    @GraphQLField
    @GraphQLNonNull
	private Integer id;
	
    @GraphQLField
    @GraphQLNonNull
	private String name;
    
	@OneToMany(mappedBy = "department")
    @GraphQLField
	private Set<Employee> employees;
	
    @GraphQLField
	@ManyToOne(fetch = FetchType.LAZY)
	private Organization organization;
}
