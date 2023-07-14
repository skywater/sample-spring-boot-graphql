package pl.piomin.samples.spring.graphql.resolver;

import graphql.annotations.annotationTypes.GraphQLField;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import pl.piomin.samples.spring.graphql.domain.Department;
import pl.piomin.samples.spring.graphql.domain.Employee;
import pl.piomin.samples.spring.graphql.domain.EmployeeInput;
import pl.piomin.samples.spring.graphql.domain.Organization;
import pl.piomin.samples.spring.graphql.repository.DepartmentRepository;
import pl.piomin.samples.spring.graphql.repository.EmployeeRepository;
import pl.piomin.samples.spring.graphql.repository.OrganizationRepository;

// Multiple mutation resolvers provided. GraphQL Annotations allows only one resolver.
@Component
@graphql.kickstart.annotations.GraphQLMutationResolver
@RequiredArgsConstructor
public class EmployeeMutableResolver { // implements GraphQLServletRootObjectBuilder {

	final DepartmentRepository departmentRepository;
	final EmployeeRepository employeeRepository;
	final OrganizationRepository organizationRepository;

    @GraphQLField
	public Employee newEmployee(EmployeeInput employeeInput) {
		Department department = departmentRepository.findById(employeeInput.getDepartmentId()).get();
		Organization organization = organizationRepository.findById(employeeInput.getOrganizationId()).get();
		return employeeRepository.save(new Employee(null, employeeInput.getFirstName(), employeeInput.getLastName(),
				employeeInput.getPosition(), employeeInput.getAge(), employeeInput.getSalary(),
				department, organization));
	}

}
