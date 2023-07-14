package pl.piomin.samples.spring.graphql.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import graphql.annotations.annotationTypes.GraphQLField;
import pl.piomin.samples.spring.graphql.domain.Employee;
import pl.piomin.samples.spring.graphql.filter.EmployeeFilter;
import pl.piomin.samples.spring.graphql.filter.FilterField;
import pl.piomin.samples.spring.graphql.repository.EmployeeRepository;

// Multiple query resolvers provided. GraphQL Annotations allows only one resolver.
@Component
@graphql.kickstart.annotations.GraphQLQueryResolver
public class EmployeeQueryResolver { // implements GraphQLServletRootObjectBuilder {
	@Autowired
	private EmployeeRepository repository;

    @GraphQLField
	public Iterable<Employee> employees() {
		return repository.findAll();
	}

    @GraphQLField
	public Employee employee(Integer id) {
		return repository.findById(id).orElse(null);
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
			return repository.findAll(spec);
		else
			return repository.findAll();
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

//	@Override
//	public Object build() {
//		return this;
//	}
//
//	@Override
//	public Object build(HttpServletRequest req) {
//		return build();
//		 
//	}
//
//	@Override
//	public Object build(HandshakeRequest req) {
//		return build();
//	}
}
