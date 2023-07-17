
/**
 * Project Name: sample-app-kickstart-anno
 * File Name: QueryResolver.java
 * @date 2023年7月17日 下午2:50:58
 * Copyright (c) 2023 jpq.com All Rights Reserved.
 */

package pl.piomin.samples.spring.graphql.anno.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.RequiredArgsConstructor;
import pl.piomin.samples.spring.graphql.anno.domain.Department;
import pl.piomin.samples.spring.graphql.anno.domain.Employee;
import pl.piomin.samples.spring.graphql.anno.domain.Organization;
import pl.piomin.samples.spring.graphql.anno.filter.EmployeeFilter;
import pl.piomin.samples.spring.graphql.anno.filter.FilterField;
import pl.piomin.samples.spring.graphql.anno.repository.DepartmentRepository;
import pl.piomin.samples.spring.graphql.anno.repository.EmployeeRepository;
import pl.piomin.samples.spring.graphql.anno.repository.OrganizationRepository;

/**
 * TODO <br/>
 * @date 2023年7月17日 下午2:50:58
 * @author jiangpq
 * @version
 */
//Multiple query resolvers provided. GraphQL Annotations allows only one resolver.
@Component
@graphql.kickstart.annotations.GraphQLQueryResolver
@RequiredArgsConstructor
public class QueryResolver {
	private final DepartmentRepository deptRepository;
	private final EmployeeRepository empRepository;
	private final OrganizationRepository orgRepository;
    
	@GraphQLField
	public Iterable<Department> departments(DataFetchingEnvironment environment) {
		DataFetchingFieldSelectionSet s = environment.getSelectionSet();
		List<Specification<Department>> specifications = new ArrayList<>();
		if (s.contains("employees") && !s.contains("organization"))
			return deptRepository.findAll(fetchEmployees());
		else if (!s.contains("employees") && s.contains("organization"))
			return deptRepository.findAll(fetchOrganization());
		else if (s.contains("employees") && s.contains("organization"))
			return deptRepository.findAll(fetchEmployees().and(fetchOrganization()));
		else
			return deptRepository.findAll();
	}

    @GraphQLField
	public Department department(Integer id, DataFetchingEnvironment environment) {
		Specification<Department> spec = byId(id);
		DataFetchingFieldSelectionSet selectionSet = environment.getSelectionSet();
		if (selectionSet.contains("employees"))
			spec = spec.and(fetchEmployees());
		if (selectionSet.contains("organization"))
			spec = spec.and(fetchOrganization());
		return deptRepository.findOne(spec).orElseThrow(NoSuchElementException::new);
	}

	private Specification<Department> fetchOrganization() {
		return (Specification<Department>) (root, query, builder) -> {
			Fetch<Department, Organization> f = root.fetch("organization", JoinType.LEFT);
			Join<Department, Organization> join = (Join<Department, Organization>) f;
			return join.getOn();
		};
	}

	private Specification<Department> fetchEmployees() {
		return (Specification<Department>) (root, query, builder) -> {
			Fetch<Department, Employee> f = root.fetch("employees", JoinType.LEFT);
			Join<Department, Employee> join = (Join<Department, Employee>) f;
			return join.getOn();
		};
	}

	private Specification<Department> byId(Integer id) {
		return (Specification<Department>) (root, query, builder) -> builder.equal(root.get("id"), id);
	}
	
    @GraphQLField
	public Iterable<Employee> employees() {
		return empRepository.findAll();
	}

    @GraphQLField
	public Employee employee(Integer id) {
		return empRepository.findById(id).orElse(null);
	}

    @GraphQLField
	public Iterable<Employee> employeesWithFilter(EmployeeFilter filter) {
		Specification<Employee> spec = null;
		if (filter.getSalary() != null)
			spec = bySalary(filter.getSalary());
		if (filter.getAge() != null)
			spec = (spec == null ? byAge(filter.getAge()) : spec.and(byAge(filter.getAge())));
		if (filter.getPosition() != null)
			spec = (spec == null ? byPosition(filter.getPosition()) :
					spec.and(byPosition(filter.getPosition())));
		if (spec != null)
			return empRepository.findAll(spec);
		else
			return empRepository.findAll();
	}

    @GraphQLField
	public Iterable<Organization> organizations() {
		return orgRepository.findAll();
	}

    @GraphQLField
	public Organization organization(Integer id) {
		return orgRepository.findById(id).orElseThrow(() -> new RuntimeException("数据不存在！"));
	}

	private Specification<Employee> bySalary(FilterField filterField) {
		return (Specification<Employee>) (root, query, builder) -> filterField.generateCriteria(builder, root.get("salary"));
	}

	private Specification<Employee> byAge(FilterField filterField) {
		return (Specification<Employee>) (root, query, builder) -> filterField.generateCriteria(builder, root.get("age"));
	}

	private Specification<Employee> byPosition(FilterField filterField) {
		return (Specification<Employee>) (root, query, builder) -> filterField.generateCriteria(builder, root.get("position"));
	}
}

 