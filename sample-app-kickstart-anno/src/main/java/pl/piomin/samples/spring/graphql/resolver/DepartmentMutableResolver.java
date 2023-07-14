package pl.piomin.samples.spring.graphql.resolver;

import graphql.annotations.annotationTypes.GraphQLField;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import pl.piomin.samples.spring.graphql.domain.Department;
import pl.piomin.samples.spring.graphql.domain.DepartmentInput;
import pl.piomin.samples.spring.graphql.domain.Organization;
import pl.piomin.samples.spring.graphql.repository.DepartmentRepository;
import pl.piomin.samples.spring.graphql.repository.OrganizationRepository;

@Component
@RequiredArgsConstructor
public class DepartmentMutableResolver {
	private final DepartmentRepository departmentRepository;
	private final OrganizationRepository organizationRepository;

    @GraphQLField
	public Department newDepartment(DepartmentInput departmentInput) {
		Organization organization = organizationRepository.findById(departmentInput.getOrganizationId()).get();
		return departmentRepository.save(new Department(null, departmentInput.getName(), null, organization));
	}

}
